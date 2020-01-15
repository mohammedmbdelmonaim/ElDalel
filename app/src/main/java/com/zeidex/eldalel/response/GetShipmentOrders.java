package com.zeidex.eldalel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetShipmentOrders {
    @SerializedName("orders")
    @Expose
    private List<Order> orders = null;

    @SerializedName("payment_type")
    @Expose
    private Integer paymentType;

    @SerializedName("whole_price")
    @Expose
    private Double whole_price;

    @SerializedName("whole_price_with_taxes")
    @Expose
    private Double whole_price_with_taxes;

    @SerializedName("taxes_amount")
    @Expose
    private Double taxes_amount;
    @SerializedName("whole_original_price")
    @Expose
    private Double whole_original_price;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Double getWhole_price() {
        return whole_price;
    }

    public void setWhole_price(Double whole_price) {
        this.whole_price = whole_price;
    }

    public Double getWhole_price_with_taxes() {
        return whole_price_with_taxes;
    }

    public void setWhole_price_with_taxes(Double whole_price_with_taxes) {
        this.whole_price_with_taxes = whole_price_with_taxes;
    }

    public Double getTaxes_amount() {
        return taxes_amount;
    }

    public void setTaxes_amount(Double taxes_amount) {
        this.taxes_amount = taxes_amount;
    }

    public Double getWhole_original_price() {
        return whole_original_price;
    }

    public void setWhole_original_price(Double whole_original_price) {
        this.whole_original_price = whole_original_price;
    }

    public class Order {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("status_code")
        @Expose
        private Integer status_code;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("total_price_with_tax")
        @Expose
        private Double totalPriceWithTax;
        @SerializedName("product_price")
        @Expose
        private Double productPrice;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("name_ar")
        @Expose
        private String nameAr;
        @SerializedName("photos")
        @Expose
        private List<Photo> photos = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getStatus_code() {
            return status_code;
        }

        public void setStatus_code(Integer status_code) {
            this.status_code = status_code;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Double getTotalPriceWithTax() {
            return totalPriceWithTax;
        }

        public void setTotalPriceWithTax(Double totalPriceWithTax) {
            this.totalPriceWithTax = totalPriceWithTax;
        }

        public Double getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(Double productPrice) {
            this.productPrice = productPrice;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
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

        public List<Photo> getPhotos() {
            return photos;
        }

        public void setPhotos(List<Photo> photos) {
            this.photos = photos;
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
}
