package com.zeidex.eldalel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.zeidex.eldalel.adapters.OrdersAdapter;
import com.zeidex.eldalel.utils.Animatoo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrdersActivity extends BaseActivity implements OrdersAdapter.OrdersOperation {
    @BindView(R.id.activity_orders_recycler)
    RecyclerView activity_orders_recycler;
    OrdersAdapter ordersAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        ButterKnife.bind(this);
        initializeRecycler();
    }

    @OnClick(R.id.item_orders_back)
    public void onBack(){
        onBackPressed();
    }

    public void initializeRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        activity_orders_recycler.setLayoutManager(layoutManager);
        activity_orders_recycler.setItemAnimator(new DefaultItemAnimator());

        ordersAdapter = new OrdersAdapter(this);
        ordersAdapter.setOrdersOperation(this);
        activity_orders_recycler.setAdapter(ordersAdapter);

    }

    @Override
    public void onClickOrder(int position) {
        startActivity(new Intent(this , ElementsOrderActivity.class));
        Animatoo.animateSwipeLeft(this);
    }
}
