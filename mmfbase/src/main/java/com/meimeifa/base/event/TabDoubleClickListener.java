package com.meimeifa.base.event;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.meimeifa.base.common.Constants;


/**
 * Created by kingkong on 13-8-26.
 */
public interface TabDoubleClickListener {

    /**
     * 注册双击tab的广播
     */

    public abstract void onRegisterListener(TabDoubleClickListener listener, Activity activity);

    /**
     * 处理tab双击事件
     *
     * @param context
     * @param intent
     */
    public abstract void handlerDoubleClick(Context context, Intent intent);


    /**
     * 接收双击tab的广播
     */
    public static class BroadcastTabDoubleClick extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

    /**
     * activity注册双击广播
     */
    public static class RegisterDoubleClick {

        public static void onRegisterDoubleClick(final TabDoubleClickListener listener, final Activity activity) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Constants.BroadcastTabDoubleClick);
            activity.registerReceiver(new BroadcastTabDoubleClick() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    super.onReceive(context, intent);

                    listener.handlerDoubleClick(context, intent);
                }
            }, intentFilter);
        }
    }
}
