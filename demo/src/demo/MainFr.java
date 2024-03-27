/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;
import com.formdev.flatlaf.FlatLightLaf;
import static demo.Handler.allOrdersT;
import static demo.Handler.allProP;
import static demo.Handler.createPieChart;
import static demo.Handler.dateError;
import static demo.Handler.intErrorTable;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import org.jfree.chart.ChartPanel;
import org.jfree.data.general.DefaultPieDataset;
import models.Order;
import models.OrderInfo;
import models.Product;
import models.ProductEx;
import models.orderDetailTable;
import models.proPanel;
/**
 *
 * @author DELL
 */
public class MainFr extends javax.swing.JFrame {
    public MainFr() {
        initComponents();
        //Giao diện lớn bằng cả màn hình 
        setExtendedState(MainFr.MAXIMIZED_BOTH);
    }
    //triển khai một pattern Singleton để đảm bảo rằng một lớp chỉ có một thể hiện duy nhất
    private static MainFr instance;
    String errorMess = "";
    public int del_stt =1;//mỗi lần nhấn nút lưu thì del_stt reset = 1
    int option = 0;
    String key = "";
//    String search = "";
    boolean isEditMode = false; //thông báo cho nút thêm là đang ở trạng thái edit order hay insert order
    boolean searchMode = false; //thông báo cho bảng table_order hiện thị order ở trạng thái nào
 
    boolean canEditDelStt = true;
    int deStock = 0;
    
    private int deStockCheck(JTextField a){
        int db_del_stt = 1;
        if(!isEditMode){
            if(del_stt != 0){
                return 1;
            } else{
                return 0;
            }
        }else{
            cndb db = cndb.getInstance();
            db.open();
            List<Order> orders = db.allOrders();
            db.close();
            String id_o = (String) a.getText();
            for(Order ord: orders){
                if(ord.getId_o().equals(id_o)){
                    db_del_stt = ord.getDel_stt();
                    break;
                }
            }
        }
        if(db_del_stt != 0 && del_stt ==0){
            return 2;
        } else if(db_del_stt ==0 && del_stt !=0){
            return 1;
        }else{
            return 0;
        }
    }
    private void dateNow(JTextField date){
        LocalDate today = LocalDate.now();
        String formattedDate = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        date.setText(formattedDate);
    }
    
    private ArrayList<String> getYear(){
        ArrayList<String> years = new ArrayList<>();
        cndb db = cndb.getInstance();
        db.open();
        int eYear = db.getEarliestYear();
        LocalDate currentDate = LocalDate.now();
        int lYear = currentDate.getYear();
        for(int i=eYear; i<=lYear; i++){
            years.add(String.valueOf(i));
        }
        return years;
    }
    
    private void defaultStt() {
        
        //Border border = BorderFactory.createLineBorder(Color.pink, 3);
        //Border emptyBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);
        cancel.setBackground(Color.white);
        toShip.setBackground(Color.white);
        toReceive.setBackground(Color.white);
        completed.setBackground(Color.white);
    }
    

        private void addDefault(){
        id_o_f.setEditable(true);
        name_c_f.setEditable(true);
        date_o_f.setEditable(true);
        addr_f.setEditable(true);
        pay_type.setEnabled(true);
        orderdetail.setEnabled(true);
        b_save_order.setEnabled(true);
        addOD.setEnabled(true);
        date_d_f.setEditable(true);
        
    }
        
    private void displayDefault(){
        id_o_f.setEditable(false);
        name_c_f.setEditable(false);
        date_o_f.setEditable(false);
        addr_f.setEditable(false);
        pay_type.setEnabled(false);
        orderdetail.setEnabled(false);
        b_save_order.setEnabled(false);
        addOD.setEnabled(false);
        date_d_f.setEditable(false);
    }
    
    
    private void defaultOrder(){
        defaultStt();
        id_o_f.setText(null);
        name_c_f.setText(null);
        date_o_f.setText(null);
        addr_f.setText(null);
        date_d_f.setText(null);
        DefaultTableModel model = (DefaultTableModel) orderdetail.getModel();
        model.setRowCount(0);
        model.addRow(new Object[]{null, null, null, null});      
    }
    
    private  JComboBox<String> createProComboBox(JTable table) {
        JComboBox<String> comboBox = new JComboBox<>();
        cndb db = cndb.getInstance();
        db.open();
        List<Product> allPro = db.allProducts();
        db.close();        
        for(Product pro : allPro){
            comboBox.addItem(pro.getName_p());
        }
        comboBox.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1 ) {
               int indexP =  comboBox.getSelectedIndex();
                if (indexP != -1 ) {                
                    Product selectedProduct = allPro.get(indexP);
                    table.setValueAt(selectedProduct.getPrice_s(), selectedRow, 3);
                    table.setValueAt(selectedProduct.getId_p(), selectedRow, 0);
                }
            }
        });
        
        table.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int column = e.getColumn();
                    if (column == 0 || column == 2) { // Check if changed column is quantity or price
                        if(column == 2){
                            errMess.setText(intErrorTable(table, e));

                        }
                        int row = e.getFirstRow();
                        if( table.getValueAt(row, 0) !=null && table.getValueAt(row, 2)!=null){
                            int quantity = Integer.parseInt(table.getValueAt(row, 2).toString());
                            String id_p = (String) table.getValueAt(row, 0);
                            cndb db = cndb.getInstance();
                            db.open();
                            int stock = db.getStock(id_p);
                            db.close();
                            int preQual =0;
                            
                            for(int i =0; i<table.getRowCount(); i++){
                                String id = (String) table.getValueAt(i, 0);
                                String qual = (String) table.getValueAt(i, 2);
                                if(id!= null && qual != null && id.equals(id_p)){
                                    preQual += Integer.valueOf(qual);
                                }
                            }
                            System.out.println(preQual);
                            if(quantity>(stock - preQual+quantity)){
                                JOptionPane.showMessageDialog(rootPane, "Vượt Quá Số Lượng Tồn Kho. Tồn Kho: " + stock);
                                table.setValueAt(null, row, 2);
                            } else{
                                float price = Float.valueOf(table.getValueAt(row, 3).toString());
                                float totalPrice = quantity * price;
                                table.setValueAt(totalPrice, row, 4); // Update total price column
                            }
                        }
                    }
                }
            }
        });
        
        TableCellEditor nonEditableEditor = new DefaultCellEditor(new JTextField()) {
            @Override
            public boolean isCellEditable(EventObject e) {
                return false;
            }
        };

        // Thiết lập trình biên tập tùy chỉnh cho cột cụ thể
        table.getColumnModel().getColumn(3).setCellEditor(nonEditableEditor);
        table.getColumnModel().getColumn(0).setCellEditor(nonEditableEditor);
        return comboBox;
    }
    
    private void xoaDonHang(JTable table) {// thiếu JTable để lấy selected row
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            String selectedID = tableModel.getValueAt(selectedRow, 0).toString();
            //System.out.println(selectedID);
            cndb db = cndb.getInstance();
            db.open();
            int deleted = db.xoa_don_hang(selectedID);
            db.close();
            if (deleted == 0) {
                JOptionPane.showMessageDialog(this, "Đã xóa đơn hàng thành công!");
                RefreshTables();
            } else {
                JOptionPane.showMessageDialog(this, "Không thể xóa đơn hàng!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một đơn hàng để xóa!");
        }
    }    

   public static MainFr getInstance() {
        if (instance == null) {
            instance = new MainFr();
        }
        return instance;
    }
   
    //Cập nhật dữ liệu trên jTable2 
     public void RefreshTables() {
        cndb db =  cndb.getInstance();
        db.open();
        List<Order> allOrders = db.allOrders();
        //System.out.print(allOrders.get(22).getDate_o());
        table_order.setModel(allOrdersT(allOrders));
        db.close();
    }
     
    public void RefreshProList() {
        //cần phải sửa jPanel1 sang layout grid hoặc flow hoặc box thì mới add được, poe muôn năm :)))
        cndb db =  cndb.getInstance();
        db.open();
        List<Product> allPro = db.allProducts();
       
        List<proPanel> proPanels = allProP(allPro);
        panel_sp.removeAll();
        for(proPanel proP: proPanels){
            panel_sp.add(proP);
        }
        db.close();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MENU = new javax.swing.JTabbedPane();
        sanpham = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        panel_sp = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        b_add_product = new javax.swing.JButton();
        b_search_product = new javax.swing.JButton();
        txt_search_product = new javax.swing.JTextField();
        donhang = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_order = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        id_o_f = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        name_c_f = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        date_o_f = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        addr_f = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        pay_type = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        orderdetail = new javax.swing.JTable();
        completed = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        toShip = new javax.swing.JButton();
        toReceive = new javax.swing.JButton();
        addOD = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        date_d_f = new javax.swing.JTextField();
        errMess = new javax.swing.JLabel();
        b_save_order = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        b_addOrder = new javax.swing.JButton();
        b_delete_order = new javax.swing.JButton();
        b_edit_order = new javax.swing.JButton();
        b_search_order = new javax.swing.JButton();
        txt_search_order = new javax.swing.JTextField();
        cb_search_option = new javax.swing.JComboBox<>();
        b_filter_order = new javax.swing.JButton();
        thongke = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        p_analysis = new javax.swing.JPanel();
        p_overview = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        cb_month = new javax.swing.JComboBox<>();
        cb_year = new javax.swing.JComboBox<>(getYear().toArray(new String[0]));
        p_analysis_product = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        p_analysis_profit = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        MENU.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        MENU.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        sanpham.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panel_sp.setLayout(new java.awt.GridLayout(0, 6));
        jScrollPane2.setViewportView(panel_sp);

        b_add_product.setText("Thêm");
        b_add_product.setFocusable(false);
        b_add_product.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        b_add_product.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        b_add_product.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_add_productActionPerformed(evt);
            }
        });

        b_search_product.setText("Tìm kiếm");
        b_search_product.setFocusable(false);
        b_search_product.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        b_search_product.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        txt_search_product.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_search_productActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(b_add_product)
                .addGap(276, 276, 276)
                .addComponent(b_search_product)
                .addGap(18, 18, 18)
                .addComponent(txt_search_product, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(2330, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(b_search_product, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(b_add_product)
                    .addComponent(txt_search_product, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout sanphamLayout = new javax.swing.GroupLayout(sanpham);
        sanpham.setLayout(sanphamLayout);
        sanphamLayout.setHorizontalGroup(
            sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        sanphamLayout.setVerticalGroup(
            sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sanphamLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE))
        );

        MENU.addTab("SẢN PHÂM", sanpham);

        table_order.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table_order.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                table_orderMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(table_order);

        jSplitPane1.setLeftComponent(jScrollPane1);

        jLabel1.setText("Mã");

        jLabel4.setText("Khách Hàng");

        name_c_f.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                name_c_fActionPerformed(evt);
            }
        });

        jLabel3.setText("Ngày đặt");

        date_o_f.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                date_o_fActionPerformed(evt);
            }
        });
        date_o_f.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                date_o_fKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                date_o_fKeyTyped(evt);
            }
        });

        jLabel5.setText("Địa chỉ giao hàng");

        jLabel6.setText("Phương thức thanh toán");

        pay_type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Trực tiếp", "COD", "Chuyển khoản" }));

        jLabel2.setText("Nội dung đơn hàng");

        orderdetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Mã sản phẩm","Sản phẩm", "Số lượng", "Đơn giá","Giá", "Giảm giá"
            }
        ));
        TableColumn productColumn = orderdetail.getColumnModel().getColumn(1);
        JComboBox comboBox = createProComboBox(orderdetail);
        productColumn.setCellEditor(new DefaultCellEditor(comboBox));
        orderdetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                orderdetailMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(orderdetail);

        completed.setText("Đã giao");
        completed.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        completed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                completedActionPerformed(evt);
            }
        });

        cancel.setText("Hủy Đơn");
        cancel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        toShip.setText("Chờ Lấy Hàng");
        toShip.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        toShip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toShipActionPerformed(evt);
            }
        });

        toReceive.setText("Đang Giao");
        toReceive.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        toReceive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toReceiveActionPerformed(evt);
            }
        });

        addOD.setText("+");
        addOD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addODActionPerformed(evt);
            }
        });

        jLabel7.setText("Ngày giao");

        date_d_f.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                date_d_fActionPerformed(evt);
            }
        });
        date_d_f.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                date_d_fKeyTyped(evt);
            }
        });

        errMess.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        errMess.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(toShip)
                                .addGap(18, 18, 18)
                                .addComponent(toReceive, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(17, 17, 17))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(errMess, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(completed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cancel, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))
                        .addContainerGap())
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 2740, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(id_o_f)
                            .addComponent(name_c_f)
                            .addComponent(date_o_f)
                            .addComponent(addr_f)
                            .addComponent(pay_type, 0, 2580, Short.MAX_VALUE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(addOD)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(date_d_f)))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancel)
                    .addComponent(errMess, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(completed)
                    .addComponent(toShip)
                    .addComponent(toReceive))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(id_o_f, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel1)))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(name_c_f, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(date_o_f, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(date_d_f, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(addr_f, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(pay_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(addOD))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))
        );

        errMess.getAccessibleContext().setAccessibleName("errMess");

        b_save_order.setText("Lưu");
        b_save_order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_save_orderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(b_save_order))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(b_save_order)
                .addGap(35, 35, 35))
        );

        jSplitPane1.setRightComponent(jPanel2);

        b_addOrder.setText("Thêm");
        b_addOrder.setFocusable(false);
        b_addOrder.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        b_addOrder.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        b_addOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_addOrderActionPerformed(evt);
            }
        });

        b_delete_order.setText("Xóa");
        b_delete_order.setFocusable(false);
        b_delete_order.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        b_delete_order.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        b_delete_order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_delete_orderActionPerformed(evt);
            }
        });

        b_edit_order.setText("Chỉnh sửa");
        b_edit_order.setFocusable(false);
        b_edit_order.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        b_edit_order.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        b_edit_order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_edit_orderActionPerformed(evt);
            }
        });

        b_search_order.setText("Tìm kiếm");
        b_search_order.setFocusable(false);
        b_search_order.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        b_search_order.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        b_search_order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_search_orderActionPerformed(evt);
            }
        });

        txt_search_order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_search_orderActionPerformed(evt);
            }
        });
        txt_search_order.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_search_orderKeyReleased(evt);
            }
        });

        cb_search_option.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tên khách hàng", "ID đơn hàng", "Sản phẩm" }));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_search_order, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(b_search_order)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cb_search_option, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(b_filter_order, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(b_addOrder)
                .addGap(18, 18, 18)
                .addComponent(b_delete_order)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(b_edit_order)
                .addGap(30, 30, 30))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(b_search_order, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b_addOrder, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b_delete_order, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b_edit_order, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_search_order, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cb_search_option, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(b_filter_order, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout donhangLayout = new javax.swing.GroupLayout(donhang);
        donhang.setLayout(donhangLayout);
        donhangLayout.setHorizontalGroup(
            donhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        donhangLayout.setVerticalGroup(
            donhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(donhangLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );

        MENU.addTab("ĐƠN HÀNG", donhang);

        p_analysis.setLayout(new java.awt.GridLayout(3, 0));

        p_overview.setBackground(new java.awt.Color(255, 255, 255));
        p_overview.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 51, 0));
        jLabel9.setText("Tổng Quan");

        jPanel1.setLayout(new java.awt.GridLayout(2, 4));

        jPanel4.setLayout(new java.awt.GridLayout(0, 1, 0, 25));
        // Thêm MouseListener vào jPanel4
        jPanel4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Thay đổi màu nền khi di chuột vào
                jPanel4.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Thay đổi màu nền khi di chuột ra khỏi
                jPanel4.setBackground(Color.GRAY); // Thay đổi màu nền khi di chuột ra khỏi
            }
        });

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("pp");
        jLabel12.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel4.add(jLabel12);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Chờ Lấy Hàng");
        jLabel11.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel4.add(jLabel11);

        jPanel1.add(jPanel4);

        jPanel7.setLayout(new java.awt.GridLayout(0, 1, 0, 25));

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("pp");
        jLabel13.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel7.add(jLabel13);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Đang Giao");
        jLabel14.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel14.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel7.add(jLabel14);

        jPanel1.add(jPanel7);

        jPanel8.setLayout(new java.awt.GridLayout(0, 1, 0, 25));

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("pp");
        jLabel15.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel15.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel8.add(jLabel15);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Hoàn Thành");
        jLabel16.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel16.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel8.add(jLabel16);

        jPanel1.add(jPanel8);

        jPanel10.setLayout(new java.awt.GridLayout(0, 1, 0, 25));

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("pp");
        jLabel17.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel17.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel10.add(jLabel17);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Đã Hủy");
        jLabel18.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel18.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel18.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel10.add(jLabel18);

        jPanel1.add(jPanel10);

        jPanel11.setLayout(new java.awt.GridLayout(0, 1, 0, 25));

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("pp");
        jLabel19.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel19.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel11.add(jLabel19);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("SP Còn Ít");
        jLabel20.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel20.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel20.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel11.add(jLabel20);

        jPanel1.add(jPanel11);

        jPanel12.setLayout(new java.awt.GridLayout(0, 1, 0, 25));

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("pp");
        jLabel21.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel21.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel12.add(jLabel21);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("SP Không Bán Được");
        jLabel22.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel22.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel22.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel12.add(jLabel22);

        jPanel1.add(jPanel12);

        jPanel13.setLayout(new java.awt.GridLayout(0, 1, 0, 25));

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("pp");
        jLabel23.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel23.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel13.add(jLabel23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("SP Tồn Nhiều");
        jLabel24.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel24.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel24.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel13.add(jLabel24);

        jPanel1.add(jPanel13);

        jPanel14.setLayout(new java.awt.GridLayout(0, 1, 0, 25));

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("pp");
        jLabel25.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel25.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel14.add(jLabel25);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("SP Bán Chạy");
        jLabel26.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel26.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel26.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel14.add(jLabel26);

        jPanel1.add(jPanel14);

        cb_month.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

        javax.swing.GroupLayout p_overviewLayout = new javax.swing.GroupLayout(p_overview);
        p_overview.setLayout(p_overviewLayout);
        p_overviewLayout.setHorizontalGroup(
            p_overviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_overviewLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(p_overviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(p_overviewLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(cb_month, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cb_year, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 2824, Short.MAX_VALUE)))
                .addGap(39, 39, 39))
        );
        p_overviewLayout.setVerticalGroup(
            p_overviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_overviewLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(p_overviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cb_month, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cb_year, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        cb_month.getAccessibleContext().setAccessibleName("");
        cb_month.getAccessibleContext().setAccessibleDescription("");

        p_analysis.add(p_overview);

        p_analysis_product.setBackground(new java.awt.Color(255, 255, 255));
        p_analysis_product.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 51, 0));
        jLabel8.setText("Thống Kê Doanh Thu Theo Từng Sản Phẩm");

        jPanel15.setLayout(new javax.swing.BoxLayout(jPanel15, javax.swing.BoxLayout.LINE_AXIS));

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout p_analysis_productLayout = new javax.swing.GroupLayout(p_analysis_product);
        p_analysis_product.setLayout(p_analysis_productLayout);
        p_analysis_productLayout.setHorizontalGroup(
            p_analysis_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_analysis_productLayout.createSequentialGroup()
                .addGroup(p_analysis_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(p_analysis_productLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel8))
                    .addGroup(p_analysis_productLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(jButton1)))
                .addContainerGap(2695, Short.MAX_VALUE))
        );
        p_analysis_productLayout.setVerticalGroup(
            p_analysis_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_analysis_productLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel8)
                .addGroup(p_analysis_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(p_analysis_productLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(185, 185, 185))
                    .addGroup(p_analysis_productLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(107, Short.MAX_VALUE))))
        );

        p_analysis.add(p_analysis_product);

        p_analysis_profit.setBackground(new java.awt.Color(255, 255, 255));
        p_analysis_profit.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 51, 0));
        jLabel10.setText("Thống Kê Doanh Thu Theo Tháng");

        javax.swing.GroupLayout p_analysis_profitLayout = new javax.swing.GroupLayout(p_analysis_profit);
        p_analysis_profit.setLayout(p_analysis_profitLayout);
        p_analysis_profitLayout.setHorizontalGroup(
            p_analysis_profitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_analysis_profitLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        p_analysis_profitLayout.setVerticalGroup(
            p_analysis_profitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_analysis_profitLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel10)
                .addContainerGap(426, Short.MAX_VALUE))
        );

        p_analysis.add(p_analysis_profit);

        jScrollPane4.setViewportView(p_analysis);

        javax.swing.GroupLayout thongkeLayout = new javax.swing.GroupLayout(thongke);
        thongke.setLayout(thongkeLayout);
        thongkeLayout.setHorizontalGroup(
            thongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4)
        );
        thongkeLayout.setVerticalGroup(
            thongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
        );

        MENU.addTab("THỐNG KÊ", thongke);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MENU)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MENU, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void name_c_fActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_name_c_fActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_name_c_fActionPerformed

    private void date_o_fActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_date_o_fActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_date_o_fActionPerformed

    private void table_orderMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_orderMouseReleased
        displayDefault();
        int selectedR = table_order.getSelectedRow();
        int index = -1;
        cndb db =  cndb.getInstance();
        db.open();
        List<Order> allOrders = new ArrayList<>();
        allOrders = db.allOrders();
        List<Product> allPro = db.allProducts();
        db.close();
        //System.out.print(""+ allOrders.get(index).getName_c());
        String id_o = (String) table_order.getValueAt(selectedR, 0);
        for (Order ord : allOrders) {
            if (ord.getId_o().equals(id_o)) {
                index = allOrders.indexOf(ord);
                break;
            }
        }
        id_o_f.setText(id_o);
        name_c_f.setText(allOrders.get(index).getName_c());
        date_o_f.setText(allOrders.get(index).getDate_o());
        date_d_f.setText(allOrders.get(index).getDate_d());
        addr_f.setText(allOrders.get(index).getAddr());
        pay_type.setSelectedItem(allOrders.get(index).getPay_type());
        
        

        int del_stt = allOrders.get(index).getDel_stt();
        defaultStt();
        switch (del_stt) {
            case 0:
            cancel.setBackground(Color.pink);
            break;
            case 1:
            toShip.setBackground(Color.pink);
            break;
            case 2:
            toReceive.setBackground(Color.pink);
            break;
            default:
            completed.setBackground(Color.pink);
            break;
        }

        //orderdetail
        DefaultTableModel model = (DefaultTableModel) orderdetail.getModel();
        model.setRowCount(0);
        List<OrderInfo> orderDetail= allOrders.get(index).getOrder_detail();
        for(OrderInfo od : orderDetail){
            String id = od.getId_p();
            //System.out.print(" " + id);
            int qual = od.getQuantity();
            int discount = od.getDiscount();
            int price = 0;
            String id_p=null;
            String name = null;
            for (Product product : allPro) {
                //System.out.print(" thu" +product.getId_p()) ;
                if (product.getId_p().equals(id)) {
                    name = product.getName_p();
                    price = product.getPrice_s();
                    id_p = product.getId_p();
                }
            }

            Object[] row = {id_p, name, qual, price, price*qual, discount};
            model.addRow(row);
            isEditMode = true;
        }
    }//GEN-LAST:event_table_orderMouseReleased

    private void addODActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addODActionPerformed
        DefaultTableModel m = (DefaultTableModel) orderdetail.getModel();
        m.addRow(new Object[]{null, null, null, null});      
    }//GEN-LAST:event_addODActionPerformed

    private void b_add_productActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_add_productActionPerformed
        productF pro = new productF();
        pro.setVisible(true);
    }//GEN-LAST:event_b_add_productActionPerformed

    private void txt_search_productActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_search_productActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_search_productActionPerformed

    private void txt_search_orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_search_orderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_search_orderActionPerformed

    private void b_addOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_addOrderActionPerformed
        addDefault();
        defaultOrder();
        dateNow(date_o_f);
        cndb db = cndb.getInstance();
        db.open();
        try {
            id_o_f.setText(db.generateOrderId() );
        } catch (SQLException ex) {
            Logger.getLogger(MainFr.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.close();
    }//GEN-LAST:event_b_addOrderActionPerformed

    private void orderdetailMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderdetailMouseReleased


    }//GEN-LAST:event_orderdetailMouseReleased

    private void toShipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toShipActionPerformed
        if(canEditDelStt){
            defaultStt();
            toShip.setBackground(Color.pink);
            b_save_order.setEnabled(true);
            del_stt=1;

        }
    }//GEN-LAST:event_toShipActionPerformed

    private void toReceiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toReceiveActionPerformed
        if(canEditDelStt){
            defaultStt();
            toReceive.setBackground(Color.pink);
            b_save_order.setEnabled(true);
            del_stt=2;

        }
    }//GEN-LAST:event_toReceiveActionPerformed

    private void completedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_completedActionPerformed
        if(canEditDelStt){
            dateNow(date_d_f);
//            date_d_f.setEditable(false);
            defaultStt();
            completed.setBackground(Color.pink);
            b_save_order.setEnabled(true);
            del_stt=3;

        }
    }//GEN-LAST:event_completedActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        if(canEditDelStt){
            defaultStt();
            cancel.setBackground(Color.pink);
            b_save_order.setEnabled(true);
            del_stt=0;

        }
    }//GEN-LAST:event_cancelActionPerformed

    private void b_save_orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_save_orderActionPerformed
        String id_o = id_o_f.getText().trim();
        String name_c = name_c_f.getText().trim();
        String date_o = Handler.getDate(date_o_f);
        String date_d = date_d_f.getText();
        
        //loi cung zui
        if (date_d != null && !date_d.isEmpty()) {
            date_d = Handler.getDate(date_d_f);
        } else {
            date_d = null; 
        }
        
        
        String addr =addr_f.getText().trim();
        String pay = (String) pay_type.getSelectedItem();
        

        List<OrderInfo> order_detail = new ArrayList<>();
        for (int row = 0; row < orderdetail.getRowCount(); row++) {
            String id_p = orderdetail.getValueAt(row, 0) +"";
            String qualS = orderdetail.getValueAt(row, 2) +"";
            String discountS = orderdetail.getValueAt(row, 5) +"";
            if(id_p == null || qualS == null ){
                JOptionPane.showMessageDialog(rootPane, "Không Được Để Trống Cột Sản Phẩm Và Số Lưọng");
            } else{                
                int qual = Integer.parseInt(qualS);     
                int discount = Integer.parseInt(discountS);   
                //Nếu trạng thái giao hàng là 1 2 3 thì giảm tồn kho, từ 1 2 3 thành 0 thì tăng tồn kho
                if(deStockCheck(id_o_f) ==1 ){
                    cndb db = cndb.getInstance();
                    db.open();  
                    db.stockUpdate(id_p, qual);
                    db.close();
                }else if(deStockCheck(id_o_f) ==2){
                    cndb db = cndb.getInstance();
                    db.open();  
                    db.stockUpdate(id_p, -qual);
                    db.close();         
                }
                //System.out.println("thunghiem "+keyO +"/"+value);
                if (qual != 0) {
                    boolean found = false;
                    for (OrderInfo od : order_detail) {
                        if (od.getId_p().equals(id_p) && od.getDiscount() == discount) {
                            // Nếu đã tồn tại OrderInfo với cùng id_p và discount, cập nhật qual
                            od.setQuantity(od.getQuantity() + qual);
                            found = true;
                            break;
                        }
                    }
                    // Nếu không tìm thấy OrderInfo với cùng id_p và discount, thêm mới vào danh sách
                    if (!found) {
                        order_detail.add(new OrderInfo(id_p, qual, discount));
                    }
                }

            }
        }
        deStock =0;
        
        if(isEditMode){
            cndb db = cndb.getInstance();
            db.open();
            int f = db.orderEdit(id_o, name_c, date_o, date_d, addr, pay, del_stt , order_detail);
            db.close();
            RefreshTables();
            if(f != -1){
                JOptionPane.showMessageDialog(rootPane, "CHỈNH SỬA ĐƠN HÀNG THÀNH CÔNG! ");
            }
            addDefault();
            defaultOrder();
            isEditMode=false;
        } else{
            cndb db = cndb.getInstance();
            db.open();           
            int f = db.orderInsert(id_o, name_c, date_o, date_d, addr, pay, del_stt , order_detail);
            db.close();
             RefreshTables();
             if(f != -1){
                 JOptionPane.showMessageDialog(rootPane, "THÊM ĐƠN HÀNG THÀNH CÔNG! ");
            } 
        }
        del_stt = 1;
//        System.out.println(del_stt);
    }//GEN-LAST:event_b_save_orderActionPerformed

    private void b_delete_orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_delete_orderActionPerformed
        xoaDonHang(table_order);
        
    }//GEN-LAST:event_b_delete_orderActionPerformed

    private void b_edit_orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_edit_orderActionPerformed
        addDefault();
        isEditMode = true;
    }//GEN-LAST:event_b_edit_orderActionPerformed

    private void date_d_fActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_date_d_fActionPerformed
        
    }//GEN-LAST:event_date_d_fActionPerformed

    private void txt_search_orderKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_search_orderKeyReleased
        searchMode = true;
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            b_search_orderActionPerformed(null);
        }
        else{
            key = txt_search_order.getText().trim();
            option = cb_search_option.getSelectedIndex();
            cndb db = cndb.getInstance();
            db.open();
            String sqlEx = db.creaSqlEx(false, option);
            List<Order> allOrders = db.allOrdersFilter(sqlEx, key, false);
           table_order.setModel(allOrdersT(allOrders));
           db.close();           
        }
    }//GEN-LAST:event_txt_search_orderKeyReleased

    private void b_search_orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_search_orderActionPerformed

        option = cb_search_option.getSelectedIndex();
        searchMode = true;
        key = txt_search_order.getText().trim();
        if(key.equals("")||key == null){
            JOptionPane.showMessageDialog(rootPane,"HÃY NHẬP VÀO GIÁ TRỊ CẦN TÌM");
        }else{
            cndb db = cndb.getInstance();
            db.open();
            String sqlEx = db.creaSqlEx(true, option);
            List<Order> allOrders = db.allOrdersFilter(sqlEx, key, true);
           table_order.setModel(allOrdersT(allOrders));
           db.close();           
        }       
    }//GEN-LAST:event_b_search_orderActionPerformed

    private void date_o_fKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_date_o_fKeyReleased

    }//GEN-LAST:event_date_o_fKeyReleased

    private void date_o_fKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_date_o_fKeyTyped
        errMess.setText(dateError(evt,date_o_f));
    }//GEN-LAST:event_date_o_fKeyTyped

    private void date_d_fKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_date_d_fKeyTyped
        errMess.setText(dateError(evt,date_d_f));
        defaultStt();       
        if(!date_d_f.getText().equals("")){
            canEditDelStt = false;
            completed.setBackground(Color.pink);
        }else{
            canEditDelStt = true;
        }

        b_save_order.setEnabled(true);
        del_stt=3;
    }//GEN-LAST:event_date_d_fKeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        cndb db = cndb.getInstance();
        db.open();
        DefaultPieDataset a= db.salesReportsdts("03", "2024",false, true);
        System.out.println(a.getValue(0));

        ChartPanel chartP = new ChartPanel(createPieChart(a, "Thử nghiệm"));
        jPanel15.add(chartP);
        jPanel15.revalidate();
        db.close();
    }//GEN-LAST:event_jButton1ActionPerformed


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
            try {
        UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFr().setVisible(true);


            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane MENU;
    private javax.swing.JButton addOD;
    private javax.swing.JTextField addr_f;
    private javax.swing.JButton b_addOrder;
    private javax.swing.JButton b_add_product;
    private javax.swing.JButton b_delete_order;
    private javax.swing.JButton b_edit_order;
    private javax.swing.JButton b_filter_order;
    private javax.swing.JButton b_save_order;
    private javax.swing.JButton b_search_order;
    private javax.swing.JButton b_search_product;
    private javax.swing.JButton cancel;
    private javax.swing.JComboBox<String> cb_month;
    private javax.swing.JComboBox<String> cb_search_option;
    private javax.swing.JComboBox<String> cb_year;
    private javax.swing.JButton completed;
    private javax.swing.JTextField date_d_f;
    private javax.swing.JTextField date_o_f;
    private javax.swing.JPanel donhang;
    private javax.swing.JLabel errMess;
    private javax.swing.JTextField id_o_f;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextField name_c_f;
    private javax.swing.JTable orderdetail;
    private javax.swing.JPanel p_analysis;
    private javax.swing.JPanel p_analysis_product;
    private javax.swing.JPanel p_analysis_profit;
    private javax.swing.JPanel p_overview;
    private javax.swing.JPanel panel_sp;
    private javax.swing.JComboBox<String> pay_type;
    private javax.swing.JPanel sanpham;
    private javax.swing.JTable table_order;
    private javax.swing.JPanel thongke;
    private javax.swing.JButton toReceive;
    private javax.swing.JButton toShip;
    private javax.swing.JTextField txt_search_order;
    private javax.swing.JTextField txt_search_product;
    // End of variables declaration//GEN-END:variables
}