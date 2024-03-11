/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structure;


import java.sql.Date;
import java.util.Map;

/**
 *
 * @author DELL
 */
public class Order {
    private String id_o;
    private String name_c;
    private Date date_o;
    private String addr;
    private String pay_type;
    private int del_stt;
    private Map<String, Integer> order_detail;//String là key là id_p , Interger là value = qual
    private int total_amount;

    public Order(String id_o, String name_c, Date date_o, String addr, String pay_type, int del_stt, Map<String, Integer> order_detail, int total_amount) {
        this.id_o = id_o;
        this.name_c = name_c;
        this.date_o = date_o;
        this.addr = addr;
        this.pay_type = pay_type;
        this.del_stt = del_stt;
        this.order_detail = order_detail;
        this.total_amount = total_amount;
    }

    public String getId_o() {
        return id_o;
    }

    public String getName_c() {
        return name_c;
    }

    public Date getDate_o() {
        return date_o;
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

    public Map<String, Integer> getOrder_detail() {
        return order_detail;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setId_o(String id_o) {
        this.id_o = id_o;
    }

    public void setName_c(String name_c) {
        this.name_c = name_c;
    }

    public void setDate_o(Date date_o) {
        this.date_o = date_o;
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

    public void setOrder_detail(Map<String, Integer> order_detail) {
        this.order_detail = order_detail;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }
    
    
}
     