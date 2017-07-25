package com.ramotion.garlandview;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public abstract class TailItem extends RecyclerView.ViewHolder {

    public TailItem(View itemView) {
        super(itemView);
    }

    abstract public boolean isScrolling();

    abstract public ViewGroup getViewGroup();

}
