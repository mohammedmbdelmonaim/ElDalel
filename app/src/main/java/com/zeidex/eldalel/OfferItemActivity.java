package com.zeidex.eldalel;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.adapters.CategoryItemAdapter;
import com.zeidex.eldalel.adapters.SubCategoriesAdapter;
import com.zeidex.eldalel.models.ProductsCategory;
import com.zeidex.eldalel.models.Subcategory;
import com.zeidex.eldalel.models.Subsubcategory;
import com.zeidex.eldalel.response.GetAddToCardResponse;
import com.zeidex.eldalel.response.GetAddToFavouriteResponse;
import com.zeidex.eldalel.response.GetProducts;
import com.zeidex.eldalel.services.AddToCardApi;
import com.zeidex.eldalel.services.AddToFavouriteApi;
import com.zeidex.eldalel.services.OffersAPI;
import com.zeidex.eldalel.utils.APIClient;
import com.zeidex.eldalel.utils.Animatoo;
import com.zeidex.eldalel.utils.ChangeLang;
import com.zeidex.eldalel.utils.GridSpacingItemDecoration;
import com.zeidex.eldalel.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zeidex.eldalel.OffersFragment.CATEGORY_NAME_INTENT_EXTRA;
import static com.zeidex.eldalel.OffersFragment.SUBCATEGORIES_INTENT_EXTRA_KEY;
import static com.zeidex.eldalel.utils.Constants.CART_NOT_EMPTY;
import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class OfferItemActivity extends BaseActivity implements CategoryItemAdapter.CategoryItemOperation, SubCategoriesAdapter.SubCategoryOperation {

    public static final String OFFERS = "offers";
    @BindView(R.id.offer_item_recycler_categories)
    RecyclerView offer_item_recycler_categories;

    @BindView(R.id.offer_category_item_recycler_list)
    RecyclerView offer_category_item_recycler_list;

    @BindView(R.id.offer_item_header_text)
    AppCompatTextView offerItemHeaderText;

    @BindView(R.id.offer_no_items_layout)
    RelativeLayout offerNoItemsLayout;

    //    CategoryItemAdapter categoryAdapter;
    CategoryItemAdapter productsAdapter;
    SubCategoriesAdapter subCategoriesAdapter;

    Dialog reloadDialog;
    String token = "";
    private List<Subcategory> subcategories;

    Map<String, String> cartPost;
    Map<String, String> likePost;
    private ArrayList<ProductsCategory> productsCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_item);
        ButterKnife.bind(this);
        initializeRecycler();
        findViews();
    }

    @OnClick(R.id.offer_item_back)
    public void onBack() {
        onBackPressed();
    }

    public void findViews() {
        if (PreferenceUtils.getCompanyLogin(this)) {
            token = PreferenceUtils.getCompanyToken(this);
        } else if (PreferenceUtils.getUserLogin(this)) {
            token = PreferenceUtils.getUserToken(this);
        }

        showDialog();
        onLoadPage();
    }

    private void onLoadPage() {
        offerItemHeaderText.setText(getIntent().getStringExtra(CATEGORY_NAME_INTENT_EXTRA));
        subcategories = getIntent().getParcelableArrayListExtra(SUBCATEGORIES_INTENT_EXTRA_KEY);
        if (subcategories != null && subcategories.size() > 0) {
            subCategoriesAdapter = new SubCategoriesAdapter(this, subcategories, true);
            subCategoriesAdapter.setSubCategoryOperation(this);
            offer_item_recycler_categories.setAdapter(subCategoriesAdapter);
            getSubcategoryProducts(subcategories.get(0).getId());
        }

    }

    public void getSubcategoryProducts(int subcategoryId) {
        reloadDialog.show();
        OffersAPI offersAPI = APIClient.getClient(SERVER_API_TEST).create(OffersAPI.class);
        offersAPI.getOffersProducts(OFFERS, subcategoryId, token).enqueue(new Callback<GetProducts>() {
            @Override
            public void onResponse(Call<GetProducts> call, Response<GetProducts> response) {
                if (response.body() != null) {
                    int code = response.body().getCode();
                    if (code == 200) {
                        List<GetProducts.Data> offers = response.body().getProducts().getDataAll();
                        if (offers.size() > 0) {
                            productsCategory = getProductsFromResponse(offers);
                            productsAdapter.setProductsList(productsCategory);
                            productsAdapter.notifyDataSetChanged();
                            showRecycler();
                        } else {
                            showEmptyView();
                        }
                    }
                    reloadDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<GetProducts> call, Throwable t) {
                Toasty.error(OfferItemActivity.this, getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    private ArrayList<ProductsCategory> getProductsFromResponse(List<GetProducts.Data> productsResponse) {
        ArrayList<ProductsCategory> products = new ArrayList<>();

        Locale locale = ChangeLang.getLocale(getResources());
        String loo = locale.getLanguage();

        if (loo.equalsIgnoreCase("ar")) {
            for (int j = 0; j < productsResponse.size(); j++) { // product loop

                GetProducts.Data currentProductResponse = productsResponse.get(j);
                String arr[] = currentProductResponse.getName_ar().split(" ", 2); // get first word
                String firstWord = arr[0];

                if (productsResponse.get(j).getPhotos().size() == 0) {
                    products.add(new ProductsCategory(currentProductResponse.getId(), "",
                            currentProductResponse.getDiscount(), firstWord, currentProductResponse.getName_ar(),
                            currentProductResponse.getPrice(), currentProductResponse.getOld_price(),
                            currentProductResponse.getFavorite(), String.valueOf(currentProductResponse.getCart()),
                            currentProductResponse.getAvailable_quantity()));
                } else {
                    products.add(new ProductsCategory(currentProductResponse.getId(), currentProductResponse.getPhotos().get(0).getFilename(),
                            currentProductResponse.getDiscount(), firstWord, currentProductResponse.getName_ar(),
                            currentProductResponse.getPrice(), currentProductResponse.getOld_price(),
                            currentProductResponse.getFavorite(), String.valueOf(currentProductResponse.getCart()), currentProductResponse.getAvailable_quantity()));
                }
            }
        } else {
            for (int j = 0; j < productsResponse.size(); j++) { // product loop

                GetProducts.Data currentProductResponse = productsResponse.get(j);
                String arr[] = currentProductResponse.getName().split(" ", 2); // get first word
                String firstWord = arr[0];

                if (productsResponse.get(j).getPhotos().size() == 0) {
                    products.add(new ProductsCategory(currentProductResponse.getId(), "",
                            currentProductResponse.getDiscount(), firstWord, currentProductResponse.getName(),
                            currentProductResponse.getPrice(), currentProductResponse.getOld_price(),
                            currentProductResponse.getFavorite(), String.valueOf(currentProductResponse.getCart()),
                            currentProductResponse.getAvailable_quantity()));

                } else {
                    products.add(new ProductsCategory(currentProductResponse.getId(), currentProductResponse.getPhotos().get(0).getFilename(),
                            currentProductResponse.getDiscount(), firstWord, currentProductResponse.getName(),
                            currentProductResponse.getPrice(), currentProductResponse.getOld_price(),
                            currentProductResponse.getFavorite(), String.valueOf(currentProductResponse.getCart()), currentProductResponse.getAvailable_quantity()));
                }
            }
        }

        return products;
    }

    private void showEmptyView() {
        offer_category_item_recycler_list.setVisibility(View.GONE);
        offerNoItemsLayout.setVisibility(View.VISIBLE);
    }

    private void showRecycler() {
        offer_category_item_recycler_list.setVisibility(View.VISIBLE);
        offerNoItemsLayout.setVisibility(View.GONE);
    }

    public void initializeRecycler() {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        offer_category_item_recycler_list.setLayoutManager(mLayoutManager);
        offer_category_item_recycler_list.setItemAnimator(new DefaultItemAnimator());

        int spanCount = 2; // 3 columns
        int spacing = 20; // 50px
        boolean includeEdge = true;
        offer_category_item_recycler_list.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

//        categoryAdapter = new CategoryItemAdapter(this);
//        categoryAdapter.setCategoryOperation(this);
//        offer_category_item_recycler_list.setAdapter(categoryAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        productsAdapter = new CategoryItemAdapter(this);
        productsAdapter.setCategoryOperation(OfferItemActivity.this);
        offer_category_item_recycler_list.setAdapter(productsAdapter);
        offer_item_recycler_categories.setLayoutManager(layoutManager);
        offer_item_recycler_categories.setItemAnimator(new DefaultItemAnimator());
    }

    private void showDialog() {
        reloadDialog = new Dialog(this);
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    @Override
    public void onClickSubCategory(int subcategoryId, String subcategoryName) {
        getSubcategoryProducts(subcategoryId);
    }

    @Override
    public void onClickSubCategoryWithSubSub(ArrayList<Subsubcategory> subsubcategories, String subcategoryName) {

    }

    @Override
    public void onClickProduct(int id, int pos) {
        startActivityForResult(new Intent(this, DetailItemActivity.class).putExtra("id", id).putExtra("similar_products", productsCategory).putExtra("getLike", productsCategory.get(pos).getLike()).putExtra("pos", pos), 1111);
        Animatoo.animateSwipeLeft(this);
    }

    @Override
    public void onClickProductLike(int id) {
        reloadDialog.show();
        prepareLikeMap(id);
        AddToFavouriteApi addToFavouriteApi = APIClient.getClient(SERVER_API_TEST).create(AddToFavouriteApi.class);
        Call<GetAddToFavouriteResponse> getAddToFavouriteResponseCall = addToFavouriteApi.getAddToFavourite(likePost);
        getAddToFavouriteResponseCall.enqueue(new Callback<GetAddToFavouriteResponse>() {
            @Override
            public void onResponse(Call<GetAddToFavouriteResponse> call, Response<GetAddToFavouriteResponse> response) {
                GetAddToFavouriteResponse getAddToFavouriteResponse = response.body();
                if (Integer.parseInt(getAddToFavouriteResponse.getCode()) == 200) {
                    Toasty.success(OfferItemActivity.this, getString(R.string.add_to_favourites), Toast.LENGTH_LONG).show();
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetAddToFavouriteResponse> call, Throwable t) {
                Toasty.error(OfferItemActivity.this, getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    @Override
    public void onAddToProductCart(int id, int position) {
        prepareCartMap(id);
        AddToCardApi addToCardApi = APIClient.getClient(SERVER_API_TEST).create(AddToCardApi.class);
        Call<GetAddToCardResponse> getAddToCardResponseCall = addToCardApi.getAddToFavourite(cartPost);
        getAddToCardResponseCall.enqueue(new Callback<GetAddToCardResponse>() {
            @Override
            public void onResponse(Call<GetAddToCardResponse> call, Response<GetAddToCardResponse> response) {
                GetAddToCardResponse getAddToCardResponse = response.body();
                if (getAddToCardResponse.getCode() == 200) {
                    Toasty.success(OfferItemActivity.this, getString(R.string.add_to_card), Toast.LENGTH_LONG).show();
                    productsAdapter.getProductsList().get(position).setCart(String.valueOf(CART_NOT_EMPTY));
                    productsAdapter.notifyItemChanged(position);
                    PreferenceUtils.saveCountOfItemsBasket(OfferItemActivity.this, Integer.parseInt(getAddToCardResponse.getItemsCount()));
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetAddToCardResponse> call, Throwable t) {
                Toasty.error(OfferItemActivity.this, getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    public void prepareCartMap(int id) {
        cartPost = new HashMap<>();
        if (PreferenceUtils.getUserLogin(OfferItemActivity.this)) {
            String token = PreferenceUtils.getUserToken(OfferItemActivity.this);
            cartPost.put("product_id", String.valueOf(id));
            cartPost.put("token", token);
            cartPost.put("quantity", "1");
        } else if (PreferenceUtils.getCompanyLogin(OfferItemActivity.this)) {
            String token = PreferenceUtils.getCompanyToken(OfferItemActivity.this);
            cartPost.put("product_id", String.valueOf(id));
            cartPost.put("token", token);
            cartPost.put("quantity", "1");
        }
        reloadDialog.show();
    }

    private void prepareLikeMap(int id) {
        likePost = new HashMap<>();
        if (PreferenceUtils.getUserLogin(OfferItemActivity.this)) {
            String token = PreferenceUtils.getUserToken(OfferItemActivity.this);
            likePost.put("product_id", String.valueOf(id));
            likePost.put("token", token);
        } else if (PreferenceUtils.getCompanyLogin(OfferItemActivity.this)) {
            String token = PreferenceUtils.getCompanyToken(OfferItemActivity.this);
            likePost.put("product_id", String.valueOf(id));
            likePost.put("token", token);
        }
    }
}

