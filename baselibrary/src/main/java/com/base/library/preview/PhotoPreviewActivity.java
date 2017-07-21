package com.base.library.preview;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.base.library.R;
import com.base.library.activity.BaseActivity;
import com.base.library.application.BaseApplication;
import com.base.library.fragment.BaseFragment;
import com.base.library.util.DialogUtil;
import com.base.library.view.photoView.HackyViewPager;
import com.base.library.view.photoView.PhotoView;
import com.base.library.view.photoView.PhotoViewAttacher;
import com.base.library.view.photoView.model.ViewImageInfo;
import com.base.library.view.upPhotoView.PictureUtil;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：王东一
 * 创建时间：2017/4/26.
 * 图片预览
 */

public class PhotoPreviewActivity extends BaseActivity {
    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";
    public static final String EXTRA_IMAGE_URLS_ID = "image_urls_id";
    private static ArrayList<String> urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_preview);
        hintTitle();
        HackyViewPager mPager = (HackyViewPager) findViewById(R.id.pager);
        int pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        urls = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLS);
        if (urls == null || urls.isEmpty()) {
            finish();
            return;
        }
        if (pagerPosition > urls.size()) {
            pagerPosition = 0;
        }
        mPager.setAdapter(new SamplePagerAdapter());
        mPager.setCurrentItem(pagerPosition, false);
    }

    private class SamplePagerAdapter extends android.support.v4.view.PagerAdapter {


        @Override
        public int getCount() {
            return urls.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            final PhotoView photoView = new PhotoView(container.getContext());
            Glide.with(PhotoPreviewActivity.this).load(urls.get(position)).into(photoView);
            photoView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    DialogUtil.showNoTitleSave(PhotoPreviewActivity.this, "保存到手机", "取消", "保存", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DialogUtil.dismiss();
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DialogUtil.dismiss();
                            PictureUtil.saveImageToGallery(PhotoPreviewActivity.this, photoView.getVisibleRectangleBitmap());
                            BaseApplication.getToastUtil().showMiddleToast("保存成功");
                        }
                    });

                    return false;
                }
            });
            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

                @Override
                public void onPhotoTap(View arg0, float arg1, float arg2) {
                    finish();
                }
            });
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
