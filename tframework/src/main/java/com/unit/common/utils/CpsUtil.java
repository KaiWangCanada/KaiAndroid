package com.unit.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.io.InputStream;

/**
 * 获取cpsId的工具类。
 * cpsId应该保存在assets文件夹下的cps.txt（可配置,搜索cps.txt替换即可）
 * Created by longbin on 14-10-11.
 */
public class CpsUtil {

    /**
     * 获取cpsId
     * @param context 上下文环境
     * @return cpsId，可能为null
     */
    public static String getCpsId(Context context) {
        if (null == context) {
            return "";
        }
        String cps = getCPSFromLocalFile(context);
        if (TextUtils.isEmpty(cps)) {
            cps = getCpsIdFromAssets(context);
        }
        return cps;
    }

    /**
     *  读取assets文件的cpsId
     * @param context 上下文环境
     * @return cpsId，可能为null
     */
    public static String getCpsIdFromAssets(Context context){
        String fileName = "cps.txt";// assets下文件
        String cps = "";
        try {
            InputStream is = context.getResources().getAssets().open(fileName);
            int len = is.available();
            byte []buffer = new byte[len];
            is.read(buffer);
            cps = new String(buffer, "UTF-8");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != cps) {
            //保存cps到本地，防止用户直接更新到没有cps的新版本导致cps关联失败
            cps.trim();
            saveCpsToLocalFile(context, cps);
        }
        return cps;
    }

    /**
     * 读取本地的SharedPreferences文件的cpsId
     * @param context 上下文环境
     * @return cpsId，可能为null
     */
    public static String getCPSFromLocalFile(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString("CPS", null);
    }

    /**
     * 把cpsId保存到本地的SharedPreferences文件里
     * @param context 上下文环境
     * @param cps cpsId
     */
    private static void saveCpsToLocalFile(Context context, String cps) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("CPS", cps);
        editor.commit();
    }
}
