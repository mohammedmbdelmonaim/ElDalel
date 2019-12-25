package com.zeidex.eldalel;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.zeidex.eldalel.response.LogoutResponse;
import com.zeidex.eldalel.services.LogoutApi;
import com.zeidex.eldalel.services.UpdateProfileApi;
import com.zeidex.eldalel.utils.APIClient;
import com.zeidex.eldalel.utils.Animatoo;
import com.zeidex.eldalel.utils.ChangeLang;
import com.zeidex.eldalel.utils.PreferenceUtils;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.Window;
import android.widget.Toast;

import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class SalesmanActivity extends BaseActivity {

    private AppBarConfiguration mAppBarConfiguration;
    String lang;
    String old_lang;
    Dialog changeLanguage;
    private DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salesman);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        showDialog();

        Locale locale = ChangeLang.getLocale(getResources());
        String loo = locale.getLanguage();
        if (loo.equalsIgnoreCase("ar")) {
            lang = "arabic";
            old_lang = "arabic";
        } else if (loo.equalsIgnoreCase("en")) {
            lang = "english";
            old_lang = "english";
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_company, R.id.nav_customer)
//                ,
//                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(mDrawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(toolbar, navController, mDrawer);
    }

    public void chooseLanguage(MenuItem chooseLanguage) {
        changeLanguage = new Dialog(this);
        changeLanguage.requestWindowFeature(Window.FEATURE_NO_TITLE);
        changeLanguage.setContentView(R.layout.dialog_change_language);
        changeLanguage.setCancelable(true);
        AppCompatButton dialog_text_active_lang = changeLanguage.findViewById(R.id.dialog_text_active_lang);
        AppCompatButton dialog_text_deactive_lang = changeLanguage.findViewById(R.id.dialog_text_deactive_lang);

        if (lang.equalsIgnoreCase("arabic")) {
            dialog_text_active_lang.setText(getString(R.string.radio_ar_register));
            dialog_text_deactive_lang.setText(getString(R.string.radio_en_register));
        } else if (lang.equalsIgnoreCase("english")) {
            dialog_text_active_lang.setText(getString(R.string.radio_en_register));
            dialog_text_deactive_lang.setText(getString(R.string.radio_ar_register));
        }

        dialog_text_active_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog_text_active_lang.getText().toString().equalsIgnoreCase(getString(R.string.radio_ar_register))) {
                    lang = "arabic";
                } else {
                    lang = "english";
                }
                changeLanguage.dismiss();
                if (!old_lang.equalsIgnoreCase(lang)) {
                    if (lang.equalsIgnoreCase("english")) {
                        ChangeLang.setNewLocale(SalesmanActivity.this, "en");
                    } else {
                        ChangeLang.setNewLocale(SalesmanActivity.this, "ar");
                    }
                }
                startActivity(new Intent(SalesmanActivity.this, SalesmanActivity.class));
                finish();
            }
        });

        dialog_text_deactive_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog_text_deactive_lang.getText().toString().equalsIgnoreCase(getString(R.string.radio_ar_register))) {
                    lang = "arabic";
                } else {
                    lang = "english";
                }

                changeLanguage.dismiss();
                if (!old_lang.equalsIgnoreCase(lang)) {
                    if (lang.equalsIgnoreCase("english")) {
                        ChangeLang.setNewLocale(SalesmanActivity.this, "en");
                    } else {
                        ChangeLang.setNewLocale(SalesmanActivity.this, "ar");
                    }
                }
                startActivity(new Intent(SalesmanActivity.this, SalesmanActivity.class));
                finish();
            }
        });
        mDrawer.closeDrawers();
        changeLanguage.show();
    }

    public void logout(MenuItem logout) {
        mDrawer.closeDrawers();
        reloadDialog.show();
        LogoutApi logoutApi = APIClient.getClient(SERVER_API_TEST).create(LogoutApi.class);
        logoutApi.logoutSalesman(PreferenceUtils.getSalesmanToken(this)).enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                if(response.body() != null){
                    if(response.body().getSuccess().equals("logout")){
                        Toasty.normal(SalesmanActivity.this, getString(R.string.log_out_success_toast), Toast.LENGTH_SHORT).show();
                        PreferenceUtils.saveSalesmanToken(SalesmanActivity.this, "");
                        PreferenceUtils.saveSalesmanLogin(SalesmanActivity.this, false);
                        startActivity(new Intent(SalesmanActivity.this, MainActivity.class));
                        finish();
                        Animatoo.animateSwipeLeft(SalesmanActivity.this);
                    }else{
                       Toasty.error(SalesmanActivity.this, response.body().getResponse(), Toast.LENGTH_SHORT).show();
                    }
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                Toasty.error(SalesmanActivity.this, getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });

    }

    Dialog reloadDialog;

    private void showDialog() {
        reloadDialog = new Dialog(SalesmanActivity.this);
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
