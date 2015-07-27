package com.mmfcommon.bean;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 黄东鲁 on 15/5/29.
 */
public class WorkEntity implements Parcelable {

  /**
   * tags : [{"tag_name":"Long","tag_id":"3"},{"tag_name":"Afro","tag_id":"25"}]
   * favourites : 1
   * cover : https://dn-mmfstatic.qbox.me/2015/04/24/19443668379301181.jpg
   * stylist_works_name : Showcase
   * created_at : 1429808478
   * stylist_works_desc : Women's hair style
   * stylist_works_id : 3
   * comments : 0
   * stylist_id : 38
   */
  @SerializedName("tags")
  private ArrayList<TagsEntity> tags = new ArrayList<>();

  @SerializedName("favourites")
  private String favourites;

  @SerializedName("cover")
  private String cover;

  @SerializedName("stylist_works_name")
  private String stylist_works_name;

  @SerializedName("created_at")
  private String created_at;

  @SerializedName("stylist_works_desc")
  private String stylist_works_desc;

  @SerializedName("stylist_works_id")
  private String stylist_works_id;

  @SerializedName("comments")
  private String comments;

  @SerializedName("stylist_id")
  private String stylist_id;

  public void setTags(ArrayList<TagsEntity> tags) {
    this.tags = tags;
  }

  public void setFavourites(String favourites) {
    this.favourites = favourites;
  }

  public void setCover(String cover) {
    this.cover = cover;
  }

  public void setStylist_works_name(String stylist_works_name) {
    this.stylist_works_name = stylist_works_name;
  }

  public void setCreated_at(String created_at) {
    this.created_at = created_at;
  }

  public void setStylist_works_desc(String stylist_works_desc) {
    this.stylist_works_desc = stylist_works_desc;
  }

  public void setStylist_works_id(String stylist_works_id) {
    this.stylist_works_id = stylist_works_id;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public void setStylist_id(String stylist_id) {
    this.stylist_id = stylist_id;
  }

  public ArrayList<TagsEntity> getTags() {
    return tags;
  }

  public String getFavourites() {
    return favourites;
  }

  public String getCover() {
    return cover;
  }

  public String getStylist_works_name() {
    return stylist_works_name;
  }

  public String getCreated_at() {
    return created_at;
  }

  public String getStylist_works_desc() {
    return stylist_works_desc;
  }

  public String getStylist_works_id() {
    return stylist_works_id;
  }

  public String getComments() {
    return comments;
  }

  public String getStylist_id() {
    return stylist_id;
  }

  public static class TagsEntity implements Parcelable, TagBeanBase {

    @SerializedName("tag_name")
    private String tag_name;

    @SerializedName("tag_id")
    private String tag_id;

    public void setTag_name(String tag_name) {
      this.tag_name = tag_name;
    }

    public void setTag_id(String tag_id) {
      this.tag_id = tag_id;
    }

    public String getTag_name() {
      return tag_name;
    }

    @Override public int describeContents() {
      return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(this.tag_name);
      dest.writeString(this.tag_id);
    }

    public TagsEntity() {
    }

    private TagsEntity(Parcel in) {
      this.tag_name = in.readString();
      this.tag_id = in.readString();
    }

    public static final Creator<TagsEntity> CREATOR = new Creator<TagsEntity>() {
      public TagsEntity createFromParcel(Parcel source) {
        return new TagsEntity(source);
      }

      public TagsEntity[] newArray(int size) {
        return new TagsEntity[size];
      }
    };

    @Override public String getTagName() {
      return tag_name;
    }

    @Override public String getTagId() {
      return tag_id;
    }
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeTypedList(tags);
    dest.writeString(this.favourites);
    dest.writeString(this.cover);
    dest.writeString(this.stylist_works_name);
    dest.writeString(this.created_at);
    dest.writeString(this.stylist_works_desc);
    dest.writeString(this.stylist_works_id);
    dest.writeString(this.comments);
    dest.writeString(this.stylist_id);
  }

  public WorkEntity() {
  }

  private WorkEntity(Parcel in) {
    in.readTypedList(tags, TagsEntity.CREATOR);
    this.favourites = in.readString();
    this.cover = in.readString();
    this.stylist_works_name = in.readString();
    this.created_at = in.readString();
    this.stylist_works_desc = in.readString();
    this.stylist_works_id = in.readString();
    this.comments = in.readString();
    this.stylist_id = in.readString();
  }

  public static final Creator<WorkEntity> CREATOR = new Creator<WorkEntity>() {
    public WorkEntity createFromParcel(Parcel source) {
      return new WorkEntity(source);
    }

    public WorkEntity[] newArray(int size) {
      return new WorkEntity[size];
    }
  };

  @SerializedName("items")
  private List<ItemsEntity> items = new ArrayList<>();


  public static class ItemsEntity implements Parcelable {
    /**
     * favourites : 0
     * updated_at : 1433401783
     * is_cover : 1
     * stylist_works_item_id : 32
     * picture_url : https://dn-public-ssl.qbox.me/mmf/2015/06/04/22986583166997024.jpg
     * status : 1
     * picture_name :
     * stylist_works_id : 23
     * comments : 0
     * picture_description : null
     */
    @SerializedName("favourites") private String favourites;
    @SerializedName("updated_at") private String updated_at;
    @SerializedName("is_cover") private String is_cover;
    @SerializedName("stylist_works_item_id") private String stylist_works_item_id;
    @SerializedName("picture_url") private String picture_url;
    @SerializedName("status") private String status;
    @SerializedName("picture_name") private String picture_name;
    @SerializedName("stylist_works_id") private String stylist_works_id;
    @SerializedName("comments") private String comments;
    @SerializedName("picture_description") private String picture_description;

    public void setFavourites(String favourites) {
      this.favourites = favourites;
    }

    public void setUpdated_at(String updated_at) {
      this.updated_at = updated_at;
    }

    public void setIs_cover(String is_cover) {
      this.is_cover = is_cover;
    }

    public void setStylist_works_item_id(String stylist_works_item_id) {
      this.stylist_works_item_id = stylist_works_item_id;
    }

    public void setPicture_url(String picture_url) {
      this.picture_url = picture_url;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public void setPicture_name(String picture_name) {
      this.picture_name = picture_name;
    }

    public void setStylist_works_id(String stylist_works_id) {
      this.stylist_works_id = stylist_works_id;
    }

    public void setComments(String comments) {
      this.comments = comments;
    }

    public void setPicture_description(String picture_description) {
      this.picture_description = picture_description;
    }

    public String getFavourites() {
      return favourites;
    }

    public String getUpdated_at() {
      return updated_at;
    }

    public String getIs_cover() {
      return is_cover;
    }

    public String getStylist_works_item_id() {
      return stylist_works_item_id;
    }

    public String getPicture_url() {
      return picture_url;
    }

    public String getStatus() {
      return status;
    }

    public String getPicture_name() {
      return picture_name;
    }

    public String getStylist_works_id() {
      return stylist_works_id;
    }

    public String getComments() {
      return comments;
    }

    public String getPicture_description() {
      return picture_description;
    }

    @Override public int describeContents() {
      return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(this.favourites);
      dest.writeString(this.updated_at);
      dest.writeString(this.is_cover);
      dest.writeString(this.stylist_works_item_id);
      dest.writeString(this.picture_url);
      dest.writeString(this.status);
      dest.writeString(this.picture_name);
      dest.writeString(this.stylist_works_id);
      dest.writeString(this.comments);
      dest.writeString(this.picture_description);
    }

    public ItemsEntity() {
    }

    private ItemsEntity(Parcel in) {
      this.favourites = in.readString();
      this.updated_at = in.readString();
      this.is_cover = in.readString();
      this.stylist_works_item_id = in.readString();
      this.picture_url = in.readString();
      this.status = in.readString();
      this.picture_name = in.readString();
      this.stylist_works_id = in.readString();
      this.comments = in.readString();
      this.picture_description = in.readString();
    }

    public static final Creator<ItemsEntity> CREATOR = new Creator<ItemsEntity>() {
      public ItemsEntity createFromParcel(Parcel source) {
        return new ItemsEntity(source);
      }

      public ItemsEntity[] newArray(int size) {
        return new ItemsEntity[size];
      }
    };
  }

  public List<ItemsEntity> getItems() {
    return items;
  }

  public void setItems(List<ItemsEntity> items) {
    this.items = items;
  }
}
