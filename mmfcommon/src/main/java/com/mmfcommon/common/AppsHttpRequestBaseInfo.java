package com.mmfcommon.common;

import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.mmfcommon.activity.MmfCommonAppBaseApplication;
import com.unit.common.httputils.HttpRequestBaseInfo;
import com.unit.common.utils.FrameMD5Util;
import com.unit.common.utils.LanguageUtils;
import com.unit.common.utils.LogOutputUtils;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * Created by kingkong on 15/3/3.
 */
public class AppsHttpRequestBaseInfo extends HttpRequestBaseInfo {
    //请求的签名串
    public static final String Sign = "sign";
    //请求的api
    public static final String Api = "api";
    //应用key
    public static final String Key = "key";
    //请求的时间戳
    public static final String Ts = "ts";
    //api请求的版本
    public static final String Version = "version";

    public static final String Format = "format";

    public static final String debug = "debug";

    public static String AppSecret;

    public static String AppKey;

    public static void setAppSecret(String appSecret) {
        AppSecret = appSecret;
    }

    public static void setAppKey(String appKey) {
        AppKey = appKey;
    }

    /**
     * 获取sign的值（经传web平台有一套appKey，appSecret认证的东西）
     *
     * @param appSecret 应用密钥
     * @param api       http请求的api方法
     * @param ts        时间戳
     * @return 认证签名信息的值（sign）
     */
    public static String getSign(String appSecret, String api, String ts) {
        return FrameMD5Util.getMD5String(appSecret + api + ts);
    }


    public static String getSongSenSign(String key, String appSecret, String api, String ts) {
        return FrameMD5Util.getMD5String("MeiMeiFa" + key + appSecret + api + ts);
    }

    private AppsHttpRequestBaseInfo() {

    }

    public static AppsHttpRequestBaseInfo GetSongSenRequestBaseInfo(String api, HttpMethod httpMethod) {
        AppsHttpRequestBaseInfo httpRequestBaseInfo = new AppsHttpRequestBaseInfo();

//        String api = getValueByKey(params, Api);
        String ts = getTs();

        String sign = getSongSenSign(AppKey, AppSecret, api, ts);

//        httpRequestBaseInfo.addHeader(Key, AppKey);
//        httpRequestBaseInfo.addHeader(Format, "array()");
//        httpRequestBaseInfo.addHeader(MmfCommonSetting.Http_Request_Key_tokens, MmfCommonAppBaseApplication.token);
//        httpRequestBaseInfo.addHeader(Ts, ts);
//        httpRequestBaseInfo.addHeader(Sign, sign);
//        httpRequestBaseInfo.addHeader(MmfCommonSetting.Http_Request_Key_lat, MmfCommonAppBaseApplication.lat + "");
//        httpRequestBaseInfo.addHeader(MmfCommonSetting.Http_Request_Key_lng, MmfCommonAppBaseApplication.lng + "");

        if (httpMethod == HttpMethod.GET) {
            httpRequestBaseInfo.addQueryStringParameter(Api, api);
            httpRequestBaseInfo.addQueryStringParameter(Key, AppKey);
            httpRequestBaseInfo.addQueryStringParameter(Format, "array()");
            httpRequestBaseInfo.addQueryStringParameter(MmfCommonSetting.Http_Request_Key_user_id, MmfCommonAppBaseApplication.userId + "");
            httpRequestBaseInfo.addQueryStringParameter(MmfCommonSetting.Http_Request_Key_tokens, MmfCommonAppBaseApplication.token);
            httpRequestBaseInfo.addQueryStringParameter(Ts, ts);
            httpRequestBaseInfo.addQueryStringParameter(Sign, sign);
            httpRequestBaseInfo.addQueryStringParameter(MmfCommonSetting.Http_Request_Key_lat, MmfCommonAppBaseApplication.lat + "");
            httpRequestBaseInfo.addQueryStringParameter(MmfCommonSetting.Http_Request_Key_lng, MmfCommonAppBaseApplication.lng + "");


        } else if (httpMethod == HttpMethod.POST) {
            httpRequestBaseInfo.addBodyParameter(Api, api);
            httpRequestBaseInfo.addBodyParameter(Key, AppKey);
            httpRequestBaseInfo.addBodyParameter(Format, "array()");
            httpRequestBaseInfo.addBodyParameter(MmfCommonSetting.Http_Request_Key_user_id, MmfCommonAppBaseApplication.userId + "");
            httpRequestBaseInfo.addBodyParameter(MmfCommonSetting.Http_Request_Key_tokens, MmfCommonAppBaseApplication.token);
            httpRequestBaseInfo.addBodyParameter(Ts, ts);
            httpRequestBaseInfo.addBodyParameter(Sign, sign);
            httpRequestBaseInfo.addBodyParameter(MmfCommonSetting.Http_Request_Key_lat, MmfCommonAppBaseApplication.lat + "");
            httpRequestBaseInfo.addBodyParameter(MmfCommonSetting.Http_Request_Key_lng, MmfCommonAppBaseApplication.lng + "");
        }

        return httpRequestBaseInfo;
    }


    public static AppsHttpRequestBaseInfo GetDewuRequestBaseInfo() {
        AppsHttpRequestBaseInfo httpRequestBaseInfo = new AppsHttpRequestBaseInfo();
        SetDewuRequestBaseInfoHeader(httpRequestBaseInfo);
        return httpRequestBaseInfo;
    }

    public static void SetDewuRequestBaseInfoHeader(AppsHttpRequestBaseInfo httpRequestBaseInfo) {
        httpRequestBaseInfo.addHeader(MmfCommonSetting.Host_Head_Key, AppKey);
        String ts = getTs();
        String sign = getSign(AppSecret, "", ts);
        httpRequestBaseInfo.addHeader(MmfCommonSetting.Host_Head_Sign, sign + "," + ts);
        if(!android.text.TextUtils.isEmpty(MmfCommonSetting.Language_Type)){
            httpRequestBaseInfo.addHeader(MmfCommonSetting.Host_Head_Language, MmfCommonSetting.Language_Type);
        }else {
            if (LanguageUtils.isChinese()) {
                httpRequestBaseInfo.addHeader(MmfCommonSetting.Host_Head_Language, MmfCommonSetting.ZH_CN_Language);
            }else {
                httpRequestBaseInfo.addHeader(MmfCommonSetting.Host_Head_Language, MmfCommonSetting.EN_Language);
            }
        }


//        header = new BasicHeader(Setting.Host_Head_Token, AppBaseApplication.token);
//        addHeader(header);

        LogOutputUtils.d(AppsHttpRequestBaseInfo.class.getSimpleName(),
//                        Setting.Host_Head_Token + "->" + AppBaseApplication.token + "\n" +
                MmfCommonSetting.Host_Head_Key + "-> " + AppKey + "\n " +
                        "AppSecret" + "-> " + AppSecret + "\n " +
                        MmfCommonSetting.Host_Head_Sign + "->" + sign + "," + ts);
    }

    public static String getValueByKey(HttpRequestBaseInfo params, String key) {
        if (null == params) {
            return null;
        }
        List<NameValuePair> bodyParams = params.getBodyParams();
        int size = null == bodyParams ? 0 : bodyParams.size();
        for (int i = 0; i < size; i++) {
            if (bodyParams.get(i).getName().equals(key)) {
                return bodyParams.get(i).getValue();
            }
        }
        return null;
    }

    /**
     * 获取时间戳
     *
     * @return
     */

    public static String getTs() {
        long time = System.currentTimeMillis();
        String ts = String.valueOf(time / 1000);
        return ts;
    }

    public void outputHead() {
        for (HeaderItem headerItem : getHeaders()) {
            LogOutputUtils.d(AppsHttpRequestBaseInfo.class.getSimpleName(), headerItem.header.getName() + ":" + headerItem.header.getValue());
        }
    }
}
