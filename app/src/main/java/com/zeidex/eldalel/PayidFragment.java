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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.zeidex.eldalel.response.GetActivatePostpaidResponse;
import com.zeidex.eldalel.response.GetBookingResponse;
import com.zeidex.eldalel.response.GetBranches;
import com.zeidex.eldalel.response.GetCountries;
import com.zeidex.eldalel.response.GetDeliveryFee;
import com.zeidex.eldalel.response.GetMakeOrderResponse;
import com.zeidex.eldalel.response.GetPostPaidResponse;
import com.zeidex.eldalel.response.GetProfileInfo;
import com.zeidex.eldalel.response.GetRegions;
import com.zeidex.eldalel.response.GetWalletResponse;
import com.zeidex.eldalel.services.BookingResponse;
import com.zeidex.eldalel.services.BranchesApi;
import com.zeidex.eldalel.services.CountriesApi;
import com.zeidex.eldalel.services.DeliveryFeeApi;
import com.zeidex.eldalel.services.MakeOrderApi;
import com.zeidex.eldalel.services.ProfileInfoApi;
import com.zeidex.eldalel.services.RegionsApi;
import com.zeidex.eldalel.utils.APIClient;
import com.zeidex.eldalel.utils.ChangeLang;
import com.zeidex.eldalel.utils.PreferenceUtils;
import com.zeidex.eldalel.utils.PriceFormatter;

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

    @BindView(R.id.fragment_payid_linear_methods)
    LinearLayoutCompat fragment_payid_linear_methods;

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

    @BindView(R.id.payment_webview)
    WebView payment_webview;

    @BindView(R.id.post_paid_payment)
    LinearLayoutCompat postPaidPaymentLayout;

    @BindView(R.id.post_paid_payment_txt)
    AppCompatTextView post_paid_payment_txt;

    @BindView(R.id.post_paid_payment_img)
    AppCompatImageView post_paid_payment_img;

    @BindView(R.id.post_paid_payment_imgcheck)
    AppCompatImageView post_paid_payment_imgcheck;

    @BindView(R.id.activation_request_tv)
    TextView activationRequestTv;

    @BindView(R.id.my_wallet_payment)
    LinearLayoutCompat myWalletPayment;

    @BindView(R.id.my_wallet_payment_txt)
    AppCompatTextView my_wallet_payment_txt;

    @BindView(R.id.my_wallet_amount_txt)
    AppCompatTextView my_wallet_amount_txt;

    @BindView(R.id.my_wallet_payment_img)
    AppCompatImageView my_wallet_payment_img;

    @BindView(R.id.my_wallet_payment_imgcheck)
    AppCompatImageView my_wallet_payment_imgcheck;

    @BindView(R.id.fragment_payid_coupon_linear)
    LinearLayoutCompat fragment_payid_coupon_linear;

    @BindView(R.id.fragment_payid_coupon_view)
    View fragment_payid_coupon_view;

    @BindView(R.id.fragment_payid_coupon_text)
    AppCompatTextView fragment_payid_coupon_text;

    @BindView(R.id.fragment_payid_delivery_linear)
    LinearLayoutCompat fragment_payid_delivery_linear;

    @BindView(R.id.fragment_payid_delivery_view)
    View fragment_payid_delivery_view;

    @BindView(R.id.fragment_payid_delivery_text)
    AppCompatTextView fragment_payid_delivery_text;


    @BindView(R.id.payment_contain)
    ConstraintLayout payment_contain;

    int shipment_type = 1;
    int shipment_method = 0;
    int address_id = ShoopingListAddressesFragment.address_id;
    String total_price = BasketFragment.totalString;
    String total_products = BasketFragment.totalwithoutString;
    double finalPriceDouble;
    String couponDiscount;
    String tax = BasketFragment.taxString;
    private double mWalletAmount;
    private boolean isPostpaidActive;
    int deliveryFee = -1;


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
        getDeliveryFee();
        if (!PreferenceUtils.getCoupoun(getContext()).equals("")) {
            finalPriceDouble = BasketFragment.finalPrice;
//            total_price = BasketFragment.totalWithDiscountString;
            couponDiscount = BasketFragment.couponDiscountString;
            fragment_payid_coupon_linear.setVisibility(View.VISIBLE);
            fragment_payid_coupon_view.setVisibility(View.VISIBLE);
            fragment_payid_coupon_text.setText(couponDiscount);
        } else {
            finalPriceDouble = BasketFragment.mTotal_price;
        }

        if (PreferenceUtils.getCompanyLogin(getContext())) {
            fragment_payid_linear_methods.setVisibility(View.GONE);
            fragment_payid_total_products_text.setText(total_products);
            fragment_payid_total_price_text.setText(PriceFormatter.toDecimalRsString(finalPriceDouble, getContext()));
            fragment_payid_tax_text.setText(tax);
            fragment_payid_paying.setOnClickListener(this);
            postPaidPaymentLayout.setVisibility(View.VISIBLE);
            getPostPaidStatus();
        } else {
            credit_card_payment.setVisibility(View.VISIBLE);
            getWalletStatus();
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
            fragment_payid_total_price_text.setText(PriceFormatter.toDecimalRsString(finalPriceDouble, getContext()));
            fragment_payid_tax_text.setText(tax);
            getCountries();

            fragment_payid_paying.setOnClickListener(this);
            shipment_to_address.setSelected(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                shipment_from_branch_img.setImageDrawable(getContext().getDrawable(R.drawable.ic_company_unchecked));
                shipment_to_address_img.setImageDrawable(getContext().getDrawable(R.drawable.ic_shipped_car_checked));

            } else {
                shipment_to_address_img.setImageResource(R.drawable.ic_shipped_car_checked);
                shipment_from_branch_img.setImageResource(R.drawable.ic_company_unchecked);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                shipment_to_address_txt.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
                shipment_from_branch_txt.setTextColor(getContext().getColor(R.color.colorshipmenttype));
            } else {
                shipment_to_address_txt.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
                shipment_from_branch_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
            }
        }
    }

    private void getDeliveryFee() {
        DeliveryFeeApi deliveryFeeApi = APIClient.getClient(SERVER_API_TEST).create(DeliveryFeeApi.class);
        deliveryFeeApi.getDeliveryFee().enqueue(new Callback<GetDeliveryFee>() {
            @Override
            public void onResponse(Call<GetDeliveryFee> call, Response<GetDeliveryFee> response) {
                if (response.body() != null) {
                    deliveryFee = response.body().getData();
                    fragment_payid_delivery_text.setText(PriceFormatter.toDecimalRsString(deliveryFee, getContext()));
                }
            }

            @Override
            public void onFailure(Call<GetDeliveryFee> call, Throwable t) {

            }
        });
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
        if (!PreferenceUtils.getCoupoun(getContext()).equalsIgnoreCase("")) {
            payment_post.put("coupon", PreferenceUtils.getCoupoun(getContext()));
        }
    }

    private void convertDaraToJsonCompany() {
        payment_post = new HashMap<>();
        payment_post.put("payment_type", String.valueOf(shipment_method));
        payment_post.put("language", lang);
        payment_post.put("token", token);
        if (!PreferenceUtils.getCoupoun(getContext()).equalsIgnoreCase("")) {
            payment_post.put("coupon", PreferenceUtils.getCoupoun(getContext()));
        }
    }

    Map<String, String> payment_post_response;
    String booking_id;

    private void convertDaraToJsonpayment() {
        payment_post_response = new HashMap<>();
        payment_post_response.put("token", token);
        payment_post_response.put("p_id", booking_id);
    }

    String lang;
    String token;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fragment_payid_paying: {
                if (shipment_method == 0) {
                    Toasty.error(getContext(), getString(R.string.choose_payment_method), Toast.LENGTH_LONG).show();
                    return;
                }
                Locale locale = ChangeLang.getLocale(getContext().getResources());
                String loo = locale.getLanguage();
                if (loo.equalsIgnoreCase("ar")) {
                    lang = "arabic";
                } else if (loo.equalsIgnoreCase("en")) {
                    lang = "english";
                }
                if (PreferenceUtils.getCompanyLogin(getContext())) {
                    token = PreferenceUtils.getCompanyToken(getContext());
                    convertDaraToJsonCompany();
                } else if (PreferenceUtils.getUserLogin(getContext())) {
                    token = PreferenceUtils.getUserToken(getContext());
                    if (shipment_type == 2) {
                        if (id_country == -1) {
                            Toasty.error(getContext(), getString(R.string.choose_country), Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (id_region == -1) {
                            Toasty.error(getContext(), getString(R.string.choose_region), Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (id_branch == -1) {
                            Toasty.error(getContext(), getString(R.string.choose_branch), Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    convertDaraToJson();
                }
                reloadDialog.show();
                MakeOrderApi makeOrderApi = APIClient.getClient(SERVER_API_TEST).create(MakeOrderApi.class);
                Call<GetMakeOrderResponse> getMakeOrderResponseCall;
                if (PreferenceUtils.getCompanyLogin(getContext())) {
                    getMakeOrderResponseCall = makeOrderApi.makeOrderResponsecompany(payment_post);
                } else {
                    getMakeOrderResponseCall = makeOrderApi.makeOrderResponse(payment_post);
                }

                getMakeOrderResponseCall.enqueue(new Callback<GetMakeOrderResponse>() {
                    @Override
                    public void onResponse(Call<GetMakeOrderResponse> call, Response<GetMakeOrderResponse> response) {
                        GetMakeOrderResponse getMakeOrderResponse = response.body();

                        if (getMakeOrderResponse.isSuccess()) {
                            if (shipment_method == 1) {
                                payment_webview.setVisibility(View.VISIBLE);
                                payment_contain.setVisibility(View.GONE);
                                booking_id = getMakeOrderResponse.getP_id();
                                WebSettings webSetting = payment_webview.getSettings();
                                webSetting.setBuiltInZoomControls(true);
                                payment_webview.setWebViewClient(new WebViewClient() {
                                    @Override
                                    public void onPageFinished(WebView view, String url) {
                                        reloadDialog.dismiss();
                                        if (url.contains("payment-page")) {
                                            convertDaraToJsonpayment();
                                            BookingResponse bookingResponse1 = APIClient.getClient(SERVER_API_TEST).create(BookingResponse.class);
                                            Call<GetBookingResponse> getBookingResponseCall;
                                            if (PreferenceUtils.getCompanyLogin(getContext())) {
                                                getBookingResponseCall = bookingResponse1.getBookingCompany(payment_post_response);
                                            } else {
                                                getBookingResponseCall = bookingResponse1.getBooking(payment_post_response);
                                            }

                                            getBookingResponseCall.enqueue(new Callback<GetBookingResponse>() {
                                                @Override
                                                public void onResponse(Call<GetBookingResponse> call, Response<GetBookingResponse> response) {
                                                    GetBookingResponse getBookingResponse = response.body();
                                                    if (getBookingResponse.isSuccess()) {
                                                        payment_webview.setVisibility(View.GONE);
                                                        payment_contain.setVisibility(View.VISIBLE);
                                                        ((PaymentActivity) getContext()).goToOrderFragment();
                                                        PreferenceUtils.saveCoupon(getContext(), "");
                                                        PreferenceUtils.saveCouponAmountType(getContext(), "");
                                                        PreferenceUtils.saveCouponAmount(getContext(), -1);
                                                        PreferenceUtils.saveCountOfItemsBasket(getContext(), 0);
                                                    } else {
                                                        Toasty.error(getContext(), getBookingResponse.getMessage(), Toast.LENGTH_LONG).show();
                                                        payment_webview.setVisibility(View.GONE);
                                                        payment_contain.setVisibility(View.VISIBLE);
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<GetBookingResponse> call, Throwable t) {
                                                    Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                                                    reloadDialog.dismiss();
                                                }
                                            });
                                        }
                                        super.onPageFinished(view, url);
                                    }

                                    @Override
                                    public void onLoadResource(WebView view, String url) {
                                        // TODO Auto-generated method stub
                                        super.onLoadResource(view, url);
                                    }

                                    @Override
                                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                        System.out.println("when you click on any interlink on webview that time you got url :-" + url);
                                        return super.shouldOverrideUrlLoading(view, url);
                                    }
                                });
                                webSetting.setJavaScriptEnabled(true);

                                payment_webview.loadUrl(getMakeOrderResponse.getPayment_url());
                            } else {
                                reloadDialog.dismiss();
                                payment_webview.setVisibility(View.GONE);
                                ((PaymentActivity) getContext()).goToOrderFragment();
                                PreferenceUtils.saveCoupon(getContext(), "");
                                PreferenceUtils.saveCouponAmountType(getContext(), "");
                                PreferenceUtils.saveCouponAmount(getContext(), -1);
                                PreferenceUtils.saveCountOfItemsBasket(getContext(), 0);
                            }


                        } else {
                            Toasty.error(getContext(), getMakeOrderResponse.getErroe(), Toast.LENGTH_LONG).show();
//                            onLoadProfile();
                            reloadDialog.dismiss();
                        }
//                        } else {
//                            Toasty.error(getContext(), getMakeOrderResponse.getErroe(), Toast.LENGTH_LONG).show();
//                            onLoadProfile();
//                        }

                    }

                    @Override
                    public void onFailure(Call<GetMakeOrderResponse> call, Throwable t) {
                        Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                        reloadDialog.dismiss();
                    }
                });
                break;
            }
        }
    }

    String mobile;

    private void onLoadProfile() {
        reloadDialog.show();
        ProfileInfoApi profileInfoApi = APIClient.getClient(SERVER_API_TEST).create(ProfileInfoApi.class);
        Call<GetProfileInfo> getProfileInfoCall;
        getProfileInfoCall = profileInfoApi.getProfileInfocompany(token);
        getProfileInfoCall.enqueue(new Callback<GetProfileInfo>() {
            @Override
            public void onResponse(Call<GetProfileInfo> call, Response<GetProfileInfo> response) {
                GetProfileInfo getProfileInfo = response.body();
                mobile = getProfileInfo.getMobile();
                Fragment fragment = new PaymentPhoneNumberFragment();
                Bundle args = new Bundle();
                args.putString("mobile", mobile);
                args.putString("from", "pay");
                fragment.setArguments(args);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.animate_slide_up_enter, R.anim.animate_slide_up_exit);
                ft.replace(R.id.payment_constrant, fragment, fragment.getTag());
                ft.addToBackStack(null);
                ft.commit();
            }

            @Override
            public void onFailure(Call<GetProfileInfo> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.shipment_from_branch)
    public void fromBranch() {
        shipment_type = 2;
        shipment_from_branch.setSelected(true);
        shipment_to_address.setSelected(false);
        fragment_payid_linear_options_branches.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            shipment_from_branch_img.setImageDrawable(getContext().getDrawable(R.drawable.ic_company));
            shipment_to_address_img.setImageDrawable(getContext().getDrawable(R.drawable.ic_shipped_car));

        } else {
            shipment_to_address_img.setImageResource(R.drawable.ic_shipped_car);
            shipment_from_branch_img.setImageResource(R.drawable.ic_company);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            shipment_to_address_txt.setTextColor(ContextCompat.getColor(getContext(), R.color.colorshipmenttype));
            shipment_from_branch_txt.setTextColor(getContext().getColor(R.color.colorPrimaryDark));
        } else {
            shipment_to_address_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
            shipment_from_branch_txt.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
        }

    }

    @OnClick(R.id.shipment_to_address)
    public void toAddress() {
        shipment_type = 1;
        shipment_from_branch.setSelected(false);
        shipment_to_address.setSelected(true);
        fragment_payid_linear_options_branches.setVisibility(View.GONE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            shipment_from_branch_img.setImageDrawable(getContext().getDrawable(R.drawable.ic_company_unchecked));
            shipment_to_address_img.setImageDrawable(getContext().getDrawable(R.drawable.ic_shipped_car_checked));

        } else {
            shipment_to_address_img.setImageResource(R.drawable.ic_shipped_car_checked);
            shipment_from_branch_img.setImageResource(R.drawable.ic_company_unchecked);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            shipment_to_address_txt.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            shipment_from_branch_txt.setTextColor(getContext().getColor(R.color.colorshipmenttype));
        } else {
            shipment_to_address_txt.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
            shipment_from_branch_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
        }
    }

    @OnClick(R.id.credit_card_payment)
    public void checkCreditCard() {
        shipment_method = 1;
        credit_card_payment.setSelected(true);
        bank_payment.setSelected(false);
        pay_on_arrive_payment.setSelected(false);
        myWalletPayment.setSelected(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            credit_card_payment_img.setImageDrawable(getContext().getDrawable(R.drawable.ic_credit_card_check));
            bank_payment_img.setImageDrawable(getContext().getDrawable(R.drawable.ic_bank_pay));
            pay_on_arrive_payment_img.setImageDrawable(getContext().getDrawable(R.drawable.ic_pay_on_arrive));
            my_wallet_payment_img.setImageDrawable(getContext().getDrawable(R.drawable.ic_wallet_grey));
        } else {
            credit_card_payment_img.setImageResource(R.drawable.ic_credit_card_check);
            bank_payment_img.setImageResource(R.drawable.ic_bank_pay);
            pay_on_arrive_payment_img.setImageResource(R.drawable.ic_pay_on_arrive);
            my_wallet_payment_img.setImageResource(R.drawable.ic_wallet_grey);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            credit_card_payment_imgcheck.setImageDrawable(getContext().getDrawable(R.drawable.ic_checked_payment));
            bank_payment_imgcheck.setImageDrawable(getContext().getDrawable(R.drawable.ic_unchecked_pay_method));
            pay_on_arrive_payment_imgcheck.setImageDrawable(getContext().getDrawable(R.drawable.ic_unchecked_pay_method));
            my_wallet_payment_imgcheck.setImageDrawable(getContext().getDrawable(R.drawable.ic_unchecked_pay_method));
        } else {
            credit_card_payment_img.setImageResource(R.drawable.ic_checked_payment);
            bank_payment_imgcheck.setImageResource(R.drawable.ic_unchecked_pay_method);
            pay_on_arrive_payment_imgcheck.setImageResource(R.drawable.ic_unchecked_pay_method);
            my_wallet_payment_imgcheck.setImageResource(R.drawable.ic_unchecked_pay_method);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            credit_card_payment_txt.setTextColor(ContextCompat.getColor(getContext(), R.color.white_color));
            bank_payment_txt.setTextColor(getContext().getColor(R.color.colorshipmenttype));
            pay_on_arrive_payment_txt.setTextColor(getContext().getColor(R.color.colorshipmenttype));
            my_wallet_payment_txt.setTextColor(getContext().getColor(R.color.colorshipmenttype));
            my_wallet_amount_txt.setTextColor(getContext().getColor(R.color.colorshipmenttype));
        } else {
            credit_card_payment_txt.setTextColor(getContext().getResources().getColor(R.color.white_color));
            bank_payment_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
            pay_on_arrive_payment_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
            my_wallet_payment_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
            my_wallet_amount_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
        }

        hideDelivery();
    }

    @OnClick(R.id.bank_payment)
    public void checkBank() {
        shipment_method = 3;
        credit_card_payment.setSelected(false);
        bank_payment.setSelected(true);
        pay_on_arrive_payment.setSelected(false);
        myWalletPayment.setSelected(false);
        postPaidPaymentLayout.setSelected(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            credit_card_payment_img.setImageDrawable(getContext().getDrawable(R.drawable.ic_credit_card));
            bank_payment_img.setImageDrawable(getContext().getDrawable(R.drawable.ic_bank_pay_checked));
            pay_on_arrive_payment_img.setImageDrawable(getContext().getDrawable(R.drawable.ic_pay_on_arrive));
            my_wallet_payment_img.setImageDrawable(getContext().getDrawable(R.drawable.ic_wallet_grey));
            if (isPostpaidActive) {
                post_paid_payment_img.setImageDrawable(getContext().getDrawable(R.drawable.ic_bill));
            }
        } else {
            credit_card_payment_img.setImageResource(R.drawable.ic_credit_card);
            bank_payment_img.setImageResource(R.drawable.ic_bank_pay_checked);
            pay_on_arrive_payment_img.setImageResource(R.drawable.ic_pay_on_arrive);
            my_wallet_payment_img.setImageResource(R.drawable.ic_wallet_grey);
            if (isPostpaidActive) {
                post_paid_payment_img.setImageResource(R.drawable.ic_bill);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            credit_card_payment_imgcheck.setImageDrawable(getContext().getDrawable(R.drawable.ic_unchecked_payment));
            bank_payment_imgcheck.setImageDrawable(getContext().getDrawable(R.drawable.ic_checked_payment));
            pay_on_arrive_payment_imgcheck.setImageDrawable(getContext().getDrawable(R.drawable.ic_unchecked_pay_method));
            post_paid_payment_imgcheck.setImageDrawable(getContext().getDrawable(R.drawable.ic_unchecked_pay_method));
            my_wallet_payment_imgcheck.setImageDrawable(getContext().getDrawable(R.drawable.ic_unchecked_pay_method));
        } else {
            credit_card_payment_img.setImageResource(R.drawable.ic_unchecked_pay_method);
            bank_payment_imgcheck.setImageResource(R.drawable.ic_checked_payment);
            pay_on_arrive_payment_imgcheck.setImageResource(R.drawable.ic_unchecked_pay_method);
            post_paid_payment_imgcheck.setImageResource(R.drawable.ic_unchecked_pay_method);
            my_wallet_payment_imgcheck.setImageResource(R.drawable.ic_unchecked_pay_method);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            credit_card_payment_txt.setTextColor(ContextCompat.getColor(getContext(), R.color.colorshipmenttype));
            bank_payment_txt.setTextColor(getContext().getColor(R.color.white_color));
            pay_on_arrive_payment_txt.setTextColor(getContext().getColor(R.color.colorshipmenttype));
            if (isPostpaidActive) {
                post_paid_payment_txt.setTextColor(getContext().getColor(R.color.colorshipmenttype));
            }
            my_wallet_payment_txt.setTextColor(getContext().getColor(R.color.colorshipmenttype));
            my_wallet_amount_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
        } else {
            credit_card_payment_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
            bank_payment_txt.setTextColor(getContext().getResources().getColor(R.color.white_color));
            pay_on_arrive_payment_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
            if (isPostpaidActive) {
                post_paid_payment_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
            }
            my_wallet_payment_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
            my_wallet_amount_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));

        }

        hideDelivery();
    }

    @OnClick(R.id.pay_on_arrive_payment)
    public void checkPay() {
        shipment_method = 2;
        credit_card_payment.setSelected(false);
        bank_payment.setSelected(false);
        pay_on_arrive_payment.setSelected(true);
        myWalletPayment.setSelected(false);
        postPaidPaymentLayout.setSelected(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            credit_card_payment_img.setImageDrawable(getContext().getDrawable(R.drawable.ic_credit_card));
            bank_payment_img.setImageDrawable(getContext().getDrawable(R.drawable.ic_bank_pay));
            pay_on_arrive_payment_img.setImageDrawable(getContext().getDrawable(R.drawable.ic_pay_on_arrive_checked));
            my_wallet_payment_img.setImageDrawable(getContext().getDrawable(R.drawable.ic_wallet_grey));
            if (isPostpaidActive) {
                post_paid_payment_img.setImageDrawable(getContext().getDrawable(R.drawable.ic_bill));
            }
        } else {
            credit_card_payment_img.setImageResource(R.drawable.ic_credit_card);
            bank_payment_img.setImageResource(R.drawable.ic_bank_pay);
            pay_on_arrive_payment_img.setImageResource(R.drawable.ic_pay_on_arrive_checked);
            my_wallet_payment_img.setImageResource(R.drawable.ic_wallet_grey);
            if (isPostpaidActive) {
                post_paid_payment_img.setImageResource(R.drawable.ic_bill);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            credit_card_payment_imgcheck.setImageDrawable(getContext().getDrawable(R.drawable.ic_unchecked_payment));
            bank_payment_imgcheck.setImageDrawable(getContext().getDrawable(R.drawable.ic_unchecked_payment));
            pay_on_arrive_payment_imgcheck.setImageDrawable(getContext().getDrawable(R.drawable.ic_checked_payment));
            post_paid_payment_imgcheck.setImageDrawable(getContext().getDrawable(R.drawable.ic_unchecked_pay_method));
            my_wallet_payment_imgcheck.setImageDrawable(getContext().getDrawable(R.drawable.ic_unchecked_pay_method));

        } else {
            credit_card_payment_img.setImageResource(R.drawable.ic_unchecked_pay_method);
            bank_payment_imgcheck.setImageResource(R.drawable.ic_unchecked_payment);
            pay_on_arrive_payment_imgcheck.setImageResource(R.drawable.ic_checked_payment);
            post_paid_payment_imgcheck.setImageResource(R.drawable.ic_unchecked_pay_method);
            my_wallet_payment_imgcheck.setImageResource(R.drawable.ic_unchecked_pay_method);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            credit_card_payment_txt.setTextColor(ContextCompat.getColor(getContext(), R.color.colorshipmenttype));
            bank_payment_txt.setTextColor(getContext().getColor(R.color.colorshipmenttype));
            pay_on_arrive_payment_txt.setTextColor(getContext().getColor(R.color.white_color));
            if (isPostpaidActive) {
                post_paid_payment_txt.setTextColor(getContext().getColor(R.color.colorshipmenttype));
            }
            my_wallet_payment_txt.setTextColor(getContext().getColor(R.color.colorshipmenttype));
            my_wallet_amount_txt.setTextColor(getContext().getColor(R.color.colorshipmenttype));
        } else {
            credit_card_payment_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
            bank_payment_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
            pay_on_arrive_payment_txt.setTextColor(getContext().getResources().getColor(R.color.white_color));
            if (isPostpaidActive) {
                post_paid_payment_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
            }
            my_wallet_payment_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
            my_wallet_amount_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
        }

        showDelivery();
    }

    @OnClick(R.id.post_paid_payment)
    public void checkPostPaid() {
        if (!isPostpaidActive) {
            Toasty.error(getContext(), getString(R.string.ask_activation_toast), Toast.LENGTH_LONG).show();
            return;
        }
        shipment_method = 4;
        bank_payment.setSelected(false);
        pay_on_arrive_payment.setSelected(false);
        postPaidPaymentLayout.setSelected(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bank_payment_img.setImageDrawable(getContext().getDrawable(R.drawable.ic_bank_pay));
            pay_on_arrive_payment_img.setImageDrawable(getContext().getDrawable(R.drawable.ic_pay_on_arrive));
            if (isPostpaidActive) {
                post_paid_payment_img.setImageDrawable(getContext().getDrawable(R.drawable.ic_bill_checked));
            }
        } else {
            bank_payment_img.setImageResource(R.drawable.ic_bank_pay);
            pay_on_arrive_payment_img.setImageResource(R.drawable.ic_pay_on_arrive);
            if (isPostpaidActive) {
                post_paid_payment_img.setImageResource(R.drawable.ic_bill_checked);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bank_payment_imgcheck.setImageDrawable(getContext().getDrawable(R.drawable.ic_unchecked_payment));
            pay_on_arrive_payment_imgcheck.setImageDrawable(getContext().getDrawable(R.drawable.ic_unchecked_payment));
            post_paid_payment_imgcheck.setImageDrawable(getContext().getDrawable(R.drawable.ic_checked_payment));

        } else {
            bank_payment_imgcheck.setImageResource(R.drawable.ic_unchecked_payment);
            pay_on_arrive_payment_imgcheck.setImageResource(R.drawable.ic_unchecked_payment);
            post_paid_payment_imgcheck.setImageResource(R.drawable.ic_checked_payment);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            bank_payment_txt.setTextColor(getContext().getColor(R.color.colorshipmenttype));
            pay_on_arrive_payment_txt.setTextColor(getContext().getColor(R.color.colorshipmenttype));
            if (isPostpaidActive) {
                post_paid_payment_txt.setTextColor(getContext().getColor(R.color.white_color));
            }
        } else {
            bank_payment_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
            pay_on_arrive_payment_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
            if (isPostpaidActive) {
                post_paid_payment_txt.setTextColor(getContext().getResources().getColor(R.color.white_color));
            }
        }

        hideDelivery();
    }

    @OnClick(R.id.my_wallet_payment)
    public void checkMyWallet() {
//        String totalPriceNoSymbol = total_price.replaceAll("\\D.","");
//        double totalPriceDouble = Double.valueOf(totalPriceNoSymbol);
//        if(mWalletAmount < totalPriceDouble){
//            Toast.makeText(getContext(), R.string.no_enough_wallet, Toast.LENGTH_LONG).show();
//            return;
//        }
        shipment_method = 5;
        credit_card_payment.setSelected(false);
        bank_payment.setSelected(false);
        pay_on_arrive_payment.setSelected(false);
        myWalletPayment.setSelected(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            credit_card_payment_img.setImageDrawable(getContext().getDrawable(R.drawable.ic_credit_card));
            bank_payment_img.setImageDrawable(getContext().getDrawable(R.drawable.ic_bank_pay));
            pay_on_arrive_payment_img.setImageDrawable(getContext().getDrawable(R.drawable.ic_pay_on_arrive));
            my_wallet_payment_img.setImageDrawable(getContext().getDrawable(R.drawable.ic_wallet_checked));
        } else {
            credit_card_payment_img.setImageResource(R.drawable.ic_credit_card);
            bank_payment_img.setImageResource(R.drawable.ic_bank_pay);
            pay_on_arrive_payment_img.setImageResource(R.drawable.ic_pay_on_arrive);
            my_wallet_payment_img.setImageResource(R.drawable.ic_wallet_checked);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            credit_card_payment_imgcheck.setImageDrawable(getContext().getDrawable(R.drawable.ic_unchecked_pay_method));
            bank_payment_imgcheck.setImageDrawable(getContext().getDrawable(R.drawable.ic_unchecked_pay_method));
            pay_on_arrive_payment_imgcheck.setImageDrawable(getContext().getDrawable(R.drawable.ic_unchecked_pay_method));
            my_wallet_payment_imgcheck.setImageDrawable(getContext().getDrawable(R.drawable.ic_checked_payment));
        } else {
            credit_card_payment_img.setImageResource(R.drawable.ic_unchecked_pay_method);
            bank_payment_imgcheck.setImageResource(R.drawable.ic_unchecked_pay_method);
            pay_on_arrive_payment_imgcheck.setImageResource(R.drawable.ic_unchecked_pay_method);
            my_wallet_payment_imgcheck.setImageResource(R.drawable.ic_checked_payment);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            credit_card_payment_txt.setTextColor(getContext().getColor(R.color.colorshipmenttype));
            bank_payment_txt.setTextColor(getContext().getColor(R.color.colorshipmenttype));
            pay_on_arrive_payment_txt.setTextColor(getContext().getColor(R.color.colorshipmenttype));
            my_wallet_payment_txt.setTextColor(ContextCompat.getColor(getContext(), R.color.white_color));
            my_wallet_amount_txt.setTextColor(ContextCompat.getColor(getContext(), R.color.white_color));
        } else {
            credit_card_payment_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
            bank_payment_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
            pay_on_arrive_payment_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
            my_wallet_payment_txt.setTextColor(getContext().getResources().getColor(R.color.white_color));
            my_wallet_amount_txt.setTextColor(getContext().getResources().getColor(R.color.white_color));

        }

        hideDelivery();
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

//                    if (ids_countries.contains(id_country)) {
//                        int index = ids_countries.indexOf(id_country);
//                        Collections.swap(ids_countries, 0, index);
//                        Collections.swap(countries, 0, index);
//                        getRegions(id_country);
//                    }
                    spinner_address_add_country_shipment.setItems(countries);
                    spinner_address_add_country_shipment.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                            id_country = ids_countries.get(position);
                            if (countries.size() > 1 && countries.contains(getString(R.string.country_spinner_label))) {
                                ids_countries.remove(Integer.valueOf(-1));
                                countries.remove(getString(R.string.country_spinner_label));
                            }
                            getRegions(id_country);
                        }
                    });
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetCountries> call, Throwable t) {
                Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
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
                if (getContext() != null && code == 200) {
                    if(!regions.contains(getString(R.string.regions_spinner_label))){
                        regions.clear();
                        ids_regions.clear();
                    }else {
                        regions.clear();
                        ids_regions.clear();
                        regions.add(getString(R.string.regions_spinner_label));
                        ids_regions.add(-1);
                    }
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
//                    if (ids_regions.contains(id_region)) {
//                        int index = ids_regions.indexOf(id_region);
//                        Collections.swap(ids_regions, 0, index);
//                        Collections.swap(regions, 0, index);
//                        getCities(id_region);
//                    }

                    spinner_address_add_regions_shipment.setItems(regions);
                    spinner_address_add_regions_shipment.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                            id_region = ids_regions.get(position);
                            if (regions.size() > 1 && regions.contains(getString(R.string.regions_spinner_label))) {
                                ids_regions.remove(Integer.valueOf(-1));
                                regions.remove(getString(R.string.regions_spinner_label));
                            }
                            getCities(id_region);
                        }
                    });
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetRegions> call, Throwable t) {
                if (getContext() != null)
                    Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
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
                if (getContext() != null && response.body() != null) {
                    GetBranches getBranches = response.body();
                    if(!branches.contains(getString(R.string.branch_spinner))){
                        branches.clear();
                        ids_branches.clear();
                    }else {
                        branches.clear();
                        ids_branches.clear();
                        branches.add(getString(R.string.branch_spinner));
                        ids_branches.add(-1);
                    }
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
//                    if (ids_branches.contains(id_branch)) {
//                        int index = ids_branches.indexOf(id_branch);
//                        Collections.swap(ids_branches, 0, index);
//                        Collections.swap(branches, 0, index);
//                    }

                    spinner_address_add_branches_shipment.setItems(branches);
                    spinner_address_add_branches_shipment.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                            id_branch = ids_branches.get(position);
                            if (branches.size() > 1 && branches.contains(getString(R.string.branch_spinner))) {
                                ids_branches.remove(Integer.valueOf(-1));
                                branches.remove(getString(R.string.branch_spinner));
                            }
                        }
                    });
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetBranches> call, Throwable t) {
                Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    private void getWalletStatus() {
        reloadDialog.show();
        MakeOrderApi makeOrderApi = APIClient.getClient(SERVER_API_TEST).create(MakeOrderApi.class);
        makeOrderApi.getWalletStatus(PreferenceUtils.getUserToken(getContext())).enqueue(new Callback<GetWalletResponse>() {
            @Override
            public void onResponse(Call<GetWalletResponse> call, Response<GetWalletResponse> response) {
                if (response.body() != null && response.body().getStatus()) {
                    if (response.body().getTotal() > 0) {
                        myWalletPayment.setVisibility(View.VISIBLE);
                        my_wallet_amount_txt.setText(PriceFormatter.toDecimalRsString(response.body().getTotal(), getContext()));
                    } else {
                        myWalletPayment.setVisibility(View.GONE);
                    }
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetWalletResponse> call, Throwable t) {
                Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    private void getPostPaidStatus() {
        reloadDialog.show();
        MakeOrderApi makeOrderApi = APIClient.getClient(SERVER_API_TEST).create(MakeOrderApi.class);
        makeOrderApi.getPostPaidStatus(PreferenceUtils.getCompanyToken(getContext())).enqueue(new Callback<GetPostPaidResponse>() {
            @Override
            public void onResponse(Call<GetPostPaidResponse> call, Response<GetPostPaidResponse> response) {
                if (response.body() != null && response.body().getStatus().equals("success")) {
                    String availabilityStatus = response.body().getAvailable();
                    if (availabilityStatus.equals("ACTIVE")) {
                        isPostpaidActive = true;
                        activationRequestTv.setVisibility(View.GONE);
                        setPostpaidActiveUI();
                        post_paid_payment_imgcheck.setVisibility(View.VISIBLE);
                    } else if (availabilityStatus.equals("AVAILABLE")) {
                        activationRequestTv.setVisibility(View.VISIBLE);
                        post_paid_payment_imgcheck.setVisibility(View.GONE);
                    } else if (availabilityStatus.equals("UNAVAILABLE")) {
                        activationRequestTv.setVisibility(View.GONE);
                        post_paid_payment_imgcheck.setVisibility(View.GONE);
                    }
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetPostPaidResponse> call, Throwable t) {
                Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    private void setPostpaidActiveUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            post_paid_payment_img.setImageDrawable(getContext().getDrawable(R.drawable.ic_bill));
        } else {
            my_wallet_payment_img.setImageResource(R.drawable.ic_bill);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            post_paid_payment_txt.setTextColor(getContext().getColor(R.color.colorshipmenttype));
        } else {
            post_paid_payment_txt.setTextColor(getContext().getResources().getColor(R.color.colorshipmenttype));
        }
    }

    @OnClick(R.id.activation_request_tv)
    void requestActivation() {
        reloadDialog.show();
        MakeOrderApi makeOrderApi = APIClient.getClient(SERVER_API_TEST).create(MakeOrderApi.class);
        makeOrderApi.activatePostpaid(PreferenceUtils.getCompanyToken(getContext())).enqueue(new Callback<GetActivatePostpaidResponse>() {
            @Override
            public void onResponse(Call<GetActivatePostpaidResponse> call, Response<GetActivatePostpaidResponse> response) {
                if (response.body() != null && getContext() != null) {
                    if (response.body().getStatus().equals("success")) {
                        activationRequestTv.setVisibility(View.GONE);
                        Toasty.success(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toasty.success(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetActivatePostpaidResponse> call, Throwable t) {
                if (getContext() != null) {
                    Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                }
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

    private void showDelivery() {
        if (PreferenceUtils.getUserLogin(getContext())) {
            fragment_payid_delivery_linear.setVisibility(View.VISIBLE);
            fragment_payid_delivery_view.setVisibility(View.VISIBLE);
            fragment_payid_total_price_text.setText(PriceFormatter.toDecimalRsString(finalPriceDouble + deliveryFee, getContext()));
        }
    }

    private void hideDelivery() {
        if (PreferenceUtils.getUserLogin(getContext())) {
            fragment_payid_delivery_linear.setVisibility(View.GONE);
            fragment_payid_delivery_view.setVisibility(View.GONE);
            fragment_payid_total_price_text.setText(PriceFormatter.toDecimalRsString(finalPriceDouble, getContext()));
        }
    }

    private void showDialog() {
        reloadDialog = new Dialog(getContext());
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
