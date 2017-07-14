package com.ramotion.garlandview.example.inner;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ramotion.garlandview.example.R;

public class InnerAdapter extends RecyclerView.Adapter<InnerItem> {

    private static final int COUNT = 10;

    @Override
    public InnerItem onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater
                .from(parent.getContext())
                .inflate(viewType, parent, false);

        return new InnerItem(view);
    }

    @Override
    public void onBindViewHolder(InnerItem holder, int position) {

    }

    @Override
    public int getItemCount() {
        return COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return R.layout.inner_card_empty;
        } else {
            return R.layout.inner_card;
        }
    }
}
