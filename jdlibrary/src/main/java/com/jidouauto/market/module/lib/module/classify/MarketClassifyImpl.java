package com.jidouauto.market.module.lib.module.classify;

import com.jidouauto.market.module.lib.api.ApiStatus;
import com.jidouauto.market.module.lib.api.bean.MarketClassifyResponse;
import com.jidouauto.market.module.lib.module.base.BasePresenterImpl;
import com.jidouauto.market.module.lib.module.base.MarketViewModel;

/**
 * Created tangxin
 * Time 2018/11/5 3:10 PM
 */
public class MarketClassifyImpl extends BasePresenterImpl<MarketClassifyContract.MarketClassifyView> implements MarketClassifyContract.MarketClassifyPresenter {

    private MarketViewModel mModel;

    public MarketClassifyImpl(MarketClassifyContract.MarketClassifyView view) {
        super(view);
        mModel = new MarketViewModel();
        start();
    }

    /**
     * 获取应用分类
     */
    @Override
    public void getMarketClassifyList() {
        getMarketClassifyList(new ApiStatus<MarketClassifyResponse>() {
            @Override
            public void before() {
                super.before();
                mView.before();
            }

            @Override
            public void success(MarketClassifyResponse data) {
                mView.getMarketClassifySuccess(data);
            }

            @Override
            public void error(Throwable throwable) {
                super.error(throwable);
                mView.error(throwable);
            }

            @Override
            public void tokenInvalid(Throwable throwable) {
                mView.tokenInvalid(throwable);
            }
        });
    }

    /**
     * 获取应用分类
     *
     * @param apiStatus 回调
     */
    public void getMarketClassifyList(ApiStatus<MarketClassifyResponse> apiStatus) {
        mModel.getMarketClassifyList(apiStatus);
    }
}
