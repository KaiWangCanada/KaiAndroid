package com.unit.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kingkong on 14/11/13.
 */
public class VerifyPhoneEmailZip {

//    要更加准确的匹配手机号码只匹配11位数字是不够的，比如说就没有以144开始的号码段，
//
//    故先要整清楚现在已经开放了多少个号码段，国家号码段分配如下：
//
//    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
//
//    联通：130、131、132、152、155、156、185、186
//
//    电信：133、153、180、189、（1349卫通）
//    那么现在就可以正则匹配测试了，

    public static boolean isMobileNO(String mobiles) {
        if (mobiles.length() != 11) {
            return false;
        }

        Pattern p = Pattern.compile("^(1[0-9])\\d{9}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();

    }


    /**
     * 判断邮编
     *
     * @param zipString
     * @return
     */
    public static boolean isZipNO(String zipString) {
        String str = "^[1-9][0-9]{5}$";
        return Pattern.compile(str).matcher(zipString).matches();
    }


    /**
     * 判断邮箱是否合法
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
