package com.mmfcommon.common;

import android.os.Message;

import com.google.gson.Gson;
import com.mmfcommon.R;
import com.mmfcommon.activity.MmfCommonAppBaseApplication;
import com.mmfcommon.bean.CommonJsonBean;
import com.mmfcommon.event.MMFHttpResponse;
import com.unit.common.config.CommonSetting;
import com.unit.common.httputils.JZHttpRequestCallbackHandler;

/**
 * Created by kingkong on 15/1/30.
 */
public class HttpCallbackHandlerMMFHttp extends MMFHttpResponse {

    JZHttpRequestCallbackHandler httpRequestCallbackHandler;

    public HttpCallbackHandlerMMFHttp(JZHttpRequestCallbackHandler httpRequestCallbackHandler) {
        this.httpRequestCallbackHandler = httpRequestCallbackHandler;
    }

    @Override
    public void onSuccess(CommonJsonBean commonJsonBean) {

        Message message = httpRequestCallbackHandler.obtainMessage();
        message.what = CommonSetting.SUCCESS;
        message.obj =new Gson().toJson(commonJsonBean);

        httpRequestCallbackHandler.sendMessage(message);
    }

    @Override
    public void onFailure(String content) {
        Message message = httpRequestCallbackHandler.obtainMessage();
        message.what = CommonSetting.FAIL;
        message.obj = content;
        httpRequestCallbackHandler.sendMessage(message);
    }

    @Override
    public void onNotLogin() {
        super.onNotLogin();
        Message message = httpRequestCallbackHandler.obtainMessage();
        message.what = CommonSetting.FAIL;
        message.obj = MmfCommonAppBaseApplication.instance.getResources().getString(R.string.not_login);
        httpRequestCallbackHandler.sendMessage(message);
    }
}
