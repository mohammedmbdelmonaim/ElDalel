package com.zeidex.eldalel;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.zeidex.eldalel.response.GetCities;
import com.zeidex.eldalel.response.GetCountries;
import com.zeidex.eldalel.response.GetProfileInfo;
import com.zeidex.eldalel.response.GetRegions;
import com.zeidex.eldalel.response.GetUpdatePasswordResponse;
import com.zeidex.eldalel.response.GetUpdateProfileResponse;
import com.zeidex.eldalel.services.CitiesApi;
import com.zeidex.eldalel.services.CountriesApi;
import com.zeidex.eldalel.services.ProfileInfoApi;
import com.zeidex.eldalel.services.RegionsApi;
import com.zeidex.eldalel.services.UpdatePasswordApi;
import com.zeidex.eldalel.services.UpdateProfileApi;
import com.zeidex.eldalel.utils.APIClient;
import com.zeidex.eldalel.utils.Animatoo;
import com.zeidex.eldalel.utils.ChangeLang;
import com.zeidex.eldalel.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class ProfileActivity extends BaseActivity {
    @BindView(R.id.spinner_activity_profile_country)
    MaterialSpinner spinner_activity_profile_country;

    @BindView(R.id.spinner_activity_profile_region)
    MaterialSpinner spinner_activity_profile_region;

    @BindView(R.id.spinner_activity_profile_city)
    MaterialSpinner spinner_activity_profile_city;

    @BindView(R.id.activity_profile_language_linear)
    LinearLayoutCompat activity_profile_language_linear;

    @BindView(R.id.activity_profile_name_edittext)
    AppCompatEditText activity_profile_name_edittext;

    @BindView(R.id.activity_profile_last_name_edittext)
    AppCompatEditText activity_profile_last_name_edittext;

    @BindView(R.id.activity_profile_email_edittext)
    AppCompatEditText activity_profile_email_edittext;

    @BindView(R.id.activity_profile_address_edittext)
    AppCompatEditText activity_profile_address_edittext;

    @BindView(R.id.activity_profile_phone_edittext)
    AppCompatEditText activity_profile_phone_edittext;

    @BindView(R.id.activity_profile_name_company_edittext)
    AppCompatEditText activity_profile_name_company_edittext;

    @BindView(R.id.activity_profile_responsible_edittext)
    AppCompatEditText activity_profile_responsible_edittext;

    @BindView(R.id.activity_profile_update_text)
    AppCompatTextView activity_profile_update_text;

    @BindView(R.id.change_password_profile)
    AppCompatTextView change_password_profile;

    @BindView(R.id.activity_profile_language_label)
    AppCompatTextView activity_profile_language_label;

    @BindView(R.id.activity_profile_name_label)
    LinearLayoutCompat activity_profile_name_label;

    @BindView(R.id.activity_profile_name_company_label)
    LinearLayoutCompat activity_profile_name_company_label;

    @BindView(R.id.activity_profile_responsible_label)
    LinearLayoutCompat activity_profile_responsible_label;

    @BindView(R.id.activity_profile_last_name_label)
    LinearLayoutCompat activity_profile_last_name_label;

    String token;
    String lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        countries = new ArrayList<>();
        ids_countries = new ArrayList<>();
        countries.add(getString(R.string.country_spinner_label_profile));
        ids_countries.add(-1);
        spinner_activity_profile_country.setItems(countries);

        regions = new ArrayList<>();
        ids_regions = new ArrayList<>();
        regions.add(getString(R.string.region_spinner_label_profile));
        ids_regions.add(-1);
        spinner_activity_profile_region.setItems(regions);

        cities = new ArrayList<>();
        ids_cities = new ArrayList<>();
        cities.add(getString(R.string.cities_spinner_label_profile));
        ids_cities.add(-1);
        spinner_activity_profile_city.setItems(cities);

        showDialog();


        if (PreferenceUtils.getCompanyLogin(ProfileActivity.this)) {
            token = PreferenceUtils.getCompanyToken(ProfileActivity.this);
            activity_profile_last_name_label.setVisibility(View.GONE);
            activity_profile_name_label.setVisibility(View.GONE);

        } else if (PreferenceUtils.getUserLogin(ProfileActivity.this)) {
            token = PreferenceUtils.getUserToken(ProfileActivity.this);
            activity_profile_name_company_label.setVisibility(View.GONE);
            activity_profile_responsible_label.setVisibility(View.GONE);
        }


        Locale locale = ChangeLang.getLocale(getResources());
        String loo = locale.getLanguage();
        if (loo.equalsIgnoreCase("ar")) {
            lang = "arabic";
            old_lang = "arabic";

            activity_profile_language_label.setText(getString(R.string.radio_ar_register));
        } else if (loo.equalsIgnoreCase("en")) {
            lang = "english";
            old_lang = "english";
            activity_profile_language_label.setText(getString(R.string.radio_en_register));
        }
        onLoadProfile();
    }
    String old_lang;
    private void onLoadProfile() {
        reloadDialog.show();
        ProfileInfoApi profileInfoApi = APIClient.getClient(SERVER_API_TEST).create(ProfileInfoApi.class);
        Call<GetProfileInfo> getProfileInfoCall ;
        if (PreferenceUtils.getCompanyLogin(this)){
            getProfileInfoCall = profileInfoApi.getProfileInfocompany(token);
        }else {
            getProfileInfoCall = profileInfoApi.getProfileInfo(token);
        }

        getProfileInfoCall.enqueue(new Callback<GetProfileInfo>() {
            @Override
            public void onResponse(Call<GetProfileInfo> call, Response<GetProfileInfo> response) {
                GetProfileInfo getProfileInfo = response.body();
                if (getProfileInfo.getCityId() != null)
                id_city = getProfileInfo.getCityId();
                if (getProfileInfo.getSubsidiaryId() != null)
                id_region = getProfileInfo.getSubsidiaryId();
                if (getProfileInfo.getCountryId() != null)
                id_country = getProfileInfo.getCountryId();

                if (getProfileInfo.getFirstName() !=  null)
                activity_profile_name_edittext.setText(getProfileInfo.getFirstName());

                if (getProfileInfo.getLastName() !=  null)
                activity_profile_last_name_edittext.setText(getProfileInfo.getLastName());

                activity_profile_email_edittext.setText(getProfileInfo.getEmail());
                activity_profile_phone_edittext.setText(getProfileInfo.getMobile());

                if (getProfileInfo.getAddressHome() != null)
                activity_profile_address_edittext.setText(getProfileInfo.getAddressHome());

                if (getProfileInfo.getAddress() != null)
                    activity_profile_address_edittext.setText(getProfileInfo.getAddress());

                if (getProfileInfo.getName() != null)
                    activity_profile_name_company_edittext.setText(getProfileInfo.getName());

                if (getProfileInfo.getResponsible() != null)
                    activity_profile_responsible_edittext.setText(getProfileInfo.getResponsible());

                getCountries();
            }

            @Override
            public void onFailure(Call<GetProfileInfo> call, Throwable t) {
                Toasty.error(ProfileActivity.this, getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    @OnClick(R.id.activity_profile_update_text)
    public void updateProfile() {
         if (id_country == -1) {
            Toasty.error(ProfileActivity.this, getString(R.string.choose_country), Toast.LENGTH_LONG).show();
            return;
        }

        if (id_region == -1) {
            Toasty.error(ProfileActivity.this, getString(R.string.choose_region), Toast.LENGTH_LONG).show();
            return;
        }

        if (id_city == -1) {
            Toasty.error(ProfileActivity.this, getString(R.string.choose_city), Toast.LENGTH_LONG).show();
            return;
        }
        final boolean fieldsOK;
        if (PreferenceUtils.getCompanyLogin(this)) {
             fieldsOK = validate(new EditText[]{activity_profile_address_edittext, activity_profile_name_company_edittext, activity_profile_responsible_edittext, activity_profile_email_edittext, activity_profile_phone_edittext});
        }else{
             fieldsOK = validate(new EditText[]{activity_profile_address_edittext, activity_profile_name_edittext, activity_profile_last_name_edittext, activity_profile_email_edittext, activity_profile_phone_edittext});

        }
        if (fieldsOK) {
            reloadDialog.show();
            UpdateProfileApi updateProfileApi = APIClient.getClient(SERVER_API_TEST).create(UpdateProfileApi.class);
            Call<GetUpdateProfileResponse> getUpdateProfileResponseCall;
            if (PreferenceUtils.getCompanyLogin(this)){
                getUpdateProfileResponseCall = updateProfileApi.updateProfilecompany(token, lang, activity_profile_name_company_edittext.getText().toString(), activity_profile_responsible_edittext.getText().toString(), id_country, id_city, id_region, activity_profile_address_edittext.getText().toString(), activity_profile_phone_edittext.getText().toString(), activity_profile_email_edittext.getText().toString());
            }else {
                getUpdateProfileResponseCall = updateProfileApi.updateProfile(token, lang, activity_profile_name_edittext.getText().toString(), activity_profile_last_name_edittext.getText().toString(), id_country, id_city, id_region, activity_profile_address_edittext.getText().toString(), activity_profile_phone_edittext.getText().toString(), activity_profile_email_edittext.getText().toString());
            }
            getUpdateProfileResponseCall.enqueue(new Callback<GetUpdateProfileResponse>() {
                @Override
                public void onResponse(Call<GetUpdateProfileResponse> call, Response<GetUpdateProfileResponse> response) {
                    GetUpdateProfileResponse getUpdateProfileResponse = response.body();
                    if (getUpdateProfileResponse.getSuccess() != null) {
                        if (getUpdateProfileResponse.getSuccess()) {
                            if (!old_lang.equalsIgnoreCase(lang)) {
                                if (lang.equalsIgnoreCase("english")) {
                                    ChangeLang.setNewLocale(ProfileActivity.this, "en");
                                } else {
                                    ChangeLang.setNewLocale(ProfileActivity.this, "ar");
                                }
                            } else {

                            }
                            Toasty.success(ProfileActivity.this, getString(R.string.update_profile_success_msg), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                            Animatoo.animateSwipeLeft(ProfileActivity.this);
                        }
                    }
                    if (getUpdateProfileResponse.getStatus() != null) {
                        if (!getUpdateProfileResponse.getStatus()) {
                            StringBuilder errors = new StringBuilder();
                            for (int i = 0; i < getUpdateProfileResponse.getData().getError().size(); i++) {
                                errors.append(getUpdateProfileResponse.getData().getError().get(i) + " , ");
                            }
                            Toasty.error(ProfileActivity.this, errors, Toast.LENGTH_LONG).show();
                        } else {
                            if (!old_lang.equalsIgnoreCase(lang)) {
                                if (lang.equalsIgnoreCase("english")) {
                                    ChangeLang.setNewLocale(ProfileActivity.this, "en");
                                } else {
                                    ChangeLang.setNewLocale(ProfileActivity.this, "ar");
                                }
                            } else {

                            }
                            Toasty.success(ProfileActivity.this, getString(R.string.update_profile_success_msg), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                            Animatoo.animateSwipeLeft(ProfileActivity.this);
                        }
                    }
                        reloadDialog.dismiss();

                }

                @Override
                public void onFailure(Call<GetUpdateProfileResponse> call, Throwable t) {
                    Toasty.error(ProfileActivity.this, getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                    reloadDialog.dismiss();
                }
            });
        }
    }


    Dialog changePass;

    @OnClick(R.id.change_password_profile)
    public void changePassword() {
        changePass = new Dialog(this);
        changePass.requestWindowFeature(Window.FEATURE_NO_TITLE);
        changePass.setContentView(R.layout.dialog_change_password);
        changePass.setCancelable(true);
        AppCompatEditText dialog_change_pass_old_edittext = changePass.findViewById(R.id.dialog_change_pass_old_edittext);
        AppCompatEditText dialog_change_pass_new_edittext = changePass.findViewById(R.id.dialog_change_pass_new_edittext);
        AppCompatEditText dialog_change_pass_new_confirm_edittext = changePass.findViewById(R.id.dialog_change_pass_new_confirm_edittext);
        AppCompatButton dialog_change_pass_update_btn = changePass.findViewById(R.id.dialog_change_pass_update_btn);
        AppCompatButton dialog_change_pass_cancel_btn = changePass.findViewById(R.id.dialog_change_pass_cancel_btn);
        dialog_change_pass_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePass.dismiss();
            }
        });

        dialog_change_pass_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dialog_change_pass_new_edittext.getText().toString().equals(dialog_change_pass_new_confirm_edittext.getText().toString())) {
                    Toasty.error(ProfileActivity.this, getString(R.string.error_confirm_message), Toast.LENGTH_LONG).show();
                    return;
                }
                final boolean fieldsOK = validate(new EditText[]{dialog_change_pass_old_edittext, dialog_change_pass_new_edittext, dialog_change_pass_new_confirm_edittext});
                if (fieldsOK) {
                    UpdatePasswordApi updatePasswordApi = APIClient.getClient(SERVER_API_TEST).create(UpdatePasswordApi.class);
                    Call<GetUpdatePasswordResponse> getUpdatePasswordResponseCall;
                    if (PreferenceUtils.getCompanyLogin(ProfileActivity.this)){
                        getUpdatePasswordResponseCall = updatePasswordApi.editPasswordsApicompany(token, lang, dialog_change_pass_old_edittext.getText().toString(), dialog_change_pass_new_edittext.getText().toString(), dialog_change_pass_new_confirm_edittext.getText().toString(), PreferenceUtils.getEmail(ProfileActivity.this));
                    }else {
                        getUpdatePasswordResponseCall = updatePasswordApi.editPasswordsApi(token, lang, dialog_change_pass_old_edittext.getText().toString(), dialog_change_pass_new_edittext.getText().toString(), dialog_change_pass_new_confirm_edittext.getText().toString(), PreferenceUtils.getEmail(ProfileActivity.this));
                    }
                    getUpdatePasswordResponseCall.enqueue(new Callback<GetUpdatePasswordResponse>() {
                        @Override
                        public void onResponse(Call<GetUpdatePasswordResponse> call, Response<GetUpdatePasswordResponse> response) {
                            GetUpdatePasswordResponse getUpdatePasswordResponse = response.body();
                            if (getUpdatePasswordResponse.getStatus().equalsIgnoreCase("false")) {
                                Toasty.error(ProfileActivity.this, getUpdatePasswordResponse.getData().getErrorString(), Toast.LENGTH_LONG).show();
                            } else {
                                Toasty.success(ProfileActivity.this, getString(R.string.password_update_success), Toast.LENGTH_LONG).show();
                                changePass.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<GetUpdatePasswordResponse> call, Throwable t) {
                            Toasty.error(ProfileActivity.this, getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });

        changePass.show();
    }


    @OnClick(R.id.item_activity_profile_back)
    public void onBack() {
        onBackPressed();
    }

    Dialog changeLanguage;

    @OnClick(R.id.activity_profile_language_linear)
    public void chooseLanguage() {
        changeLanguage = new Dialog(this);
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
                    activity_profile_language_label.setText(getString(R.string.radio_ar_register));
                } else {
                    lang = "english";
                    activity_profile_language_label.setText(getString(R.string.radio_en_register));
                }
                changeLanguage.dismiss();
            }
        });

        dialog_text_deactive_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog_text_deactive_lang.getText().toString().equalsIgnoreCase(getString(R.string.radio_ar_register))) {
                    lang = "arabic";
                    activity_profile_language_label.setText(getString(R.string.radio_ar_register));
                } else {
                    lang = "english";
                    activity_profile_language_label.setText(getString(R.string.radio_en_register));
                }
                changeLanguage.dismiss();
            }
        });
        changeLanguage.show();
    }


    ArrayList<String> countries;
    ArrayList<Integer> ids_countries;
    int id_country = -1;

    public void getCountries() {
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

                    if (ids_countries.contains(id_country)) {
                        int index = ids_countries.indexOf(id_country);
                        Collections.swap(ids_countries, 0, index);
                        Collections.swap(countries, 0, index);
                        getRegions(id_country);
                    }
                    spinner_activity_profile_country.setItems(countries);
                    spinner_activity_profile_country.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
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
                Toasty.error(ProfileActivity.this, getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
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
                    if (ids_regions.contains(id_region)) {
                        int index = ids_regions.indexOf(id_region);
                        Collections.swap(ids_regions, 0, index);
                        Collections.swap(regions, 0, index);
                        getCities(id_region);
                    }

                    spinner_activity_profile_region.setItems(regions);
                    spinner_activity_profile_region.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
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
                Toasty.error(ProfileActivity.this, getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
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
                if (ids_cities.contains(id_city)) {
                    int index = ids_cities.indexOf(id_city);
                    Collections.swap(ids_cities, 0, index);
                    Collections.swap(cities, 0, index);
                }

                spinner_activity_profile_city.setItems(cities);
                spinner_activity_profile_city.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                        id_city = ids_cities.get(position);
                    }
                });

                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetCities> call, Throwable t) {
                Toasty.error(ProfileActivity.this, getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
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
        reloadDialog = new Dialog(ProfileActivity.this);
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
