package com.zeidex.eldalel.response;

import com.google.gson.annotations.SerializedName;

public class GetAddToFavouriteResponse {
    @SerializedName("status")
    String status;

    @SerializedName("code")
    String code;

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
}
