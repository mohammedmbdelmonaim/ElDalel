package com.zeidex.eldalel;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.zeidex.eldalel.adapters.CategoriesItemAdapter;
import com.zeidex.eldalel.models.Subcategory;
import com.zeidex.eldalel.models.Subsubcategory;
import com.zeidex.eldalel.utils.ChangeLang;
import com.zeidex.eldalel.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zeidex.eldalel.OffersFragment.CATEGORY_ID_INTENT_EXTRA_KEY;
import static com.zeidex.eldalel.SearchActivity.SEARCH_NAME_ARGUMENT;
import static com.zeidex.eldalel.SubCategoriesFragment.SUBCATEGORY_ARRAY_EXTRA_KEY;
import static com.zeidex.eldalel.SubCategoriesFragment.SUBCATEGORY_ID_EXTRA_KEY;
import static com.zeidex.eldalel.SubCategoriesFragment.SUBCATEGORY_NAME_EXTRA_KEY;

public class ProductsActivity extends BaseActivity {
    @BindView(R.id.title_header_text)
    AppCompatTextView titleHeaderText;
    @BindView(R.id.vpPager_item)
    ViewPager vpPager;
    @BindView(R.id.view_pager_tab_item)
    TabLayout view_pager_tab;
    @BindView(R.id.view_pager_tab_item_sub_category)
    TabLayout view_pager_tab_item_sub_category;
    @BindView(R.id.search_header_categories_img)
    SearchView search_header_categories_img;

    List<String> cat_names;
    List<String> cat_ids;
    CategoriesItemAdapter categoriesItemAdapter;

    String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_item);
        ButterKnife.bind(this);
        findViews();
    }

    private void findViews() {
        categoryId = getIntent().getIntExtra(CATEGORY_ID_INTENT_EXTRA_KEY, -1);
        subCategoryId = getIntent().getIntExtra(SUBCATEGORY_ID_EXTRA_KEY, -1);
        if (PreferenceUtils.getCompanyLogin(this)) {
            token = PreferenceUtils.getCompanyToken(this);
        } else if (PreferenceUtils.getUserLogin(this)) {
            token = PreferenceUtils.getUserToken(this);
        }

        titleHeaderText.setText(getIntent().getStringExtra(SUBCATEGORY_NAME_EXTRA_KEY));

        search_header_categories_img.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(ProductsActivity.this, SearchActivity.class);
                intent.putExtra(SEARCH_NAME_ARGUMENT, query);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                search_header_categories_img.onActionViewCollapsed(); //to close the searchview
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        onLoadPage();
    }
    ArrayList<Subsubcategory> subsubcategories;
    private void onLoadPage() {
//         subsubcategories = getIntent().getParcelableArrayListExtra(SUBSUBCATEGORIES_INTENT_EXTRA_KEY);

        ArrayList<Subcategory> subcategories = getIntent().getParcelableArrayListExtra(SUBCATEGORY_ARRAY_EXTRA_KEY);
        if (subcategories == null){
            ProductsFragment productsFragment = new ProductsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(CATEGORY_ID_INTENT_EXTRA_KEY, categoryId);
            productsFragment.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.animate_slide_up_enter, R.anim.animate_slide_up_exit);
            ft.replace(R.id.vpPager_item, productsFragment, productsFragment.getTag());
            ft.commit();
        }else {
            subsubcategories = subcategories.get(getIntent().getIntExtra("position", 0)).getListSubSubCategory();
            for (Subcategory subcategory : subcategories) {
                Locale locale = ChangeLang.getLocale(getResources());
                String loo = locale.getLanguage();
                view_pager_tab_item_sub_category.setTabMode(TabLayout.MODE_SCROLLABLE);
                if (loo.equalsIgnoreCase("ar")) {
                    view_pager_tab_item_sub_category.addTab(view_pager_tab_item_sub_category.newTab().setText(subcategory.getNameAr()));
                } else {
                    view_pager_tab_item_sub_category.addTab(view_pager_tab_item_sub_category.newTab().setText(subcategory.getName()));
                }
            }

            view_pager_tab_item_sub_category.getTabAt(getIntent().getIntExtra("position", 0)).select();

            view_pager_tab_item_sub_category.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    subsubcategories = subcategories.get(tab.getPosition()).getListSubSubCategory();
                    subCategoryId = subcategories.get(tab.getPosition()).getId();
                    if (subsubcategories != null && subsubcategories.size() > 0) {
                        initializeViewPagerWithSubSubCategories();
                    } else {
                        initializeViewPagerWithoutSubSubCategories();
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });


            if (subsubcategories != null && subsubcategories.size() > 0) {
                initializeViewPagerWithSubSubCategories();
            } else {
                initializeViewPagerWithoutSubSubCategories();
            }
        }
    }

    @OnClick(R.id.item_fragment_back)
    public void onBack() {
        onBackPressed();
    }
    int categoryId ;
    int subCategoryId;
    public void initializeViewPagerWithoutSubSubCategories() {
        if (subCategoryId == -1) {//meaning it has no subcategory, so we fetch products from category id
            categoriesItemAdapter = new CategoriesItemAdapter(getSupportFragmentManager(), categoryId);
        } else {
            categoriesItemAdapter = new CategoriesItemAdapter(getSupportFragmentManager(), subCategoryId, categoryId);
        }
        vpPager.setAdapter(categoriesItemAdapter);
        view_pager_tab.setVisibility(View.GONE);
    }

    public void initializeViewPagerWithSubSubCategories() {
        cat_ids = new ArrayList<>();
        cat_names = new ArrayList<>();

        Locale locale = ChangeLang.getLocale(getResources());
        String loo = locale.getLanguage();

        for (Subsubcategory subsubcategory : subsubcategories) {
            cat_ids.add(String.valueOf(subsubcategory.getId()));

            if (loo.equalsIgnoreCase("ar")) {
                cat_names.add(subsubcategory.getNameAr());
            } else {
                cat_names.add(subsubcategory.getName());
            }
        }

        int subCategoryId = getIntent().getIntExtra(SUBCATEGORY_ID_EXTRA_KEY, -1);
        int categoryId = getIntent().getIntExtra(CATEGORY_ID_INTENT_EXTRA_KEY, -1);

        categoriesItemAdapter = new CategoriesItemAdapter(getSupportFragmentManager(), cat_ids, cat_names, categoryId, subCategoryId);
        vpPager.setAdapter(categoriesItemAdapter);
        view_pager_tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        view_pager_tab.setVisibility(View.VISIBLE);
        LinearLayout linearLayout = (LinearLayout) view_pager_tab.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.GRAY);
        drawable.setSize(1, 1);
        linearLayout.setDividerPadding(30);
        linearLayout.setDividerDrawable(drawable);
    }
}

