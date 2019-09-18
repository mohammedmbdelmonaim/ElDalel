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

import java.util.ArrayList;

public class DetailSizeItemAdapter extends RecyclerView.Adapter<DetailSizeItemAdapter.DetailSizeItemHolder> {
    View view;
    private Context context;
    int selectedPos = -1;
    ArrayList<String> capicities;

    public DetailSizeItemAdapter(Context context, ArrayList<String> capicities) {
        this.context = context;
        this.capicities = capicities;
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
        holder.detail_size_label.setText(capicities.get(position));
        holder.detail_size_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemChanged(selectedPos);
                selectedPos = position;
                notifyItemChanged(selectedPos);
            }
        });
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
        }
    }

    public interface AccessoriesOperation {
        void onClickPhone(int position);
    }
}