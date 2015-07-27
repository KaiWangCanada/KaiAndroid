package com.mmfcommon.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

/**
 * Created by longbin on 15/3/14.
 */
public class LimitDatePickerDialog extends DatePickerDialog {

    private int maxYear = -1;
    private int maxMonth = -1;
    private int maxDay = -1;
    private int minYear = -1;
    private int minMonth = -1;
    private int minDay = -1;


    public LimitDatePickerDialog(Context context, OnDateSetListener callBack, int year, int month, int day) {
        super(context, callBack, year, month, day);
    }

    public LimitDatePickerDialog(Context context, int theme, OnDateSetListener listener, int year, int month, int day) {
        super(context, theme, listener, year, month, day);
    }

    public void setMaxDate(int maxYear, int maxMonth, int maxDay) {
        this.maxDay = maxDay;
        this.maxMonth = maxMonth;
        this.maxYear = maxYear;
    }

    public void setMinDate(int minYear, int minMonth, int minDay) {
        this.minDay = minDay;
        this.minMonth = minMonth;
        this.minYear = minYear;
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        super.onDateChanged(view, year, month, day);
        if ((minYear > year)
                || ((minMonth > month) && (minYear == year))
                || ((minDay > day) && (minYear == year) && (minMonth == month))) {
                view.updateDate(minYear, minMonth, minDay);
        }

        //不设置最大时间
        if (maxYear!=-1&&(maxYear < year)
                || ((maxMonth < month) && (maxYear == year))
                || ((maxDay < day) && (maxYear == year) && (maxMonth == month))) {
            view.updateDate(maxYear, maxMonth, maxDay);

        }
    }
}
