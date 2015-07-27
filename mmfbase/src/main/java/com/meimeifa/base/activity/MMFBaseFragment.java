package com.meimeifa.base.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unit.common.activity.FrameworkBaseFragment;

/**
 * Created by kingkong on 14-1-2.
 */
public abstract class MMFBaseFragment extends FrameworkBaseFragment {


    protected View rootView;

    protected View setupView(LayoutInflater inflater, int layoutId) {

        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(layoutId, null);
        }
        return rootView;
    }

    protected void initView(View view) {
    }
}
