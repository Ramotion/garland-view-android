package com.ramotion.garlandview.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ramotion.garlandview.TailLayoutManager;
import com.ramotion.garlandview.TailRecyclerView;
import com.ramotion.garlandview.TailSnapHelper;
import com.ramotion.garlandview.example.inner.InnerData;
import com.ramotion.garlandview.example.outer.OuterAdapter;
import com.ramotion.garlandview.header.HeaderTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.bloco.faker.Faker;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    private final static int COUNT = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFaker();
    }

    private void initFaker() {
        Single.create(new SingleOnSubscribe<List<List<InnerData>>>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<List<List<InnerData>>> e) throws Exception {
                final Random random = new Random();
                final Faker faker = new Faker();
                final List<List<InnerData>> data = new ArrayList<>();

                for (int i = 0; i < COUNT && !e.isDisposed(); i++) {
//                    final int innerCount = faker.number.between(3, 10);
                    final int innerCount = 20;
                    final List<InnerData> innDataList = new ArrayList<InnerData>();
                    for (int j = 0; j < innerCount && !e.isDisposed(); j++) {
                        innDataList.add(createInnerData(faker));
                    }
                    data.add(innDataList);
                }

                if (!e.isDisposed()) {
                    e.onSuccess(data);
                }
            }

        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<List<InnerData>>>() {
            @Override
            public void accept(List<List<InnerData>> data) throws Exception {
                initRecyclerView(data);
            }
        });
    }

    private void initRecyclerView(List<List<InnerData>> data) {
        // TODO: remove progressbar here
        final TailRecyclerView rv = (TailRecyclerView) findViewById(R.id.recycler_view);
        rv.setLayoutManager(new TailLayoutManager(this).setPageTransformer(new HeaderTransformer()));
        rv.setAdapter(new OuterAdapter(data));

        new TailSnapHelper().attachToRecyclerView(rv);
    }

    private InnerData createInnerData(Faker faker) {
        return new InnerData(
                faker.book.title(),
                faker.name.name(),
                faker.address.city() + ", " + faker.address.stateAbbr(),
                faker.avatar.image(faker.internet.email()),
                faker.number.between(20, 50)
        );
    }

}
