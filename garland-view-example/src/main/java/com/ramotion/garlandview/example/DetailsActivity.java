package com.ramotion.garlandview.example;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ramotion.garlandview.example.inner.InnerItem;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class DetailsActivity extends AppCompatActivity {

    private static final String BUNDLE_NAME = "BUNDLE_NAME";
    private static final String BUNDLE_INFO = "BUNDLE_INFO";
    private static final String BUNDLE_TITLE = "BUNDLE_TITLE";
    private static final String BUNDLE_AVATAR_URL = "BUNDLE_AVATAR_URL";

    public static void start(Activity activity, InnerItem item) {
        Intent starter = new Intent(activity, DetailsActivity.class);

        starter.putExtra(BUNDLE_NAME, item.getItemData().name);
        starter.putExtra(BUNDLE_INFO, item.mAddress.getText().toString());
        starter.putExtra(BUNDLE_TITLE, item.getItemData().title);
        starter.putExtra(BUNDLE_AVATAR_URL, item.getItemData().avatarUrl);

        activity.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ((TextView) findViewById(R.id.tv_name)).setText(getIntent().getStringExtra(BUNDLE_NAME));
        ((TextView) findViewById(R.id.tv_info)).setText(getIntent().getStringExtra(BUNDLE_INFO));
        ((TextView) findViewById(R.id.tv_title)).setText(getIntent().getStringExtra(BUNDLE_TITLE));

        Glide.with(this)
                .load(getIntent().getStringExtra(BUNDLE_AVATAR_URL))
                .bitmapTransform(new CropCircleTransformation(this))
                .into((ImageView) findViewById(R.id.avatar));
    }

    public void onCardClick(View v) {
        super.onBackPressed();
    }

}
