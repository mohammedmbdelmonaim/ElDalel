package com.zeidex.eldalel;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.adapters.SubCategoriesAdapter;
import com.zeidex.eldalel.models.Subcategory;
import com.zeidex.eldalel.models.Subsubcategory;
import com.zeidex.eldalel.utils.Animatoo;
import com.zeidex.eldalel.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zeidex.eldalel.OffersFragment.SUBCATEGORIES_INTENT_EXTRA_KEY;

public class SubCategoriesFragment extends Fragment implements SubCategoriesAdapter.SubCategoryOperation {

    public static final String SUBCATEGORY_ID_EXTRA_KEY = "subcategory";
    public static final String SUBSUBCATEGORIES_INTENT_EXTRA_KEY = "subsubcategories";
    public static final String SUBCATEGORY_NAME_EXTRA_KEY = "subcategory_name";

    @BindView(R.id.category_recycler_list)
    RecyclerView category_recycler_list;
    @BindView(R.id.categories_no_items_layout)
    RelativeLayout noItemsLayout;

    SubCategoriesAdapter subCategoryAdapter;
//    String id;
    List<Subcategory> subCategories;

    Dialog reloadDialog;
    String token = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        subCategories = getArguments().getParcelableArrayList(SUBCATEGORIES_INTENT_EXTRA_KEY);
        if (subCategories != null && subCategories.size() > 0) {
            initializeRecycler();
            findViews();
        }else{
            showEmptyView();
        }
    }

    private void showEmptyView() {
        noItemsLayout.setVisibility(View.VISIBLE);
        category_recycler_list.setVisibility(View.GONE);
    }

    private void findViews() {
        if (PreferenceUtils.getCompanyLogin(getActivity())) {
            token = PreferenceUtils.getCompanyToken(getActivity());
        } else if (PreferenceUtils.getUserLogin(getActivity())) {
            token = PreferenceUtils.getUserToken(getActivity());
        }

        showDialog();
        onLoadPage();
    }

    private void onLoadPage() {

    }

    public void initializeRecycler() {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        category_recycler_list.setLayoutManager(mLayoutManager);
        category_recycler_list.setItemAnimator(new DefaultItemAnimator());

        subCategoryAdapter = new SubCategoriesAdapter(getActivity(), subCategories, false);
        subCategoryAdapter.setSubCategoryOperation(this);
        category_recycler_list.setAdapter(subCategoryAdapter);

    }

    private void showDialog() {
        reloadDialog = new Dialog(getActivity());
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onClickSubCategory(int subCategoryId, String subCategoryName) {
        Intent intent = new Intent(getActivity(), ProductsActivity.class);
        intent.putExtra(SUBCATEGORY_ID_EXTRA_KEY, subCategoryId);
        intent.putExtra(SUBCATEGORY_NAME_EXTRA_KEY, subCategoryName);
        getActivity().startActivity(intent);
        Animatoo.animateSwipeLeft(getActivity());
    }

    @Override
    public void onClickSubCategoryWithSubSub(ArrayList<Subsubcategory> subsubcategories, String subCategoryName) {
        Intent intent = new Intent(getActivity(), ProductsActivity.class);
        intent.putParcelableArrayListExtra(SUBSUBCATEGORIES_INTENT_EXTRA_KEY, subsubcategories);
        intent.putExtra(SUBCATEGORY_NAME_EXTRA_KEY, subCategoryName);
        getActivity().startActivity(intent);
        Animatoo.animateSwipeLeft(getActivity());
    }
}
