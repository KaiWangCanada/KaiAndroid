package com.unit.common.utils.bitmapUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.View;

import com.unit.common.utils.LogOutputUtils;

import java.io.File;

/**
 * 位图生成器
 * Created by Sammy03 on 14-9-3.
 */
public class BitmapCreator {

    static String TAG = "BitmapCreator";

    /**
     * 根据bitmap转换原型图
     *
     * @param src
     * @return
     */
    public static Bitmap getCircleImage(Bitmap src) {

        Bitmap result = null;
        try {
            //计算半径
            int rad = (src.getWidth() > src.getHeight()) ?
                    src.getHeight() : src.getWidth();
            //剪取圆形
            result = createCircleImage(src, rad);
        } catch (Exception e) {
            LogOutputUtils.e("create circle image error", e.toString());
        }

        return result;
    }

    /**
     * 根据view获取位图
     * srcView:生成位图的源视图
     * isAdapt: 是否根据特定宽度生成位图, true:是; false:否
     * resultWidth: 当isAdapt为true时所设置的宽度
     */
    public static Bitmap getBitmapFromView(View srcView, boolean isAdapt, int resultWidth) {

        Bitmap resultBmp;

        LogOutputUtils.i(TAG, "srcView.getWidth:" + srcView.getWidth());
        LogOutputUtils.i(TAG, "srcView.getHeight:" + srcView.getHeight());

        if (srcView.getWidth() != 0 && srcView.getHeight() != 0) {
            srcView.buildDrawingCache();
            resultBmp = srcView.getDrawingCache();

        } else if (isAdapt) {

            srcView.measure(View.MeasureSpec.makeMeasureSpec(resultWidth, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            srcView.layout(0, 0, srcView.getMeasuredWidth(), srcView.getMeasuredHeight());
            srcView.buildDrawingCache();
            resultBmp = srcView.getDrawingCache();
        } else {

            srcView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            srcView.layout(0, 0, srcView.getMeasuredWidth(), srcView.getMeasuredHeight());
            srcView.buildDrawingCache();
            resultBmp = srcView.getDrawingCache();
        }


//        view.setDrawingCacheEnabled(true);
//        view.buildDrawingCache();
//        resultBmp = view.getDrawingCache();
//        view.setDrawingCacheEnabled(false);

        LogOutputUtils.i(TAG, "view size:" + srcView.getWidth() + "," + srcView.getHeight());
        LogOutputUtils.i(TAG, "getMeasuredWidth:" + srcView.getMeasuredWidth() + " getMeasuredHeight:" + srcView.getMeasuredHeight());
        LogOutputUtils.i(TAG, "width * height:" + srcView.getMeasuredWidth() * srcView.getMeasuredHeight() * 4);
//        Logger.i("MaximumDrawingCacheSize:" + ViewConfiguration.get(getActivity()).getScaledMaximumDrawingCacheSize());
        LogOutputUtils.i(TAG, "getBitmapFromView:" + resultBmp);
        return resultBmp;
    }


    /**
     * 根据原图和变长绘制圆形图片
     * 其实主要靠：paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
     * 这行代码，为什么呢，我给大家解释下，SRC_IN这种模式，两个绘制的效果叠加后取交集展现后图，
     * 怎么说呢，咱们第一个绘制的是个圆形，第二个绘制的是个Bitmap，于是交集为圆形，展现的是BItmap，
     * 就实现了圆形图片效果。
     *
     * @param source
     * @param min
     * @return
     */
    public static Bitmap createCircleImage(Bitmap source, int min) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        /**
         * 产生一个同样大小的画布
         */
        Canvas canvas = new Canvas(target);
        /**
         * 首先绘制圆形
         */
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        /**
         * 使用SRC_IN
         */
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        /**
         * 绘制图片
         */
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    /**
     * 根据原图,取原图长宽最短一边绘制圆形图片
     *
     * @param source
     * @return
     */
    public static Bitmap createCircleImage(Bitmap source) {

        if (source != null) {
            int rad = (source.getWidth() > source.getHeight()) ?
                    source.getHeight() : source.getWidth();
            return createCircleImage(source, rad);
        } else {
            LogOutputUtils.i(TAG, "source is null");
            return null;
        }
    }

    public static Bitmap createBitmapBySize(String filePath, int width, int height) {

        Bitmap resultBmp = null;
        File file = new File(filePath);
        if (file.exists()) {

            try {

                Options options = BitmapTools.getBitmapInfoByFile(filePath);
                if ((options.outWidth > width || options.outHeight > height)
                        && width > 0 && height > 0) {
                    int scaleFactor = Math.max(options.outWidth / width,
                            options.outHeight / height);
                    options.inSampleSize = (scaleFactor == 0) ? 1 : scaleFactor;
                    options.inJustDecodeBounds = false;
                    LogOutputUtils.i(TAG, "scaleFactor:" + scaleFactor);
                    resultBmp = BitmapFactory.decodeFile(filePath, options);
                } else {
                    resultBmp = BitmapFactory.decodeFile(filePath);
                }

            } catch (Exception e) {
                LogOutputUtils.e(TAG, e.toString());
            }
        }

        return resultBmp;
    }
}
