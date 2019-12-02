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
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.zeidex.eldalel.adapters.DetailColorsItemAdapter;
import com.zeidex.eldalel.adapters.DetailDescriptionsAdapter;
import com.zeidex.eldalel.adapters.DetailSizeItemAdapter;
import com.zeidex.eldalel.adapters.ProductsCategory3Adapter;
import com.zeidex.eldalel.adapters.SliderAdapter;
import com.zeidex.eldalel.listeners.FirstPageFragmentListener;
import com.zeidex.eldalel.listeners.AddToCartCallback;
import com.zeidex.eldalel.models.CapacityProduct;
import com.zeidex.eldalel.models.ColorProduct;
import com.zeidex.eldalel.models.ProductsCategory;
import com.zeidex.eldalel.models.Subcategory;
import com.zeidex.eldalel.models.Subsubcategory;
import com.zeidex.eldalel.response.GetAddToCardResponse;
import com.zeidex.eldalel.response.GetAddToFavouriteResponse;
import com.zeidex.eldalel.response.GetAllCategories;
import com.zeidex.eldalel.response.GetDetailProduct;
import com.zeidex.eldalel.services.AddToCardApi;
import com.zeidex.eldalel.services.AddToFavouriteApi;
import com.zeidex.eldalel.services.AllCategoriesAPI;
import com.zeidex.eldalel.services.DetailProduct;
import com.zeidex.eldalel.utils.APIClient;
import com.zeidex.eldalel.utils.Animatoo;
import com.zeidex.eldalel.utils.ChangeLang;
import com.zeidex.eldalel.utils.PreferenceUtils;
import com.zeidex.eldalel.utils.PriceFormatter;

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
import static com.zeidex.eldalel.SearchActivity.SEARCH_NAME_ARGUMENT;
import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class DetailItemFragment extends Fragment implements ProductsCategory3Adapter.ProductsCategory3Operation, DetailColorsItemAdapter.DetailColorsOperation, DetailSizeItemAdapter.DetailCapacityOperation {
    public static final int CART_NOT_EMPTY = 1;
    @BindView(R.id.imageSlider)
    SliderView imageSlider;

    @BindView(R.id.detail_recycler_size_item)
    RecyclerView detail_recycler_size_item;

    @BindView(R.id.detail_recycler_colors_item)
    RecyclerView detail_recycler_colors_item;

    @BindView(R.id.details_recycler_like_too)
    RecyclerView details_recycler_like_too;

    @BindView(R.id.detail_vpPager)
    ViewPager detail_vpPager;

    @BindView(R.id.detail_view_pager_tab)
    TabLayout detail_view_pager_tab;

    @BindView(R.id.detail_discound_text)
    AppCompatTextView detail_discound_text;

    @BindView(R.id.detail_like_img)
    AppCompatCheckBox detail_like_img;

    @BindView(R.id.detail_name_text)
    AppCompatTextView detail_name_text;

    @BindView(R.id.detail_sescription_text)
    AppCompatTextView detail_sescription_text;

    @BindView(R.id.detail_item_text_price_before)
    AppCompatTextView detail_item_text_price_before;

    @BindView(R.id.detail_title_header_text)
    AppCompatTextView detail_title_header_text;

    @BindView(R.id.detail_item_text_price)
    AppCompatTextView detail_item_text_price;

    @BindView(R.id.detail_item_text_price_before_label)
    AppCompatTextView detail_item_text_price_before_label;

    @BindView(R.id.details_quantaty_edit)
    AppCompatEditText details_quantaty_edit;

    @BindView(R.id.details_add_to_card)
    AppCompatTextView details_add_to_card;

    @BindView(R.id.detail_item_text_price_before_linear)
    LinearLayoutCompat detail_item_text_price_before_linear;

    @BindView(R.id.detailـdiscount_linear)
    LinearLayoutCompat detailـdiscount_linear;

    @BindView(R.id.detail_text_price_before_view)
    View detail_text_price_before_view;

    @BindView(R.id.detail_text_price_before_label_view)
    View detail_text_price_before_label_view;

    @BindView(R.id.cart_count_linear_layout)
    LinearLayoutCompat cartCountLinearLayout;

    @BindView(R.id.details_add_to_cart_whole_layout)
    LinearLayoutCompat details_add_to_cart_whole_layout;

    @BindView(R.id.detail_linear_size_item)
    LinearLayoutCompat detail_linear_size_item;

    @BindView(R.id.details_like_too)
    AppCompatTextView details_like_too;

    @BindView(R.id.detail_search_header_categories_img)
    SearchView detail_search_header_categories_img;

    ProductsCategory3Adapter phonesAdapter;
    DetailSizeItemAdapter detailSizeItemAdapter;
    DetailColorsItemAdapter detailColorsItemAdapter;
    List<String> desc_names;
    DetailDescriptionsAdapter detailDescriptionsAdapter;

    String token = "";
    private int productId;
    private GetDetailProduct.Product currentProduct;
    private boolean isAdded;
    private boolean isChanged;
    Fragment frag = null;

    FirstPageFragmentListener mFirstPageFragmentListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_item, container, false);
        ButterKnife.bind(this, view);
        findViews();
        initializeRecycler();
        showDialog();
        onLoadPage();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavHostFragment.findNavController(DetailItemFragment.this).popBackStack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @OnClick(R.id.item_detail_back)
    public void onBack() {
//        if (mFirstPageFragmentListener != null) {
//            mFirstPageFragmentListener.onSwitchToNextFragment(null);
//        onBackPressed();
//        }
        NavHostFragment.findNavController(DetailItemFragment.this).navigateUp();
    }

    @OnClick(R.id.detail_share_img)
    public void share() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, currentProduct.getUrl());
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);

    }

    Map<String, String> favourite_post;

    private void convertDaraToJson(int id) {
        favourite_post = new HashMap<>();
        if (PreferenceUtils.getUserLogin(getContext())) {
            String token = PreferenceUtils.getUserToken(getContext());
            favourite_post.put("product_id", String.valueOf(id));
            favourite_post.put("token", token);
        } else if (PreferenceUtils.getCompanyLogin(getContext())) {
            String token = PreferenceUtils.getCompanyToken(getContext());
            favourite_post.put("product_id", String.valueOf(id));
            favourite_post.put("token", token);
        }
    }

    public void findViews() {
        imageSlider.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        imageSlider.setIndicatorSelectedColor(Color.parseColor("#AAAAAA"));
        imageSlider.setIndicatorUnselectedColor(Color.parseColor("#FAFAFA"));
        imageSlider.setScrollTimeInSec(4); //set scroll delay in seconds :

        detail_search_header_categories_img.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra(SEARCH_NAME_ARGUMENT, query);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                Animatoo.animateSwipeLeft(getContext());
                detail_search_header_categories_img.onActionViewCollapsed(); //to close the searchview
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    public void onClickLike(int id) {
        detail_like_img.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!PreferenceUtils.getUserLogin(getContext()) && isChecked && !PreferenceUtils.getCompanyLogin(getContext())) {
                    Toasty.error(getContext(), getString(R.string.please_login_first), Toast.LENGTH_LONG).show();
                    detail_like_img.setChecked(false);
                    return;
                }
                if ((PreferenceUtils.getUserLogin(getContext()) || PreferenceUtils.getCompanyLogin(getContext())) && !isChecked) {
                    Toasty.error(getContext(), getString(R.string.unlike_fiv), Toast.LENGTH_LONG).show();
                    detail_like_img.setChecked(true);
                    return;
                }
                if ((PreferenceUtils.getUserLogin(getContext()) || PreferenceUtils.getCompanyLogin(getContext())) && isChecked) {
                    reloadDialog.show();
                    convertDaraToJson(id);
                    AddToFavouriteApi addToFavouriteApi = APIClient.getClient(SERVER_API_TEST).create(AddToFavouriteApi.class);
                    Call<GetAddToFavouriteResponse> getAddToFavouriteResponseCall;
                    if (PreferenceUtils.getCompanyLogin(getContext())) {
                        getAddToFavouriteResponseCall = addToFavouriteApi.getAddToFavouritecompany(favourite_post);
                    } else {
                        getAddToFavouriteResponseCall = addToFavouriteApi.getAddToFavourite(favourite_post);
                    }
                    getAddToFavouriteResponseCall.enqueue(new Callback<GetAddToFavouriteResponse>() {
                        @Override
                        public void onResponse(Call<GetAddToFavouriteResponse> call, Response<GetAddToFavouriteResponse> response) {
                            GetAddToFavouriteResponse getAddToFavouriteResponse = response.body();
                            if (Integer.parseInt(getAddToFavouriteResponse.getCode()) == 200) {
                                Toasty.success(getContext(), getString(R.string.add_to_favourites), Toast.LENGTH_LONG).show();
                                isLike = true;
                                int pos = getArguments().getInt("pos");
                                if (getArguments().getParcelableArrayList("similar_products") != null) {
                                    ArrayList<ProductsCategory> productsCategories = getArguments().getParcelableArrayList("similar_products");
                                    if (productsCategories != null && productsCategories.size() > 0) {
                                        ProductsCategory productsCategory = productsCategories.get(pos);
                                        productsCategory.setLike("1");
                                        productsCategories.set(pos, productsCategory);
                                        phonesAdapter.notifyItemChanged(pos);
                                    }
                                }
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
            }
        });
    }


    boolean isLike = false;


//    @Override
//    public void onBackPressed() {
//        if (getArguments().getBooleanExtra("samethis", false)) {
//            super.onBackPressed();
//        } else {
//            Intent intent = new Intent();
//            intent.putExtra("databack", isLike);
//            intent.putExtra("added_to_cart", isAdded);
//            intent.putExtra("similar_product_change", isChanged);
//            setResult(RESULT_OK, intent);
//            finish();
//            Animatoo.animateSwipeRight(this);
//        }
//
//    }

    public void initializeRecycler() {
        RecyclerView.LayoutManager LayoutManager = new GridLayoutManager(getContext(), 3);
        RecyclerView.LayoutManager LayoutManager2 = new GridLayoutManager(getContext(), 3);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        detail_recycler_colors_item.setLayoutManager(LayoutManager);
        detail_recycler_colors_item.setItemAnimator(new DefaultItemAnimator());

        detail_recycler_size_item.setLayoutManager(LayoutManager2);
        detail_recycler_size_item.setItemAnimator(new DefaultItemAnimator());

        details_recycler_like_too.setLayoutManager(layoutManager3);
        details_recycler_like_too.setItemAnimator(new DefaultItemAnimator());

        desc_names = new ArrayList<>();
        desc_names.add(getString(R.string.desc_product_label));
        desc_names.add(getString(R.string.full_desc_product_label));
    }

    String desc_options = "", full_desc = "";
    ArrayList<String> images;
    SliderAdapter slider_adapter;
    ArrayList<ColorProduct> colors;
    ArrayList<CapacityProduct> capicities;

    public void onLoadPage() {
        productId = getArguments().getInt("id");
        images = new ArrayList<>();
        colors = new ArrayList<>();
        capicities = new ArrayList<>();

        if (PreferenceUtils.getUserLogin(getContext())) {
            token = PreferenceUtils.getUserToken(getContext());
        } else if (PreferenceUtils.getCompanyLogin(getContext())) {
            token = PreferenceUtils.getCompanyToken(getContext());
        }

        reloadDialog.show();
        getDetarServer(productId, false);
    }

    int categoryId;
    String category_name;
    ArrayList<Subcategory> subCategoriesModel;

    @OnClick(R.id.detail_name_text)
    public void goToCategory() {
        subCategoriesModel = new ArrayList<>();
        getAllCategories(categoryId);
//        CategoriesFragment categoriesFragment = new CategoriesFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt("category_id", categoryId);
//        categoriesFragment.setArguments(bundle);
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.addToBackStack(null);
//        ft.setCustomAnimations(R.anim.animate_slide_up_enter, R.anim.animate_slide_up_exit);
//        ft.replace(R.id.linear_detail_replace, categoriesFragment, "categorie_fragment");
//        ft.commit();
    }


    private List<GetAllCategories.Category> categories;
    ArrayList<Subsubcategory> subsubcategories;
    int clickSubCategoryId;
    Subcategory clickSubCategory;

    private void getAllCategories(int category_id) {
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
                                    Locale locale = ChangeLang.getLocale(getResources());
                                    String loo = locale.getLanguage();
                                    if (loo.equalsIgnoreCase("en")) {
                                        category_name = category.getName();
                                    } else if (loo.equalsIgnoreCase("ar")) {
                                        category_name = category.getNameAr();
                                    }
                                    for (int j = 0; j < category.getSubcategories().size(); j++) {

                                        if (category.getSubcategories().get(j).getId() == clickSubCategoryId) {
                                            clickSubCategory = new Subcategory(category.getSubcategories().get(j).getId(), category.getSubcategories().get(j).getNameAr(),
                                                    category.getSubcategories().get(j).getName(), category.getSubcategories().get(j).getPhoto(), subsubcategories);
                                            subCategoriesModel.add(0, clickSubCategory);
                                            continue;
                                        }
                                        subCategoriesModel.add(new Subcategory(category.getSubcategories().get(j).getId(), category.getSubcategories().get(j).getNameAr(),
                                                category.getSubcategories().get(j).getName(), category.getSubcategories().get(j).getPhoto(), subsubcategories));
                                    }
                                    break;
                                }
                            }

                        }
                        startActivity(new Intent(getContext(), OffersItemsActivity.class).putExtra(CATEGORY_ID_INTENT_EXTRA_KEY, category_id).putExtra(CATEGORY_NAME_INTENT_EXTRA, category_name)
                                .putExtra(SUBCATEGORIES_INTENT_EXTRA_KEY, subCategoriesModel));
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
    String lang;
    public void getDetarServer(int id, boolean flag) {
        Locale locale = ChangeLang.getLocale(getResources());
        String loo = locale.getLanguage();
        if (loo.equalsIgnoreCase("en")) {
            lang = "en";
        }else if (loo.equalsIgnoreCase("ar")) {
            lang = "ar";
        }
        images = new ArrayList<>();
        DetailProduct detailProduct = APIClient.getClient(SERVER_API_TEST).create(DetailProduct.class);
        Call<GetDetailProduct> getDetailProductCall = detailProduct.getDetailProduct(id, token , lang);
        getDetailProductCall.enqueue(new Callback<GetDetailProduct>() {
            @Override
            public void onResponse(Call<GetDetailProduct> call, Response<GetDetailProduct> response) {
                if (response.body() != null) {
                    GetDetailProduct getDetailProduct = response.body();
                    int code = Integer.parseInt(getDetailProduct.getCode());
                    if (code == 200) {
                        currentProduct = getDetailProduct.getData().getProduct();
                        for (int i = 0; i < currentProduct.getPhotos().size(); i++) {
                            images.add("https://www.dleel-sh.com/homepages/get/" + getDetailProduct.getData().getProduct().getPhotos().get(i).getFilename());
                        }
                        if (flag) {
                            slider_adapter = new SliderAdapter(getContext(), images);
                            imageSlider.setSliderAdapter(slider_adapter);
                            imageSlider.startAutoCycle();
                        }

                        if (currentProduct.getDiscount() == null) {
                            detailـdiscount_linear.setVisibility(View.INVISIBLE);
                        } else {
                            detailـdiscount_linear.setVisibility(View.VISIBLE);
                            detail_discound_text.setText(getDetailProduct.getData().getProduct().getDiscount());
                        }

                        if (currentProduct.getOld_price() == null) {
                            detail_item_text_price_before_linear.setVisibility(View.GONE);

                        } else {
                            detail_item_text_price_before_linear.setVisibility(View.VISIBLE);
                            detail_item_text_price_before.setText(PriceFormatter.toDecimalString(getDetailProduct.getData().getProduct().getOld_price(), getContext()));
                        }
                        if (!token.equalsIgnoreCase("")) {

                            int cartStatus = Integer.parseInt(currentProduct.getCart());
                            if (cartStatus == 2) {
                                details_add_to_card.setBackgroundColor(Color.parseColor("#B2B4B4"));
                                details_add_to_card.setText(R.string.cart_out_of_stock_label);
                                cartCountLinearLayout.setVisibility(View.GONE);
                            } else if (cartStatus == 0) {
                                details_add_to_card.setBackgroundColor(Color.parseColor("#46C004"));
                                details_add_to_card.setText(R.string.add_to_card);
                                cartCountLinearLayout.setVisibility(View.GONE);

                            } else {
                                details_add_to_card.setBackgroundColor(Color.parseColor("#047AC0"));
                                details_add_to_card.setText(R.string.phone_row_add_to_card_txt);
                                details_add_to_card.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (Integer.parseInt(details_quantaty_edit.getText().toString()) > 0) {
                                            addToCart();
                                        } else {
                                            Toasty.error(getContext(), getString(R.string.cart_no_quantity_chosen_toast), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                                cartCountLinearLayout.setVisibility(View.VISIBLE);
                            }

                            details_add_to_cart_whole_layout.setVisibility(View.VISIBLE);
                        }

                        categoryId = Integer.parseInt(getDetailProduct.getData().getProduct().getSubcategory().getCategory_id());

                        detail_item_text_price.setText(PriceFormatter.toDecimalString(getDetailProduct.getData().getProduct().getPrice(), getContext()));

                        Locale locale = ChangeLang.getLocale(getResources());
                        String loo = locale.getLanguage();
                        if (loo.equalsIgnoreCase("en")) {
                            category_name = getDetailProduct.getData().getProduct().getName();
                            detail_title_header_text.setText(getDetailProduct.getData().getProduct().getName());
                            detail_name_text.setText(getDetailProduct.getData().getProduct().getSubcategory().getName());
                            detail_sescription_text.setText(getDetailProduct.getData().getProduct().getName());
                            full_desc = getDetailProduct.getData().getProduct().getShortDesc();

                        } else if (loo.equalsIgnoreCase("ar")) {
                            category_name = getDetailProduct.getData().getProduct().getName_ar();
                            detail_title_header_text.setText(getDetailProduct.getData().getProduct().getName_ar());
                            detail_name_text.setText(getDetailProduct.getData().getProduct().getSubcategory().getName_ar());
                            detail_sescription_text.setText(getDetailProduct.getData().getProduct().getName_ar());
                            full_desc = getDetailProduct.getData().getProduct().getShortDescAr();
                        }

                        clickSubCategoryId = Integer.parseInt(getDetailProduct.getData().getProduct().getSubcategory_id());

                        desc_options = getDetailProduct.getData().getProduct().getDescription();


//                        for (int i = 0; i < getDetailProduct.getData().getProduct().getOptiongroups().size(); i++) {
//                            if (getDetailProduct.getData().getProduct().getOptiongroups().get(i).getFeatures() == null || getDetailProduct.getData().getProduct().getOptiongroups().get(i).getName() == null || getDetailProduct.getData().getProduct().getOptiongroups().get(i).getName().equalsIgnoreCase("")) {
//                                continue;
//                            }
//                            desc_options = desc_options + (getDetailProduct.getData().getProduct().getOptiongroups().get(i).getName() + " : " + getDetailProduct.getData().getProduct().getOptiongroups().get(i).getFeatures().get(0) + "\n");
//                        }


                        if (!flag) {
                            for (int i = 0; i < currentProduct.getColors().size(); i++) {
                                colors.add(new ColorProduct(currentProduct.getColors().get(i).getProduct_id(), currentProduct.getColors().get(i).getName(), currentProduct.getColors().get(i).getPhoto()));
                            }

                            for (int i = 0; i < currentProduct.getCapacities().size(); i++) {
                                capicities.add(new CapacityProduct(currentProduct.getCapacities().get(i).getProduct_id(), currentProduct.getCapacities().get(i).getName()));
                            }

                            if (capicities.size() > 0) {
                                detail_linear_size_item.setVisibility(View.VISIBLE);
                                detailSizeItemAdapter = new DetailSizeItemAdapter(getContext(), capicities);
                                detailSizeItemAdapter.setDetailCapacityOperation(DetailItemFragment.this);
                                detail_recycler_size_item.setAdapter(detailSizeItemAdapter);
                            }
                            detailColorsItemAdapter = new DetailColorsItemAdapter(getContext(), colors);
                            detailColorsItemAdapter.setDetailColorsOperation(DetailItemFragment.this);


                            detail_recycler_colors_item.setAdapter(detailColorsItemAdapter);

                            slider_adapter = new SliderAdapter(getContext(), images);
                            imageSlider.setSliderAdapter(slider_adapter);
                            imageSlider.startAutoCycle();
                        }

//                        String alredy_like = getArguments().getString("getLike");
//
//                        if (alredy_like == null || alredy_like.equals("")) {
//                        } else if (Integer.parseInt(alredy_like) == 1) {
//                            detail_like_img.setChecked(true);
//                        } else {
//                            detail_like_img.setChecked(false);
//                        }

                        if (getDetailProduct.getData().getProduct().getFavorite() == null) {
                        } else if (Integer.parseInt(getDetailProduct.getData().getProduct().getFavorite()) == 1) {
                            detail_like_img.setChecked(true);
                        } else {
                            detail_like_img.setChecked(false);
                        }

                        ArrayList<ProductsCategory> similarProducts = getArguments().getParcelableArrayList("similar_products");
                        if (similarProducts != null && similarProducts.size() > 0) {
                            phonesAdapter = new ProductsCategory3Adapter(getContext(), similarProducts);
                            phonesAdapter.setProductsCategory3Operation(DetailItemFragment.this);
                            details_recycler_like_too.setAdapter(phonesAdapter);
                        } else {
                            details_like_too.setVisibility(View.GONE);
                        }
                        detailDescriptionsAdapter = new DetailDescriptionsAdapter(desc_names, getActivity().getSupportFragmentManager(), desc_options, full_desc);
                        detail_vpPager.setAdapter(detailDescriptionsAdapter);
                        onClickLike(Integer.parseInt(getDetailProduct.getData().getProduct().getId()));

                    }
                }

                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetDetailProduct> call, Throwable t) {
                Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
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
    public void onClickColor(int position) {
        reloadDialog.show();
        getDetarServer(Integer.parseInt(colors.get(position).getProduct_id()), true);
    }

    @Override
    public void onClickProduct3(int id, int pos) {
        ArrayList<ProductsCategory> products = getArguments().getParcelableArrayList("similar_products");
        if (products != null) {
            ProductsCategory productsCategory = products.get(pos);
            String like = productsCategory.getLike();
//            Bundle bundle = new Bundle();
//            bundle.putInt("id", id);
//            bundle.putParcelableArrayList("similar_products", products);
//            bundle.putInt("pos", pos);
//            bundle.putString("getLike",like);
//            NavHostFragment.findNavController(this).navigate(R.id.action_detailItemActivity_self, bundle);
        startActivity(new Intent(getContext(), DetailItemActivity.class).putExtra("id", id).putExtra("similar_products", getArguments().getParcelableArray("similar_products")).putExtra("getLike", like).putExtra("pos", pos).putExtra("samethis", true));
        Animatoo.animateSwipeLeft(getContext());
        }
    }

    @Override
    public void onCliickProductsCategory3Like(int id, int pos) {
        reloadDialog.show();
        convertDaraToJson(id);
        AddToFavouriteApi addToFavouriteApi = APIClient.getClient(SERVER_API_TEST).create(AddToFavouriteApi.class);
        Call<GetAddToFavouriteResponse> getAddToFavouriteResponseCall;
        if (PreferenceUtils.getCompanyLogin(getContext())) {
            getAddToFavouriteResponseCall = addToFavouriteApi.getAddToFavouritecompany(favourite_post);
        } else {
            getAddToFavouriteResponseCall = addToFavouriteApi.getAddToFavourite(favourite_post);
        }
        getAddToFavouriteResponseCall.enqueue(new Callback<GetAddToFavouriteResponse>() {
            @Override
            public void onResponse(Call<GetAddToFavouriteResponse> call, Response<GetAddToFavouriteResponse> response) {
                GetAddToFavouriteResponse getAddToFavouriteResponse = response.body();
                if (Integer.parseInt(getAddToFavouriteResponse.getCode()) == 200) {
                    Toasty.success(getContext(), getString(R.string.add_to_favourites), Toast.LENGTH_LONG).show();
                    isChanged = true;
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
        prepareCartMap(id, "1");
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
                    phonesAdapter.getProductsCategoryList().get(position).setCart("0");
                    phonesAdapter.notifyItemChanged(position);
                    isChanged = true;
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

    Map<String, String> post;

    public void addToCart() {
        int quantity = Integer.parseInt(details_quantaty_edit.getText().toString());
        if (quantity > currentProduct.getAvailableQuantity()) {
            Toasty.error(getContext(), getString(R.string.quantity_not_available_toast) + " " + currentProduct.getAvailableQuantity(), Toast.LENGTH_LONG).show();
            reloadDialog.dismiss();
            return;
        }
        reloadDialog.show();
        prepareCartMap(productId, details_quantaty_edit.getText().toString());
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
                if (response.body() != null) {
                    GetAddToCardResponse getAddToCardResponse = response.body();
                    if (getAddToCardResponse.getCode() == 200 && getAddToCardResponse.getItemsCount() != null) {
//                        Toasty.success(DetailItemFragment.this, getString(R.string.add_to_card), Toast.LENGTH_LONG).show();
                        details_add_to_card.setBackgroundColor(Color.parseColor("#46C004"));
                        details_add_to_card.setText(R.string.add_to_card);
                        details_add_to_card.setEnabled(false);
                        cartCountLinearLayout.setVisibility(View.GONE);
                        isAdded = true;
                        PreferenceUtils.saveCountOfItemsBasket(getContext(), Integer.parseInt(getAddToCardResponse.getItemsCount()));
                        ((MainActivity) getActivity()).updateBasketBadge();
                        AddToCartCallback callback = (AddToCartCallback) getArguments().getSerializable("added_to_cart");
                        if(callback != null)
                        callback.setAddToCartResult(getAddToCardResponse.getItemsCount());
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

    public void prepareCartMap(int id, String quantity) {
        post = new HashMap<>();
        if (PreferenceUtils.getUserLogin(getContext())) {
            String token = PreferenceUtils.getUserToken(getContext());
            post.put("product_id", String.valueOf(id));
            post.put("token", token);
            post.put("quantity", String.valueOf(quantity));
        } else if (PreferenceUtils.getCompanyLogin(getContext())) {
            String token = PreferenceUtils.getCompanyToken(getContext());
            post.put("product_id", String.valueOf(id));
            post.put("token", token);
            post.put("quantity", String.valueOf(quantity));
        }
        reloadDialog.show();
    }

    @Override
    public void onClickCapacity(int position) {
        reloadDialog.show();
        getDetarServer(Integer.parseInt(capicities.get(position).getProduct_id()), true);
    }
}
