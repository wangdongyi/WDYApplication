package com.base.library.eventBus;

/**
 * 作者：王东一
 * 创建时间：2017/8/24.
 */

public class MessageEvent {
    private String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
