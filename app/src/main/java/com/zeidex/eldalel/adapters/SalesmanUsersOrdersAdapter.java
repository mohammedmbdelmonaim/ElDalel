package com.zeidex.eldalel.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.R;
import com.zeidex.eldalel.response.GetShipments;
import com.zeidex.eldalel.response.GetUsersOrders;

import java.util.ArrayList;
import java.util.List;

public class SalesmanUsersOrdersAdapter extends RecyclerView.Adapter<SalesmanUsersOrdersAdapter.UserOrdersHolder> {
    View view;
    private Context context;
    List<GetUsersOrders.Order> usersOrders;


    public void setUsersOrders(List<GetUsersOrders.Order> usersOrders){
        this.usersOrders = usersOrders;
        notifyDataSetChanged();
    }

    public SalesmanUsersOrdersAdapter(Context context) {
        this.context = context;
        this.usersOrders = new ArrayList<>();
    }

    public SalesmanUsersOrdersAdapter(Context context, List<GetUsersOrders.Order> orders) {
        this.context = context;
        this.usersOrders = orders;
    }

    @NonNull
    @Override
    public UserOrdersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.layout_salesman_user_order_list_item, parent, false);
        final UserOrdersHolder ordersHolder = new UserOrdersHolder(view);
        return ordersHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserOrdersHolder holder, int position) {
        GetUsersOrders.Order userOrder = usersOrders.get(position);
        holder.order_id_value_tv.setText(userOrder.getId()+"");
        holder.payment_type_value_tv.setText(userOrder.getUser().getPaymentType()+ "");
        holder.user_name_value_tv.setText(userOrder.getUser().getFirstName() + " " + userOrder.getUser().getLastName()+"");
        holder.mobile_value_tv.setText(userOrder.getUser().getMobile()+"");
        holder.order_date_value_tv.setText(userOrder.getCreatedAt()+"");
    }

    @Override
    public int getItemCount() {
        return usersOrders.size() > 0 ? usersOrders.size() : 0;
    }

    public class UserOrdersHolder extends RecyclerView.ViewHolder {
        public TextView payment_type_value_tv, user_name_value_tv, mobile_value_tv, order_date_value_tv, order_id_value_tv;

        public UserOrdersHolder(View itemView) {
            super(itemView);
            payment_type_value_tv = itemView.findViewById(R.id.payment_type_value_tv);
            user_name_value_tv = itemView.findViewById(R.id.user_name_value_tv);
            mobile_value_tv = itemView.findViewById(R.id.mobile_value_tv);
            order_date_value_tv = itemView.findViewById(R.id.order_date_value_tv);
            order_id_value_tv = itemView.findViewById(R.id.order_id_value_tv);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("shipment_id", usersOrders.get(getAdapterPosition()).getShipmentId().toString());
//                    Navigation.findNavController(v).navigate(R.id.action_nav_home_to_nav_company, bundle);
                }
            });
        }
    }
}

