package com.mmfcommon.bean;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by longbin on 15/2/3.
 */
public class MessageBean implements Serializable {

    private static final long serialVersionUID = 1L;
    public static String Type_systems = "Type_system";
    public static String Type_Users = "Type_User";

    public static String Type_systems_value = "1";
    public static String Type_Users_value = "2";

    @SerializedName("appoint_menu_id")
    private String menuId;

    @SerializedName("type")
    private String type;               //"1:公共消息, 2:预约消息",

    @SerializedName("message_id")
    private String message_id;         // "消息id",

    @SerializedName("appoint_id")
    private String message_text_id;         // "消息id",

    @SerializedName("appoint_status")
    private String appoint_status;             // "如果是预约消息,需要显示订单的状态,0全部,1待确认,2已确认,3已取消,4已完成",

    @SerializedName("read_status")
    private String read_status;             // 这条消息是否已经阅读1,未读,2已读

    @SerializedName("title")
    private String title;

    @SerializedName("content")
    private String content;

    @SerializedName("url")
    private String url;                //"公共消息的url",

    @SerializedName("receive_user_id")
    private String user_id;            //"用户id,因为要知道是哪个用户的预约订单"

    @SerializedName("time")
    private String time;            //消息时间戳

  @SerializedName("shop_id")
  private String shopId;            //店铺id

  public void setShopId(String shopId) {
    this.shopId = shopId;
  }

  public String getShopId() {

    return shopId;
  }

  public void setAppoint_status(String appoint_status) {
        this.appoint_status = appoint_status;
    }

    public void setRead_status(String read_status) {
        this.read_status = read_status;
    }


    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getAppoint_status() {

        return appoint_status;
    }

    public String getRead_status() {
        return read_status;
    }

    public void setMessage_text_id(String message_text_id) {
        this.message_text_id = message_text_id;
    }

    public String getMessage_text_id() {

        return message_text_id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {

        return time;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public static long getSerialVersionUID() {

        return serialVersionUID;
    }

    public String getType() {
        return type;
    }

    public String getMessage_id() {
        return message_id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getMenuId() {
        return menuId;
    }

}
