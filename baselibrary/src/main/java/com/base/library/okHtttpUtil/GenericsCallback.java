package com.base.library.okHtttpUtil;

import com.base.library.util.WDYJsonUtil;

import java.lang.reflect.ParameterizedType;

import okhttp3.Response;

/**
 * 作者：王东一
 * 创建时间：2017/6/29.
 */

public abstract class GenericsCallback<T> extends Callback<T> implements OkHttpListener {
    @Override
    public T parseNetworkResponse(Response response) throws Exception {
        String back = response.body().string();
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (entityClass == String.class) {
            return (T) back;
        }
        T bean = WDYJsonUtil.GetEntity(WDYJsonUtil.GetJsonObjByLevel(back), entityClass);
        onRequest(back);
        return bean;
    }

}
