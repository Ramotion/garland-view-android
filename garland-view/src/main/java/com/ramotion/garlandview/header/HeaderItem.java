package com.ramotion.garlandview.header;

import android.view.View;

import com.ramotion.garlandview.TailItem;

public abstract class HeaderItem extends TailItem {

    public HeaderItem(View itemView) {
        super(itemView);
    }

    abstract public View getHeader();

    abstract public View getHeaderAlphaView();

}
