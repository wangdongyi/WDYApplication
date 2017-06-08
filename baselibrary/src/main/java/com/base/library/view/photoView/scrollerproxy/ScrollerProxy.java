package com.base.library.view.photoView.scrollerproxy;

import android.content.Context;
import android.os.Build;

/**
 * 作者：王东一
 * 创建时间：2017/4/26.
 */

public abstract class ScrollerProxy {
    public static ScrollerProxy getScroller(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            return new PreGingerScroller(context);
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return new GingerScroller(context);
        } else {
            return new IcsScroller(context);
        }
    }

    public abstract boolean computeScrollOffset();

    public abstract void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY,
                               int maxY, int overX, int overY);

    public abstract void forceFinished(boolean finished);

    public abstract boolean isFinished();

    public abstract int getCurrX();

    public abstract int getCurrY();
}