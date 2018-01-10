package com.ramotion.garlandview.inner;


import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * ViewHolder class for {@link InnerRecyclerView}.
 */
public abstract class InnerItem extends RecyclerView.ViewHolder {

    private int prevHeight;

    public InnerItem(View itemView) {
        super(itemView);
    }

    void onItemViewHeightChanged(int newHeight) {
        if ((prevHeight != 0) && (newHeight != prevHeight)) {
            final View view = getInnerLayout();
            ViewCompat.setY(view, ViewCompat.getY(view) - (prevHeight - newHeight));
        }
        prevHeight = newHeight;
    }

    void resetInnerLayoutOffset() {
        ViewCompat.setY(getInnerLayout(), 0);
    }

    /**
     * @return  Must return main item layout
     */
    protected abstract View getInnerLayout();

}
