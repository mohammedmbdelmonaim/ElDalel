package com.zeidex.eldalel.services;


import com.zeidex.eldalel.response.GetCheckCouponResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CheckCouponAPI {

    @GET("coupon/{code}")
    Call<GetCheckCouponResponse> checkCouponResponse(@Path("code") String code);
}