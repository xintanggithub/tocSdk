package com.jidouauto.market.module.lib.api.bean;

/**
 * Created tangxin
 * Time 2018/10/24 5:32 PM
 */
public class MarketDiffUrlResponse extends MarketBaseResponse {

    private String url;
    private String patchUrl;
    private String md5;

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPatchUrl() {
        return patchUrl;
    }

    public void setPatchUrl(String patchUrl) {
        this.patchUrl = patchUrl;
    }
}
