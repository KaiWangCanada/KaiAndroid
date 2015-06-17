package com.unit.common.httputils;

import android.app.Activity;
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
 * Created by kingkong on 15/4/26.
 */
public class HttpRequestCallbackCheckActivity<T> extends RequestCallBack<T> {

    String TAG = HttpRequestCallbackCheckActivity.class.getSimpleName();
    HttpRequestCallbackHandler handler;
    Activity activity;

    public final static int SUCCESS = 1;
    public final static int FAIL = 0;

    public HttpRequestCallbackCheckActivity(Activity activity, HttpRequestCallbackHandler handler) {
        this.handler = handler;
        this.activity = activity;
    }

    public HttpRequestCallbackCheckActivity(HttpRequestCallbackHandler handler) {
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
        if (activity != null && activity.isFinishing()) {
            return;
        }
        handler.sendMessage(message);
    }

    @Override
    public void onFailure(HttpException error, String msg) {

        LogOutputUtils.e(this.getClass().getSimpleName().toString(), "onFailure->" + error.toString());

        if (activity != null && activity.isFinishing()) {
            return;
        }
        Message message = handler.obtainMessage();
        message.what = CommonSetting.FAIL;
        message.obj = FrameBaseApplication.appContext.getResources().getString(R.string.ruis_common_NetworkError);
        handler.sendMessage(message);
    }

}
