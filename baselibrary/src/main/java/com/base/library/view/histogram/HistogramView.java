package com.base.library.view.histogram;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.base.library.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * 作者：王东一 on 2016/6/3 09:31
 **/
public class HistogramView extends View {
    private Paint xLinePaint;// 坐标轴 轴线 画笔：
    private Paint hLinePaint;// 坐标轴水平内部 虚线画笔
    private Paint titlePaint;// 绘制文本的画笔
    private Paint paint;// 矩形画笔 柱状图的样式信息

    private int[] progress = {2000, 5000, 6000, 8000, 500, 6000, 9000};// 7
    // 条，显示各个柱状的数据
    private int[] aniProgress;// 实现动画的值
    private final int TRUE = 1;// 在柱状图上显示数字
    private int[] text;// 设置点击事件，显示哪一条柱状的信息
    private ArrayList<HistogramBean> arrayList = new ArrayList<>();
    private int flag;// 是否使用动画
    private int width;
    private int height;
    private int hPerHeight;
    private HistogramAnimation ani;
    private Canvas canvas;
    private int ShowMax;

    public ArrayList<HistogramBean> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<HistogramBean> arrayList) {
        this.arrayList = arrayList;
    }

    public HistogramView(Context context) {
        super(context);
        init();
    }

    public HistogramView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HistogramView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        arrayList = new ArrayList<>();

        text = new int[]{0, 0, 0, 0, 0, 0, 0};
        aniProgress = new int[]{55, 23, 44, 2, 12, 5, 12};
        ani = new HistogramAnimation();
        ani.setDuration(2000);

        xLinePaint = new Paint();
        hLinePaint = new Paint();
        titlePaint = new Paint();
        paint = new Paint();

        // 给画笔设置颜色
        xLinePaint.setColor(ContextCompat.getColor(getContext(), R.color.text_dark_gray));
        hLinePaint.setColor(ContextCompat.getColor(getContext(), R.color.line));
        titlePaint.setColor(ContextCompat.getColor(getContext(), R.color.text_grey));
        titlePaint.setTextAlign(Paint.Align.RIGHT);
        titlePaint.setTextSize(sp2px(11));
        titlePaint.setAntiAlias(true);
        titlePaint.setStyle(Paint.Style.FILL);
    }

    public void start(int flag) {
        this.flag = flag;
        this.startAnimation(ani);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        width = getWidth() - dp2px(20);
        height = getHeight() - dp2px(20);
        ShowMax = height - dp2px(20);
        //绘制X轴线
        canvas.drawLine(dp2px(35), height, width, height, xLinePaint);
        //绘制Y轴线
        canvas.drawLine(dp2px(35), height, dp2px(35), dp2px(20), xLinePaint);

        int leftHeight = height - dp2px(20);// 左侧外周的 需要划分的高度：
        hPerHeight = leftHeight / 3;// 分成三部分
        hLinePaint.setTextAlign(Paint.Align.CENTER);
        // 设置条虚线
        for (int i = 1; i < 4; i++) {
            canvas.drawLine(dp2px(35), height - i * hPerHeight, width, height - i * hPerHeight, hLinePaint);
        }
        if (arrayList.size() > 0)
            drawHistogram();
    }

    public void drawHistogram() {
        // 绘制 X 周 做坐标
        int step = (width - dp2px(20)) / getArrayList().size() - dp2px(5);
        titlePaint.setTextAlign(Paint.Align.CENTER);
        int NumMax = 0;
        for (int i = 0; i < getArrayList().size(); i++) {
            if (getArrayList().get(i).getHistogramNum() > NumMax) {
                NumMax = getArrayList().get(i).getHistogramNum();
            }
            // 设置底部的数字
            canvas.drawText(getArrayList().get(i).getName(), dp2px(8) + (i + 1) * step, height + dp2px(15), titlePaint);
        }
        DecimalFormat df = new DecimalFormat("0.0");
        double change = 60.0;
        // 绘制 Y 坐标值
        for (int i = 0; i < 4; i++) {
            canvas.drawText(df.format(NumMax / 3 * i / change) + "h", dp2px(20), height - i * hPerHeight, titlePaint);
        }
        for (int i = 0; i < getArrayList().size(); i++) {
            for (int j = 0; j < getArrayList().get(i).getArrayList().size(); j++) {
                changeColor(getArrayList().get(i).getArrayList().get(j).getColor());
                canvas.drawRect(dp2px(40 + (getArrayList().get(i).getPosition() * 40)),
                        NumMax == 0 ? 0 : (height - (((getArrayList().get(i).getArrayList().get(j).getHeight()) * ShowMax / NumMax))),
                        dp2px(60 + getArrayList().get(i).getPosition() * 40),
                        NumMax == 0 ? 0 : (j > 0 ? (height - (((getArrayList().get(i).getArrayList().get(j - 1).getHeight()) * ShowMax / NumMax))) : height),
                        paint);// 长方形
            }
        }
    }

    //获取颜色

    private void changeColor(int position) {
        switch (position) {
            case 0:
                paint.setColor(Color.rgb(140, 197, 64));
                break;
            case 1:
                paint.setColor(Color.rgb(0, 159, 93));
                break;
            case 2:
                paint.setColor(Color.rgb(1, 159, 160));
                break;
            case 3:
                paint.setColor(Color.rgb(1, 159, 222));
                break;
            case 4:
                paint.setColor(Color.rgb(0, 124, 220));
                break;
            case 5:
                paint.setColor(Color.rgb(136, 125, 221));
                break;
            case 6:
                paint.setColor(Color.rgb(205, 123, 221));
                break;
            case 7:
                paint.setColor(Color.rgb(255, 86, 117));
                break;
            case 8:
                paint.setColor(Color.rgb(255, 18, 68));
                break;
            case 9:
                paint.setColor(Color.rgb(255, 131, 69));
                break;
            case 10:
                paint.setColor(Color.rgb(248, 189, 11));
                break;
            case 11:
                paint.setColor(Color.rgb(209, 210, 212));
                break;
        }
    }

    private int dp2px(int value) {
        float v = getContext().getResources().getDisplayMetrics().density;
        return (int) (v * value + 0.5f);
    }

    private int sp2px(int value) {
        float v = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (v * value + 0.5f);
    }


    /**
     * 集成animation的一个动画类
     *
     * @author 李垭超
     */
    private class HistogramAnimation extends Animation {
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if (interpolatedTime < 1.0f && flag == 2) {
                for (int i = 0; i < aniProgress.length; i++) {
                    aniProgress[i] = (int) (progress[i] * interpolatedTime);
                }
            } else {
                for (int i = 0; i < aniProgress.length; i++) {
                    aniProgress[i] = progress[i];
                }
            }
            invalidate();
        }
    }
}
