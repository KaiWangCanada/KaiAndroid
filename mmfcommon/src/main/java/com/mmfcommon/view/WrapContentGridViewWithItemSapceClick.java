package com.mmfcommon.view;

/**
 * 空白处可以点击的GridView
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class WrapContentGridViewWithItemSapceClick extends WrapContentGridView {
    OnTouchBlankPositionListener mTouchBlankPosListener;
    int BlankPosition = -1;

    public WrapContentGridViewWithItemSapceClick(Context context) {
        super(context);
    }

    public WrapContentGridViewWithItemSapceClick(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapContentGridViewWithItemSapceClick(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mTouchBlankPosListener != null) {
            if (!isEnabled()) {
                // A disabled view that is clickable still consumes the touch
                // events, it just doesn't respond to them.
                return isClickable() || isLongClickable();
            }

            if (event.getActionMasked() == MotionEvent.ACTION_UP) {
                final int motionPosition = pointToPosition((int) event.getX(), (int) event.getY());
                //如果当前点击的位置在空白列表里面就触发事件
                if (motionPosition == BlankPosition) {
                    return mTouchBlankPosListener.onTouchBlankPosition();
                }
            }
        }

        return super.onTouchEvent(event);
    }

    public interface OnTouchBlankPositionListener {
        /**
         * @return 是否要终止事件的路由
         */
        boolean onTouchBlankPosition();
    }

    public void setOnTouchBlankPositionListener(OnTouchBlankPositionListener listener) {
        mTouchBlankPosListener = listener;
    }

//    public void setBlankPositionList(List<Integer> blankPositionList) {
//        this.blankPositionList = blankPositionList;
//    }
}