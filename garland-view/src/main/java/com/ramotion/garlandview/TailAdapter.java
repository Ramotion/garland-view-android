package com.ramotion.garlandview;

import android.support.v7.widget.RecyclerView;

/**
 * Adapter class for {@link TailRecyclerView}.
 * @param <T> outer item class.
 */
public abstract class TailAdapter<T extends TailItem> extends RecyclerView.Adapter<T> {}
