package com.zeidex.eldalel.ui.salesmanShipment;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zeidex.eldalel.R;
import com.zeidex.eldalel.response.GetCompanyShipmentProducts;
import com.zeidex.eldalel.response.GetUserShipmentProducts;
import com.zeidex.eldalel.ui.salesmanShipmentProducts.SalesmanShipmentProductsViewModel;
import com.zeidex.eldalel.utils.ChangeLang;
import com.zeidex.eldalel.utils.PreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class SalesEditQuantityFragment extends Fragment {

    @BindView(R.id.change_quantity_amount_et)
    EditText quantityEt;
    @BindView(R.id.change_quantity_notes_et)
    EditText notesEt;

    SalesmanShipmentProductsViewModel mSalesmanShipmentProductsViewModel;
    private int mPosition;
    private int mOrderId;
    private int mQuantity;
    private String mType;

    public SalesEditQuantityFragment() {

    }

    @OnClick(R.id.change_quantity_submit_btn)
    void changeQuantity() {
        String quantity = quantityEt.getText().toString();
        String notes = notesEt.getText().toString();
        if (TextUtils.isEmpty(quantity) || TextUtils.isEmpty(notes)) {
            Toasty.error(getContext(), getString(R.string.complete_fields_toast_text), Toast.LENGTH_LONG).show();
        } else {
            if (Integer.parseInt(quantity) <= 0) {
                Toasty.error(getContext(), getString(R.string.enter_valid_quantity_error), Toast.LENGTH_LONG).show();
                return;
            }
            reloadDialog.show();
            mSalesmanShipmentProductsViewModel.changeQuantity(mPosition, Integer.valueOf(quantity), notes, PreferenceUtils.getSalesmanToken(getContext()), String.valueOf(mOrderId), ChangeLang.getLocale(getContext().getResources()).getLanguage(), mType);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_salesman_edit_quantity, container, false);
        ButterKnife.bind(this, root);

        mSalesmanShipmentProductsViewModel =
                ViewModelProviders.of(getActivity()).get(SalesmanShipmentProductsViewModel.class);

        mPosition = getArguments().getInt("position");
        mOrderId = getArguments().getInt("order_id");
        mQuantity = getArguments().getInt("quantity");
        mType = getArguments().getString("type");

        showDialog();

        mSalesmanShipmentProductsViewModel.getShouldNavigateBack().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldNavigateBack) {
                if (shouldNavigateBack) {
                    reloadDialog.dismiss();
                    mSalesmanShipmentProductsViewModel.onNavigateBackCompleted();
                    getActivity().onBackPressed();
                }
            }
        });

        mSalesmanShipmentProductsViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!TextUtils.isEmpty(s)) {
                    reloadDialog.dismiss();
                    Toasty.error(getContext(), s, Toast.LENGTH_LONG).show();
                    mSalesmanShipmentProductsViewModel.onErrorShowCompleted();
                }
            }
        });

        return root;
    }

    Dialog reloadDialog;

    private void showDialog() {
        reloadDialog = new Dialog(getContext());
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

}
