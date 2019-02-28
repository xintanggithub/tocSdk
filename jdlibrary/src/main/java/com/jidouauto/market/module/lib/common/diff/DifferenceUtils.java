package com.jidouauto.market.module.lib.common.diff;

import android.content.Context;
import android.os.AsyncTask;

import com.jidouauto.market.module.lib.common.LogUtils;
import com.yyh.lib.bsdiff.DiffUtils;
import com.yyh.lib.bsdiff.PatchUtils;
import com.yyh.lib.utils.ApkUtils;
import com.yyh.lib.utils.SignUtils;

/**
 * Created tangxin
 * Time 2018/11/13 11:32 AM
 */
public class DifferenceUtils {

    /**
     * 合成新包方法
     *
     * @param oldApkPath    老包路径
     * @param newApkPath    新包路径
     * @param incrementPath 增量包路径
     * @param md5           服务器新包对应的MD5，合成之后再和此MD5对比，如果不一样，则合成有误
     * @param composeStatus 合成状态回调
     */
    public static void composeApp(String oldApkPath, String newApkPath, String incrementPath
            , String md5, final ComposeStatus composeStatus) {
        LogUtils.d("composeApp: oldApkPath :" + oldApkPath);
        LogUtils.d("composeApp: newApkPath :" + newApkPath);
        LogUtils.d("composeApp: incrementPath :" + incrementPath);
        LogUtils.d("composeApp: md5 :" + md5);
        new PatchTask(oldApkPath, newApkPath, incrementPath, md5, composeStatus).execute();
    }

    /**
     * 生成增量包方法
     *
     * @param oldApkPath    老包路径
     * @param newApkPath    新包路径
     * @param incrementPath 增量包路径
     */
    public static void getDifferential(String oldApkPath, String newApkPath, String incrementPath
            , final DiffStatus diffStatus) {
        new DiffTask(oldApkPath, newApkPath, incrementPath, diffStatus).execute();
    }


    /**
     * 获取已安装Apk文件的源Apk文件
     *
     * @param context     context
     * @param packageName 包名
     * @return String path 路径
     */
    public static String getCurrentApkPath(Context context, String packageName) {
        return ApkUtils.getSourceApkPath(context, packageName);
    }

    /**
     * 根据apk路径获取MD5值
     *
     * @param oldApkSourceDir 已安装的apk路径
     * @return MD5
     */
    public static String getCurrentApkMd5(String oldApkSourceDir) {
        return SignUtils.getCurrentRealMD5(oldApkSourceDir);
    }

    /**
     * 判断文件的MD5是否为指定值
     *
     * @param filePath 文件
     * @param md5      需要对比的MD5
     * @return true相同  false不相同
     */
    public static boolean checkMd5(String filePath, String md5) {
        return SignUtils.checkMd5(filePath, md5);
    }

    private static class PatchTask extends AsyncTask<String, Void, Integer> {
        String oldApkDir;//老包
        String destDir2;//新包
        String oldToNewPatchDir;//增量包
        ComposeStatus composeStatus;
        String md5;

        public PatchTask(String oldApkDir, String destDir2, String oldToNewPatchDir, String md5
                , ComposeStatus composeStatus) {
            this.oldApkDir = oldApkDir;
            this.destDir2 = destDir2;
            this.oldToNewPatchDir = oldToNewPatchDir;
            this.composeStatus = composeStatus;
            this.md5 = md5;
        }

        @Override
        protected Integer doInBackground(String... params) {

            try {
                int result = PatchUtils.getInstance().patch(oldApkDir, destDir2, oldToNewPatchDir);
                if (result == 0) {
                    LogUtils.d("composeApp: 合成成功 :");
                    composeStatus.success(oldApkDir, destDir2, oldToNewPatchDir, md5);
                } else {
                    LogUtils.d("composeApp: 合成失败:");
                    composeStatus.error(new Throwable("和合成失败"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                composeStatus.error(e);
                LogUtils.d("composeApp: 合成失败:" + e.getMessage());
            }
            return 1;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }
    }


    /**
     * 生成差分包线程
     */
    public static class DiffTask extends AsyncTask<String, Void, Integer> {
        String oldApkPath;
        String newApkPath;
        String incrementPath;
        DiffStatus diffStatus;

        public DiffTask(String oldApkPath, String newApkPath, String incrementPath,
                        DiffStatus diffStatus) {
            this.oldApkPath = oldApkPath;
            this.newApkPath = newApkPath;
            this.incrementPath = incrementPath;
            this.diffStatus = diffStatus;
        }

        @Override
        protected Integer doInBackground(String... params) {

            try {
                int result = DiffUtils.getInstance().genDiff(oldApkPath, newApkPath, incrementPath);
                if (result == 0) {
                    diffStatus.success(oldApkPath, newApkPath, incrementPath);
                } else {
                    diffStatus.error(new Throwable("合成失败"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                diffStatus.error(e);
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }
    }

}
