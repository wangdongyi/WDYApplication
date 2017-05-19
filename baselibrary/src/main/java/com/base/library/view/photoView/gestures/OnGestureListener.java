package com.base.library.view.photoView.gestures;

/**
 * 作者：王东一
 * 创建时间：2017/4/26.
 */

public interface OnGestureListener {
    public void onDrag(float dx, float dy);

    public void onFling(float startX, float startY, float velocityX, float velocityY);

    public void onScale(float scaleFactor, float focusX, float focusY);
}
