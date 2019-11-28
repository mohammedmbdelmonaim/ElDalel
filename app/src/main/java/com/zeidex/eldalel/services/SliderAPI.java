package com.zeidex.eldalel.services;


import com.zeidex.eldalel.response.GetSliders;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SliderAPI {

    @GET("page/slider")
    Call<GetSliders> getSliders();


}