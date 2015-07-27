package com.mmfcommon.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.mmfcommon.R;

/**
 * Created by 黄东鲁 on 15/1/27.
 */
public class ImageBitmapLoadCallBack extends BitmapLoadCallBack<ImageView> {

    int resourceId = R.drawable.image_default_168_120;

    public ImageBitmapLoadCallBack(int resourceId) {
        this.resourceId = resourceId;
    }

    public ImageBitmapLoadCallBack() {
    }

    @Override
    public void onLoadCompleted(ImageView container, String uri, Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
        if (bitmap != null) {
            container.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onLoadFailed(ImageView container, String uri, Drawable drawable) {
        container.setImageResource(resourceId);
    }
}
