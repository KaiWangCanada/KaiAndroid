package com.mmfcommon.bean;

import java.util.List;

/**
 * 区域 —— 市
 *
 * Created by 黄东鲁 on 15/1/23.
 */
public class CityBean {

    private String id = "0";
    private String name = "";
    private String fullName = "";
    private List<DistrictBean> districts;

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

    public void setDistricts(List<DistrictBean> districts) {
        this.districts = districts;
    }

    public List<DistrictBean> getDistricts() {

        return districts;
    }
}
