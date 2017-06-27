package com.ramotion.garlandview.example.tail;

import android.support.annotation.NonNull;
import android.view.View;

public class AlphaScalePageTransformer implements TailLayoutManager.PageTransformer{

    private static final float INACTIVE_SCALE = 0.8f;
    private static final float INACTIVE_ALPHA = 0.5f;

    @Override
    public void transformPage(@NonNull View page, float position) {
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

        page.setAlpha(alpha);
        page.setScaleX(scale);
        page.setScaleY(scale);
    }

}
