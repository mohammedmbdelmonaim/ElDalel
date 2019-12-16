package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetRatingResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AddRatingApi {

    @POST("user-rate/{id}")
    Call<GetRatingResponse> getUserRating(@Path("id") int id , @Body Map<String, String> partMap);


    @POST("company-rate/{id}")
    Call<GetRatingResponse> getCompanyRating(@Path("id") int id , @Body Map<String, String> partMap);
}
//////////////