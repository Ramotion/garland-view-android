package com.ramotion.garlandview.example;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.ramotion.garlandview.example.outer.OuterAdapter;
import com.ramotion.garlandview.example.outer.OuterItem;
import com.ramotion.garlandview.example.tail.TailLayoutManager;
import com.ramotion.garlandview.example.tail.TailPageTransformer;
import com.ramotion.garlandview.example.tail.TailSnapHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
    }

    private void initRecyclerView() {
        final RecyclerView rv = (RecyclerView) findViewById(R.id.recycler_view);

        final TailLayoutManager lm = new TailLayoutManager(this);
        lm.setPageTransformer(new TailPageTransformer());

        rv.setLayoutManager(lm);
        rv.setAdapter(new OuterAdapter(new OuterItem.OnClickListener() {
            @Override
            public void onClick(@NonNull View itemView, int innerPosition, int outerPosition) {
                Log.d("D", String.format("onClick| inner: %d, outer: %d", innerPosition, outerPosition));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    final Intent intent = new Intent(MainActivity.this, DetailsActivity.class);

                    final Pair<View, String> p1 = Pair.create(itemView, "card");
                    final Pair<View, String> p2 = Pair.create(itemView.findViewById(R.id.avatar), "avatar");

                    final ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(MainActivity.this, p1, p2);

                    startActivity(intent, options.toBundle());
                }
            }
        }));

        new TailSnapHelper().attachToRecyclerView(rv);
    }

}
