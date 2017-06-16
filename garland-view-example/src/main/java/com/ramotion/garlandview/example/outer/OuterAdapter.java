package com.ramotion.garlandview.example.outer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ramotion.garlandview.example.R;

public class OuterAdapter extends RecyclerView.Adapter<OuterItem> {

    private static final int COUNT = 3;

    @Override
    public OuterItem onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater
                .from(parent.getContext())
                .inflate(viewType, parent, false);

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
        if (position == 1) {
            return R.layout.outter_rv_item_center;
        } else {
            return R.layout.outter_rv_item_side;
        }
    }
}
