package com.base.library.permission;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.base.library.manufacturerUtil.Manufacturer;

import static com.base.library.manufacturerUtil.ManufacturerUtil.getManufactureUtil;


/**
 * Created by wangdongyi on 2017/2/27.
 * 权限校验
 */

public class PermissionsChecker {
    private final Context mContext;

    public PermissionsChecker(Context context) {
        mContext = context.getApplicationContext();
    }

    // 判断权限集合
    public boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    // 判断是否缺少权限
    private boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_DENIED;
    }

    public boolean backPermission(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            if (getManufactureUtil() == Manufacturer.XIAOMI) {
                AppOpsManager appOpsManager = (AppOpsManager) mContext.getSystemService(Context.APP_OPS_SERVICE);
                int checkOp = appOpsManager.checkOp(AppOpsManager.OPSTR_FINE_LOCATION, Process.myUid(), mContext.getPackageName());
                if (checkOp == AppOpsManager.MODE_IGNORED) {
                    // 权限被拒绝了 .
                } else {

                }
            }else {

            }
        }
        return false;
    }
}
