package models;

import view.MainFr;
import control.cndb;
import java.awt.AlphaComposite;
import view.proF2;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import models.ProductInfo.Product;

public class CustomPanel {
    public static class RoundedPanel extends JPanel {
        private int arcWidth = 20; // Độ cong của góc
        private int arcHeight = 20; // Độ cong của góc
        
        public RoundedPanel() {
            setBackground(new Color(255, 182, 158));
            setOpaque(false); // Đảm bảo rằng panel là trong suốt
        }

        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Tạo hình dạng bo góc
            Shape shape = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);

            // Vẽ hình dạng bo góc
            g2d.setColor(getBackground());
            g2d.fill(shape);

            // Gọi phương thức paintComponent của lớp cha để vẽ các thành phần khác (nếu có)
            super.paintComponent(g);

            g2d.dispose();
        }


        // Ghi đè phương thức getInsets để xác định khoảng trắng xung quanh panel để vẽ hình dạng bo góc
        @Override
        public Insets getInsets() {
            int borderWidth = 10; // Độ rộng của viền (nếu có)
            return new Insets(borderWidth, borderWidth, borderWidth, borderWidth);
        }
    }
    
public static class HighlightPanel extends RoundedPanel {
    private boolean isHighlighted = false;

    public HighlightPanel() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setHighlighted(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setHighlighted(false);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isHighlighted) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int arcWidth = 20; // Độ cong của góc
            int arcHeight = 20; // Độ cong của góc
            RoundRectangle2D roundedRect = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
            
            g2d.setColor(new Color(255, 255, 255, 100)); // Màu trắng với độ trong suốt 100
            g2d.fill(roundedRect); // Vẽ một hình bo góc đậm nguyên màu trên toàn bộ panel
            
            g2d.dispose();
        }
    }

    public void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
        repaint(); // Vẽ lại panel để hiển thị trạng thái mới
    }
    }
    public static class CustomBgPanel extends HighlightPanel {
        public CustomBgPanel() {
            super(); // Gọi constructor của lớp cha
            // Thiết lập màu nền tùy chỉnh
            setBackground(new Color(255, 230, 191)); // Ví dụ: Màu xanh lá cây
        }
    }    
    
    public static class HighlightLabel extends JLabel {
        private Color highlightColor = new Color(255, 255, 255, 100); // Màu nền mờ khi highlight
        private boolean highlighted = false;

        public HighlightLabel() {
            super();
            setOpaque(false); // Bỏ chế độ đục của JLabel để vẽ nền
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setHighlighted(true);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setHighlighted(false);
                }
            });
        }

        public void setHighlighted(boolean highlighted) {
            this.highlighted = highlighted;
            repaint(); // Vẽ lại label để hiển thị trạng thái mới
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (highlighted) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(highlightColor);
                g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f)); // Điều chỉnh độ trong suốt
                g2d.fillRect(0, 0, getWidth(), getHeight()); // Vẽ hình chữ nhật mờ
                g2d.dispose();
            }
        }
    }
    
    public static class proPanel extends CustomBgPanel {
    //    private boolean isHighlighted = false;
        public static ImageIcon icon;
        public static int index;

        public proPanel(byte[] imageData, String name, int price, int instock) {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setPreferredSize(new Dimension(250, 300));

            // Sự kiện khi di chuột vào panel
            addMouseListener(new MouseAdapter() {

                 //Sự kiện khi nhấp 2 lần liên tiếp vào sp 
                List<Product> allPro ;
                @Override
                public void mouseClicked(MouseEvent e) {
                    //Lấy chỉ số index của panel để xóa sp theo chỉ số của propanels 
                    Component source = e.getComponent();
                    Container parent = source.getParent();
                    index = parent.getComponentZOrder(source);

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

                        // Nút Xóa
                        JButton a11 = new JButton("Xóa");
                        a1.add(a11);
                        a11.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                // Thêm xử lý khi nhấn nút Xóa ở đây
                                cndb mn=cndb.getInstance();
                                mn.open();
                                allPro = mn.allProducts();
                                mn.xoa_san_pham_theo_thu_tu_propanels(index,allPro);
                                a.dispose();

                                //2 dòng này để cập nhật lại giao diện sp sau khi xóa 
                                MainFr mfr = MainFr.getInstance();
                                mfr.RefreshProList();
                                mn.close();
                            }
                        });

                        // Nút Chi tiết
                        JButton a22 = new JButton("Chi tiết");
                        a1.add(a22);
                        a22.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                // Thêm xử lý khi nhấn nút Chi tiết ở đây
                                cndb mn=cndb.getInstance();
                                mn.open();
                                allPro = mn.allProducts();
                                mn.chi_tiet_san_pham(index,allPro);                            
                                proF2 c=new proF2();
                                c.maspF.setText(mn.id_p); // Thiết lập giá trị id_p cho maspF
                                c.tenspF.setText(mn.name_p);
                                c.tonkhoF.setText(Integer.toString(mn.stock));
                                c.motaArea.setText(mn.desc);                           
                                // Chuyển đổi mảng byte thành một hình ảnh
                                icon = new ImageIcon(mn.image);
                                // Thiết lập hình ảnh cho JLabel
                                c.chua_hinh_anh.setIcon(icon);
                                proF2.currentImage=icon;

                                c.gianhapF.setText(Integer.toString(mn.price_i));
                                c.giabanF.setText(Integer.toString(mn.price_s));            
    //                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng ngày tháng
    //                            String formattedDate = sdf.format(date_p); // Chuyển đổi Date thành chuỗi
                                c.ngaynhapF.setText(mn.date_p); // Thiết lập giá trị cho ngaynhapF                            
                                c.khonhapF.setText(mn.depot);
                                c.setVisible(true);
                                mn.close();
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
    }
}