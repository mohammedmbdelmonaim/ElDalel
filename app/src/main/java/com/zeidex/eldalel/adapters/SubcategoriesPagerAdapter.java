package com.zeidex.eldalel.adapters;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.zeidex.eldalel.NewArrivalProductsFragment;
import com.zeidex.eldalel.utils.SmartFragmentStatePagerAdapter;

import java.util.List;

import static com.zeidex.eldalel.OffersFragment.CATEGORY_ID_INTENT_EXTRA_KEY;
import static com.zeidex.eldalel.adapters.CategoriesItemAdapter.SUBCATEGORY_ID_INTENT_EXTRA;

public class SubcategoriesPagerAdapter extends SmartFragmentStatePagerAdapter {

    List<String> ids, names;
    int categoryId;

    public SubcategoriesPagerAdapter(FragmentManager fragmentManager, List<String> ids, List<String> names, int categoryId) {
        super(fragmentManager);
        this.ids = ids;
        this.names = names;
        this.categoryId = categoryId;
    }

    @Override
    public Fragment getItem(int position) {
        NewArrivalProductsFragment newArrivalProductsFragment = new NewArrivalProductsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(SUBCATEGORY_ID_INTENT_EXTRA, Integer.parseInt(ids.get(position)));
        bundle.putInt(CATEGORY_ID_INTENT_EXTRA_KEY, categoryId);
        newArrivalProductsFragment.setArguments(bundle);
        return newArrivalProductsFragment;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return names.get(position);
    }
}
