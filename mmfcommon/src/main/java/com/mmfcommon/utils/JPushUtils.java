package com.mmfcommon.utils;

import android.text.TextUtils;
import android.util.Log;

import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.mmfcommon.activity.MmfCommonAppBaseApplication;
import com.mmfcommon.bean.CommonJsonBean;
import com.mmfcommon.common.AppsHttpRequestBaseInfo;
import com.mmfcommon.common.LoginUtils;
import com.mmfcommon.common.MMFHttpUtils;
import com.mmfcommon.common.MmfCommonSetting;
import com.mmfcommon.event.MMFHttpResponse;
import com.unit.common.utils.PreferencesUtils;

import cn.jpush.android.api.JPushInterface;

/**
 * 推送的工具类
 * Created by longbin on 15/1/29.
 */
public class JPushUtils {

    public static String TAG = "JPushUtils";

    public static void register() {
        register("");
    }

    public static void register(String bindPushApi) {

        if (!LoginUtils.isLogin()) {
            return;
        }
        final String userId = String.valueOf(MmfCommonAppBaseApplication.userId);

        //每次登录都需要绑定
//        if (userId.equals(getRegisterUserId())) {
//            //绑定的Id和当前用户id相等的话就不再绑定了
//            return;
//        }
        String jPushId = JPushInterface.getRegistrationID(MmfCommonAppBaseApplication.getAppContext());

        Log.d(TAG, "jPushId = " + jPushId);
        AppsHttpRequestBaseInfo appsHttpRequestBaseInfo = null;

        //区分松森还是德武的请求
        if (TextUtils.isEmpty(MmfCommonSetting.isRequestType)) {
            appsHttpRequestBaseInfo = AppsHttpRequestBaseInfo.GetDewuRequestBaseInfo();
        } else if (MmfCommonSetting.isRequestType.equals(MmfCommonSetting.isRequestType_SongSen)) {
            appsHttpRequestBaseInfo = AppsHttpRequestBaseInfo.GetSongSenRequestBaseInfo(bindPushApi, HttpRequest.HttpMethod.POST);
        }

        appsHttpRequestBaseInfo.addBodyParameter(MmfCommonSetting.Http_Request_Key_user_id, userId);
        appsHttpRequestBaseInfo.addBodyParameter(MmfCommonSetting.Http_Request_Key_tokens, MmfCommonAppBaseApplication.token);
        if (!TextUtils.isEmpty(bindPushApi)) {
            appsHttpRequestBaseInfo.addBodyParameter(MmfCommonSetting.Http_Request_Key_Api, bindPushApi);
        }
        appsHttpRequestBaseInfo.addBodyParameter(MmfCommonSetting.Http_Request_Key_push_id, jPushId);
        String url = MmfCommonSetting.Host_push_binding_save;
        MMFHttpUtils.getInstance().httpPostRequest(url, appsHttpRequestBaseInfo, new MMFHttpResponse() {
            @Override
            public void onSuccess(CommonJsonBean commonJsonBean) {
                super.onSuccess(commonJsonBean);
                parseRegister(commonJsonBean, userId);
            }

            @Override
            public void onFailure(String content) {
                super.onFailure(content);
            }
        });
    }

    private static void parseRegister(CommonJsonBean commonJsonBean, String userId) {
        if (commonJsonBean.isCodeValueOne()) {
            saveRegisterUserID(userId);
        }
    }

    private String getRegisterUserId() {
        return PreferencesUtils.getPreference(MmfCommonAppBaseApplication.getAppContext(),
                MmfCommonSetting.PushBind, MmfCommonSetting.Http_Request_Key_user_id, "");
    }

    private static void saveRegisterUserID(String userId) {
        PreferencesUtils.setPreferences(MmfCommonAppBaseApplication.getAppContext(),
                MmfCommonSetting.PushBind, MmfCommonSetting.Http_Request_Key_user_id, userId);
    }

    public void unbindRegister() {
        unbindRegister("");
    }

    public void unbindRegister(String deletePushApi) {

        AppsHttpRequestBaseInfo appsHttpRequestBaseInfo = null;
        //区分松森还是德武的请求
        if (TextUtils.isEmpty(MmfCommonSetting.isRequestType)) {
            appsHttpRequestBaseInfo = AppsHttpRequestBaseInfo.GetDewuRequestBaseInfo();
        } else if (MmfCommonSetting.isRequestType.equals(MmfCommonSetting.isRequestType_SongSen)) {
            appsHttpRequestBaseInfo = AppsHttpRequestBaseInfo.GetSongSenRequestBaseInfo(deletePushApi, HttpRequest.HttpMethod.POST);
        }
        String jPushId = JPushInterface.getRegistrationID(MmfCommonAppBaseApplication.getAppContext());
        appsHttpRequestBaseInfo.addBodyParameter(MmfCommonSetting.Http_Request_Key_push_id, jPushId);
        appsHttpRequestBaseInfo.addBodyParameter(MmfCommonSetting.Http_Request_Key_Api, deletePushApi);
        MMFHttpUtils.getInstance().httpPostRequest(MmfCommonSetting.Host_push_binding_delete, appsHttpRequestBaseInfo, new MMFHttpResponse() {
            @Override
            public void onSuccess(CommonJsonBean commonJsonBean) {
                super.onSuccess(commonJsonBean);
                LogUtils.d(commonJsonBean.getMsg());
                parseUnbind(commonJsonBean);
            }

            @Override
            public void onFailure(String content) {
                super.onFailure(content);
            }
        });
    }

    private static void parseUnbind(CommonJsonBean commonJsonBean) {
        if (commonJsonBean.isCodeValueOne()) {
            saveRegisterUserID("");
        }
    }
}
