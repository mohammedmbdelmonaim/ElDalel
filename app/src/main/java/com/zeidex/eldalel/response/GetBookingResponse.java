package com.zeidex.eldalel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetBookingResponse {

    @Expose
    @SerializedName("paid")
    boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
