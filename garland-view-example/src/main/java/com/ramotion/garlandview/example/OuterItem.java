package com.ramotion.garlandview.example;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class OuterItem extends RecyclerView.ViewHolder {

    private final RecyclerView mRecyclerView;

    public OuterItem(View itemView) {
        super(itemView);

        mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(new InnerAdapter());
    }

    public void setContent() {
    }

}
