package com.base.library.view.wheel;

/**
 * @author 王东一
 * @ClassName: OnWheelChangedListener
 * @Description: (滚动监听)
 * @date 2015-1-9 下午3:12:59
 */
public interface OnWheelChangedListener {
    /**
     * Callback method to be invoked when current item changed
     *
     * @param wheel    the wheel view whose state has changed
     * @param oldValue the old value of current item
     * @param newValue the new value of current item
     */
    void onChanged(WheelView wheel, int oldValue, int newValue);
}
