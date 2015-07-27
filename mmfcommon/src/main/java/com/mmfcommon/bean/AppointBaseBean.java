package com.mmfcommon.bean;

import com.google.gson.annotations.SerializedName;
import com.unit.common.utils.LogOutputUtils;
import java.io.Serializable;

/**
 * Created by longbin on 15/3/30.
 */
public class AppointBaseBean implements Serializable {
    private static final long serialVersionUID = -7752161381375256589L;

    public static final String IS_NOT_EVALUATED = "0";
    public static final String IS_EVALUATED = "1";
    /**
     * 预约id
     */
    @SerializedName("appoint_id")
    private String appointId;

    @SerializedName("stylist_service_id")
    private String serverId;

    @SerializedName("stylist_id")
    private String stylistId;

    @SerializedName("salon_id")
    private String salonId;
    /**
     * 预约状态
     */
    @SerializedName("status")
    private String status;

    /**
     * 订单号
     */
    @SerializedName("order_code")
    private String orderCode = "";
    /**
     * 服务信息
     */
    @SerializedName("service")
    private ServiceBean serviceBean;
    /**
     * 预约时间
     */
    @SerializedName("appoint_time")
    private long appointTime;

    @SerializedName("creation_date")
    private long creation_date;


    @SerializedName("name")
    private String userName = "";
    @SerializedName("handset")
    private String handset = "";
    @SerializedName("is_evaluate")
    private String evaluationStatus;

    @SerializedName("comments")
    private String comments = "";

    @SerializedName("order_type")
    private int orderType;

    @SerializedName("seccode")
    private String seccode;

    @SerializedName("expire_payment_time")
    private long expireTime;


    public long getAppointTime() {
        return appointTime;
    }

    public void setAppointTime(long appointTime) {
        this.appointTime = appointTime;
    }

    public String getSalonId() {
        return salonId;
    }

    public void setSalonId(String salonId) {
        this.salonId = salonId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public ServiceBean getServiceBean() {
        return serviceBean;
    }

    public void setServiceBean(ServiceBean serviceBean) {
        this.serviceBean = serviceBean;
    }

    public String getStatus() {
        return status;
    }
    public int getStatusInt() {
        try {
            return Integer.parseInt(status);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            LogOutputUtils.e("http", e.getMessage());
            return 0;
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void setStatus(int status) {
        this.status = Integer.toString(status);
    }

    public String getAppointId() {
        return appointId;
    }

    public void setAppointId(String appointId) {
        this.appointId = appointId;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getStylistId() {
        return stylistId;
    }

    public void setStylistId(String stylistId) {
        this.stylistId = stylistId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHandset() {
        return handset;
    }

    public void setHandset(String handset) {
        this.handset = handset;
    }

    public String getEvaluationStatus() {
        return evaluationStatus;
    }

    public void setEvaluationStatus(String evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean isEvaluated() {
        return IS_EVALUATED.equals(evaluationStatus);
    }

    public void setCreation_date(long creation_date) {
        this.creation_date = creation_date;
    }

    public static long getSerialVersionUID() {

        return serialVersionUID;
    }

    public static String getIsNotEvaluated() {
        return IS_NOT_EVALUATED;
    }

    public static String getIsEvaluated() {
        return IS_EVALUATED;
    }

    public long getCreation_date() {
        return creation_date;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getSeccode() {
        return seccode;
    }

    public void setSeccode(String seccode) {
        this.seccode = seccode;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
