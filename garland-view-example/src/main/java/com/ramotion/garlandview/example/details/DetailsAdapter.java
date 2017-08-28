package com.ramotion.garlandview.example.details;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ramotion.garlandview.example.R;
import com.ramotion.garlandview.example.databinding.DetailsItemBinding;


public class DetailsAdapter extends RecyclerView.Adapter<DetailsItem> {

    static final int COUNT = 3;

    @Override
    public DetailsItem onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final DetailsItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.details_item, parent, false);
        return new DetailsItem(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(DetailsItem holder, int position) {
    }

    @Override
    public int getItemCount() {
        return COUNT;
    }

}
