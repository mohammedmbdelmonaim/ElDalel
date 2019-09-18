package com.zeidex.eldalel;

import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SupportActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.item_support_back)
    public void onBack(){
        onBackPressed();
    }
}
