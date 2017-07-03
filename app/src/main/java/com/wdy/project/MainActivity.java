package com.wdy.project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.base.library.activity.BaseActivity;
import com.base.library.listen.NoDoubleClickListener;
import com.base.library.okHtttpUtil.GenericsCallback;
import com.base.library.okHtttpUtil.OkHttpListener;
import com.base.library.okHtttpUtil.OkHttpUtil;
import com.base.library.photopicker.utils.PhotoUtils;
import com.base.library.util.JsonUtil;
import com.base.library.util.TxtReadUtil;
import com.base.library.util.WDYLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends BaseActivity {
    TextView tv;
    String content = "/sdcard/HUDSDKLog.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Example of a call to a native method
        tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
        setTitleBackground(R.drawable.title_background);

        tv.setOnClickListener(new NoDoubleClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            protected void onNoDoubleClick(View v) {
                getRequest();
//                content = content + textFromJNI("/sdcard/HUDSDKLog.txt");
                tv.setText(TxtReadUtil.TxtRead(content));
            }
        });
    }

    public native String textFromJNI(String path);

    private void getRequest() {
        final Request request = new Request.Builder()
                .get()
                .tag(this)
                .url("http://wthrcdn.etouch.cn/weather_mini?citykey=101010100")
                .build();
        OkHttpUtil.with(this, request, new GenericsCallback<RequestBean>() {

            @Override
            public void onResponse(RequestBean response) {
                String log = JsonUtil.toJson(response);
                for (int i = 0; i < 10; i++) {
                    log = log + log;
                }
//                WDYLog.i("泛型:dddd", log);
            }

            @Override
            public boolean onBefore() {
                return true;
            }

            @Override
            public void onRequest(String response) {
//                JniLog("回掉:" + response);
            }

            @Override
            public void onError(String msg) {
//                JniLog("错误:" + msg);
            }

            @Override
            public void onCancel() {
                Log.i("onSuccess", "取消:");
            }

            @Override
            public void onFinish() {
                Log.i("onSuccess", "结束:");
            }


        });
    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

//    public native int dataFromJNI(int bufID, int priority, String tag, String msg);

//    public native void JniLog(String message);

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PhotoUtils.onPhotoResult(resultCode, data, new PhotoUtils.onPhotoBack() {
            @Override
            public void onBack(ArrayList<String> result) {

            }
        });
    }


}
