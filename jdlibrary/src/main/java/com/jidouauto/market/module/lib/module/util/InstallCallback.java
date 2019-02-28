package com.jidouauto.market.module.lib.module.util;

/**
 * Created tangxin
 * Time 2018/12/18 7:23 PM
 */
public interface InstallCallback {

    void success(String packageName);

    void error(Throwable throwable);

}
