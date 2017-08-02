package com.ramotion.garlandview.example.outer;


import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ramotion.garlandview.TailAdapter;
import com.ramotion.garlandview.example.R;
import com.ramotion.garlandview.example.outer.OuterItem;
import com.ramotion.garlandview.header.HeaderAdapter;

public class OuterAdapter extends TailAdapter<OuterItem> {

    private static int COUNT = 10;

    @Override
    public OuterItem onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new OuterItem(view);
    }

    @Override
    public void onBindViewHolder(OuterItem holder, int position) {

    }

    @Override
    public int getItemCount() {
        return COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.outer_item;
    }
}
