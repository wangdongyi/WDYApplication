package com.base.library.util;
//Created by 王东一 on 2016/11/1.


import android.util.Log;

import com.base.library.application.BaseApplication;

public class WDYLog {
    static {
        System.loadLibrary("wdy-native-lib");
    }

    public static native void JniLogD(String tag, String message);
    public static native void JniLogI(String tag,String message);
    public static native void JniLogW(String tag,String message);
    public static native void JniLogE(String tag,String message);
    public static native void JniLogF(String tag,String message);

    private static int LOG_MAX_LENGTH = 2000;

    public static void e(String title, String msg) {
        if (BaseApplication.isOpenLog()) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAX_LENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    JniLogE(title + i, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAX_LENGTH;
                } else {
                    JniLogE(title + i, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }

    public static void i(String title, String msg) {
        if (BaseApplication.isOpenLog()) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAX_LENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    JniLogI(title + i, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAX_LENGTH;
                } else {
                    JniLogI(title + i, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }

    public static void d(String title, String msg) {
        if (BaseApplication.isOpenLog()) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAX_LENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    JniLogD(title + i, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAX_LENGTH;
                } else {
                    JniLogD(title + i, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }

    public static void w(String title, String msg) {
        if (BaseApplication.isOpenLog()) {
            Log.w(title, msg);
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAX_LENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    JniLogW(title + i, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAX_LENGTH;
                } else {
                    JniLogW(title + i, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }

    public static void v(String title, String msg) {
        if (BaseApplication.isOpenLog()) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAX_LENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    JniLogF(title + i, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAX_LENGTH;
                } else {
                    JniLogF(title + i, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }
}
