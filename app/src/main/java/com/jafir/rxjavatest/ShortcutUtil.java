package com.jafir.rxjavatest;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by jafir on 16/7/26.
 */
public class ShortcutUtil {

    private static final String INSTALL_SHORTCUT =
            "com.android.launcher.action.INSTALL_SHORTCUT";
    private static final String UNINSTALL_SHORTCUT =
            "com.android.launcher.action.UNINSTALL_SHORTCUT";

    // Action 添加Shortcut
    public static final String ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";


//    public static  void appShortcut(View v) {
//        addAppShortcut(this, "进入WebShortcut", "com.bail.webshortcut", "com.bail.webshortcut.MainActivity", R.mipmap.ic_launcher);
//    }
//
//    public  static  void removeAppShortcut(View v) {
//        removeAppShortcut("进入WebShortcut", "com.bail.webshortcut", "com.bail.webshortcut.MainActivity");
//    }

    public static void addAppShortcut(Context context, String name,
                                      Class<TestWindowAcitivity> cls, int iconres) {

        if (isInstallShortcut(name)) {// 如果已经创建了一次就不会再创建了
            return;
        }

        Log.e("debug","short");

        Intent intent = new Intent(INSTALL_SHORTCUT);
//        intent.putExtra("duplicate", false);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        Parcelable icon = Intent.ShortcutIconResource.fromContext(context, iconres);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, icon);


        Intent sIntent = new Intent(Intent.ACTION_MAIN);
        sIntent.addCategory(Intent.CATEGORY_LAUNCHER);// 加入action,和category之后，程序卸载的时候才会主动将该快捷方式也卸载
        sIntent.setClass(context, cls);

        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, sIntent);
        context.sendBroadcast(intent);
    }
    /**
     * 添加快捷方式
     *
     * @param context      context
     * @param actionIntent 要启动的Intent
     * @param name         name
     */
    public static void addShortcut(Context context, Intent actionIntent, String name,
                                   boolean allowRepeat, Bitmap iconBitmap) {
        Intent addShortcutIntent = new Intent(ACTION_ADD_SHORTCUT);
        // 是否允许重复创建
        addShortcutIntent.putExtra("duplicate", allowRepeat);
        // 快捷方式的标题
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        // 快捷方式的图标
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, iconBitmap);
        // 快捷方式的动作
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, actionIntent);
        context.sendBroadcast(addShortcutIntent);
    }

    public static void removeAppShortcut(Context context, String name, String packageName, String componentName) {
        Intent intent = new Intent(UNINSTALL_SHORTCUT);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        Intent pending = new Intent();
        ComponentName comp = new ComponentName(packageName, componentName);
        pending.setAction(Intent.ACTION_MAIN);
        pending.setComponent(comp);

        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, pending);
        context.sendBroadcast(intent);
    }




    private static boolean isInstallShortcut(String name) {
        boolean isInstallShortcut = false;
        final ContentResolver cr = AppContext.app.getContentResolver();
        String AUTHORITY = "com.android.launcher.settings";
        Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
                + "/favorites?notify=true");

        Cursor c = cr.query(CONTENT_URI,
                new String[] { "title", "iconResource" }, "title=?",
                new String[] { name }, null);
        if (c != null && c.getCount() > 0) {
            isInstallShortcut = true;
        }

        if (c != null) {
            c.close();
        }

        if (isInstallShortcut) {
            return isInstallShortcut;
        }

        AUTHORITY = "com.android.launcher2.settings";
        CONTENT_URI = Uri.parse("content://" + AUTHORITY
                + "/favorites?notify=true");
        c = cr.query(CONTENT_URI, new String[] { "title", "iconResource" },
                "title=?", new String[] { name }, null);
        if (c != null && c.getCount() > 0) {
            isInstallShortcut = true;
        }

        return isInstallShortcut;
    }
}
