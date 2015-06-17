package com.unit.common.ui;

import com.unit.common.utils.DisplayUtils;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;

public class UnitProgressBar extends ProgressBar {

    public UnitProgressBar (Context context) {
        super(context);
        int round = DisplayUtils.convertDIP2PX(context, 30);
        LayoutParams layoutParams = new LayoutParams(round, round);
        layoutParams.gravity = Gravity.CENTER;

        this.setLayoutParams(layoutParams);

    }

}
