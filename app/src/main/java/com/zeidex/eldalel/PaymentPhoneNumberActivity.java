package com.zeidex.eldalel;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.zeidex.eldalel.response.GetSendCodeResponse;
import com.zeidex.eldalel.services.SendCodeToMobileApi;
import com.zeidex.eldalel.services.VerifyCodeMobileApi;
import com.zeidex.eldalel.utils.APIClient;
import com.zeidex.eldalel.utils.Animatoo;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class PaymentPhoneNumberActivity extends BaseActivity {
    @BindView(R.id.fragment_payment_phone_number_edittext)
    AppCompatEditText fragment_payment_phone_number_edittext;


    @BindView(R.id.fragment_payment_phone_number_btn_sure)
    AppCompatTextView fragment_payment_phone_number_btn_sure;

    @BindView(R.id.fragment_payment_phone_number_text_change)
    AppCompatTextView fragment_payment_phone_number_text_change;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_payment_phone_number);
        ButterKnife.bind(this);
        findViews();
    }

    String  mobile , email;

    public void findViews() {
        showDialog();

        mobile = getIntent().getStringExtra("mobile");
        email = getIntent().getStringExtra("email");

    }


    @OnClick(R.id.fragment_payment_phone_number_text_change)
    public void changeNumber() {
        onBackPressed();
    }

    @OnClick(R.id.fragment_payment_phone_number_text5)
    public void reSendCode() {
        sendCodeToMobile();
    }

    private void convertDaraVerifyToJson() {
        update_post = new HashMap<>();
        update_post.put("code", fragment_payment_phone_number_edittext.getText().toString());
        update_post.put("email", email);
        update_post.put("mobile", mobile);
    }

    @OnClick(R.id.fragment_payment_phone_number_btn_sure)
    public void verifyNumber() {
        final boolean fieldscomOK = validate(new EditText[]{fragment_payment_phone_number_edittext});
        if (fieldscomOK) {
            reloadDialog.show();
            convertDaraVerifyToJson();
            VerifyCodeMobileApi sendCodeToMobileApi = APIClient.getClient(SERVER_API_TEST).create(VerifyCodeMobileApi.class);
            Call<GetSendCodeResponse> getSendCodeResponseCall;
            getSendCodeResponseCall = sendCodeToMobileApi.verifyCodecompany(update_post);
            getSendCodeResponseCall.enqueue(new Callback<GetSendCodeResponse>() {
                @Override
                public void onResponse(Call<GetSendCodeResponse> call, Response<GetSendCodeResponse> response) {
                    GetSendCodeResponse getSendCodeResponse = response.body();
                    if (getSendCodeResponse.isSuccess()) {
                        Toasty.success(PaymentPhoneNumberActivity.this, getSendCodeResponse.getMessage(), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(PaymentPhoneNumberActivity.this, LoginActivity.class));
                        Animatoo.animateSwipeLeft(PaymentPhoneNumberActivity.this);
                        finish();

                    } else {
                        Toasty.error(PaymentPhoneNumberActivity.this, getSendCodeResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    reloadDialog.dismiss();
                }

                @Override
                public void onFailure(Call<GetSendCodeResponse> call, Throwable t) {
                    Toasty.error(PaymentPhoneNumberActivity.this, getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                    reloadDialog.dismiss();
                }
            });
        }
    }


    Map<String, String> update_post;

    private void convertDaraToJson() {
        update_post = new HashMap<>();
        update_post.put("mobile", mobile);
        update_post.put("email", email);
    }

    public void sendCodeToMobile() {
        reloadDialog.show();
        convertDaraToJson();
        SendCodeToMobileApi sendCodeToMobileApi = APIClient.getClient(SERVER_API_TEST).create(SendCodeToMobileApi.class);
        Call<GetSendCodeResponse> getSendCodeResponseCall;
        getSendCodeResponseCall = sendCodeToMobileApi.sendCodecompany(update_post);
        getSendCodeResponseCall.enqueue(new Callback<GetSendCodeResponse>() {
            @Override
            public void onResponse(Call<GetSendCodeResponse> call, Response<GetSendCodeResponse> response) {
                GetSendCodeResponse getSendCodeResponse = response.body();
                if (getSendCodeResponse.isSuccess()) {
                    Toasty.success(PaymentPhoneNumberActivity.this, getSendCodeResponse.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    String errors = "";
                    for (String errorText : getSendCodeResponse.getError()) {
                        errors += errorText;
                    }
                    Toasty.error(PaymentPhoneNumberActivity.this, errors, Toast.LENGTH_LONG).show();
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetSendCodeResponse> call, Throwable t) {
                Toasty.error(PaymentPhoneNumberActivity.this, getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
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
