package com.zeidex.eldalel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.R;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.BasketElementsHolder> {
    View view;
    private Context context;

    public OrdersOperation ordersOperation;
    public void setOrdersOperation(OrdersOperation ordersOperation) {
        this.ordersOperation = ordersOperation;
    }

    public OrdersAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public BasketElementsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.order_row, parent, false);
        final BasketElementsHolder accessoriesHolder = new BasketElementsHolder(view);
        return accessoriesHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BasketElementsHolder holder, int position) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordersOperation.onClickOrder(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class BasketElementsHolder extends RecyclerView.ViewHolder {
        public AppCompatImageView notification_row_roundimage;
        public TextView notification_row_text_notifi, notification_row_text_time;
        public CardView notification_row_cardview;
        public RelativeLayout relative;

        public BasketElementsHolder(View itemView) {
            super(itemView);
//            notification_row_roundimage = itemView.findViewById(R.id.notification_row_roundimage);
//            notification_row_text_notifi = itemView.findViewById(R.id.notification_row_text_notifi);
//            notification_row_text_time = itemView.findViewById(R.id.notification_row_text_time);
//            notification_row_cardview = itemView.findViewById(R.id.notification_row_cardview);
//            relativelative = itemView.findViewById(R.id.relative);
        }
    }

    public interface OrdersOperation{
        void onClickOrder(int position);
    }
}
