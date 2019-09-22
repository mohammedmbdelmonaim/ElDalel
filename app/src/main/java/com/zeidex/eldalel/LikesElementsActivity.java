package com.zeidex.eldalel;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.adapters.LikesElementsAdapter;
import com.zeidex.eldalel.models.GetFavorites;
import com.zeidex.eldalel.models.ProductsCategory;
import com.zeidex.eldalel.response.GetAddToCardResponse;
import com.zeidex.eldalel.services.AddToCardApi;
import com.zeidex.eldalel.services.FavoritesAPI;
import com.zeidex.eldalel.utils.APIClient;
import com.zeidex.eldalel.utils.Animatoo;
import com.zeidex.eldalel.utils.ChangeLang;
import com.zeidex.eldalel.utils.GridSpacingItemDecoration;
import com.zeidex.eldalel.utils.PreferenceUtils;
import com.zeidex.eldalel.utils.PriceFormatter;

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

import static com.zeidex.eldalel.utils.Constants.CART_NOT_EMPTY;
import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class LikesElementsActivity extends BaseActivity implements LikesElementsAdapter.LikesOperation {
    @BindView(R.id.likes_item_recycler_list)
    RecyclerView likes_item_recycler_list;
    LikesElementsAdapter likesElementsAdapter;

    String token = "";
    Dialog reloadDialog;
    private ArrayList<ProductsCategory> products;
    Map<String, String> cartPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elements_likes);
        ButterKnife.bind(this);
        initializeRecycler();
        findViews();
    }

    private void findViews() {
        if (PreferenceUtils.getCompanyLogin(this)) {
            token = PreferenceUtils.getCompanyToken(this);
        } else if (PreferenceUtils.getUserLogin(this)) {
            token = PreferenceUtils.getUserToken(this);
        }

        showDialog();
        onLoadPage();
    }

    private void onLoadPage() {
        reloadDialog.show();
        FavoritesAPI favoritesAPI = APIClient.getClient(SERVER_API_TEST).create(FavoritesAPI.class);
        favoritesAPI.getAllFavorites(token).enqueue(new Callback<GetFavorites>() {
            @Override
            public void onResponse(Call<GetFavorites> call, Response<GetFavorites> response) {
                if (response.body() != null) {
                    int code = response.body().getCode();
                    if (code == 200) {
                        List<GetFavorites.Favorite> favorites = response.body().getData().getFavorites();
                        if (favorites != null && favorites.size() > 0) {
                            products = getProductsFromResponse(favorites);
                            likesElementsAdapter.setProductList(products);
                            likesElementsAdapter.notifyDataSetChanged();
                        }
                    }
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetFavorites> call, Throwable t) {
                Toasty.error(LikesElementsActivity.this, getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    public void initializeRecycler() {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        likes_item_recycler_list.setLayoutManager(mLayoutManager);
        likes_item_recycler_list.setItemAnimator(new DefaultItemAnimator());

        int spanCount = 2; // 3 columns
        int spacing = 20; // 50px
        boolean includeEdge = true;
        likes_item_recycler_list.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        likesElementsAdapter = new LikesElementsAdapter(this);
        likesElementsAdapter.setLikesOperation(this);
        likes_item_recycler_list.setAdapter(likesElementsAdapter);
    }

    private ArrayList<ProductsCategory> getProductsFromResponse
            (List<GetFavorites.Favorite> productsResponse) {
        ArrayList<ProductsCategory> products = new ArrayList<>();

        Locale locale = ChangeLang.getLocale(getResources());
        String loo = locale.getLanguage();

        if (loo.equalsIgnoreCase("ar")) {
            for (int j = 0; j < productsResponse.size(); j++) { // product loop

                GetFavorites.Favorite currentProductResponse = productsResponse.get(j);
                String arr[] = currentProductResponse.getProduct().getNameAr().split(" ", 2); // get first word
                String firstWord = arr[0];

                Double price = currentProductResponse.getProduct().getPrice();
                String priceString = PriceFormatter.toDecimalRsString(price, getApplicationContext());
                Double discount = currentProductResponse.getProduct().getDiscount();
                String discountString = discount != null ? PriceFormatter.toRightNumber(discount, getApplicationContext()) : null;
                Double priceBefore = currentProductResponse.getProduct().getOldPrice();
                String priceBeforeString = priceBefore != null ? PriceFormatter.toDecimalRsString(priceBefore, getApplicationContext()) : null;

//                if (productsResponse.get(j).getPhotos().size() == 0) {
                products.add(new ProductsCategory(String.valueOf(currentProductResponse.getId()), "",
                        discountString, firstWord, currentProductResponse.getProduct().getNameAr(),
                        priceString, priceBeforeString,
                        "", "",
                        ""));
//                } else {
//                    products.add(new ProductsCategory(currentProductResponse.getId(), currentProductResponse.getPhotos().get(0).getFilename(),
//                            currentProductResponse.getDiscount(), firstWord, currentProductResponse.getName_ar(),
//                            currentProductResponse.getPrice(), currentProductResponse.getOld_price(),
//                            currentProductResponse.getFavorite(), String.valueOf(currentProductResponse.getCart()), currentProductResponse.getAvailable_quantity()));
//                }
            }
        } else {
            for (int j = 0; j < productsResponse.size(); j++) { // product loop

                GetFavorites.Favorite currentProductResponse = productsResponse.get(j);
                String arr[] = currentProductResponse.getProduct().getName().split(" ", 2); // get first word
                String firstWord = arr[0];

                Double discount = currentProductResponse.getProduct().getDiscount();
                String discountString = discount != null ? PriceFormatter.toRightNumber(discount, getApplicationContext()) : null;
                Double priceBefore = currentProductResponse.getProduct().getOldPrice();
                String priceBeforeString = priceBefore != null ? String.valueOf(priceBefore) : null;

//                if (productsResponse.get(j).getPhotos().size() == 0) {
                products.add(new ProductsCategory(String.valueOf(currentProductResponse.getId()), "",
                        discountString, firstWord, currentProductResponse.getProduct().getName(),
                        String.valueOf(currentProductResponse.getProduct().getPrice()), priceBeforeString,
                        "", "",
                        ""));

//                } else {
//                    products.add(new ProductsCategory(currentProductResponse.getId(), currentProductResponse.getPhotos().get(0).getFilename(),
//                            currentProductResponse.getDiscount(), firstWord, currentProductResponse.getName(),
//                            currentProductResponse.getPrice(), currentProductResponse.getOld_price(),
//                            currentProductResponse.getFavorite(), String.valueOf(currentProductResponse.getCart()), currentProductResponse.getAvailable_quantity()));
//                }
            }
        }

        return products;
    }


    @Override
    public void onClickProduct(int id, int pos) {
        startActivityForResult(new Intent(this, DetailItemActivity.class).putExtra("id", id).putExtra("similar_products", products).putExtra("getLike", "1").putExtra("pos", pos), 1111);
        Animatoo.animateSwipeLeft(this);
    }

    @Override
    public void onAddToCart(int id, int position) {
        prepareCartMap(id);
        AddToCardApi addToCardApi = APIClient.getClient(SERVER_API_TEST).create(AddToCardApi.class);
        Call<GetAddToCardResponse> getAddToCardResponseCall = addToCardApi.getAddToFavourite(cartPost);
        getAddToCardResponseCall.enqueue(new Callback<GetAddToCardResponse>() {
            @Override
            public void onResponse(Call<GetAddToCardResponse> call, Response<GetAddToCardResponse> response) {
                GetAddToCardResponse getAddToCardResponse = response.body();
                if (getAddToCardResponse.getCode() == 200) {
                    Toasty.success(LikesElementsActivity.this, getString(R.string.add_to_card), Toast.LENGTH_LONG).show();
                    likesElementsAdapter.getProductsList().get(position).setCart(String.valueOf(CART_NOT_EMPTY));
                    likesElementsAdapter.notifyItemChanged(position);
                    PreferenceUtils.saveCountOfItemsBasket(LikesElementsActivity.this, Integer.parseInt(getAddToCardResponse.getItemsCount()));
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetAddToCardResponse> call, Throwable t) {
                Toasty.error(LikesElementsActivity.this, getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    public void prepareCartMap(int id) {
        cartPost = new HashMap<>();
        if (PreferenceUtils.getUserLogin(LikesElementsActivity.this)) {
            String token = PreferenceUtils.getUserToken(LikesElementsActivity.this);
            cartPost.put("product_id", String.valueOf(id));
            cartPost.put("token", token);
            cartPost.put("quantity", "1");
        } else if (PreferenceUtils.getCompanyLogin(LikesElementsActivity.this)) {
            String token = PreferenceUtils.getCompanyToken(LikesElementsActivity.this);
            cartPost.put("product_id", String.valueOf(id));
            cartPost.put("token", token);
            cartPost.put("quantity", "1");
        }
        reloadDialog.show();
    }

    private void showDialog() {
        reloadDialog = new Dialog(this);
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

}
