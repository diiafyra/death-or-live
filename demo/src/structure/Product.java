package structure;

import static demo.Handler.formatDate;
import java.sql.Date;

public class Product {
    private String id_p;
    private String name_p;
    private int stock;
    private String desc;
    private byte[] image;
    private int price_i;
    private int price_s;
    private String date_p;
    private String depot;

    public Product(String id_p, String name_p, int stock, String desc, byte[] image, int price_i, int price_s, String date_P, String depot) {
        this.id_p = id_p;
        this.name_p = name_p;
        this.stock = stock;
        this.desc = desc;
        this.image = image;
        this.price_i = price_i;
        this.price_s = price_s;
        this.date_p = formatDate(date_P);
        this.depot = depot;
    }

    public String getId_p() {
        return id_p;
    }

    public String getName_p() {
        return name_p;
    }

    public int getStock() {
        return stock;
    }

    public String getDesc() {
        return desc;
    }

    public byte[] getImage() {
        return image;
    }

    public int getPrice_i() {
        return price_i;
    }

    public int getPrice_s() {
        return price_s;
    }

    public String getDate_p() {
        return date_p;
    }

    public String getDepot() {
        return depot;
    }

    public void setId_p(String id_p) {
        this.id_p = id_p;
    }

    public void setName_p(String name_p) {
        this.name_p = name_p;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setPrice_i(int price_i) {
        this.price_i = price_i;
    }

    public void setPrice_s(int price_s) {
        this.price_s = price_s;
    }

    public void setDate_p(String date_p) {
        this.date_p = date_p;
    }

    public void setDepot(String depot) {
        this.depot = depot;
    }



}

