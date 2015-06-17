package com.unit.common.ui.refresh;


import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;

import com.unit.common.resource.MResource;

public abstract class BaseRefreshGridViewActivity extends BaseRefreshActivity {


    @Override
    protected View setRootContentView () {
        return getLayoutInflater().inflate(MResource.getIdByName(getApplication(), "layout", "ruisframework_pulltorefresh_gridview"), null);
    }

    @Override
    protected AbsListView setAbsListView () {
        return (GridView) findViewById(MResource.getIdByName(getApplication(), "id", "framework_pulltorefresh_gridview"));
    }

}
