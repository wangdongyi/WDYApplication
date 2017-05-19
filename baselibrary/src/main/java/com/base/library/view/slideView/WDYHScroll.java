package com.base.library.view.slideView;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

import com.base.library.util.WDYLog;

/**
 * 作者：王东一 on 2016/5/22 13:46
 **/
public class WDYHScroll extends HorizontalScrollView {
    private Handler mHandler;
    private ScrollViewListener scrollViewListener;


    private float downX;
    private int AutoScroll = 0;

    public WDYHScroll(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public WDYHScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WDYHScroll(Context context) {
        super(context);
    }

    public interface ScrollViewListener {

        void onScrollChanged(ScrollType scrollType);

    }

    private int ScrollWitch;//滚动范围

    public int getScrollWitch() {
        return ScrollWitch;
    }

    public void setScrollWitch(int scrollWitch) {
        ScrollWitch = scrollWitch;
    }

    /**
     * 滚动状态
     * IDLE 滚动停止
     * TOUCH_SCROLL 手指拖动滚动
     * FLING滚动
     *
     * @author DZC
     * @version XHorizontalScrollViewgallery
     * @Time 2014-12-7 上午11:06:52
     */
    enum ScrollType {
        IDLE, TOUCH_SCROLL, FLING
    }

    /**
     * 记录当前滚动的距离
     */
    private int currentX = -9999999;
    /**
     * 当前滚动状态
     */
    private ScrollType scrollType = ScrollType.IDLE;
    /**
     * 滚动监听间隔
     */
    private int scrollDealy = 50;
    /**
     * 滚动监听runnable
     */
    private Runnable scrollRunnable = new Runnable() {

        @Override
        public void run() {
            if (AutoScroll == 0) {
                if (getScrollX() == currentX) {
                    //滚动停止  取消监听线程
                    scrollType = ScrollType.IDLE;
                    if (scrollViewListener != null) {
                        scrollViewListener.onScrollChanged(scrollType);
                    }
                    mHandler.removeCallbacks(this);
                    return;
                } else {
                    //手指离开屏幕    view还在滚动的时候
                    scrollType = ScrollType.FLING;
                    if (scrollViewListener != null) {
                        scrollViewListener.onScrollChanged(scrollType);
                    }
                }
            } else if (AutoScroll == 1) {
                if (getScrollX() > 0)
                    smoothScrollTo(0, 0);
                else {
                    mHandler.removeCallbacks(this);
                    return;
                }
            } else {
                if (getScrollX() < getScrollWitch())
                    smoothScrollTo(getScrollWitch(), 0);
                else {
                    mHandler.removeCallbacks(this);
                    return;
                }
            }
            currentX = getScrollX();
//            mHandler.postDelayed(this, scrollDealy);
        }
    };

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                WDYLog.i("删除按下", downX + "");
                break;

        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                this.scrollType = ScrollType.TOUCH_SCROLL;
                scrollViewListener.onScrollChanged(scrollType);
                //手指在上面移动的时候   取消滚动监听线程
                mHandler.removeCallbacks(scrollRunnable);
                break;
            case MotionEvent.ACTION_UP:
                if (downX - ev.getX() < -10) {
                    //左走
                    AutoScroll = 1;

                } else if (downX - ev.getX() > 10) {
                    //右走
                    AutoScroll = 2;
                }
                //手指移动的时候
                mHandler.post(scrollRunnable);
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 必须先调用这个方法设置Handler  不然会出错
     * 2014-12-7 下午3:55:39
     *
     * @param handler
     * @return void
     * @author DZC
     * @
     */
    public void setHandler(Handler handler) {
        this.mHandler = handler;
    }

    /**
     * 设置滚动监听
     * 2014-12-7 下午3:59:51
     *
     * @param listener
     * @return void
     * @author DZC
     * @
     */
    public void setOnScrollStateChangedListener(ScrollViewListener listener) {
        this.scrollViewListener = listener;
    }
}
