package com.zeidex.eldalel.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.R;
import com.zeidex.eldalel.models.ColorProduct;

import java.util.ArrayList;

public class DetailColorsItemAdapter extends RecyclerView.Adapter<DetailColorsItemAdapter.DetailColorsItemHolder> {
    View view;
    private Context context;

    int selectedPos = -1;

    ArrayList<ColorProduct> colors;

    private DetailColorsOperation detailColorsOperation;
    public void setDetailColorsOperation(DetailColorsOperation detailColorsOperation) {
        this.detailColorsOperation = detailColorsOperation;
    }

    public DetailColorsItemAdapter(Context context ,  ArrayList<ColorProduct> colors) {
        this.context = context;
        this.colors = colors;
    }

    @NonNull
    @Override
    public DetailColorsItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.detail_colors_row, parent, false);
        final DetailColorsItemHolder accessoriesHolder = new DetailColorsItemHolder(view);
        return accessoriesHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailColorsItemHolder holder, int position) {
        if (selectedPos == position){
            holder.detail_colores_linear.setSelected(true);
            holder.detail_color_label.setTextColor(Color.parseColor("#047AC0"));

        }else {
            holder.detail_colores_linear.setSelected(false);
            holder.detail_color_label.setTextColor(Color.parseColor("#606060"));
        }

        if (colors.get(position).getName().equalsIgnoreCase("black")){
            holder.detail_color_img_url.setColorFilter(context.getResources().getColor(R.color.black_color));
        }else if (colors.get(position).getName().equalsIgnoreCase("blue")){
            holder.detail_color_img_url.setColorFilter(context.getResources().getColor(R.color.blue_color));
        }else if (colors.get(position).getName().equalsIgnoreCase("GREEN")){
            holder.detail_color_img_url.setColorFilter(context.getResources().getColor(R.color.green_color));
        }else if (colors.get(position).getName().equalsIgnoreCase("grey")){
            holder.detail_color_img_url.setColorFilter(context.getResources().getColor(R.color.gray_color));
        }else if (colors.get(position).getName().equalsIgnoreCase("red")){
            holder.detail_color_img_url.setColorFilter(context.getResources().getColor(R.color.red_color));
        }

        holder.detail_color_label.setText(colors.get(position).getName());
        holder.detail_colores_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemChanged(selectedPos);
                selectedPos = position;
                notifyItemChanged(selectedPos);
                detailColorsOperation.onClickColor(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    public class DetailColorsItemHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView detail_color_label;
        public LinearLayoutCompat detail_colores_linear;
        public AppCompatImageView detail_color_img_url;

        public DetailColorsItemHolder(View itemView) {
            super(itemView);
            detail_color_label = itemView.findViewById(R.id.detail_color_label);
            detail_colores_linear = itemView.findViewById(R.id.detail_colores_linear);
            detail_color_img_url = itemView.findViewById(R.id.detail_color_img_url);
        }
    }

    public interface DetailColorsOperation{
        void onClickColor(int position);
    }
}
