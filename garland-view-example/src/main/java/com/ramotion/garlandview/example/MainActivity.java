package com.ramotion.garlandview.example;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.ramotion.garlandview.header.HeaderTransformer;
import com.ramotion.garlandview.TailLayoutManager;
import com.ramotion.garlandview.TailRecyclerView;
import com.ramotion.garlandview.TailSnapHelper;
import com.ramotion.garlandview.example.old.outer.OuterAdapter;
import com.ramotion.garlandview.example.old.outer.OuterItem;
import com.ramotion.garlandview.example.old.tail.TailLayoutManagerOld;
import com.ramotion.garlandview.example.old.tail.TailPageTransformerOld;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        initRecyclerViewOld();
        initRecyclerView();
    }

    private void initRecyclerViewOld() {
        final RecyclerView rv = (RecyclerView) findViewById(R.id.recycler_view);

        final TailLayoutManagerOld lm = new TailLayoutManagerOld(this);
        lm.setPageTransformer(new TailPageTransformerOld());

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

    private void initRecyclerView() {
        final TailRecyclerView rv = (TailRecyclerView) findViewById(R.id.recycler_view);

        final TailLayoutManager lm = new TailLayoutManager(this);
        lm.setPageTransformer(new HeaderTransformer());

        new TailSnapHelper().attachToRecyclerView(rv);
    }

}
