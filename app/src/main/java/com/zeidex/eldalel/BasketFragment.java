package com.zeidex.eldalel;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.adapters.BasketElementsAdapter;
import com.zeidex.eldalel.models.BasketProducts;
import com.zeidex.eldalel.response.DeleteBasketResponse;
import com.zeidex.eldalel.response.GetBasketCompanyProducts;
import com.zeidex.eldalel.response.GetBasketProducts;
import com.zeidex.eldalel.response.GetChangeQuantityResponse;
import com.zeidex.eldalel.response.GetCheckCouponResponse;
import com.zeidex.eldalel.services.BasketProductsApi;
import com.zeidex.eldalel.services.ChangeQuantityApi;
import com.zeidex.eldalel.services.CheckCouponAPI;
import com.zeidex.eldalel.services.DeleteBasketAPI;
import com.zeidex.eldalel.utils.APIClient;
import com.zeidex.eldalel.utils.ChangeLang;
import com.zeidex.eldalel.utils.PreferenceUtils;
import com.zeidex.eldalel.utils.PriceFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class BasketFragment extends androidx.fragment.app.Fragment implements View.OnClickListener, BasketElementsAdapter.BasketOperation {
    @BindView(R.id.fragment_basket_elements_recycler)
    RecyclerView fragment_basket_elements_recycler;

    @BindView(R.id.fragment_basket_paying)
    AppCompatTextView fragment_basket_paying;

    @BindView(R.id.fragment_basket_total_price_products_text)
    AppCompatTextView fragment_basket_total_price_products_text;

    @BindView(R.id.fragment_basket_total_price_text)
    AppCompatTextView fragment_basket_total_price_text;

    @BindView(R.id.fragment_basket_elements_recycler_noitems)
    AppCompatTextView fragment_basket_elements_recycler_noitems;

    @BindView(R.id.fragment_basket_tax_text)
    AppCompatTextView fragment_basket_tax_text;

    @BindView(R.id.fragment_basket_elements_enter_code)
    AppCompatTextView fragment_basket_elements_enter_code;

    @BindView(R.id.fragment_basket_elements_edittext)
    AppCompatEditText fragment_basket_elements_edittext;

    @BindView(R.id.fragment_basket_edittext_linear)
    LinearLayoutCompat fragment_basket_edittext_linear;

    @BindView(R.id.fragment_basket_coupon_linear)
    LinearLayoutCompat fragment_basket_coupon_linear;

    @BindView(R.id.fragment_basket_coupon_view)
    View fragment_basket_coupon_view;

    @BindView(R.id.fragment_basket_coupon_text)
    TextView fragment_basket_coupon_text;

    @BindView(R.id.coupon_valid_iv)
    AppCompatImageView couponValidIv;

    @BindView(R.id.expired_text_tv)
    AppCompatTextView expiredTextTv;

    BasketElementsAdapter basketElementsAdapter;
    public static double mTotal_price; //in case of no discount
    public static double finalPrice; // in case of discount


    boolean isCouponAlreadyAdded; //used to check from server if coupon still valid (used once at page first load)
    private boolean hasCouponDiscount; //used to update coupon discount if amount changed or item deleted
    private double total_without_tax;
    private double tax;
    private double tax_percentage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basket, container, false);

    }

    @Override
    public void onViewCreated(@Nonnull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initializeRecycler();
        fragment_basket_paying.setOnClickListener(this);
        if (basketProducts == null) {
            findViews();
        } else {
            updateUI();
        }
    }


    public void findViews() {
        showDialog();
        onLoadPage();
    }

    @OnClick({R.id.fragment_basket_elements_enter_code})
    public void onEnterCoupoun(View view) {
        if (((TextView) view).getText().equals(getString(R.string.fragment_basket_elements_enter_code_label)) || isCouponAlreadyAdded) {
            final boolean fieldscomOK = validate(new EditText[]{fragment_basket_elements_edittext});
            if (fieldscomOK) {
                reloadDialog.show();
                CheckCouponAPI checkCouponAPI = APIClient.getClient(SERVER_API_TEST).create(CheckCouponAPI.class);
                Call<GetCheckCouponResponse> getCheckCouponResponseCall = checkCouponAPI.checkCouponResponse(fragment_basket_elements_edittext.getText().toString());
                getCheckCouponResponseCall.enqueue(new Callback<GetCheckCouponResponse>() {
                    @Override
                    public void onResponse(Call<GetCheckCouponResponse> call, Response<GetCheckCouponResponse> response) {
                        GetCheckCouponResponse getCheckCouponResponse = response.body();
                        if (getContext() != null) {
                            if (getCheckCouponResponse.getStatus().equalsIgnoreCase("false")) {
                                if (isCouponAlreadyAdded) {
                                    couponValidIv.setVisibility(View.GONE);
                                    expiredTextTv.setVisibility(View.VISIBLE);

                                    fragment_basket_coupon_linear.setVisibility(View.GONE);
                                    fragment_basket_coupon_view.setVisibility(View.GONE);
                                    fragment_basket_coupon_text.setText("0");
                                    fragment_basket_total_price_text.setText(totalString);

                                    isCouponAlreadyAdded = false;
                                    hasCouponDiscount = false;
                                } else {
                                    Toasty.error(getContext(), getCheckCouponResponse.getError(), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                if (!isCouponAlreadyAdded) {
                                    addCoupon(fragment_basket_elements_edittext.getText().toString(), response.body().getData());
                                    Toasty.success(getContext(), getString(R.string.code_added_successfully), Toast.LENGTH_LONG).show();
                                } else {
                                    isCouponAlreadyAdded = false;
                                }
                            }
                        }
                        reloadDialog.dismiss();

                    }

                    @Override
                    public void onFailure(Call<GetCheckCouponResponse> call, Throwable t) {
                        if (getContext() != null) {
                            Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                        }
                        reloadDialog.dismiss();

                    }
                });
            }
        } else if (((TextView) view).getText().equals(getString(R.string.remove))) {
            resetCoupon();
        }
    }

    public void initializeRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        fragment_basket_elements_recycler.setLayoutManager(layoutManager);
        fragment_basket_elements_recycler.setItemAnimator(new DefaultItemAnimator());

    }

    String token;
    String language;
    ArrayList<BasketProducts> basketProducts;


    public static String totalString;
    public static String totalwithoutString;
    public static String taxString;
    public static String couponDiscountString;
    public static String totalWithDiscountString;

    public void onLoadPage() {
        if (PreferenceUtils.getUserLogin(getContext())) {
            token = PreferenceUtils.getUserToken(getContext());
        } else if (PreferenceUtils.getCompanyLogin(getContext())) {
            token = PreferenceUtils.getCompanyToken(getContext());
            fragment_basket_edittext_linear.setVisibility(View.GONE);
        }
        reloadDialog.show();
        basketProducts = new ArrayList<>();
        BasketProductsApi basketProductsApi = APIClient.getClient(SERVER_API_TEST).create(BasketProductsApi.class);
        Call<GetBasketProducts> getBasketProductsCall;
        Call<GetBasketCompanyProducts> getBasketProductsCompanyCall;
        if (PreferenceUtils.getCompanyLogin(getContext())) {
            getBasketProductsCompanyCall = basketProductsApi.getBasketProductscompany(token);
            getBasketProductsCompanyCall.enqueue(new Callback<GetBasketCompanyProducts>() {
                @Override
                public void onResponse(Call<GetBasketCompanyProducts> call, Response<GetBasketCompanyProducts> response) {
                    GetBasketCompanyProducts getBasketProducts = response.body();
                    if (getContext() != null) {
                        Locale locale = ChangeLang.getLocale(getContext().getResources());
                        String loo = locale.getLanguage();
                        if (loo.equalsIgnoreCase("en")) {
                            language = "english";
                            for (int i = 0; i < getBasketProducts.getCarts().size(); i++) {
                                String arr[] = getBasketProducts.getCarts().get(i).getProduct().getName().split(" ", 2); // get first word
                                String firstWord = arr[0];
                                if (getBasketProducts.getCarts().get(i).getProduct().getPhotos().size() == 0) {
                                    if (PreferenceUtils.getUserLogin(getContext())) {
                                        basketProducts.add(new BasketProducts(getBasketProducts.getCarts().get(i).getProduct().getCartId(), getBasketProducts.getCarts().get(i).getProduct().getId(),
                                                firstWord, "", getBasketProducts.getCarts().get(i).getProduct().getName(),
                                                getBasketProducts.getCarts().get(i).getProduct().getPrice(), getBasketProducts.getCarts().get(i).getProduct().getOldPrice(), getBasketProducts.getCarts().get(i).getProduct().getItemCount()));
                                    } else if (PreferenceUtils.getCompanyLogin(getContext())) {
                                        basketProducts.add(new BasketProducts(getBasketProducts.getCarts().get(i).getProduct().getCartId(), getBasketProducts.getCarts().get(i).getProduct().getId(),
                                                firstWord, "", getBasketProducts.getCarts().get(i).getProduct().getName(),
                                                getBasketProducts.getCarts().get(i).getProduct().getWholesalePrice(), getBasketProducts.getCarts().get(i).getProduct().getOldPrice(), getBasketProducts.getCarts().get(i).getProduct().getItemCount()));
                                    }
                                } else {

                                    if (PreferenceUtils.getUserLogin(getContext())) {
                                        basketProducts.add(new BasketProducts(getBasketProducts.getCarts().get(i).getProduct().getCartId(), getBasketProducts.getCarts().get(i).getProduct().getId(),
                                                firstWord, getBasketProducts.getCarts().get(i).getProduct().getPhotos().get(0).getFilename(), getBasketProducts.getCarts().get(i).getProduct().getName(),
                                                getBasketProducts.getCarts().get(i).getProduct().getPrice(), getBasketProducts.getCarts().get(i).getProduct().getOldPrice(), getBasketProducts.getCarts().get(i).getProduct().getItemCount()));
                                    } else if (PreferenceUtils.getCompanyLogin(getContext())) {
                                        basketProducts.add(new BasketProducts(getBasketProducts.getCarts().get(i).getProduct().getCartId(), getBasketProducts.getCarts().get(i).getProduct().getId(),
                                                firstWord, getBasketProducts.getCarts().get(i).getProduct().getPhotos().get(0).getFilename(), getBasketProducts.getCarts().get(i).getProduct().getName(),
                                                getBasketProducts.getCarts().get(i).getProduct().getWholesalePrice(), getBasketProducts.getCarts().get(i).getProduct().getOldPrice(), getBasketProducts.getCarts().get(i).getProduct().getItemCount()));
                                    }
                                }
                            }

                        } else {
                            language = "arabic";
                            for (int i = 0; i < getBasketProducts.getCarts().size(); i++) {
                                String arr[] = getBasketProducts.getCarts().get(i).getProduct().getNameAr().split(" ", 2); // get first word
                                String firstWord = arr[0];
                                if (getBasketProducts.getCarts().get(i).getProduct().getPhotos().size() == 0) {
                                    if (PreferenceUtils.getUserLogin(getContext())) {
                                        basketProducts.add(new BasketProducts(getBasketProducts.getCarts().get(i).getProduct().getCartId(), getBasketProducts.getCarts().get(i).getProduct().getId(),
                                                firstWord, "", getBasketProducts.getCarts().get(i).getProduct().getNameAr(),
                                                getBasketProducts.getCarts().get(i).getProduct().getPrice(), getBasketProducts.getCarts().get(i).getProduct().getOldPrice(), getBasketProducts.getCarts().get(i).getProduct().getItemCount()));
                                    } else if (PreferenceUtils.getCompanyLogin(getContext())) {
                                        basketProducts.add(new BasketProducts(getBasketProducts.getCarts().get(i).getProduct().getCartId(), getBasketProducts.getCarts().get(i).getProduct().getId(),
                                                firstWord, "", getBasketProducts.getCarts().get(i).getProduct().getNameAr(),
                                                getBasketProducts.getCarts().get(i).getProduct().getWholesalePrice(), getBasketProducts.getCarts().get(i).getProduct().getOldPrice(), getBasketProducts.getCarts().get(i).getProduct().getItemCount()));
                                    }
                                } else {

                                    if (PreferenceUtils.getUserLogin(getContext())) {
                                        basketProducts.add(new BasketProducts(getBasketProducts.getCarts().get(i).getProduct().getCartId(), getBasketProducts.getCarts().get(i).getProduct().getId(),
                                                firstWord, getBasketProducts.getCarts().get(i).getProduct().getPhotos().get(0).getFilename(), getBasketProducts.getCarts().get(i).getProduct().getNameAr(),
                                                getBasketProducts.getCarts().get(i).getProduct().getPrice(), getBasketProducts.getCarts().get(i).getProduct().getOldPrice(), getBasketProducts.getCarts().get(i).getProduct().getItemCount()));
                                    } else if (PreferenceUtils.getCompanyLogin(getContext())) {
                                        basketProducts.add(new BasketProducts(getBasketProducts.getCarts().get(i).getProduct().getCartId(), getBasketProducts.getCarts().get(i).getProduct().getId(),
                                                firstWord, getBasketProducts.getCarts().get(i).getProduct().getPhotos().get(0).getFilename(), getBasketProducts.getCarts().get(i).getProduct().getNameAr(),
                                                getBasketProducts.getCarts().get(i).getProduct().getWholesalePrice(), getBasketProducts.getCarts().get(i).getProduct().getOldPrice(), getBasketProducts.getCarts().get(i).getProduct().getItemCount()));
                                    }
                                }
                            }
                        }

                        if (basketProducts.size() == 0) {
                            fragment_basket_elements_recycler_noitems.setVisibility(View.VISIBLE);
                            fragment_basket_paying.setVisibility(View.GONE);
                            fragment_basket_edittext_linear.setVisibility(View.GONE);
                            reloadDialog.dismiss();
                            return;
                        }


//                        mTotal_price = Double.parseDouble(getBasketProducts.getOrderTotalPrice());
//                        double total_without_tax = mTotal_price * 100 / 105;
//                        double tax = mTotal_price - total_without_tax;
                        mTotal_price = Double.parseDouble(getBasketProducts.getOrderTotalPrice());
                        total_without_tax = getBasketProducts.getTotal_without_taxes();
                        tax = getBasketProducts.getTaxes_amount();
                        tax_percentage = getBasketProducts.getTaxes_percentage();

                        totalString = PriceFormatter.toDecimalRsString(mTotal_price, getContext());
                        totalwithoutString = PriceFormatter.toDecimalRsString(total_without_tax, getContext());
                        taxString = PriceFormatter.toDecimalRsString(tax, getContext());

                        updateUI();
                    }
                    reloadDialog.dismiss();

                }

                @Override
                public void onFailure(Call<GetBasketCompanyProducts> call, Throwable t) {
                    if (getContext() != null) {
                        Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                    }
                    reloadDialog.dismiss();

                }
            });
        } else {
            getBasketProductsCall = basketProductsApi.getBasketProducts(token);
            getBasketProductsCall.enqueue(new Callback<GetBasketProducts>() {
                @Override
                public void onResponse(Call<GetBasketProducts> call, Response<GetBasketProducts> response) {
                    GetBasketProducts getBasketProducts = response.body();
                    if (getContext() != null) {
                        int code = Integer.parseInt(getBasketProducts.getCode());
                        if (code == 200) {
                            Locale locale = ChangeLang.getLocale(getContext().getResources());
                            String loo = locale.getLanguage();
                            if (loo.equalsIgnoreCase("en")) {
                                language = "english";
                                for (int i = 0; i < getBasketProducts.getProducts().size(); i++) {
                                    String arr[] = getBasketProducts.getProducts().get(i).getName().split(" ", 2); // get first word
                                    String firstWord = arr[0];
                                    if (getBasketProducts.getProducts().get(i).getPhotos().size() == 0) {
                                        basketProducts.add(new BasketProducts(getBasketProducts.getProducts().get(i).getCartId(), getBasketProducts.getProducts().get(i).getId(),
                                                firstWord, "", getBasketProducts.getProducts().get(i).getName(),
                                                getBasketProducts.getProducts().get(i).getPrice(), getBasketProducts.getProducts().get(i).getOldPrice(), getBasketProducts.getProducts().get(i).getItemCount()));
                                    } else {
                                        basketProducts.add(new BasketProducts(getBasketProducts.getProducts().get(i).getCartId(), getBasketProducts.getProducts().get(i).getId(),
                                                firstWord, getBasketProducts.getProducts().get(i).getPhotos().get(0).getFilename(), getBasketProducts.getProducts().get(i).getName(),
                                                getBasketProducts.getProducts().get(i).getPrice(), getBasketProducts.getProducts().get(i).getOldPrice(), getBasketProducts.getProducts().get(i).getItemCount()));
                                    }
                                }

                            } else if (loo.equalsIgnoreCase("ar")) {
                                language = "arabic";
                                for (int i = 0; i < getBasketProducts.getProducts().size(); i++) {
                                    String arr[] = getBasketProducts.getProducts().get(i).getNameAr().split(" ", 2); // get first word
                                    String firstWord = arr[0];
                                    if (getBasketProducts.getProducts().get(i).getPhotos().size() == 0) {
                                        basketProducts.add(new BasketProducts(getBasketProducts.getProducts().get(i).getCartId(), getBasketProducts.getProducts().get(i).getId(),
                                                firstWord, "", getBasketProducts.getProducts().get(i).getNameAr(),
                                                getBasketProducts.getProducts().get(i).getPrice(), getBasketProducts.getProducts().get(i).getOldPrice(), getBasketProducts.getProducts().get(i).getItemCount()));
                                    } else {
                                        basketProducts.add(new BasketProducts(getBasketProducts.getProducts().get(i).getCartId(), getBasketProducts.getProducts().get(i).getId(),
                                                firstWord, getBasketProducts.getProducts().get(i).getPhotos().get(0).getFilename(), getBasketProducts.getProducts().get(i).getNameAr(),
                                                getBasketProducts.getProducts().get(i).getPrice(), getBasketProducts.getProducts().get(i).getOldPrice(), getBasketProducts.getProducts().get(i).getItemCount()));
                                    }
                                }
                            }
                            if (basketProducts.size() == 0) {
                                fragment_basket_elements_recycler_noitems.setVisibility(View.VISIBLE);
                                fragment_basket_paying.setVisibility(View.GONE);
                                fragment_basket_edittext_linear.setVisibility(View.GONE);
                                reloadDialog.dismiss();
                                return;
                            }

                            mTotal_price = Double.parseDouble(getBasketProducts.getTotal());
                            total_without_tax = getBasketProducts.getTotal_without_taxes();
                            tax = getBasketProducts.getTaxes_amount();
                            tax_percentage = getBasketProducts.getTaxes_percentage();


//                            double total_without_tax = mTotal_price * 100 / 105;
//                            double tax = mTotal_price - total_without_tax;

                            totalString = PriceFormatter.toDecimalRsString(mTotal_price, getContext());
                            totalwithoutString = PriceFormatter.toDecimalRsString(total_without_tax, getContext());
                            taxString = PriceFormatter.toDecimalRsString(tax, getContext());

                            updateUI();
                        }
                    }
                    reloadDialog.dismiss();
                }

                @Override
                public void onFailure(Call<GetBasketProducts> call, Throwable t) {
                    if (getContext() != null) {
                        Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                    }
                    reloadDialog.dismiss();
                }
            });
        }

    }

    private void updateUI() {
        if (basketProducts.size() == 0) {
            fragment_basket_elements_recycler_noitems.setVisibility(View.VISIBLE);
            fragment_basket_paying.setVisibility(View.GONE);
            fragment_basket_edittext_linear.setVisibility(View.GONE);
        } else {
            basketElementsAdapter = new BasketElementsAdapter(getContext(), basketProducts);
            basketElementsAdapter.setBasketOperation(BasketFragment.this);
            fragment_basket_elements_recycler.setAdapter(basketElementsAdapter);

            fragment_basket_total_price_products_text.setText(totalwithoutString);
            fragment_basket_tax_text.setText(taxString);
            fragment_basket_total_price_text.setText(totalString);

            if (!PreferenceUtils.getCoupoun(getContext()).equals("")) {
                isCouponAlreadyAdded = true;
                hasCouponDiscount = true;
                showCoupon();
            }
        }
    }

    @Override
    public void onCreate(@androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                ((MainActivity) getContext()).navigateToHomeFragment();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fragment_basket_paying: {
                startActivity(new Intent(getContext(), PaymentActivity.class));
                break;
            }
        }
    }

    Dialog reloadDialog;

    private void showDialog() {
        reloadDialog = new Dialog(getContext());
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    Map<String, String> post;

    private void convertChangeQuantityDaraToJson(String quantty, String lang, String product_id) {
        post = new HashMap<>();
        post.put("product_id", product_id);
        post.put("quantity", quantty);
        post.put("language", lang);
        post.put("token", token);
    }

    @Override
    public void onClickBasketProduct(int id, int pos) {
//        startActivity(new Intent(getContext(), DetailItemFragment.class).putExtra("id", id));
//        Animatoo.animateSwipeLeft(getContext());
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putInt("pos", pos);
        NavHostFragment.findNavController(this).navigate(R.id.action_basketFragment_to_detailItemFragment, bundle);
    }


    Dialog changeQuantity;

    @Override
    public void onChangeQuantity(int id, int pos, int cart_id) {
        changeQuantity = new Dialog(getContext());
        changeQuantity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        changeQuantity.setContentView(R.layout.dialog_change_quantity);
        changeQuantity.setCancelable(false);
        AppCompatEditText dialog_edittext = changeQuantity.findViewById(R.id.dialog_change_quantity_dialog);
        AppCompatTextView dialog_ok = changeQuantity.findViewById(R.id.dialog_text_ok);
        AppCompatTextView dialog_cancel = changeQuantity.findViewById(R.id.dialog_text_cancel);
        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeQuantity.dismiss();
            }
        });
        dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean fieldscomOK = validate(new EditText[]{dialog_edittext});
                if (fieldscomOK) {
                    convertChangeQuantityDaraToJson(dialog_edittext.getText().toString(), language, id + "");
                    reloadDialog.show();
                    ChangeQuantityApi changeQuantityApi = APIClient.getClient(SERVER_API_TEST).create(ChangeQuantityApi.class);
                    Call<GetChangeQuantityResponse> getChangeQuantityResponseCall;
                    if (PreferenceUtils.getCompanyLogin(getContext())) {
                        post.put("cart_id", cart_id + "");
                        getChangeQuantityResponseCall = changeQuantityApi.getChangeQuantitycompany(post);
                    } else {
                        getChangeQuantityResponseCall = changeQuantityApi.getChangeQuantity(post);
                    }
                    getChangeQuantityResponseCall.enqueue(new Callback<GetChangeQuantityResponse>() {
                        @Override
                        public void onResponse(Call<GetChangeQuantityResponse> call, Response<GetChangeQuantityResponse> response) {
                            GetChangeQuantityResponse changeQuantityResponse = response.body();
                            if (getContext() != null) {
                                int code = changeQuantityResponse.getCode();
                                if (code == 200) {
                                    if (changeQuantityResponse.getStatus() == true) {

//                                        mTotal_price = Double.parseDouble(changeQuantityResponse.getTotal_price());
//                                        double total_without_tax = mTotal_price * 100 / 105;
//                                        double tax = mTotal_price - total_without_tax;

                                        mTotal_price = Double.parseDouble(changeQuantityResponse.getTotal_price());
                                        total_without_tax = changeQuantityResponse.getTotal_without_taxes();
                                        tax = changeQuantityResponse.getTaxes_amount();
                                        tax_percentage = changeQuantityResponse.getTaxes_percentage();

                                        totalString = PriceFormatter.toDecimalRsString(mTotal_price, getContext());
                                        totalwithoutString = PriceFormatter.toDecimalRsString(total_without_tax, getContext());
                                        taxString = PriceFormatter.toDecimalRsString(tax, getContext());
                                        if (hasCouponDiscount) {
                                            updateCouponDiscount();
                                        } else {
                                            fragment_basket_total_price_text.setText(totalString);
                                        }
                                        fragment_basket_total_price_products_text.setText(totalwithoutString);
                                        fragment_basket_tax_text.setText(taxString);

                                        Toasty.success(getContext(), changeQuantityResponse.getSuccessMessage(), Toast.LENGTH_LONG).show();
                                        BasketProducts basketProductsModel = basketProducts.get(pos);
                                        basketProductsModel.setItem_count(changeQuantityResponse.getProdcutQuantity());
                                        basketProducts.set(pos, basketProductsModel);
                                        basketElementsAdapter.notifyItemChanged(pos);
                                        PreferenceUtils.saveCountOfItemsBasket(getContext().getApplicationContext(), Integer.parseInt(changeQuantityResponse.getAllCartItemsCount()));
                                        ((MainActivity) getActivity()).updateBasketBadge();
                                        changeQuantity.dismiss();
                                    } else {
                                        Toasty.error(getContext(), changeQuantityResponse.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                            reloadDialog.dismiss();

                        }

                        @Override
                        public void onFailure(Call<GetChangeQuantityResponse> call, Throwable t) {
                            if (getContext() != null) {
                                Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                            }
                            reloadDialog.dismiss();
                        }
                    });
                }
            }
        });
        changeQuantity.show();

    }

    private boolean validate(EditText[] fields) {
        for (int i = 0; i < fields.length; i++) {
            EditText currentField = fields[i];
            if (currentField.getText().length() <= 0) {
                currentField.setError(getString(R.string.field_required));
                currentField.requestFocus();
                return false;
            }
        }
        return true;
    }

    @Override
    public void onDeleteItem(int id, int pos) {
        reloadDialog.show();
        DeleteBasketAPI deleteBasketAPI = APIClient.getClient(SERVER_API_TEST).create(DeleteBasketAPI.class);
        Call<DeleteBasketResponse> deleteBasketResponseCall;
        if (PreferenceUtils.getCompanyLogin(getContext())) {
            deleteBasketResponseCall = deleteBasketAPI.deleteBasketProductcompany(String.valueOf(id), token);
        } else {
            deleteBasketResponseCall = deleteBasketAPI.deleteBasketProduct(String.valueOf(id), token);
        }
        deleteBasketResponseCall.enqueue(new Callback<DeleteBasketResponse>() {
            @Override
            public void onResponse(Call<DeleteBasketResponse> call, Response<DeleteBasketResponse> response) {
                DeleteBasketResponse responseBody = response.body();
                if (getContext() != null) {
                    if (response.body() != null && responseBody.getCode() == 200) {
                        basketElementsAdapter.getBasketProducts().remove(pos);
                        basketElementsAdapter.notifyItemRemoved(pos);

//                        mTotal_price = responseBody.getData().getOrderTotalPrice();
//                        double total_without_tax = mTotal_price * 100 / 105;
//                        double tax = mTotal_price - total_without_tax;
                        mTotal_price = responseBody.getData().getOrderTotalPrice();
                        total_without_tax = responseBody.getData().getTotal_without_taxes();
                        tax = responseBody.getData().getTaxes_amount();
                        tax_percentage = responseBody.getData().getTaxes_percentage();

                        totalString = PriceFormatter.toDecimalRsString(mTotal_price, getContext());
                        totalwithoutString = PriceFormatter.toDecimalRsString(total_without_tax, getContext());
                        taxString = PriceFormatter.toDecimalRsString(tax, getContext());

                        if (hasCouponDiscount) {
                            updateCouponDiscount();
                        } else {
                            fragment_basket_total_price_text.setText(totalString);
                        }
                        fragment_basket_total_price_products_text.setText(totalwithoutString);
                        fragment_basket_tax_text.setText(taxString);

                        PreferenceUtils.saveCountOfItemsBasket(getContext().getApplicationContext(), responseBody.getData().getAllCartItemsCount());
                        ((MainActivity) getActivity()).updateBasketBadge();
                        Toasty.success(getContext(), getString(R.string.delete_cart_toast), Toast.LENGTH_LONG).show();
                        if (responseBody.getData().getAllCartItemsCount() == 0) {
                            fragment_basket_elements_recycler_noitems.setVisibility(View.VISIBLE);
                            fragment_basket_paying.setVisibility(View.GONE);
                            fragment_basket_edittext_linear.setVisibility(View.GONE);
                            resetCoupon();
                            reloadDialog.dismiss();
                            return;
                        }
                    }
                }
                reloadDialog.dismiss();

            }

            @Override
            public void onFailure(Call<DeleteBasketResponse> call, Throwable t) {
                if (getContext() != null) {
                    Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                }
                reloadDialog.dismiss();

            }
        });
    }

    private void resetCoupon() {
        PreferenceUtils.saveCoupon(getContext(), "");
        PreferenceUtils.saveCouponAmountType(getContext(), "");
        PreferenceUtils.saveCouponAmount(getContext(), -1);

        fragment_basket_elements_edittext.setText("");
        fragment_basket_elements_edittext.setEnabled(true);
        fragment_basket_elements_enter_code.setText(R.string.fragment_basket_elements_enter_code_label);

        fragment_basket_coupon_linear.setVisibility(View.GONE);
        fragment_basket_coupon_view.setVisibility(View.GONE);
        fragment_basket_coupon_text.setText("0");
        fragment_basket_total_price_text.setText(totalString);

        couponValidIv.setVisibility(View.GONE);
        expiredTextTv.setVisibility(View.GONE);

        hasCouponDiscount = false;
    }

    private void addCoupon(String coupon, GetCheckCouponResponse.Data couponData) {
        String amountType = couponData.getAmountType();
        int amount = couponData.getAmount();
        PreferenceUtils.saveCoupon(getContext(), coupon);
        PreferenceUtils.saveCouponAmountType(getContext(), amountType);
        PreferenceUtils.saveCouponAmount(getContext(), amount);

        fragment_basket_elements_edittext.setText(coupon);
        fragment_basket_elements_edittext.setEnabled(false);
        fragment_basket_elements_enter_code.setText(R.string.remove);

        fragment_basket_coupon_linear.setVisibility(View.VISIBLE);
        fragment_basket_coupon_view.setVisibility(View.VISIBLE);
        couponValidIv.setVisibility(View.VISIBLE);

        double couponDiscount = calculateDiscount(amountType, amount);

        couponDiscountString = PriceFormatter.toDecimalRsString(-couponDiscount, getContext());

        double taxNewAmount = (total_without_tax - couponDiscount) * tax_percentage;
        finalPrice = total_without_tax - couponDiscount + taxNewAmount;
        fragment_basket_coupon_text.setText(couponDiscountString);
        fragment_basket_total_price_text.setText(totalWithDiscountString);

        hasCouponDiscount = true;

    }

    private void updateCouponDiscount() {
        String amountType = PreferenceUtils.getCoupounAmountType(getContext());
        int amount = PreferenceUtils.getCoupounAmount(getContext());

        double couponDiscount = calculateDiscount(amountType, amount);
        couponDiscountString = PriceFormatter.toDecimalRsString(-couponDiscount, getContext());
        double taxNewAmount = (total_without_tax - couponDiscount) * tax_percentage;
        finalPrice = total_without_tax - couponDiscount + taxNewAmount;
        totalWithDiscountString = PriceFormatter.toDecimalRsString(finalPrice, getContext());

        fragment_basket_coupon_text.setText(couponDiscountString);
        fragment_basket_total_price_text.setText(totalWithDiscountString);
    }

    private void showCoupon() {
        String coupon = PreferenceUtils.getCoupoun(getContext());
        String amountType = PreferenceUtils.getCoupounAmountType(getContext());
        int amount = PreferenceUtils.getCoupounAmount(getContext());

        fragment_basket_elements_edittext.setText(coupon);
        fragment_basket_elements_edittext.setEnabled(false);
        fragment_basket_elements_enter_code.setText(R.string.remove);

        fragment_basket_coupon_linear.setVisibility(View.VISIBLE);
        fragment_basket_coupon_view.setVisibility(View.VISIBLE);
        couponValidIv.setVisibility(View.VISIBLE);

        double couponDiscount = calculateDiscount(amountType, amount);

        couponDiscountString = PriceFormatter.toDecimalRsString(-couponDiscount, getContext());
        double taxNewAmount = (total_without_tax - couponDiscount) * tax_percentage;
        finalPrice = total_without_tax - couponDiscount + taxNewAmount;
        totalWithDiscountString = PriceFormatter.toDecimalRsString(finalPrice, getContext());

        fragment_basket_coupon_text.setText(couponDiscountString);
        fragment_basket_total_price_text.setText(totalWithDiscountString);

        onEnterCoupoun(fragment_basket_elements_enter_code);

    }

    private double calculateDiscount(String amountType, int amount) {
        if (amountType.equals("percentage")) {
            return total_without_tax * amount / 100;
        } else if (amountType.equals("fixed")) {
            return amount;
        } else return 0;
    }
}
