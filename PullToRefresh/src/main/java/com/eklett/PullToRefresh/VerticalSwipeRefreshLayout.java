package com.eklett.PullToRefresh;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by Alvin on 2015/7/14.
 */
public class VerticalSwipeRefreshLayout extends SwipeRefreshLayout {


    private int mTouchSlop;
    // 上一次触摸时的X坐标
    private float mPrevX;

    public static int XDistance=60;//如果x轴超过这个距离就不认为是垂直滑动，避免刷新


    public VerticalSwipeRefreshLayout(Context context){
        super(context);
       setmTouchSlop(ViewConfiguration.get(context).getScaledTouchSlop());
    }


    public VerticalSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        setmTouchSlop(ViewConfiguration.get(context).getScaledTouchSlop());
    }

    public void setmTouchSlop(int mTouchSlop) {
        // 触发移动事件的最短距离，如果小于这个距离就不触发移动控件
        this.mTouchSlop = mTouchSlop;
    }


    public int getmTouchSlop() {
        return mTouchSlop;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = event.getX();
                break;

            case MotionEvent.ACTION_MOVE:
                final float eventX = event.getX();
                float xDiff = Math.abs(eventX - mPrevX);
                // Log.d("refresh" ,"move----" + eventX + "   " + mPrevX + "   " + mTouchSlop);
                // 增加60的容差，让下拉刷新在竖直滑动时就可以触发
                if (xDiff > mTouchSlop + XDistance) {
                    return false;
                }
        }

        return super.onInterceptTouchEvent(event);
    }
}
