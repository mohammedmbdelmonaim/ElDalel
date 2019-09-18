package com.zeidex.eldalel;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.zeidex.eldalel.response.GetCities;
import com.zeidex.eldalel.response.GetCountries;
import com.zeidex.eldalel.response.GetRegions;
import com.zeidex.eldalel.response.GetRegisterCompanyResponse;
import com.zeidex.eldalel.response.GetRegisterResponse;
import com.zeidex.eldalel.services.CitiesApi;
import com.zeidex.eldalel.services.CountriesApi;
import com.zeidex.eldalel.services.RegionsApi;
import com.zeidex.eldalel.services.RegisterApi;
import com.zeidex.eldalel.services.RegisterCompanyApi;
import com.zeidex.eldalel.utils.APIClient;
import com.zeidex.eldalel.utils.Animatoo;
import com.zeidex.eldalel.utils.ChangeLang;
import com.zeidex.eldalel.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.register_go_enter_text)
    AppCompatTextView register_go_enter_text;

    @BindView(R.id.register_first_name_edittext)
    AppCompatEditText register_first_name_edittext;

    @BindView(R.id.register_last_name_edittext)
    AppCompatEditText register_last_name_edittext;

    @BindView(R.id.register_mail_edittext)
    AppCompatEditText register_mail_edittext;

    @BindView(R.id.register_pass_edittext)
    AppCompatEditText register_pass_edittext;

    @BindView(R.id.register_pass_confirm_edittext)
    AppCompatEditText register_pass_confirm_edittext;

    @BindView(R.id.register_responsible_edittext)
    AppCompatEditText register_responsible_edittext;

    @BindView(R.id.register_mobile_edittext)
    AppCompatEditText register_mobile_edittext;

    @BindView(R.id.register_address_edittext)
    AppCompatEditText register_address_edittext;

    @BindView(R.id.register_name_company_edittext)
    AppCompatEditText register_name_company_edittext;

    @BindView(R.id.register_radio_group)
    RadioGroup register_radio_group;

    @BindView(R.id.register_radio_group2)
    RadioGroup register_radio_group2;

    @BindView(R.id.radio_company_register)
    RadioButton radio_company_register;

    @BindView(R.id.radio_indivdual_register)
    RadioButton radio_indivdual_register;

    @BindView(R.id.radio_ar_register)
    RadioButton radio_ar_register;

    @BindView(R.id.radio_en_register)
    RadioButton radio_en_register;

    @BindView(R.id.spinner_register_country)
    MaterialSpinner spinner_register_country;

    @BindView(R.id.spinner_register_region)
    MaterialSpinner spinner_register_region;

    @BindView(R.id.spinner_register_city)
    MaterialSpinner spinner_register_city;

    @BindView(R.id.register_responsible_linear)
    LinearLayoutCompat register_responsible_linear;

    @BindView(R.id.linear_register_country)
    LinearLayoutCompat linear_register_country;

    @BindView(R.id.linear_register_region)
    LinearLayoutCompat linear_register_region;

    @BindView(R.id.linear_register_city)
    LinearLayoutCompat linear_register_city;

    @BindView(R.id.register_mobile_linear)
    LinearLayoutCompat register_mobile_linear;

    @BindView(R.id.register_address_linear)
    LinearLayoutCompat register_address_linear;

    @BindView(R.id.register_name_company_linear)
    LinearLayoutCompat register_name_company_linear;

    @BindView(R.id.register_first_name_linear)
    LinearLayoutCompat register_first_name_linear;

    @BindView(R.id.register_last_name_linear)
    LinearLayoutCompat register_last_name_linear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        findViews();
    }

    public void findViews() {
        register_go_enter_text.setOnClickListener(this);
        showDialog();
        getCountries();

        countries = new ArrayList<>();
        ids_countries = new ArrayList<>();
        countries.add(getString(R.string.country_spinner_label));
        ids_countries.add(-1);
        spinner_register_country.setItems(countries);

        regions = new ArrayList<>();
        ids_regions = new ArrayList<>();
        regions.add(getString(R.string.regions_spinner_label));
        ids_regions.add(-1);
        spinner_register_region.setItems(regions);

        cities = new ArrayList<>();
        ids_cities = new ArrayList<>();
        cities.add(getString(R.string.spinner_city_label));
        ids_cities.add(-1);
        spinner_register_city.setItems(cities);


        register_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_company_register:
                        // do operations specific to this selection
                        type_radio = "company";
                        register_responsible_linear.setVisibility(View.VISIBLE);
                        linear_register_country.setVisibility(View.VISIBLE);
                        linear_register_region.setVisibility(View.VISIBLE);
                        linear_register_city.setVisibility(View.VISIBLE);
                        register_mobile_linear.setVisibility(View.VISIBLE);
                        register_address_linear.setVisibility(View.VISIBLE);
                        register_name_company_linear.setVisibility(View.VISIBLE);
                        register_first_name_linear.setVisibility(View.GONE);
                        register_last_name_linear.setVisibility(View.GONE);
                        break;
                    case R.id.radio_indivdual_register:
                        // do operations specific to this selection
                        type_radio = "individual";
                        register_responsible_linear.setVisibility(View.GONE);
                        linear_register_country.setVisibility(View.GONE);
                        linear_register_region.setVisibility(View.GONE);
                        linear_register_city.setVisibility(View.GONE);
                        register_mobile_linear.setVisibility(View.GONE);
                        register_address_linear.setVisibility(View.GONE);
                        register_name_company_linear.setVisibility(View.GONE);
                        register_first_name_linear.setVisibility(View.VISIBLE);
                        register_last_name_linear.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        register_radio_group2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_ar_register:
                        // do operations specific to this selection
                        lang = "arabic";
                        break;
                    case R.id.radio_en_register:
                        // do operations specific to this selection
                        lang = "english";
                        break;
                }
            }
        });
    }

    Map<String, String> register_post;

    private void convertDaraToJson() {
        register_post = new HashMap<>();
        String name = register_first_name_edittext.getText().toString();
        String last_name = register_last_name_edittext.getText().toString();
        String email = register_mail_edittext.getText().toString();
        String pass = register_pass_edittext.getText().toString();
        String confirm_pass = register_pass_confirm_edittext.getText().toString();
        register_post.put("firstName", name);
        register_post.put("lastName", last_name);
        register_post.put("email", email);
        register_post.put("password", pass);
        register_post.put("password_confirmation", confirm_pass);
        register_post.put("language", lang);
    }

    private void convertDataCompanyToJson() {
        register_post = new HashMap<>();
        String name = register_name_company_edittext.getText().toString();
        String email = register_mail_edittext.getText().toString();
        String pass = register_pass_edittext.getText().toString();
        String confirm_pass = register_pass_confirm_edittext.getText().toString();
        String responsible = register_responsible_edittext.getText().toString();
        String mobile = register_mobile_edittext.getText().toString();
        String address = register_address_edittext.getText().toString();
        register_post.put("name", name);
        register_post.put("email", email);
        register_post.put("password", pass);
        register_post.put("password_confirmation", confirm_pass);
        register_post.put("language", lang);
        register_post.put("responsible", responsible);
        register_post.put("mobile", mobile);
        register_post.put("country_id", String.valueOf(id_country));
        register_post.put("subsidiary_id", String.valueOf(id_region));
        register_post.put("city_id", String.valueOf(id_city));
        register_post.put("address", address);
    }

    String type_radio = "";
    String lang = "";

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.register_go_enter_text: {
                checkRegistration();
            }
        }
    }

    String errors;

    public void checkRegistration() {
        if (!register_pass_edittext.getText().toString().equals(register_pass_confirm_edittext.getText().toString())) {
            Toasty.error(this, getString(R.string.error_confirm_message), Toast.LENGTH_LONG).show();
            return;
        }

        RegisterApi registerApi = null;
        Call<GetRegisterResponse> getRegisterResponseCall = null;
        if (type_radio.equals("individual")) {
            final boolean fieldsOK = validate(new EditText[]{register_first_name_edittext, register_last_name_edittext, register_pass_edittext, register_pass_confirm_edittext, register_mail_edittext});
            if (fieldsOK && !type_radio.equalsIgnoreCase("") && !lang.equalsIgnoreCase("")) {
                convertDaraToJson();
                reloadDialog.show();
                registerApi = APIClient.getClient(SERVER_API_TEST).create(RegisterApi.class);
                getRegisterResponseCall = registerApi.getRegister(register_post);
                getRegisterResponseCall.enqueue(new Callback<GetRegisterResponse>() {
                    @Override
                    public void onResponse(Call<GetRegisterResponse> call, Response<GetRegisterResponse> response) {
                        GetRegisterResponse getRegisterResponse = response.body();
                        String status = getRegisterResponse.getSuccess();
                        if (status.equals("true")) {
                            if (lang.equals("arabic")) {
                                ChangeLang.setNewLocale(RegisterActivity.this, "ar");
                            } else {
                                ChangeLang.setNewLocale(RegisterActivity.this, "en");
                            }
                            PreferenceUtils.saveUserToken(RegisterActivity.this, getRegisterResponse.getTokenUser()
                            );
//                            PreferenceUtils.saveUserLogin(RegisterActivity.this, true);
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            Animatoo.animateSwipeLeft(RegisterActivity.this);
                        } else if (status.equals("false")) {
                            errors = "";
                            for (int i = 0; i < getRegisterResponse.getError().size(); i++) {
                                errors = errors + getRegisterResponse.getError().get(i) + " , ";
                            }
                            Toasty.error(RegisterActivity.this, errors, Toast.LENGTH_LONG).show();
                        }
                        reloadDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<GetRegisterResponse> call, Throwable t) {
                        Toasty.error(RegisterActivity.this, getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                        reloadDialog.dismiss();
                    }
                });
            }
        } else if (type_radio.equals("company")) {
            if (id_country == -1) {
                Toasty.error(RegisterActivity.this, getString(R.string.choose_country), Toast.LENGTH_LONG).show();
                return;
            }

            if (id_region == -1) {
                Toasty.error(RegisterActivity.this, getString(R.string.choose_region), Toast.LENGTH_LONG).show();
                return;
            }

            if (id_city == -1) {
                Toasty.error(RegisterActivity.this, getString(R.string.choose_city), Toast.LENGTH_LONG).show();
                return;
            }

            final boolean fieldscomOK = validate(new EditText[]{register_responsible_edittext, register_mobile_edittext, register_address_edittext, register_name_company_edittext});

            if (fieldscomOK && id_country != -1 && id_region != -1 && id_city != -1) {
                reloadDialog.show();
                convertDataCompanyToJson();
                RegisterCompanyApi registerCompanyApi = null;
                Call<GetRegisterCompanyResponse> getRegisteCompanyrResponseCall = null;
                registerCompanyApi = APIClient.getClient(SERVER_API_TEST).create(RegisterCompanyApi.class);
                getRegisteCompanyrResponseCall = registerCompanyApi.getRegister(register_post);
                getRegisteCompanyrResponseCall.enqueue(new Callback<GetRegisterCompanyResponse>() {
                    @Override
                    public void onResponse(Call<GetRegisterCompanyResponse> call, Response<GetRegisterCompanyResponse> response) {
                        GetRegisterCompanyResponse getRegisterResponse = response.body();
                        String status = getRegisterResponse.getSuccess();
                        if (status != null) {
                            if (status.equals("true")) {
                                if (lang.equals("arabic")) {
                                    ChangeLang.setNewLocale(RegisterActivity.this, "ar");
                                } else {
                                    ChangeLang.setNewLocale(RegisterActivity.this, "en");
                                }
//                                PreferenceUtils.saveUserLogin(RegisterActivity.this, true);
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                Animatoo.animateSwipeLeft(RegisterActivity.this);
                            }
                        } else {
                            Toasty.error(RegisterActivity.this, getRegisterResponse.getError().toString(), Toast.LENGTH_LONG).show();
                        }
                        reloadDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<GetRegisterCompanyResponse> call, Throwable t) {
                        Toasty.error(RegisterActivity.this, getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                        reloadDialog.dismiss();
                    }
                });
            }

        }
        if (type_radio.equalsIgnoreCase("")) {
            Toasty.error(this, getString(R.string.choose_type), Toast.LENGTH_LONG).show();
            return;
        }

        if (lang.equalsIgnoreCase("")) {
            Toasty.error(this, getString(R.string.choose_lang), Toast.LENGTH_LONG).show();
            return;
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
                    spinner_register_country.setItems(countries);
                    spinner_register_country.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
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
                Toasty.error(RegisterActivity.this, getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
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
                    spinner_register_region.setItems(regions);
                    spinner_register_region.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
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
                Toasty.error(RegisterActivity.this, getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
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
                spinner_register_city.setItems(cities);
                spinner_register_city.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                        id_city = ids_cities.get(position);
                    }
                });

                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetCities> call, Throwable t) {
                Toasty.error(RegisterActivity.this, getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
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
        reloadDialog = new Dialog(this);
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
