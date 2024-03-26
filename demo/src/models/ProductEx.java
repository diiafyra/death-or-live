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
    
public class ProductEx{
   private String name;
   private int qual;
   private int price_i;
   private int price_s;

   public ProductEx(String name, int qual, int price_i, int price_s) {
       this.name = name;
       this.qual = qual;
       this.price_i = price_i;
       this.price_s = price_s;
   }

    public String getName() {
        return name;
    }

    public int getQual() {
        return qual;
    }

    public int getPrice_i() {
        return price_i;
    }

    public int getPrice_s() {
        return price_s;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQual(int qual) {
        this.qual = qual;
    }

    public void setPrice_i(int price_i) {
        this.price_i = price_i;
    }

    public void setPrice_s(int price_s) {
        this.price_s = price_s;
    }
   
   public int getProfit(){
       int profit = qual*(price_s-price_i);
       return profit;
   }
   
   public boolean checkProfit(){
       if(getProfit()>0){
           return true;
       } else{
           return false;
       }
   }
}   
