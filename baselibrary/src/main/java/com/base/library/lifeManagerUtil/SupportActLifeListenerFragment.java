package com.base.library.lifeManagerUtil;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

/**
 * 作者：王东一
 * 创建时间：2017/6/28.
 */

public class SupportActLifeListenerFragment extends Fragment {
    private LifeListenerManager listenerManager;

    public void setActLifeListenerManager(LifeListenerManager manager){
        listenerManager = manager;
    }

    public LifeListenerManager getLifeListenerManager(){
        return listenerManager;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        listenerManager.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        listenerManager.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        listenerManager.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        listenerManager.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listenerManager.onDestroy();
        listenerManager = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        listenerManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        listenerManager.onActivityResult(requestCode, resultCode, data);
    }
}
