package com.ramotion.garlandview.example;


import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.ramotion.garlandview.header.HeaderAdapter;

public class OuterAdapter extends HeaderAdapter<OuterItem> {

    private static int COUNT = 10;

    @Override
    public void onBindViewHolder(OuterItem holder, int position) {

    }

    @Override
    public int getItemCount() {
        return COUNT;
    }

    @NonNull
    @Override
    protected View inflateTailItemHeaderLayout() {
        return null;
    }

    @Override
    protected OuterItem createTailItemViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

}
