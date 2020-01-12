package com.zeidex.eldalel;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.adapters.ElementsOrdersAdapter;
import com.zeidex.eldalel.response.GetShipmentOrders;
import com.zeidex.eldalel.services.ShipmentOrdersApi;
import com.zeidex.eldalel.utils.APIClient;
import com.zeidex.eldalel.utils.PreferenceUtils;
import com.zeidex.eldalel.utils.PriceFormatter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class ElementsOrderActivity extends BaseActivity implements ElementsOrdersAdapter.OrderOperation {
    @BindView(R.id.activity_elemetnts_orders_recycler)
    RecyclerView activity_elemetnts_orders_recycler;

    @BindView(R.id.activity_elemetnts_orders_total_price_products_text)
    AppCompatTextView activity_elemetnts_orders_total_price_products_text;

    @BindView(R.id.activity_elemetnts_orders_tax_text)
    AppCompatTextView activity_elemetnts_orders_tax_text;

    @BindView(R.id.activity_elemetnts_orders_total_price_text)
    AppCompatTextView activity_elemetnts_orders_total_price_text;

    @BindView(R.id.activity_elemetnts_orders_coupon_linear)
    LinearLayoutCompat activity_elemetnts_orders_coupon_linear;

    @BindView(R.id.activity_elemetnts_orders_coupon_view)
    View activity_elemetnts_orders_coupon_view;

    @BindView(R.id.activity_elemetnts_orders_coupon_text)
    TextView activity_elemetnts_orders_coupon_text;

    @BindView(R.id.activity_elemetnts_orders_delivery_linear)
    LinearLayoutCompat activity_elemetnts_orders_delivery_linear;

    @BindView(R.id.activity_elemetnts_orders_delivery_view)
    View activity_elemetnts_orders_delivery_view;

    @BindView(R.id.activity_elemetnts_orders_delivery_text)
    TextView activity_elemetnts_orders_delivery_text;

    ElementsOrdersAdapter elementsOrderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elements_orders);
        ButterKnife.bind(this);
        showDialog();
        initializeRecycler();
        onLoadShipments();
    }

    @OnClick(R.id.item_elements_orders_back)
    public void onBack() {
        onBackPressed();
    }

    public void initializeRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        activity_elemetnts_orders_recycler.setLayoutManager(layoutManager);
        activity_elemetnts_orders_recycler.setItemAnimator(new DefaultItemAnimator());


    }

    String token;
    ArrayList<GetShipmentOrders.Order> orders;

    double total_with_coupon_and_tax;
    double total_with_tax_only;
    double total_inc_coupon_tax_delivery;

    public void onLoadShipments() {
        reloadDialog.show();
        if (PreferenceUtils.getCompanyLogin(this)) {
            token = PreferenceUtils.getCompanyToken(this);
        } else if (PreferenceUtils.getUserLogin(this)) {
            token = PreferenceUtils.getUserToken(this);
        }
        int id = getIntent().getIntExtra("shipment_id", 0);
        ShipmentOrdersApi shipmentOrdersApi = APIClient.getClient(SERVER_API_TEST).create(ShipmentOrdersApi.class);
        Call<GetShipmentOrders> getShipmentOrdersCall;
        if (PreferenceUtils.getCompanyLogin(ElementsOrderActivity.this)) {
            getShipmentOrdersCall = shipmentOrdersApi.getShipmentOrderscompany(id, token);
        } else {
            getShipmentOrdersCall = shipmentOrdersApi.getShipmentOrders(id, token);
        }
        getShipmentOrdersCall.enqueue(new Callback<GetShipmentOrders>() {
            @Override
            public void onResponse(Call<GetShipmentOrders> call, Response<GetShipmentOrders> response) {
                GetShipmentOrders getShipmentOrders = response.body();
                orders = (ArrayList<GetShipmentOrders.Order>) getShipmentOrders.getOrders();
//                if (orders.size() == 0){
//                    shipments_recycler_noitems.setVisibility(View.VISIBLE);
//                    return;
//                }
                double total_with_tax = 0;
                for (int i = 0; i < orders.size(); i++) {
//                    price += orders.get(i).getProductPrice();
                    total_with_tax_only += orders.get(i).getProductPrice() * orders.get(i).getQuantity();
                    total_inc_coupon_tax_delivery += orders.get(i).getTotalPriceWithTax();
                }

                if (PreferenceUtils.getUserLogin(ElementsOrderActivity.this) && getShipmentOrders.getPaymentType() == 2){
                    total_with_coupon_and_tax = total_inc_coupon_tax_delivery - 18;
                    activity_elemetnts_orders_delivery_linear.setVisibility(View.VISIBLE);
                    activity_elemetnts_orders_delivery_view.setVisibility(View.VISIBLE);
                    activity_elemetnts_orders_delivery_text.setText(PriceFormatter.toDecimalRsString(18.0, ElementsOrderActivity.this));
                } else {
                    total_with_coupon_and_tax = total_inc_coupon_tax_delivery;
                }

                double coupon = total_with_tax_only - total_with_coupon_and_tax;

                double total_without_tax = total_with_tax_only * 100 / 105;
                double tax = total_with_tax_only - total_without_tax;
//                double total_with_tax = total_without_tax + tax;

                if (Math.round(coupon) > 0) {
                    activity_elemetnts_orders_coupon_linear.setVisibility(View.VISIBLE);
                    activity_elemetnts_orders_coupon_view.setVisibility(View.VISIBLE);
                    activity_elemetnts_orders_coupon_text.setText(PriceFormatter.toDecimalRsString(-coupon, ElementsOrderActivity.this));
                }

                String totalString = PriceFormatter.toDecimalRsString(total_inc_coupon_tax_delivery, ElementsOrderActivity.this);
                String totalwithoutString = PriceFormatter.toDecimalRsString(total_without_tax, ElementsOrderActivity.this);
                String taxString = PriceFormatter.toDecimalRsString(tax, ElementsOrderActivity.this);

                activity_elemetnts_orders_total_price_products_text.setText(totalwithoutString);
                activity_elemetnts_orders_tax_text.setText(taxString);
                activity_elemetnts_orders_total_price_text.setText(totalString);

                elementsOrderAdapter = new ElementsOrdersAdapter(ElementsOrderActivity.this, orders);
                elementsOrderAdapter.setOrderOperation(ElementsOrderActivity.this);
                activity_elemetnts_orders_recycler.setAdapter(elementsOrderAdapter);
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetShipmentOrders> call, Throwable t) {

            }
        });


    }

    Dialog reloadDialog;

    private void showDialog() {
        reloadDialog = new Dialog(this);
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onClickOrderProduct(int id, int pos) {

    }
}
