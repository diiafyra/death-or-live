/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
/**
 *
 * @author DELL
 */
public class MainFr extends javax.swing.JFrame {

    /**
     * Creates new form MainFr
     */
    public MainFr() {
        initComponents();
    }
    public void RefreshTables() {
        cndb db =  cndb.getInstance();
        db.open();
        jTable2.setModel(db.allOrdersT());
        //tbl_order.setModel(db.allOrder());
        db.close();
    }
    
        public class proPanel extends JPanel{
        public proPanel(byte[] imageData, String name, int price, int instock){
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setPreferredSize(new Dimension(300, 400));
            
            ImageIcon image = new ImageIcon(imageData);
            JLabel imageLb = new JLabel(image);
            imageLb.setPreferredSize(new Dimension(200, 200));
            add(imageLb);
            
            JLabel nameLb = new JLabel(name);
            JLabel priceLb = new JLabel(String.valueOf(price));
            JLabel instockLb = new JLabel(String.valueOf(instock));
            //JLabel chỉ nhận giá trị đối chiếu là String
            add(nameLb);
            add(priceLb);
            add(instockLb);
        }
        
    }
        
    public void RefreshProList() {
        //cần phải sửa jPanel1 sang layout grid hoặc flow hoặc box thì mới add được, poe muôn năm :)))
        cndb db =  cndb.getInstance();
        db.open();
        List<cndb.product> allPro = db.allProducts();

        List<cndb.proPanel> proPanels = db.allProP(allPro);
        //System.out.print(""+ allPro.get(0).getName());
        for(cndb.proPanel proP: proPanels){
            /*JLabel nameLabel = (JLabel) proP.getComponent(1); // Lấy JLabel t2 trong proPanel (chứa tên)
            JLabel priceLabel = (JLabel) proP.getComponent(2); // Lấy JLabel t3 trong proPanel (chứa giá)
            JLabel instockLabel = (JLabel) proP.getComponent(3); // Lấy JLabel t4 trong proPanel (chứa số lượng tồn)

            String name = nameLabel.getText(); // Lấy giá trị của JLabel chứa tên
            String price = priceLabel.getText(); // Lấy giá trị của JLabel chứa giá
            String instock = instockLabel.getText(); // Lấy giá trị của JLabel chứa số lượng tồn

            System.out.println("Name: " + name + ", Price: " + price + ", Instock: " + instock);*/
            jPanel1.add(proP);
        }
        db.close();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MENU = new javax.swing.JTabbedPane();
        sanpham = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        donhang = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        thongke = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        MENU.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        MENU.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        sanpham.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jToolBar1.setRollover(true);

        jButton1.setText("Thêm");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton2.setText("Xóa");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton2);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel1.setLayout(new java.awt.GridLayout());
        jScrollPane2.setViewportView(jPanel1);

        javax.swing.GroupLayout sanphamLayout = new javax.swing.GroupLayout(sanpham);
        sanpham.setLayout(sanphamLayout);
        sanphamLayout.setHorizontalGroup(
            sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 908, Short.MAX_VALUE)
        );
        sanphamLayout.setVerticalGroup(
            sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sanphamLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        jScrollPane1.setViewportView(jTable2);

        jSplitPane1.setLeftComponent(jScrollPane1);

        javax.swing.GroupLayout donhangLayout = new javax.swing.GroupLayout(donhang);
        donhang.setLayout(donhangLayout);
        donhangLayout.setHorizontalGroup(
            donhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 908, Short.MAX_VALUE)
        );
        donhangLayout.setVerticalGroup(
            donhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE)
        );

        MENU.addTab("ĐƠN HÀNG", donhang);

        javax.swing.GroupLayout thongkeLayout = new javax.swing.GroupLayout(thongke);
        thongke.setLayout(thongkeLayout);
        thongkeLayout.setHorizontalGroup(
            thongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 908, Short.MAX_VALUE)
        );
        thongkeLayout.setVerticalGroup(
            thongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 582, Short.MAX_VALUE)
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
            .addComponent(MENU, javax.swing.GroupLayout.DEFAULT_SIZE, 991, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MENU, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        product pro = new product();
        pro.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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
    private javax.swing.JPanel donhang;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTable2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel sanpham;
    private javax.swing.JPanel thongke;
    // End of variables declaration//GEN-END:variables
}
