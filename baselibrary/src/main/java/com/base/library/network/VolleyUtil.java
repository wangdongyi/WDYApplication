package com.base.library.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.base.library.application.BaseApplication;
import com.base.library.bean.HeaderBean;
import com.base.library.util.WDYLog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者：王东一 on 2016/3/22 11:03
 **/
public class VolleyUtil {
    public static final String TAG = "VolleyPatterns";
    private Context mContext;
    //是否显示错误提示
    private Boolean isShowToast = false;
    //成功值
    public static final int SUCCESS = 0;
    public static final int MESSAGE_ERROR = 1; // 错误
    public static final int FAIL = 400; // 失败
    public static final int ERROR = 500; // 错误

    public VolleyUtil(Context mContext) {
        this.mContext = mContext;
    }

    //请求列队
    private RequestQueue mRequestQueue = null;
    //Fields retryPolicy : (网路请求延时)
    private static RetryPolicy retryPolicy = null;

    public Boolean getIsShowToast() {
        return isShowToast;
    }

    public void setIsShowToast(Boolean isShowToast) {
        this.isShowToast = isShowToast;
    }

    // 得到列队
    RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }

    /**
     * Adds the specified request to the global queue, if tag is specified then
     * it is used else Default TAG is used.
     * <p/>
     * param req
     * param tag
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     * <p/>
     * param req
     * param tag
     */
    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important to
     * specify a TAG so that the pending/ongoing requests can be cancelled.
     * <p/>
     * param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    /**
     * Title: getRetryPolicy
     * Description: (设置超时时间)
     * param @param time 1000是1秒
     * param @return 设定文件
     * return RetryPolicy 返回类型
     * throws
     */
    private RetryPolicy getRetryPolicy() {
        if (retryPolicy == null) {
            retryPolicy = new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        }
        return retryPolicy;
    }


    private VolleyHandler handler = new VolleyHandler();

    //post请求
    public void postStringRequest(String Url, final PostStringRequestListener onPostStringRequestListener) {
        if (NetUtil.getNetWorkType(BaseApplication.getInstance()) == 0) {
            BaseApplication.getToastUtil().showMiddleToast("当前无网络");
            return;
        }
        if (!onPostStringRequestListener.onBefore()) {
            return;
        }
        StringRequest mStringRequest = new StringRequest(Request.Method.POST,
                Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        WDYLog.i("输出值", response + "");
                        onPostStringRequestListener.onSuccess(response);
                        onPostStringRequestListener.onFinish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null) {
                            byte[] htmlBodyBytes = error.networkResponse.data;
                            WDYLog.i("get请求失败的返回值", new String(htmlBodyBytes) + "");
                            onPostStringRequestListener.onError(new String(htmlBodyBytes));
                        } else {
                            onPostStringRequestListener.onError("异常");
                        }
                        onPostStringRequestListener.onFinish();
                    }
                }) {
            // Volley请求类提供了一个 getHeaders（）的方法，重载这个方法可以自定义HTTP 的头信息。（也可不实现）
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                ArrayList<HeaderBean> headerList = onPostStringRequestListener.onSetHeard(new ArrayList<HeaderBean>());
                for (int i = 0; i < headerList.size(); i++) {
                    headers.put(headerList.get(i).getKey(), headerList.get(i).getValue());
                }
//                headers.put("App-Key", "qf3d5gbjqfh2h");
//                long timestamp = System.currentTimeMillis();
//                headers.put("Nonce", String.valueOf(timestamp));
//                headers.put("Timestamp", String.valueOf(timestamp));
//                Signature = CodeUtil.encryptSHA("aDaA6jCq9Skm" + String.valueOf(timestamp) + String.valueOf(timestamp));
//                headers.put("Signature", Signature);
                return headers;
            }

            // 携带参数
            @Override
            protected HashMap<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> data = onPostStringRequestListener.onSetDate(new HashMap<String, String>());
                WDYLog.i("输入值", data + "");
                return data;
            }


        };
        mStringRequest.setRetryPolicy(getRetryPolicy());
        getRequestQueue().add(mStringRequest);
    }

    //post请求
    public void postJsonObjectRequest(String Url, final PostJsonObjectRequestListener listener) {
        if (NetUtil.getNetWorkType(BaseApplication.getInstance()) == 0) {
            BaseApplication.getToastUtil().showMiddleToast("当前无网络");
            return;
        }
        if (!listener.onBefore()) {
            return;
        }
        //设置参数
        JSONObject jsonObject = listener.onSetDate(new JSONObject());
        WDYLog.i("输入值", jsonObject.toString() + "");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url, jsonObject,
                new ResponseListener<JSONObject>() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                    @Override
                    public void onResponse(JSONObject response) {
                        WDYLog.i("输出值", response.toString() + "");
                        listener.onSuccess(response);
                        listener.onFinish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null) {
                            byte[] htmlBodyBytes = error.networkResponse.data;
                            WDYLog.i("get请求失败的返回值", new String(htmlBodyBytes) + "");
                            listener.onError(new String(htmlBodyBytes));
                        } else {
                            listener.onError("异常");
                        }
                        listener.onFinish();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                ArrayList<HeaderBean> headerList = listener.onSetHeard(new ArrayList<HeaderBean>());
                for (int i = 0; i < headerList.size(); i++) {
                    headers.put(headerList.get(i).getKey(), headerList.get(i).getValue());
                }
//                headers.put("Accept", "*/*");
//                headers.put("Content-Type", "application/json;");
//                headers.put("App-Key", "qf3d5gbjqfh2h");
//                long timestamp = System.currentTimeMillis();
//                headers.put("Nonce", String.valueOf(timestamp));
//                headers.put("Timestamp", String.valueOf(timestamp));
//                Signature = CodeUtil.encryptSHA("aDaA6jCq9Skm" + String.valueOf(timestamp) + String.valueOf(timestamp));
//                headers.put("Signature", Signature);
                return headers;
            }

        };
        jsonObjectRequest.setRetryPolicy(getRetryPolicy());
        getRequestQueue().add(jsonObjectRequest);
    }

    private int status;
    private String msg;

    //get请求
    public void getJsonObjectRequest(String Url, final VolleyGetListener onListener) {
        if (NetUtil.getNetWorkType(BaseApplication.getInstance()) == 0) {
            BaseApplication.getToastUtil().showMiddleToast("当前无网络");
            return;
        }
        if (!onListener.onBefore()) {
            return;
        }
        WDYLog.i("get请求成功的地址", Url + "");
        JsonObjectRequest newMissRequest = new JsonObjectRequest(Request.Method.GET,
                Url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        WDYLog.i("get请求成功的返回值", jsonObject.toString() + "");
                        onListener.onSuccess(jsonObject.toString());
                        onListener.onFinish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null) {
                            byte[] htmlBodyBytes = error.networkResponse.data;
                            WDYLog.i("get请求失败的返回值", new String(htmlBodyBytes) + "");
                            onListener.onError(new String(htmlBodyBytes));
                        } else {
                            onListener.onError(error.getMessage());
                        }
                        onListener.onFinish();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                ArrayList<HeaderBean> headerList = onListener.onSetHeard(new ArrayList<HeaderBean>());
                for (int i = 0; i < headerList.size(); i++) {
                    headers.put(headerList.get(i).getKey(), headerList.get(i).getValue());
                    WDYLog.i("get请求头文件", headerList.get(i).getKey() + ":" + headerList.get(i).getValue());
                }
//                headers.put("Accept", "*/*");
//                headers.put("Content-Type", "application/json;");
//                headers.put("App-Key", "qf3d5gbjqfh2h");
//                long timestamp = System.currentTimeMillis();
//                headers.put("Nonce", String.valueOf(timestamp));
//                headers.put("Timestamp", String.valueOf(timestamp));
//                Signature = CodeUtil.encryptSHA("aDaA6jCq9Skm" + String.valueOf(timestamp) + String.valueOf(timestamp));
//                headers.put("Signature", Signature);
                return headers;
            }


        };
        newMissRequest.setRetryPolicy(getRetryPolicy());
        getRequestQueue().add(newMissRequest);
    }

    //post请求
    public void getStringRequest(String Url, final VolleyGetListener onListener) {
        if (NetUtil.getNetWorkType(BaseApplication.getInstance()) == 0) {
            BaseApplication.getToastUtil().showMiddleToast("当前无网络");
            return;
        }
        if (!onListener.onBefore()) {
            return;
        }
        StringRequest mStringRequest = new StringRequest(Request.Method.GET,
                Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        WDYLog.i("输出值", response + "");
                        onListener.onSuccess(response);
                        onListener.onFinish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null) {
                            byte[] htmlBodyBytes = error.networkResponse.data;
                            WDYLog.i("get请求失败的返回值", new String(htmlBodyBytes) + "");
                            onListener.onError(new String(htmlBodyBytes));
                        } else {
                            onListener.onError(error.getMessage());
                        }
                        onListener.onFinish();
                    }
                }) {
            // Volley请求类提供了一个 getHeaders（）的方法，重载这个方法可以自定义HTTP 的头信息。（也可不实现）
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                ArrayList<HeaderBean> headerList = onListener.onSetHeard(new ArrayList<HeaderBean>());
                for (int i = 0; i < headerList.size(); i++) {
                    headers.put(headerList.get(i).getKey(), headerList.get(i).getValue());
                    WDYLog.i("get请求头文件", headerList.get(i).getKey() + ":" + headerList.get(i).getValue());
                }
//                headers.put("App-Key", "qf3d5gbjqfh2h");
//                long timestamp = System.currentTimeMillis();
//                headers.put("Nonce", String.valueOf(timestamp));
//                headers.put("Timestamp", String.valueOf(timestamp));
//                Signature = CodeUtil.encryptSHA("aDaA6jCq9Skm" + String.valueOf(timestamp) + String.valueOf(timestamp));
//                headers.put("Signature", Signature);
                return headers;
            }

//            // 携带参数
//            @Override
//            protected HashMap<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> data = onListener.onSetDate(new HashMap<String, String>());
//                WDYLog.i("输入值", data + "");
//                return data;
//            }


        };
        mStringRequest.setRetryPolicy(getRetryPolicy());
        getRequestQueue().add(mStringRequest);
    }

    @SuppressLint("HandlerLeak")
    private class VolleyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }
}
