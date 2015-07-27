package com.mmfcommon.bean;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by longbin on 15/3/30.
 */
public class SalonBaseBean implements Serializable {
    private static final long serialVersionUID = -8595943845826788970L;


    @SerializedName("salon_id")
    private String salonId;

    @SerializedName("salon_name")
    private String salonName;

    @SerializedName("address")
    private String address;

    @SerializedName("lng")
    private String lng;
    @SerializedName("lat")
    private String lat;

    @SerializedName("telephone")
    private String telephone;

    @SerializedName("open_time")
    private String openTime;

    @SerializedName("close_time")
    private String closeTime;

    public String getSalonName() {
        return salonName;
    }

    public void setSalonName(String salonName) {
        this.salonName = salonName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLng() {
        return lng;
    }
    public double getLngDouble() {
        return Double.parseDouble(lng);
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }
    public double getLatDouble() {
        return Double.parseDouble(lat);
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getSalonId() {
        return salonId;
    }

    public void setSalonId(String salonId) {
        this.salonId = salonId;
    }


    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

}
