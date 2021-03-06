package com.wdy.project;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.base.library.activity.WDYBaseActivity;
import com.base.library.application.BaseApplication;
import com.base.library.listen.NoDoubleClickListener;
import com.base.library.listen.OnPermissionListen;
import com.base.library.okHtttpUtil.GenericsCallback;
import com.base.library.okHtttpUtil.OkHttpUtil;
import com.base.library.permission.PermissionsManager;
import com.base.library.preview.PhotoPreviewUtil;
import com.base.library.util.CodeUtil;
import com.base.library.util.CountDownUtil;
import com.base.library.util.DialogUtil;
import com.base.library.util.WDYJsonUtil;
import com.base.library.util.WDYLog;
import com.base.library.view.advertView.AdvertisementView;
import com.base.library.view.loading.CellularView;
import com.base.library.view.upPhotoView.UpPhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.util.ArrayList;

import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;


public class MainActivity extends WDYBaseActivity {
    TextView tv;
    String content = "/sdcard/HUDSDKLog.txt";
    private ImageView sample_image;
    private TextView sample_text;
    private ImageView sample_image2;
    private ArrayList<String> list = new ArrayList<>();
    private CellularView cellular;
    private AdvertisementView advertisementImageview;
    private TextView sampleText;
    private ImageView sampleImage;
    private ImageView sampleImage2;
    private ArrayList<String> sourceImageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
        MaterialRippleLayout.on(tv)
                .rippleColor(ContextCompat.getColor(this, R.color.top_color))
                .rippleAlpha(0.2f)
                .rippleHover(false)
                .create();
        tv.setOnClickListener(new NoDoubleClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            protected void onNoDoubleClick(View v) {
//                getRequest();
//                content = content + textFromJNI("/sdcard/HUDSDKLog.txt");
                getUserAgreement();
            }
        });
//        ArrayList<PermissionBean> list = new ArrayList<>();
//        list = (ArrayList<PermissionBean>) BaseApplication.getSharedPreferencesUtil().getList("list", list);
//
//        if (list.size() == 0) {
//            list.addAll(PermissionsList.getPermissionsList().getPermissionsArrayList());
//            BaseApplication.getSharedPreferencesUtil().saveList("list", list);
//            tv.setText("不存在这个界面");
//        } else {
//            tv.setText("存在这个界面" + list.size());
//        }

        setStatusBar(true);
//        PackageManager pm = getPackageManager();
//         boolean permission = (PackageManager.PERMISSION_GRANTED == pm.checkPermission(Manifest.permission.CALL_PHONE, "com.wdy.project"));
//         if (permission) {
//             BaseApplication.getToastUtil().showMiddleToast("有这个权限");
//             Intent intent = new Intent(Intent.ACTION_CALL);
//             Uri data = Uri.parse("tel:" + "10086");
//             intent.setData(data);
//             startActivity(intent);
//             }else {
//             BaseApplication.getToastUtil().showMiddleToast("木有这个权限");
//         }
//        Permissions4M.get(MainActivity.this)
//                // 是否强制弹出权限申请对话框，建议设置为 true，默认为 true
//                // .requestForce(true)
//                // 是否支持 5.0 权限申请，默认为 false
//                // .requestUnderM(false)
//                // 权限，单权限申请仅只能填入一个
//                .requestPermissions(Manifest.permission.CALL_PHONE)
//                // 权限码
//                .requestCodes(100)
//                // 如果需要使用 @PermissionNonRationale 注解的话，建议添加如下一行
//                // 返回的 intent 是跳转至**系统设置页面**
//                // .requestPageType(Permissions4M.PageType.MANAGER_PAGE)
//                // 返回的 intent 是跳转至**手机管家页面**
//                // .requestPageType(Permissions4M.PageType.ANDROID_SETTING_PAGE)
//                .request();
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            BaseApplication.getToastUtil().showMiddleToast("您没有开启权限");
//            return;
//        }else {
//            Intent intent = new Intent(Intent.ACTION_CALL);
//            Uri data = Uri.parse("tel:" + "10086");
//            intent.setData(data);
//            startActivity(intent);
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            PermissionsChecker mChecker = new PermissionsChecker(this);
//            if (mChecker.lacksPermissions(Manifest.permission.CALL_PHONE)) {
//                BaseApplication.getToastUtil().showMiddleToast("您没有开启权限");
//            } else {
//                BaseApplication.getToastUtil().showMiddleToast("您开启权限");
//
//            }
//        } else
//            BaseApplication.getToastUtil().showMiddleToast("小于6.0");

        PermissionsManager.with(MainActivity.this, new OnPermissionListen() {
            @Override
            public void callBack(boolean isHave) {
                if (isHave) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    Uri data = Uri.parse("tel:" + "10086");
                    intent.setData(data);
                } else {
                    BaseApplication.getToastUtil().showMiddleToast("您没有开启权限");
                }
            }
        }, Manifest.permission.CALL_PHONE);
        getUserAgreement();

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
                BaseApplication.getSharedPreferencesUtil().saveBean("RequestBean", response);
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

    private void getUserAgreement() {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        InputIdBean inputIdBean = new InputIdBean();
        inputIdBean.setId("sdada");
        RequestBody requestBody = RequestBody.create(JSON, WDYJsonUtil.toJson(inputIdBean));
        final Request request = new Request.Builder()
//                .addHeader("auth_token", "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiIxODY0MDUzMzYzNCIsImF1ZCI6InVzZXIiLCJleHAiOjE1MDYwNDk5MTgsImlhdCI6MTUwMzQ1NzkyMywianRpIjoiMTA4NjZiZTktNGJlNS00MWJjLThiNGUtNjc1YTI1YjRlMTg0In0.--a66ATqGuOYsf1mTAI7GEjXKHhcFvD-nW2XDHaVqOs")
                .addHeader("auth_token", ".eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiIxODY0MDUzMzYzNCIsImF1ZCI6InVzZXIiLCJleHAiOjE1MDYwNDk5MTgsImlhdCI6MTUwMzQ1NzkyMywianRpIjoiMTA4NjZiZTktNGJlNS00MWJjLThiNGUtNjc1YTI1YjRlMTg0In0.--a66ATqGuOYsf1mTAI7GEjXKHhcFvD-nW2XDHaVqOs")
                .post(requestBody)
                .tag(this)
                .url("http://172.16.175.18:8501/user/cancelTrip")
                .build();
        OkHttpUtil.with(this, request, new GenericsCallback<AboutUsBean>() {

            @Override
            public void onResponse(AboutUsBean response) {
                switch (response.getStatus()) {
                    case "SUCCESS":
                        WDYLog.d("suceess", response.getData().getContent());
                        break;
                    case "ERROR":
                        BaseApplication.getToastUtil().showMiddleToast(response.getMsg());
                        break;
                }
            }

            @Override
            public boolean onBefore() {
                DialogUtil.show(MainActivity.this);
                return true;
            }

            @Override
            public void onRequest(String response) {
                WDYLog.e("onResponse", response);
            }

            @Override
            public void onError(String msg) {
                WDYLog.e("错误", msg);
            }

            @Override
            public void onCancel() {
                DialogUtil.dismiss();
            }

            @Override
            public void onFinish() {
                DialogUtil.dismiss();
            }
        });
    }

    public native String stringFromJNI();

    static {
        System.loadLibrary("native-lib");
    }

    private void initView() {
        sample_image = (ImageView) findViewById(R.id.sample_image);
        MultiTransformation multi = new MultiTransformation(new BlurTransformation(25), new CircleCrop());
//        Glide.with(MainActivity.this)
//                .load("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-11-18380166_305443499890139_8426655762360565760_n.jpg")
//                .apply(bitmapTransform(multi))
//                .into(sample_image);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(com.base.library.R.drawable.default_picture)
                .error(com.base.library.R.drawable.default_picture)
                .transforms(multi)
                .priority(Priority.HIGH);
        Glide.with(this).load("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-11-18380166_305443499890139_8426655762360565760_n.jpg").apply(options).into(sample_image);
        sample_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                CountDownUtil.getInstance().cancel();
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

//                RequestBean requestBean = BaseApplication.getSharedPreferencesUtil().getBean("RequestBean", RequestBean.class);
//                tv.setText(requestBean.getData().getCity());
            }
        });
        sample_text = (TextView) findViewById(R.id.sample_text);
        sample_image2 = (ImageView) findViewById(R.id.sample_image2);
        sample_image2.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                CountDownUtil.getInstance().with(5000, new CountDownUtil.CountDownListen() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTick(long time) {
                        tv.setText(time / 1000 + "存在这个界面");
                    }

                    @Override
                    public void onFinish() {

                    }
                });
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

        advertisementImageview = (AdvertisementView) findViewById(R.id.advertisement_imageview);
        initAdvertisement();
    }

    public void clickRecycleTest(View view) {
        Intent intent = new Intent(MainActivity.this, RecycleTestActivity.class);
        startActivity(intent);
    }

    private void initAdvertisement() {
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
        advertisementImageview.init(sourceImageList);
        advertisementImageview.setAutoPlay(true);
        advertisementImageview.setOnItemClick(new AdvertisementView.onItemClick() {
            @Override
            public void click(int position) {
                PhotoPreviewUtil.movePhotoPreview(MainActivity.this, advertisementImageview, sourceImageList, 1);
            }
        });
    }

}
