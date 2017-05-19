package com.base.library.util;

import android.os.Build;

/**
 * Created by Jorstin on 2015/6/23.
 */
public class SDKVersionUtils {

    public static boolean isSmallerVersion(int version) {
        return (Build.VERSION.SDK_INT < version);
    }

    public static boolean isGreaterorEqual(int version) {
        return (Build.VERSION.SDK_INT >= version);
    }

    public static boolean isSmallerorEqual(int version) {
        return (Build.VERSION.SDK_INT <= version);
    }
}
