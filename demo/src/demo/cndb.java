/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DELL
 */
public class cndb {
    class product{
        private String id;
        private String name;
        private int instock;
        private String desc;
        private byte[] image;
        private int price;
        private Date date;
        private String depot;

        public product(String id, String name, int instock, String desc, byte[] image, int price, Date date, String depot) {
            this.id = id;
            this.name = name;
            this.instock = instock;
            this.desc = desc;
            this.image = image;
            this.price = price;
            this.date = date;
            this.depot = depot;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getInstock() {
            return instock;
        }

        public String getDesc() {
            return desc;
        }

        public byte[] getImage() {
            return image;
        }

        public int getPrice() {
            return price;
        }

        public Date getDate() {
            return date;
        }

        public String getDepot() {
            return depot;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setInstock(int instock) {
            this.instock = instock;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public void setImage(byte[] image) {
            this.image = image;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public void setDepot(String depot) {
            this.depot = depot;
        }
       
        
        
    }
    
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
    
    //phương thức hiển thị tất cả sản phẩm hiện có trong database vào bảng đơn hàng trong ứng dụng
    public DefaultTableModel allOrdersT(){
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
        public int productInsert(String id, String name, int instock , String desc, byte[] image, int price, String depot, Date date){
        int status = 0;
        try {
            String sql = "Insert Into products(ID_PRO, NAME_PRO, INSTOCK,DESC,IMAGE,PRICE, depot, date) values(?, ?, ?, ?, ?, ?, ?, ?)";
            pre = conn.prepareStatement(sql);
            pre.setString(1, id);
            pre.setString(2, name);
            pre.setInt(3, instock);
            pre.setString(4, desc);
            pre.setBytes(5, image);
            pre.setInt(6, price);
            pre.setString(7, depot);
             
            pre.setDate(8, date);
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
 
  
        
    public List<product> allProducts() {
        List<product> proList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM products";
            pre = conn.prepareStatement(sql);
            ResultSet rlt = pre.executeQuery();
            while (rlt.next()) {
                String id = rlt.getString("ID_PRO");
                String name = rlt.getString("NAME_PRO");
                int instock = rlt.getInt("INSTOCK");
                String desc = rlt.getString("DESC");
                byte[] image = rlt.getBytes("image");//getByte và getBytes
                int price = rlt.getInt("price");
                Date date = rlt.getDate("date");
                String depot = rlt.getString("depot");
                product pro = new product(id, name, instock, desc, image, price, date, depot);
                proList.add(pro);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
        return proList;
    }

    //tạo 1 panel chứa mỗi sản phẩm để gọi giống row defautltablemodel

public class proPanel extends JPanel {
    private boolean isHighlighted = false;

    public proPanel(byte[] imageData, String name, int price, int instock) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 300));

        // Sự kiện khi di chuột vào panel
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setHighlighted(true); // Đánh dấu panel được sáng lên
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setHighlighted(false); // Đánh dấu panel không được sáng lên
            }
        });

        ImageIcon image = new ImageIcon(imageData);
        JLabel imageLb = new JLabel(image);
        imageLb.setPreferredSize(new Dimension(200, 200));
        imageLb.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(imageLb);

        JLabel nameLb = new JLabel(name);
        nameLb.setAlignmentX(Component.CENTER_ALIGNMENT); // Căn giữa theo chiều ngang
        JLabel priceLb = new JLabel(String.valueOf(price)); // JLabel chỉ nhận giá trị đối chiếu là String
        priceLb.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel instockLb = new JLabel(String.valueOf(instock));
        instockLb.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(nameLb);
        add(priceLb);
        add(instockLb);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isHighlighted) {
            // Vẽ một màu nền mờ lên panel khi được sáng lên
            g.setColor(new Color(255, 255, 255, 100)); // Màu trắng với độ trong suốt 100
            g.fillRect(0, 0, getWidth(), getHeight()); // Vẽ một hình chữ nhật đậm nguyên màu trên toàn bộ panel
        }
    }

    // Phương thức để đặt trạng thái sáng lên của panel
    public void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
        repaint(); // Vẽ lại panel để hiển thị trạng thái mới
    }
}


  //phương thức hiển thị sản phẩm đang có trong database ra bảng
    
    public List<proPanel> allProP(List<product> allPro) {
        List<proPanel> proPanels = new ArrayList<>();
        for (product product : allPro) {
            String name = product.getName();
            int instock = product.getInstock();
            int price = product.getPrice();
            byte[] image = product.getImage();
            proPanel proP = new proPanel(image, name, price, instock);
            proPanels.add(proP);
        }
        return proPanels;
    }
    
    //phương thức hiển thị chi tiết sản phẩm khi nhấn vào
    
    //phương thức chỉnh sửa sản phẩm
    
    // phương thức xóa sản phẩm
    
    //phương thức tìm kiếm sản phẩm theo tên
    
    
    //
}
