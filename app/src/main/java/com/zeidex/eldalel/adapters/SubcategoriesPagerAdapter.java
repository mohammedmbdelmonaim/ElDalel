package com.zeidex.eldalel.adapters;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.zeidex.eldalel.NewArrivalProductsFragment;
import com.zeidex.eldalel.utils.SmartFragmentStatePagerAdapter;

import java.util.List;

public class SubcategoriesPagerAdapter extends SmartFragmentStatePagerAdapter {
    List<String> ids, names;

    public SubcategoriesPagerAdapter(FragmentManager fragmentManager, List<String> ids, List<String> names) {
        super(fragmentManager);
        this.ids = ids;
        this.names = names;
    }

    @Override
    public Fragment getItem(int position) {
        NewArrivalProductsFragment newArrivalProductsFragment = new NewArrivalProductsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", ids.get(position));
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
