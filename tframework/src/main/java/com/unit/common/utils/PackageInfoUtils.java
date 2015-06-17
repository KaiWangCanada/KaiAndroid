package com.unit.common.utils;

import android.content.*;
import android.content.pm.*;
import android.content.pm.PackageManager.*;

/**
 * Created by kingkong on 14-4-24.
 */
public class PackageInfoUtils {

    public static PackageInfo GetPackageInfo (Context context) {
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return pi;
    }


    public static String GetVersionName (Context context) {
        String versionName = "";
        try {
            versionName = GetPackageInfo(context).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return versionName;

    }

    public static int GetVersionCode (Context context) {
        int versionCode = 0;
        try {
            versionCode = GetPackageInfo(context).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return versionCode;

    }

}
