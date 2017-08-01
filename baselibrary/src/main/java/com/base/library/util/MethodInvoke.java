package com.base.library.util;

import android.app.Activity;
import android.content.Context;

import com.base.library.R;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

//Created by 王东一 on 2016/9/8.
public class MethodInvoke {
    private static final String TAG = "WDY.MethodInvoke";

    public static int getTransitionValue(String componentName) {
        Class<?> clazz = getTransitionClass(componentName);
        if(clazz != null) {
            return getAnnotationValue(clazz);
        }
        return 0;
    }

    private static Class<?> getTransitionClass(String componentName) {
        try {
            Class clazz = Class.forName(componentName);
            return clazz;
        } catch (ClassNotFoundException e) {
            LogUtil.e(TAG , "Class " + componentName + " not found in dex");
        }
        return null;
    }

    public static int getAnnotationValue(Class<?> clazz) {
        ActivityTransition clazzAnnotation = clazz.getAnnotation(ActivityTransition.class);
        if(clazzAnnotation != null) {
            return clazzAnnotation.value();
        }
        while (clazz.getSuperclass() != null) {
            clazz = clazz.getSuperclass();
            clazzAnnotation = clazz.getAnnotation(ActivityTransition.class);
            if(clazzAnnotation != null) {
                return clazzAnnotation.value();
            }
        }
        return 0;
    }

    public static void startTransitionPopin(Context context) {
        if ((context == null) || (!(context instanceof Activity))) {
            return;
        }
        ((Activity) context).overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

    public static void startTransitionPopout(Context context) {
        if ((context == null) || (!(context instanceof Activity))) {
            return;
        }
        ((Activity) context).overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

    public static void startTransitionNotChange(Context context) {
        if ((context == null) || (!(context instanceof Activity))) {
            return;
        }
        ((Activity) context).overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }



    public interface OnSwipeInvokeResultListener {
        void onSwipeInvoke(boolean success);
    }

    public static class SwipeInvocationHandler implements InvocationHandler {

        public WeakReference<OnSwipeInvokeResultListener> mWeakReference ;
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if(mWeakReference == null) {
                LogUtil.i(TAG , "swipe invoke fail, callbackRef NULL!");
                return null;
            }
            OnSwipeInvokeResultListener onSwipeInvokeResultListener = mWeakReference.get();
            if(onSwipeInvokeResultListener == null) {
                LogUtil.i(TAG , "swipe invoke fail, callback NULL!");
                return null;
            }
            boolean result = false;
            if(args != null) {
                if(args.length > 0) {
                    boolean isBoolArgs = (args[0] instanceof Boolean);
                    if(isBoolArgs) {
                        result = ((Boolean)args[0]).booleanValue();
                    }
                }
            }
            onSwipeInvokeResultListener.onSwipeInvoke(result);
            return null;
        }
    }
}
