package com.base.library.view.sideBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.base.library.R;
import com.base.library.util.CodeUtil;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wangdongyi on 2016/12/22.
 * 需要传入一个textview
 * 字母筛选器
 */

public class SideBar extends View {
    // 触摸事件
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    private ArrayList<String> letterList = new ArrayList<>();
    private int textSize;//字母字体大小
    private int textColor;//字母颜色

    public ArrayList<String> getLetterList() {
        return letterList;
    }

    public void setLetterList(ArrayList<String> letterList) {
        this.letterList.clear();
        this.letterList.addAll(letterList);
    }

    private int choose = -1;// 选中
    private Paint paint = new Paint();

    private TextView mTextDialog;

    /**
     * 为SideBar设置显示字母的TextView
     *
     * @param mTextDialog
     */
    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }


    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SideBar(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        if (getLetterList().size() == 0) {
            getLetterList().addAll(Arrays.asList(getResources().getStringArray(R.array.letter)));
        }
    }

    private void init(Context context, AttributeSet attrs) {
        if (getLetterList().size() == 0) {
            getLetterList().addAll(Arrays.asList(getResources().getStringArray(R.array.letter)));
        }
        initAttributes(context, attrs);
    }

    private void initAttributes(Context context, AttributeSet attributeSet) {
        TypedArray attr = getTypedArray(context, attributeSet, R.styleable.SideBar);
        if (attr == null) {
            return;
        }
        try {
            textSize = CodeUtil.sp2px(context, attr.getInt(R.styleable.SideBar_SideBar_textSize, 10));
            textColor = attr.getColor(R.styleable.SideBar_SideBar_textColor, ContextCompat.getColor(context, R.color.text_blue));
        } finally {
            attr.recycle();
        }
    }

    protected TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }

    /**
     * 重写这个方法
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取焦点改变背景颜色.
        int height = getHeight();// 获取对应高度
        int width = getWidth(); // 获取对应宽度
        int singleHeight = height / letterList.size();// 获取每一个字母的高度

        for (int i = 0; i < letterList.size(); i++) {
            paint.setColor(textColor);
            // paint.setColor(Color.WHITE);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(textSize);
            // 选中的状态
            if (i == choose) {
                paint.setColor(Color.parseColor("#3399ff"));
                paint.setFakeBoldText(true);
            }
            // x坐标等于中间-字符串宽度的一半.
            float xPos = width / 2 - paint.measureText(letterList.get(i)) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(letterList.get(i), xPos, yPos, paint);
            paint.reset();// 重置画笔
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();// 点击y坐标
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * letterList.size());// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.

        switch (action) {
            case MotionEvent.ACTION_UP:
                choose = -1;//
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;

            default:
                if (oldChoose != c) {
                    if (c >= 0 && c < letterList.size()) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged(letterList.get(c));
                        }
                        if (mTextDialog != null) {
                            mTextDialog.setText(letterList.get(c));
                            mTextDialog.setVisibility(View.VISIBLE);
                        }
                        choose = c;
                        invalidate();
                    }
                }

                break;
        }
        return true;
    }

    /**
     * 向外公开的方法
     *
     *  onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    /**
     * 接口
     *
     * @author coder
     */
    public interface OnTouchingLetterChangedListener {
        public void onTouchingLetterChanged(String select);
    }
}
