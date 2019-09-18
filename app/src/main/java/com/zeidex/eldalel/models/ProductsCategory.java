package com.zeidex.eldalel.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductsCategory implements Parcelable {
    String id;
    String imgUrl;
    String discount;
    String type;
    String name;
    String price;
    String price_before;
    String available_quantity;
    String like;
    String cart;

    public ProductsCategory(String id, String imgUrl, String discount, String type, String name, String price, String price_before, String like, String cart, String available_quantity) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.discount = discount;
        this.type = type;
        this.name = name;
        this.price = price;
        this.price_before = price_before;
        this.available_quantity = available_quantity;
        this.like = like;
        this.cart = cart;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getAvailable_quantity() {
        return available_quantity;
    }

    public void setAvailable_quantity(String available_quantity) {
        this.available_quantity = available_quantity;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getCart() {
        return cart;
    }

    public void setCart(String cart) {
        this.cart = cart;
    }

    protected ProductsCategory(Parcel in) {
        id = in.readString();
        imgUrl = in.readString();
        discount = in.readString();
        type = in.readString();
        name = in.readString();
        price = in.readString();
        price_before = in.readString();
        available_quantity = in.readString();
        like = in.readString();
        cart = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(imgUrl);
        dest.writeString(discount);
        dest.writeString(type);
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(price_before);
        dest.writeString(available_quantity);
        dest.writeString(like);
        dest.writeString(cart);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ProductsCategory> CREATOR = new Parcelable.Creator<ProductsCategory>() {
        @Override
        public ProductsCategory createFromParcel(Parcel in) {
            return new ProductsCategory(in);
        }

        @Override
        public ProductsCategory[] newArray(int size) {
            return new ProductsCategory[size];
        }
    };
}