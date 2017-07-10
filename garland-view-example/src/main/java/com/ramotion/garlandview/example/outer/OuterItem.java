package com.ramotion.garlandview.example.outer;


import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.ramotion.garlandview.example.inner.InnerAdapter;
import com.ramotion.garlandview.example.R;
import com.ramotion.garlandview.example.inner.InnerDecorator;

import java.util.LinkedList;
import java.util.List;

public class OuterItem extends RecyclerView.ViewHolder {

    private final List<View> mCollapsingHeaders = new LinkedList<>();
    private final RecyclerView mRecyclerView;

    private final int mHeaderHeight;
    private final int mHeaderMinHeight;

    public OuterItem(View itemView) {
        super(itemView);

        mHeaderHeight = itemView.getContext().getResources().getDimensionPixelSize(R.dimen.header_top_height);
        mHeaderMinHeight = itemView.getContext().getResources().getDimensionPixelSize(R.dimen.header_top_min_height);

        mCollapsingHeaders.add(((LinearLayout) itemView.findViewById(R.id.header_linear_layout)).getChildAt(0));
        mCollapsingHeaders.add(itemView.findViewById(R.id.header_background));

        final LinearLayoutManager lm = new LinearLayoutManager(itemView.getContext());

        mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setAdapter(new InnerAdapter());

        final int topOffset = itemView.getContext().getResources().getDimensionPixelSize(R.dimen.inner_item_offset);
        mRecyclerView.addItemDecoration(new InnerDecorator(topOffset));

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (lm.findFirstVisibleItemPosition() == 0) {
                    final View view = lm.findViewByPosition(0);

                    final  int height = view.getHeight();
                    final float newHeight = height + view.getY();
                    final float ratio = Math.min(1, newHeight / (height - mHeaderMinHeight / 2));

                    for (View h: mCollapsingHeaders) {
                        h.setBottom(Math.max(mHeaderMinHeight, (int)Math.ceil(mHeaderHeight * ratio)));
                    }
                }
            }
        });
    }

    public void setContent() {
    }

}
