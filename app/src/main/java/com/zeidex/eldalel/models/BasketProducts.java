package com.zeidex.eldalel.models;

public class BasketProducts {
    String cart_id , product_id , title , imgurl , name , price , price_before , item_count;


    public BasketProducts(String cart_id, String product_id, String title, String imgurl, String name, String price, String price_before, String item_count) {
        this.cart_id = cart_id;
        this.product_id = product_id;
        this.title = title;
        this.imgurl = imgurl;
        this.name = name;
        this.price = price;
        this.price_before = price_before;
        this.item_count = item_count;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice_before() {
        return price_before;
    }

    public void setPrice_before(String price_before) {
        this.price_before = price_before;
    }

    public String getItem_count() {
        return item_count;
    }

    public void setItem_count(String item_count) {
        this.item_count = item_count;
    }
}
