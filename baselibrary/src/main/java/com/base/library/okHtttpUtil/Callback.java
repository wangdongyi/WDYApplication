package com.base.library.okHtttpUtil;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：王东一
 * 创建时间：2017/6/29.
 */

public abstract class Callback<T> {

    public abstract T parseNetworkResponse(Response response) throws Exception;

    public abstract void onResponse(T response);
}
