package com.zeidex.eldalel.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zeidex.eldalel.R;
import com.zeidex.eldalel.models.Subcategory;
import com.zeidex.eldalel.models.Subsubcategory;
import com.zeidex.eldalel.utils.ChangeLang;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SubCategoriesAdapter extends RecyclerView.Adapter<SubCategoriesAdapter.OffersCategoriesHolder> {
    private Context context;
    List<Subcategory> subCategories;
    boolean isOffered;
    int selectedPos = 0;
    View view;

    private SubCategoryOperation mSubCategoryOperation;

    public void setSubCategoryOperation(SubCategoryOperation subCategoryOperation) {
        this.mSubCategoryOperation = subCategoryOperation;
    }

    public SubCategoriesAdapter(Context context) {
        this.context = context;
    }

    public SubCategoriesAdapter(Context context, List<Subcategory> subCategories, boolean isOffered) {
        this.context = context;
        this.subCategories = subCategories;
        this.isOffered = isOffered;
    }

    @NonNull
    @Override
    public OffersCategoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.offers_category_row, parent, false);
        final OffersCategoriesHolder accessoriesHolder = new OffersCategoriesHolder(view);
        return accessoriesHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OffersCategoriesHolder holder, int position) {
        Subcategory currentSubCategory = subCategories.get(position);

        Locale locale = ChangeLang.getLocale(context.getResources());
        String loo = locale.getLanguage();
        if (loo.equalsIgnoreCase("ar")) {
            holder.subCategoryTextView.setText(currentSubCategory.getNameAr());
        } else {
            holder.subCategoryTextView.setText(currentSubCategory.getName());
        }

        // @params isOffered is for either highlighting the selection (in case of offers) or not (in case of all categories)
        if (isOffered) {
            if (selectedPos == position) {
                holder.subcategoryItemCard.setSelected(true);
                holder.subCategoryTextView.setTextColor(Color.parseColor("#047AC0"));

            } else {
                holder.subcategoryItemCard.setSelected(false);
                holder.subCategoryTextView.setTextColor(Color.parseColor("#606060"));
            }
        }

        Glide.with(context)
                .load("https://www.dleel-sh.com/homepages/get/" + currentSubCategory.getImagePath())
                .placeholder(R.drawable.condition_logo)
                .fitCenter()
                .into(holder.subCategoryImageView);

    }

    @Override
    public int getItemCount() {
        return subCategories.size();
    }

    public class OffersCategoriesHolder extends RecyclerView.ViewHolder {
        //        @BindView(R.id.offer_category_item_img)
        AppCompatImageView subCategoryImageView;
        //        @BindView(R.id.offer_category_item_text)
        AppCompatTextView subCategoryTextView;
        CardView subcategoryItemCard;

        public OffersCategoriesHolder(View itemView) {
            super(itemView);
            subCategoryImageView = itemView.findViewById(R.id.offer_category_item_img);
            subCategoryTextView = itemView.findViewById(R.id.offer_category_item_text);
            subcategoryItemCard = itemView.findViewById(R.id.subcategory_item_card);

//            ButterKnife.bind(this, itemView);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int subcategoryId = subCategories.get(getAdapterPosition()).getId();
                    String subcategoryName;
                    Locale locale = ChangeLang.getLocale(context.getResources());
                    String loo = locale.getLanguage();
                    if (loo.equalsIgnoreCase("ar")) {
                        subcategoryName = subCategories.get(getAdapterPosition()).getNameAr();
                    } else {
                        subcategoryName = subCategories.get(getAdapterPosition()).getName();
                    }
                    //This change in behavior depends on whether these subcategories are offer subcategories or not
                    //In case of all subcategories, check if it has subsubcategories or not
                    if (isOffered) {
                        notifyItemChanged(selectedPos);
                        selectedPos = getAdapterPosition();
                        notifyItemChanged(selectedPos);
                        mSubCategoryOperation.onClickSubCategory(subcategoryId, subcategoryName , getAdapterPosition());
                    } else {
                        if (subCategories.get(getAdapterPosition()).getListSubSubCategory() != null && subCategories.get(getAdapterPosition()).getListSubSubCategory().size() > 0) {
                            mSubCategoryOperation.onClickSubCategoryWithSubSub(subCategories.get(getAdapterPosition()).getListSubSubCategory(), subcategoryName, subcategoryId , getAdapterPosition());
                        } else {
                            mSubCategoryOperation.onClickSubCategory(subcategoryId, subcategoryName , getAdapterPosition());
                        }
                    }
                }
            });
        }
    }

    public void setSelectedPos(int pos){
        notifyItemChanged(selectedPos);
        selectedPos = pos;
        notifyItemChanged(selectedPos);
    }

    public interface SubCategoryOperation {
        void onClickSubCategory(int subcategoryId, String subcategoryName , int pos);

        void onClickSubCategoryWithSubSub(ArrayList<Subsubcategory> subsubcategories, String subcategoryName, int subcategoryId , int pos);
    }
}
