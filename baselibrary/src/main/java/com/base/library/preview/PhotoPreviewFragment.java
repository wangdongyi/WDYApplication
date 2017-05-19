package com.base.library.preview;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.library.R;
import com.base.library.fragment.BaseFragment;
import com.base.library.util.DialogUtil;
import com.base.library.view.photoView.PhotoView;
import com.base.library.view.photoView.PhotoViewAttacher;
import com.base.library.view.photoView.model.ViewImageInfo;
import com.base.library.view.upPhotoView.PictureUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 作者：王东一
 * 创建时间：2017/4/26.
 */

@SuppressLint("ValidFragment")
public class PhotoPreviewFragment extends BaseFragment {
    private static final String TAG = "PhotoPreviewFragment";
    private PhotoView mImageView;
    private ViewImageInfo mEntry;
    private boolean isBuild = false;
    private Bitmap bitmap;

    public PhotoPreviewFragment(ViewImageInfo mEntry) {
        this.mEntry = mEntry;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setContentView(inflater, R.layout.fragment_photo_preview);
        init();
        return main;
    }

    @Override
    protected void init() {
        mImageView = (PhotoView) main.findViewById(R.id.image);
        mImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogUtil.showNoTitleSave(getActivity(), "保存到手机", "取消", "保存", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtil.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtil.dismiss();
                        PictureUtil.saveBitmap(getActivity(), mImageView.getVisibleRectangleBitmap());
                    }
                });

                return false;
            }
        });
        mImageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

            @Override
            public void onPhotoTap(View arg0, float arg1, float arg2) {
                getActivity().finish();
            }
        });
        if (bitmap == null)
            Glide.with(this).load(mEntry.getPicurl()).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    bitmap = resource;
                    mImageView.setImageBitmap(bitmap);
                    ((PhotoViewAttacher) mImageView.getIPhotoViewImplementation()).update();
                }
            });
        else {
            mImageView.setImageBitmap(bitmap);
            ((PhotoViewAttacher) mImageView.getIPhotoViewImplementation()).update();
        }

    }


    @Override
    protected void lazyLoad() {
        isBuild = true;
        init();
    }

    public void RecycleBitmap() {
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
            System.gc();
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        RecycleBitmap();
        if (mImageView != null) {
            mImageView.setImageDrawable(null);
        }
        mImageView = null;
    }
}
