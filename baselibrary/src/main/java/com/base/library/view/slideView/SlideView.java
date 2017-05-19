package com.base.library.view.slideView;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.base.library.R;
import com.base.library.util.CodeUtil;

/**
 * 作者：王东一 on 2016/5/21 20:21
 **/
public class SlideView extends FrameLayout {
    private static final String TAG = "SlideView";
    private Button deleteView;
    private LinearLayout content;
    private WDYHScroll mScroller;
    private LinearLayout layoutChild;
    private onDeleteClick onDeleteClick;

    public SlideView.onDeleteClick getOnDeleteClick() {
        return onDeleteClick;
    }

    public void setOnDeleteClick(SlideView.onDeleteClick onDeleteClick) {
        this.onDeleteClick = onDeleteClick;
    }

    public interface onDeleteClick {
        void onDelete();
    }

    public SlideView(Context context) {
        super(context);
        initView();
    }

    public SlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SlideView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }


    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.slide_view, this, true);
        deleteView = (Button) findViewById(R.id.delete);
        content = (LinearLayout) findViewById(R.id.layout_content);
        mScroller = (WDYHScroll) findViewById(R.id.scrollView);
        LinearLayout.LayoutParams mLayoutParams = (LinearLayout.LayoutParams) content.getLayoutParams();
        mLayoutParams.width = CodeUtil.getScreenWidth(getContext());
        content.setLayoutParams(mLayoutParams);
        mScroller.setHandler(new Handler());
        mScroller.setScrollWitch(CodeUtil.dip2px(getContext(), 100));
        mScroller.setOnScrollStateChangedListener(new WDYHScroll.ScrollViewListener() {
            @Override
            public void onScrollChanged(WDYHScroll.ScrollType scrollType) {

            }
        });
        deleteView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mScroller.smoothScrollTo(0, 0);
                if (getOnDeleteClick() != null) {
                    getOnDeleteClick().onDelete();
                }
            }
        });
    }

    public void addContent(View view) {
        content.removeAllViewsInLayout();
        content.addView(view);
    }

}
