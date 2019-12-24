package com.zeidex.eldalel.ui.salesmanhome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.zeidex.eldalel.R;

import butterknife.OnClick;

public class SalesmanHomeFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_salesman_home, container, false);
        root.findViewById(R.id.company_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(SalesmanHomeFragment.this).navigate(R.id.action_nav_home_to_nav_company);
            }
        });

        root.findViewById(R.id.customer_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(SalesmanHomeFragment.this).navigate(R.id.action_nav_home_to_nav_customer);
            }
        });
        return root;
    }
}