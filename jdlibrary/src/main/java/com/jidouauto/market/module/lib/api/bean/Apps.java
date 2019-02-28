package com.jidouauto.market.module.lib.api.bean;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * Created tangxin
 * Time 2018/11/6 6:01 PM
 */
public class Apps {

    private List<AppItem> appItems;

    public List<AppItem> getAppItems() {
        return appItems;
    }

    public void setAppItems(List<AppItem> appItems) {
        this.appItems = appItems;
    }

    public String getApps() {
        return JSON.toJSONString(appItems == null ? new ArrayList<>() : appItems);
    }

    public static class AppItem {
        private String packageName;
        private Integer versionCode;

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public Integer getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(Integer versionCode) {
            this.versionCode = versionCode;
        }
    }
}
