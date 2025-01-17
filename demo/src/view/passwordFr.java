
package view;

//import static demo.MainFr.pass_change;

import javax.swing.*;
import java.awt.event.KeyEvent;


public class passwordFr extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public passwordFr() {
        initComponents();
        // Đặt JFrame ở giữa màn hình
        setLocationRelativeTo(null);
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        password = new javax.swing.JPasswordField();
        LoginBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        password.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordActionPerformed(evt);
            }
        });
        password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                passwordKeyTyped(evt);
            }
        });

        LoginBtn.setText("OK");
        LoginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginBtnActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel2.setText("Password");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(35, 35, 35)
                        .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(LoginBtn)))
                .addContainerGap(139, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(179, 179, 179)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(70, 70, 70)
                .addComponent(LoginBtn)
                .addContainerGap(116, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LoginBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginBtnActionPerformed
            String enteredText = password.getText();
            if (enteredText.equals(originalPassword)) {
                MainFr mfr = MainFr.getInstance();
                mfr.setVisible(true);
                mfr.RefreshTables();
                mfr.RefreshProList();
                dispose();  
            } else {
                // Mật khẩu không đúng, có thể hiển thị thông báo lỗi hoặc thực hiện hành động khác
                JOptionPane.showMessageDialog(this, "Mật khẩu không đúng", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }     
    }//GEN-LAST:event_LoginBtnActionPerformed

    private void passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordActionPerformed
      
    }//GEN-LAST:event_passwordActionPerformed
    
    //Mật khẩu nhé , không tạo database nữa 
    private void passwordKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordKeyTyped
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            String enteredText = password.getText();
            
//            MainFr abc=new MainFr();
//            JTextField a = abc.setpass();
//            originalPassword=a.getText();
//
////            // Đọc giá trị từ tệp
////            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
////                String line = reader.readLine();
////                int savedValue = Integer.parseInt(line);
////                System.out.println("Saved value: " + savedValue);
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
                   
            if (enteredText.equals(originalPassword)) {
                MainFr mfr = MainFr.getInstance();
                mfr.setVisible(true);
                mfr.RefreshTables();
                mfr.RefreshProList();
                dispose();  
            } else {
                // Mật khẩu không đúng, có thể hiển thị thông báo lỗi hoặc thực hiện hành động khác
                JOptionPane.showMessageDialog(this, "Mật khẩu không đúng", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_passwordKeyTyped

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(passwordFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(passwordFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(passwordFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(passwordFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new passwordFr().setVisible(true);
            }
        });
    }
    protected String originalPassword ="abc" ; // Thay đổi chuỗi này thành mật khẩu 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton LoginBtn;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPasswordField password;
    // End of variables declaration//GEN-END:variables
}
