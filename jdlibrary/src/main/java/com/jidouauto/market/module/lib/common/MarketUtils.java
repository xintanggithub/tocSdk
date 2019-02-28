package com.jidouauto.market.module.lib.common;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created tangxin
 * Time 2018/11/6 8:56 PM
 */
public class MarketUtils {

    private static final String TAG = "AppStatus";
    private static final String SYSTEM_APP_PACKAGE_HEAD = "com.jidouauto.market.lib";

    /**
     * 未安装
     */
    public static final int APP_STATUS_NOT_INSTALL = 1;
    /**
     * 已安装，需要更新
     */
    public static final int APP_STATUS_NEED_UPDATE = 2;
    /**
     * 已安装,不需要更新
     */
    public static final int APP_STATUS_ALREADY_INSTALL = 3;

    /**
     * 获取应用列表
     *
     * @param context 上下文
     * @return <b>result 返回系统应用以外的已安装应用列表</b>
     */
    public static Map<String, PackageInfo> getLocalInstallAppList(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Map<String, PackageInfo> localAppMap = new HashMap<>();
        try {
            List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
            for (int i = 0; i < packageInfoList.size(); i++) {
                PackageInfo packageInfo = packageInfoList.get(i);
                //过滤掉系统app
                if ((ApplicationInfo.FLAG_SYSTEM & packageInfo.applicationInfo.flags) != 0 &&
                        !TextUtils.isEmpty(packageInfo.packageName) &&
                        !packageInfo.packageName.startsWith(SYSTEM_APP_PACKAGE_HEAD)) {
                    continue;
                }
                localAppMap.put(packageInfo.packageName, packageInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localAppMap;
    }

    /**
     * 获取版本号 versionCode
     *
     * @param context     上下文
     * @param packageName 需要获取版本号的包名
     * @return <b>版本号versionCode</b>
     */
    public static int getAppVersionCode(Context context, String packageName) {
        int versioncode = -1;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            versioncode = pi.versionCode;
        } catch (Exception e) {
            LogUtils.e(TAG + ":VersionInfo Exception" + e);
        }
        return versioncode;
    }

    /**
     * 判断应用是否已安装
     *
     * @param context 上下文
     * @param pkgName 需要判断是否已安装应用的包名
     * @return <b>true 已安装 false 未安装<b/>
     */
    public static boolean isInstalled(Context context, String pkgName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName
                    , PackageManager.GET_GIDS);
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.e(TAG + ":isInstalled error :" + JSON.toJSONString(e));
        }
        return packageInfo != null;
    }

    /**
     * 判断应用是否需要更新
     *
     * @param context     上下文
     * @param packageName 需要校验的包名
     * @param versionCode 用以对比已安装APP版本的versionCode，以此确认是否需要更新
     * @return <b>true 需要更新 false 不需要更新</b>
     */
    public static boolean isUpdate(Context context, String packageName, int versionCode) {
        //如果比versionCode大，或者相等，则不需要更新
        return !(getAppVersionCode(context, packageName) < versionCode);
    }

    /**
     * 根据包名判断应用状态
     *
     * @param context     上下文
     * @param packageName 需要校验的应用包名
     * @return <b>APP_STATUS_NOT_INSTALL<b/>   未安装<br>
     * <b>APP_STATUS_NEED_UPDATE</b>    需要更新<br>
     * <b>APP_STATUS_ALREADY_INSTALL</b>   已安装<br>
     */
    public static int checkAppStatus(Context context, String packageName, int versionCode) {
        //判断应用是否已安装
        if (isInstalled(context, packageName)) {//已安装
            //判断应用是否需要更新
            if (!isUpdate(context, packageName, versionCode)) {//如果需要更新
                return APP_STATUS_NEED_UPDATE;
            } else {//已是最新
                return APP_STATUS_ALREADY_INSTALL;
            }
        } else {//未安装
            return APP_STATUS_NOT_INSTALL;
        }
    }

    /**
     * 卸载指定包名的应用
     *
     * @param packageName
     */
    public static void uninstall(String packageName) {
        Uri packageURI = Uri.parse("package:".concat(packageName));
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(packageURI);
        MarketConfig.getContext().startActivity(intent);
    }
}
