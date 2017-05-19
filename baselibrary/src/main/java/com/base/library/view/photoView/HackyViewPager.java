package com.base.library.view.photoView;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.base.library.util.WDYLog;

/**
 * 作者：王东一
 * 创建时间：2017/4/26.
 */

public class HackyViewPager extends ViewPager {
    private static final String TAG = "HackyViewPager";

    public HackyViewPager(Context context) {
        super(context);
    }

    public HackyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            WDYLog.e(TAG, "hacky viewpager error1");
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            WDYLog.e(TAG, "hacky viewpager error2");
            return false;
        }
    }
}
