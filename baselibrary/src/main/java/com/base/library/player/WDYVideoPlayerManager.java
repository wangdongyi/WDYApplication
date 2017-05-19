package com.base.library.player;

/**
 * 作者：王东一
 * 创建时间：2017/4/10.
 */

public class WDYVideoPlayerManager {
    public static WDYVideoPlayer FIRST_FLOOR_JCVD;
    public static WDYVideoPlayer SECOND_FLOOR_JCVD;

    public static void setFirstFloor(WDYVideoPlayer jcVideoPlayer) {
        FIRST_FLOOR_JCVD = jcVideoPlayer;
    }

    public static void setSecondFloor(WDYVideoPlayer jcVideoPlayer) {
        SECOND_FLOOR_JCVD = jcVideoPlayer;
    }

    public static WDYVideoPlayer getFirstFloor() {
        return FIRST_FLOOR_JCVD;
    }

    public static WDYVideoPlayer getSecondFloor() {
        return SECOND_FLOOR_JCVD;
    }

    public static WDYVideoPlayer getCurrentWDYPlayer() {
        if (getSecondFloor() != null) {
            return getSecondFloor();
        }
        return getFirstFloor();
    }

    public static void completeAll() {
        if (SECOND_FLOOR_JCVD != null) {
            SECOND_FLOOR_JCVD.onCompletion();
            SECOND_FLOOR_JCVD = null;
        }
        if (FIRST_FLOOR_JCVD != null) {
            FIRST_FLOOR_JCVD.onCompletion();
            FIRST_FLOOR_JCVD = null;
        }
    }
}
