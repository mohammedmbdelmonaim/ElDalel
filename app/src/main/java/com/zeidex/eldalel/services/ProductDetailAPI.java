package com.zeidex.eldalel.services;


import com.zeidex.eldalel.response.GetDetailProduct;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductDetailAPI {

    @GET("group/products/{id}/{group}/{storage_group}")
    Call<GetDetailProduct> getProductDetail(@Path("id") int id , @Path("group") int group , @Path("storage_group") int storage_group);

}