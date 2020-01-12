package com.zeidex.eldalel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zeidex.eldalel.R;
import com.zeidex.eldalel.models.ProductsCategory;
import com.zeidex.eldalel.utils.PreferenceUtils;
import com.zeidex.eldalel.utils.PriceFormatter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class LikesElementsAdapter extends RecyclerView.Adapter<LikesElementsAdapter.CategoryHolder> {
    private Context context;
    List<ProductsCategory> productList;
    View view;

    private LikesOperation likesOperation;

    public void setLikesOperation(LikesOperation likesOperation) {
        this.likesOperation = likesOperation;
    }

    public void setProductList(List<ProductsCategory> productList) {
        this.productList = productList;
    }

    public List<ProductsCategory> getProductsList() {
        return productList;
    }

    public LikesElementsAdapter(Context context) {
        this.context = context;
    }

    public LikesElementsAdapter(Context context, List<ProductsCategory> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.likes_item_row, parent, false);
        final CategoryHolder categoryHolder = new CategoryHolder(view);
        return categoryHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        ProductsCategory productsCategory = productList.get(position);

        String discount = productsCategory.getDiscount();
        if (discount == null) {
            holder.discountLinear.setVisibility(View.GONE);
        } else {
            holder.discountLinear.setVisibility(View.VISIBLE);
            holder.likeItemDiscountText.setText(discount);
        }

        String price = productsCategory.getPrice();
        if (price != null) {
            Double priceDouble = Double.parseDouble(price);
            holder.likeItemPriceText.setText(PriceFormatter.toDecimalString(priceDouble, context.getApplicationContext()));
        }

        String oldPrice = productsCategory.getPrice_before();
        if (oldPrice == null) {
            holder.likeItemPriceBeforeText.setVisibility(View.INVISIBLE);
            holder.likeItemPriceBeforeLabel.setVisibility(View.INVISIBLE);
        } else {
            holder.likeItemPriceBeforeText.setVisibility(View.VISIBLE);
            holder.likeItemPriceBeforeLabel.setVisibility(View.VISIBLE);
            Double oldPriceDouble = Double.parseDouble(oldPrice);
            holder.likeItemPriceBeforeText.setText(PriceFormatter.toDecimalRsString(oldPriceDouble, context.getApplicationContext()));
        }

        holder.likeItemNameText.setText(productsCategory.getName());
        holder.likeItemTypeText.setText(productsCategory.getType());

        if (productsCategory.getImgUrl() != null) {
            Glide.with(context)
                    .load("https://dleel.com/homepages/get/" + productsCategory.getImgUrl())
                    .placeholder(R.drawable.condition_logo)
                    .centerCrop()
                    .into(holder.likeItemImageView);
        }

        int cartStatus = Integer.parseInt(productsCategory.getCart());
        if (cartStatus == 2) {
            holder.likeItemAddToCart.setBackgroundResource(R.drawable.row_out_of_stock_cart);
            holder.likeItemAddToCart.setText(R.string.cart_out_of_stock_label);
        } else if (cartStatus == 0) {
            holder.likeItemAddToCart.setBackgroundResource(R.drawable.row_already_added_cart);
            holder.likeItemAddToCart.setText(R.string.add_to_card);
        } else {
            holder.likeItemAddToCart.setBackgroundResource(R.drawable.row_add_to_car_bg);
            holder.likeItemAddToCart.setText(R.string.phone_row_add_to_card_txt);

        }
    }

    @Override
    public int getItemCount() {
        return productList != null && productList.size() > 0 ? productList.size() : 0;
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.like_item_image_delete)
        AppCompatImageView likeItemImageDelete;
        @BindView(R.id.like_item_img_url)
        AppCompatImageView likeItemImageView;
        @BindView(R.id.like_item_discount_text)
        AppCompatTextView likeItemDiscountText;
        @BindView(R.id.like_item_text_type)
        AppCompatTextView likeItemTypeText;
        @BindView(R.id.like_item_text_name)
        AppCompatTextView likeItemNameText;
        @BindView(R.id.like_item_text_price)
        AppCompatTextView likeItemPriceText;
        @BindView(R.id.like_item_text_price_before)
        AppCompatTextView likeItemPriceBeforeText;
        @BindView(R.id.like_item_price_before_label)
        View likeItemPriceBeforeLabel;
        @BindView(R.id.like_item_text_add_to_card)
        AppCompatTextView likeItemAddToCart;
        @BindView(R.id.discount_linear)
        LinearLayoutCompat discountLinear;

        public CategoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    likesOperation.onClickProduct(Integer.parseInt(productList.get(getAdapterPosition()).getId()), getAdapterPosition());
                }
            });

            likeItemAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!PreferenceUtils.getUserLogin(context) && !PreferenceUtils.getCompanyLogin(context)) {
                        Toasty.error(context, context.getString(R.string.please_login_first), Toast.LENGTH_LONG).show();
                        return;
                    }
                    if ((PreferenceUtils.getUserLogin(context) || PreferenceUtils.getCompanyLogin(context))) {
                        if (Integer.parseInt(productList.get(getAdapterPosition()).getCart()) == 1)
                            likesOperation.onAddToCart(Integer.parseInt(productList.get(getAdapterPosition()).getId()), getAdapterPosition());
                    }
                }
            });

            likeItemImageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!PreferenceUtils.getUserLogin(context) && !PreferenceUtils.getCompanyLogin(context)) {
                        Toasty.error(context, context.getString(R.string.please_login_first), Toast.LENGTH_LONG).show();
                        return;
                    }
                    if ((PreferenceUtils.getUserLogin(context) || PreferenceUtils.getCompanyLogin(context))) {
                        likesOperation.onRemoveFavorite(Integer.parseInt(productList.get(getAdapterPosition()).getId()), getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface LikesOperation {
        void onClickProduct(int id, int pos);

        void onAddToCart(int id, int position);

        void onRemoveFavorite(int id, int position);
    }
}
