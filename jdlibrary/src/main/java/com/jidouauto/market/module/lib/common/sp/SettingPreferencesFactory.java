package com.jidouauto.market.module.lib.common.sp;

import android.content.Context;

/**
 * Created tangxin
 * Time 2018/10/11 下午1:47
 */
public class SettingPreferencesFactory extends BaseSharedPreferencesFactory {

    private static final String TAG = "SettingPreferencesFactory";

    public SettingPreferencesFactory(Context context) {
        super(context);
    }

    @Override
    protected String getKey() {
        return "market_lib_sp";
    }
}
