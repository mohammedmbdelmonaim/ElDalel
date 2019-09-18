package com.zeidex.eldalel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetBasketProducts {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("cartCount")
    @Expose
    private String cartCount;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCartCount() {
        return cartCount;
    }

    public void setCartCount(String cartCount) {
        this.cartCount = cartCount;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }


    public class Photo {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("option_id")
        @Expose
        private String optionId;
        @SerializedName("filename")
        @Expose
        private String filename;
        @SerializedName("mime")
        @Expose
        private String mime;
        @SerializedName("original_filename")
        @Expose
        private String originalFilename;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getOptionId() {
            return optionId;
        }

        public void setOptionId(String optionId) {
            this.optionId = optionId;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getMime() {
            return mime;
        }

        public void setMime(String mime) {
            this.mime = mime;
        }

        public String getOriginalFilename() {
            return originalFilename;
        }

        public void setOriginalFilename(String originalFilename) {
            this.originalFilename = originalFilename;
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


    public class Product {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("subcategory_id")
        @Expose
        private String subcategoryId;
        @SerializedName("subsubcategory_id")
        @Expose
        private String subsubcategoryId;
        @SerializedName("location_id")
        @Expose
        private String locationId;
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
        private String price;
        @SerializedName("old_price")
        @Expose
        private String oldPrice;
        @SerializedName("wholesale_price")
        @Expose
        private String wholesalePrice;
        @SerializedName("wholesale_old_price")
        @Expose
        private String wholesaleOldPrice;
        @SerializedName("weight")
        @Expose
        private String weight;
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
        private String status;
        @SerializedName("stock")
        @Expose
        private String stock;
        @SerializedName("live")
        @Expose
        private String live;
        @SerializedName("show_desc")
        @Expose
        private String showDesc;
        @SerializedName("unlimited")
        @Expose
        private String unlimited;
        @SerializedName("voucher_discount")
        @Expose
        private String voucherDiscount;
        @SerializedName("offer_content")
        @Expose
        private String offerContent;
        @SerializedName("offer_content_en")
        @Expose
        private String offerContentEn;
        @SerializedName("discount_content")
        @Expose
        private String discountContent;
        @SerializedName("discount_content_en")
        @Expose
        private String discountContentEn;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("content_ar")
        @Expose
        private String contentAr;
        @SerializedName("content_en")
        @Expose
        private String contentEn;
        @SerializedName("limit_user_quantity")
        @Expose
        private String limitUserQuantity;
        @SerializedName("available_user_quantity")
        @Expose
        private String availableUserQuantity;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("cart_id")
        @Expose
        private String cartId;
        @SerializedName("itemCount")
        @Expose
        private String itemCount;
        @SerializedName("photos")
        @Expose
        private List<Photo> photos = null;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSubcategoryId() {
            return subcategoryId;
        }

        public void setSubcategoryId(String subcategoryId) {
            this.subcategoryId = subcategoryId;
        }

        public String getSubsubcategoryId() {
            return subsubcategoryId;
        }

        public void setSubsubcategoryId(String subsubcategoryId) {
            this.subsubcategoryId = subsubcategoryId;
        }

        public String getLocationId() {
            return locationId;
        }

        public void setLocationId(String locationId) {
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

        public String getWholesalePrice() {
            return wholesalePrice;
        }

        public void setWholesalePrice(String wholesalePrice) {
            this.wholesalePrice = wholesalePrice;
        }

        public String getWholesaleOldPrice() {
            return wholesaleOldPrice;
        }

        public void setWholesaleOldPrice(String wholesaleOldPrice) {
            this.wholesaleOldPrice = wholesaleOldPrice;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public String getLive() {
            return live;
        }

        public void setLive(String live) {
            this.live = live;
        }

        public String getShowDesc() {
            return showDesc;
        }

        public void setShowDesc(String showDesc) {
            this.showDesc = showDesc;
        }

        public String getUnlimited() {
            return unlimited;
        }

        public void setUnlimited(String unlimited) {
            this.unlimited = unlimited;
        }

        public String getVoucherDiscount() {
            return voucherDiscount;
        }

        public void setVoucherDiscount(String voucherDiscount) {
            this.voucherDiscount = voucherDiscount;
        }

        public String getOfferContent() {
            return offerContent;
        }

        public void setOfferContent(String offerContent) {
            this.offerContent = offerContent;
        }

        public String getOfferContentEn() {
            return offerContentEn;
        }

        public void setOfferContentEn(String offerContentEn) {
            this.offerContentEn = offerContentEn;
        }

        public String getDiscountContent() {
            return discountContent;
        }

        public void setDiscountContent(String discountContent) {
            this.discountContent = discountContent;
        }

        public String getDiscountContentEn() {
            return discountContentEn;
        }

        public void setDiscountContentEn(String discountContentEn) {
            this.discountContentEn = discountContentEn;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getContentAr() {
            return contentAr;
        }

        public void setContentAr(String contentAr) {
            this.contentAr = contentAr;
        }

        public String getContentEn() {
            return contentEn;
        }

        public void setContentEn(String contentEn) {
            this.contentEn = contentEn;
        }

        public String getLimitUserQuantity() {
            return limitUserQuantity;
        }

        public void setLimitUserQuantity(String limitUserQuantity) {
            this.limitUserQuantity = limitUserQuantity;
        }

        public String getAvailableUserQuantity() {
            return availableUserQuantity;
        }

        public void setAvailableUserQuantity(String availableUserQuantity) {
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

        public String getCartId() {
            return cartId;
        }

        public void setCartId(String cartId) {
            this.cartId = cartId;
        }

        public String getItemCount() {
            return itemCount;
        }

        public void setItemCount(String itemCount) {
            this.itemCount = itemCount;
        }

        public List<Photo> getPhotos() {
            return photos;
        }

        public void setPhotos(List<Photo> photos) {
            this.photos = photos;
        }

    }

}