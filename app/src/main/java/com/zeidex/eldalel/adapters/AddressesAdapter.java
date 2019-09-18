package com.zeidex.eldalel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.R;
import com.zeidex.eldalel.models.ProductsCategory;

import java.util.List;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.AddressesHolder> {
    private Context context;
    List<ProductsCategory> accessoryList;
    View view;


    public AddressesAdapter(Context context) {
        this.context = context;
    }

    public AddressesAdapter(Context context, List<ProductsCategory> accessoryList) {
        this.context = context;
        this.accessoryList = accessoryList;
    }

    @NonNull
    @Override
    public AddressesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.address_row, parent, false);
        final AddressesHolder addressesHolder = new AddressesHolder(view);
        return addressesHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddressesHolder holder, int position) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public class AddressesHolder extends RecyclerView.ViewHolder {
        public AppCompatImageView maps_payment_img , unchecked_payment_img , delete_payment_img , edit_payment_img;
        public AppCompatTextView name_payment_addresses_text, name_payment_addresses_textView , address_payment_addresses_text , address_payment_addresses_textView , mobile_payment_addresses_text,mobile_payment_addresses_textView;
        ConstraintLayout address_row_constraint;

        public AddressesHolder(View itemView) {
            super(itemView);
            maps_payment_img = itemView.findViewById(R.id.maps_payment_img);
            unchecked_payment_img = itemView.findViewById(R.id.unchecked_payment_img);
            delete_payment_img = itemView.findViewById(R.id.delete_payment_img);
            edit_payment_img = itemView.findViewById(R.id.edit_payment_img);
            name_payment_addresses_text = itemView.findViewById(R.id.name_payment_addresses_text);
            name_payment_addresses_textView = itemView.findViewById(R.id.name_payment_addresses_textView);
            address_payment_addresses_text = itemView.findViewById(R.id.address_payment_addresses_text);
            address_payment_addresses_textView = itemView.findViewById(R.id.address_payment_addresses_textView);
            mobile_payment_addresses_text = itemView.findViewById(R.id.mobile_payment_addresses_text);
            mobile_payment_addresses_textView = itemView.findViewById(R.id.mobile_payment_addresses_textView);
            address_row_constraint = itemView.findViewById(R.id.address_row_constraint);

        }
    }


}
