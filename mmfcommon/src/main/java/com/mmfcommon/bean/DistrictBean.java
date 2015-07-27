package com.mmfcommon.bean;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 * 区域 —— 市
 *
 * Created by 黄东鲁 on 15/1/28.
 */
public class DistrictBean {

    @SerializedName("id")
    private String id = "";
    @SerializedName("name")
    private String name = "";
    @SerializedName("fullName")
    private String fullName = "";
    private List<StreetBean> streets = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<StreetBean> getStreets() {
        return streets;
    }

    public void setStreets(List<StreetBean> streets) {
        this.streets = streets;
    }
}
