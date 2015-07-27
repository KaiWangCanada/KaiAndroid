package com.mmfcommon.utils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.unit.common.utils.LogOutputUtils;

/**
 * Created by 黄东鲁 on 15/7/1.
 */
public class RestartAppUtils {


  public static void restart(Activity context, int delay) {
    if (delay == 0) {
      delay = 1;
    }
    Intent restartIntent =
        context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
    restartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    restartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK );
    PendingIntent intent =
        PendingIntent.getActivity(context, 0, restartIntent, PendingIntent.FLAG_ONE_SHOT);
    AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    manager.set(AlarmManager.RTC, System.currentTimeMillis() + delay, intent);
    context.finish();
    System.exit(2);
  }

}
