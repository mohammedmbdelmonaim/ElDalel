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

    @SerializedName("discount")
    @Expose
    private Double discount;

    @SerializedName("delivery_fees")
    @Expose
    private Double delivery_fees;

    @SerializedName("taxes_amount")
    @Expose
    private Double taxes_amount;
    @SerializedName("net_price")
    @Expose
    private Double net_price;
    @SerializedName("total_price")
    @Expose
    private Double total_price;

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


    public Double getTaxes_amount() {
        return taxes_amount;
    }

    public void setTaxes_amount(Double taxes_amount) {
        this.taxes_amount = taxes_amount;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getDelivery_fees() {
        return delivery_fees;
    }

    public void setDelivery_fees(Double delivery_fees) {
        this.delivery_fees = delivery_fees;
    }

    public Double getNet_price() {
        return net_price;
    }

    public void setNet_price(Double net_price) {
        this.net_price = net_price;
    }

    public Double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
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
