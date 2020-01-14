package com.zeidex.eldalel;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.IntentCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.aakira.expandablelayout.ExpandableLayoutListener;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.innovattic.rangeseekbar.RangeSeekBar;
import com.zeidex.eldalel.adapters.FilterCategoriesAdapter;
import com.zeidex.eldalel.adapters.FilterSubcategoriesAdapter;
import com.zeidex.eldalel.adapters.OrdersAdapter;
import com.zeidex.eldalel.response.GetAllCategories;
import com.zeidex.eldalel.services.AllCategoriesAPI;
import com.zeidex.eldalel.utils.APIClient;
import com.zeidex.eldalel.utils.Animatoo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zeidex.eldalel.CategoriesFragment.NO_PRODUCTS_STATUS;
import static com.zeidex.eldalel.OffersFragment.CATEGORY_ID_INTENT_EXTRA_KEY;
import static com.zeidex.eldalel.adapters.CategoriesItemAdapter.SUBCATEGORY_ID_INTENT_EXTRA;
import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class FilterActivity extends BaseActivity implements View.OnClickListener, FilterCategoriesAdapter.CheckOperation, FilterSubcategoriesAdapter.CheckOperation {

    public static final String FILTER_CATEGORY_ID = "filter_category_id";
    public static final String FILTER_PRICE_FROM = "filter_price_from";
    public static final String FILTER_PRICE_TO = "filter_price_to";
    public static final String FILTER_SUBCATEGORY_ID = "filter_subcategory_id";
    public static final String FILTER_HAS_OFFER = "filter_has_offer";
    public static final String FILTER_IS_NEW_ARRIVAL = "filter_is_new_arrival";
    public static final String FILTER_PRODUCT_NAME = "filter_product_name";

    @BindView(R.id.filter_categories_linear)
    LinearLayoutCompat filter_categories_linear;

    @BindView(R.id.filter_categories_expandable)
    ExpandableRelativeLayout filter_categories_expandable;

    @BindView(R.id.filter_prices_linear)
    LinearLayoutCompat filter_prices_linear;

    @BindView(R.id.filter_prices_expandable)
    ExpandableRelativeLayout filter_prices_expandable;

    @BindView(R.id.filter_categories_recycler)
    RecyclerView filter_categories_recycler;

    @BindView(R.id.filter_categories_arrow)
    AppCompatImageView filter_categories_arrow;

    @BindView(R.id.filter_categories_text)
    AppCompatTextView filter_categories_text;

    @BindView(R.id.filter_subcategories_linear)
    LinearLayoutCompat filter_subcategories_linear;

    @BindView(R.id.filter_subcategories_expandable)
    ExpandableRelativeLayout filter_subcategories_expandable;

    @BindView(R.id.filter_subcategories_recycler)
    RecyclerView filter_subcategories_recycler;

    @BindView(R.id.filter_subcategories_text)
    AppCompatTextView filter_subcategories_text;

    @BindView(R.id.filter_subcategories_arrow)
    AppCompatImageView filter_subcategories_arrow;

    @BindView(R.id.filter_prices_arrow)
    AppCompatImageView filter_prices_arrow;

    @BindView(R.id.filter_prices_text)
    AppCompatTextView filter_prices_text;

    @BindView(R.id.filter_prices_internal_from_text)
    AppCompatTextView filter_prices_internal_from_text;

    @BindView(R.id.filter_prices_internal_to_text)
    AppCompatTextView filter_prices_internal_to_text;

    @BindView(R.id.rangeSeekBar)
    RangeSeekBar rangeSeekBar;

    @BindView(R.id.filter_discount_linear)
    LinearLayoutCompat filter_discount_linear;

    @BindView(R.id.filter_discount_text)
    AppCompatTextView filter_discount_text;

    @BindView(R.id.filter_discount_arrow)
    AppCompatImageView filter_discount_arrow;

    @BindView(R.id.filter_discount_expandable)
    ExpandableRelativeLayout filter_discount_expandable;

    @BindView(R.id.filter_offer_radio)
    AppCompatRadioButton filter_offer_radio;

    @BindView(R.id.filter_no_offer_radio)
    AppCompatRadioButton filter_no_offer_radio;

    Dialog reloadDialog;

    private List<GetAllCategories.Category> categories;
    private FilterSubcategoriesAdapter filterSubcategoriesAdapter;
    private List<GetAllCategories.Subcategory> subcategories;
    private int categoryId;
    private int subCategoryId;
    private int categoryInitialPosition;
    private int subcategoryInitialPosition;
    private boolean isNewArrival;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        findViews();
    }

    @OnClick(R.id.item_filter_back)
    public void onBack() {
        onBackPressed();
        Animatoo.animateSwipeRight(this);
    }

    @OnClick(R.id.filter_btn)
    public void filter() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("should_finish_activity", true);
        setResult(RESULT_OK, resultIntent);

        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra(FILTER_CATEGORY_ID, categoryId);
        intent.putExtra(FILTER_SUBCATEGORY_ID, subCategoryId);
        intent.putExtra(FILTER_PRICE_FROM, Integer.parseInt(filter_prices_internal_from_text.getText().toString()));
        intent.putExtra(FILTER_PRICE_TO, Integer.parseInt(filter_prices_internal_to_text.getText().toString()));
        intent.putExtra(FILTER_HAS_OFFER, filter_offer_radio.isChecked());
        intent.putExtra(FILTER_IS_NEW_ARRIVAL, isNewArrival);
        intent.putExtra(FILTER_PRODUCT_NAME, getIntent().getStringExtra(FILTER_PRODUCT_NAME));
        startActivity(intent);
        finish();
        Animatoo.animateSwipeRight(this);
    }

    public void findViews() {
        filter_categories_linear.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(FilterActivity.this);
        filter_categories_recycler.setLayoutManager(layoutManager);
        filter_categories_recycler.setItemAnimator(new DefaultItemAnimator());

        filter_subcategories_linear.setOnClickListener(this);
        filterSubcategoriesAdapter = new FilterSubcategoriesAdapter(this, new ArrayList<>());
        filter_subcategories_recycler.setAdapter(filterSubcategoriesAdapter);
        LinearLayoutManager subcatLayoutManager = new LinearLayoutManager(FilterActivity.this);
        filter_subcategories_recycler.setLayoutManager(subcatLayoutManager);
        filter_subcategories_recycler.setItemAnimator(new DefaultItemAnimator());

        filter_discount_linear.setOnClickListener(this);
        filter_no_offer_radio.setChecked(true);

        categoryId = getIntent().getIntExtra(CATEGORY_ID_INTENT_EXTRA_KEY, -1);
        subCategoryId = getIntent().getIntExtra(SUBCATEGORY_ID_INTENT_EXTRA, -1);

        isNewArrival = getIntent().getBooleanExtra("is_new_arrival", false);
        if (isNewArrival) {
            filter_discount_linear.setVisibility(View.GONE);
        }
        boolean hasOffer = getIntent().getBooleanExtra("has_offer", false);
        if (hasOffer) {
            filter_offer_radio.setChecked(true);
            filter_discount_expandable.toggle();
        }

        setupListeners();

        filter_prices_linear.setOnClickListener(this);
        rangeSeekBar.setSeekBarChangeListener(new RangeSeekBar.SeekBarChangeListener() {
            @Override
            public void onStartedSeeking() {
            }

            @Override
            public void onStoppedSeeking() {
            }

            @Override
            public void onValueChanged(int i, int i1) {
                filter_prices_internal_from_text.setText(i + "");
                filter_prices_internal_to_text.setText(i1 + "");
            }
        });

        showDialog();
        onLoadPage();
    }

    private void onLoadPage() {
        getAllCategories();
    }

    private void getAllCategories() {
        reloadDialog.show();
        AllCategoriesAPI allCategoriesAPI = APIClient.getClient(SERVER_API_TEST).create(AllCategoriesAPI.class);
        allCategoriesAPI.getAllCategories(NO_PRODUCTS_STATUS).enqueue(new Callback<GetAllCategories>() {
            @Override
            public void onResponse(Call<GetAllCategories> call, Response<GetAllCategories> response) {
                if (response.body() != null) {
                    int code = response.body().getCode();
                    if (code == 200) {
                        categories = response.body().getData().getCategories();
                        if (categories.size() > 0) {
                            if (categoryId == -1) {
                                categories.get(0).getId();
                            } else {
                                for (GetAllCategories.Category category : categories) {
                                    if (category.getId() == categoryId) {
                                        categoryInitialPosition = categories.indexOf(category);
                                        break;
                                    }
                                }
                            }
                            FilterCategoriesAdapter filterCategoriesAdapter = new FilterCategoriesAdapter(FilterActivity.this, categories, categoryInitialPosition);
                            filter_categories_recycler.setAdapter(filterCategoriesAdapter);
                            filterCategoriesAdapter.setCheckOperation(FilterActivity.this);
                            setupSubcategories(categoryInitialPosition, true);
                        }
                    }
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetAllCategories> call, Throwable t) {
                Toasty.error(FilterActivity.this, getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    private void setupSubcategories(int position, boolean isInitialSetup) {
        if (categories != null && categories.size() > 0) {
            subcategories = categories.get(position).getSubcategories();
            if (subcategories.size() > 0) {
                if (isInitialSetup) {
                    if (subCategoryId == -1) {
                        subcategories.get(0).getId();
                    } else {
                        for (GetAllCategories.Subcategory subcategory : subcategories) {
                            if (subcategory.getId() == subCategoryId) {
                                subcategoryInitialPosition = subcategories.indexOf(subcategory);
                                break;
                            }
                        }
                    }
                } else {
                    subCategoryId = subcategories.get(0).getId();
                }
                filterSubcategoriesAdapter.setSubcategoryList(subcategories);
                if (isInitialSetup)
                    filterSubcategoriesAdapter.setInitialPosition(subcategoryInitialPosition);
                filterSubcategoriesAdapter.notifyDataSetChanged();
                filterSubcategoriesAdapter.setCheckOperation(FilterActivity.this);
                filter_subcategories_linear.setEnabled(true);
                filter_subcategories_text.setAlpha(1f);
                filter_subcategories_arrow.setAlpha(1f);
            } else {
                if (filter_subcategories_expandable.isExpanded()) {
                    filter_subcategories_expandable.toggle();
                }
                filterSubcategoriesAdapter.setSubcategoryList(new ArrayList<>());
                filterSubcategoriesAdapter.notifyDataSetChanged();
                filterSubcategoriesAdapter.setCheckOperation(FilterActivity.this);
                filter_subcategories_linear.setEnabled(false);
                filter_subcategories_text.setAlpha(0.5f);
                filter_subcategories_arrow.setAlpha(0.5f);
            }
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.filter_categories_linear: {
                filter_categories_expandable.toggle();
                break;
            }

            case R.id.filter_subcategories_linear: {
                filter_subcategories_expandable.toggle();
                break;
            }

            case R.id.filter_prices_linear: {
                filter_prices_expandable.toggle();
                break;
            }

            case R.id.filter_discount_linear: {
                filter_discount_expandable.toggle();
                break;
            }
        }
    }

    public void setupListeners() {
        filter_categories_expandable.setListener(new ExpandableLayoutListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationEnd() {
            }

            @Override
            public void onPreOpen() {
            }

            @Override
            public void onPreClose() {
            }

            @Override
            public void onOpened() {
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    filter_categories_linear.setBackgroundDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.expand_filter_bg));
                    filter_categories_arrow.setImageDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.ic_keyboard_arrow_down));
                    filter_categories_expandable.setBackgroundDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.expand_expandable_bg));
                    filter_categories_text.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    filter_categories_linear.setBackground(ContextCompat.getDrawable(FilterActivity.this, R.drawable.expand_filter_bg));
                    filter_categories_arrow.setImageDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.ic_keyboard_arrow_down));
                    filter_categories_expandable.setBackgroundDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.expand_expandable_bg));
                    filter_categories_text.setTextColor(Color.parseColor("#FFFFFF"));
                }
            }

            @Override
            public void onClosed() {
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    filter_categories_linear.setBackgroundDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.row_account_notclicked));
                    filter_categories_arrow.setImageDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.ic_keyboard_arrow_up));
                    filter_categories_text.setTextColor(Color.parseColor("#606060"));

                } else {
                    filter_categories_linear.setBackground(ContextCompat.getDrawable(FilterActivity.this, R.drawable.row_account_notclicked));
                    filter_categories_arrow.setImageDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.ic_keyboard_arrow_up));
                    filter_categories_text.setTextColor(Color.parseColor("#606060"));
                }
            }
        });

        filter_subcategories_expandable.setListener(new ExpandableLayoutListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationEnd() {
            }

            @Override
            public void onPreOpen() {
            }

            @Override
            public void onPreClose() {
            }

            @Override
            public void onOpened() {
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    filter_subcategories_linear.setBackgroundDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.expand_filter_bg));
                    filter_subcategories_arrow.setImageDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.ic_keyboard_arrow_down));
                    filter_subcategories_expandable.setBackgroundDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.expand_expandable_bg));
                    filter_subcategories_text.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    filter_subcategories_linear.setBackground(ContextCompat.getDrawable(FilterActivity.this, R.drawable.expand_filter_bg));
                    filter_subcategories_arrow.setImageDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.ic_keyboard_arrow_down));
                    filter_subcategories_expandable.setBackgroundDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.expand_expandable_bg));
                    filter_subcategories_text.setTextColor(Color.parseColor("#FFFFFF"));
                }
            }

            @Override
            public void onClosed() {
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    filter_subcategories_linear.setBackgroundDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.row_account_notclicked));
                    filter_subcategories_arrow.setImageDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.ic_keyboard_arrow_up));
                    filter_subcategories_text.setTextColor(Color.parseColor("#606060"));

                } else {
                    filter_subcategories_linear.setBackground(ContextCompat.getDrawable(FilterActivity.this, R.drawable.row_account_notclicked));
                    filter_subcategories_arrow.setImageDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.ic_keyboard_arrow_up));
                    filter_subcategories_text.setTextColor(Color.parseColor("#606060"));
                }
            }
        });

        filter_discount_expandable.setListener(new ExpandableLayoutListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationEnd() {
            }

            @Override
            public void onPreOpen() {
            }

            @Override
            public void onPreClose() {
            }

            @Override
            public void onOpened() {
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    filter_discount_linear.setBackgroundDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.expand_filter_bg));
                    filter_discount_arrow.setImageDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.ic_keyboard_arrow_down));
                    filter_discount_expandable.setBackgroundDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.expand_expandable_bg));
                    filter_discount_text.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    filter_discount_linear.setBackground(ContextCompat.getDrawable(FilterActivity.this, R.drawable.expand_filter_bg));
                    filter_discount_arrow.setImageDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.ic_keyboard_arrow_down));
                    filter_discount_expandable.setBackgroundDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.expand_expandable_bg));
                    filter_discount_text.setTextColor(Color.parseColor("#FFFFFF"));
                }
            }

            @Override
            public void onClosed() {
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    filter_discount_linear.setBackgroundDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.row_account_notclicked));
                    filter_discount_arrow.setImageDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.ic_keyboard_arrow_up));
                    filter_discount_text.setTextColor(Color.parseColor("#606060"));

                } else {
                    filter_discount_linear.setBackground(ContextCompat.getDrawable(FilterActivity.this, R.drawable.row_account_notclicked));
                    filter_discount_arrow.setImageDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.ic_keyboard_arrow_up));
                    filter_discount_text.setTextColor(Color.parseColor("#606060"));
                }
            }
        });


        filter_prices_expandable.setListener(new ExpandableLayoutListener() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationEnd() {

            }

            @Override
            public void onPreOpen() {

            }

            @Override
            public void onPreClose() {

            }

            @Override
            public void onOpened() {
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    filter_prices_linear.setBackgroundDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.expand_filter_bg));
                    filter_prices_arrow.setImageDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.ic_keyboard_arrow_down));
                    filter_prices_expandable.setBackgroundDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.expand_expandable_bg));
                    filter_prices_text.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    filter_prices_linear.setBackground(ContextCompat.getDrawable(FilterActivity.this, R.drawable.expand_filter_bg));
                    filter_prices_arrow.setImageDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.ic_keyboard_arrow_down));
                    filter_prices_expandable.setBackgroundDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.expand_expandable_bg));
                    filter_prices_text.setTextColor(Color.parseColor("#FFFFFF"));
                }


            }

            @Override
            public void onClosed() {
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    filter_prices_linear.setBackgroundDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.row_account_notclicked));
                    filter_prices_arrow.setImageDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.ic_keyboard_arrow_up));
                    filter_prices_text.setTextColor(Color.parseColor("#606060"));

                } else {
                    filter_prices_linear.setBackground(ContextCompat.getDrawable(FilterActivity.this, R.drawable.row_account_notclicked));
                    filter_prices_arrow.setImageDrawable(ContextCompat.getDrawable(FilterActivity.this, R.drawable.ic_keyboard_arrow_up));
                    filter_prices_text.setTextColor(Color.parseColor("#606060"));
                }
            }
        });

    }

    private void showDialog() {
        reloadDialog = new Dialog(this);
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onCheckCategory(int position, int id) {
        categoryId = id;
        setupSubcategories(position, false);
    }

    @Override
    public void onCheckSubcategory(int position, int id) {
        subCategoryId = id;
    }

    @Override
    public void onBackPressed() {
        finish();
        Animatoo.animateSwipeRight(this);
    }
}
