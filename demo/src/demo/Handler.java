/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import structure.Order;
import structure.Product;
import structure.proPanel;

/**
 *
 * @author DELL
 */
public class Handler {
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
            int cost = order.getTotal_amount();
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
    public static String dateError(KeyEvent evt, JTextField date){

        char ch = evt.getKeyChar();
        String errorMessage="";
        errorMessage = intError(evt);
        if(!errorMessage.equals("")){
            errorMessage += ". Nhập đúng định dạng dd/mm/yyyy";
        }        
        if((date.getText().length() == 1 || date.getText().length() == 4) && ch != '\b'){
            date.setText(date.getText() + ch +"/");
            evt.consume(); 
        }
        if((date.getText().length() == 2 || date.getText().length() == 5) && ch == '\b'){
            check = true;
        }else if(check && ch != '\b' && (date.getText().length() == 2 || date.getText().length() == 5) ){
            date.setText(date.getText() + "/" + ch);
            evt.consume();
            check = false;
        }
        
        if (date.getText().length() == 10) {
            errorMessage = "Nhập quá nhiều ký tự. Nhập đúng định dạng dd/mm/yyyy";
            evt.consume(); 
        } 
//        date.requestFocusInWindow(); // Di chuyển con trỏ văn bản đến JTextField
        return errorMessage; 
    }


   
    // Lớp xử lý sự kiện để paste hình ảnh từ Clipboard
    public static ImageIcon pasteImageFromClipboard() {
        ImageIcon currentImage = null;
        try {
            Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.imageFlavor)) {
                Image image = (Image) transferable.getTransferData(DataFlavor.imageFlavor);
                ImageIcon icon = new ImageIcon(image);
                //imageLb.setIcon(icon);
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
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage(), "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
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
    

    //phương thức tìm kiếm sản phẩm theo tên
    
    
    //
}
