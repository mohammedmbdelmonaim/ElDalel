package com.zeidex.eldalel;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zeidex.eldalel.utils.Animatoo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zeidex.eldalel.FilterActivity.FILTER_CATEGORY_ID;
import static com.zeidex.eldalel.FilterActivity.FILTER_HAS_OFFER;
import static com.zeidex.eldalel.FilterActivity.FILTER_IS_NEW_ARRIVAL;
import static com.zeidex.eldalel.FilterActivity.FILTER_PRICE_FROM;
import static com.zeidex.eldalel.FilterActivity.FILTER_PRICE_TO;
import static com.zeidex.eldalel.FilterActivity.FILTER_PRODUCT_NAME;
import static com.zeidex.eldalel.FilterActivity.FILTER_SUBCATEGORY_ID;
import static com.zeidex.eldalel.NewArrivalsFragment.NEW_ARRIVAL;
import static com.zeidex.eldalel.OfferItemActivity.OFFERS;

public class SearchActivity extends BaseActivity {
    public static final String FILTER_STATUS = "status";
    @BindView(R.id.item_filter_img)
    AppCompatImageView item_filter_img;
    @BindView(R.id.activity_search_header_text)
    AppCompatTextView activity_search_header_text;

    public static final String SEARCH_NAME_ARGUMENT = "search_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String searchName = intent.getStringExtra(SEARCH_NAME_ARGUMENT);


        int filterCategoryId = intent.getIntExtra(FILTER_CATEGORY_ID, -1);
        int filterSubcategoryId = intent.getIntExtra(FILTER_SUBCATEGORY_ID, -1);
        int filterPriceFrom = intent.getIntExtra(FILTER_PRICE_FROM, -1);
        int filterPriceTo = intent.getIntExtra(FILTER_PRICE_TO, -1);
        boolean filterHasOffer = intent.getBooleanExtra(FILTER_HAS_OFFER, false);
        boolean filterIsNewArrival = intent.getBooleanExtra(FILTER_IS_NEW_ARRIVAL, false);
        String filterSearchResults = intent.getStringExtra(FILTER_PRODUCT_NAME);


        Bundle bundle = new Bundle();
        if (searchName != null) {
            bundle.putString(SEARCH_NAME_ARGUMENT, searchName);
        } else {
            activity_search_header_text.setText(getString(R.string.activity_filter_header_text));
            item_filter_img.setVisibility(View.VISIBLE);
            bundle.putInt(FILTER_CATEGORY_ID, filterCategoryId);
            bundle.putInt(FILTER_SUBCATEGORY_ID, filterSubcategoryId);
            bundle.putInt(FILTER_PRICE_FROM, filterPriceFrom);
            bundle.putInt(FILTER_PRICE_TO, filterPriceTo);
            if(filterSearchResults != null) bundle.putString(FILTER_PRODUCT_NAME, filterSearchResults);
            if (filterHasOffer) bundle.putString(FILTER_STATUS, OFFERS);
            else if (filterIsNewArrival) bundle.putString(FILTER_STATUS, NEW_ARRIVAL);
        }

        ProductsFragment productsFragment = new ProductsFragment();
        productsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.search_products_results_container, productsFragment).commit();
    }

    @OnClick(R.id.item_search_back)
    public void onBack() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        finish();
        Animatoo.animateSwipeRight(this);
    }
}
