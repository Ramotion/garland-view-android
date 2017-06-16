package com.ramotion.garlandview.example.outer;

import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.ramotion.garlandview.example.R;

public class OuterLayoutManager extends LinearLayoutManager {

    private Context mContext;

    public OuterLayoutManager(Context context, boolean reverseLayout) {
        super(context, HORIZONTAL, reverseLayout);

        mContext = context;
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        final int scrolled = super.scrollHorizontallyBy(dx, recycler, state);
        updateViews(scrolled);
        return scrolled;
    }

    private void updateViews(int dx) {
        final View view = findCenterView();
        if (view == null) {
            return;
        }

        final LinearLayout ll = (LinearLayout) view.findViewById(R.id.linear_layout);
        if (ll == null) {
            return;
        }

        for (int i = 1, cnt = ll.getChildCount(); i < cnt; i++) {
            final float x = i * dx * 0.3f;
            final View child = ll.getChildAt(i);
            ViewCompat.setX(child, ViewCompat.getX(child) + x);
        }
    }

    @Nullable
    private View findCenterView() {
        final int center = getWidth() / 2;

        int lastDiff = Integer.MAX_VALUE;
        View result = null;

        for (int i = 0, cnt = getChildCount(); i < cnt; i++) {
            final View view = getChildAt(i);
            final int viewCenter = (int)((view.getLeft() + view.getWidth()) / 2);
            final int diff = Math.abs(center - viewCenter);
            if (diff < lastDiff) {
                lastDiff = diff;
                result = view;
            }
        }

        Log.d("D", "lm center = " + center);
        Log.d("D", "result center = " + result.getWidth() / 2);

        return result;
    }

}
