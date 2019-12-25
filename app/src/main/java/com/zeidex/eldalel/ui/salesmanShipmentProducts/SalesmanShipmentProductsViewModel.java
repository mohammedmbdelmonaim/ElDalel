package com.zeidex.eldalel.ui.salesmanShipmentProducts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zeidex.eldalel.response.GetChangeSalesResponse;
import com.zeidex.eldalel.response.GetCompanyShipmentProducts;
import com.zeidex.eldalel.response.GetUserShipmentProducts;
import com.zeidex.eldalel.services.SalesmanApi;
import com.zeidex.eldalel.utils.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class SalesmanShipmentProductsViewModel extends ViewModel {

    private MutableLiveData<GetUserShipmentProducts> mUserShipmentProducts;
    private MutableLiveData<GetCompanyShipmentProducts> mCompanyShipmentProducts;
    private MutableLiveData<Boolean> mHasOrders;
    private MutableLiveData<Boolean> mShouldNavigateBack;
    private MutableLiveData<String> mError;
    private MutableLiveData<String> mMessage;
    private final SalesmanApi mSalesmanApi;

    public SalesmanShipmentProductsViewModel() {
        mSalesmanApi = APIClient.getClient(SERVER_API_TEST).create(SalesmanApi.class);
        mUserShipmentProducts = new MutableLiveData<>();
        mCompanyShipmentProducts = new MutableLiveData<>();
        mShouldNavigateBack = new MutableLiveData<>();
        mError = new MutableLiveData<>();
        mMessage = new MutableLiveData<>();
        mHasOrders = new MutableLiveData<>();
    }

    public LiveData<GetUserShipmentProducts> getUsersShipmentProducts() {
        return mUserShipmentProducts;
    }

    public LiveData<GetCompanyShipmentProducts> getCompanyShipmentProducts() {
        return mCompanyShipmentProducts;
    }

    public LiveData<String> getError() {
        return mError;
    }

    public LiveData<Boolean> getHasOrders() {
        return mHasOrders;
    }

    public LiveData<String> getSuccessMessage(){return mMessage; }

    public LiveData<Boolean> getShouldNavigateBack(){return mShouldNavigateBack;}


    public void changeQuantity(int position, int quantity, String note, String token, String orderId, String language, String type) {
        mSalesmanApi.changeQuantity(token, orderId, String.valueOf(quantity), note, language).enqueue(new Callback<GetChangeSalesResponse>() {
            @Override
            public void onResponse(Call<GetChangeSalesResponse> call, Response<GetChangeSalesResponse> response) {
                if (response.body() != null && response.body().getData().getSuccess().equals("true")) {
                    if (type.equals("company")) {
                        GetCompanyShipmentProducts getCompanyShipmentProducts = mCompanyShipmentProducts.getValue();
                        getCompanyShipmentProducts.getData().getOrders().get(position).setAvailableQuantity(quantity);
                        mCompanyShipmentProducts.setValue(getCompanyShipmentProducts);
                    } else if (type.equals("user")) {
                        GetUserShipmentProducts getUserShipmentProducts = mUserShipmentProducts.getValue();
                        getUserShipmentProducts.getData().getOrders().get(position).setAvailableQuantity(quantity);
                        mUserShipmentProducts.setValue(getUserShipmentProducts);
                    }
                    mShouldNavigateBack.setValue(true);
                    mMessage.setValue("change");
                } else {
                    mError.setValue(response.body().getData().getErrors().get(0));
                }
            }

            @Override
            public void onFailure(Call<GetChangeSalesResponse> call, Throwable t) {
                mError.setValue(t.getMessage());
            }
        });
    }

    public void cancelOrder(int position, String note, String token, String orderId, String language, String type){
        mSalesmanApi.cancelOrder(token, orderId, language, note).enqueue(new Callback<GetChangeSalesResponse>() {
            @Override
            public void onResponse(Call<GetChangeSalesResponse> call, Response<GetChangeSalesResponse> response) {
                if (response.body() != null && response.body().getData().getSuccess().equals("true")) {
                    if (type.equals("company")) {
                        GetCompanyShipmentProducts getCompanyShipmentProducts = mCompanyShipmentProducts.getValue();
                        getCompanyShipmentProducts.getData().getOrders().remove(position);
                        updateCompanyLiveData(getCompanyShipmentProducts);
                    } else if (type.equals("user")) {
                        GetUserShipmentProducts getUserShipmentProducts = mUserShipmentProducts.getValue();
                        getUserShipmentProducts.getData().getOrders().remove(position);
                        updateUserLiveData(getUserShipmentProducts);
                    }
                    mShouldNavigateBack.setValue(true);
                    mMessage.setValue("cancel");
                }else{
                    mError.setValue(response.body().getData().getErrors().get(0));
                }
            }

            @Override
            public void onFailure(Call<GetChangeSalesResponse> call, Throwable t) {
                mError.setValue(t.getMessage());
            }
        });
    }

    public void deliverOrder(int position, String token, String orderId, String language, String type){
        mSalesmanApi.deliverOrder(token, orderId, language).enqueue(new Callback<GetChangeSalesResponse>() {
            @Override
            public void onResponse(Call<GetChangeSalesResponse> call, Response<GetChangeSalesResponse> response) {
                if (response.body() != null && response.body().getData().getSuccess().equals("true")) {
                    if (type.equals("company")) {
                        GetCompanyShipmentProducts getCompanyShipmentProducts = mCompanyShipmentProducts.getValue();
                        getCompanyShipmentProducts.getData().getOrders().remove(position);
                        updateCompanyLiveData(getCompanyShipmentProducts);
                        mCompanyShipmentProducts.setValue(getCompanyShipmentProducts);
                    } else if (type.equals("user")) {
                        GetUserShipmentProducts getUserShipmentProducts = mUserShipmentProducts.getValue();
                        getUserShipmentProducts.getData().getOrders().remove(position);
                        updateUserLiveData(getUserShipmentProducts);
                    }
                    mMessage.setValue("success");
                } else{
                  mError.setValue(response.body().getData().getErrors().get(0));
                }
            }

            @Override
            public void onFailure(Call<GetChangeSalesResponse> call, Throwable t) {
                mError.setValue(t.getMessage());
            }
        });
    }

    public void onToastShowCompleted(){
        mMessage.setValue("");
    }

    public void onNavigateBackCompleted(){
        mShouldNavigateBack.setValue(false);
    }

    public void onErrorShowCompleted(){
        mError.setValue("");
    }

    public void fetchUserShipmentProducts(String shipmentId, String token) {
        mSalesmanApi.getUserShipmentProducts(shipmentId, token).enqueue(new Callback<GetUserShipmentProducts>() {
            @Override
            public void onResponse(Call<GetUserShipmentProducts> call, Response<GetUserShipmentProducts> response) {
                if (response.body() != null && response.body().getData().getSuccess().equals("true")) {
                    updateUserLiveData(response.body());
                    mError.setValue("");
                } else {
                    mError.setValue(response.body().getData().getErrors().get(0));
                }
            }

            @Override
            public void onFailure(Call<GetUserShipmentProducts> call, Throwable t) {
                mError.setValue("Please make sure you are connected to the Internet!");
            }
        });
    }

    public void fetchCompanyShipmentProducts(String shipmentId, String token) {
        mSalesmanApi.getCompanyShipmentProducts(shipmentId, token).enqueue(new Callback<GetCompanyShipmentProducts>() {
            @Override
            public void onResponse(Call<GetCompanyShipmentProducts> call, Response<GetCompanyShipmentProducts> response) {
                if (response.body() != null && response.isSuccessful()) {
                    updateCompanyLiveData(response.body());
                    mError.setValue("");
                } else {
                    mError.setValue(response.message());
                }
            }

            @Override
            public void onFailure(Call<GetCompanyShipmentProducts> call, Throwable t) {
                mError.setValue("Please make sure you are connected to the Internet!");
            }
        });
    }

    private void updateCompanyLiveData(GetCompanyShipmentProducts response) {
        if (response.getData().getOrders().size() > 0) {
            mCompanyShipmentProducts.setValue(response);
            mHasOrders.setValue(true);
        } else {
            mHasOrders.setValue(false);
        }
    }

    private void updateUserLiveData(GetUserShipmentProducts response) {
        if (response.getData().getOrders().size() > 0) {
            mUserShipmentProducts.setValue(response);
            mHasOrders.setValue(true);
        } else {
            mHasOrders.setValue(false);
        }
    }

    public void resetShipmentProducts() {
        mUserShipmentProducts = new MutableLiveData<>();
        mCompanyShipmentProducts = new MutableLiveData<>();
    }
}
