package com.ramotion.garlandview.header;


import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class HeaderDecorator extends RecyclerView.ItemDecoration {

    private final int mHeaderHeight;
    private final int mOffset;

    public HeaderDecorator(int headerHeight, int offset) {
        this.mHeaderHeight = headerHeight;
        this.mOffset = offset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        final int topOffset = parent.getChildAdapterPosition(view) == 0 ? mHeaderHeight + mOffset : mOffset;
        outRect.set(0, topOffset, 0, 0);
    }
}
