package com.ramotion.garlandview.inner;


import androidx.recyclerview.widget.RecyclerView;

/**
 * Adapter class for {@link InnerRecyclerView}.
 * @param <T> inner item class.
 */
public abstract class InnerAdapter<T extends InnerItem> extends RecyclerView.Adapter<T> {}