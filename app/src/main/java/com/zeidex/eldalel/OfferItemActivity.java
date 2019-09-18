package com.zeidex.eldalel;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.adapters.CategoryItemAdapter;
import com.zeidex.eldalel.adapters.SubCategoriesAdapter;
import com.zeidex.eldalel.models.Subcategory;
import com.zeidex.eldalel.models.Subsubcategory;
import com.zeidex.eldalel.response.GetCategorizedOffers;
import com.zeidex.eldalel.services.OffersAPI;
import com.zeidex.eldalel.utils.APIClient;
import com.zeidex.eldalel.utils.Animatoo;
import com.zeidex.eldalel.utils.GridSpacingItemDecoration;
import com.zeidex.eldalel.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zeidex.eldalel.OffersFragment.CATEGORY_NAME_INTENT_EXTRA;
import static com.zeidex.eldalel.OffersFragment.SUBCATEGORIES_INTENT_EXTRA_KEY;
import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class OfferItemActivity extends BaseActivity implements CategoryItemAdapter.CategoryOperation, SubCategoriesAdapter.SubCategoryOperation {

    public static final String OFFERS = "offers";
    @BindView(R.id.offer_item_recycler_categories)
    RecyclerView offer_item_recycler_categories;

    @BindView(R.id.offer_category_item_recycler_list)
    RecyclerView offer_category_item_recycler_list;

    @BindView(R.id.offer_item_header_text)
    AppCompatTextView offerItemHeaderText;

    @BindView(R.id.offer_no_items_layout)
    RelativeLayout offerNoItemsLayout;

    CategoryItemAdapter categoryAdapter;
    SubCategoriesAdapter subCategoriesAdapter;

    Dialog reloadDialog;
    String token = "";
    private List<Subcategory> subcategories;


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

    public void findViews() {
        if (PreferenceUtils.getCompanyLogin(this)) {
            token = PreferenceUtils.getCompanyToken(this);
        } else if (PreferenceUtils.getUserLogin(this)) {
            token = PreferenceUtils.getUserToken(this);
        }

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
            getSubcategoryProducts(subcategories.get(0).getId());
        }

    }

    public void getSubcategoryProducts(int subcategoryId) {
        reloadDialog.show();
        OffersAPI offersAPI = APIClient.getClient(SERVER_API_TEST).create(OffersAPI.class);
        offersAPI.getOffersProducts(OFFERS, subcategoryId, token).enqueue(new Callback<GetCategorizedOffers>() {
            @Override
            public void onResponse(Call<GetCategorizedOffers> call, Response<GetCategorizedOffers> response) {
                if (response.body() != null) {
                    int code = response.body().getCode();
                    if (code == 200) {
                        List<GetCategorizedOffers.Products.Data> offers = response.body().getProducts().getData();

                        if (offers.size() > 0) {
                            categoryAdapter = new CategoryItemAdapter(OfferItemActivity.this, offers);
                            categoryAdapter.setCategoryOperation(OfferItemActivity.this);
                            offer_category_item_recycler_list.setAdapter(categoryAdapter);
                            showRecycler();
                        }else{
                            showEmptyView();
                        }
                    }
                    reloadDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<GetCategorizedOffers> call, Throwable t) {
                Toasty.error(OfferItemActivity.this, getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    private void showEmptyView() {
        offer_category_item_recycler_list.setVisibility(View.GONE);
        offerNoItemsLayout.setVisibility(View.VISIBLE);
    }

    private void showRecycler() {
        offer_category_item_recycler_list.setVisibility(View.VISIBLE);
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
    public void onClickCategory(int position) {
        startActivity(new Intent(this, DetailItemActivity.class));
        Animatoo.animateSwipeLeft(this);
    }


    @Override
    public void onClickSubCategory(int subcategoryId, String subcategoryName) {
        getSubcategoryProducts(subcategoryId);
    }

    @Override
    public void onClickSubCategoryWithSubSub(ArrayList<Subsubcategory> subsubcategories, String subcategoryName) {

    }
}
