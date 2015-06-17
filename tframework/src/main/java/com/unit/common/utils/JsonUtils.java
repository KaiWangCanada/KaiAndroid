package com.unit.common.utils;

/**
 * Created by kingkong on 13-9-12.
 */

import android.os.Debug;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedList;

/**
 * JsonUtils工具类：
 *
 * @author devin.hu
 * @description 采用Gson，google的一个开源项目,实现json对象的解析以及json对象跟java对象的互转
 */
public class JsonUtils {

    /**
     * 将java对象转换成json对象
     *
     * @param obj
     * @return
     */
    public static String parseObj2Json (Object obj) {
        if (obj == null) return null;
        Gson gson = new Gson();
        String objstr = gson.toJson(obj);
        if (Debug.isDebuggerConnected()) {
            Log.i("parseObj2Json", objstr);
        }
        return objstr;
    }


    /**
     * 将java对象的属性转换成json的key
     *
     * @param obj
     * @return
     */
    public static String parseObj2JsonOnField (Object obj) {
        if (obj == null) return null;
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES).create();
        String objstr = gson.toJson(obj);
        if (Debug.isDebuggerConnected()) {
            Log.i("parseObj2JsonOnField", objstr);
        }
        return objstr;
    }

    /**
     * 将json对象转换成java对象
     *
     * @param jsonData
     * @param c
     * @return
     */
    public static <T> T parseJson2Obj (String jsonData, Class<T> c) {
        if (jsonData == null) return null;
        Gson gson = new Gson();
        T obj = gson.fromJson(jsonData, c);
        if (Debug.isDebuggerConnected()) {
            Log.i("parseJson2Obj", obj.toString());
        }
        return obj;
    }

    public static <T> LinkedList<T> parseJson03 (String jsonData, Class<T> c) {
        Type listType = new TypeToken<LinkedList<T>>() {
        }.getType();
        Gson gson = new Gson();
        LinkedList<T> list = gson.fromJson(jsonData, listType);

//        for (Iterator iterator = users.iterator(); iterator.hasNext();) {
//            User user = (User) iterator.next();
//            System.out.println("name--->" + user.getName());
//            System.out.println("age---->" + user.getAge());
//        }
        if (Debug.isDebuggerConnected()) {
            Log.i("parseJson2Obj", list.toString());
        }
        return list;
    }
}