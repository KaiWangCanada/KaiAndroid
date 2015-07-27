package com.mmfcommon.activity;

import com.meimeifa.base.activity.MMFBaseWithExitActivity;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by kingkong on 15/1/12.
 */
public class AppBaseWithExitActivity extends MMFBaseWithExitActivity {

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
