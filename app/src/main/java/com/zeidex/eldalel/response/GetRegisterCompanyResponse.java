package com.zeidex.eldalel.response;

import com.google.gson.annotations.SerializedName;

public class GetRegisterCompanyResponse {
    @SerializedName("success")
    String success;

    @SerializedName("sms_code")
    String sms_code;

    @SerializedName("error")
    String error;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getSms_code() {
        return sms_code;
    }

    public void setSms_code(String sms_code) {
        this.sms_code = sms_code;
    }
}
