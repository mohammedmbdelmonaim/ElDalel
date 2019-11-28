package com.zeidex.eldalel.adapters;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.zeidex.eldalel.DetailDescFragment;
import com.zeidex.eldalel.utils.SmartFragmentStatePagerAdapter;

import java.util.List;

public class DetailDescriptionsAdapter extends SmartFragmentStatePagerAdapter {
    List<String> desc_names;
    String desc_options , full_desc;

    public DetailDescriptionsAdapter(List<String> desc_names ,FragmentManager fragmentManager , String desc_options , String full_desc) {
        super(fragmentManager);
        this.desc_names = desc_names;
        this.desc_options = desc_options;
        this.full_desc = full_desc;
    }

    @Override
    public Fragment getItem(int position) {
        DetailDescFragment detailDescFragment = new DetailDescFragment();
            Bundle bundle = new Bundle();
            bundle.putString("pos" , ""+position);
            bundle.putString("desc_options" , desc_options);
            bundle.putString("full_desc" , full_desc);
            detailDescFragment.setArguments(bundle);
            return detailDescFragment;
    }

    @Override
    public int getCount() {
        return desc_names.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return desc_names.get(position);
    }
}
