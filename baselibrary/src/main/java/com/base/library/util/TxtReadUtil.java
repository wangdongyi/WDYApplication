package com.base.library.util;

/**
 * 作者：王东一
 * 创建时间：2017/7/3.
 * txt文件阅读
 */

public class TxtReadUtil {
    static {
        System.loadLibrary("wdy-native-lib");
    }

    public static native String TxtRead(String path);
}
