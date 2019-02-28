package com.jidouauto.market.module;

import android.app.Application;

import com.jidouauto.download.lib.download.JDDownLoadManager;
import com.jidouauto.market.module.lib.common.FileUtils;
import com.jidouauto.market.module.lib.common.MarketConfig;

/**
 * Created tangxin
 * Time 2018/11/5 1:33 PM
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new MarketConfig(this).setJdChannel("audi").debug(true);
        JDDownLoadManager.getInstance().init(this)
                .setDebugLog(true) //开始debug日志
                .setMaxThreadCount(3)//并发任务3个
//                .setPath(FileUtils.getDataPath() + "download/")//设置下载路径
                .setRetryCount(3);//重试次数3次
    }

}
