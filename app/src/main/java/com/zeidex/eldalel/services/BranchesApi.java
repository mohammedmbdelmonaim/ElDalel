package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetBranches;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BranchesApi {

    @GET("subsidiary/showrooms/{id}")
    Call<GetBranches> getBranches(@Path("id") int id);
}
