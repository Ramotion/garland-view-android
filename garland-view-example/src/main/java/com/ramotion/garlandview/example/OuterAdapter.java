package com.ramotion.garlandview.example;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OuterAdapter extends RecyclerView.Adapter<Outerltem> {

    private static final int COUNT = 6;

    @Override
    public Outerltem onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater
                .from(parent.getContext())
                .inflate(viewType, parent, false);

        return new Outerltem(view);
    }

    @Override
    public void onBindViewHolder(Outerltem holder, int position) {

    }

    @Override
    public int getItemCount() {
        return COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return R.layout.tail_item_1;
        } else {
            return R.layout.tail_item_2;
        }
    }
}
