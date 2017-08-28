package com.ramotion.garlandview.example.details;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ramotion.garlandview.example.MainActivity;
import com.ramotion.garlandview.example.R;
import com.ramotion.garlandview.example.inner.InnerItem;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class DetailsActivity extends AppCompatActivity {

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

        ((TextView) findViewById(R.id.tv_name)).setText(getIntent().getStringExtra(BUNDLE_NAME));
        ((TextView) findViewById(R.id.tv_info)).setText(getIntent().getStringExtra(BUNDLE_INFO));

        // TODO: set from Faker
//        ((TextView) findViewById(R.id.tv_status)).setText(getIntent().getStringExtra(BUNDLE_TITLE));

        ((RecyclerView)findViewById(R.id.recycler_view)).setAdapter(new DetailsAdapter());

        Glide.with(this)
                .load(getIntent().getStringExtra(BUNDLE_AVATAR_URL))
                .bitmapTransform(new CropCircleTransformation(this))
                .into((ImageView) findViewById(R.id.avatar));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {
                private boolean isClosing = false;

                @Override public void onTransitionPause(Transition transition) {}
                @Override public void onTransitionResume(Transition transition) {}
                @Override public void onTransitionCancel(Transition transition) {}

                @Override public void onTransitionStart(Transition transition) {
                    if (isClosing) {
//                        findViewById(R.id.textView3).animate().alpha(0).start();
//                        findViewById(R.id.card).animate().alpha(0).start();
                    }
                }

                @Override public void onTransitionEnd(Transition transition) {
                    if (!isClosing) {
                        isClosing = true;
                    }
                }
            });
        }
    }

    public void onCardClick(View v) {
        super.onBackPressed();
    }

}
