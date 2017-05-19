package com.base.library.view.photoView.gestures;

import android.view.MotionEvent;

/**
 * 作者：王东一
 * 创建时间：2017/4/26.
 */

public interface GestureDetector {
    public boolean onTouchEvent(MotionEvent ev);

    public boolean isScaling();

    public void setOnGestureListener(OnGestureListener listener);
}
