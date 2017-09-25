package com.base.library.permission;

import java.io.Serializable;

/**
 * 作者：王东一
 * 创建时间：2017/9/12.
 */

public class PermissionBean implements Serializable {
    private String group;
    private String permission;
    private boolean have = false;

    public boolean isHave() {
        return have;
    }

    public void setHave(boolean have) {
        this.have = have;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
