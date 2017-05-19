package com.base.library.fragment;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 作者：王东一 on 2016/3/22 09:53
 **/
public abstract class BaseFragment extends Fragment {
    //懒加载
    protected boolean isViewShown = false;
    protected View main;

    protected void setContentView(LayoutInflater inflater, int layoutResID) {
        if (main == null) {
            this.main = inflater.inflate(layoutResID, null);
        }
    }

    //初始化代码
    protected abstract void init();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isViewShown = null != getView();
    }


    protected abstract void lazyLoad();


}
