package com.mmfcommon.api;

import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.mmfcommon.common.AppsHttpRequestBaseInfo;
import java.io.File;

/**
 * Created by 黄东鲁 on 15/1/26.
 */
public abstract class ApiRequest<T> extends ApiRequestBase {

    protected AppsHttpRequestBaseInfo mRequestInfo;
    private HttpMethod httpMethod;

    public ApiRequest(T bean, OnSuccessCallback onSuccessCallback, OnFailureCallback onFailureCallback, HttpMethod httpMethod) {
        super(onSuccessCallback, onFailureCallback);
        this.httpMethod = httpMethod;
        if (mRequestInfo == null) {
            mRequestInfo = AppsHttpRequestBaseInfo.GetDewuRequestBaseInfo();
        }
        updateParams(bean);
    }

    public void requestData() {
        if (httpMethod == HttpMethod.GET) {
            get(api(), mRequestInfo);
        } else if (httpMethod == HttpMethod.POST) {
            post(api(), mRequestInfo);
        }
    }

    protected abstract String api();

    public void updateParameter(String key, String value) {
        if (mRequestInfo != null) {
            if (httpMethod == HttpMethod.GET) {
                mRequestInfo.addQueryStringParameter(key, value);
            } else if (httpMethod == HttpMethod.POST) {
                mRequestInfo.addBodyParameter(key, value);
            }
        }
    }
    public void updateParameter(String key, File value) throws Exception{
        if (mRequestInfo != null) {
            if (httpMethod == HttpMethod.GET) {
                //mRequestInfo.addQueryStringParameter(key, value);
                new RuntimeException("upload file cannot use get method !");
            } else if (httpMethod == HttpMethod.POST) {
                mRequestInfo.addBodyParameter(key, value);
            }
        }
    }

    public void updateParams(T params) {
    }

    public void updateParameter(String key, int value) {
        updateParameter(key, Integer.toString(value));
    }

}