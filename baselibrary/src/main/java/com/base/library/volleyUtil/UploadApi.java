package com.base.library.volleyUtil;

import com.android.volley.Request;
import com.base.library.application.BaseApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdongyi on 2017/1/5.
 */

public class UploadApi {
    /**
     * 上传图片接口
     *
     * @param bitmap   需要上传的图片
     * @param listener 请求回调
     */
    public static void uploadImg(String token, String url, FormImage formImage, ResponseListener listener) {
        List<FormImage> imageList = new ArrayList<FormImage>();
        imageList.add(formImage);
        Request request = new PostUploadRequest(token, url, imageList, listener);
        BaseApplication.getVolleyUtil().getRequestQueue().add(request);
    }
}
