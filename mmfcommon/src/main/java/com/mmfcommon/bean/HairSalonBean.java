package com.mmfcommon.bean;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

/**
 * 美发店实体类
 * <p/>
 * Created by 黄东鲁 on 15/1/9.
 */
public class HairSalonBean extends SalonBaseBean implements Serializable {


    @SerializedName("brand_id")
    private String brandId;


    private String province;
    private String city;
    private String district;
    private String town;


    @SerializedName("kilometers")
    private String kiloMeters = "";


    @SerializedName("stars")
    private String stars;



    @SerializedName("avatar_path")
    private String avatarPath;

    @SerializedName("stylists")
    private String stylistsCount;

    @SerializedName("stylist")
    private List<HairStylistBean> stylists;

    @SerializedName("profile")
    private String profile;

    @SerializedName("creation_date")
    private String createDate;

    @SerializedName("services")
    private List<ServiceBean> services;

    @SerializedName("business_hours")
    private String businessHours;


    @SerializedName("brand")
    private Brand brand;

    @SerializedName("is_favourite")
    private boolean isFavourite;

    @SerializedName("image_path")
    private String imagePath;

    @SerializedName("business_status")
    private String business_status;   //1:营业,休息

    private int price_interval; // 价格区间

    public String getBusiness_status() {
        return business_status;
    }

    public void setBusiness_status(String business_status) {
        this.business_status = business_status;
    }

    public void setFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    public boolean isFavourite() {

        return isFavourite;
    }

    public HairSalonBean(String salonName) {
        setSalonName(salonName);
    }



    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }



    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }



    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getStylistsCount() {
        return stylistsCount;
    }

    public void setStylistsCount(String stylistsCount) {
        this.stylistsCount = stylistsCount;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public List<ServiceBean> getServices() {
        return services;
    }

    public void setServices(List<ServiceBean> services) {
        this.services = services;
    }

    public String getKiloMeters() {
        return kiloMeters;
    }

    public void setKiloMeters(String kiloMeters) {
        this.kiloMeters = kiloMeters;
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours;
    }


    public class Brand implements Serializable {
        @SerializedName("img")
        private String img;
        @SerializedName("brand_id")
        private String brand_id;
        @SerializedName("name")
        private String name;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Brand{" +
                    "img='" + img + '\'' +
                    ", brand_id='" + brand_id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "HairSalonBean{" +
                "salonId=" + getSalonId() +
                ", brandId=" + brandId +
                ", salonName='" + getSalonName() + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", appDistrict='" + district + '\'' +
                ", town='" + town + '\'' +
                ", address='" + getAddress() + '\'' +
                ", kiloMeters='" + kiloMeters + '\'' +
                ", telephone='" + getTelephone() + '\'' +
                ", stars=" + stars +
                ", lng=" + getLng() +
                ", lat=" + getLat() +
                ", avatarPath='" + avatarPath + '\'' +
                ", stylistsCount=" + stylistsCount +
                ", profile='" + profile + '\'' +
                ", createDate='" + createDate + '\'' +
                ", services=" + services +
                ", businessHours='" + businessHours + '\'' +
                ", brand=" + brand +
                '}';
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<HairStylistBean> getStylists() {
        return stylists;
    }

    public void setStylists(List<HairStylistBean> stylists) {
        this.stylists = stylists;
    }

    public int getPrice_interval() {
        return price_interval;
    }

    public void setPrice_interval(int price_interval) {
        this.price_interval = price_interval;
    }
}
