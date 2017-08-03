package com.ramotion.garlandview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class InnerDecorator extends RecyclerView.ItemDecoration {

    private final int mOffset;

    public InnerDecorator(int offset) {
        mOffset = offset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) != 0) {
            outRect.set(0, mOffset, 0, 0);
        }
    }

}
