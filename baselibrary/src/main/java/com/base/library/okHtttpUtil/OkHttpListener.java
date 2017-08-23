package com.base.library.okHtttpUtil;

import org.json.JSONObject;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：王东一
 * 创建时间：2017/6/28.
 * 返回接口
 */

public interface OkHttpListener {
    boolean onBefore();

    void onResponse(String response);

    void onError(String msg);

    void onCancel();

    void onFinish();
}
