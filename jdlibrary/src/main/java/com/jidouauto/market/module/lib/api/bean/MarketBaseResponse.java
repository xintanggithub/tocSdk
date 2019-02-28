package com.jidouauto.market.module.lib.api.bean;

/**
 * Created tangxin
 * Time 2018/10/24 5:22 PM
 */
public class MarketBaseResponse {

    /**
     * 是否请求成功
     */
    private boolean result;
    /**
     * 如果出错，相关原因
     */
    private String reason;

    public MarketBaseResponse() {
    }

    public MarketBaseResponse(boolean result, String reason) {
        this.result = result;
        this.reason = reason;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
