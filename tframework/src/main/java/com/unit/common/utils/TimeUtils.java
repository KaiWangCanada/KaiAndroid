package com.unit.common.utils;

import android.content.Context;

import com.unit.common.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xiaozhou on 2014/8/18.
 */
public class TimeUtils {

    public static final String HH_MM = "HH:mm";
    public static final String MM_SS = "mm:ss";
    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String MM_DD = "MM-dd";

    /**
     * 获取当前时间和指定时间的距离
     *
     * @param current   当前时间（精确到毫秒）
     * @param time      指定时间（精确到毫秒）
     * @param isShowDay 是否要显示即时时间
     * @return
     */
    public static String getTime(long current, long time, boolean isShowDay) {

        if (!isShowDay) {
            return getTimeToMinute(time);
        }

        long a = ((current - time) / 60) / 1000;
        if (a <= 3) {
            return "刚刚";
        } else if (a < 60) {
            return a + "分钟前";
        } else if (a < 60 * 24) {
            return (int) a / 60 + "小时前";
        } else if (a < 60 * 24 * 7) {
            return (int) (a / (60 * 24)) + "天前";
        } else {
            return getTimeToDay(time);
        }
//        else if (a < 60 * 24 * 30) {
//            return (int) (a / (60 * 24 * 7)) + "周前";
//        } else if (a < 60 * 24 * 30 * 12) {
//            return (int) (a / (60 * 24 * 30)) + "个月前";
//        } else {
//            return (int) (a / (60 * 24 * 30 * 12)) + "年前";
//        }
    }
    /**
     * 获取当前时间和指定时间的距离
     *
     * @param current   当前时间（精确到毫秒）
     * @param time      指定时间（精确到毫秒）
     * @param isShowDay 是否要显示即时时间
     * @return
     */
    public static String getTimeInternational(Context context, long current, long time, boolean isShowDay) {

        if (!isShowDay) {
            return getTimeToMinute(time);
        }

        long a = ((current - time) / 60) / 1000;
        if (a <= 3) {
            return context.getString(R.string.ruis_a_moment_ago);
        } else if (a < 60) {
            return a + context.getString(R.string.ruis_minutes_ago);
        } else if (a < 60 * 24) {
            int hour =(int) a / 60;
            if (hour == 1) {
                return context.getString(R.string.ruis_a_day_ago);
            }
            return hour + context.getString(R.string.ruis_days_ago);
        } else if (a < 60 * 24 * 7) {
            int day = (int) (a / (60 * 24));
            if (day == 1) {
                return context.getString(R.string.ruis_a_day_ago);
            }
            return day + context.getString(R.string.ruis_days_ago);
        } else {
            return getTimeToDay(time);
        }
//        else if (a < 60 * 24 * 30) {
//            return (int) (a / (60 * 24 * 7)) + "周前";
//        } else if (a < 60 * 24 * 30 * 12) {
//            return (int) (a / (60 * 24 * 30)) + "个月前";
//        } else {
//            return (int) (a / (60 * 24 * 30 * 12)) + "年前";
//        }
    }

    public static String getTimeEfficiency(long time) {
        long min = time / 60;
        if (min <= 0) {
            return time + " 秒";
        } else if (min < 60) {
            return min + " 分钟";
        } else if (min < 60 * 24) {
            return (int) min / 60 + " 小时";
        } else if (min < 60 * 24 * 7) {
            return (int) (min / (60 * 24)) + " 天";
        } else if (min < 60 * 24 * 30) {
            return (int) (min / (60 * 24 * 7)) + " 周";
        } else {
            return (int) (min / (60 * 24 * 30)) + " 个月";
        }
    }


    /**
     * 取得指定年月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        return new SimpleDateFormat(YYYY_MM_DD).format(cal.getTime());
    }

    public static String getTimeToDay(long time) {
        if (time < 0) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat();
        String pattern = YYYY_MM_DD;
        sdf.applyPattern(pattern);
        return sdf.format(time);
    }

    public static String getTimeToMinute(long time) {
        if (time < 0) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat();
        String pattern = YYYY_MM_DD_HH_MM;
        sdf.applyPattern(pattern);
        return sdf.format(time);
    }

    /**
     * 取得制定年月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
        return new SimpleDateFormat(YYYY_MM_DD).format(cal.getTime());
    }

    /**
     * 取得个月的date
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getFirstDateFromYearMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
        return cal.getTime();
    }

    public static Date getLastDateFromYearMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
        return cal.getTime();
    }

    /**
     * 取得个月的date
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getDateFromYearMonthDay(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    /**
     * 获取时间段
     *
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param patternBegin
     * @param patternEnd
     * @return
     */
    public static String getPeriod(long beginTime, long endTime, String patternBegin, String patternEnd) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(patternBegin);
        Date date = new Date(beginTime);
        String scheduleTime = sdf.format(date);
        sdf.applyPattern(patternEnd);
        date.setTime(endTime);
        scheduleTime += "-" + sdf.format(date);
        return scheduleTime;
    }

    /**
     * 转换一下时间格式
     *
     * @param millis  毫秒级的时间戳
     * @param pattern 转换的格式
     */
    public static String formatTime(long millis, String pattern) {
        Date date = new Date(millis);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }
}
