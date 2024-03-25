package demo;

import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


//kiếp nạn đầu tiên đã quaaaa

public class Demo {
    
    
    public static void main(String[] args) {
        //giao diện flatlaf, đỡ phèn có thể thêm css để thiết kế thêm
//        UIManager.put("Button.disabledBackground", Color.PINK); 

        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }  

        passwordFr pwFr = new passwordFr();
        pwFr.setVisible(true);
    }
}