package com.ramotion.garlandview.example.old.inner;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class InnerItem extends RecyclerView.ViewHolder {

    public interface OnClickListener {
        void onClick(@NonNull View itemView, int position);
    }

    public InnerItem(@NonNull View itemView, @NonNull final OnClickListener listener) {
        super(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view, getAdapterPosition());
            }
        });
    }

}
