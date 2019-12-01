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
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.AppCompatEditText;
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

    BasketElementsAdapter basketElementsAdapter;

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
        findViews();
    }


    public void findViews() {
        showDialog();
        fragment_basket_paying.setOnClickListener(this);
        onLoadPage();
    }

    @OnClick({R.id.fragment_basket_elements_enter_code})
    public void onEnterCoupoun() {
        final boolean fieldscomOK = validate(new EditText[]{fragment_basket_elements_edittext});
        if (fieldscomOK) {
            reloadDialog.show();
            CheckCouponAPI checkCouponAPI = APIClient.getClient(SERVER_API_TEST).create(CheckCouponAPI.class);
            Call<GetCheckCouponResponse> getCheckCouponResponseCall = checkCouponAPI.checkCouponResponse(fragment_basket_elements_edittext.getText().toString());
            getCheckCouponResponseCall.enqueue(new Callback<GetCheckCouponResponse>() {
                @Override
                public void onResponse(Call<GetCheckCouponResponse> call, Response<GetCheckCouponResponse> response) {
                    GetCheckCouponResponse getCheckCouponResponse = response.body();
                    if (getCheckCouponResponse.getStatus().equalsIgnoreCase("false")) {
                        Toasty.error(getActivity(), getCheckCouponResponse.getError(), Toast.LENGTH_LONG).show();
                    } else {
                        PreferenceUtils.saveCoupon(getActivity(), fragment_basket_elements_edittext.getText().toString());
                        fragment_basket_elements_edittext.setText("");
                        Toasty.success(getActivity(), "مبروك", Toast.LENGTH_LONG).show();
                    }
                    reloadDialog.dismiss();
                }

                @Override
                public void onFailure(Call<GetCheckCouponResponse> call, Throwable t) {
                    Toasty.error(getActivity(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                    reloadDialog.dismiss();
                }
            });
        }
    }

    public void initializeRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        fragment_basket_elements_recycler.setLayoutManager(layoutManager);
        fragment_basket_elements_recycler.setItemAnimator(new DefaultItemAnimator());

    }

    String token;
    String language;
    ArrayList<BasketProducts> basketProducts;


    public static String totalString;
    public static String totalwithoutString;
    public static String taxString;

    public void onLoadPage() {
        if (PreferenceUtils.getUserLogin(getActivity())) {
            token = PreferenceUtils.getUserToken(getActivity());
        } else if (PreferenceUtils.getCompanyLogin(getActivity())) {
            token = PreferenceUtils.getCompanyToken(getActivity());
        }
        reloadDialog.show();
        basketProducts = new ArrayList<>();
        BasketProductsApi basketProductsApi = APIClient.getClient(SERVER_API_TEST).create(BasketProductsApi.class);
        Call<GetBasketProducts> getBasketProductsCall;
        Call<GetBasketCompanyProducts> getBasketProductsCompanyCall;
        if (PreferenceUtils.getCompanyLogin(getActivity())) {
            getBasketProductsCompanyCall = basketProductsApi.getBasketProductscompany(token);
            getBasketProductsCompanyCall.enqueue(new Callback<GetBasketCompanyProducts>() {
                @Override
                public void onResponse(Call<GetBasketCompanyProducts> call, Response<GetBasketCompanyProducts> response) {
                    GetBasketCompanyProducts getBasketProducts = response.body();
                    Locale locale = ChangeLang.getLocale(getContext().getResources());
                    String loo = locale.getLanguage();
                    if (loo.equalsIgnoreCase("en")) {
                        language = "english";
                        for (int i = 0; i < getBasketProducts.getCarts().size(); i++) {
                            String arr[] = getBasketProducts.getCarts().get(i).getProduct().getName().split(" ", 2); // get first word
                            String firstWord = arr[0];
                            if (getBasketProducts.getCarts().get(i).getProduct().getPhotos().size() == 0) {
                                basketProducts.add(new BasketProducts(getBasketProducts.getCarts().get(i).getProduct().getCartId(), getBasketProducts.getCarts().get(i).getProduct().getId(),
                                        firstWord, "", getBasketProducts.getCarts().get(i).getProduct().getName(),
                                        getBasketProducts.getCarts().get(i).getProduct().getPrice(), getBasketProducts.getCarts().get(i).getProduct().getOldPrice(), getBasketProducts.getCarts().get(i).getProduct().getItemCount()));
                            } else {
                                basketProducts.add(new BasketProducts(getBasketProducts.getCarts().get(i).getProduct().getCartId(), getBasketProducts.getCarts().get(i).getProduct().getId(),
                                        firstWord, getBasketProducts.getCarts().get(i).getProduct().getPhotos().get(0).getFilename(), getBasketProducts.getCarts().get(i).getProduct().getName(),
                                        getBasketProducts.getCarts().get(i).getProduct().getPrice(), getBasketProducts.getCarts().get(i).getProduct().getOldPrice(), getBasketProducts.getCarts().get(i).getProduct().getItemCount()));
                            }
                        }

                    } else if (loo.equalsIgnoreCase("ar")) {
                        language = "arabic";
                        for (int i = 0; i < getBasketProducts.getCarts().size(); i++) {
                            String arr[] = getBasketProducts.getCarts().get(i).getProduct().getNameAr().split(" ", 2); // get first word
                            String firstWord = arr[0];
                            if (getBasketProducts.getCarts().get(i).getProduct().getPhotos().size() == 0) {
                                basketProducts.add(new BasketProducts(getBasketProducts.getCarts().get(i).getProduct().getCartId(), getBasketProducts.getCarts().get(i).getProduct().getId(),
                                        firstWord, "", getBasketProducts.getCarts().get(i).getProduct().getNameAr(),
                                        getBasketProducts.getCarts().get(i).getProduct().getPrice(), getBasketProducts.getCarts().get(i).getProduct().getOldPrice(), getBasketProducts.getCarts().get(i).getProduct().getItemCount()));
                            } else {
                                basketProducts.add(new BasketProducts(getBasketProducts.getCarts().get(i).getProduct().getCartId(), getBasketProducts.getCarts().get(i).getProduct().getId(),
                                        firstWord, getBasketProducts.getCarts().get(i).getProduct().getPhotos().get(0).getFilename(), getBasketProducts.getCarts().get(i).getProduct().getNameAr(),
                                        getBasketProducts.getCarts().get(i).getProduct().getPrice(), getBasketProducts.getCarts().get(i).getProduct().getOldPrice(), getBasketProducts.getCarts().get(i).getProduct().getItemCount()));
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

                    double total_without_tax = Double.parseDouble(getBasketProducts.getOrderTotalPrice());
                    double tax = (Double.parseDouble(getBasketProducts.getOrderTotalPrice()) * 5) / 100;
                    double total_price = Double.parseDouble(getBasketProducts.getOrderTotalPrice()) + tax;
                    basketElementsAdapter = new BasketElementsAdapter(getActivity(), basketProducts);
                    basketElementsAdapter.setBasketOperation(BasketFragment.this);
                    fragment_basket_elements_recycler.setAdapter(basketElementsAdapter);

                    totalString = PriceFormatter.toDecimalRsString(total_price, getActivity().getApplicationContext());
                    totalwithoutString = PriceFormatter.toDecimalRsString(total_without_tax, getActivity().getApplicationContext());
                    taxString = PriceFormatter.toDecimalRsString(tax, getActivity().getApplicationContext());

                    fragment_basket_total_price_products_text.setText(totalwithoutString);
                    fragment_basket_tax_text.setText(taxString);
                    fragment_basket_total_price_text.setText(totalString);

                    reloadDialog.dismiss();
                }

                @Override
                public void onFailure(Call<GetBasketCompanyProducts> call, Throwable t) {
                    Toasty.error(getActivity(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                    reloadDialog.dismiss();
                }
            });
        } else {
            getBasketProductsCall = basketProductsApi.getBasketProducts(token);
            getBasketProductsCall.enqueue(new Callback<GetBasketProducts>() {
                @Override
                public void onResponse(Call<GetBasketProducts> call, Response<GetBasketProducts> response) {
                    GetBasketProducts getBasketProducts = response.body();
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

                        double total_without_tax = Double.parseDouble(getBasketProducts.getTotal());
                        double tax = (Double.parseDouble(getBasketProducts.getTotal()) * 5) / 100;
                        double total_price = Double.parseDouble(getBasketProducts.getTotal()) + tax;

                        basketElementsAdapter = new BasketElementsAdapter(getActivity(), basketProducts);
                        basketElementsAdapter.setBasketOperation(BasketFragment.this);
                        fragment_basket_elements_recycler.setAdapter(basketElementsAdapter);

                        totalString = PriceFormatter.toDecimalRsString(total_price, getActivity().getApplicationContext());
                        totalwithoutString = PriceFormatter.toDecimalRsString(total_without_tax, getActivity().getApplicationContext());
                        taxString = PriceFormatter.toDecimalRsString(tax, getActivity().getApplicationContext());

                        fragment_basket_total_price_products_text.setText(totalwithoutString);
                        fragment_basket_tax_text.setText(taxString);
                        fragment_basket_total_price_text.setText(totalString);
                    }
                    reloadDialog.dismiss();
                }

                @Override
                public void onFailure(Call<GetBasketProducts> call, Throwable t) {
                    Toasty.error(getActivity(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                    reloadDialog.dismiss();
                }
            });
        }

    }

    @Override
    public void onCreate(@androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                ((MainActivity)getActivity()).navigateToHomeFragment();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fragment_basket_paying: {
                startActivity(new Intent(getActivity(), PaymentActivity.class));
                break;
            }
        }
    }

    Dialog reloadDialog;

    private void showDialog() {
        reloadDialog = new Dialog(getActivity());
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
//        startActivity(new Intent(getActivity(), DetailItemFragment.class).putExtra("id", id));
//        Animatoo.animateSwipeLeft(getActivity());
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putInt("pos", pos);
        NavHostFragment.findNavController(this).navigate(R.id.action_basketFragment_to_detailItemFragment, bundle);
    }


    Dialog changeQuantity;

    @Override
    public void onChangeQuantity(int id, int pos, int cart_id) {
        changeQuantity = new Dialog(getActivity());
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
                    if (PreferenceUtils.getCompanyLogin(getActivity())) {
                        post.put("cart_id", cart_id + "");
                        getChangeQuantityResponseCall = changeQuantityApi.getChangeQuantitycompany(post);
                    } else {
                        getChangeQuantityResponseCall = changeQuantityApi.getChangeQuantity(post);
                    }
                    getChangeQuantityResponseCall.enqueue(new Callback<GetChangeQuantityResponse>() {
                        @Override
                        public void onResponse(Call<GetChangeQuantityResponse> call, Response<GetChangeQuantityResponse> response) {
                            GetChangeQuantityResponse changeQuantityResponse = response.body();
                            int code = changeQuantityResponse.getCode();
                            if (code == 200) {
                                if (changeQuantityResponse.getStatus() == true) {

                                    double total_without_tax = Double.parseDouble(changeQuantityResponse.getTotal_price());
                                    double tax = (Double.parseDouble(changeQuantityResponse.getTotal_price()) * 5) / 100;
                                    double total_price = Double.parseDouble(changeQuantityResponse.getTotal_price()) + tax;
                                    totalString = PriceFormatter.toDecimalRsString(total_price, getActivity().getApplicationContext());
                                    totalwithoutString = PriceFormatter.toDecimalRsString(total_without_tax, getActivity().getApplicationContext());
                                    taxString = PriceFormatter.toDecimalRsString(tax, getActivity().getApplicationContext());
                                    fragment_basket_total_price_text.setText(totalString);
                                    fragment_basket_total_price_products_text.setText(totalwithoutString);
                                    fragment_basket_tax_text.setText(taxString);

                                    Toasty.success(getActivity(), changeQuantityResponse.getSuccessMessage(), Toast.LENGTH_LONG).show();
                                    BasketProducts basketProductsModel = basketProducts.get(pos);
                                    basketProductsModel.setItem_count(changeQuantityResponse.getProdcutQuantity());
                                    basketProducts.set(pos, basketProductsModel);
                                    basketElementsAdapter.notifyItemChanged(pos);
                                    PreferenceUtils.saveCountOfItemsBasket(getContext().getApplicationContext(), Integer.parseInt(changeQuantityResponse.getAllCartItemsCount()));
                                    reloadDialog.dismiss();
                                    changeQuantity.dismiss();
                                } else {
                                    Toasty.error(getActivity(), changeQuantityResponse.getMessage(), Toast.LENGTH_LONG).show();
                                    reloadDialog.dismiss();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<GetChangeQuantityResponse> call, Throwable t) {
                            Toasty.error(getActivity(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
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
        if (PreferenceUtils.getCompanyLogin(getActivity())) {
            deleteBasketResponseCall = deleteBasketAPI.deleteBasketProductcompany(String.valueOf(id), token);
        } else {
            deleteBasketResponseCall = deleteBasketAPI.deleteBasketProduct(String.valueOf(id), token);
        }
        deleteBasketResponseCall.enqueue(new Callback<DeleteBasketResponse>() {
            @Override
            public void onResponse(Call<DeleteBasketResponse> call, Response<DeleteBasketResponse> response) {
                DeleteBasketResponse responseBody = response.body();
                if (response != null && responseBody.getCode() == 200) {
                    basketElementsAdapter.getBasketProducts().remove(pos);
                    basketElementsAdapter.notifyItemRemoved(pos);

                    double total_price = responseBody.getData().getOrderTotalPrice();
                    double total_without_tax = responseBody.getData().getOrderTotalPrice() - ((responseBody.getData().getOrderTotalPrice() * 5) / 100);
                    double tax = (responseBody.getData().getOrderTotalPrice() * 5) / 100;
                    totalString = PriceFormatter.toDecimalRsString(total_price, getActivity().getApplicationContext());
                    totalwithoutString = PriceFormatter.toDecimalRsString(total_without_tax, getActivity().getApplicationContext());
                    taxString = PriceFormatter.toDecimalRsString(tax, getActivity().getApplicationContext());
                    fragment_basket_total_price_text.setText(totalString);
                    fragment_basket_total_price_products_text.setText(totalwithoutString);
                    fragment_basket_tax_text.setText(taxString);

                    PreferenceUtils.saveCountOfItemsBasket(getContext().getApplicationContext(), responseBody.getData().getAllCartItemsCount());
                    Toasty.success(getActivity(), getString(R.string.delete_cart_toast), Toast.LENGTH_LONG).show();
                    if (responseBody.getData().getAllCartItemsCount() == 0) {
                        fragment_basket_elements_recycler_noitems.setVisibility(View.VISIBLE);
                        fragment_basket_paying.setVisibility(View.GONE);
                        reloadDialog.dismiss();
                        return;
                    }
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<DeleteBasketResponse> call, Throwable t) {
                Toasty.error(getActivity(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }
}
