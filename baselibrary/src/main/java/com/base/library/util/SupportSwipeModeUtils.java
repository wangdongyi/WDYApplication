package com.base.library.util;

import android.content.SharedPreferences;
import android.os.Build;

public class SupportSwipeModeUtils {

    private static final String TAG = "SupportSwipeModeUtils";
    private static int mode = 0;

    public static void switchSwipebackMode(boolean enable) {
        SharedPreferences sharePreference = CCPAppManager.getSharePreference();
        boolean supportSwipe = sharePreference.getBoolean("settings_support_swipe", true);
        if(supportSwipe != enable) {
            sharePreference.edit().putBoolean("settings_support_swipe", enable).commit();
        }
        WDYLog.d(TAG , "switchSwipebackMode, from " + supportSwipe + " to " + enable);
    }

    public static boolean isEnable() {
        if(DemoUtils.nullAsNil(Build.VERSION.INCREMENTAL).toLowerCase().contains("flyme")
                || DemoUtils.nullAsNil(Build.DISPLAY).toLowerCase().contains("flyme")) {
            return false;
        }

        if(mode == 0) {
            if(false) {
                mode = 2;
            } else {
                mode = 1;
            }
        }
        return mode == 1;
    }
}
