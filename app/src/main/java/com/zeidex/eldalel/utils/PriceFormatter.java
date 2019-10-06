package com.zeidex.eldalel.utils;

import android.content.Context;

import com.zeidex.eldalel.R;

import java.text.DecimalFormat;
import java.util.Locale;

public class PriceFormatter {

    public static String toDecimalRsString(double price, Context context){
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String priceString = df.getNumberInstance(Locale.US).format(price);
        return priceString + " " + context.getResources().getString(R.string.currancy_label);
    }

    public static String toDecimalString(double price, Context context){
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String priceString = df.getNumberInstance(Locale.US).format(price);
        return priceString;
    }

    public static String toRightNumber(double discount, Context context){
        DecimalFormat dc = new DecimalFormat("#");
        return context.getResources().getString(R.string.discount_label) + " " + dc.format(discount) + " %";
    }
}
