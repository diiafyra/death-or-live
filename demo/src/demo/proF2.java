package demo;

import static demo.Handler.convertToBufferedImage;
import static demo.Handler.dateError;
import static demo.Handler.getByteArray;
import static demo.Handler.intError;
import static demo.Handler.pasteImageFromClipboard;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import structure.proPanel;
import java.sql.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JFileChooser;

public class proF2 extends javax.swing.JFrame {

    public proF2() {
        initComponents();
        // Đặt JFrame ở giữa màn hình
        setLocationRelativeTo(null);
        
        // Không cho phép xóa nội dung ban đầu của các textfield 
        maspF.setEditable(false);
        tenspF.setEditable(false);
        maspF.setEditable(false);
        giabanF.setEditable(false);
        khonhapF.setEditable(false);
        tonkhoF.setEditable(false);
        gianhapF.setEditable(false);
        motaArea.setEditable(false);
        ngaynhapF.setEditable(false);
        
        
        //Ẩn nút cho đến khi nhấn nut_chinh_sua
        nut_luu_chinh_sua.setVisible(false);
        txt_hinh_anh.setVisible(false);
        brower.setVisible(false);
        
    }   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        giaL = new javax.swing.JLabel();
        nut_luu_chinh_sua = new javax.swing.JButton();
        tenspF = new javax.swing.JTextField();
        errorMess = new javax.swing.JLabel();
        ngaynhapL = new javax.swing.JLabel();
        gianhapF = new javax.swing.JTextField();
        txt_hinh_anh = new javax.swing.JTextField();
        maspL = new javax.swing.JLabel();
        giabanF = new javax.swing.JTextField();
        khonhapL = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        motaArea = new javax.swing.JTextArea();
        nut_edit = new javax.swing.JButton();
        maspF = new javax.swing.JTextField();
        chua_hinh_anh = new javax.swing.JLabel();
        motaL = new javax.swing.JLabel();
        khonhapF = new javax.swing.JTextField();
        tonkhoF = new javax.swing.JTextField();
        tonkhoL = new javax.swing.JLabel();
        ngaynhapF = new javax.swing.JTextField();
        gianhapL = new javax.swing.JLabel();
        tenspL = new javax.swing.JLabel();
        brower = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        giaL.setText("Giá bán");

        nut_luu_chinh_sua.setText("Lưu chỉnh sửa");
        nut_luu_chinh_sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nut_luu_chinh_suaActionPerformed(evt);
            }
        });

        tenspF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tenspFActionPerformed(evt);
            }
        });
        tenspF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tenspFKeyTyped(evt);
            }
        });

        errorMess.setForeground(new java.awt.Color(255, 51, 51));
        errorMess.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        ngaynhapL.setText("Ngày nhập");

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

        txt_hinh_anh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_hinh_anhActionPerformed(evt);
            }
        });
        txt_hinh_anh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_hinh_anhKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_hinh_anhKeyTyped(evt);
            }
        });

        maspL.setText("Mã SP");

        giabanF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                giabanFActionPerformed(evt);
            }
        });
        giabanF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                giabanFKeyTyped(evt);
            }
        });

        khonhapL.setText("Kho nhập");

        motaArea.setColumns(20);
        motaArea.setRows(5);
        jScrollPane5.setViewportView(motaArea);

        nut_edit.setText("Edit ?");
        nut_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nut_editActionPerformed(evt);
            }
        });
        nut_edit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nut_editKeyTyped(evt);
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

        chua_hinh_anh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        chua_hinh_anh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        chua_hinh_anh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                chua_hinh_anhKeyTyped(evt);
            }
        });

        motaL.setText("Mô tả SP");

        khonhapF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                khonhapFActionPerformed(evt);
            }
        });
        khonhapF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                khonhapFKeyTyped(evt);
            }
        });

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

        tonkhoL.setText("Tồn kho");

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

        gianhapL.setText("Giá nhập");

        tenspL.setText("Tên SP");

        brower.setText("Brower");
        brower.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browerActionPerformed(evt);
            }
        });

        jLayeredPane1.setLayer(giaL, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(nut_luu_chinh_sua, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(tenspF, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(errorMess, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(ngaynhapL, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(gianhapF, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(txt_hinh_anh, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(maspL, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(giabanF, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(khonhapL, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jScrollPane5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(nut_edit, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(maspF, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(chua_hinh_anh, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(motaL, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(khonhapF, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(tonkhoF, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(tonkhoL, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(ngaynhapF, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(gianhapL, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(tenspL, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(brower, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(khonhapL)
                    .addComponent(gianhapL)
                    .addComponent(motaL)
                    .addComponent(maspL)
                    .addComponent(ngaynhapL))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addGap(122, 122, 122)
                                .addComponent(errorMess, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_hinh_anh, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(brower))
                                .addGap(87, 87, 87)
                                .addComponent(chua_hinh_anh, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(223, 223, 223))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(khonhapF, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ngaynhapF, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(maspF, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(gianhapF, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(nut_luu_chinh_sua))
                            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                    .addGap(1, 1, 1)
                                    .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                            .addComponent(giaL)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(giabanF, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                            .addComponent(tenspL)
                                            .addGap(75, 75, 75)
                                            .addComponent(tenspF, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                    .addComponent(tonkhoL)
                                    .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                            .addGap(218, 218, 218)
                                            .addComponent(nut_edit))
                                        .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                            .addGap(72, 72, 72)
                                            .addComponent(tonkhoF, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addContainerGap())))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chua_hinh_anh, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(txt_hinh_anh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(brower)
                        .addGap(31, 31, 31)))
                .addGap(18, 18, 18)
                .addComponent(errorMess, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(maspL)
                            .addComponent(maspF, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ngaynhapL)
                            .addComponent(ngaynhapF, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(khonhapL)
                            .addComponent(khonhapF, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(gianhapL)
                            .addComponent(gianhapF, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addComponent(motaL)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tenspL)
                            .addComponent(tenspF, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(giaL)
                            .addComponent(giabanF, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tonkhoL, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tonkhoF, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 279, Short.MAX_VALUE)
                        .addComponent(nut_edit)
                        .addGap(31, 31, 31)
                        .addComponent(nut_luu_chinh_sua)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void chua_hinh_anhKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_chua_hinh_anhKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_chua_hinh_anhKeyTyped

    private void txt_hinh_anhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_hinh_anhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hinh_anhActionPerformed

    private void txt_hinh_anhKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_hinh_anhKeyPressed
        if (evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_V) {
            currentImage = pasteImageFromClipboard();
            chua_hinh_anh.setIcon(currentImage);
            System.out.println("image no-null");
        }
    }//GEN-LAST:event_txt_hinh_anhKeyPressed

    private void nut_editKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nut_editKeyTyped

    }//GEN-LAST:event_nut_editKeyTyped

    private void nut_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nut_editActionPerformed
        nut_edit.setVisible(false);
        brower.setVisible(true);
        nut_luu_chinh_sua.setVisible(true);
        txt_hinh_anh.setVisible(true);
        chua_hinh_anh.setVisible(true); 
        tenspF.setEditable(true);
        giabanF.setEditable(true);
        khonhapF.setEditable(true);
        tonkhoF.setEditable(true);
        gianhapF.setEditable(true);
        motaArea.setEditable(true);
        ngaynhapF.setEditable(true);
    }//GEN-LAST:event_nut_editActionPerformed

    private void nut_luu_chinh_suaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nut_luu_chinh_suaActionPerformed

        String masp = maspF.getText().trim();
        String tensp = tenspF.getText().trim();
        String date = Handler.getDate(ngaynhapF);
        int stock = Integer.parseInt(tonkhoF.getText().trim());
        String desc = motaArea.getText().trim();
        byte[] image = null;
        if (currentImage != null) {
            try {
                  image = cndb.getImageBytes(currentImage.getImage());  
            } catch (IOException ex) {
                Logger.getLogger(productF.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
        int price_i= Integer.parseInt(gianhapF.getText().trim());
        int price_s=Integer.parseInt(giabanF.getText().trim());
        String depot=khonhapF.getText().trim();
        cndb a=cndb.getInstance();
        a.open();        
        a.productUpdate(masp, tensp, stock, desc, image, price_i, price_s, depot, date);

        //2 dòng này để cập nhật lại giao diện sp sau khi xóa 
        MainFr mfr = MainFr.getInstance();
        mfr.RefreshProList(); 
        
        a.close();
        
        setVisible(false);
    }//GEN-LAST:event_nut_luu_chinh_suaActionPerformed

    
    private void maspFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maspFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maspFActionPerformed

    private void maspFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_maspFKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_maspFKeyTyped

    private void ngaynhapFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ngaynhapFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ngaynhapFActionPerformed

    private void ngaynhapFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ngaynhapFKeyTyped
        errorMess.setText(dateError(evt, ngaynhapF));
//        a1++; 
    }//GEN-LAST:event_ngaynhapFKeyTyped

    private void tenspFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tenspFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tenspFActionPerformed

    private void tenspFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tenspFKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_tenspFKeyTyped

    private void khonhapFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_khonhapFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_khonhapFActionPerformed

    private void khonhapFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_khonhapFKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_khonhapFKeyTyped

    private void gianhapFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gianhapFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_gianhapFActionPerformed

    private void gianhapFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_gianhapFKeyTyped
        errorMess.setText(intError(evt));
    }//GEN-LAST:event_gianhapFKeyTyped

    private void tonkhoFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tonkhoFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tonkhoFActionPerformed

    private void tonkhoFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tonkhoFKeyTyped
        errorMess.setText(intError(evt));
    }//GEN-LAST:event_tonkhoFKeyTyped

    private void giabanFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_giabanFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_giabanFActionPerformed

    private void giabanFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_giabanFKeyTyped
        errorMess.setText(intError(evt));
    }//GEN-LAST:event_giabanFKeyTyped

    private void txt_hinh_anhKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_hinh_anhKeyTyped
          
    }//GEN-LAST:event_txt_hinh_anhKeyTyped

    private void browerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browerActionPerformed
        JFileChooser chooser = new JFileChooser(); // Tạo một đối tượng JFileChooser
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY); // Chỉ cho phép chọn tệp
        int result = chooser.showOpenDialog(this); // Hiển thị hộp thoại chọn tệp
        if (result == JFileChooser.APPROVE_OPTION) { // Nếu người dùng chọn một tệp
            File file = chooser.getSelectedFile(); // Lấy đối tượng File từ tệp đã chọn
            currentImage=cndb.displayImageOnLabel(file, chua_hinh_anh); // Hiển thị hình ảnh trên JLabel và Gán hình ảnh cho currentImage
        }
    }//GEN-LAST:event_browerActionPerformed


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
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new proF2().setVisible(true);
            }
        });
    }
    
    public static ImageIcon currentImage;
//    int a1=0;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton brower;
    public javax.swing.JLabel chua_hinh_anh;
    private javax.swing.JLabel errorMess;
    private javax.swing.JLabel giaL;
    public javax.swing.JTextField giabanF;
    public javax.swing.JTextField gianhapF;
    private javax.swing.JLabel gianhapL;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JScrollPane jScrollPane5;
    public javax.swing.JTextField khonhapF;
    private javax.swing.JLabel khonhapL;
    public javax.swing.JTextField maspF;
    private javax.swing.JLabel maspL;
    public static javax.swing.JTextArea motaArea;
    private javax.swing.JLabel motaL;
    public javax.swing.JTextField ngaynhapF;
    private javax.swing.JLabel ngaynhapL;
    private javax.swing.JButton nut_edit;
    private javax.swing.JButton nut_luu_chinh_sua;
    public javax.swing.JTextField tenspF;
    private javax.swing.JLabel tenspL;
    public javax.swing.JTextField tonkhoF;
    private javax.swing.JLabel tonkhoL;
    private javax.swing.JTextField txt_hinh_anh;
    // End of variables declaration//GEN-END:variables
}
