package com.zeidex.eldalel;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {
    public static final String SEARCH_NAME_ARGUMENT = "search_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        String searchName = getIntent().getStringExtra(SEARCH_NAME_ARGUMENT);
        Bundle bundle = new Bundle();
        if(searchName != null){
            bundle.putString(SEARCH_NAME_ARGUMENT, searchName);
        }
        ProductsFragment productsFragment = new ProductsFragment();
        productsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.search_products_results_container, productsFragment).commit();
    }
}
