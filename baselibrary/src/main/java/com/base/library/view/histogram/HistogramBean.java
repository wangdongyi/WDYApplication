package com.base.library.view.histogram;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 作者：王东一 on 2016/6/3 14:23
 **/
public class HistogramBean implements Serializable {
    private int position;
    private String name;
    private int histogramNum;
    private ArrayList<HistogramItemBean> arrayList;

    public int getHistogramNum() {
        return histogramNum;
    }

    public void setHistogramNum(int histogramNum) {
        this.histogramNum = histogramNum;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<HistogramItemBean> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<HistogramItemBean> arrayList) {
        this.arrayList = arrayList;
    }
}
