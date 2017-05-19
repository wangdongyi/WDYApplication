package com.base.library.view.histogram;

import java.io.Serializable;

/**
 * 作者：王东一 on 2016/6/3 14:17
 **/
public class HistogramItemBean implements Serializable {
    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private int color;
    private int height;
    private int position;
}
