package com.base.library.view.upPhotoView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.library.R;
import com.base.library.activity.PermissionsActivity;
import com.base.library.permission.PermissionsChecker;
import com.base.library.util.CodeUtil;

import java.io.File;
import java.util.List;

import static com.base.library.view.upPhotoView.DoPicCapUtil.IMAGE_FILE_LOCATION;


/**
 * 作者：王东一 on 2015/12/22 09:16
 **/
public class UpPhotoView {
    private static final int REQUEST_PERMISSION = 4;  //权限请求
    private File iconDir;
    private AlertDialog mAlertDialog;
    private Context mContext;
    private RelativeLayout layout;
    private onPhotoLinear onPhotoLinear;
    private static onPhotoList onPhotoList;
    private onCameraListen onCameraListen;
    private TextView textView_photo, textView_picture, textView_change, textView_cancel;
    private View line;
    private Uri imageUri;//原图保存地址
    private onClickChange onClickChange;
    private boolean isShowChange = false;
    private PermissionsChecker mPermissionsChecker; // 权限检测器
    static final String[] PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    public UpPhotoView.onClickChange getOnClickChange() {
        return onClickChange;
    }

    public void setOnClickChange(UpPhotoView.onClickChange onClickChange) {
        this.onClickChange = onClickChange;
    }

    public interface onClickChange {
        void onClick();
    }

    public UpPhotoView.onCameraListen getOnCameraListen() {
        return onCameraListen;
    }

    public void setOnCameraListen(UpPhotoView.onCameraListen onCameraListen) {
        this.onCameraListen = onCameraListen;
    }

    private int more = 0;

    public UpPhotoView.onPhotoLinear getOnPhotoLinear() {
        return onPhotoLinear;
    }

    public void setOnPhotoLinear(UpPhotoView.onPhotoLinear onPhotoLinear) {
        this.onPhotoLinear = onPhotoLinear;
    }

    public static UpPhotoView.onPhotoList getOnPhotoList() {
        return onPhotoList;
    }

    public static void setOnPhotoList(UpPhotoView.onPhotoList onPhotoList) {
        UpPhotoView.onPhotoList = onPhotoList;
    }

    public interface onCameraListen {
        void path(String Path);
    }

    public interface onPhotoLinear {
        void selectPhoto(String Path);
    }

    public interface onPhotoList {
        void selectPhoto(List<String> Path);
    }

    public UpPhotoView(Context mContext) {
        this.mContext = mContext;
        init();
    }

    public UpPhotoView(Context mContext, int more) {
        this.mContext = mContext;
        this.more = more;
        init();
    }

    public UpPhotoView(Context mContext, boolean show) {
        this.mContext = mContext;
        this.isShowChange = show;
        init();
    }

    @SuppressLint("InflateParams")
    private void init() {
        mPermissionsChecker = new PermissionsChecker(mContext);
        LayoutInflater inflaterDl = LayoutInflater.from(mContext);
        layout = (RelativeLayout) inflaterDl.inflate(R.layout.image_up_layout, null);
        mAlertDialog = new AlertDialog.Builder(mContext, R.style.PhotoDialog).create();
        RelativeLayout main = (RelativeLayout) layout.findViewById(R.id.main);
        textView_photo = (TextView) layout.findViewById(R.id.textView_photo);
        textView_picture = (TextView) layout.findViewById(R.id.textView_picture);
        textView_change = (TextView) layout.findViewById(R.id.textView_change);
        textView_cancel = (TextView) layout.findViewById(R.id.textView_cancel);
        line = layout.findViewById(R.id.line);
        if (isShowChange) {
            textView_change.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
        } else {
            textView_change.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        }
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
        textView_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //相机
                mAlertDialog.dismiss();
                if (getOnCameraListen() != null) {
                    getOnCameraListen().path("ss");
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
                            PermissionsActivity.startActivityForResult((Activity) mContext, REQUEST_PERMISSION, PERMISSIONS);
                        } else {
                            openCamera();
                        }
                    } else {
                        openCamera();
                    }
                }

            }
        });
        textView_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //相册
                mAlertDialog.dismiss();
                Intent intent = null;
                try {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                        intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                    } else {
                        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    }
                    ((Activity) mContext).startActivityForResult(intent, DoPicCapUtil.REQUEST_CODE_ALBUM);
                } catch (Exception e) {
                    //  访问相册失败
                    CodeUtil.showToastShort(mContext, "请开启访问相册权限");
                    e.printStackTrace();
                }
            }
        });
        textView_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
                if (getOnClickChange() != null)
                    getOnClickChange().onClick();
            }
        });
        textView_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
    }

    private void openCamera() {
        // 判断SD卡是否存在
        if (!CodeUtil.getSDStatus(mContext))
            return;
        // 调用相机
        Intent mIntent = new Intent();
        File file = createIconFile();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileprovider", file);//通过FileProvider创建一个content类型的Uri
            mIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        } else {
            imageUri = Uri.fromFile(file);
            // 指定拍摄照片保存路径
        }
        mIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        mIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        ((Activity) mContext).startActivityForResult(mIntent, DoPicCapUtil.REQUEST_CODE_CAPTURE);

    }

    public void showView() {
        mAlertDialog.show();
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams lp = mAlertDialog.getWindow().getAttributes();
        lp.width = dm.widthPixels; //设置宽度
        mAlertDialog.getWindow().setAttributes(lp);
        mAlertDialog.getWindow().setContentView(layout, lp);
    }

    //创建缓存相机图片
    public File createIconFile() {
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

    public void ActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode != Activity.RESULT_OK) {
                return;
            }
            switch (requestCode) {
                //相机完成
                case DoPicCapUtil.REQUEST_CODE_CAPTURE:
                    Intent intent1 = new Intent(mContext, CropImageActivity.class);
                    intent1.putExtra("PATH", IMAGE_FILE_LOCATION);
                    ((Activity) mContext).startActivityForResult(intent1, DoPicCapUtil.CAMERA_CROP_DATA);
                    break;
                case DoPicCapUtil.REQUEST_CODE_ALBUM:
                    // 图库点击确定
                    Intent intent2 = new Intent(mContext, CropImageActivity.class);
                    String filePath = DoPicCapUtil.doPicture(data, mContext);
                    intent2.putExtra("PATH", filePath);
                    ((Activity) mContext).startActivityForResult(intent2, DoPicCapUtil.CAMERA_CROP_DATA);
                    break;
                case DoPicCapUtil.CAMERA_CROP_DATA:
                    //剪切后的路径
                    String path = data.getStringExtra("PATH");
                    getOnPhotoLinear().selectPhoto(path);
                    break;
                case 620:
                    List<String> list = data.getStringArrayListExtra("selected");
                    if (getOnPhotoList() != null) {
                        getOnPhotoList().selectPhoto(list);
                    }
                    break;
                case REQUEST_PERMISSION://权限请求
                    if (resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
                        mAlertDialog.dismiss();
                    } else {
                        openCamera();
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            //  Auto-generated catch block
            e.printStackTrace();
        }
    }
}
