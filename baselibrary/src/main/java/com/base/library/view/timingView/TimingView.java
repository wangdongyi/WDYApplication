package com.base.library.view.timingView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.base.library.R;
import com.base.library.util.CodeUtil;

/**
 * Created by wangdongyi on 2017/2/23.
 * 定时控件
 */

public class TimingView extends View {
    /**
     * 第一圈的颜色
     */
    private int mFirstColor;
    /**
     * 第二圈的颜色
     */
    private int mSecondColor;
    /**
     * 圈的宽度
     */
    private int mCircleWidth;
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 当前进度
     */
    private int mProgress;

    /**
     * 速度
     */
    private int mSpeed = 10;

    /**
     * 是否应该开始下一个
     */
    private boolean isNext = false;
    private boolean working = true;
    private onRunListen onRunListen;
    public Runnable refreshRunnable;
    private Thread mThread;


    public TimingView(Context context) {
        super(context);
        init();
    }

    public TimingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 获取自定义控件的一些值
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public TimingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.timingView, defStyleAttr, 0);
//        mFirstColor = a.getColor(R.styleable.timingView_CircleColor, Color.GRAY);
//        mSpeed = a.getInt(R.styleable.timingView_CircleSpeed, 20);
//        mCircleWidth = CodeUtil.dip2px(getContext(), a.getInt(R.styleable.timingView_CircleWidth, 10));
//        a.recycle();
        init();
    }

    private void init() {
        mCircleWidth = CodeUtil.dip2px(getContext(), 3);
        mFirstColor = ContextCompat.getColor(getContext(), R.color.white);
        mSecondColor = ContextCompat.getColor(getContext(), R.color.blue);
        mPaint = new Paint();
        working = true;
        refreshRunnable = new Runnable() {
            @Override
            public void run() {
                while (working) {
                    mProgress++;
                    if (mProgress == 360) {
                        if (getOnRunListen() != null) {
                            getOnRunListen().onFinish();
                        }
                        working = false;
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(mSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        //绘图线程
        mThread = new Thread(refreshRunnable);
        mThread.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int centre = getWidth() / 2; // 获取圆心的x坐标
        int radius = centre - mCircleWidth / 2;// 半径
        mPaint.setStrokeWidth(mCircleWidth); // 设置圆环的宽度
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setTextSize(CodeUtil.sp2px(getContext(), 12));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius); // 用于定义的圆弧的形状和大小的界限
        if (!isNext) {// 第一颜色的圈完整，第二颜色跑
            mPaint.setColor(mFirstColor); // 设置圆环的颜色
            canvas.drawCircle(centre, centre, radius, mPaint); // 画出圆环
            mPaint.setColor(mSecondColor); // 设置圆环的颜色
            canvas.drawArc(oval, -90, mProgress, false, mPaint); // 根据进度画圆弧
        } else {
            mPaint.setColor(mSecondColor); // 设置圆环的颜色
            canvas.drawCircle(centre, centre, radius, mPaint); // 画出圆环
            mPaint.setColor(mFirstColor); // 设置圆环的颜色
            canvas.drawArc(oval, -90, mProgress, false, mPaint); // 根据进度画圆弧
        }
    }

    public TimingView.onRunListen getOnRunListen() {
        return onRunListen;
    }

    public void setOnRunListen(TimingView.onRunListen onRunListen) {
        this.onRunListen = onRunListen;
    }

    public interface onRunListen {
        void onFinish();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        closeHandler();
    }

    public void closeHandler() {
        if (working) {
            if (mThread != null && mThread.isAlive()) {
                mThread.interrupt();
                mThread = null;
            }
            working = false;
        }
    }
}
