package com.jidouauto.market.module.lib.api.bean;


import com.jidouauto.market.module.lib.common.StringUtils;

import java.io.Serializable;

public class PackageBean implements Serializable {

    public int apiLevel;
    public int versionId;
    public String id;
    public String name;
    public String name_en;
    public String packageName;
    public String versionName;
    public String versionCode;
    public String url;
    public String checksum;
    public long size;
    public String icon;

    // added property
    public int downloadStatus;
    private long downloadSize;
    private long totalSize;

    private int needUpdate;
    private int installed;

    //local
    private String diffUrl;

    //fileDownloader ID
    public int fileDownloaderId = -1;

    public String getDiffUrl() {
        return diffUrl;
    }

    public void setDiffUrl(String diffUrl) {
        this.diffUrl = diffUrl;
    }

    public long getDownloadSize() {
        return downloadSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setDownloadSize(long downloadSize) {
        this.downloadSize = downloadSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public int getNeedUpdate() {
        return needUpdate;
    }

    public void setNeedUpdate(int needUpdate) {
        this.needUpdate = needUpdate;
    }

    public int getInstalled() {
        return installed;
    }

    public void setInstalled(int installed) {
        this.installed = installed;
    }

    public String localeName() {
        return name;
    }

    public String formatSize() {
        return StringUtils.getFileSize(size);
    }


    public int percent() {
        int percent = (int) (100.0 * getDownloadSize() / getTotalSize());
        if (percent > 100) {   // 临时规避
            return 100;
        } else {
            return percent;
        }
    }

    public String formatPercent() {
        if (totalSize == 0) {
            return "0 %";
        } else {
            return percent() + " %";
        }
    }

}
