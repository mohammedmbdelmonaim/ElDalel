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
import com.zeidex.eldalel.response.GetCategorizedOffers;
import com.zeidex.eldalel.response.GetProducts;
import com.zeidex.eldalel.utils.ChangeLang;
import com.zeidex.eldalel.utils.PriceFormatter;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.CategoryHolder> {
    private Context context;
    List<GetCategorizedOffers.Products.Data> offerList;
    List<GetProducts.Data> productsList;
    View view;
    private CategoryOperation categoryOperation;

    public List<GetCategorizedOffers.Products.Data> getOfferList() {
        return offerList;
    }

    public void setCategoryOperation(CategoryOperation categoryOperation) {
        this.categoryOperation = categoryOperation;
    }

    public CategoryItemAdapter(Context context, List<GetCategorizedOffers.Products.Data> offers) {
        this.context = context;
        offerList = offers;
    }

    public CategoryItemAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.category_item_row, parent, false);
        final CategoryHolder categoryHolder = new CategoryHolder(view);
        return categoryHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        GetCategorizedOffers.Products.Data currentOffer = offerList.get(position);

        Locale locale = ChangeLang.getLocale(context.getResources());
        String loo = locale.getLanguage();

        if (loo.equalsIgnoreCase("ar")) {
            holder.offerItemNameText.setText(currentOffer.getNameAr());
        } else {
            holder.offerItemNameText.setText(currentOffer.getName());
        }

        Double oldPrice = (Double) currentOffer.getOldPrice();
        if (oldPrice == null) {
            holder.offerItemOldPriceText.setVisibility(View.INVISIBLE);
            holder.discountLabel.setVisibility(View.INVISIBLE);
        } else {
            holder.offerItemOldPriceText.setVisibility(View.VISIBLE);
            holder.discountLabel.setVisibility(View.VISIBLE);
            holder.offerItemOldPriceText.setText(PriceFormatter.toDecimalRsString(oldPrice, context.getApplicationContext()));
        }

        Double price = currentOffer.getPrice();
        if (price != null) {
            holder.offerItemPriceText.setText(PriceFormatter.toDecimalRsString(price, context.getApplicationContext()));
        }

        if (currentOffer.getDiscount() == null) {
            holder.offerItemDiscountText.setVisibility(View.GONE);
        } else {
            holder.offerItemDiscountText.setVisibility(View.VISIBLE);
            holder.offerItemDiscountText.setText(PriceFormatter.toRightNumber((double)currentOffer.getDiscount(), context.getApplicationContext()));
        }

        if (loo.equalsIgnoreCase("ar")) {
            holder.categoryItemTypeText.setText(currentOffer.getSubcategory().getNameAr());
        } else {
            holder.categoryItemTypeText.setText(currentOffer.getSubcategory().getName());
        }

        if (currentOffer.getPhotos().size() > 0) {
            Glide.with(context)
                    .load("https://www.dleel-sh.com/homepages/get/" + currentOffer.getPhotos().get(0).getFilename())
                    .placeholder(R.drawable.condition_logo)
                    .centerCrop()
                    .into(holder.offerItemImage);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryOperation.onClickCategory(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return offerList.size() > 0 ? offerList.size() : 0;
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.category_item_image_like)
        AppCompatImageView offerItemLikeImage;
        @BindView(R.id.category_item_img_url)
        AppCompatImageView offerItemImage;
        @BindView(R.id.category_item_discount_text)
        AppCompatTextView offerItemDiscountText;
        @BindView(R.id.category_item_text_name)
        AppCompatTextView offerItemNameText;
        @BindView(R.id.category_item_text_type)
        AppCompatTextView categoryItemTypeText;
        @BindView(R.id.category_item_text_price)
        AppCompatTextView offerItemPriceText;
        @BindView(R.id.category_item_text_price_before)
        AppCompatTextView offerItemOldPriceText;
        @BindView(R.id.category_item_text_add_to_card)
        AppCompatTextView offerItemAddToCart;
        @BindView(R.id.discount_label)
        View discountLabel;

//        public AppCompatImageView notification_row_roundimage;
//        public TextView notification_row_text_notifi, notification_row_text_time;
//        public CardView notification_row_cardview;
//        public RelativeLayout relative;

        public CategoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            notification_row_roundimage = itemView.findViewById(R.id.notification_row_roundimage);
//            notification_row_text_notifi = itemView.findViewById(R.id.notification_row_text_notifi);
//            notification_row_text_time = itemView.findViewById(R.id.notification_row_text_time);
//            notification_row_cardview = itemView.findViewById(R.id.notification_row_cardview);
//            relativelative = itemView.findViewById(R.id.relative);
        }
    }

    public interface CategoryOperation {
        void onClickCategory(int position);
    }
}
