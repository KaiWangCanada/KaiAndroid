package com.mmfcommon.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by longbin on 15/5/5.
 */
public class UpdateInfoBean {

    @SerializedName("versionCode")
    public int versionCode;
    @SerializedName("versionName")
    public String versionName;
    @SerializedName("versionSummary")
    public String versionSummary;
    @SerializedName("fileUrl")
    public String fileUrl;
    @SerializedName("needUpdate")
    public int needUpdate;//1为强制更新
}
