package com.unit.common.db;

import com.lidroid.xutils.db.annotation.Foreign;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Transient;

/**
 * Created by kingkong on 13-9-10.
 */
public class Child {
    @Id(column = "childId")
    int cid;

    // Transient使这个列被忽略，不存入数据库
    @Transient
    public String willIgnore;

    @Foreign(column = "parentId", foreign = "cid")
    public Parent parent;

    public void setCid (int cid) {
        this.cid = cid;
    }

    public void setParent (Parent parent) {
        this.parent = parent;
    }

    public int getCid () {
        return cid;
    }

    public Parent getParent () {
        return parent;
    }

    public String toString () {
        return "child->cid:" + cid + "|" + parent.toString();
    }
}