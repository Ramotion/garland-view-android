package com.ramotion.garlandview.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.ramotion.garlandview.example.tail.AlphaScalePageTransformer;
import com.ramotion.garlandview.example.tail.TailAdapter;
import com.ramotion.garlandview.example.tail.TailLayoutManager;

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
        lm.setPageTransformer(new AlphaScalePageTransformer());

        rv.setLayoutManager(lm);
        rv.setAdapter(new TailAdapter());
//        new PagerSnapHelper().attachToRecyclerView(rv); // TODO: implement similar helper for TailLayoutManager
    }

}
