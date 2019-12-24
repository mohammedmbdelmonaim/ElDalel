package com.zeidex.eldalel.ui.salesmancustomer;

import android.app.Dialog;
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

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.OrdersActivity;
import com.zeidex.eldalel.R;
import com.zeidex.eldalel.adapters.SalesmanUsersOrdersAdapter;
import com.zeidex.eldalel.response.GetUsersOrders;
import com.zeidex.eldalel.utils.PreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class SalesmanUsersFragment extends Fragment {

    @BindView(R.id.users_shipments_recycler)
    RecyclerView usersShipmentsRecycler;
    @BindView(R.id.no_orders_tv)
    TextView noOrdersTextView;

    private SalesmanUsersViewModel mSalesmanUsersViewModel;
    private SalesmanUsersOrdersAdapter mSalesmanUsersOrdersAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mSalesmanUsersViewModel =
                ViewModelProviders.of(this).get(SalesmanUsersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_salesman_users, container, false);
        ButterKnife.bind(this, root);

        showDialog();
        setupRecycler();
        setupObservers();

        mSalesmanUsersViewModel.fetchUsersOrders(PreferenceUtils.getSalesmanToken(getContext()));
        reloadDialog.show();
//        mSalesmanCustomersViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//
//            }
//        });
        return root;
    }

    private void setupObservers() {
        mSalesmanUsersViewModel.getUsersOrders().observe(this, new Observer<GetUsersOrders>() {
            @Override
            public void onChanged(GetUsersOrders getUsersOrders) {
                mSalesmanUsersOrdersAdapter.setUsersOrders(getUsersOrders.getData().getOrders());
                reloadDialog.dismiss();
            }
        });

        mSalesmanUsersViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!TextUtils.isEmpty(s)) {
                    Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                    reloadDialog.dismiss();
                }
            }
        });

        mSalesmanUsersViewModel.getHasOrders().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean hasOrders) {
                if (!hasOrders) {
                    noOrdersTextView.setVisibility(View.VISIBLE);
                    usersShipmentsRecycler.setVisibility(View.GONE);
                    reloadDialog.dismiss();
                } else {
                    noOrdersTextView.setVisibility(View.GONE);
                    usersShipmentsRecycler.setVisibility(View.VISIBLE);
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
        mSalesmanUsersOrdersAdapter = new SalesmanUsersOrdersAdapter(getContext());
        usersShipmentsRecycler.setAdapter(mSalesmanUsersOrdersAdapter);
        usersShipmentsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}