package com.base.library.bean;

import java.io.Serializable;

/**
 * Created by wangdongyi on 2017/2/7.
 * 样式设置
 */

public class ThemBean implements Serializable {
    private int statusBarColor;//状态栏颜色
    private int titleBackground;//标题颜色
    private int backMipmap;//返回箭头
    private int titleTextColor;//标题颜色
    private boolean statusBarDark = false;//状态栏文字颜色

    public boolean isStatusBarDark() {
        return statusBarDark;
    }

    public void setStatusBarDark(boolean statusBarDark) {
        this.statusBarDark = statusBarDark;
    }

    public int getTitleTextColor() {
        return titleTextColor;
    }

    public void setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
    }

    public int getStatusBarColor() {
        return statusBarColor;
    }

    public void setStatusBarColor(int statusBarColor) {
        this.statusBarColor = statusBarColor;
    }

    public int getTitleBackground() {
        return titleBackground;
    }

    public void setTitleBackground(int titleBackground) {
        this.titleBackground = titleBackground;
    }

    public int getBackMipmap() {
        return backMipmap;
    }

    public void setBackMipmap(int backMipmap) {
        this.backMipmap = backMipmap;
    }

}
