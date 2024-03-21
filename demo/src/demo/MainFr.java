/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;
import com.formdev.flatlaf.FlatLightLaf;
import static demo.Handler.allOrdersT;
import static demo.Handler.allProP;
import java.awt.Color;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import structure.Order;
import structure.Product;
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
        List<Order> allOders = db.allOrders();
        jTable2.setModel(allOrdersT(allOders));
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
        jTable2 = new javax.swing.JTable();
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
        jButton8 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        addB = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jTextField4 = new javax.swing.JTextField();
        thongke = new javax.swing.JPanel();
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
                .addContainerGap(51, Short.MAX_VALUE))
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

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTable2MouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable2);

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

        jLabel5.setText("Địa chỉ giao hàng");

        jLabel6.setText("Phương thức thanh toán");

        pay_type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Trực tiếp", "COD", "Chuyển khoản" }));

        jLabel2.setText("Nội dung đơn hàng");

        orderdetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Sản phẩm", "Số lượng", "Đơn giá","Giá"
            }
        ));
        TableColumn productColumn = orderdetail.getColumnModel().getColumn(0);
        productColumn.setCellEditor(new DefaultCellEditor(createProComboBox()));
        orderdetail.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int column = e.getColumn();
                    if (column == 0 || column == 1) { // Check if changed column is quantity or price
                        int row = e.getFirstRow();
                        if( orderdetail.getValueAt(row, 0) !=null && orderdetail.getValueAt(row, 1)!=null){
                            int quantity = Integer.parseInt(orderdetail.getValueAt(row, 1).toString());
                            float price = Float.valueOf(orderdetail.getValueAt(row, 2).toString());
                            float totalPrice = quantity * price;
                            orderdetail.setValueAt(totalPrice, row, 3); // Update total price column
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
        completed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                completedActionPerformed(evt);
            }
        });

        cancel.setText("Hủy Đơn");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        toShip.setText("Chờ Lấy Hàng");
        toShip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toShipActionPerformed(evt);
            }
        });

        toReceive.setText("Đang Giao");
        toReceive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toReceiveActionPerformed(evt);
            }
        });

        jButton8.setText("+");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(toShip)
                        .addGap(18, 18, 18)
                        .addComponent(toReceive)
                        .addGap(18, 18, 18)
                        .addComponent(completed))
                    .addComponent(cancel))
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(id_o_f)
                            .addComponent(name_c_f)
                            .addComponent(date_o_f)
                            .addComponent(addr_f)
                            .addComponent(pay_type, 0, 714, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton8))))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cancel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(completed)
                    .addComponent(toReceive)
                    .addComponent(toShip))
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
                    .addComponent(jLabel5)
                    .addComponent(addr_f, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(pay_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jButton8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton7.setText("Lưu");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
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
                        .addComponent(jButton7))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addGap(46, 46, 46))
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

        jButton20.setText("Xóa");
        jButton20.setFocusable(false);
        jButton20.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton20.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jButton21.setText("Chỉnh sửa");
        jButton21.setFocusable(false);
        jButton21.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton21.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jButton22.setText("Tìm kiếm");
        jButton22.setFocusable(false);
        jButton22.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton22.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addB)
                .addGap(18, 18, 18)
                .addComponent(jButton20)
                .addGap(18, 18, 18)
                .addComponent(jButton21)
                .addGap(104, 104, 104)
                .addComponent(jButton22)
                .addGap(18, 18, 18)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton22)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(addB, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton20, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton21, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 973, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1))
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

        javax.swing.GroupLayout thongkeLayout = new javax.swing.GroupLayout(thongke);
        thongke.setLayout(thongkeLayout);
        thongkeLayout.setHorizontalGroup(
            thongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 973, Short.MAX_VALUE)
        );
        thongkeLayout.setVerticalGroup(
            thongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 573, Short.MAX_VALUE)
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

    private void jTable2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseReleased
        index = jTable2.getSelectedRow();
        cndb db =  cndb.getInstance();
        db.open();
        List<Order> allOrders = db.allOrders();
        List<Product> allPro = db.allProducts();
        db.close();
        //System.out.print(""+ allOrders.get(index).getName_c());
        id_o_f.setText(allOrders.get(index).getId_o());
        name_c_f.setText(allOrders.get(index).getName_c());
        date_o_f.setText(allOrders.get(index).getDate_o().toString());
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

        DefaultTableModel model = (DefaultTableModel) orderdetail.getModel();
        model.setRowCount(0);
        Map<String, Integer> orderDetail= allOrders.get(index).getOrder_detail();
        for(Map.Entry<String, Integer> entry: orderDetail.entrySet()){
            String id = entry.getKey();
            //System.out.print(" " + id);
            int qual = entry.getValue();
            int price = 0;
            String name = null;
            for (Product product : allPro) {
                //System.out.print(" thu" +product.getId_p()) ;
                if (product.getId_p().equals(id)) {
                    name = product.getName_p();
                    price = product.getPrice_s();
                }
            }

            Object[] row = {name, qual, price, price*qual};
            model.addRow(row);
        }
        displayDefault();
        /*Object[] row = {null, null, null, null};
        model.addRow(row);*/
    }//GEN-LAST:event_jTable2MouseReleased

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        DefaultTableModel model = (DefaultTableModel) orderdetail.getModel();
        model.addRow(new Object[]{null, null, null, null});
    }//GEN-LAST:event_jButton8ActionPerformed

    private void nut_them_spActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nut_them_spActionPerformed
        productF pro = new productF();
        pro.setVisible(true);
    }//GEN-LAST:event_nut_them_spActionPerformed

    private void txt_tim_kiem_spActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tim_kiem_spActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tim_kiem_spActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void addBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBActionPerformed
        addDefault();
        defaultOrder();

    }//GEN-LAST:event_addBActionPerformed

    private void orderdetailMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderdetailMouseReleased


    }//GEN-LAST:event_orderdetailMouseReleased
public int del_stt;
    private void toShipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toShipActionPerformed
        defaultStt();
        toShip.setBackground(Color.pink);
        del_stt=1;
    }//GEN-LAST:event_toShipActionPerformed

    private void toReceiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toReceiveActionPerformed
        defaultStt();
        toReceive.setBackground(Color.pink);
        del_stt=2;
    }//GEN-LAST:event_toReceiveActionPerformed

    private void completedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_completedActionPerformed
        defaultStt();
        completed.setBackground(Color.pink);
        del_stt=3;
    }//GEN-LAST:event_completedActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        defaultStt();
        cancel.setBackground(Color.pink);
        del_stt=0;
    }//GEN-LAST:event_cancelActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        String id_o = id_o_f.getText().trim();
        String name_c = name_c_f.getText().trim();
        Date date_o = Handler.getDate(date_o_f);
        String addr =addr_f.getText().trim();
        String pay = (String) pay_type.getSelectedItem();
        
        //System.out.print(pay);
        cndb db = cndb.getInstance();
        db.open();
        Map<String, Integer> order_detail = new HashMap<>();
        for (int row = 0; row < orderdetail.getRowCount(); row++) {
            List<Product> proList = db.allProducts();
            String key = proList.get(indexP).getId_p();
            int value = Integer.parseInt(orderdetail.getValueAt(row, 1).toString());
            System.out.println("thunghiem"+value);
            
            order_detail.put(key, value); 
        } 

        db.orderInsert(id_o, name_c, date_o, addr, pay, del_stt , order_detail);
        db.close();
        RefreshTables();
    }//GEN-LAST:event_jButton7ActionPerformed

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
        //orderdetail.getCellEditor().isCellEditable(anEvent)
        
    }
        
    private void displayDefault(){
        id_o_f.setEditable(false);
        name_c_f.setEditable(false);
        date_o_f.setEditable(false);
        addr_f.setEditable(false);
        //orderdetail.getCellEditor().isCellEditable(anEvent)
        
    }
    private void defaultOrder(){
        defaultStt();
        id_o_f.setText(null);
        name_c_f.setText(null);
        date_o_f.setText(null);
        addr_f.setText(null);
        DefaultTableModel model = (DefaultTableModel) orderdetail.getModel();
        model.setRowCount(0);
        model.addRow(new Object[]{null, null, null, null});
    }
    
    int indexP;
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
                    orderdetail.setValueAt(selectedProduct.getPrice_s(), selectedRow, 2);
                }
            }
        });
        return comboBox;
    }
    
    
    public int index;
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
    private javax.swing.JTextField addr_f;
    private javax.swing.JButton cancel;
    private javax.swing.JButton completed;
    private javax.swing.JTextField date_o_f;
    private javax.swing.JPanel donhang;
    private javax.swing.JTextField id_o_f;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
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
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField name_c_f;
    private javax.swing.JButton nut_them_sp;
    private javax.swing.JButton nut_tim_kiem_sp;
    private javax.swing.JTable orderdetail;
    private javax.swing.JPanel panel_sp;
    private javax.swing.JComboBox<String> pay_type;
    private javax.swing.JPanel sanpham;
    private javax.swing.JPanel thongke;
    private javax.swing.JButton toReceive;
    private javax.swing.JButton toShip;
    private javax.swing.JTextField txt_tim_kiem_sp;
    // End of variables declaration//GEN-END:variables
}
