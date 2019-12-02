package com.zeidex.eldalel;

import android.app.Dialog;
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
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zeidex.eldalel.adapters.SubCategoriesAdapter;
import com.zeidex.eldalel.models.Subcategory;
import com.zeidex.eldalel.models.Subsubcategory;
import com.zeidex.eldalel.utils.ChangeLang;
import com.zeidex.eldalel.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zeidex.eldalel.OffersFragment.CATEGORY_ID_INTENT_EXTRA_KEY;
import static com.zeidex.eldalel.OffersFragment.CATEGORY_NAME_INTENT_EXTRA;
import static com.zeidex.eldalel.OffersFragment.SUBCATEGORIES_INTENT_EXTRA_KEY;
import static com.zeidex.eldalel.adapters.CategoriesAdapter.CATEGORY_IMAGE_NAME;
import static com.zeidex.eldalel.adapters.CategoriesAdapter.CATEGORY_NAME_AR_INTENT_EXTRA;

public class SubCategoriesFragment extends Fragment implements SubCategoriesAdapter.SubCategoryOperation {

    public static final String SUBCATEGORY_ID_EXTRA_KEY = "subcategory";
    public static final String SUBSUBCATEGORIES_INTENT_EXTRA_KEY = "subsubcategories";
    public static final String SUBCATEGORY_NAME_EXTRA_KEY = "subcategory_name";
    public static final String SUBCATEGORY_ARRAY_EXTRA_KEY = "subcategory_array";

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
    ArrayList<Subcategory> subCategories;

    Dialog reloadDialog;
    String token = "";
    private int categoryId;
    String categoryName;

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
        categoryId = getArguments().getInt(CATEGORY_ID_INTENT_EXTRA_KEY);
        Locale locale = ChangeLang.getLocale(getContext().getResources());
        String loo = locale.getLanguage();
        if (loo.equalsIgnoreCase("ar")) {
            categoryName = getArguments().getString(CATEGORY_NAME_AR_INTENT_EXTRA);
        } else {
            categoryName = getArguments().getString(CATEGORY_NAME_INTENT_EXTRA);
        }

        if (subCategories != null && subCategories.size() > 0) {
            initializeRecycler();
            findViews();
        } else {
            //If there are no subcategories, pass the category id to get the products based on it

            showCategoryCard(categoryId, categoryName);
//            Intent intent = new Intent(getContext(), ProductsActivity.class);
//            intent.putExtra(CATEGORY_ID_INTENT_EXTRA_KEY, categoryId);
//            getContext().startActivity(intent);
//            Animatoo.animateSwipeLeft(getContext());
        }
    }

    private void showCategoryCard(int categoryId, String categoryName) {
        categoryCardText.setText(categoryName);
        String imageName = getArguments().getString(CATEGORY_IMAGE_NAME);
        if(imageName != null){
            Glide.with(getContext())
                    .load("https://www.dleel-sh.com/homepages/get/" + imageName)
                    .placeholder(R.drawable.condition_logo)
                    .centerCrop()
                    .into(categoryCardImage);
        }
        categoryCardItem.setVisibility(View.VISIBLE);
        categoryCardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
//                Intent intent = new Intent(getContext(), ProductsActivity.class);
                bundle.putInt(CATEGORY_ID_INTENT_EXTRA_KEY, categoryId);
                bundle.putString(CATEGORY_NAME_INTENT_EXTRA, categoryName);
                NavHostFragment.findNavController(SubCategoriesFragment.this).navigate(R.id.action_categoriesFragment_to_productsActivity, bundle);
//                getContext().startActivity(intent);
//                Animatoo.animateSwipeLeft(getContext());
            }
        });
    }

    private void findViews() {
        if (PreferenceUtils.getCompanyLogin(getContext())) {
            token = PreferenceUtils.getCompanyToken(getContext());
        } else if (PreferenceUtils.getUserLogin(getContext())) {
            token = PreferenceUtils.getUserToken(getContext());
        }

        showDialog();
        onLoadPage();
    }

    private void onLoadPage() {

    }

    public void initializeRecycler() {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);
        category_recycler_list.setLayoutManager(mLayoutManager);
        category_recycler_list.setItemAnimator(new DefaultItemAnimator());

        subCategoryAdapter = new SubCategoriesAdapter(getContext(), subCategories, false);
        subCategoryAdapter.setSubCategoryOperation(this);
        category_recycler_list.setAdapter(subCategoryAdapter);

    }

    private void showDialog() {
        reloadDialog = new Dialog(getContext());
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onClickSubCategory(int subCategoryId, String subCategoryName , int pos) {
        Bundle bundle = new Bundle();
//        Intent intent = new Intent(getContext(), ProductsActivity.class);
        bundle.putInt(CATEGORY_ID_INTENT_EXTRA_KEY, categoryId);
        bundle.putInt(SUBCATEGORY_ID_EXTRA_KEY, subCategoryId);
        bundle.putString(SUBCATEGORY_NAME_EXTRA_KEY, subCategoryName);
        bundle.putString(CATEGORY_NAME_INTENT_EXTRA, categoryName);
        bundle.putInt("position", pos);
        bundle.putParcelableArrayList(SUBCATEGORY_ARRAY_EXTRA_KEY , subCategories);
//        getContext().startActivity(intent);
//        Animatoo.animateSwipeLeft(getContext());
        NavHostFragment.findNavController(this).navigate(R.id.action_categoriesFragment_to_productsActivity, bundle);
    }

    @Override
    public void onClickSubCategoryWithSubSub(ArrayList<Subsubcategory> subsubcategories, String subCategoryName, int subcategoryId , int pos) {
        Bundle bundle = new Bundle();
//        Intent intent = new Intent(getContext(), ProductsActivity.class);
        bundle.putInt(CATEGORY_ID_INTENT_EXTRA_KEY, categoryId);
        bundle.putInt(SUBCATEGORY_ID_EXTRA_KEY, subcategoryId);
        bundle.putString(SUBCATEGORY_NAME_EXTRA_KEY, subCategoryName);
        bundle.putParcelableArrayList(SUBSUBCATEGORIES_INTENT_EXTRA_KEY, subsubcategories);
        bundle.putParcelableArrayList(SUBCATEGORY_ARRAY_EXTRA_KEY , subCategories);
        bundle.putString(CATEGORY_NAME_INTENT_EXTRA, categoryName);
        bundle.putInt("position", pos);
        NavHostFragment.findNavController(this).navigate(R.id.action_categoriesFragment_to_productsActivity, bundle);
//        getContext().startActivity(intent);
//        Animatoo.animateSwipeLeft(getContext());
    }
}
