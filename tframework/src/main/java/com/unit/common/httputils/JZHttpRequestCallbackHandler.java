package com.unit.common.httputils;

import android.os.Message;

import com.unit.common.R;
import com.unit.common.application.FrameBaseApplication;
import com.unit.common.config.CommonSetting;
import com.unit.common.db.JZCommonJsonBean;
import com.unit.common.utils.JZCommonJsonUtils;
import com.unit.common.utils.LogOutputUtils;

/**
 * Created by kingkong on 15/1/30.
 */
public class JZHttpRequestCallbackHandler extends HttpRequestCallbackHandler {

    public String TAG = JZHttpRequestCallbackHandler.this.getClass().getSimpleName();

    @Override
    public void handleMessage(Message msg) {

        String content = (String) msg.obj;
        LogOutputUtils.d(TAG, content);
        JZCommonJsonBean jzCommonJsonBean = JZCommonJsonUtils.parseCommonJson(content);
        switch (msg.what) {
            case CommonSetting.SUCCESS:
                if (jzCommonJsonBean.getCode() == CommonSetting.HTTP_SUCCESS_CODE) {
                    onSuccess( jzCommonJsonBean.getResponse());
                } else if (jzCommonJsonBean.getCode() == CommonSetting.HTTP_NoData_CODE) {
                    onFailure(FrameBaseApplication.appContext.getString(R.string.ruis_common_no_more));
                } else {
                    onFailure(jzCommonJsonBean.getMsg());
                }

                break;
            case CommonSetting.FAIL:
                onFailure(content);
            default:
                break;
        }
    }

    @Override
    public void onSuccess(String content) {

    }

    @Override
    public void onFailure(String content) {

    }
}
