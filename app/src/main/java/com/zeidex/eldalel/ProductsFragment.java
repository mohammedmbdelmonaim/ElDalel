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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.adapters.ProductsCategory3Adapter;
import com.zeidex.eldalel.models.ProductsCategory;
import com.zeidex.eldalel.response.GetAddToCardResponse;
import com.zeidex.eldalel.response.GetProducts;
import com.zeidex.eldalel.services.AddToCardApi;
import com.zeidex.eldalel.services.ProductsAPI;
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
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zeidex.eldalel.adapters.CategoriesItemAdapter.SUBCATEGORY_ID_INTENT_EXTRA;
import static com.zeidex.eldalel.adapters.CategoriesItemAdapter.SUB_SUBCATEGORY_ID_INTENT_EXTRA;
import static com.zeidex.eldalel.utils.Constants.CART_NOT_EMPTY;
import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class ProductsFragment extends Fragment implements /*CategoryItemAdapter.CategoryOperation,*/ ProductsCategory3Adapter.ProductsCategory3Operation {
    @BindView(R.id.category_item_recycler_list)
    RecyclerView category_item_recycler_list;
    @BindView(R.id.categories_no_items_layout)
    RelativeLayout noItemsLayout;

    Dialog reloadDialog;
    String token = "";

//    CategoryItemAdapter categoryAdapter;

    ProductsCategory3Adapter productsAdapter;
    private int subcategoryId;
    private int subsubcategoryId;
    Map<String, String> post;

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
        findViews();
    }

    private void findViews() {
        if (PreferenceUtils.getCompanyLogin(getActivity())) {
            token = PreferenceUtils.getCompanyToken(getActivity());
        } else if (PreferenceUtils.getUserLogin(getActivity())) {
            token = PreferenceUtils.getUserToken(getActivity());
        }

        subcategoryId = getArguments().getInt(SUBCATEGORY_ID_INTENT_EXTRA, -1);
        subsubcategoryId = getArguments().getInt(SUB_SUBCATEGORY_ID_INTENT_EXTRA, -1);

        showDialog();
        onLoadPage();
    }

    private void onLoadPage() {
        if (subsubcategoryId != -1) { //meaning there are subsubcategories
            getProductsFromSubSubCategory(subsubcategoryId);
        } else if (subcategoryId != -1) {
            getProductsFromSubCategory(subcategoryId);
        }
    }

    private void getProductsFromSubSubCategory(int subsubCategoryId) {
        reloadDialog.show();
        ProductsAPI productsAPI = APIClient.getClient(SERVER_API_TEST).create(ProductsAPI.class);
        productsAPI.getProductsFromSubSubCategory(subsubCategoryId, token).enqueue(new Callback<GetProducts>() {
            @Override
            public void onResponse(Call<GetProducts> call, Response<GetProducts> response) {
                if (response.body() != null) {
                    int code = response.body().getCode();
                    if (code == 200) {
                        List<GetProducts.Data> products = response.body().getProducts().getDataAll();
                        if (products.size() > 0) {
                            ArrayList<ProductsCategory> productsCategory = getProductsFromResponse(products);
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

    private void showEmptyView() {
        noItemsLayout.setVisibility(View.VISIBLE);
        category_item_recycler_list.setVisibility(View.GONE);
    }

    private void getProductsFromSubCategory(int subCategoryId) {
        reloadDialog.show();
        ProductsAPI productsAPI = APIClient.getClient(SERVER_API_TEST).create(ProductsAPI.class);
        productsAPI.getProducts(subCategoryId, token).enqueue(new Callback<GetProducts>() {
            @Override
            public void onResponse(Call<GetProducts> call, Response<GetProducts> response) {
                if (response.body() != null) {
                    int code = response.body().getCode();
                    if (code == 200) {
                        List<GetProducts.Data> products = response.body().getProducts().getDataAll();
                        if (products.size() > 0) {
                            ArrayList<ProductsCategory> productsCategory = getProductsFromResponse(products);
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

    public void initializeRecycler(List<ProductsCategory> products) {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        category_item_recycler_list.setLayoutManager(mLayoutManager);
        category_item_recycler_list.setItemAnimator(new DefaultItemAnimator());

        int spanCount = 2; // 3 columns
        int spacing = 20; // 50px
        boolean includeEdge = true;
        category_item_recycler_list.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        productsAdapter = new ProductsCategory3Adapter(getActivity(), products);
        productsAdapter.setProductsCategory3Operation(ProductsFragment.this);
        category_item_recycler_list.setAdapter(productsAdapter);
//        categoryAdapter = new CategoryItemAdapter(getActivity());
//        categoryAdapter.setCategoryOperation(this);
//        category_item_recycler_list.setAdapter(categoryAdapter);

    }

    private void showDialog() {
        reloadDialog = new Dialog(getActivity());
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


//    @Override
//    public void onClickCategory(int position) {
//        startActivity(new Intent(getActivity(), DetailItemActivity.class));
//        Animatoo.animateSwipeLeft(getActivity());
//    }

    @Override
    public void onClickProduct3(int id, int pos) {
//        position_detail = pos;
//        startActivityForResult(new Intent(getActivity(), DetailItemActivity.class).putExtra("id", id).putExtra("similar_products", home_category3).putExtra("getLike", home_category3.get(pos).getLike()).putExtra("pos", pos), 1111);
//        Animatoo.animateSwipeLeft(getActivity());
    }
//
    @Override
    public void onCliickProductsCategory3Like(int id) {
//        reloadDialog.show();
//        convertDaraToJson(id);
//        AddToFavouriteApi addToFavouriteApi = APIClient.getClient(SERVER_API_TEST).create(AddToFavouriteApi.class);
//        Call<GetAddToFavouriteResponse> getAddToFavouriteResponseCall = addToFavouriteApi.getAddToFavourite(post);
//        getAddToFavouriteResponseCall.enqueue(new Callback<GetAddToFavouriteResponse>() {
//            @Override
//            public void onResponse(Call<GetAddToFavouriteResponse> call, Response<GetAddToFavouriteResponse> response) {
//                GetAddToFavouriteResponse getAddToFavouriteResponse = response.body();
//                if (Integer.parseInt(getAddToFavouriteResponse.getCode()) == 200) {
//                    Toasty.success(getActivity(), getString(R.string.add_to_favourites), Toast.LENGTH_LONG).show();
//                }
//                reloadDialog.dismiss();
//            }
//
//            @Override
//            public void onFailure(Call<GetAddToFavouriteResponse> call, Throwable t) {
//                Toasty.error(getActivity(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
//                reloadDialog.dismiss();
//                Fragment frg = null;
//                frg = getActivity().getSupportFragmentManager().findFragmentByTag("main_fragment");
//                final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                ft.detach(frg);
//                ft.attach(frg);
//                ft.commit();
//            }
//        });
    }

    @Override
    public void onAddToProductCategory3Cart(int id, int position) {
        prepareCartMap(id);
        AddToCardApi addToCardApi = APIClient.getClient(SERVER_API_TEST).create(AddToCardApi.class);
        Call<GetAddToCardResponse> getAddToCardResponseCall = addToCardApi.getAddToFavourite(post);
        getAddToCardResponseCall.enqueue(new Callback<GetAddToCardResponse>() {
            @Override
            public void onResponse(Call<GetAddToCardResponse> call, Response<GetAddToCardResponse> response) {
                GetAddToCardResponse getAddToCardResponse = response.body();
                if (getAddToCardResponse.getCode() == 200) {
                    Toasty.success(getActivity(), getString(R.string.add_to_card), Toast.LENGTH_LONG).show();
                    productsAdapter.getProductsCategoryList().get(position).setCart(String.valueOf(CART_NOT_EMPTY));
                    productsAdapter.notifyItemChanged(position);
                    PreferenceUtils.saveCountOfItemsBasket(getActivity(), Integer.parseInt(getAddToCardResponse.getItemsCount()));
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetAddToCardResponse> call, Throwable t) {
                Toasty.error(getActivity(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
                Fragment frg = null;
                frg = getActivity().getSupportFragmentManager().findFragmentByTag("main_fragment");
                final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.detach(frg);
                ft.attach(frg);
                ft.commit();
            }
        });
    }

    public void prepareCartMap(int id) {
        post = new HashMap<>();
        if (PreferenceUtils.getUserLogin(getActivity())) {
            String token = PreferenceUtils.getUserToken(getActivity());
            post.put("product_id", String.valueOf(id));
            post.put("token", token);
            post.put("quantity", "1");
        } else if (PreferenceUtils.getCompanyLogin(getActivity())) {
            String token = PreferenceUtils.getCompanyToken(getActivity());
            post.put("product_id", String.valueOf(id));
            post.put("token", token);
            post.put("quantity", "1");
        }
        reloadDialog.show();
    }
}
