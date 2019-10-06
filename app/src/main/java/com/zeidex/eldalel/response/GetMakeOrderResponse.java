package com.zeidex.eldalel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetMakeOrderResponse {
    @Expose
    @SerializedName("success")
    String success;

    @Expose
    @SerializedName("error")
    String erroe;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getErroe() {
        return erroe;
    }

    public void setErroe(String erroe) {
        this.erroe = erroe;
    }
}
