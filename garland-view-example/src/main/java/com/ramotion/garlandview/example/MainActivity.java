package com.ramotion.garlandview.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.ramotion.garlandview.example.outer.OuterAdapter;
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
        rv.setAdapter(new OuterAdapter());

        new TailSnapHelper().attachToRecyclerView(rv);
    }

}
