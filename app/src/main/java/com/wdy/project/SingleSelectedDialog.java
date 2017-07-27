package com.wdy.project;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.library.listen.NoDoubleClickListener;
import com.base.library.util.CodeUtil;
import com.base.library.view.pickerView.PickerView;

import java.util.ArrayList;

/**
 * 作者：王东一
 * 创建时间：2017/7/27.
 */

public class SingleSelectedDialog {
    @SuppressLint("StaticFieldLeak")
    private static SingleSelectedDialog mInstance;

    public interface selected {
        void handle(String selected);
    }

    private Context context;
    private AlertDialog mAlertDialog;
    private selected selected;
    private ViewHolder viewHolder;
    private View layout;
    private String select;
    private int selectNum;
    private ArrayList<String> list = new ArrayList<>();

    public static SingleSelectedDialog getInstance() {
        if (mInstance == null) {
            synchronized (SingleSelectedDialog.class) {
                if (mInstance == null) {
                    mInstance = new SingleSelectedDialog();
                }
            }
        }
        return mInstance;
    }

    public void with(Context context, ArrayList<String> list, int selectNum, selected selected) {
        this.context = context;
        this.selected = selected;
        this.list = list;
        this.selectNum = selectNum;
        initDialog();
    }


    private void initDialog() {
        if (mAlertDialog == null) {
            LayoutInflater inflaterDl = LayoutInflater.from(context);
            layout = inflaterDl.inflate(R.layout.dialog_single_selsected, null);
            mAlertDialog = new AlertDialog.Builder(context, R.style.PhotoDialog).create();
            viewHolder = new ViewHolder(layout);
            viewHolder.buttonCancel.setOnClickListener(new NoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    dismiss();
                }
            });
            viewHolder.buttonDone.setOnClickListener(new NoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    selected.handle(select);
                    dismiss();
                }
            });
        }
        initArrayList();
        mAlertDialog.show();
        WindowManager.LayoutParams lp = mAlertDialog.getWindow().getAttributes();
        lp.width = CodeUtil.getScreenWidth(context); //设置宽度
        lp.height = CodeUtil.getScreenHeight(context);
        mAlertDialog.getWindow().setAttributes(lp);
        mAlertDialog.getWindow().setContentView(layout, lp);

    }

    private void initArrayList() {
        viewHolder.pickerViewMonth.setData(list);
        viewHolder.pickerViewMonth.setSelected(selectNum);
        viewHolder.pickerViewMonth.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String s) {
                select = s;
            }
        });
    }


    private void dismiss() {
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
        }
    }

    private static class ViewHolder {
        View rootView;
        TextView buttonCancel;
        TextView buttonDone;
        PickerView pickerViewMonth;
        LinearLayout layoutBottom;
        public RelativeLayout main;

        ViewHolder(View rootView) {
            this.rootView = rootView;
            this.buttonCancel = (TextView) rootView.findViewById(R.id.button_cancel);
            this.buttonDone = (TextView) rootView.findViewById(R.id.button_done);
            this.pickerViewMonth = (PickerView) rootView.findViewById(R.id.pickerView_month);
            this.layoutBottom = (LinearLayout) rootView.findViewById(R.id.layout_bottom);
            this.main = (RelativeLayout) rootView.findViewById(R.id.main);
        }

    }
}
