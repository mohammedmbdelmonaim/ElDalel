package com.zeidex.eldalel.ui.salesmanShipmentProducts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.R;
import com.zeidex.eldalel.SalesmanActivity;
import com.zeidex.eldalel.adapters.CompanyShipmentProductsAdapter;
import com.zeidex.eldalel.adapters.UserShipmentProductsAdapter;
import com.zeidex.eldalel.listeners.OrderShipmentsActions;
import com.zeidex.eldalel.response.GetCompanyShipmentProducts;
import com.zeidex.eldalel.response.GetUserShipmentProducts;
import com.zeidex.eldalel.utils.ChangeLang;
import com.zeidex.eldalel.utils.PreferenceUtils;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class SalesmanShipmentProductsFragment extends Fragment implements OrderShipmentsActions {

    @BindView(R.id.shipment_products_recycler)
    RecyclerView shipmentProductsRecycler;
    @BindView(R.id.no_products_tv)
    TextView noProductsTextView;

    String shipmentId;
    String orderId;
    String type;

    SalesmanShipmentProductsViewModel mSalesmanShipmentProductsViewModel;
    UserShipmentProductsAdapter mUserShipmentProductsAdapter;
    CompanyShipmentProductsAdapter mCompanyShipmentProductsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_salesman_shipment_products, container, false);
        ButterKnife.bind(this, root);

        shipmentId = getArguments().getString("shipment_id");
        orderId = getArguments().getString("order_id");
        type = getArguments().getString("type");

        ((SalesmanActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.shipment_title) + " " + shipmentId);

        if (mSalesmanShipmentProductsViewModel == null) {
            mSalesmanShipmentProductsViewModel =
                    ViewModelProviders.of(getActivity()).get(SalesmanShipmentProductsViewModel.class);

            mSalesmanShipmentProductsViewModel.resetShipmentProducts();

            showDialog();
            setupRecycler();
            setupObservers();

            if (type.equals("user")) {
                mSalesmanShipmentProductsViewModel.fetchUserShipmentProducts(shipmentId, PreferenceUtils.getSalesmanToken(getContext()));
            } else if (type.equals("company")) {
                mSalesmanShipmentProductsViewModel.fetchCompanyShipmentProducts(shipmentId, PreferenceUtils.getSalesmanToken(getContext()));
            }
            reloadDialog.show();
        }else{
            if (type.equals("user")) {
                mUserShipmentProductsAdapter.setShipmentAction(this);
                shipmentProductsRecycler.setAdapter(mUserShipmentProductsAdapter);
            } else if (type.equals("company")) {
                mCompanyShipmentProductsAdapter.setShipmentAction(this);
                shipmentProductsRecycler.setAdapter(mCompanyShipmentProductsAdapter);
            }
            shipmentProductsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        return root;
    }

    private void setupObservers() {
        mSalesmanShipmentProductsViewModel.getUsersShipmentProducts().observe(this, new Observer<GetUserShipmentProducts>() {
            @Override
            public void onChanged(GetUserShipmentProducts getUserShipmentProducts) {
                mUserShipmentProductsAdapter.setShipmentProducts(getUserShipmentProducts.getData().getOrders());
                reloadDialog.dismiss();
            }
        });

        mSalesmanShipmentProductsViewModel.getCompanyShipmentProducts().observe(this, new Observer<GetCompanyShipmentProducts>() {
            @Override
            public void onChanged(GetCompanyShipmentProducts getCompanyShipmentProducts) {
                mCompanyShipmentProductsAdapter.setShipmentProducts(getCompanyShipmentProducts.getData().getOrders());
                reloadDialog.dismiss();
            }
        });


        mSalesmanShipmentProductsViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!TextUtils.isEmpty(s)) {
                    Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                    mSalesmanShipmentProductsViewModel.onErrorShowCompleted();
                    reloadDialog.dismiss();
                }
            }
        });

        mSalesmanShipmentProductsViewModel.getHasOrders().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean hasOrders) {
                if (!hasOrders) {
                    noProductsTextView.setVisibility(View.VISIBLE);
                    shipmentProductsRecycler.setVisibility(View.GONE);
                    reloadDialog.dismiss();
                } else {
                    noProductsTextView.setVisibility(View.GONE);
                    shipmentProductsRecycler.setVisibility(View.VISIBLE);
                }
            }
        });

        mSalesmanShipmentProductsViewModel.getSuccessMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!TextUtils.isEmpty(s)) {
                    if (s.equals("success")) {
                        Toast.makeText(getContext(), R.string.delivered_successfully, Toast.LENGTH_SHORT).show();
                    } else if (s.equals("change")) {
                        Toast.makeText(getContext(), R.string.changed_successfully, Toast.LENGTH_SHORT).show();
                    } else if (s.equals("cancel")) {
                        Toast.makeText(getContext(), R.string.cancelled_successfully, Toast.LENGTH_SHORT).show();
                    }
                    mSalesmanShipmentProductsViewModel.onToastShowCompleted();
                }
            }
        });
    }

    Dialog reloadDialog;

    private void showDialog() {
        reloadDialog = new Dialog(getContext());
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void setupRecycler() {
        if (type.equals("user")) {
            mUserShipmentProductsAdapter = new UserShipmentProductsAdapter(getContext());
            mUserShipmentProductsAdapter.setShipmentAction(this);
            shipmentProductsRecycler.setAdapter(mUserShipmentProductsAdapter);
        } else if (type.equals("company")) {
            mCompanyShipmentProductsAdapter = new CompanyShipmentProductsAdapter(getContext());
            mCompanyShipmentProductsAdapter.setShipmentAction(this);
            shipmentProductsRecycler.setAdapter(mCompanyShipmentProductsAdapter);
        }

        shipmentProductsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onOrderShipmentDeliver(int orderId, int position, String type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(R.string.sure_you_delivered).setPositiveButton(R.string.delivered, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mSalesmanShipmentProductsViewModel.deliverOrder(position, PreferenceUtils.getSalesmanToken(getContext()), String.valueOf(orderId), ChangeLang.getLocale(getContext().getResources()).getLanguage(), type);
                reloadDialog.show();
                dialog.dismiss();
            }
        }).setNegativeButton(R.string.dialog_text_cancel_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

    @Override
    public void onOrderShipmentCancel(int orderId, int position, String type) {
        Bundle bundle = new Bundle();
        bundle.putInt("order_id", orderId);
        bundle.putInt("position", position);
        bundle.putString("type", type);
        NavHostFragment.findNavController(this).navigate(R.id.action_salesmanShipmentProductsFragment_to_salesmanCancelOrder, bundle);
    }

    @Override
    public void onOrderShipmentChangeQuantity(int orderId, int position, int quantity, String type) {
        Bundle bundle = new Bundle();
        bundle.putInt("order_id", orderId);
        bundle.putInt("position", position);
        bundle.putInt("quantity", quantity);
        bundle.putString("type", type);
        NavHostFragment.findNavController(this).navigate(R.id.action_salesmanShipmentProductsFragment_to_salesEditQuantityFragment, bundle);
    }
}
