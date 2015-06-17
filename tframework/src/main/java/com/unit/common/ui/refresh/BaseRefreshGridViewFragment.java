package com.unit.common.ui.refresh;

import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;

import com.unit.common.resource.MResource;


public abstract class BaseRefreshGridViewFragment extends BaseRefreshFragment {


    @Override
    protected View setRootContentView () {
        return getActivity().getLayoutInflater().inflate(MResource.getIdByName(getActivity().getApplication(), "layout", "ruisframework_pulltorefresh_gridview"), null);
    }

    @Override
    protected AbsListView setAbsListView () {
        return (GridView) rootView.findViewById(MResource.getIdByName(getActivity().getApplication(), "id", "framework_pulltorefresh_gridview"));
    }

}
