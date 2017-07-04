package com.ramotion.garlandview.example.tail;

import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

public class TailSnapHelper extends SnapHelper {

    private static final int MAX_SCROLL_ON_FLING_DURATION = 100; // ms
    private static final float MILLISECONDS_PER_INCH = 100f;

    private RecyclerView mRecyclerView;

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        super.attachToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @Nullable
    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager lm, @NonNull View targetView) {
        final int[] out = new int[] {0 ,0};

        if (lm.canScrollHorizontally()) {
            // TODO: user with lm without sideOffset
            /*
            final int targetCenter = lm.getDecoratedLeft(targetView) + lm.getDecoratedMeasuredWidth(targetView) / 2;
            final int center = lm.getWidth() / 2;
            out[0] = targetCenter - center;
            */
            final int targetStart = lm.getDecoratedLeft(targetView);
            out[0] = targetStart - ((TailLayoutManager)lm).getSideOffset();
        }

        return out;
    }

    @Nullable
    @Override
    public View findSnapView(RecyclerView.LayoutManager lm) {
        final int childCount = lm.getChildCount();
        if (childCount == 0) {
            return null;
        }

        final int center = lm.getWidth() / 2;

        int absClosest = Integer.MAX_VALUE;
        View closestChild = null;

        for (int i = 0; i < childCount; i++) {
            final View child = lm.getChildAt(i);
            final int childCenter = lm.getDecoratedLeft(child) + lm.getDecoratedMeasuredWidth(child) / 2;
            int absDistance = Math.abs(childCenter - center);

            if (absDistance < absClosest) {
                absClosest = absDistance;
                closestChild = child;
            }
        }

        return closestChild;
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager lm, int velocityX, int velocityY) {
        final int itemCount = lm.getItemCount();
        if (itemCount == 0) {
            return RecyclerView.NO_POSITION;
        }

        final int childCount = lm.getChildCount();
        if (childCount == 0) {
            return RecyclerView.NO_POSITION;
        }

        final int start = ((TailLayoutManager)lm).getSideOffset();

        int absClosest = Integer.MAX_VALUE;
        View closestChild = null;
        for (int i = 0; i < childCount; i++) {
            final View child = lm.getChildAt(i);
            final int childStart = lm.getDecoratedLeft(child);
            int absDistance = Math.abs(childStart - start);

            if (absDistance < absClosest) {
                absClosest = absDistance;
                closestChild = child;
            }
        }

        if (closestChild == null) {
            return RecyclerView.NO_POSITION;
        }

        final int currentPosition = lm.getPosition(closestChild);
        if (currentPosition == RecyclerView.NO_POSITION) {
            return RecyclerView.NO_POSITION;
        }

        final boolean leftDirection = velocityX > 0;

        if (leftDirection) {
            return Math.min(itemCount - 1, currentPosition + 1);
        } else {
            return Math.max(0, currentPosition - 1);
        }
    }

    @Nullable
    @Override
    protected LinearSmoothScroller createSnapScroller(RecyclerView.LayoutManager layoutManager) {
        return new LinearSmoothScroller(mRecyclerView.getContext()) {
            @Override
            protected void onTargetFound(View targetView, RecyclerView.State state, Action action) {
                int[] snapDistances = calculateDistanceToFinalSnap(mRecyclerView.getLayoutManager(),
                        targetView);
                final int dx = snapDistances[0];
                final int dy = snapDistances[1];
                final int time = calculateTimeForDeceleration(Math.max(Math.abs(dx), Math.abs(dy)));
                if (time > 0) {
                    action.update(dx, dy, time, mDecelerateInterpolator);
                }
            }

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
            }

            @Override
            protected int calculateTimeForScrolling(int dx) {
                return Math.min(MAX_SCROLL_ON_FLING_DURATION, super.calculateTimeForScrolling(dx));
            }
        };
    }
}
