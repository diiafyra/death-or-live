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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import structure.Order;
import structure.Product;

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
            String sql = "SELECT o.ID_O, c.NAME_C, o.DATE_O, o.ADDR, o.PAY_TYPE, o.DEL_STT, od.ID_P, p.ID_P, od.QUAL, p.PRICE_S "
                    + "FROM ORDERS o "
                    + "JOIN CUSTOMERS c ON o.ID_C = c.ID_C "
                    + "JOIN ORDERS_DETAIL od ON o.ID_O = od.ID_O "
                    + "JOIN PRODUCTS p ON od.ID_P = p.ID_P";//truy vấn sql 
            pre = conn.prepareStatement(sql); //pre một lệnh truy vấn sql select chuẩn bị thực thi trên database
            ResultSet rlt = pre.executeQuery();//thực hiện việc truy vấn và trả kết quả vào 1 đối tượng ResultSet tên rlt
            
            Map<String, Order> orderMap = new HashMap<>();
            while (rlt.next()) {
                String orderId = rlt.getString("ID_O");
                Date date = rlt.getDate("DATE_O");
                //System.out.print(""+date);
                //Nếu dòng tiếp theo có orderId không có trong map chưa nếu chưa thì thêm ..
                if (!orderMap.containsKey(orderId)) {
                    orderMap.put(orderId, new Order(orderId, rlt.getString("NAME_C"), rlt.getDate("DATE_O"),
                            rlt.getString("ADDR"), rlt.getString("PAY_TYPE"), rlt.getInt("DEL_STT"),
                            new HashMap<>(), 0));
                }
                Order order = orderMap.get(orderId);
                String productName = rlt.getString("ID_P");
                int quantity = rlt.getInt("QUAL");
                order.getOrder_detail().put(productName, quantity);
                order.setTotal_amount(order.getTotal_amount() + quantity * rlt.getInt("PRICE_S"));
            }
            allOrders.addAll(orderMap.values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allOrders;
    }
    
    
    //phương thức thêm đơn hàng vào database
    private Map<String, Integer> orderDetailIdCounters = new HashMap<>();

    private String generateOrderDetailId(String id_o) {
        int counter = orderDetailIdCounters.getOrDefault(id_o, 0) + 1;
        orderDetailIdCounters.put(id_o, counter);
        String paddedCounter = String.format("%03d", counter % 1000); // Đảm bảo số có 3 chữ số bằng cách thêm số 0 vào trước nếu cần
        return id_o + paddedCounter;
    }
        public int orderInsert(String id_o, String name_c, Date date_o , String addr, String pay_type, String del_stt, Map<String, Integer> order_detail){
            
        int status = 0;
        try {
            String sql = "Insert Into orders(ID_O, NAME_C, DATE_O, ADDR, PAY_TYPE, DEL_STT) values(?, ?, ?, ?, ?, ?)";
            pre = conn.prepareStatement(sql);
            pre.setString(1, id_o);
            pre.setString(2, name_c);
            pre.setDate(3, date_o);
            pre.setString(4, addr);
            pre.setString(5, pay_type);
            pre.setString(6, del_stt);
            
            status = pre.executeUpdate();
            
        String orderDetailSql = "INSERT INTO order_detail (ID_O, ID_O_D, ID_P, QUAL) VALUES (?, ?, ?, ?)";
        PreparedStatement orderDetailStmt = conn.prepareStatement(orderDetailSql);
        for (Map.Entry<String, Integer> entry : order_detail.entrySet()) {
            orderDetailStmt.setString(1, id_o);
            orderDetailStmt.setString(2, generateOrderDetailId(id_o)); //tạo id_o_p
            orderDetailStmt.setString(3, entry.getKey()); // key là String = id_p, 
            orderDetailStmt.setInt(4, entry.getValue());// value là Integer = qual
            orderDetailStmt.addBatch();
        }
        orderDetailStmt.executeBatch();
        
        } catch (Exception e) {
            System.err.println("customerInsert Error : " + e);
            JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage(), "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
            status = -1;
        }

        return status;
    }
    
    //phương thức thêm sản phẩm vào database
        public int productInsert(String id_p, String name_p, int stock , String desc, byte[] image,int price_i, int price_s, String depot, Date date){
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
             
            pre.setDate(9, date);
            status = pre.executeUpdate();
        } catch (Exception e) {
            System.err.println("customerInsert Error : " + e);
            JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage(), "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
            status = -1;
        }
        return status;
        }
 
  
    public List<Product> allProducts() {
        List<Product> proList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM PRODUCTS";
            pre = conn.prepareStatement(sql);
            ResultSet rlt = pre.executeQuery();
            while (rlt.next()) {
                String id = rlt.getString("ID_P");
                String name = rlt.getString("NAME_P");
                int instock = rlt.getInt("STOCK");
                String desc = rlt.getString("DESC");
                byte[] image = rlt.getBytes("image");//getByte và getBytes
                int price_i = rlt.getInt("PRICE_I");
                int price_s = rlt.getInt("PRICE_S");
                Date date = rlt.getDate("date_p");
                String depot = rlt.getString("depot");
                Product pro = new Product(id, name, instock, desc, image, price_i, price_s, date, depot);
                proList.add(pro);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
        return proList;
    }
    
    //Phương thức xóa sản phẩm 
    
    //Phương thức xóa đơn hàng
    
    //Phương thức chỉnh sửa sản phẩm
    
    //Phương thức chỉnh sửa đơn hàng
}
