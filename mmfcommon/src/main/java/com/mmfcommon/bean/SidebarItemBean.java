package com.mmfcommon.bean;

import android.graphics.drawable.Drawable;

/**
 * 侧边栏item
 *
 * Created by 黄东鲁 on 15/3/30.
 */
public class SidebarItemBean {

    public int id;
    public String text;
    public Drawable drawableLeft;
    public boolean showRedPoint = false;

    public SidebarItemBean(int id, String text, Drawable drawableLeft) {
        this.id = id;
        this.text = text;
        this.drawableLeft = drawableLeft;
    }

    public SidebarItemBean(int id, String text) {
        this.id = id;
        this.text = text;
    }
    public SidebarItemBean(int id) {
        this.id = id;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SidebarItemBean that = (SidebarItemBean) o;

        return id == that.id;
    }

    @Override public int hashCode() {
        return id;
    }
}
