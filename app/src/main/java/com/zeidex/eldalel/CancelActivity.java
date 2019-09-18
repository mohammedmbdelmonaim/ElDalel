package com.zeidex.eldalel;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CancelActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.item_cancel_back)
    public void onBack(){
        onBackPressed();
    }
}
