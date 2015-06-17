package com.unit.common.httputils;

/**
 * Created by kingkong on 13-9-13.
 */
public class HttpResponseBaseInfo {
    public static final String  TAG="ResponseBaseInfo";

    //所有继承此类都要自定义一个新对象并设置属性名为 RESPONSE_INFO,
    //因为Gson和json字符串的key一一对应

    public String RESPONSE_STATUS;

    public String Tips;  //信息提示

}
