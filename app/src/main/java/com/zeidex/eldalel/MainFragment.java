package com.zeidex.eldalel;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.zeidex.eldalel.adapters.AccessoriesAdapter;
import com.zeidex.eldalel.adapters.HomeSliderAdapter;
import com.zeidex.eldalel.adapters.PhonesAdapter;
import com.zeidex.eldalel.adapters.ProductsCategory3Adapter;
import com.zeidex.eldalel.listeners.AddToCartCallback;
import com.zeidex.eldalel.listeners.FirstPageFragmentListener;
import com.zeidex.eldalel.models.ProductsCategory;
import com.zeidex.eldalel.models.Subcategory;
import com.zeidex.eldalel.models.Subsubcategory;
import com.zeidex.eldalel.response.GetAddToCardResponse;
import com.zeidex.eldalel.response.GetAddToFavouriteResponse;
import com.zeidex.eldalel.response.GetAllCategories;
import com.zeidex.eldalel.response.GetHomeProducts;
import com.zeidex.eldalel.response.GetSliders;
import com.zeidex.eldalel.services.AddToCardApi;
import com.zeidex.eldalel.services.AddToFavouriteApi;
import com.zeidex.eldalel.services.AllCategoriesAPI;
import com.zeidex.eldalel.services.HomeProducts;
import com.zeidex.eldalel.services.SliderAPI;
import com.zeidex.eldalel.utils.APIClient;
import com.zeidex.eldalel.utils.Animatoo;
import com.zeidex.eldalel.utils.ChangeLang;
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

import static com.zeidex.eldalel.SearchActivity.SEARCH_NAME_ARGUMENT;
import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class MainFragment extends androidx.fragment.app.Fragment implements ProductsCategory3Adapter.ProductsCategory3Operation, PhonesAdapter.PhonesOperation, AccessoriesAdapter.AccessoriesOperation {
    private static final String TAG = "Toast_View";
    @BindView(R.id.main_recycler_accessories)
    RecyclerView main_recycler_accessories;

    @BindView(R.id.main_recycler_phones)
    RecyclerView main_recycler_phones;

    @BindView(R.id.main_recycler_category3)
    RecyclerView main_recycler_category3;

    @BindView(R.id.fragment_main_searchview)
    SearchView fragment_main_searchview;

    @BindView(R.id.accessories_label)
    AppCompatTextView accessories_label;

    @BindView(R.id.phones_label)
    AppCompatTextView phones_label;

    @BindView(R.id.category3_label)
    AppCompatTextView category3_label;

    @BindView(R.id.fragment_main_basket_top_txt)
    AppCompatTextView fragment_main_basket_top_txt;

    @BindView(R.id.constraint_home_category3)
    ConstraintLayout constraint_home_category3;

    @BindView(R.id.imageSlider)
    SliderView imageSlider;
    private List<GetSliders.Data> sliders;

    @OnClick(R.id.acc_more)
    void navigateToAcc() {
        subCategoriesModel = new ArrayList<>();
        getAllCategories(categories_ids.get(0), categories_names.get(0));
    }

    @OnClick(R.id.phone_more)
    void navigateToPhone() {
        subCategoriesModel = new ArrayList<>();
        getAllCategories(categories_ids.get(1), categories_names.get(1));
    }

    ArrayList<Subcategory> subCategoriesModel;

    @OnClick(R.id.category3_more)
    void navigateToCategoryMore() {
        subCategoriesModel = new ArrayList<>();
        getAllCategories(categories_ids.get(2), categories_names.get(2));

    }


    @OnClick(R.id.fragment_main_basket_top_constraint)
    public void goToBasket() {
        if (!PreferenceUtils.getUserLogin(getContext()) && !PreferenceUtils.getCompanyLogin(getContext())) {
            Toasty.error(getContext(), getString(R.string.please_login_first), Toast.LENGTH_LONG).show();
            return;
        }
        MenuItem selectedItem;
        selectedItem = ((MainActivity) getContext()).mBottomNav.getMenu().getItem(3);
        selectedItem.setChecked(true);
        ((MainActivity) getContext()).selectFragment(selectedItem);


        Fragment fragment = new BasketFragment();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//            ft.setCustomAnimations(R.anim.animate_slide_up_enter, R.anim.animate_slide_up_exit);
        ft.replace(R.id.container_activity, fragment, "basket_fragment");
        ft.addToBackStack(null);
        ft.commit();
    }

    AccessoriesAdapter accessoriesAdapter;

    PhonesAdapter phonesAdapter;

    ProductsCategory3Adapter category3Adapter;

    HomeSliderAdapter homeSliderAdapter;

    static FirstPageFragmentListener firstPageListener;

//    public MainFragment(FirstPageFragmentListener listener){
//        firstPageListener = listener;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initializeRecycler();
        findViews();
    }

    public void findViews() {
        if (PreferenceUtils.getCompanyLogin(getContext())) {
            token = PreferenceUtils.getCompanyToken(getContext());
        } else if (PreferenceUtils.getUserLogin(getContext())) {
            token = PreferenceUtils.getUserToken(getContext());
        }

        if (token.equalsIgnoreCase("") || PreferenceUtils.getCountOfItemsBasket(getContext()) <= 0) {
            fragment_main_basket_top_txt.setVisibility(View.GONE);
        } else {
            fragment_main_basket_top_txt.setVisibility(View.VISIBLE);
            fragment_main_basket_top_txt.setText("" + PreferenceUtils.getCountOfItemsBasket(getContext()));
        }

        imageSlider.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        imageSlider.setIndicatorSelectedColor(Color.parseColor("#AAAAAA"));
        imageSlider.setIndicatorUnselectedColor(Color.parseColor("#FAFAFA"));
        imageSlider.setScrollTimeInSec(4); //set scroll delay in seconds :

        fragment_main_searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra(SEARCH_NAME_ARGUMENT, query);
                startActivity(intent);
                Animatoo.animateSwipeLeft(getContext());
                fragment_main_searchview.onActionViewCollapsed(); //to close the searchview
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        showDialog();

        if (home_category1 != null && home_category2 != null && home_category3 != null) {
            updateUI();


            if (sliders != null) {
                homeSliderAdapter = new HomeSliderAdapter(getContext(), sliders);
                imageSlider.setSliderAdapter(homeSliderAdapter);
                imageSlider.startAutoCycle();
            }

        } else {
            onLoadPage();
        }
    }

    public void initializeRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        main_recycler_accessories.setLayoutManager(layoutManager);
        main_recycler_accessories.setItemAnimator(new DefaultItemAnimator());

        main_recycler_phones.setLayoutManager(layoutManager2);
        main_recycler_phones.setItemAnimator(new DefaultItemAnimator());

        main_recycler_category3.setLayoutManager(layoutManager3);
        main_recycler_category3.setItemAnimator(new DefaultItemAnimator());
//
    }

    ArrayList<Subsubcategory> subsubcategories;
    private List<GetAllCategories.Category> categories;

    private void getAllCategories(int category_id, String category_name) {
        subsubcategories = new ArrayList<>();
        reloadDialog.show();
        AllCategoriesAPI allCategoriesAPI = APIClient.getClient(SERVER_API_TEST).create(AllCategoriesAPI.class);
        allCategoriesAPI.getAllCategories(1).enqueue(new Callback<GetAllCategories>() {
            @Override
            public void onResponse(Call<GetAllCategories> call, Response<GetAllCategories> response) {
                if (response.body() != null) {
                    int code = response.body().getCode();
                    if (code == 200) {
                        categories = response.body().getData().getCategories();
                        if (categories.size() > 0) {
                            for (GetAllCategories.Category category : categories) {
                                if (category.getId() == category_id) {
                                    for (int j = 0; j < category.getSubcategories().size(); j++) {
                                        subCategoriesModel.add(new Subcategory(category.getSubcategories().get(j).getId(), category.getSubcategories().get(j).getNameAr(),
                                                category.getSubcategories().get(j).getName(), category.getSubcategories().get(j).getPhoto(), subsubcategories));
                                    }
                                }
                            }

                        }
//                        Bundle bundle = new Bundle();
//                        bundle.putInt(CATEGORY_ID_INTENT_EXTRA_KEY,category_id);
//                        bundle.putString(CATEGORY_NAME_INTENT_EXTRA , category_name);
//                        bundle.putParcelableArrayList(SUBCATEGORIES_INTENT_EXTRA_KEY , subCategoriesModel);
                        NavHostFragment.findNavController(MainFragment.this).navigate(MainFragmentDirections.actionMainFragmentToOfferItemActivity2(category_name, category_id, false, subCategoriesModel.toArray(new Subcategory[subCategoriesModel.size()])));
//                        startActivity(new Intent(getContext() , OfferItemActivity.class).putExtra(CATEGORY_ID_INTENT_EXTRA_KEY,category_id).putExtra(CATEGORY_NAME_INTENT_EXTRA , category_name)
//                                .putExtra(SUBCATEGORIES_INTENT_EXTRA_KEY , subCategoriesModel));
                    }
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetAllCategories> call, Throwable t) {
                Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    ArrayList<ProductsCategory> home_category1;
    ArrayList<ProductsCategory> home_category2;
    ArrayList<ProductsCategory> home_category3;
    ArrayList<String> categories_names;
    ArrayList<Integer> categories_ids;
    String token = "";

    public void onLoadPage() {
        home_category1 = new ArrayList<>();
        home_category2 = new ArrayList<>();
        home_category3 = new ArrayList<>();
        categories_names = new ArrayList<>();
        categories_ids = new ArrayList<>();
        reloadDialog.show();

        HomeProducts homeProducts = APIClient.getClient(SERVER_API_TEST).create(HomeProducts.class);
        Call<GetHomeProducts> getLoginResponseCall = homeProducts.getHomeProducts(token);
        getLoginResponseCall.enqueue(new Callback<GetHomeProducts>() {
            @Override
            public void onResponse(Call<GetHomeProducts> call, Response<GetHomeProducts> response) {
                GetHomeProducts getHomeProducts = response.body();
                if (getHomeProducts != null) {
                    int code = Integer.parseInt(getHomeProducts.getCode());
                    if (code == 200) {
                        if (!token.equalsIgnoreCase("")) {
                            int cartCount = getHomeProducts.getData().getCountCart();
                            if (cartCount > 0) {
                                fragment_main_basket_top_txt.setVisibility(View.VISIBLE);
                                fragment_main_basket_top_txt.setText(String.valueOf(cartCount));
                            }
                        }
                        for (int i = 0; i < getHomeProducts.getData().getCategories().size(); i++) { //category loop
                            if (getHomeProducts.getData().getCategories().get(i).getProducts().size() == 0) {
                                continue;
                            }

                            Locale locale = ChangeLang.getLocale(getContext().getResources());
                            String loo = locale.getLanguage();
                            if (loo.equalsIgnoreCase("en")) {
                                categories_ids.add(Integer.parseInt(getHomeProducts.getData().getCategories().get(i).getId()));
                                categories_names.add(getHomeProducts.getData().getCategories().get(i).getName());

                                for (int j = 0; j < getHomeProducts.getData().getCategories().get(i).getProducts().size(); j++) { // product loop

                                    String arr[] = getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getName().split(" ", 2); // get first word
                                    String firstWord = arr[0];

                                    if (getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getPhotos().size() == 0) {
                                        home_category1.add(new ProductsCategory(getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getId(), "",
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getDiscount(), firstWord, getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getName(),
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getPrice(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getOld_price(),
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getFavorite(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getCart(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getAvailable_quantity()));
                                    } else {
                                        home_category1.add(new ProductsCategory(getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getId(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getPhotos().get(0).getFilename(),
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getDiscount(), firstWord, getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getName(),
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getPrice(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getOld_price(),
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getFavorite(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getCart(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getAvailable_quantity()));
                                    }


                                }

                            } else if (loo.equalsIgnoreCase("ar")) {
                                categories_ids.add(Integer.parseInt(getHomeProducts.getData().getCategories().get(i).getId()));
                                categories_names.add(getHomeProducts.getData().getCategories().get(i).getName_ar());

                                for (int j = 0; j < getHomeProducts.getData().getCategories().get(i).getProducts().size(); j++) { // product loop

                                    String arr[] = getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getName_ar().split(" ", 2); // get first word
                                    String firstWord = arr[0];

                                    if (getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getPhotos().size() == 0) {
                                        home_category1.add(new ProductsCategory(getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getId(), "",
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getDiscount(), firstWord, getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getName_ar(),
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getPrice(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getOld_price(),
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getFavorite(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getCart(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getAvailable_quantity()));
                                    } else {
                                        home_category1.add(new ProductsCategory(getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getId(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getPhotos().get(0).getFilename(),
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getDiscount(), firstWord, getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getName_ar(),
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getPrice(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getOld_price(),
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getFavorite(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getCart(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getAvailable_quantity()));
                                    }
                                }
                            }
                            break;
                        }

                        for (int i = 0; i < getHomeProducts.getData().getCategories().size(); i++) { //category loop
                            if (getHomeProducts.getData().getCategories().get(i).getProducts().size() == 0) {
                                continue;
                            }
                            int o = Integer.parseInt(getHomeProducts.getData().getCategories().get(i).getId());
                            if (categories_ids.get(0) == Integer.parseInt(getHomeProducts.getData().getCategories().get(i).getId())) {
                                continue;
                            }
                            Locale locale = ChangeLang.getLocale(getContext().getResources());
                            String loo = locale.getLanguage();
                            if (loo.equalsIgnoreCase("en")) {
                                categories_ids.add(Integer.parseInt(getHomeProducts.getData().getCategories().get(i).getId()));
                                categories_names.add(getHomeProducts.getData().getCategories().get(i).getName());

                                for (int j = 0; j < getHomeProducts.getData().getCategories().get(i).getProducts().size(); j++) { // product loop

                                    String arr[] = getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getName().split(" ", 2); // get first word
                                    String firstWord = arr[0];

                                    if (getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getPhotos().size() == 0) {
                                        home_category2.add(new ProductsCategory(getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getId(), "",
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getDiscount(), firstWord, getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getName(),
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getPrice(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getOld_price(),
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getFavorite(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getCart(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getAvailable_quantity()));
                                    } else {
                                        home_category2.add(new ProductsCategory(getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getId(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getPhotos().get(0).getFilename(),
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getDiscount(), firstWord, getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getName(),
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getPrice(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getOld_price(),
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getFavorite(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getCart(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getAvailable_quantity()));
                                    }

                                }

                            } else if (loo.equalsIgnoreCase("ar")) {
                                categories_ids.add(Integer.parseInt(getHomeProducts.getData().getCategories().get(i).getId()));
                                categories_names.add(getHomeProducts.getData().getCategories().get(i).getName_ar());

                                for (int j = 0; j < getHomeProducts.getData().getCategories().get(i).getProducts().size(); j++) { // product loop

                                    String arr[] = getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getName_ar().split(" ", 2); // get first word
                                    String firstWord = arr[0];

                                    if (getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getPhotos().size() == 0) {
                                        home_category2.add(new ProductsCategory(getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getId(), "",
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getDiscount(), firstWord, getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getName_ar(),
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getPrice(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getOld_price(),
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getFavorite(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getCart(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getAvailable_quantity()));
                                    } else {
                                        home_category2.add(new ProductsCategory(getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getId(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getPhotos().get(0).getFilename(),
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getDiscount(), firstWord, getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getName_ar(),
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getPrice(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getOld_price(),
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getFavorite(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getCart(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getAvailable_quantity()));
                                    }

                                }
                            }

                            break;
                        }

                        for (int i = 0; i < getHomeProducts.getData().getCategories().size(); i++) { //category loop
                            if (getHomeProducts.getData().getCategories().get(i).getProducts().size() == 0) {
                                continue;
                            }
                            int o = Integer.parseInt(getHomeProducts.getData().getCategories().get(i).getId());
                            if (categories_ids.get(1) == Integer.parseInt(getHomeProducts.getData().getCategories().get(i).getId()) || categories_ids.get(0) == Integer.parseInt(getHomeProducts.getData().getCategories().get(i).getId())) {
                                continue;
                            }
                            Locale locale = ChangeLang.getLocale(getContext().getResources());
                            String loo = locale.getLanguage();
                            if (loo.equalsIgnoreCase("en")) {
                                categories_ids.add(Integer.parseInt(getHomeProducts.getData().getCategories().get(i).getId()));
                                categories_names.add(getHomeProducts.getData().getCategories().get(i).getName());

                                for (int j = 0; j < getHomeProducts.getData().getCategories().get(i).getProducts().size(); j++) { // product loop

                                    String arr[] = getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getName().split(" ", 2); // get first word
                                    String firstWord = arr[0];

                                    if (getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getPhotos().size() == 0) {
                                        home_category3.add(new ProductsCategory(getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getId(), "",
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getDiscount(), firstWord, getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getName(),
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getPrice(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getOld_price(),
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getFavorite(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getCart(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getAvailable_quantity()));
                                    } else {
                                        home_category3.add(new ProductsCategory(getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getId(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getPhotos().get(0).getFilename(),
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getDiscount(), firstWord, getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getName(),
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getPrice(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getOld_price(),
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getFavorite(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getCart(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getAvailable_quantity()));
                                    }

                                }

                            } else if (loo.equalsIgnoreCase("ar")) {
                                categories_ids.add(Integer.parseInt(getHomeProducts.getData().getCategories().get(i).getId()));
                                categories_names.add(getHomeProducts.getData().getCategories().get(i).getName_ar());

                                for (int j = 0; j < getHomeProducts.getData().getCategories().get(i).getProducts().size(); j++) { // product loop

                                    String arr[] = getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getName_ar().split(" ", 2); // get first word
                                    String firstWord = arr[0];

                                    if (getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getPhotos().size() == 0) {
                                        home_category3.add(new ProductsCategory(getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getId(), "",
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getDiscount(), firstWord, getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getName_ar(),
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getPrice(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getOld_price(),
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getFavorite(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getCart(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getAvailable_quantity()));
                                    } else {
                                        home_category3.add(new ProductsCategory(getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getId(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getPhotos().get(0).getFilename(),
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getDiscount(), firstWord, getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getName_ar(),
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getPrice(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getOld_price(),
                                                getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getFavorite(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getCart(), getHomeProducts.getData().getCategories().get(i).getProducts().get(j).getAvailable_quantity()));
                                    }
                                }
                            }

                            break;
                        }
                    }
                    updateUI();
                    reloadDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<GetHomeProducts> call, Throwable t) {
                if (getContext() != null) {
                    showAToast(getString(R.string.confirm_internet));
//                    Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                    reloadDialog.dismiss();
                    Fragment frg = null;
                    frg = getActivity().getSupportFragmentManager().findFragmentByTag("main_fragment");
                    final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.commitAllowingStateLoss();
                }
            }
        });

        getSlidersData();


    }

    private void updateUI() {
        if (home_category3.size() > 0) {
            category3Adapter = new ProductsCategory3Adapter(getContext(), home_category3);
            category3Adapter.setProductsCategory3Operation(MainFragment.this);
            main_recycler_category3.setAdapter(category3Adapter);
            category3_label.setText(categories_names.get(2));
        }else{
            constraint_home_category3.setVisibility(View.GONE);
        }


        if (home_category2.size() > 0) {
            phonesAdapter = new PhonesAdapter(getContext(), home_category2);
            phonesAdapter.setnPhones(MainFragment.this);
            main_recycler_phones.setAdapter(phonesAdapter);
            phones_label.setText(categories_names.get(1));
        }

        if (home_category1.size() > 0) {
            accessoriesAdapter = new AccessoriesAdapter(getContext(), home_category1);
            accessoriesAdapter.setAccessoriesOperation(MainFragment.this);
            main_recycler_accessories.setAdapter(accessoriesAdapter);
            accessories_label.setText(categories_names.get(0));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public void showAToast(String st) {
        toast = Toasty.error(getContext(), st, Toast.LENGTH_LONG);//"Toast toast" is declared in the class
        if (toast_visibility) {

        } else {
            toast.show();
        }

//        try{ toast.getView().isShown();     // true if visible
//            toast.setText(st);
//        } catch (Exception e) {         // invisible if exception
//            toast = Toasty.error(getContext(), st, Toast.LENGTH_LONG);
//        }
//        toast.show();  //finally display it
    }

    synchronized public void cancel() {
        if (toast == null) {
            Log.d(TAG, "cancel: toast is null (occurs first time only)");
            return;
        }
        final View view = toast.getView();
        if (view == null) {
            Log.d(TAG, "cancel: view is null");
            return;
        }
        if (view.isShown()) {
            toast.cancel();
        } else {
            Log.d(TAG, "cancel: view is already dismissed");
        }
    }


    public Toast toast;
    static boolean toast_visibility;
//    private List<ProductsCategory> getProducts(int categoryId, GetHomeProducts getHomeProducts) {
//        ArrayList<ProductsCategory> home_category = new ArrayList<>();
//
//        Locale locale = ChangeLang.getLocale(getContext().getResources());
//        String loo = locale.getLanguage();
//        if (loo.equalsIgnoreCase("en")) {
//            categories_ids.add(Integer.parseInt(getHomeProducts.getData().getCategories().get(categoryId).getId()));
//            categories_names.add(getHomeProducts.getData().getCategories().get(categoryId).getName());
//
//            for (int j = 0; j < getHomeProducts.getData().getCategories().get(categoryId).getProducts().size(); j++) { // product loop
//
//                String arr[] = getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getName().split(" ", 2); // get first word
//                String firstWord = arr[0];
//
//                if (getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getPhotos().size() == 0) {
//                    home_category.add(new ProductsCategory(getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getId(), "",
//                            getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getDiscount(), firstWord, getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getName(),
//                            getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getPrice(), getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getOld_price(),
//                            getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getFavorite(), String.valueOf(getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getCart()), getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getAvailable_quantity()));
//                } else {
//                    home_category.add(new ProductsCategory(getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getId(), getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getPhotos().get(0).getFilename(),
//                            getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getDiscount(), firstWord, getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getName(),
//                            getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getPrice(), getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getOld_price(),
//                            getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getFavorite(), String.valueOf(getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getCart()), getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getAvailable_quantity()));
//                }
//
//            }
//
//        } else if (loo.equalsIgnoreCase("ar")) {
//            categories_ids.add(Integer.parseInt(getHomeProducts.getData().getCategories().get(categoryId).getId()));
//            categories_names.add(getHomeProducts.getData().getCategories().get(categoryId).getName_ar());
//
//            for (int j = 0; j < getHomeProducts.getData().getCategories().get(categoryId).getProducts().size(); j++) { // product loop
//
//                String arr[] = getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getName_ar().split(" ", 2); // get first word
//                String firstWord = arr[0];
//
//                if (getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getPhotos().size() == 0) {
//                    home_category.add(new ProductsCategory(getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getId(), "",
//                            getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getDiscount(), firstWord, getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getName_ar(),
//                            getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getPrice(), getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getOld_price(),
//                            getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getFavorite(), String.valueOf(getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getCart()), getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getAvailable_quantity()));
//                } else {
//                    home_category.add(new ProductsCategory(getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getId(), getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getPhotos().get(0).getFilename(),
//                            getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getDiscount(), firstWord, getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getName_ar(),
//                            getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getPrice(), getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getOld_price(),
//                            getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getFavorite(), String.valueOf(getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getCart()), getHomeProducts.getData().getCategories().get(categoryId).getProducts().get(j).getAvailable_quantity()));
//                }
//
//            }
//        }
//        return home_category;
//    }


    private void getSlidersData() {
        SliderAPI sliderAPI = APIClient.getClient(SERVER_API_TEST).create(SliderAPI.class);
        Call<GetSliders> getSlidersCall = sliderAPI.getSliders();
        getSlidersCall.enqueue(new Callback<GetSliders>() {
            @Override
            public void onResponse(Call<GetSliders> call, Response<GetSliders> response) {
                sliders = response.body().getData();
                homeSliderAdapter = new HomeSliderAdapter(getContext(), sliders);
                imageSlider.setSliderAdapter(homeSliderAdapter);
                imageSlider.startAutoCycle();
            }

            @Override
            public void onFailure(Call<GetSliders> call, Throwable t) {
                if (getContext() != null) {
                    Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                    reloadDialog.dismiss();
                }
            }
        });
    }

    int position_detail;

    Map<String, String> post;

    private void convertDaraToJson(int id) {
        post = new HashMap<>();
        if (PreferenceUtils.getUserLogin(getContext())) {
            String token = PreferenceUtils.getUserToken(getContext());
            post.put("product_id", String.valueOf(id));
            post.put("token", token);
        } else if (PreferenceUtils.getCompanyLogin(getContext())) {
            String token = PreferenceUtils.getCompanyToken(getContext());
            post.put("product_id", String.valueOf(id));
            post.put("token", token);
        }
    }

    Dialog reloadDialog;

    private void showDialog() {
        reloadDialog = new Dialog(getContext());
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onClickProduct3(int id, int pos) {
        position_detail = pos;

        AddToCartCallback callback = new AddToCartCallback() {
            @Override
            public void setAddToCartResult(String totalItemCount) {
                updateCategory3CartUI(pos, totalItemCount);
            }
        };

        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos);
        bundle.putInt("id", id);
        bundle.putParcelableArrayList("similar_products", home_category3);
        bundle.putString("getLike", home_category3.get(pos).getLike());
        bundle.putSerializable("added_to_cart", callback);
//        firstPageListener.onSwitchToNextFragment(bundle);
        NavHostFragment.findNavController(this).navigate(R.id.action_mainFragment_to_detailItemFragment, bundle);
    }

    private void updateCategory3CartUI(int pos, String totalItemCount) {
        category3Adapter.getProductsCategoryList().get(pos).setCart("0");
        category3Adapter.notifyItemChanged(pos);
    }

    @Override
    public void onCliickProductsCategory3Like(int id, int pos) {
        reloadDialog.show();
        convertDaraToJson(id);
        AddToFavouriteApi addToFavouriteApi = APIClient.getClient(SERVER_API_TEST).create(AddToFavouriteApi.class);
        Call<GetAddToFavouriteResponse> getAddToFavouriteResponseCall;
        if (PreferenceUtils.getCompanyLogin(getContext())) {
            getAddToFavouriteResponseCall = addToFavouriteApi.getAddToFavouritecompany(post);
        } else {
            getAddToFavouriteResponseCall = addToFavouriteApi.getAddToFavourite(post);
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
    public void onAddToProductCategory3Cart(int id, int position) {
        reloadDialog.show();
        prepareCartMap(id);
        AddToCardApi addToCardApi = APIClient.getClient(SERVER_API_TEST).create(AddToCardApi.class);
        Call<GetAddToCardResponse> getAddToCardResponseCall;
        if (PreferenceUtils.getCompanyLogin(getContext())) {
            post.put("language", "arabic");
            getAddToCardResponseCall = addToCardApi.getAddToCartcompany(post);
        } else {
            getAddToCardResponseCall = addToCardApi.getAddToCart(post);
        }
        getAddToCardResponseCall.enqueue(new Callback<GetAddToCardResponse>() {
            @Override
            public void onResponse(Call<GetAddToCardResponse> call, Response<GetAddToCardResponse> response) {
                GetAddToCardResponse getAddToCardResponse = response.body();
                if (getAddToCardResponse.getCode() == 200) {
                    Toasty.success(getContext(), getString(R.string.add_to_card), Toast.LENGTH_LONG).show();
                    category3Adapter.getProductsCategoryList().get(position).setCart("0");
                    category3Adapter.notifyItemChanged(position);
                    fragment_main_basket_top_txt.setText(getAddToCardResponse.getItemsCount());
                    fragment_main_basket_top_txt.setVisibility(View.VISIBLE);
                    PreferenceUtils.saveCountOfItemsBasket(getContext(), Integer.parseInt(getAddToCardResponse.getItemsCount()));
                    ((MainActivity) getActivity()).updateBasketBadge();
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
    public void onClickPhone(int id, int pos) {
        position_detail = pos;
        AddToCartCallback callback = new AddToCartCallback() {
            @Override
            public void setAddToCartResult(String totalItemCount) {
                updatePhoneCartUI(pos, totalItemCount);
            }
        };
        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos);
        bundle.putInt("id", id);
        bundle.putParcelableArrayList("similar_products", home_category2);
        bundle.putString("getLike", home_category2.get(pos).getLike());
        bundle.putSerializable("added_to_cart", callback);

//        firstPageListener.onSwitchToNextFragment(bundle);
        NavHostFragment.findNavController(this).navigate(R.id.action_mainFragment_to_detailItemFragment, bundle);
    }

    private void updatePhoneCartUI(int pos, String totalItemCount) {
        phonesAdapter.getPhoneList().get(pos).setCart("0");
        phonesAdapter.notifyItemChanged(pos);
    }

    @Override
    public void onCliickPhoneLike(int id) {
        reloadDialog.show();
        convertDaraToJson(id);
        AddToFavouriteApi addToFavouriteApi = APIClient.getClient(SERVER_API_TEST).create(AddToFavouriteApi.class);
        Call<GetAddToFavouriteResponse> getAddToFavouriteResponseCall;
        if (PreferenceUtils.getCompanyLogin(getContext())) {
            getAddToFavouriteResponseCall = addToFavouriteApi.getAddToFavouritecompany(post);
        } else {
            getAddToFavouriteResponseCall = addToFavouriteApi.getAddToFavourite(post);
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
    public void onAddToPhoneCart(int id, int position) {
        reloadDialog.show();
        prepareCartMap(id);
        AddToCardApi addToCardApi = APIClient.getClient(SERVER_API_TEST).create(AddToCardApi.class);
        Call<GetAddToCardResponse> getAddToCardResponseCall;
        if (PreferenceUtils.getCompanyLogin(getContext())) {
            post.put("language", "arabic");
            getAddToCardResponseCall = addToCardApi.getAddToCartcompany(post);
        } else {
            getAddToCardResponseCall = addToCardApi.getAddToCart(post);
        }
        getAddToCardResponseCall.enqueue(new Callback<GetAddToCardResponse>() {
            @Override
            public void onResponse(Call<GetAddToCardResponse> call, Response<GetAddToCardResponse> response) {
                GetAddToCardResponse getAddToCardResponse = response.body();
                if (getAddToCardResponse.getCode() == 200) {
                    Toasty.success(getContext(), getString(R.string.add_to_card), Toast.LENGTH_LONG).show();
                    phonesAdapter.getPhoneList().get(position).setCart("0");
                    phonesAdapter.notifyItemChanged(position);
                    fragment_main_basket_top_txt.setText(getAddToCardResponse.getItemsCount());
                    fragment_main_basket_top_txt.setVisibility(View.VISIBLE);
                    PreferenceUtils.saveCountOfItemsBasket(getContext(), Integer.parseInt(getAddToCardResponse.getItemsCount()));
                    ((MainActivity) getActivity()).updateBasketBadge();
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
    public void onClickAcssesory(int id, int pos) {
        position_detail = pos;
        AddToCartCallback callback = new AddToCartCallback() {
            @Override
            public void setAddToCartResult(String totalItemCount) {
                updateAccessoryCartUI(pos, totalItemCount);
            }
        };
        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos);
        bundle.putInt("id", id);
        bundle.putParcelableArrayList("similar_products", home_category1);
        bundle.putString("getLike", home_category1.get(pos).getLike());
        bundle.putSerializable("added_to_cart", callback);

        NavHostFragment.findNavController(this).navigate(R.id.action_mainFragment_to_detailItemFragment, bundle);
//        NavHostFragment.findNavController(this).navigate(MainFragmentDirections.actionMainFragmentToDetailItemActivity(id, pos, home_category1.toArray(new ProductsCategory[home_category1.size()]),home_category1.get(pos).getLike()));
////        firstPageListener.onSwitchToNextFragment(bundle);
//        startActivityForResult(new Intent(getContext(), DetailItemFragment.class).putExtra("id", id).putExtra("similar_products", home_category1).putExtra("getLike", home_category1.get(pos).getLike()).putExtra("pos", pos), 11111);
//        Animatoo.animateSwipeLeft(getContext());
    }

    private void updateAccessoryCartUI(int pos, String totalItemCount) {
        accessoriesAdapter.getAccessoryList().get(pos).setCart("0");
        accessoriesAdapter.notifyItemChanged(pos);
    }

    @Override
    public void onCliickAccessoryLike(int id) {
        reloadDialog.show();
        convertDaraToJson(id);
        AddToFavouriteApi addToFavouriteApi = APIClient.getClient(SERVER_API_TEST).create(AddToFavouriteApi.class);
        Call<GetAddToFavouriteResponse> getAddToFavouriteResponseCall;
        if (PreferenceUtils.getCompanyLogin(getContext())) {
            getAddToFavouriteResponseCall = addToFavouriteApi.getAddToFavouritecompany(post);
        } else {
            getAddToFavouriteResponseCall = addToFavouriteApi.getAddToFavourite(post);
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
    public void onAddToAccessoryCart(int id, int position) {
        reloadDialog.show();
        prepareCartMap(id);
        AddToCardApi addToCardApi = APIClient.getClient(SERVER_API_TEST).create(AddToCardApi.class);
        Call<GetAddToCardResponse> getAddToCardResponseCall;
        if (PreferenceUtils.getCompanyLogin(getContext())) {
            post.put("language", "arabic");
            getAddToCardResponseCall = addToCardApi.getAddToCartcompany(post);
        } else {
            getAddToCardResponseCall = addToCardApi.getAddToCart(post);
        }
        getAddToCardResponseCall.enqueue(new Callback<GetAddToCardResponse>() {
            @Override
            public void onResponse(Call<GetAddToCardResponse> call, Response<GetAddToCardResponse> response) {
                GetAddToCardResponse getAddToCardResponse = response.body();
                if (getAddToCardResponse.getCode() == 200) {
                    if (getAddToCardResponse.getStatus()) {
                        Toasty.success(getContext(), getString(R.string.add_to_card), Toast.LENGTH_LONG).show();
                        accessoriesAdapter.getAccessoryList().get(position).setCart("0");
                        accessoriesAdapter.notifyItemChanged(position);
                        fragment_main_basket_top_txt.setText(getAddToCardResponse.getItemsCount());
                        fragment_main_basket_top_txt.setVisibility(View.VISIBLE);
                        if (getAddToCardResponse.getItemsCount() != null) {
                            PreferenceUtils.saveCountOfItemsBasket(getContext(), Integer.parseInt(getAddToCardResponse.getItemsCount()));
                            ((MainActivity) getActivity()).updateBasketBadge();
                        }
                    } else {
                        Toasty.error(getContext(), getAddToCardResponse.getMessage(), Toast.LENGTH_LONG).show();
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getBooleanExtra("similar_product_change", false)) {
            fragment_main_basket_top_txt.setText("" + PreferenceUtils.getCountOfItemsBasket(getContext()));
            onLoadPage();
            return;
        }
        if (requestCode == 111) {
            if (data.getBooleanExtra("databack", false)) {
                ProductsCategory productsCategory = home_category2.get(position_detail);
                productsCategory.setLike("1");
                home_category2.set(position_detail, productsCategory);
                phonesAdapter.notifyItemChanged(position_detail);
            }
            if (data.getBooleanExtra("added_to_cart", false)) {
                ProductsCategory productsCategory = home_category2.get(position_detail);
                productsCategory.setCart("0");
                home_category2.set(position_detail, productsCategory);
                phonesAdapter.notifyItemChanged(position_detail);
                fragment_main_basket_top_txt.setVisibility(View.VISIBLE);
                fragment_main_basket_top_txt.setText("" + PreferenceUtils.getCountOfItemsBasket(getContext()));

            }
        } else if (requestCode == 11111) {
            if (data.getBooleanExtra("databack", false)) {
                ProductsCategory productsCategory = home_category1.get(position_detail);
                productsCategory.setLike("1");
                home_category1.set(position_detail, productsCategory);
                accessoriesAdapter.notifyItemChanged(position_detail);
            }
            if (data.getBooleanExtra("added_to_cart", false)) {
                ProductsCategory productsCategory = home_category1.get(position_detail);
                productsCategory.setCart("0");
                home_category1.set(position_detail, productsCategory);
                accessoriesAdapter.notifyItemChanged(position_detail);
                fragment_main_basket_top_txt.setVisibility(View.VISIBLE);
                fragment_main_basket_top_txt.setText("" + PreferenceUtils.getCountOfItemsBasket(getContext()));
            }

        } else if (requestCode == 1111) {
            if (data.getBooleanExtra("databack", false)) {
                ProductsCategory productsCategory = home_category3.get(position_detail);
                productsCategory.setLike("1");
                home_category3.set(position_detail, productsCategory);
                category3Adapter.notifyItemChanged(position_detail);
            }
            if (data.getBooleanExtra("added_to_cart", false)) {
                ProductsCategory productsCategory = home_category3.get(position_detail);
                productsCategory.setCart("0");
                home_category3.set(position_detail, productsCategory);
                category3Adapter.notifyItemChanged(position_detail);
                fragment_main_basket_top_txt.setVisibility(View.VISIBLE);
                fragment_main_basket_top_txt.setText("" + PreferenceUtils.getCountOfItemsBasket(getContext()));
            }
        }
    }

    public void prepareCartMap(int id) {
        post = new HashMap<>();
        if (PreferenceUtils.getUserLogin(getContext())) {
            String token = PreferenceUtils.getUserToken(getContext());
            post.put("product_id", String.valueOf(id));
            post.put("token", token);
            post.put("quantity", "1");
        } else if (PreferenceUtils.getCompanyLogin(getContext())) {
            String token = PreferenceUtils.getCompanyToken(getContext());
            post.put("product_id", String.valueOf(id));
            post.put("token", token);
            post.put("quantity", "1");
        }
        reloadDialog.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        fragment_main_searchview.onActionViewCollapsed(); //to close the searchview
    }
}
