package com.pingan.baselibs.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.ImageView;

import com.pingan.baselibs.BuildConfig;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;

import java.io.File;

import okhttp3.OkHttpClient;

/**
 * Created by yangwen881 on 17/2/23.
 */

public class PicassoUtils {

    private static final String TAG = PicassoUtils.class.getSimpleName();

    public static final int SCALE_FIT = 0;
    public static final int SCALE_CENTER_INSIDE = 1;
    public static final int SCALE_CENTER_CROP = 2;

    private static final Bitmap.Config DEFAULT_CONFIG = Bitmap.Config.RGB_565;
    private static final int DEFAULT_COLOR = android.R.color.white;

    private static Context mContext;
    private static Picasso sInstance;

    private static int mDefaultImage;

    public static void init(Context context, int defaultImage) {
        mContext = context.getApplicationContext();
        PicassoUtils.mDefaultImage = defaultImage;
        if (sInstance == null) {
            String cacheFilePath = null;
            if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
                File file = context.getExternalCacheDir();
                if (file != null) {
                    cacheFilePath = file.getAbsolutePath()
                            + File.separator
                            + "imageCache";
                } else {
                    file = context.getCacheDir();
                    if (file != null) {
                        cacheFilePath = file.getAbsolutePath()
                                + File.separator
                                + "imageCache";
                    }
                }
            } else {
                File file = context.getCacheDir();
                if (file != null)
                    cacheFilePath = file.getAbsolutePath()
                            + File.separator
                            + "imageCache";
            }
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (cacheFilePath != null) {
                File cacheFileDir = new File(cacheFilePath);
                if (!cacheFileDir.exists()) {
                    cacheFileDir.mkdirs();
                }
                builder.cache(new okhttp3.Cache(cacheFileDir, 1024 * 1024 * 100));
            }
            sInstance = new Picasso.Builder(mContext)
                    .defaultBitmapConfig(DEFAULT_CONFIG)
                    .downloader(new OkHttp3Downloader(builder.build()))
                    .loggingEnabled(BuildConfig.DEBUG)
                    .build();
            Picasso.setSingletonInstance(sInstance);
        }
    }

    /**
     * 加载网络图片
     */
    public static void loadImageUrl(String imageUrl, ImageView imageView) {
        loadImageUrl(imageUrl, mDefaultImage, imageView, 0, 0);
    }

    /**
     * 加载网络图片
     *
     * @param imageUrl
     * @param imageView
     * @param width
     * @param height
     */
    public static void loadImageUrl(String imageUrl, ImageView imageView, int width, int height) {
        loadImageUrl(imageUrl, mDefaultImage, imageView, width, height);
    }

    public static void loadImageUrl(String imageUrl, int defaultImage, ImageView imageView) {
        loadImageUrl(imageUrl, defaultImage, imageView, 0, 0);
    }
    /**
     * 加载网络图片
     */
    public static void loadImageUrl(String imageUrl, int defaultImage, ImageView imageView, int width, int height) {
        if (imageView != null) {
            if (!TextUtils.isEmpty(imageUrl)) {
                if (defaultImage <= 0) {
                    defaultImage = DEFAULT_COLOR;
                }
                RequestCreator requestCreator = sInstance.load(imageUrl)
                        .placeholder(defaultImage)
                        .error(defaultImage);
                if (width != 0 && height != 0) {
                    //requestCreator.resize(width, height);
                } else {
                    //requestCreator.fit();
                }
                requestCreator.into(imageView);
            } else {
                imageView.setImageResource(defaultImage);
            }
        }
    }

    /**
     * 加载sd卡图片
     * @param imagePath
     * @param imageView
     */
    public static void loadImagePath(String imagePath, ImageView imageView) {
        if (imageView != null) {
            if (!TextUtils.isEmpty(imagePath)) {
                sInstance.load(new File(imagePath))
                        .placeholder(DEFAULT_COLOR)
                        .error(DEFAULT_COLOR)
                        .fit()
                        .into(imageView);
            } else {
                imageView.setImageResource(DEFAULT_COLOR);
            }
        }
    }

    /**
     * 加载资源图片
     */
    public static void loadImageRes(int resId, ImageView imageView) {
        loadImageRes(resId, mDefaultImage, imageView);
    }

    /**
     * 加载资源图片
     */
    public static void loadImageRes(int resId, int defaultImage, ImageView imageView) {
        if (imageView != null) {
            if (resId > 0) {
                if (defaultImage < 0) {
                    defaultImage = DEFAULT_COLOR;
                }
                sInstance
                        .load(resId)
                        .placeholder(defaultImage)
                        .error(defaultImage)
                        .fit()
                        .into(imageView);
            } else {
                imageView.setImageResource(defaultImage);
            }
        }
    }

    /**
     * 加载特定尺寸的图片
     * @param imageUrl
     * @param height
     * @param width
     * @param imageView
     */
    public static void loadCropImage(String imageUrl, int height, int width, ImageView imageView) {
        loadCropImage(imageUrl, height, width, mDefaultImage, SCALE_FIT, imageView);
    }

    /**
     * 加载特定尺寸的图片
     * @param imageUrl
     * @param height
     * @param width
     * @param defaultImage
     * @param imageView
     */
    public static void loadCropImage(String imageUrl, int height, int width, int defaultImage,
            ImageView imageView) {
        loadCropImage(imageUrl, height, width, defaultImage, SCALE_FIT, imageView);
    }

    /**
     * 加载特定尺寸的图片
     * @param imageUrl
     * @param height
     * @param width
     * @param defaultImage
     * @param scaleType
     * @param imageView
     */
    public static void loadCropImage(String imageUrl, int height, int width, int defaultImage, int scaleType,
            ImageView imageView) {
        if (imageView != null) {
            if (!TextUtils.isEmpty(imageUrl)) {
                if (defaultImage < 0) {
                    defaultImage = DEFAULT_COLOR;
                }
                RequestCreator creator = sInstance.load(imageUrl)
                        .resize(height, width)
                        .placeholder(defaultImage)
                        .error(defaultImage);
                switch (scaleType) {
                    case SCALE_CENTER_CROP:
                        creator.centerCrop();
                        break;
                    case SCALE_CENTER_INSIDE:
                        creator.centerInside();
                        break;
                    default:
                        creator.fit();
                        break;
                }
                creator.into(imageView);
            } else {
                imageView.setImageResource(defaultImage);
            }
        }
    }

    /**
     * 加载网络图片为模糊图片
     */
    public static void loadImageUrlBlur(String imageUrl, @NonNull ImageView imageView, int defaultImage, int width, int height) {
        if (defaultImage <= 0) {
            defaultImage = DEFAULT_COLOR;
        }
        if (!TextUtils.isEmpty(imageUrl)) {
            sInstance.load(imageUrl)
                    .placeholder(defaultImage)
                    .error(defaultImage)
                    .centerInside()
                    .resize(width, height)
                    .transform(new BlurTransformation())
                    .into(imageView);
        } else {
            imageView.setImageResource(defaultImage);
        }
    }

    public static class BlurTransformation implements Transformation {

        @Override
        public Bitmap transform(Bitmap bitmap) {
            return FastBlur.doBlur(bitmap, 30, true);
        }

        @Override public String key() {
            return "blur";
        }
    }

}
