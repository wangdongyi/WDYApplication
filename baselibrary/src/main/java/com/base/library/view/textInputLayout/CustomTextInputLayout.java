package com.base.library.view.textInputLayout;

import android.content.Context;
import android.graphics.Canvas;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * 作者：王东一 on 2016/3/24 16:40
 **/
public class CustomTextInputLayout extends TextInputLayout {

    private boolean mIsHintSet;
    private CharSequence mHint;

    public CustomTextInputLayout(Context context) {
        super(context);
    }

    public CustomTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof EditText) {
            mHint = ((EditText) child).getHint();
        }
        super.addView(child, index, params);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!mIsHintSet && ViewCompat.isLaidOut(this)) {
            setHint(null);
            if (getEditText() != null)
                if (!TextUtils.isEmpty(getEditText().getHint())) {
                    mHint = getEditText().getHint();
                }
            setHint(mHint);
            mIsHintSet = true;
        }
    }
}
