package com.mmfcommon.bean;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by longbin on 15/1/14.
 */
public class UserInfoBean implements Serializable {

    private static final long serialVersionUID = 3771622156396627524L;
    @SerializedName("user_id")
    private int userId = -1;
    @SerializedName("password")
    private String password;
    @SerializedName("user_name")
    private String nick;
    @SerializedName("gender")
    private String gender;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("avatar_path")
    private String avatar;

    @SerializedName("handset")
    private String handset;

    @SerializedName("total")
    private int total;

//    @SerializedName("calendar")
//    private String calendar;

    @SerializedName("age")
    private String age;

    @SerializedName("area_code")
    private String areaCode;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return  avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHandset() {
        return handset;
    }

    public void setHandset(String handset) {
        this.handset = handset;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

//    public String getCalendar() {
//        return calendar;
//    }
//
//    public void setCalendar(String calendar) {
//        this.calendar = calendar;
//    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
