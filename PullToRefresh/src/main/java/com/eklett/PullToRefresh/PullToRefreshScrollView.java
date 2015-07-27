package com.eklett.PullToRefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by Eklett on 15/7/1.
 */
public class PullToRefreshScrollView extends PullToRefreshBase<ScrollView> {
    public PullToRefreshScrollView(Context context) {
        super(context);
    }

    public PullToRefreshScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected ScrollView createRefreshableView(Context context, AttributeSet attrs) {
        ScrollView scrollView = new ScrollView(context, attrs);
        scrollView.setId(R.id.ptr_lib_scroll_view);
        return scrollView;
    }

    @Override
    protected void resetRefreshableState() {

    }

    @Override
    protected void pullUpRefresh(State mCurrentState, Mode mCurrentMode) {

    }

    @Override
    protected View createLoadingLayout(Context context) {
        return null;
    }

}
