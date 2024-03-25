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
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.jfree.chart.ChartPanel;
import org.jfree.data.general.DefaultPieDataset;
import structure.Order;
import structure.Product;
import structure.ProductEx;
import structure.proPanel;
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
    public int del_stt;
    public int index;
    int indexP;
    int option = 0;
    String key = "";
//    String search = "";
    boolean isEditMode = false;
    boolean searchMode = false;
    boolean closeSearch = false;
    boolean canEditDS = true;
    
    private void dateNow(JTextField date){
        LocalDate today = LocalDate.now();
        String formattedDate = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        date.setText(formattedDate);
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
        saveB.setEnabled(true);
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
        saveB.setEnabled(false);
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
    
    private  JComboBox<String> createProComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();
        cndb db = cndb.getInstance();
        db.open();
        List<Product> allPro = db.allProducts();
        for(Product pro : allPro){
            comboBox.addItem(pro.getName_p());
        }
        db.close();
        
        comboBox.addActionListener(e -> {
            int selectedRow = orderdetail.getSelectedRow();
            if (selectedRow != -1 ) {
               indexP =  comboBox.getSelectedIndex();
                if (indexP != -1 ) {                
                    Product selectedProduct = allPro.get(indexP);
                    orderdetail.setValueAt(selectedProduct.getPrice_s(), selectedRow, 3);
                    orderdetail.setValueAt(selectedProduct.getId_p(), selectedRow, 0);
                }
            }
        });
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
        orderTable.setModel(allOrdersT(allOrders));
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
        nut_them_sp = new javax.swing.JButton();
        nut_tim_kiem_sp = new javax.swing.JButton();
        txt_tim_kiem_sp = new javax.swing.JTextField();
        donhang = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        orderTable = new javax.swing.JTable();
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
        saveB = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        addB = new javax.swing.JButton();
        deleteB = new javax.swing.JButton();
        editB = new javax.swing.JButton();
        searchB = new javax.swing.JButton();
        searchF = new javax.swing.JTextField();
        searchOption = new javax.swing.JComboBox<>();
        filterB = new javax.swing.JButton();
        thongke = new javax.swing.JPanel();
        chartPanel = new javax.swing.JPanel();
        pie_chart = new javax.swing.JButton();
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

        nut_them_sp.setText("Thêm");
        nut_them_sp.setFocusable(false);
        nut_them_sp.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        nut_them_sp.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        nut_them_sp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nut_them_spActionPerformed(evt);
            }
        });

        nut_tim_kiem_sp.setText("Tìm kiếm");
        nut_tim_kiem_sp.setFocusable(false);
        nut_tim_kiem_sp.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        nut_tim_kiem_sp.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        txt_tim_kiem_sp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tim_kiem_spActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nut_them_sp)
                .addGap(276, 276, 276)
                .addComponent(nut_tim_kiem_sp)
                .addGap(18, 18, 18)
                .addComponent(txt_tim_kiem_sp, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nut_tim_kiem_sp, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(nut_them_sp)
                    .addComponent(txt_tim_kiem_sp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE))
        );

        MENU.addTab("SẢN PHÂM", sanpham);

        orderTable.setModel(new javax.swing.table.DefaultTableModel(
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
        orderTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                orderTableMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(orderTable);

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
                "Mã sản phẩm","Sản phẩm", "Số lượng", "Đơn giá","Giá"
            }
        ));
        TableColumn productColumn = orderdetail.getColumnModel().getColumn(1);
        JComboBox comboBox = createProComboBox();
        productColumn.setCellEditor(new DefaultCellEditor(comboBox));
        orderdetail.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int column = e.getColumn();
                    if (column == 0 || column == 2) { // Check if changed column is quantity or price
                        int row = e.getFirstRow();
                        if( orderdetail.getValueAt(row, 0) !=null && orderdetail.getValueAt(row, 2)!=null){
                            int quantity = Integer.parseInt(orderdetail.getValueAt(row, 2).toString());
                            float price = Float.valueOf(orderdetail.getValueAt(row, 3).toString());
                            float totalPrice = quantity * price;
                            orderdetail.setValueAt(totalPrice, row, 4); // Update total price column
                        }
                    }
                }
            }
        });
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
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 894, Short.MAX_VALUE)
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
                            .addComponent(pay_type, 0, 734, Short.MAX_VALUE)
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
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE))
        );

        errMess.getAccessibleContext().setAccessibleName("errMess");

        saveB.setText("Lưu");
        saveB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBActionPerformed(evt);
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
                        .addComponent(saveB))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(saveB)
                .addGap(35, 35, 35))
        );

        jSplitPane1.setRightComponent(jPanel2);

        addB.setText("Thêm");
        addB.setFocusable(false);
        addB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBActionPerformed(evt);
            }
        });

        deleteB.setText("Xóa");
        deleteB.setFocusable(false);
        deleteB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deleteB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        deleteB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBActionPerformed(evt);
            }
        });

        editB.setText("Chỉnh sửa");
        editB.setFocusable(false);
        editB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        editB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        editB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBActionPerformed(evt);
            }
        });

        searchB.setText("Tìm kiếm");
        searchB.setFocusable(false);
        searchB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        searchB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        searchB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBActionPerformed(evt);
            }
        });

        searchF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchFActionPerformed(evt);
            }
        });
        searchF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchFKeyReleased(evt);
            }
        });

        searchOption.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tên khách hàng", "ID đơn hàng", "Sản phẩm" }));

        filterB.setIcon(new javax.swing.ImageIcon("D:\\CMCFirstyear\\lthdt\\PROG3001-BIT230400\\PROG3001-CMCU\\death-or-live\\demo\\filterBF.png")); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchF, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(searchB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchOption, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(filterB, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 158, Short.MAX_VALUE)
                .addComponent(addB)
                .addGap(18, 18, 18)
                .addComponent(deleteB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(editB)
                .addGap(30, 30, 30))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(searchB, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addB, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteB, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editB, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchOption, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(filterB, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
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

        chartPanel.setBackground(new java.awt.Color(255, 255, 204));
        chartPanel.setLayout(new javax.swing.BoxLayout(chartPanel, javax.swing.BoxLayout.LINE_AXIS));

        pie_chart.setText("Cơ cấu doanh thu theo sản phẩm");
        pie_chart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pie_chartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout thongkeLayout = new javax.swing.GroupLayout(thongke);
        thongke.setLayout(thongkeLayout);
        thongkeLayout.setHorizontalGroup(
            thongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, thongkeLayout.createSequentialGroup()
                .addContainerGap(306, Short.MAX_VALUE)
                .addComponent(pie_chart)
                .addGap(63, 63, 63)
                .addComponent(chartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(129, 129, 129))
        );
        thongkeLayout.setVerticalGroup(
            thongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thongkeLayout.createSequentialGroup()
                .addGroup(thongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(thongkeLayout.createSequentialGroup()
                        .addGap(243, 243, 243)
                        .addComponent(pie_chart))
                    .addGroup(thongkeLayout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addComponent(chartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(163, Short.MAX_VALUE))
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

    private void orderTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderTableMouseReleased
        displayDefault();
        index = orderTable.getSelectedRow();
        cndb db =  cndb.getInstance();
        db.open();
        List<Order> allOrders = new ArrayList<>();
        if(!searchMode){
            allOrders = db.allOrders();
        }else{
            String sqlEx = db.creaSqlEx(closeSearch, option);
            allOrders = db.allOrdersFilter(sqlEx, key, closeSearch);
        }
        List<Product> allPro = db.allProducts();
        db.close();
        //System.out.print(""+ allOrders.get(index).getName_c());
        id_o_f.setText(allOrders.get(index).getId_o());
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
        Map<String, Integer> orderDetail= allOrders.get(index).getOrder_detail();
        for(Map.Entry<String,  Integer> entry: orderDetail.entrySet()){
            String id = entry.getKey();
            //System.out.print(" " + id);
            int qual = entry.getValue();
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

            Object[] row = {id_p, name, qual, price, price*qual};
            model.addRow(row);
            isEditMode = true;
        }
    }//GEN-LAST:event_orderTableMouseReleased

    private void addODActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addODActionPerformed
        DefaultTableModel m = (DefaultTableModel) orderdetail.getModel();
        m.addRow(new Object[]{null, null, null, null});      
    }//GEN-LAST:event_addODActionPerformed

    private void nut_them_spActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nut_them_spActionPerformed
        productF pro = new productF();
        pro.setVisible(true);
    }//GEN-LAST:event_nut_them_spActionPerformed

    private void txt_tim_kiem_spActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tim_kiem_spActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tim_kiem_spActionPerformed

    private void searchFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchFActionPerformed

    private void addBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBActionPerformed
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
    }//GEN-LAST:event_addBActionPerformed

    private void orderdetailMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderdetailMouseReleased


    }//GEN-LAST:event_orderdetailMouseReleased

    private void toShipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toShipActionPerformed
        if(canEditDS){
            defaultStt();
            toShip.setBackground(Color.pink);
            saveB.setEnabled(true);
            del_stt=1;
        }
    }//GEN-LAST:event_toShipActionPerformed

    private void toReceiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toReceiveActionPerformed
        if(canEditDS){
            defaultStt();
            toReceive.setBackground(Color.pink);
            saveB.setEnabled(true);
            del_stt=2;
        }
    }//GEN-LAST:event_toReceiveActionPerformed

    private void completedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_completedActionPerformed
        if(canEditDS){
            dateNow(date_d_f);
//            date_d_f.setEditable(false);
            defaultStt();
            completed.setBackground(Color.pink);
            saveB.setEnabled(true);
            del_stt=3;
        }
    }//GEN-LAST:event_completedActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        if(canEditDS){
            defaultStt();
            cancel.setBackground(Color.pink);
            saveB.setEnabled(true);
            del_stt=0;
        }
    }//GEN-LAST:event_cancelActionPerformed

    private void saveBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBActionPerformed
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
        
        //System.out.print(pay);
        cndb db = cndb.getInstance();
        db.open();
        Map<String, Integer> order_detail = new HashMap<>();
        for (int row = 0; row < orderdetail.getRowCount(); row++) {
            String keyO = (String) orderdetail.getValueAt(row, 0);
            int value = Integer.parseInt(orderdetail.getValueAt(row, 2).toString());
            //System.out.println("thunghiem "+keyO +"/"+value);
            order_detail.put(keyO, value); 
        } 
        
        if(isEditMode){
            int f = db.orderEdit(id_o, name_c, date_o, date_d, addr, pay, del_stt , order_detail);
            RefreshTables();
            if(f != -1){
                JOptionPane.showMessageDialog(rootPane, "CHỈNH SỬA SẢN PHẨM THÀNH CÔNG! ");
            }
            addDefault();
            defaultOrder();
            isEditMode=false;
        }else{
        if (date_d == null) {
            System.out.print("null");
        }
            int f = db.orderInsert(id_o, name_c, date_o, date_d, addr, pay, del_stt , order_detail);
            RefreshTables();
            if(f != -1){
                JOptionPane.showMessageDialog(rootPane, "THÊM SẢN PHẨM THÀNH CÔNG! ");
            }            
        }
        System.out.println(del_stt);
        db.close();
//        RefreshTables();
    }//GEN-LAST:event_saveBActionPerformed

    private void deleteBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBActionPerformed
        xoaDonHang(orderTable);
        
    }//GEN-LAST:event_deleteBActionPerformed

    private void editBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBActionPerformed
        addDefault();
        isEditMode = true;
    }//GEN-LAST:event_editBActionPerformed

    private void date_d_fActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_date_d_fActionPerformed
        
    }//GEN-LAST:event_date_d_fActionPerformed

    private void searchFKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchFKeyReleased
        searchMode = true;
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            searchBActionPerformed(null);
        }
        else{
            key = searchF.getText().trim();
            option = searchOption.getSelectedIndex();
            cndb db = cndb.getInstance();
            db.open();
            String sqlEx = db.creaSqlEx(false, option);
            List<Order> allOrders = db.allOrdersFilter(sqlEx, key, false);
           orderTable.setModel(allOrdersT(allOrders));
           db.close();           
        }
    }//GEN-LAST:event_searchFKeyReleased

    private void searchBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBActionPerformed

        option = searchOption.getSelectedIndex();
        searchMode = true;
        key = searchF.getText().trim();
        if(key.equals("")){
            JOptionPane.showMessageDialog(rootPane,"HÃY NHẬP VÀO GIÁ TRỊ CẦN TÌM");
        }else{
            cndb db = cndb.getInstance();
            db.open();
            String sqlEx = db.creaSqlEx(true, option);
            List<Order> allOrders = db.allOrdersFilter(sqlEx, key, true);
           orderTable.setModel(allOrdersT(allOrders));
           db.close();           
        }       
    }//GEN-LAST:event_searchBActionPerformed

    private void date_o_fKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_date_o_fKeyReleased

    }//GEN-LAST:event_date_o_fKeyReleased

    private void date_o_fKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_date_o_fKeyTyped
        errMess.setText(dateError(evt,date_o_f));
    }//GEN-LAST:event_date_o_fKeyTyped

    private void date_d_fKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_date_d_fKeyTyped
        errMess.setText(dateError(evt,date_d_f));
        defaultStt();       
        if(!date_d_f.getText().equals("")){
            canEditDS = false;
            completed.setBackground(Color.pink);
        }else{
            canEditDS = true;
        }

        saveB.setEnabled(true);
        del_stt=3;
    }//GEN-LAST:event_date_d_fKeyTyped

    private void pie_chartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pie_chartActionPerformed
        cndb db = cndb.getInstance();
        db.open();
        DefaultPieDataset a= db.salesReportsdts("03", "2024",true, false);
        System.out.println(a.getValue(0));
 
        ChartPanel chartP = new ChartPanel(createPieChart(a, "Thử nghiệm"));
        chartPanel.add(chartP);
        chartPanel.revalidate();
        db.close();
    }//GEN-LAST:event_pie_chartActionPerformed


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
    private javax.swing.JButton addB;
    private javax.swing.JButton addOD;
    private javax.swing.JTextField addr_f;
    private javax.swing.JButton cancel;
    private javax.swing.JPanel chartPanel;
    private javax.swing.JButton completed;
    private javax.swing.JTextField date_d_f;
    private javax.swing.JTextField date_o_f;
    private javax.swing.JButton deleteB;
    private javax.swing.JPanel donhang;
    private javax.swing.JButton editB;
    private javax.swing.JLabel errMess;
    private javax.swing.JButton filterB;
    private javax.swing.JTextField id_o_f;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextField name_c_f;
    private javax.swing.JButton nut_them_sp;
    private javax.swing.JButton nut_tim_kiem_sp;
    private javax.swing.JTable orderTable;
    private javax.swing.JTable orderdetail;
    private javax.swing.JPanel panel_sp;
    private javax.swing.JComboBox<String> pay_type;
    private javax.swing.JButton pie_chart;
    private javax.swing.JPanel sanpham;
    private javax.swing.JButton saveB;
    private javax.swing.JButton searchB;
    private javax.swing.JTextField searchF;
    private javax.swing.JComboBox<String> searchOption;
    private javax.swing.JPanel thongke;
    private javax.swing.JButton toReceive;
    private javax.swing.JButton toShip;
    private javax.swing.JTextField txt_tim_kiem_sp;
    // End of variables declaration//GEN-END:variables
}