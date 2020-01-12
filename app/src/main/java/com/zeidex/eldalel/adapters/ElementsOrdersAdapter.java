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
import com.zeidex.eldalel.response.GetShipmentOrders;
import com.zeidex.eldalel.utils.ChangeLang;
import com.zeidex.eldalel.utils.PriceFormatter;

import java.util.ArrayList;
import java.util.Locale;

public class ElementsOrdersAdapter extends RecyclerView.Adapter<ElementsOrdersAdapter.BasketElementsHolder> {
    View view;
    private Context context;

    ArrayList<GetShipmentOrders.Order> orders;

    OrderOperation orderOperation;

    public void setOrderOperation(OrderOperation orderOperation) {
        this.orderOperation = orderOperation;
    }

    public ElementsOrdersAdapter(Context context, ArrayList<GetShipmentOrders.Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public BasketElementsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.element_order_row, parent, false);
        final BasketElementsHolder accessoriesHolder = new BasketElementsHolder(view);
        return accessoriesHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BasketElementsHolder holder, int position) {
        GetShipmentOrders.Order order = orders.get(position);
        Locale locale = ChangeLang.getLocale(context.getResources());
        String loo = locale.getLanguage();
        if (loo.equalsIgnoreCase("ar")) {
            String arr[] = order.getNameAr().split(" ", 2); // get first word
            String firstWord = arr[0];
            holder.order_element_name.setText(firstWord);
            holder.order_element_desc.setText(order.getNameAr());
        } else if (loo.equalsIgnoreCase("en")) {
            String arr[] = order.getName().split(" ", 2); // get first word
            String firstWord = arr[0];
            holder.order_element_name.setText(firstWord);
            holder.order_element_desc.setText(order.getName());
        }
        holder.order_element_status.setText(order.getStatus());

        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (order.getStatus_code() == 4){
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.order_element_status.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.status_refuse_bg));
            } else {
                holder.order_element_status.setBackground(context.getResources().getDrawable(R.drawable.status_refuse_bg));
            }
        }else if (order.getStatus_code() == 4){
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.order_element_status.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.status_connect_bg));
            } else {
                holder.order_element_status.setBackground(context.getResources().getDrawable(R.drawable.status_connect_bg));
            }
        }else if (order.getStatus_code() == 5){
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.order_element_status.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.status_pending_bg));
            } else {
                holder.order_element_status.setBackground(context.getResources().getDrawable(R.drawable.status_pending_bg));
            }
        }else{
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.order_element_status.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.status_pending_bg));
            } else {
                holder.order_element_status.setBackground(context.getResources().getDrawable(R.drawable.status_pending_bg));
            }
        }


        Double price = order.getProductPrice();
        if (price != null)
            holder.order_element_price.setText(PriceFormatter.toDecimalString(price, context.getApplicationContext()));

        Glide.with(context)
                .load("https://dleel.com/homepages/get/" + order.getPhotos().get(0).getFilename())
                .placeholder(R.drawable.condition_logo)
                .centerCrop()
                .into(holder.order_element_img);

        holder.order_element_currency_number.setText(order.getQuantity()+"");
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class BasketElementsHolder extends RecyclerView.ViewHolder {
        public AppCompatImageView order_element_img;
        public AppCompatTextView order_element_name, order_element_desc, order_element_currency_price_before, order_element_number, order_element_price, order_element_currency_number, order_element_status;
//        LinearLayoutCompat order_element_currency_linear, phone_text_price_before_linear;

        public BasketElementsHolder(View itemView) {
            super(itemView);
            order_element_img = itemView.findViewById(R.id.order_element_img);
            order_element_name = itemView.findViewById(R.id.order_element_name);
            order_element_desc = itemView.findViewById(R.id.order_element_desc);
            order_element_currency_price_before = itemView.findViewById(R.id.order_element_currency_price_before);
            order_element_price = itemView.findViewById(R.id.order_element_price);
            order_element_currency_number = itemView.findViewById(R.id.order_element_currency_number);
//            phone_text_price_before_linear = itemView.findViewById(R.id.phone_text_price_before_linear);
            order_element_status = itemView.findViewById(R.id.order_element_status);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = getAdapterPosition();
                    orderOperation.onClickOrderProduct(orders.get(getAdapterPosition()).getId(), getAdapterPosition());
                }
            });
        }


    }

    public interface OrderOperation {
        void onClickOrderProduct(int id, int pos);

    }
}
