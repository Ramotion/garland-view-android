package com.ramotion.garlandview.example.tail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.ramotion.garlandview.example.R;

public class OuterLayoutManager extends LinearLayoutManager {

    public OuterLayoutManager(Context context, boolean reverseLayout) {
        super(context, HORIZONTAL, reverseLayout);
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        final int scrolled = super.scrollHorizontallyBy(dx, recycler, state);
        updateViews(computeHorizontalScrollOffset(state) % getWidth(), dx);
        return scrolled;
    }

    private void updateViews(int scrolled, int dx) {
        final int childCount = getChildCount();
        if (childCount == 0) {
            return;
        }

        final int width = getWidth();
        final int half = width / 2;
        Log.d("D", "----------------------------------------------");
        Log.d("D", "width = " + width + ", half = " + half + ", scrolled = " + scrolled);

        for (int i = 0; i < childCount; i++) {
            final View view = getChildAt(i);
            final LinearLayout ll = (LinearLayout) view.findViewById(R.id.linear_layout);
            if (ll == null) {
                continue;
            }

            final int offset;
            if (Math.abs(scrolled) < half) {
                offset = scrolled;
            } else {
                offset = half - (Math.abs(scrolled) - half);
            }

            final int viewLeft = view.getLeft();
            Log.d("D", "i = " + i + ", viewLeft = " + viewLeft);

//            getCurrentDirection(ll, offset, dx);
            offsetChildren(ll, offset, dx);
        }
    }

    private void offsetChildren(@NonNull LinearLayout ll, float offset, int dx) {
        final int childCount = ll.getChildCount();
        if (childCount < 2) {
            return;
        }

        final float v0 = ViewCompat.getX(ll.getChildAt(0));
        final float v1 = ViewCompat.getX(ll.getChildAt(1));
        final float offsetV1V2 = v0 - v1;


        final float sign;
        if (Math.abs(offsetV1V2) <= 2) {
            sign = -Math.signum(dx);
        } else {
            sign = Math.signum(offsetV1V2);
        }

        for (int i = 1; i < childCount; i++) {
            final View child = ll.getChildAt(i);
            if (child.getTop() > ll.getBottom()) {
                break;
            }

            final float x = v0 - sign * i * offset / 3;
            ViewCompat.setX(child, x);
        }
    }

    private int getCurrentDirection(@NonNull LinearLayout ll, float offset, int dx) {
        return 0;
    }

}
