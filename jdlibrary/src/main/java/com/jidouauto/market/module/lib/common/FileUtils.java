package com.jidouauto.market.module.lib.common;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.jidouauto.market.module.lib.common.http.MD5Utils;

import org.apache.http.util.EncodingUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * 文件工具类
 */
public class FileUtils {
    private static final String TAG = "FileUtils";

    /**
     * Convert byte[] to hex string.将byte转换成int，
     * 然后利用Integer.toHexString(int)来转换成16进制字符串。
     *
     * @param src byte[] data
     * @return hex string
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 根据文件名称和路径，获取sd卡中的文件，以File形式返回byte
     */
    public static File getFile(String fileName, String folder)
            throws IOException {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            File pathFile = new File(Environment.getExternalStorageDirectory()
                    + folder);
            // && !pathFile .isDirectory()
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            File file = new File(pathFile, fileName);
            return file;
        }
        return null;
    }

    /**
     * 根据文件名称和路径，获取sd卡中的文件，判断文件是否存在，存在返回true
     */
    public static Boolean checkFile(String fileName, String folder) {
        try {
            File targetFile = getFile(fileName, folder);

            if (!targetFile.exists()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据Uri返回文件绝对路径
     * 兼容了file:///开头的 和 content://开头的情况
     */
    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore
                    .Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 检查文件是否存在
     */
    public static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }

    public static void copyFile(File sourcefile, File targetFile) {
        FileInputStream input = null;
        BufferedInputStream inbuff = null;
        FileOutputStream out = null;
        BufferedOutputStream outbuff = null;

        try {

            input = new FileInputStream(sourcefile);
            inbuff = new BufferedInputStream(input);

            out = new FileOutputStream(targetFile);
            outbuff = new BufferedOutputStream(out);

            byte[] b = new byte[1024 * 5];
            int len = 0;
            while ((len = inbuff.read(b)) != -1) {
                outbuff.write(b, 0, len);
            }
            outbuff.flush();
        } catch (Exception ex) {
        } finally {
            try {
                if (inbuff != null)
                    inbuff.close();
                if (outbuff != null)
                    outbuff.close();
                if (out != null)
                    out.close();
                if (input != null)
                    input.close();
            } catch (Exception ex) {

            }
        }
    }

    /**
     * 保存图片到本机
     *
     * @param context            context
     * @param fileName           文件名
     * @param file               file
     * @param saveResultCallback 保存结果callback
     */
    public static void saveImage(final Context context, final String fileName, final File file,
                                 final SaveResultCallback saveResultCallback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File appDir = new File(Environment.getExternalStorageDirectory(), "qiming");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                String saveFileName = "qiming_pic";
                if (fileName.contains(".png") || fileName.contains(".gif")) {
                    String fileFormat = fileName.substring(fileName.lastIndexOf("."));
                    saveFileName = MD5Utils.getMD5("qiming_pic" + fileName) + fileFormat;
                } else {
                    saveFileName = MD5Utils.getMD5("qiming_pic" + fileName) + ".png";
                }
                saveFileName = saveFileName.substring(20);//取前20位作为SaveName
                File savefile = new File(appDir, saveFileName);
                try {
                    InputStream is = new FileInputStream(file);
                    FileOutputStream fos = new FileOutputStream(savefile);
                    byte[] buffer = new byte[1024 * 1024];//1M缓冲区
                    int count = 0;
                    while ((count = is.read(buffer)) > 0) {
                        fos.write(buffer, 0, count);
                    }
                    fos.close();
                    is.close();
                    saveResultCallback.onSavedSuccess();
                } catch (FileNotFoundException e) {
                    saveResultCallback.onSavedFailed();
                    e.printStackTrace();
                } catch (IOException e) {
                    saveResultCallback.onSavedFailed();
                    e.printStackTrace();
                }
                //保存图片后发送广播通知更新数据库
                Uri uri = Uri.fromFile(savefile);
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            }
        }).start();
    }

    /**
     * 保存Bitmap到本机
     *
     * @param context            context
     * @param fileName           bitmap文件名
     * @param bmp                bitmap
     * @param saveResultCallback 保存结果callback
     */
    public static void saveBitmap(final Context context, final String fileName, final Bitmap bmp,
                                  final SaveResultCallback
                                          saveResultCallback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File appDir = new File(Environment.getExternalStorageDirectory(), "qiming");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                //                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                // 设置以当前时间格式为图片名称
                String saveFileName = MD5Utils.getMD5("qiming_pic" + fileName) + ".png";
                saveFileName = saveFileName.substring(20);//取前20位作为SaveName
                File file = new File(appDir, saveFileName);
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    fos.close();
                    saveResultCallback.onSavedSuccess();
                } catch (FileNotFoundException e) {
                    saveResultCallback.onSavedFailed();
                    e.printStackTrace();
                } catch (IOException e) {
                    saveResultCallback.onSavedFailed();
                    e.printStackTrace();
                }
                //保存图片后发送广播通知更新数据库
                Uri uri = Uri.fromFile(file);
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            }
        }).start();
    }

    public interface SaveResultCallback {
        void onSavedSuccess();

        void onSavedFailed();
    }

    public static String SDCard = Environment.getExternalStorageDirectory().getAbsolutePath();
    private static String pachageName = getPackageName();

    /**
     * 返回数据目录
     *
     * @return
     */
    public static String getDataPath() {
        if (checkSDCard()) {
            return FileUtils.SDCard + "/Android/data/" + getPackageName() + "/";
        } else {
            return "/data/data/" + getPackageName() + "/";
        }
    }

    public static String getPackageName() {
        if (pachageName != null) {
            return pachageName;
        }
        return MarketConfig.getContext().getPackageName();
    }

    /**
     * 检测SDCard是否可用
     *
     * @return
     */
    public static boolean checkSDCard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 返回File 如果没有就创建
     *
     * @return directory
     */
    public static File getDirectory(String path) {
        File appDir = new File(path);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        return appDir;
    }

    /**
     * 删除文件夹
     *
     * @param sPath 路径
     * @return
     */
    public static boolean deleteDirectory(String sPath) {
        boolean flag = false;
        // 如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        // 删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            } // 删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag)
            return false;
        // 删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    public static void createFolder(String path) {
        path = separatorReplace(path);
        File folder = new File(path);
        if (folder.isDirectory()) {
            return;
        } else if (folder.isFile()) {
            deleteFile(path);
        }
        folder.mkdirs();
    }

    public static File createFile(String path) throws Exception {
        path = separatorReplace(path);
        File file = new File(path);
        if (file.isFile()) {
            return file;
        } else if (file.isDirectory()) {
            deleteFolder(path);
        }
        return createFile(file);
    }

    public static File createFile(File file) throws Exception {
        createParentFolder(file);
        if (!file.createNewFile()) {
            throw new Exception("create file failure!");
        }
        return file;
    }

    public static void deleteFolder(String path) throws Exception {
        path = separatorReplace(path);
        File folder = getFolder(path);
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                deleteFolder(file.getAbsolutePath());
            } else if (file.isFile()) {
                deleteFile(file.getAbsolutePath());
            }
        }
        folder.delete();
    }

    public static File getFolder(String path) throws FileNotFoundException {
        path = separatorReplace(path);
        File folder = new File(path);
        if (!folder.isDirectory()) {
            throw new FileNotFoundException("folder not found!");
        }
        return folder;
    }

    public static File getFile(String path) {
        path = separatorReplace(path);
        File file = new File(path);
        if (!file.isFile()) {
            return null;
        }
        return file;
    }

    public static String separatorReplace(String path) {
        return path.replace("\\", "/");
    }

    /**
     * 删除文件
     *
     * @param sPath 路径
     * @return
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    public static void createParentFolder(File file) throws Exception {
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                throw new Exception("create parent directory failure!");
            }
        }
    }


    /**
     * 写入文件
     *
     * @param path
     * @param content
     * @return 1: 写入成功 0: 写入失败
     */
    public static int writeFile(String path, String content) {
        try {
            File f = new File(path);
            if (f.exists()) {
                f.delete();
            } else {
                createFolder(path.substring(0, path.lastIndexOf("/")));
            }
            if (f.createNewFile()) {
                FileOutputStream utput = new FileOutputStream(f);
                utput.write(content.getBytes());
                utput.close();
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
        return 1;
    }

    /**
     * 写入文件
     *
     * @param path
     * @param in
     * @return 1: 写入成功 0: 写入失败
     */
    public static int writeFile(String path, InputStream in) {
        try {
            if (in == null)
                return 0;
            File f = new File(path);
            if (f.exists()) {
                f.delete();
            }
            if (f.createNewFile()) {
                FileOutputStream utput = new FileOutputStream(f);
                byte[] buffer = new byte[1024];
                int count = -1;
                while ((count = in.read(buffer)) != -1) {
                    utput.write(buffer, 0, count);
                    utput.flush();
                }
                utput.close();
                in.close();
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
        return 1;
    }

    /**
     * 复制文件
     *
     * @param is
     * @param os
     * @return 1: 写入成功 0: 写入失败
     * @throws IOException
     */
    public static int copyStream(InputStream is, OutputStream os) {
        try {
            final int buffer_size = 1024;
            byte[] bytes = new byte[buffer_size];
            while (true) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1) {
                    break;
                }
                os.write(bytes, 0, count);
            }
            return 1;
        } catch (IOException e) {
            return 0;
        } finally {
            try {
                is.close();
                os.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 读取序列化对象
     *
     * @param filePath
     * @return
     */
    public static Object readerObject(String filePath) {
        Object oo;
        try {
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream objectIn = new ObjectInputStream(fis);
            oo = objectIn.readObject();
            objectIn.close();
            fis.close();
        } catch (Exception e) {
            return null;
        }
        return oo;
    }

    /**
     * 写入序列化对象
     *
     * @param path
     * @param object
     * @return
     */
    public static int writeObject(String path, Object object) {
        try {
            File f = new File(path);
            if (f.exists()) {
                f.delete();
            }
            if (f.createNewFile()) {
                FileOutputStream utput = new FileOutputStream(f);
                ObjectOutputStream objOut = new ObjectOutputStream(utput);
                objOut.writeObject(object);
                objOut.close();
                utput.close();
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
        return 1;
    }


    /**
     * Bitmap 转换成 byte[]
     *
     * @param bm
     * @return
     */
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 保存图片
     *
     * @param path
     * @param bitmap
     */
    public static void saveBitmap(String path, Bitmap bitmap) {
        try {
            File f = new File(path);
            if (f.exists())
                f.delete();
            f.createNewFile();
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(f);
            BufferedOutputStream bos = new BufferedOutputStream(fOut);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getFileContent(String fileName) {
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(fileName);
            int length = fin.available();
            byte[] bytes = new byte[length];
            fin.read(bytes);
            return EncodingUtils.getString(bytes, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fin != null) {
                    fin.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getFileName(String filePath) {
        return filePath.substring(0, filePath.lastIndexOf("."));
    }

    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }
}


