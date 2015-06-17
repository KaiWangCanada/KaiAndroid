package com.unit.common.httputils;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseStream;

/**
 * Created by kingkong on 15/1/16.
 */
public class JZHttpUtils extends AppsHttpUtil {
    private static JZHttpUtils instance;


    public static JZHttpUtils getInstance() {

        if (instance == null) {
            instance = new JZHttpUtils();
        }
        return instance;
    }

    private JZHttpUtils() {

    }


    // ***************************************** send request *******************************************

    public <T> HttpHandler<T> HttpGetRequest(String url,
                                             HttpRequestCallback<T> callBack) {

        return super.HttpGetRequest(url, null, callBack);
    }

    public <T> HttpHandler<T> HttpPostRequest(String url,
                                              HttpRequestCallback<T> callBack) {

        return super.HttpPostRequest(url, null, callBack);
    }

    public <T> HttpHandler<T> HttpGetRequest(String url, HttpRequestBaseInfo params,
                                             HttpRequestCallback<T> callBack) {
        setCommonParams(params);
        return super.HttpGetRequest(url, params, callBack);
    }

    public <T> HttpHandler<T> HttpPostRequest(String url, HttpRequestBaseInfo params,
                                              HttpRequestCallback<T> callBack) {
        setCommonParams(params);
        return super.HttpPostRequest(url, params, callBack);
    }

    public ResponseStream HttpGetRequestSync(String url) throws HttpException {

        return super.HttpGetRequestSync(url, null);
    }

    public ResponseStream HttpPostRequestSync(String url) throws HttpException {

        return super.HttpPostRequestSync(url, null);
    }

    public ResponseStream HttpGetRequestSync(String url, HttpRequestBaseInfo params) throws HttpException {
        setCommonParams(params);
        return super.HttpGetRequestSync(url, params);
    }

    public ResponseStream HttpPostRequestSync(String url, HttpRequestBaseInfo params) throws HttpException {
        setCommonParams(params);
        return super.HttpPostRequestSync(url, params);
    }
    // ***************************************** send request *******************************************


    public void setCommonParams(HttpRequestBaseInfo params) {
//        if (null == params) {
//            return;
//        }
//
//        params.addBodyParameter(Key, AppKey);
//        params.addBodyParameter(Format, "array");
//        String api = getValueByKey(params, Api);
//        String ts = getTs();
//        params.addBodyParameter(Ts, ts);
//        String sign = getSign(api, ts, AppSecret);
//        params.addBodyParameter(Sign, sign);

    }
}
