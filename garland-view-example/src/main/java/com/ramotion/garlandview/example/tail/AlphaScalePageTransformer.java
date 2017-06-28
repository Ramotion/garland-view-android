package com.ramotion.garlandview.example.tail;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.ramotion.garlandview.example.R;

public class AlphaScalePageTransformer implements TailLayoutManager.PageTransformer{

    private static final float INACTIVE_SCALE = 0.7f;
    private static final float INACTIVE_ALPHA = 0.5f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        Log.d("D", "position = " + position);

        final LinearLayout ll = (LinearLayout) page.findViewById(R.id.linear_layout);

        final int childCount = ll.getChildCount();
        if (childCount == 0) {
            return;
        }

        final float alpha;
        final float scale;
        if (position < -1) {
            alpha = INACTIVE_ALPHA;
            scale = INACTIVE_SCALE;
        } else if (position <= 1) {
            alpha = INACTIVE_ALPHA + (1 - INACTIVE_ALPHA) * (1 - Math.abs(position));
            scale = INACTIVE_SCALE + (1 - INACTIVE_SCALE) * (1 - Math.abs(position));
        } else {
            alpha = INACTIVE_ALPHA;
            scale = INACTIVE_SCALE;
        }

        final int width = page.getWidth();

        final float viewOffset = width * Math.abs(position);
        final float step = viewOffset / 4;

        final int childHeight = ll.getChildAt(0).getHeight();
        final float yOffset = (childHeight - childHeight * scale) / 2;

        for (int i = 0, cnt = ll.getChildCount(); i < cnt; i++) {
            final View child = ll.getChildAt(i);
            ViewCompat.setAlpha(child, alpha);
            ViewCompat.setScaleX(child, scale);
            ViewCompat.setScaleY(child, scale);

            final float j = Math.max(-2, 1 - i);
            ViewCompat.setTranslationY(child, j * yOffset);


        }
    }

}
