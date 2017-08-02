package com.ramotion.garlandview.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ramotion.garlandview.TailItemTransformer;
import com.ramotion.garlandview.TailLayoutManager;
import com.ramotion.garlandview.TailRecyclerView;
import com.ramotion.garlandview.TailSnapHelper;
import com.ramotion.garlandview.example.outer.OuterAdapter;
import com.ramotion.garlandview.header.HeaderTransformer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
    }

    private void initRecyclerView() {
        final TailLayoutManager lm = new TailLayoutManager(this);
        lm.setPageTransformer(new TailItemTransformer());

        final TailRecyclerView rv = (TailRecyclerView) findViewById(R.id.recycler_view);
        rv.setLayoutManager(lm);
        rv.setAdapter(new OuterAdapter());

        new TailSnapHelper().attachToRecyclerView(rv);
    }

}
