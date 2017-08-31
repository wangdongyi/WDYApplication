package com.base.library.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.base.library.application.BaseApplication;

import java.util.LinkedList;
import java.util.List;

/**
 * 作者：王东一 on 2016/3/21 16:56
 **/
public class ActivityManage {
    private List<AppCompatActivity> activityList = new LinkedList<AppCompatActivity>();
    private static ActivityManage instance;


    // 单例模式中获取唯一的MyApplication实例
    public static ActivityManage getInstance() {
        if (null == instance) {
            instance = new ActivityManage();
        }
        return instance;
    }

    // 添加Activity到容器中
    public void addActivity(AppCompatActivity activity) {
        activityList.add(activity);
    }

    // 添加Activity到容器中
    public void removeActivity(AppCompatActivity activity) {
        activityList.remove(activity);
    }

    public void finishActivity(Class<?>... activityClasses) {
        for (Class<?> ac : activityClasses) {
            for (Activity a : activityList) {
                if(ac.toString().equals(a.getClass().toString())){
                    a.finish();
                }
            }
        }
    }

    public boolean HaveActivity(Class<?>... activityClasses) {
        boolean have = false;
        for (Class<?> ac : activityClasses) {
            for (int i = 0; i < activityList.size(); i++) {
                if(ac.toString().equals(activityList.get(i).getClass().toString())){
                    have = true;
                }
            }
        }
        return have;
    }

    public boolean getTopActivity(Class<?>... activityClasses) {
        String topPackageName = "";
        ActivityManager activityManager = (ActivityManager) BaseApplication.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(1);
        ActivityManager.RunningTaskInfo taskInfo = tasks.get(0);
        topPackageName = taskInfo.topActivity.getClassName();
        if (topPackageName.equals(activityClasses[0].getName())) {
            return true;
        } else {
            return false;
        }
    }

    public void removeActivity() {
        for (AppCompatActivity activity : activityList) {
            activity.finish();
        }
    }

    // 遍历所有Activity并finish
    public void exit() {
        for (AppCompatActivity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }
}
