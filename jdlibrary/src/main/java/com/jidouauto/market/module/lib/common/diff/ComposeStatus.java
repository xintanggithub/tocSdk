package com.jidouauto.market.module.lib.common.diff;

/**
 * Created tangxin
 * Time 2018/11/13 11:49 AM
 */
public interface ComposeStatus {

    /**
     * 成功
     *
     * @param oldApkPath    老包路径
     * @param newApkPath    新包路径
     * @param incrementPath 增量包路径
     * @param md5           服务器新包对应的MD5，合成之后再和此MD5对比，如果不一样，则合成有误
     */
    void success(String oldApkPath, String newApkPath, String incrementPath, String md5);

    /**
     * 失败
     *
     * @param throwable 错误信息
     */
    void error(Throwable throwable);

}
