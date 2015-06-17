package com.unit.common.utils;

import android.util.Log;

/**
 * 主要控制logcat 输出 项目名称：app43 类名称：CommonContrl 类描述： 创建人：APP43 创建时间：2012-2-20
 * 上午9:18:12 修改人：APP43 修改时间：2012-2-20 上午9:18:12 修改备注：
 */
public class LogOutputUtils {

    public static boolean isDebug = true;

    public static void e (String tag, String msg) {
        if (isDebug) {
            try {
                Log.e(tag, msg);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public static void i (String tag, String msg) {
        if (isDebug) {
            try {
                Log.i(tag, msg);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public static void v (String tag, String msg) {
        if (isDebug) {
            try {
                Log.v(tag, msg);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public static void d (String tag, String msg) {
        if (isDebug) {
            try {
                Log.d(tag, msg);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

}
