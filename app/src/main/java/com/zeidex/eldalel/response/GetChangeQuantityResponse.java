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

}