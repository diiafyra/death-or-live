/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structure;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author DELL
 */
public class proPanel extends JPanel {
    private boolean isHighlighted = false;

    public proPanel(byte[] imageData, String name, int price, int instock) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 300));

        // Sự kiện khi di chuột vào panel
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setHighlighted(true); // Đánh dấu panel được sáng lên
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setHighlighted(false); // Đánh dấu panel không được sáng lên
            }
        });

        ImageIcon image = new ImageIcon(imageData);
        JLabel imageLb = new JLabel(image);
        imageLb.setPreferredSize(new Dimension(200, 200));
        imageLb.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(imageLb);

        JLabel nameLb = new JLabel(name);
        nameLb.setAlignmentX(Component.CENTER_ALIGNMENT); // Căn giữa theo chiều ngang
        JLabel priceLb = new JLabel(String.valueOf(price)); // JLabel chỉ nhận giá trị đối chiếu là String
        priceLb.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel instockLb = new JLabel(String.valueOf(instock));
        instockLb.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(nameLb);
        add(priceLb);
        add(instockLb);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isHighlighted) {
            // Vẽ một màu nền mờ lên panel khi được sáng lên
            g.setColor(new Color(255, 255, 255, 100)); // Màu trắng với độ trong suốt 100
            g.fillRect(0, 0, getWidth(), getHeight()); // Vẽ một hình chữ nhật đậm nguyên màu trên toàn bộ panel
        }
    }

    // Phương thức để đặt trạng thái sáng lên của panel
    public void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
        repaint(); // Vẽ lại panel để hiển thị trạng thái mới
    }
}