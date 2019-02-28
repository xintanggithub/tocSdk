package com.jidouauto.market.module.lib.module.common;

import com.jidouauto.market.module.lib.api.ApiStatus;
import com.jidouauto.market.module.lib.api.bean.MarketBaseResponse;
import com.jidouauto.market.module.lib.api.bean.MarketDiffUrlResponse;
import com.jidouauto.market.module.lib.api.bean.MarketUpAppStatusRequest;
import com.jidouauto.market.module.lib.api.bean.MarketUpdateDownloadSizeRequest;
import com.jidouauto.market.module.lib.module.base.BasePresenterImpl;
import com.jidouauto.market.module.lib.module.base.MarketViewModel;

/**
 * Created tangxin
 * Time 2018/11/5 3:34 PM
 */
public class MarketCommonImpl extends BasePresenterImpl<MarketCommonContract.MarketCommonView>
        implements MarketCommonContract.MarketCommonPresenter {

    private MarketViewModel mModel;

    public MarketCommonImpl(MarketCommonContract.MarketCommonView view) {
        super(view);
        mModel = new MarketViewModel();
        start();
    }

    /**
     * 获取下载链接 （差分包、更新使用）
     *
     * @param versionId           应用版本信息ID 必填
     * @param md5                 已安装版本MD5 必填
     * @param mCurrentVersionCode 当前已安装版本的版本号 必填
     */
    @Override
    public void getMarketDiffUrl(long versionId, String md5, int mCurrentVersionCode) {
        marketDiffUrl(versionId, md5, mCurrentVersionCode, new ApiStatus<MarketDiffUrlResponse>() {
            @Override
            public void before() {
                super.before();
                mView.before();
            }

            @Override
            public void success(MarketDiffUrlResponse data) {
                mView.marketDiffUrlSuccess(data);
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
     * 更新应用下载次数
     *
     * @param body 请求request 必填
     */
    @Override
    public void updateDownloadCount(MarketUpdateDownloadSizeRequest body) {
        updateDownloadCount(body, new ApiStatus<MarketBaseResponse>() {
            @Override
            public void before() {
                super.before();
                mView.before();
            }

            @Override
            public void success(MarketBaseResponse data) {
                mView.updateDownloadCountSuccess(data);
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
     * 同步车机应用安装信息 <br/>
     * <b>tips:</b> apps为json串  "apps":"[{\"packageName\":\"com.moji.mjweather\",\"versionCode\"
     * :\"7060602\"},{\"packageName\":\"com.jidouauto.carletter\",\"versionCode\":\"414\"}
     *
     * @param body 请求body 必填
     */
    @Override
    public void marketSynStatus(MarketUpAppStatusRequest body) {
        marketSynStatus(body, new ApiStatus<MarketBaseResponse>() {
            @Override
            public void before() {
                super.before();
                mView.before();
            }

            @Override
            public void success(MarketBaseResponse data) {
                mView.marketSynStatusSuccess(data);
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
     * 获取下载链接 （差分包、更新使用）
     *
     * @param versionId           应用版本信息ID 必填
     * @param md5                 已安装版本MD5 必填
     * @param mCurrentVersionCode 当前已安装版本的版本号 必填
     * @param apiStatus           回调
     */
    public void marketDiffUrl(long versionId, String md5, int mCurrentVersionCode,
                              ApiStatus<MarketDiffUrlResponse> apiStatus) {
        mModel.marketDiffUrl(versionId, md5, mCurrentVersionCode, apiStatus);
    }

    /**
     * 更新应用下载次数
     *
     * @param body      请求request 必填
     * @param apiStatus 回调
     */
    public void updateDownloadCount(MarketUpdateDownloadSizeRequest body, ApiStatus<MarketBaseResponse> apiStatus) {
        mModel.updateDownloadCount(body, apiStatus);
    }

    /**
     * 同步车机应用安装信息 <br/>
     * <b>tips:</b> apps为json串  "apps":"[{\"packageName\":\"com.moji.mjweather\",\"versionCode\"
     * :\"7060602\"},{\"packageName\":\"com.jidouauto.carletter\",\"versionCode\":\"414\"}
     *
     * @param body      请求body 必填
     * @param apiStatus 回调
     */
    public void marketSynStatus(MarketUpAppStatusRequest body, ApiStatus<MarketBaseResponse> apiStatus) {
        mModel.marketSynStatus(body, apiStatus);
    }

}
