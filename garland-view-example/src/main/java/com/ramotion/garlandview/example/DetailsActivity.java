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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Pair<View, String> p1 = Pair.create(item.itemView, "card");
            final Pair<View, String> p2 = Pair.create((View)item.mAvatar, "avatar");

            final ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(activity, p1, p2);

            activity.startActivity(starter, options.toBundle());
        } else {
            activity.startActivity(starter);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final String avatarUrl = getIntent().getStringExtra(BUNDLE_AVATAR_URL);
        final ImageView avatar = (ImageView) findViewById(R.id.avatar);

        Glide.with(this)
                .load(avatarUrl)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(avatar);
    }

}
