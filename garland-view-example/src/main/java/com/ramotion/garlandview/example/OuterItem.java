package com.ramotion.garlandview.example;

import android.view.View;
import android.view.ViewGroup;

import com.ramotion.garlandview.header.HeaderItem;

/**
 * Created by user on 24.07.17.
 */

public class OuterItem extends HeaderItem {

    public OuterItem(View itemView) {
        super(itemView);
    }

    @Override
    public View getHeader() {
        return null;
    }

    @Override
    public View getHeaderAlphaView() {
        return null;
    }

    @Override
    public boolean isScrolling() {
        return false;
    }

    @Override
    public ViewGroup getViewGroup() {
        return null;
    }

}
