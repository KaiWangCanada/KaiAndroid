package com.mmfcommon.activity;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.mmfcommon.bean.StoreUserBean;
import com.mmfcommon.bean.UserInfoBean;
import com.mmfcommon.common.AppCommonConfig;
import com.mmfcommon.common.LoginUtils;
import com.unit.common.application.FrameBaseApplication;

import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by kingkong on 15/1/12.
 */
public class MmfCommonAppBaseApplication extends FrameBaseApplication {
    public static StoreUserBean storeUserBean = new StoreUserBean();
    public static int userId = -1;
    public static String token = "";
    public static UserInfoBean userInfoBean;
    public static MmfCommonAppBaseApplication instance;
    public static double lat = 0;
    public static double lng = 0;
    public static AMapLocation aMapLocation=null;//当前用户定位

    private HashMap<String, Integer> animation;

    public static AppCommonConfig appCommonConfig;


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
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);
        // 本地登录
        LoginUtils loginUtils = new LoginUtils();
        loginUtils.localLogin();
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
