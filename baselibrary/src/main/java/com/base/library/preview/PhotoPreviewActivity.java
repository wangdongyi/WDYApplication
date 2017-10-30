package com.base.library.preview;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.*;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.base.library.R;
import com.base.library.application.BaseApplication;
import com.base.library.immersion.ImmersionBar;
import com.base.library.util.DialogUtil;
import com.base.library.util.StatusBarUtil;
import com.base.library.view.upPhotoView.PictureUtil;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

/**
 * 作者：王东一
 * 创建时间：2017/4/26.
 * 图片预览
 */

public class PhotoPreviewActivity extends AppCompatActivity {
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";
    private ArrayList<String> urls = new ArrayList<>();
    private ArrayList<PhotoView> photoViewArrayList = new ArrayList<>();
    private PhotoViewPager pager;
    private PhotoPreviewLayout mainLayout;
    private int pagerPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_preview);
        StatusBarUtil.setStatusBarDark(this, false);
        //透明状态栏，不写默认透明色
        ImmersionBar.with(this).transparentStatusBar().init();
        pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        urls = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLS);
        if (urls == null || urls.isEmpty()) {
            finish();
            overridePendingTransition(0, R.anim.browser_exit_anim);
            return;
        }
        if (pagerPosition > urls.size()) {
            pagerPosition = 0;
        }
        initView();
    }

    private void initView() {
        pager = (PhotoViewPager) findViewById(R.id.pager);
        mainLayout = (PhotoPreviewLayout) findViewById(R.id.main_layout);
        mainLayout.setDragScale(true);
        ViewCompat.setTransitionName(pager, urls.get(pagerPosition));
        for (int i = 0; i < urls.size(); i++) {
            final PhotoView photoView = new PhotoView(this);
            photoView.setDrawingCacheEnabled(true);
            Glide.with(PhotoPreviewActivity.this).load(urls.get(i)).into(photoView);
            photoView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    DialogUtil.showNoTitleSave(PhotoPreviewActivity.this, "保存改图片到手机相册", "取消", "保存", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DialogUtil.dismiss();
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DialogUtil.dismiss();
                            PictureUtil.saveImageToGallery(PhotoPreviewActivity.this, photoView.getDrawingCache());
                            BaseApplication.getToastUtil().showMiddleToast("保存成功");
                        }
                    });
                    return false;
                }
            });
            photoView.setOnPhotoTapListener(new OnPhotoTapListener() {
                @Override
                public void onPhotoTap(ImageView view, float x, float y) {
                    onBackPressed();
                }
            });
            photoViewArrayList.add(photoView);
        }
        pager.setAdapter(new SamplePagerAdapter());
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //支持Transition的设定支持缩放

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pager.setCurrentItem(pagerPosition, false);
    }

    private class SamplePagerAdapter extends android.support.v4.view.PagerAdapter {

        @Override
        public int getCount() {
            return photoViewArrayList.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, final int position) {

            container.addView(photoViewArrayList.get(position));

            return photoViewArrayList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(photoViewArrayList.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAfterTransition(this);
    }

}
