package com.zeidex.eldalel;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
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
import com.zeidex.eldalel.services.FilterAPI;
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

import static com.zeidex.eldalel.OffersFragment.CATEGORY_ID_INTENT_EXTRA_KEY;
import static com.zeidex.eldalel.OffersFragment.CATEGORY_NAME_INTENT_EXTRA;
import static com.zeidex.eldalel.OffersFragment.SUBCATEGORIES_INTENT_EXTRA_KEY;
import static com.zeidex.eldalel.ProductsFragment.FINISH_ACTIVITY_CODE;
import static com.zeidex.eldalel.ProductsFragment.SORT_ASC;
import static com.zeidex.eldalel.ProductsFragment.SORT_DATE_DESC_INDEX;
import static com.zeidex.eldalel.ProductsFragment.SORT_DESC;
import static com.zeidex.eldalel.SearchActivity.SEARCH_NAME_ARGUMENT;
import static com.zeidex.eldalel.adapters.CategoriesItemAdapter.SUBCATEGORY_ID_INTENT_EXTRA;
import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class OfferItemActivity extends BaseActivity implements CategoryItemAdapter.CategoryItemOperation, SubCategoriesAdapter.SubCategoryOperation {

    public static final String OFFERS = "offer";
    @BindView(R.id.offer_item_recycler_categories)
    RecyclerView offer_item_recycler_categories;

    @BindView(R.id.offer_category_item_recycler_list)
    RecyclerView offer_category_item_recycler_list;

    @BindView(R.id.offer_item_header_text)
    AppCompatTextView offerItemHeaderText;

    @BindView(R.id.offer_item_details_header)
    RelativeLayout offerItemDetailsHeader;

    @BindView(R.id.offer_no_items_layout)
    RelativeLayout offerNoItemsLayout;

    @BindView(R.id.offer_category_text_label_count)
    AppCompatTextView labelCountText;

    @BindView(R.id.offer_descendant_items_category)
    AppCompatImageView offer_descendant_items_category;

    @BindView(R.id.offer_search_items_category)
    AppCompatImageView offer_search_items_category;

    @BindView(R.id.offer_item_search)
    SearchView offer_item_search;

    CategoryItemAdapter productsAdapter;
    SubCategoriesAdapter subCategoriesAdapter;

    int position_detail;

    Dialog reloadDialog;
    String token = "";
    private List<Subcategory> subcategories;

    Map<String, String> cartPost;
    Map<String, String> likePost;
    private ArrayList<ProductsCategory> productsCategory;
    private Map<String, Object> filterMap;
    private PopupMenu dropDownMenu;
    private int subcategoryId;
    private int categoryId;


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

    boolean is_offered;

    public void findViews() {
       is_offered =  getIntent().getBooleanExtra("is_offered", false);
        if (PreferenceUtils.getCompanyLogin(this)) {
            token = PreferenceUtils.getCompanyToken(this);
        } else if (PreferenceUtils.getUserLogin(this)) {
            token = PreferenceUtils.getUserToken(this);
        }

        offer_item_search.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(OfferItemActivity.this, SearchActivity.class);
                intent.putExtra(SEARCH_NAME_ARGUMENT, query);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                offer_item_search.onActionViewCollapsed(); //to close the searchview
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        filterMap = new HashMap<>();
        if (is_offered) {
            filterMap.put("status", OFFERS);
        }
        dropDownMenu = new PopupMenu(this, offer_descendant_items_category);
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
        offerItemHeaderText.setText(getIntent().getStringExtra(CATEGORY_NAME_INTENT_EXTRA));
        subcategories = getIntent().getParcelableArrayListExtra(SUBCATEGORIES_INTENT_EXTRA_KEY);
        if (subcategories != null && subcategories.size() > 0) {
            subCategoriesAdapter = new SubCategoriesAdapter(this, subcategories, true);
            subCategoriesAdapter.setSubCategoryOperation(this);
            offer_item_recycler_categories.setAdapter(subCategoriesAdapter);
            subcategoryId = subcategories.get(0).getId();
            categoryId = getIntent().getIntExtra(CATEGORY_ID_INTENT_EXTRA_KEY, -1);
            filterMap.put("subcat_id", subcategoryId);
            getSubcategoryProducts(subcategoryId);
        } else {
            categoryId = getIntent().getIntExtra(CATEGORY_ID_INTENT_EXTRA_KEY, -1);
            filterMap.put("cat_id", categoryId);
            getCategoryProducts(categoryId);
        }

    }
    String status = "";
    private void getCategoryProducts(int categoryId) {
        reloadDialog.show();
        OffersAPI offersAPI = APIClient.getClient(SERVER_API_TEST).create(OffersAPI.class);

        if (is_offered){
            status = OFFERS;
        }else{
            status = "";
        }
        offersAPI.getOffersProductsFromCategories(status, categoryId, token).enqueue(new Callback<GetProducts>() {
            @Override
            public void onResponse(Call<GetProducts> call, Response<GetProducts> response) {
                if (response.body() != null) {
                    int code = response.body().getCode();
                    if (code == 200) {
                        List<GetProducts.Data> offers = response.body().getProducts().getDataAll();
                        if (offers.size() > 0) {
                            labelCountText.setText(String.valueOf(offers.size()));
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

    public void getSubcategoryProducts(int subcategoryId) {
        reloadDialog.show();
        if (is_offered){
            status = OFFERS;
        }else{
            status = "";
        }
        OffersAPI offersAPI = APIClient.getClient(SERVER_API_TEST).create(OffersAPI.class);
        offersAPI.getOffersProducts(status, subcategoryId, token).enqueue(new Callback<GetProducts>() {
            @Override
            public void onResponse(Call<GetProducts> call, Response<GetProducts> response) {
                if (response.body() != null) {
                    int code = response.body().getCode();
                    if (code == 200) {
                        List<GetProducts.Data> offers = response.body().getProducts().getDataAll();
                        if (offers.size() > 0) {
                            labelCountText.setText(String.valueOf(offers.size()));
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

    private void showEmptyView() {
        offer_category_item_recycler_list.setVisibility(View.GONE);
        offerItemDetailsHeader.setVisibility(View.INVISIBLE);
        offerNoItemsLayout.setVisibility(View.VISIBLE);
    }

    private void showRecycler() {
        offer_category_item_recycler_list.setVisibility(View.VISIBLE);
        offerItemDetailsHeader.setVisibility(View.VISIBLE);
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
    public void onClickSubCategory(int subcategoryId, String subcategoryName , int pos) {
        this.subcategoryId = subcategoryId;
        filterMap.put("subcat_id", subcategoryId);
        getSubcategoryProducts(subcategoryId);
    }

    @Override
    public void onClickSubCategoryWithSubSub(ArrayList<Subsubcategory> subsubcategories, String subcategoryName, int subcategoryId , int pos) {

    }

    @Override
    public void onClickProduct(int id, int pos) {
        position_detail = pos;
        startActivityForResult(new Intent(this, DetailItemFragment.class).putExtra("id", id).putExtra("similar_products", productsCategory).putExtra("getLike", productsCategory.get(pos).getLike()).putExtra("pos", pos), 1111);
        Animatoo.animateSwipeLeft(this);
    }

    @Override
    public void onClickProductLike(int id) {
        reloadDialog.show();
        prepareLikeMap(id);
        AddToFavouriteApi addToFavouriteApi = APIClient.getClient(SERVER_API_TEST).create(AddToFavouriteApi.class);
        Call<GetAddToFavouriteResponse> getAddToFavouriteResponseCall;
        if (PreferenceUtils.getCompanyLogin(OfferItemActivity.this)) {
            getAddToFavouriteResponseCall = addToFavouriteApi.getAddToFavouritecompany(likePost);
        }else {
            getAddToFavouriteResponseCall = addToFavouriteApi.getAddToFavourite(likePost);
        }
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

        Call<GetAddToCardResponse> getAddToCardResponseCall;
        if (PreferenceUtils.getCompanyLogin(OfferItemActivity.this)) {
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
                    Toasty.success(OfferItemActivity.this, getString(R.string.add_to_card), Toast.LENGTH_LONG).show();
                    productsAdapter.getProductsList().get(position).setCart("0");
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

        if (requestCode == FINISH_ACTIVITY_CODE && data != null) {
            boolean shouldFinishActivity = data.getBooleanExtra("should_finish_activity", false);
            if (shouldFinishActivity) finish();
        }
//        } else if (requestCode == FILTER_REQUEST_CODE && data != null) {
//            offerItemHeaderText.setText("");
//            int filterCategoryId = data.getIntExtra("filter_category_id", -1);
//            int filterPriceFrom = data.getIntExtra("filter_price_from", -1);
//            int filterPriceTo = data.getIntExtra("filter_price_to", -1);
//            filterMap = new HashMap<>();
//            if (filterCategoryId != -1) filterMap.put("cat_id", filterCategoryId);
//            if (filterPriceFrom != -1) filterMap.put("from", filterPriceFrom);
//            if (filterPriceTo != -1) filterMap.put("to", filterPriceTo);
//            filterResults();
//        }
    }

    @OnClick(R.id.offer_descendant_items_category)
    void sortResults() {
        dropDownMenu.show();
    }

    @OnClick(R.id.offer_search_items_category)
    void navigateToFilterActivity() {
        Intent intent = new Intent(this, FilterActivity.class);
        intent.putExtra("has_offer", true);
        intent.putExtra(CATEGORY_ID_INTENT_EXTRA_KEY, categoryId);
        intent.putExtra(SUBCATEGORY_ID_INTENT_EXTRA, subcategoryId);
        startActivityForResult(intent, FINISH_ACTIVITY_CODE);
        Animatoo.animateSwipeLeft(this);
    }

}