package com.zeidex.eldalel.response;

import com.google.gson.annotations.Expose;
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
        @SerializedName("offers")
        @Expose
        private List<Offer> offers;

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

        public List<Offer> getOffers() {
            return offers;
        }

        public void setOffers(List<Offer> offers) {
            this.offers = offers;
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

    public class Offer {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("subcategory_id")
        @Expose
        private Integer subcategoryId;
        @SerializedName("subsubcategory_id")
        @Expose
        private Integer subsubcategoryId;
        @SerializedName("offer_number")
        @Expose
        private String offerNumber;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("name_ar")
        @Expose
        private String nameAr;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("old_price")
        @Expose
        private String oldPrice;
        @SerializedName("wholesale_price")
        @Expose
        private Double wholesalePrice;
        @SerializedName("wholesale_old_price")
        @Expose
        private Double wholesaleOldPrice;
        @SerializedName("shortDesc")
        @Expose
        private String shortDesc;
        @SerializedName("shortDescAr")
        @Expose
        private String shortDescAr;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("status_company")
        @Expose
        private Integer statusCompany;
        @SerializedName("stock")
        @Expose
        private Object stock;
        @SerializedName("live")
        @Expose
        private Integer live;
        @SerializedName("live_company")
        @Expose
        private Integer liveCompany;
        @SerializedName("discount_user")
        @Expose
        private String discountUser;
        @SerializedName("discount_company")
        @Expose
        private String discountCompany;
        @SerializedName("content_ar")
        @Expose
        private Object contentAr;
        @SerializedName("content_en")
        @Expose
        private Object contentEn;
        @SerializedName("limit_user_quantity")
        @Expose
        private Integer limitUserQuantity;
        @SerializedName("available_user_quantity")
        @Expose
        private Integer availableUserQuantity;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("available_quantity")
        @Expose
        private String availableQuantity;
        @SerializedName("photos")
        @Expose
        private List<Photo> photos;
        @SerializedName("favorite")
        @Expose
        private String favorite;
        @SerializedName("cart_status")
        @Expose
        private String cart;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getSubcategoryId() {
            return subcategoryId;
        }

        public void setSubcategoryId(Integer subcategoryId) {
            this.subcategoryId = subcategoryId;
        }

        public Integer getSubsubcategoryId() {
            return subsubcategoryId;
        }

        public void setSubsubcategoryId(Integer subsubcategoryId) {
            this.subsubcategoryId = subsubcategoryId;
        }

        public String getOfferNumber() {
            return offerNumber;
        }

        public void setOfferNumber(String offerNumber) {
            this.offerNumber = offerNumber;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNameAr() {
            return nameAr;
        }

        public void setNameAr(String nameAr) {
            this.nameAr = nameAr;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getOldPrice() {
            return oldPrice;
        }

        public void setOldPrice(String oldPrice) {
            this.oldPrice = oldPrice;
        }

        public Double getWholesalePrice() {
            return wholesalePrice;
        }

        public void setWholesalePrice(Double wholesalePrice) {
            this.wholesalePrice = wholesalePrice;
        }

        public Double getWholesaleOldPrice() {
            return wholesaleOldPrice;
        }

        public void setWholesaleOldPrice(Double wholesaleOldPrice) {
            this.wholesaleOldPrice = wholesaleOldPrice;
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

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getStatusCompany() {
            return statusCompany;
        }

        public void setStatusCompany(Integer statusCompany) {
            this.statusCompany = statusCompany;
        }

        public Object getStock() {
            return stock;
        }

        public void setStock(Object stock) {
            this.stock = stock;
        }

        public Integer getLive() {
            return live;
        }

        public void setLive(Integer live) {
            this.live = live;
        }

        public Integer getLiveCompany() {
            return liveCompany;
        }

        public void setLiveCompany(Integer liveCompany) {
            this.liveCompany = liveCompany;
        }

        public String getDiscountUser() {
            return discountUser;
        }

        public void setDiscountUser(String discountUser) {
            this.discountUser = discountUser;
        }

        public String getDiscountCompany() {
            return discountCompany;
        }

        public void setDiscountCompany(String discountCompany) {
            this.discountCompany = discountCompany;
        }

        public Object getContentAr() {
            return contentAr;
        }

        public void setContentAr(Object contentAr) {
            this.contentAr = contentAr;
        }

        public Object getContentEn() {
            return contentEn;
        }

        public void setContentEn(Object contentEn) {
            this.contentEn = contentEn;
        }

        public Integer getLimitUserQuantity() {
            return limitUserQuantity;
        }

        public void setLimitUserQuantity(Integer limitUserQuantity) {
            this.limitUserQuantity = limitUserQuantity;
        }

        public Integer getAvailableUserQuantity() {
            return availableUserQuantity;
        }

        public void setAvailableUserQuantity(Integer availableUserQuantity) {
            this.availableUserQuantity = availableUserQuantity;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getAvailableQuantity() {
            return availableQuantity;
        }

        public void setAvailableQuantity(String availableQuantity) {
            this.availableQuantity = availableQuantity;
        }

        public List<Photo> getPhotos() {
            return photos;
        }

        public void setPhotos(List<Photo> photos) {
            this.photos = photos;
        }

        public String getFavorite() {
            return favorite;
        }

        public void setFavorite(String favorite) {
            this.favorite = favorite;
        }

        public String getCart() {
            return cart;
        }

        public void setCart(String cart) {
            this.cart = cart;
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
