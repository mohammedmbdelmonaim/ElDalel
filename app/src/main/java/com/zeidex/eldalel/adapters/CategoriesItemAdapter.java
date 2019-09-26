package com.zeidex.eldalel.adapters;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.zeidex.eldalel.ProductsFragment;
import com.zeidex.eldalel.utils.SmartFragmentStatePagerAdapter;

import java.util.List;

import static com.zeidex.eldalel.OffersFragment.CATEGORY_ID_INTENT_EXTRA_KEY;

public class CategoriesItemAdapter extends SmartFragmentStatePagerAdapter {
    public static final String PRODUCTS_INTENT_EXTRA_KEY = "products";
    public static final String SUBCATEGORY_ID_INTENT_EXTRA = "subcategory_id";
    public static final String SUB_SUBCATEGORY_ID_INTENT_EXTRA = "sub_subcategory_id";
    List<String> ids, names;
    int subcategoryId = -1;
    int categoryId = -1;
//    ArrayList<ProductsCategory> products;

    public CategoriesItemAdapter(FragmentManager fragmentManager, List<String> ids, List<String> names, int categoryId, int subcategoryId) {
        super(fragmentManager);
        this.ids = ids;
        this.names = names;
        this.categoryId = categoryId;
        this.subcategoryId = subcategoryId;
    }

    public CategoriesItemAdapter(FragmentManager fragmentManager, int subcategoryId, int categoryId) {
        super(fragmentManager);
        this.subcategoryId = subcategoryId;
        this.categoryId = categoryId;
    }

    public CategoriesItemAdapter(FragmentManager fragmentManager, int categoryId) {
        super(fragmentManager);
        this.categoryId = categoryId;
    }

//    public CategoriesItemAdapter(FragmentManager fragmentManager, ArrayList<ProductsCategory> products) {
//        super(fragmentManager);
//        this.products = products;
//    }

    @Override
    public Fragment getItem(int position) {
        ProductsFragment productsFragment = new ProductsFragment();
        Bundle bundle = new Bundle();
        if (ids == null) { //indicates that there are no subsubcategories
            if (subcategoryId == -1) //indicates that there are no subcategories either
            {
                bundle.putInt(CATEGORY_ID_INTENT_EXTRA_KEY, categoryId);
            } else {
                bundle.putInt(CATEGORY_ID_INTENT_EXTRA_KEY, categoryId);
                bundle.putInt(SUBCATEGORY_ID_INTENT_EXTRA, subcategoryId);
            }
            productsFragment.setArguments(bundle);
            return productsFragment;
        }

        bundle.putInt(CATEGORY_ID_INTENT_EXTRA_KEY, categoryId);
        bundle.putInt(SUBCATEGORY_ID_INTENT_EXTRA, subcategoryId);
        bundle.putInt(SUB_SUBCATEGORY_ID_INTENT_EXTRA, Integer.valueOf(ids.get(position)));
        productsFragment.setArguments(bundle);
        return productsFragment;
    }

    @Override
    public int getCount() {
        return names != null ? names.size() : 1;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return names != null ? names.get(position) : "";
    }
}
