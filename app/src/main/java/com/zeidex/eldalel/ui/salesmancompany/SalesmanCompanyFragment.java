package com.zeidex.eldalel.ui.salesmancompany;

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

import com.zeidex.eldalel.R;
import com.zeidex.eldalel.adapters.SalesmanCompaniesOrdersAdapter;
import com.zeidex.eldalel.adapters.SalesmanUsersOrdersAdapter;
import com.zeidex.eldalel.response.GetCompaniesOrders;
import com.zeidex.eldalel.response.GetUsersOrders;
import com.zeidex.eldalel.ui.salesmancustomer.SalesmanUsersViewModel;
import com.zeidex.eldalel.utils.PreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class SalesmanCompanyFragment extends Fragment {


    @BindView(R.id.company_shipments_recycler)
    RecyclerView companiesShipmentsRecycler;
    @BindView(R.id.no_orders_tv)
    TextView noOrdersTextView;

    private SalesmanCompanyViewModel mSalesmanCompanyViewModel;
    private SalesmanCompaniesOrdersAdapter mSalesmanCompaniesOrdersAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mSalesmanCompanyViewModel =
                ViewModelProviders.of(this).get(SalesmanCompanyViewModel.class);
        View root = inflater.inflate(R.layout.fragment_salesman_company, container, false);
        ButterKnife.bind(this, root);

        showDialog();
        setupRecycler();
        setupObservers();

        mSalesmanCompanyViewModel.fetchCompaniesOrders(PreferenceUtils.getSalesmanToken(getContext()));
        reloadDialog.show();
        return root;
    }

    private void setupObservers() {
        mSalesmanCompanyViewModel.getCompaniesOrders().observe(this, new Observer<GetCompaniesOrders>() {
            @Override
            public void onChanged(GetCompaniesOrders getCompaniesOrders) {
                mSalesmanCompaniesOrdersAdapter.setCompaniesOrders(getCompaniesOrders.getData().getOrders());
                reloadDialog.dismiss();
            }
        });

        mSalesmanCompanyViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!TextUtils.isEmpty(s)) {
                    Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                    reloadDialog.dismiss();
                }
            }
        });

        mSalesmanCompanyViewModel.getHasOrders().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean hasOrders) {
                if (!hasOrders) {
                    noOrdersTextView.setVisibility(View.VISIBLE);
                    companiesShipmentsRecycler.setVisibility(View.GONE);
                    reloadDialog.dismiss();
                } else {
                    noOrdersTextView.setVisibility(View.GONE);
                    companiesShipmentsRecycler.setVisibility(View.VISIBLE);
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
        mSalesmanCompaniesOrdersAdapter = new SalesmanCompaniesOrdersAdapter(getContext());
        companiesShipmentsRecycler.setAdapter(mSalesmanCompaniesOrdersAdapter);
        companiesShipmentsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}