package com.zeidex.eldalel.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetFavorites {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("success")
        @Expose
        private String success;
        @SerializedName("favorites")
        @Expose
        private List<Favorite> favorites = null;
        @SerializedName("empty_favorite")
        @Expose
        private String emptyFavorite;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public List<Favorite> getFavorites() {
            return favorites;
        }

        public void setFavorites(List<Favorite> favorites) {
            this.favorites = favorites;
        }

        public String getEmptyFavorite() {
            return emptyFavorite;
        }

        public void setEmptyFavorite(String emptyFavorite) {
            this.emptyFavorite = emptyFavorite;
        }

    }

    public class Favorite {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("company_id")
        @Expose
        private Object companyId;
        @SerializedName("product_id")
        @Expose
        private Integer productId;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("product")
        @Expose
        private Product product;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Object getCompanyId() {
            return companyId;
        }

        public void setCompanyId(Object companyId) {
            this.companyId = companyId;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
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

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

    }

    public class Product {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("subcategory_id")
        @Expose
        private Integer subcategoryId;
        @SerializedName("subsubcategory_id")
        @Expose
        private Integer subsubcategoryId;
        @SerializedName("location_id")
        @Expose
        private Object locationId;
        @SerializedName("offer_number")
        @Expose
        private String offerNumber;
        @SerializedName("sku")
        @Expose
        private String sku;
        @SerializedName("group")
        @Expose
        private String group;
        @SerializedName("storage_group")
        @Expose
        private String storageGroup;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("name_ar")
        @Expose
        private String nameAr;
        @SerializedName("price")
        @Expose
        private Double price;
        @SerializedName("old_price")
        @Expose
        private Double oldPrice;
        @SerializedName("wholesaler_price")
        @Expose
        private Double wholesalePrice;
        @SerializedName("wholesaler_old_price")
        @Expose
        private Double wholesaleOldPrice;
        @SerializedName("weight")
        @Expose
        private Double weight;
        @SerializedName("cartDesc")
        @Expose
        private String cartDesc;
        @SerializedName("shortDesc")
        @Expose
        private String shortDesc;
        @SerializedName("shortDescAr")
        @Expose
        private String shortDescAr;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("stock")
        @Expose
        private Object stock;
        @SerializedName("live")
        @Expose
        private Integer live;
        @SerializedName("show_desc")
        @Expose
        private Integer showDesc;
        @SerializedName("unlimited")
        @Expose
        private Object unlimited;
        @SerializedName("voucher_discount")
        @Expose
        private Object voucherDiscount;
        @SerializedName("offer_content")
        @Expose
        private Object offerContent;
        @SerializedName("offer_content_en")
        @Expose
        private Object offerContentEn;
        @SerializedName("discount_content")
        @Expose
        private Object discountContent;
        @SerializedName("discount_content_en")
        @Expose
        private Object discountContentEn;
        @SerializedName("discount")
        @Expose
        private Integer discount;
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

        public Object getLocationId() {
            return locationId;
        }

        public void setLocationId(Object locationId) {
            this.locationId = locationId;
        }

        public String getOfferNumber() {
            return offerNumber;
        }

        public void setOfferNumber(String offerNumber) {
            this.offerNumber = offerNumber;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getStorageGroup() {
            return storageGroup;
        }

        public void setStorageGroup(String storageGroup) {
            this.storageGroup = storageGroup;
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

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Double getOldPrice() {
            return oldPrice;
        }

        public void setOldPrice(Double oldPrice) {
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

        public Double getWeight() {
            return weight;
        }

        public void setWeight(Double weight) {
            this.weight = weight;
        }

        public String getCartDesc() {
            return cartDesc;
        }

        public void setCartDesc(String cartDesc) {
            this.cartDesc = cartDesc;
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

        public Integer getShowDesc() {
            return showDesc;
        }

        public void setShowDesc(Integer showDesc) {
            this.showDesc = showDesc;
        }

        public Object getUnlimited() {
            return unlimited;
        }

        public void setUnlimited(Object unlimited) {
            this.unlimited = unlimited;
        }

        public Object getVoucherDiscount() {
            return voucherDiscount;
        }

        public void setVoucherDiscount(Object voucherDiscount) {
            this.voucherDiscount = voucherDiscount;
        }

        public Object getOfferContent() {
            return offerContent;
        }

        public void setOfferContent(Object offerContent) {
            this.offerContent = offerContent;
        }

        public Object getOfferContentEn() {
            return offerContentEn;
        }

        public void setOfferContentEn(Object offerContentEn) {
            this.offerContentEn = offerContentEn;
        }

        public Object getDiscountContent() {
            return discountContent;
        }

        public void setDiscountContent(Object discountContent) {
            this.discountContent = discountContent;
        }

        public Object getDiscountContentEn() {
            return discountContentEn;
        }

        public void setDiscountContentEn(Object discountContentEn) {
            this.discountContentEn = discountContentEn;
        }

        public Integer getDiscount() {
            return discount;
        }

        public void setDiscount(Integer discount) {
            this.discount = discount;
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
    }
}
