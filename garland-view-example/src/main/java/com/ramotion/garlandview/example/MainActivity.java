package com.ramotion.garlandview.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;

import com.ramotion.garlandview.example.outer.OuterAdapter;
import com.ramotion.garlandview.example.outer.OuterLayoutManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
    }

    private void initRecyclerView() {
        final RecyclerView rv = (RecyclerView) findViewById(R.id.recycler_view);
        final OuterLayoutManager lm = new OuterLayoutManager(this, false);
        rv.setLayoutManager(lm);
        rv.setAdapter(new OuterAdapter());
        new LinearSnapHelper().attachToRecyclerView(rv);

        lm.scrollToPosition(1);
    }

}
