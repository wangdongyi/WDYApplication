package com.base.library.volleyUtil;

import com.base.library.bean.HeaderBean;

import java.util.ArrayList;

/**
 * Created by wangdongyi on 2017/1/6.
 * get请求监听
 */

public interface VolleyGetListener {
    ArrayList<HeaderBean> onSetHeard(ArrayList<HeaderBean> headerList);
    boolean onBefore();

    void onSuccess(String jsonObject);

    void onError(String msg);

    void onFinish();
}
