/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DELL
 */
public class cndb {
    String driver = "org.sqlite.JDBC";
    String url = "jdbc:sqlite:QLBH_DOL.db";
    Connection conn = null;
    private static cndb instance;
    private cndb (){
        
        try {
            Class.forName(driver);//tải jdbc driver vào bộ nhớ
            conn = DriverManager.getConnection(url); //đăng kí kết nối vs database qua địa chỉ lưu vào biến tham chiếu conn
            System.out.println("KẾT NỐI THÀNH CÔNG");
        } catch (Exception ex) {
            Logger.getLogger(cndb.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    public static cndb getInstance(){
        if(instance == null){
            instance = new cndb();
        }
        return instance;
    }//singleton pattern nhằm chỉ tạo ra 1 đối tượng cndb duy nhất trong quá trình chạy

    private PreparedStatement pre;
    
    //phương thức hiển thị tất cả sản phẩm hiện có trong database vào bảng đơn hàng trong ứng dụng
    public DefaultTableModel allOrders(){
        DefaultTableModel dTM = new DefaultTableModel();
        dTM.addColumn("Mã Đơn Hàng");
        dTM.addColumn("Tên Khách Hàng");
        dTM.addColumn("Thành Tiền");
        dTM.addColumn("Trạng thái giao hàng");
        
        try{
            String sql =
                    "SELECT o.ID_ORDER, \n" +
            "       c.NAME_CUS, \n" +
            "       SUM(p.PRICE * od.QUANLITY) AS total_price,\n" +
            "       o.DELIVERY_STT\n" +
            "FROM ORDERS o\n" +
            "JOIN CUSTOMERS c ON o.ID_CUS = c.ID_CUS\n" +
            "JOIN ORDERS_DETAIL od ON o.ID_ORDER = od.ID_ORDER\n" +
            "JOIN PRODUCTS p ON od.ID_PRO = p.ID_PRO\n" +
            "GROUP BY o.ID_ORDER;";//truy vấn sql trả về mã đơn hàng, tên khách hàng, tổng giá trị đơn hàng và trạng thái giao hàng
            pre = conn.prepareStatement(sql); //pre một lệnh truy vấn sql select chuẩn bị thực thi trên database
            ResultSet rlt = pre.executeQuery();//thực hiện việc truy vấn và trả kết quả vào 1 đối tượng ResultSet tên rlt
            while(rlt.next()){
                String id = rlt.getString("ID_ORDER");
                String name = rlt.getString("NAME_CUS");
                int cost = rlt.getInt("total_price");
                String deliveryStt = rlt.getString("DELIVERY_STT");
                Object[] row = {id, name, cost, deliveryStt};
                dTM.addRow(row);
            }
        } catch(Exception e){
            System.err.println(" Error : " + e);
        }
        return dTM;
    }
    
    //phương thức hiển thị chi tiết đơn hàng khi nhấn vào
    
    //phương thức thêm đơn hàng vào bảng
    
    //phương thức sửa đơn hàng
    
    //phương thức xóa đơn hàng
    
    //phương thức tìm kiếm đơn hàng theo tên khách hàng hoặc sản phẩm
    
    
    
    //phương thức thêm sản phẩm vào database
        public int customerInsert(String id, String name, int instock , String desc, byte[] image, int price){
        int status = 0;
        try {
            String sql = "Insert Into products(ID_PRO, NAME_PRO, INSTOCK,DESC,IMAGE,PRICE) values(?, ?, ?, ?, ?, ?)";
            pre = conn.prepareStatement(sql);
            pre.setString(1, id);
            pre.setString(2, name);
            pre.setInt(3, instock);
            pre.setString(4, desc);
            pre.setBytes(5, image);
            pre.setInt(6, price);
            status = pre.executeUpdate();
        } catch (Exception e) {
            System.err.println("customerInsert Error : " + e);
            if(e.toString().contains("SQLITE_CONSTRAINT_UNIQUE")){
                status = -1;
            }
        }
        return status;
        }

        // Chuyển đổi ImageIcon thành mảng byte
        /*byte[] imageData = null;
        if (currentImage != null) {
            imageData = getByteArray(currentImage.getImage());
        }*/
 
    //phương thức hiển thị sản phẩm đang có trong database ra bảng
    
    //phương thức hiển thị chi tiết sản phẩm khi nhấn vào
    
    //phương thức chỉnh sửa sản phẩm
    
    // phương thức xóa sản phẩm
    
    //phương thức tìm kiếm sản phẩm theo tên
    
    
    //
}
