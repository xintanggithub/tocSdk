package com.jidouauto.market.module.lib.module.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageInstallObserver;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.jidouauto.market.module.lib.common.LogUtils;
import com.jidouauto.market.module.lib.common.MarketConfig;
import com.jidouauto.market.module.lib.common.MarketUtils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import zlc.season.rxdownload3.helper.UtilsKt;

/**
 * Created tangxin
 * Time 2018/10/22 上午11:47
 */
public class MarketInstallUtil {
    private static final String TAG = "MarketInstallUtil";

    private String apkPackageName;

//    private SettingPreferencesFactory mSp;

    public static MarketInstallUtil mMarketInstallUtil;
    private InstallCallback installCallback;

    public static MarketInstallUtil getInit() {
        if (null == mMarketInstallUtil) {
            mMarketInstallUtil = new MarketInstallUtil();
        }
        return mMarketInstallUtil;

    }

    public MarketInstallUtil callback(InstallCallback installCallback) {
        this.installCallback = installCallback;
        return mMarketInstallUtil;
    }

    public InstallCallback getCallback() {
        if (null != installCallback) {
            return installCallback;
        }
        return new InstallCallback() {
            @Override
            public void success(String packageName) {

            }

            @Override
            public void error(Throwable throwable) {

            }
        };
    }

    public MarketInstallUtil() {
//        mSp = new SettingPreferencesFactory(MarketConfig.getContext());
    }

    /**
     * 安装方法
     *
     * @param path apk路径
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void install(String path) {
        File apkFile = new File(path);
        apkPackageName = UtilsKt.getPackageName(MarketConfig.getContext(), apkFile);
//        //是否发起了重复安装
//        int installCount = mSp.getIntValue(apkPackageName, 0);
//        if (installCount > 0) {
//            return;
//        } else {
//            mSp.setIntValue(apkPackageName, installCount + 1);
//        }
        LogUtils.d("start install: " + apkPackageName);
        ApplicationInfo applicationInfo = MarketConfig.getContext().getApplicationInfo();
        boolean isRootPower = isRootPower(applicationInfo);
        if (isRootPower) {
            if (Build.VERSION.SDK_INT >= 28) {
                PackageInstallerUtil.installSilence(apkFile.getPath(), new PackageInstaller.SessionCallback() {
                    @Override
                    public void onCreated(int sessionId) {
                    }

                    @Override
                    public void onBadgingChanged(int sessionId) {
                    }

                    @Override
                    public void onActiveChanged(int sessionId, boolean active) {
                    }

                    @Override
                    public void onProgressChanged(int sessionId, float progress) {
                    }

                    @Override
                    public void onFinished(int sessionId, boolean success) {
                        if (success) {
                            getCallback().success(apkPackageName);
                        } else {
                            getCallback().error(new Throwable("install error !"));
                        }
                    }
                });
            } else {
                Uri uri = Uri.fromFile(apkFile);
                PackageManager packageManager = MarketConfig.getContext().getPackageManager();
                try {
                    @SuppressLint("PrivateApi") Method method = packageManager.getClass()
                            .getDeclaredMethod("installPackage", Uri.class
                                    , IPackageInstallObserver.class, int.class, String.class);
                    method.setAccessible(true);
                    try {
                        method.invoke(packageManager, uri, new IPackageInstallObserver.Stub() {
                            @Override
                            public void packageInstalled(String pkgName, int resultCode) {
//                                mSp.setIntValue(apkPackageName, 0);
                                getCallback().success(pkgName);
                            }
                        }, 2, MarketConfig.getContext().getPackageName());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                        getCallback().error(new Throwable(e));
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                    getCallback().error(new Throwable(e));
                }
            }
        } else {
            defInstall(path);
        }
    }

    /**
     * 安装方法
     *
     * @param path apk路径
     */
    public void defInstall(String path) {
        if (Build.VERSION.SDK_INT <= 23) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MarketConfig.getContext().startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            String authority = MarketConfig.getAuthorities();
            LogUtils.d("authority:" + authority);
            Uri apkUri = FileProvider.getUriForFile(MarketConfig.getContext(), authority
                    , new File(path));
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
            MarketConfig.getContext().startActivity(install);
        }
    }

    /**
     * 卸载方法
     *
     * @param apkPackageName apk包名
     */
    public void unInstall(String apkPackageName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            PackageInstallerUtil.unInstall(apkPackageName);
        } else {
            MarketUtils.uninstall(apkPackageName);
        }
    }

    @SuppressLint("PrivateApi")
    public static boolean isRootPower(ApplicationInfo applicationInfo) {
        try {
            boolean isPrivilegedApp = applicationInfo.isPrivilegedApp();
//            boolean isSystemApp = applicationInfo.isSystemApp();
            LogUtils.d("MarketAppStatus isPrivilegedApp:" + isPrivilegedApp);
//            LogUtils.d("MarketAppStatus isSystemApp:" + isSystemApp);
//            return isPrivilegedApp || isSystemApp;
            return isPrivilegedApp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
