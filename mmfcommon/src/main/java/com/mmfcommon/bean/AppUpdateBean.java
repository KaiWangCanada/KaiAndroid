package com.mmfcommon.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by longbin on 15/3/16.
 */
public class AppUpdateBean {

    @SerializedName("app_package_name")
    private String packageName;
    @SerializedName("version_number")
    private int versionCode;
    @SerializedName("version_name")
    private String versionName;
    @SerializedName("update_content")
    private String content;
    @SerializedName("download_url")
    private String downloadUrl;
    @SerializedName("create_at")
    private String createDate;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
