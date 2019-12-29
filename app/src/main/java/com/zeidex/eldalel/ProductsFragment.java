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
import com.zeidex.eldalel.services.ProductsAPI;
import com.zeidex.eldalel.services.SearchProductAPI;
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

import static com.zeidex.eldalel.FilterActivity.FILTER_CATEGORY_ID;
import static com.zeidex.eldalel.FilterActivity.FILTER_PRICE_FROM;
import static com.zeidex.eldalel.FilterActivity.FILTER_PRICE_TO;
import static com.zeidex.eldalel.FilterActivity.FILTER_SUBCATEGORY_ID;
import static com.zeidex.eldalel.OffersFragment.CATEGORY_ID_INTENT_EXTRA_KEY;
import static com.zeidex.eldalel.SearchActivity.FILTER_STATUS;
import static com.zeidex.eldalel.SearchActivity.SEARCH_NAME_ARGUMENT;
import static com.zeidex.eldalel.adapters.CategoriesItemAdapter.SUBCATEGORY_ID_INTENT_EXTRA;
import static com.zeidex.eldalel.adapters.CategoriesItemAdapter.SUB_SUBCATEGORY_ID_INTENT_EXTRA;
import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class ProductsFragment extends Fragment implements /*CategoryItemAdapter.CategoryOperation,*/  CategoryItemAdapter.CategoryItemOperation {
    public static final int FILTER_REQUEST_CODE = 500;
    public static final String SORT_ASC = "asc";
    public static final String SORT_DESC = "desc";
    public static final int SORT_DATE_DESC_INDEX = 0;
    public static final int FINISH_ACTIVITY_CODE = 458;
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

//    CategoryItemAdapter categoryAdapter;

    CategoryItemAdapter productsAdapter;
    private int subcategoryId;
    private int subsubcategoryId;
    Map<String, String> cartPost;
    Map<String, String> likePost;
    private ArrayList<ProductsCategory> allProductsCategory;
    private int categoryId;
    private int position_detail;
    private String searchName;
    private Map<String, Object> filterMap; //used to set filter or sort parameters
    private PopupMenu dropDownMenu;
    private int filterCategoryId;
    private int filterPriceFrom;
    private int filterPriceTo;
    private int filterSubcategoryId;
    private String filterStatus;
    private int currentPage = 1;
    private RecyclerView.LayoutManager mLayoutManager;
    private int productsPerLoad = 19;
    private boolean isLoadedBefore = false;
    private boolean shouldLoadMore = true;
    //This is used to differentiate between any "getproducts" process and sorting
    //because in case of sorting getproducts should be called before incrementing page number
    //as when clearing the list the listener on scroll will be triggered immediately
    private boolean shouldSort = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_category, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        allProductsCategory = new ArrayList<>();
        setupRecyclerPagination();
        findViews();
    }

    private void setupRecyclerPagination() {
        category_item_recycler_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //When the recycler hits the end,
                if (!recyclerView.canScrollVertically(1) && shouldLoadMore) {
                    if (!shouldSort) {
                        currentPage++;
                        onLoadPage();
                    } else {
                        filterResults();
                        currentPage++;
                    }

                }
            }
        });
    }

    private void findViews() {
        if (PreferenceUtils.getCompanyLogin(getContext())) {
            token = PreferenceUtils.getCompanyToken(getContext());
        } else if (PreferenceUtils.getUserLogin(getContext())) {
            token = PreferenceUtils.getUserToken(getContext());
        }

        categoryId = getArguments().getInt(CATEGORY_ID_INTENT_EXTRA_KEY, -1);
        subcategoryId = getArguments().getInt(SUBCATEGORY_ID_INTENT_EXTRA, -1);
        subsubcategoryId = getArguments().getInt(SUB_SUBCATEGORY_ID_INTENT_EXTRA, -1);
        searchName = getArguments().getString(SEARCH_NAME_ARGUMENT);

        filterCategoryId = getArguments().getInt(FILTER_CATEGORY_ID, -1);
        filterPriceFrom = getArguments().getInt(FILTER_PRICE_FROM, -1);
        filterPriceTo = getArguments().getInt(FILTER_PRICE_TO, -1);
        filterSubcategoryId = getArguments().getInt(FILTER_SUBCATEGORY_ID, -1);
        filterStatus = getArguments().getString(FILTER_STATUS);

        filterMap = new HashMap<>();

        dropDownMenu = new PopupMenu(getContext(), descendantItemsCategoryImage);
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
                if (productsAdapter != null) {
                    allProductsCategory.clear();
                    productsAdapter.clearList();
                    currentPage = 1;
                    isLoadedBefore = false;
                    shouldLoadMore = true;
                    shouldSort = true;
                }
//                filterResults();
                return true;
            }
        });

        showDialog();
        onLoadPage();
    }

    private void onLoadPage() {
        if (subsubcategoryId != -1) { //meaning there are subsubcategories
            getProductsFromSubSubCategory(subsubcategoryId);
            filterMap.put("sub_subcat_id", subsubcategoryId);
        } else if (subcategoryId != -1) {
            getProductsFromSubCategory(subcategoryId);
            filterMap.put("subcat_id", subcategoryId);
        } else if (categoryId != -1) {
            getProductsFromCategory(categoryId);
            filterMap.put("cat_id", categoryId);
        } else if (searchName != null) {
            getProductsFromSearch(searchName);
            filterMap.put("product_name", searchName);
        } else {
            if (filterCategoryId != -1) filterMap.put("cat_id", filterCategoryId);
            if (filterSubcategoryId != -1) filterMap.put("subcat_id", filterSubcategoryId);
            if (filterPriceFrom != -1) filterMap.put("from", filterPriceFrom);
            if (filterPriceTo != -1) filterMap.put("to", filterPriceTo);
            if (filterStatus != null) {
                filterMap.put(FILTER_STATUS, filterStatus);
            }
//            initializeRecycler(new ArrayList<>());
            filterResults();
        }
    }

    private void getProductsFromSearch(String searchName) {
        reloadDialog.show();
        SearchProductAPI searchAPI = APIClient.getClient(SERVER_API_TEST).create(SearchProductAPI.class);
        searchAPI.getProductsFromNameSearch(searchName, token, currentPage).enqueue(new Callback<GetProducts>() {
            @Override
            public void onResponse(Call<GetProducts> call, Response<GetProducts> response) {
                if (response.body() != null) {
                    int code = response.body().getCode();
                    if (code == 200) {
                        List<GetProducts.Data> products = response.body().getProducts().getDataAll();
                        if (products.size() > 0) {
                            ArrayList<ProductsCategory> productsCategory = getProductsFromResponse(products);
                            allProductsCategory.addAll(productsCategory);
                            initializeRecycler(productsCategory);
                        } else {
                            if (isLoadedBefore) shouldLoadMore = false;
                            else showEmptyView();
                        }
                    }
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetProducts> call, Throwable t) {
                Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    private void getProductsFromCategory(int categoryId) {
        reloadDialog.show();
        ProductsAPI productsAPI = APIClient.getClient(SERVER_API_TEST).create(ProductsAPI.class);
        productsAPI.getProductsFromCategory(categoryId, token, currentPage).enqueue(new Callback<GetProducts>() {
            @Override
            public void onResponse(Call<GetProducts> call, Response<GetProducts> response) {
                if (response.body() != null) {
                    int code = response.body().getCode();
                    if (code == 200) {
                        List<GetProducts.Data> products = response.body().getProducts().getDataAll();
                        if (products.size() > 0) {
                            ArrayList<ProductsCategory> productsCategory = getProductsFromResponse(products);
                            allProductsCategory.addAll(productsCategory);
                            initializeRecycler(productsCategory);
                        } else {
                            if (isLoadedBefore) shouldLoadMore = false;
                            else showEmptyView();
                        }
                    }
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetProducts> call, Throwable t) {
                Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    private void getProductsFromSubSubCategory(int subsubCategoryId) {
        reloadDialog.show();
        ProductsAPI productsAPI = APIClient.getClient(SERVER_API_TEST).create(ProductsAPI.class);
        productsAPI.getProductsFromSubSubCategory(subsubCategoryId, token, currentPage).enqueue(new Callback<GetProducts>() {
            @Override
            public void onResponse(Call<GetProducts> call, Response<GetProducts> response) {
                if (response.body() != null) {
                    int code = response.body().getCode();
                    if (code == 200) {
                        List<GetProducts.Data> products = response.body().getProducts().getDataAll();
                        if (products.size() > 0) {
                            ArrayList<ProductsCategory> productsCategory = getProductsFromResponse(products);
                            allProductsCategory.addAll(productsCategory);
                            initializeRecycler(productsCategory);
                        } else {
                            if (isLoadedBefore) shouldLoadMore = false;
                            else showEmptyView();
                        }
                    }
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetProducts> call, Throwable t) {
                Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    private void showEmptyView() {
        noItemsLayout.setVisibility(View.VISIBLE);
        category_item_recycler_list.setVisibility(View.GONE);
        itemDetailsHeader.setVisibility(View.GONE);
    }

    private void getProductsFromSubCategory(int subCategoryId) {
        reloadDialog.show();
        ProductsAPI productsAPI = APIClient.getClient(SERVER_API_TEST).create(ProductsAPI.class);
        productsAPI.getProductsFromSubcategory(subCategoryId, token, currentPage).enqueue(new Callback<GetProducts>() {
            @Override
            public void onResponse(Call<GetProducts> call, Response<GetProducts> response) {
                if (response.body() != null) {
                    int code = response.body().getCode();
                    if (code == 200) {
                        List<GetProducts.Data> products = response.body().getProducts().getDataAll();
                        if (products.size() > 0) {
                            ArrayList<ProductsCategory> productsCategory = getProductsFromResponse(products);
                            allProductsCategory.addAll(productsCategory);
                            initializeRecycler(productsCategory);
                        } else {
                            if (isLoadedBefore) shouldLoadMore = false;
                            else showEmptyView();
                        }
                    }
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetProducts> call, Throwable t) {
                Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
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
        if (productsAdapter == null) {
            productsAdapter = new CategoryItemAdapter(getContext(), products);

            mLayoutManager = new GridLayoutManager(getContext(), 2);
            category_item_recycler_list.setLayoutManager(mLayoutManager);
            category_item_recycler_list.setItemAnimator(new DefaultItemAnimator());

            int spanCount = 2; // 3 columns
            int spacing = 20; // 50px
            boolean includeEdge = true;
            category_item_recycler_list.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

            productsAdapter.setCategoryOperation(ProductsFragment.this);
            category_item_recycler_list.setAdapter(productsAdapter);
            labelCountText.setText(String.valueOf(products.size()));
            isLoadedBefore = true;
        } else {
            int currentSize = productsAdapter.updateProductsList(products);
            labelCountText.setText(String.valueOf(currentSize));
        }
        itemDetailsHeader.setVisibility(View.VISIBLE);

        if (products.size() < productsPerLoad) {
            shouldLoadMore = false;
        }
//        categoryAdapter = new CategoryItemAdapter(getContext());
//        categoryAdapter.setCategoryOperation(this);
//        category_item_recycler_list.setAdapter(categoryAdapter);

    }

    private void showDialog() {
        reloadDialog = new Dialog(getContext());
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


//    @Override
//    public void onClickCategory(int position) {
//        startActivity(new Intent(getContext(), DetailItemFragment.class));
//        Animatoo.animateSwipeLeft(getContext());
//    }

//    @Override
//    public void onClickProduct3(int id, int pos) {
//        position_detail = pos;
//        startActivityForResult(new Intent(getContext(), DetailItemFragment.class).putExtra("id", id).putExtra("similar_products", productsCategory).putExtra("getLike", productsCategory.get(pos).getLike()).putExtra("pos", pos), 1111);
//        Animatoo.animateSwipeLeft(getContext());
//    }

//    @Override
//    public void onCliickProductsCategory3Like(int id, int pos) {
//        reloadDialog.show();
//        prepareLikeMap(id);
//        AddToFavouriteApi addToFavouriteApi = APIClient.getClient(SERVER_API_TEST).create(AddToFavouriteApi.class);
//        Call<GetAddToFavouriteResponse> getAddToFavouriteResponseCall;
//        if (PreferenceUtils.getCompanyLogin(getContext())) {
//            getAddToFavouriteResponseCall = addToFavouriteApi.getAddToFavouritecompany(likePost);
//        }else {
//            getAddToFavouriteResponseCall = addToFavouriteApi.getAddToFavourite(likePost);
//        }
//        getAddToFavouriteResponseCall.enqueue(new Callback<GetAddToFavouriteResponse>() {
//            @Override
//            public void onResponse(Call<GetAddToFavouriteResponse> call, Response<GetAddToFavouriteResponse> response) {
//                GetAddToFavouriteResponse getAddToFavouriteResponse = response.body();
//                if (Integer.parseInt(getAddToFavouriteResponse.getCode()) == 200) {
//                    Toasty.success(getContext(), getString(R.string.add_to_favourites), Toast.LENGTH_LONG).show();
//                }
//                reloadDialog.dismiss();
//            }
//
//            @Override
//            public void onFailure(Call<GetAddToFavouriteResponse> call, Throwable t) {
//                Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
//                reloadDialog.dismiss();
//            }
//        });
//    }

//    @Override
//    public void onAddToProductCategory3Cart(int id, int position) {
//        prepareCartMap(id);
//        AddToCardApi addToCardApi = APIClient.getClient(SERVER_API_TEST).create(AddToCardApi.class);
//        Call<GetAddToCardResponse> getAddToCardResponseCall;
//        if (PreferenceUtils.getCompanyLogin(getContext())) {
//            cartPost.put("language" , "arabic");
//            getAddToCardResponseCall = addToCardApi.getAddToCartcompany(cartPost);
//        }else {
//            getAddToCardResponseCall = addToCardApi.getAddToCart(cartPost);
//        }
//        getAddToCardResponseCall.enqueue(new Callback<GetAddToCardResponse>() {
//            @Override
//            public void onResponse(Call<GetAddToCardResponse> call, Response<GetAddToCardResponse> response) {
//                GetAddToCardResponse getAddToCardResponse = response.body();
//                if (getAddToCardResponse.getCode() == 200) {
//                    Toasty.success(getContext(), getString(R.string.add_to_card), Toast.LENGTH_LONG).show();
//                    productsAdapter.getProductsCategoryList().get(position).setCart("0");
//                    productsAdapter.notifyItemChanged(position);
//                    PreferenceUtils.saveCountOfItemsBasket(getContext(), Integer.parseInt(getAddToCardResponse.getItemsCount()));
//                }
//                reloadDialog.dismiss();
//            }
//
//            @Override
//            public void onFailure(Call<GetAddToCardResponse> call, Throwable t) {
//                Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
//                reloadDialog.dismiss();
//            }
//        });
//    }

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

    private void prepareLikeMap(int id) {
        likePost = new HashMap<>();
        if (PreferenceUtils.getUserLogin(getContext())) {
            String token = PreferenceUtils.getUserToken(getContext());
            likePost.put("product_id", String.valueOf(id));
            likePost.put("token", token);
        } else if (PreferenceUtils.getCompanyLogin(getContext())) {
            String token = PreferenceUtils.getCompanyToken(getContext());
            likePost.put("product_id", String.valueOf(id));
            likePost.put("token", token);
        }
    }

    @OnClick(R.id.search_items_category)
    void navigateToFilterActivity() {
        Intent intent = new Intent(getContext(), FilterActivity.class);
        intent.putExtra(CATEGORY_ID_INTENT_EXTRA_KEY, categoryId);
        intent.putExtra(SUBCATEGORY_ID_INTENT_EXTRA, subcategoryId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, FINISH_ACTIVITY_CODE);
        Animatoo.animateSwipeLeft(getContext());
    }

    @OnClick(R.id.descendant_items_category)
    void sortResults() {
        dropDownMenu.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1111) {
            if (data.getBooleanExtra("databack", false)) {
                allProductsCategory.get(position_detail).setLike("1");
                productsAdapter.notifyItemChanged(position_detail);
            }
            if (data.getBooleanExtra("added_to_cart", false)) {
                allProductsCategory.get(position_detail).setCart("0");
                productsAdapter.notifyItemChanged(position_detail);
            }
        }

        if (requestCode == FINISH_ACTIVITY_CODE && data != null) {
            boolean shouldFinishActivity = data.getBooleanExtra("should_finish_activity", false);
            if (shouldFinishActivity) getActivity().finish();
        }

//        if (requestCode == FILTER_REQUEST_CODE && data != null) {
//            ((ProductsActivity) getContext()).titleHeaderText.setText("");
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

    private void filterResults() {
        reloadDialog.show();
        FilterAPI filterAPI = APIClient.getClient(SERVER_API_TEST).create(FilterAPI.class);
        filterAPI.getProductsFromFilter(filterMap, token, currentPage).enqueue(new Callback<GetProducts>() {
            @Override
            public void onResponse(Call<GetProducts> call, Response<GetProducts> response) {
                if (response.body() != null) {
                    int code = response.body().getCode();
                    if (code == 200) {
                        List<GetProducts.Data> products = response.body().getProducts().getDataAll();
                        if (products.size() > 0) {
                            ArrayList<ProductsCategory> productsCategory = getProductsFromResponse(products);
                            allProductsCategory.addAll(productsCategory);
//                            productsAdapter.setProductsList(productsCategory);
//                            labelCountText.setText(String.valueOf(products.size()));
//                            productsAdapter.notifyDataSetChanged();
                            initializeRecycler(productsCategory);
                        } else {
                            if (isLoadedBefore) shouldLoadMore = false;
                            else showEmptyView();
                        }
                    }
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetProducts> call, Throwable t) {
                Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    @Override
    public void onClickProduct(int id, int pos) {
        position_detail = pos;
        startActivityForResult(new Intent(getContext(), DetailItemActivity.class).putExtra("id", id).putExtra("similar_products", allProductsCategory).putExtra("getLike", allProductsCategory.get(pos).getLike()).putExtra("pos", pos), 1111);
        Animatoo.animateSwipeLeft(getContext());
    }

    @Override
    public void onClickProductLike(int id) {
        reloadDialog.show();
        prepareLikeMap(id);
        AddToFavouriteApi addToFavouriteApi = APIClient.getClient(SERVER_API_TEST).create(AddToFavouriteApi.class);
        Call<GetAddToFavouriteResponse> getAddToFavouriteResponseCall;
        if (PreferenceUtils.getCompanyLogin(getContext())) {
            getAddToFavouriteResponseCall = addToFavouriteApi.getAddToFavouritecompany(likePost);
        } else {
            getAddToFavouriteResponseCall = addToFavouriteApi.getAddToFavourite(likePost);
        }
        getAddToFavouriteResponseCall.enqueue(new Callback<GetAddToFavouriteResponse>() {
            @Override
            public void onResponse(Call<GetAddToFavouriteResponse> call, Response<GetAddToFavouriteResponse> response) {
                GetAddToFavouriteResponse getAddToFavouriteResponse = response.body();
                if (Integer.parseInt(getAddToFavouriteResponse.getCode()) == 200) {
                    Toasty.success(getContext(), getString(R.string.add_to_favourites), Toast.LENGTH_LONG).show();
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetAddToFavouriteResponse> call, Throwable t) {
                Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    @Override
    public void onAddToProductCart(int id, int position) {
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
                    productsAdapter.getProductsList().get(position).setCart("0");
                    productsAdapter.notifyItemChanged(position);
                    PreferenceUtils.saveCountOfItemsBasket(getContext(), Integer.parseInt(getAddToCardResponse.getItemsCount()));
                    try{
                        ((MainActivity) getActivity()).updateBasketBadge();
                    }catch(Exception ex){

                    }
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
}
