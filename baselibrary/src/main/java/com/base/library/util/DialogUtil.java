package com.base.library.util;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.library.R;

/**
 * 作者：王东一 on 2016/4/5 14:51
 **/
public class DialogUtil {
    //等待提示对话框
    private static Dialog dialog = null;

    public static void show(Context mContext) {
        dialog = new Dialog(mContext, R.style.DialogStyle);
        View view = LayoutInflater.from(mContext).inflate(R.layout.loading_layout, null);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        Window win = dialog.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = win.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        params.y = CodeUtil.getScreenHeight(mContext);
        win.setAttributes(params);
        dialog.show();
    }

    public static void showNoTitle(Context mContext, String content, String left, String right, View.OnClickListener leftListener, View.OnClickListener rightListener) {
        dialog = new Dialog(mContext, R.style.DialogStyle);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_no_title, null);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        TextView textViewContent = (TextView) view.findViewById(R.id.textView_content);
        TextView textViewLeft = (TextView) view.findViewById(R.id.textView_left);
        TextView textViewRight = (TextView) view.findViewById(R.id.textView_right);
        textViewContent.setText(content);
        textViewLeft.setText(left);
        textViewLeft.setOnClickListener(leftListener);
        textViewRight.setText(right);
        textViewRight.setOnClickListener(rightListener);
        dialog.show();
    }

    public static void showWhitSubtitle(Context mContext, String content, String Subtitle, String left, String right, View.OnClickListener leftListener, View.OnClickListener rightListener) {
        dialog = new Dialog(mContext, R.style.DialogStyle);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_no_title, null);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        TextView textViewContent = (TextView) view.findViewById(R.id.textView_content);
        TextView textViewSubtitle = (TextView) view.findViewById(R.id.textView_subtitle);
        TextView textViewLeft = (TextView) view.findViewById(R.id.textView_left);
        TextView textViewRight = (TextView) view.findViewById(R.id.textView_right);
        textViewContent.setText(content);
        textViewSubtitle.setText(Subtitle);
        textViewLeft.setText(left);
        textViewLeft.setTextColor(ContextCompat.getColor(mContext, R.color.text_blue));
        textViewLeft.setOnClickListener(leftListener);
        textViewRight.setText(right);
        textViewRight.setOnClickListener(rightListener);
        dialog.show();
    }

    public static void showNoTitleSave(Context mContext, String content, String left, String right, View.OnClickListener leftListener, View.OnClickListener rightListener) {
        dialog = new Dialog(mContext, R.style.DialogStyle);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_no_title, null);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        TextView textViewContent = (TextView) view.findViewById(R.id.textView_content);
        TextView textViewLeft = (TextView) view.findViewById(R.id.textView_left);
        TextView textViewRight = (TextView) view.findViewById(R.id.textView_right);
        textViewContent.setText(content);
        textViewLeft.setText(left);
        textViewLeft.setTextColor(ContextCompat.getColor(mContext, R.color.text_blue));
        textViewLeft.setOnClickListener(leftListener);
        textViewRight.setText(right);
        textViewRight.setOnClickListener(rightListener);
        dialog.show();
    }

    public static void showNoTitleSingeButton(Context mContext, String content, String subtitle, String button, View.OnClickListener Listener) {
        dialog = new Dialog(mContext, R.style.DialogStyle);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_singe_button, null);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        TextView textViewContent = (TextView) view.findViewById(R.id.textView_content);
        TextView textView_subtitle = (TextView) view.findViewById(R.id.textView_subtitle);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textViewContent.setText(content);
        textView_subtitle.setText(subtitle);
        textView.setText(button);
        textView.setOnClickListener(Listener);
        textViewContent.setTextColor(ContextCompat.getColor(mContext, R.color.text_blue));
        textView_subtitle.setTextColor(ContextCompat.getColor(mContext, R.color.text_blue));
        textView.setTextColor(ContextCompat.getColor(mContext, R.color.text_blue));
        dialog.show();
    }

    public static void showNoTitleSingeButton(Context mContext, boolean Cancelable, String content, String subtitle, String button, View.OnClickListener Listener) {
        dialog = new Dialog(mContext, R.style.DialogStyle);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_singe_button, null);
        dialog.setContentView(view);
        dialog.setCancelable(Cancelable);
        TextView textViewContent = (TextView) view.findViewById(R.id.textView_content);
        TextView textView_subtitle = (TextView) view.findViewById(R.id.textView_subtitle);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textViewContent.setText(content);
        textView_subtitle.setText(subtitle);
        textView.setText(button);
        textView.setOnClickListener(Listener);
        textViewContent.setTextColor(ContextCompat.getColor(mContext, R.color.text_blue));
        textView_subtitle.setTextColor(ContextCompat.getColor(mContext, R.color.text_blue));
        textView.setTextColor(ContextCompat.getColor(mContext, R.color.text_blue));
        dialog.show();
    }

    public static void show(Context mContext, String title, String content, View.OnClickListener leftListener, View.OnClickListener rightListener) {
        dialog = new Dialog(mContext, R.style.DialogStyle);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_layout, null);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        TextView textViewTitle = (TextView) view.findViewById(R.id.textView_title);
        TextView textViewContent = (TextView) view.findViewById(R.id.textView_content);
        TextView textViewLeft = (TextView) view.findViewById(R.id.textView_left);
        TextView textViewRight = (TextView) view.findViewById(R.id.textView_right);
        textViewTitle.setText(title);
        textViewContent.setText(content);
        textViewLeft.setOnClickListener(leftListener);
        textViewRight.setOnClickListener(rightListener);
        dialog.show();
    }

    public static void showDeleteDialog(Context mContext, View.OnClickListener listener) {
        @SuppressLint("InflateParams")
        RelativeLayout layout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.delete_dialog_layout, null);
        dialog = new AlertDialog.Builder(mContext, R.style.DeleteDialog).create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = CodeUtil.getScreenWidth(mContext); //设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(layout, lp);
        TextView textView = (TextView) layout.findViewById(R.id.textView_delete);
        textView.setOnClickListener(listener);
        TextView textView1 = (TextView) layout.findViewById(R.id.textView_cancel);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public static void showBookCoverDialog(Context mContext, View.OnClickListener listener1, View.OnClickListener listener2, View.OnClickListener listener3) {
        @SuppressLint("InflateParams")
        RelativeLayout layout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.up_load_book_cover_dialog, null);
        dialog = new AlertDialog.Builder(mContext, R.style.DeleteDialog).create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = CodeUtil.getScreenWidth(mContext); //设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(layout, lp);
        TextView textView1 = (TextView) layout.findViewById(R.id.textView_photo);
        textView1.setOnClickListener(listener1);
        TextView textView2 = (TextView) layout.findViewById(R.id.textView_picture);
        textView2.setOnClickListener(listener2);
        TextView textView3 = (TextView) layout.findViewById(R.id.textView_change);
        textView3.setOnClickListener(listener3);
        TextView textView4 = (TextView) layout.findViewById(R.id.textView_cancel);
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public static void showChoosePicDialog(Context mContext, View.OnClickListener listener1, View.OnClickListener listener2) {
        @SuppressLint("InflateParams")
        RelativeLayout layout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.up_load_book_cover_dialog, null);
        dialog = new AlertDialog.Builder(mContext, R.style.DeleteDialog).create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = CodeUtil.getScreenWidth(mContext); //设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(layout, lp);
        TextView textView1 = (TextView) layout.findViewById(R.id.textView_photo);
        textView1.setOnClickListener(listener1);
        TextView textView2 = (TextView) layout.findViewById(R.id.textView_picture);
        textView2.setOnClickListener(listener2);
        TextView textView3 = (TextView) layout.findViewById(R.id.textView_change);
        textView3.setVisibility(View.GONE);
        TextView textView4 = (TextView) layout.findViewById(R.id.textView_cancel);
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public static void showDeleteDialog(Context mContext, String title, View.OnClickListener listener) {
        @SuppressLint("InflateParams")
        RelativeLayout layout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.delete_dialog_layout, null);
        dialog = new AlertDialog.Builder(mContext, R.style.DeleteDialog).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = CodeUtil.getScreenWidth(mContext); //设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(layout, lp);
        TextView textView = (TextView) layout.findViewById(R.id.textView_delete);
        textView.setOnClickListener(listener);
        textView.setText(title);
        TextView textView1 = (TextView) layout.findViewById(R.id.textView_cancel);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public static void showPayDialog(Context mContext, View.OnClickListener weixinlistener, View.OnClickListener zhifubaolistener) {
        @SuppressLint("InflateParams")
        RelativeLayout layout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.dialog_pay_layout, null);
        dialog = new AlertDialog.Builder(mContext, R.style.DeleteDialog).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = CodeUtil.getScreenWidth(mContext); //设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(layout, lp);
        TextView textView = (TextView) layout.findViewById(R.id.textView_wexin);
        textView.setOnClickListener(weixinlistener);
        TextView textView1 = (TextView) layout.findViewById(R.id.textView_zhifubao);
        textView1.setOnClickListener(zhifubaolistener);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public static void dismiss() {
        if (null != dialog) {
            dialog.dismiss();
        }
    }

}
