package com.base.library.preview;

import android.content.Context;
import android.content.Intent;

import com.base.library.view.photoView.model.ViewImageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：王东一
 * 创建时间：2017/5/2.
 * 图片预览工具
 */

public class PhotoPreviewUtil {
    //跳转到图片预览 ArrayList<String> urls 图片地址 selected 选择位置
    public static void movePhotoPreview(Context context, ArrayList<String> urls, int selected) {
        Intent intent = new Intent(context, PhotoPreviewActivity.class);
        intent.putExtra(PhotoPreviewActivity.EXTRA_IMAGE_INDEX, selected);
        intent.putExtra(PhotoPreviewActivity.EXTRA_IMAGE_URLS, urls);
        context.startActivity(intent);
    }
}
