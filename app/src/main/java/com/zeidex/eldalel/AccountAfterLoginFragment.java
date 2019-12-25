package com.zeidex.eldalel;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.zeidex.eldalel.response.LogoutResponse;
import com.zeidex.eldalel.services.LogoutApi;
import com.zeidex.eldalel.utils.APIClient;
import com.zeidex.eldalel.utils.Animatoo;
import com.zeidex.eldalel.utils.PreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class AccountAfterLoginFragment extends androidx.fragment.app.Fragment implements View.OnClickListener {
    @BindView(R.id.ragment_account_after_login_logout_linear)
    LinearLayoutCompat ragment_account_after_login_logout_linear;

    @BindView(R.id.fragment_account_after_login_profile_linear)
    LinearLayoutCompat fragment_account_after_login_profile_linear;

    @BindView(R.id.fragment_account_after_login_orders_linear)
    LinearLayoutCompat fragment_account_after_login_orders_linear;

    @BindView(R.id.fragment_account_after_login_likes_linear)
    LinearLayoutCompat fragment_account_after_login_likes_linear;

    @BindView(R.id.ragment_account_after_login_pay_linear)
    LinearLayoutCompat fragment_account_after_login_pay_linear;

    @BindView(R.id.fragment_account_after_login_wallet_linear)
    LinearLayoutCompat fragment_account_after_login_wallet_linear;

    @BindView(R.id.fragment_account_after_login_addresses_linear)
    LinearLayoutCompat fragment_account_after_login_addresses_linear;




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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                ((MainActivity)getContext()).navigateToHomeFragment();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @OnClick({R.id.fragment_account_after_login_addresses_linear})
    public void goToAddressesActivity(){
        startActivity(new Intent(getContext(), AddressesActivity.class));
        Animatoo.animateSwipeLeft(getContext());
    }

    public void findViews(){
        showDialog();

        if (PreferenceUtils.getCompanyLogin(getContext())) {
            fragment_account_after_login_addresses_linear.setVisibility(View.GONE);
            fragment_account_after_login_wallet_linear.setVisibility(View.GONE);
        }

        Fragment fragment = new FragmentAccountHelpers();
        getFragmentManager().beginTransaction().replace(R.id.fragment_account_after_login_getHelers , fragment).commit();

        ragment_account_after_login_logout_linear.setOnClickListener(this);
        fragment_account_after_login_profile_linear.setOnClickListener(this);
        fragment_account_after_login_orders_linear.setOnClickListener(this);
        fragment_account_after_login_likes_linear.setOnClickListener(this);
        fragment_account_after_login_pay_linear.setOnClickListener(this);
        fragment_account_after_login_wallet_linear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.ragment_account_after_login_logout_linear:{
                if(PreferenceUtils.getUserLogin(getContext())){
                    logoutUser();
                } else if (PreferenceUtils.getCompanyLogin(getContext())){
                    logoutCompany();
                }
                break;
            }
            case R.id.fragment_account_after_login_profile_linear:{
                startActivity(new Intent(getContext(), ProfileActivity.class));
                Animatoo.animateSwipeLeft(getContext());
                break;
            }
            case R.id.fragment_account_after_login_orders_linear:{
                startActivity(new Intent(getContext(), OrdersActivity.class));
                Animatoo.animateSwipeLeft(getContext());
                break;
            }
            case R.id.fragment_account_after_login_likes_linear:{
//                startActivity(new Intent(getContext(), LikesElementsFragment.class));
//                Animatoo.animateSwipeLeft(getContext());
                NavHostFragment.findNavController(this).navigate(AccountAfterLoginFragmentDirections.actionAccountAfterLoginFragmentToLikesElementsFragment());
                break;
            }
            case R.id.ragment_account_after_login_pay_linear:{
                startActivity(new Intent(getContext(), PaymentMethodsActivity.class));
                Animatoo.animateSwipeLeft(getContext());
                break;
            }
            case R.id.fragment_account_after_login_wallet_linear:{
                startActivity(new Intent(getContext(), WalletActivity.class));
                Animatoo.animateSwipeLeft(getContext());
                break;
            }
        }
    }

    void logoutUser() {
        reloadDialog.show();
        LogoutApi logoutApi = APIClient.getClient(SERVER_API_TEST).create(LogoutApi.class);
        logoutApi.logoutUser(PreferenceUtils.getUserToken(getContext())).enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                if(response.body() != null){
                    if(response.body().getSuccess().equals("success")){
                        Toasty.normal(getContext(), getString(R.string.log_out_success_toast), Toast.LENGTH_SHORT).show();
                        PreferenceUtils.saveUserLogin(getContext() , false);
                        PreferenceUtils.saveUserToken(getContext(), "");
                        PreferenceUtils.saveCountOfItemsBasket(getContext(), 0);
                        ((MainActivity) getActivity()).updateBasketBadge();
                        startActivity(new Intent(getContext(), MainActivity.class));
                        Animatoo.animateSwipeLeft(getContext());
                    }else{
                        Toasty.error(getContext(), response.body().getResponse(), Toast.LENGTH_SHORT).show();
                    }
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    void logoutCompany() {
        reloadDialog.show();
        LogoutApi logoutApi = APIClient.getClient(SERVER_API_TEST).create(LogoutApi.class);
        logoutApi.logoutCompany(PreferenceUtils.getCompanyToken(getContext())).enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                if(response.body() != null){
                    if(response.body().getSuccess().equals("success")){
                        Toasty.normal(getContext(), getString(R.string.log_out_success_toast), Toast.LENGTH_SHORT).show();
                        PreferenceUtils.saveCompanyToken(getContext(), "");
                        PreferenceUtils.saveCompanyLogin(getContext(), false);
                        PreferenceUtils.saveCountOfItemsBasket(getContext(), 0);
                        ((MainActivity) getActivity()).updateBasketBadge();
                        startActivity(new Intent(getContext(), MainActivity.class));
                        Animatoo.animateSwipeLeft(getContext());
                    }else{
                        Toasty.error(getContext(), response.body().getResponse(), Toast.LENGTH_SHORT).show();
                    }
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });

    }

    Dialog reloadDialog;

    private void showDialog() {
        reloadDialog = new Dialog(getContext());
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
