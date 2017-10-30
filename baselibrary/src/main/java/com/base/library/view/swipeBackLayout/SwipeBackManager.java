package com.base.library.view.swipeBackLayout;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceView;
import android.view.View;
import android.webkit.WebView;

import com.base.library.util.ActivityManage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 作者：王东一
 * 创建时间：2017/10/27.
 */

public class SwipeBackManager implements Application.ActivityLifecycleCallbacks {
    private static final SwipeBackManager sInstance = new SwipeBackManager();
    private Set<Class<? extends View>> mProblemViewClassSet = new HashSet<>();

    public static SwipeBackManager getInstance() {
        return sInstance;
    }

    private SwipeBackManager() {
    }

    public void init(Application application, List<Class<? extends View>> problemViewClassList) {
        application.registerActivityLifecycleCallbacks(this);
        mProblemViewClassSet.add(WebView.class);
        mProblemViewClassSet.add(SurfaceView.class);
        if (problemViewClassList != null) {
            mProblemViewClassSet.addAll(problemViewClassList);
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        ActivityManage.getInstance().addActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ActivityManage.getInstance().removeActivity(activity);
    }

    /**
     * 获取倒数第二个 Activity
     *
     * @return
     */
    @Nullable
    public Activity getPenultimateActivity(Activity currentActivity) {
        Activity activity = null;
        try {
            if (ActivityManage.getInstance().getActivityList().size() > 1) {
                activity = ActivityManage.getInstance().getActivityList().get(ActivityManage.getInstance().getActivityList().size() - 2);

                if (currentActivity.equals(activity)) {
                    int index = ActivityManage.getInstance().getActivityList().indexOf(currentActivity);
                    if (index > 0) {
                        // 处理内存泄漏或最后一个 Activity 正在 finishing 的情况
                        activity = ActivityManage.getInstance().getActivityList().get(index - 1);
                    } else if (ActivityManage.getInstance().getActivityList().size() == 2) {
                        // 处理屏幕旋转后 mActivityStack 中顺序错乱
                        activity = ActivityManage.getInstance().getActivityList().lastElement();
                    }
                }
            }
        } catch (Exception e) {
        }
        return activity;
    }

    /**
     * 滑动返回是否可用
     *
     * @return
     */
    public boolean isSwipeBackEnable() {
        return ActivityManage.getInstance().getActivityList().size() > 1;
    }

    /**
     * 某个 view 是否会导致滑动返回后立即触摸界面时应用崩溃
     *
     * @param view
     * @return
     */
    public boolean isProblemView(View view) {
        return mProblemViewClassSet.contains(view.getClass());
    }
}
