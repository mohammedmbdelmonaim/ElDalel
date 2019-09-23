package com.zeidex.eldalel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.adapters.AddressesAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoopingListAddressesFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.fragment_shooping_list_addresses_recycler)
    RecyclerView fragment_shooping_list_addresses_recycler;

    @BindView(R.id.fragment_shooping_list_addresses_text_add)
    AppCompatTextView fragment_shooping_list_addresses_text_add;

    @BindView(R.id.fragment_shooping_list_addresses_text_next)
    AppCompatTextView fragment_shooping_list_addresses_text_next;

    AddressesAdapter addressesAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shooping_list_addresses, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        fragment_shooping_list_addresses_text_add.setOnClickListener(this);
        fragment_shooping_list_addresses_text_next.setOnClickListener(this);
        initializeRecycler();
    }

    public void initializeRecycler(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        fragment_shooping_list_addresses_recycler.setLayoutManager(layoutManager);
        fragment_shooping_list_addresses_recycler.setItemAnimator(new DefaultItemAnimator());
        fragment_shooping_list_addresses_recycler.addItemDecoration(new DividerItemDecoration(fragment_shooping_list_addresses_recycler.getContext(), DividerItemDecoration.VERTICAL));
        addressesAdapter = new AddressesAdapter(getActivity());
        fragment_shooping_list_addresses_recycler.setAdapter(addressesAdapter);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fragment_shooping_list_addresses_text_add:{
                Fragment fragment = new AddNewAddressFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.animate_slide_up_enter, R.anim.animate_slide_up_exit);
                ft.replace(R.id.payment_constrant, fragment, fragment.getTag());
                ft.addToBackStack(null);
                ft.commit();
                break;
            }
            case R.id.fragment_shooping_list_addresses_text_next:{
                ((PaymentActivity)getActivity()).goToPayidFragment();
                break;
            }
        }
    }
}
