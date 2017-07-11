package com.base.library.lifeManagerUtil;

import android.content.Intent;

/**
 * 作者：王东一
 * 创建时间：2017/7/7.
 */

public interface OnActivityResultListener extends LifeListener {
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
