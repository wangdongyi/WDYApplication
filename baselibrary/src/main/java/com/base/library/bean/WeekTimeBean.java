package com.base.library.bean;

import java.io.Serializable;

/**
 * Created by wangdongyi on 2017/1/9.
 */

public class WeekTimeBean implements Serializable{
    private int year;
    private int month;
    private int day;
    private int week;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }
}
