package com.unit.common.db;

import com.lidroid.xutils.db.annotation.Id;

/**
 * Created by kingkong on 13-9-10.
 */
public class Parent {

    @Id(column = "parentId")
    int pid;

    int cid;

    public void setCid (int cid) {
        this.cid = cid;
    }

    public int getCid () {

        return cid;
    }

    public void setPid (int pid) {
        this.pid = pid;
    }

    public int getPid () {
        return pid;
    }

    public String toString () {
        return "parent->pid:" + pid + "|cid:" + cid;
    }

}