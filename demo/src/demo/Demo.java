package demo;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatLightLaf;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.util.List;
import java.util.Map;



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