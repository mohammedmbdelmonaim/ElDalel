package com.zeidex.eldalel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.R;
import com.zeidex.eldalel.models.CapacityProduct;

import java.util.ArrayList;

public class DetailSizeItemAdapter extends RecyclerView.Adapter<DetailSizeItemAdapter.DetailSizeItemHolder> {
    View view;
    private Context context;
    int selectedPos = 0;
    ArrayList<CapacityProduct> capicities;

    public DetailSizeItemAdapter(Context context, ArrayList<CapacityProduct> capicities) {
        this.context = context;
        this.capicities = capicities;
    }

    private DetailCapacityOperation detailCapacityOperation;

    public void setDetailCapacityOperation(DetailCapacityOperation detailCapacityOperation) {
        this.detailCapacityOperation = detailCapacityOperation;
    }

    @NonNull
    @Override
    public DetailSizeItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.detail_size_row, parent, false);
        final DetailSizeItemHolder accessoriesHolder = new DetailSizeItemHolder(view);
        return accessoriesHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailSizeItemHolder holder, int position) {
        if (selectedPos == position) {
            holder.detail_size_linear.setSelected(true);

        } else {
            holder.detail_size_linear.setSelected(false);
        }
        holder.detail_size_label.setText(capicities.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return capicities.size();
    }


    public class DetailSizeItemHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView detail_size_label;
        public LinearLayoutCompat detail_size_linear;

        public DetailSizeItemHolder(View itemView) {
            super(itemView);
            detail_size_label = itemView.findViewById(R.id.detail_size_label);
            detail_size_linear = itemView.findViewById(R.id.detail_size_linear);

            detail_size_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyItemChanged(selectedPos);
                    selectedPos = getAdapterPosition();
                    notifyItemChanged(selectedPos);
                    detailCapacityOperation.onClickCapacity(getAdapterPosition());
                }
            });
        }
    }

    public interface DetailCapacityOperation {
        void onClickCapacity(int position);
    }
}