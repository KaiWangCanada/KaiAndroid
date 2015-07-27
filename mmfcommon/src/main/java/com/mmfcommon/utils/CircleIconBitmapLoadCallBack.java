package com.mmfcommon.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.arasthel.asyncjob.AsyncJob;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.mmfcommon.R;
import com.unit.common.utils.bitmapUtils.BitmapCreator;

/**
 * Created by 黄东鲁 on 15/1/27.
 */
public class CircleIconBitmapLoadCallBack extends BitmapLoadCallBack<ImageView>{

    @Override
    public void onLoadCompleted(final ImageView container, String uri, final Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
        if (bitmap != null) {
            new AsyncJob.AsyncJobBuilder<Bitmap>()
                   .doInBackground(new AsyncJob.AsyncAction<Bitmap>() {
                       @Override
                       public Bitmap doAsync() {
                           return BitmapCreator.getCircleImage(bitmap);
                       }
                   })
                    .doWhenFinished(new AsyncJob.AsyncResultAction<Bitmap>() {
                        @Override
                        public void onResult(Bitmap b) {
                            container.setImageBitmap(b);
                        }
                    }).create().start();
        }
    }

    @Override
    public void onLoadFailed(ImageView container, String uri, Drawable drawable) {
        container.setImageResource(R.drawable.icon_head_default);
    }
}
