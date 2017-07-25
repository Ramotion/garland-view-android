package com.ramotion.garlandview.header;


import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ramotion.R;
import com.ramotion.garlandview.TailAdapter;

public abstract class HeaderAdapter<T extends HeaderItem> extends TailAdapter<T> {

    private final int mHeaderHeight;

    /**
     * @param headerHeight - header height in pixels.
     */
    public HeaderAdapter(int headerHeight) {
        mHeaderHeight = headerHeight;
    }

    @Override
    final public T onCreateViewHolder(ViewGroup parent, int viewType) {
        addHeaderToItem(inflateTailItemHeaderLayout());
        return createTailItemViewHolder(parent, viewType);
    }

    @NonNull
    abstract protected View inflateTailItemHeaderLayout();

    abstract protected T createTailItemViewHolder(ViewGroup parent, int viewType);

    private void addHeaderToItem(@NonNull View header) {
        final View headerLayout = LayoutInflater.from(header.getContext()).inflate(R.layout.header_item, null, false);

        final View headerParent = headerLayout.findViewById(R.id.header);
        final ViewGroup.LayoutParams lp = header.getLayoutParams();
        lp.height = mHeaderHeight;
        headerParent.setLayoutParams(lp);

        final View headerAlpha = headerLayout.findViewById(R.id.header_alpha);
        final ViewGroup.LayoutParams lpAlpha = headerAlpha.getLayoutParams();
        lpAlpha.height = mHeaderHeight;
        headerAlpha.setLayoutParams(lp);

        ((FrameLayout)headerParent).addView(header, 0, lpAlpha);
    }

}
