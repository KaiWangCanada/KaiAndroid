package com.mmfcommon.api;

import com.lidroid.xutils.http.client.HttpRequest;
import com.mmfcommon.common.AppsHttpRequestBaseInfo;
import com.mmfcommon.common.MmfCommonSetting;

/**
 * Created by 黄东鲁 on 15/4/14.
 */
public abstract class SongSenApiBase<T> extends ApiRequestBase {

    protected AppsHttpRequestBaseInfo mRequestInfo;
    private HttpRequest.HttpMethod httpMethod;

    public SongSenApiBase(T params, ApiRequestBase.OnSuccessCallback onSuccessCallback, ApiRequestBase.OnFailureCallback onFailureCallback, HttpRequest.HttpMethod httpMethod) {
        super(onSuccessCallback, onFailureCallback);
        this.httpMethod = httpMethod;
        if (mRequestInfo == null) {
            mRequestInfo = AppsHttpRequestBaseInfo.GetSongSenRequestBaseInfo(api(),httpMethod);
        }
        updateParameter("api", api());
        updateParams(params);
    }

    public void requestData() {
        if (httpMethod == HttpRequest.HttpMethod.GET) {
            get(MmfCommonSetting.Host, mRequestInfo);
        } else if (httpMethod == HttpRequest.HttpMethod.POST) {
            post(MmfCommonSetting.Host, mRequestInfo);
        }
    }

    protected abstract String api();

    protected void updateParameter(String key, String value) {
        if (mRequestInfo != null) {
            if (httpMethod == HttpRequest.HttpMethod.GET) {
                mRequestInfo.addQueryStringParameter(key, value);
            } else if (httpMethod == HttpRequest.HttpMethod.POST) {
                mRequestInfo.addBodyParameter(key, value);
            }
        }
    }

    public abstract void updateParams(T params);

    protected void updateParameter(String key, int value) {
        updateParameter(key, Integer.toString(value));
    }
}
