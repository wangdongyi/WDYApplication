package com.base.library.wheelDialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.library.R;
import com.base.library.util.CodeUtil;
import com.base.library.view.wheel.OnWheelScrollListener;
import com.base.library.view.wheel.WDYWheelView;
import com.base.library.view.wheel.WheelView;
import com.base.library.view.wheel.adapter.SelectedWheelAdapter;

import java.util.ArrayList;

/**
 * 作者：王东一
 * 创建时间：2017/4/30.
 */

public class WheelDialog {
    private static String selected;
    private static Dialog dialog = null;
    private static String left, right;

    public interface selectedListener {
        void selected(String selected);
    }

    public interface selectedDoubleListener {
        void selected(String left_selected, String right_selected);
    }

    public static void showSingle(Context mContext, final ArrayList<String> list, String userSelected, final selectedListener selectedListener) {
        @SuppressLint("InflateParams")
        RelativeLayout layout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.dialog_wheel_single, null);
        dialog = new AlertDialog.Builder(mContext, R.style.DeleteDialog).create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = CodeUtil.getScreenWidth(mContext); //设置宽度
        lp.height = CodeUtil.getScreenHeight(mContext);
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(layout, lp);
        final TextView textView = (TextView) layout.findViewById(R.id.cancel_textView);
        final TextView textView1 = (TextView) layout.findViewById(R.id.true_textView);
        final WDYWheelView wheelView = (WDYWheelView) layout.findViewById(R.id.wheelView);
        wheelView.setOffset(1);
        wheelView.setItems(list);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(userSelected)) {
                wheelView.setSeletion(i);
            }
        }
        wheelView.setOnWheelViewListener(new WDYWheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                super.onSelected(selectedIndex, item);
                if (list.size() > selectedIndex) {
                    selected = item;
                }
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                selectedListener.selected(selected);
            }
        });

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public static void showDouble(Context mContext, final ArrayList<String> left_list, String left_userSelected,
                                  final ArrayList<String> right_list, String right_userSelected,
                                  final selectedDoubleListener selectedDoubleListener) {
        left = left_userSelected;
        right = right_userSelected;
        @SuppressLint("InflateParams")
        RelativeLayout layout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.dialog_wheel_double, null);
        dialog = new AlertDialog.Builder(mContext, R.style.DeleteDialog).create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = CodeUtil.getScreenWidth(mContext); //设置宽度
        lp.height = CodeUtil.getScreenHeight(mContext);
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(layout, lp);
        final TextView textView = (TextView) layout.findViewById(R.id.cancel_textView);
        final TextView textView1 = (TextView) layout.findViewById(R.id.true_textView);
        final WDYWheelView wheelViewLeft = (WDYWheelView) layout.findViewById(R.id.wheelView_left);
        final WDYWheelView wheelViewRight = (WDYWheelView) layout.findViewById(R.id.wheelView_right);
        wheelViewLeft.setOffset(1);
        wheelViewLeft.setItems(left_list);
        for (int i = 0; i < left_list.size(); i++) {
            if (left_list.get(i).equals(left)) {
                wheelViewLeft.setSeletion(i);
            }
        }
        wheelViewLeft.setOnWheelViewListener(new WDYWheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                super.onSelected(selectedIndex, item);
                if (left_list.size() > selectedIndex) {
                    left = item;
                }
            }
        });
        wheelViewRight.setOffset(1);
        wheelViewRight.setItems(right_list);
        for (int i = 0; i < right_list.size(); i++) {
            if (right_list.get(i).equals(right)) {
                wheelViewRight.setSeletion(i);
            }
        }
        wheelViewRight.setOnWheelViewListener(new WDYWheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                super.onSelected(selectedIndex, item);
                if (right_list.size() > selectedIndex) {
                    right = item;
                }
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                selectedDoubleListener.selected(left, right);
            }
        });

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
