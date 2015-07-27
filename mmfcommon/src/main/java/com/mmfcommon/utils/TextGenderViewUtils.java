package com.mmfcommon.utils;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.mmfcommon.R;

/**
 * Created by 黄东鲁 on 15/1/27.
 */
public class TextGenderViewUtils {

    public static void setGirl(TextView v) {
        Drawable drawable = v.getContext().getResources().getDrawable(R.drawable.icon_girl);
        v.setBackgroundColor(v.getContext().getResources().getColor(R.color.bg_girl));
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        v.setCompoundDrawables(drawable, null, null, null);
    }

    public static void setBoy(TextView v) {
        Drawable drawable = v.getContext().getResources().getDrawable(R.drawable.icon_boy);
        v.setBackgroundColor(v.getContext().getResources().getColor(R.color.bg_boy));
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        v.setCompoundDrawables(drawable, null, null, null);
    }
}
