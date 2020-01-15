package com.zeidex.eldalel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.zeidex.eldalel.R;
import com.zeidex.eldalel.response.GetOffersCategories;
import com.zeidex.eldalel.utils.ChangeLang;

import java.util.List;
import java.util.Locale;

public class NewArrivalsCategoriesAdapter extends RecyclerView.Adapter<NewArrivalsCategoriesAdapter.OffersHolder> {
    private Context context;
    List<GetOffersCategories.Category> categoryList;
    View view;

    private CategoryOperation categoryOperation;

    public void setCategoryOperation(CategoryOperation categoryOperation) {
        this.categoryOperation = categoryOperation;
    }

    public NewArrivalsCategoriesAdapter(Context context, List<GetOffersCategories.Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public OffersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.offers_category_row, parent, false);
        final OffersHolder accessoriesHolder = new OffersHolder(view);
        return accessoriesHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OffersHolder holder, int position) {

        GetOffersCategories.Category currentCategory = categoryList.get(position);

        Locale locale = ChangeLang.getLocale(context.getResources());
        String loo = locale.getLanguage();
        if (loo.equalsIgnoreCase("ar")) {
            holder.categoryTextView.setText(currentCategory.getNameAr());
        } else {
            holder.categoryTextView.setText(currentCategory.getName());
        }

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable .setStrokeWidth(5f);
        circularProgressDrawable .setCenterRadius(30f);
        circularProgressDrawable .start();

        Glide.with(context)
                .load("https://dleel.com/homepages/get/" + currentCategory.getPhoto())
                .placeholder(circularProgressDrawable)
                .error(R.drawable.condition_logo)
                .fitCenter()
                .into(holder.categoryImageView);

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class OffersHolder extends RecyclerView.ViewHolder {
        AppCompatImageView categoryImageView;
        AppCompatTextView categoryTextView;

        public OffersHolder(View itemView) {
            super(itemView);
            categoryImageView = itemView.findViewById(R.id.offer_category_item_img);
            categoryTextView = itemView.findViewById(R.id.offer_category_item_text);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoryOperation.onClickCategory(getAdapterPosition());
                }
            });
        }
    }

    public interface CategoryOperation {
        void onClickCategory(int position);
    }
}
