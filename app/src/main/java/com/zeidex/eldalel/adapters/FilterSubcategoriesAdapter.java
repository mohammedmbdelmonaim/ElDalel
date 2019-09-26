package com.zeidex.eldalel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.R;
import com.zeidex.eldalel.response.GetAllCategories;
import com.zeidex.eldalel.utils.ChangeLang;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterSubcategoriesAdapter extends RecyclerView.Adapter<FilterSubcategoriesAdapter.SubcategoryHolder> {
    private Context context;
    List<GetAllCategories.Subcategory> subcategoryList;
    int selectedPos = 0;
    View view;

    private FilterSubcategoriesAdapter.CheckOperation checkOperation;

    public void setCheckOperation(FilterSubcategoriesAdapter.CheckOperation checkOperation) {
        this.checkOperation = checkOperation;
    }

    public void setSubcategoryList(List<GetAllCategories.Subcategory> subcategoryList) {
        this.subcategoryList = subcategoryList;
        selectedPos = 0;
    }

    public FilterSubcategoriesAdapter(Context context) {
        this.context = context;
    }


    public FilterSubcategoriesAdapter(Context context, List<GetAllCategories.Subcategory> subcategoryList) {
        this.context = context;
        this.subcategoryList = subcategoryList;
    }

    @NonNull
    @Override
    public FilterSubcategoriesAdapter.SubcategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.filter_category_row, parent, false);
        final FilterSubcategoriesAdapter.SubcategoryHolder subcategoryHolder = new FilterSubcategoriesAdapter.SubcategoryHolder(view);
        return subcategoryHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FilterSubcategoriesAdapter.SubcategoryHolder holder, int position) {
        Locale locale = ChangeLang.getLocale(context.getResources());
        String loo = locale.getLanguage();
        if (loo.equalsIgnoreCase("ar")) {
            holder.filter_category_row_text.setText(subcategoryList.get(position).getNameAr());
        } else {
            holder.filter_category_row_text.setText(subcategoryList.get(position).getName());
        }
        if (position == selectedPos) {
            holder.filter_category_row_checkview.setChecked(true);
        } else {
            holder.filter_category_row_checkview.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        if (subcategoryList != null && subcategoryList.size() > 0) {
            return subcategoryList.size();
        } else {
            return 0;
        }
    }

    public void setInitialPosition(int subcategoryInitialPosition) {
        selectedPos = subcategoryInitialPosition;
    }

    public class SubcategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.filter_category_row_text)
        AppCompatTextView filter_category_row_text;
        @BindView(R.id.filter_category_row_checkview)
        AppCompatCheckBox filter_category_row_checkview;

        public SubcategoryHolder(View itemView) {
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
            checkOperation.onCheckSubcategory(getAdapterPosition(), subcategoryList.get(getAdapterPosition()).getId());
        }
    }

    public interface CheckOperation {
        void onCheckSubcategory(int position, int id);
    }
}
