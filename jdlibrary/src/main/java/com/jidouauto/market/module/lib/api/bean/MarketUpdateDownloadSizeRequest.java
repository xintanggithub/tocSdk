package com.jidouauto.market.module.lib.api.bean;

/**
 * Created tangxin
 * Time 2018/10/24 5:28 PM
 */
public class MarketUpdateDownloadSizeRequest {
    private long versionId;
    private String nonceStr;
    private long ts;
    @Deprecated
    private String sign;

    public long getVersionId() {
        return versionId;
    }

    public void setVersionId(long versionId) {
        this.versionId = versionId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    @Deprecated
    public String getSign() {
        return sign;
    }

    @Deprecated
    public void setSign(String sign) {
        this.sign = sign;
    }
}
