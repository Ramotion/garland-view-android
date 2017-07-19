package com.ramotion.garlandview.example.tail;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ramotion.garlandview.example.R;

// TODO: use viewHolders
public class TailPageTransformer implements TailLayoutManager.PageTransformer{

    private static final float INACTIVE_SCALE = 0.7f;
    private static final float INACTIVE_SCALE_CHILD = 0.3f;
    private static final float INACTIVE_ALPHA = 0.5f;
    private static final float INACTIVE_ALPHA_CHILD = 0.0f;
    private static final float PIVOT_X_SCALE = 0.8f;
    private static final int OFFSET_MAX = 150;

    private static class AlphaScaleParams {
        final float position;
        final float scale;
        final float alpha;
        final float alphaChild;
        final float pivotX;
        final float offsetY;

        AlphaScaleParams(float position, float scale, float alpha, float alphaChild, float pivotX, float offsetY) {
            this.position = position;
            this.scale = scale;
            this.alpha = alpha;
            this.alphaChild = alphaChild;
            this.pivotX = pivotX;
            this.offsetY = offsetY;
        }
    }

    @Override
    public void transformPage(@NonNull View page, float position) {
        final View header = page.findViewById(R.id.header);
        final AlphaScaleParams params = getParamsForPosition(header, position);
        applyAlphaScaleEffectOnHeader(header, params);

        final RecyclerView rv = (RecyclerView) page.findViewById(R.id.recycler_view);
        applyAlphaScaleEffectOnRecyclerView(rv, params);
        applyTailEffect(rv, position);

        final View cutter = page.findViewById(R.id.cutter);
        cutter.setBottom((int)Math.ceil(params.offsetY * 2));
    }

    private float computeRatio(float minValue, float maxValue, float position) {
        if (position < -1 || position > 1) {
            return minValue;
        }

        return minValue + (maxValue - minValue) * (1 - Math.abs(position));
    }

    private AlphaScaleParams getParamsForPosition(@NonNull View view, float position) {
        final float scale = computeRatio(INACTIVE_SCALE, 1, position);
        final float alpha = computeRatio(INACTIVE_ALPHA, 1, position);
        final float alphaChild = computeRatio(INACTIVE_ALPHA_CHILD, 1, position);
        final float pivotRatio = Math.max(-1, Math.min(1, position));
        final float half = view.getWidth() / 2;
        final float pivotX = half - pivotRatio * half * PIVOT_X_SCALE;
        final int childHeight = view.getHeight();
        final float offsetY = (childHeight - childHeight * scale) / 2;

        return new AlphaScaleParams(position, scale, alpha, alphaChild, pivotX, offsetY);
    }

    private void applyAlphaScaleEffectOnHeader(@NonNull View view, @NonNull AlphaScaleParams params) {
        ViewCompat.setPivotX(view, params.pivotX);
        ViewCompat.setScaleX(view, params.scale);
        ViewCompat.setScaleY(view, params.scale);
        ViewCompat.setAlpha(view, params.alpha);
        ViewCompat.setTranslationY(view, params.offsetY);

        view.findViewById(R.id.header_alpha).setAlpha(1 - params.alphaChild);
    }

    private void applyAlphaScaleEffectOnRecyclerView(@NonNull RecyclerView rv, @NonNull AlphaScaleParams params) {
        final int childCount = rv.getChildCount();
        if (childCount == 0) {
            return;
        }

        final float scaleStep = (INACTIVE_SCALE - INACTIVE_SCALE_CHILD) / 3; // Divide on visible children count
        float scaleMin = INACTIVE_SCALE_CHILD;

        for (int i = 0, cnt = rv.getChildCount(); i < cnt; i++) {
            final View view = rv.getChildAt(i);

            ViewCompat.setPivotX(view, params.pivotX);
            ViewCompat.setAlpha(view, params.alphaChild);

            final float scale;
            if (i == 0) {
                scale = params.scale;
            } else {
                scale = computeRatio(scaleMin, 1, params.position);
                scaleMin += scaleStep;
            }

            ViewCompat.setScaleX(view, scale);
            ViewCompat.setScaleY(view, scale);

            final float j = Math.max(-2, 1 - i);
            ViewCompat.setTranslationY(view, j * params.offsetY);
        }
    }

    private void applyTailEffect(@NonNull ViewGroup ll, float position) {
        if (ll.getChildCount() < 2) {
            return;
        }

        if (position < -1 || position > 1) {
            for (int i = 1, cnt = ll.getChildCount(); i < cnt; i++) {
                ViewCompat.setX(ll.getChildAt(i), 0);
            }
            return;
        }

        float floorDiff = position - (float) Math.floor(position);
        if (floorDiff == 0f) {
            floorDiff = 1f;
        }

        View fistOffsetChild = ll.getChildAt(1);
        for (int i = 1, cnt = ll.getChildCount(); i < cnt; i++) {
            final View child = ll.getChildAt(i);
            if (child.getY() > child.getHeight()) {
                fistOffsetChild = child;
                break;
            }
        }

        float sign;
        final float childX = ViewCompat.getX(fistOffsetChild);
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

        int j = 0;
        for (int i = 1, cnt = ll.getChildCount(); i < cnt; i++) {
            final View child = ll.getChildAt(i);

            if (child.getY() <= child.getHeight()) {
                continue;
            } else {
                j++;
            }

            final float childOffset;
            if (floorDiff > 0.5f) {
                childOffset = (1f - floorDiff) * OFFSET_MAX * j;
            } else {
                childOffset = floorDiff * OFFSET_MAX * j;
            }

            ViewCompat.setX(child, sign * childOffset);
        }
    }

}
