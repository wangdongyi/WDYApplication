package com.base.library.view.brokenLineView;

import java.io.Serializable;

/**
 * Created by wangdongyi on 2017/2/23.
 * 折线图模型
 */

public class BrokenLineBean implements Serializable {
    private double XAxis;//横坐标
    private double YAxis;//纵坐标
    private String XName;//横坐标名称
    private String YName;//纵坐标名称

    public double getXAxis() {
        return XAxis;
    }

    public void setXAxis(double XAxis) {
        this.XAxis = XAxis;
    }

    public double getYAxis() {
        return YAxis;
    }

    public void setYAxis(double YAxis) {
        this.YAxis = YAxis;
    }

    public String getXName() {
        return XName;
    }

    public void setXName(String XName) {
        this.XName = XName;
    }

    public String getYName() {
        return YName;
    }

    public void setYName(String YName) {
        this.YName = YName;
    }
}
