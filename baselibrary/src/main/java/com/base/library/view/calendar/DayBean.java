package com.base.library.view.calendar;

import java.io.Serializable;

/**
 * 作者：王东一 on 2016/6/2 16:42
 **/
public class DayBean implements Serializable {
    private int day;
    private boolean IsToday = false;
    private boolean IsSelected = false;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean isSelected() {
        return IsSelected;
    }

    public void setSelected(boolean selected) {
        IsSelected = selected;
    }

    public boolean isToday() {
        return IsToday;
    }

    public void setToday(boolean today) {
        IsToday = today;
    }
}
