package com.wdy.project;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.library.activity.BaseActivity;
import com.base.library.application.BaseApplication;
import com.base.library.listen.NoDoubleClickListener;
import com.base.library.listen.OnPermissionListen;
import com.base.library.okHtttpUtil.GenericsCallback;
import com.base.library.okHtttpUtil.OkHttpUtil;
import com.base.library.permission.PermissionsManager;
import com.base.library.preview.PhotoPreviewUtil;
import com.base.library.util.TxtReadUtil;
import com.base.library.util.WDYJsonUtil;
import com.base.library.util.WDYLog;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import okhttp3.Request;


public class MainActivity extends BaseActivity{
    TextView tv;
    String content = "/sdcard/HUDSDKLog.txt";
    private ImageView sample_image;
    private TextView sample_text;
    private ImageView sample_image2;
    private ArrayList<String> list = new ArrayList<>();

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
        PermissionsManager.with(this, new OnPermissionListen() {
            @Override
            public void callBack(boolean isHave) {
                if (isHave) {
                } else {
                    BaseApplication.getToastUtil().showMiddleToast("您没有开启权限");
                }
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA);

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
        Glide.with(MainActivity.this).load("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-11-18380166_305443499890139_8426655762360565760_n.jpg").asBitmap().into(sample_image);
        sample_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preview();
//                UpPhotoView.getInstance().with(MainActivity.this, new UpPhotoView.onBackPath() {
//                    @Override
//                    public void path(String Path) {
//                        try {
//                            sample_image.setImageBitmap(CodeUtil.convertBitmap(Path));
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
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
                //选择车辆
//                for (int i = 0; i < 5; i++) {
//                    list.add("车辆"+i);
//                }
//                SingleSelectedDialog.getInstance().with(MainActivity.this, list, 2, new SingleSelectedDialog.selected() {
//                    @Override
//                    public void handle(String selected) {
//
//                    }
//                });
            }
        });
    }

    private void preview() {
        ArrayList<String> sourceImageList = new ArrayList<>();
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-12-18380140_455327614813449_854681840315793408_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-11-18380166_305443499890139_8426655762360565760_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-10-18382517_1955528334668679_3605707761767153664_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-09-18443931_429618670743803_5734501112254300160_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-08-18252341_289400908178710_9137908350942445568_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-05-18251898_1013302395468665_8734429858911748096_n.jpg");
        sourceImageList.add("http://ww1.sinaimg.cn/large/61e74233ly1feuogwvg27j20p00zkqe7.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-02-926821_1453024764952889_775781470_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-28-18094719_120129648541065_8356500748640452608_n.jpg");
        sourceImageList.add("https://ws1.sinaimg.cn/mw690/610dc034ly1ffwb7npldpj20u00u076z.jpg");
        sourceImageList.add("https://ws1.sinaimg.cn/large/610dc034ly1ffv3gxs37oj20u011i0vk.jpg");
        sourceImageList.add("https://ws1.sinaimg.cn/large/610dc034ly1fftusiwb8hj20u00zan1j.jpg");
        sourceImageList.add("http://ww1.sinaimg.cn/large/610dc034ly1ffmwnrkv1hj20ku0q1wfu.jpg");
        sourceImageList.add("https://ws1.sinaimg.cn/large/610dc034ly1ffyp4g2vwxj20u00tu77b.jpg");
        sourceImageList.add("https://ws1.sinaimg.cn/large/610dc034ly1ffxjlvinj5j20u011igri.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-11-17881546_248332202297978_2420944671002853376_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-12-17662441_1675934806042139_7236493360834281472_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-13-17882785_926451654163513_7725522121023029248_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-14-17881962_1329090457138411_8289893708619317248_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-16-17934400_1738549946443321_2924146161843437568_n.jpg");
        PhotoPreviewUtil.movePhotoPreview(MainActivity.this, sample_image, sourceImageList, 1);
    }
}
