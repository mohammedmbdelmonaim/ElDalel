package com.zeidex.eldalel;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.adapters.CategoryItemAdapter;
import com.zeidex.eldalel.models.ProductsCategory;
import com.zeidex.eldalel.response.GetAddToCardResponse;
import com.zeidex.eldalel.response.GetAddToFavouriteResponse;
import com.zeidex.eldalel.response.GetProducts;
import com.zeidex.eldalel.services.AddToCardApi;
import com.zeidex.eldalel.services.AddToFavouriteApi;
import com.zeidex.eldalel.services.FilterAPI;
import com.zeidex.eldalel.services.NewArrivalsAPI;
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

import static com.zeidex.eldalel.NewArrivalsFragment.NEW_ARRIVAL;
import static com.zeidex.eldalel.OffersFragment.CATEGORY_ID_INTENT_EXTRA_KEY;
import static com.zeidex.eldalel.ProductsFragment.FINISH_ACTIVITY_CODE;
import static com.zeidex.eldalel.ProductsFragment.SORT_ASC;
import static com.zeidex.eldalel.ProductsFragment.SORT_DATE_DESC_INDEX;
import static com.zeidex.eldalel.ProductsFragment.SORT_DESC;
import static com.zeidex.eldalel.adapters.CategoriesItemAdapter.SUBCATEGORY_ID_INTENT_EXTRA;
import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewArrivalProductsFragment extends Fragment implements /*CategoryItemAdapter.CategoryOperation, */CategoryItemAdapter.CategoryItemOperation {
    @BindView(R.id.category_item_recycler_list)
    RecyclerView category_item_recycler_list;
    @BindView(R.id.categories_no_items_layout)
    RelativeLayout noItemsLayout;
    @BindView(R.id.category_item_details_header)
    RelativeLayout itemDetailsHeader;
    @BindView(R.id.category_text_label_count)
    AppCompatTextView labelCountText;
    @BindView(R.id.descendant_items_category)
    AppCompatImageView descendantItemsCategoryImage;
    @BindView(R.id.search_items_category)
    AppCompatImageView searchItemsCategoryImage;

    Dialog reloadDialog;
    String token = "";
    int position_detail;

    Map<String, String> cartPost;
    Map<String, String> likePost;
    private Map<String, Object> filterMap;
    private PopupMenu dropDownMenu;

    CategoryItemAdapter productsAdapter;
    //    private int subcategoryId;
    private ArrayList<ProductsCategory> productsCategory;
    private int categoryId;
    private int subcategoryId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_arrival_products, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        findViews();
    }

    private void findViews() {
        if (PreferenceUtils.getCompanyLogin(getActivity())) {
            token = PreferenceUtils.getCompanyToken(getActivity());
        } else if (PreferenceUtils.getUserLogin(getActivity())) {
            token = PreferenceUtils.getUserToken(getActivity());
        }

        filterMap = new HashMap<>();
        filterMap.put("status", NEW_ARRIVAL);
        dropDownMenu = new PopupMenu(getActivity(), descendantItemsCategoryImage);
        dropDownMenu.getMenuInflater().inflate(R.menu.menu_sort_dropdown, dropDownMenu.getMenu());
        dropDownMenu.getMenu().getItem(SORT_DATE_DESC_INDEX).setChecked(true);

        dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case (R.id.sort_price_ascend):
                        filterMap.put("order_price", SORT_ASC);
                        filterMap.remove("order_date");
                        break;

                    case (R.id.sort_price_descend):
                        filterMap.put("order_price", SORT_DESC);
                        filterMap.remove("order_date");
                        break;

                    case (R.id.sort_date_ascend):
                        filterMap.put("order_date", SORT_ASC);
                        filterMap.remove("order_price");
                        break;

                    case (R.id.sort_date_descend):
                        filterMap.put("order_date", SORT_DESC);
                        filterMap.remove("order_price");
                        break;
                }
                filterResults();
                return true;
            }
        });
        showDialog();
        onLoadPage();
    }

    private void onLoadPage() {
        productsCategory = new ArrayList<>();
        categoryId = getArguments().getInt(CATEGORY_ID_INTENT_EXTRA_KEY);
        subcategoryId = getArguments().getInt(SUBCATEGORY_ID_INTENT_EXTRA, -1);
        if (subcategoryId != -1) {
            getProductsFromSubcategory(subcategoryId);

        } else {
            getProductsFromCategory(categoryId);
        }
    }

    private void getProductsFromCategory(int categoryId) {
        reloadDialog.show();
        NewArrivalsAPI newArrivalsAPI = APIClient.getClient(SERVER_API_TEST).create(NewArrivalsAPI.class);
        newArrivalsAPI.getNewArrivalsProductsFromCategory(categoryId, NEW_ARRIVAL, token).enqueue(new Callback<GetProducts>() {
            @Override
            public void onResponse(Call<GetProducts> call, Response<GetProducts> response) {
                if (response.body() != null) {
                    int code = response.body().getCode();
                    if (code == 200) {
                        List<GetProducts.Data> products = response.body().getProducts().getDataAll();
                        if (products.size() > 0) {
                            productsCategory = getProductsFromResponse(products);
                            initializeRecycler(productsCategory);
                        } else {
                            showEmptyView();
                        }
                    }
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetProducts> call, Throwable t) {
                Toasty.error(getActivity(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    private void getProductsFromSubcategory(int subcategoryId) {
        reloadDialog.show();
        NewArrivalsAPI newArrivalsAPI = APIClient.getClient(SERVER_API_TEST).create(NewArrivalsAPI.class);
        newArrivalsAPI.getNewArrivalsProductsFromSubCategory(subcategoryId, NEW_ARRIVAL, token).enqueue(new Callback<GetProducts>() {
            @Override
            public void onResponse(Call<GetProducts> call, Response<GetProducts> response) {
                if (response.body() != null) {
                    int code = response.body().getCode();
                    if (code == 200) {
                        List<GetProducts.Data> products = response.body().getProducts().getDataAll();
                        if (products.size() > 0) {
                            productsCategory = getProductsFromResponse(products);
                            initializeRecycler(productsCategory);
                        } else {
                            showEmptyView();
                        }
                    }
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetProducts> call, Throwable t) {
                Toasty.error(getActivity(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    private void filterResults() {
        reloadDialog.show();
        FilterAPI filterAPI = APIClient.getClient(SERVER_API_TEST).create(FilterAPI.class);
        filterAPI.getProductsFromFilter(filterMap, token).enqueue(new Callback<GetProducts>() {
            @Override
            public void onResponse(Call<GetProducts> call, Response<GetProducts> response) {
                if (response.body() != null) {
                    int code = response.body().getCode();
                    if (code == 200) {
                        List<GetProducts.Data> products = response.body().getProducts().getDataAll();
                        if (products.size() > 0) {
                            productsCategory = getProductsFromResponse(products);
                            productsAdapter.setProductsList(productsCategory);
                            productsAdapter.notifyDataSetChanged();
                            labelCountText.setText(String.valueOf(products.size()));
                        } else {
                            showEmptyView();
                        }
                    }
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetProducts> call, Throwable t) {
                Toasty.error(getActivity(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    private void showEmptyView() {
        noItemsLayout.setVisibility(View.VISIBLE);
        category_item_recycler_list.setVisibility(View.GONE);
        itemDetailsHeader.setVisibility(View.INVISIBLE);
    }

    private ArrayList<ProductsCategory> getProductsFromResponse(List<GetProducts.Data> productsResponse) {
        ArrayList<ProductsCategory> products = new ArrayList<>();

        Locale locale = ChangeLang.getLocale(getContext().getResources());
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
                            currentProductResponse.getFavorite(), currentProductResponse.getCart(),
                            currentProductResponse.getAvailable_quantity()));
                } else {
                    products.add(new ProductsCategory(currentProductResponse.getId(), currentProductResponse.getPhotos().get(0).getFilename(),
                            currentProductResponse.getDiscount(), firstWord, currentProductResponse.getName_ar(),
                            currentProductResponse.getPrice(), currentProductResponse.getOld_price(),
                            currentProductResponse.getFavorite(), currentProductResponse.getCart(), currentProductResponse.getAvailable_quantity()));
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
                            currentProductResponse.getFavorite(), currentProductResponse.getCart(),
                            currentProductResponse.getAvailable_quantity()));
                } else {
                    products.add(new ProductsCategory(currentProductResponse.getId(), currentProductResponse.getPhotos().get(0).getFilename(),
                            currentProductResponse.getDiscount(), firstWord, currentProductResponse.getName(),
                            currentProductResponse.getPrice(), currentProductResponse.getOld_price(),
                            currentProductResponse.getFavorite(), currentProductResponse.getCart(), currentProductResponse.getAvailable_quantity()));
                }
            }
        }

        return products;
    }

    public void initializeRecycler(List<ProductsCategory> products) {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        category_item_recycler_list.setLayoutManager(mLayoutManager);
        category_item_recycler_list.setItemAnimator(new DefaultItemAnimator());

        int spanCount = 2; // 3 columns
        int spacing = 20; // 50px
        boolean includeEdge = true;
        category_item_recycler_list.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        productsAdapter = new CategoryItemAdapter(getActivity(), products);
        productsAdapter.setCategoryOperation(NewArrivalProductsFragment.this);
        category_item_recycler_list.setAdapter(productsAdapter);

        labelCountText.setText(String.valueOf(products.size()));
        itemDetailsHeader.setVisibility(View.VISIBLE);
    }

    private void showDialog() {
        reloadDialog = new Dialog(getActivity());
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void prepareCartMap(int id) {
        cartPost = new HashMap<>();
        if (PreferenceUtils.getUserLogin(getActivity())) {
            String token = PreferenceUtils.getUserToken(getActivity());
            cartPost.put("product_id", String.valueOf(id));
            cartPost.put("token", token);
            cartPost.put("quantity", "1");
        } else if (PreferenceUtils.getCompanyLogin(getActivity())) {
            String token = PreferenceUtils.getCompanyToken(getActivity());
            cartPost.put("product_id", String.valueOf(id));
            cartPost.put("token", token);
            cartPost.put("quantity", "1");
        }
        reloadDialog.show();
    }

    private void prepareLikeMap(int id) {
        likePost = new HashMap<>();
        if (PreferenceUtils.getUserLogin(getActivity())) {
            String token = PreferenceUtils.getUserToken(getActivity());
            likePost.put("product_id", String.valueOf(id));
            likePost.put("token", token);
        } else if (PreferenceUtils.getCompanyLogin(getActivity())) {
            String token = PreferenceUtils.getCompanyToken(getActivity());
            likePost.put("product_id", String.valueOf(id));
            likePost.put("token", token);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1111) {
            if (data.getBooleanExtra("databack", false)) {
                    productsCategory.get(position_detail).setLike("1");
                    productsAdapter.notifyItemChanged(position_detail);
            }
            if (data.getBooleanExtra("added_to_cart", false)) {
                productsCategory.get(position_detail).setCart("0");
                productsAdapter.notifyItemChanged(position_detail);
            }
        }

        if(requestCode == FINISH_ACTIVITY_CODE && data != null){
            boolean shouldFinishActivity = data.getBooleanExtra("should_finish_activity", false);
            if(shouldFinishActivity) getActivity().finish();
        }
    }


    @OnClick(R.id.descendant_items_category)
    void sortResults() {
        dropDownMenu.show();
    }

    @OnClick(R.id.search_items_category)
    void navigateToFilterActivity() {
        Intent intent = new Intent(getActivity(), FilterActivity.class);
        intent.putExtra("is_new_arrival", true);
        intent.putExtra(CATEGORY_ID_INTENT_EXTRA_KEY, categoryId);
        intent.putExtra(SUBCATEGORY_ID_INTENT_EXTRA, subcategoryId);
        startActivityForResult(intent, FINISH_ACTIVITY_CODE);
        Animatoo.animateSwipeLeft(getActivity());
    }

    @Override
    public void onClickProduct(int id, int pos) {
        position_detail = pos;
        startActivityForResult(new Intent(getActivity(), DetailItemActivity.class).putExtra("id", id).putExtra("similar_products", productsCategory).putExtra("getLike", productsCategory.get(pos).getLike()).putExtra("pos", pos), 1111);
        Animatoo.animateSwipeLeft(getActivity());
    }

    @Override
    public void onClickProductLike(int id) {
        reloadDialog.show();
        prepareLikeMap(id);
        AddToFavouriteApi addToFavouriteApi = APIClient.getClient(SERVER_API_TEST).create(AddToFavouriteApi.class);
        Call<GetAddToFavouriteResponse> getAddToFavouriteResponseCall;
        if (PreferenceUtils.getCompanyLogin(getActivity())) {
            getAddToFavouriteResponseCall = addToFavouriteApi.getAddToFavouritecompany(likePost);
        }else {
            getAddToFavouriteResponseCall = addToFavouriteApi.getAddToFavourite(likePost);
        }
        getAddToFavouriteResponseCall.enqueue(new Callback<GetAddToFavouriteResponse>() {
            @Override
            public void onResponse(Call<GetAddToFavouriteResponse> call, Response<GetAddToFavouriteResponse> response) {
                GetAddToFavouriteResponse getAddToFavouriteResponse = response.body();
                if (Integer.parseInt(getAddToFavouriteResponse.getCode()) == 200) {
                    Toasty.success(getActivity(), getString(R.string.add_to_favourites), Toast.LENGTH_LONG).show();
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetAddToFavouriteResponse> call, Throwable t) {
                Toasty.error(getActivity(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    @Override
    public void onAddToProductCart(int id, int position) {
        prepareCartMap(id);
        AddToCardApi addToCardApi = APIClient.getClient(SERVER_API_TEST).create(AddToCardApi.class);
        Call<GetAddToCardResponse> getAddToCardResponseCall;
        if (PreferenceUtils.getCompanyLogin(getActivity())) {
            cartPost.put("language" , "arabic");
            getAddToCardResponseCall = addToCardApi.getAddToCartcompany(cartPost);
        }else {
            getAddToCardResponseCall = addToCardApi.getAddToCart(cartPost);
        }
        getAddToCardResponseCall.enqueue(new Callback<GetAddToCardResponse>() {
            @Override
            public void onResponse(Call<GetAddToCardResponse> call, Response<GetAddToCardResponse> response) {
                GetAddToCardResponse getAddToCardResponse = response.body();
                if (getAddToCardResponse.getCode() == 200) {
                    Toasty.success(getActivity(), getString(R.string.add_to_card), Toast.LENGTH_LONG).show();
                    productsAdapter.getProductsList().get(position).setCart("0");
                    productsAdapter.notifyItemChanged(position);
                    PreferenceUtils.saveCountOfItemsBasket(getActivity(), Integer.parseInt(getAddToCardResponse.getItemsCount()));
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetAddToCardResponse> call, Throwable t) {
                Toasty.error(getActivity(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }
}
