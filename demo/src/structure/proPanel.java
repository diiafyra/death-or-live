/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structure;

import demo.MainFr;
import demo.cndb;
import demo.proF2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import sun.applet.Main;

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
             //Sự kiện khi nhấp 2 lần liên tiếp vào sp 
            @Override
            public void mouseClicked(MouseEvent e) {
                // Kiểm tra xem đây có phải là sự kiện nhấp đúp
                if (e.getClickCount() == 2) {
                    // Lấy tọa độ của sự kiện chuột
                    Point z1 = e.getLocationOnScreen();
                    // Tạo và hiển thị JFrame mới
                    JFrame a = new JFrame("Edit This Product");
                    a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    a.setSize(400, 300);
                    // Thiết lập vị trí của JFrame mới dựa trên tọa độ của sự kiện chuột
                    a.setLocation(z1.x, z1.y);
                    // JPanel chứa các nút
                    JPanel a1 = new JPanel();
//                    JPanel a2 = new JPanel();
                    
                    // Nút Xóa
                    JButton a11 = new JButton("Xóa");
                    a1.add(a11);
                    a11.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            // Thêm xử lý khi nhấn nút Xóa ở đây
                            cndb mn=new cndb();
                            mn.xoa_san_pham(e, imageData);    
                            a.dispose();
                            
                            //2 dòng này để cập nhật lại giao diện sp sau khi xóa 
                            MainFr mfr = MainFr.getInstance();
                            mfr.RefreshProList();
                        }
                    });

                    // Nút Chi tiết
                    JButton a22 = new JButton("Chi tiết");
                    a1.add(a22);
                    a22.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            // Thêm xử lý khi nhấn nút Chi tiết ở đây
                            cndb mn=new cndb();
                            mn.chi_tiet_san_pham(imageData);
                            String id_p = mn.getId_p();
                            String name_p= mn.getName_p();
                            int stock=mn.getStock();
                            String desc=mn.getDesc();
                            byte[] image=mn.getImage();
                            int price_i=mn.getPrice_i();
                            int price_s=mn.getPrice_s();
                            Date date_p=mn.getDate_p();
                            String depot=mn.getDepot();                            
                            proF2 c=new proF2();
                            c.maspF.setText(id_p); // Thiết lập giá trị id_p cho maspF
                            c.tenspF.setText(name_p);
                            c.tonkhoF.setText(Integer.toString(stock));
                            c.motaArea.setText(desc);
                            
                            // Chuyển đổi mảng byte thành một hình ảnh
                            ImageIcon icon = new ImageIcon(image);
                            // Thiết lập hình ảnh cho JLabel
                            c.imageLb.setIcon(icon);

                            c.gianhapF.setText(Integer.toString(price_i));
                            c.giaF.setText(Integer.toString(price_s));
                            
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Định dạng ngày tháng
                            String formattedDate = sdf.format(date_p); // Chuyển đổi Date thành chuỗi
                            c.ngaynhapF.setText(formattedDate); // Thiết lập giá trị cho ngaynhapF
                            
                            c.khonhapF.setText(depot);
                            c.setVisible(true);
                        }
                    });

                    // Thêm JPanel chứa các nút vào JFrame
                    a.add(a1);
                    
                    a.setVisible(true);
                }
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