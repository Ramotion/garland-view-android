package com.ramotion.garlandview.example.outer;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ramotion.garlandview.inner.InnerLayoutManager;
import com.ramotion.garlandview.example.R;
import com.ramotion.garlandview.example.inner.InnerAdapter;
import com.ramotion.garlandview.header.HeaderDecorator;
import com.ramotion.garlandview.header.HeaderItem;

public class OuterItem extends HeaderItem {

    private final static float AVATAR_RATIO_START = 1f;
    private final static float AVATAR_RATIO_MAX = 0.25f;
    private final static float AVATAR_RATIO_DIFF = AVATAR_RATIO_START - AVATAR_RATIO_MAX;

    private final static float ANSWER_RATIO_START = 0.75f;
    private final static float ANSWER_RATIO_MAX = 0.35f;
    private final static float ANSWER_RATIO_DIFF = ANSWER_RATIO_START - ANSWER_RATIO_MAX;

    private final static float MIDDLE_RATIO_START = 0.7f;
    private final static float MIDDLE_RATIO_MAX = 0.1f;
    private final static float MIDDLE_RATIO_DIFF = MIDDLE_RATIO_START- MIDDLE_RATIO_MAX;

    private final static float FOOTER_RATIO_START = 1.1f;
    private final static float FOOTER_RATIO_MAX = 0.35f;
    private final static float FOOTER_RATIO_DIFF = FOOTER_RATIO_START - FOOTER_RATIO_MAX;

    private final View mHeader;
    private final View mHeaderAlpha;

    private final RecyclerView mRecyclerView;

    private final View mHeaderMiddle;
    private final View mHeaderMiddleCollapsible;
    private final View mHeaderMiddleAnswer;
    private final View mHeaderFooterCollapsible;
    private final View mHeaderCaption1;
    private final View mHeaderCaption2;

    private final int mHeaderMiddleHeight;
    private final int m10dp;

    private boolean mIsScrolling;

    public OuterItem(View itemView) {
        super(itemView);

        mHeaderMiddleHeight = itemView.getContext().getResources().getDimensionPixelSize(R.dimen.item_middle_height);
        m10dp = itemView.getContext().getResources().getDimensionPixelSize(R.dimen.dp10);

        mHeader = itemView.findViewById(R.id.header);
        mHeaderAlpha = itemView.findViewById(R.id.header_alpha);

        mHeaderMiddle = itemView.findViewById(R.id.header_middle);
        mHeaderMiddleCollapsible = mHeaderMiddle.findViewById(R.id.header_middle_collapsible);
        mHeaderMiddleAnswer= mHeaderMiddle.findViewById(R.id.header_middle_answer);
        mHeaderFooterCollapsible = itemView.findViewById(R.id.header_footer);
        mHeaderCaption1 = itemView.findViewById(R.id.header_text_1);
        mHeaderCaption2 = itemView.findViewById(R.id.header_text_2);

        // Init RecyclerView
        mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(new InnerAdapter());
        mRecyclerView.setLayoutManager(new InnerLayoutManager());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                mIsScrolling = newState != RecyclerView.SCROLL_STATE_IDLE;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                onItemScrolled(recyclerView, dx, dy);
            }
        });

        final int offset = itemView.getContext().getResources().getDimensionPixelSize(R.dimen.inner_item_offset);
        final int headerHeight = itemView.getContext().getResources().getDimensionPixelSize(R.dimen.inner_item_height);
        mRecyclerView.addItemDecoration(new HeaderDecorator(headerHeight, offset));
    }

    @Override
    public boolean isScrolling() {
        return mIsScrolling;
    }

    @Override
    public ViewGroup getViewGroup() {
        return mRecyclerView;
    }


    @Override
    public View getHeader() {
        return mHeader;
    }

    @Override
    public View getHeaderAlphaView() {
        return mHeaderAlpha;
    }

    private float computeRatio(RecyclerView recyclerView) {
        final View child0 = recyclerView.getChildAt(0);
        final int pos = recyclerView.getChildAdapterPosition(child0);
        if (pos != 0) {
            return 0;
        }

        final int height = child0.getHeight();
        final float y = Math.max(0, child0.getY());
        return y / height;
    }

    private void onItemScrolled(RecyclerView recyclerView, int dx, int dy) {
        final float ratio = computeRatio(recyclerView);

        final float footerRatio = Math.max(0, Math.min(FOOTER_RATIO_START, ratio) - FOOTER_RATIO_DIFF) / FOOTER_RATIO_MAX;
        final float avatarRatio = Math.max(0, Math.min(AVATAR_RATIO_START, ratio) - AVATAR_RATIO_DIFF) / AVATAR_RATIO_MAX;
        final float answerRatio = Math.max(0, Math.min(ANSWER_RATIO_START, ratio) - ANSWER_RATIO_DIFF) / ANSWER_RATIO_MAX;
        final float middleRatio = Math.max(0, Math.min(MIDDLE_RATIO_START, ratio) - MIDDLE_RATIO_DIFF) / MIDDLE_RATIO_MAX;

        ViewCompat.setPivotY(mHeaderFooterCollapsible, 0);
        ViewCompat.setScaleY(mHeaderFooterCollapsible, footerRatio);
        ViewCompat.setAlpha(mHeaderFooterCollapsible, footerRatio);

        ViewCompat.setPivotX(mHeaderMiddleCollapsible, mHeaderMiddleCollapsible.getWidth() / 7);
        ViewCompat.setPivotY(mHeaderMiddleCollapsible, mHeaderMiddleCollapsible.getHeight() / 2);
        ViewCompat.setScaleX(mHeaderMiddleCollapsible, avatarRatio);
        ViewCompat.setScaleY(mHeaderMiddleCollapsible, avatarRatio);
        ViewCompat.setAlpha(mHeaderMiddleCollapsible, avatarRatio);

        ViewCompat.setPivotY(mHeaderMiddleAnswer, mHeaderMiddleAnswer.getHeight());
        ViewCompat.setScaleY(mHeaderMiddleAnswer, 1f - answerRatio);
        ViewCompat.setAlpha(mHeaderMiddleAnswer, 1f - answerRatio);

        ViewCompat.setAlpha(mHeaderCaption1, answerRatio);
        ViewCompat.setAlpha(mHeaderCaption2, 1f - answerRatio);

        final ViewGroup.LayoutParams lp = mHeaderMiddle.getLayoutParams();
        lp.height = mHeaderMiddleHeight - (int)(m10dp * (1f - middleRatio));
        mHeaderMiddle.setLayoutParams(lp);
    }

}
