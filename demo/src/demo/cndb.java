/*
 * To change this license header, choose License Headerlt in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jfree.data.general.DefaultPieDataset;
import structure.Order;
import structure.Product;
import structure.ProductEx;

/**
 *
 * @author DELL
 */
public class cndb {
//        String url = "jdbc:mariadb://localhost:3306/QLBH";
//        String user = "root";
//        String password = "Hyun@IOIGOT7";    
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


//        try {
//            // Kết nối đến cơ sở dữ liệu
//            Connection conn = DriverManager.getConnection(url, user, password);
//            System.out.println("Kết nối đến MariaDB thành công!");
//
//        } catch (SQLException e) {
//            System.err.println("Lỗi khi kết nối đến MariaDB: " + e.getMessage());
//        }
    }
    
//singleton pattern nhằm chỉ tạo ra 1 đối tượng cndb duy nhất trong quá trình chạy
    private static cndb instance;    
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
            String sql = "SELECT o.ID_O, o.NAME_C, o.DATE_O, o.DATE_D, o.ADDR, o.PAY_TYPE, o.DEL_STT, od.ID_P, od.ID_P, od.QUAL, p.PRICE_S "
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
                            new HashMap<>(), 0));
                }
                Order order = orderMap.get(orderId);
                String productId = rlt.getString("ID_P");
                int quantity = rlt.getInt("QUAL");
                order.getOrder_detail().put(productId, quantity);
                order.setTotal_amount(order.getTotal_amount() + quantity * rlt.getInt("PRICE_S"));
            }
            allOrders.addAll(orderMap.values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.print(allOrders.size());
        return allOrders;
    }
    
    
//phương thức thêm đơn hàng vào database
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


        public int orderInsert(String id_o, String name_c, String date_o, String date_d, String addr, String pay_type, int del_stt, Map<String, Integer> order_detail){
            
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
        public int orderEdit(String id_o, String name_c, String date_o, String date_d, String addr, String pay_type, int del_stt, Map<String, Integer> order_detail){
            
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
        
        public int insertOD(String id_o, Map<String, Integer> order_detail) {
            int status = 0;       
            try {
            String orderDetailSql = "INSERT INTO orders_detail (ID_O, ID_O_D, ID_P, QUAL) VALUES (?, ?, ?, ?)";
            PreparedStatement orderDetailStmt = conn.prepareStatement(orderDetailSql);
            for (Map.Entry<String, Integer> entry : order_detail.entrySet()) {
                orderDetailStmt.setString(1, id_o);
                orderDetailStmt.setString(2, generateOrderDetailId(id_o)); //tạo id_o_p
                orderDetailStmt.setString(3, entry.getKey()); // key là String = id_p, 
                orderDetailStmt.setInt(4, entry.getValue());// value là Integer = qual
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
        
        public List<ProductEx> salesReports(String month, String year){
                        List<ProductEx> proExs = new ArrayList<>();
            try {
                String sql = "SELECT od.ID_P, \n" +
                        "       p.NAME_P, \n" +
                        "       SUM(od.QUAL) AS total_QUAL, \n" +
                        "       p.PRICE_S, \n" +
                        "       p.PRICE_I, \n" +
                        "       o.DATE_D\n" +
                        "FROM ORDERS o \n" +
                        "JOIN ORDERS_DETAIL od ON o.ID_O = od.ID_O \n" +
                        "JOIN PRODUCTS p ON od.ID_P = p.ID_P \n" +
                        "WHERE strftime('%m', o.DATE_O) = ? AND strftime('%Y', o.DATE_O) = ?\n" +
                        "GROUP BY od.ID_P;";

                pre = conn.prepareStatement(sql); //pre một lệnh truy vấn sql select chuẩn bị thực thi trên database
                pre.setString(1, month);
                pre.setString(2, year);
                ResultSet rlt = pre.executeQuery();//thực hiện việc truy vấn và trả kết quả vào 1 đối tượng ResultSet tên rlt
                while (rlt.next()) {
                    String proId = rlt.getString("ID_P");
                    String proName = rlt.getString("NAME_P");
                    String pro = proId + proName;
                    int price_s = rlt.getInt("PRICE_S");
                    int price_i = rlt.getInt("PRICE_I");
                    int qual = rlt.getInt("total_QUAL");
                    ProductEx proEx = new ProductEx(pro, qual, price_i, price_s);
                    proExs.add(proEx);
                }            
            } catch (SQLException ex) {
                Logger.getLogger(cndb.class.getName()).log(Level.SEVERE, null, ex);
            }
            return proExs;
        }
        
//        
    public DefaultPieDataset salesReportsdts( String month, String year, boolean profitMode, boolean checkProfit){

        List<ProductEx> proExs = salesReports(month, year);
        DefaultPieDataset profitDataset = new DefaultPieDataset();
        DefaultPieDataset qualDataset = new DefaultPieDataset();     
        DefaultPieDataset lossDataset = new DefaultPieDataset();     
//        Map<String,Integer> salesProfitReport = new HashMap<>();
//        Map<String,Integer> salesQualReport = new HashMap<>();
//
//            for(ProductEx pro : proExs){
//                String name = pro.getName();
//                int profit = pro.getProfit();
//                int qual = pro.getQual();
//                salesProfitReport.put(name, profit);
//                salesQualReport.put(name, qual);
//            }

            int totalQual = 0;
            int totalProfit = 0;
            int totalLoss =0;
            for (ProductEx pro : proExs) {
                totalQual += pro.getQual();
                if(pro.checkProfit()){
                    totalProfit += pro.getProfit();
                } else {
                    totalLoss -= pro.getProfit();
                }
            }
            
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
//                        System.out.println(productName+"/"+profitQual);                    
                }
                
                if(pro.checkProfit()){
                    int productProfit = pro.getProfit();
                    double profitPercentage = ((double) productProfit / totalProfit) * 100;                
                    if (profitPercentage < 1) {
                        otherProfit += productProfit;
                    } else {
                        profitDataset.setValue(productName, productProfit);
//                        System.out.println(productName+"/"+profitPercentage);
                    }
                } else {
                    int productLoss = - pro.getProfit();
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


//                 for (Map.Entry<String, Integer> entry : salesQualReport.entrySet()) {
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

    public List<Order> allOrdersFilter(String sqlEx, String key, boolean btn){
        List<Order> allOrders = new ArrayList<>();

         try{
            String sql = "SELECT o.ID_O, o.NAME_C, o.DATE_O, o.DATE_D, o.ADDR, o.PAY_TYPE, o.DEL_STT, od.ID_P, od.ID_P, od.QUAL, p.PRICE_S "
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
                String date = rlt.getString("DATE_O");
                //System.out.print(""+date);
                //Nếu dòng tiếp theo có orderId không có trong map chưa nếu chưa thì thêm ..
                if (!orderMap.containsKey(orderId)) {
                    orderMap.put(orderId, new Order(orderId, rlt.getString("NAME_C"), rlt.getString("DATE_O"), rlt.getString("DATE_P"),
                            rlt.getString("ADDR"), rlt.getString("PAY_TYPE"), rlt.getInt("DEL_STT"),
                            new HashMap<>(), 0));
                }
                Order order = orderMap.get(orderId);
                String productId = rlt.getString("ID_P");
                int quantity = rlt.getInt("QUAL");
                order.getOrder_detail().put(productId, quantity);
                order.setTotal_amount(order.getTotal_amount() + quantity * rlt.getInt("PRICE_S"));
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
                sqlA = btn ? "p.ID_P = ? OR p_NAME_P = ? " + sqlB : "p.ID_P LIKE ? OR p_NAME_P LIKE ? ";
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
    
//    //PT xóa sản phẩm theo id 
//    public void xoa_san_pham_theo_ID(String id_p) {
//        // Kết nối cơ sở dữ liệu
//        PreparedStatement statement = null;
//
//        try {
//            // Tạo truy vấn SQL để xóa sản phẩm dựa trên ID_P
//            String sql = "DELETE FROM PRODUCTS WHERE ID_P = ?";
//            statement = conn.prepareStatement(sql);
//            statement.setString(1, id_p);
//            System.out.println("1");
//            // Thực hiện truy vấn xóa
//            int rowsDeleted = statement.executeUpdate();
//
//            // Kiểm tra xem sản phẩm đã được xóa thành công hay không
//            if (rowsDeleted > 0) {
//                JOptionPane.showMessageDialog(null, "Sản phẩm đã được xóa thành công!");
//            } else {
//                JOptionPane.showMessageDialog(null, "Không thể xóa sản phẩm!");
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Lỗi khi xóa sản phẩm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }    
//    }
    
    
    
    //Phương thức bổ trợ hiện chi tiết sản phẩm 
    private String id_p;
    private String name_p;
    private int stock;
    private String desc;
    private byte[] image;
    private int price_i;
    private int price_s;
    private String date_p;
    private String depot;

    public void chi_tiet_san_pham(byte[] inputImage) {
        try {
            // Thực hiện truy vấn SQL để so sánh với image đầu vào 
            String sql = "SELECT * FROM PRODUCTS WHERE IMAGE = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            // Đặt tham số cho câu truy vấn
            statement.setBytes(1, inputImage);

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
                this.date_p = ab.getString("DATE_P");
                this.depot = ab.getString("DEPOT");
                System.out.println("truy vấn được");
                // Sử dụng các giá trị đã lấy ra ở đây
            } else {
                System.out.println("Không tìm thấy sản phẩm với IMAGE đã cho");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi truy vấn sản phẩm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String getId_p() {
        return id_p;
    }

    public String getName_p() {
        return name_p;
    }

    public int getStock() {
        return stock;
    }

    public String getDesc() {
        return desc;
    }

    public byte[] getImage() {
        return image;
    }

    public int getPrice_i() {
        return price_i;
    }

    public int getPrice_s() {
        return price_s;
    }

    public String getDate_p() {
        return date_p;
    }

    public String getDepot() {
        return depot;
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

//    // Phương thức đóng kết nối CSDL
//    public void disconnect() {
//        try {
//            if (conn != null && !conn.isClosed()) {
//                conn.close();
//                System.out.println("Đã đóng kết nối đến CSDL!");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }         
//    }
    //Phương thức chỉnh sửa sản phẩm
    
}



