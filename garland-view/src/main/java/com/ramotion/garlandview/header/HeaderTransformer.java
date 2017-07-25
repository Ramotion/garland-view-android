package com.ramotion.garlandview.header;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.ramotion.garlandview.TailItemTransformer;
import com.ramotion.garlandview.TailLayoutManager;

public class HeaderTransformer implements TailLayoutManager.PageTransformer<HeaderItem> {

    private final TailItemTransformer mTransformer = new TailItemTransformer();

    @Override
    public void transformPage(@NonNull HeaderItem item, float scrollPosition) {
        mTransformer.transformPage(item, scrollPosition);

        final TailItemTransformer.TransformParams params =
                mTransformer.getParamsForPosition(item.itemView, scrollPosition);

        final View header = item.getHeader();
        ViewCompat.setPivotX(header, params.pivotX);
        ViewCompat.setScaleX(header, params.scale);
        ViewCompat.setScaleY(header, params.scale);
        ViewCompat.setAlpha(header, params.alpha);
        ViewCompat.setTranslationY(header, params.offsetY);

        ViewCompat.setAlpha(item.getHeaderAlphaView(), 1 - params.alphaChild);
    }

}
