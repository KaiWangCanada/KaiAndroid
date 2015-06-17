package com.unit.common.httputils;

import android.os.Message;
import android.text.TextUtils;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.unit.common.R;
import com.unit.common.application.FrameBaseApplication;
import com.unit.common.config.CommonSetting;
import com.unit.common.utils.LogOutputUtils;


/**
 * 网络请求的回调,做了一些优化过滤处理
 * Created by kingkong on 13-8-2.
 */
public class HttpRequestCallback<T> extends RequestCallBack<T> {


    String TAG = HttpRequestCallback.class.getSimpleName();
    HttpRequestCallbackHandler handler;

    public HttpRequestCallback(HttpRequestCallbackHandler handler) {
        this.handler = handler;
    }


    @Override
    public void onLoading(long total, long current, boolean isUploading) {
        super.onLoading(total, current, isUploading);
    }

    @Override
    public void onSuccess(ResponseInfo<T> responseInfo) {
        Message message = handler.obtainMessage();
        try {
            String content = (String) responseInfo.result;

            if (!TextUtils.isEmpty((String) content)) {
                message.what = CommonSetting.SUCCESS;
                message.obj = content;
                LogOutputUtils.d(TAG, "success->" + content);
            } else {
                message.what = CommonSetting.FAIL;
                message.obj = CommonSetting.Content_Is_Null;
                LogOutputUtils.d(TAG, "responseInfo is null");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            message.what = CommonSetting.FAIL;
            message.obj = CommonSetting.Content_Is_Null;
            LogOutputUtils.d(TAG, "responseInfo Exception");
        }

        handler.sendMessage(message);
    }

    @Override
    public void onFailure(HttpException error, String msg) {

        LogOutputUtils.e(this.getClass().getSimpleName().toString(),"error code ->"+error.getExceptionCode()+ "onFailure->" + error.toString());
        Message message = handler.obtainMessage();
        message.what = CommonSetting.FAIL;
        message.obj = FrameBaseApplication.appContext.getResources().getString(R.string.ruis_common_NetworkError);
        handler.sendMessage(message);
    }


}
