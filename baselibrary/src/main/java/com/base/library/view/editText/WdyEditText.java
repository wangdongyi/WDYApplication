package com.base.library.view.editText;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.TextView;

import com.base.library.R;
import com.base.library.util.CodeUtil;

import java.util.Random;

/**
 * 作者：王东一 on 2016/3/23 14:01
 **/
@SuppressLint("AppCompatCustomView")
public class WdyEditText extends EditText {
    private ViewGroup contentContainer;
    private int height;
    private String cacheStr = "";
    private Drawable deleteDrawable;
    private boolean clear = false;
    //是否显示删除动画
    private boolean isPlay = false;
    //是否显示删除按钮
    private boolean isDelete = true;

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void setIsPlay(boolean isPlay) {
        this.isPlay = isPlay;
    }

    private int deleteWith;

    public WdyEditText(Context context) {
        this(context, null);
        init(context, null);
        setListener();
    }

    public WdyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        setListener();
    }


    public WdyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
        setListener();

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    private void setListener() {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (cacheStr.length() > s.length() && !clear && isPlay()) {
                    char last = cacheStr.charAt(cacheStr.length() - 1);
                    update(last, false);
                }
                cacheStr = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isDelete())
                    setDrawable();
            }
        });
    }

    private void update(char last, boolean isUp) {
        final TextView textView = new TextView(getContext());
        textView.setTextColor(getTextColors());
        textView.setTextSize(getTextSize());
        textView.setText(String.valueOf(last));
        textView.setGravity(Gravity.CENTER);
        contentContainer.addView(textView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.measure(0, 0);


        int pos = getSelectionStart();
        Layout layout = getLayout();
        int line = layout.getLineForOffset(pos);
        int baseline = layout.getLineBaseline(line);
        int ascent = layout.getLineAscent(line);

        float startX = 0;
        float startY = 0;
        float endX = 0;
        float endY = 0;
        if (isUp) {
            startX = layout.getPrimaryHorizontal(pos) + 100;
            startY = 300;
            endX = startX;
            endY = baseline + ascent;
        } else {
            endX = new Random().nextInt(contentContainer.getWidth());
            endY = height / 3 * 2;
            startX = layout.getPrimaryHorizontal(pos) + textView.getTextSize() / 2;
            int[] location = new int[2];
            getLocationOnScreen(location);
            startY = location[1] + ascent + baseline;
        }


        final AnimatorSet animSet = new AnimatorSet();
        ObjectAnimator animX = ObjectAnimator.ofFloat(textView, "translationX", startX, endX);
        ObjectAnimator animY = ObjectAnimator.ofFloat(textView, "translationY", startY, endY);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(textView, "scaleX", 0.6f, 1.2f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(textView, "scaleY", 0.6f, 1.2f);

        animY.setInterpolator(new DecelerateInterpolator());
        animSet.setDuration(500);
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                contentContainer.removeView(textView);
            }
        });
        animSet.playTogether(animX, animY, scaleX, scaleY);
        animSet.start();
    }


    private void init(Context context, AttributeSet attributeSet) {
        initAttributes(context, attributeSet);
        if (!isInEditMode()) {
            contentContainer = (ViewGroup) ((AppCompatActivity) getContext()).findViewById(android.R.id.content);
            DisplayMetrics dm = new DisplayMetrics();
            ((AppCompatActivity) getContext()).getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
            height = dm.heightPixels;//高度
            deleteDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.search_delete, null);
            assert deleteDrawable != null;
            deleteDrawable.setBounds(0, 0, deleteWith, deleteWith);
            clearFocus();
            setOnFocusChangeListener(new OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (isDelete())
                        setDrawable();
                }
            });
        }
    }

    protected TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }

    private void initAttributes(Context context, AttributeSet attributeSet) {
        TypedArray attr = getTypedArray(context, attributeSet, R.styleable.WdyEditTextStyleable);
        if (attr == null) {
            return;
        }
        try {
            deleteWith = (int) attr.getDimension(R.styleable.WdyEditTextStyleable_delete_width, CodeUtil.dip2px(context, 30));
        } finally {
            attr.recycle();
        }
    }

    private void setDrawable() {
        //  绘制删除按钮
        if (getText().toString().length() > 0 && isFocused()) {
            setCompoundDrawables(null, null, deleteDrawable, null);
            clear = false;
        } else {
            setCompoundDrawables(null, null, null, null);
        }
    }

    // 处理删除事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (deleteDrawable != null && isDelete()) {
                int eventX = (int) event.getRawX();
                int eventY = (int) event.getRawY();
                Rect rect = new Rect();
                getGlobalVisibleRect(rect);
                rect.left = rect.right - CodeUtil.dip2px(getContext(), 50);
                if (rect.contains(eventX, eventY)) {
                    setText("");
                    clear = true;
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }


}
