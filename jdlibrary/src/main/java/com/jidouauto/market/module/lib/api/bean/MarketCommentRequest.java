package com.jidouauto.market.module.lib.api.bean;

/**
 * Created tangxin
 * Time 2018/10/24 5:25 PM
 */
public class MarketCommentRequest {
    private String token;//	是		JDO Token
    private long versionId;//		是		应用版本ID
    private String channel;//		是		渠道名称
    private int rate;//		否		评分
    private String content;//		否		评论内容
    private String nonceStr;//		否		随机字符串
    private long ts;//		是		时间戳
    private String sign;//

    public String getToken() {
        return token;
    }

    /**
     * JDO Token
     */
    public void setToken(String token) {
        this.token = token;
    }

    public long getVersionId() {
        return versionId;
    }

    /**
     * 应用版本ID
     */
    public void setVersionId(long versionId) {
        this.versionId = versionId;
    }

    public String getChannel() {
        return channel;
    }

    /**
     * 渠道名称
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getRate() {
        return rate;
    }

    /**
     * 评分
     */
    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

}
