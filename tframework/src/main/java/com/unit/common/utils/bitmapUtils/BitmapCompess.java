package com.unit.common.utils.bitmapUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by kingkong on 2014/10/16.
 */
public class BitmapCompess {
    private final static String TAG="ImageUtils";
    /**
     * 圆角图片
     * @param source 原始图片
     * @return
     */
    public static Bitmap getFilletImage(Bitmap source){
        int min=Math.min(source.getWidth(), source.getHeight());
        return getFilletImage(source, min, min);
    }

    /**
     * 圆角图片
     * @param source 原始图片
     * @param width 宽
     * @param height 高
     * @return
     */
    public static Bitmap getFilletImage(Bitmap source, int width, int height){
        return getFilletImage(source, width, height, 10f, 10f);
    }

    /**
     * 圆角图片
     * @param source 原始图片
     * @param rx x半径
     * @param ry y半径
     * @return
     */
    public static Bitmap getFilletImage(Bitmap source, float rx, float ry){
        int min=Math.min(source.getWidth(), source.getHeight());
        return getFilletImage(source, min, min, rx, ry);
    }

    /**
     * 圆角图片
     * @param source 原始图片
     * @param width 宽
     * @param height 高
     * @param rx x半径
     * @param ry y半径
     * @return
     */
    public static Bitmap getFilletImage(Bitmap source, int width, int height, float rx, float ry){
        Paint paint = new Paint();
        Bitmap target = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        RectF rect=new RectF();
        rect.bottom=height;
        rect.top=0;
        rect.left=0;
        rect.right=width;
        canvas.drawRoundRect(rect, rx, ry, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    /**
     * 根据路径获取Bitmap
     * @param path 路径
     * @return
     * @throws java.io.IOException
     */
    public static Bitmap getBitmapByPath(String path) throws IOException {
        return getBitmapByPath(path, 100, 100);
    }

    /**
     * 根据路径获取Bitmap
     * @param path 路径
     * @param width 最大宽度
     * @param height 最大高度
     * @return
     * @throws java.io.IOException
     */
    public static Bitmap getBitmapByPath(String path, int width, int height) throws IOException {
        return getBitmapByPath(new File(path), width, height);
    }

    public static Bitmap getBitmapByPath(File file, int width, int height) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= width)
                    && (options.outHeight >> i <= height)) {
                in = new BufferedInputStream(
                        new FileInputStream(file));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }

    /**
     * 压缩图片，默认格式为jpg
     * @param srcPath 源图片路径
     * @param quality 图片质量（0-100）,100为原图质量
     * @param targetPath 输出压缩后图片的文件路径
     * @throws java.io.IOException
     */
    public static void compress(String srcPath, int quality, String targetPath) throws IOException {
        compress(srcPath, quality, Integer.MAX_VALUE, Integer.MAX_VALUE, targetPath);
    }

    /**
     * 压缩图片，默认格式为jpg，若图片大于最大宽或高，将按比例缩小
     * @param srcPath 源图片路径
     * @param quality 图片质量（0-100）,100为原图质量
     * @param maxWidth 最大宽（将自动识别，数值大的对应长边）
     * @param maxHeight 最大高（将自动识别，数值大的对应长边）
     * @param targetPath 输出压缩后图片的文件路径
     * @throws java.io.IOException
     */
    public static void compress(String srcPath, int quality, int maxWidth, int maxHeight, String targetPath) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcPath, options);
        int scaleW, scaleH;
        if(options.outWidth > options.outHeight){
            int maxW=Math.max(maxWidth, maxHeight);
            int maxH=Math.min(maxWidth, maxHeight);
            scaleW=options.outWidth > maxW ? (options.outWidth / maxW)+1 : 1;
            scaleH=options.outHeight > maxH ? (options.outHeight / maxH)+1 : 1;
        }else{
            int maxW=Math.min(maxWidth, maxHeight);
            int maxH=Math.max(maxWidth, maxHeight);
            scaleW=options.outWidth > maxW ? (options.outWidth / maxW)+1 : 1;
            scaleH=options.outHeight > maxH ? (options.outHeight / maxH)+1 : 1;
        }
        //int scaleW=options.outWidth > maxWidth ? (options.outWidth / maxWidth)+1 : 1;
        //int scaleH=options.outHeight > maxHeight ? (options.outHeight / maxHeight)+1 : 1;
        int scale = Math.max(scaleW, scaleH);
        options.inSampleSize=scale;
        options.inDither=false;
        options.inPreferredConfig=null;
        options.inJustDecodeBounds=false;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, options);
        File targetFile=new File(targetPath);
        FileOutputStream out=new FileOutputStream(targetFile);
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);

        Log.i(TAG, "compress "+srcPath+" --> "+targetPath);
        Log.i(TAG, "compress quality="+quality+",outWidth="+options.outWidth+",outHeight="+options.outHeight);
    }
}
