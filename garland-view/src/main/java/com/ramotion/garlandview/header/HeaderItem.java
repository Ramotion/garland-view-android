package com.ramotion.garlandview.header;

import android.view.View;

import com.ramotion.garlandview.TailItem;
import com.ramotion.garlandview.inner.InnerRecyclerView;

/**
 * Implementation of {@link TailItem}
 */
public abstract class HeaderItem extends TailItem<InnerRecyclerView> {

    public HeaderItem(View itemView) {
        super(itemView);
    }

    /**
     * @return  Must return header main layout
     */
    abstract public View getHeader();

    /**
     * @return  Must return header alpha-layout.
     * Alpha-layout is the layout which will hide header's views,
     * when item will be scrolled to left or right.
     */
    abstract public View getHeaderAlphaView();

}
