package com.unit.common.utils;

import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by kingkong on 15/4/22.
 */
public class KeyboardUtils {

    //键盘是否显示
    public static boolean keybordIsShow(Window window) {
        return window.getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
    }

    //隐藏键盘
    public static void invisiableKeybard(Activity activity) {

        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && getCurrentFocusView(activity) != null) {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocusView(activity).getWindowToken(), 0);// 隐藏软键盘
        }
    }

    //获取是否聚焦
    public static View getCurrentFocusView(Activity activity) {
        return activity.getCurrentFocus();
    }
}
