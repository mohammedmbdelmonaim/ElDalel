package com.zeidex.eldalel;

import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConnectActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.item_connect_back)
    public void onBack(){
        onBackPressed();
    }
}

