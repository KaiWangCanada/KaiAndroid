package com.unit.common.utils.bitmapUtils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.lidroid.xutils.bitmap.core.BitmapDecoder;
import com.unit.common.config.CommonSetting;
import com.unit.common.utils.LogOutputUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Sammy03 on 14/11/26.
 */
public class BitmapTools {

    static String TAG = "BitmapTools";

    /**
     * 获取图片信息
     *
     * @param context 上下文对象
     * @param res     图片资源id
     * @return
     */
    public static BitmapFactory.Options getBitmapInfoByResource(Context context, int res) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        try {
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(context.getResources(), res, options);
        } catch (Exception e) {
            LogOutputUtils.e(TAG, "decode bitmap error, can not get the bitmap options" + e.toString());
        }

        return options;
    }

    /**
     * 获取图片信息
     *
     * @param path 图片路径
     * @return
     */
    public static BitmapFactory.Options getBitmapInfoByFile(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        try {
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
        } catch (Exception e) {
            LogOutputUtils.e(TAG, "decode bitmap error, can not get the bitmap options" + e.toString());
        }

        return options;
    }

    /**
     * 获取图片信息
     *
     * @param ins 图片输入流
     * @return
     */
    public static BitmapFactory.Options getBitmapInfoByStream(InputStream ins) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        try {
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(ins, null, options);
        } catch (Exception e) {
            LogOutputUtils.e(TAG, "decode bitmap error, can not get the bitmap options" + e.toString());
        }

        return options;
    }

    /**
     * 缩放图片
     *
     * @param srcBmp
     * @param width
     * @param height
     * @return
     */
    public static Bitmap scaleBitmap(Bitmap srcBmp, float width, float height) {

        Bitmap bitmap = null;
        try {
            float scaleW = width / srcBmp.getWidth();
            float scaleH = height / srcBmp.getHeight();

            Matrix matrix = new Matrix();
            matrix.postScale(scaleW, scaleH);
            bitmap = Bitmap.createBitmap(srcBmp, 0, 0, srcBmp.getWidth(),
                    srcBmp.getHeight(), matrix, true);
        } catch (Exception e) {
            bitmap = srcBmp;
            LogOutputUtils.e(TAG, "create bitmap faild" + e.toString());
        }
        return bitmap;
    }

    /**
     * 把图片储存到本地
     *
     * @param bitmap
     * @param path
     */
    public static File saveBitmapToFile(Bitmap bitmap, String path)
            throws Exception {

        File file = null;
        if (bitmap != null) {
            file = new File(path);
            FileOutputStream fOut;
            file.createNewFile();
            fOut = new FileOutputStream(file);
            fOut.flush();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.close();
        }//end of if

        return file;
    }// end of bitmapToFile()

    /**
     * 把图片储存到本地
     *
     * @param bitmap
     * @param path
     */
    public static File saveBitmapToFile(Bitmap bitmap, String path, int compress)
            throws Exception {

        File file = null;
        if (bitmap != null) {
            file = new File(path);
            FileOutputStream fOut;
            file.createNewFile();
            fOut = new FileOutputStream(file);
            fOut.flush();
            bitmap.compress(Bitmap.CompressFormat.PNG, compress, fOut);
            fOut.close();
        }//end of if

        return file;
    }// end of bitmapToFile()

    /**
     * 按质量压缩图片
     *
     * @param bitmap  源图片
     * @param quality 图片质量(KB)
     * @return
     * @throws java.io.IOException
     */
    public static Bitmap compressImage(Bitmap bitmap, int quality) throws OutOfMemoryError, Exception {
        Log.d("图片压缩", "压缩开始");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        if (baos.toByteArray().length / 1024 <= quality) {
            return bitmap;
        }
        int options = 90;
        int temp = baos.toByteArray().length / 1024;
        //循环判断如果压缩后图片是否大于quality(kb),大于继续压缩
        if (quality < temp) {
            options = (int) ((float) (quality) / temp * 100);
            Log.d("图片压缩", "压缩比例::" + options);
            if (options < 30) {
                options = 30;
            }
        }
//        Log.d("图片压缩", "压缩比例" + options);
//        //重置baos即清空baos
        baos.reset();
//        //这里压缩options%，把压缩后的数据存放到baos中
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
//        Log.d("图片压缩", "压缩前图片大小 = " + temp + "kb, 压缩后图片大小 = " + baos.toByteArray().length / 1024 + "kb");

//        while (baos.toByteArray().length / 1024 > quality) {
//            //重置baos即清空baos
//            baos.reset();
//            //这里压缩options%，把压缩后的数据存放到baos中
//            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
//
//            options -= 10;//每次都减少10
//            if (options == 0) {
//                options = 8;
//            } else if (options < 0) {
//                options = 30;
//                baos.reset();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
//                break;
//            }
//        }
        Log.d("图片压缩", "压缩比例" + options);
        Log.d("图片压缩", "压缩前图片大小 = " + temp + "kb, 压缩后图片大小 = " + baos.toByteArray().length / 1024 + "kb");

        //把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        //把ByteArrayInputStream数据生成图片
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inJustDecodeBounds = false;
        bitmapOptions.inSampleSize = 5;   //width，hight设为原来的十分一
        bitmap = BitmapFactory.decodeStream(isBm, null, bitmapOptions);

//        bitmap = BitmapFactory.decodeStream(isBm, null, null);
        baos.close();
        return bitmap;
    }

    public static int compressSize = 8000000;

    /**
     * 按质量压缩图片
     *
     * @param bitmap 源图片
     * @return
     * @throws java.io.IOException
     */
    public static Bitmap compressImage(Bitmap bitmap) throws Exception, OutOfMemoryError {
        Bitmap tempBitmap = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            int bitmapInSize;

            int bitmapPX = bitmap.getWidth() * bitmap.getHeight();
            int limit = compressSize + 1; //300万像素
            bitmapInSize = bitmapPX / limit + 1;

            int compressScale;
            if (bitmapInSize > 1) {
                compressScale = (int) (1 / (float) (bitmapInSize - 1) * 100);
            } else {
                compressScale = 100;
            }

            //重置baos即清空baos
            baos.reset();
            //这里压缩options%，把压缩后的数据存放到baos中
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressScale, baos);

            //把压缩后的数据baos存放到ByteArrayInputStream中
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
            //把ByteArrayInputStream数据生成图片
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inJustDecodeBounds = false;
            bitmapOptions.inSampleSize = bitmapInSize;   //width，hight设为原来的inSampleSize分一
            bitmap = BitmapFactory.decodeStream(isBm, null, bitmapOptions);

            baos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            baos.close();
        } catch (OutOfMemoryError error) {
            throw error;
        } finally {
            baos.close();
        }

        return bitmap;
    }

    /**
     * 按质量压缩图片
     *
     * @param filePath 源图片
     * @return
     * @throws java.io.IOException
     */
    public static Bitmap compressImage(String filePath) throws IOException, OutOfMemoryError {
        Bitmap tempBitmap = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inJustDecodeBounds = true;//不把图片加入内存,只是为了拿出图片的宽高,来计算我们要压缩的宽高值
            bitmapOptions.inSampleSize = 1;
            tempBitmap = BitmapFactory.decodeFile(filePath, bitmapOptions);

            int bitmapInSize;

            int bitmapPX = bitmapOptions.outWidth * bitmapOptions.outHeight;
            int limit = compressSize; //300万像素
            bitmapInSize = bitmapPX / limit + 1;//算好压缩的宽高值

            int compressScale;
            if (bitmapInSize > 1) {
                compressScale = (int) (1 / (float) (bitmapInSize - 1) * 100);
            } else {
                compressScale = 100;
            }

            bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inJustDecodeBounds = false;//把图片加入内存,并拿到bitmap
            bitmapOptions.inSampleSize = bitmapInSize;//得到之前压缩的值
            tempBitmap = BitmapFactory.decodeFile(filePath, bitmapOptions);

            //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            tempBitmap.compress(Bitmap.CompressFormat.JPEG, compressScale, baos);
            //把压缩后的数据baos存放到ByteArrayInputStream中
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
            //把ByteArrayInputStream数据生成图片
            bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inJustDecodeBounds = false;
            bitmapOptions.inSampleSize = bitmapInSize;   //width，hight设为原来的inSampleSize分一
            tempBitmap = BitmapFactory.decodeStream(isBm, null, bitmapOptions);

            baos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            baos.close();
        } catch (OutOfMemoryError error) {
            throw error;
        } finally {
            baos.close();
        }

        return tempBitmap;
    }

    /**
     * 保存图片到图库
     *
     * @param context
     * @param bmp
     */
    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(CommonSetting.imageDirectory, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
    }

    /**
     * 保存图片到图库
     *
     * @param context
     * @param file
     */
    public static void saveImageToGallery(Context context, File file) {
        // 首先保存图片

        Bitmap bmp = BitmapDecoder.decodeFile(file.getAbsolutePath());
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), file.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
    }

    /**
     * 文件转成图片
     *
     * @param filePath
     * @return
     */
    public static Bitmap GetBitmapFromfile(String filePath) {
        Bitmap bitmap = null;
        try {
            bitmap = compressImage(filePath);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return bitmap;
    }

    /**
     * 文件转成图片
     *
     * @param file
     * @return
     */
    public static Bitmap GetBitmapFromfile(File file) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapDecoder.decodeFile(file.getAbsolutePath());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return bitmap;
    }

    public static void Recyle(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            // 回收并且置为null
            bitmap.recycle();
            bitmap = null;
        }
        System.gc();
    }

}
