package com.zeidex.eldalel.adapters;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.zeidex.eldalel.NewArrivalsFragment;
import com.zeidex.eldalel.SubCategoriesFragment;
import com.zeidex.eldalel.models.Subcategory;
import com.zeidex.eldalel.models.Subsubcategory;
import com.zeidex.eldalel.response.GetAllCategories;
import com.zeidex.eldalel.utils.SmartFragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.zeidex.eldalel.OffersFragment.SUBCATEGORIES_INTENT_EXTRA_KEY;

public class CategoriesAdapter extends SmartFragmentStatePagerAdapter {
    List<String> ids, names;
    List<GetAllCategories.Category> categories;

    public CategoriesAdapter(FragmentManager fragmentManager, List<String> ids, List<String> names, List<GetAllCategories.Category> categories) {
        super(fragmentManager);
        this.ids = ids;
        this.names = names;
        this.categories = categories;
    }

    @Override
    public Fragment getItem(int position) {
        SubCategoriesFragment subCategoriesFragment = new SubCategoriesFragment();
        Bundle bundle = new Bundle();

        if (position != 0) {
            ArrayList<Subcategory> subCategoriesModel = new ArrayList<>();
            //we subtract 1 from position to get the actual position for categories as new arrival is taking the first position
            for (GetAllCategories.Subcategory subcategory : categories.get(position - 1).getSubcategories()) {
                Subcategory subcategoryModel = new Subcategory(subcategory.getId(), subcategory.getNameAr(),
                        subcategory.getName(), "");

                //Check if subcategory has subsubcategory, loop through them to add them to the subcategory model
                if (subcategory.getSubsubcategories().size() > 0) {
                    ArrayList<Subsubcategory> subsubcategories = new ArrayList<>();

                    for (GetAllCategories.Subsubcategory subsubcategory : subcategory.getSubsubcategories()) {
                        subsubcategories.add(new Subsubcategory(subsubcategory.getId(), subsubcategory.getName(), subsubcategory.getNameAr()));
                    }
                    subcategoryModel.setListSubSubCategory(subsubcategories);
                }

                subCategoriesModel.add(subcategoryModel);
            }
            bundle.putParcelableArrayList(SUBCATEGORIES_INTENT_EXTRA_KEY, subCategoriesModel);
//            bundle.putString("id", ids.get(position));
            subCategoriesFragment.setArguments(bundle);
            return subCategoriesFragment;
        } else {
            return new NewArrivalsFragment();
        }
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
