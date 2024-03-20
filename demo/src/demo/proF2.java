/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.sql.Date;
import javax.swing.JTextField;
import structure.proPanel;

/**
 *
 * @author dhhuo
 */
public class proF2 extends javax.swing.JFrame {

    public proF2() {
        initComponents();
    }   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        maspL = new javax.swing.JLabel();
        imageLb = new javax.swing.JLabel();
        maspF = new java.awt.TextField();
        ngaynhapL = new javax.swing.JLabel();
        ngaynhapF = new java.awt.TextField();
        tonkhoL = new javax.swing.JLabel();
        tonkhoF = new java.awt.TextField();
        khonhapL = new javax.swing.JLabel();
        khonhapF = new java.awt.TextField();
        gianhapL = new javax.swing.JLabel();
        gianhapF = new java.awt.TextField();
        motaL = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        motaArea = new javax.swing.JTextArea();
        tenspL = new javax.swing.JLabel();
        tenspF = new java.awt.TextField();
        giaL = new javax.swing.JLabel();
        giaF = new java.awt.TextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        maspL.setText("Mã SP");

        imageLb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imageLb.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        imageLb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                imageLbKeyTyped(evt);
            }
        });

        maspF.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                maspFMouseDragged(evt);
            }
        });
        maspF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                maspFFocusGained(evt);
            }
        });
        maspF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maspFActionPerformed(evt);
            }
        });
        maspF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                maspFKeyTyped(evt);
            }
        });

        ngaynhapL.setText("Ngày nhập");

        ngaynhapF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ngaynhapFActionPerformed(evt);
            }
        });
        ngaynhapF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ngaynhapFKeyTyped(evt);
            }
        });

        tonkhoL.setText("Tồn kho");

        tonkhoF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tonkhoFActionPerformed(evt);
            }
        });
        tonkhoF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tonkhoFKeyTyped(evt);
            }
        });

        khonhapL.setText("Kho nhập");

        khonhapF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                khonhapFActionPerformed(evt);
            }
        });

        gianhapL.setText("Giá nhập");

        gianhapF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gianhapFActionPerformed(evt);
            }
        });
        gianhapF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                gianhapFKeyTyped(evt);
            }
        });

        motaL.setText("Mô tả SP");

        motaArea.setColumns(20);
        motaArea.setRows(5);
        jScrollPane5.setViewportView(motaArea);

        tenspL.setText("Tên SP");

        tenspF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tenspFActionPerformed(evt);
            }
        });

        giaL.setText("Giá bán");

        giaF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                giaFActionPerformed(evt);
            }
        });
        giaF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                giaFKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(khonhapL)
                    .addComponent(gianhapL)
                    .addComponent(motaL)
                    .addComponent(maspL)
                    .addComponent(ngaynhapL))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ngaynhapF, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(maspF, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(giaL)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(giaF, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tenspL)
                                .addGap(75, 75, 75)
                                .addComponent(tenspF, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(khonhapF, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(gianhapF, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(tonkhoL)
                                .addGap(72, 72, 72)))
                        .addComponent(tonkhoF, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(90, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(268, 268, 268)
                .addComponent(imageLb, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imageLb, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(maspL)
                        .addComponent(maspF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tenspL))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tenspF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(ngaynhapL)
                        .addComponent(giaL))
                    .addComponent(giaF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ngaynhapF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tonkhoL, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tonkhoF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(khonhapL)
                            .addComponent(khonhapF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(gianhapL)
                            .addComponent(gianhapF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addComponent(motaL)))))
                .addContainerGap(130, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void imageLbKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_imageLbKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_imageLbKeyTyped

    private void maspFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maspFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maspFActionPerformed

    private void ngaynhapFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ngaynhapFActionPerformed

    }//GEN-LAST:event_ngaynhapFActionPerformed

    private void ngaynhapFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ngaynhapFKeyTyped

    }//GEN-LAST:event_ngaynhapFKeyTyped

    private void tonkhoFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tonkhoFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tonkhoFActionPerformed

    private void tonkhoFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tonkhoFKeyTyped
    }//GEN-LAST:event_tonkhoFKeyTyped

    private void khonhapFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_khonhapFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_khonhapFActionPerformed

    private void gianhapFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gianhapFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_gianhapFActionPerformed

    private void gianhapFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_gianhapFKeyTyped
    }//GEN-LAST:event_gianhapFKeyTyped

    private void tenspFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tenspFActionPerformed

    }//GEN-LAST:event_tenspFActionPerformed

    private void giaFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_giaFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_giaFActionPerformed

    private void giaFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_giaFKeyTyped
    }//GEN-LAST:event_giaFKeyTyped
    private void maspFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_maspFKeyTyped

    }//GEN-LAST:event_maspFKeyTyped

    private void maspFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_maspFFocusGained

    }//GEN-LAST:event_maspFFocusGained

    private void maspFMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_maspFMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_maspFMouseDragged


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
            java.util.logging.Logger.getLogger(proF2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(proF2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(proF2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(proF2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new proF2().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public java.awt.TextField giaF;
    private javax.swing.JLabel giaL;
    public java.awt.TextField gianhapF;
    private javax.swing.JLabel gianhapL;
    public javax.swing.JLabel imageLb;
    private javax.swing.JScrollPane jScrollPane5;
    public java.awt.TextField khonhapF;
    private javax.swing.JLabel khonhapL;
    public java.awt.TextField maspF;
    private javax.swing.JLabel maspL;
    public static javax.swing.JTextArea motaArea;
    private javax.swing.JLabel motaL;
    public java.awt.TextField ngaynhapF;
    private javax.swing.JLabel ngaynhapL;
    public java.awt.TextField tenspF;
    private javax.swing.JLabel tenspL;
    public java.awt.TextField tonkhoF;
    private javax.swing.JLabel tonkhoL;
    // End of variables declaration//GEN-END:variables
}
