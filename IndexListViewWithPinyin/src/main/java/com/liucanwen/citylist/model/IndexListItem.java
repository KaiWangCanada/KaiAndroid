package com.liucanwen.citylist.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.liucanwen.citylist.widget.ContactItemInterface;

public class IndexListItem implements ContactItemInterface, Parcelable {

    public static final long SerializableID = 1l;

    private String nickName;
    private String indexName;
    private String itemId;

    public IndexListItem(String nickName, String indexName) {
        super();
        this.nickName = nickName;
        this.setItemId(indexName);
    }

    public IndexListItem() {
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemId() {

        return itemId;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getIndexName() {

        return indexName;
    }

    @Override
    public String getDisplayInfo() {
        return nickName;
    }

    @Override
    public String getItemForIndex() {
        return indexName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    //内容描述接口，基本不用管
    @Override
    public int describeContents() {
        return 0;
    }

    //写入接口函数，打包
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // 1.必须按成员变量声明的顺序封装数据，不然会出现获取数据出错
        // 2.序列化对象
        dest.writeString(indexName);
        dest.writeString(nickName);
        dest.writeString(itemId);
    }

    //读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。因为实现类在这里还是不可知的，所以需要用到模板的方式，继承类名通过模板参数传入。
    //为了能够实现模板参数的传入，这里定义Creator嵌入接口,内含两个接口函数分别返回单个和多个继承类实例。
    // 1.必须实现Parcelable.Creator接口,否则在获取Person数据的时候，会报错，如下：
    // android.os.BadParcelableException:
    // Parcelable protocol requires a Parcelable.Creator object called  CREATOR on class com.um.demo.Person
    // 2.这个接口实现了从Percel容器读取Person数据，并返回Person对象给逻辑层使用
    // 3.实现Parcelable.Creator接口对象名必须为CREATOR，不如同样会报错上面所提到的错；
    // 4.在读取Parcel容器里的数据事，必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
    // 5.反序列化对象
    public static final Parcelable.Creator<IndexListItem> CREATOR = new Creator() {

        @Override
        public IndexListItem createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            // 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
            IndexListItem p = new IndexListItem();
            p.setIndexName(source.readString());
            p.setNickName(source.readString());
            p.setItemId(source.readString());
            return p;
        }

        @Override
        public IndexListItem[] newArray(int size) {
            // TODO Auto-generated method stub
            return new IndexListItem[size];
        }
    };


}
