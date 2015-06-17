package com.wq.photo;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * 调用媒体选择库
 * 需要在inten中传递2个参数
 * 1. 选择模式 chose_mode  0  //单选 1多选
 * 2. 选择张数 max_chose_count  多选模式默认 9 张
 */
public class MediaChoseActivity extends ActionBarActivity {

  public static final int CHOSE_MODE_SINGLE = 0;
  public static final int CHOSE_MODE_MULTIPLE = 1;
  public int max_chose_count = 1;
  public LinkedHashMap<String, String> imageMap = new LinkedHashMap<>();
  PhotoGalleryFragment photoGalleryFragment;
  int choseMode = CHOSE_MODE_MULTIPLE;

  boolean isNeedCrop = false;
  int crop_image_w, crop_image_h;

  boolean isPreviewOnly = false;

  public static final String EXTRA_DATA = "data";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_media_chose);


    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    choseMode = getIntent().getIntExtra("chose_mode", CHOSE_MODE_MULTIPLE);
    isPreviewOnly = getIntent().getBooleanExtra("is_preview", false);
    if (choseMode == 1) {
      max_chose_count = getIntent().getIntExtra("max_chose_count", 9);
    }
    //是否需要剪裁
    isNeedCrop = getIntent().getBooleanExtra("crop", false);
    if (isNeedCrop) {
      choseMode = CHOSE_MODE_SINGLE;
      max_chose_count = 1;
      crop_image_w = getIntent().getIntExtra("crop_image_w", 720);
      crop_image_h = getIntent().getIntExtra("crop_image_h", 720);
    }
    if (!isPreviewOnly) {
      photoGalleryFragment = PhotoGalleryFragment.newInstance(choseMode, max_chose_count);
      fragmentTransaction.add(R.id.container, photoGalleryFragment,
          PhotoGalleryFragment.class.getSimpleName());
      fragmentTransaction.commit();
    } else {
      List<String> imageList = getIntent().getStringArrayListExtra("preview_images");
      String currentImage = getIntent().getStringExtra("current_image");
      for (String imagePath : imageList) {
        HashMap<String, String> map = new HashMap<>();
        map.put(imagePath, imagePath);
        imageMap.putAll(map);
      }
      startPreview(getImageChoseMap(), currentImage);
    }
  }

  boolean isPreview = false;

  public void startPreview(LinkedHashMap map, String currentimage) {
    if (isNeedCrop && !isCropOver) {
      sendStarCrop(currentimage);
    } else {
      Set<String> keys = map.keySet();
      ArrayList<String> ims = new ArrayList<>();
      int pos = 0;
      int i = 0;
      for (String s : keys) {
        ims.add((String) map.get(s));
        if (map.get(s).equals(currentimage)) {
          pos = i;
        }
        i++;
      }
      FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
      fragmentTransaction.add(R.id.container, ImagePreviewFragment.newInstance(ims, pos),
          ImagePreviewFragment.class.getSimpleName());
      fragmentTransaction.addToBackStack("con");
      fragmentTransaction.commit();
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      isPreview = true;
      invalidateOptionsMenu();
    }
  }

  public Fragment getCurrentFragment(String tag) {
    return getSupportFragmentManager().findFragmentByTag(tag);
  }

  @Override protected void onResume() {
    super.onResume();
  }

  public LinkedHashMap getImageChoseMap() {
    return imageMap;
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.photo_gallery_menu, menu);
    if (isPreview && (choseMode == CHOSE_MODE_MULTIPLE)) {
      menu.findItem(R.id.menu_photo_delete).setVisible(true);
    } else {
      menu.findItem(R.id.menu_photo_delete).setVisible(false);
    }
    if (imageMap.size() < 1) {
      menu.findItem(R.id.menu_photo_count).setEnabled(false);
      menu.findItem(R.id.menu_photo_count).setVisible(false);
    } else {
      menu.findItem(R.id.menu_photo_count).setEnabled(true);
      menu.findItem(R.id.menu_photo_count).setVisible(true);
      if (choseMode == CHOSE_MODE_MULTIPLE) {
        menu.findItem(R.id.menu_photo_count)
            .setTitle(getString(R.string.save) + "(" + imageMap.size() + "/" + max_chose_count + ")");
      } else {
        menu.findItem(R.id.menu_photo_count).setTitle("发送(1)");
      }
    }
    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      if (isPreviewOnly) {
        finish();
      } else {
        popFragment();
      }
    } else if (item.getItemId() == R.id.menu_photo_delete) {
      ImagePreviewFragment fragment =
          (ImagePreviewFragment) getCurrentFragment(ImagePreviewFragment.class.getSimpleName());
      if (fragment != null) {
        String img = fragment.delete();
        Iterator iterator = imageMap.keySet().iterator();
        while (iterator.hasNext()) {
          String key = (String) iterator.next();
          if (imageMap.get(key).equals(img)) {
            iterator.remove();
          }
        }
        invalidateOptionsMenu();

        if (isPreviewOnly && imageMap.isEmpty()) {
          setResult(RESULT_OK);
          finish();
        }

      }
    } else if (item.getItemId() == R.id.menu_photo_count) {
      sendImages();
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
    FragmentManager fm = getSupportFragmentManager();
    if (keyCode == KeyEvent.KEYCODE_BACK && fm.getBackStackEntryCount() > 0) {
      if (isPreviewOnly) {
        finish();
      } else {
        popFragment();
      }
      return false;
    }
    return super.onKeyDown(keyCode, event);
  }

  public void log(String msg) {
    Log.i("gallery", msg);
  }

  public void popFragment() {
    FragmentManager fm = getSupportFragmentManager();
    fm.popBackStackImmediate();
    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    isPreview = false;
    invalidateOptionsMenu();
    if (photoGalleryFragment != null && choseMode == CHOSE_MODE_MULTIPLE) {
      photoGalleryFragment.notifyDataSetChanged();
    }
  }

  boolean isCropOver = false;

  public void sendImages() {
    if (isNeedCrop && !isCropOver) {
      Iterator iterator = imageMap.keySet().iterator();
      File file = new File(iterator.next().toString());
      if (!file.exists()) {
        Toast.makeText(this, R.string.file_not_exist, Toast.LENGTH_SHORT).show();
      }
      sendStarCrop(file.getAbsolutePath());
    } else {
      Intent intent = new Intent();
      ArrayList<String> img = new ArrayList<>();
      Iterator iterator = imageMap.keySet().iterator();
      while (iterator.hasNext()) {
        String key = (String) iterator.next();
        img.add((String) imageMap.get(key));
      }
      intent.putExtra(EXTRA_DATA, img);
      setResult(RESULT_OK, intent);
      finish();
    }
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CROP && (choseMode
        == CHOSE_MODE_SINGLE)) {
      Intent intent = new Intent();
      ArrayList<String> img = new ArrayList<>();
      String crop_path = data.getStringExtra("crop_path");
      isCropOver = true;
      if (crop_path != null && new File(crop_path) != null) {
        img.add(crop_path);
        intent.putExtra(EXTRA_DATA, img);
        setResult(RESULT_OK, intent);
        finish();
      } else {
        Toast.makeText(this, "截取图片失败", Toast.LENGTH_SHORT).show();
      }
    } else if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CAMERA && (choseMode
        == CHOSE_MODE_SINGLE)) {
      if (currentfile != null && currentfile.exists() && currentfile.length() > 10) {
        if (isNeedCrop && !isCropOver) {
          sendStarCrop(currentfile.getAbsolutePath());
        } else {
          Intent intent = new Intent();
          ArrayList<String> img = new ArrayList<>();
          img.add(currentfile.getAbsolutePath());
          intent.putExtra(EXTRA_DATA, img);
          setResult(RESULT_OK, intent);
          finish();
        }
        insertImage(currentfile.getAbsolutePath());
      } else {
        Toast.makeText(MediaChoseActivity.this, "获取图片失败", Toast.LENGTH_SHORT).show();
      }
    } else if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CAMERA && (choseMode == CHOSE_MODE_MULTIPLE)) {

      if (currentfile != null && currentfile.exists() && currentfile.length() > 10) {
        getImageChoseMap().put(currentfile.getAbsolutePath(), currentfile.getAbsolutePath());
        invalidateOptionsMenu();
        insertImage(currentfile.getAbsolutePath());
      } else {
        Toast.makeText(MediaChoseActivity.this, "获取图片失败", Toast.LENGTH_SHORT).show();
      }
    }
  }

  public void insertImage(String fileName) {
    try {
      MediaStore.Images.Media.insertImage(getContentResolver(), fileName,
          new File(fileName).getName(), new File(fileName).getName());
      Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
      Uri uri = Uri.fromFile(new File(fileName));
      intent.setData(uri);
      sendBroadcast(intent);
      MediaScannerConnection.scanFile(this, new String[] { fileName },
          new String[] { "image/jpeg" }, new MediaScannerConnection.MediaScannerConnectionClient() {
        @Override public void onMediaScannerConnected() {
        }

        @Override public void onScanCompleted(String path, Uri uri) {
          photoGalleryFragment.addCaptureFile(path);
        }
      });
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static final int REQUEST_CODE_CAMERA = 2001;
  public static final int REQUEST_CODE_CROP = 2002;
  File currentfile;

  public void sendStarCamera() {
    currentfile = getTempFile();
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(currentfile));
    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
    startActivityForResult(intent, REQUEST_CODE_CAMERA);
  }

  public void sendStarCrop(String path) {
    Intent intent = new Intent(this, CropImageActivity.class);
    intent.setData(Uri.fromFile(new File(path)));
    intent.putExtra("crop_image_w", crop_image_w);
    intent.putExtra("crop_image_h", crop_image_h);
    intent.putExtra(MediaStore.EXTRA_OUTPUT, getCropFile().getAbsolutePath());
    startActivityForResult(intent, REQUEST_CODE_CROP);
  }

  public File getTempFile() {
    String str = null;
    Date date = null;
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    date = new Date(System.currentTimeMillis());
    str = format.format(date);
    return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
        "IMG_" + str + ".jpg");
  }

  public File getCropFile() {
    return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
        ".crop.jpg");
  }
}
