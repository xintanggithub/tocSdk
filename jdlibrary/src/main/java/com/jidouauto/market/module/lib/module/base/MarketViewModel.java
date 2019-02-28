package com.jidouauto.market.module.lib.module.base;

import android.arch.lifecycle.ViewModel;

import com.alibaba.fastjson.JSON;
import com.jidouauto.market.module.lib.common.MarketConfig;
import com.jidouauto.market.module.lib.api.ApiStatus;
import com.jidouauto.market.module.lib.api.MarketApi;
import com.jidouauto.market.module.lib.api.bean.MarketBaseResponse;
import com.jidouauto.market.module.lib.api.bean.MarketClassifyResponse;
import com.jidouauto.market.module.lib.api.bean.MarketCommentRequest;
import com.jidouauto.market.module.lib.api.bean.MarketCommentsResponse;
import com.jidouauto.market.module.lib.api.bean.MarketDetailResponse;
import com.jidouauto.market.module.lib.api.bean.MarketDiffUrlResponse;
import com.jidouauto.market.module.lib.api.bean.MarketUpAppStatusRequest;
import com.jidouauto.market.module.lib.api.bean.MarketUpdateDownloadSizeRequest;
import com.jidouauto.market.module.lib.api.bean.PackageListResponse;
import com.jidouauto.market.module.lib.common.LogUtils;
import com.jidouauto.market.module.lib.common.StringUtils;
import com.jidouauto.market.module.lib.common.http.JDOSignUtil;
import com.jidouauto.market.module.lib.common.http.RetrofitFactory;

/**
 * Created tangxin
 * Time 2018/11/2 2:05 PM
 */
public class MarketViewModel extends ViewModel {

    private static final String TAG = "MarketViewModel";

    private MarketApi mApi;

    public MarketViewModel() {
        mApi = RetrofitFactory.createApi(MarketApi.class);
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
        String channel = MarketConfig.getJdChannel();
        String log = String.format("getMarketList page:%s  pageSize:%s  classifyId:%s  keyword:%s  " +
                "channel:%s", page, pageSize, classifyId, keyword, channel);
        LogUtils.d(log);
        apiStatus.request(log);
        RetrofitFactory.createData(mApi.marketList(page, pageSize, channel, classifyId, keyword),
                apiStatus);
    }

    /**
     * 获取应用详情
     *
     * @param versionId 应用版本ID 必填
     * @param cid       车机ID 必填
     * @param apiStatus 回调
     */
    public void getMarketDetail(int versionId, String cid,
                                ApiStatus<MarketDetailResponse> apiStatus) {
        String channel = MarketConfig.getJdChannel();
        String log = String.format("getMarketDetail versionId:%s  cid:%s  channel:%s", versionId,
                cid, channel);
        LogUtils.d(log);
        apiStatus.request(log);
        RetrofitFactory.createData(mApi.marketDetail(versionId, cid, channel), apiStatus);
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
        if (StringUtils.isEmpty(body.getChannel())) {
            body.setChannel(MarketConfig.getJdChannel());
        }
        body.setTs(System.currentTimeMillis());
        body.setSign(JDOSignUtil.getSignStr(JSON.toJSONString(body), JDOSignUtil.SIGN_KEY));
        String log = "marketSynStatus body：" + JSON.toJSONString(body);
        LogUtils.d(log);
        apiStatus.request(log);
        RetrofitFactory.createData(mApi.marketUpApps(body), apiStatus);
    }

    /**
     * 更新应用下载次数
     *
     * @param body      请求request 必填
     * @param apiStatus 回调
     */
    public void updateDownloadCount(MarketUpdateDownloadSizeRequest body,
                                    ApiStatus<MarketBaseResponse> apiStatus) {
        body.setTs(System.currentTimeMillis());
        body.setSign(JDOSignUtil.getSignStr(JSON.toJSONString(body), JDOSignUtil.SIGN_KEY));
        String log = "updateDownloadCount body：" + JSON.toJSONString(body);
        LogUtils.d(log);
        apiStatus.request(log);
        RetrofitFactory.createData(mApi.updateDownloadCount(body), apiStatus);
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
        String channel = MarketConfig.getJdChannel();
        String log = String.format("marketComments page:%s  pageSize:%s  channel:%s  packageId:%s",
                page, pageSize, channel, packageId);
        LogUtils.d(log);
        apiStatus.request(log);
        RetrofitFactory.createData(mApi.marketComments(page, pageSize, channel, packageId), apiStatus);
    }

    /**
     * 发表评论
     *
     * @param body      请求request 必填
     * @param apiStatus 回调
     */
    public void comment(MarketCommentRequest body, ApiStatus<MarketBaseResponse> apiStatus) {
        body.setTs(System.currentTimeMillis());
        body.setChannel(MarketConfig.getJdChannel());
        body.setSign(JDOSignUtil.getSignStr(JSON.toJSONString(body), JDOSignUtil.SIGN_KEY));
        String log = "comment body：" + JSON.toJSONString(body);
        LogUtils.d(log);
        apiStatus.request(log);
        RetrofitFactory.createData(mApi.comment(body), apiStatus);
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
        String log = String.format("getMarketDiffUrl versionId:%s  md5:%s  mCurrentVersionCode:%s",
                versionId, md5, mCurrentVersionCode);
        LogUtils.d(log);
        apiStatus.request(log);
        RetrofitFactory.createData(mApi.marketDiffUrl(versionId, md5, mCurrentVersionCode), apiStatus);
    }

    /**
     * 获取应用分类
     *
     * @param apiStatus 回调
     */
    public void getMarketClassifyList(ApiStatus<MarketClassifyResponse> apiStatus) {
        String channel = MarketConfig.getJdChannel();
        String log = "getMarketClassifyList  channel:" + channel;
        LogUtils.d(log);
        apiStatus.request(log);
        RetrofitFactory.createData(mApi.getMarketClassifyList(channel), apiStatus);
    }

    /**
     * 获取推荐应用
     *
     * @param apiStatus 回调
     */
    public void getRecommend(ApiStatus<PackageListResponse> apiStatus) {
        String channel = MarketConfig.getJdChannel();
        String log = "getMarketClassifyList  channel:" + channel;
        LogUtils.d(log);
        apiStatus.request(log);
        RetrofitFactory.createData(mApi.getRecommend(channel), apiStatus);
    }

}
