package com.zeidex.eldalel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCheckCouponResponse {
    @Expose
    @SerializedName("status")
    String status;

    @Expose
    @SerializedName("error")
    String error;

    @SerializedName("data")
    @Expose
    private Data data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("start_date")
        @Expose
        private String startDate;
        @SerializedName("end_date")
        @Expose
        private String endDate;
        @SerializedName("amount_type")
        @Expose
        private String amountType;
        @SerializedName("amount")
        @Expose
        private Integer amount;
        @SerializedName("active")
        @Expose
        private Integer active;
        @SerializedName("min_price")
        @Expose
        private Integer minPrice;
        @SerializedName("limit_all")
        @Expose
        private Integer limitAll;
        @SerializedName("limit_user")
        @Expose
        private Integer limitUser;
        @SerializedName("exclude_sale_products")
        @Expose
        private Integer excludeSaleProducts;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getAmountType() {
            return amountType;
        }

        public void setAmountType(String amountType) {
            this.amountType = amountType;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public Integer getActive() {
            return active;
        }

        public void setActive(Integer active) {
            this.active = active;
        }

        public Integer getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(Integer minPrice) {
            this.minPrice = minPrice;
        }

        public Integer getLimitAll() {
            return limitAll;
        }

        public void setLimitAll(Integer limitAll) {
            this.limitAll = limitAll;
        }

        public Integer getLimitUser() {
            return limitUser;
        }

        public void setLimitUser(Integer limitUser) {
            this.limitUser = limitUser;
        }

        public Integer getExcludeSaleProducts() {
            return excludeSaleProducts;
        }

        public void setExcludeSaleProducts(Integer excludeSaleProducts) {
            this.excludeSaleProducts = excludeSaleProducts;
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
