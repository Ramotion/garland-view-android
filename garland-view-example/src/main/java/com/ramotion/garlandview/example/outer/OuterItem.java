package com.ramotion.garlandview.example.outer;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.ramotion.garlandview.example.R;

public class OuterItem extends RecyclerView.ViewHolder {

    public OuterItem(View itemView) {
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
