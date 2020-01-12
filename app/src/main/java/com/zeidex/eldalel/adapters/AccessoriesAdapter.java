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

public class AccessoriesAdapter extends RecyclerView.Adapter<AccessoriesAdapter.AccessoriesHolder> {
    private Context context;

    public List<ProductsCategory> getAccessoryList() {
        return accessoryList;
    }

    List<ProductsCategory> accessoryList;
    View view;

    private AccessoriesOperation accessoriesOperation;

    public void setAccessoriesOperation(AccessoriesOperation accessoriesOperation) {
        this.accessoriesOperation = accessoriesOperation;
    }

    public void setAccessoryList(ArrayList<ProductsCategory> accessoryList){
        this.accessoryList = accessoryList;
        notifyDataSetChanged();
    }

    public AccessoriesAdapter(Context context) {
        this.context = context;
    }

    public AccessoriesAdapter(Context context, List<ProductsCategory> accessoryList) {
        this.context = context;
        this.accessoryList = accessoryList;
    }

    @NonNull
    @Override
    public AccessoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.phone_row, parent, false);
        final AccessoriesHolder accessoriesHolder = new AccessoriesHolder(view);
        return accessoriesHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AccessoriesHolder holder, int position) {
        ProductsCategory accessory = accessoryList.get(position);
        if (accessory.getLike() == null) {
        } else if (Integer.parseInt(accessory.getLike()) == 1) {
            holder.phone_image_like.setChecked(true);
        } else {
            holder.phone_image_like.setChecked(false);
        }

        if (accessory.getDiscount() == null) {
            holder.discount_linear.setVisibility(View.GONE);
        } else {
            holder.discount_linear.setVisibility(View.VISIBLE);
            holder.discount_result.setText(accessory.getDiscount());
        }

        if (accessory.getPrice_before() == null) {
            holder.phone_text_price_before.setVisibility(View.GONE);
            holder.phone_text_price_before_label.setVisibility(View.GONE);
            holder.phone_text_price_before_view.setVisibility(View.GONE);
            holder.phone_text_price_before_label_view.setVisibility(View.GONE);

        } else {
            holder.phone_text_price_before_label.setVisibility(View.VISIBLE);
            holder.phone_text_price_before_view.setVisibility(View.VISIBLE);
            holder.phone_text_price_before_label_view.setVisibility(View.VISIBLE);
            holder.phone_text_price_before.setVisibility(View.VISIBLE);
            Double priceBefore = Double.parseDouble(accessory.getPrice_before());
            holder.phone_text_price_before.setText(PriceFormatter.toDecimalString(priceBefore, context.getApplicationContext()));
        }

        holder.phone_text_name.setText(accessory.getName());
        holder.phone_text_type.setText(accessory.getType());
        double price = Double.parseDouble(accessory.getPrice());
        holder.phone_text_price.setText(PriceFormatter.toDecimalString(price, context.getApplicationContext()));
        Glide.with(context)
                .load("https://dleel.com/homepages/get/" + accessory.getImgUrl())
                .placeholder(R.drawable.condition_logo)
                .centerCrop()
                .into(holder.phone_img_url);

        if (PreferenceUtils.getUserLogin(context) || PreferenceUtils.getCompanyLogin(context)) {
            int cartStatus = Integer.parseInt(accessory.getCart());
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
        return accessoryList.size();
    }

    public class AccessoriesHolder extends RecyclerView.ViewHolder {
        public AppCompatImageView phone_img_url;
        public AppCompatCheckBox phone_image_like;
        public AppCompatTextView discount_result, phone_text_type, phone_text_name, phone_text_price, phone_text_price_before, phone_text_price_before_label, phone_row_add_to_card;
        View phone_text_price_before_view, phone_text_price_before_label_view;
        LinearLayoutCompat discount_linear;

        public AccessoriesHolder(View itemView) {
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
                        if (Integer.parseInt(accessoryList.get(getAdapterPosition()).getCart()) == 1)
                            accessoriesOperation.onAddToAccessoryCart(Integer.parseInt(accessoryList.get(getAdapterPosition()).getId()), getAdapterPosition());
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
                        accessoriesOperation.onCliickAccessoryLike(Integer.parseInt(accessoryList.get(getAdapterPosition()).getId()), getAdapterPosition());
                    }

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    accessoriesOperation.onClickAcssesory(Integer.parseInt(accessoryList.get(getAdapterPosition()).getId()), getAdapterPosition());
                }
            });
        }
    }

    public interface AccessoriesOperation {
        void onClickAcssesory(int id, int pos);

        void onCliickAccessoryLike(int id, int position);

        void onAddToAccessoryCart(int id, int position);
    }
}