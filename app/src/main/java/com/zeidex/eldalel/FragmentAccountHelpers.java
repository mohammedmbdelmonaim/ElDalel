package com.zeidex.eldalel;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import com.zeidex.eldalel.utils.Animatoo;
import com.zeidex.eldalel.utils.ChangeLang;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @BindView(R.id.fragment_account_language_linear)
    LinearLayoutCompat fragment_account_language_linear;

    @BindView(R.id.fragment_account_helper_text)
    AppCompatTextView fragment_account_helper_text;

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

    String lang;

    public void findViews() {
        Locale locale = ChangeLang.getLocale(getResources());
        String loo = locale.getLanguage();
        if (loo.equalsIgnoreCase("ar")) {
            lang = "arabic";
            fragment_account_helper_text.setText(getString(R.string.radio_ar_register));
        } else if (loo.equalsIgnoreCase("en")) {
            lang = "english";
            fragment_account_helper_text.setText(getString(R.string.radio_en_register));
        }


        fragment_account_links_conditions_linear.setOnClickListener(this);
        fragment_account_contact_us_linear.setOnClickListener(this);

        fragment_account_links_privacy_linear.setOnClickListener(this);
        fragment_account_links_cancel_linear.setOnClickListener(this);

        fragment_account_links_exchange_linear.setOnClickListener(this);
        fragment_account_links_connect_linear.setOnClickListener(this);

        fragment_account_links_guarantee_linear.setOnClickListener(this);
        fragment_account_links_support_linear.setOnClickListener(this);


    }

    Dialog changeLanguage;

    @OnClick(R.id.fragment_account_language_linear)
    public void chooseLanguage() {
        changeLanguage = new Dialog(getActivity());
        changeLanguage.requestWindowFeature(Window.FEATURE_NO_TITLE);
        changeLanguage.setContentView(R.layout.dialog_change_language);
        changeLanguage.setCancelable(true);
        AppCompatButton dialog_text_active_lang = changeLanguage.findViewById(R.id.dialog_text_active_lang);
        AppCompatButton dialog_text_deactive_lang = changeLanguage.findViewById(R.id.dialog_text_deactive_lang);
        if (lang.equalsIgnoreCase("arabic")) {
            dialog_text_active_lang.setText(getString(R.string.radio_ar_register));
            dialog_text_deactive_lang.setText(getString(R.string.radio_en_register));
        } else if (lang.equalsIgnoreCase("english")) {
            dialog_text_active_lang.setText(getString(R.string.radio_en_register));
            dialog_text_deactive_lang.setText(getString(R.string.radio_ar_register));
        }

        dialog_text_active_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog_text_active_lang.getText().toString().equalsIgnoreCase(getString(R.string.radio_ar_register))) {
                    lang = "arabic";
                    fragment_account_helper_text.setText(getString(R.string.radio_ar_register));
                    ChangeLang.setNewLocale(getActivity() , "ar");
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    Animatoo.animateSwipeLeft(getActivity());
                } else {
                    lang = "english";
                    fragment_account_helper_text.setText(getString(R.string.radio_en_register));
                    ChangeLang.setNewLocale(getActivity() , "en");
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    Animatoo.animateSwipeLeft(getActivity());
                }
                changeLanguage.dismiss();
            }
        });

        dialog_text_deactive_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog_text_deactive_lang.getText().toString().equalsIgnoreCase(getString(R.string.radio_ar_register))) {
                    lang = "arabic";
                    fragment_account_helper_text.setText(getString(R.string.radio_ar_register));
                    ChangeLang.setNewLocale(getActivity() , "ar");
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    Animatoo.animateSwipeLeft(getActivity());
                } else {
                    lang = "english";
                    fragment_account_helper_text.setText(getString(R.string.radio_en_register));
                    ChangeLang.setNewLocale(getActivity() , "en");
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    Animatoo.animateSwipeLeft(getActivity());
                }
                changeLanguage.dismiss();
            }
        });
        changeLanguage.show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fragment_account_links_conditions_linear: {
                startActivity(new Intent(getActivity(), ConditionsActivity.class));
                Animatoo.animateSwipeLeft(getActivity());
                break;
            }

            case R.id.fragment_account_links_privacy_linear: {
                startActivity(new Intent(getActivity(), PrivacyActivity.class));
                Animatoo.animateSwipeLeft(getActivity());
                break;
            }

            case R.id.fragment_account_links_cancel_linear: {
                startActivity(new Intent(getActivity(), CancelActivity.class));
                Animatoo.animateSwipeLeft(getActivity());
                break;
            }

            case R.id.fragment_account_links_exchange_linear: {
                startActivity(new Intent(getActivity(), ExchangeActivity.class));
                Animatoo.animateSwipeLeft(getActivity());
                break;
            }

            case R.id.fragment_account_links_connect_linear: {
                startActivity(new Intent(getActivity(), ConnectActivity.class));
                Animatoo.animateSwipeLeft(getActivity());
                break;
            }

            case R.id.fragment_account_links_guarantee_linear: {
                startActivity(new Intent(getActivity(), GuaranteeActivity.class));
                Animatoo.animateSwipeLeft(getActivity());
                break;
            }

            case R.id.fragment_account_links_support_linear: {
                startActivity(new Intent(getActivity(), SupportActivity.class));
                Animatoo.animateSwipeLeft(getActivity());
                break;
            }
            case R.id.fragment_account_contact_us_linear: {
                startActivity(new Intent(getActivity(), ContactUsActivity.class));
                Animatoo.animateSwipeLeft(getActivity());
                break;
            }
        }
    }
}
