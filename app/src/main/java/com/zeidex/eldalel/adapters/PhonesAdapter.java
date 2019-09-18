package com.zeidex.eldalel.adapters;

import android.content.Context;
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

public class PhonesAdapter extends RecyclerView.Adapter<PhonesAdapter.PhoneHolder> {
    private Context context;

    public List<ProductsCategory> getPhoneList() {
        return phoneList;
    }

    List<ProductsCategory> phoneList;
    View view;

    private PhonesOperation phonesOperation;

    public void setnPhones(PhonesOperation phonesOperation) {
        this.phonesOperation = phonesOperation;
    }

    public PhonesAdapter(Context context) {
        this.context = context;
    }

    public PhonesAdapter(Context context, List<ProductsCategory> phoneList) {
        this.context = context;
        this.phoneList = phoneList;
    }

    @NonNull
    @Override
    public PhoneHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.phone_row, parent, false);
        final PhoneHolder phoneHolder = new PhoneHolder(view);
        return phoneHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneHolder holder, int position) {
        ProductsCategory phoneModel = phoneList.get(position);
        if (phoneModel.getLike() == null) {

        } else if (phoneModel.getLike().equalsIgnoreCase("1")) {
            holder.phone_image_like.setChecked(true);
        } else {
            holder.phone_image_like.setChecked(false);
        }

        if (phoneModel.getDiscount() == null) {
            holder.discount_linear.setVisibility(View.GONE);
        } else {
            holder.discount_linear.setVisibility(View.VISIBLE);
            holder.discount_result.setText(phoneModel.getDiscount());
        }

        if (phoneModel.getPrice_before() == null) {
            holder.phone_text_price_before.setVisibility(View.GONE);
            holder.phone_text_price_before_label.setVisibility(View.GONE);
            holder.phone_text_price_before_view.setVisibility(View.GONE);
            holder.phone_text_price_before_label_view.setVisibility(View.GONE);

        } else {
            holder.phone_text_price_before_label.setVisibility(View.VISIBLE);
            holder.phone_text_price_before_view.setVisibility(View.VISIBLE);
            holder.phone_text_price_before_label_view.setVisibility(View.VISIBLE);
            holder.phone_text_price_before.setVisibility(View.VISIBLE);
            holder.phone_text_price_before.setText(phoneModel.getPrice_before());
        }

        holder.phone_text_name.setText(phoneModel.getName());
        holder.phone_text_type.setText(phoneModel.getType());
        holder.phone_text_price.setText(phoneModel.getPrice());
        holder.phone_text_price_before.setText(phoneModel.getPrice_before());
        Glide.with(context)
                .load("https://www.dleel-sh.com/homepages/get/" + phoneModel.getImgUrl())
                .placeholder(R.drawable.condition_logo)
                .centerCrop()
                .into(holder.phone_img_url);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phonesOperation.onClickPhone(Integer.parseInt(phoneModel.getId()), position);
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
                    phonesOperation.onCliickPhoneLike(Integer.parseInt(phoneModel.getId()));
                }
            }
        });

        String cartStatus = phoneModel.getCart();
        if (cartStatus.equals(String.valueOf(CART_EMPTY))) {
            if (phoneModel.getAvailable_quantity().equals(String.valueOf(NOT_AVAILABLE))) {
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
                    if ((PreferenceUtils.getUserLogin(context) || PreferenceUtils.getCompanyLogin(context))) {
                        if (!phoneModel.getCart().equals(String.valueOf(CART_NOT_EMPTY)) && !phoneModel.getAvailable_quantity().equals(String.valueOf(NOT_AVAILABLE)))
                            phonesOperation.onAddToPhoneCart(Integer.parseInt(phoneModel.getId()), position);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return phoneList.size();
    }

    public class PhoneHolder extends RecyclerView.ViewHolder {
        public AppCompatImageView phone_img_url;
        public AppCompatCheckBox phone_image_like;
        public AppCompatTextView discount_result, phone_text_type, phone_text_name, phone_text_price, phone_text_price_before, phone_text_price_before_label, phone_row_add_to_card;
        View phone_text_price_before_view, phone_text_price_before_label_view;
        LinearLayoutCompat discount_linear;

        public PhoneHolder(View itemView) {
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

    public interface PhonesOperation {
        void onClickPhone(int id, int position);

        void onCliickPhoneLike(int id);

        void onAddToPhoneCart(int id, int position);
    }
}
