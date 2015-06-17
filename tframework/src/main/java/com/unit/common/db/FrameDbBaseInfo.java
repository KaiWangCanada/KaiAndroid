package com.unit.common.db;

import com.lidroid.xutils.db.annotation.*;

/**保存到数据库的bean要继承此类
 * Created by kingkong on 13-9-4.
 */
public class FrameDbBaseInfo {

    @Id
    int framwDbId;         //使用xutils框架必须要用id

    public void setFramwDbId (int framwDbId) {
        this.framwDbId = framwDbId;
    }

    public int getFramwDbId () {
        return framwDbId;
    }
}
