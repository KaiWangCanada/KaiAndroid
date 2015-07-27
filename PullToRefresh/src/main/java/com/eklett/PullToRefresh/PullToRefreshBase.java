package com.eklett.PullToRefresh;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by Eklett on 15/6/29.
 */
public abstract class PullToRefreshBase<T extends View> extends VerticalSwipeRefreshLayout {
    FrameLayout mRefreshableViewWrapper;

    T mRefreshableView;
    View mLoadingLayout;
    OnRefreshListener<T> mOnRefreshListener;
    OnRefreshListener2<T> mOnRefreshListener2;
    Mode mMode = Mode.getDefault();
    State mState = State.getDefault();

    static final String STATE_STATE = "ptr_state";
    static final String STATE_MODE = "ptr_mode";
    static final String STATE_SUPER = "ptr_super";

    public PullToRefreshBase(Context context) {
        super(context);
        init(context, null);
    }

    public PullToRefreshBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // Refreshable View
        // By passing the attrs, we can add ListView/GridView params via XML
        mRefreshableView = createRefreshableView(context, attrs);
        addRefreshableView(context, mRefreshableView);
        mLoadingLayout = createLoadingLayout(context);
        setSwipeRefreshListener();
        pullUpRefresh(mState, mMode);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (null == mRefreshableViewWrapper || child == mRefreshableViewWrapper) {
            addViewInternal(child, index, params);
            return;
        }
        if ((null == getRefreshableView() || child == getRefreshableView())) {
            mRefreshableViewWrapper.addView(child, index, params);
            return;
        }
        final T refreshableView = getRefreshableView();

        if (refreshableView instanceof ViewGroup) {
            ((ViewGroup) refreshableView).addView(child, index, params);
        } else {
            throw new UnsupportedOperationException("Refreshable View is not a ViewGroup so can't addView");
        }
    }

    /**
     * 把listView/gridView/scrollView添加到swipeRefreshLayout中.
     * swipeRefreshLayout中间添加一层FrameLayout用于添加emptyView,修复listView等添加EmptyView与SwipeRefreshLayout冲突的bug.
     */
    private void addRefreshableView(Context context, T refreshableView) {
        mRefreshableViewWrapper = new FrameLayout(context);
        mRefreshableViewWrapper.addView(refreshableView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        addViewInternal(mRefreshableViewWrapper,
                new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    /**
     * Used internally for adding view. Need because we override addView to
     * pass-through to the Refreshable View
     */
    protected final void addViewInternal(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
    }

    /**
     * Used internally for adding view. Need because we override addView to
     * pass-through to the Refreshable View
     */
    protected final void addViewInternal(View child, ViewGroup.LayoutParams params) {
        super.addView(child, -1, params);
    }

    public void setRefreshing() {
        setRefreshing(true);
    }

    public final T getRefreshableView() {
        return mRefreshableView;
    }

    protected abstract T createRefreshableView(Context context, AttributeSet attrs);

    protected abstract void resetRefreshableState();

    protected abstract void pullUpRefresh(State mCurrentState, Mode mCurrentMode);

    protected abstract View createLoadingLayout(Context context);

    public View getLoadingLayout(){
        return mLoadingLayout;
    }

    public void setLoadingLayout(View view) {
        this.mLoadingLayout = view;
    }

    public void setMode(Mode mMode) {
        this.mMode = mMode;
        if (mMode == Mode.DISABLE || mMode == Mode.PULL_FROM_END) {
            setEnabled(false);
        } else {
            setEnabled(true);
        }
    }

    public Mode getMode() {
        return mMode;
    }

    State getState() {
        return mState;
    }

    void setState(State mState) {
        this.mState = mState;
    }

    /**
     * 设置下拉事件监听
     */
    private void setSwipeRefreshListener() {
        setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mMode == Mode.DISABLE || mMode == Mode.PULL_FROM_END) {
                    return;
                }
                if (mState == State.REFRESHING) {
                    return;
                }
                mState = State.REFRESHING;
                //刷新时重置加载更多的状态.
                resetRefreshableState();
                if (null != mOnRefreshListener2) {
                    mOnRefreshListener2.onPullDownToRefresh(PullToRefreshBase.this);
                }
                if (null != mOnRefreshListener) {
                    mOnRefreshListener.onRefresh(PullToRefreshBase.this);
                }
            }
        });
    }

    /**
     * 刷新完成重置状态.
     */
    public final void onRefreshComplete() {
        if (isRefreshing()) {
            setState(State.RESET);
            setRefreshing(false);
        }
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        super.setRefreshing(refreshing);
        if (refreshing) {
            mState = State.REFRESHING;
        } else {
            mState = State.RESET;
        }
    }

    @Override
    public boolean isRefreshing() {
        return super.isRefreshing() || mState == State.REFRESHING || mState == State.MANUAL_REFRESHING;
    }

    /**
     * Called by {@link #onRestoreInstanceState(android.os.Parcelable)} so that derivative
     * classes can handle their saved instance state.
     *
     * @param savedInstanceState - Bundle which contains saved instance state.
     */
    protected void onPtrRestoreInstanceState(Bundle savedInstanceState) {
    }

    /**
     * Called by {@link #onSaveInstanceState()} so that derivative classes can
     * save their instance state.
     *
     * @param saveState - Bundle to be updated with saved state.
     */
    protected void onPtrSaveInstanceState(Bundle saveState) {
    }

    @Override
    protected final void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;

            setMode(Mode.mapIntToValue(bundle.getInt(STATE_MODE, 0)));


            // Let super Restore Itself
            super.onRestoreInstanceState(bundle.getParcelable(STATE_SUPER));

            State viewState = State.mapIntToValue(bundle.getInt(STATE_STATE, 0));
            if (viewState == State.REFRESHING || viewState == State.MANUAL_REFRESHING) {
                setState(viewState);
            }

            // Now let derivative classes restore their state
            onPtrRestoreInstanceState(bundle);
            return;
        }

        super.onRestoreInstanceState(state);
    }

    @Override
    protected final Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();

        // Let derivative classes get a chance to save state first, that way we
        // can make sure they don't overrite any of our values
        onPtrSaveInstanceState(bundle);

        bundle.putInt(STATE_STATE, mState.getIntValue());
        bundle.putInt(STATE_MODE, mMode.getIntValue());
        bundle.putParcelable(STATE_SUPER, super.onSaveInstanceState());

        return bundle;
    }

    public final void setOnRefreshListener(OnRefreshListener<T> listener) {
        mOnRefreshListener = listener;
        mOnRefreshListener2 = null;
    }

    public final void setOnRefreshListener(OnRefreshListener2<T> listener) {
        mOnRefreshListener2 = listener;
        mOnRefreshListener = null;
    }

    public static enum Mode {
        DISABLE(0x1),
        PULL_FROM_START(0x2),
        PULL_FROM_END(0x3),
        BOTH(0x4);

        static Mode getDefault() {
            return PULL_FROM_START;
        }

        static Mode mapIntToValue(final int modeInt) {
            for (Mode value : Mode.values()) {
                if (modeInt == value.getIntValue()) {
                    return value;
                }
            }

            // If not, return default
            return getDefault();
        }

        private int mIntValue;

        // The modeInt values need to match those from attrs.xml
        Mode(int modeInt) {
            mIntValue = modeInt;
        }

        int getIntValue() {
            return mIntValue;
        }

    }

    public static enum State {

        /**
         * When the UI is in a state which means that user is not interacting
         * with the Pull-to-Refresh function.
         */
        RESET(0x1),

//        /**
//         * When the UI is being pulled by the user, but has not been pulled far
//         * enough so that it refreshes when released.
//         */
//        PULL_TO_REFRESH,
//
//        /**
//         * When the UI is being pulled by the user, and <strong>has</strong>
//         * been pulled far enough so that it will refresh when released.
//         */
//        RELEASE_TO_REFRESH,

        /**
         * When the UI is currently refreshing, caused by a pull gesture.
         */
        REFRESHING(0x2),

        /**
         * When the UI is currently refreshing, caused by a call to.
         */
        MANUAL_REFRESHING(0x3);

        static State getDefault() {
            return RESET;
        }

        static State mapIntToValue(final int modeInt) {
            for (State value : State.values()) {
                if (modeInt == value.getIntValue()) {
                    return value;
                }
            }

            // If not, return default
            return getDefault();
        }

        private int mIntValue;

        // The modeInt values need to match those from attrs.xml
        State(int modeInt) {
            mIntValue = modeInt;
        }

        int getIntValue() {
            return mIntValue;
        }
    }

    @Override
    public boolean canChildScrollUp() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (mRefreshableView != null)
                return mRefreshableView.canScrollVertically(-1);
        }

        return false;
    }

    /**
     * Simple Listener to listen for any callbacks to Refresh.
     *
     * @author Chris Banes
     */
    public static interface OnRefreshListener<V extends View> {

        /**
         * onRefresh will be called for both a Pull from start, and Pull from
         * end
         */
        public void onRefresh(final PullToRefreshBase<V> refreshView);

    }

    /**
     * An advanced version of the Listener to listen for callbacks to Refresh.
     * This listener is different as it allows you to differentiate between Pull
     * Ups, and Pull Downs.
     *
     * @author Chris Banes
     */
    public static interface OnRefreshListener2<V extends View> {

        /**
         * onPullDownToRefresh will be called only when the user has Pulled from
         * the start, and released.
         */
        public void onPullDownToRefresh(final PullToRefreshBase<V> refreshView);

        /**
         * onPullUpToRefresh will be called only when the user has Pulled from
         * the end, and released.
         */
        public void onPullUpToRefresh(final PullToRefreshBase<V> refreshView);

    }
}
