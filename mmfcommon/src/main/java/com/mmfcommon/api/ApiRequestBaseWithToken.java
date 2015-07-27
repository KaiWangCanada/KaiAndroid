package com.mmfcommon.api;


import com.lidroid.xutils.http.HttpCache;
import com.lidroid.xutils.util.LogUtils;
import com.mmfcommon.bean.CommonJsonBean;
import com.mmfcommon.common.AppsHttpRequestBaseInfo;
import com.mmfcommon.common.MMFHttpUtils;
import com.mmfcommon.event.HttpResponseListener;
import com.unit.common.httputils.JZHttpUtils;
import com.unit.common.utils.LogOutputUtils;

import static com.mmfcommon.api.ApiRequestBase.OnFailureCallback;

/**
 * 带token的api基类
 * <p/>
 * Created by 黄东鲁 on 15/1/29.
 */
public class ApiRequestBaseWithToken {

    private OnSuccessJsonCallback onSuccessJsonCallback;
    private OnFailureCallback onFailureCallback;
    private OnNotLoginCallback onNotLoginCallback;

    private long cacheTimeOut = JZHttpUtils.cacheExpiry;
    public void setCacheTimeOut(long cacheTimeOutInMillSecond) {
        this.cacheTimeOut = cacheTimeOutInMillSecond;
    }

    public ApiRequestBaseWithToken(OnSuccessJsonCallback onSuccessJsonCallback, OnFailureCallback onFailureCallback, OnNotLoginCallback onNotLoginCallback) {
        this.onSuccessJsonCallback = onSuccessJsonCallback;
        this.onFailureCallback = onFailureCallback;
        this.onNotLoginCallback = onNotLoginCallback;
    }

    public void post(String url, AppsHttpRequestBaseInfo requestInfo) {
        MMFHttpUtils.getInstance().setCacheTimeOut(cacheTimeOut);
        MMFHttpUtils.getInstance().httpPostRequest(url, requestInfo, new HttpResponseListener() {
            @Override
            public void onSuccess(CommonJsonBean commonJsonBean) {
                if (onSuccessJsonCallback != null) {
                    try {
                        onSuccessJsonCallback.onSuccess(commonJsonBean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(String content) {
                if (null != onFailureCallback) {
                    try {
                        onFailureCallback.onFailure(content);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNotLogin() {
                if (onNotLoginCallback != null) {
                    try {
                        onNotLoginCallback.onNotLogin();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void get(String url, AppsHttpRequestBaseInfo requestInfo) {
        MMFHttpUtils.getInstance().setCacheTimeOut(cacheTimeOut);
        MMFHttpUtils.getInstance().httpGetRequest(url, requestInfo, new HttpResponseListener() {
            @Override
            public void onSuccess(CommonJsonBean commonJsonBean) {
                if (onSuccessJsonCallback != null) {
                    try {
                        onSuccessJsonCallback.onSuccess(commonJsonBean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(String content) {
                if (null != onFailureCallback) {
                    try {
                        onFailureCallback.onFailure(content);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNotLogin() {
                if (onNotLoginCallback != null) {
                    try {
                        onNotLoginCallback.onNotLogin();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public interface OnSuccessJsonCallback {
        public void onSuccess(CommonJsonBean jsonBean);
    }

    public interface OnNotLoginCallback {
        public void onNotLogin();
    }
}