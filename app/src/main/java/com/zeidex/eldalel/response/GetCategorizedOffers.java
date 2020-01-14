package com.zeidex.eldalel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCategorizedOffers {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("products")
    @Expose
    private Products products;

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

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public class Products {

        @SerializedName("current_page")
        @Expose
        private Integer currentPage;
        @SerializedName("data")
        @Expose
        private List<Data> data = null;

        public Integer getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(Integer currentPage) {
            this.currentPage = currentPage;
        }

        public List<Data> getData() {
            return data;
        }

        public void setData(List<Data> data) {
            this.data = data;
        }

        public class Data {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("subcategory_id")
            @Expose
            private Integer subcategoryId;
            @SerializedName("subsubcategory_id")
            @Expose
            private Object subsubcategoryId;
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
            private Object oldPrice;
            @SerializedName("wholesaler_price")
            @Expose
            private Integer wholesalePrice;
            @SerializedName("wholesaler_old_price")
            @Expose
            private Object wholesaleOldPrice;
            @SerializedName("weight")
            @Expose
            private Object weight;
            @SerializedName("cartDesc")
            @Expose
            private Object cartDesc;
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
            private Object discount;
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
            private Integer availableQuantity;
            @SerializedName("favorite")
            @Expose
            private Integer favorite;
            @SerializedName("cart_status")
            @Expose
            private String cart;
            @SerializedName("photos")
            @Expose
            private List<Photo> photos = null;
            @SerializedName("images")
            @Expose
            private List<Object> images = null;
            @SerializedName("subcategory")
            @Expose
            private Subcategory subcategory;
            @SerializedName("optiongroups")
            @Expose
            private List<Optiongroup> optiongroups = null;
            @SerializedName("options")
            @Expose
            private List<Option> options = null;
            @SerializedName("quantities")
            @Expose
            private List<Object> quantities = null;

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

            public Object getSubsubcategoryId() {
                return subsubcategoryId;
            }

            public void setSubsubcategoryId(Object subsubcategoryId) {
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

            public Object getOldPrice() {
                return oldPrice;
            }

            public void setOldPrice(Object oldPrice) {
                this.oldPrice = oldPrice;
            }

            public Integer getWholesalePrice() {
                return wholesalePrice;
            }

            public void setWholesalePrice(Integer wholesalePrice) {
                this.wholesalePrice = wholesalePrice;
            }

            public Object getWholesaleOldPrice() {
                return wholesaleOldPrice;
            }

            public void setWholesaleOldPrice(Object wholesaleOldPrice) {
                this.wholesaleOldPrice = wholesaleOldPrice;
            }

            public Object getWeight() {
                return weight;
            }

            public void setWeight(Object weight) {
                this.weight = weight;
            }

            public Object getCartDesc() {
                return cartDesc;
            }

            public void setCartDesc(Object cartDesc) {
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

            public Object getDiscount() {
                return discount;
            }

            public void setDiscount(Object discount) {
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

            public Integer getAvailableQuantity() {
                return availableQuantity;
            }

            public void setAvailableQuantity(Integer availableQuantity) {
                this.availableQuantity = availableQuantity;
            }

            public Integer getFavorite() {
                return favorite;
            }

            public void setFavorite(Integer favorite) {
                this.favorite = favorite;
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

            public List<Object> getImages() {
                return images;
            }

            public void setImages(List<Object> images) {
                this.images = images;
            }

            public Subcategory getSubcategory() {
                return subcategory;
            }

            public void setSubcategory(Subcategory subcategory) {
                this.subcategory = subcategory;
            }

            public List<Optiongroup> getOptiongroups() {
                return optiongroups;
            }

            public void setOptiongroups(List<Optiongroup> optiongroups) {
                this.optiongroups = optiongroups;
            }

            public List<Option> getOptions() {
                return options;
            }

            public void setOptions(List<Option> options) {
                this.options = options;
            }

            public List<Object> getQuantities() {
                return quantities;
            }

            public void setQuantities(List<Object> quantities) {
                this.quantities = quantities;
            }

        }

        public class Option {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("optiongroup_id")
            @Expose
            private Integer optiongroupId;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("created_at")
            @Expose
            private String createdAt;
            @SerializedName("updated_at")
            @Expose
            private String updatedAt;
            @SerializedName("pivot")
            @Expose
            private Pivot_ pivot;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Integer getOptiongroupId() {
                return optiongroupId;
            }

            public void setOptiongroupId(Integer optiongroupId) {
                this.optiongroupId = optiongroupId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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

            public Pivot_ getPivot() {
                return pivot;
            }

            public void setPivot(Pivot_ pivot) {
                this.pivot = pivot;
            }

        }

        public class Optiongroup {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("created_at")
            @Expose
            private String createdAt;
            @SerializedName("updated_at")
            @Expose
            private String updatedAt;
            @SerializedName("pivot")
            @Expose
            private Pivot pivot;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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

            public Pivot getPivot() {
                return pivot;
            }

            public void setPivot(Pivot pivot) {
                this.pivot = pivot;
            }

        }

        public class Photo {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("product_id")
            @Expose
            private Integer productId;
            @SerializedName("option_id")
            @Expose
            private Integer optionId;
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

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Integer getProductId() {
                return productId;
            }

            public void setProductId(Integer productId) {
                this.productId = productId;
            }

            public Integer getOptionId() {
                return optionId;
            }

            public void setOptionId(Integer optionId) {
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

        public class Pivot {

            @SerializedName("product_id")
            @Expose
            private Integer productId;
            @SerializedName("optiongroup_id")
            @Expose
            private Integer optiongroupId;
            @SerializedName("created_at")
            @Expose
            private String createdAt;
            @SerializedName("updated_at")
            @Expose
            private String updatedAt;

            public Integer getProductId() {
                return productId;
            }

            public void setProductId(Integer productId) {
                this.productId = productId;
            }

            public Integer getOptiongroupId() {
                return optiongroupId;
            }

            public void setOptiongroupId(Integer optiongroupId) {
                this.optiongroupId = optiongroupId;
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

        public class Pivot_ {

            @SerializedName("product_id")
            @Expose
            private Integer productId;
            @SerializedName("option_id")
            @Expose
            private Integer optionId;
            @SerializedName("created_at")
            @Expose
            private String createdAt;
            @SerializedName("updated_at")
            @Expose
            private String updatedAt;
            @SerializedName("optiongroup_id")
            @Expose
            private Integer optiongroupId;
            @SerializedName("price_increment")
            @Expose
            private Integer priceIncrement;
            @SerializedName("other_info")
            @Expose
            private Object otherInfo;
            @SerializedName("live")
            @Expose
            private Integer live;
            @SerializedName("discount")
            @Expose
            private Integer discount;

            public Integer getProductId() {
                return productId;
            }

            public void setProductId(Integer productId) {
                this.productId = productId;
            }

            public Integer getOptionId() {
                return optionId;
            }

            public void setOptionId(Integer optionId) {
                this.optionId = optionId;
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

            public Integer getOptiongroupId() {
                return optiongroupId;
            }

            public void setOptiongroupId(Integer optiongroupId) {
                this.optiongroupId = optiongroupId;
            }

            public Integer getPriceIncrement() {
                return priceIncrement;
            }

            public void setPriceIncrement(Integer priceIncrement) {
                this.priceIncrement = priceIncrement;
            }

            public Object getOtherInfo() {
                return otherInfo;
            }

            public void setOtherInfo(Object otherInfo) {
                this.otherInfo = otherInfo;
            }

            public Integer getLive() {
                return live;
            }

            public void setLive(Integer live) {
                this.live = live;
            }

            public Integer getDiscount() {
                return discount;
            }

            public void setDiscount(Integer discount) {
                this.discount = discount;
            }

        }

        public class Subcategory {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("category_id")
            @Expose
            private Integer categoryId;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("name_ar")
            @Expose
            private String nameAr;
            @SerializedName("active")
            @Expose
            private Integer active;
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

            public Integer getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(Integer categoryId) {
                this.categoryId = categoryId;
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

            public Integer getActive() {
                return active;
            }

            public void setActive(Integer active) {
                this.active = active;
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
}