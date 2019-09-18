package com.zeidex.eldalel;

import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrivacyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.item_privacy_back)
    public void onBack(){
        onBackPressed();
    }
}
