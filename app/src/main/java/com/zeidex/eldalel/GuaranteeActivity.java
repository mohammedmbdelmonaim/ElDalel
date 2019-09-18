package com.zeidex.eldalel;

import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuaranteeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guarantee);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.item_gurantee_back)
    public void onBack(){
        onBackPressed();
    }
}
