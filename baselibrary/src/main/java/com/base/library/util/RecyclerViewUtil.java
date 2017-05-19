package com.base.library.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.base.library.application.BaseApplication;

/**
 * 作者：王东一
 * 创建时间：2017/5/2.
 */

public class RecyclerViewUtil {

    private boolean move = false;
    private int position;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    public RecyclerViewUtil(RecyclerView recyclerView, int position) {
        this.position = position;
        this.mRecyclerView = recyclerView;
        recyclerView.addOnScrollListener(new RecyclerViewListener());
        mLinearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
    }

    public void smoothMoveToPosition() {
        if (position<0 || position>=mRecyclerView.getAdapter().getItemCount() ){
            BaseApplication.getToastUtil().showMiddleToast("超出范围了");
            return;
        }
        mRecyclerView.stopScroll();
        int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();
        if (position <= firstItem) {
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            int top = mRecyclerView.getChildAt(position - firstItem).getTop();
            mRecyclerView.smoothScrollBy(0, top);
        } else {
            mRecyclerView.smoothScrollToPosition(position);
            move = true;
        }
    }

    private class RecyclerViewListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (move && newState == RecyclerView.SCROLL_STATE_IDLE) {
                move = false;
                int n = position - mLinearLayoutManager.findFirstVisibleItemPosition();
                if (0 <= n && n < mRecyclerView.getChildCount()) {
                    int top = mRecyclerView.getChildAt(n).getTop();
                    mRecyclerView.smoothScrollBy(0, top);
                }

            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (move) {
                move = false;
                int n = position - mLinearLayoutManager.findFirstVisibleItemPosition();
                if (0 <= n && n < mRecyclerView.getChildCount()) {
                    int top = mRecyclerView.getChildAt(n).getTop();
                    mRecyclerView.scrollBy(0, top);
                }
            }
        }
    }
}
