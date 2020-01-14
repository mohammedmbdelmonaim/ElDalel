package com.zeidex.eldalel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zeidex.eldalel.R;
import com.zeidex.eldalel.models.ProductsCategory;
import com.zeidex.eldalel.utils.PreferenceUtils;
import com.zeidex.eldalel.utils.PriceFormatter;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class HomeOfferAdapter extends RecyclerView.Adapter<HomeOfferAdapter.HomeOfferHolder> {

    private Context context;
    public List<ProductsCategory> getOfferList() {
        return offerList;
    }

    List<ProductsCategory> offerList;
    View view;

    private HomeOfferAdapter.OffersOperation offersOperation;

    public void setOffersOperation(OffersOperation offersOperation) {
        this.offersOperation = offersOperation;
    }

    public void setOfferList(ArrayList<ProductsCategory> offerList){
        this.offerList = offerList;
        notifyDataSetChanged();
    }

    public HomeOfferAdapter(Context context) {
        this.context = context;
    }

    public HomeOfferAdapter(Context context, List<ProductsCategory> offerList) {
        this.context = context;
        this.offerList = offerList;
    }

    @NonNull
    @Override
    public HomeOfferAdapter.HomeOfferHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.phone_row, parent, false);
        final HomeOfferAdapter.HomeOfferHolder homeOfferHolder = new HomeOfferAdapter.HomeOfferHolder(view);
        return homeOfferHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeOfferAdapter.HomeOfferHolder holder, int position) {
        ProductsCategory offer = offerList.get(position);
        if (offer.getLike() == null) {
        } else if (Integer.parseInt(offer.getLike()) == 1) {
            holder.phone_image_like.setChecked(true);
        } else {
            holder.phone_image_like.setChecked(false);
        }

        if (offer.getDiscount() == null || offer.getDiscount().equals("0")) {
            holder.discount_linear.setVisibility(View.GONE);
        } else {
            holder.discount_linear.setVisibility(View.VISIBLE);
            holder.discount_result.setText(offer.getDiscount());
        }

        if (offer.getPrice_before() == null || offer.getPrice_before().equals("0")) {
            holder.phone_text_price_before.setVisibility(View.GONE);
            holder.phone_text_price_before_label.setVisibility(View.GONE);
            holder.phone_text_price_before_view.setVisibility(View.GONE);
            holder.phone_text_price_before_label_view.setVisibility(View.GONE);
        } else {
            holder.phone_text_price_before_label.setVisibility(View.VISIBLE);
            holder.phone_text_price_before_view.setVisibility(View.VISIBLE);
            holder.phone_text_price_before_label_view.setVisibility(View.VISIBLE);
            holder.phone_text_price_before.setVisibility(View.VISIBLE);
            Double priceBefore = Double.parseDouble(offer.getPrice_before());
            holder.phone_text_price_before.setText(PriceFormatter.toDecimalString(priceBefore, context.getApplicationContext()));
        }

        holder.phone_text_name.setText(offer.getName());
        holder.phone_text_type.setText(offer.getType());
        double price = Double.parseDouble(offer.getPrice());
        String priceDecimal = PriceFormatter.toDecimalString(price, context.getApplicationContext());
        holder.phone_text_price.setText(priceDecimal);
        Glide.with(context)
                .load("https://dleel.com/homepages/get/" + offer.getImgUrl())
                .placeholder(R.drawable.condition_logo)
                .centerCrop()
                .into(holder.phone_img_url);

        if (PreferenceUtils.getUserLogin(context) || PreferenceUtils.getCompanyLogin(context)) {
            int cartStatus = Integer.parseInt(offer.getCart());
            if (cartStatus == 2) {
                holder.phone_row_add_to_card.setBackgroundResource(R.drawable.row_out_of_stock_cart);
                holder.phone_row_add_to_card.setText(R.string.cart_out_of_stock_label);
            } else if (cartStatus == 0) {
                holder.phone_row_add_to_card.setBackgroundResource(R.drawable.row_already_added_cart);
                holder.phone_row_add_to_card.setText(R.string.add_to_card);
            } else {
                holder.phone_row_add_to_card.setBackgroundResource(R.drawable.row_add_to_car_bg);
                holder.phone_row_add_to_card.setText(R.string.phone_row_add_to_card_txt);
            }
        }
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }

    public class HomeOfferHolder extends RecyclerView.ViewHolder {
        public AppCompatImageView phone_img_url;
        public AppCompatCheckBox phone_image_like;
        public AppCompatTextView discount_result, phone_text_type, phone_text_name, phone_text_price, phone_text_price_before, phone_text_price_before_label, phone_row_add_to_card;
        View phone_text_price_before_view, phone_text_price_before_label_view;
        LinearLayoutCompat discount_linear;

        public HomeOfferHolder(View itemView) {
            super(itemView);
            phone_img_url = itemView.findViewById(R.id.phone_img_url);
            phone_image_like = itemView.findViewById(R.id.phone_image_like);
            discount_result = itemView.findViewById(R.id.discount_result);
            phone_text_type = itemView.findViewById(R.id.phone_text_type);
            phone_text_name = itemView.findViewById(R.id.phone_text_name);
            phone_text_price = itemView.findViewById(R.id.phone_text_price);
            phone_text_price_before = itemView.findViewById(R.id.phone_text_price_before);
            discount_linear = itemView.findViewById(R.id.discount_linear);
            phone_text_price_before_view = itemView.findViewById(R.id.phone_text_price_before_view);
            phone_text_price_before_label_view = itemView.findViewById(R.id.phone_text_price_before_label_view);
            phone_text_price_before_label = itemView.findViewById(R.id.phone_text_price_before_label);
            phone_row_add_to_card = itemView.findViewById(R.id.phone_row_add_to_card);

            phone_row_add_to_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!PreferenceUtils.getUserLogin(context) && !PreferenceUtils.getCompanyLogin(context)) {
                        Toasty.error(context, context.getString(R.string.please_login_first), Toast.LENGTH_LONG).show();
                        return;
                    }
                    if ((PreferenceUtils.getUserLogin(context) || PreferenceUtils.getCompanyLogin(context))) {
                        if (Integer.parseInt(offerList.get(getAdapterPosition()).getCart()) == 1)
                            offersOperation.onAddToOfferCart(Integer.parseInt(offerList.get(getAdapterPosition()).getId()), getAdapterPosition());
                    }
                }
            });

            phone_image_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean isChecked = phone_image_like.isChecked();

                    if (!PreferenceUtils.getUserLogin(context) && isChecked && !PreferenceUtils.getCompanyLogin(context)) {
                        Toasty.error(context, context.getString(R.string.please_login_first), Toast.LENGTH_LONG).show();
                        phone_image_like.setChecked(false);
                        return;
                    }
                    if ((PreferenceUtils.getUserLogin(context) || PreferenceUtils.getCompanyLogin(context)) && !isChecked) {
                        Toasty.error(context, context.getString(R.string.unlike_fiv), Toast.LENGTH_LONG).show();
                        phone_image_like.setChecked(true);
                        return;
                    }
                    if ((PreferenceUtils.getUserLogin(context) || PreferenceUtils.getCompanyLogin(context)) && isChecked) {
                        offersOperation.onCliickOfferLike(Integer.parseInt(offerList.get(getAdapterPosition()).getId()), getAdapterPosition());
                    }

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    offersOperation.onClickOffer(Integer.parseInt(offerList.get(getAdapterPosition()).getId()), getAdapterPosition());
                }
            });
        }
    }

    public interface OffersOperation {
        void onClickOffer(int id, int pos);

        void onCliickOfferLike(int id, int position);

        void onAddToOfferCart(int id, int position);
    }

}
