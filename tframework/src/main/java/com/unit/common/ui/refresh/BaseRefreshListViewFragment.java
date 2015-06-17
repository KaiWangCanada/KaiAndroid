package com.unit.common.ui.refresh;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.unit.common.resource.MResource;


public abstract class BaseRefreshListViewFragment extends BaseRefreshFragment {


    @Override
    protected View setRootContentView () {
        return activity.getLayoutInflater().inflate(
                MResource.getIdByName(activity.getApplication(), "layout", "ruisframework_pulltorefresh_listview"), null);
    }

    @Override
    protected AbsListView setAbsListView () {
        return (ListView) rootView.findViewById(MResource
                .getIdByName(activity.getApplication(), "id", "framework_pulltorefresh_listview"));
    }

}
