package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetWalletResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WalletApi {

    @GET("user/wallet")
    Call<GetWalletResponse> getWallet(@Query("token") String token);

    @GET("company/wallet")
    Call<GetWalletResponse> getWalletcompany(@Query("token") String token);
}