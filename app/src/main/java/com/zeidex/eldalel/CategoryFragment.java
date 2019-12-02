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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.adapters.CategoryAdapter;
import com.zeidex.eldalel.utils.PreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryFragment extends Fragment /*implements CategoryAdapter.CategoryOperation */{
    @BindView(R.id.category_recycler_list)
    RecyclerView category_recycler_list;

    CategoryAdapter categoryAdapter;
    String id;

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
        id = getArguments().getString("id");
        initializeRecycler();
        findViews();
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

    public void initializeRecycler(){
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);
        category_recycler_list.setLayoutManager(mLayoutManager);
        category_recycler_list.setItemAnimator(new DefaultItemAnimator());

        categoryAdapter = new CategoryAdapter(getContext());
//        categoryAdapter.setCategoryOperation(this);
        category_recycler_list.setAdapter(categoryAdapter);

    }


//    @Override
//    public void onClickCategory(int position) {
//        getContext().startActivity(new Intent(getContext() , ProductsActivity.class));
//        Animatoo.animateSwipeLeft(getContext());
//    }

    private void showDialog() {
        reloadDialog = new Dialog(getContext());
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
