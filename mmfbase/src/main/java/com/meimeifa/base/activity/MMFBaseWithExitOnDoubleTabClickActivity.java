package com.meimeifa.base.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.meimeifa.base.event.TabDoubleClickListener;


/**
 * Created by kingkong on 15/1/20.
 */
public abstract class MMFBaseWithExitOnDoubleTabClickActivity extends MMFBaseWithExitActivity implements TabDoubleClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onRegisterListener(this,this);
    }

    /**
     * 注册接收双击tab广播
     *
     * @param listener
     * @param activity
     */
    @Override
    public void onRegisterListener(TabDoubleClickListener listener, Activity activity) {
        TabDoubleClickListener.RegisterDoubleClick.onRegisterDoubleClick(listener, activity);
    }

    /**
     * 处理双击事件
     *
     * @param context
     * @param intent
     */
    @Override
    public abstract void handlerDoubleClick(Context context, Intent intent);
}
