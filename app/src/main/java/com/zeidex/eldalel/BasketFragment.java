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

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.adapters.BasketElementsAdapter;
import com.zeidex.eldalel.models.BasketProducts;
import com.zeidex.eldalel.response.DeleteBasketResponse;
import com.zeidex.eldalel.response.GetBasketProducts;
import com.zeidex.eldalel.response.GetChangeQuantityResponse;
import com.zeidex.eldalel.services.BasketProductsApi;
import com.zeidex.eldalel.services.ChangeQuantityApi;
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

    @BindView(R.id.fragment_basket_elements_edittext)
    AppCompatEditText fragment_basket_elements_edittext;

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

    public void initializeRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        fragment_basket_elements_recycler.setLayoutManager(layoutManager);
        fragment_basket_elements_recycler.setItemAnimator(new DefaultItemAnimator());

    }

    String token;
    String language;
    ArrayList<BasketProducts> basketProducts;

    public void onLoadPage() {
        if (PreferenceUtils.getUserLogin(getActivity())) {
            token = PreferenceUtils.getUserToken(getActivity());
        } else if (PreferenceUtils.getCompanyLogin(getActivity())) {
            token = PreferenceUtils.getCompanyToken(getActivity());
        }
        reloadDialog.show();
        basketProducts = new ArrayList<>();
        BasketProductsApi basketProductsApi = APIClient.getClient(SERVER_API_TEST).create(BasketProductsApi.class);
        Call<GetBasketProducts> getBasketProductsCall = basketProductsApi.getBasketProducts(token);
        getBasketProductsCall.enqueue(new Callback<GetBasketProducts>() {
            @Override
            public void onResponse(Call<GetBasketProducts> call, Response<GetBasketProducts> response) {
                GetBasketProducts getBasketProducts = response.body();
                int code = Integer.parseInt(getBasketProducts.getCode());
                if (code == 200) {
                    Locale locale = ChangeLang.getLocale(getResources());
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
                    basketElementsAdapter = new BasketElementsAdapter(getActivity(), basketProducts);
                    basketElementsAdapter.setBasketOperation(BasketFragment.this);
                    fragment_basket_elements_recycler.setAdapter(basketElementsAdapter);
                    double totalDouble = Double.parseDouble(getBasketProducts.getTotal());
                    String totalString = PriceFormatter.toDecimalRsString(totalDouble, getActivity().getApplicationContext());
                    fragment_basket_total_price_products_text.setText(totalString);
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
    }


    Dialog changeQuantity;

    @Override
    public void onChangeQuantity(int id, int pos) {
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
                    Call<GetChangeQuantityResponse> getChangeQuantityResponseCall = changeQuantityApi.getChangeQuantity(post);
                    getChangeQuantityResponseCall.enqueue(new Callback<GetChangeQuantityResponse>() {
                        @Override
                        public void onResponse(Call<GetChangeQuantityResponse> call, Response<GetChangeQuantityResponse> response) {
                            GetChangeQuantityResponse changeQuantityResponse = response.body();
                            int code = changeQuantityResponse.getCode();
                            if (code == 200) {
                                if (changeQuantityResponse.getStatus() == true) {
                                    Toasty.success(getActivity(), changeQuantityResponse.getSuccessMessage(), Toast.LENGTH_LONG).show();
                                    BasketProducts basketProductsModel = basketProducts.get(pos);
                                    basketProductsModel.setItem_count(changeQuantityResponse.getProdcutQuantity());
                                    basketProducts.set(pos, basketProductsModel);
                                    basketElementsAdapter.notifyItemChanged(pos);
                                    reloadDialog.dismiss();
                                    changeQuantity.dismiss();
                                } else {
                                    Toasty.error(getActivity(), changeQuantityResponse.getMessage(), Toast.LENGTH_LONG).show();
                                    reloadDialog.dismiss();
                                    changeQuantity.dismiss();
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
            if (currentField.getText().toString().length() <= 0) {
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
        Call<DeleteBasketResponse> deleteBasketResponseCall = deleteBasketAPI.deleteBasketProduct(String.valueOf(id), token);
        deleteBasketResponseCall.enqueue(new Callback<DeleteBasketResponse>() {
            @Override
            public void onResponse(Call<DeleteBasketResponse> call, Response<DeleteBasketResponse> response) {
                if(response!= null && response.body().getCode() == 200){
                    basketElementsAdapter.getBasketProducts().remove(pos);
                    basketElementsAdapter.notifyItemRemoved(pos);
                    String totalString = PriceFormatter.toDecimalRsString(response.body().getData().getOrderTotalPrice(), getActivity().getApplicationContext());
                    fragment_basket_total_price_products_text.setText(totalString);
                    fragment_basket_total_price_text.setText(totalString);
                    Toasty.success(getActivity(), getString(R.string.delete_cart_toast) , Toast.LENGTH_LONG).show();
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
