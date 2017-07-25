package com.ramotion.garlandview.example.old.outer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ramotion.garlandview.example.R;

public class OuterAdapter extends RecyclerView.Adapter<OuterItem> {

    private static final int COUNT = 6;

    private final OuterItem.OnClickListener mItemOnClickListener;

    public OuterAdapter(@NonNull OuterItem.OnClickListener listener) {
        super();
        mItemOnClickListener = listener;
    }

    @Override
    public OuterItem onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.outer_item, parent, false);

        return new OuterItem(view, mItemOnClickListener);
    }

    @Override
    public void onBindViewHolder(OuterItem holder, int position) {
        holder.setContent();
    }

    @Override
    public int getItemCount() {
        return COUNT;
    }

}
