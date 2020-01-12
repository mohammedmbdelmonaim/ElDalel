package com.zeidex.eldalel.ui.salesmancustomer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zeidex.eldalel.response.GetUsersOrders;
import com.zeidex.eldalel.services.SalesmanApi;
import com.zeidex.eldalel.utils.APIClient;
import com.zeidex.eldalel.utils.PreferenceUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class SalesmanUsersViewModel extends ViewModel {

    private MutableLiveData<GetUsersOrders> mUserOrders;
    private MutableLiveData<Boolean> mHasOrders;
    private MutableLiveData<String> mError;

    public SalesmanUsersViewModel() {
        mUserOrders = new MutableLiveData<>();
        mError = new MutableLiveData<>();
        mHasOrders = new MutableLiveData<>();
    }

    public LiveData<GetUsersOrders> getUsersOrders() {
        return mUserOrders;
    }
    public LiveData<String> getError(){return  mError;}
    public LiveData<Boolean> getHasOrders(){return mHasOrders;}

    public void onErrorShowCompleted(){
        mError.setValue("");
    }

    public void fetchUsersOrders(String token) {
        SalesmanApi salesmanApi = APIClient.getClient(SERVER_API_TEST).create(SalesmanApi.class);
        salesmanApi.getUserOrders(token).enqueue(new Callback<GetUsersOrders>() {
            @Override
            public void onResponse(Call<GetUsersOrders> call, Response<GetUsersOrders> response) {
                if (response.body() != null && response.isSuccessful()) {
                    updateLiveData(response);
                    mError.setValue("");
                } else {
                    mError.setValue(response.message());
                }
            }

            @Override
            public void onFailure(Call<GetUsersOrders> call, Throwable t) {
                mError.setValue(t.getMessage());
            }
        });
    }

    private void updateLiveData(Response<GetUsersOrders> response) {
        if (response.body().getData().getOrders().size() > 0) {
            mUserOrders.setValue(response.body());
            mHasOrders.setValue(true);
        } else {
            mHasOrders.setValue(false);
        }
    }
}