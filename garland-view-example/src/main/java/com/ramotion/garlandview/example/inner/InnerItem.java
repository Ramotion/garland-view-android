package com.ramotion.garlandview.example.inner;


import android.view.View;
import android.view.ViewGroup;

public class InnerItem  extends com.ramotion.garlandview.inner.InnerItem {

    private final View mInnerLayout;

    public InnerItem(View itemView) {
        super(itemView);
        mInnerLayout = ((ViewGroup)itemView).getChildAt(0);
    }

    @Override
    protected View getInnerLayout() {
        return mInnerLayout;
    }

}
