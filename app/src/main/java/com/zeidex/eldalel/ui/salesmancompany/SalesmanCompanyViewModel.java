package com.zeidex.eldalel.ui.salesmancompany;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zeidex.eldalel.response.GetCompaniesOrders;
import com.zeidex.eldalel.response.GetUsersOrders;
import com.zeidex.eldalel.services.SalesmanApi;
import com.zeidex.eldalel.utils.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class SalesmanCompanyViewModel extends ViewModel {

    private MutableLiveData<GetCompaniesOrders> mCompaniesOrders;
    private MutableLiveData<Boolean> mHasOrders;
    private MutableLiveData<String> mError;

    public SalesmanCompanyViewModel() {
        mCompaniesOrders = new MutableLiveData<>();
        mError = new MutableLiveData<>();
        mHasOrders = new MutableLiveData<>();
    }

    public LiveData<GetCompaniesOrders> getCompaniesOrders() {
        return mCompaniesOrders;
    }
    public LiveData<String> getError(){return  mError;}
    public LiveData<Boolean> getHasOrders(){return mHasOrders;}

    public void fetchCompaniesOrders(String token) {
        SalesmanApi salesmanApi = APIClient.getClient(SERVER_API_TEST).create(SalesmanApi.class);
        salesmanApi.getCompanyOrders(token).enqueue(new Callback<GetCompaniesOrders>() {
            @Override
            public void onResponse(Call<GetCompaniesOrders> call, Response<GetCompaniesOrders> response) {
                if (response.body() != null && response.isSuccessful()) {
                    updateLiveData(response);
                    mError.setValue("");
                } else {
                    mError.setValue(response.message());
                }
            }

            @Override
            public void onFailure(Call<GetCompaniesOrders> call, Throwable t) {
                mError.setValue("Please make sure you are connected to the Internet!");
            }
        });
    }

    private void updateLiveData(Response<GetCompaniesOrders> response) {
        if (response.body().getData().getOrders().size() > 0) {
            mCompaniesOrders.setValue(response.body());
            mHasOrders.setValue(true);
        } else {
            mHasOrders.setValue(false);
        }
    }
}