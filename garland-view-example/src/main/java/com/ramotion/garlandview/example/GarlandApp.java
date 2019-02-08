package com.ramotion.garlandview.example;

import android.app.Application;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import io.bloco.faker.Faker;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class GarlandApp extends Application {

    public interface FakerReadyListener {
        void onFakerReady(Faker faker);
    }

    private Faker mFaker;

    private final List<WeakReference<FakerReadyListener>> mListeners = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        initFaker();
    }

    public void addListener(@NonNull FakerReadyListener listener) {
        mListeners.add(new WeakReference<>(listener));
        if (mFaker != null) {
            listener.onFakerReady(mFaker);
        }
    }

    public void removeListener(FakerReadyListener listener) {
        for (WeakReference<FakerReadyListener> wRef: mListeners) {
            if (wRef.get() == listener) {
                mListeners.remove(wRef);
                break;
            }
        }
    }

    private void initFaker() {
        Single.create(new SingleOnSubscribe<Faker>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<Faker> e) throws Exception {
                final Faker faker = new Faker();

                if (!e.isDisposed()) {
                    e.onSuccess(faker);
                }
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Faker>() {
            @Override
            public void accept(Faker faker) throws Exception {
                mFaker = faker;
                
                for (WeakReference<FakerReadyListener> wRef: mListeners) {
                    final FakerReadyListener listener = wRef.get();
                    if (listener != null) {
                        listener.onFakerReady(mFaker);
                    }
                }
            }
        });
    }

}
