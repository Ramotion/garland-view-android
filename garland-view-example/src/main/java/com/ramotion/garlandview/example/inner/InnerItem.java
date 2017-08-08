package com.ramotion.garlandview.example.inner;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ramotion.garlandview.example.R;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class InnerItem  extends com.ramotion.garlandview.inner.InnerItem {

    private final View mInnerLayout;

    private final TextView mHeader;
    private final TextView mName;
    private final TextView mAddress;
    private final ImageView mAvatar;

    public InnerItem(View itemView) {
        super(itemView);
        mInnerLayout = ((ViewGroup)itemView).getChildAt(0);

        mHeader = (TextView) itemView.findViewById(R.id.tv_header);
        mName = (TextView) itemView.findViewById(R.id.tv_name);
        mAddress = (TextView) itemView.findViewById(R.id.tv_address);
        mAvatar = (ImageView) itemView.findViewById(R.id.avatar);
    }

    @Override
    protected View getInnerLayout() {
        return mInnerLayout;
    }

    public void clearContent() {
        Glide.clear(mAvatar);
    }

    void setContent(InnerData data) {
        mHeader.setText(data.title);
        mName.setText(String.format("%s %s", data.name, itemView.getContext().getString(R.string.answer_low)));
        mAddress.setText(data.address);

        Glide.with(itemView.getContext())
                .load(data.avatarUrl)
                .bitmapTransform(new CropCircleTransformation(itemView.getContext()))
                .into(mAvatar);
    }

}
