package com.zeidex.eldalel;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;

import com.zeidex.eldalel.response.GetStaticPageRespons;
import com.zeidex.eldalel.services.PayStaticApi;
import com.zeidex.eldalel.utils.APIClient;
import com.zeidex.eldalel.utils.ChangeLang;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class PaymentMethodsActivity extends BaseActivity {
    @BindView(R.id.payment_methods_text)
    AppCompatTextView payment_methods_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_methods);
        ButterKnife.bind(this);
        showDialog();
        onLoadPage();
    }

    @OnClick(R.id.item_payment_methods_back)
    public void onBack(){
        onBackPressed();
    }

    public void onLoadPage(){
        reloadDialog.show();
        PayStaticApi payStaticApi = APIClient.getClient(SERVER_API_TEST).create(PayStaticApi.class);
        Call<GetStaticPageRespons> getStaticPageResponsCall = payStaticApi.getPayResponse();
        getStaticPageResponsCall.enqueue(new Callback<GetStaticPageRespons>() {
            @Override
            public void onResponse(Call<GetStaticPageRespons> call, Response<GetStaticPageRespons> response) {
                reloadDialog.dismiss();
                GetStaticPageRespons getStaticPageRespons = response.body();
                if (getStaticPageRespons.getStatus() == true) {
                    Locale locale = ChangeLang.getLocale(getResources());
                    String loo = locale.getLanguage();
                    if (loo.equalsIgnoreCase("en")) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            payment_methods_text.setText(Html.fromHtml(getStaticPageRespons.getData().getContent_en(), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            payment_methods_text.setText(Html.fromHtml(getStaticPageRespons.getData().getContent_en()));
                        }
                    } else if (loo.equalsIgnoreCase("ar")) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            payment_methods_text.setText(Html.fromHtml(getStaticPageRespons.getData().getContent_ar(), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            payment_methods_text.setText(Html.fromHtml(getStaticPageRespons.getData().getContent_ar()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetStaticPageRespons> call, Throwable t) {
                Toasty.error(PaymentMethodsActivity.this, getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
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
}
