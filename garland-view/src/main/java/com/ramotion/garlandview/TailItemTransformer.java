package com.ramotion.garlandview;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;

import com.ramotion.R;

public class TailItemTransformer implements TailLayoutManager.PageTransformer {

    private static final float INACTIVE_SCALE = 0.7f;
    private static final float INACTIVE_SCALE_CHILD = 0.3f;
    private static final float INACTIVE_ALPHA = 0.5f;
    private static final float INACTIVE_ALPHA_CHILD = 0.0f;
    private static final float PIVOT_X_SCALE = 0.8f;
    private static final int OFFSET_MAX = 150;

    public static class TransformParams {
        public final float position;
        public final float floorDiff;
        public final float scale;
        public final float alpha;
        public final float alphaChild;
        public final float pivotX;
        public final float offsetY;

        TransformParams(float position, float floorDiff, float scale, float alpha,
                        float alphaChild, float pivotX, float offsetY)
        {
            this.position = position;
            this.floorDiff = floorDiff;
            this.scale = scale;
            this.alpha = alpha;
            this.alphaChild = alphaChild;
            this.pivotX = pivotX;
            this.offsetY = offsetY;
        }
    }

    private float mParamsPosition;
    private TransformParams mParams;

    @Override
    public void transformPage(@NonNull TailItem item, float scrollPosition) {
        if (item.getViewGroup().getChildCount() == 0) {
            return;
        }

        final View child = item.getViewGroup().getChildAt(0);
        final int viewHeight = child.getMeasuredHeight();
        final TransformParams params = getParamsForPosition(scrollPosition, child.getWidth(), viewHeight);
        applyAlphaScaleEffect(item.getViewGroup(), params, viewHeight);
        applyTailEffect(item.getViewGroup(), params);
    }

    public TransformParams getParamsForPosition(float position, int childWidth, int childHeight) {
        if (position == mParamsPosition && mParams != null) {
            return mParams;
        }

        float floorDiff = position - (float) Math.floor(position);
        if (floorDiff == 0f) {
            floorDiff = 1f;
        }

        final float scale = computeRatio(INACTIVE_SCALE, 1, position);
        final float alpha = computeRatio(INACTIVE_ALPHA, 1, position);
        final float alphaChild = computeRatio(INACTIVE_ALPHA_CHILD, 1, position);
        final float pivotRatio = Math.max(-1, Math.min(1, position));
        final float half = childWidth / 2;
        final float pivotX = half - pivotRatio * half * PIVOT_X_SCALE;
        final float offsetY = (childHeight - childHeight * scale) / 2;

        mParams = new TransformParams(position, floorDiff, scale, alpha, alphaChild, pivotX, offsetY);
        mParamsPosition = position;

        return mParams;
    }

    private float computeRatio(float minValue, float maxValue, float position) {
        if (position < -1 || position > 1) {
            return minValue;
        }

        return minValue + (maxValue - minValue) * (1 - Math.abs(position));
    }

    private void applyAlphaScaleEffect(@NonNull ViewGroup vg, @NonNull TransformParams params, int viewHeight) {
        final float scaleStep = (INACTIVE_SCALE - INACTIVE_SCALE_CHILD) / 3; // Divide on visible children count
        float scaleMin = INACTIVE_SCALE_CHILD;

        int j = 0;
        for (int i = 0, cnt = vg.getChildCount(); i < cnt; i++) {
            final View view = vg.getChildAt(i);

            ViewCompat.setPivotX(view, params.pivotX);
            ViewCompat.setAlpha(view, params.alphaChild);

            boolean isHeaderView;
            if (params.floorDiff == 1f || view.getTag(R.id.tail_header_tag) == null) {
                isHeaderView = view.getY() < viewHeight;
                view.setTag(R.id.tail_header_tag, isHeaderView);
            } else {
                isHeaderView = (Boolean) view.getTag(R.id.tail_header_tag);
            }

            final float scale;
            if (isHeaderView) {
                scale = params.scale;
            } else {
                scale = computeRatio(scaleMin, 1, params.position);
                scaleMin += scaleStep;
            }

            ViewCompat.setScaleX(view, scale);
            ViewCompat.setScaleY(view, scale);

            if (isHeaderView) {
                ViewCompat.setTranslationY(view, params.offsetY);
            } else {
                ViewCompat.setTranslationY(view, j * params.offsetY);
                j--;
            }
        }
    }

    private void applyTailEffect(@NonNull ViewGroup vg, @NonNull TransformParams params) {
        final float position = params.position;
        final float floorDiff = params.floorDiff;

        if (position < -1 || position > 1) {
            for (int i = 0, cnt = vg.getChildCount(); i < cnt; i++) {
                ViewCompat.setX(vg.getChildAt(i), 0);
            }
            return;
        }

        final View firstOffsetChild = vg.getChildAt(vg.getChildCount() - 1);

        float sign;
        final float childX = ViewCompat.getX(firstOffsetChild);
        if (floorDiff > 0.5f) {
            if (childX < -10){
                sign = -1;
            } else {
                sign = 1;
            }
        } else {
            if (childX > 10) {
                sign = 1;
            } else {
                sign = -1;
            }
        }

        for (int i = 0, j = 1, cnt = vg.getChildCount(); i < cnt; i++) {
            final View child = vg.getChildAt(i);

            final boolean isHeaderView = (Boolean) child.getTag(R.id.tail_header_tag);
            if (isHeaderView) {
                continue;
            }

            final float childOffset;
            if (floorDiff > 0.5f) {
                childOffset = (1f - floorDiff) * OFFSET_MAX * j;
            } else {
                childOffset = floorDiff * OFFSET_MAX * j;
            }

            ViewCompat.setX(child, sign * childOffset);

            j++;
        }
    }

}
