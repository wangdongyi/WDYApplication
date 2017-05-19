package com.base.library.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by wangdongyi on 2017/2/10.
 * 弹出吐司框
 */

public class ToastUtil {
    private static Toast tst = null;
    private Context context;

    public ToastUtil(Context context) {
        this.context = context;
    }

    /**
     * 显示Toast提示窗口(长时间)
     * <p>
     * param context
     * param msg
     */
    public void showToastLong(String msg) {
        if (context == null || TextUtils.isEmpty(msg)) {
            return;
        }

        showToast(msg, Toast.LENGTH_LONG);
    }

    /**
     * 显示Toast提示窗口(长时间)
     * <p>
     * param context
     * param msgId
     */
    public void showToastLong(int msgId) {
        if (context == null) {
            return;
        }

        showToastLong(context.getResources().getString(msgId));
    }

    /**
     * 显示Toast提示窗口(短时间)
     * <p>
     * param context
     * param msg
     */
    public void showToastShort(String msg) {
        if (context == null || TextUtils.isEmpty(msg)) {
            return;
        }

        // Toast显示时间全为长时间显示
        showToast(msg, Toast.LENGTH_SHORT);
    }

    /**
     * 显示Toast提示窗口(短时间)
     * <p>
     * param context
     * param msgId
     */
    public void showToastShort(int msgId) {
        if (context == null) {
            return;
        }

        showToastShort(context.getResources().getString(msgId));
    }

    /**
     * 显示Toast提示窗口
     * <p>
     * param context
     * param msg
     * param length
     */
    public void showToast(String msg, int length) {
        if (context == null || TextUtils.isEmpty(msg)) {
            return;
        }

        if (tst != null) {
            tst.cancel();
            tst = null;
        }
        tst = Toast.makeText(context, msg, length);
        tst.setGravity(Gravity.CENTER, 0, 0);
        tst.show();
    }

    /**
     * 显示Toast提示窗口
     * <p>
     * param context
     * param msg
     * param length
     */
    public void showMiddleToast(String msg) {
        if (context == null || TextUtils.isEmpty(msg)) {
            return;
        }

        if (tst != null) {
            tst.cancel();
            tst = null;
        }
        tst = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        tst.setGravity(Gravity.CENTER, 0, 0);
        tst.show();
    }
}
