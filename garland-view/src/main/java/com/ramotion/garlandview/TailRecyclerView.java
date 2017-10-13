package com.ramotion.garlandview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;


public class TailRecyclerView extends RecyclerView {

    public TailRecyclerView(Context context) {
        this(context, null);
    }

    public TailRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TailRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutManager(new TailLayoutManager(context, attrs, defStyle));
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (!(adapter instanceof TailAdapter)) {
            throw new IllegalArgumentException("Adapter must be instance of TailAdapter class");
        }
        super.setAdapter(adapter);
    }

    @Override
    public void setLayoutManager(LayoutManager lm) {
        if (!(lm instanceof TailLayoutManager)) {
            throw new IllegalArgumentException("LayoutManager must be instance of TailLayoutManager class");
        }
        super.setLayoutManager(lm);
    }

}
