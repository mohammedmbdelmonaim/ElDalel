package com.zeidex.eldalel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.R;
import com.zeidex.eldalel.response.GetShipments;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.BasketElementsHolder> {
    View view;
    private Context context;
    ArrayList<GetShipments.Shipment> shipments;

    public OrdersOperation ordersOperation;
    public void setOrdersOperation(OrdersOperation ordersOperation) {
        this.ordersOperation = ordersOperation;
    }

    public OrdersAdapter(Context context) {
        this.context = context;
    }

    public OrdersAdapter(Context context , ArrayList<GetShipments.Shipment> shipments) {
        this.context = context;
        this.shipments = shipments;
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
        GetShipments.Shipment shipment = shipments.get(position);
        holder.order_date_text.setText(shipment.getCreatedAt());
        holder.order_number_order_text.setText(shipment.getOrdersCount()+"");
        holder.order_state_text.setText(shipment.getPaymentType());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordersOperation.onClickOrder(shipment.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return shipments.size();
    }

    public class BasketElementsHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView order_number_order_text , order_date_text , order_state_text;

        public BasketElementsHolder(View itemView) {
            super(itemView);
            order_number_order_text = itemView.findViewById(R.id.order_number_order_text);
            order_date_text = itemView.findViewById(R.id.order_date_text);
            order_state_text = itemView.findViewById(R.id.order_state_text);
        }
    }

    public interface OrdersOperation{
        void onClickOrder(int shipment_id);
    }
}
