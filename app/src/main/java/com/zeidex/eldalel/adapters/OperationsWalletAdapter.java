package com.zeidex.eldalel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.R;
import com.zeidex.eldalel.response.GetWalletResponse;

import java.util.ArrayList;

public class OperationsWalletAdapter extends RecyclerView.Adapter<OperationsWalletAdapter.BasketElementsHolder> {
    View view;
    private Context context;
    ArrayList<GetWalletResponse.Datum> operations_wallet;

    public OperationsWalletAdapter(Context context) {
        this.context = context;
    }

    public OperationsWalletAdapter(Context context , ArrayList<GetWalletResponse.Datum> operations_wallet) {
        this.context = context;
        this.operations_wallet = operations_wallet;
    }

    @NonNull
    @Override
    public BasketElementsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.operation_wallet_row, parent, false);
        final BasketElementsHolder accessoriesHolder = new BasketElementsHolder(view);
        return accessoriesHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BasketElementsHolder holder, int position) {
        GetWalletResponse.Datum operation = operations_wallet.get(position);
        holder.wallet_type_text.setText(operation.getAmountType());
        holder.order_amount_text.setText(operation.getAmount()+"");
        holder.wallet_date_text.setText(operation.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return operations_wallet.size();
    }

    public class BasketElementsHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView wallet_type_text , order_amount_text , wallet_date_text;

        public BasketElementsHolder(View itemView) {
            super(itemView);
            wallet_type_text = itemView.findViewById(R.id.wallet_type_text);
            order_amount_text = itemView.findViewById(R.id.order_amount_text);
            wallet_date_text = itemView.findViewById(R.id.wallet_date_text);
        }
    }
}
