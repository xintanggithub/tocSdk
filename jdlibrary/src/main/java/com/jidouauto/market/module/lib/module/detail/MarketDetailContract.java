package com.jidouauto.market.module.lib.module.detail;

import com.jidouauto.market.module.lib.api.bean.MarketDetailResponse;
import com.jidouauto.market.module.lib.module.base.BasePresenter;
import com.jidouauto.market.module.lib.module.base.BaseView;

/**
 * Created tangxin
 * Time 2018/11/5 3:00 PM
 */
public class MarketDetailContract {
    public interface MarketDetailPresenter extends BasePresenter {

        void getMarketDetail(int versionId, String cid);

    }

    public interface MarketDetailView extends BaseView {

        void getMarketDetailSuccess(MarketDetailResponse response);

    }
}
