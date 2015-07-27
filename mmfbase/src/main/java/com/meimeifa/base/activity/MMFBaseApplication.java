package com.meimeifa.base.activity;

import android.content.Context;

import com.unit.common.application.FrameBaseApplication;

import java.util.HashMap;

/**
 * Created by kingkong on 15/1/12.
 */
public class MMFBaseApplication extends FrameBaseApplication {
    // 测试环境
//    public static String Host = "http://api.meimeifa.com.cn/";


    public static int userId = -1;
    public static String token = "";
    public static MMFBaseApplication instance;
    public static double lat = 0;
    public static double lng = 0;

    private HashMap<String, Integer> animation;


    public static Context getAppContext() {
        if (null != instance) {
            return instance.getApplicationContext();
        } else {
            return null;
        }
    }

    @Override
    public void onCreate() {
        animation = new HashMap<String, Integer>();

        super.onCreate();
        instance = this;
    }


    public void setAnimAction(String tag, Integer value) {
        animation.put(tag, value);
    }

    public boolean hasAnim() {
        if (animation.size() > 0) {
            return true;
        }
        return false;
    }

    public Integer getAnimActionByTag(String tag) {
        return animation.get(tag);
    }
}
