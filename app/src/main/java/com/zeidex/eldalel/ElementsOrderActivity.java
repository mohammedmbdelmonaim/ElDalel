package com.zeidex.eldalel;

import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.adapters.ElementsOrdersAdapter;
import com.zeidex.eldalel.adapters.OrdersAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ElementsOrderActivity extends BaseActivity {
    @BindView(R.id.activity_elemetnts_orders_recycler)
    RecyclerView activity_elemetnts_orders_recycler;
    ElementsOrdersAdapter elementsOrderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elements_orders);
        ButterKnife.bind(this);
        initializeRecycler();
    }

    @OnClick(R.id.item_elements_orders_back)
    public void onBack(){
        onBackPressed();
    }

    public void initializeRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        activity_elemetnts_orders_recycler.setLayoutManager(layoutManager);
        activity_elemetnts_orders_recycler.setItemAnimator(new DefaultItemAnimator());

        elementsOrderAdapter = new ElementsOrdersAdapter(this);
        activity_elemetnts_orders_recycler.setAdapter(elementsOrderAdapter);

    }
}
