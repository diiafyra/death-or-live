/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.awt.Image;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
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
            String deliveryStt = order.getDel_stt();
            Object[] row = {id, name, cost, deliveryStt};
            dTM.addRow(row);
            }
        return dTM;
    }
    //phương thức hiển thị chi tiết đơn hàng khi nhấn vào
    
    //phương thức thêm đơn hàng vào bảng
    
    //phương thức sửa đơn hàng
    
    //phương thức xóa đơn hàng
    
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
    
    public static String intError(KeyEvent evt){
        String errorMessage;
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
    
    public static byte[] getByteArray(Image image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write((BufferedImage) image, "jpg", baos);
        return baos.toByteArray();
    }
    
    public static Date getDate(TextField label){
        String dateFormatString = "dd/MM/yyyy";

        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatString);

        String dateString = label.getText();

        Date date = null;
        try {
            java.util.Date utilDate = dateFormat.parse(dateString);
            date = new Date(utilDate.getTime());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage(), "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(productF.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
    }
    //phương thức hiển thị chi tiết sản phẩm khi nhấn vào
    
    //phương thức chỉnh sửa sản phẩm
    
    // phương thức xóa sản phẩm
    
    //phương thức tìm kiếm sản phẩm theo tên
    
    
    //
}