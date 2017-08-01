package com.base.library.view.upPhotoView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.library.R;
import com.base.library.application.BaseApplication;
import com.base.library.lifeManagerUtil.LifeManager;
import com.base.library.lifeManagerUtil.OnActivityResultListener;
import com.base.library.listen.OnPermissionListen;
import com.base.library.permission.PermissionsManager;
import com.base.library.util.CodeUtil;

import java.io.File;

import static com.base.library.view.upPhotoView.DoPicCapUtil.IMAGE_FILE_LOCATION;


/**
 * 作者：王东一 on 2015/12/22 09:16
 **/
public class UpPhotoView {
    @SuppressLint("StaticFieldLeak")
    private static UpPhotoView mInstance;
    private File iconDir;
    private AlertDialog mAlertDialog;
    private RelativeLayout layout;
    private Activity activity;
    private Fragment fragment;
    private android.support.v4.app.Fragment fragment_v4;
    private OnActivityResultListener onActivityResultListener = null;
    private onBackPath onBackPath;

    public interface onBackPath {
        void path(String Path);
    }

    private UpPhotoView.onBackPath getOnBackPath() {
        return onBackPath;
    }

    private void setOnBackPath(UpPhotoView.onBackPath onBackPath) {
        this.onBackPath = onBackPath;
    }

    private void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    private void setFragment_v4(android.support.v4.app.Fragment fragment_v4) {
        this.fragment_v4 = fragment_v4;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public static UpPhotoView getInstance() {
        if (mInstance == null) {
            synchronized (UpPhotoView.class) {
                if (mInstance == null) {
                    mInstance = new UpPhotoView();
                }
            }
        }
        return mInstance;
    }

    private void getActivityResultListener() {
        if (onActivityResultListener == null) {
            onActivityResultListener = new OnActivityResultListener() {
                @Override
                public void onActivityResult(int requestCode, int resultCode, Intent data) {
                    try {
                        if (resultCode != Activity.RESULT_OK) {
                            return;
                        }
                        switch (requestCode) {
                            //相机完成
                            case DoPicCapUtil.REQUEST_CODE_CAPTURE:
                                Intent intent1 = new Intent(activity, CropImageActivity.class);
                                intent1.putExtra("PATH", IMAGE_FILE_LOCATION);
                                LifeManager.getInstance().startActivityForResult(intent1, DoPicCapUtil.CAMERA_CROP_DATA);
                                break;
                            case DoPicCapUtil.REQUEST_CODE_ALBUM:
                                // 图库点击确定
                                Intent intent2 = new Intent(activity, CropImageActivity.class);
                                String filePath = DoPicCapUtil.doPicture(data, activity);
                                intent2.putExtra("PATH", filePath);
                                LifeManager.getInstance().startActivityForResult(intent2, DoPicCapUtil.CAMERA_CROP_DATA);
                                break;
                            case DoPicCapUtil.CAMERA_CROP_DATA:
                                //剪切后的路径
                                String path = data.getStringExtra("PATH");
                                getOnBackPath().path(path);
                                break;
                        }
                    } catch (Exception e) {
                        //  Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCreate(Bundle bundle) {
                }

                @Override
                public void onStart() {
                }

                @Override
                public void onResume() {
                }

                @Override
                public void onPause() {

                }

                @Override
                public void onStop() {

                }

                @Override
                public void onDestroy() {
                    Destroy();
                }
            };
        }
    }

    public void with(Activity activity, final onBackPath onBackPath) {
        setOnBackPath(onBackPath);
        setActivity(activity);
        getActivityResultListener();
        LifeManager.getInstance().ObserveActivity(activity, onActivityResultListener);
        init();
    }

    public void with(Fragment fragment, final onBackPath onBackPath) {
        setOnBackPath(onBackPath);
        setFragment(fragment);
        getActivityResultListener();
        LifeManager.getInstance().ObserveActivity(activity, onActivityResultListener);
        init();
    }

    public void with(android.support.v4.app.Fragment fragment_v4, final onBackPath onBackPath) {
        setOnBackPath(onBackPath);
        setFragment_v4(fragment_v4);
        getActivityResultListener();
        LifeManager.getInstance().ObserveActivity(activity, onActivityResultListener);
        init();
    }

    private Context getContext() {
        if (activity != null) {
            return activity;
        } else if (fragment != null) {
            return fragment.getActivity();
        } else {
            return fragment_v4.getActivity();
        }
    }

    @SuppressLint("InflateParams")
    private void init() {
        if (mAlertDialog == null) {
            LayoutInflater inflaterDl = LayoutInflater.from(getContext());
            layout = (RelativeLayout) inflaterDl.inflate(R.layout.image_up_layout, null);
            mAlertDialog = new AlertDialog.Builder(getContext(), R.style.PhotoDialog).create();
            RelativeLayout main = (RelativeLayout) layout.findViewById(R.id.main);
            TextView textView_photo = (TextView) layout.findViewById(R.id.textView_photo);
            TextView textView_picture = (TextView) layout.findViewById(R.id.textView_picture);
            TextView textView_cancel = (TextView) layout.findViewById(R.id.textView_cancel);
            main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAlertDialog.dismiss();
                }
            });
            textView_photo.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("InlinedApi")
                @Override
                public void onClick(View v) {
                    //相机
                    mAlertDialog.dismiss();
                    PermissionsManager.with(getInstance().activity, new OnPermissionListen() {
                        @Override
                        public void callBack(boolean isHave) {
                            if (isHave) {
                                openCamera();
                            } else {
                                BaseApplication.getToastUtil().showMiddleToast("您没有开启权限");
                            }
                        }
                    }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
                }
            });
            textView_picture.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {
                    //相册
                    mAlertDialog.dismiss();
                    PermissionsManager.with(getInstance().activity, new OnPermissionListen() {
                        @Override
                        public void callBack(boolean isHave) {
                            if (isHave) {
                                Intent intent = null;
                                try {
                                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                                        intent = new Intent(Intent.ACTION_GET_CONTENT);
                                        intent.setType("image/*");
                                    } else {
                                        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    }
                                    LifeManager.getInstance().startActivityForResult(intent, DoPicCapUtil.REQUEST_CODE_ALBUM);
                                } catch (Exception e) {
                                    //  访问相册失败
                                    BaseApplication.getToastUtil().showMiddleToast("请开启访问相册权限");
                                    e.printStackTrace();
                                }
                            } else {
                                BaseApplication.getToastUtil().showMiddleToast("您没有开启权限");
                            }
                        }
                    }, Manifest.permission.READ_EXTERNAL_STORAGE);

                }
            });
            textView_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAlertDialog.dismiss();
                }
            });
            mAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    mAlertDialog = null;
                }
            });
        }
        mAlertDialog.show();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams lp = mAlertDialog.getWindow().getAttributes();
        lp.width = dm.widthPixels; //设置宽度
        mAlertDialog.getWindow().setAttributes(lp);
        mAlertDialog.getWindow().setContentView(layout, lp);
    }

    private void openCamera() {
        // 判断SD卡是否存在
        if (!CodeUtil.getSDStatus(activity))
            return;
        // 调用相机
        Intent mIntent = new Intent();
        File file = createIconFile();
        Uri imageUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileprovider", file);//通过FileProvider创建一个content类型的Uri
            mIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        } else {
            imageUri = Uri.fromFile(file);
            // 指定拍摄照片保存路径
        }
        mIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        mIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        LifeManager.getInstance().startActivityForResult(mIntent, DoPicCapUtil.REQUEST_CODE_CAPTURE);
    }


    //创建缓存相机图片
    private File createIconFile() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File external = Environment.getExternalStorageDirectory();
            String rootDir = "/" + "cache";
            iconDir = new File(external, rootDir + "/camera");
            if (!iconDir.exists()) {
                iconDir.mkdirs();
            }
        }
        return new File(iconDir, "temp.png");
    }

    private void Destroy() {
        mInstance = null;
        activity = null;
        fragment = null;
        fragment_v4 = null;
        onActivityResultListener = null;
    }
}
