package com.jidouauto.market.module.lib.common;

import android.app.Application;

/**
 * Created tangxin
 * Time 2018/11/2 10:54 AM
 */
public class MarKetManager {

    public static MarketConfig mConfig;

    public static MarketConfig getInstance(Application application) {
        mConfig = new MarketConfig(application);
        return mConfig;
    }
}
