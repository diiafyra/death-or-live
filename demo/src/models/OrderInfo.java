/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author DELL
 */
public class OrderInfo {
    private String id_p;
    private int quantity;
    private int price_d;

    public OrderInfo(String id_p, int quantity, int discount) {
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