package com.base.library.zxing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.base.library.R;
import com.base.library.util.CodeUtil;

/**
 * 作者：王东一 on 2016/6/1 14:11
 **/
public class ScanningMain extends Activity {
    private final static int SCANNIN_GREQUEST_CODE = 1;
    public static ScanningMain instence_scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanning_main);
        instence_scan = ScanningMain.this;
        Intent intent = new Intent();
        intent.setClass(ScanningMain.this, MipcaActivityCapture.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String str = bundle.getString("result");
                    String spStr[] = str.split(",");
                    Log.e("结果", str);
                    CodeUtil.showToastShort(ScanningMain.this, str + "");
                }
                break;
        }
    }


}