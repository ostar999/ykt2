package com.plv.thirdpart.blankj.utilcode.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.plv.thirdpart.blankj.utilcode.util.ShellUtils;
import java.io.File;
import java.util.List;

/* loaded from: classes5.dex */
public final class AppUtils {

    public static class AppInfo {
        private Drawable icon;
        private boolean isSystem;
        private String name;
        private String packageName;
        private String packagePath;
        private int versionCode;
        private String versionName;

        public AppInfo(String str, String str2, Drawable drawable, String str3, String str4, int i2, boolean z2) {
            setName(str2);
            setIcon(drawable);
            setPackageName(str);
            setPackagePath(str3);
            setVersionName(str4);
            setVersionCode(i2);
            setSystem(z2);
        }

        public Drawable getIcon() {
            return this.icon;
        }

        public String getName() {
            return this.name;
        }

        public String getPackageName() {
            return this.packageName;
        }

        public String getPackagePath() {
            return this.packagePath;
        }

        public int getVersionCode() {
            return this.versionCode;
        }

        public String getVersionName() {
            return this.versionName;
        }

        public boolean isSystem() {
            return this.isSystem;
        }

        public void setIcon(Drawable drawable) {
            this.icon = drawable;
        }

        public void setName(String str) {
            this.name = str;
        }

        public void setPackageName(String str) {
            this.packageName = str;
        }

        public void setPackagePath(String str) {
            this.packagePath = str;
        }

        public void setSystem(boolean z2) {
            this.isSystem = z2;
        }

        public void setVersionCode(int i2) {
            this.versionCode = i2;
        }

        public void setVersionName(String str) {
            this.versionName = str;
        }

        public String toString() {
            return "pkg name: " + getPackageName() + "\napp name: " + getName() + "\napp path: " + getPackagePath() + "\napp v name: " + getVersionName() + "\napp v code: " + getVersionCode() + "\nis system: " + isSystem();
        }
    }

    private AppUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean cleanAppData(String... strArr) {
        File[] fileArr = new File[strArr.length];
        int length = strArr.length;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            fileArr[i3] = new File(strArr[i2]);
            i2++;
            i3++;
        }
        return cleanAppData(fileArr);
    }

    public static void exitApp() {
        List<Activity> list = Utils.sActivityList;
        for (int size = list.size() - 1; size >= 0; size--) {
            list.get(size).finish();
            list.remove(size);
        }
        System.exit(0);
    }

    public static void getAppDetailsSettings() {
        getAppDetailsSettings(Utils.getApp().getPackageName());
    }

    public static Drawable getAppIcon() {
        return getAppIcon(Utils.getApp().getPackageName());
    }

    public static String getAppName() {
        return getAppName(Utils.getApp().getPackageName());
    }

    public static String getAppPackageName() {
        return Utils.getApp().getPackageName();
    }

    public static String getAppPath() {
        return getAppPath(Utils.getApp().getPackageName());
    }

    public static Signature[] getAppSignature() {
        return getAppSignature(Utils.getApp().getPackageName());
    }

    public static String getAppSignatureSHA1() {
        return getAppSignatureSHA1(Utils.getApp().getPackageName());
    }

    public static int getAppVersionCode() {
        return getAppVersionCode(Utils.getApp().getPackageName());
    }

    public static String getAppVersionName() {
        return getAppVersionName(Utils.getApp().getPackageName());
    }

    private static AppInfo getBean(PackageManager packageManager, PackageInfo packageInfo) {
        if (packageManager == null || packageInfo == null) {
            return null;
        }
        ApplicationInfo applicationInfo = packageInfo.applicationInfo;
        return new AppInfo(packageInfo.packageName, applicationInfo.loadLabel(packageManager).toString(), applicationInfo.loadIcon(packageManager), applicationInfo.sourceDir, packageInfo.versionName, packageInfo.versionCode, (applicationInfo.flags & 1) != 0);
    }

    public static boolean isAppBackground() {
        return !isAppForeground();
    }

    public static boolean isAppDebug() {
        return isAppDebug(Utils.getApp().getPackageName());
    }

    public static boolean isAppForeground() {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) Utils.getApp().getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses();
        if (runningAppProcesses != null && runningAppProcesses.size() != 0) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.importance == 100) {
                    return runningAppProcessInfo.processName.equals(Utils.getApp().getPackageName());
                }
            }
        }
        return false;
    }

    @Deprecated
    public static boolean isAppForeground(String str) {
        return false;
    }

    public static boolean isAppRoot() {
        ShellUtils.CommandResult commandResultExecCmd = ShellUtils.execCmd("echo root", true);
        if (commandResultExecCmd.result == 0) {
            return true;
        }
        if (commandResultExecCmd.errorMsg == null) {
            return false;
        }
        Log.d("AppUtils", "isAppRoot() called" + commandResultExecCmd.errorMsg);
        return false;
    }

    public static boolean isInstallApp(String str, String str2) {
        Intent intent = new Intent(str);
        intent.addCategory(str2);
        return Utils.getApp().getPackageManager().resolveActivity(intent, 0) != null;
    }

    private static boolean isSpace(String str) {
        if (str == null) {
            return true;
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (!Character.isWhitespace(str.charAt(i2))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSystemApp() {
        return isSystemApp(Utils.getApp().getPackageName());
    }

    public static void launchApp(String str) {
        if (isSpace(str)) {
            return;
        }
        Utils.getApp().startActivity(IntentUtils.getLaunchAppIntent(str));
    }

    public static void uninstallApp(String str) {
        if (isSpace(str)) {
            return;
        }
        Utils.getApp().startActivity(IntentUtils.getUninstallAppIntent(str));
    }

    public static boolean uninstallAppSilent(String str, boolean z2) {
        if (isSpace(str)) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm uninstall ");
        sb.append(z2 ? "-k " : "");
        sb.append(str);
        String str2 = ShellUtils.execCmd(sb.toString(), !isSystemApp(), true).successMsg;
        return str2 != null && str2.toLowerCase().contains("success");
    }

    public static void getAppDetailsSettings(String str) {
        if (isSpace(str)) {
            return;
        }
        Utils.getApp().startActivity(IntentUtils.getAppDetailsSettingsIntent(str));
    }

    public static Drawable getAppIcon(String str) throws PackageManager.NameNotFoundException {
        if (isSpace(str)) {
            return null;
        }
        try {
            PackageManager packageManager = Utils.getApp().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
            if (packageInfo == null) {
                return null;
            }
            return packageInfo.applicationInfo.loadIcon(packageManager);
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String getAppName(String str) throws PackageManager.NameNotFoundException {
        if (isSpace(str)) {
            return null;
        }
        try {
            PackageManager packageManager = Utils.getApp().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
            if (packageInfo == null) {
                return null;
            }
            return packageInfo.applicationInfo.loadLabel(packageManager).toString();
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String getAppPath(String str) throws PackageManager.NameNotFoundException {
        if (isSpace(str)) {
            return null;
        }
        try {
            PackageInfo packageInfo = Utils.getApp().getPackageManager().getPackageInfo(str, 0);
            if (packageInfo == null) {
                return null;
            }
            return packageInfo.applicationInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static Signature[] getAppSignature(String str) throws PackageManager.NameNotFoundException {
        if (isSpace(str)) {
            return null;
        }
        try {
            PackageInfo packageInfo = Utils.getApp().getPackageManager().getPackageInfo(str, 64);
            if (packageInfo == null) {
                return null;
            }
            return packageInfo.signatures;
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String getAppSignatureSHA1(String str) throws PackageManager.NameNotFoundException {
        Signature[] appSignature = getAppSignature(str);
        if (appSignature == null) {
            return null;
        }
        return EncryptUtils.encryptSHA1ToString(appSignature[0].toByteArray()).replaceAll("(?<=[0-9A-F]{2})[0-9A-F]{2}", ":$0");
    }

    public static int getAppVersionCode(String str) throws PackageManager.NameNotFoundException {
        if (isSpace(str)) {
            return -1;
        }
        try {
            PackageInfo packageInfo = Utils.getApp().getPackageManager().getPackageInfo(str, 0);
            if (packageInfo == null) {
                return -1;
            }
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    public static String getAppVersionName(String str) throws PackageManager.NameNotFoundException {
        if (isSpace(str)) {
            return null;
        }
        try {
            PackageInfo packageInfo = Utils.getApp().getPackageManager().getPackageInfo(str, 0);
            if (packageInfo == null) {
                return null;
            }
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static boolean isAppDebug(String str) throws PackageManager.NameNotFoundException {
        if (isSpace(str)) {
            return false;
        }
        try {
            ApplicationInfo applicationInfo = Utils.getApp().getPackageManager().getApplicationInfo(str, 0);
            if (applicationInfo != null) {
                return (applicationInfo.flags & 2) != 0;
            }
            return false;
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static boolean isSystemApp(String str) throws PackageManager.NameNotFoundException {
        if (isSpace(str)) {
            return false;
        }
        try {
            ApplicationInfo applicationInfo = Utils.getApp().getPackageManager().getApplicationInfo(str, 0);
            if (applicationInfo != null) {
                return (applicationInfo.flags & 1) != 0;
            }
            return false;
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static void launchApp(Activity activity, String str, int i2) {
        if (isSpace(str)) {
            return;
        }
        activity.startActivityForResult(IntentUtils.getLaunchAppIntent(str), i2);
    }

    public static void uninstallApp(Activity activity, String str, int i2) {
        if (isSpace(str)) {
            return;
        }
        activity.startActivityForResult(IntentUtils.getUninstallAppIntent(str), i2);
    }

    public static boolean cleanAppData(File... fileArr) {
        boolean zCleanInternalCache = CleanUtils.cleanInternalCache() & CleanUtils.cleanInternalDbs() & CleanUtils.cleanInternalSP() & CleanUtils.cleanInternalFiles() & CleanUtils.cleanExternalCache();
        for (File file : fileArr) {
            zCleanInternalCache &= CleanUtils.cleanCustomCache(file);
        }
        return zCleanInternalCache;
    }

    public static boolean isInstallApp(String str) {
        return (isSpace(str) || IntentUtils.getLaunchAppIntent(str) == null) ? false : true;
    }
}
