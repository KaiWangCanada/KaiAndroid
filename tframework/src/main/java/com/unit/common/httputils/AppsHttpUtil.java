package com.unit.common.httputils;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.RequestParams.HeaderItem;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.unit.common.utils.LogOutputUtils;

import org.apache.http.NameValuePair;

/**
 * http请求相关类,独立于第三方类库,以便自己的框架增删功能
 * Created by kingkong on 14-3-27.
 */
public class AppsHttpUtil extends HttpUtils {

    public static boolean isShowLog = true;  //是否打印请求
    private static String TAG = AppsHttpUtil.class.getSimpleName();
    private static final int cacheExpiry = 3000;//3秒缓存

    public AppsHttpUtil() {
        configDefaultHttpCacheExpiry(cacheExpiry);
    }
//    }

    // ***************************************** send request *******************************************

    public <T> HttpHandler<T> HttpGetRequest(String url,
                                             HttpRequestCallback<T> callBack) {
        showLog(HttpMethod.GET.toString(), url);
        return super.send(HttpMethod.GET, url, null, callBack);
    }

    public <T> HttpHandler<T> HttpPostRequest(String url,
                                              HttpRequestCallback<T> callBack) {
        showLog(HttpMethod.POST.toString(), url);
        return super.send(HttpMethod.POST, url, null, callBack);
    }

    public <T> HttpHandler<T> HttpGetRequest(String url, HttpRequestBaseInfo params,
                                             HttpRequestCallback<T> callBack) {
        showLog(HttpMethod.GET.toString(), url, params);
        return super.send(HttpMethod.GET, url, params, callBack);
    }

    public <T> HttpHandler<T> HttpPostRequest(String url, HttpRequestBaseInfo params,
                                              HttpRequestCallback<T> callBack) {
        showLog(HttpMethod.POST.toString(), url, params);
        return super.send(HttpMethod.POST, url, params, callBack);
    }

    public ResponseStream HttpGetRequestSync(String url) throws HttpException {
        showLog(HttpMethod.GET.toString(), url);
        return super.sendSync(HttpMethod.GET, url, null);
    }

    public ResponseStream HttpPostRequestSync(String url) throws HttpException {
        showLog(HttpMethod.POST.toString(), url);
        return super.sendSync(HttpMethod.POST, url, null);
    }

    public ResponseStream HttpGetRequestSync(String url, HttpRequestBaseInfo params) throws HttpException {
        showLog(HttpMethod.GET.toString(), url, params);
        return super.sendSync(HttpMethod.GET, url, params);
    }

    public ResponseStream HttpPostRequestSync(String url, HttpRequestBaseInfo params) throws HttpException {
        showLog(HttpMethod.POST.toString(), url, params);
        return super.sendSync(HttpMethod.POST, url, params);
    }
    // ***************************************** send request *******************************************


    void showLog(String httpType, String url, RequestParams params) {
        if (isShowLog) {
            LogOutputUtils.d(AppsHttpUtil.class.getSimpleName().toString(), "httpType ->" + httpType + "| url ->" + url);
            if (params != null && params.getBodyParams() != null) {
                for (NameValuePair nameValuePair : params.getBodyParams()) {
                    LogOutputUtils.d(AppsHttpUtil.class.getSimpleName().toString(), "BodyParams->" + nameValuePair.getName() + ": " + nameValuePair.getValue());
                }
            }

            if (params != null && params.getQueryStringParams() != null) {
                for (NameValuePair nameValuePair : params.getQueryStringParams()) {
                    LogOutputUtils.d(AppsHttpUtil.class.getSimpleName().toString(), "QueryStringParams->" + nameValuePair.getName() + ": " + nameValuePair.getValue());
                }
            }

            if (params != null && params.getHeaders() != null) {
                for (HeaderItem nameValuePair : params.getHeaders()) {
                    LogOutputUtils.d(AppsHttpUtil.class.getSimpleName().toString(), "headItem->" + nameValuePair.header.getName() + ": " + nameValuePair.header.getValue());
                }
            }
        }
    }

    void showLog(String httpType, String url) {
        if (isShowLog) {
            LogOutputUtils.d(AppsHttpUtil.class.getSimpleName().toString(), "httpType -> " + httpType + ":" + "request url ->" + url);
        }
    }
}
