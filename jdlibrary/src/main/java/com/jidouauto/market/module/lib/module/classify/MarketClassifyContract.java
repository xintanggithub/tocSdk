package com.jidouauto.market.module.lib.module.classify;

import com.jidouauto.market.module.lib.api.bean.MarketClassifyResponse;
import com.jidouauto.market.module.lib.module.base.BasePresenter;
import com.jidouauto.market.module.lib.module.base.BaseView;

/**
 * Created tangxin
 * Time 2018/11/5 3:10 PM
 */
public class MarketClassifyContract {

    public interface MarketClassifyPresenter extends BasePresenter {

        void getMarketClassifyList();

    }

    public interface MarketClassifyView extends BaseView {

        void getMarketClassifySuccess(MarketClassifyResponse response);

    }
}
