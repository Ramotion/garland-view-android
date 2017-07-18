package com.ramotion.garlandview.example.outer;


import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.ramotion.garlandview.example.R;
import com.ramotion.garlandview.example.inner.InnerAdapter;
import com.ramotion.garlandview.example.inner.InnerDecorator;
import com.ramotion.garlandview.example.inner.InnerItem;
import com.ramotion.garlandview.example.tail.TailItem;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class OuterItem extends RecyclerView.ViewHolder implements TailItem {

    public interface OnClickListener {
        void onClick(@NonNull View itemView, int innerPosition, int outerPosition);
    }

    private final RecyclerView mRecyclerView;

    private final View mHeaderMiddle;
    private final View mHeaderMiddleCollapsible;
    private final View mHeaderMiddleAnswer;
    private final View mHeaderFooter;
    private final View mHeaderFooterCollapsible;
    private final View mHeaderCaption1;
    private final View mHeaderCaption2;

    private final int m10dp;
    private final int m60dp;

    private boolean mIsScrolling = false;

    public OuterItem(@NonNull View itemView, @NonNull final OnClickListener listener) {
        super(itemView);

        m10dp = itemView.getContext().getResources().getDimensionPixelSize(R.dimen.dp10);
        m60dp = itemView.getContext().getResources().getDimensionPixelSize(R.dimen.dp60);

        mHeaderMiddle = itemView.findViewById(R.id.header_middle);
        mHeaderMiddleCollapsible = mHeaderMiddle.findViewById(R.id.header_middle_collapsible);
        mHeaderMiddleAnswer= mHeaderMiddle.findViewById(R.id.header_middle_answer);
        mHeaderFooter = itemView.findViewById(R.id.header_footer);
        mHeaderFooterCollapsible = mHeaderFooter.findViewById(R.id.header_footer_collapsible);

        mHeaderCaption1 = itemView.findViewById(R.id.header_caption_1);
        mHeaderCaption2 = itemView.findViewById(R.id.header_caption_2);

        final LinearLayoutManager lm = new LinearLayoutManager(itemView.getContext());

        mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setAdapter(new InnerAdapter(new InnerItem.OnClickListener() {
            @Override
            public void onClick(@NonNull View itemView, int position) {
                listener.onClick(itemView, position, getAdapterPosition());
            }
        }));

        final int topOffset = itemView.getContext().getResources().getDimensionPixelSize(R.dimen.inner_item_offset);
        mRecyclerView.addItemDecoration(new InnerDecorator(topOffset));

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                mIsScrolling = newState != RecyclerView.SCROLL_STATE_IDLE;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                final float scrollRatio;
                if (lm.findFirstVisibleItemPosition() == 0) {
                    final View view = lm.findViewByPosition(0);
                    final  int height = view.getHeight();
                    final float newHeight = height + view.getY();
                    scrollRatio = Math.min(1, newHeight / height);
                } else {
                    scrollRatio = 0;
                }
                onHeaderScrolled(scrollRatio);
            }
        });
    }

    public void setContent() {
//        ((TextView)itemView.findViewById(R.id.header_caption_1)).setText(String.valueOf(getAdapterPosition()));
    }

    protected void onHeaderScrolled(float scrollRatio) {
        final float footerRatioStart = 1f;
        final float footerRatioMax = 0.35f;
        final float footerRatioDiff = footerRatioStart - footerRatioMax;

        final float avatarRatioStart = 1f;
        final float avatarRatioMax = 0.25f;
        final float avatarRatioDiff = avatarRatioStart - avatarRatioMax;

        final float middleAnswerRatioStart = 0.75f;
        final float middleAnswerRatioMax = 0.35f;
        final float middleAnswerRatioDiff = middleAnswerRatioStart - middleAnswerRatioMax;

        final float middleRatioStart = 0.7f;
        final float middleRatioMax = 0.1f;
        final float middleRatioDiff = middleRatioStart - middleRatioMax;

        final float footerRatio = max(0, scrollRatio - footerRatioDiff) / footerRatioMax;
        final float avatarRatio = max(0, scrollRatio - avatarRatioDiff) / avatarRatioMax;
        final float middleAnswerRatio = max(0, min(middleAnswerRatioStart, scrollRatio) - middleAnswerRatioDiff) / middleAnswerRatioMax;
        final float middleRatio = max(0, min(middleRatioStart, scrollRatio) - middleRatioDiff) / middleRatioMax;
        Log.d("D", String.format("onHeaderScrolled| scroll: %f", scrollRatio));

        ViewCompat.setAlpha(mHeaderFooterCollapsible, footerRatio);
        ViewCompat.setPivotY(mHeaderFooterCollapsible, 0);
        ViewCompat.setScaleY(mHeaderFooterCollapsible, footerRatio);

        ViewCompat.setPivotX(mHeaderMiddleCollapsible, mHeaderMiddleCollapsible.getWidth() / 7);
        ViewCompat.setPivotY(mHeaderMiddleCollapsible, mHeaderMiddleCollapsible.getHeight() / 2);
        ViewCompat.setScaleX(mHeaderMiddleCollapsible, avatarRatio);
        ViewCompat.setScaleY(mHeaderMiddleCollapsible, avatarRatio);
        ViewCompat.setAlpha(mHeaderMiddleCollapsible, avatarRatio);

        ViewCompat.setAlpha(mHeaderMiddleAnswer, 1f - middleAnswerRatio);
        ViewCompat.setPivotY(mHeaderMiddleAnswer, mHeaderMiddleAnswer.getHeight());
        ViewCompat.setScaleY(mHeaderMiddleAnswer, 1f - middleAnswerRatio);

        ViewCompat.setAlpha(mHeaderCaption1, middleAnswerRatio);
        ViewCompat.setAlpha(mHeaderCaption2, 1f - middleAnswerRatio);

        final ViewGroup.LayoutParams lp = mHeaderMiddle.getLayoutParams();
        lp.height = m60dp - (int)(m10dp * (1f - middleRatio));
        mHeaderMiddle.setLayoutParams(lp);
    }

    @Override
    public boolean isScrolling() {
        return mIsScrolling;
    }

}
