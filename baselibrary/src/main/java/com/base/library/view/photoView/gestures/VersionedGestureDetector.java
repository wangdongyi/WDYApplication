package com.base.library.view.photoView.gestures;

import android.content.Context;
import android.os.Build;

/**
 * 作者：王东一
 * 创建时间：2017/4/26.
 */

public class VersionedGestureDetector {
    public static GestureDetector newInstance(Context context, OnGestureListener listener) {
        final int sdkVersion = Build.VERSION.SDK_INT;
        GestureDetector detector;

        if (sdkVersion < Build.VERSION_CODES.ECLAIR) {
            detector = new CupcakeGestureDetector(context);
        } else if (sdkVersion < Build.VERSION_CODES.FROYO) {
            detector = new EclairGestureDetector(context);
        } else {
            detector = new FroyoGestureDetector(context);
        }

        detector.setOnGestureListener(listener);

        return detector;
    }

}
