package com.unit.common.config;

import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.io.File;

/**
 * 通用系统环境变量，须由应用进行初始化，供其他通用API使用
 */
public class CommonSetting {
    // 设备信息
    public static TelephonyManager telephonyManager;

    public static String appName; // 应用名，当前APP的包名
    public static int versionCode; // 版本号，对应于Manifest文件里的versionCode
    public static String versionName; // 用作显示的版本号，对应于Manifest文件里的versionName

    public static String Content_Not_Match = "Content_Not_Match";//服务器返回内容不符
    public static String Content_Is_Null = "Content_Is_Null";//服务器返回内容为空

    // 应用辅助类信息
    public static DisplayMetrics displayMetrics; // 显示参数类
    public static int screenHeight = 0;
    public static int screenWidth = 0;
    public static float density = 0;

//    public static File visibleImageDirectory;   //保存图片的文件夹

    //用户不可见 ./
    public static File downloadDirectory;   //下载文件夹
    public static File imageDirectory;      //图片缓存的文件夹

    public static final int HTTP_SUCCESS_CODE = 1;
    public static final int HTTP_NoData_CODE = -1;

    public final static int SUCCESS = 1;
    public final static int FAIL = 0;

    public final static String AppIsRunning = "AppIsRunning";

    public final static String AppIsExit = "AppIsExit";

    public static String FileName = ".tframework";//默认文件夹名称
    public static String FilePath;

    public final static String UPDATE_APK_URL = "APK_URL";
}
