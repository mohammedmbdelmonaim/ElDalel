package com.zeidex.eldalel.utils;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.zeidex.eldalel.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailDescFragment extends Fragment {
    @BindView(R.id.detail_description_desc)
    AppCompatTextView detail_description_desc;
    String desc_options, full_desc;
    int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_description, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        position = Integer.parseInt(getArguments().getString("pos"));
        desc_options = getArguments().getString("desc_options");
        full_desc = getArguments().getString("full_desc");
        if (position == 0) {
            detail_description_desc.setText(desc_options);
        } else {
            detail_description_desc.setText(Html.fromHtml(full_desc));
        }
    }
}
