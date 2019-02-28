package com.jidouauto.market.module.lib.api.bean;

/**
 * Created tangxin
 * Time 2018/10/24 5:20 PM
 */
public class MarketUpAppStatusRequest {

    private String cid;
    private String channel;
    private String nonceStr;
    private long ts;
    @Deprecated
    private String sign;
    /**
     * APP列表 json串
     * "apps":"[{\"packageName\":\"com.moji.mjweather\",\"versionCode\":\"7060602\"},{\"packageName\":\"com.jidouauto.carletter\",\"versionCode\":\"414\"}
     */
    private String apps;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getChannel() {
        return channel;
    }

    @Deprecated
    public void setChannel(String channel) {
        this.channel = channel;
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

    public String getSign() {
        return sign;
    }

    @Deprecated
    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getApps() {
        return apps;
    }

    public void setApps(String apps) {
        this.apps = apps;
    }

    public static class Apps {
        private String packageName;
        private int versionCode;

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }
    }
}
