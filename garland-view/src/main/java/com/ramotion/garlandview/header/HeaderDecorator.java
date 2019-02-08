package com.ramotion.garlandview.header;


import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class HeaderDecorator extends RecyclerView.ItemDecoration {

    private final int mHeaderHeight;
    private final int mOffset;

    public HeaderDecorator(int headerHeight, int offset) {
        this.mHeaderHeight = headerHeight;
        this.mOffset = offset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        final int position = parent.getChildAdapterPosition(view);
        final boolean isFirst = position == 0;
        final boolean isLast = position == parent.getAdapter().getItemCount() - 1;
        final int topOffset = isFirst ? mHeaderHeight + mOffset : mOffset;
        final int bottomOffset = isLast ? mOffset : 0;
        outRect.set(0, topOffset, 0, bottomOffset);
    }
}
