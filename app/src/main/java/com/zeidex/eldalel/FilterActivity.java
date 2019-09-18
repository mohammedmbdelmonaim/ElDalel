package com.zeidex.eldalel;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.aakira.expandablelayout.ExpandableLayoutListener;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.innovattic.rangeseekbar.RangeSeekBar;
import com.zeidex.eldalel.adapters.FilterCategoriesAdapter;
import com.zeidex.eldalel.adapters.OrdersAdapter;
import com.zeidex.eldalel.utils.Animatoo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilterActivity extends BaseActivity implements View.OnClickListener {

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




    FilterCategoriesAdapter filterCategoriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        findViews();
    }

    @OnClick(R.id.item_filter_back)
    public void onBack(){
        onBackPressed();
    }

    public void findViews() {
        filter_categories_linear.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(FilterActivity.this);
        filter_categories_recycler.setLayoutManager(layoutManager);
        filter_categories_recycler.setItemAnimator(new DefaultItemAnimator());
        filterCategoriesAdapter = new FilterCategoriesAdapter(FilterActivity.this);
        filter_categories_recycler.setAdapter(filterCategoriesAdapter);

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
                filter_prices_internal_from_text.setText(i+"");
                filter_prices_internal_to_text.setText(i1+"");

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.filter_categories_linear: {

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
                filter_categories_expandable.toggle();
                break;
            }

            case R.id.filter_prices_linear: {

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
                filter_prices_expandable.toggle();
                break;
            }
        }
    }
}
