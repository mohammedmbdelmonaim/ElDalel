package com.zeidex.eldalel;

import android.app.Dialog;
import android.content.Intent;
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

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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

import static com.zeidex.eldalel.SearchActivity.SEARCH_NAME_ARGUMENT;
import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class CategoriesFragment extends androidx.fragment.app.Fragment {
    public static final String NEW_ARRIVALS_ID = "-1";
    public static final int NO_PRODUCTS_STATUS = 1;
    @BindView(R.id.vpPager)
    ViewPager vpPager;

    @BindView(R.id.view_pager_tab)
    TabLayout view_pager_tab;

    @BindView(R.id.fragment_categories_searchview)
    SearchView fragment_categories_searchview;

    List<String> cat_names;
    List<String> cat_ids;
    CategoriesAdapter categoriesAdapter;

    Dialog reloadDialog;
    String token = "";
    private int categoryId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                ((MainActivity) getContext()).navigateToHomeFragment();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
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

        fragment_categories_searchview.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra(SEARCH_NAME_ARGUMENT, query);
                startActivity(intent);
                fragment_categories_searchview.onActionViewCollapsed(); //to close the searchview
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        if (categoriesWithSub != null) {
            updateUI();
        } else {
            findViews();
        }
    }

    private void findViews() {
        if (PreferenceUtils.getCompanyLogin(getContext())) {
            token = PreferenceUtils.getCompanyToken(getContext());
        } else if (PreferenceUtils.getUserLogin(getContext())) {
            token = PreferenceUtils.getUserToken(getContext());
        }

        cat_ids = new ArrayList<>();
        cat_names = new ArrayList<>();

        Bundle bundle = getArguments();
        if (bundle != null) {
            categoryId = bundle.getInt("category_id", -1);
        }

        showDialog();
        onLoadPage();
    }

    List<GetAllCategories.Category> categoriesWithSub;

    private void onLoadPage() {
        reloadDialog.show();
        AllCategoriesAPI allCategoriesAPI = APIClient.getClient(SERVER_API_TEST).create(AllCategoriesAPI.class);
        allCategoriesAPI.getAllCategories(NO_PRODUCTS_STATUS).enqueue(new Callback<GetAllCategories>() {
            @Override
            public void onResponse(Call<GetAllCategories> call, Response<GetAllCategories> response) {
                if (response.body() != null) {
                    int code = response.body().getCode();
                    if (code == 200) {
                        categoriesWithSub = new ArrayList<>();
                        List<GetAllCategories.Category> categories = response.body().getData().getCategories();
                        if (categories.size() > 0) {
                            for (GetAllCategories.Category category : categories) {
                                if (category.getSubcategories().size() == 0) {
                                    continue;
                                } else {
                                    categoriesWithSub.add(category);
                                }

                            }
                            initializeViewPager(categoriesWithSub);

                        }
                    }
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetAllCategories> call, Throwable t) {
                Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    public void initializeViewPager(List<GetAllCategories.Category> categories) {
        cat_ids.clear();
        cat_names.clear();

        Locale locale = ChangeLang.getLocale(getContext().getResources());
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

        updateUI();
    }

    private void updateUI() {
        categoriesAdapter = new CategoriesAdapter(getChildFragmentManager(), cat_ids, cat_names, categoriesWithSub);
        vpPager.setAdapter(categoriesAdapter);
        view_pager_tab.setTabMode(TabLayout.MODE_SCROLLABLE);

        if (categoryId != -1) {
            String categoryIdString = String.valueOf(categoryId);
            for (String id : cat_ids) {
                if (id.equals(categoryIdString)) {
                    int position = cat_ids.indexOf(id);
                    vpPager.setCurrentItem(position);
                }
            }
        }
    }

    private void showDialog() {
        reloadDialog = new Dialog(getContext());
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
