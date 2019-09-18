package com.zeidex.eldalel.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
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

import java.util.List;

import es.dmoral.toasty.Toasty;

import static com.zeidex.eldalel.utils.Constants.CART_EMPTY;
import static com.zeidex.eldalel.utils.Constants.CART_NOT_EMPTY;
import static com.zeidex.eldalel.utils.Constants.NOT_AVAILABLE;

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
            holder.phone_text_price_before.setText(accessory.getPrice_before());
        }

        holder.phone_text_name.setText(accessory.getName());
        holder.phone_text_type.setText(accessory.getType());
        holder.phone_text_price.setText(accessory.getPrice());
        Glide.with(context)
                .load("https://www.dleel-sh.com/homepages/get/" + accessory.getImgUrl())
                .placeholder(R.drawable.condition_logo)
                .centerCrop()
                .into(holder.phone_img_url);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessoriesOperation.onClickAcssesory(Integer.parseInt(accessory.getId()), position);
            }
        });


        holder.phone_image_like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!PreferenceUtils.getUserLogin(context) && isChecked && !PreferenceUtils.getCompanyLogin(context)) {
                    Toasty.error(context, context.getString(R.string.please_login_first), Toast.LENGTH_LONG).show();
                    holder.phone_image_like.setChecked(false);
                    return;
                }
                if ((PreferenceUtils.getUserLogin(context) || PreferenceUtils.getCompanyLogin(context)) && !isChecked) {
                    Toasty.error(context, context.getString(R.string.unlike_fiv), Toast.LENGTH_LONG).show();
                    holder.phone_image_like.setChecked(true);
                    return;
                }
                if ((PreferenceUtils.getUserLogin(context) || PreferenceUtils.getCompanyLogin(context)) && isChecked) {
                    accessoriesOperation.onCliickAccessoryLike(Integer.parseInt(accessory.getId()));
                }
            }
        });

        String cartStatus = accessory.getCart();
        Log.d("cart_status", "onBindViewHolder: " + cartStatus);
        if (cartStatus.equals(String.valueOf(CART_EMPTY))) {
            if (accessory.getAvailable_quantity().equals(String.valueOf(NOT_AVAILABLE))) {
                holder.phone_row_add_to_card.setBackgroundResource(R.drawable.row_out_of_stock_cart);
                holder.phone_row_add_to_card.setText(R.string.cart_out_of_stock_label);
            } else {
                holder.phone_row_add_to_card.setBackgroundResource(R.drawable.row_add_to_car_bg);
                holder.phone_row_add_to_card.setText(R.string.phone_row_add_to_card_txt);
            }
        } else if (cartStatus.equals(String.valueOf(CART_NOT_EMPTY))) {
            holder.phone_row_add_to_card.setBackgroundResource(R.drawable.row_already_added_cart);
            holder.phone_row_add_to_card.setText(R.string.add_to_card);
        }

        holder.phone_row_add_to_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PreferenceUtils.getUserLogin(context) && !PreferenceUtils.getCompanyLogin(context)) {
                    Toasty.error(context, context.getString(R.string.please_login_first), Toast.LENGTH_LONG).show();
                    return;
                }
                if ((PreferenceUtils.getUserLogin(context) || PreferenceUtils.getCompanyLogin(context))) {
                    if (!accessory.getCart().equals(String.valueOf(CART_NOT_EMPTY)) && !accessory.getAvailable_quantity().equals(String.valueOf(NOT_AVAILABLE)))
                        accessoriesOperation.onAddToAccessoryCart(Integer.parseInt(accessory.getId()), position);
                }
            }
        });
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
        }
    }

    public interface AccessoriesOperation {
        void onClickAcssesory(int id, int pos);

        void onCliickAccessoryLike(int id);

        void onAddToAccessoryCart(int id, int position);
    }
}
