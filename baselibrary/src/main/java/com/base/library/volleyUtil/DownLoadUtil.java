package com.base.library.volleyUtil;
//Created by 王东一 on 2016/11/14.


import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.base.library.util.WDYLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownLoadUtil extends AsyncTask<String, Integer, File> {
    private static final String TAG = "DownLoadUtil";

    public DownloadLister getDownloadLister() {
        return downloadLister;
    }

    public void setDownloadLister(DownloadLister downloadLister) {
        this.downloadLister = downloadLister;
    }

    private DownloadLister downloadLister;

    public DownLoadUtil() {
    }

    public interface DownloadLister {
        void onStart();

        void onIsHave();

        void onProgress(int progress);

        void onSucceed(String path);

        void onCancel();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        WDYLog.d(TAG, "下载异步线程开始");
        if (getDownloadLister() != null) {
            getDownloadLister().onStart();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Log.d(TAG, "下载进度" + values[0]);
        if (getDownloadLister() != null) {
            getDownloadLister().onProgress(values[0]);
        }
    }

    @Override
    protected File doInBackground(String... params) {
        try {
            String dirName = Environment.getExternalStorageDirectory() + "/WDYDownLoad/";
            File file = new File(dirName);
            //不存在创建
            if (!file.exists()) {
                file.mkdir();
            }
            //下载后的文件名
            String fileName = dirName + params[1];
            File file1 = new File(fileName);
            if (file1.exists()) {
                if (getDownloadLister() != null) {
                    getDownloadLister().onIsHave();
                }
                return null;
            }
            URL url = new URL(params[0]);
            //打开连接
            URLConnection conn = url.openConnection();
            //获得长度
            int contentLength = conn.getContentLength();
            WDYLog.d(TAG, "contentLength = " + contentLength);

            //打开输入流
            InputStream is = conn.getInputStream();
            //创建文件夹 MyDownLoad，在存储卡下

            //创建字节流
            byte[] bs = new byte[1024];
            int count = 0;
            int length = -1;
            OutputStream os = new FileOutputStream(fileName);
            //写数据
            while ((length = is.read(bs)) != -1) {
                os.write(bs, 0, length);
                count += length;
                publishProgress((int) (count / ((float) contentLength) * 100));
            }
            //完成后关闭流
            WDYLog.d(TAG, "download-finish");
            os.close();
            is.close();
            if (getDownloadLister() != null) {
                getDownloadLister().onSucceed(fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (getDownloadLister() != null) {
            getDownloadLister().onCancel();
        }
    }
}
