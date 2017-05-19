package com.base.library.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by wangdongyi on 2017/2/27.
 * 网络管理
 */

public class NetUtil {
    /**
     * 没有网络
     */
    public static final int NETWORKTYPE_INVALID = 0;
    /**
     * 手机网络
     */
    public static final int NETWORKTYPE_PHONE = 1;
    /**
     * wifi网络
     */
    public static final int NETWORKTYPE_WIFI = 2;

    // 得到本机Mac地址
    @SuppressLint("HardwareIds")
    public static String getLocalMac(Context mContext) {
        String mac = "";
        // 获取wifi管理器
        WifiManager wifiMng = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfor = wifiMng.getConnectionInfo();
        mac = "本机的mac地址是：" + wifiInfor.getMacAddress();
        return mac;
    }

    //获取当前网路类型
    public static int getNetWorkType(Context context) {
        int mNetWorkType = 0;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String type = networkInfo.getTypeName();
            if (type.equalsIgnoreCase("WIFI")) {
                mNetWorkType = NETWORKTYPE_WIFI;
            } else if (type.equalsIgnoreCase("MOBILE")) {
                mNetWorkType = NETWORKTYPE_PHONE;
            }
        } else {
            mNetWorkType = NETWORKTYPE_INVALID;
        }
        return mNetWorkType;
    }
}
