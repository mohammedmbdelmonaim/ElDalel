package com.zeidex.eldalel;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.zeidex.eldalel.adapters.SubcategoriesPagerAdapter;
import com.zeidex.eldalel.models.Subcategory;
import com.zeidex.eldalel.utils.ChangeLang;
import com.zeidex.eldalel.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zeidex.eldalel.NewArrivalsFragment.CATEGORY_NAME_INTENT_EXTRA;
import static com.zeidex.eldalel.NewArrivalsFragment.SUBCATEGORIES_INTENT_EXTRA_KEY;
import static com.zeidex.eldalel.OffersFragment.CATEGORY_ID_INTENT_EXTRA_KEY;

public class NewArrivalSubcategoriesActivity extends BaseActivity {

    @BindView(R.id.vpPager)
    ViewPager vpPager;
    @BindView(R.id.view_pager_tab)
    TabLayout view_pager_tab;
    @BindView(R.id.title_header_text)
    AppCompatTextView titleHeaderTextView;
    @BindView(R.id.products_framelayout)
    FrameLayout productsFrameLayout;

    List<String> cat_names;
    List<String> cat_ids;
    SubcategoriesPagerAdapter subcategoriesPagerAdapter;

    Dialog reloadDialog;
    String token = "";
    List<Subcategory> subcategories;
    private int categoryId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategories_new_arrival);
        ButterKnife.bind(this);
        LinearLayout linearLayout = (LinearLayout) view_pager_tab.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.GRAY);
        drawable.setSize(1, 1);
        linearLayout.setDividerPadding(30);
        linearLayout.setDividerDrawable(drawable);
        findViews();
    }

    public void findViews() {
        if (PreferenceUtils.getCompanyLogin(this)) {
            token = PreferenceUtils.getCompanyToken(this);
        } else if (PreferenceUtils.getUserLogin(this)) {
            token = PreferenceUtils.getUserToken(this);
        }

        cat_ids = new ArrayList<>();
        cat_names = new ArrayList<>();

        titleHeaderTextView.setText(getIntent().getStringExtra(CATEGORY_NAME_INTENT_EXTRA));

        showDialog();
        onLoadPage();
    }

    private void onLoadPage() {

        subcategories = getIntent().getParcelableArrayListExtra(SUBCATEGORIES_INTENT_EXTRA_KEY);
        categoryId = getIntent().getIntExtra(CATEGORY_ID_INTENT_EXTRA_KEY, -1);
        if (subcategories != null && subcategories.size() > 0) {
            initializeViewPager(); //with subcategories
        } else {
            vpPager.setVisibility(View.GONE);
            initializeProductsFragment(); //with category products
        }

    }

    private void initializeProductsFragment() {
        int categoryId = getIntent().getIntExtra(CATEGORY_ID_INTENT_EXTRA_KEY, -1);
        NewArrivalProductsFragment newArrivalProductsFragment = new NewArrivalProductsFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(CATEGORY_ID_INTENT_EXTRA_KEY, categoryId);
        newArrivalProductsFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.products_framelayout, newArrivalProductsFragment).commit();
        productsFrameLayout.setVisibility(View.VISIBLE);

    }

    public void initializeViewPager() {
        cat_ids.clear();
        cat_names.clear();

        Locale locale = ChangeLang.getLocale(getResources());
        String loo = locale.getLanguage();

        for (Subcategory subcategory : subcategories) {
            cat_ids.add(subcategory.getId() + "");
            if (loo.equalsIgnoreCase("ar")) {
                cat_names.add(subcategory.getNameAr());
            } else {
                cat_names.add(subcategory.getName());
            }
        }

        subcategoriesPagerAdapter = new SubcategoriesPagerAdapter(getSupportFragmentManager(), cat_ids, cat_names);
        vpPager.setAdapter(subcategoriesPagerAdapter);
        view_pager_tab.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void showDialog() {
        reloadDialog = new Dialog(this);
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @OnClick(R.id.item_fragment_back)
    public void onBack() {
        onBackPressed();
    }

}

