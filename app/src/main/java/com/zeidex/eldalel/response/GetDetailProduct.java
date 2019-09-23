package com.zeidex.eldalel.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetDetailProduct {
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
        @SerializedName("product")
        Product product;

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
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
        Double price;

        @SerializedName("old_price")
        Double old_price;

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

        @SerializedName("discount")
        String discount;

        @SerializedName("photos")
        List<Photo> photos;

        @SerializedName("subcategory")
        SubCategory subcategory;

        @SerializedName("optiongroups")
        List<OptionGroups> optiongroups;

        @SerializedName("colors")
        List<Color> colors;

        @SerializedName("cart")
        Integer cart;

        @SerializedName("available_quantity")
        Integer availableQuantity;

        @SerializedName("capacities")
        List<String> capacities;

        public List<Color> getColors() {
            return colors;
        }

        public void setColors(List<Color> colors) {
            this.colors = colors;
        }

        public List<String> getCapacities() {
            return capacities;
        }

        public void setCapacities(List<String> capacities) {
            this.capacities = capacities;
        }

        public List<OptionGroups> getOptiongroups() {
            return optiongroups;
        }

        public void setOptiongroups(List<OptionGroups> optiongroups) {
            this.optiongroups = optiongroups;
        }

        public SubCategory getSubcategory() {
            return subcategory;
        }

        public void setSubcategory(SubCategory subcategory) {
            this.subcategory = subcategory;
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

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Double getOld_price() {
            return old_price;
        }

        public void setOld_price(Double old_price) {
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

        public Integer getCart() {
            return cart;
        }

        public void setCart(Integer cart) {
            this.cart = cart;
        }

        public Integer getAvailableQuantity() {
            return availableQuantity;
        }

        public void setAvailableQuantity(Integer availableQuantity) {
            this.availableQuantity = availableQuantity;
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

    public class Color{
        @SerializedName("name")
        String name;

        @SerializedName("product_id")
        String product_id;

        @SerializedName("photo")
        String photo;

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }
    }

    public class SubCategory{
        @SerializedName("name")
        String name;

        @SerializedName("name_ar")
        String name_ar;

        @SerializedName("id")
        String id;

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public class OptionGroups{
        @SerializedName("id")
        String id;

        @SerializedName("name")
        String name;

        @SerializedName("features")
        List<String> features;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<String> getFeatures() {
            return features;
        }

        public void setFeatures(List<String> features) {
            this.features = features;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
