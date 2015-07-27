package com.mmfcommon.utils;

import android.graphics.Paint;
import android.widget.TextView;

/**
 * Created by 黄东鲁 on 15/1/26.
 */
public class ViewUtils {

    /**
     * 加删除线
     *
     * @param textView
     */
    public static void addDelLine(TextView textView) {
        Paint paint = textView.getPaint();
        paint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        paint.setAntiAlias(true);
    }

}
