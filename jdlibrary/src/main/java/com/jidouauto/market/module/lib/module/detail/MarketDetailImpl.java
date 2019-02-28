package com.jidouauto.market.module.lib.module.detail;

import com.jidouauto.market.module.lib.api.ApiStatus;
import com.jidouauto.market.module.lib.api.bean.MarketDetailResponse;
import com.jidouauto.market.module.lib.module.base.BasePresenterImpl;
import com.jidouauto.market.module.lib.module.base.MarketViewModel;

/**
 * Created tangxin
 * Time 2018/11/5 3:00 PM
 */
public class MarketDetailImpl extends BasePresenterImpl<MarketDetailContract.MarketDetailView> implements MarketDetailContract.MarketDetailPresenter {

    private MarketViewModel mModel;

    public MarketDetailImpl(MarketDetailContract.MarketDetailView view) {
        super(view);
        mModel = new MarketViewModel();
        start();
    }

    /**
     * 获取应用详情
     *
     * @param versionId 应用版本ID 必填
     * @param cid       车机ID 必填
     */
    @Override
    public void getMarketDetail(int versionId, String cid) {
        getMarketDetail(versionId, cid, new ApiStatus<MarketDetailResponse>() {
            @Override
            public void before() {
                super.before();
                mView.before();
            }

            @Override
            public void success(MarketDetailResponse data) {
                mView.getMarketDetailSuccess(data);
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
     * 获取应用详情
     *
     * @param versionId 应用版本ID 必填
     * @param cid       车机ID 必填
     */
    public void getMarketDetail(int versionId, String cid, ApiStatus<MarketDetailResponse> apiStatus) {
        mModel.getMarketDetail(versionId, cid, apiStatus);
    }
}
