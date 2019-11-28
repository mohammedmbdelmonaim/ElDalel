package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetStaticPageRespons;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PayStaticApi {

    @GET("page/job")
    Call<GetStaticPageRespons> getPayResponse();

    @GET("page/terms_conditions/policy")
    Call<GetStaticPageRespons> getConditionsResponse();

    @GET("page/privacy")
    Call<GetStaticPageRespons> getPrivacyResponse();

    @GET("page/cancel/policy")
    Call<GetStaticPageRespons> getCancelRequestResponse();

    @GET("page/return_switch/policy")
    Call<GetStaticPageRespons> getReturnResponse();

    @GET("page/shipping/delivery")
    Call<GetStaticPageRespons> getDeliveryResponse();

    @GET("page/warranty")
    Call<GetStaticPageRespons> getWarrantyResponse();

    @GET("page/technical/support")
    Call<GetStaticPageRespons> getSupportResponse();
}