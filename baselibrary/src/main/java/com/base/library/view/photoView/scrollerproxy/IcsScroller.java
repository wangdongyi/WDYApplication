package com.base.library.view.photoView.scrollerproxy;

import android.content.Context;

/**
 * 作者：王东一
 * 创建时间：2017/4/26.
 */

public class IcsScroller extends GingerScroller {
    public IcsScroller(Context context) {
        super(context);
    }

    @Override
    public boolean computeScrollOffset() {
        return mScroller.computeScrollOffset();
    }
}
