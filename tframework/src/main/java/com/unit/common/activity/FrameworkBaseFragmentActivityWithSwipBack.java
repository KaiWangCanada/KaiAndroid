package com.unit.common.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;
import com.unit.common.R;
import com.unit.common.ui.DialogAndToast;
import com.unit.common.utils.DisplayUtils;

/**
 * Created by kingkong on 13-9-2.
 */
public class FrameworkBaseFragmentActivityWithSwipBack extends FragmentActivity {
    protected String TAG = FrameworkBaseFragmentActivityWithSwipBack.class.getSimpleName();
    protected Activity activity;
    protected ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = this;
        progressDialog = DialogAndToast.getProgressDialog(activity, getString(R.string.ruis_common_idLoading));
        SlidrConfig config = new SlidrConfig.Builder()
                .sensitivity(0.2f)
                .position(SlidrPosition.LEFT)
                .touchSize(DisplayUtils.convertDIP2PX(this, 50))
                .build();
        try {
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                Slidr.attach(this, config);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void showProgressDialog() {
        if (!activity.isFinishing()&&progressDialog!=null&&!progressDialog.isShowing())
            progressDialog.show();
    }

    public void hideProgressDialog() {
        if (!activity.isFinishing()&&progressDialog!=null&&progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        return true;
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    /**
     * 初始化界面
     */
    public void initView() {
    }


    @Override
    protected void onDestroy() {
        hideProgressDialog();
        super.onDestroy(); //
    }

    public void pareJsonAndUpdateView(String jsonString) {
    }

    protected void doSthBeforeExit() {
    }

    public void exit() {
        doSthBeforeExit();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}