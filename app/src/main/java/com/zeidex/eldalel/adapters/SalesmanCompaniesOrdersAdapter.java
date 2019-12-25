package com.zeidex.eldalel.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.R;
import com.zeidex.eldalel.response.GetCompaniesOrders;
import com.zeidex.eldalel.response.GetUsersOrders;

import java.util.ArrayList;
import java.util.List;

public class SalesmanCompaniesOrdersAdapter extends RecyclerView.Adapter<SalesmanCompaniesOrdersAdapter.CompanyOrdersHolder>{
    View view;
    private Context context;
    List<GetCompaniesOrders.Order> companiesOrders;


    public void setCompaniesOrders(List<GetCompaniesOrders.Order> companiesOrders){
        this.companiesOrders = companiesOrders;
        notifyDataSetChanged();
    }

    public SalesmanCompaniesOrdersAdapter(Context context) {
        this.context = context;
        this.companiesOrders = new ArrayList<>();
    }


    @NonNull
    @Override
    public CompanyOrdersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.layout_salesman_company_order_list_item, parent, false);
        final CompanyOrdersHolder ordersHolder = new CompanyOrdersHolder(view);
        return ordersHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyOrdersHolder holder, int position) {
        GetCompaniesOrders.Order companyOrder = companiesOrders.get(position);
        holder.shipment_id_value_tv.setText(companyOrder.getShipment().getShipmentNumber() +"");

        String paymentType;
        int paymentId = companyOrder.getCompany().getPaymentType();

        switch (paymentId) {
//            case 1:
//                paymentType = context.getResources().getString(R.string.credit_card_payment_txt_label);
//                break;

            case 2:
                paymentType = context.getResources().getString(R.string.pay_on_arrive_payment_txt_label);
                break;

            case 3:
                paymentType = context.getResources().getString(R.string.bank_payment_txt_label);
                break;

            case 4:
                paymentType = context.getResources().getString(R.string.postpaid_payment_method);
                break;

            default:
                paymentType = "";
                break;
        }
        holder.payment_type_value_tv.setText(paymentType);
        holder.company_name_value_tv.setText(companyOrder.getCompany().getName());
        holder.responsible_value_tv.setText(companyOrder.getCompany().getResponsible());
        holder.mobile_value_tv.setText(companyOrder.getCompany().getMobile()+"");
        holder.order_date_value_tv.setText(companyOrder.getCreatedAt()+"");
    }

    @Override
    public int getItemCount() {
        return companiesOrders.size() > 0 ? companiesOrders.size() : 0;
    }

    public class CompanyOrdersHolder extends RecyclerView.ViewHolder {
        public TextView payment_type_value_tv, company_name_value_tv,responsible_value_tv, mobile_value_tv, order_date_value_tv, shipment_id_value_tv ;

        public CompanyOrdersHolder(View itemView) {
            super(itemView);
            payment_type_value_tv = itemView.findViewById(R.id.payment_type_value_tv);
            company_name_value_tv = itemView.findViewById(R.id.company_name_value_tv);
            mobile_value_tv = itemView.findViewById(R.id.mobile_value_tv);
            order_date_value_tv = itemView.findViewById(R.id.order_date_value_tv);
            shipment_id_value_tv = itemView.findViewById(R.id.shipment_id_value_tv);
            responsible_value_tv = itemView.findViewById(R.id.responsible_value_tv);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("shipment_id", companiesOrders.get(getAdapterPosition()).getShipmentId().toString());
                    bundle.putString("order_id", companiesOrders.get(getAdapterPosition()).getId().toString());
                    bundle.putString("type", "company");
                    bundle.putInt("shipment_number", companiesOrders.get(getAdapterPosition()).getShipment().getShipmentNumber());
                    Navigation.findNavController(v).navigate(R.id.action_nav_company_to_salesmanShipmentProductsFragment, bundle);
                }
            });
        }
    }

}
