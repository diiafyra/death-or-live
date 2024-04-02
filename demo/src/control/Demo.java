package control;

import view.passwordFr;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class Demo {

    private static void setColor() {
        UIManager.put("TabbedPane.background", new Color(250, 215, 160));  // Màu nền của TabbedPane
        UIManager.put("TabbedPane.focusColor", new Color(222, 153, 155));
        UIManager.put("Button.background", new Color(222, 153, 155));         // Màu nền của Button  
        UIManager.put("TextField.background", new Color(255, 182, 158));     // Màu nền của TextField
        UIManager.put("ComboBox.background", new Color(140, 204, 185));      // Màu nền của ComboBox
        UIManager.put("Panel.background", new Color(255, 230, 191));         // Màu nền của Panel
        UIManager.put("Label.foreground", new Color(59, 59, 59));            // Màu chữ của Label
        UIManager.put("Menu.background", new Color(59, 59, 59));             // Màu chữ của Menu
        UIManager.put("RootPane.background", new Color(255, 230, 191));         // Màu nền của MenuBar
        UIManager.put("ScrollPane.background", new Color(255, 230, 191));                 // Màu nền của ScrollPane
        UIManager.put("Table.background", new Color(140, 204, 185));         // Màu nền của Table
        UIManager.put("SplitPane.background", new Color(255, 230, 191));     // Màu nền của SplitPane
        UIManager.put("TableHeader.background", new Color(255, 204, 0));     // Màu nền của Header trong bảng
        UIManager.put("TableHeader.foreground", Color.BLACK);                // Màu chữ của Header trong bảng
        UIManager.put("Table.gridColor", new Color(255, 204, 0));            // Màu của đường viền trong bảng khi thay đổi số liệu

        UIManager.put("OptionPane.background", new Color(255, 230, 191));
        UIManager.put("ComboBox.foreground", new Color(0, 0, 0));
        UIManager.put("TabbedPane.foreground", new Color(0, 0, 0));   // Màu nền của TabbedPane
        UIManager.put("Button.foreground", Color.WHITE);                      // Màu chữ của Button
        UIManager.put("Label.foreground", new Color(0, 0, 0));            // Màu chữ của Label
        UIManager.put("Table.foreground", new Color(0, 0, 0));
        UIManager.put("TextField.foreground", new Color(0, 0, 0));
        UIManager.put("Button.foreground", new Color(0, 0, 0)); 
        UIManager.put("MenuBar.foreground", new Color(0, 0, 0));             // Màu chữ của Menu
        UIManager.put("MenuBar.background", new Color(250, 215, 160));
    }



    public static void main(String[] args) {
//         Sử dụng giao diện FlatLaf
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        setColor();
        // Tạo và hiển thị frame
        passwordFr pwFr = new passwordFr();
        pwFr.setVisible(true);
    }
}
