package com.jidouauto.market.module.lib.api;

import com.jidouauto.market.module.lib.api.bean.MarketBaseResponse;
import com.jidouauto.market.module.lib.common.LogUtils;

/**
 * Created tangxin
 * Time 2018/10/13 下午6:19
 */
public abstract class ApiStatus<T extends MarketBaseResponse> implements BaseStatus<T> {


    @Override
    public void request(String params) {
        LogUtils.d("MarketApi params【" + params + "】");
    }

    @Override
    public void before() {
    }

    @Override
    public void error(Throwable throwable) {
    }

    @Override
    public void tokenInvalid(Throwable throwable) {
    }

    @Override
    public void isEmpty() {
    }
}
