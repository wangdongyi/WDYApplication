package com.base.library.okHtttpUtil;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import com.base.library.util.WDYLog;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 作者：王东一
 * 创建时间：2017/6/29.
 */

public class Platform {
    private static final Platform PLATFORM = findPlatform();

    public static Platform get() {
        WDYLog.i("PLATFORM-初始化", PLATFORM.getClass().toString());
        return PLATFORM;
    }

    private static Platform findPlatform() {
        try {
            Class.forName("android.os.Build");
            if (Build.VERSION.SDK_INT != 0) {
                return new Android();
            }
        } catch (ClassNotFoundException ignored) {
        }
        return new Platform();
    }

    public Executor defaultCallbackExecutor() {
        return Executors.newCachedThreadPool();
    }

    public void execute(Runnable runnable) {
        defaultCallbackExecutor().execute(runnable);
    }


    static class Android extends Platform {
        @Override
        public Executor defaultCallbackExecutor() {
            return new MainThreadExecutor();
        }

        static class MainThreadExecutor implements Executor {
            private final Handler handler = new Handler(Looper.getMainLooper());

            @Override
            public void execute(Runnable runnable) {
                handler.post(runnable);
            }
        }
    }


}
