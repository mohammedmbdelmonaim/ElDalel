package com.zeidex.eldalel;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
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
import com.zeidex.eldalel.models.ColorProduct;
import com.zeidex.eldalel.models.ProductsCategory;
import com.zeidex.eldalel.response.GetAddToFavouriteResponse;
import com.zeidex.eldalel.response.GetDetailProduct;
import com.zeidex.eldalel.services.AddToFavouriteApi;
import com.zeidex.eldalel.services.DetailProduct;
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

import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class DetailItemActivity extends BaseActivity implements ProductsCategory3Adapter.ProductsCategory3Operation, DetailColorsItemAdapter.DetailColorsOperation {
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


    ProductsCategory3Adapter phonesAdapter;
    DetailSizeItemAdapter detailSizeItemAdapter;
    DetailColorsItemAdapter detailColorsItemAdapter;
    List<String> desc_names;
    DetailDescriptionsAdapter detailDescriptionsAdapter;

    @OnClick(R.id.detail_share_img)
    public void share() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);
        ButterKnife.bind(this);
        findViews();
        initializeRecycler();
        showDialog();
        onLoadPage();
    }

    @OnClick(R.id.item_detail_back)
    public void onBack(){
        onBackPressed();
    }

    Map<String, String> favourite_post;

    private void convertDaraToJson(int id) {
        favourite_post = new HashMap<>();
        if (PreferenceUtils.getUserLogin(this)) {
            String token = PreferenceUtils.getUserToken(this);
            favourite_post.put("product_id", String.valueOf(id));
            favourite_post.put("token", token);
        } else if (PreferenceUtils.getCompanyLogin(this)) {
            String token = PreferenceUtils.getCompanyToken(this);
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
    }


    public void onClickLike(int id){
         detail_like_img.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!PreferenceUtils.getUserLogin(DetailItemActivity.this) && isChecked && !PreferenceUtils.getCompanyLogin(DetailItemActivity.this)) {
                    Toasty.error(DetailItemActivity.this, getString(R.string.please_login_first), Toast.LENGTH_LONG).show();
                    detail_like_img.setChecked(false);
                    return;
                }
                if ((PreferenceUtils.getUserLogin(DetailItemActivity.this) || PreferenceUtils.getCompanyLogin(DetailItemActivity.this)) && !isChecked) {
                    Toasty.error(DetailItemActivity.this, getString(R.string.unlike_fiv), Toast.LENGTH_LONG).show();
                    detail_like_img.setChecked(true);
                    return;
                }
                if ((PreferenceUtils.getUserLogin(DetailItemActivity.this) || PreferenceUtils.getCompanyLogin(DetailItemActivity.this)) && isChecked) {
                    reloadDialog.show();
                    convertDaraToJson(id);
                    AddToFavouriteApi addToFavouriteApi = APIClient.getClient(SERVER_API_TEST).create(AddToFavouriteApi.class);
                    Call<GetAddToFavouriteResponse> getAddToFavouriteResponseCall = addToFavouriteApi.getAddToFavourite(favourite_post);
                    getAddToFavouriteResponseCall.enqueue(new Callback<GetAddToFavouriteResponse>() {
                        @Override
                        public void onResponse(Call<GetAddToFavouriteResponse> call, Response<GetAddToFavouriteResponse> response) {
                            GetAddToFavouriteResponse getAddToFavouriteResponse = response.body();
                            if (Integer.parseInt(getAddToFavouriteResponse.getCode()) == 200) {
                                Toasty.success(DetailItemActivity.this, getString(R.string.add_to_favourites), Toast.LENGTH_LONG).show();
                                isLike = true;
                                int pos = getIntent().getIntExtra("pos" , -1);
                                ArrayList<ProductsCategory>productsCategories =  getIntent().getParcelableArrayListExtra("similar_products");
                                ProductsCategory productsCategory = productsCategories.get(pos);
                                productsCategory.setLike("1");
                                productsCategories.set(pos, productsCategory);
                                phonesAdapter.notifyItemChanged(pos);
                            }
                            reloadDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<GetAddToFavouriteResponse> call, Throwable t) {
                            Toasty.error(DetailItemActivity.this, getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                            reloadDialog.dismiss();
                        }
                    });
                }
            }
        });
    }


    boolean isLike = false;


    @Override
    public void onBackPressed() {
        if (getIntent().getBooleanExtra("samethis" , false)){
            super.onBackPressed();
        }else {
            Intent intent = new Intent();
            intent.putExtra("databack", isLike);
            setResult(RESULT_OK, intent);
            finish();
            Animatoo.animateSwipeRight(this);
        }

    }

    public void initializeRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        detail_recycler_colors_item.setLayoutManager(layoutManager);
        detail_recycler_colors_item.setItemAnimator(new DefaultItemAnimator());

        detail_recycler_size_item.setLayoutManager(layoutManager2);
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
    ArrayList<String> capicities;

    public void onLoadPage() {
        int id = getIntent().getIntExtra("id", 0);
        images = new ArrayList<>();
        colors = new ArrayList<>();
        capicities = new ArrayList<>();
        reloadDialog.show();
        getDetarServer(id, false);
    }

    public void getDetarServer(int id, boolean flag) {
        images = new ArrayList<>();
        DetailProduct detailProduct = APIClient.getClient(SERVER_API_TEST).create(DetailProduct.class);
        Call<GetDetailProduct> getDetailProductCall = detailProduct.getDetailProduct(id);
        getDetailProductCall.enqueue(new Callback<GetDetailProduct>() {
            @Override
            public void onResponse(Call<GetDetailProduct> call, Response<GetDetailProduct> response) {
                GetDetailProduct getDetailProduct = response.body();
                int code = Integer.parseInt(getDetailProduct.getCode());
                if (code == 200) {
                    for (int i = 0; i < getDetailProduct.getData().getProduct().getPhotos().size(); i++) {
                        images.add("https://www.dleel-sh.com/homepages/get/" + getDetailProduct.getData().getProduct().getPhotos().get(i).getFilename());
                    }
                    if (flag) {
                        slider_adapter = new SliderAdapter(DetailItemActivity.this, images);
                        imageSlider.setSliderAdapter(slider_adapter);
                        imageSlider.startAutoCycle();
                        return;
                    }

                    if (getDetailProduct.getData().getProduct().getDiscount() == null) {
                        detailـdiscount_linear.setVisibility(View.GONE);
                    } else {
                        detailـdiscount_linear.setVisibility(View.VISIBLE);
                        detail_discound_text.setText(getDetailProduct.getData().getProduct().getDiscount());
                    }

                    if (getDetailProduct.getData().getProduct().getOld_price() == null) {
                        detail_item_text_price_before_linear.setVisibility(View.GONE);

                    } else {
                        detail_item_text_price_before_linear.setVisibility(View.VISIBLE);
                        detail_item_text_price_before.setText(getDetailProduct.getData().getProduct().getOld_price());
                    }


                    detail_item_text_price.setText(getDetailProduct.getData().getProduct().getPrice());

                    Locale locale = ChangeLang.getLocale(getResources());
                    String loo = locale.getLanguage();
                    if (loo.equalsIgnoreCase("en")) {
                        detail_title_header_text.setText(getDetailProduct.getData().getProduct().getName());
                        detail_name_text.setText(getDetailProduct.getData().getProduct().getSubcategory().getName());
                        detail_sescription_text.setText(getDetailProduct.getData().getProduct().getName());
                        full_desc = getDetailProduct.getData().getProduct().getShortDesc();

                    } else if (loo.equalsIgnoreCase("ar")) {
                        detail_title_header_text.setText(getDetailProduct.getData().getProduct().getName_ar());
                        detail_name_text.setText(getDetailProduct.getData().getProduct().getSubcategory().getName_ar());
                        detail_sescription_text.setText(getDetailProduct.getData().getProduct().getName_ar());
                        full_desc = getDetailProduct.getData().getProduct().getShortDescAr();
                    }

                    for (int i = 0; i < getDetailProduct.getData().getProduct().getOptiongroups().size(); i++) {
                        if (getDetailProduct.getData().getProduct().getOptiongroups().get(i).getFeatures() == null || getDetailProduct.getData().getProduct().getOptiongroups().get(i).getName() == null || getDetailProduct.getData().getProduct().getOptiongroups().get(i).getName().equalsIgnoreCase("")) {
                            continue;
                        }
                        desc_options = desc_options + (getDetailProduct.getData().getProduct().getOptiongroups().get(i).getName() + " : " + getDetailProduct.getData().getProduct().getOptiongroups().get(i).getFeatures().get(0) + "\n");
                    }


                    for (int i = 0; i < getDetailProduct.getData().getProduct().getColors().size(); i++) {
                        colors.add(new ColorProduct(getDetailProduct.getData().getProduct().getColors().get(i).getProduct_id(), getDetailProduct.getData().getProduct().getColors().get(i).getName()));
                    }

                    for (int i = 0; i < getDetailProduct.getData().getProduct().getCapacities().size(); i++) {
                        capicities.add(getDetailProduct.getData().getProduct().getCapacities().get(i));
                    }



                    detailSizeItemAdapter = new DetailSizeItemAdapter(DetailItemActivity.this, capicities);
                    detailColorsItemAdapter = new DetailColorsItemAdapter(DetailItemActivity.this, colors);
                    detailColorsItemAdapter.setDetailColorsOperation(DetailItemActivity.this);

                    detail_recycler_size_item.setAdapter(detailSizeItemAdapter);
                    detail_recycler_colors_item.setAdapter(detailColorsItemAdapter);

                    slider_adapter = new SliderAdapter(DetailItemActivity.this, images);
                    imageSlider.setSliderAdapter(slider_adapter);
                    imageSlider.startAutoCycle();

                    String alredy_like = getIntent().getStringExtra("getLike");

                    if (alredy_like == null) {
                    } else if (Integer.parseInt(alredy_like) == 1) {
                        detail_like_img.setChecked(true);
                    } else {
                        detail_like_img.setChecked(false);
                    }

                    phonesAdapter = new ProductsCategory3Adapter(DetailItemActivity.this, getIntent().getParcelableArrayListExtra("similar_products"));
                    phonesAdapter.setProductsCategory3Operation(DetailItemActivity.this);
                    details_recycler_like_too.setAdapter(phonesAdapter);

                    detailDescriptionsAdapter = new DetailDescriptionsAdapter(desc_names, getSupportFragmentManager(), desc_options, full_desc);
                    detail_vpPager.setAdapter(detailDescriptionsAdapter);
                    onClickLike(Integer.parseInt(getDetailProduct.getData().getProduct().getId()));
                }


                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetDetailProduct> call, Throwable t) {
                Toasty.error(DetailItemActivity.this, getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }


    Dialog reloadDialog;

    private void showDialog() {
        reloadDialog = new Dialog(this);
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onClickColor(int position) {
        getDetarServer(Integer.parseInt(colors.get(position).getProduct_id()), true);
    }

    @Override
    public void onClickProduct3(int id, int pos) {
        ProductsCategory productsCategory = (ProductsCategory) getIntent().getParcelableArrayListExtra("similar_products").get(pos);
        String like = productsCategory.getLike();
        startActivity(new Intent(this, DetailItemActivity.class).putExtra("id", id).putExtra("similar_products", getIntent().getParcelableArrayListExtra("similar_products")).putExtra("getLike" , like).putExtra("pos" , pos).putExtra("samethis",true));
        Animatoo.animateSwipeLeft(this);
    }

    @Override
    public void onCliickProductsCategory3Like(int id) {
        reloadDialog.show();
        convertDaraToJson(id);
        AddToFavouriteApi addToFavouriteApi = APIClient.getClient(SERVER_API_TEST).create(AddToFavouriteApi.class);
        Call<GetAddToFavouriteResponse> getAddToFavouriteResponseCall = addToFavouriteApi.getAddToFavourite(favourite_post);
        getAddToFavouriteResponseCall.enqueue(new Callback<GetAddToFavouriteResponse>() {
            @Override
            public void onResponse(Call<GetAddToFavouriteResponse> call, Response<GetAddToFavouriteResponse> response) {
                GetAddToFavouriteResponse getAddToFavouriteResponse = response.body();
                if (Integer.parseInt(getAddToFavouriteResponse.getCode()) == 200) {
                    Toasty.success(DetailItemActivity.this, getString(R.string.add_to_favourites), Toast.LENGTH_LONG).show();
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetAddToFavouriteResponse> call, Throwable t) {
                Toasty.error(DetailItemActivity.this, getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    @Override
    public void onAddToProductCategory3Cart(int id, int position) {

    }
}
