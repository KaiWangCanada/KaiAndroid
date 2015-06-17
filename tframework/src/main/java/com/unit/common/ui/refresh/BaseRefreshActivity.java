package com.unit.common.ui.refresh;


import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.client.HttpRequest;
import com.unit.common.activity.FrameworkBaseFragmentActivity;
import com.unit.common.httputils.HttpRequestCallback;
import com.unit.common.httputils.HttpRequestCallbackHandler;
import com.unit.common.resource.MResource;
import com.unit.common.utils.LogOutputUtils;


public abstract class BaseRefreshActivity extends FrameworkBaseFragmentActivity implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {

    protected int pageNo = 1;
    protected int pageSize = 20;
    protected String dataUrl = "";
    protected Activity activity;
    protected boolean isLoadFinish = true;//是否从网络加载完数据,避免重复请求
    BaseAdapter baseAdapter;
    protected int totalPage = -1;
    HttpUtils http = new HttpUtils();
    //-----------------控件--------------

    protected PullToRefreshView pullToRefreshView;
    protected ProgressBar articleProgressBar;

    protected AbsListView absListView;
    protected View rootView;

    /**
     * 刷新handler
     */
    HttpRequestCallbackHandler refreshHandler = new HttpRequestCallbackHandler() {

        @Override
        public void onSuccess (String content) {
            super.onSuccess(content);
            hideProgresBar();

            setRefreshHandlerSuccess(content);
            pageNo++;
            isLoadFinish = true;
        }

        @Override
        public void onFailure (String content) {
            super.onFailure(content);
            hideProgresBar();

            setRefreshHandlerFail(content);
            isLoadFinish = true;
        }
    };


    HttpRequestCallbackHandler loadmoreHandler = new HttpRequestCallbackHandler() {
        @Override
        public void onSuccess (String content) {
            super.onSuccess(content);
            hideProgresBar();

            setLoadmoreHandlerSuccess(content);
            pageNo++;
            isLoadFinish = true;

        }

        @Override
        public void onFailure (String content) {
            super.onFailure(content);
            hideProgresBar();

            setLoadmoreHandlerFail(content);
            isLoadFinish = true;
        }
    };


    @Override
    public void onCreate (Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        initView();
        firstLoadListData();
    }


    public void setArticleProgressBar (ProgressBar articleProgressBar) {
        try {
            if (articleProgressBar == null) {
                this.articleProgressBar = (ProgressBar) findViewById(MResource.getIdByName(getApplication(), "id", "loadingprogress"));
            } else {
                this.articleProgressBar = articleProgressBar;
            }

        } catch (Exception e) {
            LogOutputUtils.e(activity.getClass().getSimpleName().toString() + ":setArticleProgressBar()", e.toString());
        }
    }

    public ProgressBar getArticleProgressBar () {
        return articleProgressBar;
    }

    @TargetApi(VERSION_CODES.HONEYCOMB)
    @Override
    public void initView () {
        super.initView();
        rootView = setRootContentView();
        setContentView(rootView);
        absListView = setAbsListView();
        pullToRefreshView = (PullToRefreshView) findViewById(MResource.getIdByName(getApplication(), "id", "framework_main_pull_refresh_view"));
        setArticleProgressBar(null);
        articleProgressBar = getArticleProgressBar();

        absListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> adapterView, View view, int i, long l) {
                OnListItemClick(adapterView, view, i, l);
            }
        });

        pullToRefreshView.setOnHeaderRefreshListener(this);
        pullToRefreshView.setOnFooterRefreshListener(this);

        baseAdapter = getBaseAdapter();
        absListView.setAdapter(baseAdapter);
    }

    /**
     * 滑动加载异步
     */
    protected void loadListItemAsy () {

        //判断是否加载完成
        if (isLoadFinish) {
            if (totalPage < 0 || pageNo > setTotalPage()) {
                hideProgresBar();
                removeFooterView();
              //TODO 没有更多数据
                return;
            }
            isLoadFinish = false;
            dataUrl = setupUrl();
            if (dataUrl != null) {
                http.send(HttpRequest.HttpMethod.GET, dataUrl, new HttpRequestCallback(loadmoreHandler));
            }
            LogOutputUtils.e(TAG, "load pageNo:" + pageNo);
        }
    }

    protected void showProgresBar () {
        if (articleProgressBar != null)
            articleProgressBar.setVisibility(View.VISIBLE);
    }

    protected void hideProgresBar () {
        if (articleProgressBar != null)
            articleProgressBar.setVisibility(View.GONE);
    }


    public boolean isToUpPull() {
        return pullToRefreshView.isToUpPull();
    }

    public boolean isToDownPull() {
        return pullToRefreshView.isToDownPull();
    }

    public void setToUpPull(boolean toUpPull) {
        pullToRefreshView.setToUpPull(toUpPull);
    }

    public void setToDownPull(boolean toDownPull) {
        pullToRefreshView.setToDownPull(toDownPull);
    }

    /**
     * 加载数据
     */
    protected void firstLoadListData () {
        pageNo = 1;
        dataUrl = setupUrl();
        if (!TextUtils.isEmpty(dataUrl)) {
            isLoadFinish = false;
            http.send(HttpRequest.HttpMethod.GET, dataUrl, new HttpRequestCallback(refreshHandler));
        }
    }

    /**
     * 刷新listView并显示正在刷新view
     */

    protected void showRefreshView () {

        pullToRefreshView.headerRefreshing();
    }

    protected void removeFooterView () {

        pullToRefreshView.onFooterRefreshComplete();
    }

    @Override
    public void onFooterRefresh (PullToRefreshView view) {

        pullToRefreshView.postDelayed(new Runnable() {

            @Override
            public void run () {
                loadListItemAsy();
                pullToRefreshView.onFooterRefreshComplete();
            }
        }, 1000);
    }

    @Override
    public void onHeaderRefresh (PullToRefreshView view) {

        pullToRefreshView.postDelayed(new Runnable() {

            @Override
            public void run () {
                //设置更新时间
                //mPullToRefreshView.onHeaderRefreshComplete("最近更新:01-23 12:01");
                firstLoadListData();
                pullToRefreshView.onHeaderRefreshComplete();
            }
        }, 1000);

    }


    //-----------需要重写的方法---------------

    protected abstract View setRootContentView ();

    protected abstract AbsListView setAbsListView ();

    /**
     * 初始化时必须先设置数据适配基类
     *
     * @return
     */
    protected abstract BaseAdapter getBaseAdapter ();

    /**
     * 返回总页数
     *
     * @return
     */
    protected abstract int setTotalPage ();

    /**
     * 让子类通过此方法配置url
     */
    protected abstract String setupUrl ();
    //TODO 到时候分页的注释要去掉
    //        dataUrl = AppsEnv.Tab1ActiityListUrl + "type =" + type + "pageNo=" + pageNo;
    //        return CommonSetting.TestUrl;

    protected void setLoadmoreHandlerSuccess (String content) {
        //TODO 加载更多成功
        removeFooterView();
    }

    protected void setLoadmoreHandlerFail (String content) {
        //TODO 加载更多失败
        LogOutputUtils.e(TAG, content);

    }

    protected void setRefreshHandlerSuccess (String content) {
        //TODO 下拉刷新成功
    }

    protected void setRefreshHandlerFail (String content) {
        //TODO 下拉刷新失败
        LogOutputUtils.e(TAG, content);

    }

    /**
     * 重写此Item点击方法
     *
     * @param l
     * @param v
     * @param position
     * @param id
     */
    protected void OnListItemClick (AdapterView<?> l, View v, int position, long id) {
        //TODO 点击Item
    }

    public HttpUtils getHttp () {
        return http;
    }
}
