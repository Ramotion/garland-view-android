package com.ramotion.garlandview.example.outer;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ramotion.garlandview.InnerLayoutManager;
import com.ramotion.garlandview.example.R;
import com.ramotion.garlandview.example.inner.InnerAdapter;
import com.ramotion.garlandview.header.HeaderDecorator;
import com.ramotion.garlandview.header.HeaderItem;

public class OuterItem extends HeaderItem {

    private final View mHeader;
    private final View mHeaderAlpha;
    private final RecyclerView mRecyclerView;

    private boolean mIsScrolling;

    public OuterItem(View itemView) {
        super(itemView);

        mHeader = itemView.findViewById(R.id.header);
        mHeaderAlpha = itemView.findViewById(R.id.header_alpha);

        mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(new InnerAdapter());
        mRecyclerView.setLayoutManager(new InnerLayoutManager());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                mIsScrolling = newState != RecyclerView.SCROLL_STATE_IDLE;
            }
        });

        final int offset = itemView.getContext().getResources().getDimensionPixelSize(R.dimen.inner_item_offset);
        final int headerHieght = itemView.getContext().getResources().getDimensionPixelSize(R.dimen.inner_item_height);
        mRecyclerView.addItemDecoration(new HeaderDecorator(headerHieght, offset));
    }

    @Override
    public boolean isScrolling() {
        return mIsScrolling;
    }

    @Override
    public ViewGroup getViewGroup() {
        return mRecyclerView;
    }


    @Override
    public View getHeader() {
        return mHeader;
    }

    @Override
    public View getHeaderAlphaView() {
        return mHeaderAlpha;
    }

}
