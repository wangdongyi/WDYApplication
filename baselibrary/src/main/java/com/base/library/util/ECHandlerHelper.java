package com.base.library.util;//Created by 王东一 on 2016/9/8.

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;

public class ECHandlerHelper {
    private static final String a=null ;
    private static Handler b;
    private Handler c;
    private HandlerThread d;

    public ECHandlerHelper() {
//        c.d(a, "init stack:" + a.b());
        b = null;
        this.c = null;
        this.d = new HandlerThread(ECHandlerHelper.class.getSimpleName(), 0);
        this.d.start();
    }

    private static Handler a() {
        if(b == null) {
            b = new Handler(Looper.getMainLooper());
        }

        return b;
    }

    public static void postDelayedRunnOnUI(Runnable var0, long var1) {
        if(var0 != null) {
            a().postDelayed(var0, var1);
        }
    }

    public static void postRunnOnUI(Runnable var0) {
        if(var0 != null) {
            a().post(var0);
        }
    }

    public static void removeCallbacksRunnOnUI(Runnable var0) {
        if(var0 != null) {
            a().removeCallbacks(var0);
        }
    }

    public void postDelayedRunnOnThead(Runnable var1, long var2) {
        if(var1 != null) {
            this.getTheadHandler().postDelayed(var1, var2);
        }
    }

    public void setHighPriority() {
        if(this.d != null && this.d.isAlive()) {
            int var1 = this.d.getThreadId();

            try {
                if(Process.getThreadPriority(var1) == -8) {
//                    c.d(a, "setHighPriority No Need.");
                    return;
                }

                Process.setThreadPriority(var1, -8);
            } catch (Exception var2) {
//                c.a(a, "thread: " + var1 + " setHighPriority failed");
                return;
            }

//            c.d(a, "thread:" + var1 + " setHighPriority to" + Process.getThreadPriority(var1));
        } else {
//            c.a(a, "setHighPriority failed thread is dead");
        }
    }

    public boolean checkInHighPriority() {
        boolean var1 = true;
        if(this.d != null && this.d.isAlive()) {
            int var2 = this.d.getThreadId();

            try {
                var1 = Process.getThreadPriority(var2) == -8;
            } catch (Exception var3) {
//                c.a(a, "thread:" + var2 + "  check inHighPriority failed");
            }

            return var1;
        } else {
//            c.a(a, "check inHighPriority failed thread is dead");
            return false;
        }
    }

    public void setLowPriority() {
        if(this.d != null && this.d.isAlive()) {
            int var1 = this.d.getThreadId();

            try {
                if(Process.getThreadPriority(var1) == 0) {
//                    c.d(a, "setLowPriority No Need.");
                    return;
                }

                Process.setThreadPriority(var1, 0);
            } catch (Exception var2) {
//                c.a(a, "thread: " + var1 + " setLowPriority failed");
                return;
            }

//            c.d(a, "thread:" + var1 + " setLowPriority to" + Process.getThreadPriority(var1));
        } else {
//            c.a(a, "setLowPriority failed thread is dead");
        }
    }

    public static boolean isMainThread() {
        return Thread.currentThread().getId() != Looper.getMainLooper().getThread().getId();
    }

    public Handler getTheadHandler() {
        if(this.c == null) {
            this.c = new Handler(this.d.getLooper());
        }

        return this.c;
    }

    public final Looper getLooper() {
        return this.d.getLooper();
    }

    public boolean isRunnOnThead() {
        return Thread.currentThread().getId() != this.d.getId();
    }

    public int postRunnOnThead(Runnable var1) {
        if(var1 == null) {
            return -1;
        } else {
            this.getTheadHandler().post(var1);
            return 0;
        }
    }
}
