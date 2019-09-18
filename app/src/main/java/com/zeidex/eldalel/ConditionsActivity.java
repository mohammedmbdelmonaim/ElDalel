package com.zeidex.eldalel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConditionsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conditions);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.item_condi_back)
    public void onBack(){
        onBackPressed();
    }
}
