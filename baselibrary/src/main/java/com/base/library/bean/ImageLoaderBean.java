package com.base.library.bean;

import java.io.Serializable;

/**
 * Created by wangdongyi on 2017/2/7.
 * 图片加载默认项
 */

public class ImageLoaderBean implements Serializable {
    private int default_user_avatar_EmptyUri;//用户默认图片
    private int default_user_avatar_Fail;//用户默认图片
    private int default_user_avatar_Loading;//用户默认图片
    private int user_radian;//100是圆
    private int default_radian;//100是圆
    private int default_picture_EmptyUri;//默认加载图片
    private int default_picture_Fail;//默认加载图片
    private int default_picture_Loading;//默认加载图片

    public int getDefault_radian() {
        return default_radian;
    }

    public void setDefault_radian(int default_radian) {
        this.default_radian = default_radian;
    }

    public int getDefault_user_avatar_EmptyUri() {
        return default_user_avatar_EmptyUri;
    }

    public void setDefault_user_avatar_EmptyUri(int default_user_avatar_EmptyUri) {
        this.default_user_avatar_EmptyUri = default_user_avatar_EmptyUri;
    }

    public int getDefault_user_avatar_Fail() {
        return default_user_avatar_Fail;
    }

    public void setDefault_user_avatar_Fail(int default_user_avatar_Fail) {
        this.default_user_avatar_Fail = default_user_avatar_Fail;
    }

    public int getDefault_user_avatar_Loading() {
        return default_user_avatar_Loading;
    }

    public void setDefault_user_avatar_Loading(int default_user_avatar_Loading) {
        this.default_user_avatar_Loading = default_user_avatar_Loading;
    }

    public int getUser_radian() {
        return user_radian;
    }

    public void setUser_radian(int user_radian) {
        this.user_radian = user_radian;
    }

    public int getDefault_picture_EmptyUri() {
        return default_picture_EmptyUri;
    }

    public void setDefault_picture_EmptyUri(int default_picture_EmptyUri) {
        this.default_picture_EmptyUri = default_picture_EmptyUri;
    }

    public int getDefault_picture_Fail() {
        return default_picture_Fail;
    }

    public void setDefault_picture_Fail(int default_picture_Fail) {
        this.default_picture_Fail = default_picture_Fail;
    }

    public int getDefault_picture_Loading() {
        return default_picture_Loading;
    }

    public void setDefault_picture_Loading(int default_picture_Loading) {
        this.default_picture_Loading = default_picture_Loading;
    }
}
