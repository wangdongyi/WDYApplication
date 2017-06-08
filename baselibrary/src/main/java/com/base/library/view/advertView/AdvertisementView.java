package com.base.library.view.advertView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.base.library.R;
import com.base.library.application.BaseApplication;
import com.base.library.bean.AdvertisementBean;
import com.base.library.util.CodeUtil;
import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：王东一 on 2016/3/23 15:16
 **/
public class AdvertisementView extends FrameLayout implements Runnable, OnTouchListener {
    private Context mContext;
    /**
     * PHOTO_CHANGE_TIME : (自动播放时间)
     */
    private static final int PHOTO_CHANGE_TIME = 4000;
    /**
     * MSG_CHANGE_PHOTO : (播放图片消息)
     */
    private static final int MSG_CHANGE_PHOTO = 1200;
    private ImageView singImageView;
    // 自动轮播启用开关
    private boolean isAutoPlay = true;

    /**
     * 图片资源id
     */
    private List<String> imgIdArray = new ArrayList<>();

    private ViewPager viewPager;
    //添加viewPage
    private LinearLayout linear_viewPager;
    //点击图片接口
    private onItemClick onItemClick;
    //是否触摸广告位
    private boolean onTouchPager = false;
    //滚动时是否有动画
    private boolean isShowMoveAnimation = false;
    private AdvertisementAdapter advertisementAdapter;
    private ArrayList<AdvertisementBean> list = new ArrayList<>();

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(PHOTO_CHANGE_TIME);
                Circulation();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onTouchPager = true;
                break;
            case MotionEvent.ACTION_UP:
                onTouchPager = false;
                break;
        }
        return imgIdArray.size() == 0 || imgIdArray.size() == 1;
    }


    public interface onItemClick {
        void click(int position);
    }

    public void setOnItemClick(AdvertisementView.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    private AdHandler mHandler = new AdHandler();

    @SuppressLint("HandlerLeak")
    private class AdHandler extends Handler {
        @Override
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case MSG_CHANGE_PHOTO:
                    if (getImgIdArray().size() > 1) {
                        int index = viewPager.getCurrentItem();
                        viewPager.setCurrentItem(index + 1, true);
                        for (int i = 0; i < list.size(); i++) {
                            if (i == index + 1) {
                                list.get(i).setSelected(true);
                            } else {
                                list.get(i).setSelected(false);
                            }
                        }
                        advertisementAdapter.notifyDataSetChanged();
                    }
                    break;
            }
            super.dispatchMessage(msg);
        }
    }

    public AdvertisementView(Context context) {
        this(context, null);
        mContext = context;
    }

    public AdvertisementView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mContext = context;
    }

    public AdvertisementView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initData();
    }

    public void init(List<String> url) {
        if (url == null) {
            return;
        }
        if (!CodeUtil.isEmpty(url)) {
            if (!CodeUtil.compare(url, imgIdArray)) {
                getImgIdArray().clear();
                setImgIdArray(url);
                initUI();
            }
        } else {
            getImgIdArray().clear();
            initUI();
        }
    }

    /**
     * 初始化相关Data
     */
    private void initData() {
        LayoutInflater.from(mContext).inflate(R.layout.advertisement_view, this, true);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.viewGroup_recyclerView);
        linear_viewPager = (LinearLayout) findViewById(R.id.linear_viewPager);
        singImageView = (ImageView) findViewById(R.id.advertisement_imageview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        advertisementAdapter = new AdvertisementAdapter(mContext, list);
        mRecyclerView.setAdapter(advertisementAdapter);
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * 初始化Views等UI
     */
    private void initUI() {
        switch (getImgIdArray().size()) {
            case 0://没有图片
                linear_viewPager.removeAllViews();
                linear_viewPager.setVisibility(View.GONE);
                singImageView.setVisibility(VISIBLE);
                ImageLoader.getInstance().displayImage("", singImageView, BaseApplication.getOptionsRectangular());
                singImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                break;
            case 1://只有一张图
                linear_viewPager.removeAllViews();
                linear_viewPager.setVisibility(View.GONE);
                singImageView.setVisibility(VISIBLE);
                AdvertisementBean advertisementBean1 = new AdvertisementBean();
                advertisementBean1.setPictureSelected(R.drawable.white_circle);
                advertisementBean1.setPictureUnSelected(R.drawable.grey_circle);
                advertisementBean1.setPosition(0);
                advertisementBean1.setSelected(true);
                list.add(advertisementBean1);
                advertisementAdapter.notifyDataSetChanged();
                ImageLoader.getInstance().displayImage(getImgIdArray().get(0), singImageView, BaseApplication.getOptionsRectangular(), new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        singImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        singImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        singImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        singImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    }
                });
                break;
            default://多张图片
                singImageView.setVisibility(View.GONE);
                linear_viewPager.setVisibility(VISIBLE);
                linear_viewPager.removeAllViews();
                //装ImageView数组
                ImageView[][] mImageViews = new ImageView[2][];
                mImageViews[0] = new ImageView[getImgIdArray().size()];
                mImageViews[1] = new ImageView[getImgIdArray().size()];
                viewPager = new ViewPager(mContext);
                linear_viewPager.addView(viewPager, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                viewPager.setFocusable(true);
                viewPager.setOnTouchListener(this);
                viewPager.addOnPageChangeListener(new MyPageChangeListener());
                for (int i = 0; i < getImgIdArray().size(); i++) {
                    AdvertisementBean advertisementBean = new AdvertisementBean();
                    advertisementBean.setPictureSelected(R.drawable.white_circle);
                    advertisementBean.setPictureUnSelected(R.drawable.grey_circle);
                    advertisementBean.setPosition(i);
                    if (i == 0) {
                        // 选中圆点
                        advertisementBean.setSelected(true);
                    } else {
                        // 未选中圆点
                        advertisementBean.setSelected(false);
                    }
                    list.add(advertisementBean);
                }
                advertisementAdapter.notifyDataSetChanged();
                for (int i = 0; i < mImageViews.length; i++) {
                    for (int j = 0; j < mImageViews[i].length; j++) {
                        final ImageView imageView = new ImageView(mContext);
                        final int finalJ = j;
                        imageView.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (onItemClick != null) {
                                    onItemClick.click(finalJ);
                                }
                            }
                        });
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        Glide.with(mContext).load(getImgIdArray().get(j)).into(imageView);
                        mImageViews[i][j] = imageView;
                    }
                }
                if (isShowMoveAnimation())
                    viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
                viewPager.setAdapter(new MyPagerAdapter(mImageViews));
                viewPager.setCurrentItem(imgIdArray.size() * 50 - imgIdArray.size());
                initViewPagerScroll(viewPager, 1000);
                setActivated(true);
                break;
        }
    }

    /**
     * 填充ViewPager的页面适配器
     */
    private class MyPagerAdapter extends PagerAdapter {
        private ImageView[][] data;

        MyPagerAdapter(ImageView[][] mImageViews) {
            data = mImageViews;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (getImgIdArray().size() == 1)
                (container).removeView(data[position / getImgIdArray().size() % 2][0]);
            else
                (container).removeView(data[position / getImgIdArray().size() % 2][position % getImgIdArray().size()]);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            try {
                if (getImgIdArray().size() == 1)
                    return data[position / getImgIdArray().size() % 2][0];
                else
                    (container).addView(data[position / getImgIdArray().size() % 2][position % getImgIdArray().size()], 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data[position / getImgIdArray().size() % 2][position % getImgIdArray().size()];
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(ViewGroup container) {
            super.startUpdate(container);
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    /**
     * ViewPager的监听器 当ViewPager中页面的状态发生改变时调用
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int pos) {
            setImageBackground(pos % getImgIdArray().size());
        }

    }

    private void setImageBackground(int selectItemsIndex) {
        for (int i = 0; i < list.size(); i++) {
            if (i == selectItemsIndex) {
                list.get(i).setSelected(true);
            } else {
                list.get(i).setSelected(false);
            }
        }
        advertisementAdapter.notifyDataSetChanged();
    }

    private void Circulation() {
        if (isAutoPlay() && getImgIdArray().size() > 1 && !onTouchPager)
            mHandler.sendEmptyMessage(MSG_CHANGE_PHOTO);
    }

    public List<String> getImgIdArray() {
        return imgIdArray;
    }

    public void setImgIdArray(List<String> imgIdArray) {
        this.imgIdArray.clear();
        this.imgIdArray.addAll(imgIdArray);
    }

    public boolean isShowMoveAnimation() {
        return isShowMoveAnimation;
    }

    public void setShowMoveAnimation(boolean showMoveAnimation) {
        isShowMoveAnimation = showMoveAnimation;
    }

    public boolean isAutoPlay() {
        return isAutoPlay;
    }

    public void setAutoPlay(boolean isAutoPlay) {
        this.isAutoPlay = isAutoPlay;
    }

    public void clearMemory() {
        ImageLoader.getInstance().clearMemoryCache();
    }

    private class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        @SuppressLint("NewApi")
        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) //a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
            { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

    private class ViewPagerScroller extends Scroller {
        private int scrollDuration = 1000;// 滑动速度

        int getScrollDuration() {
            return scrollDuration;
        }

        void setScrollDuration(int scrollDuration) {
            this.scrollDuration = scrollDuration;
        }

        ViewPagerScroller(Context context) {
            super(context);
        }

        public ViewPagerScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public ViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, getScrollDuration());
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, getScrollDuration());
        }
    }

    /**
     * 设置ViewPager的滑动速度
     */
    private void initViewPagerScroll(ViewPager viewPager, int scrollDuration) {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            ViewPagerScroller scroller = new ViewPagerScroller(viewPager.getContext());
            scroller.setScrollDuration(scrollDuration);
            mScroller.set(viewPager, scroller);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ignored) {

        }
    }

}

