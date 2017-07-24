package com.wdy.project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.library.activity.BaseActivity;
import com.base.library.listen.NoDoubleClickListener;
import com.base.library.okHtttpUtil.GenericsCallback;
import com.base.library.okHtttpUtil.OkHttpUtil;
import com.base.library.util.CodeUtil;
import com.base.library.util.TxtReadUtil;
import com.base.library.util.WDYJsonUtil;
import com.base.library.util.WDYLog;
import com.base.library.view.upPhotoView.UpPhotoView;

import java.io.IOException;

import okhttp3.Request;


public class MainActivity extends BaseActivity {
    TextView tv;
    String content = "/sdcard/HUDSDKLog.txt";
    private ImageView sample_image;
    private TextView sample_text;
    private ImageView sample_image2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
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
        setStatusBar(true);
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
                String log = WDYJsonUtil.toJson(response);
                for (int i = 0; i < 10; i++) {
                    log = log + log;
                }
                WDYLog.i("泛型:dddd", log);
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

    public native String stringFromJNI();

    static {
        System.loadLibrary("native-lib");
    }

    private void initView() {
        sample_image = (ImageView) findViewById(R.id.sample_image);
        sample_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpPhotoView.getInstance().with(MainActivity.this, new UpPhotoView.onBackPath() {
                    @Override
                    public void path(String Path) {
                        try {
                            sample_image.setImageBitmap(CodeUtil.convertBitmap(Path));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        sample_text = (TextView) findViewById(R.id.sample_text);
        sample_image2 = (ImageView) findViewById(R.id.sample_image2);
        sample_image2.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
//                UpPhotoView.getInstance().with(MainActivity.this, new UpPhotoView.onBackPath() {
//                    @Override
//                    public void path(String Path) {
//                        try {
//                            sample_image2.setImageBitmap(CodeUtil.convertBitmap(Path));
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
                Intent intent = new Intent(MainActivity.this, NextActivity.class);
                startActivity(intent);
            }
        });
    }
}
