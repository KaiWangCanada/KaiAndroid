package com.mmfcommon.api;

import com.lidroid.xutils.util.LogUtils;
import com.mmfcommon.common.AppsHttpRequestBaseInfo;
import com.unit.common.httputils.HttpRequestCallback;
import com.unit.common.httputils.HttpRequestCallbackHandler;
import com.unit.common.httputils.JZHttpUtils;
import com.unit.common.utils.LogOutputUtils;

/**
 * Created by 黄东鲁 on 15/1/29.
 */
public abstract class ApiRequestBase {
    private OnSuccessCallback onSuccessCallback;
    private OnFailureCallback onFailureCallback;
    private OnLoadingCallback onLoadingCallback;

    public void setOnLoadingCallback(OnLoadingCallback onLoadingCallback) {
        this.onLoadingCallback = onLoadingCallback;
    }

    public ApiRequestBase(OnSuccessCallback onSuccessCallback, OnFailureCallback onFailureCallback) {
        this.onSuccessCallback = onSuccessCallback;
        this.onFailureCallback = onFailureCallback;
    }

    public void onHttpSuccess(String content){}
    public void onHttpFailure(String content){}

    public void post(final String url,AppsHttpRequestBaseInfo requestInfo) {
        JZHttpUtils.getInstance().HttpPostRequest(url, requestInfo,
                new HttpRequestCallback<String>(new HttpRequestCallbackHandler() {
                    @Override
                    public void onSuccess(String content) {
                        LogUtils.d("url ->"+url+" ||response->"+content);
                        onHttpSuccess(content);
                        if (onSuccessCallback != null) {
                            try {
                                onSuccessCallback.onSuccess(content);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(String content) {
                        super.onFailure(content);
                        LogUtils.d("url ->" + url + " ||response->" + content);
                        onHttpFailure(content);
                        if (null != onFailureCallback) {
                            try {
                                onFailureCallback.onFailure(content);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }) {
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                        LogOutputUtils.d("http>>>>", "api request loading " + total + "   ------   " + current);

                        if (onLoadingCallback != null) {
                            onLoadingCallback.onLoading(total, current, isUploading);
                        }
                    }
                });
    }

    public void get(final String url,AppsHttpRequestBaseInfo requestInfo) {
        JZHttpUtils.getInstance().HttpGetRequest(url, requestInfo,
                new HttpRequestCallback<String>(new HttpRequestCallbackHandler() {
                    @Override
                    public void onSuccess(String content) {
                        LogUtils.d("url ->" + url + " ||response->" + content);
                        onHttpSuccess(content);
                        if (onSuccessCallback != null) {

                            try {
                                onSuccessCallback.onSuccess(content);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onFailure(String content) {
                        super.onFailure(content);
                        LogUtils.d("url ->" + url + " ||response->" + content);
                        onHttpFailure(content);
                        if (null != onFailureCallback) {

                            try {
                                onFailureCallback.onFailure(content);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }) {
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                        LogOutputUtils.d("http>>>>", "api request loading " + total + "   ------   " + current);

                        if (onLoadingCallback != null) {
                            onLoadingCallback.onLoading(total, current, isUploading);
                        }
                    }
                });
    }

    public interface OnSuccessCallback {
        public void onSuccess(String content);
    }

    public interface OnFailureCallback {
        public void onFailure(String content);
    }
    public interface OnLoadingCallback {
        void onLoading(long total, long current, boolean isUploading);
    }
}
