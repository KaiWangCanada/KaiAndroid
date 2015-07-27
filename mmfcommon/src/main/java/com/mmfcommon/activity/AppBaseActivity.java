package com.mmfcommon.activity;

import android.view.View;
import com.meimeifa.base.activity.MMFBaseActivity;
import com.zhuge.analysis.stat.ZhugeSDK;

import cn.jpush.android.api.JPushInterface;

/**
 * 支持右滑退出
 * Created by kingkong on 15/1/12.
 */
public class AppBaseActivity extends MMFBaseActivity {

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
        //初始化分析跟踪
        ZhugeSDK.getInstance().init(getApplicationContext());
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    public void exit() {
        ZhugeSDK.getInstance().flush(getApplicationContext());
        super.exit();
    }

    public <T extends View> T findView(int resId) {
        return (T) findViewById(resId);
    }

}
