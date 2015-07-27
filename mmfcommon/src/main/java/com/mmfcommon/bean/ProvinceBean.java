package com.mmfcommon.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 区域-省
 *
 * Created by 黄东鲁 on 15/1/23.
 */
public class ProvinceBean {

    private String id;
    private String name;
    private String fullname;

    private List<CityBean> citys = new ArrayList<>();

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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public List<CityBean> getCitys() {
        return citys;
    }

    public void setCitys(List<CityBean> citys) {
        this.citys = citys;
    }
}
