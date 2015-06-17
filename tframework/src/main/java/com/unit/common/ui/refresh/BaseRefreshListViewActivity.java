package com.unit.common.ui.refresh;


import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.unit.common.resource.MResource;

public abstract class BaseRefreshListViewActivity extends BaseRefreshActivity {



    @Override
    protected View setRootContentView() {
        return getLayoutInflater().inflate(
                MResource.getIdByName(getApplication(), "layout", "ruisframework_pulltorefresh_listview"), null);
    }

    @Override
    protected AbsListView setAbsListView() {
        return (ListView) findViewById(MResource
                .getIdByName(getApplication(), "id", "framework_pulltorefresh_listview"));
    }

}
