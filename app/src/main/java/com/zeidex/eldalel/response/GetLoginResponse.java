package com.zeidex.eldalel.response;

import com.google.gson.annotations.SerializedName;

public class GetLoginResponse {
    @SerializedName("success")
    String success;

    @SerializedName("tokenUser")
    String tokenUser;

    @SerializedName("tokenCompany")
    String tokenCompany;

    @SerializedName("message")
    String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTokenUser() {
        return tokenUser;
    }

    public void setTokenUser(String tokenUser) {
        this.tokenUser = tokenUser;
    }

    public String getTokenCompany() {
        return tokenCompany;
    }

    public void setTokenCompany(String tokenCompany) {
        this.tokenCompany = tokenCompany;
    }
}
