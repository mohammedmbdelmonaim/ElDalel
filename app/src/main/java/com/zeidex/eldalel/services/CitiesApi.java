package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetCities;
import com.zeidex.eldalel.response.GetRegions;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CitiesApi {

    @GET("subsidiary/cities/{id}")
    Call<GetCities> getCities(@Path("id") int id);
}
