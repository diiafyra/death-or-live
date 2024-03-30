/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import models.Order;
import models.Product;
import models.ProductEx;
import models.proPanel;
import org.jfree.data.category.DefaultCategoryDataset;

public class Handler {
    public static class HighlightPanel extends JPanel {
    private boolean isHighlighted = false;

    public HighlightPanel() {
        setBackground(new Color(140, 204, 185));
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
            g.setColor(new Color(255, 255, 255, 100)); // Màu trắng với độ trong suốt 100
            g.fillRect(0, 0, getWidth(), getHeight()); // Vẽ một hình chữ nhật đậm nguyên màu trên toàn bộ panel
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
//phương thức hiển thị tất cả sản phẩm hiện có trong list vào bảng đơn hàng trong ứng dụng
    public static DefaultTableModel allOrdersT(List<Order> allOrders){
        DefaultTableModel dTM = new DefaultTableModel();
        dTM.addColumn("Mã Đơn Hàng");
        dTM.addColumn("Tên Khách Hàng");
        dTM.addColumn("Thành Tiền");
        dTM.addColumn("Trạng thái giao hàng");

        for(Order order: allOrders){
            String id = order.getId_o();
            String name = order.getName_c();
            double cost = order.getTotal_amount();
            int deliveryStt = order.getDel_stt();
            Object[] row = {id, name, cost, deliveryStt};
            dTM.addRow(row);
            }
        return dTM;
    }
   
    
    //phương thức tìm kiếm đơn hàng theo tên khách hàng hoặc sản phẩm
    
    
    //phương thức hiển thị sản phẩm đang có trong database ra bảng   
    public static List<proPanel> allProP(List<Product> allPro) {
        List<proPanel> proPanels = new ArrayList<>();
        for (Product product : allPro) {
            String name = product.getName_p();
            int instock = product.getStock();
            int price = product.getPrice_s();
            byte[] image = product.getImage();
            proPanel proP = new proPanel(image, name, price, instock);
            proPanels.add(proP);
        }
        return proPanels;
    }
    
    public static List<Product> noSalePro(List<Product> allPro, List<ProductEx> proExs) {
//        List<Product> allPro = db.allProducts();
//        List<ProductEx> proExs = db.salesReports(month, year);
        List<Product> noSalePros = new ArrayList<>();
        Map<String, Integer> proSale = new HashMap<>();

        // Tính số lượng bán của mỗi sản phẩm và lưu vào Map proSale
        for (ProductEx proEx : proExs) {
            proSale.put(proEx.getId(), proSale.getOrDefault(proEx.getId(), 0) + proEx.getQual());
        }

        // Kiểm tra từng sản phẩm trong allPro
        for (Product pro : allPro) {
            if (!proSale.containsKey(pro.getId_p())) {
                noSalePros.add(pro);
            }
        }
        return noSalePros;
    }

    
    public static List<Product> bestSalePro(List<Product> allPro, List<ProductEx> proExs, int limit) {
        List<Product> bestSalePros = new ArrayList<>();
        Map<String, Integer> proSale = new HashMap<>();

        // Tính số lượng bán của mỗi sản phẩm và lưu vào Map proSale
        for (ProductEx proEx : proExs) {
            proSale.put(proEx.getId(), proSale.getOrDefault(proEx.getId(), 0) + proEx.getQual());
        }

        // Kiểm tra từng sản phẩm trong allPro
        for (Product pro : allPro) {
            Integer saleQuantity = proSale.get(pro.getId_p());
            if (saleQuantity != null && saleQuantity >= limit) {
                bestSalePros.add(pro);
            }
        }
        return bestSalePros;
    }
    
    public static List<Product> stockProAna(List<Product> allPro, int limit, int option) {
        List<Product> proFilterByStock = new ArrayList<>();

        // Kiểm tra từng sản phẩm trong allPro
        for (Product pro : allPro) {
            switch(option){
                case 0:
                    if (pro.getStock() == 0) {
                        proFilterByStock.add(pro);
                    }
                    break;
                case 1:
                    if (pro.getStock() < limit && pro.getStock() !=0) {
                        proFilterByStock.add(pro);
                    }
                    break;                    
                case 2:
                    if (pro.getStock() >= 0 ) {
                        proFilterByStock.add(pro);
                    }
                    break;                       
            }
        }
        return proFilterByStock;
    }

    
    //xác định lỗi khi người dùng nhập vào bảng không phải số
    public static String intErrorTable(JTable table, TableModelEvent e) {
        String errorMessage = " ";
        int row = e.getFirstRow();
        int column = e.getColumn();
        String value = (String) table.getValueAt(row, column);

        if ( value != null && !value.matches("\\d+")) { // Kiểm tra nếu giá trị không phải là số nguyên dương
            errorMessage = "HÃY NHẬP VÀO SỐ";
            table.setValueAt(null, row, column);
        } else {
            errorMessage = "";
        }
        return errorMessage;
    }
    
    //xác định lỗi khi người dùng nhập vào không phải là số
    public static String intError(KeyEvent evt){
        String errorMessage=" ";
        char ch = evt.getKeyChar();
        if(!Character.isDigit(ch)){
            errorMessage = "HÃY NHẬP VÀO SỐ";
            if(Character.toString(ch).equals("\b")  ) {
                errorMessage = "";
            }   
            evt.consume(); 
        }
        else{
            errorMessage = "";
        }                         
        return errorMessage;
    }
    
    //xác định lỗi khi người dùng nhập vào không phải là date
    static boolean check = false;   
    
    public static String dateError(KeyEvent evt, JTextField date) {
        char ch = evt.getKeyChar();
        String errorMessage = intError(evt);
        if (!errorMessage.equals("")) {
            errorMessage += ". Nhập đúng định dạng dd/mm/yyyy";
        }
        
        if ((date.getText().length() == 1 || date.getText().length() == 4) && ch != '\b') {
            date.setText(date.getText() + ch + "/");
            evt.consume();
        }
        
        if ((date.getText().length() == 2 || date.getText().length() == 5) && ch == '\b') {
            check = true;
        } else if (check && ch != '\b' && (date.getText().length() == 2 || date.getText().length() == 5)) {
            date.setText(date.getText() + "/" + ch);
            evt.consume();
            check = false;
        }
        
        if (date.getText().length() == 10) {
            errorMessage = "Nhập quá nhiều ký tự. Nhập đúng định dạng dd/mm/yyyy";
            evt.consume();
        }
        
        
        return errorMessage;
    }
    //PT lấy ngày hiện tại 
    public static String getCurrentDate() {
        // Định dạng ngày tháng
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        // Lấy ngày hiện tại
        LocalDate currentDate = LocalDate.now();
        // Chuyển định dạng ngày thành chuỗi
        String formattedDate = currentDate.format(formatter);
        return formattedDate;
    }    

    // Lớp xử lý sự kiện để paste hình ảnh từ Clipboard
    public static ImageIcon pasteImageFromClipboard() {
        ImageIcon currentImage = null;
        try {
            Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.imageFlavor)) {
                Image image = (Image) transferable.getTransferData(DataFlavor.imageFlavor);
                ImageIcon icon = new ImageIcon(image);

                // Kiểm tra kích thước của hình ảnh
                int width = icon.getIconWidth();
                int height = icon.getIconHeight();

                // Nếu kích thước vượt quá 200x200, thì thay đổi kích thước
                if (width > 200 || height > 200) {
                    int newWidth = width > 200 ? 200 : width; // Giảm chiều rộng nếu lớn hơn 200
                    int newHeight = height > 200 ? height * 200 / width : height; // Tính toán chiều cao mới

                    // Thay đổi kích thước hình ảnh
                    image = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                    icon = new ImageIcon(image);
                }

                currentImage = icon; // Lưu ImageIcon hiện tại
            }
        } catch (UnsupportedFlavorException | IOException ex) {
            ex.printStackTrace();
        }
        return currentImage;
    }


    
        //chuyển đổi từ ToolkitImage sang BufferedImage
    public static BufferedImage convertToBufferedImage(ImageIcon icon) {
        Image image = icon.getImage();
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        // Tạo BufferedImage mới có kích thước tương ứng với hình ảnh
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Vẽ hình ảnh lên BufferedImage
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return bufferedImage;
    }
    
    //nhận một đối tượng hình ảnh (Image) và chuyển đổi nó thành một mảng byte
    public static byte[] getByteArray(Image image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write((BufferedImage) image, "jpg", baos);
        return baos.toByteArray();
    }

    public static String getDate(JTextField label) {
        String inputFormat = "dd/MM/yyyy";
        String outputFormat = "yyyy-MM-dd";

        SimpleDateFormat inputDateFormat = new SimpleDateFormat(inputFormat);
        SimpleDateFormat outputDateFormat = new SimpleDateFormat(outputFormat);

        String dateString = label.getText();

        try {
            // Chuyển đổi từ định dạng đầu vào sang định dạng chuẩn ISO 8601
            java.util.Date utilDate = inputDateFormat.parse(dateString);
            return outputDateFormat.format(utilDate);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage(), "Sai định dạng ngày", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(productF.class.getName()).log(Level.SEVERE, null, ex);
            return null; // Trả về null nếu có lỗi xảy ra
        }
    }
    
    public static String formatDate(String dateString) {
        String formattedDate = null;
        if(dateString != null){
            try {

                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");

                java.util.Date utilDate = inputFormat.parse(dateString);

                Date sqlDate = new Date(utilDate.getTime());

                SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                // Chuyển đổi đối tượng java.sql.Date thành chuỗi định dạng "dd/MM/yyyy"
                formattedDate = outputFormat.format(sqlDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return formattedDate;
    }


    //Phương thức tạo biểu đồ tròn, đối số tên bảng và map<sp,%>
    
    public static JFreeChart createPieChart(DefaultPieDataset dataset, String nameChart){
        
        JFreeChart chart = ChartFactory.createPieChart(
                nameChart, 
                dataset,             // Dataset
                true,                // Hiển thị chú thích
                true,                // Hiển thị tooltips
                false                // Không hiển thị URLs
        );        
        return chart;
    }

    public static JFreeChart createLineChart(String title, DefaultCategoryDataset dataset) {

        JFreeChart chart = ChartFactory.createLineChart(
                title, // Chart title
                "Tháng",          // X-axis label
                "VND",             // Y-axis label
                dataset
        );
        return chart;
    }
    
    

    
     //Phương thức khi nhấn enter thì chuyẻn từ jtextField này sang jtextField khác
    public static void enter(KeyEvent evt, JTextField b) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // Chuyển focus sang textField_out khi nhấn Enter
            b.requestFocusInWindow();
        }
    }
}
