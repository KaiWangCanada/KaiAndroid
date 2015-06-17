package com.unit.common.utils;

import android.text.TextUtils;

/**
 * Created by kingkong on 15/4/21.
 */
public class StringUtils {

    /**
     * 判断两个字符串是否互相包含
     * @param data1
     * @param data2
     * @return
     */
    public static boolean isContain(String data1, String data2) {

        if (TextUtils.isEmpty(data1) || TextUtils.isEmpty(data2)) {
            return true;
        }

        boolean isContain = false;
        if (data1.contains(data2) || data2.contains(data1)) {
            isContain = true;
        }
        return isContain;
    }

}
