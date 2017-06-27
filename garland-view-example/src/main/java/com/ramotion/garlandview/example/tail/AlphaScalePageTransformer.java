package com.ramotion.garlandview.example.tail;

import android.support.annotation.NonNull;
import android.view.View;

public class AlphaScalePageTransformer implements TailLayoutManager.PageTransformer{

    private static final float INACTIVE_SCALE = 0.8f;
    private static final float INACTIVE_ALPHA = 0.5f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        if (position < -1) {
            page.setAlpha(INACTIVE_ALPHA);
            page.setScaleX(INACTIVE_SCALE);
            page.setScaleY(INACTIVE_SCALE);
        } else if (position <= 1) {
            float scale = INACTIVE_SCALE + (1 - INACTIVE_SCALE) * (1 - Math.abs(position));
            float alpha = INACTIVE_ALPHA + (1 - INACTIVE_ALPHA) * (1 - Math.abs(position));
            page.setScaleX(scale);
            page.setScaleY(scale);
            page.setAlpha(alpha);
        } else {
            page.setAlpha(INACTIVE_ALPHA);
            page.setScaleX(INACTIVE_SCALE);
            page.setScaleY(INACTIVE_SCALE);
        }
    }

}
