package com.base.library.network;

import com.base.library.bean.HeaderBean;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 作者：王东一 on 2016/4/11 13:25
 **/
public interface PostStringRequestListener {
    ArrayList<HeaderBean> onSetHeard(ArrayList<HeaderBean> headerList);

    HashMap<String, String> onSetDate(HashMap<String, String> date);

    boolean onBefore();

    void onSuccess(String jsonObject);

    void onError(String msg);

    void onFinish();

}
