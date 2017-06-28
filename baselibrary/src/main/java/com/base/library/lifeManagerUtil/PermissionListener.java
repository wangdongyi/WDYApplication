package com.base.library.lifeManagerUtil;

import android.support.annotation.NonNull;

/**
 * 作者：王东一
 * 创建时间：2017/6/28.
 * 用于监听权限的回调
 */

public interface PermissionListener extends LifeListener {
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
}
