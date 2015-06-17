package com.unit.common.db;

import com.google.gson.annotations.SerializedName;

/**
 * Created by longbin on 15/1/17.
 */
public class JZCommonJsonBean {

    @SerializedName("code")
    private int code;

    @SerializedName("msg")
    private String msg;

    @SerializedName("response")
    private String response;

    @SerializedName("ts")
    private long ts;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public boolean isCodeValueOne() {
        return code == 1;
    }

    @Override
    public String toString() {
        return "CommonJsonBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", response='" + response + '\'' +
                ", ts=" + ts +
                '}';
    }
}
