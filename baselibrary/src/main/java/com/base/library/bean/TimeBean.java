package com.base.library.bean;

import java.io.Serializable;

/**
 * 作者：王东一 on 2016/5/25 14:12
 **/
public class TimeBean implements Serializable {
    private int date;
    private String dateName;

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getDateName() {
        return dateName;
    }

    public void setDateName(String dateName) {
        this.dateName = dateName;
    }
}
