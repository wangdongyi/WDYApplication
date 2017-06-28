package com.base.library.lifeManagerUtil;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Looper;

/**
 * 作者：王东一
 * 创建时间：2017/6/28.
 */

public class LifeManager {
    private static final String FRAGMENT_TAG = "ActLifeListenerFragment";

    private static volatile LifeManager mInstance;

    private LifeManager() {

    }

    public static LifeManager getInstance() {
        if (null == mInstance) {
            synchronized (LifeManager.class) {
                if (null == mInstance) {
                    mInstance = new LifeManager();
                }
            }
        }
        return mInstance;
    }


    /**
     * 开始监听生命周期
     */
    public void ObserveAct(Activity activity, LifeListener Listener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
            throw new IllegalArgumentException("You cannot start a observe for a destroyed activity");
        }
        if (!(Looper.myLooper() == Looper.getMainLooper())) {
            throw new IllegalArgumentException("You must start a observe on mainThread");
        }
        android.app.FragmentManager fm = activity.getFragmentManager();
        SupportActLifeListenerFragment fragment = findFragment(fm, Listener);//找到绑定的Fragment
        LifeListenerManager manager = findLifeListenerManager(fragment);//找到指定Fragment的ActLifeListenerManager
        manager.addLifeListener(Listener);//添加监听
    }

    /**
     * 开始监听生命周期
     */
    public void ObserveAct(android.support.v4.app.Fragment fragment, LifeListener Listener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && fragment.getActivity().isDestroyed()) {
            throw new IllegalArgumentException("You cannot start a observe for a destroyed activity");
        }
        if (!(Looper.myLooper() == Looper.getMainLooper())) {
            throw new IllegalArgumentException("You must start a observe on mainThread");
        }
        android.app.FragmentManager fm = fragment.getActivity().getFragmentManager();
        SupportActLifeListenerFragment supportFragment = findFragment(fm, Listener);//找到绑定的Fragment
        LifeListenerManager manager = findLifeListenerManager(supportFragment);//找到指定Fragment的ActLifeListenerManager
        manager.addLifeListener(Listener);//添加监听
    }
    /**
     * 开始监听生命周期
     */
    public void ObserveAct(Fragment fragment, LifeListener Listener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && fragment.getActivity().isDestroyed()) {
            throw new IllegalArgumentException("You cannot start a observe for a destroyed activity");
        }
        if (!(Looper.myLooper() == Looper.getMainLooper())) {
            throw new IllegalArgumentException("You must start a observe on mainThread");
        }
        android.app.FragmentManager fm = fragment.getActivity().getFragmentManager();
        SupportActLifeListenerFragment supportFragment = findFragment(fm, Listener);//找到绑定的Fragment
        LifeListenerManager manager = findLifeListenerManager(supportFragment);//找到指定Fragment的ActLifeListenerManager
        manager.addLifeListener(Listener);//添加监听
    }
    /**
     * 找到用于监听生命周期的空白的Fragment
     */
    private SupportActLifeListenerFragment findFragment(FragmentManager fm, LifeListener actListener) {
        SupportActLifeListenerFragment current = (SupportActLifeListenerFragment) fm.findFragmentByTag(FRAGMENT_TAG);
        if (current == null) {//没有找到，则新建
            current = new SupportActLifeListenerFragment();
            fm.beginTransaction().add(current, FRAGMENT_TAG).commitAllowingStateLoss();//添加Fragment
        }
        return current;
    }

    /**
     * 用于获取管理对应Fragment的ActLifeListenerManager
     *
     * @param fragment
     * @return
     */
    private LifeListenerManager findLifeListenerManager(SupportActLifeListenerFragment fragment) {
        LifeListenerManager manager = fragment.getLifeListenerManager();
        if (null == manager) {
            manager = new LifeListenerManager();
        }
        fragment.setActLifeListenerManager(manager);
        return manager;
    }

}
