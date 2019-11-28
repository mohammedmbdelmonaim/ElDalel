package com.zeidex.eldalel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zeidex.eldalel.utils.CustomWebView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailDescFragment extends Fragment {
//    @BindView(R.id.detail_description_desc)
//    AppCompatTextView detail_description_desc;

    @BindView(R.id.webview)
    CustomWebView webview;

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
        full_desc.replace("\\" , "");
//        PicassoImageGetter imageGetter = new PicassoImageGetter(detail_description_desc);

        WebSettings webSetting = webview.getSettings();
        webSetting.setBuiltInZoomControls(true);

        if (position == 0) {
            webview.loadData(desc_options, "text/html", "UTF-8");
//            Spannable html;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                html = (Spannable) Html.fromHtml(desc_options, Html.FROM_HTML_MODE_LEGACY, imageGetter, null);
//            } else {
//                html = (Spannable) Html.fromHtml(desc_options, imageGetter, null);
//            }
//            detail_description_desc.setText(html);
        } else {
            webview.loadData(full_desc, "text/html", "UTF-8");
//            Spannable html;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                html = (Spannable) Html.fromHtml(full_desc, Html.FROM_HTML_MODE_LEGACY, imageGetter, null);
//            } else {
//                html = (Spannable) Html.fromHtml(full_desc, imageGetter, null);
//            }
//            detail_description_desc.setText(html);
        }
    }
}
