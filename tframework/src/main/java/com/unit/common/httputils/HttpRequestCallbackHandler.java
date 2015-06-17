package com.unit.common.httputils;

import android.os.Handler;
import android.os.Message;

import com.unit.common.config.CommonSetting;

/**
 * 主要用于网络请求回调的Handler,做了一些优化过滤处理
 * Created by kingkong on 13-8-2.
 */
public class HttpRequestCallbackHandler extends Handler {

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        try {
            String content = (String) msg.obj;
            switch (msg.what) {
                case CommonSetting.SUCCESS:
                    onSuccess(content);
                    break;
                case CommonSetting.FAIL:
                    onFailure(content);
                default:
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void onSuccess(String content) {
    }

    public void onFailure(String content) {
    }
}
