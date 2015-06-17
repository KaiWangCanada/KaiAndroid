package com.unit.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.lidroid.xutils.BitmapUtils;
import com.unit.common.config.CommonSetting;

/**
 * 主要用于继承BitmapUtils,并设定图片缓存的文件夹,便于管理
 * Created by kingkong on 15/1/30.
 */
public class FrameBitmapUtils extends BitmapUtils {

    public static FrameBitmapUtils getInstance(Context context) {
        return new FrameBitmapUtils(context, CommonSetting.imageDirectory.getAbsolutePath());
    }

    public static FrameBitmapUtils getInstance(Context context, Drawable loadingDrawable, Drawable loadFailedDrawable) {
        FrameBitmapUtils frameBitmapUtils = new FrameBitmapUtils(context, CommonSetting.imageDirectory.getAbsolutePath());

        try {
            frameBitmapUtils.configDefaultLoadFailedImage(loadFailedDrawable);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            frameBitmapUtils.configDefaultLoadingImage(loadingDrawable);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return frameBitmapUtils;
    }

    /**
     * @param context
     * @param loadingDrawableId   资源id
     * @param loadFailedDrawableId
     * @return
     */
    public static FrameBitmapUtils getInstance(Context context, int loadingDrawableId, int loadFailedDrawableId) {
        FrameBitmapUtils frameBitmapUtils = new FrameBitmapUtils(context, CommonSetting.imageDirectory.getAbsolutePath());

        try {
            frameBitmapUtils.configDefaultLoadingImage(loadingDrawableId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            frameBitmapUtils.configDefaultLoadFailedImage(loadFailedDrawableId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return frameBitmapUtils;
    }

    public static FrameBitmapUtils getInstance(Context context, Bitmap loadingDrawable, Bitmap loadFailedDrawable) {
        FrameBitmapUtils frameBitmapUtils = new FrameBitmapUtils(context, CommonSetting.imageDirectory.getAbsolutePath());

        try {
            frameBitmapUtils.configDefaultLoadingImage(loadingDrawable);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            frameBitmapUtils.configDefaultLoadFailedImage(loadFailedDrawable);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return frameBitmapUtils;
    }

    private FrameBitmapUtils(Context context) {
        super(context);
    }

    private FrameBitmapUtils(Context context, String diskCachePath) {
        super(context, diskCachePath);
    }

    private FrameBitmapUtils(Context context, String diskCachePath, int memoryCacheSize) {
        super(context, diskCachePath, memoryCacheSize);
    }

    private FrameBitmapUtils(Context context, String diskCachePath, int memoryCacheSize, int diskCacheSize) {
        super(context, diskCachePath, memoryCacheSize, diskCacheSize);
    }

    private FrameBitmapUtils(Context context, String diskCachePath, float memoryCachePercent) {
        super(context, diskCachePath, memoryCachePercent);
    }

    private FrameBitmapUtils(Context context, String diskCachePath, float memoryCachePercent, int diskCacheSize) {
        super(context, diskCachePath, memoryCachePercent, diskCacheSize);
    }


}
