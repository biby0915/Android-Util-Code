package com.zby.myapplication.test;

import android.app.Application;

import com.zby.util.AppUtil;
import com.zby.util.PermissionUtil;
import com.zby.util.constant.PermissionConstant;

/**
 * author ZhuBingYang
 * date   2019-09-06
 */
public class PermissionTest implements Runnable {
    @Override
    public void run() {
        Application application = AppUtil.getApplication();
        PermissionUtil.hasAllPermissions(application, PermissionConstant.GROUP_STORAGE);
        PermissionUtil.hasAllPermissions(application, PermissionConstant.GROUP_CAMERA);
        PermissionUtil.hasAllPermissions(application, PermissionConstant.GROUP_CALENDAR);
        PermissionUtil.hasAllPermissions(application, PermissionConstant.GROUP_CONTACTS);
        PermissionUtil.hasAllPermissions(application, PermissionConstant.GROUP_LOCATION);
        PermissionUtil.hasAllPermissions(application, PermissionConstant.GROUP_SENSORS);
    }
}
