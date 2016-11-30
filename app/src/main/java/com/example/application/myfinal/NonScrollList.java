package com.example.application.myfinal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class NonScrollList extends ListView {
    public NonScrollList(Context context) {
        super(context);
    }
    public NonScrollList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public NonScrollList(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public void onMeasure(int widthMeasure, int heightMeasure) {
        int heightMeasureSpec_custom = View.MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasure, heightMeasureSpec_custom);
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = getMeasuredHeight();
    }
}
