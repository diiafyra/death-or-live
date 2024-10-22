/*
 * To change this license header, choose License Headerlt in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import models.OrderInfo.Order;
import models.OrderInfo.Order_detail;
import models.ProductInfo.Product;
import models.ProductInfo.ProductEx;
import org.jfree.data.general.DefaultPieDataset;

import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author DELL
 */
public class cndb {
  
    String driver = "org.sqlite.JDBC";
    String url = "jdbc:sqlite:QLBH_DOL.db";
    Connection conn = null;

    private cndb (){       
        try {
            Class.forName(driver);//tải jdbc driver vào bộ nhớ
            conn = DriverManager.getConnection(url); //đăng kí kết nối vs database qua địa chỉ lưu vào biến tham chiếu conn
            System.out.println("KẾT NỐI THÀNH CÔNG");
        } catch (Exception ex) {
            Logger.getLogger(cndb.class.getName()).log(Level.SEVERE, null, ex);
        }   

    }
    
//singleton pattern nhằm chỉ tạo ra 1 đối tượng cndb duy nhất trong quá trình chạy
    private static cndb instance;    
    //1
    //singleton pattern nhằm chỉ tạo ra 1 đối tượng cndb duy nhất trong quá trình chạy
    public static cndb getInstance(){
        if(instance == null){
            instance = new cndb();
        }
        return instance;
    }
    
    public void close(){
        try {
            if(conn != null && !conn.isClosed()){
                conn.close();
            } 
            if( pre != null){
                pre.close();
            }
       } catch (Exception e) {
            System.err.println("Close Error : " + e); 
        }
    }
    
    public void open(){
        try {
            if(conn == null || conn.isClosed()){
                conn = DriverManager.getConnection(url);
            } 
       } catch (Exception e) {
            System.err.println("Open Error : " + e); 
        }
    }

    private PreparedStatement pre;
    
    //Truy vấn đơn hàng trong database lưu vào 1 list Order
    public List<Order> allOrders(){
        List<Order> allOrders = new ArrayList<>();
        
         try{
            String sql = "SELECT o.ID_O, o.NAME_C, o.DATE_O, o.DATE_D, o.ADDR, o.PAY_TYPE, o.DEL_STT, od.ID_P, od.ID_P, od.QUAL, od.DISCOUNT "
                    + "FROM ORDERS o "
                    + "JOIN ORDERS_DETAIL od ON o.ID_O = od.ID_O "
                    + "JOIN PRODUCTS p ON od.ID_P = p.ID_P";//truy vấn sql 
            pre = conn.prepareStatement(sql); //pre một lệnh truy vấn sql select chuẩn bị thực thi trên database
            ResultSet rlt = pre.executeQuery();//thực hiện việc truy vấn và trả kết quả vào 1 đối tượng ResultSet tên rlt
            
            Map<String, Order> orderMap = new HashMap<>();
            while (rlt.next()) {
                String orderId = rlt.getString("ID_O");
                String date = rlt.getString("DATE_O");
                //System.out.print(""+date);
                //Nếu dòng tiếp theo có orderId không có trong map chưa nếu chưa thì thêm ..
                if (!orderMap.containsKey(orderId)) {
                    orderMap.put(orderId, new Order(orderId, rlt.getString("NAME_C"), rlt.getString("DATE_O"),  rlt.getString("DATE_D"),
                            rlt.getString("ADDR"), rlt.getString("PAY_TYPE"), rlt.getInt("DEL_STT"),
                            new ArrayList<>(), 0));
                }
                Order order = orderMap.get(orderId);
                String productId = rlt.getString("ID_P");
                int quantity = rlt.getInt("QUAL");
                int discount= rlt.getInt("DISCOUNT"); 
                order.setTotal_amount(order.getTotal_amount() + quantity * rlt.getInt("DISCOUNT"));
                List<Order_detail> orderDetail = order.getOrder_detail();
                orderDetail.add(new Order_detail(productId,quantity, discount));
            }
            allOrders.addAll(orderMap.values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.print(allOrders.size());
        return allOrders;
    }
    
    
    private Map<String, Integer> orderDetailIdCounters = new HashMap<>();

    private String generateOrderDetailId(String id_o) {
        int counter = orderDetailIdCounters.getOrDefault(id_o, 0)+1 ;
        orderDetailIdCounters.put(id_o, counter);
        String paddedCounter = String.format("%03d", counter % 1000); // Đảm bảo số có 3 chữ số bằng cách thêm số 0 vào trước nếu cần
        return id_o + paddedCounter;
    }
    
    public String generateOrderId() throws SQLException {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = today.format(formatter);        
        String datePart = today.format(DateTimeFormatter.ofPattern("yyyyMMdd")); // Đổi "yyyymmdd" thành "yyyyMMdd"
        String sql = "SELECT COUNT(*) FROM ORDERS WHERE DATE(DATE_O) = ?";//truy vấn sql
        pre = conn.prepareStatement(sql);
        pre.setString(1, formattedDate);
        ResultSet rlt = pre.executeQuery();
        int counter = rlt.getInt("COUNT(*)");
        String paddedCounter = String.format("%03d", counter % 1000); 
        String orderId = datePart + paddedCounter;
        return orderId;
    }


    public int orderInsert(String id_o, String name_c, String date_o, String date_d, String addr, String pay_type, int del_stt, List<Order_detail> order_detail){
            
        int status = 0;
        try {
            String sql = "Insert Into orders(ID_O, NAME_C, DATE_O, DATE_D, ADDR, PAY_TYPE, DEL_STT) values(?, ?, ?, ?, ?, ?, ?)";
            pre = conn.prepareStatement(sql);
            pre.setString(1, id_o);
            pre.setString(2, name_c);
            pre.setString(3, date_o);

            pre.setString(7, date_d);

            pre.setString(5, addr);
            pre.setString(6, pay_type);
            pre.setInt(7, del_stt);
            
            status = pre.executeUpdate();

        insertOD(id_o,order_detail);
        
        } catch (Exception e) {
            System.err.println("Order Insert Error : " + e);
            JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage(), "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
            status = -1;
        }

        return status;
    }
     //Phương thức chỉnh sửa đơn hàng
    public int orderEdit(String id_o, String name_c, String date_o, String date_d, String addr, String pay_type, int del_stt, List<Order_detail> order_detail){
            
        int status = 0;
        try {
            String sql = "UPDATE ORDERS "
                    + "SET NAME_C=?, DATE_O=?, DATE_D =?, ADDR=?, PAY_TYPE=?, DEL_STT=? "
                    + "WHERE ID_O=?";
            pre = conn.prepareStatement(sql);
            pre.setString(7, id_o);
            pre.setString(1, name_c);
            pre.setString(2, date_o);
            pre.setString(3, date_d);
            pre.setString(4, addr);
            pre.setString(5, pay_type);
            pre.setInt(6, del_stt);
            
            status = pre.executeUpdate();
            String deleteOrderDetailSql = "DELETE FROM ORDERS_DETAIL WHERE ID_O = ?";
            PreparedStatement deleteOrderDetailStmt = conn.prepareStatement(deleteOrderDetailSql);
            deleteOrderDetailStmt.setString(1, id_o);
            deleteOrderDetailStmt.executeUpdate();
            
            insertOD(id_o,order_detail);
        
        } catch (Exception e) {
            System.err.println("customerInsert Error : " + e);
            JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage(), "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
            status = -1;
        }

        return status;
        }
        
    public int insertOD(String id_o, List< Order_detail> order_detail) {
        int status = 0;       
        try {
        String orderDetailSql = "INSERT INTO orders_detail (ID_O, ID_O_D, ID_P, QUAL, DISCOUNT) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement orderDetailStmt = conn.prepareStatement(orderDetailSql);
        for (Order_detail od : order_detail) {
            orderDetailStmt.setString(1, id_o); // ID của đơn hàng
            orderDetailStmt.setString(2, generateOrderDetailId(id_o)); // ID chi tiết đơn hàng
            orderDetailStmt.setString(3, od.getId_p()); // ID của sản phẩm
            orderDetailStmt.setInt(4, od.getQuantity()); // Số lượng của sản phẩm
            orderDetailStmt.setInt(5, od.getDiscount()); // Giảm giá của sản

            orderDetailStmt.addBatch();
        }
        orderDetailStmt.executeBatch();

        } catch (SQLException ex) {
            Logger.getLogger(cndb.class.getName()).log(Level.SEVERE, null, ex);
            status = -1;
        }            
        return status;
    }

    public int xoa_don_hang(String orderID) {
        int status = 0;


        try {
            String query = "DELETE FROM orders WHERE id_o = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            System.out.println(orderID);
            statement.setString(1, orderID);

            System.out.println("1");
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Xoá đơn hàng thành công!");
            } else {
                System.out.println("Không tìm thấy đơn hàng để xoá!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            status =-1;
        }
        return status;
    }
        
    public int getEarliestYear(){
        int eYear = 0;
        try {
            String sql = "SELECT MIN(strftime('%Y', DATE_O)) AS EARLIEST_YEAR FROM ORDERS";

            pre = conn.prepareStatement(sql);
            ResultSet rlt = pre.executeQuery();
            eYear = rlt.getInt("EARLIEST_YEAR");
        } catch (SQLException ex) {
            Logger.getLogger(cndb.class.getName()).log(Level.SEVERE, null, ex);
        }
        return eYear;
    }
        
    public List<ProductEx> salesReports(String month, String year){
                    List<ProductEx> proExs = new ArrayList<>();
        try {
            String sql = "SELECT od.ID_P, " +
            "       p.NAME_P, " +
            "       SUM(od.QUAL) AS total_QUAL, " +
            "       p.PRICE_S, " +
            "       p.PRICE_I, " +
            "       od.DISCOUNT " +
            "FROM ORDERS o " +
            "JOIN ORDERS_DETAIL od ON o.ID_O = od.ID_O " +
            "JOIN PRODUCTS p ON od.ID_P = p.ID_P " +
            "WHERE strftime('%m', o.DATE_O) = ? AND strftime('%Y', o.DATE_O) = ? " +
            "GROUP BY od.ID_P, od.DISCOUNT;";
            pre = conn.prepareStatement(sql); //pre một lệnh truy vấn sql select chuẩn bị thực thi trên database
            pre.setString(1, month);
            pre.setString(2, year);
            ResultSet rlt = pre.executeQuery();//thực hiện việc truy vấn và trả kết quả vào 1 đối tượng ResultSet tên rlt
            while (rlt.next()) {
                String proId = rlt.getString("ID_P");
                String proName = rlt.getString("NAME_P");
//                    String pro = proId + proName;
                int price_s = rlt.getInt("PRICE_S");
                int price_i = rlt.getInt("PRICE_I");
                int qual = rlt.getInt("total_QUAL");
                int discount = rlt.getInt("DISCOUNT");
                ProductEx proEx = new ProductEx(proId, proName , qual, price_i, price_s, discount);
                proExs.add(proEx);
            }            
        } catch (SQLException ex) {
            Logger.getLogger(cndb.class.getName()).log(Level.SEVERE, null, ex);
        }
        return proExs;
    }
        
        
    public double getProfit(String month, String year){
//        System.out.println(month +"?"+year);
        double totalProfit = 0;
        List<ProductEx> proExs = salesReports(month, year);
//        System.out.println(proExs.size() + " size");
        for(ProductEx proEx: proExs){
//            System.out.println("Sản phẩm : "+ proEx.getProfit());
            totalProfit += proEx.getProfit();
        }
        return totalProfit;
    }        

    public DefaultCategoryDataset salesTotalProfitdts(String year, boolean yearMode, String qarter){
        DefaultCategoryDataset profitDataset = new DefaultCategoryDataset();
        if(yearMode){
            for(int i = 1; i<=12; i++){ 
                String month = String.format("%02d",i);
//                System.out.println("Tháng: "+ month);
                double totalProfit = getProfit(month, year);
                
                profitDataset.setValue(totalProfit, "Lợi Nhuận", "Tháng " +i);
            }
        }else{
            switch(qarter){
                case "Quý 1":
                    for(int i = 1; i<=3; i++){ 
                        String month = String.format("%02d",i);
                        double totalProfit = getProfit(month, year);
                        profitDataset.setValue(totalProfit, "Lợi Nhuận", "Tháng " +i);
                    } 
                    break;
                case "Quý 2":
                    for(int i = 4; i<=6; i++){ 
                        String month = String.format("%02d",i);
                        double totalProfit = getProfit(month, year);
                        profitDataset.setValue(totalProfit, "Lợi Nhuận", "Tháng " +i);
                    } 
                    break;
                case "Quý 3":
                    for(int i = 7; i<=9; i++){ 
                        String month = String.format("%02d",i);
                        double totalProfit = getProfit(month, year);
                        profitDataset.setValue(totalProfit, "Lợi Nhuận", "Tháng " +i);
                    }
                    break;
                case "Quý 4":
                    for(int i = 10; i<=12; i++){ 
                        String month = String.format("%02d",i);
                        double totalProfit = getProfit(month, year);
                        profitDataset.setValue(totalProfit, "Lợi Nhuận", "Tháng " +i);
                    } 
                    break;
            }
        }
        return profitDataset;
    }        
//        
    public DefaultPieDataset salesReportsdts( String month, String year, boolean profitMode, boolean checkProfit){
        System.out.println(month+"thu"+ year);  
        List<ProductEx> proExs = salesReports(month, year);
        System.out.println(proExs.size() + "size proEx");  
        DefaultPieDataset profitDataset = new DefaultPieDataset();
        DefaultPieDataset qualDataset = new DefaultPieDataset();     
        DefaultPieDataset lossDataset = new DefaultPieDataset();     

            int totalQual = 0;
            int totalProfit = 0;
            int totalLoss =0;
            for (ProductEx pro : proExs) {
                totalQual += pro.getQual();
                if(pro.checkProfit()){
                    System.out.println(pro.getProfit() + " wth");  
                    totalProfit += pro.getProfit();
                } else {
                    totalLoss -= pro.getProfit();
                    System.out.println(pro.getProfit() + " wtf");  
                }
            }
            System.out.println(totalLoss + "hihi");  
            System.out.println(totalProfit + "haha");
            int otherProfit = 0;
            int otherLoss = 0;
            int otherQual =0;
            
            for (ProductEx pro : proExs) {
                String productName = pro.getName();
                int productQual = pro.getQual();
                double qualPercentage = ((double) productQual / totalQual) * 100;  
                if(qualPercentage <1){
                    otherQual += productQual;
                } else{
                        qualDataset.setValue(productName, productQual);
                        System.out.println(productName+"/"+productQual);                    
                }
                
                if(pro.checkProfit()){
                    double productProfit = pro.getProfit();
                    System.out.println("profit " +productProfit);
                    double profitPercentage = ((double) productProfit / totalProfit) * 100;                
                    if (profitPercentage < 1) {
                        otherProfit += productProfit;
                    } else {
                        profitDataset.setValue(productName, productProfit);
                        System.out.println(productName+"/"+profitPercentage);
                    }
                } else {
                    double productLoss = - pro.getProfit();
//                    System.out.println("loss " +productLoss + productName);
                    double lossPercentage = ((double) productLoss / totalLoss)*100;
                    if(lossPercentage <1){
                        otherLoss += productLoss;
                    } else{
                        
                        lossDataset.setValue(productName, productLoss);
                    }
                }
            }
            
            qualDataset.setValue("Other", otherQual);
            profitDataset.setValue("Other", otherProfit);
            lossDataset.setValue("Other", otherLoss);
            
            System.out.println(otherLoss +" other loss");

//                 for () {
//                    System.out.println(entry.getKey()+"/"+entry.getValue());
//                    
//                }               
            if(profitMode){
                if(checkProfit){
                    return profitDataset;
                }else{
                    return lossDataset;
                }
            }else{
                return qualDataset;
            }

    }
    
    public List<Order> OrdersFilterByDS(String month, String year, int del_stt){
        List<Order> allOrders = new ArrayList<>();
        
         try{
            String sql = "SELECT o.ID_O, o.NAME_C, o.DATE_O, o.DATE_D, o.ADDR, o.PAY_TYPE, o.DEL_STT, od.ID_P, od.ID_P, od.QUAL, p.PRICE_S, od.DISCOUNT "
                    + "FROM ORDERS o "
                    + "JOIN ORDERS_DETAIL od ON o.ID_O = od.ID_O "
                    + "JOIN PRODUCTS p ON od.ID_P = p.ID_P "//truy vấn sql 
                    + "WHERE o.DEL_STT = ? and strftime('%m', o.DATE_O) = ? AND strftime('%Y', o.DATE_O) = ?";//truy vấn sql 
            pre = conn.prepareStatement(sql); 
            pre.setInt(1, del_stt);
            pre.setString(2, month);
            pre.setString(3, year);
            ResultSet rlt = pre.executeQuery();//thực hiện việc truy vấn và trả kết quả vào 1 đối tượng ResultSet tên rlt
            
            Map<String, Order> orderMap = new HashMap<>();
            while (rlt.next()) {
                String orderId = rlt.getString("ID_O");
                String date = rlt.getString("DATE_O");
                //System.out.print(""+date);
                //Nếu dòng tiếp theo có orderId không có trong map chưa nếu chưa thì thêm ..
                if (!orderMap.containsKey(orderId)) {
                    orderMap.put(orderId, new Order(orderId, rlt.getString("NAME_C"), rlt.getString("DATE_O"),  rlt.getString("DATE_D"),
                            rlt.getString("ADDR"), rlt.getString("PAY_TYPE"), rlt.getInt("DEL_STT"),
                            new ArrayList<>(), 0));
                }
                Order order = orderMap.get(orderId);
                String productId = rlt.getString("ID_P");
                int quantity = rlt.getInt("QUAL");
                int discount= rlt.getInt("DISCOUNT"); 
                order.setTotal_amount(order.getTotal_amount() + quantity * rlt.getInt("DISCOUNT"));
                List<Order_detail> orderDetail = order.getOrder_detail();
                orderDetail.add(new Order_detail(productId,quantity, discount));
            }
            allOrders.addAll(orderMap.values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.print(allOrders.size());
        return allOrders;
    }

    public List<Order> allOrdersFilter(String sqlEx, String key, boolean btn){
        List<Order> allOrders = new ArrayList<>();

         try{
            String sql = "SELECT o.ID_O, o.NAME_C, o.DATE_O, o.DATE_D, o.ADDR, o.PAY_TYPE, o.DEL_STT, od.ID_P, od.ID_P, od.QUAL, p.PRICE_S, od.DISCOUNT "
                    + "FROM ORDERS o "
                    + "JOIN ORDERS_DETAIL od ON o.ID_O = od.ID_O "
                    + "JOIN PRODUCTS p ON od.ID_P = p.ID_P"
                    + sqlEx;//truy vấn sql 
            System.out.print(sql);
            pre = conn.prepareStatement(sql); 
            if(btn){
                pre.setString(1, key);
            } else{
                pre.setString(1, "%"+key+"%");
            }
            ResultSet rlt = pre.executeQuery();//thực hiện việc truy vấn và trả kết quả vào 1 đối tượng ResultSet tên rlt

            Map<String, Order> orderMap = new HashMap<>();
            while (rlt.next()) {
                String orderId = rlt.getString("ID_O");
//                String date = rlt.getString("DATE_O");
                //System.out.print(""+date);
                //Nếu dòng tiếp theo có orderId không có trong map chưa nếu chưa thì thêm ..
                if (!orderMap.containsKey(orderId)) {
                    orderMap.put(orderId, new Order(orderId, rlt.getString("NAME_C"), rlt.getString("DATE_O"), rlt.getString("DATE_D"),
                            rlt.getString("ADDR"), rlt.getString("PAY_TYPE"), rlt.getInt("DEL_STT"),
                            new ArrayList<>(), 0));
                }
                Order order = orderMap.get(orderId);
                String productId = rlt.getString("ID_P");
                int quantity = rlt.getInt("QUAL");
                int discount = rlt.getInt("DISCOUNT");
                order.getOrder_detail().add(new Order_detail(productId, quantity, discount));
                order.setTotal_amount(order.getTotal_amount() + quantity * rlt.getInt("PRICE_S")*discount);
            }
            allOrders.addAll(orderMap.values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.print(allOrders.size());
        return allOrders;
    }
    
    public String creaSqlEx(boolean btn, int option) {
        ///btn là khi người dùng nhấn nút tìm kiếm = key phải y hệt thông tin trong đơn hàng = tìm kiếm chặt
//// option là tìm kiếm theo tên 0 tên người mua, 1 id đơn hàng, 2 id sản phẩm hoặc tên sản phẩm, 3 trạng thái giao hàng, 4. Thanh toán
        String sqlA = "";
        String sqlB = "";
        String sqlEx = " WHERE ";

        if (btn) {
            sqlB = " COLLATE NOCASE";
        }

        switch (option) {
            case 0:
                sqlA = btn ? "o.NAME_C = ?" + sqlB : "o.NAME_C LIKE ? ";
                break;
            case 1:
                sqlA = btn ? "o.ID_O = ?" + sqlB : "o.ID_O LIKE ? ";
                break;
            case 2:
                sqlA = btn ? "p.ID_P = ? OR p.NAME_P = ? " + sqlB : "p.ID_P LIKE ? OR p.NAME_P LIKE ? ";
                break;
            case 3:
                sqlA = btn ? "o.DEL_STT = ? " : "o.DEL_STT = ? ";
                break;
            case 4:
                sqlA = btn ? "o.PAY_TYPE = ? " : "o.PAY_TYPE = ? ";
                break;
            default:
                return "";
        }

        sqlEx += sqlA;
        return sqlEx;
    }

    public int getStock(String id_p){
        int stock = 0;
        try {
            String sql = "SELECT STOCK FROM PRODUCTS WHERE ID_P =?";
            pre = conn.prepareStatement(sql);
            pre.setString(1, id_p);
            ResultSet rlt = pre.executeQuery();
            if (rlt.next()) { // Di chuyển con trỏ đến dòng đầu tiên
                stock = rlt.getInt("STOCK"); // Lấy giá trị từ cột STOCK
            }
        } catch (SQLException ex) {
            Logger.getLogger(cndb.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stock;
    }

 
    //phương thức thêm sản phẩm vào database
    public int productInsert(String id_p, String name_p, int stock , String desc, byte[] image,int price_i, int price_s, String depot, String date){
        int status = 0;
        try {
            String sql = "Insert Into products(ID_P, NAME_P, STOCK, DESC, IMAGE, PRICE_I, PRICE_S, depot, date_p) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pre = conn.prepareStatement(sql);
            pre.setString(1, id_p);
            pre.setString(2, name_p);
            pre.setInt(3, stock);
            pre.setString(4, desc);
            pre.setBytes(5, image);
            pre.setInt(6, price_i);
            pre.setInt(7, price_s);
            pre.setString(8, depot);

            pre.setString(9, date);
            status = pre.executeUpdate();
        } catch (Exception e) {
            System.err.println("customerInsert Error : " + e);
            JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage(), "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
            status = -1;
        }
        return status;
    }
 
    //truy vấn tất cả các sản phẩm từ cơ sở dữ liệu và trả về một danh sách các đối tượng Product 
    public List<Product> allProducts() {
        List<Product> proList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM PRODUCTS";
            try (PreparedStatement pre = conn.prepareStatement(sql);
                 ResultSet rlt = pre.executeQuery()) {
                while (rlt.next()) {
                    String id = rlt.getString("ID_P");
                    String name = rlt.getString("NAME_P");
                    int instock = rlt.getInt("STOCK");
                    String desc = rlt.getString("DESC");
                    byte[] image = rlt.getBytes("image"); // Đảm bảo cột image đúng kiểu dữ liệu
                    int price_i = rlt.getInt("PRICE_I");
                    int price_s = rlt.getInt("PRICE_S");
                    String date = rlt.getString("date_p");
                    String depot = rlt.getString("depot");
                    Product pro = new Product(id, name, instock, desc, image, price_i, price_s, date, depot);
                    proList.add(pro);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            // Xử lý ngoại lệ hoặc thông báo cho người dùng về lỗi
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        return proList;
    }

    //Phương thức xóa sản phẩm theo hình ảnh 
    public void xoa_san_pham(java.awt.event.ActionEvent e, byte[] image){
        // Kết nối cơ sở dữ liệu
        try {
            // Tạo truy vấn SQL để xóa sản phẩm
            String sql = "DELETE FROM PRODUCTS WHERE IMAGE = ?";
            pre = conn.prepareStatement(sql);
            pre.setBytes(1, image);
            System.out.println("1");
            // Thực hiện truy vấn xóa
            int rowsDeleted = pre.executeUpdate();

            // Kiểm tra xem sản phẩm đã được xóa thành công hay không
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Sản phẩm đã được xóa thành công!");
            } else {
                JOptionPane.showMessageDialog(null, "Không thể xóa sản phẩm!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi xóa sản phẩm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }    
    }
    
    //Phương thức bổ trợ hiện chi tiết sản phẩm 
    public String id_p;
    public String name_p;
    public int stock;
    public String desc;
    public byte[] image;
    public int price_i;
    public int price_s;
    public String date_p;
    public String depot;    
    
    public void chi_tiet_san_pham(int index, List<Product> allPro) {         
        try {
            // Lấy ID của sản phẩm cần xóa từ danh sách sản phẩm
            String id_s = allPro.get(index).getId_p();
            
            // Thực hiện truy vấn SQL để so sánh với image đầu vào 
            String sql = "SELECT * FROM PRODUCTS WHERE ID_P = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            // Đặt tham số cho câu truy vấn
            statement.setString(1, id_s);

            // Thực thi truy vấn và lấy kết quả
            ResultSet ab = statement.executeQuery();
            if (ab.next()) {
                // Lấy thông tin từ bản ghi kết quả
                this.id_p = ab.getString("ID_P");
                this.name_p = ab.getString("NAME_P");
                this.stock = ab.getInt("STOCK");
                this.desc = ab.getString("DESC");
                this.image = ab.getBytes("IMAGE");
                this.price_i = ab.getInt("PRICE_I");
                this.price_s = ab.getInt("PRICE_S");
                String date_A= ab.getString("DATE_P");
                this.date_p =Handler.formatDate(date_A);
                this.depot = ab.getString("DEPOT");
                System.out.println("truy vấn được");
                // Sử dụng các giá trị đã lấy ra ở đây
            } else {
                System.out.println("Không tìm thấy sản phẩm với index đã cho");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi truy vấn sản phẩm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //PT update sản phẩm theo ID 
    public int productUpdate(String id_p, String name_p, int stock, String desc, byte[] image, int price_i, int price_s, String depot, String date) {
//        cndb a=cndb.getInstance();
//        a.open();
        int status = 0;
        try {
            String sql = "UPDATE products SET NAME_P = ?, STOCK = ?, DESC = ?, IMAGE = ?, PRICE_I = ?, PRICE_S = ?, depot = ?, date_p = ? WHERE ID_P = ?";
            pre = conn.prepareStatement(sql);
            pre.setString(1, name_p);
            pre.setInt(2, stock);
            pre.setString(3, desc);
            pre.setBytes(4, image);
            pre.setInt(5, price_i);
            pre.setInt(6, price_s);
            pre.setString(7, depot);
            pre.setString(8, date);
            pre.setString(9, id_p);

            status = pre.executeUpdate();
        } catch (Exception e) {
            System.err.println("productUpdate Error: " + e);
            JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage(), "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
            status = -1;
        }
        //a.close();
        return status;

    }    

    //Phương thức xóa sản phẩm theo index 
    public void xoa_san_pham_theo_thu_tu_propanels(int index, List<Product> allPro){
        try {
            // Lấy ID của sản phẩm cần xóa từ danh sách sản phẩm
            String productIdToDelete = allPro.get(index).getId_p();

            // Tạo câu truy vấn SQL để xóa sản phẩm với ID_P tương ứng
            String sql = "DELETE FROM PRODUCTS WHERE ID_P = ?";

            // Chuẩn bị câu truy vấn
            pre = conn.prepareStatement(sql);

            // Thiết lập giá trị cho tham số trong câu truy vấn SQL
            pre.setString(1, productIdToDelete);

            // Thực hiện truy vấn xóa
            int rowsDeleted = pre.executeUpdate();

            // Kiểm tra xem sản phẩm đã được xóa thành công hay không
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Sản phẩm đã được xóa thành công!");
            } else {
                JOptionPane.showMessageDialog(null, "Không thể xóa sản phẩm!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi xóa sản phẩm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }    
    }
    
    // Hàm hiển thị hình ảnh từ tệp lên JLabel
    public static ImageIcon displayImageOnLabel(File file, JLabel label) {
        try {
            // Tạo một ImageIcon từ tệp đã chọn
            ImageIcon icon = new ImageIcon(file.getPath());
            // Lấy kích thước hiện tại của JLabel
            int width = label.getWidth();
            int height = label.getHeight();
            // Chỉnh kích thước của hình ảnh để phù hợp với JLabel
            Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            // Tạo một ImageIcon mới từ hình ảnh đã chỉnh kích thước
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            // Đặt hình ảnh mới lên JLabel
            label.setIcon(scaledIcon);
            return icon;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }  
    
    // Phương thức hình ảnh khi dùng giữa clipboad , CSDL , Netbean 
    public static byte[] getImageBytes(Image image) throws IOException {
        BufferedImage bufferedImage = convertToBufferedImage(image);
        return getByteArray(bufferedImage);
    }
    public static BufferedImage convertToBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        bufferedImage.getGraphics().drawImage(image, 0, 0, null);
        return bufferedImage;
    }
    public static byte[] getByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        javax.imageio.ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
    public static byte[] getByteArray(Image image) throws IOException {
        BufferedImage bufferedImage = convertToBufferedImage(image);
        return getByteArray(bufferedImage);
    }
    public static BufferedImage convertToolkitImageToBufferedImage(Image image) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        bufferedImage.getGraphics().drawImage(image, 0, 0, null);
        return bufferedImage;
    }
    
    //Kiểm tra một JPanel a có chứa chuõi a không , có thì trả về số 1 , không thì trả về số 0 
    public static JLabel label;
    public static String labelText;
    public static int tim_kiem_san_pham(JPanel panel, String abc) {
        List<String> text = new ArrayList<>();
        Component[] components = panel.getComponents();
        for (Component comp : components) {
            if (comp instanceof JLabel) {
                label = (JLabel) comp;
                labelText = label.getText();
                text.add(labelText);
            }        
            for(String a: text){
                if(a==null){
                    a="!!!";
                }
                if(a.contains(abc)){          
                    return 1;
                }                    
            }
        }       
        return 0;
    }
    
    
    //PT update tồn kho sản phẩm theo ID 
    public int stockUpdate(String id_p, int qual){
        int status = 0;
        try {
            String sql = "UPDATE PRODUCTS SET STOCK = STOCK - ? WHERE ID_P =?";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, qual);
            pre.setString(2, id_p);
            status = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(cndb.class.getName()).log(Level.SEVERE, null, ex);
            status = -1;
        }
        return status;
    }
    
    
}



