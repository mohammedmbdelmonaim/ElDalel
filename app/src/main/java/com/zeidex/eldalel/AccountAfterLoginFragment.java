package com.zeidex.eldalel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import com.zeidex.eldalel.utils.Animatoo;
import com.zeidex.eldalel.utils.PreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountAfterLoginFragment extends androidx.fragment.app.Fragment implements View.OnClickListener {
    @BindView(R.id.ragment_account_after_login_logout_linear)
    LinearLayoutCompat ragment_account_after_login_logout_linear;

    @BindView(R.id.fragment_account_after_login_profile_linear)
    LinearLayoutCompat fragment_account_after_login_profile_linear;

    @BindView(R.id.fragment_account_after_login_orders_linear)
    LinearLayoutCompat fragment_account_after_login_orders_linear;

    @BindView(R.id.fragment_account_after_login_likes_linear)
    LinearLayoutCompat fragment_account_after_login_likes_linear;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_after_login, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        findViews();
    }
    public void findViews(){
        Fragment fragment = new FragmentAccountHelpers();
        getFragmentManager().beginTransaction().replace(R.id.fragment_account_after_login_getHelers , fragment).commit();

        ragment_account_after_login_logout_linear.setOnClickListener(this);
        fragment_account_after_login_profile_linear.setOnClickListener(this);
        fragment_account_after_login_orders_linear.setOnClickListener(this);
        fragment_account_after_login_likes_linear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.ragment_account_after_login_logout_linear:{
                PreferenceUtils.saveUserLogin(getActivity() , false);
                PreferenceUtils.saveUserToken(getActivity(), "");
                PreferenceUtils.saveCompanyToken(getActivity(), "");
                PreferenceUtils.saveCompanyLogin(getActivity(), false);
                PreferenceUtils.saveCountOfItemsBasket(getActivity(), 0);
                startActivity(new Intent(getActivity(), MainActivity.class));
                Animatoo.animateSwipeLeft(getActivity());
                break;
            }
            case R.id.fragment_account_after_login_profile_linear:{
                startActivity(new Intent(getActivity(), ProfileActivity.class));
                Animatoo.animateSwipeLeft(getActivity());
                break;
            }
            case R.id.fragment_account_after_login_orders_linear:{
                startActivity(new Intent(getActivity(), OrdersActivity.class));
                Animatoo.animateSwipeLeft(getActivity());
                break;
            }
            case R.id.fragment_account_after_login_likes_linear:{
                startActivity(new Intent(getActivity(), LikesElementsActivity.class));
                Animatoo.animateSwipeLeft(getActivity());
                break;
            }
        }
    }
}
