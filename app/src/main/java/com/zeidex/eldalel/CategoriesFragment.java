package com.zeidex.eldalel;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.zeidex.eldalel.adapters.CategoriesAdapter;
import com.zeidex.eldalel.response.GetAllCategories;
import com.zeidex.eldalel.services.AllCategoriesAPI;
import com.zeidex.eldalel.utils.APIClient;
import com.zeidex.eldalel.utils.ChangeLang;
import com.zeidex.eldalel.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class CategoriesFragment extends androidx.fragment.app.Fragment {
    public static final String NEW_ARRIVALS_ID = "-1";
    public static final int NO_PRODUCTS_STATUS = 1;
    @BindView(R.id.vpPager)
    ViewPager vpPager;

    @BindView(R.id.view_pager_tab)
    TabLayout view_pager_tab;

    List<String> cat_names;
    List<String> cat_ids;
    CategoriesAdapter categoriesAdapter;

    Dialog reloadDialog;
    String token = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        LinearLayout linearLayout = (LinearLayout) view_pager_tab.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.GRAY);
        drawable.setSize(1, 1);
        linearLayout.setDividerPadding(30);
        linearLayout.setDividerDrawable(drawable);
        findViews();
    }

    private void findViews() {
        if (PreferenceUtils.getCompanyLogin(getActivity())) {
            token = PreferenceUtils.getCompanyToken(getActivity());
        } else if (PreferenceUtils.getUserLogin(getActivity())) {
            token = PreferenceUtils.getUserToken(getActivity());
        }

        cat_ids = new ArrayList<>();
        cat_names = new ArrayList<>();

        showDialog();
        onLoadPage();
    }

    private void onLoadPage() {
        reloadDialog.show();
        AllCategoriesAPI allCategoriesAPI = APIClient.getClient(SERVER_API_TEST).create(AllCategoriesAPI.class);
        allCategoriesAPI.getAllCategories(NO_PRODUCTS_STATUS).enqueue(new Callback<GetAllCategories>() {
            @Override
            public void onResponse(Call<GetAllCategories> call, Response<GetAllCategories> response) {
                if (response.body() != null) {
                    int code = response.body().getCode();
                    if (code == 200) {
                        List<GetAllCategories.Category> categories = response.body().getData().getCategories();
                        if (categories.size() > 0) {
                            initializeViewPager(categories);
                        }
                    }
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetAllCategories> call, Throwable t) {
                Toasty.error(getActivity(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    public void initializeViewPager(List<GetAllCategories.Category> categories) {
        cat_ids.clear();
        cat_names.clear();

        Locale locale = ChangeLang.getLocale(getResources());
        String loo = locale.getLanguage();

        cat_ids.add(NEW_ARRIVALS_ID);
        cat_names.add(getString(R.string.new_arrival_header_text));

        for (GetAllCategories.Category category : categories) {
            cat_ids.add(category.getId() + "");
            if (loo.equalsIgnoreCase("ar")) {
                cat_names.add(category.getNameAr());
            } else {
                cat_names.add(category.getName());
            }
        }

        categoriesAdapter = new CategoriesAdapter(getFragmentManager(), cat_ids, cat_names, categories);
        vpPager.setAdapter(categoriesAdapter);
        view_pager_tab.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void showDialog() {
        reloadDialog = new Dialog(getActivity());
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
