package com.zeidex.eldalel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDeliveryFee {

@SerializedName("data")
@Expose
private Integer data;
@SerializedName("status")
@Expose
private Boolean status;

public Integer getData() {
return data;
}

public void setData(Integer data) {
this.data = data;
}

public Boolean getStatus() {
return status;
}

public void setStatus(Boolean status) {
this.status = status;
}

}