package com.zeidex.eldalel;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.phelat.navigationresult.BundleFragment;
import com.zeidex.eldalel.adapters.LikesElementsAdapter;
import com.zeidex.eldalel.models.ProductsCategory;
import com.zeidex.eldalel.response.DeleteFavoriteResponse;
import com.zeidex.eldalel.response.GetAddToCardResponse;
import com.zeidex.eldalel.response.GetFavorites;
import com.zeidex.eldalel.services.AddToCardApi;
import com.zeidex.eldalel.services.FavoritesAPI;
import com.zeidex.eldalel.utils.APIClient;
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

import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class LikesElementsFragment extends BundleFragment implements LikesElementsAdapter.LikesOperation {
    @BindView(R.id.likes_item_recycler_list)
    RecyclerView likes_item_recycler_list;
    @BindView(R.id.likes_text_label_count)
    AppCompatTextView likes_text_label_count;
    @BindView(R.id.likes_text_label_text)
    AppCompatTextView likes_text_label_text;
    @BindView(R.id.favorites_no_items_layout)
    RelativeLayout noItemsLayout;

    LikesElementsAdapter likesElementsAdapter;

    String token = "";
    Dialog reloadDialog;
    private ArrayList<ProductsCategory> products;
    Map<String, String> cartPost;
    private DetailItemFragment mDetailItemFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_elements_likes, container, false);
        ButterKnife.bind(this, view);
        initializeRecycler();
        if (products != null) {
            updateUI(products);
        } else {
            findViews();
        }
        return view;
    }

    private void updateUI(ArrayList<ProductsCategory> products) {
        if (products.size() > 0) {
            noItemsLayout.setVisibility(View.GONE);
            likes_text_label_count.setText(String.valueOf(products.size()));
            likes_text_label_text.setVisibility(View.VISIBLE);
            likesElementsAdapter.setProductList(products);
            likesElementsAdapter.notifyDataSetChanged();
        } else {
            noItemsLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavHostFragment.findNavController(LikesElementsFragment.this).popBackStack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_elements_likes);
//        ButterKnife.bind(this);
//        initializeRecycler();
//        findViews();
//    }

    private void findViews() {
        if (PreferenceUtils.getCompanyLogin(getContext())) {
            token = PreferenceUtils.getCompanyToken(getContext());
        } else if (PreferenceUtils.getUserLogin(getContext())) {
            token = PreferenceUtils.getUserToken(getContext());
        } else {//ask user to sign in first
        }

        showDialog();
        onLoadPage();
    }

    @OnClick(R.id.item_favourite_back)
    public void onback() {
        NavHostFragment.findNavController(this).navigateUp();
    }

    private void onLoadPage() {
        reloadDialog.show();
        FavoritesAPI favoritesAPI = APIClient.getClient(SERVER_API_TEST).create(FavoritesAPI.class);
        Call<GetFavorites> getFavoritesCall;
        if (PreferenceUtils.getCompanyLogin(getContext())) {
            getFavoritesCall = favoritesAPI.getAllFavoritescompany(token);
        } else {
            getFavoritesCall = favoritesAPI.getAllFavorites(token);
        }
        getFavoritesCall.enqueue(new Callback<GetFavorites>() {
            @Override
            public void onResponse(Call<GetFavorites> call, Response<GetFavorites> response) {
                if (response.body() != null) {
                    int code = response.body().getCode();
                    if (code == 200) {
                        List<GetFavorites.Favorite> favorites = response.body().getData().getFavorites();
                        if (favorites != null && favorites.size() > 0) {
                            noItemsLayout.setVisibility(View.GONE);
                            likes_text_label_count.setText(String.valueOf(favorites.size()));
                            likes_text_label_text.setVisibility(View.VISIBLE);
                            products = getProductsFromResponse(favorites);
                            likesElementsAdapter.setProductList(products);
                            likesElementsAdapter.notifyDataSetChanged();
                        } else {
                            noItemsLayout.setVisibility(View.VISIBLE);
                        }
                    }
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetFavorites> call, Throwable t) {
                Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    public void initializeRecycler() {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        likes_item_recycler_list.setLayoutManager(mLayoutManager);
        likes_item_recycler_list.setItemAnimator(new DefaultItemAnimator());

        int spanCount = 2; // 3 columns
        int spacing = 20; // 50px
        boolean includeEdge = true;
        likes_item_recycler_list.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        likesElementsAdapter = new LikesElementsAdapter(getContext());
        likesElementsAdapter.setLikesOperation(this);
        likes_item_recycler_list.setAdapter(likesElementsAdapter);
    }

    private ArrayList<ProductsCategory> getProductsFromResponse(List<GetFavorites.Favorite> productsResponse) {
        ArrayList<ProductsCategory> products = new ArrayList<>();

        Locale locale = ChangeLang.getLocale(getResources());
        String loo = locale.getLanguage();

        if (loo.equalsIgnoreCase("ar")) {
            for (int j = 0; j < productsResponse.size(); j++) { // product loop

                GetFavorites.Favorite currentProductResponse = productsResponse.get(j);
                String arr[] = currentProductResponse.getProduct().getNameAr().split(" ", 2); // get first word
                String firstWord = arr[0];

                Double price = currentProductResponse.getProduct().getPrice();
                String priceString = String.valueOf(price);
                Integer discount = currentProductResponse.getProduct().getDiscount();
                String discountString = discount != null ? String.valueOf(discount) : null;
                Double priceBefore = currentProductResponse.getProduct().getOldPrice();
                String priceBeforeString = priceBefore != null ? String.valueOf(priceBefore) : null;

                if (currentProductResponse.getProduct().getPhotos().size() == 0) {
                    products.add(new ProductsCategory(String.valueOf(currentProductResponse.getProduct().getId()), "",
                            discountString, firstWord, currentProductResponse.getProduct().getNameAr(),
                            priceString, priceBeforeString,
                            "", currentProductResponse.getProduct().getCart(),
                            currentProductResponse.getProduct().getAvailableQuantity() + ""));
                } else {
                    products.add(new ProductsCategory(String.valueOf(currentProductResponse.getProduct().getId()),
                            currentProductResponse.getProduct().getPhotos().get(0).getFilename(),
                            discountString, firstWord, currentProductResponse.getProduct().getNameAr(),
                            priceString, priceBeforeString,
                            "", currentProductResponse.getProduct().getCart(),
                            currentProductResponse.getProduct().getAvailableQuantity() + ""));
                }
            }
        } else {
            for (int j = 0; j < productsResponse.size(); j++) { // product loop

                GetFavorites.Favorite currentProductResponse = productsResponse.get(j);
                String arr[] = currentProductResponse.getProduct().getName().split(" ", 2); // get first word
                String firstWord = arr[0];

                Integer discount = currentProductResponse.getProduct().getDiscount();
                String discountString = discount != null ? String.valueOf(discount) : null;
                Double priceBefore = currentProductResponse.getProduct().getOldPrice();
                String priceBeforeString = priceBefore != null ? String.valueOf(priceBefore) : null;

                if (currentProductResponse.getProduct().getPhotos().size() == 0) {
                    products.add(new ProductsCategory(String.valueOf(currentProductResponse.getProduct().getId()), "",
                            discountString, firstWord, currentProductResponse.getProduct().getName(),
                            String.valueOf(currentProductResponse.getProduct().getPrice()), priceBeforeString,
                            "", currentProductResponse.getProduct().getCart(),
                            currentProductResponse.getProduct().getAvailableQuantity() + ""));

                } else {
                    products.add(new ProductsCategory(String.valueOf(currentProductResponse.getProduct().getId()),
                            currentProductResponse.getProduct().getPhotos().get(0).getFilename(),
                            discountString, firstWord, currentProductResponse.getProduct().getName(),
                            String.valueOf(currentProductResponse.getProduct().getPrice()), priceBeforeString,
                            "", currentProductResponse.getProduct().getCart(),
                            currentProductResponse.getProduct().getAvailableQuantity() + ""));
                }
            }
        }

        return products;
    }


    @Override
    public void onClickProduct(int id, int pos) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putParcelableArrayList("similar_products", products);
        bundle.putString("getLike", "1");
        bundle.putInt("pos", pos);
        NavHostFragment.findNavController(this).navigate(R.id.action_likesElementsFragment_to_detailItemActivity, bundle);
//        mDetailItemFragment = new DetailItemFragment(null, bundle);
//        getSupportFragmentManager().beginTransaction().replace(R.id.container, mDetailItemFragment).commit();
//        startActivityForResult(new Intent(this, DetailItemFragment.class).
//        Animatoo.animateSwipeLeft(this);
    }

    @Override
    public void onAddToCart(int id, int position) {
        prepareCartMap(id);
        AddToCardApi addToCardApi = APIClient.getClient(SERVER_API_TEST).create(AddToCardApi.class);
        Call<GetAddToCardResponse> getAddToCardResponseCall;
        if (PreferenceUtils.getCompanyLogin(getContext())) {
            cartPost.put("language", "arabic");
            getAddToCardResponseCall = addToCardApi.getAddToCartcompany(cartPost);
        } else {
            getAddToCardResponseCall = addToCardApi.getAddToCart(cartPost);
        }
        getAddToCardResponseCall.enqueue(new Callback<GetAddToCardResponse>() {
            @Override
            public void onResponse(Call<GetAddToCardResponse> call, Response<GetAddToCardResponse> response) {
                GetAddToCardResponse getAddToCardResponse = response.body();
                if (getAddToCardResponse.getCode() == 200) {
                    Toasty.success(getContext(), getString(R.string.add_to_card), Toast.LENGTH_LONG).show();
                    likesElementsAdapter.getProductsList().get(position).setCart("0");
                    likesElementsAdapter.notifyItemChanged(position);
                    PreferenceUtils.saveCountOfItemsBasket(getContext(), Integer.parseInt(getAddToCardResponse.getItemsCount()));
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetAddToCardResponse> call, Throwable t) {
                Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    @Override
    public void onRemoveFavorite(int id, int position) {
        reloadDialog.show();
        FavoritesAPI favoritesAPI = APIClient.getClient(SERVER_API_TEST).create(FavoritesAPI.class);
        Call<DeleteFavoriteResponse> getFavoritesCall;
        if (PreferenceUtils.getCompanyLogin(getContext())) {
            getFavoritesCall = favoritesAPI.deleteFavoritecompany(id, token);
        } else {
            getFavoritesCall = favoritesAPI.deleteFavorite(id, token);
        }
        getFavoritesCall.enqueue(new Callback<DeleteFavoriteResponse>() {
            @Override
            public void onResponse(Call<DeleteFavoriteResponse> call, Response<DeleteFavoriteResponse> response) {
                DeleteFavoriteResponse deleteFavoriteResponse = response.body();
                if (deleteFavoriteResponse != null) {
                    int code = deleteFavoriteResponse.getCode();
                    if (code == 200) {
                        Toasty.success(getContext(), getString(R.string.remove_fav), Toast.LENGTH_LONG).show();
                        likesElementsAdapter.getProductsList().remove(position);
                        likesElementsAdapter.notifyItemRemoved(position);
                        int remainingProductsCount = likesElementsAdapter.getProductsList().size();
                        if (remainingProductsCount > 0)
                            likes_text_label_count.setText(String.valueOf(remainingProductsCount));
                        else {
                            likes_text_label_count.setVisibility(View.GONE);
                            likes_text_label_text.setVisibility(View.GONE);
                            noItemsLayout.setVisibility(View.VISIBLE);
                        }
                    }
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<DeleteFavoriteResponse> call, Throwable t) {
                Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    public void prepareCartMap(int id) {
        cartPost = new HashMap<>();
        if (PreferenceUtils.getUserLogin(getContext())) {
            String token = PreferenceUtils.getUserToken(getContext());
            cartPost.put("product_id", String.valueOf(id));
            cartPost.put("token", token);
            cartPost.put("quantity", "1");
        } else if (PreferenceUtils.getCompanyLogin(getContext())) {
            String token = PreferenceUtils.getCompanyToken(getContext());
            cartPost.put("product_id", String.valueOf(id));
            cartPost.put("token", token);
            cartPost.put("quantity", "1");
        }
        reloadDialog.show();
    }

    private void showDialog() {
        reloadDialog = new Dialog(getContext());
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

}
