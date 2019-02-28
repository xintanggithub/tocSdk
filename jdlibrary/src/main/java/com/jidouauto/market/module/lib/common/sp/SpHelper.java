package com.jidouauto.market.module.lib.common.sp;

import com.jidouauto.market.module.lib.common.MarketConfig;

/**
 * Created tangxin
 * Time 2018/11/2 10:46 AM
 */
public class SpHelper {

    private static final String LANGUAGE_SP_KEY = "jd_language_sp_key";
    private static final String JD_CHANNEL_SP_KEY = "jd_channel_sp_key";
    private static final String JD_AUTHORITIES_SP_KEY = "jd_authorities_sp_key";

    private SettingPreferencesFactory mSp;

    public SettingPreferencesFactory getSp() {
        return null == mSp ? new SettingPreferencesFactory(MarketConfig.getContext()) : mSp;
    }

    public void setSp(SettingPreferencesFactory mSp) {
        this.mSp = mSp;
    }

    public void setLanguageCache(String language) {
        getSp().setStringValue(LANGUAGE_SP_KEY, language);
    }

    public String getLanguageCache() {
        return getSp().getStringValue(LANGUAGE_SP_KEY);
    }

    public void setChannel(String channel) {
        getSp().setStringValue(JD_CHANNEL_SP_KEY, channel);
    }

    public void setAuthorities(String authorities) {
        getSp().setStringValue(JD_AUTHORITIES_SP_KEY, authorities);
    }

    public String getAuthorities() {
        return getSp().getStringValue(JD_AUTHORITIES_SP_KEY);
    }

    public String getChannel() {
        return getSp().getStringValue(JD_CHANNEL_SP_KEY);
    }

}
