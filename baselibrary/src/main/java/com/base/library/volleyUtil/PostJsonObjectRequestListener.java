package com.base.library.volleyUtil;

import com.base.library.bean.HeaderBean;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wangdongyi on 2017/1/6.
 */

public interface PostJsonObjectRequestListener {

    ArrayList<HeaderBean> onSetHeard(ArrayList<HeaderBean> headerList);

    JSONObject onSetDate(JSONObject jsonObject);

    boolean onBefore();

    void onSuccess(JSONObject jsonObject);

    void onError(String msg);

    void onFinish();
}
