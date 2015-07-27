package com.meimeifa.base.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.meimeifa.base.R;
import com.newrelic.agent.android.NewRelic;
import com.unit.common.activity.FrameworkBaseFragmentActivity;


/**
 * Created by kingkong on 15/1/12.
 */
public class MMFBaseWithExitActivity extends FrameworkBaseFragmentActivity {

    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //NewRelic统计,自定义标签
        NewRelic.recordMetric(this.getClass().getSimpleName(), "record activity", 1);
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(),
                    R.string.exit,
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            exit();
        }
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
}
