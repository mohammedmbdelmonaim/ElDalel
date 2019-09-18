package com.zeidex.eldalel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.zeidex.eldalel.utils.ChangeLang;

public class BaseActivity extends AppCompatActivity {
    private BroadcastReceiver networkreceiver = null;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(ChangeLang.setLocale(base));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        networkreceiver = new NetworkReceiver();
        registerReceiver(networkreceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkreceiver);
    }
}
