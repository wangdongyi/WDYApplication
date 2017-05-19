package com.base.library.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.library.R;
import com.base.library.util.AnimatorUtils;
import com.base.library.util.MethodInvoke;
import com.base.library.util.SDKVersionUtils;
import com.base.library.util.SupportSwipeModeUtils;
import com.base.library.util.SwipeActivityManager;
import com.base.library.view.relativeLayout.SwipeBackLayout;

//Created by 王东一 on 2016/9/9.
public class WDYActivity extends AppCompatActivity implements SwipeActivityManager.SwipeListener, SwipeBackLayout.OnSwipeGestureDelegate {
    private static final String TAG = "WDY.WDYActivity";
    protected boolean canSwipe = true;
    public SwipeBackLayout mSwipeBackLayout;
    public boolean mOnDragging;
    private WindowAnimation mAnimation = new WindowAnimation();

    public boolean isCanSwipe() {
        return canSwipe;
    }

    public void setCanSwipe(boolean canSwipe) {
        this.canSwipe = canSwipe;
    }

    private void onStartActivityAction(Intent intent) {
        if (intent == null) {
            super.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
            return;
        }
        String className = null;
        ComponentName component = intent.getComponent();
        if (component != null) {
            className = component.getClassName();
            if (!(className.startsWith(component.getPackageName()))) {
                className = component.getPackageName() + component.getClassName();
            }
        } else {
            return;
        }
        if ((0x2 & MethodInvoke.getTransitionValue(className)) != 0) {
            super.overridePendingTransition(mAnimation.openEnter, mAnimation.openExit);
            return;
        }

        if ((0x4 & MethodInvoke.getTransitionValue(className)) != 0) {
            MethodInvoke.startTransitionNotChange(this);
            return;
        }
        MethodInvoke.startTransitionPopin(this);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public boolean onActivityCreate() {
        if (isSupperSwipe()) {
            ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView();
            mSwipeBackLayout = (SwipeBackLayout) LayoutInflater.from(this).inflate(R.layout.swipeback_layout, viewGroup, false);
            mSwipeBackLayout.init();
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getWindow().getDecorView().setBackground(null);
            ViewGroup childAtView = (ViewGroup) viewGroup.getChildAt(0);
            childAtView.setBackgroundColor(ContextCompat.getColor(WDYActivity.this, R.color.transparent));
            viewGroup.removeView(childAtView);
            mSwipeBackLayout.addView(childAtView);
            mSwipeBackLayout.setContentView(childAtView);
            viewGroup.addView(this.mSwipeBackLayout);
            mSwipeBackLayout.setSwipeGestureDelegate(this);
            return true;
        }

        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mSwipeBackLayout != null) {
                mSwipeBackLayout.setEnableGesture(false);
            }
            if (!isFinishing()) {
                SwipeActivityManager.pushCallback(this);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            SwipeActivityManager.popCallback(this);
            if (mSwipeBackLayout != null) {
                mSwipeBackLayout.setEnableGesture(isCanSwipe());
                mSwipeBackLayout.mScrolling = false;
            }
        }
    }

    private boolean isSupperSwipe() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return false;
        }
        if (SupportSwipeModeUtils.isEnable()) {
            if (isEnableSwipe()) {
                return true;
            }
        }
        return false;
    }


    protected boolean isEnableSwipe() {
        return isCanSwipe();
    }

    @Override
    public void onScrollParent(float scrollPercent) {
        View decorView = getWindow().getDecorView();
        if ((decorView instanceof ViewGroup) && (((ViewGroup) decorView).getChildCount() > 0)) {
            decorView = ((ViewGroup) decorView).getChildAt(0);
        }
        if (Float.compare(1.0F, scrollPercent) <= 0) {
            AnimatorUtils.startViewAnimation(decorView, 0.0F);
            return;
        }
        AnimatorUtils.startViewAnimation(decorView, -1.0F * decorView.getWidth() / 4 * (1.0F - scrollPercent));
    }

    @Override
    public void notifySettle(boolean open, int speed) {
        View decorView = getWindow().getDecorView();
        if ((decorView instanceof ViewGroup) && (((ViewGroup) decorView).getChildCount() > 0)) {
            decorView = ((ViewGroup) decorView).getChildAt(0);
        }
        long duration = 120L;
        if (speed <= 0) {
            duration = 240L;
        }
        if (open) {
            AnimatorUtils.updateViewAnimation(decorView, duration, 0.0F, null);
            return;
        }
        AnimatorUtils.updateViewAnimation(decorView, duration, -1 * decorView.getWidth() / 4, null);
    }

    @Override
    public boolean isEnableGesture() {
        return false;
    }

    @Override
    public void onSwipeBack() {
        if (!(isFinishing())) {
            finish();
        }
        mOnDragging = false;
    }

    @Override
    public void onDragging() {
        mOnDragging = true;
    }

    @Override
    public void onCancel() {
        mOnDragging = false;
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (isSupperSwipe() && event.getKeyCode() == KeyEvent.KEYCODE_BACK && mSwipeBackLayout.isSwipeBacking()) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void finish() {
        super.finish();
        if (isEnableSwipe()) {
            SwipeActivityManager.notifySwipe(1.0F);
        }
        super.overridePendingTransition(mAnimation.closeEnter, mAnimation.closeExit);
        if ((0x2 & MethodInvoke.getAnnotationValue(super.getClass())) == 0) {
            return;
        }
        if ((0x4 & MethodInvoke.getAnnotationValue(super.getClass())) != 0) {
            MethodInvoke.startTransitionNotChange(this);
            return;
        }
        MethodInvoke.startTransitionPopout(this);

    }

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return;
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void startActivities(Intent[] intents) {
        super.startActivities(intents);
        onStartActivityAction(null);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void startActivities(Intent[] intents, Bundle bundle) {
        super.startActivities(intents, bundle);
        onStartActivityAction(null);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        onStartActivityAction(intent);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void startActivity(Intent intent, Bundle bundle) {
        super.startActivity(intent, bundle);
        onStartActivityAction(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        onStartActivityAction(intent);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        onStartActivityAction(intent);
    }


    public static class WindowAnimation {
        public static int activityOpenEnterAnimation;
        public static int activityOpenExitAnimation;
        public static int activityCloseEnterAnimation;
        public static int activityCloseExitAnimation;

        public int openEnter = activityOpenEnterAnimation;
        public int openExit = activityOpenExitAnimation;
        public int closeEnter = activityCloseEnterAnimation;
        public int closeExit = activityCloseExitAnimation;

        static {
            if (!(SDKVersionUtils.isSmallerVersion(19) && SupportSwipeModeUtils.isEnable())) {
                activityOpenEnterAnimation = R.anim.slide_right_in;
                activityOpenExitAnimation = R.anim.slide_left_out;
                activityCloseEnterAnimation = R.anim.slide_left_in;
                activityCloseExitAnimation = R.anim.slide_right_out;
            } else {
//                activityOpenEnterAnimation = R.anim.pop_in;
//                activityOpenExitAnimation = R.anim.anim_not_change;
//                activityCloseEnterAnimation = R.anim.anim_not_change;
//                activityCloseExitAnimation = R.anim.pop_out;
            }
        }
    }
}
