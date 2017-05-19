package com.base.library.view.brokenLineView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.base.library.R;
import com.base.library.util.CodeUtil;
import com.base.library.view.pickerView.PickerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdongyi on 2017/2/23.
 * 折线图
 */

public class BrokenLineView extends View {
    public static final String TAG = "BrokenLineView";

    /**
     * text之间间距和minTextSize之比
     */
    public static final float MARGIN_ALPHA = 2.8f;
    /**
     * 自动回滚到中间的速度
     */
    public static final float SPEED = 2;

    private Paint imaginary_line_paint;//画虚线
    private Paint text_paintY;//y轴的字体
    private Paint text_paintX;//x轴的字体
    private Paint circle_paint;//小原点
    private Paint circle_big_paint;//小原点
    private Paint line_paint;//小原点
    private ArrayList<Integer> YAxisList = new ArrayList<>();//y轴数据
    private ArrayList<Integer> XAxisList = new ArrayList<>();//x轴数据
    private List<String> mDataList;
    /**
     * 选中的位置，这个位置是mDataList的中心位置，一直不变
     */
    private int mCurrentSelected;
    private Paint mPaint, nPaint;

    private float mMaxTextSize = 80;
    private float mMinTextSize = 40;

    private float mMaxTextAlpha = 255;
    private float mMinTextAlpha = 120;

    private int mColorText = 0x333333;
    private int nColorText = 0x666666;

    private int mViewHeight;
    private int mViewWidth;

    private float mLastDownY;
    private int maxHeight;
    /**
     * 滑动的距离
     */
    private float mMoveLen = 0;
    private Canvas canvas;
    private PickerView.onSelectListener mSelectListener;
    private ArrayList<Integer> brokenLineBeanArrayList = new ArrayList<>();

    public ArrayList<Integer> getBrokenLineBeanArrayList() {
        return brokenLineBeanArrayList;
    }

    public void setBrokenLineBeanArrayList(ArrayList<Integer> brokenLineBeanArrayList) {
        this.brokenLineBeanArrayList = brokenLineBeanArrayList;
    }


    public BrokenLineView(Context context) {
        super(context);
        init();
    }

    public BrokenLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewHeight = getMeasuredHeight();
        mViewWidth = getMeasuredWidth();
//        // 按照View的高度计算字体大小
//        mMaxTextSize = mViewHeight / 7f;
//        mMinTextSize = mMaxTextSize / 2.2f;
//        invalidate();
    }

    private void init() {
        maxHeight = (5 * CodeUtil.dip2px(getContext(), 40));
        //虚线画笔设置
        imaginary_line_paint = new Paint();
        imaginary_line_paint.setColor(ContextCompat.getColor(getContext(), R.color.line));
        imaginary_line_paint.setStyle(Paint.Style.STROKE);
        imaginary_line_paint.setAntiAlias(true);
        imaginary_line_paint.setStrokeWidth(CodeUtil.dip2px(getContext(), 1));
        //y轴画笔设置颜色
        text_paintY = new Paint();
        text_paintY.setColor(ContextCompat.getColor(getContext(), R.color.text_grey));
        text_paintY.setTextSize(CodeUtil.sp2px(getContext(), 16));
        text_paintY.setAntiAlias(true);
        text_paintY.setStyle(Paint.Style.FILL);
        text_paintY.setTextAlign(Paint.Align.LEFT);
        YAxisList.add(100);
        YAxisList.add(80);
        YAxisList.add(60);
        YAxisList.add(40);
        YAxisList.add(20);
        YAxisList.add(0);
        XAxisList.add(1);
        XAxisList.add(2);
        XAxisList.add(3);
        XAxisList.add(4);
        XAxisList.add(5);
        XAxisList.add(6);
        XAxisList.add(7);
        //设置小圆点
        circle_paint = new Paint();
        // 设置画笔为抗锯齿
        circle_paint.setAntiAlias(true);
        // 设置颜色为红色
        circle_paint.setColor(ContextCompat.getColor(getContext(), R.color.blue));
        /**
         * 画笔样式分三种： 1.Paint.Style.STROKE：描边 2.Paint.Style.FILL_AND_STROKE：描边并填充
         * 3.Paint.Style.FILL：填充
         */
        circle_paint.setStyle(Paint.Style.FILL);

        circle_paint.setStrokeWidth(CodeUtil.dip2px(getContext(), 5));
        //设置小圆点
        circle_big_paint = new Paint();
        // 设置画笔为抗锯齿
        circle_big_paint.setAntiAlias(true);
        // 设置颜色为红色
        circle_big_paint.setColor(ContextCompat.getColor(getContext(), R.color.white));
        /**
         * 画笔样式分三种： 1.Paint.Style.STROKE：描边 2.Paint.Style.FILL_AND_STROKE：描边并填充
         * 3.Paint.Style.FILL：填充
         */
        circle_big_paint.setStyle(Paint.Style.FILL);

        circle_big_paint.setStrokeWidth(CodeUtil.dip2px(getContext(), 5));
        line_paint = new Paint();
        line_paint.setAntiAlias(true);
        line_paint.setStyle(Paint.Style.STROKE);
        line_paint.setStrokeWidth(CodeUtil.dip2px(getContext(), 2));
        line_paint.setColor(ContextCompat.getColor(getContext(), R.color.blue));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 根据index绘制view
        drawData(canvas);
    }

    private void drawData(Canvas canvas) {
        drawBackground(canvas);
        this.canvas = canvas;
//        //绘制原点
//        for (int i = 0; i < XAxisList.size(); i++) {
//            canvas.drawCircle((i + 1) * x_text_width - CodeUtil.dip2px(getContext(), 25), (XAxisList.size() - i - 1) * CodeUtil.dip2px(getContext(), 32), CodeUtil.dip2px(getContext(), 5), circle_paint);
//        }
//        //画线
//        for (int i = 0; i < XAxisList.size(); i++) {
//            Path path = new Path();
//            path.moveTo(i * 200 + 20, i * 200 + 20);
//            path.lineTo((i + 1) * 200 - 10, (i + 1) * 200 - 10);
//            canvas.drawPath(path, line_paint);
//        }
    }

    public void drawBackground(Canvas canvas) {
        // 绘制背景虚线
        for (int i = 0; i < 6; i++) {
            Path path = new Path();
            path.moveTo(0, i * CodeUtil.dip2px(getContext(), 40));
            path.lineTo(mViewWidth, i * CodeUtil.dip2px(getContext(), 40));
            int imaginary_line_width = CodeUtil.dip2px(getContext(), 3);
            PathEffect effects = new DashPathEffect(new float[]{imaginary_line_width, imaginary_line_width, imaginary_line_width, imaginary_line_width}, 1);
            imaginary_line_paint.setPathEffect(effects);
            canvas.drawPath(path, imaginary_line_paint);
        }
        //绘制y轴
        for (int i = 0; i < YAxisList.size(); i++) {
            canvas.drawText(String.valueOf(YAxisList.get(i)), 0, i * CodeUtil.dip2px(getContext(), 40) + CodeUtil.dip2px(getContext(), 25), text_paintY);
        }
        //绘制x轴
        int x_text_width = mViewWidth / 7;
        for (int i = 0; i < XAxisList.size(); i++) {
            canvas.drawText(String.valueOf(XAxisList.get(i)), (i + 1) * x_text_width - CodeUtil.dip2px(getContext(), 10), 6 * CodeUtil.dip2px(getContext(), 40) + CodeUtil.dip2px(getContext(), 5), text_paintY);
        }
        //绘制折线
        for (int i = 0; i < getBrokenLineBeanArrayList().size(); i++) {
            float x = (i + 1) * mViewWidth / 7 - CodeUtil.dip2px(getContext(), 5);
            float y = maxHeight - (maxHeight * getBrokenLineBeanArrayList().get(i) / 100) + CodeUtil.dip2px(getContext(), 25);
            if (i < 6) {
                Path path = new Path();
                path.moveTo(x, y);
                path.lineTo(getX(i + 1), getY(i + 1));
                canvas.drawPath(path, line_paint);
            }
            canvas.drawCircle(x, y, CodeUtil.dip2px(getContext(), 7), circle_big_paint);
            canvas.drawCircle(x, y, CodeUtil.dip2px(getContext(), 5), circle_paint);

        }
    }

    public float getX(int i) {
        float x = 0;
        x = (i + 1) * mViewWidth / 7 - CodeUtil.dip2px(getContext(), 5);
        return x;
    }

    public float getY(int i) {
        float Y = 0;
        Y = maxHeight - (maxHeight * getBrokenLineBeanArrayList().get(i) / 100) + CodeUtil.dip2px(getContext(), 25);
        return Y;
    }

    public void drawData() {

        invalidate();
    }
}
