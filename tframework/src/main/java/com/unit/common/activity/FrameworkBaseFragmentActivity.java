package com.unit.common.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;

import com.unit.common.R;
import com.unit.common.ui.DialogAndToast;

/**
 * Created by kingkong on 13-9-2.
 */
public abstract class FrameworkBaseFragmentActivity extends FragmentActivity {
    protected String TAG = FrameworkBaseFragmentActivity.class.getSimpleName();
    protected Activity activity;
    protected ProgressDialog progressDialog;

   public Handler dialogHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            hideProgressDialog();
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        progressDialog = DialogAndToast.getProgressDialog(activity, getString(R.string.ruis_common_idLoading));
        TAG = this.getClass().getSimpleName();
    }

    /**
     * 初始化界面
     */
    public void initView() {
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
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