package com.base.library.view.pieChart;

import java.io.Serializable;

/**
 * 作者：王东一 on 2016/6/4 08:29
 **/
public class PieChartItem implements Serializable {
    private int color;
    private int percentage;
    private int starAngle;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public int getStarAngle() {
        return starAngle;
    }

    public void setStarAngle(int starAngle) {
        this.starAngle = starAngle;
    }
}