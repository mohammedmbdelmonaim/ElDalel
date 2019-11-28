package com.zeidex.eldalel;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.zeidex.eldalel.utils.Animatoo;
import com.zeidex.eldalel.utils.AppPermissions;


public class SplashActivity extends BaseActivity {

    String[] appPermissions = {Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET , Manifest.permission.WRITE_EXTERNAL_STORAGE , Manifest.permission.READ_EXTERNAL_STORAGE};
    AppPermissions permissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Animatoo.animateSwipeLeft(this);
//        new ChangeLang(this, "").setLocale();
        setContentView(R.layout.activity_splash);
//        permissions = new AppPermissions(this , appPermissions);
//        if (permissions.checkAndRequestPermission()){
            new Handler().postDelayed(new Runnable() {
//                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    Animatoo.animateSwipeLeft(SplashActivity.this);
                    finish();
                }
            }, 4000);
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
       if (this.permissions.onRequestPermissionsResult(requestCode , grantResults , permissions)){
           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                   startActivity(new Intent(SplashActivity.this, MainActivity.class));
                   Animatoo.animateSwipeLeft(SplashActivity.this);
                   finish();
               }
           }, 4000);
       }
    }
}

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        animationDrawable.start();
//    }
