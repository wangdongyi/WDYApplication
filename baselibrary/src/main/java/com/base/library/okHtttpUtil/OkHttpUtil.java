package com.base.library.okHtttpUtil;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.base.library.application.BaseApplication;
import com.base.library.lifeManagerUtil.LifeListener;
import com.base.library.lifeManagerUtil.LifeManager;
import com.base.library.volleyUtil.NetUtil;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.manager.RequestManagerRetriever;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：王东一
 * 创建时间：2017/6/27.
 */

public class OkHttpUtil {

    public static void with(Activity activity, Request request, final OkHttpListener okHttpListener) {
        if (NetUtil.getNetWorkType(BaseApplication.getInstance()) == 0) {
            BaseApplication.getToastUtil().showMiddleToast("当前无网络");
            return;
        }
        if (!okHttpListener.onBefore()) {
            return;
        }
        File sdcache = activity.getExternalCacheDir();
        int cacheSize = 10 * 1024 * 1024;
        assert sdcache != null;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
        OkHttpClient okHttpClient = builder.build();
        Call call = okHttpClient.newCall(request);
        final Call finalCall = call;
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                okHttpListener.onError(e.toString());
                okHttpListener.onFinish();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    okHttpListener.onSuccess(response.toString());
                    okHttpListener.onFinish();
                } else {
                    okHttpListener.onError(response.networkResponse().toString());
                    okHttpListener.onFinish();
                }
            }
        });
        LifeManager.getInstance().ObserveAct(activity, new LifeListener() {
            @Override
            public void onCreate(Bundle bundle) {
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onResume() {
            }

            @Override
            public void onPause() {
            }

            @Override
            public void onStop() {
            }

            @Override
            public void onDestroy() {
                okHttpListener.onCancel();
                finalCall.cancel();
            }
        });
    }

    public static void with(android.support.v4.app.Fragment fragment, Request request, final OkHttpListener okHttpListener) {
        if (NetUtil.getNetWorkType(BaseApplication.getInstance()) == 0) {
            BaseApplication.getToastUtil().showMiddleToast("当前无网络");
            return;
        }
        if (!okHttpListener.onBefore()) {
            return;
        }
        File sdcache = fragment.getActivity().getExternalCacheDir();
        int cacheSize = 10 * 1024 * 1024;
        assert sdcache != null;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
        OkHttpClient okHttpClient = builder.build();
        Call call = okHttpClient.newCall(request);
        final Call finalCall = call;
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                okHttpListener.onError(e.toString());
                okHttpListener.onFinish();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    okHttpListener.onSuccess(response.toString());
                    okHttpListener.onFinish();
                } else {
                    okHttpListener.onError(response.networkResponse().toString());
                    okHttpListener.onFinish();
                }
            }
        });
        LifeManager.getInstance().ObserveAct(fragment, new LifeListener() {
            @Override
            public void onCreate(Bundle bundle) {
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onResume() {
            }

            @Override
            public void onPause() {
            }

            @Override
            public void onStop() {
            }

            @Override
            public void onDestroy() {
                okHttpListener.onCancel();
                finalCall.cancel();
            }
        });
    }

    public static void with(Fragment fragment, Request request, final OkHttpListener okHttpListener) {
        if (NetUtil.getNetWorkType(BaseApplication.getInstance()) == 0) {
            BaseApplication.getToastUtil().showMiddleToast("当前无网络");
            return;
        }
        if (!okHttpListener.onBefore()) {
            return;
        }
        File sdcache = fragment.getActivity().getExternalCacheDir();
        int cacheSize = 10 * 1024 * 1024;
        assert sdcache != null;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
        OkHttpClient okHttpClient = builder.build();
        Call call = okHttpClient.newCall(request);
        final Call finalCall = call;
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                okHttpListener.onError(e.toString());
                okHttpListener.onFinish();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    okHttpListener.onSuccess(response.toString());
                    okHttpListener.onFinish();
                } else {
                    okHttpListener.onError(response.networkResponse().toString());
                    okHttpListener.onFinish();
                }
            }
        });
        LifeManager.getInstance().ObserveAct(fragment, new LifeListener() {
            @Override
            public void onCreate(Bundle bundle) {
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onResume() {
            }

            @Override
            public void onPause() {
            }

            @Override
            public void onStop() {
            }

            @Override
            public void onDestroy() {
                okHttpListener.onCancel();
                finalCall.cancel();
            }
        });
    }

    public static void with(View view, Request request, final OkHttpListener okHttpListener) {
        if (NetUtil.getNetWorkType(BaseApplication.getInstance()) == 0) {
            BaseApplication.getToastUtil().showMiddleToast("当前无网络");
            return;
        }
        if (!okHttpListener.onBefore()) {
            return;
        }
        File sdcache = view.getContext().getExternalCacheDir();
        int cacheSize = 10 * 1024 * 1024;
        assert sdcache != null;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
        OkHttpClient okHttpClient = builder.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                okHttpListener.onError(e.toString());
                okHttpListener.onFinish();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    okHttpListener.onSuccess(response.toString());
                    okHttpListener.onFinish();
                } else {
                    okHttpListener.onError(response.networkResponse().toString());
                    okHttpListener.onFinish();
                }
            }
        });
    }

    public static void with(Context context, Request request, final OkHttpListener okHttpListener) {
        if (NetUtil.getNetWorkType(BaseApplication.getInstance()) == 0) {
            BaseApplication.getToastUtil().showMiddleToast("当前无网络");
            return;
        }
        if (!okHttpListener.onBefore()) {
            return;
        }
        File sdcache = context.getExternalCacheDir();
        int cacheSize = 10 * 1024 * 1024;
        assert sdcache != null;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
        OkHttpClient okHttpClient = builder.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                okHttpListener.onError(e.toString());
                okHttpListener.onFinish();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    okHttpListener.onSuccess(response.toString());
                    okHttpListener.onFinish();
                } else {
                    okHttpListener.onError(response.networkResponse().toString());
                    okHttpListener.onFinish();
                }
            }
        });
    }
}
