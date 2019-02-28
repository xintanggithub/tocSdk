package com.jidouauto.market.module.lib.common.diff;

/**
 * Created tangxin
 * Time 2018/11/14 10:05 AM
 */
public interface DiffStatus {
    /**
     * 成功
     *
     * @param oldApkPath    老包路径
     * @param newApkPath    新包路径
     * @param incrementPath 增量包路径
     */
    void success(String oldApkPath, String newApkPath, String incrementPath);

    /**
     * 失败
     *
     * @param throwable 错误信息
     */
    void error(Throwable throwable);
}
