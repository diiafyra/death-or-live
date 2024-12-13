package view;

import control.Handler;
import control.cndb;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static control.Handler.*;

public class productF extends javax.swing.JFrame {
    public productF() {
        initComponents();
        // Đặt JFrame ở giữa màn hình
        setLocationRelativeTo(null);
        
        //Gán cho ngaynhapF gí trị ngyaf hôm nay
        ngaynhapF.setText(Handler.getCurrentDate());
        
        // Yêu cầu con trỏ chuột chuyển đến JTextField khi JFrame được hiển thị
        maspF.requestFocusInWindow();
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        maspL = new javax.swing.JLabel();
        imageL = new javax.swing.JLabel();
        tenspL = new javax.swing.JLabel();
        motaL = new javax.swing.JLabel();
        tonkhoL = new javax.swing.JLabel();
        khonhapL = new javax.swing.JLabel();
        giaL = new javax.swing.JLabel();
        brower = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        motaArea = new javax.swing.JTextArea();
        gianhapL = new javax.swing.JLabel();
        imageLb = new javax.swing.JLabel();
        add = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        ngaynhapL = new javax.swing.JLabel();
        errorMess = new javax.swing.JLabel();
        maspF = new javax.swing.JTextField();
        ngaynhapF = new javax.swing.JTextField();
        tenspF = new javax.swing.JTextField();
        khonhapF = new javax.swing.JTextField();
        gianhapF = new javax.swing.JTextField();
        tonkhoF = new javax.swing.JTextField();
        giaF = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        maspL.setText("Mã SP");
        jPanel4.add(maspL, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        imageL.setText("Hình ảnh SP");
        jPanel4.add(imageL, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, -1));

        tenspL.setText("Tên SP");
        jPanel4.add(tenspL, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 50, -1, -1));

        motaL.setText("Mô tả SP");
        jPanel4.add(motaL, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 300, -1, -1));

        tonkhoL.setText("Tồn kho");
        jPanel4.add(tonkhoL, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, 21));

        khonhapL.setText("Kho nhập");
        jPanel4.add(khonhapL, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 170, -1, -1));

        giaL.setText("Giá bán");
        jPanel4.add(giaL, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        brower.setText("Brower");
        brower.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browerActionPerformed(evt);
            }
        });
        jPanel4.add(brower, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 260, -1, -1));

        motaArea.setColumns(20);
        motaArea.setRows(5);
        jScrollPane5.setViewportView(motaArea);

        jPanel4.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 330, 206, 170));

        gianhapL.setText("Giá nhập");
        jPanel4.add(gianhapL, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 230, -1, -1));

        imageLb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imageLb.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        imageLb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                imageLbKeyTyped(evt);
            }
        });
        jPanel4.add(imageLb, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 200, 200));

        add.setText("Thêm");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });
        jPanel4.add(add, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 520, 120, 40));

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });
        jPanel4.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 90, 20));

        ngaynhapL.setText("Ngày nhập");
        jPanel4.add(ngaynhapL, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 110, -1, -1));

        errorMess.setForeground(new java.awt.Color(255, 51, 51));
        errorMess.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel4.add(errorMess, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 320, 20));

        maspF.setMinimumSize(new java.awt.Dimension(6, 19));
        maspF.setPreferredSize(new java.awt.Dimension(6, 19));
        maspF.setRequestFocusEnabled(false);
        maspF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maspFActionPerformed(evt);
            }
        });
        maspF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                maspFKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                maspFKeyTyped(evt);
            }
        });
        jPanel4.add(maspF, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 210, 25));

        ngaynhapF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ngaynhapFActionPerformed(evt);
            }
        });
        ngaynhapF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ngaynhapFKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ngaynhapFKeyTyped(evt);
            }
        });
        jPanel4.add(ngaynhapF, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 140, 210, 25));

        tenspF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tenspFActionPerformed(evt);
            }
        });
        tenspF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tenspFKeyPressed(evt);
            }
        });
        jPanel4.add(tenspF, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 80, 210, 25));

        khonhapF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                khonhapFKeyPressed(evt);
            }
        });
        jPanel4.add(khonhapF, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 200, 210, 25));

        gianhapF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                gianhapFKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                gianhapFKeyTyped(evt);
            }
        });
        jPanel4.add(gianhapF, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 260, 210, 25));

        tonkhoF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tonkhoFKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tonkhoFKeyTyped(evt);
            }
        });
        jPanel4.add(tonkhoF, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 210, 25));

        giaF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                giaFKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                giaFKeyTyped(evt);
            }
        });
        jPanel4.add(giaF, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 210, 25));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void browerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browerActionPerformed
        JFileChooser chooser = new JFileChooser(); // Tạo một đối tượng JFileChooser
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY); // Chỉ cho phép chọn tệp
        int result = chooser.showOpenDialog(this); // Hiển thị hộp thoại chọn tệp
        if (result == JFileChooser.APPROVE_OPTION) { // Nếu người dùng chọn một tệp
            File file = chooser.getSelectedFile(); // Lấy đối tượng File từ tệp đã chọn
            currentImage=cndb.displayImageOnLabel(file, imageLb); // Hiển thị hình ảnh trên JLabel và Gán hình ảnh cho currentImage

        }        
    }//GEN-LAST:event_browerActionPerformed

    private void imageLbKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_imageLbKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_imageLbKeyTyped

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        String id = maspF.getText();
        String name = tenspF.getText();
        int instock = Integer.parseInt(tonkhoF.getText());
        String desc = motaArea.getText();
        byte[] imageData = null;
        if (currentImage != null) {
            try {
                // Dùng cndb.getImageBytes 
                imageData = cndb.getImageBytes(currentImage.getImage());
            } catch (IOException ex) {
                Logger.getLogger(productF.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        int price_s = Integer.parseInt(giaF.getText());
        int price_i = Integer.parseInt(gianhapF.getText());
        String depot = khonhapF.getText();

        String date = getDate(ngaynhapF);

        cndb db = cndb.getInstance();
        db.open();
        int insertStatus = db.productInsert(id, name, instock, desc, imageData, price_i, price_s, depot, date);
        if(insertStatus!=-1){
            JOptionPane.showMessageDialog(null, "Thêm sản phẩm thành công!");
        }
        System.out.print(insertStatus);

        MainFr mfr = MainFr.getInstance();
        mfr.RefreshProList();
        
        setVisible(false);
        db.close();
    }//GEN-LAST:event_addActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        if (evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_V) {
            currentImage = pasteImageFromClipboard();
            imageLb.setIcon(currentImage);
        }
    }//GEN-LAST:event_jTextField1KeyPressed

    private void giaFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_giaFKeyTyped
        errorMess.setText(intError(evt));
    }//GEN-LAST:event_giaFKeyTyped

    private void tonkhoFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tonkhoFKeyTyped
        errorMess.setText(intError(evt));
    }//GEN-LAST:event_tonkhoFKeyTyped

    private void gianhapFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_gianhapFKeyTyped
        errorMess.setText(intError(evt));
    }//GEN-LAST:event_gianhapFKeyTyped

    private void ngaynhapFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ngaynhapFKeyTyped
        errorMess.setText(dateError(evt, ngaynhapF));
    }//GEN-LAST:event_ngaynhapFKeyTyped

    private void ngaynhapFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ngaynhapFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ngaynhapFActionPerformed

    private void maspFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maspFActionPerformed

    }//GEN-LAST:event_maspFActionPerformed

    private void maspFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_maspFKeyTyped

    }//GEN-LAST:event_maspFKeyTyped

    private void maspFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_maspFKeyPressed
        Handler.enter(evt,tenspF);
    }//GEN-LAST:event_maspFKeyPressed

    private void tenspFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tenspFKeyPressed
        Handler.enter(evt,giaF);
    }//GEN-LAST:event_tenspFKeyPressed

    private void ngaynhapFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ngaynhapFKeyPressed
        Handler.enter(evt,tonkhoF);
    }//GEN-LAST:event_ngaynhapFKeyPressed

    private void tonkhoFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tonkhoFKeyPressed
        Handler.enter(evt,khonhapF);
    }//GEN-LAST:event_tonkhoFKeyPressed

    private void khonhapFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_khonhapFKeyPressed
        Handler.enter(evt,gianhapF);
    }//GEN-LAST:event_khonhapFKeyPressed

    private void gianhapFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_gianhapFKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // Chuyển focus sang textField_out khi nhấn Enter
            motaArea.requestFocusInWindow();
        }        
    }//GEN-LAST:event_gianhapFKeyPressed

    private void giaFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_giaFKeyPressed
        Handler.enter(evt,ngaynhapF);
    }//GEN-LAST:event_giaFKeyPressed

    private void tenspFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tenspFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tenspFActionPerformed

       
    ImageIcon currentImage;
    int a1=0;
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(productF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(productF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(productF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(productF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new productF().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JButton brower;
    private javax.swing.JLabel errorMess;
    private javax.swing.JTextField giaF;
    private javax.swing.JLabel giaL;
    private javax.swing.JTextField gianhapF;
    private javax.swing.JLabel gianhapL;
    private javax.swing.JLabel imageL;
    private javax.swing.JLabel imageLb;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField khonhapF;
    private javax.swing.JLabel khonhapL;
    private javax.swing.JTextField maspF;
    private javax.swing.JLabel maspL;
    private javax.swing.JTextArea motaArea;
    private javax.swing.JLabel motaL;
    private javax.swing.JTextField ngaynhapF;
    private javax.swing.JLabel ngaynhapL;
    private javax.swing.JTextField tenspF;
    private javax.swing.JLabel tenspL;
    private javax.swing.JTextField tonkhoF;
    private javax.swing.JLabel tonkhoL;
    // End of variables declaration//GEN-END:variables
}
