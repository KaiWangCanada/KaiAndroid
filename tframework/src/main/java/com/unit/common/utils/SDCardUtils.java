package com.unit.common.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by kingkong on 13-9-4.
 */
public class SDCardUtils {


    public static  String getSDPath () {
        File sdDir = null;
        try {
            boolean sdCardExist = Environment.getExternalStorageState()
                    .equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在
            if (sdCardExist) {
                sdDir = Environment.getExternalStorageDirectory();//获取跟目录
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return  null;
        }

        if(sdDir==null){
            return null;
        }
        return sdDir.toString();

    }
}
