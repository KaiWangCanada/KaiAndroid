package com.meimeifa.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.baidu.mobstat.StatService;
import com.meimeifa.base.R;
import com.newrelic.agent.android.NewRelic;
import com.unit.common.activity.FrameworkBaseFragmentActivity;


/**
 * 不支持右滑退出
 * Created by kingkong on 15/1/12.
 */
public class MMFBaseActivityNormal extends FrameworkBaseFragmentActivity {

    String tag = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //NewRelic统计,自定义标签
        NewRelic.recordMetric(this.getClass().getSimpleName(), "record activity", 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
    }


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_left_out);
    }

    public void onBack(View v) {
        this.finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_right_out);
    }

    @Override
    public void exit() {
        MMFBaseActivity.isRunning = false;
        super.exit();
    }
}
