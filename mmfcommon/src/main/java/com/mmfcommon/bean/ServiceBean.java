package com.mmfcommon.bean;

import android.text.TextUtils;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 *
 * Created by 黄东鲁 on 15/1/24.
 * (Update by 龙彬 on 15/3/30.) commit: 提取公共部分
 */
public class ServiceBean extends ServiceBaseBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("min_price")
    private String min_price = "0";
    @SerializedName("avatar_path")
    private String avatarPath;

    @SerializedName("max_store_price")
    private String maxStorePrice = "0";
    @SerializedName("salon_service_name")
    private String salonServiceName;
    @SerializedName("salon_service_id")
    private String salon_service_id;
    @SerializedName("max_price")
    private String max_price = "0";

    public void setMin_price(String min_price) {
        this.min_price = min_price;
    }

    public void setMax_price(String max_price) {
        this.max_price = max_price;
    }

    public String getMin_price() {
        if (TextUtils.isEmpty(min_price)) {
            min_price = "0";
        }
        return min_price;
    }

    public String getMax_price() {
        if (TextUtils.isEmpty(max_price)) {
            max_price = "0";
        }
        return max_price;
    }

    public void setServicesId(String servicesId) {
        setServicesId(servicesId);
    }

    public void setSalon_service_id(String salon_service_id) {
        this.salon_service_id = salon_service_id;
    }


    public String getSalon_service_id() {
        return salon_service_id;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }


    public void setMaxStorePrice(String maxStorePrice) {
        this.maxStorePrice = maxStorePrice;
    }

    public String getMaxStorePrice() {
        if (TextUtils.isEmpty(maxStorePrice)) {
            maxStorePrice = "0";
        }
        return maxStorePrice;
    }

    public String getSalonServiceName() {
        return salonServiceName;
    }

    public void setSalonServiceName(String salonServiceName) {
        this.salonServiceName = salonServiceName;
    }
}
