package com.unit.common.httputils;

import com.lidroid.xutils.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.NameValuePair;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kingkong on 14-8-5.
 */
public class HttpRequestBaseInfo extends RequestParams {
    @Override
    public void addQueryStringParameter(String name, String value) {
        removeRepeatNameValuePair(getQueryStringParams(), name);
        super.addQueryStringParameter(name, value);
    }

    @Override
    public void addQueryStringParameter(NameValuePair nameValuePair) {
        removeRepeatNameValuePair(getQueryStringParams(), nameValuePair.getName());
        super.addQueryStringParameter(nameValuePair);
    }

    @Override
    public void addQueryStringParameter(List<NameValuePair> nameValuePairs) {
        for (NameValuePair temp : nameValuePairs) {
            removeRepeatNameValuePair(getQueryStringParams(), temp.getName());
        }
        super.addQueryStringParameter(nameValuePairs);
    }

    @Override
    public void addBodyParameter(String name, String value) {
        removeRepeatNameValuePair(getBodyParams(), name);
        super.addBodyParameter(name, value);
    }

    @Override
    public void addBodyParameter(NameValuePair nameValuePair) {
        removeRepeatNameValuePair(getBodyParams(), nameValuePair.getName());
        super.addBodyParameter(nameValuePair);
    }

    @Override
    public void addBodyParameter(List<NameValuePair> nameValuePairs) {
        for (NameValuePair temp : nameValuePairs) {
            removeRepeatNameValuePair(getBodyParams(), temp.getName());
        }
        super.addBodyParameter(nameValuePairs);
    }

    @Override
    public void addBodyParameter(String key, File file) {
        removeRepeatHashMap(getFileParams(), key);
        super.addBodyParameter(key, file);
    }

    @Override
    public void addBodyParameter(String key, File file, String mimeType) {
        removeRepeatHashMap(getFileParams(), key);
        super.addBodyParameter(key, file, mimeType);
    }

    @Override
    public void addBodyParameter(String key, File file, String mimeType, String charset) {
        removeRepeatHashMap(getFileParams(), key);
        super.addBodyParameter(key, file, mimeType, charset);
    }

    @Override
    public void addBodyParameter(String key, File file, String fileName, String mimeType, String charset) {
        removeRepeatHashMap(getFileParams(), key);
        super.addBodyParameter(key, file, fileName, mimeType, charset);
    }

    @Override
    public void addBodyParameter(String key, InputStream stream, long length) {
        removeRepeatHashMap(getFileParams(), key);
        super.addBodyParameter(key, stream, length);
    }

    @Override
    public void addBodyParameter(String key, InputStream stream, long length, String fileName) {
        removeRepeatHashMap(getFileParams(), key);
        super.addBodyParameter(key, stream, length, fileName);
    }

    @Override
    public void addBodyParameter(String key, InputStream stream, long length, String fileName, String mimeType) {
        removeRepeatHashMap(getFileParams(), key);
        super.addBodyParameter(key, stream, length, fileName, mimeType);
    }

    @Override
    public void addHeader(Header header) {
        removeRepeatHead(getHeaders(), header.getName());
        super.addHeader(header);
    }

    @Override
    public void addHeader(String name, String value) {
        removeRepeatHead(getHeaders(), name);
        super.addHeader(name, value);
    }

    @Override
    public void addHeaders(List<Header> headers) {
        for (Header temp : headers) {
            removeRepeatHead(getHeaders(), temp.getName());
        }
        super.addHeaders(headers);
    }

    public void removeRepeatNameValuePair(List<NameValuePair> list, String name) {

        if (list == null)
            return;
        try {

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        NameValuePair nameValuePair = isContianNameValuePair(list, name);
        if (name != null) {
            list.remove(nameValuePair);
        }

    }

    public NameValuePair isContianNameValuePair(List<NameValuePair> list, String name) {

        if (list == null)
            return null;

        NameValuePair nameValuePair = null;
        try {
            for (NameValuePair temp : list) {
                if (temp.getName().equals(name)) {
                    nameValuePair = temp;
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return nameValuePair;
    }

    public void removeRepeatHashMap(HashMap hashMap, String name) {

        if (hashMap == null)
            return;

        try {
            if (hashMap.containsKey(name)) {
                hashMap.remove(name);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void removeRepeatHead(List<HeaderItem> list, String name) {

        if (list == null)
            return;

        try {
            HeaderItem header = isContianHeader(list, name);
            if (name != null) {
                list.remove(header);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public HeaderItem isContianHeader(List<HeaderItem> list, String name) {

        if (list == null)
            return null;

        HeaderItem header = null;
        try {
            for (HeaderItem temp : list) {
                if (temp.header.getName().equals(name)) {
                    header = temp;
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return header;
    }

}

