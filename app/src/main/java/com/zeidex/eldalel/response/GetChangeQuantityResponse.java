package com.zeidex.eldalel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetChangeQuantityResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("success_message")
    @Expose
    private String successMessage;
    @SerializedName("prodcutQuantity")
    @Expose
    private String prodcutQuantity;
    @SerializedName("allCartItemsCount")
    @Expose
    private String allCartItemsCount;
    @SerializedName("total_price")
    @Expose
    private String total_price;
    @SerializedName("taxes_percentage")
    @Expose
    private Double taxes_percentage;
    @SerializedName("total_without_taxes")
    @Expose
    private Double total_without_taxes;
    @SerializedName("taxes_amount")
    @Expose
    private Double taxes_amount;

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

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getProdcutQuantity() {
        return prodcutQuantity;
    }

    public void setProdcutQuantity(String prodcutQuantity) {
        this.prodcutQuantity = prodcutQuantity;
    }

    public String getAllCartItemsCount() {
        return allCartItemsCount;
    }

    public void setAllCartItemsCount(String allCartItemsCount) {
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

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }
}