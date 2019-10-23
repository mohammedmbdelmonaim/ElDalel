package com.zeidex.eldalel;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.adapters.OperationsWalletAdapter;
import com.zeidex.eldalel.response.GetWalletResponse;
import com.zeidex.eldalel.services.WalletApi;
import com.zeidex.eldalel.utils.APIClient;
import com.zeidex.eldalel.utils.PreferenceUtils;
import com.zeidex.eldalel.utils.PriceFormatter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class WalletActivity extends AppCompatActivity {
    @BindView(R.id.activity_operations_wallet_recycler)
    RecyclerView activity_operations_wallet_recycler;

    @BindView(R.id.activity_operations_wallet_no)
    AppCompatTextView activity_operations_wallet_no;

    @BindView(R.id.activity_operations_wallet_total_result)
    AppCompatTextView activity_operations_wallet_total_result;

    @BindView(R.id.activity_operations_wallet_total_label)
    AppCompatTextView activity_operations_wallet_total_label;

    OperationsWalletAdapter operationsWalletAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        ButterKnife.bind(this);
        showDialog();
        initializeRecycler();
        onLoadShipments();
    }
    @OnClick(R.id.item_wallet_back)
    public void onBack() {
        onBackPressed();
    }


    public void initializeRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        activity_operations_wallet_recycler.setLayoutManager(layoutManager);
        activity_operations_wallet_recycler.setItemAnimator(new DefaultItemAnimator());
    }

    String token;
    ArrayList<GetWalletResponse.Datum> operations_wallet;

    public void onLoadShipments() {
        reloadDialog.show();
        if (PreferenceUtils.getCompanyLogin(this)) {
            token = PreferenceUtils.getCompanyToken(this);
        } else if (PreferenceUtils.getUserLogin(this)) {
            token = PreferenceUtils.getUserToken(this);
        }
        WalletApi walletApi = APIClient.getClient(SERVER_API_TEST).create(WalletApi.class);
        Call<GetWalletResponse> getWalletResponseCall;
        if (PreferenceUtils.getCompanyLogin(WalletActivity.this)) {
            getWalletResponseCall = walletApi.getWalletcompany(token);
        }else {
            getWalletResponseCall = walletApi.getWallet(token);
        }
        getWalletResponseCall.enqueue(new Callback<GetWalletResponse>() {
            @Override
            public void onResponse(Call<GetWalletResponse> call, Response<GetWalletResponse> response) {
                GetWalletResponse getWalletResponse = response.body();
                operations_wallet = (ArrayList<GetWalletResponse.Datum>) getWalletResponse.getData();
                if (operations_wallet.size() == 0){
                    activity_operations_wallet_no.setVisibility(View.VISIBLE);
                    activity_operations_wallet_total_label.setVisibility(View.GONE);
                    activity_operations_wallet_total_result.setVisibility(View.GONE);
                    reloadDialog.dismiss();
                    return;
                }
                activity_operations_wallet_total_result.setText(PriceFormatter.toDecimalRsString(getWalletResponse.getTotal() , WalletActivity.this));
                operationsWalletAdapter = new OperationsWalletAdapter(WalletActivity.this , operations_wallet);
                activity_operations_wallet_recycler.setAdapter(operationsWalletAdapter);
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetWalletResponse> call, Throwable t) {
                Toasty.error(WalletActivity.this, getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    Dialog reloadDialog;

    private void showDialog() {
        reloadDialog = new Dialog(WalletActivity.this);
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}

