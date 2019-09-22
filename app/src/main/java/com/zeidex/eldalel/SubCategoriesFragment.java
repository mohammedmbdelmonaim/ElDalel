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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.adapters.SubCategoriesAdapter;
import com.zeidex.eldalel.models.Subcategory;
import com.zeidex.eldalel.models.Subsubcategory;
import com.zeidex.eldalel.utils.Animatoo;
import com.zeidex.eldalel.utils.ChangeLang;
import com.zeidex.eldalel.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zeidex.eldalel.OffersFragment.CATEGORY_ID_INTENT_EXTRA_KEY;
import static com.zeidex.eldalel.OffersFragment.CATEGORY_NAME_INTENT_EXTRA;
import static com.zeidex.eldalel.OffersFragment.SUBCATEGORIES_INTENT_EXTRA_KEY;
import static com.zeidex.eldalel.adapters.CategoriesAdapter.CATEGORY_NAME_AR_INTENT_EXTRA;

public class SubCategoriesFragment extends Fragment implements SubCategoriesAdapter.SubCategoryOperation {

    public static final String SUBCATEGORY_ID_EXTRA_KEY = "subcategory";
    public static final String SUBSUBCATEGORIES_INTENT_EXTRA_KEY = "subsubcategories";
    public static final String SUBCATEGORY_NAME_EXTRA_KEY = "subcategory_name";

    @BindView(R.id.category_recycler_list)
    RecyclerView category_recycler_list;
    @BindView(R.id.category_card_item)
    CardView categoryCardItem;
    @BindView(R.id.offer_category_item_img)
    AppCompatImageView categoryCardImage;
    @BindView(R.id.offer_category_item_text)
    AppCompatTextView categoryCardText;

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
        } else {
            //If there are no subcategories, pass the category id to get the products based on it
            int categoryId = getArguments().getInt(CATEGORY_ID_INTENT_EXTRA_KEY);
            String categoryName;
            Locale locale = ChangeLang.getLocale(getResources());
            String loo = locale.getLanguage();
            if (loo.equalsIgnoreCase("ar")) {
                categoryName = getArguments().getString(CATEGORY_NAME_AR_INTENT_EXTRA);
            } else {
                categoryName = getArguments().getString(CATEGORY_NAME_INTENT_EXTRA);
            }
            showCategoryCard(categoryId, categoryName);
//            Intent intent = new Intent(getActivity(), ProductsActivity.class);
//            intent.putExtra(CATEGORY_ID_INTENT_EXTRA_KEY, categoryId);
//            getActivity().startActivity(intent);
//            Animatoo.animateSwipeLeft(getActivity());
        }
    }

    private void showCategoryCard(int categoryId, String categoryName) {
        categoryCardText.setText(categoryName);
        categoryCardItem.setVisibility(View.VISIBLE);
        categoryCardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProductsActivity.class);
                intent.putExtra(CATEGORY_ID_INTENT_EXTRA_KEY, categoryId);
                getActivity().startActivity(intent);
                Animatoo.animateSwipeLeft(getActivity());
            }
        });
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
