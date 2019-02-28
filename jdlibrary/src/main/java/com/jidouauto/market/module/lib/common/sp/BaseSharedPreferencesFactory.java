package com.jidouauto.market.module.lib.common.sp;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created tangxin
 * Time 2018/10/11 下午1:45
 */
public abstract class BaseSharedPreferencesFactory {

    private Context mContext;
    private int mMode;
    private SharedPreferences mSharedPreferences;

    public BaseSharedPreferencesFactory(Context context) {
        this(context, Context.MODE_PRIVATE);
    }

    public BaseSharedPreferencesFactory(Context context, int mode) {
        mContext = context.getApplicationContext();
        mMode = mode;
    }

    protected abstract String getKey();

    public void clearData() {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.clear();
        editor.apply();
    }

    public void removeByKey(String key) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.remove(key);
        editor.apply();
    }

    public SharedPreferences getSharedPreferences() {
        if (mSharedPreferences == null) {
            mSharedPreferences = mContext.getSharedPreferences(getKey(), mMode);
        }
        return mSharedPreferences;
    }

    public void setBoolValue(String keyName, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(keyName, value);
        editor.apply();
    }

    public boolean getBoolValue(String keyName, boolean defValue) {
        SharedPreferences sp = getSharedPreferences();
        return sp.getBoolean(keyName, defValue);
    }

    public void setStringValue(String keyName, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(keyName, value);
        editor.apply();
    }

    public String getStringValue(String keyName) {
        SharedPreferences sp = getSharedPreferences();
        return sp.getString(keyName, "");
    }

    public void setIntValue(String keyName, int value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(keyName, value);
        editor.apply();
    }

    public int getIntValue(String keyName, int defValue) {
        SharedPreferences sp = getSharedPreferences();
        return sp.getInt(keyName, defValue);
    }
}
