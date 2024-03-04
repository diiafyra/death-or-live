package demo;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatLightLaf;


public class Demo {
    public static void main(String[] args) {
        //giao diện flatlaf, đỡ phèn có thể thêm css để thiết kế thêm

        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }  

        passwordFr pwFr = new passwordFr();
        pwFr.setVisible(true);
    }
}