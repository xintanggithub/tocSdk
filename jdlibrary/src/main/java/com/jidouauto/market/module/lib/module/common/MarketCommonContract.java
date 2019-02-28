package com.jidouauto.market.module.lib.module.common;

import com.jidouauto.market.module.lib.api.bean.MarketBaseResponse;
import com.jidouauto.market.module.lib.api.bean.MarketDiffUrlResponse;
import com.jidouauto.market.module.lib.api.bean.MarketUpAppStatusRequest;
import com.jidouauto.market.module.lib.api.bean.MarketUpdateDownloadSizeRequest;
import com.jidouauto.market.module.lib.module.base.BasePresenter;
import com.jidouauto.market.module.lib.module.base.BaseView;

/**
 * Created tangxin
 * Time 2018/11/5 3:33 PM
 */
public class MarketCommonContract {

    public interface MarketCommonPresenter extends BasePresenter {

        void getMarketDiffUrl(long versionId, String md5, int mCurrentVersionCode);

        void updateDownloadCount(MarketUpdateDownloadSizeRequest body);

        void marketSynStatus(MarketUpAppStatusRequest body);

    }

    public interface MarketCommonView extends BaseView {

        void marketDiffUrlSuccess(MarketDiffUrlResponse response);

        void updateDownloadCountSuccess(MarketBaseResponse response);

        void marketSynStatusSuccess(MarketBaseResponse response);

    }
}
