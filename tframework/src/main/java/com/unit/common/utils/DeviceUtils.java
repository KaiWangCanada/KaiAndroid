package com.unit.common.utils;

import android.os.Build;

/**
 * Created by longbin on 14-8-28.
 */
public class DeviceUtils {

    public static int getOSVersionCode() {
        return Build.VERSION.SDK_INT;
    }

    public static String getOSBrand() {
        return Build.BRAND;
    }

    public static String getFactory() {
        return Build.PRODUCT;
    }

    public static String getDeviceId() {
        return Build.SERIAL;
    }
}
