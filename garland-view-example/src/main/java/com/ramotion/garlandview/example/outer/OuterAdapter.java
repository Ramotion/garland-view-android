package com.ramotion.garlandview.example.outer;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ramotion.garlandview.TailAdapter;
import com.ramotion.garlandview.example.R;
import com.ramotion.garlandview.example.inner.InnerData;

import java.util.ArrayList;
import java.util.List;

public class OuterAdapter extends TailAdapter<OuterItem> {

    private final List<List<InnerData>> mData;

    public OuterAdapter(List<List<InnerData>> data) {
        this.mData = data;
    }

    @Override
    public OuterItem onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new OuterItem(view);
    }

    @Override
    public void onBindViewHolder(OuterItem holder, int position) {
        holder.setContent(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.outer_item;
    }

}
