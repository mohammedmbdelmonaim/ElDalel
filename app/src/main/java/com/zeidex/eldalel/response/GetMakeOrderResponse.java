package com.zeidex.eldalel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetMakeOrderResponse {
    @Expose
    @SerializedName("success")
    boolean success;

    @Expose
    @SerializedName("error")
    String erroe;

    @Expose
    @SerializedName("payment_url")
    String payment_url;

    @Expose
    @SerializedName("p_id")
    String p_id;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErroe() {
        return erroe;
    }

    public void setErroe(String erroe) {
        this.erroe = erroe;
    }

    public String getPayment_url() {
        return payment_url;
    }

    public void setPayment_url(String payment_url) {
        this.payment_url = payment_url;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }
}
