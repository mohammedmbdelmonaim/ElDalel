package com.zeidex.eldalel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zeidex.eldalel.R;
import com.zeidex.eldalel.models.BasketProducts;
import com.zeidex.eldalel.utils.PriceFormatter;

import java.util.ArrayList;
import java.util.List;

public class BasketElementsAdapter extends RecyclerView.Adapter<BasketElementsAdapter.BasketElementsHolder> {
    View view;
    private Context context;
    ArrayList<BasketProducts> basketProducts;

    private BasketOperation basketOperation;

    public void setBasketOperation(BasketOperation basketOperation) {
        this.basketOperation = basketOperation;
    }

    public List<BasketProducts> getBasketProducts() {
        return basketProducts;
    }

    public BasketElementsAdapter(Context context, ArrayList<BasketProducts> basketProducts) {
        this.context = context;
        this.basketProducts = basketProducts;
    }

    @NonNull
    @Override
    public BasketElementsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.basket_element_row, parent, false);
        final BasketElementsHolder basketElementsHolder = new BasketElementsHolder(view);
        return basketElementsHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BasketElementsHolder holder, int position) {
        BasketProducts basketProductsModel = basketProducts.get(position);


        if (basketProductsModel.getPrice_before() == null || basketProductsModel.getPrice_before().equals("0")) {
            holder.phone_text_price_before_linear.setVisibility(View.GONE);

        } else {
            holder.phone_text_price_before_linear.setVisibility(View.VISIBLE);
            String priceBeforeString = basketProductsModel.getPrice_before();
            Double priceBefore = Double.parseDouble(priceBeforeString);
            holder.basket_element_currency_price_before.setText(PriceFormatter.toDecimalString(priceBefore, context.getApplicationContext()));

        }

        holder.basket_element_name.setText(basketProductsModel.getTitle());
        holder.basket_element_desc.setText(basketProductsModel.getName());
        Double price = Double.parseDouble(basketProductsModel.getPrice());
        if (price != null)
            holder.basket_element_price.setText(PriceFormatter.toDecimalString(price, context.getApplicationContext()));

        Glide.with(context)
                .load("https://dleel.com/homepages/get/" + basketProductsModel.getImgurl())
                .placeholder(R.drawable.condition_logo)
                .centerCrop()
                .into(holder.basket_element_img);

        holder.basket_element_currency_number.setText(basketProductsModel.getItem_count());
    }

    @Override
    public int getItemCount() {
        return basketProducts.size();
    }

    public class BasketElementsHolder extends RecyclerView.ViewHolder {
        public AppCompatImageView basket_element_img, basket_element_currency_img_delete;
        public AppCompatTextView basket_element_name, basket_element_desc, basket_element_currency_price_before, basket_element_number, basket_element_price, basket_element_currency_number;
        LinearLayoutCompat basket_element_currency_linear, phone_text_price_before_linear;

        public BasketElementsHolder(View itemView) {
            super(itemView);
            basket_element_img = itemView.findViewById(R.id.basket_element_img);
            basket_element_currency_img_delete = itemView.findViewById(R.id.basket_element_currency_img_delete);
            basket_element_name = itemView.findViewById(R.id.basket_element_name);
            basket_element_desc = itemView.findViewById(R.id.basket_element_desc);
            basket_element_currency_price_before = itemView.findViewById(R.id.basket_element_currency_price_before);
//            basket_element_number = itemView.findViewById(R.id.basket_element_number);
            basket_element_currency_linear = itemView.findViewById(R.id.basket_element_currency_linear);
            basket_element_price = itemView.findViewById(R.id.basket_element_price);
            basket_element_currency_number = itemView.findViewById(R.id.basket_element_currency_number);
            phone_text_price_before_linear = itemView.findViewById(R.id.phone_text_price_before_linear);

            basket_element_currency_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    basketOperation.onChangeQuantity(Integer.parseInt(basketProducts.get(getAdapterPosition()).getProduct_id()), getAdapterPosition() , Integer.parseInt(basketProducts.get(getAdapterPosition()).getCart_id()));
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    basketOperation.onClickBasketProduct(Integer.parseInt(basketProducts.get(getAdapterPosition()).getProduct_id()), getAdapterPosition());
                }
            });

            basket_element_currency_img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    basketOperation.onDeleteItem(Integer.parseInt(basketProducts.get(getAdapterPosition()).getCart_id()), getAdapterPosition());
                }
            });
        }
    }

    public interface BasketOperation {
        void onClickBasketProduct(int id, int pos);

        void onChangeQuantity(int id, int pos , int cart_id);

        void onDeleteItem(int id, int pos);
    }
}
