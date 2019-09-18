package com.zeidex.eldalel.utils;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class NonScrollBar extends RecyclerView {

    public NonScrollBar(Context context) {
        super(context);
    }
    public NonScrollBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public NonScrollBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMeasureSpec_custom = View.MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec_custom);
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = getMeasuredHeight();
    }
}
