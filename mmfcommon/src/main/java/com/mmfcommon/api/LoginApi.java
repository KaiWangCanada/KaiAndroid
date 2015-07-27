package com.mmfcommon.api;

import android.text.TextUtils;

import com.lidroid.xutils.http.client.HttpRequest;
import com.mmfcommon.common.MmfCommonSetting;
import com.unit.common.utils.FrameMD5Util;

import static com.mmfcommon.common.MmfCommonSetting.API_KEY_ACCOUNT;
import static com.mmfcommon.common.MmfCommonSetting.API_KEY_PASSWORD;
import static com.mmfcommon.common.MmfCommonSetting.LOGIN_API;

/**
 * Created by 黄东鲁 on 15/4/14.
 */
public class LoginApi extends SongSenApiBase<LoginApi.LoginParams> {

    public LoginApi(LoginParams params, OnSuccessCallback onSuccessCallback, OnFailureCallback onFailureCallback, HttpRequest.HttpMethod httpMethod) {
        super(params, onSuccessCallback, onFailureCallback, httpMethod);
    }

    @Override
    protected String api() {
        return LOGIN_API;
    }

    @Override
    public void updateParams(LoginParams params) {
        updateParameter(API_KEY_ACCOUNT, params.account);
        if (!TextUtils.isEmpty(params.password)) {
            updateParameter(API_KEY_PASSWORD, FrameMD5Util.getMD5String(params.password));
        }
        if (!TextUtils.isEmpty(params.areaCode)) {
            updateParameter(MmfCommonSetting.Http_Request_Key_area_code, params.areaCode);
        }
    }

    public static class LoginParams {
        public String account = "";
        public String password = "";
        public String areaCode = "";
    }
}
