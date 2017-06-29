package com.base.library.okHtttpUtil;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;

import com.base.library.application.BaseApplication;
import com.base.library.lifeManagerUtil.LifeListener;
import com.base.library.lifeManagerUtil.LifeManager;
import com.base.library.volleyUtil.NetUtil;

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
    private static OkHttpUtil mInstance;
    private OkHttpClient mOkHttpClient;
    private Platform platform;

    public static OkHttpUtil getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpUtil.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtil();
                }
            }
        }
        return mInstance;
    }

    private OkHttpUtil() {
        File sdcache = BaseApplication.getInstance().getExternalCacheDir();
        int cacheSize = 10 * 1024 * 1024;
        assert sdcache != null;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
        mOkHttpClient = builder.build();
        platform = Platform.get();
    }

    public static void with(Activity activity, Request request, final GenericsCallback genericsCallback) {
        if (NetUtil.getNetWorkType(BaseApplication.getInstance()) == 0) {
            BaseApplication.getToastUtil().showMiddleToast("当前无网络");
            return;
        }
        if (!genericsCallback.onBefore()) {
            return;
        }
        Call call = getInstance().mOkHttpClient.newCall(request);
        final Call finalCall = call;
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull final IOException e) {
                if (call.isCanceled()) {
                    return;
                }
                getInstance().platform.defaultCallbackExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        genericsCallback.onError(e.toString());
                        genericsCallback.onFinish();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                if (call.isCanceled()) {
                    return;
                }
                if (response.isSuccessful()) {
                    try {
                        final Object o = genericsCallback.parseNetworkResponse(response);
                        getInstance().platform.defaultCallbackExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                genericsCallback.onResponse(o);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getInstance().platform.defaultCallbackExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            genericsCallback.onFinish();
                        }
                    });

                } else {
                    getInstance().platform.defaultCallbackExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            genericsCallback.onError(response.networkResponse().toString());
                            genericsCallback.onFinish();
                        }
                    });
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
                genericsCallback.onCancel();
                finalCall.cancel();
            }
        });
    }

    public static void with(android.support.v4.app.Fragment fragment, Request request, final GenericsCallback genericsCallback) {
        if (NetUtil.getNetWorkType(BaseApplication.getInstance()) == 0) {
            BaseApplication.getToastUtil().showMiddleToast("当前无网络");
            return;
        }
        if (!genericsCallback.onBefore()) {
            return;
        }
        Call call = getInstance().mOkHttpClient.newCall(request);
        final Call finalCall = call;
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull final IOException e) {
                if (call.isCanceled()) {
                    return;
                }
                getInstance().platform.defaultCallbackExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        genericsCallback.onError(e.toString());
                        genericsCallback.onFinish();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                if (call.isCanceled()) {
                    return;
                }
                if (response.isSuccessful()) {
                    try {
                        final Object o = genericsCallback.parseNetworkResponse(response);
                        getInstance().platform.defaultCallbackExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                genericsCallback.onResponse(o);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getInstance().platform.defaultCallbackExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            genericsCallback.onFinish();
                        }
                    });
                } else {
                    getInstance().platform.defaultCallbackExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            genericsCallback.onError(response.networkResponse().toString());
                            genericsCallback.onFinish();
                        }
                    });
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
                genericsCallback.onCancel();
                finalCall.cancel();
            }
        });
    }

    public static void with(Fragment fragment, Request request, final GenericsCallback genericsCallback) {
        if (NetUtil.getNetWorkType(BaseApplication.getInstance()) == 0) {
            BaseApplication.getToastUtil().showMiddleToast("当前无网络");
            return;
        }
        if (!genericsCallback.onBefore()) {
            return;
        }
        Call call = getInstance().mOkHttpClient.newCall(request);
        final Call finalCall = call;
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull final IOException e) {
                if (call.isCanceled()) {
                    return;
                }
                getInstance().platform.defaultCallbackExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        genericsCallback.onError(e.toString());
                        genericsCallback.onFinish();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                if (call.isCanceled()) {
                    return;
                }
                if (response.isSuccessful()) {
                    try {
                        final Object o = genericsCallback.parseNetworkResponse(response);
                        getInstance().platform.defaultCallbackExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                genericsCallback.onResponse(o);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getInstance().platform.defaultCallbackExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            genericsCallback.onFinish();
                        }
                    });
                } else {
                    getInstance().platform.defaultCallbackExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            genericsCallback.onError(response.networkResponse().toString());
                            genericsCallback.onFinish();
                        }
                    });
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
                genericsCallback.onCancel();
                finalCall.cancel();
            }
        });
    }

    public static void with(View view, Request request, final GenericsCallback genericsCallback) {
        if (NetUtil.getNetWorkType(BaseApplication.getInstance()) == 0) {
            BaseApplication.getToastUtil().showMiddleToast("当前无网络");
            return;
        }
        if (!genericsCallback.onBefore()) {
            return;
        }
        Call call = getInstance().mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull final IOException e) {
                if (call.isCanceled()) {
                    return;
                }
                getInstance().platform.defaultCallbackExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        genericsCallback.onError(e.toString());
                        genericsCallback.onFinish();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                if (call.isCanceled()) {
                    return;
                }
                if (response.isSuccessful()) {
                    try {
                        final Object o = genericsCallback.parseNetworkResponse(response);
                        getInstance().platform.defaultCallbackExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                genericsCallback.onResponse(o);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getInstance().platform.defaultCallbackExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            genericsCallback.onFinish();
                        }
                    });
                } else {
                    getInstance().platform.defaultCallbackExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            genericsCallback.onError(response.networkResponse().toString());
                            genericsCallback.onFinish();
                        }
                    });
                }
            }
        });
    }

    public static void with(Context context, Request request, final GenericsCallback genericsCallback) {
        if (NetUtil.getNetWorkType(BaseApplication.getInstance()) == 0) {
            BaseApplication.getToastUtil().showMiddleToast("当前无网络");
            return;
        }
        if (!genericsCallback.onBefore()) {
            return;
        }
        Call call = getInstance().mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull final IOException e) {
                if (call.isCanceled()) {
                    return;
                }
                getInstance().platform.defaultCallbackExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        genericsCallback.onError(e.toString());
                        genericsCallback.onFinish();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                if (call.isCanceled()) {
                    return;
                }
                if (response.isSuccessful()) {
                    try {
                        final Object o = genericsCallback.parseNetworkResponse(response);
                        getInstance().platform.defaultCallbackExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                genericsCallback.onResponse(o);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getInstance().platform.defaultCallbackExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            genericsCallback.onFinish();
                        }
                    });
                } else {
                    getInstance().platform.defaultCallbackExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            genericsCallback.onError(response.networkResponse().toString());
                            genericsCallback.onFinish();
                        }
                    });
                }
            }
        });
    }
}
