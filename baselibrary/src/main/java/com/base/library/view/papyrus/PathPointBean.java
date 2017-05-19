package com.base.library.view.papyrus;

import android.graphics.Path;

import java.io.Serializable;

/**
 * Created by wangdongyi on 2016/12/8.
 */

public class PathPointBean implements Serializable {
    private int position;

    private Path path;

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


}
