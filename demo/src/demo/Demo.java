package demo;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatLightLaf;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.util.List;
import java.util.Map;



public class Demo {
    
    
    class order{
        private String id_o;
        private String id_c;
        private String date;
        private String addr;
        private String payType;
        private String deliStt;
        private Map<product, Integer> productQuantities;
        private int totalAmount;

        public order(String id_o, String id_c, String date, String addr, String payType, String deliStt, Map<product, Integer> productQuantities, int totalAmount) {
            this.id_o = id_o;
            this.id_c = id_c;
            this.date = date;
            this.addr = addr;
            this.payType = payType;
            this.deliStt = deliStt;
            this.productQuantities = productQuantities;
            this.totalAmount = totalAmount;
        }

        public String getId_o() {
            return id_o;
        }

        public String getId_c() {
            return id_c;
        }

        public String getDate() {
            return date;
        }

        public String getAddr() {
            return addr;
        }

        public String getPayType() {
            return payType;
        }

        public String getDeliStt() {
            return deliStt;
        }

        public Map<product, Integer> getProductQuantities() {
            return productQuantities;
        }

        public int getTotalAmount() {
            return totalAmount;
        }

        public void setId_o(String id_o) {
            this.id_o = id_o;
        }

        public void setId_c(String id_c) {
            this.id_c = id_c;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public void setDeliStt(String deliStt) {
            this.deliStt = deliStt;
        }

        public void setProductQuantities(Map<product, Integer> productQuantities) {
            this.productQuantities = productQuantities;
        }

        public void setTotalAmount(int totalAmount) {
            this.totalAmount = totalAmount;
        }
    }
    
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