package com.ramotion.garlandview.example.details;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ramotion.garlandview.example.GarlandApp;
import com.ramotion.garlandview.example.main.MainActivity;
import com.ramotion.garlandview.example.R;
import com.ramotion.garlandview.example.main.inner.InnerItem;
import com.ramotion.garlandview.example.profile.ProfileActivity;

import java.util.ArrayList;
import java.util.List;

import io.bloco.faker.Faker;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class DetailsActivity extends AppCompatActivity implements GarlandApp.FakerReadyListener {

    private static final int ITEM_COUNT = 2;

    private static final String BUNDLE_NAME = "BUNDLE_NAME";
    private static final String BUNDLE_INFO = "BUNDLE_INFO";
    private static final String BUNDLE_AVATAR_URL = "BUNDLE_AVATAR_URL";

    public static void start(final MainActivity activity, final InnerItem item) {
        Intent starter = new Intent(activity, DetailsActivity.class);

        starter.putExtra(BUNDLE_NAME, item.getItemData().name);
        starter.putExtra(BUNDLE_INFO, item.mAddress.getText().toString());
        starter.putExtra(BUNDLE_AVATAR_URL, item.getItemData().avatarUrl);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Pair<View, String> p1 = Pair.create(item.itemView, activity.getString(R.string.transition_card));
            final Pair<View, String> p2 = Pair.create(item.mAvatarBorder, activity.getString(R.string.transition_avatar_border));
//            final Pair<View, String> p3 = Pair.create((View)item.mName, activity.getString(R.string.transition_name));
//            final Pair<View, String> p4 = Pair.create((View)item.mAddress, activity.getString(R.string.transition_info));

            final ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(activity, p1, p2);
//                    .makeSceneTransitionAnimation(activity, p1, p2, p3, p4);

            activity.startActivity(starter, options.toBundle());
        } else {
            activity.startActivity(starter);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ((GarlandApp)getApplication()).addListener(this);

        ((TextView) findViewById(R.id.tv_name)).setText(getIntent().getStringExtra(BUNDLE_NAME));
        ((TextView) findViewById(R.id.tv_info)).setText(getIntent().getStringExtra(BUNDLE_INFO));

        Glide.with(this)
                .load(getIntent().getStringExtra(BUNDLE_AVATAR_URL))
                .bitmapTransform(new CropCircleTransformation(this))
                .into((ImageView) findViewById(R.id.avatar));
    }

    @Override
    public void onFakerReady(Faker faker) {
        ((TextView) findViewById(R.id.tv_status)).setText(faker.book.title());

        // TODO: make static package visible and shared between this and profile activity
        final List<DetailsData> data = new ArrayList<>();
        for (int i = 0; i < ITEM_COUNT; i++) {
            data.add(new DetailsData(faker.book.title(), faker.name.name()));
        }

        ((RecyclerView)findViewById(R.id.recycler_view)).setAdapter(new DetailsAdapter(data));
    }

    public void onCardClick(View v) {
//        super.onBackPressed();

        ProfileActivity.start(this,
                getIntent().getStringExtra(BUNDLE_AVATAR_URL),
                getIntent().getStringExtra(BUNDLE_NAME),
                getIntent().getStringExtra(BUNDLE_INFO),
                ((TextView) findViewById(R.id.tv_status)).getText().toString(),
                findViewById(R.id.avatar),
                findViewById(R.id.card),
                findViewById(R.id.iv_background),
                findViewById(R.id.recycler_view));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }

}
