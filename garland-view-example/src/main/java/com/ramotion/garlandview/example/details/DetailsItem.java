package com.ramotion.garlandview.example.details;


import android.view.View;
import android.widget.TextView;

import com.ramotion.garlandview.example.R;

import androidx.recyclerview.widget.RecyclerView;

class DetailsItem extends RecyclerView.ViewHolder {

    DetailsItem(View itemView) {
        super(itemView);
    }

    void setContent(DetailsData data) {
        ((TextView)itemView.findViewById(R.id.tv_title)).setText(data.title);
        ((TextView)itemView.findViewById(R.id.tv_text)).setText(data.text);
    }

}
