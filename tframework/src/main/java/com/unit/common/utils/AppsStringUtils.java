package com.unit.common.utils;

/**
 * Created by kingkong on 14/11/24.
 */
public class AppsStringUtils {

    //从url获取文件名称
    public static String GetNamePostfixFromUrl(String url) {
        String name = "";

        try {
            String[] temp = url.split("/");
            name = temp[temp.length - 1];
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return name;
    }


    /**
     * 从url获取名称,不包括后缀
     *
     * @param url
     * @return
     */
    public static String GetNameFromUrl(String url) {

        String name = "";

        try {
            String temp[] = GetNamePostfixFromUrl(url).split(".");
            return temp[0];
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return name;
    }

    //从url获取后缀
    public static String GetPostfixFromUrl(String url) {
        String name = "";

        try {
            String temp[] = GetNamePostfixFromUrl(url).split(".");
            return temp[1];
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return name;
    }
}
