package com.zeidex.eldalel;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.zeidex.eldalel.response.GetLoginResponse;
import com.zeidex.eldalel.response.GetRegisterResponse;
import com.zeidex.eldalel.services.LoginApi;
import com.zeidex.eldalel.utils.APIClient;
import com.zeidex.eldalel.utils.Animatoo;
import com.zeidex.eldalel.utils.ChangeLang;
import com.zeidex.eldalel.utils.PreferenceUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.login_go_enter_text)
    AppCompatTextView login_go_enter_text;

    @BindView(R.id.login_no_have_account_text)
    AppCompatTextView login_no_have_account_text;

    @BindView(R.id.login_not_now_text)
    AppCompatTextView login_not_now_text;

    @BindView(R.id.login_mail_edittext)
    AppCompatEditText login_mail_edittext;

    @BindView(R.id.login_pass_edittext)
    AppCompatEditText login_pass_edittext;

    @BindView(R.id.login_remember_checkbox)
    AppCompatCheckBox login_remember_checkbox;

    boolean checked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        findViews();
    }

    public void findViews(){
        if (!PreferenceUtils.getEmail(this).equalsIgnoreCase("") && !PreferenceUtils.getPassword(this).equalsIgnoreCase("")){
            login_mail_edittext.setText(PreferenceUtils.getEmail(this));
            login_pass_edittext.setText(PreferenceUtils.getPassword(this));
            login_remember_checkbox.setChecked(true);
            checked = true;
        }

        showDialog();

        login_go_enter_text.setOnClickListener(this);
        login_no_have_account_text.setOnClickListener(this);
        login_not_now_text.setOnClickListener(this);

        login_remember_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    checked = true;
                }else {
                    checked = false;
                }
            }
        });
    }


    String lang;
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.login_go_enter_text:{

                Locale locale = ChangeLang.getLocale(getResources());
                String loo = locale.getLanguage();
                if (loo.equalsIgnoreCase("en")) {
                    lang = "english";
                }else if (loo.equalsIgnoreCase("ar")){
                    lang = "arabic";
                }

                checkLogin();
                break;
            }
            case R.id.login_no_have_account_text:{
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                Animatoo.animateSwipeLeft(LoginActivity.this);
                break;
            }
            case R.id.login_not_now_text:{
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                Animatoo.animateSwipeLeft(LoginActivity.this);
                break;
            }
        }
    }
    Map<String, String> login_post;
    private void convertDaraToJson() {
        login_post = new HashMap<>();
        String emil = login_mail_edittext.getText().toString();
        String pass = login_pass_edittext.getText().toString();
        login_post.put("password", pass);
        login_post.put("email", emil);
        login_post.put("language", lang);
    }



    public void checkLogin(){
        final boolean fieldsOK = validate(new EditText[]{login_mail_edittext, login_pass_edittext});
        if (fieldsOK){
            convertDaraToJson();
            reloadDialog.show();
            LoginApi loginApi = APIClient.getClient(SERVER_API_TEST).create(LoginApi.class);
            Call<GetLoginResponse> getLoginResponseCall = loginApi.getLogin(login_post);
            getLoginResponseCall.enqueue(new Callback<GetLoginResponse>() {
                @Override
                public void onResponse(Call<GetLoginResponse> call, Response<GetLoginResponse> response) {
                    GetLoginResponse getLoginResponse = response.body();
                    String status = getLoginResponse.getSuccess();
                    if (status.equals("true")) {
                        if (getLoginResponse.getTokenUser() != null){
                            PreferenceUtils.saveUserToken(LoginActivity.this, getLoginResponse.getTokenUser());
                            PreferenceUtils.saveUserLogin(LoginActivity.this , true);
                        }else if (getLoginResponse.getTokenCompany() != null){
                            PreferenceUtils.saveCompanyToken(LoginActivity.this, getLoginResponse.getTokenCompany());
                            PreferenceUtils.saveCompanyLogin(LoginActivity.this , true);
                        }

                        if (checked){
                            PreferenceUtils.saveEmail(LoginActivity.this , login_mail_edittext.getText().toString());
                            PreferenceUtils.savePassword(LoginActivity.this , login_pass_edittext.getText().toString());
                        }else {
                            PreferenceUtils.saveEmail(LoginActivity.this , "");
                            PreferenceUtils.savePassword(LoginActivity.this , "");
                        }


                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        Animatoo.animateSwipeLeft(LoginActivity.this);

                    }else if (status.equalsIgnoreCase("false")){
                        Toasty.error(LoginActivity.this, getLoginResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    reloadDialog.dismiss();
                }

                @Override
                public void onFailure(Call<GetLoginResponse> call, Throwable t) {
                    Toasty.error(LoginActivity.this, getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                    reloadDialog.dismiss();
                }
            });
        }
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

    Dialog reloadDialog;

    private void showDialog() {
        reloadDialog = new Dialog(this);
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
