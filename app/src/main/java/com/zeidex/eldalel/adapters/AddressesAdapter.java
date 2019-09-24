package com.zeidex.eldalel.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.R;
import com.zeidex.eldalel.response.GetAddresses;

import java.util.List;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.AddressesHolder> {
    private Context context;
    View view;
    String state_addresses;

    List<GetAddresses.Address> addresses;
    int selectedPos = 0;

    private AddressOperation addressOperation;

    public void setAddressOperation(AddressOperation addressOperation) {
        this.addressOperation = addressOperation;
    }

    public AddressesAdapter(Context context) {
        this.context = context;
    }

    public AddressesAdapter(Context context, List<GetAddresses.Address> addresses ,String state_addresses) {
        this.context = context;
        this.addresses = addresses;
        this.state_addresses = state_addresses;
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
        GetAddresses.Address address = addresses.get(position);
        if (selectedPos == position){
            holder.address_row_constraint.setSelected(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.unchecked_payment_img.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_check_address, context.getApplicationContext().getTheme()));
            } else {
                holder.unchecked_payment_img.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_check_address));
            }
        }else {
            holder.address_row_constraint.setSelected(false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.unchecked_payment_img.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_unchecked_payment, context.getApplicationContext().getTheme()));
            } else {
                holder.unchecked_payment_img.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_unchecked_payment));
            }
        }

        if (state_addresses.equalsIgnoreCase("address")){
            holder.edit_payment_img.setVisibility(View.VISIBLE);
        }else if(state_addresses.equalsIgnoreCase("payment")){
            holder.edit_payment_img.setVisibility(View.GONE);
        }
        holder.name_payment_addresses_textView.setText(address.getFullName());
        holder.address_payment_addresses_textView.setText(address.getAddress());
        holder.mobile_payment_addresses_textView.setText(address.getMobile());

    }

    @Override
    public int getItemCount() {
        return addresses.size();
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

            address_row_constraint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyItemChanged(selectedPos);
                    selectedPos = getAdapterPosition();
                    notifyItemChanged(selectedPos);
                    addressOperation.onClickAddress(getAdapterPosition() , addresses.get(getAdapterPosition()).getId());
                }
            });

            edit_payment_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addressOperation.onEditAddress(getAdapterPosition() , addresses.get(getAdapterPosition()).getId());
                }
            });
        }


    }
    public interface AddressOperation{
        void onClickAddress(int position , int address_id);
        void onEditAddress(int position , int address_id);
    }

}
