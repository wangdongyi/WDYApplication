package com.base.library.bean;

import java.io.Serializable;

/**
 * Created by wangdongyi on 2017/1/6.
 * http请求头文件
 */

public class HeaderBean implements Serializable {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
