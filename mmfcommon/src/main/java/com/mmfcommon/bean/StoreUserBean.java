package com.mmfcommon.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 黄东鲁 on 15/3/27.
 */
public class StoreUserBean {


    @SerializedName("clerk_id")  public String clerk_id = "";
    @SerializedName("handset")   public String handset = "";
    @SerializedName("salon_id")  public String salon_id = "";
    @SerializedName("stylist_id")    public String stylist_id = "";
    @SerializedName("clerk_name")    public String clerk_name = "";
    @SerializedName("clerk_type")    public String clerk_type = "";
    @SerializedName("creation_date")     public String creation_date = "";
    @SerializedName("avatar_path")   public String avatar_path = "";
    @SerializedName("created_at")    public String created_at = "";
    @SerializedName("create_user_id")    public String create_user_id = "";
    @SerializedName("updated_at")    public String updated_at = "";
    @SerializedName("update_user_id")    public String update_user_id = "";
    @SerializedName("status")    public String status = "";
    @SerializedName("area_code") public String areaCode;

}
