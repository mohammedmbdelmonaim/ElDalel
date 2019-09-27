package com.zeidex.eldalel;

import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressesActivity extends BaseActivity {

    @BindView(R.id.activity_address_header_item)
    LinearLayoutCompat activity_address_header_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresses);
        ButterKnife.bind(this);
        Fragment fragment = new ShoopingListAddressesFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putString("from", "address");

        fragment.setArguments(args);

        ft.setCustomAnimations(R.anim.animate_slide_up_enter, R.anim.animate_slide_up_exit);
        ft.replace(R.id.activity_address_constraint, fragment, fragment.getTag());
        ft.commit();
    }

    @OnClick(R.id.item_address_support_back)
    public void onBack(){
        onBackPressed();
    }
}
