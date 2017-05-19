package com.base.library.cache;

import com.base.library.bean.Mp3Info;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by wangdongyi on 2017/2/13.
 * 音频文件缓存
 */

public class DownloadCache {
    public static ArrayList<Mp3Info> mp3InfoArrayList=new ArrayList<>();
    public static ArrayList<String> searchFile(String path) {
        ArrayList<String> list = new ArrayList<>();
        File[] files = new File(path).listFiles();
        for (File file : files) {
            list.add(file.getName());
        }
        return list;
    }
}
