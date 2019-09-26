package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetCities;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BranchesApi {

    @GET("subsidiary/showrooms/{id}")
    Call<GetCities> getBranches(@Path("id") int id);
}
