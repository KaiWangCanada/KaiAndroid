package com.mmfcommon.bean;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by longbin on 15/3/30.
 */
public class StylistBaseBean implements Serializable {
    private static final long serialVersionUID = 5278640663344569136L;

    public static String business_status_On = "1";
    public static String business_status_Off = "0";

    @SerializedName("stylist_id")
    private String stylistId;

    @SerializedName("stylist_name")
    private String stylistName = "";

    @SerializedName("gender")
    private String gender = ""; // 性别

    @SerializedName("avatar_path")
    private String avatar = ""; //头像

    @SerializedName("birthday")
    private String birthday = ""; // 生日

    @SerializedName("profile")
    private String profile = ""; // 简介


    @SerializedName("stars")
    private String stars = "0"; //评分


    @SerializedName("title_name")
    private String rank = ""; // 等级


    @SerializedName("background_image_path")
    private String backgroundImagePath;

    @SerializedName("age")
    private String age = "0";                //年龄


    public String getStylistName() {
        return stylistName;
    }

    public void setStylistName(String stylistName) {
        this.stylistName = stylistName;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStylistId() {
        return stylistId;
    }

    public void setStylistId(String stylistId) {
        this.stylistId = stylistId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAge() {
        return age;
    }

    public String getBackgroundImagePath() {
        return backgroundImagePath;
    }

    public void setBackgroundImagePath(String backgroundImagePath) {
        this.backgroundImagePath = backgroundImagePath;
    }
}
