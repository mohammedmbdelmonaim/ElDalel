package com.zeidex.eldalel.ui.salesmanShipment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.zeidex.eldalel.R;
import com.zeidex.eldalel.ui.salesmanShipmentProducts.SalesmanShipmentProductsViewModel;
import com.zeidex.eldalel.utils.ChangeLang;
import com.zeidex.eldalel.utils.PreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;


public class SalesmanCancelOrderFragment extends Fragment {

//    @BindView(R.id.cancel_order_notes_et)
//    EditText notesEt;

    @BindView(R.id.cancel_order_spinner)
    Spinner cancelOrderSpinner;

    SalesmanShipmentProductsViewModel mSalesmanShipmentProductsViewModel;
    private int mPosition;
    private int mOrderId;
    private String mType;

    public SalesmanCancelOrderFragment() {
        // Required empty public constructor
    }

    @OnClick(R.id.cancel_order_submit_btn)
    void cancelOrder() {
//        String notes = notesEt.getText().toString();
//        if (TextUtils.isEmpty(notes)) {
//            Toasty.error(getContext(), getString(R.string.complete_fields_toast_text), Toast.LENGTH_LONG).show();
//        } else {
        String notes = cancelOrderSpinner.getSelectedItem().toString();
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(R.string.sure_you_want_cancel).setPositiveButton(R.string.cancel_order_label, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    reloadDialog.show();
                    mSalesmanShipmentProductsViewModel.cancelOrder(mPosition, notes, PreferenceUtils.getSalesmanToken(getContext()), String.valueOf(mOrderId), ChangeLang.getLocale(getContext().getResources()).getLanguage(), mType);
                    dialog.dismiss();
                }
            }).setNegativeButton(R.string.dismiss, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_salesman_cancel_order, container, false);
        ButterKnife.bind(this, root);

        mSalesmanShipmentProductsViewModel =
                ViewModelProviders.of(getActivity()).get(SalesmanShipmentProductsViewModel.class);

        mPosition = getArguments().getInt("position");
        mOrderId = getArguments().getInt("order_id");
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
