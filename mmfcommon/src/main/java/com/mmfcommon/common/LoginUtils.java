package com.mmfcommon.common;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.lidroid.xutils.util.LogUtils;
import com.mmfcommon.R;
import com.mmfcommon.activity.MmfCommonAppBaseApplication;
import com.mmfcommon.api.ApiRequestBase;
import com.mmfcommon.api.LoginApi;
import com.mmfcommon.bean.CommonJsonBean;
import com.mmfcommon.bean.StoreUserBean;
import com.mmfcommon.bean.UserInfoBean;
import com.mmfcommon.event.ResultListener;
import com.mmfcommon.utils.CommonJsonUtils;
import com.mmfcommon.utils.JPushUtils;
import com.unit.common.httputils.HttpRequestCallback;
import com.unit.common.httputils.HttpRequestCallbackHandler;
import com.unit.common.httputils.JZHttpUtils;
import com.unit.common.utils.LogOutputUtils;

import org.json.JSONObject;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

import static com.lidroid.xutils.http.client.HttpRequest.HttpMethod.POST;
import static com.mmfcommon.activity.MmfCommonAppBaseApplication.getAppContext;
import static com.mmfcommon.activity.MmfCommonAppBaseApplication.lat;
import static com.mmfcommon.activity.MmfCommonAppBaseApplication.lng;
import static com.mmfcommon.activity.MmfCommonAppBaseApplication.token;
import static com.mmfcommon.activity.MmfCommonAppBaseApplication.userId;
import static com.mmfcommon.activity.MmfCommonAppBaseApplication.storeUserBean;
import static com.mmfcommon.activity.MmfCommonAppBaseApplication.userInfoBean;
import static com.mmfcommon.common.MmfCommonSetting.API_GET_Bind_push;
import static com.mmfcommon.common.MmfCommonSetting.API_KEY_ACCOUNT;
import static com.mmfcommon.common.MmfCommonSetting.API_KEY_PASSWORD;
import static com.mmfcommon.common.MmfCommonSetting.Host_Login;
import static com.mmfcommon.common.MmfCommonSetting.Http_Request_Key_city_id;
import static com.mmfcommon.common.MmfCommonSetting.Http_Request_Key_district_id;
import static com.mmfcommon.common.MmfCommonSetting.Http_Request_Key_handset;
import static com.mmfcommon.common.MmfCommonSetting.Http_Request_Key_lat;
import static com.mmfcommon.common.MmfCommonSetting.Http_Request_Key_lng;
import static com.mmfcommon.common.MmfCommonSetting.Http_Request_Key_password;
import static com.mmfcommon.common.MmfCommonSetting.Http_Request_Key_province_id;
import static com.mmfcommon.common.MmfCommonSetting.Http_Request_Key_town_id;
import static com.mmfcommon.common.MmfCommonSetting.PREF_KEY_LOGIN_PARAMS;
import static com.mmfcommon.common.MmfCommonSetting.USER_INFO_FILE;
import static com.mmfcommon.common.MmfCommonSetting.USER_LOGIN_KEY;
import static com.mmfcommon.common.MmfCommonSetting.USER_TOKEN_KEY;
import static com.unit.common.utils.JsonUtils.parseJson2Obj;
import static com.unit.common.utils.PreferencesUtils.getPreference;
import static com.unit.common.utils.PreferencesUtils.setPreferences;

/**
 * Created by longbin on 15/1/19.
 */
public class LoginUtils {

    public static final int APP_CLIENT = 1;
    public static final int APP_STORE = 2;
    public static final int APP_OA = 3;
    public static final int APP_CLIENT_EN = 4;
    public static final int APP_STORE_EN = 5;


    private LoginApi loginApi;
    private LoginApi.LoginParams loginParams = new LoginApi.LoginParams();

    private ResultListener listener;

    String account = "";
    //    private String bindPushAPi,deletePushAPi;//有些人写的绑定推送接口以参数的形式，有些是直接通过url推送

    public void setListener(ResultListener listener) {
        this.listener = listener;
    }

    public void songsenLogin(final String account, final String password) {
        LoginUtils.this.account = account;
        if (loginApi == null) {
            loginApi = new LoginApi(loginParams, new ApiRequestBase.OnSuccessCallback() {
                @Override
                public void onSuccess(String content) {
                    LogOutputUtils.d("http_login", content);
                    parseLogin(content);
                }
            }, new ApiRequestBase.OnFailureCallback() {
                @Override
                public void onFailure(String content) {
                    listener.onResponse(false, getAppContext().getString(R.string.error_network));
                }
            }, POST);
        }
        loginParams.account = account;
        loginParams.password = password;
        loginApi.updateParams(loginParams);
        loginApi.requestData();
    }

    public void login(final String handset, final String password) {
        login(handset, password, null);
    }

    public void login(final String handset, final String password, final String areaCode) {
        AppsHttpRequestBaseInfo appsHttpRequestBaseInfo = AppsHttpRequestBaseInfo.GetDewuRequestBaseInfo();
        appsHttpRequestBaseInfo.addBodyParameter(Http_Request_Key_handset, handset);
        appsHttpRequestBaseInfo.addBodyParameter(Http_Request_Key_password, password);
        appsHttpRequestBaseInfo.addBodyParameter(Http_Request_Key_lng, "" + lng);
        appsHttpRequestBaseInfo.addBodyParameter(Http_Request_Key_lat, "" + lat);
        appsHttpRequestBaseInfo.addBodyParameter(Http_Request_Key_province_id, "");
        appsHttpRequestBaseInfo.addBodyParameter(Http_Request_Key_city_id, "");
        appsHttpRequestBaseInfo.addBodyParameter(Http_Request_Key_district_id, "");
        appsHttpRequestBaseInfo.addBodyParameter(Http_Request_Key_town_id, "");
        this.account = handset;

        loginParams.account = account;
        loginParams.password = password;

        if (null != areaCode) {
            appsHttpRequestBaseInfo.addBodyParameter(MmfCommonSetting.Http_Request_Key_area_code, areaCode);
        }

        JZHttpUtils.getInstance().HttpPostRequest(Host_Login, appsHttpRequestBaseInfo,
                new HttpRequestCallback<String>(new HttpRequestCallbackHandler() {
                    @Override
                    public void onSuccess(String content) {
                        super.onSuccess(content);
                        LogUtils.d(content);
                        parseLogin(content, areaCode);
                    }

                    @Override
                    public void onFailure(String content) {
                        super.onFailure(content);
                        if (null != listener) {
                            listener.onResponse(false, getAppContext().getString(R.string.error_network));
                        }
                    }
                }));
    }

    private void parseLogin(String content) {
        parseLogin(content, null);
    }

    private void parseLogin(String content, final String areaCode) {
        CommonJsonBean commonJsonBean = CommonJsonUtils.parseCommonJson(content);

        if (commonJsonBean.getCode() != 1) {
            if (null != listener) {
                listener.onResponse(false, commonJsonBean.getMsg());
            }
        } else {
            try {
                String response = commonJsonBean.getResponse();
                JSONObject responseJson = new JSONObject(response);
                String user = responseJson.optString("user", "");
                String token = responseJson.optString("token", "");
                MmfCommonAppBaseApplication.token = token;

                parseUserLogin(user, areaCode);

                parseStoreLogin(user, areaCode);

                saveUserInfo(user, token);
                saveUserAccountPassword(loginParams.account, loginParams.password);

                JPushUtils.register(API_GET_Bind_push);
                if (null != listener) {
                    listener.onResponse(true, getAppContext().getString(R.string.login_success));
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (null != listener) {
                    listener.onResponse(false, getAppContext().getString(R.string.login_failure));
                }
            }

        }
    }

    private void parseUserLogin(String user, String areaCode) {
        userInfoBean = parseJson2Obj(user, UserInfoBean.class);
        userInfoBean.setAreaCode(areaCode);
        userId = userInfoBean.getUserId();
    }

    private void parseStoreLogin(String user, String areaCode) {
        storeUserBean = parseJson2Obj(user, StoreUserBean.class);
        storeUserBean.clerk_name = account;
        storeUserBean.areaCode = areaCode;
    }

    /**
     * 把登录结果保存到本地
     *
     * @param user  用户信息
     * @param token token
     */
    private void saveUserInfo(String user, String token) {
        setPreferences(getAppContext(),
                USER_INFO_FILE, USER_LOGIN_KEY, user);
        setPreferences(getAppContext(),
                USER_INFO_FILE, USER_TOKEN_KEY, token);
        setPreferences(getAppContext(),
                USER_INFO_FILE, API_KEY_ACCOUNT, account);

        if (loginParams != null) {
            setPreferences(getAppContext(), USER_INFO_FILE, PREF_KEY_LOGIN_PARAMS, new Gson().toJson(loginParams));
        }
    }

    private void saveUserAccountPassword(String account, String password) {
        setPreferences(getAppContext(), USER_INFO_FILE, API_KEY_ACCOUNT, account);
        setPreferences(getAppContext(), USER_INFO_FILE, API_KEY_PASSWORD, password);
    }

    public void logout() {
        setPreferences(getAppContext(),
                USER_INFO_FILE, USER_LOGIN_KEY, "");
        setPreferences(getAppContext(),
                USER_INFO_FILE, USER_TOKEN_KEY, "");
        JPushUtils jPushUtils = new JPushUtils();
        jPushUtils.unbindRegister(MmfCommonSetting.API_GET_Delete_push);
        userInfoBean = null;
        storeUserBean = null;
        userId = -1;
        token = "";
    }

    /**
     * 本地登录，即获取上一次登录结果
     */
    public void localLogin() {
        String user = getPreference(getAppContext(),
                USER_INFO_FILE, USER_LOGIN_KEY, null);
        String token = getPreference(getAppContext(),
                USER_INFO_FILE, USER_TOKEN_KEY, "");
        String localAccount = getPreference(getAppContext(),
                USER_INFO_FILE, API_KEY_ACCOUNT, "");
        if (!TextUtils.isEmpty(localAccount)) {
            this.account = localAccount;
            MmfCommonAppBaseApplication.storeUserBean.clerk_name = localAccount;
        }

        setUserInfo(user, token);
    }

    private void setUserInfo(String user, String token) {
        if (!TextUtils.isEmpty(user)) {
            userInfoBean = parseJson2Obj(user, UserInfoBean.class);
            if (null != userInfoBean) {
                userId = userInfoBean.getUserId();
            }
            storeUserBean = parseJson2Obj(user, StoreUserBean.class);
            if (userId <= 0) {
                try {
                    userId = Integer.parseInt(storeUserBean.clerk_id, -1);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        } else {
            userInfoBean = null;
            storeUserBean = null;
            userId = -1;
        }
        if (null != token) {
            MmfCommonAppBaseApplication.token = token;
        }
    }

    /**
     * @return 判断用户是否在登录状态下
     */
    public static boolean isLogin() {
        return userId > 0;
    }

    public static void wechatLogin(Context context, PlatformActionListener paListener) {
        ShareSDK.initSDK(context);
        Platform wechat = ShareSDK.getPlatform(context, Wechat.NAME);
        wechat.setPlatformActionListener(paListener);
        wechat.showUser(null);
    }
//
//    public String getBindPushAPi() {
//        return bindPushAPi;
//    }
//
//    public String getDeletePushAPi() {
//        return deletePushAPi;
//    }
//
//    public void setBindPushAPi(String bindPushAPi) {
//        this.bindPushAPi = bindPushAPi;
//    }
//
//    public void setDeletePushAPi(String deletePushAPi) {
//        this.deletePushAPi = deletePushAPi;
//    }
}
