package com.base.library.lifeManagerUtil;

import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;

import static com.base.library.permission.PermissionsManager.PERMISSIONS_REQUEST_CODE;

/**
 * 作者：王东一
 * 创建时间：2017/6/28.
 */

public class SupportActLifeListenerFragment extends Fragment {
    private LifeListenerManager listenerManager;

    public void setActLifeListenerManager(LifeListenerManager manager) {
        listenerManager = manager;
    }

    public LifeListenerManager getLifeListenerManager() {
        return listenerManager;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void LifeRequestPermissions(final String[] array, final int code) {
        if (!isAdded()) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isAdded()) {
                        SupportActLifeListenerFragment.this.requestPermissions(array, code);
                    }
                }
            }, 500);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isAdded()) {
                SupportActLifeListenerFragment.this.requestPermissions(array, code);
            }
        }
    }

    public void LifeStartActivityResult(final Intent intent, final int code) {
        if (!isAdded()) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    SupportActLifeListenerFragment.this.startActivityForResult(intent, code);
                }
            }, 500);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isAdded()) {
                SupportActLifeListenerFragment.this.startActivityForResult(intent, code);
            }
        }
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        listenerManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        listenerManager.onActivityResult(requestCode, resultCode, data);

    }

}
