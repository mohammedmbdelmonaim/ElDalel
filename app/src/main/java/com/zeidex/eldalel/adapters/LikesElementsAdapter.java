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
import com.zeidex.eldalel.models.Category;

import java.util.List;

public class LikesElementsAdapter extends RecyclerView.Adapter<LikesElementsAdapter.CategoryHolder> {
    private Context context;
    List<Category> categoryList;
    View view;

    private LikesOperation likesOperation;

    public void setLikesOperation(LikesOperation likesOperation) {
        this.likesOperation = likesOperation;
    }

    public LikesElementsAdapter(Context context) {
        this.context = context;
    }

    public LikesElementsAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.likes_item_row, parent, false);
        final CategoryHolder categoryHolder = new CategoryHolder(view);
        return categoryHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likesOperation.onClickLike(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {
        public AppCompatImageView notification_row_roundimage;
        public TextView notification_row_text_notifi, notification_row_text_time;
        public CardView notification_row_cardview;
        public RelativeLayout relative;

        public CategoryHolder(View itemView) {
            super(itemView);
//            notification_row_roundimage = itemView.findViewById(R.id.notification_row_roundimage);
//            notification_row_text_notifi = itemView.findViewById(R.id.notification_row_text_notifi);
//            notification_row_text_time = itemView.findViewById(R.id.notification_row_text_time);
//            notification_row_cardview = itemView.findViewById(R.id.notification_row_cardview);
//            relativelative = itemView.findViewById(R.id.relative);
        }
    }

    public interface LikesOperation{
        void onClickLike(int position);
    }
}
