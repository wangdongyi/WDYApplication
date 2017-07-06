package com.base.library.application;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.support.multidex.MultiDexApplication;
import android.view.View;
import android.widget.ImageView;

import com.base.library.R;
import com.base.library.bean.ImageLoaderBean;
import com.base.library.bean.ThemBean;
import com.base.library.util.CrashHandler;
import com.base.library.volleyUtil.VolleyUtil;
import com.base.library.util.CCPAppManager;
import com.base.library.util.CodeUtil;
import com.base.library.util.LogUtil;
import com.base.library.util.SharedPreferencesUtil;
import com.base.library.util.ToastUtil;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * 作者：王东一 on 2016/3/24 11:16
 **/
public class BaseApplication extends MultiDexApplication {
    private static BaseApplication instance;
    //正方形
    protected static DisplayImageOptions square_options;

    private static DisplayImageOptions optionsRectangular;

    private static DisplayImageOptions optionsHeadPortrait;
    //样式
    private static ThemBean themBean;
    //日志是否打开
    private static boolean isOpenLog = true;
    //
    @SuppressLint("StaticFieldLeak")
    private static VolleyUtil volleyUtil;
    @SuppressLint("StaticFieldLeak")
    private static ToastUtil toastUtil;
    private static SharedPreferencesUtil sharedPreferencesUtil;

    public static boolean isOpenLog() {
        return isOpenLog;
    }

    public static void setIsOpenLog(boolean isOpenLog) {
        BaseApplication.isOpenLog = isOpenLog;
    }

    public static VolleyUtil getVolleyUtil() {
        if (volleyUtil == null) {
            synchronized (VolleyUtil.class) {
                if (volleyUtil == null)
                    volleyUtil = new VolleyUtil(getInstance());
            }
        }
        return volleyUtil;
    }

    public static ToastUtil getToastUtil() {
        if (toastUtil == null) {
            synchronized (ToastUtil.class) {
                if (toastUtil == null)
                    toastUtil = new ToastUtil(getInstance());
            }
        }
        return toastUtil;
    }

    public static SharedPreferencesUtil getSharedPreferencesUtil() {
        if (sharedPreferencesUtil == null) {
            synchronized (SharedPreferencesUtil.class) {
                if (sharedPreferencesUtil == null)
                    sharedPreferencesUtil = new SharedPreferencesUtil(getInstance());
            }
        }
        return sharedPreferencesUtil;
    }

    public void showUserIcon(String url, ImageView imageView) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(url, imageView, getOptionsHeadPortrait(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_CROP);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }
        });
    }

    public void showPicture(String url, ImageView imageView) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(url, imageView, getOptionsRectangular(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (CodeUtil.isEmpty(imageUri)) {
                    ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                } else {
                    ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }
        });
    }
    //网络请求

    /**
     * 单例，返回一个实例
     *
     * @return
     */
    public static BaseApplication getInstance() {
        if (instance == null) {
            LogUtil.w("[ECApplication] instance is null.");
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initApp();
    }

    public void initApp() {
        instance = this;
        CCPAppManager.setContext(instance);
        initImageLoader();
        setOptionsHeadPortrait(this);
        setOptionsRectangular();
        setSquareOptions();
        setIsOpenLog(isApkInDebug(instance));
        if (!isApkInDebug(instance)) {
            startCrash();
        }
    }

    public void startCrash() {
        CrashHandler.getInstance().init(this);
    }

    public static ThemBean getThemBean() {
        if (themBean == null) {
            themBean = new ThemBean();
        }
        return themBean;
    }

    public static void setThemBean(ThemBean themBean) {
        BaseApplication.themBean = themBean;
    }

    protected void initImageLoader() {
        File cacheDir = StorageUtils.getOwnCacheDirectory(this, getPackageName() + "/Cache");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(this)
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCacheFileCount(100) //缓存的文件数量
                .diskCache(new UnlimitedDiskCache(cacheDir))//自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .build();//开始构建
        ImageLoader.getInstance().init(config);
    }

    public static DisplayImageOptions getOptionsHeadPortrait() {
        return optionsHeadPortrait;
    }

    //自定义ImageLoader默认图片
    public static void initImageLoader(ImageLoaderBean imageLoaderBean) {
        BaseApplication.optionsHeadPortrait = new DisplayImageOptions.Builder()
                .showImageOnFail(imageLoaderBean.getDefault_user_avatar_Fail())
                .showImageOnLoading(imageLoaderBean.getDefault_user_avatar_Loading())
                .showImageForEmptyUri(imageLoaderBean.getDefault_user_avatar_EmptyUri())
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示//设置图片的解码类型
                .displayer(new RoundedBitmapDisplayer(CodeUtil.dip2px(getInstance(), imageLoaderBean.getUser_radian())))//是否设置为圆角，弧度为多少
                .build();
        square_options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnFail(imageLoaderBean.getDefault_picture_Fail())
                .showImageOnLoading(imageLoaderBean.getDefault_picture_Loading())
                .showImageForEmptyUri(imageLoaderBean.getDefault_picture_EmptyUri())
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示
                .displayer(new RoundedBitmapDisplayer(CodeUtil.dip2px(getInstance(), imageLoaderBean.getDefault_radian())))//是否设置为圆角，弧度为多少
                .build();
    }

    public static void setOptionsHeadPortrait(Context context) {
        BaseApplication.optionsHeadPortrait = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.default_useravatar)
                .showImageOnLoading(R.drawable.default_useravatar)
                .showImageForEmptyUri(R.drawable.default_useravatar)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示//设置图片的解码类型
                .displayer(new RoundedBitmapDisplayer(CodeUtil.dip2px(context, 10)))//是否设置为圆角，弧度为多少
                .build();
    }

    public static DisplayImageOptions getOptionsRectangular() {
        return optionsRectangular;
    }

    public static void setOptionsRectangular() {
        BaseApplication.optionsRectangular = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnFail(R.drawable.failure)
                .showImageOnLoading(R.drawable.default_picture)
                .showImageForEmptyUri(R.drawable.default_picture)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .build();
    }

    public void setSquareOptions() {
        square_options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnFail(R.drawable.default_picture)
                .showImageOnLoading(R.drawable.default_picture)
                .showImageForEmptyUri(R.drawable.default_picture)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示
                .build();

    }

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    /**
     * 判断当前应用是否是debug状态
     */

    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }
}
