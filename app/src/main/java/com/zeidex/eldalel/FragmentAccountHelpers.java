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

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentAccountHelpers extends Fragment implements View.OnClickListener {
    @BindView(R.id.fragment_account_links_conditions_linear)
    LinearLayoutCompat fragment_account_links_conditions_linear;

    @BindView(R.id.fragment_account_contact_us_linear)
    LinearLayoutCompat fragment_account_contact_us_linear;


    @BindView(R.id.fragment_account_links_privacy_linear)
    LinearLayoutCompat fragment_account_links_privacy_linear;

    @BindView(R.id.fragment_account_links_cancel_linear)
    LinearLayoutCompat fragment_account_links_cancel_linear;


    @BindView(R.id.fragment_account_links_exchange_linear)
    LinearLayoutCompat fragment_account_links_exchange_linear;

    @BindView(R.id.fragment_account_links_connect_linear)
    LinearLayoutCompat fragment_account_links_connect_linear;


    @BindView(R.id.fragment_account_links_guarantee_linear)
    LinearLayoutCompat fragment_account_links_guarantee_linear;

    @BindView(R.id.fragment_account_links_support_linear)
    LinearLayoutCompat fragment_account_links_support_linear;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_helper, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        findViews();
    }

    public void findViews() {
        fragment_account_links_conditions_linear.setOnClickListener(this);
        fragment_account_contact_us_linear.setOnClickListener(this);

        fragment_account_links_privacy_linear.setOnClickListener(this);
        fragment_account_links_cancel_linear.setOnClickListener(this);

        fragment_account_links_exchange_linear.setOnClickListener(this);
        fragment_account_links_connect_linear.setOnClickListener(this);

        fragment_account_links_guarantee_linear.setOnClickListener(this);
        fragment_account_links_support_linear.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fragment_account_links_conditions_linear: {
                startActivity(new Intent(getActivity() , ConditionsActivity.class));
                Animatoo.animateSwipeLeft(getActivity());
                break;
            }

            case R.id.fragment_account_links_privacy_linear: {
                startActivity(new Intent(getActivity() , PrivacyActivity.class));
                Animatoo.animateSwipeLeft(getActivity());
                break;
            }

            case R.id.fragment_account_links_cancel_linear: {
                startActivity(new Intent(getActivity() , CancelActivity.class));
                Animatoo.animateSwipeLeft(getActivity());
                break;
            }

            case R.id.fragment_account_links_exchange_linear: {
                startActivity(new Intent(getActivity() , ExchangeActivity.class));
                Animatoo.animateSwipeLeft(getActivity());
                break;
            }

            case R.id.fragment_account_links_connect_linear: {
                startActivity(new Intent(getActivity() , ConnectActivity.class));
                Animatoo.animateSwipeLeft(getActivity());
                break;
            }

            case R.id.fragment_account_links_guarantee_linear: {
                startActivity(new Intent(getActivity() , GuaranteeActivity.class));
                Animatoo.animateSwipeLeft(getActivity());
                break;
            }

            case R.id.fragment_account_links_support_linear: {
                startActivity(new Intent(getActivity() , SupportActivity.class));
                Animatoo.animateSwipeLeft(getActivity());
                break;
            }
            case R.id.fragment_account_contact_us_linear:{
                startActivity(new Intent(getActivity() , ContactUsActivity.class));
                Animatoo.animateSwipeLeft(getActivity());
                break;
            }
        }
    }
}
