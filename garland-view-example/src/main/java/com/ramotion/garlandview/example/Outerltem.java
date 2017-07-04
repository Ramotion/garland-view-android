package com.ramotion.garlandview.example;


import android.support.v7.widget.RecyclerView;
import android.view.View;

public class Outerltem extends RecyclerView.ViewHolder {

    public Outerltem(View itemView) {
        super(itemView);

        /*
        final LinearLayout layout = (LinearLayout) itemView.findViewById(R.id.linear_layout);
        for (int i = 0, cnt = layout.getChildCount(); i < cnt; i++) {
            final View view =layout.getChildAt(i);
            ViewCompat.setTranslationX(view, i * 50);
        }
        */
    }

}
