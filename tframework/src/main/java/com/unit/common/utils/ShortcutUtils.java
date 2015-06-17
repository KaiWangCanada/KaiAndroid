package com.unit.common.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;

/**
 * app快捷方式的工具类
 * Created by longbin on 14-12-3.
 */
public class ShortcutUtils {

    /**
     * 添加快捷方式,
     * 需要权限 <p/>
     * <code>&lt;uses-permission android:name="com.android.launcher.permission.INSTALL_SHORTCUT" /&gt;</code> <br/>
     * <code>&lt;uses-permission android:name="com.android.launcher.permission.READ_SETTINGS" /&gt;</code>
     * @param context 上下文
     * @param intentClass 点击快捷方式的跳转的类
     * @param appName 快捷方式的名称
     * @param iconRes 快捷方式的图标资源id
     */
    public static void addShortcut(Context context, Class intentClass, String appName, int iconRes) {
        if (hasShortcut(context, appName)) {
            return;
        }

        Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        Intent shortcutIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());

        // 获取应用的名字
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, appName);
        // 不能重复创建，但是不一定有效
        intent.putExtra("duplicate", false);
        Intent.ShortcutIconResource parcelable = Intent.ShortcutIconResource.fromContext(context, iconRes);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, parcelable);

        shortcutIntent.setClass(context, intentClass);
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        context.sendBroadcast(intent);
    }

    /**
     * 判断快捷方式是不是存在,需要权限
     * <code>&lt;uses-permission android:name="com.android.launcher.permission.READ_SETTINGS" /&gt;</code>
     * @param context 上下文环境
     * @return <code>true</code>快捷方式已经存在
     */

    public static boolean hasShortcut(Context context, String appName) {
        boolean isExist = false;
        final ContentResolver cr = context.getContentResolver();
        final String AUTHORITY = "com.android.launcher.settings";
        final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/favorites?notify=true");
        Cursor c = cr.query(CONTENT_URI, new String[] { "title", "iconResource" }, "title=?", new String[] { appName }, null);
        if (c != null && c.getCount() > 0) {
            isExist = true;
        }
        try {
            c.close();
        } finally {
            c = null;
        }
        return isExist;
    }

    /**
     * 删除快捷法师图标，需要权限
     * <code>&lt;uses-permission android:name="com.android.launcher.permission.UNINSTALL_SHORTCUT" /&gt;</code>
     * @param cx 上下文
     */
    public static void deleteShortcut(Context cx) {
        Intent intent = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
        String title = null;
        try {
            PackageManager pm = cx.getPackageManager();
            title = pm.getApplicationLabel(pm.getApplicationInfo(cx.getPackageName(),
                            PackageManager.GET_META_DATA)).toString();
        } catch (PackageManager.NameNotFoundException e) {
        }
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
        Intent deleteIntent = cx.getPackageManager().getLaunchIntentForPackage(cx.getPackageName());
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, deleteIntent);
        cx.sendBroadcast(intent);
    }
}
