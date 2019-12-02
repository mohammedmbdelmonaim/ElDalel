package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetBookingResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BookingResponse {

    @POST("is-paid")
    Call<GetBookingResponse> getBooking(@Body Map<String, String> partMap) ;

    @POST("company-is-paid")
    Call<GetBookingResponse> getBookingCompany(@Body Map<String, String> partMap) ;
}
