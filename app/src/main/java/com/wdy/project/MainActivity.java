package com.wdy.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.base.library.activity.BaseActivity;
import com.base.library.okHtttpUtil.OkHttpListener;
import com.base.library.okHtttpUtil.OkHttpUtil;
import com.base.library.photopicker.utils.PhotoUtils;
import com.base.library.preview.PhotoPreviewUtil;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends BaseActivity {


    TextView sampleText;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
        setTitleBackground(R.drawable.title_background);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRequest();
            }
        });
        final Request request = new Request.Builder()
                .get()
                .tag(this)
                .url("http://www.wooyun.org")
                .build();
        OkHttpUtil.with(this, request, new OkHttpListener() {
            @Override
            public boolean onBefore() {
                return true;
            }

            @Override
            public void onSuccess(String Request) {
                Log.i("WY", "打印GET响应的数据：" + Request);
            }

            @Override
            public void onError(String msg) {

            }

            @Override
            public void onCancel() {
                Log.i("WY", "打印GET响应的数据：取消" );
            }

            @Override
            public void onFinish() {

            }
        });
    }

    private void getRequest() {
        final Request request = new Request.Builder()
                .get()
                .tag(this)
                .url("http://www.wooyun.org")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.i("WY", "打印GET响应的数据：" + response.body().string());
                } else {
                    throw new IOException("Unexpected code " + response);
                }
            }
        });
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

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
