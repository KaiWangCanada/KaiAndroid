package com.unit.common.utils.bitmapUtils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.unit.common.utils.LogOutputUtils;

/**
 * Created by Sammy03 on 13-12-27.
 */
public class DefaultBlur {
    static String TAG = "DefaultBlur";

    /* 设置图片模糊 */
    public Bitmap blur(Bitmap bmpSource, int Blur)  //源位图，模糊强度
    {
        int pixels[] = new int[bmpSource.getWidth() * bmpSource.getHeight()];  //颜色数组，一个像素对应一个元素
        int pixelsRawSource[] = new int[bmpSource.getWidth() * bmpSource.getHeight() * 3];  //三原色数组，作为元数据，在每一层模糊强度的时候不可更改
        int pixelsRawNew[] = new int[bmpSource.getWidth() * bmpSource.getHeight() * 3];  //三原色数组，接受计算过的三原色值
        bmpSource.getPixels(pixels, 0, bmpSource.getWidth(), 0, 0, bmpSource.getWidth(), bmpSource.getHeight());  //获取像素点

        //模糊强度，每循环一次强度增加一次
        for (int k = 1; k <= Blur; k++) {
            //从图片中获取每个像素三原色的值
            for (int i = 0; i < pixels.length; i++) {
                pixelsRawSource[i * 3 + 0] = Color.red(pixels[i]);
                pixelsRawSource[i * 3 + 1] = Color.green(pixels[i]);
                pixelsRawSource[i * 3 + 2] = Color.blue(pixels[i]);
            }

            //取每个点上下左右点的平均值作自己的值
            int CurrentPixel = bmpSource.getWidth() * 3 + 3; // 当前处理的像素点，从点(2,2)开始
            for (int i = 0; i < bmpSource.getHeight() - 3; i++) // 高度循环
            {
                for (int j = 0; j < bmpSource.getWidth() * 3; j++) // 宽度循环
                {
                    CurrentPixel += 1;
                    // 取上下左右，取平均值
                    int sumColor = 0; // 颜色和
                    sumColor = pixelsRawSource[CurrentPixel - bmpSource.getWidth() * 3]; // 上一点
                    sumColor = sumColor + pixelsRawSource[CurrentPixel - 3]; // 左一点
                    sumColor = sumColor + pixelsRawSource[CurrentPixel + 3]; // 右一点
                    sumColor = sumColor + pixelsRawSource[CurrentPixel + bmpSource.getWidth() * 3]; // 下一点
                    pixelsRawNew[CurrentPixel] = Math.round(sumColor / 4); // 设置像素点
                }
            }

            //将新三原色组合成像素颜色
            for (int i = 0; i < pixels.length; i++) {
                pixels[i] = Color.rgb(pixelsRawNew[i * 3 + 0], pixelsRawNew[i * 3 + 1], pixelsRawNew[i * 3 + 2]);
            }
        }

        //应用到图像
        Bitmap bmpReturn = Bitmap.createBitmap(bmpSource.getWidth(), bmpSource.getHeight(), Bitmap.Config.ARGB_8888);
        bmpReturn.setPixels(pixels, 0, bmpSource.getWidth(), 0, 0, bmpSource.getWidth(), bmpSource.getHeight());  //必须新建位图然后填充，不能直接填充源图像，否则内存报错

        return bmpReturn;
    }


    public Bitmap createOverBitmap(int width, int height, Bitmap.Config config) {

        Bitmap tmp = Bitmap.createBitmap(width, height, config);
        Bitmap resultBmp = createOverBitmap(tmp);
        tmp.recycle();
        return resultBmp;
    }

    public Bitmap createOverBitmap(Bitmap srcBmp) {

        if (srcBmp != null) {
            Bitmap resultBmp = Bitmap.createBitmap(srcBmp.getWidth(), srcBmp.getHeight(),
                    srcBmp.getConfig());

            Canvas canvas = new Canvas(resultBmp);
            canvas.drawColor(Color.GRAY);
            canvas.save();
            canvas.restore();

            return resultBmp;
        }

        return null;
    }

    /**
     * 模糊操作
     *
     * @param bitmap_main
     * @param bitmap_over
     * @param blur        （0=<blur<=255）值越大越不清
     */
    public Bitmap blurImage(Bitmap bitmap_main, Bitmap bitmap_over, int blur) {

        Bitmap bitmap = Bitmap.createBitmap(bitmap_main.getWidth(),
                bitmap_main.getHeight(), bitmap_main.getConfig());

        Canvas canvas = new Canvas(bitmap);

        int width = bitmap_over.getWidth();
        int height = bitmap_over.getHeight();

        LogOutputUtils.i(TAG, "DefaultBlur.blurImage - bitmap_over size:"
                + width + "," + height);

        //设置覆盖图的图片的宽和高与模糊图片相同
        //bitmap_over = setOverImage(bitmap_over, width,height);
        Paint paint = new Paint();
        //消除锯齿
        paint.setAntiAlias(true);
        //先画要模糊的图片
        canvas.drawBitmap(bitmap_main, 0, 0, paint);
        //设置画笔透明度(透明度越低，越模糊)
        paint.setAlpha(blur);
        //画上覆盖图片
        canvas.drawBitmap(bitmap_over, 0, 0, paint);
        //到这里图片模糊已经完成
        canvas.save();
        canvas.restore();

        return bitmap;
    }

    /**
     * 设置覆盖图片的大小
     *
     * @param bmp
     * @param new_width
     * @param new_height
     * @return
     */
    private Bitmap setOverImage(Bitmap bmp, int new_width, int new_height) {
        Bitmap bitmap = null;
        try {
            int width = bmp.getWidth();
            int height = bmp.getHeight();

            float scale_w = ((float) new_width) / width;
            float scale_h = ((float) new_height) / height;
            // 创建操作图片用的matrix对象
            Matrix matrix = new Matrix();
            // 缩放图片动作
            matrix.postScale(scale_w, scale_h);
            // 创建新的图片
            bitmap = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
