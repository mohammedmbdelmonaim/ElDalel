package com.zeidex.eldalel;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.zeidex.eldalel.response.GetAddAddressResponse;
import com.zeidex.eldalel.response.GetCities;
import com.zeidex.eldalel.response.GetCountries;
import com.zeidex.eldalel.response.GetRegions;
import com.zeidex.eldalel.services.AddAddressApi;
import com.zeidex.eldalel.services.CitiesApi;
import com.zeidex.eldalel.services.CountriesApi;
import com.zeidex.eldalel.services.EditAddressApi;
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

public class AddNewAddressFragment extends Fragment {
    @BindView(R.id.address_add_go_enter_text)
    AppCompatTextView address_add_go_enter_text;

    @BindView(R.id.address_add_first_name_edittext)
    AppCompatEditText address_add_first_name_edittext;

    @BindView(R.id.address_add_last_name_edittext)
    AppCompatEditText address_add_last_name_edittext;

    @BindView(R.id.address_add_mail_edittext)
    AppCompatEditText address_add_mail_edittext;

    @BindView(R.id.address_add_postal_code_edittext)
    AppCompatEditText address_add_postal_code_edittext;

    @BindView(R.id.address_add_pass_confirm_edittext)
    AppCompatEditText address_add_pass_confirm_edittext;

    @BindView(R.id.address_add_responsible_edittext)
    AppCompatEditText address_add_responsible_edittext;

    @BindView(R.id.address_add_mobile_edittext)
    AppCompatEditText address_add_mobile_edittext;

    @BindView(R.id.address_add_address_edittext)
    AppCompatEditText address_add_address_edittext;

    @BindView(R.id.address_add_name_company_edittext)
    AppCompatEditText address_add_name_company_edittext;

    @BindView(R.id.address_add_radio_group)
    RadioGroup address_add_radio_group;

    @BindView(R.id.address_add_radio_group2)
    RadioGroup address_add_radio_group2;

    @BindView(R.id.radio_company_address_add)
    RadioButton radio_company_address_add;

    @BindView(R.id.radio_indivdual_address_add)
    RadioButton radio_indivdual_address_add;

    @BindView(R.id.radio_ar_address_add)
    RadioButton radio_ar_address_add;

    @BindView(R.id.radio_en_address_add)
    RadioButton radio_en_address_add;

    @BindView(R.id.spinner_address_add_country)
    MaterialSpinner spinner_address_add_country;

    @BindView(R.id.spinner_address_add_region)
    MaterialSpinner spinner_address_add_region;

    @BindView(R.id.spinner_address_add_city)
    MaterialSpinner spinner_address_add_city;

    @BindView(R.id.address_add_responsible_linear)
    LinearLayoutCompat address_add_responsible_linear;

    @BindView(R.id.linear_address_add_country)
    LinearLayoutCompat linear_address_add_country;

    @BindView(R.id.linear_address_add_region)
    LinearLayoutCompat linear_address_add_region;

    @BindView(R.id.linear_address_add_city)
    LinearLayoutCompat linear_address_add_city;

    @BindView(R.id.address_add_mobile_linear)
    LinearLayoutCompat address_add_mobile_linear;

    @BindView(R.id.address_add_address_linear)
    LinearLayoutCompat address_add_address_linear;

    @BindView(R.id.address_add_name_company_linear)
    LinearLayoutCompat address_add_name_company_linear;

    @BindView(R.id.address_add_first_name_linear)
    LinearLayoutCompat address_add_first_name_linear;

    @BindView(R.id.address_add_last_name_linear)
    LinearLayoutCompat address_add_last_name_linear;

    String token = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_address, container, false);

    }
    String state_addresses;
    int address_id;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        Bundle args = getArguments();
        state_addresses = args.getString("from");
        address_id = args.getInt("id");

        if (address_id > 0){
            address_add_go_enter_text.setText(getString(R.string.address_add_go_update_text));
            getDataAddress(args);

        }

        if (state_addresses.equalsIgnoreCase("address")){
            ((AddressesActivity)getActivity()).activity_address_header_item.setVisibility(View.GONE);
        }else if(state_addresses.equalsIgnoreCase("payment")){
            ((PaymentActivity)getActivity()).activity_payment_steps_linear.setVisibility(View.GONE);
            ((PaymentActivity)getActivity()).activity_payment_header_item.setVisibility(View.GONE);
        }


        if (PreferenceUtils.getCompanyLogin(getActivity())) {
            token = PreferenceUtils.getCompanyToken(getActivity());
        } else if (PreferenceUtils.getUserLogin(getActivity())) {
            token = PreferenceUtils.getUserToken(getActivity());
        }



        showDialog();
        getCountries();

        countries = new ArrayList<>();
        ids_countries = new ArrayList<>();
        countries.add(getString(R.string.country_spinner_label));
        ids_countries.add(-1);
        spinner_address_add_country.setItems(countries);

        regions = new ArrayList<>();
        ids_regions = new ArrayList<>();
        regions.add(getString(R.string.regions_spinner_label));
        ids_regions.add(-1);
        spinner_address_add_region.setItems(regions);

        cities = new ArrayList<>();
        ids_cities = new ArrayList<>();
        cities.add(getString(R.string.spinner_city_label));
        ids_cities.add(-1);
        spinner_address_add_city.setItems(cities);

        address_add_radio_group2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_ar_address_add:
                        // do operations specific to this selection
                        lang = "arabic";
                        break;
                    case R.id.radio_en_address_add:
                        // do operations specific to this selection
                        lang = "english";
                        break;
                }
            }
        });
    }

    @OnClick(R.id.address_add_go_enter_text)
    public void onAddAddress() {
        if (address_id > 0){
            editAddress();
        }else {
            checkAddresses();
        }

    }

    public void getDataAddress(Bundle args){
        address_add_first_name_edittext.setText(args.getString("first_name"));
        address_add_last_name_edittext.setText(args.getString("last_name"));
        address_add_address_edittext.setText(args.getString("address"));
        address_add_postal_code_edittext.setText(args.getString("postal_code"));
        address_add_mobile_edittext.setText(args.getString("mobile"));

        Locale locale = ChangeLang.getLocale(getResources());
        String loo = locale.getLanguage();
        if (loo.equalsIgnoreCase("ar")){
            lang = "arabic";
            address_add_radio_group2.check(R.id.radio_ar_address_add);
        }else if (loo.equalsIgnoreCase("en")){
            lang = "english";
            address_add_radio_group2.check(R.id.radio_en_address_add);
        }

        id_country = args.getInt("country_id");
        id_region = args.getInt("subsidiary_id");
        id_city = args.getInt("city_id");
    }

    public void editAddress(){
        if (lang.equalsIgnoreCase("")) {
            Toasty.error(getActivity(), getString(R.string.choose_lang), Toast.LENGTH_LONG).show();
            return;
        }
        if (id_country == -1) {
            Toasty.error(getActivity(), getString(R.string.choose_country), Toast.LENGTH_LONG).show();
            return;
        }

        if (id_region == -1) {
            Toasty.error(getActivity(), getString(R.string.choose_region), Toast.LENGTH_LONG).show();
            return;
        }

        if (id_city == -1) {
            Toasty.error(getActivity(), getString(R.string.choose_city), Toast.LENGTH_LONG).show();
            return;
        }
        final boolean fieldscomOK = validate(new EditText[]{address_add_first_name_edittext, address_add_last_name_edittext, address_add_address_edittext, address_add_mobile_edittext, address_add_postal_code_edittext});

        if (fieldscomOK && id_country != -1 && id_region != -1 && id_city != -1) {

            reloadDialog.show();
            convertDaraToJson();
            address_post.put("id", String.valueOf(address_id));
            EditAddressApi editAddressApi = APIClient.getClient(SERVER_API_TEST).create(EditAddressApi.class);
            Call<GetAddAddressResponse> getEditAddressResponseCall = editAddressApi.editAddressApi(address_post);
            getEditAddressResponseCall.enqueue(new Callback<GetAddAddressResponse>() {
                @Override
                public void onResponse(Call<GetAddAddressResponse> call, Response<GetAddAddressResponse> response) {
                    GetAddAddressResponse getAddAddressResponse = response.body();
                    if (getAddAddressResponse.isSuccess()){
                        Toasty.success(getActivity(), getString(R.string.edit_address_success), Toast.LENGTH_LONG).show();
                        Fragment fragment = new ShoopingListAddressesFragment();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.setCustomAnimations(R.anim.animate_slide_up_enter, R.anim.animate_slide_up_exit);
                        if (state_addresses.equalsIgnoreCase("address")){
                            ft.replace(R.id.activity_address_constraint, fragment, fragment.getTag());
                        }else if(state_addresses.equalsIgnoreCase("payment")){
                            ft.replace(R.id.payment_constrant, fragment, fragment.getTag());
                        }
//                        ft.addToBackStack(null);
                        ft.commit();
                    }else {
                        String errors = "";
                        for (String errorText : getAddAddressResponse.getError()){
                            errors += errorText;
                        }
                        Toasty.error(getActivity(), errors, Toast.LENGTH_LONG).show();
                    }
                    reloadDialog.dismiss();
                }

                @Override
                public void onFailure(Call<GetAddAddressResponse> call, Throwable t) {
                    Toasty.error(getActivity(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                    reloadDialog.dismiss();
                }
            });
        }
    }

    String lang = "";
    Map<String, String> address_post;

    private void convertDaraToJson() {
        address_post = new HashMap<>();
        String name = address_add_first_name_edittext.getText().toString();
        String last_name = address_add_last_name_edittext.getText().toString();
        String address = address_add_address_edittext.getText().toString();
        String mobile = address_add_mobile_edittext.getText().toString();
        String postal_code = address_add_postal_code_edittext.getText().toString();
        address_post.put("first_name", name);
        address_post.put("token", token);
        address_post.put("last_name", last_name);
        address_post.put("address", address);
        address_post.put("mobile", mobile);
        address_post.put("postal_code", postal_code);
        address_post.put("language", lang);
        address_post.put("country_id", String.valueOf(id_country));
        address_post.put("subsidiary_id", String.valueOf(id_region));
        address_post.put("city_id", String.valueOf(id_city));
    }


    String errors;

    public void checkAddresses() {
        if (lang.equalsIgnoreCase("")) {
            Toasty.error(getActivity(), getString(R.string.choose_lang), Toast.LENGTH_LONG).show();
            return;
        }
        if (id_country == -1) {
            Toasty.error(getActivity(), getString(R.string.choose_country), Toast.LENGTH_LONG).show();
            return;
        }

        if (id_region == -1) {
            Toasty.error(getActivity(), getString(R.string.choose_region), Toast.LENGTH_LONG).show();
            return;
        }

        if (id_city == -1) {
            Toasty.error(getActivity(), getString(R.string.choose_city), Toast.LENGTH_LONG).show();
            return;
        }
        final boolean fieldscomOK = validate(new EditText[]{address_add_first_name_edittext, address_add_last_name_edittext, address_add_address_edittext, address_add_mobile_edittext, address_add_postal_code_edittext});

        if (fieldscomOK && id_country != -1 && id_region != -1 && id_city != -1) {
            reloadDialog.show();
            convertDaraToJson();

            AddAddressApi addAddressApi = APIClient.getClient(SERVER_API_TEST).create(AddAddressApi.class);
            Call<GetAddAddressResponse> getAddAddressResponseCall = addAddressApi.getAddressApi(address_post);
            getAddAddressResponseCall.enqueue(new Callback<GetAddAddressResponse>() {
                @Override
                public void onResponse(Call<GetAddAddressResponse> call, Response<GetAddAddressResponse> response) {
                    GetAddAddressResponse getAddAddressResponse = response.body();
                    if (getAddAddressResponse.isSuccess()){
                        Toasty.success(getActivity(), getString(R.string.add_address_sucess), Toast.LENGTH_LONG).show();
                        Fragment fragment = new ShoopingListAddressesFragment();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.setCustomAnimations(R.anim.animate_slide_up_enter, R.anim.animate_slide_up_exit);
                        if (state_addresses.equalsIgnoreCase("address")){
                            ft.replace(R.id.activity_address_constraint, fragment, fragment.getTag());
                        }else if(state_addresses.equalsIgnoreCase("payment")){
                            ft.replace(R.id.payment_constrant, fragment, fragment.getTag());
                        }
//                        ft.addToBackStack(null);
                        ft.commit();
                    }else {
                        String errors = "";
                        for (String errorText : getAddAddressResponse.getError()){
                            errors += errorText;
                        }
                        Toasty.error(getActivity(), errors, Toast.LENGTH_LONG).show();
                    }
                    reloadDialog.dismiss();
                }

                @Override
                public void onFailure(Call<GetAddAddressResponse> call, Throwable t) {
                    Toasty.error(getActivity(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                    reloadDialog.dismiss();
                }
            });
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
                    Locale locale = ChangeLang.getLocale(getResources());
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
                    spinner_address_add_country.setItems(countries);
                    spinner_address_add_country.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
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
                    Locale locale = ChangeLang.getLocale(getResources());
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

                    spinner_address_add_region.setItems(regions);
                    spinner_address_add_region.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
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


    ArrayList<String> cities;
    ArrayList<Integer> ids_cities;
    int id_city = -1;

    public void getCities(int region_id) {
        reloadDialog.show();
        CitiesApi citiesApi = APIClient.getClient(SERVER_API_TEST).create(CitiesApi.class);
        Call<GetCities> getCitiesCall = citiesApi.getCities(region_id);
        getCitiesCall.enqueue(new Callback<GetCities>() {
            @Override
            public void onResponse(Call<GetCities> call, Response<GetCities> response) {
                GetCities getCities = response.body();
                Locale locale = ChangeLang.getLocale(getResources());
                String loo = locale.getLanguage();
                if (loo.equalsIgnoreCase("en")) {
                    for (int i = 0; i < getCities.getCities().size(); i++) { //category loop
                        cities.add(getCities.getCities().get(i).getName_en());
                        ids_cities.add(Integer.parseInt(getCities.getCities().get(i).getId()));
                    }

                } else if (loo.equalsIgnoreCase("ar")) {
                    for (int i = 0; i < getCities.getCities().size(); i++) { //category loop
                        cities.add(getCities.getCities().get(i).getName_ar());
                        ids_cities.add(Integer.parseInt(getCities.getCities().get(i).getId()));
                    }
                }
                if (ids_cities.contains(id_city)){
                    int index = ids_cities.indexOf(id_city);
                    Collections.swap(ids_cities , 0 , index);
                    Collections.swap(cities , 0 , index);
                }

                spinner_address_add_city.setItems(cities);
                spinner_address_add_city.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                        id_city = ids_cities.get(position);
                    }
                });

                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetCities> call, Throwable t) {
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
