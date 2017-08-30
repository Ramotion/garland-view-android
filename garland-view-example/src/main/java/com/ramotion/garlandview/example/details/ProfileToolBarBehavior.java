package com.ramotion.garlandview.example.details;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.ramotion.garlandview.example.R;

// TODO: remove
public class ProfileToolBarBehavior extends CoordinatorLayout.Behavior<FrameLayout> {

    private View mToolBar;
    private View mCollapsingToolbarLayout;

    public ProfileToolBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FrameLayout child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FrameLayout child, View dependency) {
        final AppBarLayout appBar = (AppBarLayout) dependency;
        final int scroll = appBar.getMeasuredHeight() - appBar.getBottom();
        Log.d("D", String.format("b: %d, s: %d, z: %f, ts: %d",
                appBar.getBottom(), scroll, ViewCompat.getZ(appBar), appBar.getTotalScrollRange()));

        child.setTranslationY(-scroll);
        if (scroll == appBar.getTotalScrollRange()) {
//            ViewCompat.setZ(child, 20);
        } else {
//            ViewCompat.setZ(child, ViewCompat.getZ(appBar));
        }

        return true;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, FrameLayout child, int layoutDirection) {
        Log.d("D", "onLayoutChild");

        mToolBar = parent.findViewById(R.id.toolbar);
        mCollapsingToolbarLayout = parent.findViewById(R.id.toolbar_layout);

        final View appBar = parent.findViewById(R.id.app_bar);
        final int appBarBottom = appBar.getBottom();
        final int childWidth = child.getMeasuredWidth();
        final int childHeight = child.getMeasuredHeight();
        child.layout(0, appBarBottom - childHeight, childWidth, appBarBottom);

        return true;
    }
}
