package com.eklett.PullToRefresh;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Eklett on 15/6/29.
 */
public class PullToRefreshListView extends PullToRefreshBase<ListView> {
    private View footView;
    private TextView tvFooter;
    private ProgressBar pbFooter;
    private boolean isLoadFailure = false;
    private String loadingText;

    public PullToRefreshListView(Context context) {
        super(context);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected ListView createRefreshableView(Context context, AttributeSet attrs) {
        loadingText = context.getString(R.string.ptr_loading);
        ListView listView = new ListView(context, attrs);
        listView.setId(android.R.id.list);
        listView.setFooterDividersEnabled(false);//nexus5 5.0系统以上如果不设置这一行会报数组异常
        return listView;
    }

    @Override
    public void resetRefreshableState() {
        isLoadFailure = false;
    }

    @Override
    protected View createLoadingLayout(Context context) {
        addFooterView();
        return footView;
    }

    public void addFooterView() {
        removeFooterView();
        footView = LayoutInflater.from(getContext()).inflate(R.layout.ptr_list_footer, null);
        tvFooter = (TextView) footView.findViewById(R.id.footer_button);
        pbFooter = (ProgressBar) footView.findViewById(R.id.footer_progressBar);
        mRefreshableView.addFooterView(footView);
        footView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isLoadFailure = false;
                loadMore();
            }
        });
    }

    @Override
    public void setLoadingLayout(View view) {
        super.setLoadingLayout(view);
        removeFooterView();
        mRefreshableView.addFooterView(view);
    }

    public void removeFooterView() {
        if (null != footView && mRefreshableView.getFooterViewsCount() != 0) {
            mRefreshableView.removeFooterView(footView);
            footView = null;
            tvFooter = null;
            pbFooter = null;
        }
    }

    public void hideFooterView() {
        if (null != footView) {
            footView.setVisibility(GONE);
        }
        isLoadFailure = true;
    }

    public void showFooterView() {
        if (null != footView) {
            footView.setVisibility(VISIBLE);
        }
        isLoadFailure = false;
    }

    @Override
    protected void pullUpRefresh(State mCurrentState, Mode mCurrentMode) {
        setAutoLoadMore();
        if (mMode == Mode.PULL_FROM_START || mMode == Mode.DISABLE) {
            removeFooterView();
        }
    }

    @Override
    public void setMode(Mode mMode) {
        super.setMode(mMode);
        if (mMode == Mode.PULL_FROM_START || mMode == Mode.DISABLE) {
            removeFooterView();
        } else {
            addFooterView();
        }
    }

    private void setAutoLoadMore() {
        mRefreshableView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (0 == firstVisibleItem && visibleItemCount == totalItemCount) {
                    removeFooterView();
                    return;
                }
                // 滑动到底部的时候自动加载更多
                if (mRefreshableView.getCount() != 0 && firstVisibleItem + visibleItemCount >= totalItemCount
                        && !isRefreshing() && !isLoadFailure) {
                    loadMore();
                }
            }
        });
    }

    private void loadMore() {
        if (mMode == Mode.BOTH) {
            if (null != mOnRefreshListener2) {
                setState(State.REFRESHING);
                showLoadingView();
                mOnRefreshListener2.onPullUpToRefresh(this);
            }
        } else if (mMode == Mode.PULL_FROM_END) {
            if (null != mOnRefreshListener) {
                setState(State.REFRESHING);
                showLoadingView();
                mOnRefreshListener.onRefresh(this);
            }
        }
    }

    private void showLoadingView() {
        if (null != footView) {
            showFooterView();
        } else {
            addFooterView();
            showFooterView();
        }
        setLoadingViewText(loadingText);
        if (null != pbFooter) {
            pbFooter.setVisibility(VISIBLE);
        }
    }

    public void setLoadingText(String loadingText) {
        this.loadingText = loadingText;
    }

    public void setIsLoadFailure(boolean isLoadFailure) {
        this.isLoadFailure = isLoadFailure;
    }

    /**
     * 加载更多失败,显示加载失败的样式并且让用户可以点击重新加载.
     */
    public void loadMoreFailure() {
        loadMoreFailure(null);
    }

    /**
     * 加载更多失败,显示加载失败的样式并且让用户可以点击重新加载.
     * @param message 显示加载失败的信息
     */
    public void loadMoreFailure(String message) {
        if (null == mRefreshableView.getAdapter() || mRefreshableView.getAdapter().getCount() == 0) {
            hideFooterView();
            return;
        } else {
            showFooterView();
        }
        if (TextUtils.isEmpty(message)) {
            message = getContext().getString(R.string.ptr_load_failure);
        }
        setLoadingViewText(message);
    }

    /**
     * 如果数据全部加载完了,隐藏footerView并且设置不可滚动到底部就加载更多.
     */
    public void noMoreData() {
        removeFooterView();
        isLoadFailure = true;
    }

    private void setLoadingViewText(String message) {
        if (null != tvFooter) {
            tvFooter.setVisibility(VISIBLE);
            tvFooter.setText(message);
        }
        if (null != pbFooter) {
            pbFooter.setVisibility(GONE);
        }
    }

    public void setEmptyView(View emptyView) {
        if (null != emptyView) {
            emptyView.setClickable(true);

            // 把emptyView从外面的布局中转移到swipeRefreshLayout中的frameLayout来,再设置为listView的emptyView.
            // 这样来解决swipeRefreshLayout里面的listView设置emptyView不显示或者不能刷新的bug.
            ViewParent newEmptyViewParent = emptyView.getParent();
            if (null != newEmptyViewParent && newEmptyViewParent instanceof ViewGroup) {
                ((ViewGroup) newEmptyViewParent).removeView(emptyView);
            }

            mRefreshableViewWrapper.addView(emptyView, -1, convertEmptyViewLayoutParams(emptyView.getLayoutParams()));
        }

        mRefreshableView.setEmptyView(emptyView);
        mRefreshableView.setVisibility(VISIBLE);
    }

    public void setAdapter(ListAdapter adapter) {
        addFooterView();//在设置adapter之前加上这一句，加上HeadView或者FooterView才行，要不listview会随机调用adater
        mRefreshableView.setAdapter(adapter);
        mRefreshableView.setVisibility(VISIBLE);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mRefreshableView.setOnItemClickListener(listener);
    }

    private static FrameLayout.LayoutParams convertEmptyViewLayoutParams(ViewGroup.LayoutParams lp) {
        FrameLayout.LayoutParams newLp = null;

        if (null != lp) {
            newLp = new FrameLayout.LayoutParams(lp);

            if (lp instanceof LinearLayout.LayoutParams) {
                newLp.gravity = ((LinearLayout.LayoutParams) lp).gravity;
            } else {
                newLp.gravity = Gravity.CENTER;
            }
        }

        return newLp;
    }
}
