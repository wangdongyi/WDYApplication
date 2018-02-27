package com.base.library.activity;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.base.library.R;
import com.base.library.application.BaseApplication;
import com.base.library.immersion.ImmersionBar;
import com.base.library.listen.OnKeyboardListener;
import com.base.library.util.ActivityManage;
import com.base.library.util.ActivityTransition;
import com.base.library.util.CodeUtil;
import com.base.library.util.CrashHandler;
import com.base.library.util.Exit;
import com.base.library.util.MethodInvoke;
import com.base.library.util.SDKVersionUtils;
import com.base.library.util.StatusBarUtil;
import com.base.library.util.SupportSwipeModeUtils;

/**
 * 作者：王东一 on 2016/3/21 16:54
 **/
@ActivityTransition(0)
public class WDYBaseActivity extends AppCompatActivity implements SwipeBackHelper.Delegate {
    private static final String TAG = WDYBaseActivity.class.getSimpleName();
    protected SwipeBackHelper mSwipeBackHelper;
    protected SwipeBackLayout swipeBackLayout;
    protected FrameLayout ccp_content_fl;
    private WindowAnimation mAnimation = new WindowAnimation();
    //键盘是否弹出
    protected Boolean isShowKeyboard = false;
    //子view
    public View mBaseLayoutView;

    public OnKeyboardListener getOnKeyboardListener() {
        return onKeyboardListener;
    }

    public void setOnKeyboardListener(OnKeyboardListener onKeyboardListener) {
        this.onKeyboardListener = onKeyboardListener;
    }

    //键盘状态监听
    private OnKeyboardListener onKeyboardListener;
    private boolean isViewBuild = false;
    private boolean openExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
        ActivityManage.getInstance().addActivity(this);
        //设置状态栏文字颜色
        StatusBarUtil.setStatusBarDark(this, BaseApplication.getThemBean().isStatusBarDark());
        initSystemBar();
    }

    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {

    }

    @Override
    public void onSwipeBackLayoutCancel() {

    }

    @Override
    public void onSwipeBackLayoutExecuted() {
        finish();
    }

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish() {
        mSwipeBackHelper = new SwipeBackHelper(this, this);

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.shadow_left);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.base_layout);
        mBaseLayoutView = LayoutInflater.from(this).inflate(layoutResID, null);
        initView();
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            ccp_content_fl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onAnimationComplete();
                }
            }, 300);
        }
    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        if (!isViewBuild) {
            isViewBuild = true;
            onAnimationComplete();
        }
    }

    //过场动画加载完成在处理耗时逻辑
    protected void onAnimationComplete() {

    }

    private void initView() {
        ccp_content_fl = (FrameLayout) findViewById(R.id.ccp_content_fl);
        assert ccp_content_fl != null;
        ccp_content_fl.addView(mBaseLayoutView, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        ccp_content_fl.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                ccp_content_fl.getWindowVisibleDisplayFrame(r);
                if (ccp_content_fl.getRootView().getHeight() - (r.bottom - r.top) > CodeUtil.dip2px(WDYBaseActivity.this, 100)) {
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

    }


    //键盘状态监听
    public void setIsShowKeyboard(Boolean isShowKeyboard) {
        this.isShowKeyboard = isShowKeyboard;
    }


    //设置状态栏颜色
    private void initSystemBar() {
        ImmersionBar.with(this)
                .transparentStatusBar()  //透明状态栏，不写默认透明色
                .init();

    }

    //设置主题是不是深色的
    public void setStatusBar(boolean dark) {
        StatusBarUtil.setStatusBarDark(this, dark);
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
    protected void onResume() {
        super.onResume();
        if (!BaseApplication.isOpenLog())
            CrashHandler.getInstance().setContext(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeSoftKeyboard();
        ActivityManage.getInstance().removeActivity(this);
        ImmersionBar.with(this).destroy();  //不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再
    }

    //开启双击返回键返回
    protected void setOpenExit() {
        openExit = true;
    }

    @Override
    public void onBackPressed() {
        if (mSwipeBackHelper.isSliding()) {
            return;
        }
        super.onBackPressed();
    }

    /**
     * 监听键盘按键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //  监听键盘按键
        if (openExit)
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                pressAgainExit();
                return true;
            }
        return super.onKeyDown(keyCode, event);

    }

    /**
     *
     */
    private Exit exit = new Exit();

    /**
     * 再按一次退出程序。
     */
    private void pressAgainExit() {
        if (exit.isExit()) {
            ActivityManage.getInstance().exit();
        } else {
            CodeUtil.showToastShort(this, "连按两次退出程序");
            exit.doExitInOneSecond();
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (mSwipeBackHelper.isSliding()) {
            super.overridePendingTransition(R.anim.activity_finish, R.anim.activity_new);
        } else {
            super.overridePendingTransition(mAnimation.closeEnter, mAnimation.closeExit);
        }
    }

    private void onStartActivityAction(Intent intent) {
        if (intent == null) {
            super.overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
            return;
        }
        String className = null;
        ComponentName component = intent.getComponent();
        if (component != null) {
            className = component.getClassName();
            if (!(className.startsWith(component.getPackageName()))) {
                className = component.getPackageName() + component.getClassName();
            }
        } else {
            return;
        }
        if ((0x2 & MethodInvoke.getTransitionValue(className)) != 0) {
            super.overridePendingTransition(mAnimation.openEnter, mAnimation.openExit);
//            super.overridePendingTransition(R.anim.activity_finish, R.anim.activity_finish);
            return;
        }
        if ((0x4 & MethodInvoke.getTransitionValue(className)) != 0) {
            MethodInvoke.startTransitionNotChange(this);
            return;
        }
        MethodInvoke.startTransitionPopin(this);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void startActivities(Intent[] intents) {
        super.startActivities(intents);
        onStartActivityAction(null);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void startActivities(Intent[] intents, Bundle bundle) {
        super.startActivities(intents, bundle);
        onStartActivityAction(null);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        onStartActivityAction(intent);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void startActivity(Intent intent, Bundle bundle) {
        super.startActivity(intent, bundle);
        onStartActivityAction(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        onStartActivityAction(intent);
    }

//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    @Override
//    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
//        super.startActivityForResult(intent, requestCode, options);
//        onStartActivityAction(intent);
//    }

    private static class WindowAnimation {
        static int activityOpenEnterAnimation;
        static int activityOpenExitAnimation;
        static int activityCloseEnterAnimation;
        static int activityCloseExitAnimation;

        int openEnter = activityOpenEnterAnimation;
        int openExit = activityOpenExitAnimation;
        int closeEnter = activityCloseEnterAnimation;
        int closeExit = activityCloseExitAnimation;

        static {
            if (!(SDKVersionUtils.isSmallerVersion(19) && SupportSwipeModeUtils.isEnable())) {
                activityOpenEnterAnimation = R.anim.slide_right_in;
                activityOpenExitAnimation = R.anim.slide_left_out;
                activityCloseEnterAnimation = R.anim.slide_left_in;
                activityCloseExitAnimation = R.anim.slide_right_out;
            } else {
                activityOpenEnterAnimation = R.anim.slide_right_in;
                activityOpenExitAnimation = R.anim.slide_left_out;
                activityCloseEnterAnimation = R.anim.slide_left_in;
                activityCloseExitAnimation = R.anim.slide_right_out;
            }
        }
    }

}
