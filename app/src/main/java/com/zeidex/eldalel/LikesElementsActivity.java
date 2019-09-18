package com.zeidex.eldalel;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.adapters.ElementsOrdersAdapter;
import com.zeidex.eldalel.adapters.LikesElementsAdapter;
import com.zeidex.eldalel.utils.Animatoo;
import com.zeidex.eldalel.utils.GridSpacingItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LikesElementsActivity extends BaseActivity implements LikesElementsAdapter.LikesOperation {
    @BindView(R.id.likes_item_recycler_list)
    RecyclerView likes_item_recycler_list;
    LikesElementsAdapter likesElementsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elements_likes);
        ButterKnife.bind(this);
        initializeRecycler();
    }

    public void initializeRecycler(){
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        likes_item_recycler_list.setLayoutManager(mLayoutManager);
        likes_item_recycler_list.setItemAnimator(new DefaultItemAnimator());

        int spanCount = 2; // 3 columns
        int spacing = 20; // 50px
        boolean includeEdge = true;
        likes_item_recycler_list.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        likesElementsAdapter = new LikesElementsAdapter(this);
        likesElementsAdapter.setLikesOperation(this);
        likes_item_recycler_list.setAdapter(likesElementsAdapter);

    }

    @Override
    public void onClickLike(int position) {
        startActivity(new Intent(this , DetailItemActivity.class));
        Animatoo.animateSwipeLeft(this);
    }
}
