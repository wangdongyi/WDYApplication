package com.base.library.permission;

import android.Manifest;
import android.annotation.TargetApi;
import android.os.Build;

import com.base.library.application.BaseApplication;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 作者：王东一
 * 创建时间：2017/9/12.
 */

public class PermissionsList {
    private static PermissionsList permissionsList;

    private ArrayList<PermissionBean> permissionsArrayList = new ArrayList<>();

    public static PermissionsList getPermissionsList() {
        if (permissionsList == null) {
            synchronized (PermissionsList.class) {
                if (permissionsList == null)
                    permissionsList = new PermissionsList();
            }
        }
        return permissionsList;
    }

    private PermissionsList() {
        permissionsArrayList= (ArrayList<PermissionBean>) BaseApplication.getSharedPreferencesUtil().getList("permissionsArrayList", new ArrayList<PermissionBean>());
        if(permissionsArrayList.size()>0){

        }else {
            PermissionBean b1 = new PermissionBean();
            b1.setGroup("android.permission-group.CALENDAR");
            b1.setPermission(Manifest.permission.READ_CALENDAR);
            permissionsArrayList.add(b1);

            PermissionBean b2 = new PermissionBean();
            b2.setGroup("android.permission-group.CALENDAR");
            b2.setPermission(Manifest.permission.WRITE_CALENDAR);
            permissionsArrayList.add(b2);

            PermissionBean b3 = new PermissionBean();
            b3.setGroup("android.permission-group.CAMERA");
            b3.setPermission(Manifest.permission.CAMERA);
            permissionsArrayList.add(b3);

            PermissionBean b4 = new PermissionBean();
            b4.setGroup("android.permission-group.CONTACTS");
            b4.setPermission(Manifest.permission.READ_CONTACTS);
            permissionsArrayList.add(b4);

            PermissionBean b5 = new PermissionBean();
            b5.setGroup("android.permission-group.CONTACTS");
            b5.setPermission(Manifest.permission.WRITE_CONTACTS);
            permissionsArrayList.add(b5);

            PermissionBean b6 = new PermissionBean();
            b6.setGroup("android.permission-group.CONTACTS");
            b6.setPermission(Manifest.permission.GET_ACCOUNTS);
            permissionsArrayList.add(b6);

            PermissionBean b7 = new PermissionBean();
            b7.setGroup("android.permission-group.LOCATION");
            b7.setPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            permissionsArrayList.add(b7);

            PermissionBean b8 = new PermissionBean();
            b8.setGroup("android.permission-group.LOCATION");
            b8.setPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            permissionsArrayList.add(b8);

            PermissionBean b9 = new PermissionBean();
            b9.setGroup("android.permission-group.MICROPHONE");
            b9.setPermission(Manifest.permission.RECORD_AUDIO);
            permissionsArrayList.add(b9);

            PermissionBean b10 = new PermissionBean();
            b10.setGroup("android.permission-group.PHONE");
            b10.setPermission(Manifest.permission.READ_PHONE_STATE);
            permissionsArrayList.add(b10);

            PermissionBean b11 = new PermissionBean();
            b11.setGroup("android.permission-group.PHONE");
            b11.setPermission(Manifest.permission.CALL_PHONE);
            permissionsArrayList.add(b11);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                PermissionBean b12 = new PermissionBean();
                b12.setGroup("android.permission-group.PHONE");
                b12.setPermission(Manifest.permission.READ_CALL_LOG);
                permissionsArrayList.add(b12);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                PermissionBean b13 = new PermissionBean();
                b13.setGroup("android.permission-group.PHONE");
                b13.setPermission(Manifest.permission.WRITE_CALL_LOG);
                permissionsArrayList.add(b13);
            }

            PermissionBean b14 = new PermissionBean();
            b14.setGroup("android.permission-group.PHONE");
            b14.setPermission(Manifest.permission.ADD_VOICEMAIL);
            permissionsArrayList.add(b14);

            PermissionBean b15 = new PermissionBean();
            b15.setGroup("android.permission-group.PHONE");
            b15.setPermission(Manifest.permission.USE_SIP);
            permissionsArrayList.add(b15);

            PermissionBean b16 = new PermissionBean();
            b16.setGroup("android.permission-group.PHONE");
            b16.setPermission(Manifest.permission.PROCESS_OUTGOING_CALLS);
            permissionsArrayList.add(b16);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                PermissionBean b17 = new PermissionBean();
                b17.setGroup("android.permission-group.SENSORS");
                b17.setPermission(Manifest.permission.BODY_SENSORS);
                permissionsArrayList.add(b17);
            }

            PermissionBean b18 = new PermissionBean();
            b18.setGroup("android.permission-group.SMS");
            b18.setPermission(Manifest.permission.SEND_SMS);
            permissionsArrayList.add(b18);

            PermissionBean b19 = new PermissionBean();
            b19.setGroup("android.permission-group.SMS");
            b19.setPermission(Manifest.permission.RECEIVE_SMS);
            permissionsArrayList.add(b19);

            PermissionBean b20 = new PermissionBean();
            b20.setGroup("android.permission-group.SMS");
            b20.setPermission(Manifest.permission.READ_SMS);
            permissionsArrayList.add(b20);

            PermissionBean b21 = new PermissionBean();
            b21.setGroup("android.permission-group.SMS");
            b21.setPermission(Manifest.permission.RECEIVE_WAP_PUSH);
            permissionsArrayList.add(b21);

            PermissionBean b22 = new PermissionBean();
            b22.setGroup("android.permission-group.SMS");
            b22.setPermission(Manifest.permission.RECEIVE_MMS);
            permissionsArrayList.add(b22);

            PermissionBean b23 = new PermissionBean();
            b23.setGroup("android.permission-group.SMS");
            b23.setPermission(Manifest.permission.USE_SIP);
            permissionsArrayList.add(b23);

//        PermissionBean b24 = new PermissionBean();
//        b24.setGroup("android.permission-group.SMS");
//        b24.setPermission(Manifest.permission.READ_CELL_BROADCASTS);
//        permissionsArrayList.add(b24);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                PermissionBean b25 = new PermissionBean();
                b25.setGroup("android.permission-group.STORAGE");
                b25.setPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                permissionsArrayList.add(b25);
            }

            PermissionBean b26 = new PermissionBean();
            b26.setGroup("android.permission-group.CALENSTORAGEAR");
            b26.setPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            permissionsArrayList.add(b26);
        }

    }

    public ArrayList<PermissionBean> getPermissionsArrayList() {
        return permissionsArrayList;
    }

}
