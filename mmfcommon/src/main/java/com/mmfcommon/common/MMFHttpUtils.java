package com.mmfcommon.common;

import android.text.TextUtils;

import com.lidroid.xutils.util.LogUtils;
import com.mmfcommon.activity.MmfCommonAppBaseApplication;
import com.mmfcommon.bean.CommonJsonBean;
import com.mmfcommon.bean.UserInfoBean;
import com.mmfcommon.event.HttpResponseListener;
import com.mmfcommon.utils.CommonJsonUtils;
import com.mmfcommon.utils.JPushUtils;
import com.unit.common.httputils.HttpRequestCallback;
import com.unit.common.httputils.HttpRequestCallbackHandler;
import com.unit.common.httputils.JZHttpUtils;
import com.unit.common.utils.PreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import static com.mmfcommon.common.MmfCommonSetting.HTTP_SUCCESS_CODE;
import static com.mmfcommon.common.MmfCommonSetting.Host_token_get;
import static com.mmfcommon.common.MmfCommonSetting.Http_Request_Key_area_code;
import static com.mmfcommon.common.MmfCommonSetting.Http_Request_Key_handset;
import static com.mmfcommon.common.MmfCommonSetting.Http_Request_Key_password;
import static com.mmfcommon.common.MmfCommonSetting.Http_Request_Key_tokens;
import static com.mmfcommon.common.MmfCommonSetting.TOKEN_ERROR;
import static com.mmfcommon.common.MmfCommonSetting.TOKEN_PASSWORD_ERROR;
import static com.mmfcommon.common.MmfCommonSetting.USER_INFO_FILE;
import static com.mmfcommon.common.MmfCommonSetting.USER_TOKEN_KEY;

/**
 * 需要token的才采用这个，否则采用JZHttpUtils
 * Created by longbin on 15/1/20.
 */
public class MMFHttpUtils {

    private static MMFHttpUtils instance;

    public static MMFHttpUtils getInstance() {
        if (null == instance) {
            synchronized (MMFHttpUtils.class) {
                if (null == instance) {
                    instance = new MMFHttpUtils();
                }
            }
        }
        return instance;
    }

    private MMFHttpUtils() {
    }

    private long cacheTimeOut = JZHttpUtils.cacheExpiry;

    public void setCacheTimeOut(long cacheTimeOutInMillSecond) {
        this.cacheTimeOut = cacheTimeOutInMillSecond;
    }

    /**
     * 判断请求是否请求2次，防止死循环
     */
    //    private boolean isRequestTwice = false;

    private final int HTTP_GET = 0;
    private final int HTTP_POST = 1;

    public void httpPostRequest(final String url,
                                final AppsHttpRequestBaseInfo appsHttpRequestBaseInfo, final HttpResponseListener listener) {
        if (!LoginUtils.isLogin()) {
            if (listener != null) {
                listener.onNotLogin();
            }
            return;
        }
        if (LoginUtils.isLogin() && TextUtils.isEmpty(MmfCommonAppBaseApplication.token)) {
            getToken(url, appsHttpRequestBaseInfo, new CommonJsonBean(), listener, HTTP_POST);
            return;
        }
        appsHttpRequestBaseInfo.addBodyParameter(Http_Request_Key_tokens,
                MmfCommonAppBaseApplication.token);
        JZHttpUtils.getInstance().setCacheTimeOut(cacheTimeOut);
        JZHttpUtils.getInstance()
                .HttpPostRequest(url, appsHttpRequestBaseInfo,
                        new HttpRequestCallback<String>(new HttpRequestCallbackHandler() {
                            @Override
                            public void onSuccess(String content) {
                                super.onSuccess(content);
                                JZHttpUtils.getInstance().initCacheTimeOut();
                                LogUtils.d("url ->" + url + " ||response->" + content);
                                parseCommon(url, content, appsHttpRequestBaseInfo, listener, HTTP_POST);
                            }

                            @Override
                            public void onFailure(String content) {
                                super.onFailure(content);
                                JZHttpUtils.getInstance().initCacheTimeOut();
                                LogUtils.d("url ->" + url + " ||response->" + content);
                                LogUtils.e(content);
                                if (null != listener) {
                                    listener.onFailure(content);
                                }
                            }
                        }));
    }

    public void httpGetRequest(final String url,
                               final AppsHttpRequestBaseInfo appsHttpRequestBaseInfo, final HttpResponseListener listener) {
        if (!LoginUtils.isLogin()) {
            if (listener != null) {
                listener.onNotLogin();
            }
            return;
        }
        if (LoginUtils.isLogin() && TextUtils.isEmpty(MmfCommonAppBaseApplication.token)) {
            getToken(url, appsHttpRequestBaseInfo, new CommonJsonBean(), listener, HTTP_GET);
            return;
        }
        appsHttpRequestBaseInfo.addQueryStringParameter(Http_Request_Key_tokens,
                MmfCommonAppBaseApplication.token);
        JZHttpUtils.getInstance().setCacheTimeOut(cacheTimeOut);
        JZHttpUtils.getInstance()
                .HttpGetRequest(url, appsHttpRequestBaseInfo,
                        new HttpRequestCallback<String>(new HttpRequestCallbackHandler() {
                            @Override
                            public void onSuccess(String content) {
                                super.onSuccess(content);
                                JZHttpUtils.getInstance().initCacheTimeOut();
                                LogUtils.d("url ->" + url + " ||response->" + content);
                                parseCommon(url, content, appsHttpRequestBaseInfo, listener, HTTP_GET);
                            }

                            @Override
                            public void onFailure(String content) {
                                super.onFailure(content);
                                JZHttpUtils.getInstance().initCacheTimeOut();
                                LogUtils.d("url ->" + url + " ||response->" + content);
                                if (null != listener) {
                                    listener.onFailure(content);
                                }
                            }
                        }));
    }

    private void parseCommon(String url, String content,
                             final AppsHttpRequestBaseInfo appsHttpRequestBaseInfo, final HttpResponseListener listener,
                             int httpMethod) {
        CommonJsonBean commonJsonBean = CommonJsonUtils.parseCommonJson(content);
        int code = commonJsonBean.getCode();
        if (code == TOKEN_ERROR
            //                || code == Setting.TOKEN_NULL || code == Setting.TOKEN_GONE
                ) {
            // token 出错，需要重新获取token
            //            if (!isRequestTwice) {
            //                getToken(url, appsHttpRequestBaseInfo, commonJsonBean, listener, httpMethod);
            //            } else {
            //                if (null != listener) {
            //                    listener.onSuccess(commonJsonBean);
            //                }
            //            }
            getToken(url, appsHttpRequestBaseInfo, commonJsonBean, listener, httpMethod);
        } else {
            if (null != listener) {
                listener.onSuccess(commonJsonBean);
            }
        }
    }

    /**
     * 获取token，成功后重新执行原来的请求
     *
     * @param appsHttpRequestBaseInfo 原来的请求的参数
     * @param commonJsonBean          原来请求返回的数据
     * @param listener                原来的请求的回调
     */
    private synchronized void getToken(final String url,
                                       final AppsHttpRequestBaseInfo appsHttpRequestBaseInfo, final CommonJsonBean commonJsonBean,
                                       final HttpResponseListener listener, final int httpMethod) {
        if (!LoginUtils.isLogin()) {
            if (listener != null) {
                listener.onNotLogin();
            }
            return;
        }
        UserInfoBean userInfoBean = MmfCommonAppBaseApplication.userInfoBean;
        if (null == userInfoBean) {
            // TODO 没有登录，先登录
            if (null != listener) {
                listener.onNotLogin();
                //TODO 有些接口没登录是做在了onSuccess方法里面
                if (null != commonJsonBean) {
                    listener.onSuccess(commonJsonBean);
                }
            }
            return;
        }
        String handset = userInfoBean.getHandset();
        String password = userInfoBean.getPassword();
        String areaCode = userInfoBean.getAreaCode();
        AppsHttpRequestBaseInfo tokenRequestParam = AppsHttpRequestBaseInfo.GetDewuRequestBaseInfo();
        tokenRequestParam.addBodyParameter(Http_Request_Key_handset, handset);
        tokenRequestParam.addBodyParameter(Http_Request_Key_password, password);
        tokenRequestParam.addBodyParameter(Http_Request_Key_tokens, MmfCommonAppBaseApplication.token);
        if (!TextUtils.isEmpty(areaCode)) {
            tokenRequestParam.addBodyParameter(Http_Request_Key_area_code, areaCode);
        }
        JZHttpUtils.getInstance()
                .HttpPostRequest(Host_token_get, tokenRequestParam,
                        new HttpRequestCallback<String>(new HttpRequestCallbackHandler() {
                            @Override
                            public void onSuccess(String content) {
                                super.onSuccess(content);
                                LogUtils.d("refresh token onSuccess url ->" + url + " ||response->" + content);

                                try {
                                    CommonJsonBean tokenBean = CommonJsonUtils.parseCommonJson(content);

                                    //FIXME 需要判断当前的token 是不是最新的
                                    if (HTTP_SUCCESS_CODE == tokenBean.getCode()) {
                                        //                            isRequestTwice = true;
                                        JSONObject responseJson = new JSONObject(tokenBean.getResponse());

                                        String tokenTemp = responseJson.optString("token", "");
                                        if (!MmfCommonAppBaseApplication.token.equals(tokenTemp)) {
                                            MmfCommonAppBaseApplication.token = tokenTemp;
                                        }

                                        httpRequestAgain(url, appsHttpRequestBaseInfo, listener, httpMethod);

                                        PreferencesUtils.getPreference(MmfCommonAppBaseApplication.getAppContext(),
                                                USER_INFO_FILE, USER_TOKEN_KEY, MmfCommonAppBaseApplication.token);

                                        JPushUtils.register(MmfCommonSetting.API_GET_Bind_push);
                                    } else if (TOKEN_PASSWORD_ERROR == tokenBean.getCode()) {
                                        LoginUtils loginUtils = new LoginUtils();
                                        loginUtils.logout();
                                        listener.onNotLogin();
                                    } else {
                                        if (null != listener) {
                                            listener.onSuccess(commonJsonBean);
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    if (null != listener) {
                                        listener.onSuccess(commonJsonBean);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(String content) {
                                super.onFailure(content);
                                LogUtils.d("refresh token failure url ->" + url + " ||response->" + content);
                                if (null != listener) {
                                    listener.onFailure(content);
                                }
                            }
                        }));
    }

    private boolean parseTokens(String content) {
        boolean getTokenStatus = false;
        try {
            CommonJsonBean tokenBean = CommonJsonUtils.parseCommonJson(content);
            getTokenStatus = (HTTP_SUCCESS_CODE == tokenBean.getCode());
            if (getTokenStatus) {
                JSONObject responseJson = new JSONObject(tokenBean.getResponse());
                MmfCommonAppBaseApplication.token = responseJson.optString("token", "");
                PreferencesUtils.getPreference(MmfCommonAppBaseApplication.getAppContext(), USER_INFO_FILE,
                        USER_TOKEN_KEY, MmfCommonAppBaseApplication.token);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getTokenStatus;
    }

    /**
     * 重新请求原来的请求
     */
    private void httpRequestAgain(String url, final AppsHttpRequestBaseInfo appsHttpRequestBaseInfo,
                                  final HttpResponseListener listener, final int httpMethod) {
        if (httpMethod == HTTP_GET) {
            appsHttpRequestBaseInfo.addQueryStringParameter(Http_Request_Key_tokens,
                    MmfCommonAppBaseApplication.token);
        } else {
            appsHttpRequestBaseInfo.addBodyParameter(Http_Request_Key_tokens,
                    MmfCommonAppBaseApplication.token);
        }
        //        appsHttpRequestBaseInfo.addBodyParameter(MmfCommonSetting.Http_Request_Key_tokens, MmfCommonAppBaseApplication.token);
        //        appsHttpRequestBaseInfo.addHeader(MmfCommonSetting.Http_Request_Key_tokens, MmfCommonAppBaseApplication.token);
        AppsHttpRequestBaseInfo.SetDewuRequestBaseInfoHeader(appsHttpRequestBaseInfo);
        if (httpMethod == HTTP_POST) {
            httpPostRequest(url, appsHttpRequestBaseInfo, listener);
        } else {
            httpGetRequest(url, appsHttpRequestBaseInfo, listener);
        }
    }
}