package com.unit.common.httputils;

import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import java.io.File;

/**
 * http请求Demo
 * Created by kingkong on 15/1/11.
 */
public class HttpRequstDemo {

    /**
     * http下载demo
     */
    public void TestHttpDemo() {
        HttpRequestBaseInfo httpRequestBaseInfo = new HttpRequestBaseInfo();
        httpRequestBaseInfo.addBodyParameter("key", "value");

        httpRequestBaseInfo.addBodyParameter("key", new File("path", "name"));
        JZHttpUtils.getInstance().send(
                HttpMethod.GET,
                "url",
                httpRequestBaseInfo,
                new HttpRequestCallback<String>(new HttpRequestCallbackHandler() {
                    @Override
                    public void onSuccess(String content) {
                        //请求成功content 为服务器返回的数据
                        super.onSuccess(content);
                    }

                    @Override
                    public void onFailure(String content) {
                        //请求失败content 为服务器返回的数据
                        super.onFailure(content);
                    }
                }));
    }


    /**
     * 下载文件异步方法,支持断点续传，随时停止下载任务，开始任务
     *
     * @return
     */
    public HttpHandler downloadFilesDemo() {

        HttpHandler handler;

        //可以调用
//        handler.pause(); //暂停下载
//        handler.cancel();//取消下载
        return handler = JZHttpUtils.getInstance().download(
                "fileUrl", //文件下载地址
                "filePath",//文件存储到sd卡的路径
                true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
                true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
                new HttpDownloadCallback());
    }
}
