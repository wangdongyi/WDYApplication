package com.base.library.view.wheel.model;

import java.io.Serializable;

/**
 * 作者：王东一
 * 创建时间：2017/5/1.
 */

public class WheelItemBean implements Serializable {
    private int position;
    private String text;
    private boolean isSelected;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
