package com.unit.common.utils;

import android.util.Log;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.unit.common.httputils.AppsHttpUtil;

import java.io.File;

/**
 * Created by kingkong on 14/10/28.
 */
public class FileDownloadUtils {

    static String TAG = "FielDownloadUtils";

    /**
     * 下载文件异步方法,支持断点续传，随时停止下载任务，开始任务
     *
     * @param fileUrl
     * @param filePath
     * @param callbackHandler
     * @return
     */
    public static HttpHandler downloadFiles(String fileUrl, String filePath, DownloadCallback<File> callbackHandler) {

        HttpHandler handler;

        //可以调用
//        handler.pause(); //暂停下载
//        handler.cancel();//取消下载


        return handler = new AppsHttpUtil().download(
                fileUrl, //文件下载地址
                filePath,//文件存储到sd卡的路径
                true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
                true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
                callbackHandler);


    }

    public static class DownloadCallback<File> extends RequestCallBack<File> {

        @Override
        public void onStart() {
            Log.d(TAG, "onStart ->");
        }

        @Override
        public void onLoading(long total, long current, boolean isUploading) {

            Log.d(TAG, " onLoading current ->" + current + "  total->" + total + "  isUploading->" + isUploading);

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


}
