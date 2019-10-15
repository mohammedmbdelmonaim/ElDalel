package com.zeidex.eldalel;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.adapters.OrdersAdapter;
import com.zeidex.eldalel.response.GetShipments;
import com.zeidex.eldalel.services.ShipmentsApi;
import com.zeidex.eldalel.utils.APIClient;
import com.zeidex.eldalel.utils.Animatoo;
import com.zeidex.eldalel.utils.PreferenceUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class OrdersActivity extends BaseActivity implements OrdersAdapter.OrdersOperation {
    @BindView(R.id.activity_orders_recycler)
    RecyclerView activity_orders_recycler;

    @BindView(R.id.activity_orders_number_text)
    AppCompatTextView activity_orders_number_text;

    @BindView(R.id.shipments_recycler_noitems)
    AppCompatTextView shipments_recycler_noitems;

    OrdersAdapter ordersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        ButterKnife.bind(this);
        showDialog();
        initializeRecycler();
        onLoadShipments();
    }

    @OnClick(R.id.item_orders_back)
    public void onBack() {
        onBackPressed();
    }

    public void initializeRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        activity_orders_recycler.setLayoutManager(layoutManager);
        activity_orders_recycler.setItemAnimator(new DefaultItemAnimator());
    }

    String token;
    ArrayList<GetShipments.Shipment> shipments;

    public void onLoadShipments() {
        reloadDialog.show();if (PreferenceUtils.getCompanyLogin(this)) {
            token = PreferenceUtils.getCompanyToken(this);
        } else if (PreferenceUtils.getUserLogin(this)) {
            token = PreferenceUtils.getUserToken(this);
        }
        ShipmentsApi shipmentsApi = APIClient.getClient(SERVER_API_TEST).create(ShipmentsApi.class);
        Call<GetShipments> getShipmentsCall = shipmentsApi.getShipments(token);
        getShipmentsCall.enqueue(new Callback<GetShipments>() {
            @Override
            public void onResponse(Call<GetShipments> call, Response<GetShipments> response) {
                GetShipments getShipments = response.body();
                shipments = (ArrayList<GetShipments.Shipment>) getShipments.getShipments();
                if (shipments.size() == 0){
                    shipments_recycler_noitems.setVisibility(View.VISIBLE);
                    reloadDialog.dismiss();
                    return;
                }

                activity_orders_number_text.setText(getShipments.getShipments().size()+"");

                ordersAdapter = new OrdersAdapter(OrdersActivity.this , shipments);
                ordersAdapter.setOrdersOperation(OrdersActivity.this);
                activity_orders_recycler.setAdapter(ordersAdapter);
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetShipments> call, Throwable t) {
                Toasty.error(OrdersActivity.this, getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    @Override
    public void onClickOrder(int shipment_id) {
        startActivity(new Intent(this, ElementsOrderActivity.class).putExtra("shipment_id" , shipment_id));
        Animatoo.animateSwipeLeft(this);
    }

    Dialog reloadDialog;

    private void showDialog() {
        reloadDialog = new Dialog(OrdersActivity.this);
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
