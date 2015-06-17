package com.unit.common.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;

import com.unit.common.application.FrameBaseApplication;
import com.unit.common.config.CommonSetting;
import com.unit.common.utils.AppUpdateUtils;

/**
 * 更新App的服务
 * Created by longbin on 15/2/4.
 */
public class UpdateService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String url = intent.getStringExtra(CommonSetting.UPDATE_APK_URL);
        if (TextUtils.isEmpty(url)) {
            FrameBaseApplication.isDownLoadAppCompleted = true;
            return super.onStartCommand(intent, flags, startId);
        }
        AppUpdateUtils appUpdateUtils = new AppUpdateUtils(this);
        appUpdateUtils.update(url);
        return super.onStartCommand(intent, flags, startId);
    }
}
