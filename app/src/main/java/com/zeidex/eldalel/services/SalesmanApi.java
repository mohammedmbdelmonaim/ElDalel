package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetCompaniesOrders;
import com.zeidex.eldalel.response.GetCompanyShipmentProducts;
import com.zeidex.eldalel.response.GetProducts;
import com.zeidex.eldalel.response.GetUserShipmentProducts;
import com.zeidex.eldalel.response.GetUsersOrders;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SalesmanApi {
    @GET("salesman/companies/orders")
    Call<GetCompaniesOrders> getCompanyOrders(@Query("token") String token);

    @GET("salesman/users/orders")
    Call<GetUsersOrders> getUserOrders(@Query("token") String token);

    @GET("salesman/user/shipment/orders/{shipment_id}")
    Call<GetUserShipmentProducts> getUserShipmentProducts(@Path("shipment_id") String shipmentId, @Query("token") String token);

    @GET("salesman/company/shipment/orders/{shipment_id}")
    Call<GetCompanyShipmentProducts> getCompanyShipmentProducts(@Path("shipment_id") String shipmentId, @Query("token") String token);

}
