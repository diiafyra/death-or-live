/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.formdev.flatlaf.FlatLightLaf;
import control.Handler;
import control.cndb;
import org.jfree.chart.ChartPanel;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static control.Handler.*;
import models.OrderInfo.Order;
import models.OrderInfo.Order_detail;
import models.Panel.HighlightLabel;
import models.Panel.HighlightPanel;
import models.Panel.RoundedPanel;
import models.Panel.proPanel;
import models.ProductInfo.Product;
import models.ProductInfo.ProductEx;
/**
 *
 * @author DELL
 */
public class MainFr extends javax.swing.JFrame {
    public MainFr() {
        initComponents();
        //Giao diện lớn bằng cả màn hình 
        setExtendedState(MainFr.MAXIMIZED_BOTH);
        tro_lai.setVisible(false);
        b_filter_order.setVisible(false);
        b_save_edit.setVisible(false);

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
    int bestSellerCutoff;
    int lowQuantityCutoff;
    int highQuantityCutoff;
    String month = null;
    String year = null;
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
        b_save_add.setEnabled(true);
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
        b_save_add.setEnabled(false);
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
        int newRow = orderdetail.getRowCount() - 1;
        orderdetail.setRowSelectionInterval(newRow, newRow);
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
                    table.setValueAt(selectedProduct.getPrice_s(), selectedRow, 5);
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
        int totalC=0;
        cndb db =  cndb.getInstance();
        db.open();
        List<Product> allPro = db.allProducts();
        for(Product pro: allPro){
            totalC += pro.getCapital();
        }
        List<proPanel> proPanels = allProP(allPro);
        panel_sp.removeAll();
        for(proPanel proP: proPanels){
            panel_sp.add(proP);
        }
        db.close();
        l_totalCapital.setText(totalC + " VND");
    }

    public void setOverview(){
        month = (String) cb_month.getSelectedItem();
        year = (String) cb_year.getSelectedItem();
        cndb db = cndb.getInstance();
        try {
            db.open();
            int shipOrder = db.OrdersFilterByDS(month, year, 1).size();
            int receiveOrder = db.OrdersFilterByDS(month, year, 2).size();
            int completedOrder = db.OrdersFilterByDS(month, year, 3).size();
            int cancelOrder = db.OrdersFilterByDS(month, year, 4).size();
            l_toShip.setText(shipOrder+"");
            l_toReceive.setText(receiveOrder+"");
            l_completed.setText(completedOrder+"");
            l_cancel.setText(cancelOrder+"");

            List<Product> allPro = db.allProducts();
            List<ProductEx> proExs = db.salesReports(month, year);
            int cNoSalePro = noSalePro(allPro, proExs).size();
            int cBestSalePro = bestSalePro(allPro, proExs, bestSellerCutoff).size();
            int cLowStock = stockProAna(allPro, lowQuantityCutoff, 1).size();
            int cHighStock = stockProAna(allPro, highQuantityCutoff, 2).size();
            int cNoStock = stockProAna(allPro, lowQuantityCutoff, 0).size();
            l_noQualPro.setText(cNoStock+"");
            l_lowQualPro.setText(cLowStock+"");
            l_highQualPro.setText(cHighStock+"");
            l_bestSalePro.setText(cBestSalePro+"");
            l_noSalePro.setText(cNoSalePro+"");
        } catch (Exception e) {
            // Xử lý ngoại lệ
            e.printStackTrace();
        } finally {
            // Luôn đảm bảo rằng cơ sở dữ liệu được đóng
            try {
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    public void setTotalProfitChart(){
        String year = (String) cb_year1.getSelectedItem();
        String qarter = (String) cb_qarter.getSelectedItem();
        boolean yearMode;
        if(cb_yearMode.getSelectedItem().toString().equals("Theo Quý")){
            yearMode = false;
        } else{
            yearMode = true;
        }
        cndb db = cndb.getInstance();
        db.open();
        DefaultCategoryDataset totalProfitDS = db.salesTotalProfitdts(year, yearMode, qarter);
//        for(int i = 0; i<12; i++){
//        System.out.println(totalProfitDS.getValue(0, 2) + "thunghiem");
//        }
        ChartPanel chartP;
        chartP = new ChartPanel(createLineChart("Báo Cáo Lợi Nhuận", totalProfitDS));
        p_categoryChart.removeAll();
        p_categoryChart.add(chartP);
        p_categoryChart.revalidate();
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
        tro_lai = new javax.swing.JButton();
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
        b_save_add = new javax.swing.JButton();
        b_save_edit = new javax.swing.JButton();
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
        jPanel1 = new RoundedPanel();
        jPanel4 = new HighlightPanel();
        l_toShip = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel7 = new HighlightPanel();
        l_toReceive = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel8 = new HighlightPanel();
        l_completed = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel10 = new HighlightPanel();
        l_cancel = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        cb_month = new javax.swing.JComboBox<>();
        cb_year = new javax.swing.JComboBox<>(getYear().toArray(new String[0]));
        jPanel16 = new RoundedPanel();
        jPanel11 = new HighlightPanel();
        l_noQualPro = new HighlightLabel();
        l_lowQualPro = new HighlightLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel12 = new HighlightPanel();
        l_highQualPro = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel13 = new HighlightPanel();
        l_noSalePro = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel14 = new HighlightPanel();
        l_bestSalePro = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel18 = new RoundedPanel();
        l_totalCapital = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        p_analysis_product = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        profit_graph = new RoundedPanel();
        loss_graph = new RoundedPanel();
        cb_month1 = new javax.swing.JComboBox<>();
        cb_year2 = new javax.swing.JComboBox<>(getYear().toArray(new String[0]));
        p_analysis_profit = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        cb_year1 = new javax.swing.JComboBox<>(getYear().toArray(new String[0]));
        cb_yearMode = new javax.swing.JComboBox<>();
        cb_qarter = new javax.swing.JComboBox<>();
        p_categoryChart = new RoundedPanel();

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
        nut_tim_kiem_sp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nut_tim_kiem_spActionPerformed(evt);
            }
        });

        txt_tim_kiem_sp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tim_kiem_spActionPerformed(evt);
            }
        });
        txt_tim_kiem_sp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_tim_kiem_spKeyTyped(evt);
            }
        });

        tro_lai.setText("Home");
        tro_lai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tro_laiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nut_them_sp)
                .addGap(192, 192, 192)
                .addComponent(tro_lai)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nut_tim_kiem_sp)
                .addGap(18, 18, 18)
                .addComponent(txt_tim_kiem_sp, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(tro_lai)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout sanphamLayout = new javax.swing.GroupLayout(sanpham);
        sanpham.setLayout(sanphamLayout);
        sanphamLayout.setHorizontalGroup(
            sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sanphamLayout.createSequentialGroup()
                .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sanphamLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jScrollPane2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sanphamLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        sanphamLayout.setVerticalGroup(
            sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sanphamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE))
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
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 859, Short.MAX_VALUE)
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
                            .addComponent(pay_type, 0, 699, Short.MAX_VALUE)
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
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
        );

        errMess.getAccessibleContext().setAccessibleName("errMess");

        b_save_add.setText("Thêm Đơn Hàng");
        b_save_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_save_addActionPerformed(evt);
            }
        });

        b_save_edit.setText("Lưu Chỉnh Sửa");
        b_save_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_save_editActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(b_save_edit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(b_save_add)))
                .addGap(30, 30, 30))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b_save_add)
                    .addComponent(b_save_edit))
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

        b_filter_order.setText("Home");
        b_filter_order.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        b_filter_order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_filter_orderActionPerformed(evt);
            }
        });

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
                .addComponent(b_filter_order, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
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

        jScrollPane4.getVerticalScrollBar().setUnitIncrement(50);
        jScrollPane4.getVerticalScrollBar().setBlockIncrement(100);

        p_analysis.setPreferredSize(new java.awt.Dimension(1500, 2160));
        p_analysis.setLayout(new java.awt.GridLayout(0, 1));

        p_overview.setBackground(new java.awt.Color(255, 230, 191));
        p_overview.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        p_overview.setPreferredSize(new java.awt.Dimension(848, 646));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 51, 0));
        jLabel9.setText("Tổng Quan");

        jPanel1.setPreferredSize(new java.awt.Dimension(1500, 201));
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        jPanel4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cndb db =  cndb.getInstance();
                db.open();
                List<Order> allOrders = db.OrdersFilterByDS(month, year, 1);
                //System.out.print(allOrders.get(22).getDate_o());
                table_order.setModel(allOrdersT(allOrders));
                db.close();;
                MENU.setSelectedIndex(1);
                b_filter_order.setVisible(true);
            }
        });
        jPanel4.setLayout(new java.awt.GridLayout(0, 1, 0, 25));

        l_toShip.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        l_toShip.setText("pp");
        l_toShip.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        l_toShip.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel4.add(l_toShip);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Chờ Lấy Hàng");
        jLabel11.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel4.add(jLabel11);

        jPanel1.add(jPanel4);

        jPanel7.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cndb db =  cndb.getInstance();
                db.open();
                List<Order> allOrders = db.OrdersFilterByDS(month, year, 2);
                //System.out.print(allOrders.get(22).getDate_o());
                table_order.setModel(allOrdersT(allOrders));
                db.close();;
                MENU.setSelectedIndex(1);
                b_filter_order.setVisible(true);
            }
        });
        jPanel7.setLayout(new java.awt.GridLayout(0, 1, 0, 25));

        l_toReceive.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        l_toReceive.setText("pp");
        l_toReceive.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        l_toReceive.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel7.add(l_toReceive);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Đang Giao");
        jLabel14.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel14.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel7.add(jLabel14);

        jPanel1.add(jPanel7);

        jPanel8.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cndb db =  cndb.getInstance();
                db.open();
                List<Order> allOrders = db.OrdersFilterByDS(month, year, 3);
                //System.out.print(allOrders.get(22).getDate_o());
                table_order.setModel(allOrdersT(allOrders));
                db.close();;
                MENU.setSelectedIndex(1);
                b_filter_order.setVisible(true);

            }
        });
        jPanel8.setLayout(new java.awt.GridLayout(0, 1, 0, 25));

        l_completed.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        l_completed.setText("pp");
        l_completed.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        l_completed.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel8.add(l_completed);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Hoàn Thành");
        jLabel16.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel16.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel8.add(jLabel16);

        jPanel1.add(jPanel8);

        jPanel10.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cndb db =  cndb.getInstance();
                db.open();
                List<Order> allOrders = db.OrdersFilterByDS(month, year, 0);
                //System.out.print(allOrders.get(22).getDate_o());
                table_order.setModel(allOrdersT(allOrders));
                db.close();;
                MENU.setSelectedIndex(1);
                b_filter_order.setVisible(true);

            }
        });
        jPanel10.setLayout(new java.awt.GridLayout(0, 1, 0, 25));

        l_cancel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        l_cancel.setText("pp");
        l_cancel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        l_cancel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel10.add(l_cancel);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Đã Hủy");
        jLabel18.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel18.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel18.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel10.add(jLabel18);

        jPanel1.add(jPanel10);

        cb_month.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        cb_month.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_monthActionPerformed(evt);
            }
        });

        cb_year.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_yearActionPerformed(evt);
            }
        });

        jPanel16.setLayout(new java.awt.GridLayout(1, 0));

        jPanel11.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cndb db =  cndb.getInstance();
                db.open();
                List<Product> allPro = db.allProducts();
                List<Product> lowQualPro = stockProAna(allPro, lowQuantityCutoff, 1);
                List<Product> noQualPro = stockProAna(allPro, lowQuantityCutoff, 0);
                lowQualPro.addAll(noQualPro);
                List<proPanel> proPanels = allProP(lowQualPro);
                panel_sp.removeAll();
                for(proPanel proP: proPanels){
                    panel_sp.add(proP);
                }
                db.close();
                tro_lai.setVisible(true);
                MENU.setSelectedIndex(0);

            }
        });

        l_noQualPro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cndb db =  cndb.getInstance();
                db.open();
                List<Product> allPro = db.allProducts();
                List<Product> noQualPro = stockProAna(allPro, lowQuantityCutoff, 0);
                List<proPanel> proPanels = allProP(noQualPro);
                panel_sp.removeAll();

                for(proPanel proP: proPanels){
                    panel_sp.add(proP);
                }
                db.close();
                tro_lai.setVisible(true);
                MENU.setSelectedIndex(0);

            }
        });
        l_noQualPro.setForeground(new java.awt.Color(255, 0, 0));
        l_noQualPro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        l_noQualPro.setText("pp");
        l_noQualPro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        l_lowQualPro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cndb db =  cndb.getInstance();
                db.open();
                List<Product> allPro = db.allProducts();
                List<Product> lowQualPro = stockProAna(allPro, lowQuantityCutoff, 1);
                List<proPanel> proPanels = allProP(lowQualPro);
                panel_sp.removeAll();
                for(proPanel proP: proPanels){
                    panel_sp.add(proP);
                }
                db.close();
                tro_lai.setVisible(true);
                MENU.setSelectedIndex(0);

            }
        });
        l_lowQualPro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        l_lowQualPro.setText("pp");

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("SP Còn Ít");
        jLabel20.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel20.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel20.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(l_noQualPro, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(l_lowQualPro, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(jLabel20)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(l_noQualPro, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(l_lowQualPro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel20))
        );

        jPanel16.add(jPanel11);

        jPanel12.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cndb db =  cndb.getInstance();
                db.open();
                List<Product> allPro = db.allProducts();
                List<Product> highQualPro = stockProAna(allPro, lowQuantityCutoff, 2);
                List<proPanel> proPanels = allProP(highQualPro);
                panel_sp.removeAll();
                for(proPanel proP: proPanels){
                    panel_sp.add(proP);
                }
                db.close();
                tro_lai.setVisible(true);
                MENU.setSelectedIndex(0);

            }
        });
        jPanel12.setLayout(new java.awt.GridLayout(0, 1, 0, 25));

        l_highQualPro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        l_highQualPro.setText("pp");
        l_highQualPro.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        l_highQualPro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel12.add(l_highQualPro);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("SP Tồn Nhiều");
        jLabel22.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel22.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel22.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel12.add(jLabel22);

        jPanel16.add(jPanel12);

        jPanel13.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cndb db =  cndb.getInstance();
                db.open();
                List<Product> allPro = db.allProducts();
                List<ProductEx> proExs = db.salesReports(month, year);
                List<Product> noSalePro = noSalePro(allPro, proExs);
                List<proPanel> proPanels = allProP(noSalePro);
                panel_sp.removeAll();
                for(proPanel proP: proPanels){
                    panel_sp.add(proP);
                }
                db.close();
                MENU.setSelectedIndex(0);
                tro_lai.setVisible(true);
            }
        });
        jPanel13.setLayout(new java.awt.GridLayout(0, 1, 0, 25));

        l_noSalePro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        l_noSalePro.setText("pp");
        l_noSalePro.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        l_noSalePro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel13.add(l_noSalePro);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("SP Không Bán Được");
        jLabel24.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel24.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel24.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel13.add(jLabel24);

        jPanel16.add(jPanel13);

        jPanel14.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cndb db =  cndb.getInstance();
                db.open();
                List<Product> allPro = db.allProducts();
                List<ProductEx> proExs = db.salesReports(month, year);
                List<Product> bestSalePro = bestSalePro(allPro, proExs, bestSellerCutoff);
                List<proPanel> proPanels = allProP(bestSalePro);
                panel_sp.removeAll();
                for(proPanel proP: proPanels){
                    panel_sp.add(proP);
                }
                db.close();
                MENU.setSelectedIndex(0);
                tro_lai.setVisible(true);

            }
        });
        jPanel14.setLayout(new java.awt.GridLayout(0, 1, 0, 25));

        l_bestSalePro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        l_bestSalePro.setText("pp");
        l_bestSalePro.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        l_bestSalePro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel14.add(l_bestSalePro);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("SP Bán Chạy");
        jLabel26.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel26.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel26.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel14.add(jLabel26);

        jPanel16.add(jPanel14);

        jPanel18.setLayout(new java.awt.GridLayout(0, 1, 0, 30));

        l_totalCapital.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        l_totalCapital.setText("xxx");
        l_totalCapital.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel18.add(l_totalCapital);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Tổng Vốn Hiện Tại");
        jLabel12.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel18.add(jLabel12);

        jLabel27.setText("ĐƠN HÀNG");

        jLabel28.setText("SẢN PHẨM");

        javax.swing.GroupLayout p_overviewLayout = new javax.swing.GroupLayout(p_overview);
        p_overview.setLayout(p_overviewLayout);
        p_overviewLayout.setHorizontalGroup(
            p_overviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_overviewLayout.createSequentialGroup()
                .addGap(148, 148, 148)
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(165, 165, 165))
            .addGroup(p_overviewLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(p_overviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(p_overviewLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(p_overviewLayout.createSequentialGroup()
                        .addGroup(p_overviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(jLabel28)
                            .addGroup(p_overviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGroup(p_overviewLayout.createSequentialGroup()
                                    .addComponent(cb_month, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(39, 39, 39)
                                    .addComponent(cb_year, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)))
                        .addGap(35, 35, 35))))
        );
        p_overviewLayout.setVerticalGroup(
            p_overviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_overviewLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(p_overviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb_month, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cb_year, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addComponent(jLabel27)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jLabel28)
                .addGap(32, 32, 32)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cb_month.getAccessibleContext().setAccessibleName("");
        cb_month.getAccessibleContext().setAccessibleDescription("");

        p_analysis.add(p_overview);

        p_analysis_product.setBackground(new java.awt.Color(255, 230, 191));
        p_analysis_product.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 51, 0));
        jLabel8.setText("Thống Kê Doanh Thu Theo Từng Sản Phẩm");

        profit_graph.setLayout(new javax.swing.BoxLayout(profit_graph, javax.swing.BoxLayout.LINE_AXIS));

        loss_graph.setLayout(new javax.swing.BoxLayout(loss_graph, javax.swing.BoxLayout.LINE_AXIS));

        cb_month1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        cb_month1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_month1ActionPerformed(evt);
            }
        });

        cb_year2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_year2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout p_analysis_productLayout = new javax.swing.GroupLayout(p_analysis_product);
        p_analysis_product.setLayout(p_analysis_productLayout);
        p_analysis_productLayout.setHorizontalGroup(
            p_analysis_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_analysis_productLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(p_analysis_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(p_analysis_productLayout.createSequentialGroup()
                        .addComponent(profit_graph, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE)
                        .addGap(80, 80, 80)
                        .addComponent(loss_graph, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
                        .addGap(32, 32, 32))
                    .addGroup(p_analysis_productLayout.createSequentialGroup()
                        .addGroup(p_analysis_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(p_analysis_productLayout.createSequentialGroup()
                                .addComponent(cb_month1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(cb_year2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel8))
                        .addGap(33, 33, 33))))
        );
        p_analysis_productLayout.setVerticalGroup(
            p_analysis_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_analysis_productLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(p_analysis_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(loss_graph, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(p_analysis_productLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(21, 21, 21)
                        .addGroup(p_analysis_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cb_month1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cb_year2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addComponent(profit_graph, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 266, Short.MAX_VALUE))
        );

        p_analysis.add(p_analysis_product);

        p_analysis_profit.setBackground(new java.awt.Color(255, 230, 191));
        p_analysis_profit.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 51, 0));
        jLabel10.setText("Thống Kê Doanh Thu Theo Tháng");

        cb_year1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_year1ActionPerformed(evt);
            }
        });

        cb_yearMode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Theo Quý", "Theo Năm" }));
        cb_yearMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_yearModeActionPerformed(evt);
            }
        });

        cb_qarter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Quý 1", "Quý 2", "Quý 3", "Quý 4" }));
        cb_qarter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_qarterActionPerformed(evt);
            }
        });

        p_categoryChart.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout p_analysis_profitLayout = new javax.swing.GroupLayout(p_analysis_profit);
        p_analysis_profit.setLayout(p_analysis_profitLayout);
        p_analysis_profitLayout.setHorizontalGroup(
            p_analysis_profitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_analysis_profitLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(p_analysis_profitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(p_categoryChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(p_analysis_profitLayout.createSequentialGroup()
                        .addGroup(p_analysis_profitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addGroup(p_analysis_profitLayout.createSequentialGroup()
                                .addComponent(cb_yearMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cb_qarter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cb_year1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 1122, Short.MAX_VALUE)))
                .addGap(49, 49, 49))
        );
        p_analysis_profitLayout.setVerticalGroup(
            p_analysis_profitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_analysis_profitLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel10)
                .addGap(38, 38, 38)
                .addGroup(p_analysis_profitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb_year1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cb_yearMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cb_qarter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addComponent(p_categoryChart, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(187, Short.MAX_VALUE))
        );

        p_analysis.add(p_analysis_profit);

        jScrollPane4.setViewportView(p_analysis);

        javax.swing.GroupLayout thongkeLayout = new javax.swing.GroupLayout(thongke);
        thongke.setLayout(thongkeLayout);
        thongkeLayout.setHorizontalGroup(
            thongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 958, Short.MAX_VALUE)
        );
        thongkeLayout.setVerticalGroup(
            thongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
        );

        MENU.addTab("THỐNG KÊ", thongke);

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
        List<Order_detail> orderDetail= allOrders.get(index).getOrder_detail();
        for(Order_detail od : orderDetail){
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
        int newRow = orderdetail.getRowCount() - 1;
        orderdetail.setRowSelectionInterval(newRow, newRow);
    }//GEN-LAST:event_addODActionPerformed

    private void txt_search_orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_search_orderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_search_orderActionPerformed

    private void b_addOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_addOrderActionPerformed
        addDefault();
        defaultOrder();
        b_save_add.setVisible(true);
        b_save_edit.setVisible(false);
        date_o_f.setText(getCurrentDate());
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
            date_d_f.setText("");
            defaultStt();
            toShip.setBackground(Color.pink);
            b_save_add.setEnabled(true);
            del_stt=1;

        }
    }//GEN-LAST:event_toShipActionPerformed

    private void toReceiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toReceiveActionPerformed
        if(canEditDelStt){
            date_d_f.setText("");
            defaultStt();
            toReceive.setBackground(Color.pink);
            b_save_add.setEnabled(true);
            del_stt=2;

        }
    }//GEN-LAST:event_toReceiveActionPerformed

    private void completedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_completedActionPerformed
        if(canEditDelStt){
            date_d_f.setText(getCurrentDate());
//            date_d_f.setEditable(false);
            defaultStt();
            completed.setBackground(Color.pink);
            b_save_add.setEnabled(true);
            del_stt=3;

        }
    }//GEN-LAST:event_completedActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        if(canEditDelStt){
            date_d_f.setText("");
            defaultStt();
            cancel.setBackground(Color.pink);
            b_save_add.setEnabled(true);
            del_stt=0;

        }
    }//GEN-LAST:event_cancelActionPerformed

    private void b_save_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_save_addActionPerformed
        boolean check = true;
        if(date_o_f.getText().toString().length()!=10){
            JOptionPane.showMessageDialog(rootPane, "Nhập đúng định dạng ngày tháng");
        }else{
            String id_o = id_o_f.getText().trim();
            String name_c = name_c_f.getText().trim();
            String date_o = Handler.getDate(date_o_f);
            String date_d = Handler.getDate(date_d_f);

            String addr =addr_f.getText().trim();
            String pay = (String) pay_type.getSelectedItem();

            List<Order_detail> order_detail = new ArrayList<>();
            for (int row = 0; row < orderdetail.getRowCount(); row++) {
                String id_p = (String) orderdetail.getValueAt(row, 0);
                String qualS = (String) orderdetail.getValueAt(row, 2);
                String discountS = (String) orderdetail.getValueAt(row, 5);

                if (id_p == null || qualS == null || discountS == null) {
                    check = false;
                    break;
                } else {
                    int qual = Integer.parseInt(qualS);

                    int discount = (int) orderdetail.getValueAt(row, 3);
                    if (discountS != null) {
                        discount = Integer.parseInt(discountS);
                    }

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
                        for (Order_detail od : order_detail) {
                            if (od.getId_p().equals(id_p) && od.getDiscount() == discount) {
                                // Nếu đã tồn tại OrderInfo với cùng id_p và discount, cập nhật qual
                                od.setQuantity(od.getQuantity() + qual);
                                found = true;
                                break;
                            }
                        }
                        // Nếu không tìm thấy OrderInfo với cùng id_p và discount, thêm mới vào danh sách
                        if (!found) {
                            order_detail.add(new Order_detail(id_p, qual, discount));
                        }
                    }
                }
            }
            deStock =0;

            if(check){
                cndb db = cndb.getInstance();
                db.open();           
                int f = db.orderInsert(id_o, name_c, date_o, date_d, addr, pay, del_stt , order_detail);
                db.close();
                 RefreshTables();
                 if(f != -1){
                     JOptionPane.showMessageDialog(rootPane, "THÊM ĐƠN HÀNG THÀNH CÔNG! ");
                 } else{
                     JOptionPane.showMessageDialog(rootPane, "LỖI");
                 }
                del_stt = 1;
            } else {
                JOptionPane.showMessageDialog(rootPane, "KHÔNG ĐƯỢC ĐỂ TRỐNG THÔNG TIN ĐƠN HÀNG ");
            }
        }
    }//GEN-LAST:event_b_save_addActionPerformed

    private void b_delete_orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_delete_orderActionPerformed
        int selectedRow = table_order.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel tableModel = (DefaultTableModel) table_order.getModel();
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
        
    }//GEN-LAST:event_b_delete_orderActionPerformed

    private void b_edit_orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_edit_orderActionPerformed
        if(table_order.getSelectedRow()==-1){
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 đơn hàng để chỉnh sửa");
        } else{
            addDefault();
            b_save_add.setVisible(false);
            b_save_edit.setVisible(true);
            isEditMode = true;
        }
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

        b_save_add.setEnabled(true);
        del_stt=3;
    }//GEN-LAST:event_date_d_fKeyTyped

    private void cb_monthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_monthActionPerformed
        setOverview();
    }//GEN-LAST:event_cb_monthActionPerformed

    private void cb_yearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_yearActionPerformed

        setOverview();
    }//GEN-LAST:event_cb_yearActionPerformed

    private void cb_year1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_year1ActionPerformed
        setTotalProfitChart();
    }//GEN-LAST:event_cb_year1ActionPerformed

    private void cb_yearModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_yearModeActionPerformed
        setTotalProfitChart();
    }//GEN-LAST:event_cb_yearModeActionPerformed

    private void cb_qarterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_qarterActionPerformed
        setTotalProfitChart();
    }//GEN-LAST:event_cb_qarterActionPerformed

    private void cb_month1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_month1ActionPerformed
        profit_graph.removeAll();
        loss_graph.removeAll();
        cndb db = cndb.getInstance();
        db.open();

        String selectedMonth = (String) cb_month1.getSelectedItem();
        String selectedYear = (String) cb_year2.getSelectedItem();

        DefaultPieDataset year_profit = db.salesReportsdts(selectedMonth,selectedYear, false, true);
        System.out.println(year_profit.getValue(0));
        ChartPanel chart_profit = new ChartPanel(createPieChart(year_profit, "Lãi"));
        profit_graph.add(chart_profit);
        profit_graph.revalidate();
        DefaultPieDataset year_loss = db.salesReportsdts(selectedMonth,selectedYear, false, false);
        System.out.println(year_loss.getValue(0));
        ChartPanel chart_loss = new ChartPanel(createPieChart(year_loss, "Lỗ"));
        loss_graph.add(chart_loss);
        loss_graph.revalidate();

        db.close();
    }//GEN-LAST:event_cb_month1ActionPerformed

    private void cb_year2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_year2ActionPerformed
        
        profit_graph.removeAll();
        loss_graph.removeAll();
        cndb db = cndb.getInstance();
        db.open();

        String selectedMonth = (String) cb_month1.getSelectedItem();
        String selectedYear = (String) cb_year2.getSelectedItem();

        DefaultPieDataset year_profit = db.salesReportsdts(selectedMonth,selectedYear, true, true);
        System.out.println(year_profit.getValue(0) + "abcsd");
        ChartPanel chart_profit = new ChartPanel(createPieChart(year_profit, "Lãi"));
        profit_graph.add(chart_profit);
        profit_graph.revalidate();
        DefaultPieDataset year_loss = db.salesReportsdts(selectedMonth, selectedYear, true, false);
//        System.out.println(year_loss.getValue(1)+ "xyz");
        ChartPanel chart_loss = new ChartPanel(createPieChart(year_loss, "Lỗ"));
        loss_graph.add(chart_loss);
        loss_graph.revalidate();

        db.close();
    }//GEN-LAST:event_cb_year2ActionPerformed

    private void nut_them_spActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nut_them_spActionPerformed
        productF pro = new productF();
        pro.setVisible(true);
    }//GEN-LAST:event_nut_them_spActionPerformed

    private void nut_tim_kiem_spActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nut_tim_kiem_spActionPerformed
        tro_lai.setVisible(true);
        panel_sp.removeAll();
        cndb db =  cndb.getInstance();
        db.open();
        List<Product> allPro = db.allProducts();
        List<proPanel> proPanels = allProP(allPro);
        String abc=txt_tim_kiem_sp.getText();
        System.out.println(abc);

        for( JPanel a : proPanels){
            if(db.tim_kiem_san_pham(a, abc)== 1){
                System.out.println("abafdsa");
                System.out.println(db.tim_kiem_san_pham(a, abc));
                panel_sp.add(a);
            }
        }
        panel_sp.revalidate();
        panel_sp.repaint();
        db.close();
    }//GEN-LAST:event_nut_tim_kiem_spActionPerformed

    private void txt_tim_kiem_spActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tim_kiem_spActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tim_kiem_spActionPerformed

    private void txt_tim_kiem_spKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_tim_kiem_spKeyTyped
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            tro_lai.setVisible(true);
            panel_sp.removeAll();
            cndb db =  cndb.getInstance();
            db.open();
            List<Product> allPro = db.allProducts();
            List<proPanel> proPanels = allProP(allPro);
            String abc=txt_tim_kiem_sp.getText();
            System.out.println(abc);

            for( JPanel a : proPanels){
                if(db.tim_kiem_san_pham(a, abc)== 1){
                    System.out.println("abafdsa");
                    System.out.println(db.tim_kiem_san_pham(a, abc));
                    panel_sp.add(a);
                }
            }
            panel_sp.revalidate();
            panel_sp.repaint();
            db.close();
        }
    }//GEN-LAST:event_txt_tim_kiem_spKeyTyped

    private void tro_laiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tro_laiActionPerformed
        RefreshProList();
        tro_lai.setVisible(false);
    }//GEN-LAST:event_tro_laiActionPerformed

    private void b_filter_orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_filter_orderActionPerformed
        RefreshTables();
        b_filter_order.setVisible(false);
    }//GEN-LAST:event_b_filter_orderActionPerformed

    private void b_save_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_save_editActionPerformed
        boolean check = true;
        if(date_o_f.getText().toString().length()!=10){
            JOptionPane.showMessageDialog(rootPane, "Nhập đúng định dạng ngày tháng");
        }else{
            String id_o = id_o_f.getText().trim();
            String name_c = name_c_f.getText().trim();
            String date_o = Handler.getDate(date_o_f);
            String date_d = Handler.getDate(date_d_f);

            String addr =addr_f.getText().trim();
            String pay = (String) pay_type.getSelectedItem();

            List<Order_detail> order_detail = new ArrayList<>();
            for (int row = 0; row < orderdetail.getRowCount(); row++) {
                String id_p = (String) orderdetail.getValueAt(row, 0);
                String qualS = (String) orderdetail.getValueAt(row, 2);
                String discountS = (String) orderdetail.getValueAt(row, 5);

                if (id_p == null || qualS == null || discountS == null) {
                    check = false;
                    break;
                } else {
                    int qual = Integer.parseInt(qualS);

                    int discount = (int) orderdetail.getValueAt(row, 3);
                    if (discountS != null) {
                        discount = Integer.parseInt(discountS);
                    }

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
                        for (Order_detail od : order_detail) {
                            if (od.getId_p().equals(id_p) && od.getDiscount() == discount) {
                                // Nếu đã tồn tại OrderInfo với cùng id_p và discount, cập nhật qual
                                od.setQuantity(od.getQuantity() + qual);
                                found = true;
                                break;
                            }
                        }
                        // Nếu không tìm thấy OrderInfo với cùng id_p và discount, thêm mới vào danh sách
                        if (!found) {
                            order_detail.add(new Order_detail(id_p, qual, discount));
                        }
                    }
                }
            }
            deStock = 0;

            if(check){
                cndb db = cndb.getInstance();
                db.open();
                int f = db.orderEdit(id_o, name_c, date_o, date_d, addr, pay, del_stt , order_detail);
                db.close();
                RefreshTables();
                if(f != -1){
                    JOptionPane.showMessageDialog(rootPane, "CHỈNH SỬA ĐƠN HÀNG THÀNH CÔNG! ");
                    addDefault();
                    defaultOrder();
                    isEditMode=false;
                    b_save_edit.setVisible(false);
                }else{
                    JOptionPane.showMessageDialog(rootPane, "LỖI");
                }
            }else{
                JOptionPane.showMessageDialog(rootPane, "KHÔNG ĐƯỢC DỂ TRỐNG THÔNG TIN ĐƠN HÀNG ");
            }
        }
            
    }//GEN-LAST:event_b_save_editActionPerformed


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
    private javax.swing.JButton b_delete_order;
    private javax.swing.JButton b_edit_order;
    private javax.swing.JButton b_filter_order;
    private javax.swing.JButton b_save_add;
    private javax.swing.JButton b_save_edit;
    private javax.swing.JButton b_search_order;
    private javax.swing.JButton cancel;
    private javax.swing.JComboBox<String> cb_month;
    private javax.swing.JComboBox<String> cb_month1;
    private javax.swing.JComboBox<String> cb_qarter;
    private javax.swing.JComboBox<String> cb_search_option;
    private javax.swing.JComboBox<String> cb_year;
    private javax.swing.JComboBox<String> cb_year1;
    private javax.swing.JComboBox<String> cb_year2;
    private javax.swing.JComboBox<String> cb_yearMode;
    private javax.swing.JButton completed;
    private javax.swing.JTextField date_d_f;
    private javax.swing.JTextField date_o_f;
    private javax.swing.JPanel donhang;
    private javax.swing.JLabel errMess;
    private javax.swing.JTextField id_o_f;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel18;
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
    private javax.swing.JLabel l_bestSalePro;
    private javax.swing.JLabel l_cancel;
    private javax.swing.JLabel l_completed;
    private javax.swing.JLabel l_highQualPro;
    private javax.swing.JLabel l_lowQualPro;
    private javax.swing.JLabel l_noQualPro;
    private javax.swing.JLabel l_noSalePro;
    private javax.swing.JLabel l_toReceive;
    private javax.swing.JLabel l_toShip;
    private javax.swing.JLabel l_totalCapital;
    private javax.swing.JPanel loss_graph;
    private javax.swing.JTextField name_c_f;
    private javax.swing.JButton nut_them_sp;
    private javax.swing.JButton nut_tim_kiem_sp;
    private javax.swing.JTable orderdetail;
    private javax.swing.JPanel p_analysis;
    private javax.swing.JPanel p_analysis_product;
    private javax.swing.JPanel p_analysis_profit;
    private javax.swing.JPanel p_categoryChart;
    private javax.swing.JPanel p_overview;
    private javax.swing.JPanel panel_sp;
    private javax.swing.JComboBox<String> pay_type;
    private javax.swing.JPanel profit_graph;
    private javax.swing.JPanel sanpham;
    private javax.swing.JTable table_order;
    private javax.swing.JPanel thongke;
    private javax.swing.JButton toReceive;
    private javax.swing.JButton toShip;
    private javax.swing.JButton tro_lai;
    private javax.swing.JTextField txt_search_order;
    private javax.swing.JTextField txt_tim_kiem_sp;
    // End of variables declaration//GEN-END:variables
}