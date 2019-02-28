package com.jidouauto.market.module.lib.module.list;

import com.jidouauto.market.module.lib.api.bean.PackageListResponse;
import com.jidouauto.market.module.lib.module.base.BasePresenter;
import com.jidouauto.market.module.lib.module.base.BaseView;

/**
 * Created tangxin
 * Time 2018/11/2 12:04 PM
 */
public class MarketListContract {

    public interface MarketListPresenter extends BasePresenter {

        void getMarketList(int page, int pageSize, Long classifyId, String keyword);

        void getRecommend();
    }

    public interface MarketListView extends BaseView {

        void getMarketListSuccess(PackageListResponse response);

        void getRecommendSuccess(PackageListResponse response);

    }

}
