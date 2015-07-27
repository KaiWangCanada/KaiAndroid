package com.mmfcommon.activity;

import com.meimeifa.base.activity.MMFBaseActivityNormal;

import cn.jpush.android.api.JPushInterface;

/**
 * 不支持右滑退出
 * Created by kingkong on 15/1/12.
 */
public class AppBaseActivityNormal extends MMFBaseActivityNormal {

    String tag = this.getClass().getSimpleName();

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }
}
