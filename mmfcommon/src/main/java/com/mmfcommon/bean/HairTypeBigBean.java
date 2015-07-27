package com.mmfcommon.bean;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

/**
 * Created by kingkong on 15/1/14.
 */
public class HairTypeBigBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String bigTypeId;

    @SerializedName("tag_type_name")
    private String bigTypeName;

    @SerializedName("children")
    private List<StyleBean> smallBeanList;

    public List<StyleBean> getSmallBeanList() {
        return smallBeanList;
    }

    public void setSmallBeanList(List<StyleBean> smallBeanList) {
        this.smallBeanList = smallBeanList;
    }

    public void setBigTypeName(String bigTypeName) {
        this.bigTypeName = bigTypeName;
    }


    public void setBigTypeId(String bigTypeId) {
        this.bigTypeId = bigTypeId;
    }

    public static long getSerialVersionUID() {

        return serialVersionUID;
    }

    public String getBigTypeId() {
        return bigTypeId;
    }

    public String getBigTypeName() {
        return bigTypeName;
    }


}
