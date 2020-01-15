package com.zeidex.eldalel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteBasketResponse {

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
        @SerializedName("order_total_price")
        @Expose
        private Double orderTotalPrice;
        @SerializedName("empty_cart")
        @Expose
        private String emptyCart;
        @SerializedName("allCartItemsCount")
        @Expose
        private Integer allCartItemsCount;
        @SerializedName("taxes_percentage")
        @Expose
        private Double taxes_percentage;
        @SerializedName("total_without_taxes")
        @Expose
        private Double total_without_taxes;
        @SerializedName("taxes_amount")
        @Expose
        private Double taxes_amount;

        public Double getOrderTotalPrice() {
            return orderTotalPrice;
        }

        public void setOrderTotalPrice(Double orderTotalPrice) {
            this.orderTotalPrice = orderTotalPrice;
        }

        public String getEmptyCart() {
            return emptyCart;
        }

        public void setEmptyCart(String emptyCart) {
            this.emptyCart = emptyCart;
        }

        public Integer getAllCartItemsCount() {
            return allCartItemsCount;
        }

        public void setAllCartItemsCount(Integer allCartItemsCount) {
            this.allCartItemsCount = allCartItemsCount;
        }

        public Double getTaxes_percentage() {
            return taxes_percentage;
        }

        public void setTaxes_percentage(Double taxes_percentage) {
            this.taxes_percentage = taxes_percentage;
        }

        public Double getTotal_without_taxes() {
            return total_without_taxes;
        }

        public void setTotal_without_taxes(Double total_without_taxes) {
            this.total_without_taxes = total_without_taxes;
        }

        public Double getTaxes_amount() {
            return taxes_amount;
        }

        public void setTaxes_amount(Double taxes_amount) {
            this.taxes_amount = taxes_amount;
        }
    }
}
