package com.base.library.view.viewPager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 作者：王东一 on 2016/5/20 18:22
 **/
public class WDYViewPager extends ViewPager {
    private boolean mDisableScroll = true;

    public WDYViewPager(Context context) {
        super(context);
    }

    public WDYViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDisableScroll(boolean mDisableScroll) {
        this.mDisableScroll = mDisableScroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mDisableScroll) {
            try {
                super.onInterceptTouchEvent(ev);
                return super.onInterceptTouchEvent(ev);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        } else {
            return false;
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mDisableScroll) {
            try {
                super.onTouchEvent(ev);
                return super.onInterceptTouchEvent(ev);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        } else {
            return false;
        }
    }


}
