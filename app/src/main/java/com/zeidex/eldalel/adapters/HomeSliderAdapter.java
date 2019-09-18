package com.zeidex.eldalel.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.zeidex.eldalel.R;
import com.zeidex.eldalel.response.GetOffers;
import com.zeidex.eldalel.utils.ChangeLang;
import com.zeidex.eldalel.utils.PriceFormatter;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeSliderAdapter extends SliderViewAdapter<HomeSliderAdapter.SliderAdapterVH> {

    private Context context;
    List<GetOffers.Offer> offers;

    public HomeSliderAdapter(Context context) {
        this.context = context;
    }

    public HomeSliderAdapter(Context context, List<GetOffers.Offer> offers) {
        this.context = context;
        this.offers = offers;
    }

    @Override
    public HomeSliderAdapter.SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_slider_layout_item, null);
        return new HomeSliderAdapter.SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(HomeSliderAdapter.SliderAdapterVH viewHolder, int position) {
        GetOffers.Offer currentOffer = offers.get(position);

        if (currentOffer.getPhotos().size() > 0) {
            Glide.with(context)
                    .load("https://www.dleel-sh.com/homepages/get/" + currentOffer.getPhotos().get(0).getFilename())
                    .into(viewHolder.sliderProductImageView);
        }

        double priceDouble = Double.parseDouble(currentOffer.getPrice());
        viewHolder.sliderProductOfferTextView.setText(PriceFormatter.toDecimalRsString(priceDouble, context.getApplicationContext()));


        String oldPrice = currentOffer.getOld_price();
        if (oldPrice != null) {
            double oldPriceDouble = Double.parseDouble(currentOffer.getOld_price());
            viewHolder.sliderProductPriceTextView.setText(PriceFormatter.toDecimalRsString(oldPriceDouble, context.getApplicationContext()));
            viewHolder.sliderProductPriceTextView.setPaintFlags(viewHolder.sliderProductPriceTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG );
        }

        Locale locale = ChangeLang.getLocale(context.getResources());
        String loo = locale.getLanguage();
        if (loo.equalsIgnoreCase("ar")) {
            viewHolder.sliderProductNameTextView.setText(currentOffer.getName_ar());
        } else {
            viewHolder.sliderProductNameTextView.setText(currentOffer.getName());
        }
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return offers.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        @BindView(R.id.slider_product_image_view)
        ImageView sliderProductImageView;
        @BindView(R.id.slider_product_name_text_view)
        TextView sliderProductNameTextView;
        @BindView(R.id.slider_product_offer_text_view)
        TextView sliderProductOfferTextView;
        @BindView(R.id.slider_old_price_text_view)
        TextView sliderProductPriceTextView;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
