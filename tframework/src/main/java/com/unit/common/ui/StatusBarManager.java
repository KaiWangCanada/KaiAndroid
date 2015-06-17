package com.unit.common.ui;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.lang.reflect.Field;

/**
 * Created by 黄东鲁 on 15/4/6.
 */
public class StatusBarManager {

    /**
     * 状态栏颜色设置
     * @param topView 顶部的那个控件，用来设置padding
     * @param colorResId 状态栏颜色
     */
    public static void setStatusBarTranslucent(Activity activity, View topView, int colorResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // Translucent status bar
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            topView.setPadding(0, getStatusBarHeight(activity), 0, 0);

            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setTintColor(activity.getResources().getColor(colorResId));
        }

    }

    // 获取手机状态栏高度
    public static int getStatusBarHeight(Activity activity) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = activity.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }
}
