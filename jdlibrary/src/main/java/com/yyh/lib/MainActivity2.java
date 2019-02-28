package com.yyh.lib;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jidouauto.market.module.lib.R;
import com.yyh.lib.bsdiff.DiffUtils;
import com.yyh.lib.bsdiff.PatchUtils;
import com.yyh.lib.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity2 extends Activity {

    private ProgressBar loadding;

    // 成功
    private static final int WHAT_SUCCESS = 1;
    // 失败
    private static final int WHAT_FAIL_PATCH = 0;
    /**
     * 老版本apk
     */
    private String oldApkDir = Environment.getExternalStorageDirectory().toString() + "/bsdiff_old.apk";
    /**
     * 新版本apk
     */
    private String newApkDir = Environment.getExternalStorageDirectory().toString() + "/bsdiff_new_build.apk";
    /**
     * 根据新老版本差分包生成的新版本apk
     */
    private String destDir2 = Environment.getExternalStorageDirectory().toString() + "/bsdiff_new.apk";
    /**
     * 新老版本的增量更新包
     */
    private String oldToNewPatchDir = Environment.getExternalStorageDirectory().toString() + "/old-to-new.patch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_diff_layout);
        loadding = findViewById(R.id.loadding);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(getApplicationContext(), "复制成功", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(getApplicationContext(), "复制失败", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(getApplicationContext(), "生成增新包成功", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(getApplicationContext(), "生成增新包失败", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(getApplicationContext(), "合成新版本成功", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    Toast.makeText(getApplicationContext(), "合成新版本失败", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 复制文件
     *
     * @param view
     */
    public void copy(View view) {
        loadding.setVisibility(View.VISIBLE);
        new CopyTask().execute(oldApkDir, "bsdiff_old.apk", newApkDir, "bsdiff_new.apk");
    }

    /**
     * 生成差分增量包
     *
     * @param view
     */
    public void bsdiff(View view) {
        loadding.setVisibility(View.VISIBLE);
        new DiffTask().execute();
    }

    /**
     * 合成新版本
     *
     * @param view
     */
    public void bspatch(View view) {
        loadding.setVisibility(View.VISIBLE);
        new PatchTask().execute();
    }

    /**
     * 安装老版本
     *
     * @param view
     */
    public void installOld(View view) {
        install(oldApkDir);
    }

    /**
     * 安装新版本
     *
     * @param view
     */
    public void installNew(View view) {
        install(destDir2);
    }

    /**
     * 复制文件线程
     */
    private class CopyTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... params) {

            for (int i = 0; i < params.length; i += 2) {
                try {
                    File file = new File(params[i]);
                    if (!file.exists())
                        FileUtils.createFile(file);

                    InputStream is;
                    OutputStream os = new FileOutputStream(params[i]);
                    is = getAssets().open(params[i + 1]);
                    byte[] buffer = new byte[1024];
                    int length = is.read(buffer);
                    while (length > 0) {
                        os.write(buffer, 0, length);
                        length = is.read(buffer);
                    }
                    os.flush();
                    is.close();
                    os.close();
                } catch (Exception e) {
                    handler.obtainMessage(1).sendToTarget();
                    return null;
                }
            }
            handler.obtainMessage(0).sendToTarget();
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            loadding.setVisibility(View.GONE);
        }
    }

    /**
     * 生成差分包线程
     *
     * @author yuyuhang
     * @date 2016-1-25 下午12:24:34
     */
    private class DiffTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {

            try {
                int result = DiffUtils.getInstance().genDiff(oldApkDir, newApkDir, oldToNewPatchDir);
                if (result == 0) {
                    handler.obtainMessage(2).sendToTarget();
                    return WHAT_SUCCESS;
                } else {
                    handler.obtainMessage(3).sendToTarget();
                    return WHAT_FAIL_PATCH;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return WHAT_FAIL_PATCH;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            loadding.setVisibility(View.GONE);
        }
    }

    /**
     * 差分包合成APK线程
     *
     * @author yuyuhang
     * @date 2016-1-25 下午12:24:34
     */
    private class PatchTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {

            try {

                int result = PatchUtils.getInstance().patch(oldApkDir, destDir2, oldToNewPatchDir);
                if (result == 0) {
                    handler.obtainMessage(4).sendToTarget();
                    return WHAT_SUCCESS;
                } else {
                    handler.obtainMessage(5).sendToTarget();
                    return WHAT_FAIL_PATCH;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return WHAT_FAIL_PATCH;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            loadding.setVisibility(View.GONE);
        }
    }

    /**
     * 安装软件
     *
     * @param dir
     */
    private void install(String dir) {
        String command = "chmod 777 " + dir;
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(command); // 可执行权限
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + dir), "application/vnd.android.package-archive");
        startActivity(intent);
    }
/*

    public void appList(View view) {
        Intent intent = new Intent(this, ApplistActivity.class);
        startActivity(intent);
    }
*/

}
