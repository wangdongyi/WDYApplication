package com.base.library.util;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;

/**
 * 作者：王东一
 * 创建时间：2017/9/5.
 */

public class CountDownUtil {
    private static CountDownUtil mInstance;
    private CountDownTimer timer;//验证码定时器
    private boolean isTask = false;//定时器运行状态

    public static CountDownUtil getInstance() {
        if (mInstance == null) {
            synchronized (CountDownUtil.class) {
                if (mInstance == null) {
                    mInstance = new CountDownUtil();
                }
            }
        }
        return mInstance;
    }

    public void with(int time, final CountDownListen countDownListen) {
        if (!isTask) {
            //编辑定时器
            timer = new CountDownTimer(time + 1050, 1000) {
                @SuppressLint("SetTextI18n")
                @Override
                public void onTick(long millisUntilFinished) {
                    countDownListen.onTick(millisUntilFinished);
                }

                @Override
                public void onFinish() {
                    countDownListen.onFinish();
                    isTask = false;
                }
            };
            timer.start();
            isTask = true;
        }
    }

    public interface CountDownListen {
        void onTick(long time);

        void onFinish();
    }
}
