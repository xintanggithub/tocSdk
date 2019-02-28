package com.jidouauto.market.module.lib.api;

import com.jidouauto.market.module.lib.api.bean.MarketBaseResponse;
import com.jidouauto.market.module.lib.api.bean.MarketClassifyResponse;
import com.jidouauto.market.module.lib.api.bean.MarketCommentRequest;
import com.jidouauto.market.module.lib.api.bean.MarketCommentsResponse;
import com.jidouauto.market.module.lib.api.bean.MarketDetailResponse;
import com.jidouauto.market.module.lib.api.bean.MarketDiffUrlResponse;
import com.jidouauto.market.module.lib.api.bean.MarketUpAppStatusRequest;
import com.jidouauto.market.module.lib.api.bean.MarketUpdateDownloadSizeRequest;
import com.jidouauto.market.module.lib.api.bean.PackageListResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created tangxin
 * Time 2018/10/12 下午3:21
 */
public interface MarketApi {

    public static final String MARKET_HOST = "https://api.jidouauto.com/market/";


    //列表
    @GET("apps/list")
    Observable<PackageListResponse> marketList(@Query("page") int page,
                                               @Query("pageSize") int pageSize,
                                               @Query("channel") String channel,
                                               @Query("classifyId") Long classifyId,
                                               @Query("keyword") String keyword);

    //详情
    @GET("apps/detail")
    Observable<MarketDetailResponse> marketDetail(@Query("versionId") int versionId,
                                                  @Query("cid") String cid,
                                                  @Query("channel") String channel);

    //同步车机安装应用信息
    @POST("apps/syn_status")
    Observable<MarketBaseResponse> marketUpApps(@Body MarketUpAppStatusRequest body);

    //更新下载次数
    @POST("apps/download_num")
    Observable<MarketBaseResponse> updateDownloadCount(@Body MarketUpdateDownloadSizeRequest body);

    //获取评论列表
    @GET("apps/comment_list")
    Observable<MarketCommentsResponse> marketComments(@Query("page") int page,
                                                      @Query("pageSize") int pageSize,
                                                      @Query("channel") String channel,
                                                      @Query("packageId") long packageId);

    //发表评论
    @POST("apps/comment")
    Observable<MarketBaseResponse> comment(@Body MarketCommentRequest body);

    //获取下载链接(更新使用)
    @GET("apps/download_url")
    Observable<MarketDiffUrlResponse> marketDiffUrl(@Query("versionId") long versionId,
                                                    @Query("md5") String md5,
                                                    @Query("currentVersion") int currentVersion);

    //获取应用分类
    @GET("apps/classify_list")
    Observable<MarketClassifyResponse> getMarketClassifyList(@Query("channel") String channel);

    //获取推荐应用
    @GET("apps/recommend")
    Observable<PackageListResponse> getRecommend(@Query("channel") String channel);


}
