package com.zeidex.eldalel.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.zeidex.eldalel.R;
import com.zeidex.eldalel.models.ProductsCategory;
import com.zeidex.eldalel.utils.PreferenceUtils;
import com.zeidex.eldalel.utils.PriceFormatter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.CategoryHolder> {
    private Context context;
//    List<GetCategorizedOffers.Products.Data> offerList;
    List<ProductsCategory> productsList;
    View view;
    private CategoryItemOperation categoryOperation;

    public List<ProductsCategory> getProductsList() {
        return productsList;
    }
    public void setProductsList(List<ProductsCategory> productsList){this.productsList = productsList;}


    public void setCategoryOperation(CategoryItemOperation categoryOperation) {
        this.categoryOperation = categoryOperation;
    }

    public int updateProductsList(List<ProductsCategory> newProducts){
        productsList.addAll(newProducts);
        notifyDataSetChanged();
        return getItemCount();
    }

    public void clearList(){
        productsList.clear();
        notifyDataSetChanged();
    }

    public CategoryItemAdapter(Context context, List<ProductsCategory> productsList) {
        this.context = context;
        this.productsList = productsList;
        if (PreferenceUtils.getUserLogin(context)) {
            token = PreferenceUtils.getUserToken(context);
        } else if (PreferenceUtils.getCompanyLogin(context)) {
            token = PreferenceUtils.getCompanyToken(context);
        }
    }
    String token = "";
    public CategoryItemAdapter(Context context) {
        this.context = context;
        productsList = new ArrayList<>();
        if (PreferenceUtils.getUserLogin(context)) {
            token = PreferenceUtils.getUserToken(context);
        } else if (PreferenceUtils.getCompanyLogin(context)) {
            token = PreferenceUtils.getCompanyToken(context);
        }
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
        ProductsCategory currentOffer = productsList.get(position);

        holder.offerItemNameText.setText(currentOffer.getName());

        String price = currentOffer.getPrice();
        if (price != null) {
            double priceDouble = Double.parseDouble(price);
            holder.offerItemPriceText.setText(PriceFormatter.toDecimalString(priceDouble, context.getApplicationContext()));
        }

        String oldPrice = currentOffer.getPrice_before();
        if (oldPrice == null || oldPrice.equals("0")) {
            holder.offerItemOldPriceText.setVisibility(View.INVISIBLE);
            holder.discountLabel.setVisibility(View.INVISIBLE);
        } else {
            double priceDouble = Double.parseDouble(oldPrice);
            holder.offerItemOldPriceText.setVisibility(View.VISIBLE);
            holder.discountLabel.setVisibility(View.VISIBLE);
            holder.offerItemOldPriceText.setText(PriceFormatter.toDecimalRsString(priceDouble, context.getApplicationContext()));
        }

        String discount = currentOffer.getDiscount();
        if (discount == null || discount.equals("0")) {
            holder.offerItemDiscountText.setVisibility(View.GONE);
        } else {
            double discountDouble = Double.parseDouble(discount);
            holder.offerItemDiscountText.setVisibility(View.VISIBLE);
            holder.offerItemDiscountText.setText(discount);
            holder.offerItemDiscountText.setText(PriceFormatter.toRightNumber(discountDouble, context.getApplicationContext()));
        }

        holder.categoryItemTypeText.setText(currentOffer.getType());


        if (!token.equalsIgnoreCase("")) {
            if (currentOffer.getLike().equalsIgnoreCase("1")) {
                holder.offerItemLikeImage.setChecked(true);
            } else {
                holder.offerItemLikeImage.setChecked(false);
            }

            int cartStatus = Integer.parseInt(currentOffer.getCart());
            if (cartStatus == 2) {
                holder.offerItemAddToCart.setBackgroundResource(R.drawable.row_out_of_stock_cart);
                holder.offerItemAddToCart.setText(R.string.cart_out_of_stock_label);
            } else if (cartStatus == 0) {
                holder.offerItemAddToCart.setBackgroundResource(R.drawable.row_already_added_cart);
                holder.offerItemAddToCart.setText(R.string.add_to_card);
            } else {
                holder.offerItemAddToCart.setBackgroundResource(R.drawable.row_add_to_car_bg);
                holder.offerItemAddToCart.setText(R.string.phone_row_add_to_card_txt);

            }
        }

        if (!TextUtils.isEmpty(currentOffer.getImgUrl())) {
            CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
            circularProgressDrawable .setStrokeWidth(5f);
            circularProgressDrawable .setCenterRadius(30f);
            circularProgressDrawable .start();

            Glide.with(context)
                    .load("https://dleel.com/homepages/get/" + currentOffer.getImgUrl())
                    .placeholder(circularProgressDrawable)
                    .error(R.drawable.condition_logo)
                    .centerCrop()
                    .into(holder.offerItemImage);
        }
    }

    @Override
    public int getItemCount() {
        return productsList.size() > 0 ? productsList.size() : 0;
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.category_item_image_like)
        AppCompatCheckBox offerItemLikeImage;
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

        public CategoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            offerItemAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!PreferenceUtils.getUserLogin(context) && !PreferenceUtils.getCompanyLogin(context)) {
                        Toasty.error(context, context.getString(R.string.please_login_first), Toast.LENGTH_LONG).show();
                        return;
                    }
                    if ((PreferenceUtils.getUserLogin(context) || PreferenceUtils.getCompanyLogin(context))) {
                        if (Integer.parseInt(productsList.get(getAdapterPosition()).getCart()) == 1)
                            categoryOperation.onAddToProductCart(Integer.parseInt(productsList.get(getAdapterPosition()).getId()), getAdapterPosition());
                    }
                }
            });

            offerItemLikeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean isChecked = offerItemLikeImage.isChecked();

                    if (!PreferenceUtils.getUserLogin(context) && isChecked && !PreferenceUtils.getCompanyLogin(context)) {
                        Toasty.error(context, context.getString(R.string.please_login_first), Toast.LENGTH_LONG).show();
                        offerItemLikeImage.setChecked(false);
                        return;
                    }
                    if ((PreferenceUtils.getUserLogin(context) || PreferenceUtils.getCompanyLogin(context)) && !isChecked) {
                        Toasty.error(context, context.getString(R.string.unlike_fiv), Toast.LENGTH_LONG).show();
                        offerItemLikeImage.setChecked(true);
                        return;
                    }
                    if ((PreferenceUtils.getUserLogin(context) || PreferenceUtils.getCompanyLogin(context)) && isChecked) {
                        categoryOperation.onClickProductLike(Integer.parseInt(productsList.get(getAdapterPosition()).getId()));
                    }

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoryOperation.onClickProduct(Integer.parseInt(productsList.get(getAdapterPosition()).getId()), getAdapterPosition());
                }
            });
        }
    }

    public interface CategoryItemOperation {
        void onClickProduct(int id, int pos);

        void onClickProductLike(int id);

        void onAddToProductCart(int id, int position);
    }
}
