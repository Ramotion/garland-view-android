package com.ramotion.garlandview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class InnerDecorator extends RecyclerView.ItemDecoration {

    private final int mTopOffset;

    public InnerDecorator(int offset) {
        mTopOffset = offset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) != 0) {
            outRect.set(0, mTopOffset, 0, 0);
        }
    }

}
