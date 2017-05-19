package com.base.library.view.wheel.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.library.R;
import com.base.library.view.wheel.model.WheelItemBean;

import java.util.ArrayList;

/**
 * 作者：王东一
 * 创建时间：2017/4/30.
 */

public class SelectedWheelAdapter extends AbstractWheelTextAdapter {
    /**
     * Constructor
     */
    public ArrayList<String> list;

    public SelectedWheelAdapter(Context context, ArrayList<String> list) {
        super(context, R.layout.adapter_wheel_item, NO_RESOURCE);
        this.list = list;
    }

    @Override
    public View getItem(int index, View cachedView, ViewGroup parent) {
        View view = super.getItem(index, cachedView, parent);
        TextView textCity = (TextView) view.findViewById(R.id.textView);
        textCity.setText(list.get(index));
        return view;
    }

    public int getItemsCount() {
        return list.size();
    }

    @Override
    public CharSequence getItemText(int index) {
        return list.get(index);
    }

}
