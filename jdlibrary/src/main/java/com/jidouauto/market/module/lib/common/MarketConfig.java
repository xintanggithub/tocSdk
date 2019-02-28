package com.jidouauto.market.module.lib.common;

import android.app.Application;

import com.jidouauto.market.module.lib.common.sp.SpHelper;

import java.util.Locale;

/**
 * Created tangxin
 * Time 2018/11/2 10:37 AM
 */
public class MarketConfig {

    private static final String TAG = "Market Config";
    public static String KEY_INSTALL_START = "def_install_start_key";
    public static String KEY_INSTALL_PROGRESS = "def_install_progress_key";
    public static String KEY_INSTALL_FAIL = "def_install_fail_key";
    public static String KEY_INSTALL_SUCCESS = "def_install_success_key";
    public static String KEY_INSTALL_COMPLETE = "def_instal_complete_key";

    private static String JD_CHANNEL;
    private static Application sApplication;
    private static SpHelper mSp;

    public MarketConfig(Application application) {
        sApplication = application;
        mSp = new SpHelper();
        KEY_INSTALL_START = KEY_INSTALL_START + application.getClass().getName();
        KEY_INSTALL_PROGRESS = KEY_INSTALL_PROGRESS + application.getClass().getName();
        KEY_INSTALL_FAIL = KEY_INSTALL_FAIL + application.getClass().getName();
        KEY_INSTALL_SUCCESS = KEY_INSTALL_SUCCESS + application.getClass().getName();
        KEY_INSTALL_COMPLETE = KEY_INSTALL_COMPLETE + application.getClass().getName();
    }

    public static Application getContext() {
        return sApplication;
    }

    public static SpHelper getSp() {
        return mSp;
    }

    public MarketConfig setJdChannel(String channel) {
        getSp().setChannel(channel);
        return this;
    }

    public MarketConfig debug(boolean isOpen) {
        LogUtils.setDebuggable(isOpen);
        return this;
    }

    public static String getJdChannel() {
        if (StringUtils.isEmpty(JD_CHANNEL)) {
            return getSp().getChannel();
        }
        return JD_CHANNEL;
    }

    public MarketConfig setAuthorities(String authorities) {
        getSp().setAuthorities(authorities);
        return this;
    }

    public static String getAuthorities() {
        return getSp().getAuthorities();
    }

    /**
     * 获取语言环境
     *
     * @return 如 zh_CN 、zh_TW
     */
    public static String getLanguageCountry() {
        Locale locale = sApplication.getResources().getConfiguration().locale;
        return locale.getLanguage() + "_" + locale.getCountry();
    }

    /**
     * 获取语言环境
     *
     * @return 如 zh 、en
     */
    public static String getLanguage() {
        return sApplication.getResources().getConfiguration().locale.getLanguage();
    }


}
