package com.zeidex.eldalel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.R;
import com.zeidex.eldalel.models.Category;
import com.zeidex.eldalel.response.GetAllCategories;
import com.zeidex.eldalel.response.GetDetailProduct;
import com.zeidex.eldalel.utils.ChangeLang;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterCategoriesAdapter extends RecyclerView.Adapter<FilterCategoriesAdapter.CategoryHolder> {
    private Context context;
    List<GetAllCategories.Category> categoryList;
    int selectedPos = 0;
    View view;

    private CheckOperation checkOperation;

    public void setCheckOperation(CheckOperation checkOperation) {
        this.checkOperation = checkOperation;
    }

    public FilterCategoriesAdapter(Context context) {
        this.context = context;
    }

    public FilterCategoriesAdapter(Context context, List<GetAllCategories.Category> categoryList, int categoryInitialPosition) {
        this.context = context;
        this.categoryList = categoryList;
        selectedPos = categoryInitialPosition;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.filter_category_row, parent, false);
        final CategoryHolder categoryHolder = new CategoryHolder(view);
        return categoryHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Locale locale = ChangeLang.getLocale(context.getResources());
        String loo = locale.getLanguage();
        if (loo.equalsIgnoreCase("ar")) {
            holder.filter_category_row_text.setText(categoryList.get(position).getNameAr());
        } else {
            holder.filter_category_row_text.setText(categoryList.get(position).getName());
        }
        if (position == selectedPos) {
            holder.filter_category_row_checkview.setChecked(true);
        } else {
            holder.filter_category_row_checkview.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.filter_category_row_text)
        AppCompatTextView filter_category_row_text;
        @BindView(R.id.filter_category_row_checkview)
        AppCompatCheckBox filter_category_row_checkview;

        public CategoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            filter_category_row_checkview.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            notifyItemChanged(selectedPos);
            selectedPos = getAdapterPosition();
            notifyItemChanged(selectedPos);
            checkOperation.onCheckCategory(getAdapterPosition(), categoryList.get(getAdapterPosition()).getId());
        }
    }

    public interface CheckOperation {
        void onCheckCategory(int position, int id);
    }
}
