package com.unit.common.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.unit.common.R;
import com.unit.common.application.FrameBaseApplication;
import com.unit.common.config.CommonSetting;

import java.io.File;

/**
 * 下载APP的工具类
 * Created by longbin on 15/2/4.
 */
public class AppUpdateUtils {

    private NotificationManager notificationManager;
    private Notification notification;
    private int notificationId = 0x121;
    private PendingIntent contentIntent;
    private Intent intentInstallApk;

    private Context context;

    public AppUpdateUtils(Context context) {
        this.context = context;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notification = new Notification();
    }


    public void update(String url) {
        String filePath = CommonSetting.downloadDirectory.getAbsolutePath() + File.separator + "temp" + System.currentTimeMillis() + ".apk";
        FileDownloadUtils.downloadFiles(url, filePath, new FileDownloadUtils.DownloadCallback<File>() {
            @Override
            public void onStart() {
                super.onStart();
                startDownload();
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
                loading(total, current, isUploading);
            }

            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                super.onSuccess(responseInfo);
                notifyInstallApk(responseInfo.result);
                FrameBaseApplication.isDownLoadAppCompleted = true;
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                super.onFailure(error, msg);
            }
        });
    }

    private void startDownload() {
        notification.icon = android.R.drawable.stat_sys_download;
        notification.tickerText = context.getString(R.string.app_name) + context.getString(R.string.ruis_update);
        notification.setLatestEventInfo(context,
                context.getString(R.string.ruis_already_download) + context.getString(R.string.app_name),
                "0%", null);
        notification.when = System.currentTimeMillis();
        notification.defaults = Notification.DEFAULT_LIGHTS;
        notification.flags = Notification.FLAG_NO_CLEAR;

        Intent updateIntent = new Intent();
        updateIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        contentIntent = PendingIntent.getActivity(context, 0, updateIntent, 0);
        notification.contentIntent = contentIntent;

        // 将下载任务添加到任务栏中
        notificationManager.notify(notificationId, notification);
    }

    private void loading(long total, long current, boolean isUploading) {
        if (total < 0.0001) {
            return;
        }
        int percent = (int) ((double) current / (double) total * 100);
        notification.setLatestEventInfo(context,
                context.getString(R.string.ruis_already_download) + context.getString(R.string.app_name),
                percent + "%", null);
        Intent nullIntent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, nullIntent, 0);
        notification.contentIntent = pendingIntent;
        notificationManager.notify(notificationId, notification);
    }

    private void notifyInstallApk(File file) {
        installApk(file);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intentInstallApk, 0);
        notification.flags = Notification.FLAG_INSISTENT;
        notification.contentIntent = contentIntent;
        notification.setLatestEventInfo(context, context.getString(R.string.app_name),
                context.getString(R.string.ruis_download_app_complete), contentIntent);

        // 将下载任务添加到任务栏中
        notificationManager.notify(notificationId, notification);
    }

    public void installApk(File file) {
        intentInstallApk = new Intent(Intent.ACTION_VIEW);
        intentInstallApk.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intentInstallApk.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intentInstallApk);
    }

}
