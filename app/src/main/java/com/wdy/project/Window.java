package com.wdy.project;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.base.library.listen.NoDoubleClickListener;
import com.base.library.util.CodeUtil;

/**
 * 作者：王东一
 * 创建时间：2017/9/26.
 */

public class Window {
    @SuppressLint("StaticFieldLeak")
    private static Window mInstance;
    private static AlertDialog mAlertDialog = null;
    private Context mContext;
    private View layout;
    private ViewHolder viewHolder;

    public static Window getInstance() {
        if (mInstance == null) {
            synchronized (Window.class) {
                if (mInstance == null) {
                    mInstance = new Window();
                }
            }
        }
        return mInstance;
    }

    public void with(Context context, onClickSure onClickSure) {
        this.mContext = context;
        initDialog();
    }

    private void initDialog() {
        if (mAlertDialog == null) {
            LayoutInflater inflaterDl = LayoutInflater.from(mContext);
            layout = inflaterDl.inflate(R.layout.window_change_password, null);
            mAlertDialog = new AlertDialog.Builder(mContext, R.style.PhotoDialog).create();
            viewHolder = new ViewHolder(layout);
            viewHolder.rootView.setOnClickListener(new NoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    mAlertDialog.dismiss();
                }
            });
        }
        mAlertDialog.show();
        WindowManager.LayoutParams lp = mAlertDialog.getWindow().getAttributes();
        lp.width = CodeUtil.getScreenWidth(mContext); //设置宽度
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mAlertDialog.getWindow().setAttributes(lp);
        mAlertDialog.getWindow().setContentView(layout, lp);
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mAlertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                mAlertDialog = null;
//                viewHolder = null;
            }
        });
    }

    public interface onClickSure {
        void onClick(String password);
    }

    public static class ViewHolder {
        public View rootView;
        public LinearLayout mainLayout;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mainLayout = (LinearLayout) rootView.findViewById(R.id.main_layout);
        }

    }
}
