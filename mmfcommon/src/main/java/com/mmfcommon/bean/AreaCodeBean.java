package com.mmfcommon.bean;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

/**
 * Created by longbin on 15/4/30.
 */
public class AreaCodeBean implements Serializable {

    private static final long serialVersionUID = 8047105041130441297L;
    @SerializedName("area_code_list")
    private List<AreaCode> areaCodes;

    public List<AreaCode> getAreaCodes() {
        return areaCodes;
    }

    public void setAreaCodes(List<AreaCode> areaCodes) {
        this.areaCodes = areaCodes;
    }

    public static class AreaCode implements Serializable {

        private static final long serialVersionUID = 4810587553372641246L;

        @SerializedName("id")
        public String id;

        @SerializedName("area_code")
        public String areaCode;

        @SerializedName("area_name")
        public String areaName;

        @SerializedName("flag_path")
        public String flagPath;
    }
}
