package com.mmfcommon.api;


import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.mmfcommon.common.AppsHttpRequestBaseInfo;
import com.mmfcommon.common.MmfCommonSetting;

import static com.mmfcommon.api.ApiRequestBase.OnFailureCallback;

/**
 * Created by 黄东鲁 on 15/4/15.
 */
public abstract class SongSenApiWithTokenBase<T> extends ApiRequestBaseWithToken {

    protected AppsHttpRequestBaseInfo mRequestInfo;
    //    protected String url = "";
    private HttpMethod httpMethod;

    public SongSenApiWithTokenBase(/*String url, */T bean, OnSuccessJsonCallback onSuccessCallback, OnFailureCallback onFailureCallback, OnNotLoginCallback onNotLoginCallback, HttpMethod httpMethod) {
        super(onSuccessCallback, onFailureCallback, onNotLoginCallback);
//        this.url = url;
        this.httpMethod = httpMethod;
        if (mRequestInfo == null) {
            mRequestInfo = AppsHttpRequestBaseInfo.GetSongSenRequestBaseInfo(api(), httpMethod);
        }
        updateParams(bean);
    }

    public void requestData() {
        mRequestInfo.outputHead();
        if (httpMethod == HttpMethod.GET) {
            get(MmfCommonSetting.Host, mRequestInfo);
        } else if (httpMethod == HttpMethod.POST) {
            post(MmfCommonSetting.Host, mRequestInfo);
        }
    }

    protected abstract String api();

    protected void updateParameter(String key, String value) {
        if (mRequestInfo != null) {
            if (httpMethod == HttpMethod.GET) {
                mRequestInfo.addQueryStringParameter(key, value);
            } else if (httpMethod == HttpMethod.POST) {
                mRequestInfo.addBodyParameter(key, value);
            }
        }
    }

    protected void updateParameter(String key, int value) {
        updateParameter(key, Integer.toString(value));
    }

    public void updateParams(T params) {
    }
}
