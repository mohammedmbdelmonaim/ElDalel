package com.zeidex.eldalel;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.zeidex.eldalel.response.GetSendCodeResponse;
import com.zeidex.eldalel.services.SendCodeToMobileApi;
import com.zeidex.eldalel.services.VerifyCodeMobileApi;
import com.zeidex.eldalel.utils.APIClient;
import com.zeidex.eldalel.utils.ChangeLang;
import com.zeidex.eldalel.utils.PreferenceUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class PaymentPhoneNumberFragment extends Fragment {
    @BindView(R.id.fragment_payment_phone_number_edittext)
    AppCompatEditText fragment_payment_phone_number_edittext;



    @BindView(R.id.fragment_payment_phone_number_btn_sure)
    AppCompatTextView fragment_payment_phone_number_btn_sure;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_phone_number, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        findViews();
    }

    String token, lang, mobile , state_addresses;

    public void findViews() {
        showDialog();
        Bundle args = getArguments();
        mobile = args.getString("mobile");

        state_addresses = args.getString("from");

        if (PreferenceUtils.getCompanyLogin(getActivity())) {
            token = PreferenceUtils.getCompanyToken(getActivity());
        } else if (PreferenceUtils.getUserLogin(getActivity())) {
            token = PreferenceUtils.getUserToken(getActivity());
        }

        Locale locale = ChangeLang.getLocale(getResources());
        String loo = locale.getLanguage();
        if (loo.equalsIgnoreCase("en")) {
            lang = "english";
        } else {
            lang = "arabic";
        }
    }

    @OnClick(R.id.fragment_payment_phone_number_text_change)
    public void changeNumber() {
        Fragment fragment = new ShoopingListAddressesFragment();
        Bundle args = new Bundle();
        args.putString("from", state_addresses);
        fragment.setArguments(args);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.animate_slide_up_enter, R.anim.animate_slide_up_exit);
        if (state_addresses.equalsIgnoreCase("address")){
            ft.replace(R.id.activity_address_constraint, fragment, fragment.getTag());
        }else if(state_addresses.equalsIgnoreCase("payment")){
            ft.replace(R.id.payment_constrant, fragment, fragment.getTag());
        }
//                        ft.addToBackStack(null);
        ft.commit();
    }

    @OnClick(R.id.fragment_payment_phone_number_text5)
    public void reSendCode() {
        sendCodeToMobile();
    }

    private void convertDaraVerifyToJson() {
        update_post = new HashMap<>();
        update_post.put("code", fragment_payment_phone_number_edittext.getText().toString());
        update_post.put("language", lang);
        update_post.put("token", token);
    }

    @OnClick(R.id.fragment_payment_phone_number_btn_sure)
    public void verifyNumber() {
        final boolean fieldscomOK = validate(new EditText[]{fragment_payment_phone_number_edittext});
        if (fieldscomOK) {
            reloadDialog.show();
            convertDaraVerifyToJson();
            VerifyCodeMobileApi sendCodeToMobileApi = APIClient.getClient(SERVER_API_TEST).create(VerifyCodeMobileApi.class);
            Call<GetSendCodeResponse> getSendCodeResponseCall = sendCodeToMobileApi.verifyCode(update_post);
            getSendCodeResponseCall.enqueue(new Callback<GetSendCodeResponse>() {
                @Override
                public void onResponse(Call<GetSendCodeResponse> call, Response<GetSendCodeResponse> response) {
                    GetSendCodeResponse getSendCodeResponse = response.body();
                    if (getSendCodeResponse.isSuccess()) {
                        Toasty.success(getActivity(), getSendCodeResponse.getMessage(), Toast.LENGTH_LONG).show();
                        Fragment fragment = new ShoopingListAddressesFragment();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.setCustomAnimations(R.anim.animate_slide_up_enter, R.anim.animate_slide_up_exit);
                        if (state_addresses.equalsIgnoreCase("address")){
                            ft.replace(R.id.activity_address_constraint, fragment, fragment.getTag());
                        }else if(state_addresses.equalsIgnoreCase("payment")){
                            ft.replace(R.id.payment_constrant, fragment, fragment.getTag());
                        }
//                        ft.addToBackStack(null);
                        ft.commit();
                    } else {
                        Toasty.error(getActivity(), getSendCodeResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    reloadDialog.dismiss();
                }

                @Override
                public void onFailure(Call<GetSendCodeResponse> call, Throwable t) {
                    Toasty.error(getActivity(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                    reloadDialog.dismiss();
                }
            });
        }
    }



    Map<String, String> update_post;

    private void convertDaraToJson() {
        update_post = new HashMap<>();
        update_post.put("mobile", mobile);
        update_post.put("language", lang);
        update_post.put("token", token);
    }

    public void sendCodeToMobile() {
        reloadDialog.show();
        convertDaraToJson();
        SendCodeToMobileApi sendCodeToMobileApi = APIClient.getClient(SERVER_API_TEST).create(SendCodeToMobileApi.class);
        Call<GetSendCodeResponse> getSendCodeResponseCall = sendCodeToMobileApi.sendCode(update_post);
        getSendCodeResponseCall.enqueue(new Callback<GetSendCodeResponse>() {
            @Override
            public void onResponse(Call<GetSendCodeResponse> call, Response<GetSendCodeResponse> response) {
                GetSendCodeResponse getSendCodeResponse = response.body();
                if (getSendCodeResponse.isSuccess()) {
                    Toasty.success(getActivity(), getSendCodeResponse.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    String errors = "";
                    for (String errorText : getSendCodeResponse.getError()) {
                        errors += errorText;
                    }
                    Toasty.error(getActivity(), errors, Toast.LENGTH_LONG).show();
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetSendCodeResponse> call, Throwable t) {
                Toasty.error(getActivity(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    Dialog reloadDialog;

    private void showDialog() {
        reloadDialog = new Dialog(getActivity());
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private boolean validate(EditText[] fields) {
        for (int i = 0; i < fields.length; i++) {
            EditText currentField = fields[i];
            if (currentField.getText().toString().length() <= 0) {
                currentField.setError(getString(R.string.field_required));
                currentField.requestFocus();
                return false;
            }
        }
        return true;
    }
}
