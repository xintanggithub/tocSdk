package com.jidouauto.market.module.lib.module.comment;

import com.jidouauto.market.module.lib.api.bean.MarketBaseResponse;
import com.jidouauto.market.module.lib.api.bean.MarketCommentRequest;
import com.jidouauto.market.module.lib.api.bean.MarketCommentsResponse;
import com.jidouauto.market.module.lib.module.base.BasePresenter;
import com.jidouauto.market.module.lib.module.base.BaseView;

/**
 * Created tangxin
 * Time 2018/11/5 3:23 PM
 */
public class MarketCommentContract {
    /**
     * The interface Market comment presenter.
     */
    public interface MarketCommentPresenter extends BasePresenter {

        /**
         * Comment.
         *
         * @param body the body
         */
        void comment(MarketCommentRequest body);

        /**
         * Gets market comments.
         *
         * @param page      the page
         * @param pageSize  the page size
         * @param packageId the package id
         */
        void getMarketComments(int page, int pageSize, long packageId);

    }

    /**
     * The interface Market comment view.
     */
    public interface MarketCommentView extends BaseView {

        /**
         * Comment success.
         *
         * @param response the response
         */
        void commentSuccess(MarketBaseResponse response);

        /**
         * Gets market comments.
         *
         * @param response the response
         */
        void getMarketComments(MarketCommentsResponse response);

    }
}
