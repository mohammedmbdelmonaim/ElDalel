package com.zeidex.eldalel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zeidex.eldalel.R;
import com.zeidex.eldalel.response.GetOffersCategories;
import com.zeidex.eldalel.utils.ChangeLang;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OffersHolder> {
    private Context context;
    List<GetOffersCategories.Category> categoryList;
    View view;

    private OffersOperation offersOperation;

    public void setOffersOperation(OffersOperation offersOperation) {
        this.offersOperation = offersOperation;
    }

    public OffersAdapter(Context context) {
        this.context = context;
    }

    public OffersAdapter(Context context, List<GetOffersCategories.Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public OffersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.offers_asymmetrical_row, parent, false);
        final OffersHolder accessoriesHolder = new OffersHolder(view);
        return accessoriesHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OffersHolder holder, int position) {

        GetOffersCategories.Category currentCategory = categoryList.get(position);

        Locale locale = ChangeLang.getLocale(context.getResources());
        String loo = locale.getLanguage();
        if (loo.equalsIgnoreCase("ar")) {
            holder.offerRowLabel.setText(currentCategory.getNameAr());
        } else {
            holder.offerRowLabel.setText(currentCategory.getName());
        }

        Glide.with(context)
                .load("https://dleel.com/homepages/get/" + currentCategory.getPhoto())
                .placeholder(R.drawable.condition_logo)
                .fitCenter()
                .into(holder.offerRowImage);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offersOperation.onClickOffer(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class OffersHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.offer_row_image)
        AppCompatImageView offerRowImage;
        @BindView(R.id.offer_row_label)
        AppCompatTextView offerRowLabel;

        public OffersHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OffersOperation {
        void onClickOffer(int position);
    }
}
