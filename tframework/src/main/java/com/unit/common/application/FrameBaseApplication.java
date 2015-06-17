package com.unit.common.application;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.unit.common.config.CommonSetting;
import com.unit.common.utils.FileUtils;
import com.unit.common.utils.SDCardUtils;

import java.io.File;

public class FrameBaseApplication extends Application {


    protected static String TAG = FrameBaseApplication.class.getSimpleName();
    boolean isDelOldDirectory = false;//是否删除旧的程序文件夹
    public static Boolean isAppRunning = false;
    public static Context appContext;

    public static boolean isDownLoadAppCompleted = true;


    @Override
    public void onCreate() {
        super.onCreate();
        isAppRunning = true;
        appContext = this;
        setDelOldDirectory();
        initCommonSetting(this, isDelOldDirectory);
    }

    public static void initCommonSetting(Context context, boolean isDel) {

        String sdFileName = "";// 文件在sd卡上存放目录名字

        CommonSetting.telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        // 获取版本信息
        try {
            PackageInfo pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            CommonSetting.appName = pinfo.packageName;
            CommonSetting.versionCode = pinfo.versionCode;
            CommonSetting.versionName = pinfo.versionName;
            sdFileName = pinfo.packageName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        // 获取屏幕分辨率和密度
        DisplayMetrics metrics = context.getApplicationContext().getResources()
                .getDisplayMetrics();
        CommonSetting.displayMetrics = metrics;
        CommonSetting.screenWidth = metrics.widthPixels;
        CommonSetting.screenHeight = metrics.heightPixels;
        CommonSetting.density = metrics.density;


        String path = SDCardUtils.getSDPath();
        if (path == null) {
            path = context.getApplicationContext().getFilesDir().getAbsolutePath();

        }

//        String visiblePath = path + "/" + sdFileName;
//
//        File visibleFile = new File(visiblePath);
//        if (visibleFile == null || !visibleFile.exists()) {
//
//            visibleFile.mkdirs();
//        }
//
//        //保存用户图片文件夹
//        File visiableImageFile = new File(visiblePath + "/" + "images");
//
//        if (CommonSetting.visibleImageDirectory == null || !CommonSetting.visibleImageDirectory.exists()) {
//            CommonSetting.visibleImageDirectory = visiableImageFile;
//            CommonSetting.visibleImageDirectory.mkdirs();
//        }

        path += "/" + sdFileName;

        File appFileDirectory = new File(path);

        //是否要删除旧版本文件夹
        if (isDel) {
            FileUtils.delete(appFileDirectory);
        }

        //初始化隐藏文件夹
        if (appFileDirectory == null || !appFileDirectory.exists()) {
            appFileDirectory.mkdirs();
        }

        File downloadFile = new File(path + "/download");

        // 初始化文件下载目录
        if (CommonSetting.downloadDirectory == null || !CommonSetting.downloadDirectory.exists()) {
            CommonSetting.downloadDirectory = downloadFile;
            CommonSetting.downloadDirectory.mkdirs();
        }


        File imagesFile = new File(path + "/images");
        // 初始化图片目录
        if (CommonSetting.imageDirectory == null || !CommonSetting.imageDirectory.exists()) {
            CommonSetting.imageDirectory = imagesFile;
            CommonSetting.imageDirectory.mkdirs();
        }
    }

    /**
     * 可以在这里设置  isDelOldDirectory的值来删除就文件夹
     */
    public void setDelOldDirectory() {

    }
}
