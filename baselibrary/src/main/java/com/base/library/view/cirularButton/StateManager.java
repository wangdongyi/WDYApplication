package com.base.library.view.cirularButton;

/**
 * 作者：王东一 on 2016/3/18 14:37
 **/
public class StateManager {
    private boolean mIsEnabled;
    private int mProgress;

    public StateManager(CircularProgressButton progressButton) {
        mIsEnabled = progressButton.isEnabled();
        mProgress = progressButton.getProgress();
    }

    public void saveProgress(CircularProgressButton progressButton) {
        mProgress = progressButton.getProgress();
    }

    public boolean isEnabled() {
        return mIsEnabled;
    }

    public int getProgress() {
        return mProgress;
    }

    public void checkState(CircularProgressButton progressButton) {
        if (progressButton.getProgress() != getProgress()) {
            progressButton.setProgress(progressButton.getProgress());
        } else if(progressButton.isEnabled() != isEnabled()) {
            progressButton.setEnabled(progressButton.isEnabled());
        }
    }
}
