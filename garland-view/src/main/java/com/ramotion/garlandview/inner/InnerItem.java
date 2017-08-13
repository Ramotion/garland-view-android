package com.ramotion.garlandview.inner;


import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class InnerItem extends RecyclerView.ViewHolder {

    private int prevHeight;

    public InnerItem(View itemView) {
        super(itemView);
    }

    void onItemViewHeightChanged(int newHeight) {
        if ((prevHeight != 0) && (newHeight != prevHeight)) {
            shiftUpInnerLayout(prevHeight - newHeight);
        }
        prevHeight = newHeight;
    }

    protected void shiftUpInnerLayout(int offset) {
        final View view = getInnerLayout();
        ViewCompat.setY(view, ViewCompat.getY(view) - offset);
    }

    /**
     * @return  Must return main item layout
     */
    protected abstract View getInnerLayout();

}
