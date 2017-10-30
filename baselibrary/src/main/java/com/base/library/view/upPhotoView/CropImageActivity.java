package com.base.library.view.upPhotoView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.base.library.R;
import com.base.library.activity.WDYBaseActivity;
import com.base.library.application.BaseApplication;
import com.base.library.util.CodeUtil;

import java.io.File;


/**
 * 作者：王东一 on 2015/12/22 10:06
 * 图片切割
 **/
public class CropImageActivity extends WDYBaseActivity implements View.OnClickListener {
    private static final String TAG = "CropImageActivity";
    private CropImageView mImageView;
    private Bitmap mBitmap;
    private CropImage mCrop;
    private Button mSave;
    private Button mCancel, rotateLeft, rotateRight;
    private String mPath = "CropImageActivity";
    public int screenWidth = 0;
    public int screenHeight = 0;
    private PhotoHandler mHandler = new PhotoHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop_image);
        init();
    }

    private void init() {
//        setTitleName("裁剪图片");
        getWindowWH();
        mImageView = (CropImageView) findViewById(R.id.crop_image);
        mSave = (Button) this.findViewById(R.id.okBtn);
        mCancel = (Button) this.findViewById(R.id.cancelBtn);
        rotateLeft = (Button) this.findViewById(R.id.rotateLeft);
        rotateRight = (Button) this.findViewById(R.id.rotateRight);
        mSave.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        rotateLeft.setOnClickListener(this);
        rotateRight.setOnClickListener(this);    //创建缓存相机图片
        mPath = getIntent().getStringExtra("PATH");
        if (!CodeUtil.isEmpty(mPath)) {
            //相册中原来的图片
            try {
                File mFile = new File(mPath);
                mBitmap = DoPicCapUtil.getBitmapFromSD(mFile, DoPicCapUtil.SCALEIMG, 500, 500);
                if (mBitmap == null) {
                    BaseApplication.getToastUtil().showMiddleToast("没有找到图片");
                    finish();
                } else {
                    resetImageView(mBitmap);
                }
            } catch (Exception e) {
                BaseApplication.getToastUtil().showMiddleToast("没有找到图片");
                finish();
            }
        } else {
            BaseApplication.getToastUtil().showMiddleToast("没有找到图片");
            finish();
        }

    }

    /**
     * 获取屏幕的高和宽
     */
    private void getWindowWH() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
    }

    private void resetImageView(Bitmap b) {
        mImageView.clear();
        mImageView.setImageBitmap(b);
        mImageView.setImageBitmapResetBase(b, true);
        mCrop = new CropImage(this, mImageView, mHandler);
        mCrop.crop(b);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.cancelBtn) {
            finish();

        } else if (i == R.id.okBtn) {
            String path = mCrop.saveToLocal(mCrop.cropAndSave());
            Intent intent = new Intent();
            intent.putExtra("PATH", path);
            setResult(RESULT_OK, intent);
            finish();

        } else if (i == R.id.rotateLeft) {
            mCrop.startRotate(270.f);

        } else if (i == R.id.rotateRight) {
            mCrop.startRotate(90.f);

        }
    }

    @SuppressLint("HandlerLeak")
    private class PhotoHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    break;
            }
            super.handleMessage(msg);
        }
    }
}
