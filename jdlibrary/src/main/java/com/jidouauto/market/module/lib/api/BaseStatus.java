package com.jidouauto.market.module.lib.api;

import com.jidouauto.market.module.lib.api.bean.MarketBaseResponse;

/**
 * Created tangxin
 * Time 2018/11/2 11:18 AM
 */
public interface BaseStatus<T extends MarketBaseResponse> {

    void request(String params);

    void before();

    void isEmpty();

    void success(T data);

    void error(Throwable throwable);

    void tokenInvalid(Throwable throwable);

}
