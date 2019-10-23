package com.zeidex.eldalel;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.zeidex.eldalel.response.GetBranches;
import com.zeidex.eldalel.response.GetCountries;
import com.zeidex.eldalel.response.GetMakeOrderResponse;
import com.zeidex.eldalel.response.GetRegions;
import com.zeidex.eldalel.services.BranchesApi;
import com.zeidex.eldalel.services.CountriesApi;
import com.zeidex.eldalel.services.MakeOrderApi;
import com.zeidex.eldalel.services.RegionsApi;
import com.zeidex.eldalel.utils.APIClient;
import com.zeidex.eldalel.utils.ChangeLang;
import com.zeidex.eldalel.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class PayidFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.fragment_payid_paying)
    AppCompatTextView fragment_payid_paying;

    @BindView(R.id.spinner_address_add_regions_shipment)
    MaterialSpinner spinner_address_add_regions_shipment;

    @BindView(R.id.spinner_address_add_country_shipment)
    MaterialSpinner spinner_address_add_country_shipment;

    @BindView(R.id.spinner_address_add_branches_shipment)
    MaterialSpinner spinner_address_add_branches_shipment;

    @BindView(R.id.shipment_from_branch)
    LinearLayoutCompat shipment_from_branch;

    @BindView(R.id.shipment_to_address)
    LinearLayoutCompat shipment_to_address;

    @BindView(R.id.fragment_payid_linear_options_branches)
    LinearLayoutCompat fragment_payid_linear_options_branches;

    @BindView(R.id.shipment_from_branch_img)
    AppCompatImageView shipment_from_branch_img;

    @BindView(R.id.shipment_to_address_img)
    AppCompatImageView shipment_to_address_img;

    @BindView(R.id.shipment_from_branch_txt)
    TextView shipment_from_branch_txt;

    @BindView(R.id.shipment_to_address_txt)
    TextView shipment_to_address_txt;

    @BindView(R.id.credit_card_payment)
    LinearLayoutCompat credit_card_payment;

    @BindView(R.id.credit_card_payment_txt)
    AppCompatTextView credit_card_payment_txt;

    @BindView(R.id.fragment_payid_tax_text)
    AppCompatTextView fragment_payid_tax_text;

    @BindView(R.id.credit_card_payment_img)
    AppCompatImageView credit_card_payment_img;

    @BindView(R.id.credit_card_payment_imgcheck)
    AppCompatImageView credit_card_payment_imgcheck;

    @BindView(R.id.bank_payment)
    LinearLayoutCompat bank_payment;

    @BindView(R.id.bank_payment_txt)
    AppCompatTextView bank_payment_txt;

    @BindView(R.id.bank_payment_img)
    AppCompatImageView bank_payment_img;

    @BindView(R.id.bank_payment_imgcheck)
    AppCompatImageView bank_payment_imgcheck;

    @BindView(R.id.pay_on_arrive_payment)
    LinearLayoutCompat pay_on_arrive_payment;

    @BindView(R.id.pay_on_arrive_payment_txt)
    AppCompatTextView pay_on_arrive_payment_txt;

    @BindView(R.id.pay_on_arrive_payment_img)
    AppCompatImageView pay_on_arrive_payment_img;

    @BindView(R.id.pay_on_arrive_payment_imgcheck)
    AppCompatImageView pay_on_arrive_payment_imgcheck;

    @BindView(R.id.fragment_payid_total_products_text)
    AppCompatTextView fragment_payid_total_products_text;

    @BindView(R.id.fragment_payid_total_price_text)
    AppCompatTextView fragment_payid_total_price_text;

    int shipment_type = 1;
    int shipment_method = 0;
    int address_id = ShoopingListAddressesFragment.address_id;
    String total_price = BasketFragment.totalString;
    String total_products = BasketFragment.totalwithoutString;
    String tax = BasketFragment.taxString;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payid, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        showDialog();
        countries = new ArrayList<>();
        ids_countries = new ArrayList<>();
        countries.add(getString(R.string.country_spinner_label));
        ids_countries.add(-1);
        spinner_address_add_country_shipment.setItems(countries);

        regions = new ArrayList<>();
        ids_regions = new ArrayList<>();
        regions.add(getString(R.string.regions_spinner_label));
        ids_regions.add(-1);
        spinner_address_add_regions_shipment.setItems(regions);

        branches = new ArrayList<>();
        ids_branches = new ArrayList<>();
        branches.add(getString(R.string.branch_spinner));
        ids_branches.add(-1);
        spinner_address_add_branches_shipment.setItems(branches);
        fragment_payid_total_products_text.setText(total_products);
        fragment_payid_total_price_text.setText(total_price);
        fragment_payid_tax_text.setText(tax);
        getCountries();

        fragment_payid_paying.setOnClickListener(this);
        shipment_to_address.setSelected(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            shipment_from_branch_img.setImageDrawable(getActivity().getDrawable(R.drawable.ic_company_unchecked));
            shipment_to_address_img.setImageDrawable(getActivity().getDrawable(R.drawable.ic_shipped_car_checked));

        }else {
            shipment_to_address_img.setImageResource(R.drawable.ic_shipped_car_checked);
            shipment_from_branch_img.setImageResource(R.drawable.ic_company_unchecked);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            shipment_to_address_txt.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
            shipment_from_branch_txt.setTextColor(getActivity().getColor(R.color.colorshipmenttype));
        }else{
            shipment_to_address_txt.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
            shipment_from_branch_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
        }
    }

    Map<String, String> payment_post;

    private void convertDaraToJson() {
        payment_post = new HashMap<>();
        payment_post.put("payment_type", String.valueOf(shipment_method));
        payment_post.put("address_type", String.valueOf(shipment_type));
        payment_post.put("language", lang);
        payment_post.put("token", token);
        if (shipment_type == 2) {
            payment_post.put("subsidiary_id", String.valueOf(id_region));
            payment_post.put("showroom_id", String.valueOf(id_branch));
        }
        if (!PreferenceUtils.getCoupoun(getActivity()).equalsIgnoreCase("")){
            payment_post.put("coupon", PreferenceUtils.getCoupoun(getActivity()));
        }
    }

    String lang;
    String token;
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fragment_payid_paying:{
                if (shipment_type == 2) {
                    if (id_country == -1) {
                        Toasty.error(getActivity(), getString(R.string.choose_country), Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (id_region == -1) {
                        Toasty.error(getActivity(), getString(R.string.choose_region), Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (id_branch == -1) {
                        Toasty.error(getActivity(), getString(R.string.choose_branch), Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                if (shipment_method == 0){
                    Toasty.error(getActivity(), getString(R.string.choose_payment_method), Toast.LENGTH_LONG).show();
                    return;
                }
                if (PreferenceUtils.getCompanyLogin(getActivity())) {
                    token = PreferenceUtils.getCompanyToken(getActivity());
                } else if (PreferenceUtils.getUserLogin(getActivity())) {
                    token = PreferenceUtils.getUserToken(getActivity());
                }
                Locale locale = ChangeLang.getLocale(getContext().getResources());
                String loo = locale.getLanguage();
                if (loo.equalsIgnoreCase("ar")){
                    lang = "arabic";
                }else if (loo.equalsIgnoreCase("en")) {
                    lang = "english";
                }

                convertDaraToJson();

                reloadDialog.show();
                MakeOrderApi makeOrderApi = APIClient.getClient(SERVER_API_TEST).create(MakeOrderApi.class);
                Call<GetMakeOrderResponse> getMakeOrderResponseCall;
                if (PreferenceUtils.getCompanyLogin(getActivity())){
                    getMakeOrderResponseCall = makeOrderApi.makeOrderResponsecompany(payment_post);
                }else{
                    getMakeOrderResponseCall = makeOrderApi.makeOrderResponse(payment_post);
                }

                getMakeOrderResponseCall.enqueue(new Callback<GetMakeOrderResponse>() {
                    @Override
                    public void onResponse(Call<GetMakeOrderResponse> call, Response<GetMakeOrderResponse> response) {
                        GetMakeOrderResponse getMakeOrderResponse = response.body();
                        if (getMakeOrderResponse.getSuccess().equalsIgnoreCase("true")){
                            ((PaymentActivity)getActivity()).goToOrderFragment();
                            PreferenceUtils.saveCoupon(getActivity() , "");
                            PreferenceUtils.saveCountOfItemsBasket(getActivity() , 0);
                        }else{
                            Toasty.error(getActivity(), getMakeOrderResponse.getErroe(), Toast.LENGTH_LONG).show();

                        }
                        reloadDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<GetMakeOrderResponse> call, Throwable t) {
                        Toasty.error(getActivity(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                        reloadDialog.dismiss();
                    }
                });


                break;
            }
        }
    }

    @OnClick(R.id.shipment_from_branch)
    public void fromBranch(){
        shipment_type = 2;
        shipment_from_branch.setSelected(true);
        shipment_to_address.setSelected(false);
        fragment_payid_linear_options_branches.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            shipment_from_branch_img.setImageDrawable(getActivity().getDrawable(R.drawable.ic_company));
            shipment_to_address_img.setImageDrawable(getActivity().getDrawable(R.drawable.ic_shipped_car));

        }else {
            shipment_to_address_img.setImageResource(R.drawable.ic_shipped_car);
            shipment_from_branch_img.setImageResource(R.drawable.ic_company);
        }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                shipment_to_address_txt.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorshipmenttype));
                shipment_from_branch_txt.setTextColor(getActivity().getColor(R.color.colorPrimaryDark));
            }else{
                shipment_to_address_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
                shipment_from_branch_txt.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
            }

    }

    @OnClick(R.id.shipment_to_address)
    public void toAddress(){
        shipment_type = 1;
        shipment_from_branch.setSelected(false);
        shipment_to_address.setSelected(true);
        fragment_payid_linear_options_branches.setVisibility(View.GONE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            shipment_from_branch_img.setImageDrawable(getActivity().getDrawable(R.drawable.ic_company_unchecked));
            shipment_to_address_img.setImageDrawable(getActivity().getDrawable(R.drawable.ic_shipped_car_checked));

        }else {
            shipment_to_address_img.setImageResource(R.drawable.ic_shipped_car_checked);
            shipment_from_branch_img.setImageResource(R.drawable.ic_company_unchecked);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            shipment_to_address_txt.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
            shipment_from_branch_txt.setTextColor(getActivity().getColor(R.color.colorshipmenttype));
        }else{
            shipment_to_address_txt.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
            shipment_from_branch_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
        }
    }

    @OnClick(R.id.credit_card_payment)
    public void checkCreditCard(){
        shipment_method = 1;
        credit_card_payment.setSelected(true);
        bank_payment.setSelected(false);
        pay_on_arrive_payment.setSelected(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            credit_card_payment_img.setImageDrawable(getActivity().getDrawable(R.drawable.ic_credit_card_check));
            bank_payment_img.setImageDrawable(getActivity().getDrawable(R.drawable.ic_bank_pay));
            pay_on_arrive_payment_img.setImageDrawable(getActivity().getDrawable(R.drawable.ic_pay_on_arrive));

        }else {
            credit_card_payment_img.setImageResource(R.drawable.ic_credit_card_check);
            bank_payment_img.setImageResource(R.drawable.ic_bank_pay);
            pay_on_arrive_payment_img.setImageResource(R.drawable.ic_pay_on_arrive);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            credit_card_payment_imgcheck.setImageDrawable(getActivity().getDrawable(R.drawable.ic_checked_payment));
            bank_payment_imgcheck.setImageDrawable(getActivity().getDrawable(R.drawable.ic_unchecked_pay_method));
            pay_on_arrive_payment_imgcheck.setImageDrawable(getActivity().getDrawable(R.drawable.ic_unchecked_pay_method));

        }else {
            credit_card_payment_img.setImageResource(R.drawable.ic_checked_payment);
            bank_payment_imgcheck.setImageResource(R.drawable.ic_unchecked_pay_method);
            pay_on_arrive_payment_imgcheck.setImageResource(R.drawable.ic_unchecked_pay_method);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            credit_card_payment_txt.setTextColor(ContextCompat.getColor(getActivity(), R.color.white_color));
            bank_payment_txt.setTextColor(getActivity().getColor(R.color.colorshipmenttype));
            pay_on_arrive_payment_txt.setTextColor(getActivity().getColor(R.color.colorshipmenttype));
        }else{
            credit_card_payment_txt.setTextColor(getContext().getResources().getColor(R.color.white_color));
            bank_payment_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
            pay_on_arrive_payment_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
        }
    }

    @OnClick(R.id.bank_payment)
    public void checkBank(){
        shipment_method = 3;
        credit_card_payment.setSelected(false);
        bank_payment.setSelected(true);
        pay_on_arrive_payment.setSelected(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            credit_card_payment_img.setImageDrawable(getActivity().getDrawable(R.drawable.ic_credit_card));
            bank_payment_img.setImageDrawable(getActivity().getDrawable(R.drawable.ic_bank_pay_checked));
            pay_on_arrive_payment_img.setImageDrawable(getActivity().getDrawable(R.drawable.ic_pay_on_arrive));

        }else {
            credit_card_payment_img.setImageResource(R.drawable.ic_credit_card);
            bank_payment_img.setImageResource(R.drawable.ic_bank_pay_checked);
            pay_on_arrive_payment_img.setImageResource(R.drawable.ic_pay_on_arrive);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            credit_card_payment_imgcheck.setImageDrawable(getActivity().getDrawable(R.drawable.ic_unchecked_payment));
            bank_payment_imgcheck.setImageDrawable(getActivity().getDrawable(R.drawable.ic_checked_payment));
            pay_on_arrive_payment_imgcheck.setImageDrawable(getActivity().getDrawable(R.drawable.ic_unchecked_pay_method));

        }else {
            credit_card_payment_img.setImageResource(R.drawable.ic_unchecked_pay_method);
            bank_payment_imgcheck.setImageResource(R.drawable.ic_checked_payment);
            pay_on_arrive_payment_imgcheck.setImageResource(R.drawable.ic_unchecked_pay_method);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            credit_card_payment_txt.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorshipmenttype));
            bank_payment_txt.setTextColor(getActivity().getColor(R.color.white_color));
            pay_on_arrive_payment_txt.setTextColor(getActivity().getColor(R.color.colorshipmenttype));
        }else{
            credit_card_payment_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
            bank_payment_txt.setTextColor(getContext().getResources().getColor(R.color.white_color));
            pay_on_arrive_payment_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
        }
    }

    @OnClick(R.id.pay_on_arrive_payment)
    public void checkPay(){
        shipment_method = 2;
        credit_card_payment.setSelected(false);
        bank_payment.setSelected(false);
        pay_on_arrive_payment.setSelected(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            credit_card_payment_img.setImageDrawable(getActivity().getDrawable(R.drawable.ic_credit_card));
            bank_payment_img.setImageDrawable(getActivity().getDrawable(R.drawable.ic_bank_pay));
            pay_on_arrive_payment_img.setImageDrawable(getActivity().getDrawable(R.drawable.ic_pay_on_arrive_checked));

        }else {
            credit_card_payment_img.setImageResource(R.drawable.ic_credit_card);
            bank_payment_img.setImageResource(R.drawable.ic_bank_pay);
            pay_on_arrive_payment_img.setImageResource(R.drawable.ic_pay_on_arrive_checked);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            credit_card_payment_imgcheck.setImageDrawable(getActivity().getDrawable(R.drawable.ic_unchecked_payment));
            bank_payment_imgcheck.setImageDrawable(getActivity().getDrawable(R.drawable.ic_unchecked_payment));
            pay_on_arrive_payment_imgcheck.setImageDrawable(getActivity().getDrawable(R.drawable.ic_checked_payment));

        }else {
            credit_card_payment_img.setImageResource(R.drawable.ic_unchecked_pay_method);
            bank_payment_imgcheck.setImageResource(R.drawable.ic_unchecked_payment);
            pay_on_arrive_payment_imgcheck.setImageResource(R.drawable.ic_checked_payment);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            credit_card_payment_txt.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorshipmenttype));
            bank_payment_txt.setTextColor(getActivity().getColor(R.color.colorshipmenttype));
            pay_on_arrive_payment_txt.setTextColor(getActivity().getColor(R.color.white_color));
        }else{
            credit_card_payment_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
            bank_payment_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
            pay_on_arrive_payment_txt.setTextColor(getContext().getResources().getColor(R.color.white_color));
        }
    }




    ArrayList<String> countries;
    ArrayList<Integer> ids_countries;
    int id_country = -1;

    public void getCountries() {
        reloadDialog.show();
        CountriesApi countriesApi = APIClient.getClient(SERVER_API_TEST).create(CountriesApi.class);
        Call<GetCountries> getCountriesCall = countriesApi.getCountries();
        getCountriesCall.enqueue(new Callback<GetCountries>() {
            @Override
            public void onResponse(Call<GetCountries> call, Response<GetCountries> response) {
                GetCountries getCountries = response.body();
                int code = Integer.parseInt(getCountries.getCode());
                if (code == 200) {
                    Locale locale = ChangeLang.getLocale(getContext().getResources());
                    String loo = locale.getLanguage();
                    if (loo.equalsIgnoreCase("en")) {
                        for (int i = 0; i < getCountries.getData().getCountries().size(); i++) { //category loop
                            countries.add(getCountries.getData().getCountries().get(i).getName_en());
                            ids_countries.add(Integer.parseInt(getCountries.getData().getCountries().get(i).getId()));
                        }

                    } else if (loo.equalsIgnoreCase("ar")) {
                        for (int i = 0; i < getCountries.getData().getCountries().size(); i++) { //category loop
                            countries.add(getCountries.getData().getCountries().get(i).getName_ar());
                            ids_countries.add(Integer.parseInt(getCountries.getData().getCountries().get(i).getId()));
                        }
                    }

                    if (ids_countries.contains(id_country)){
                        int index = ids_countries.indexOf(id_country);
                        Collections.swap(ids_countries , 0 , index);
                        Collections.swap(countries , 0 , index);
                        getRegions(id_country);
                    }
                    spinner_address_add_country_shipment.setItems(countries);
                    spinner_address_add_country_shipment.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                            id_country = ids_countries.get(position);
                            getRegions(id_country);
                        }
                    });
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetCountries> call, Throwable t) {
                Toasty.error(getActivity(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    ArrayList<String> regions;
    ArrayList<Integer> ids_regions;
    int id_region = -1;

    public void getRegions(int country_id) {
        reloadDialog.show();
        RegionsApi regionsApi = APIClient.getClient(SERVER_API_TEST).create(RegionsApi.class);
        Call<GetRegions> getRegionsCall = regionsApi.getRegions(country_id);
        getRegionsCall.enqueue(new Callback<GetRegions>() {
            @Override
            public void onResponse(Call<GetRegions> call, Response<GetRegions> response) {
                GetRegions getRegions = response.body();
                int code = Integer.parseInt(getRegions.getCode());
                if (code == 200) {
                    Locale locale = ChangeLang.getLocale(getContext().getResources());
                    String loo = locale.getLanguage();
                    if (loo.equalsIgnoreCase("en")) {
                        for (int i = 0; i < getRegions.getData().getSubsidiaries().size(); i++) { //category loop
                            regions.add(getRegions.getData().getSubsidiaries().get(i).getName_en());
                            ids_regions.add(Integer.parseInt(getRegions.getData().getSubsidiaries().get(i).getId()));
                        }

                    } else if (loo.equalsIgnoreCase("ar")) {
                        for (int i = 0; i < getRegions.getData().getSubsidiaries().size(); i++) { //category loop
                            regions.add(getRegions.getData().getSubsidiaries().get(i).getName_ar());
                            ids_regions.add(Integer.parseInt(getRegions.getData().getSubsidiaries().get(i).getId()));
                        }
                    }
                    if (ids_regions.contains(id_region)){
                        int index = ids_regions.indexOf(id_region);
                        Collections.swap(ids_regions , 0 , index);
                        Collections.swap(regions , 0 , index);
                        getCities(id_region);
                    }

                    spinner_address_add_regions_shipment.setItems(regions);
                    spinner_address_add_regions_shipment.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                            id_region = ids_regions.get(position);
                            getCities(id_region);
                        }
                    });
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetRegions> call, Throwable t) {
                Toasty.error(getActivity(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }


    ArrayList<String> branches;
    ArrayList<Integer> ids_branches;
    int id_branch = -1;

    public void getCities(int region_id) {
        reloadDialog.show();
        BranchesApi branchesApi = APIClient.getClient(SERVER_API_TEST).create(BranchesApi.class);
        Call<GetBranches> getBranchesCall = branchesApi.getBranches(region_id);
        getBranchesCall.enqueue(new Callback<GetBranches>() {
            @Override
            public void onResponse(Call<GetBranches> call, Response<GetBranches> response) {
                GetBranches getBranches = response.body();
                Locale locale = ChangeLang.getLocale(getContext().getResources());
                String loo = locale.getLanguage();
                if (loo.equalsIgnoreCase("en")) {
                    for (int i = 0; i < getBranches.getData().getShowrooms().size(); i++) { //category loop
                        branches.add(getBranches.getData().getShowrooms().get(i).getName_en());
                        ids_branches.add(Integer.parseInt(getBranches.getData().getShowrooms().get(i).getId()));
                    }

                } else if (loo.equalsIgnoreCase("ar")) {
                    for (int i = 0; i < getBranches.getData().getShowrooms().size(); i++) { //category loop
                        branches.add(getBranches.getData().getShowrooms().get(i).getName_ar());
                        ids_branches.add(Integer.parseInt(getBranches.getData().getShowrooms().get(i).getId()));
                    }
                }
                if (ids_branches.contains(id_branch)){
                    int index = ids_branches.indexOf(id_branch);
                    Collections.swap(ids_branches , 0 , index);
                    Collections.swap(branches , 0 , index);
                }

                spinner_address_add_branches_shipment.setItems(branches);
                spinner_address_add_branches_shipment.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                        id_branch = ids_branches.get(position);
                    }
                });

                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetBranches> call, Throwable t) {
                Toasty.error(getActivity(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });


    }

    private boolean validate(EditText[] fields) {
        for (int i = 0; i < fields.length; i++) {
            EditText currentField = fields[i];
            if (currentField.getText().toString().length() <= 0) {
                currentField.setError(getString(R.string.field_required));
                currentField.requestFocus();
                return false;
            }
        }
        return true;
    }

    Dialog reloadDialog;

    private void showDialog() {
        reloadDialog = new Dialog(getActivity());
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
