package com.ramotion.garlandview.example.outer;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ramotion.garlandview.InnerDecorator;
import com.ramotion.garlandview.TailItem;
import com.ramotion.garlandview.example.R;
import com.ramotion.garlandview.example.inner.InnerAdapter;

public class OuterItem extends TailItem {

    private final RecyclerView mRecyclerView;

    private boolean mIsScrolling;

    public OuterItem(View itemView) {
        super(itemView);

        mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(new InnerAdapter());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                mIsScrolling = newState != RecyclerView.SCROLL_STATE_IDLE;
            }
        });

        final int offset = itemView.getContext().getResources().getDimensionPixelSize(R.dimen.inner_item_offset);
        mRecyclerView.addItemDecoration(new InnerDecorator(offset));
    }

    @Override
    public boolean isScrolling() {
        return mIsScrolling;
    }

    @Override
    public ViewGroup getViewGroup() {
        return mRecyclerView;
    }

}
