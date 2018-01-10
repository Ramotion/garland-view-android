package com.ramotion.garlandview.inner;


import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * A {@link android.support.v7.widget.RecyclerView.LayoutManager} implementation.
 */
public class InnerLayoutManager extends RecyclerView.LayoutManager
        implements RecyclerView.SmoothScroller.ScrollVectorProvider {

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

    private final SparseArray<View> mViewCache = new SparseArray<>();

    private RecyclerView mRecyclerView;

    private int mScrollToPosition = RecyclerView.NO_POSITION;

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        if (!(view instanceof InnerRecyclerView)) {
            throw new IllegalArgumentException("RecyclerView must be instance of InnerRecyclerView class");
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
    public boolean canScrollHorizontally() {
        return false;
    }

    @Override
    public boolean canScrollVertically() {
        return getChildCount() != 0;
    }

    @Override
    public PointF computeScrollVectorForPosition(int targetPosition) {
        if (getChildCount() == 0) {
            return null;
        }
        final int firstChildPos = getPosition(getChildAt(0));
        final int direction = targetPosition < firstChildPos ? -1 : 1;
        return new PointF(0, direction);
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
            scrollOffset = getDecoratedBottom(anchorView) - getDecoratedMeasuredHeight(anchorView);
        }

        detachAndScrapAttachedViews(recycler);
        layoutViews(anchorPos, recycler, state);

        if (scrollOffset != 0) {
            scrollVerticallyBy(-scrollOffset, recycler, state);
        }
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        mScrollToPosition = RecyclerView.NO_POSITION;

        int scrolled;

        if (dy < 0) { // scroll down
            final View view = getChildAt(0);
            final int viewHeight = getDecoratedMeasuredHeight(view);
            final int viewTop = getDecoratedBottom(view) - viewHeight;
            final int view0Top = viewTop - getPosition(view) * viewHeight;
            final int view0TopScrolled = view0Top - dy;

            final int border = 0;
            if (view0TopScrolled <= border) {
                scrolled = dy;
            } else {
                scrolled = border - Math.abs(view0Top);
            }
        } else { // scroll up
            final View view = getChildAt(getChildCount() - 1);
            final int viewBottom = getDecoratedBottom(view);
            final int viewNBottom = viewBottom + (getItemCount() - 1 - getPosition(view)) * getDecoratedMeasuredHeight(view);
            final int viewNBottomScrolled = viewNBottom - dy;

            final int itemCount = getItemCount();
            final int viewPos =  mRecyclerView.getChildAdapterPosition(view);
            final boolean lastItem = itemCount - 1 == viewPos;

            final int border = getHeight();

            if (lastItem && viewBottom < border) {
                scrolled = 0;
            } else if (viewNBottomScrolled >= border) {
                scrolled = dy;
            } else {
                scrolled = viewNBottom - border;
            }
        }

        offsetChildrenVertical(-scrolled);
        layoutViews(getAnchorPosition(), recycler, state);

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
     * @return first visible item position.
     */
    public int findFirstVisibleItemPosition() {
        return getChildCount() != 0 ? getPosition(getChildAt(0)) : RecyclerView.NO_POSITION;
    }

    @Nullable
    private View getTopView() {
        int prevBottom = Integer.MAX_VALUE;
        View topView = null;

        for (int i = 0, cnt = getChildCount(); i < cnt; i++) {
            final View child = getChildAt(i);
            final int bottom = getDecoratedBottom(child);
            if (bottom < 0) {
                continue;
            }

            if (bottom < prevBottom) {
                prevBottom = bottom;
                topView = child;
            }
        }

        return topView;
    }

    private int getAnchorPosition() {
        if (mScrollToPosition != RecyclerView.NO_POSITION) {
            return mScrollToPosition;
        }

        if (getChildCount() == 0) {
            return 0;
        } else {
            final View topView = getTopView();
            if (topView == null) {
                return 0;
            } else {
                return getPosition(topView);
            }
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

        fillTop(anchorPos, recycler, state);
        fillBottom(anchorPos, recycler, state);

        for (int i = 0, cnt = mViewCache.size(); i < cnt; i++) {
            recycler.recycleView(mViewCache.valueAt(i));
        }

        applyTransformation();
    }

    private void fillTop(int anchorPos, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (anchorPos <= 0) {
            return;
        }

        final View anchorView = mViewCache.get(anchorPos);
        if (anchorView == null) {
            return;
        }

        final int viewDecoratedHeight = getDecoratedMeasuredHeight(anchorView);
        final int anchorTop = getDecoratedBottom(anchorView) - viewDecoratedHeight;

        final int pos = anchorPos - 1;
        View view = mViewCache.get(pos);
        if (view != null) {
            attachView(view);
            mViewCache.remove(pos);
        } else {
            view = recycler.getViewForPosition(pos);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            final int viewHeight = pos == 0 ? anchorView.getHeight() : viewDecoratedHeight;
            layoutDecorated(view, 0, anchorTop - viewHeight, getWidth(), anchorTop);
        }
    }

    private void fillBottom(int anchorPos, RecyclerView.Recycler recycler, RecyclerView.State state) {
        final int itemCount = getItemCount();
        final int width = getWidth();
        final int height = getHeight();

        int pos = anchorPos;
        int viewTop = 0;

        boolean fillBottom = true;
        while (fillBottom && pos < itemCount) {
            View view = mViewCache.get(pos);
            if (view != null) {
                attachView(view);
                mViewCache.remove(pos);
            } else {
                view = recycler.getViewForPosition(pos);
                addView(view);
                measureChildWithMargins(view, 0, 0);
                layoutDecorated(view, 0, viewTop, width, viewTop + getDecoratedMeasuredHeight(view));
            }

            viewTop = getDecoratedBottom(view);
            fillBottom = viewTop < height;
            pos++;
        }
    }

    private void applyTransformation() {
        for (int i = 0, cnt = getChildCount(); i < cnt; i++) {
            final View view = getChildAt(i);
            final int viewHeight = view.getMeasuredHeight();

            if (view.getBottom() > 0) {
                final int viewBottom = view.getBottom();
                if (viewBottom - viewHeight < 0) {
                    view.setTop(0);
                } else {
                    view.setTop(viewBottom - viewHeight);
                }

                final int viewNewHeight = viewBottom - view.getTop();
                ((InnerItem) mRecyclerView.getChildViewHolder(view)).onItemViewHeightChanged(viewNewHeight);
            }

            if (view.getTop() > 0) {
                ((InnerItem) mRecyclerView.getChildViewHolder(view)).resetInnerLayoutOffset();
            }
        }
    }

}
