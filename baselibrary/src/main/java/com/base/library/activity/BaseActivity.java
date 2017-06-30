package com.base.library.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.library.R;
import com.base.library.application.BaseApplication;
import com.base.library.bean.ThemBean;
import com.base.library.listen.OnPermissionListen;
import com.base.library.util.ActivityManage;
import com.base.library.util.ActivityTaskUtils;
import com.base.library.util.ActivityTransition;
import com.base.library.util.CodeUtil;
import com.base.library.util.CrashHandler;
import com.base.library.util.StatusBarUtil;
import com.base.library.util.WDYLog;
import com.base.library.view.relativeLayout.CCPLayoutListenerView;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;

/**
 * 作者：王东一 on 2016/3/21 16:54
 **/
@ActivityTransition(0)
public class BaseActivity extends WDYActivity implements GestureDetector.OnGestureListener, ActivityCompat.OnRequestPermissionsResultCallback {
    private static final String TAG = BaseActivity.class.getSimpleName();
    // 要申请的权限
    private final int PERMISSIONS_REQUEST_CODE = 1114;
    private AlertDialog dialog;
    protected CCPLayoutListenerView ccp_content_fl;
    //接收子类view的布局
    protected RelativeLayout mRelativeLayout = null;
    //键盘是否弹出
    protected Boolean isShowKeyboard = false;
    //左侧按钮
    private RelativeLayout lift_Relative;
    //右侧按钮
    private RelativeLayout right_Relative;
    //左侧按钮图片
    private ImageView lift_imageView;
    //右侧按钮
    private ImageView Right_imageView;
    //标题名称
    private TextView title_textView;
    //右侧文字
    private TextView Right_textView;
    //状态栏
    private View wdyBaseStatus;
    //子view
    public View mBaseLayoutView;
    //标题+状态栏
    private LinearLayout wdyBaseTitleLayout;
    //标题
    private RelativeLayout wdyActivityTitleLayout;
    //标题管理
    private SystemBarTintManager tintManager;
    //键盘状态监听
    private onKeyboardListener onKeyboardListener;
    private boolean mIsHorizontalScrolling = false;
    private int mScrollLimit = 0;
    private boolean mIsChildScrolling = false;
    private int mMinExitScrollX = 0;
    private OnPermissionListen onPermissionListen;//权限监听

    public OnPermissionListen getOnPermissionListen() {
        return onPermissionListen;
    }

    public void setOnPermissionListen(OnPermissionListen onPermissionListen) {
        this.onPermissionListen = onPermissionListen;
    }

    public BaseActivity.onKeyboardListener getOnKeyboardListener() {
        return onKeyboardListener;
    }

    public void setOnKeyboardListener(BaseActivity.onKeyboardListener onKeyboardListener) {
        this.onKeyboardListener = onKeyboardListener;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    public boolean isEnableRightSlideGesture() {
        return true;
    }

    private boolean isCannotHorizontalScroll() {
        return (this.mScrollLimit >= 5);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (!(isEnableRightSlideGesture())) {
            return false;
        }
        if (isCannotHorizontalScroll()) {
            return false;
        }
        if ((!(this.mIsHorizontalScrolling)) && (Math.abs(2.0F * distanceY) > Math.abs(distanceX))) {
            this.mScrollLimit = (1 + this.mScrollLimit);
            return false;
        }
        this.mIsHorizontalScrolling = true;
        if (e1 == null || e2 == null) {
            return false;
        }
        float f1 = 0.0F;
        if (!(this.mIsChildScrolling)) {
            if (e1 != null) {
                f1 = e1.getX();
            }
            float f2 = 0.0F;
            if (e2 != null) {
                f2 = e2.getX();
            }
            if (f1 - f2 < getMinExitScrollX()) {
                this.mScrollLimit = 5;
                close();
                return true;
            }
        }
        return false;
    }


    private int getMinExitScrollX() {
        if (this.mMinExitScrollX == 0) {
            this.mMinExitScrollX = (int) (getResources().getInteger(R.integer.min_exit_scroll_factor) * CodeUtil.getScreenWidth(this) / 100.0F);
            this.mMinExitScrollX = (-this.mMinExitScrollX);
        }
        return this.mMinExitScrollX;
    }

    public void close() {
        finish();
    }



    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }


    public interface onKeyboardListener {
        void isShow(boolean isShow);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManage.getInstance().addActivity(this);
        //设置状态栏文字颜色
        StatusBarUtil.setStatusBarDark(this, BaseApplication.getThemBean().isStatusBarDark());
    }

    //检验权限
    public void checkPermission(ArrayList<String> permission, OnPermissionListen onPermissionListen) {
        setOnPermissionListen(onPermissionListen);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            ArrayList<String> myPermission = new ArrayList<>();
            for (int i = 0; i < permission.size(); i++) {
                int permissionType = ContextCompat.checkSelfPermission(this, permission.get(i));
                switch (permissionType) {
                    case PackageManager.PERMISSION_GRANTED:
                        //授权
                        break;
                    case PackageManager.PERMISSION_DENIED:
                        //拒绝
                        myPermission.add(permission.get(i));
                        break;
                }
            }
            if (myPermission.size() == 0) {
                getOnPermissionListen().callBack(true);
            } else {
                String[] array = new String[myPermission.size()];
                for (int i = 0; i < myPermission.size(); i++) {
                    array[i] = myPermission.get(i);
                }
                ActivityCompat.requestPermissions(this, array, PERMISSIONS_REQUEST_CODE);
            }
        } else {
            getOnPermissionListen().callBack(true);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.base_layout);
        mBaseLayoutView = LayoutInflater.from(this).inflate(layoutResID, null);
        initView();
        onActivityCreate();
    }

    private void initView() {
        ccp_content_fl = (CCPLayoutListenerView) findViewById(R.id.ccp_content_fl);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.WDY_content);
        assert mRelativeLayout != null;
        mRelativeLayout.removeAllViews();
        mRelativeLayout.addView(mBaseLayoutView, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        lift_Relative = (RelativeLayout) findViewById(R.id.back_layout);
        right_Relative = (RelativeLayout) findViewById(R.id.Right_layout);
        lift_imageView = (ImageView) findViewById(R.id.back_imageView);
        Right_imageView = (ImageView) findViewById(R.id.Right_imageView);
        title_textView = (TextView) findViewById(R.id.title_textView);
        Right_textView = (TextView) findViewById(R.id.Right_textView);
        wdyBaseStatus = (View) findViewById(R.id.wdy_base_status);
        wdyBaseTitleLayout = (LinearLayout) findViewById(R.id.wdy_base_title_layout);
        wdyActivityTitleLayout = (RelativeLayout) findViewById(R.id.wdy_activity_title_layout);
        setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ccp_content_fl.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                ccp_content_fl.getWindowVisibleDisplayFrame(r);
                if (ccp_content_fl.getRootView().getHeight() - (r.bottom - r.top) > 100) {
                    setIsShowKeyboard(true);
                    if (getOnKeyboardListener() != null)
                        getOnKeyboardListener().isShow(true);
                } else {
                    setIsShowKeyboard(false);
                    if (getOnKeyboardListener() != null)
                        getOnKeyboardListener().isShow(false);
                }
            }
        });
        initSystemBar();
        changeThem();
        if (mSwipeBackLayout != null) {
            mSwipeBackLayout.init();
            mSwipeBackLayout.setSwipeGestureDelegate(this);
        }

    }

    //修改样式
    private void changeThem() {
        if (BaseApplication.getThemBean() != null) {
            ThemBean themBean = BaseApplication.getThemBean();
            if (themBean.getStatusBarColor() != 0)
                ccp_content_fl.setBackgroundColor(themBean.getStatusBarColor());
            if (themBean.getTitleBackground() != 0)
                wdyBaseTitleLayout.setBackgroundColor(themBean.getTitleBackground());
            if (themBean.getBackMipmap() != 0)
                lift_imageView.setImageResource(themBean.getBackMipmap());
            if (themBean.getTitleTextColor() != 0)
                title_textView.setTextColor(themBean.getTitleTextColor());
        }
    }

    //键盘状态监听
    public void setIsShowKeyboard(Boolean isShowKeyboard) {
        this.isShowKeyboard = isShowKeyboard;
    }

    //设置标题
    protected void setTitleName(String name) {
        title_textView.setText(name);
    }

    //设置标题
    protected void setTitleNameColor(int color) {
        title_textView.setTextColor(ContextCompat.getColor(this, color));
    }

    //隐藏标题栏
    protected void hintTitle() {
        if (wdyActivityTitleLayout != null)
            wdyActivityTitleLayout.setVisibility(View.GONE);
    }

    //隐藏电池栏
    protected void hintStatus() {
        if (wdyBaseStatus != null)
            wdyBaseStatus.setVisibility(View.GONE);
    }

    //设置左侧按钮状态
    protected void hintLeft() {
        lift_Relative.setVisibility(View.INVISIBLE);
    }

    //设置左键
    protected void setLeftClick(View.OnClickListener l) {
        if (lift_Relative != null)
            lift_Relative.setOnClickListener(l);
    }

    protected void setLeftLogo(int id) {
        lift_imageView.setImageResource(id);
    }

    //设置标题背景
    protected void setTitleBackground(int id) {
        if (wdyBaseTitleLayout != null)
            wdyBaseTitleLayout.setBackgroundResource(id);
    }

    //设置状态栏颜色
    private void initSystemBar() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //底部沉嵌式
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(true);
            tintManager.setTintColor(ContextCompat.getColor(this, R.color.transparent));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //设置高度
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) wdyBaseStatus.getLayoutParams();
            params.height = getStatusHeight();
            wdyBaseStatus.setLayoutParams(params);
        }
    }

    //设置主题是不是深色的
    public void setStatusBar(boolean dark) {
        StatusBarUtil.setStatusBarDark(this, dark);
    }

    //获取状态栏高度
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public int getStatusHeight() {
        int statusHeight = 0;
        Rect localRect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    //设置状态颜色
    protected void setTitleBarColor(int id) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            tintManager.setTintColor(ContextCompat.getColor(this, id));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, id));
        }
    }

    //设置状态栏+标题的背景色
    protected void setTitleLayoutColor(int id) {
        wdyBaseTitleLayout.setBackgroundColor(ContextCompat.getColor(this, id));
    }

    //初始化右侧按钮
    protected void setRightInit(String text, int logo, View.OnClickListener listener) {
        if (TextUtils.isEmpty(text)) {
            Right_textView.setVisibility(View.GONE);
        } else {
            Right_textView.setVisibility(View.VISIBLE);
            Right_textView.setText(text);
        }
        Right_imageView.setVisibility(View.VISIBLE);
        Right_imageView.setImageResource(logo);
        right_Relative.setOnClickListener(listener);
    }

    //初始化右侧按钮
    protected void setRightInit(String text, View.OnClickListener listener) {
        if (TextUtils.isEmpty(text)) {
            Right_textView.setVisibility(View.GONE);
        } else {
            Right_textView.setVisibility(View.VISIBLE);
            Right_textView.setText(text);
        }
        Right_imageView.setVisibility(View.GONE);
        right_Relative.setOnClickListener(listener);
    }

    //初始化右侧按钮
    protected void setRightInit(int logo, View.OnClickListener listener) {
        Right_textView.setVisibility(View.GONE);
        Right_imageView.setVisibility(View.VISIBLE);
        Right_imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        Right_imageView.setImageResource(logo);
        right_Relative.setOnClickListener(listener);
    }

    //隐藏右侧按钮
    protected void hintRight() {
        right_Relative.setVisibility(View.INVISIBLE);
    }

    protected void showRight() {
        right_Relative.setVisibility(View.VISIBLE);
    }

    /**
     * 关闭软键盘
     */
    protected void closeSoftKeyboard() {
        // 关闭软键盘
        if (isShowKeyboard) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b) {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限
                        showDialogTipUserGoToAppSettting();
                    } else {
                        getOnPermissionListen().callBack(false);
                    }
                } else {
                    getOnPermissionListen().callBack(true);
                }
            }
        }
    }
    // 提示用户去应用设置界面手动开启权限

    private void showDialogTipUserGoToAppSettting() {
        dialog = new AlertDialog.Builder(this)
                .setTitle("权限不可用")
                .setMessage("请在-应用设置-权限-中，开启权限")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        dialog.dismiss();
                        goToAppSetting();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getOnPermissionListen().callBack(false);
                    }
                }).setCancelable(false).show();
    }

    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, PERMISSIONS_REQUEST_CODE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CrashHandler.getInstance().setContext(this);
    }

    @Override
    protected void onDestroy() {
        WDYLog.d(TAG, "checktask onCreate:" + super.getClass().getSimpleName() + "#0x" + super.hashCode() + ", taskid:" + getTaskId() + ", task:" + new ActivityTaskUtils(this));
        super.onDestroy();
        ActivityManage.getInstance().removeActivity(this);
    }
}
