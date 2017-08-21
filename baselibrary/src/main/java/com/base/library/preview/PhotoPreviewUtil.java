package com.base.library.preview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.base.library.R;

import java.util.ArrayList;

/**
 * 作者：王东一
 * 创建时间：2017/5/2.
 * 图片预览工具
 */

public class PhotoPreviewUtil {
    //跳转到图片预览 ArrayList<String> urls 图片地址 selected 选择位置
    public static void movePhotoPreview(Context context, View view, ArrayList<String> urls, int selected) {
        Intent intent = new Intent(context, PhotoPreviewActivity.class);
        intent.putExtra(PhotoPreviewActivity.EXTRA_IMAGE_INDEX, selected);
        intent.putExtra(PhotoPreviewActivity.EXTRA_IMAGE_URLS, urls);
        //android V4包的类,用于两个activity转场时的缩放效果实现
        if (view != null) {
            try {
                ViewCompat.setTransitionName(view, urls.get(selected));
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, view, ViewCompat.getTransitionName(view));
                ActivityCompat.startActivity(context, intent, compat.toBundle());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.browser_enter_anim, 0);
            }
        } else {
            context.startActivity(intent);
            ((Activity) context).overridePendingTransition(R.anim.browser_enter_anim, 0);
        }
    }

}
