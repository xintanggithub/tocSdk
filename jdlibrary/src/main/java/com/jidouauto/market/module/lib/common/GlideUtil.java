package com.jidouauto.market.module.lib.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by simon on 18-2-28.
 */

public class GlideUtil {

    public static void setRoundIcon(final Context context, Uri uri, final ImageView imageView) {
        Glide.with(context)
                .asBitmap()
                .load(uri)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    public static void setCornerIcon(final Context context, Uri uri, final ImageView imageView, final float radius) {
        Glide.with(context)
                .asBitmap()
                .load(uri)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(false);
                        circularBitmapDrawable.setCornerRadius(radius);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    public static void setCornerIcon(final Context context, String path, final ImageView imageView, final float radius) {
        Glide.with(context)
                .asBitmap()
                .load(path)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(false);
                        circularBitmapDrawable.setCornerRadius(radius);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    public static void setCornerIcon(final Context context, String path, final ImageView imageView, final float radius, RequestOptions options) {
        Glide.with(context)
                .asBitmap()
                .load(path)
                .apply(options)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(false);
                        circularBitmapDrawable.setCornerRadius(radius);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    public static void setRoundIcon(final Context context, File file, final ImageView imageView) {
        Glide.with(context)
                .asBitmap()
                .load(file)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    public static void setRoundIcon(final Context context, String path, final ImageView imageView) {
        Glide.with(context)
                .asBitmap()
                .load(path)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    public static void setRoundIcon(final Context context, Integer res, final ImageView imageView) {
        Glide.with(context)
                .asBitmap()
                .load(res)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    public static void setRoundIcon(final Context context, Uri uri, final ImageView imageView, Drawable drawable) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(drawable);
        Glide.with(context)
                .asBitmap()
                .apply(requestOptions)
                .load(uri)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    public static void setRoundIcon(final Context context, File uri, final ImageView imageView, Drawable drawable) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(drawable);
        Glide.with(context)
                .asBitmap()
                .apply(requestOptions)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    public static void setRoundIcon(final Context context, String uri, final ImageView imageView, Drawable drawable) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(drawable);
        Glide.with(context)
                .asBitmap()
                .apply(requestOptions)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    public static void setRoundIcon(final Context context, Integer uri, final ImageView imageView, Drawable drawable) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(drawable);
        Glide.with(context)
                .asBitmap()
                .apply(requestOptions)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }


    public static void setIcon(final Context context, Uri uri, final ImageView imageView) {
        Glide.with(context)
                .asBitmap()
                .load(uri)
                .into(imageView);
    }

    public static void setIcon(final Context context, File uri, final ImageView imageView) {
        Glide.with(context)
                .asBitmap()
                .load(uri)
                .into(imageView);
    }

    public static void setIcon(final Context context, String uri, final ImageView imageView) {
        Glide.with(context)
                .asBitmap()
                .load(uri)
                .into(imageView);
    }

    public static void setIcon(final Context context, String uri, final ImageView imageView, RequestOptions options) {
        Glide.with(context)
                .asBitmap()
                .load(uri)
                .apply(options)
                .into(imageView);
    }

    public static void setIcon(final Context context, Integer uri, final ImageView imageView) {
        Glide.with(context)
                .asBitmap()
                .load(uri)
                .into(imageView);
    }

    public static void setIcon(final Context context, Uri uri, final ImageView imageView, Drawable drawable) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(drawable);
        Glide.with(context)
                .asBitmap()
                .load(uri)
                .apply(requestOptions)
                .into(imageView);
    }

    public static void setIcon(final Context context, File uri, final ImageView imageView, Drawable drawable) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(drawable);
        Glide.with(context)
                .asBitmap()
                .load(uri)
                .apply(requestOptions)
                .into(imageView);
    }

    public static void setIcon(final Context context, String uri, final ImageView imageView, Drawable drawable) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(drawable);
        Glide.with(context)
                .asBitmap()
                .load(uri)
                .apply(requestOptions)
                .into(imageView);
    }

    public static void setIcon(final Context context, Integer uri, final ImageView imageView, Drawable drawable) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(drawable);
        Glide.with(context)
                .asBitmap()
                .load(uri)
                .apply(requestOptions)
                .into(imageView);
    }

    public static int px2dp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public static int dp2px(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static File getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        int be = 1;
        int width = newOpts.outWidth;
        int height = newOpts.outHeight;

        if (width > height) {
            if (width > 1024) {
                be = 1024 / width;
            }
        } else {
            if (height > 1024) {
                be = 1024 / height;
            }
        }
        if (be <= 0) {
            be = 1;
        }
        newOpts.inJustDecodeBounds = false;
        newOpts.inSampleSize = be;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap, srcPath);
    }

    public static File compressImage(Bitmap image, String path) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        while (baos.toByteArray().length > 2 * 1024 * 1024) {
            baos.reset();
            options -= 10;
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        try {
            File oldFile = new File(path);
            File pic = new File(Environment.getExternalStorageDirectory() + "/comp" + new File(path).getName());
            int len = -1;
            byte[] buf = new byte[8192];
            FileOutputStream fos = new FileOutputStream(pic);
            while ((len = isBm.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            fos.close();
            return pic;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
