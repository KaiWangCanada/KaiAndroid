package com.unit.common.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by kingkong on 15/4/23.
 */
public class PhoneUtils {

    public static void CallNumber(Activity activity, String number) {
        Uri uri = Uri.parse("tel:" + number);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        activity.startActivity(intent);
    }

    public static void CallRecord(Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL_BUTTON);
        activity.startActivity(intent);
    }

}
