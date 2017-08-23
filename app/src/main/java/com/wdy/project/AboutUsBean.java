package com.wdy.project;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 作者：王东一
 * 创建时间：2017/8/23.
 */

public class AboutUsBean implements Serializable {
    /**
     * status : 0
     * msg : 获取成功
     * data : {"content":"内容"}
     */

    @SerializedName("status")
    private String status;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private DataBean data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * content : 内容
         */

        @SerializedName("content")
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
