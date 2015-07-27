package com.mmfcommon.bean;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by longbin on 15/3/30.
 */
public class ServiceBaseBean implements Serializable {
    private static final long serialVersionUID = 1366118336134948157L;

    @SerializedName("service_id")
    private String servicesId;

    @SerializedName("service_name")
    private String serviceName = "";

    public String getServicesId() {
        return servicesId;
    }

    public void setServicesId(String servicesId) {
        this.servicesId = servicesId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

}
