package com.ramotion.garlandview;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;

import com.ramotion.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A {@link androidx.recyclerview.widget.RecyclerView.LayoutManager} implementation.
 */
public class TailLayoutManager extends RecyclerView.LayoutManager
        implements RecyclerView.SmoothScroller.ScrollVectorProvider {

    public interface PageTransformer<T extends TailItem> {
        void transformPage(@NonNull T item, float scrollPosition);
    }

    private static class SavedState implements Parcelable {

        int anchorPos;

        SavedState() {

        }

        SavedState(Parcel in) {
            anchorPos = in.readInt();
        }

        public SavedState(SavedState other) {
            anchorPos = other.anchorPos;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(anchorPos);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

    }

    private int mScrollToPosition = RecyclerView.NO_POSITION;

    private final static int DEFAULT_ITEM_START = 30;
    private final static int DEFAULT_ITEM_GAP = 0;

    private final SparseArray<View> mViewCache = new SparseArray<>();

    private final int mItemStart;
    private final int mItemGap;

    private RecyclerView mRecyclerView;
    private PageTransformer mPageTransformer;

    /**
     * Creates TailLayoutManager with default values
     * @param context Current context, will be used to access resources.
     */
    public TailLayoutManager(@NonNull Context context) {
        this(context, null);
    }

    /**
     * XML constructor
     * See {@link R.styleable#GarlandView_itemStart}
     * See {@link R.styleable#GarlandView_itemGap}
     */
    public TailLayoutManager(@NonNull Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * See {@link TailLayoutManager#TailLayoutManager(Context)}
     */
    public TailLayoutManager(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    /**
     * See {@link TailLayoutManager#TailLayoutManager(Context)}
     */
    public TailLayoutManager(@NonNull Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final float density = context.getResources().getDisplayMetrics().density;
        final int defaultItemStart = (int) (density * DEFAULT_ITEM_START);
        final int defaultItemGap = (int) (density * DEFAULT_ITEM_GAP);

        if (attrs == null) {
            mItemStart = defaultItemStart;
            mItemGap = defaultItemGap;
        } else {
            final TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.GarlandView, 0, 0);
            try {
                mItemStart = a.getDimensionPixelSize(R.styleable.GarlandView_itemStart, defaultItemStart);
                mItemGap = a.getDimensionPixelSize(R.styleable.GarlandView_itemGap, defaultItemGap);
            } finally {
                a.recycle();
            }
        }
    }

    public TailLayoutManager(int itemStart, int itemGap) {
        mItemStart = itemStart;
        mItemGap = itemGap;
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        if (!(view instanceof TailRecyclerView)) {
            throw new IllegalArgumentException("RecyclerView must be instance of TailRecyclerView class");
        }
        super.onAttachedToWindow(view);
        mRecyclerView = view;
    }

    @Override
    public void onDetachedFromWindow(RecyclerView view, RecyclerView.Recycler recycler) {
        super.onDetachedFromWindow(view, recycler);
        mRecyclerView = null;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }

    @Override
    public boolean canScrollHorizontally() {
        return getChildCount() != 0;
    }

    @Override
    public PointF computeScrollVectorForPosition(int targetPosition) {
        if (getChildCount() == 0) {
            return null;
        }
        final int firstChildPos = getPosition(getChildAt(0));
        final int direction = targetPosition < firstChildPos ? -1 : 1;
        return new PointF(direction, 0);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() == 0) {
            removeAndRecycleAllViews(recycler);
            return;
        }

        final int childCount = getChildCount();
        if (childCount == 0 && state.isPreLayout()) {
            return;
        }

        final int anchorPos = getAnchorPosition();

        final int scrollOffset;
        if (childCount == 0) {
            scrollOffset = 0;
        } else {
            final View anchorView = findViewByPosition(anchorPos);
            scrollOffset = mItemStart - getDecoratedLeft(anchorView);
        }

        detachAndScrapAttachedViews(recycler);
        layoutViews(anchorPos, recycler, state);

        if (scrollOffset == 0) {
            applyTransformation();
        } else {
            scrollHorizontallyBy(scrollOffset, recycler, state);
        }
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        mScrollToPosition = RecyclerView.NO_POSITION;

        final int childCount = getChildCount();
        if (childCount == 0) {
            return 0;
        }

        for (int i = 0, cnt = getChildCount(); i < cnt; i++) {
            final TailItem vh = (TailItem) mRecyclerView.getChildViewHolder(getChildAt(i));
            if (vh.isScrolling()) {
                return 0;
            }
        }

        int scrolled;

        if (dx < 0) {
            final View view = getChildAt(0);
            final int viewPos = getPosition(view);
            final int viewLeft = getDecoratedLeft(view);
            final int view0Left = viewLeft - viewPos * getDecoratedMeasuredWidth(view);
            final int view0LeftScrolled = view0Left - dx;

            final int border = mItemStart;
            if (view0LeftScrolled <= border) {
                scrolled = dx;
            } else {
                scrolled = -(border - Math.abs(view0Left));
            }
        } else {
            final View view = getChildAt(childCount - 1);
            final int viewPos = getPosition(view);
            final int viewLeft = getDecoratedLeft(view);
            final int viewNLeft = viewLeft + (getItemCount() - 1 - viewPos) * getDecoratedMeasuredWidth(view);
            final int viewNLeftScrolled = viewNLeft - dx;

            final int border = mItemStart;
            if (viewNLeftScrolled >= border) {
                scrolled = dx;
            } else {
                scrolled = viewNLeft - border;
            }
        }

        offsetChildrenHorizontal(-scrolled);

        layoutViews(getAnchorPosition(), recycler, state);
        applyTransformation();

        return scrolled;
    }

    @Override
    public void scrollToPosition(int position) {
        if (position < 0 || position >= getItemCount()) {
            return;
        }

        mScrollToPosition = position;
        requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        SavedState state = new SavedState();
        state.anchorPos = getAnchorPosition();
        return state;
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            SavedState state = (SavedState) parcelable;
            mScrollToPosition = state.anchorPos;
            requestLayout();
        }
    }

    @Override
    public void onAdapterChanged(RecyclerView.Adapter oldAdapter, RecyclerView.Adapter newAdapter) {
        removeAllViews();
    }

    /**
     * Set page transformer,
     * @param transformer new page transformer or null.
     * @return current instance of TailLayoutManager.
     */
    public TailLayoutManager setPageTransformer(@NonNull PageTransformer transformer) {
        mPageTransformer = transformer;
        return this;
    }

    /**
     * Return current center view or null.
      * @return current center view or null.
     */
    @Nullable
    public View getCenterView() {
        final int center = getWidth() / 2;

        int absClosest = Integer.MAX_VALUE;
        View centerChild = null;

        for (int i = 0, cnt = getChildCount(); i < cnt; i++) {
            final View child = getChildAt(i);
            final int childCenter = getDecoratedLeft(child) + getDecoratedMeasuredWidth(child) / 2;
            int absDistance = Math.abs(childCenter - center);

            if (absDistance < absClosest) {
                absClosest = absDistance;
                centerChild = child;
            }
        }

        return centerChild;
    }

    /**
     * Return item start offset.
     * @return item start offset in pixels.
     */
    public int getItemStart() {
        return mItemStart;
    }

    private int getAnchorPosition() {
        if (mScrollToPosition != RecyclerView.NO_POSITION) {
            return mScrollToPosition;
        }

        if (getChildCount() == 0) {
            return 0;
        } else {
            final View centerView = getCenterView();
            if (centerView == null) {
                return 0;
            }

            return getPosition(centerView);
        }
    }

    private void layoutViews(int anchorPos, RecyclerView.Recycler recycler, RecyclerView.State state) {
        mViewCache.clear();

        for (int i = 0, cnt = getChildCount(); i < cnt; i++) {
            View view = getChildAt(i);
            mViewCache.put(getPosition(view), view);
        }

        for (int i = 0, cnt = mViewCache.size(); i < cnt; i++) {
            detachView(mViewCache.valueAt(i));
        }

        fillLeft(anchorPos, recycler, state);
        fillRight(anchorPos, recycler, state);

        for (int i = 0, cnt = mViewCache.size(); i < cnt; i++) {
            recycler.recycleView(mViewCache.valueAt(i));
        }
    }

    private void fillLeft(int anchorPos, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (anchorPos <= 0) {
            return;
        }

        final int anchorViewLeft;
        final View anchorView = mViewCache.get(anchorPos);
        if (anchorView != null) {
            anchorViewLeft = getDecoratedLeft(anchorView);
        } else {
            anchorViewLeft = mItemStart;
        }

        final int pos = anchorPos - 1;
        final int width = getWidth();
        final int viewWidth = width - mItemStart * 2;

        View view = mViewCache.get(pos);
        if (view != null) {
            attachView(view);
            mViewCache.remove(pos);
        } else {
            view = recycler.getViewForPosition(pos);
            addView(view);
            measureChildWithMargins(view, mItemStart * 2, 0);
            final int viewRight = anchorViewLeft - mItemGap;
            final int viewLeft = viewRight - viewWidth;
            layoutDecorated(view, viewLeft, 0, viewRight, getDecoratedMeasuredHeight(view));
        }
    }

    private void fillRight(int anchorPos, RecyclerView.Recycler recycler, RecyclerView.State state) {
        final int itemCount = getItemCount();
        final int width = getWidth();
        final int viewWidth = width - mItemStart * 2;

        int viewLeft = mItemStart;
        int pos = anchorPos;

        boolean fillRight = true;
        while (fillRight && pos < itemCount) {
            View view = mViewCache.get(pos);
            if (view != null) {
                attachView(view);
                mViewCache.remove(pos);
            } else {
                view = recycler.getViewForPosition(pos);
                addView(view);
                measureChildWithMargins(view, mItemStart * 2, 0);
                layoutDecorated(view, viewLeft, 0, viewLeft + viewWidth, getDecoratedMeasuredHeight(view));
            }

            viewLeft = getDecoratedRight(view) + mItemGap;
            fillRight = viewLeft < width;
            pos++;
        }
    }

    private void applyTransformation() {
        if (mPageTransformer == null) {
            return;
        }

        final int viewWidth = getWidth() - mItemStart * 2;
        for (int i = 0, cnt = getChildCount(); i < cnt; i++) {
            final View view = getChildAt(i);
            final int viewLeft = getDecoratedLeft(view);
            final float position = ((float) (viewLeft - mItemStart)) / viewWidth;

            mPageTransformer.transformPage((TailItem) mRecyclerView.getChildViewHolder(view), position);
        }
    }

}
