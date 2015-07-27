package com.mmfcommon.bean;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by longbin on 15/1/22.
 */
public class StyleBean implements Serializable, Parcelable, TagBeanBase {

    private static final long serialVersionUID = 5958424592477621963L;

    @SerializedName("tag_id")
    private String styleId;

    //@SerializedName("style_name")

    @SerializedName("tag_name")
    private String styleName;

    @SerializedName("avatar_path")
    private String styleAvatar;

    boolean isSelect = false;//是否选择了该发型,只是选择全部分类使用到

//    private String BigTypeName;//大类别的名称,只是选择全部分类使用到
//
//    public void setBigTypeName(String bigTypeName) {
//        BigTypeName = bigTypeName;
//    }
//
//    public String getBigTypeNames() {
//
//        return BigTypeName;
//    }

    public StyleBean(WorkEntity.TagsEntity tagsEntity) {
        this.styleId = tagsEntity.getTagId();
        this.styleName = tagsEntity.getTagName();
        this.styleAvatar = "";
        this.isSelect = true;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean isSelect) {

        this.isSelect = isSelect;
    }

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getStyleAvatar() {
        return styleAvatar;
    }

    public void setStyleAvatar(String styleAvatar) {
        this.styleAvatar = styleAvatar;
    }

    public static long getSerialVersionUID() {

        return serialVersionUID;
    }

    public StyleBean(Parcel in) {
        this.styleId = in.readString();
        this.styleName = in.readString();
        this.styleAvatar = in.readString();
        this.isSelect = in.readByte() != 0;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.styleId);
        dest.writeString(this.styleName);
        dest.writeString(this.styleAvatar);
        dest.writeByte((byte) (this.isSelect ? 1 : 0));
    }

    public static final Parcelable.Creator<StyleBean> CREATOR = new Parcelable.Creator<StyleBean>() {

        public StyleBean createFromParcel(Parcel in) {
            return new StyleBean(in);
        }

        public StyleBean[] newArray(int size) {
            return new StyleBean[size];
        }
    };

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StyleBean styleBean = (StyleBean) o;

        return !(styleId != null ? !styleId.equals(styleBean.styleId) : styleBean.styleId != null);
    }

    @Override public int hashCode() {
        return styleId != null ? styleId.hashCode() : 0;
    }

    @Override public String getTagName() {
        return styleName;
    }

    @Override public String getTagId() {
        return styleId;
    }
}
