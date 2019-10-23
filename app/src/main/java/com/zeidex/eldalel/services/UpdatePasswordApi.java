package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetUpdatePasswordResponse;

import retrofit2.Call;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface UpdatePasswordApi {

    @PUT("user/update/account/info")
    Call<GetUpdatePasswordResponse> editPasswordsApi(@Query("token") String token , @Query("language") String language , @Query("old_password") String old_password , @Query("password") String password , @Query("password_confirmation") String password_confirmation , @Query("email") String email );

    @PUT("company/update/account/info")
    Call<GetUpdatePasswordResponse> editPasswordsApicompany(@Query("token") String token , @Query("language") String language , @Query("old_password") String old_password , @Query("password") String password , @Query("password_confirmation") String password_confirmation , @Query("email") String email );
}
