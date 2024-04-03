/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;


import static control.Handler.formatDate;
import java.util.List;

/**
 *
 * @author DELL
 */

public class OrderInfo {
    public static class Order_detail{
        private String id_p;
        private int quantity;
        private int price_d;

        public Order_detail(String id_p, int quantity, int discount) {
            this.id_p = id_p;
            this.quantity = quantity;
            this.price_d = discount;
        }

        public String getId_p() {
            return id_p;
        }

        public int getQuantity() {
            return quantity;
        }

        public int getDiscount() {
            return price_d;
        }

        public void setId_p(String id_p) {
            this.id_p = id_p;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public void setDiscount(int discount) {
            this.price_d = discount;
        }



    }
    public static class Order{
        private String id_o;
        private String name_c;
        private String date_o;
        private String date_d;
        private String addr;
        private String pay_type;
        private int del_stt;
        private List<Order_detail> order_detail;//String là key là id_p , Interger là value = qual
        private double total_amount;

        public Order(String id_o, String name_c, String date_o, String date_d, String addr, String pay_type, int del_stt, List<Order_detail> order_detail, double total_amount) {
            this.id_o = id_o;
            this.name_c = name_c;
            this.date_o = formatDate(date_o);
            this.date_d = formatDate(date_d);
            this.addr = addr;
            this.pay_type = pay_type;
            this.del_stt = del_stt;
            this.order_detail = order_detail;
            this.total_amount = total_amount;
        }


        public void setId_o(String id_o) {
            this.id_o = id_o;
        }

        public void setName_c(String name_c) {
            this.name_c = name_c;
        }

        public void setDate_o(String date_o) {
            this.date_o = date_o;
        }

        public void setDate_d(String date_d) {
            this.date_d = date_d;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public void setDel_stt(int del_stt) {
            this.del_stt = del_stt;
        }

        public void setOD(List<Order_detail> order_detail) {
            this.order_detail = order_detail;
        }

        public void setTotal_amount(double total_amount) {
            this.total_amount = total_amount;
        }

        public String getId_o() {
            return id_o;
        }

        public String getName_c() {
            return name_c;
        }

        public String getDate_o() {
            return date_o;
        }

        public String getDate_d() {
            return date_d;
        }

        public String getAddr() {
            return addr;
        }

        public String getPay_type() {
            return pay_type;
        }

        public int getDel_stt() {
            return del_stt;
        }

        public List<Order_detail> getOrder_detail() {
            return order_detail;
        }

        public double getTotal_amount() {
            return total_amount;
        }


    }
    
}
     