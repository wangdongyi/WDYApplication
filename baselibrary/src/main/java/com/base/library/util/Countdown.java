package com.base.library.util;

import android.os.CountDownTimer;

/**
 * 作者：王东一 on 2016/3/28 14:34
 **/
public class Countdown extends CountDownTimer {
    private IsFinishListener isFinishListener;

    public Countdown(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {

    }

    @Override
    public void onFinish() {
        this.isFinishListener.FinishChange();
    }

    // 实现该接口,使用全部被选择
    public interface IsFinishListener {

        void FinishChange();
    }

    public void setIsFinishListener(IsFinishListener isFinishListener) {
        this.isFinishListener = isFinishListener;
    }
}
