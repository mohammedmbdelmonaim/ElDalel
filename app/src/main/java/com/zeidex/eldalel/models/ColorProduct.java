package com.zeidex.eldalel.models;

public class ColorProduct {
    String product_id;
    String name;
    String photo;

    public ColorProduct(String product_id, String name, String photo) {
        this.product_id = product_id;
        this.name = name;
        this.photo = photo;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
