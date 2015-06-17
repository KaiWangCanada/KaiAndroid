package com.unit.common.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.unit.common.ui.DialogAndToast;

/**
 * Created by kingkong on 14-1-2.
 */

public class FrameworkBaseFragment extends Fragment {

    public Fragment fragment;

    protected String TAG = FrameworkBaseFragment.class.getSimpleName();

    public Activity activity;
    public ProgressDialog progressDialog;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        progressDialog = DialogAndToast.getProgressDialog(activity, "");
    }

    public void showProgressDialog() {
        if (!activity.isFinishing()&&progressDialog!=null&&!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    public void hideProgressDialog() {
        if (!activity.isFinishing()&&progressDialog != null&&progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragment = this;
        TAG = this.toString();
    }

    protected void doSthBeforeExit() {
    }

    public void exit() {
        doSthBeforeExit();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
