package com.base.library.bean;

import java.io.Serializable;

/**
 * Created by wangdongyi on 2017/2/7.
 * 样式设置
 */

public class ThemBean implements Serializable {

    private boolean statusBarDark = false;//状态栏文字颜色

    public boolean isStatusBarDark() {
        return statusBarDark;
    }

    public void setStatusBarDark(boolean statusBarDark) {
        this.statusBarDark = statusBarDark;
    }

}
