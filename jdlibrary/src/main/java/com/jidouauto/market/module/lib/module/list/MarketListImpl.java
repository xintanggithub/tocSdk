package com.jidouauto.market.module.lib.module.list;

import com.jidouauto.market.module.lib.api.ApiStatus;
import com.jidouauto.market.module.lib.api.bean.PackageListResponse;
import com.jidouauto.market.module.lib.module.base.BasePresenterImpl;
import com.jidouauto.market.module.lib.module.base.MarketViewModel;

/**
 * Created tangxin
 * Time 2018/11/2 12:04 PM
 */
public class MarketListImpl extends BasePresenterImpl<MarketListContract.MarketListView>
        implements MarketListContract.MarketListPresenter {

    private MarketViewModel mModel;

    public MarketListImpl(MarketListContract.MarketListView view) {
        super(view);
        mModel = new MarketViewModel();
        start();
    }

    /**
     * 获取应用列表
     *
     * @param page       页码  必填
     * @param pageSize   每页条数 必填
     * @param classifyId 分类ID 选填
     * @param keyword    搜索关键字 选填
     */
    @Override
    public void getMarketList(int page, int pageSize, Long classifyId, String keyword) {
        getMarketList(page, pageSize, classifyId, keyword, new ApiStatus<PackageListResponse>() {
            @Override
            public void before() {
                super.before();
                mView.before();
            }

            @Override
            public void success(PackageListResponse data) {
                mView.getMarketListSuccess(data);
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
     * 获取推荐应用
     */
    @Override
    public void getRecommend() {
        getRecommend(new ApiStatus<PackageListResponse>() {
            @Override
            public void before() {
                super.before();
                mView.before();
            }

            @Override
            public void success(PackageListResponse data) {
                mView.getRecommendSuccess(data);
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
     * 获取推荐应用
     *
     * @param apiStatus 回调
     */
    public void getRecommend(ApiStatus<PackageListResponse> apiStatus) {
        mModel.getRecommend(apiStatus);
    }


    /**
     * 获取应用列表
     *
     * @param page       页码  必填
     * @param pageSize   每页条数 必填
     * @param classifyId 分类ID 选填
     * @param keyword    搜索关键字 选填
     * @param apiStatus  回调
     */
    public void getMarketList(int page, int pageSize, Long classifyId, String keyword,
                              ApiStatus<PackageListResponse> apiStatus) {
        mModel.getMarketList(page, pageSize, classifyId, keyword, apiStatus);
    }


}
