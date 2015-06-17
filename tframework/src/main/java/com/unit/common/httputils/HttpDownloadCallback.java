package com.unit.common.httputils;

import android.util.Log;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;

/**下载文件回调,目前主要加上一些debug打印
 * Created by kingkong on 15/1/11.
 */
public class HttpDownloadCallback extends RequestCallBack<File> {

    public static String TAG = HttpDownloadCallback.class.getSimpleName();

    @Override
    public void onStart() {
        Log.d(TAG, "onStart ->");
    }

    @Override
    public void onLoading(long total, long current, boolean isUploading) {
        Log.d(TAG, " onLoading current ->" + current + "  total->" + total);
    }

    @Override
    public void onSuccess(ResponseInfo<File> responseInfo) {
        Log.d(TAG, "onSuccess ->");
    }


    @Override
    public void onFailure(HttpException error, String msg) {
        Log.d(TAG, "onFailure ->" + msg);
    }
}
