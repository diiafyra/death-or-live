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
    }//singleton pattern nhằm chỉ tạo ra 1 đối tượng kndb duy nhất trong quá trình chạy

    private PreparedStatement pre;
    
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
            "JOIN PRODUCT p ON od.ID_PRODUCT = p.ID_PRODUCT\n" +
            "GROUP BY o.ID_ORDER;";
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
}
