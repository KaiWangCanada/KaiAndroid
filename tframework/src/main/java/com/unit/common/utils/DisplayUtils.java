package com.unit.common.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.ViewConfiguration;

/**
 * 用于转换界面尺寸的工具类
 */
public class DisplayUtils {

    //转换dip为px
    public static int convertDIP2PX(Context context, int dip) {
        float scale = context.getResources().getDisplayMetrics().densityDpi;

        return (int) (dip * (scale / 160));
    }

    //转换px为dip
    public static int convertPX2DIP(Context context, int px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) ((px * 160) / scale);
    }


    public static final int getHeightInPx(Context context) {
        final int height = context.getResources().getDisplayMetrics().heightPixels;
        return height;
    }

    public static final int getWidthInPx(Context context) {
        final int width = context.getResources().getDisplayMetrics().widthPixels;
        return width;
    }

    public static final int getHeightInDp(Context context) {
        final float height = context.getResources().getDisplayMetrics().heightPixels;
        int heightInDp = px2dip(context, height);
        return heightInDp;
    }

    public static final int getWidthInDp(Context context) {
        final float height = context.getResources().getDisplayMetrics().heightPixels;
        int widthInDp = px2dip(context, height);
        return widthInDp;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (spValue * scale + 0.5f);
    }

    public static int getNavigationBarHeight(Resources res, Context context) {
        final int apiLevel = Build.VERSION.SDK_INT;
        if ((apiLevel >= Build.VERSION_CODES.HONEYCOMB && apiLevel <= Build.VERSION_CODES.HONEYCOMB_MR2)
                ||
                (apiLevel >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && !ViewConfiguration.get(context).hasPermanentMenuKey())
                ) {
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                return res.getDimensionPixelSize(resourceId);
            }
        }
        return 0;
    }
}
