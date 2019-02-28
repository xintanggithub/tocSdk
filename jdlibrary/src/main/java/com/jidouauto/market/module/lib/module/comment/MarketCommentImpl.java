package com.jidouauto.market.module.lib.module.comment;

import com.jidouauto.market.module.lib.api.ApiStatus;
import com.jidouauto.market.module.lib.api.bean.MarketBaseResponse;
import com.jidouauto.market.module.lib.api.bean.MarketCommentRequest;
import com.jidouauto.market.module.lib.api.bean.MarketCommentsResponse;
import com.jidouauto.market.module.lib.module.base.BasePresenterImpl;
import com.jidouauto.market.module.lib.module.base.MarketViewModel;

/**
 * Created tangxin
 * Time 2018/11/5 3:24 PM
 */
public class MarketCommentImpl extends BasePresenterImpl<MarketCommentContract.MarketCommentView>
        implements MarketCommentContract.MarketCommentPresenter {

    private MarketViewModel mModel;

    /**
     * Instantiates a new Market comment.
     *
     * @param view the view
     */
    public MarketCommentImpl(MarketCommentContract.MarketCommentView view) {
        super(view);
        mModel = new MarketViewModel();
        start();
    }

    /**
     * 发表评论
     *
     * @param body 请求request 必填
     */
    @Override
    public void comment(MarketCommentRequest body) {
        comment(body, new ApiStatus<MarketBaseResponse>() {
            @Override
            public void before() {
                super.before();
                mView.before();
            }

            @Override
            public void success(MarketBaseResponse data) {
                mView.commentSuccess(data);
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
     * 获取应用评论列表
     *
     * @param page      页码  必填
     * @param pageSize  每页条数  必填
     * @param packageId 应用基础ID 必填
     */
    @Override
    public void getMarketComments(int page, int pageSize, long packageId) {
        getMarketComments(page, pageSize, packageId, new ApiStatus<MarketCommentsResponse>() {
            @Override
            public void before() {
                super.before();
                mView.before();
            }

            @Override
            public void success(MarketCommentsResponse data) {
                mView.getMarketComments(data);
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
     * 发表评论
     *
     * @param body      请求request 必填
     * @param apiStatus 回调
     */
    public void comment(MarketCommentRequest body, ApiStatus<MarketBaseResponse> apiStatus) {
        mModel.comment(body, apiStatus);
    }

    /**
     * 获取应用评论列表
     *
     * @param page      页码  必填
     * @param pageSize  每页条数  必填
     * @param packageId 应用基础ID 必填
     * @param apiStatus 回调
     */
    public void getMarketComments(int page, int pageSize, long packageId,
                                  ApiStatus<MarketCommentsResponse> apiStatus) {
        mModel.getMarketComments(page, pageSize, packageId, apiStatus);
    }
}
