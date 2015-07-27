package com.mmfcommon.bean;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

/**
 * 发型师实体类
 * <p/>
 * Created by 黄东鲁 on 15/1/10.
 */
public class HairStylistBean extends StylistBaseBean implements Serializable {
    private static final long serialVersionUID = 8193758667235743348L;

    @SerializedName("calendar")
    private String calendar = ""; // 公历或农历

    @SerializedName("services")
    private List<ServiceBean> services; // 可以服务的列表

    @SerializedName("all_services")
    private List<ServiceBean> allServices; // 可以服务的列表

    @SerializedName("rest_day")
    private String restDay = "";

    @SerializedName("kilometers")
    private String kiloMeters = "";

    @SerializedName("handset")
    private String telephone = "";

    @SerializedName("shop")
    private HairSalonBean salon;

    @SerializedName("is_favourite")
    private boolean isFavourite;

    private String maxPrice;
    private String minPrice;

    @SerializedName("business_status")
    private String business_status;   //1:营业,休息


    public void setBusiness_status(String business_status) {
        this.business_status = business_status;
    }

    public String getBusiness_status() {
        return business_status;
    }

    public void setFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public HairStylistBean(String stylistName) {
        setStylistName(stylistName);
    }

    public HairStylistBean() {
    }


    public String getCalendar() {
        return calendar;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
    }


    public String getRestDay() {
        return restDay;
    }

    public void setRestDay(String restDay) {
        this.restDay = restDay;
    }

    public String getKiloMeters() {
        return kiloMeters;
    }

    public void setKiloMeters(String kiloMeters) {
        this.kiloMeters = kiloMeters;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<ServiceBean> getServices() {
        return services;
    }

    public void setServices(List<ServiceBean> services) {
        this.services = services;
    }

    public HairSalonBean getSalon() {
        return salon;
    }

    public void setSalon(HairSalonBean salon) {
        this.salon = salon;
    }



    /*FIXME 测试*/
    public HairStylistBean stylistName(String name) {
        this.setStylistName(name);
        return this;
    }

    public HairStylistBean age(String age) {
        setAge(age);
        return this;
    }

    public HairStylistBean services(List<ServiceBean> services) {
        setServices(services);
        return this;
    }

    public HairStylistBean gender(String gender) {
        setGender(gender);
        return this;
    }

    public HairStylistBean rank(String rank) {
        setRank(rank);
        return this;
    }

    public List<ServiceBean> getAllServices() {
        return allServices;
    }

    public void setAllServices(List<ServiceBean> allServices) {
        this.allServices = allServices;
    }
}
