package com.ramotion.garlandview.header;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class HeaderLayoutManager extends LinearLayoutManager {

    public HeaderLayoutManager(Context context) {
        super(context);
    }

    public HeaderLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public HeaderLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public int getOrientation() {
        return VERTICAL;
    }

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        offsetTopChild();
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        final int result = super.scrollVerticallyBy(dy, recycler, state);
        offsetTopChild();
        return result;
    }

    private void offsetTopChild() {
        final int topChildPos = getReverseLayout() ? getChildCount() - 1 : 0;
        final View topChild = getChildAt(topChildPos);
        Log.d("D", String.format("topChild| top: %d, y: %f", topChild.getTop(), topChild.getY()));
    }

}
