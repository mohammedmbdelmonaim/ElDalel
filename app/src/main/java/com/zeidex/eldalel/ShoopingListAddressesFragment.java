package com.zeidex.eldalel;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.adapters.AddressesAdapter;
import com.zeidex.eldalel.response.GetAddresses;
import com.zeidex.eldalel.response.GetChangeAddressResponse;
import com.zeidex.eldalel.services.AddressAPI;
import com.zeidex.eldalel.services.ChangeAddressToPrimaryApi;
import com.zeidex.eldalel.utils.APIClient;
import com.zeidex.eldalel.utils.ChangeLang;
import com.zeidex.eldalel.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class ShoopingListAddressesFragment extends Fragment implements View.OnClickListener, AddressesAdapter.AddressOperation {
    @BindView(R.id.fragment_shooping_list_addresses_recycler)
    RecyclerView fragment_shooping_list_addresses_recycler;

    @BindView(R.id.fragment_shooping_list_addresses_text_add)
    AppCompatTextView fragment_shooping_list_addresses_text_add;

    @BindView(R.id.fragment_shooping_list_addresses_text_next)
    AppCompatTextView fragment_shooping_list_addresses_text_next;

    @BindView(R.id.no_addresses)
    AppCompatTextView no_addresses;

    AddressesAdapter addressesAdapter;
    Dialog reloadDialog;


    List<GetAddresses.Address> addresses;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shooping_list_addresses, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        fragment_shooping_list_addresses_text_add.setOnClickListener(this);
        fragment_shooping_list_addresses_text_next.setOnClickListener(this);
        findViews();
        initializeRecycler();
    }

    public void initializeRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        fragment_shooping_list_addresses_recycler.setLayoutManager(layoutManager);
        fragment_shooping_list_addresses_recycler.setItemAnimator(new DefaultItemAnimator());
        fragment_shooping_list_addresses_recycler.addItemDecoration(new DividerItemDecoration(fragment_shooping_list_addresses_recycler.getContext(), DividerItemDecoration.VERTICAL));


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fragment_shooping_list_addresses_text_add: {
                Fragment fragment = new AddNewAddressFragment();
                Bundle args = new Bundle();
                args.putString("from", state_addresses);
                args.putInt("id" , -1);
                fragment.setArguments(args);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.animate_slide_up_enter, R.anim.animate_slide_up_exit);
                if (state_addresses.equalsIgnoreCase("address")){
                    ft.replace(R.id.activity_address_constraint, fragment, fragment.getTag());
                }else if(state_addresses.equalsIgnoreCase("payment")){
                    ft.replace(R.id.payment_constrant, fragment, fragment.getTag());
                }
                ft.commit();
                break;
            }
            case R.id.fragment_shooping_list_addresses_text_next: {
                ((PaymentActivity) getActivity()).goToPayidFragment();
                break;
            }
        }
    }

    String token;
    String state_addresses;
    private void findViews() {

        Bundle args = getArguments();
        state_addresses = args.getString("from");

        if (state_addresses.equalsIgnoreCase("address")){
            fragment_shooping_list_addresses_text_next.setVisibility(View.GONE);
        }else if(state_addresses.equalsIgnoreCase("payment")){
            fragment_shooping_list_addresses_text_next.setVisibility(View.VISIBLE);

        }

        if (PreferenceUtils.getCompanyLogin(getActivity())) {
            token = PreferenceUtils.getCompanyToken(getActivity());
        } else if (PreferenceUtils.getUserLogin(getActivity())) {
            token = PreferenceUtils.getUserToken(getActivity());
        }
        showDialog();
        onLoadPage();
    }

    private void onLoadPage() {
        reloadDialog.show();
        AddressAPI addressAPI = APIClient.getClient(SERVER_API_TEST).create(AddressAPI.class);
        addressAPI.getAllAddresses(token).enqueue(new Callback<GetAddresses>() {
            @Override
            public void onResponse(Call<GetAddresses> call, Response<GetAddresses> response) {
                    addresses = new ArrayList<>();
                    addresses.add(response.body().getPrimaryAddress());
                    addresses.addAll(response.body().getAddresses());
                    address_id = response.body().getPrimaryAddress().getId();
                    addressesAdapter = new AddressesAdapter(getActivity() , addresses , state_addresses);
                    addressesAdapter.setAddressOperation(ShoopingListAddressesFragment.this);
                    fragment_shooping_list_addresses_recycler.setAdapter(addressesAdapter);

                    if (addresses.size() == 0){
                        no_addresses.setVisibility(View.VISIBLE);
                    }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetAddresses> call, Throwable t) {
                Toasty.error(getActivity(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    private void showDialog() {
        reloadDialog = new Dialog(getActivity());
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public static int address_id;
    Dialog changeAddress;
    String lang;

    Map<String, String> update_post;

    private void convertDaraToJson(String lang, int address_id) {
        update_post = new HashMap<>();
        update_post.put("id", String.valueOf(address_id));
        update_post.put("language", lang);
        update_post.put("token", token);
    }

    @Override
    public void onClickAddress(int position, int address_id) {
        if (changeAddress != null){
            changeAddress.dismiss();
        }
            this.address_id = address_id;
            changeAddress = new Dialog(getActivity());
            changeAddress.requestWindowFeature(Window.FEATURE_NO_TITLE);
            changeAddress.setContentView(R.layout.dialog_change_address);
            changeAddress.setCancelable(false);

            AppCompatTextView dialog_ok = changeAddress.findViewById(R.id.dialog_text_ok);
            AppCompatTextView dialog_cancel = changeAddress.findViewById(R.id.dialog_text_cancel);
            dialog_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeAddress.dismiss();
                }
            });
            dialog_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeAddress.dismiss();
                    reloadDialog.show();
                    Locale locale = ChangeLang.getLocale(getResources());
                    String loo = locale.getLanguage();
                    if (loo.equalsIgnoreCase("en")) {
                        lang = "english";
                        convertDaraToJson("english" , address_id);
                    }else{
                        lang = "arabic";
                        convertDaraToJson("arabic" , address_id);
                    }
                    ChangeAddressToPrimaryApi changeAddressToPrimaryApi = APIClient.getClient(SERVER_API_TEST).create(ChangeAddressToPrimaryApi.class);
                    Call<GetChangeAddressResponse> getChangeAddressResponseCall = changeAddressToPrimaryApi.updateAddressApi(update_post);
                    getChangeAddressResponseCall.enqueue(new Callback<GetChangeAddressResponse>() {
                        @Override
                        public void onResponse(Call<GetChangeAddressResponse> call, Response<GetChangeAddressResponse> response) {
                            GetChangeAddressResponse getChangeAddressResponse = response.body();
                            if (getChangeAddressResponse.isSuccess()){
                                Toasty.success(getActivity(), getString(R.string.change_primary_address_success), Toast.LENGTH_LONG).show();
                                if (getChangeAddressResponse.getVerify() == 0){
                                    Fragment fragment = new PaymentPhoneNumberFragment();
                                    Bundle args = new Bundle();
                                    args.putString("mobile", addresses.get(position).getMobile());
                                    args.putString("from" , state_addresses);
                                    fragment.setArguments(args);
                                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                                    ft.setCustomAnimations(R.anim.animate_slide_up_enter, R.anim.animate_slide_up_exit);
                                    if (state_addresses.equalsIgnoreCase("address")){
                                        ft.replace(R.id.activity_address_constraint, fragment, fragment.getTag());
                                    }else if(state_addresses.equalsIgnoreCase("payment")){
                                        ft.replace(R.id.payment_constrant, fragment, fragment.getTag());
                                    }

//                        ft.addToBackStack(null);
                                    ft.commit();
                                }

                            }else {
                                String errors = "";
                                for (String errorText : getChangeAddressResponse.getError()){
                                    errors += errorText;
                                }
                                Toasty.error(getActivity(), errors, Toast.LENGTH_LONG).show();
                            }
                            reloadDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<GetChangeAddressResponse> call, Throwable t) {
                            Toasty.error(getActivity(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                            reloadDialog.dismiss();
                        }
                    });

                }
            });
            changeAddress.show();

    }

    @Override
    public void onEditAddress(int position, int address_id) {
        Fragment fragment = new AddNewAddressFragment();
        Bundle args = new Bundle();
        args.putString("from", "address");
        args.putInt("id" , address_id);
        args.putString("first_name" , addresses.get(position).getFirstName());
        args.putString("last_name" , addresses.get(position).getLastName());
        args.putString("address" , addresses.get(position).getAddress());
        args.putString("postal_code" , addresses.get(position).getPostalCode());
        args.putString("mobile" , addresses.get(position).getMobile());
        args.putInt("country_id" , addresses.get(position).getCountryId());
        args.putInt("subsidiary_id" , addresses.get(position).getSubsidiaryId());
        args.putInt("city_id" , addresses.get(position).getCityId());
        fragment.setArguments(args);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.animate_slide_up_enter, R.anim.animate_slide_up_exit);
        ft.replace(R.id.activity_address_constraint, fragment, fragment.getTag());
        ft.commit();
    }
}
