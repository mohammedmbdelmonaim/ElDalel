package com.zeidex.eldalel.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetHomeProducts {
    @SerializedName("code")
    String code;

    @SerializedName("message")
    String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    @SerializedName("data")
    Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        @SerializedName("categories")
        List<Category> categories;
        @SerializedName("count_cart")
        Integer countCart;

        public List<Category> getCategories() {
            return categories;
        }

        public void setCategories(List<Category> categories) {
            this.categories = categories;
        }

        public Integer getCountCart() {
            return countCart;
        }

        public void setCountCart(Integer countCart) {
            this.countCart = countCart;
        }
    }

    public class Category{
        @SerializedName("id")
        String id;

        @SerializedName("name")
        String name;

        @SerializedName("name_ar")
        String name_ar;

        @SerializedName("products")
        List<Product> products;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName_ar() {
            return name_ar;
        }

        public void setName_ar(String name_ar) {
            this.name_ar = name_ar;
        }

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }
    }

    public class Product{
        @SerializedName("id")
        String id;

        @SerializedName("subcategory_id")
        String subcategory_id;

        @SerializedName("name")
        String name;

        @SerializedName("name_ar")
        String name_ar;

        @SerializedName("price")
        String price;

        @SerializedName("old_price")
        String old_price;

        @SerializedName("shortDesc")
        String shortDesc;

        @SerializedName("shortDescAr")
        String shortDescAr;

        @SerializedName("status")
        String status;

        @SerializedName("offer_number")
        String offer_number;

        @SerializedName("group")
        String group;

        @SerializedName("sku")
        String sku;

        @SerializedName("storage_group")
        String storage_group;

        @SerializedName("favorite")
        String favorite;

        @SerializedName("discount")
        String discount;

        @SerializedName("available_quantity")
        String available_quantity;

        @SerializedName("cart_status")
        String cart;

        @SerializedName("photos")
        List<Photo> photos;

        public String getFavorite() {
            return favorite;
        }

        public void setFavorite(String favorite) {
            this.favorite = favorite;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSubcategory_id() {
            return subcategory_id;
        }

        public void setSubcategory_id(String subcategory_id) {
            this.subcategory_id = subcategory_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName_ar() {
            return name_ar;
        }

        public void setName_ar(String name_ar) {
            this.name_ar = name_ar;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getOld_price() {
            return old_price;
        }

        public void setOld_price(String old_price) {
            this.old_price = old_price;
        }

        public String getShortDesc() {
            return shortDesc;
        }

        public void setShortDesc(String shortDesc) {
            this.shortDesc = shortDesc;
        }

        public String getShortDescAr() {
            return shortDescAr;
        }

        public void setShortDescAr(String shortDescAr) {
            this.shortDescAr = shortDescAr;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getOffer_number() {
            return offer_number;
        }

        public void setOffer_number(String offer_number) {
            this.offer_number = offer_number;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getStorage_group() {
            return storage_group;
        }

        public void setStorage_group(String storage_group) {
            this.storage_group = storage_group;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getAvailable_quantity() {
            return available_quantity;
        }

        public void setAvailable_quantity(String available_quantity) {
            this.available_quantity = available_quantity;
        }

        public String getCart() {
            return cart;
        }

        public void setCart(String cart) {
            this.cart = cart;
        }

        public List<Photo> getPhotos() {
            return photos;
        }

        public void setPhotos(List<Photo> photos) {
            this.photos = photos;
        }
    }

    public class Photo{
        @SerializedName("filename")
        String filename;

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }
    }
}
