package com.mmfcommon.api;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lidroid.xutils.http.client.HttpRequest;
import com.mmfcommon.R;
import com.mmfcommon.activity.MmfCommonAppBaseApplication;
import com.mmfcommon.bean.AreaCodeBean;
import com.mmfcommon.bean.CommonJsonBean;
import com.mmfcommon.common.MmfCommonSetting;
import com.mmfcommon.event.ResultListener;
import com.mmfcommon.utils.CommonJsonUtils;
import com.unit.common.utils.PreferencesUtils;

/**
 * Created by longbin on 15/4/30.
 */
public class AreaCodeApiUtils {

    private ResultListener listener;

    private AreaCodeBean areaCodeBean;

    public void setListener(ResultListener listener) {
        this.listener = listener;
    }

    public AreaCodeBean getAreaCodeBean() {
        String response = PreferencesUtils.getPreference(MmfCommonAppBaseApplication.appContext, MmfCommonSetting.Area_Code, MmfCommonSetting.Area_Code, null);
        if (!TextUtils.isEmpty(response)) {
            parseAreaCode(response);
        }
        return areaCodeBean;
    }

    public void getAreaCode() {
        AreaCodeApi areaCodeApi = new AreaCodeApi(new ApiRequestBase.OnSuccessCallback() {
            @Override
            public void onSuccess(String content) {
                CommonJsonBean commonJsonBean = CommonJsonUtils.parseCommonJson(content);
                if (commonJsonBean.isCodeValueOne()) {
                    String response = commonJsonBean.getResponse();
                    if (TextUtils.isEmpty(response)) {
                        return;
                    }
                    PreferencesUtils.setPreferences(MmfCommonAppBaseApplication.appContext, MmfCommonSetting.Area_Code, MmfCommonSetting.Area_Code, response);
                    if (null != listener) {
                        listener.onResponse(true, null);
                    }
                } else {
                    String message = commonJsonBean.getMsg();
                    if (null != listener) {
                        listener.onResponse(false, message);
                    }
                }
            }
        }, new ApiRequestBase.OnFailureCallback() {
            @Override
            public void onFailure(String content) {
                if (null != listener) {
                    String message = MmfCommonAppBaseApplication.appContext.getString(R.string.error_network);
                    listener.onResponse(false, message);
                }
            }
        }, HttpRequest.HttpMethod.GET);
        areaCodeApi.requestData();
    }

    private void parseAreaCode(String content) {
        try {
            areaCodeBean = new Gson().fromJson(content, AreaCodeBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    class AreaCodeApi extends ApiRequest {

        public AreaCodeApi(OnSuccessCallback onSuccessCallback, OnFailureCallback onFailureCallback, HttpRequest.HttpMethod httpMethod) {
            super(null, onSuccessCallback, onFailureCallback, httpMethod);
        }

        @Override
        protected String api() {
            return MmfCommonSetting.Host_Area_Code_Get;
        }

    }

}
