package com.ramotion.garlandview.inner;


import android.support.v7.widget.RecyclerView;

/**
 * Adapter class for {@link InnerRecyclerView}.
 * @param <T> inner item class.
 */
public abstract class InnerAdapter<T extends InnerItem> extends RecyclerView.Adapter<T> {}