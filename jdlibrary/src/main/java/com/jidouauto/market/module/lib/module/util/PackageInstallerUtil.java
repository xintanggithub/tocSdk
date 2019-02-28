package com.jidouauto.market.module.lib.module.util;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.jidouauto.market.module.lib.common.MarketConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by jeffyu on 2018/6/20.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PackageInstallerUtil {
    public static final String TAG = PackageInstallerUtil.class.getName();

    public static void installSilence(final String path) {
        File file = new File(path);
        if (file.isFile()) {
            PackageInstaller packageInstaller = MarketConfig.getContext().getPackageManager()
                    .getPackageInstaller();
            try {
                PackageInfo packageInfo = MarketConfig.getContext().getPackageManager()
                        .getPackageArchiveInfo(path, PackageManager.GET_META_DATA);
                PackageInstaller.SessionParams sessionParams = createSessionParams(packageInfo
                        , file.length());
                sessionParams.setReferrerUri(Uri.fromFile(new File(path)));
                int sessionId = createSession(packageInstaller, sessionParams);
                registerSessionCallback(packageInstaller);
                try {
                    PackageInstaller.Session session = packageInstaller.openSession(sessionId);
                    writeSession(session, path);
                    commitSession(session, sessionId);
                } catch (IOException e) {
                    e.printStackTrace();
                    //fail
                    Log.d(TAG, String.format("jeffyu Error: Failed to writeSession APK file: %s", e));
                }
            } catch (Exception e) {
                Log.d(TAG, String.format("jeffyu Error: Failed to parse APK file: %s", e));
            }
        } else {
            Log.d(TAG, String.format("jeffyu Error: Can't open non-file: %s", path));
        }
    }

    public static void installSilence(final String path, PackageInstaller.SessionCallback callback) {
        File file = new File(path);
        if (file.isFile()) {
            PackageInstaller packageInstaller = MarketConfig.getContext().getPackageManager()
                    .getPackageInstaller();
            try {
                PackageInfo packageInfo = MarketConfig.getContext().getPackageManager()
                        .getPackageArchiveInfo(path, PackageManager.GET_META_DATA);
                PackageInstaller.SessionParams sessionParams = createSessionParams(packageInfo
                        , file.length());
                sessionParams.setReferrerUri(Uri.fromFile(new File(path)));
                int sessionId = createSession(packageInstaller, sessionParams);
                packageInstaller.registerSessionCallback(callback, new Handler());
                try {
                    PackageInstaller.Session session = packageInstaller.openSession(sessionId);
                    writeSession(session, path);
                    commitSession(session, sessionId);
                } catch (IOException e) {
                    e.printStackTrace();
                    //fail
                    Log.d(TAG, String.format("jeffyu Error: Failed to writeSession APK file: %s", e));
                }
            } catch (Exception e) {
                Log.d(TAG, String.format("jeffyu Error: Failed to parse APK file: %s", e));
            }
        } else {
            Log.d(TAG, String.format("jeffyu Error: Can't open non-file: %s", path));
        }
    }

    /**
     * 创建Session参数
     *
     * @return
     */
    private static PackageInstaller.SessionParams createSessionParams(PackageInfo packageInfo, long pkgSize) {
        PackageInstaller.SessionParams sessionParams = new PackageInstaller
                .SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL);
        sessionParams.installFlags |= PackageManager.INSTALL_REPLACE_EXISTING;
        sessionParams.installFlags |= PackageManager.INSTALL_ALL_USERS;
        sessionParams.setAppPackageName(packageInfo.packageName);
        sessionParams.setInstallLocation(packageInfo.installLocation);
        sessionParams.setSize(pkgSize);
        return sessionParams;
    }

    /**
     * 创建session
     *
     * @param installer
     * @param sessionParams
     * @return
     */
    private static int createSession(PackageInstaller installer, PackageInstaller.SessionParams sessionParams) {
        try {
            int sessionId = installer.createSession(sessionParams);
            Log.d(TAG, String.format("jeffyu createSession id %s", sessionId));
            return sessionId;
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, String.format("jeffyu createSession error"));
        return -1;
    }

    /**
     * 将apk写入session
     *
     * @return
     */
    private static int writeSession(PackageInstaller.Session session, String inPath) {
        session.setStagingProgress(0);
        try {
            File file = new File(inPath);
            try (InputStream in = new FileInputStream(file)) {
                long sizeBytes = file.length();
                try (OutputStream out = session
                        .openWrite("base.apk", 0, sizeBytes)) {
                    byte[] buffer = new byte[1024 * 1024];
                    while (true) {
                        int numRead = in.read(buffer);
                        if (numRead == -1) {
                            out.flush();
                            session.fsync(out);
                            break;
                        }
                        out.write(buffer, 0, numRead);
                        if (sizeBytes > 0) {
                            float fraction = ((float) numRead / (float) sizeBytes);
                            session.addProgress(fraction);
                        }
                    }
                }
            }
            Log.d(TAG, String.format("jeffyu writeSession success"));
            return PackageInstaller.STATUS_SUCCESS;
        } catch (IOException | SecurityException e) {
            Log.d(TAG, String.format("jeffyu Could not write package %s", e));
            session.close();
            return PackageInstaller.STATUS_FAILURE;
        }
    }

    private static void commitSession(PackageInstaller.Session session, int sessionId) {
        Intent broadcastIntent = new Intent("com.ijidou.market.INSTALLSTATE");
        broadcastIntent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        broadcastIntent.setPackage("com.ijidou.market");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                MarketConfig.getContext(),
                sessionId,
                broadcastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Log.d(TAG, String.format("jeffyu commit session"));
        session.commit(pendingIntent.getIntentSender());
    }

    private static void registerSessionCallback(PackageInstaller installer) {
        installer.registerSessionCallback(new PackageInstaller.SessionCallback() {
            @Override
            public void onCreated(int sessionId) {
                Log.d(TAG, String.format("jeffyu onCreated id %s", sessionId));
            }

            @Override
            public void onBadgingChanged(int sessionId) {
                Log.d(TAG, String.format("jeffyu onBadgingChanged id %s", sessionId));
            }

            @Override
            public void onActiveChanged(int sessionId, boolean active) {
                Log.d(TAG, String.format("jeffyu onActiveChanged id %s active %s", sessionId, active));
            }

            @Override
            public void onProgressChanged(int sessionId, float progress) {
                Log.d(TAG, String.format("jeffyu onProgressChanged id %s progress %s", sessionId, progress));
            }

            @Override
            public void onFinished(int sessionId, boolean success) {
                Log.d(TAG, String.format("jeffyu onFinished id %s progress %s", sessionId, success));
            }
        }, new Handler());
    }

    public static void unInstall(String pkgName) {
        Log.e(TAG, "jeffyu unInstall pkgName:" + pkgName);
        Context mContext = MarketConfig.getContext();
        Intent intent = new Intent(mContext, mContext.getClass());
        PendingIntent sender = PendingIntent.getActivity(mContext, 0, intent, 0);
        PackageInstaller packageInstaller = mContext.getPackageManager().getPackageInstaller();
        try {
            packageInstaller.uninstall(pkgName, sender.getIntentSender());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
