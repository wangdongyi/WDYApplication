package com.base.library.bean;

import java.io.Serializable;

/**
 * 作者：王东一
 * 创建时间：2017/4/6.
 */

public class AdvertisementBean implements Serializable {
    private int pictureSelected;
    private int pictureUnSelected;
    private boolean isSelected;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPictureSelected() {
        return pictureSelected;
    }

    public void setPictureSelected(int pictureSelected) {
        this.pictureSelected = pictureSelected;
    }

    public int getPictureUnSelected() {
        return pictureUnSelected;
    }

    public void setPictureUnSelected(int pictureUnSelected) {
        this.pictureUnSelected = pictureUnSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
