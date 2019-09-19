package com.zeidex.eldalel;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.adapters.LikesElementsAdapter;
import com.zeidex.eldalel.models.GetFavorites;
import com.zeidex.eldalel.models.ProductsCategory;
import com.zeidex.eldalel.response.GetProducts;
import com.zeidex.eldalel.services.FavoritesAPI;
import com.zeidex.eldalel.utils.APIClient;
import com.zeidex.eldalel.utils.ChangeLang;
import com.zeidex.eldalel.utils.GridSpacingItemDecoration;
import com.zeidex.eldalel.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class LikesElementsActivity extends BaseActivity implements LikesElementsAdapter.LikesOperation {
    @BindView(R.id.likes_item_recycler_list)
    RecyclerView likes_item_recycler_list;
    LikesElementsAdapter likesElementsAdapter;

    String token = "";
    Dialog reloadDialog;

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
                List<GetFavorites.Favorite> favorites = response.body().getData().getFavorites();
                for(GetFavorites.Favorite favorite : favorites){

                }
            }

            @Override
            public void onFailure(Call<GetFavorites> call, Throwable t) {

            }
        });
    }

    public void initializeRecycler(){
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

    private ArrayList<ProductsCategory> getProductsFromResponse(List<GetFavorites.Product> productsResponse) {
        ArrayList<ProductsCategory> products = new ArrayList<>();

        Locale locale = ChangeLang.getLocale(getResources());
        String loo = locale.getLanguage();

        if (loo.equalsIgnoreCase("ar")) {
            for (int j = 0; j < productsResponse.size(); j++) { // product loop

                GetFavorites.Product currentProductResponse = productsResponse.get(j);
                String arr[] = currentProductResponse.getNameAr().split(" ", 2); // get first word
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


    @Override
    public void onClickProduct(int id, int pos) {

    }

    @Override
    public void onAddToCart(int id, int position) {

    }

    private void showDialog() {
        reloadDialog = new Dialog(this);
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

}
