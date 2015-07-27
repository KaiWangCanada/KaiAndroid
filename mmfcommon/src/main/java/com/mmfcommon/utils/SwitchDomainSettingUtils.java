package com.mmfcommon.utils;

import android.content.Context;
import android.text.TextUtils;
import com.mmfcommon.utils.storage.ACache;

/**
 * Created by 黄东鲁 on 15/7/1.
 */
public class SwitchDomainSettingUtils {


  public final static String KEY_SWITCH_DOMAIN = "SWITCH_DOMAIN";
  public final static String VALUE_SWITCH_DOMAIN_TEST = "SWITCH_DOMAIN_TEST";
  public final static String VALUE_SWITCH_DOMAIN_OFFICIAL = "SWITCH_DOMAIN_OFFICIAL";


  public static void switchToTestVersion(Context context) {
    ACache.get(context).put(KEY_SWITCH_DOMAIN, VALUE_SWITCH_DOMAIN_TEST);
  }


  public static void switchToOfficialVersion(Context context) {
    ACache.get(context).put(KEY_SWITCH_DOMAIN, VALUE_SWITCH_DOMAIN_OFFICIAL);
  }


  public static boolean isTestVersion(Context context) {
    String version = ACache.get(context).getAsString(KEY_SWITCH_DOMAIN);
    return !TextUtils.isEmpty(version) && version.equals(VALUE_SWITCH_DOMAIN_TEST);
  }

}
