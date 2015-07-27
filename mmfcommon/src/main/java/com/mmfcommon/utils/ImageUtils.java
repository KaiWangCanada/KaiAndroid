package com.mmfcommon.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 黄东鲁 on 15/5/18.
 */
public class ImageUtils {

  public static Bitmap getBitmapFromAsset(Context context, String strName) {
    AssetManager assetManager = context.getAssets();
    InputStream istr = null;
    try {
      istr = assetManager.open(strName);
    } catch (IOException e) {
      e.printStackTrace();
    }
    Bitmap bitmap = BitmapFactory.decodeStream(istr);
    return bitmap;
  }


  public static String saveImageToGallery(Context context, Bitmap bmp, String dir) {
    // 首先保存图片
    File appDir = new File(Environment.getExternalStorageDirectory(), dir);
    if (!appDir.exists()) {
      appDir.mkdir();
    }
    String fileName = System.currentTimeMillis() + ".jpg";
    File file = new File(appDir, fileName);
    try {
      FileOutputStream fos = new FileOutputStream(file);
      bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
      fos.flush();
      fos.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    String url = "";
    // 其次把文件插入到系统图库
    try {
      url=  MediaStore.Images.Media.insertImage(context.getContentResolver(),
          file.getAbsolutePath(), fileName, null);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    // 最后通知图库更新
    context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(
        "file://" +  url)));

    if (!bmp.isRecycled()){
      bmp.recycle();
      System.gc();
    }
    return file.getAbsolutePath();
  }
}
