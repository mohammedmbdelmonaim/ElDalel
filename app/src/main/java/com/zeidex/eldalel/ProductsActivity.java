package com.zeidex.eldalel;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
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
import static com.zeidex.eldalel.OffersFragment.CATEGORY_NAME_INTENT_EXTRA;
import static com.zeidex.eldalel.SearchActivity.SEARCH_NAME_ARGUMENT;
import static com.zeidex.eldalel.SubCategoriesFragment.SUBCATEGORY_ARRAY_EXTRA_KEY;
import static com.zeidex.eldalel.SubCategoriesFragment.SUBCATEGORY_ID_EXTRA_KEY;

public class ProductsActivity extends Fragment {
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_categories_item, container, false);
        ButterKnife.bind(this, view);
        findViews();
        return view;
    }

    private void findViews() {
        categoryId = getArguments().getInt(CATEGORY_ID_INTENT_EXTRA_KEY, -1);
        subCategoryId = getArguments().getInt(SUBCATEGORY_ID_EXTRA_KEY, -1);
        if (PreferenceUtils.getCompanyLogin(getActivity())) {
            token = PreferenceUtils.getCompanyToken(getActivity());
        } else if (PreferenceUtils.getUserLogin(getActivity())) {
            token = PreferenceUtils.getUserToken(getActivity());
        }

//        titleHeaderText.setText(getArguments().getString(SUBCATEGORY_NAME_EXTRA_KEY));
        titleHeaderText.setText(getArguments().getString(CATEGORY_NAME_INTENT_EXTRA));
        search_header_categories_img.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
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

        ArrayList<Subcategory> subcategories = getArguments().getParcelableArrayList(SUBCATEGORY_ARRAY_EXTRA_KEY);
        if (subcategories == null){
            ProductsFragment productsFragment = new ProductsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(CATEGORY_ID_INTENT_EXTRA_KEY, categoryId);
            productsFragment.setArguments(bundle);
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.animate_slide_up_enter, R.anim.animate_slide_up_exit);
            ft.replace(R.id.vpPager_item, productsFragment, productsFragment.getTag());
            ft.commit();
        }else {
            subsubcategories = subcategories.get(getArguments().getInt("position", 0)).getListSubSubCategory();
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

            view_pager_tab_item_sub_category.getTabAt(getArguments().getInt("position", 0)).select();

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
        //handle back
        NavHostFragment.findNavController(this).navigateUp();
    }
    int categoryId ;
    int subCategoryId;
    public void initializeViewPagerWithoutSubSubCategories() {
        if (subCategoryId == -1) {//meaning it has no subcategory, so we fetch products from category id
            categoriesItemAdapter = new CategoriesItemAdapter(getActivity().getSupportFragmentManager(), categoryId);
        } else {
            categoriesItemAdapter = new CategoriesItemAdapter(getActivity().getSupportFragmentManager(), subCategoryId, categoryId);
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

        int subCategoryId = getArguments().getInt(SUBCATEGORY_ID_EXTRA_KEY, -1);
        int categoryId = getArguments().getInt(CATEGORY_ID_INTENT_EXTRA_KEY, -1);

        categoriesItemAdapter = new CategoriesItemAdapter(getActivity().getSupportFragmentManager(), cat_ids, cat_names, categoryId, subCategoryId);
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

