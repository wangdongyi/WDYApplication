package com.base.library.lifeManagerUtil;

import android.os.Bundle;

/**
 * 作者：王东一
 * 创建时间：2017/6/28.
 */

public interface LifeListener {
    public void onCreate(Bundle bundle);

    public void onStart();

    public void onResume();

    public void onPause();

    public void onStop();

    public void onDestroy();
}
