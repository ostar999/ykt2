package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.hjq.permissions.Permission;
import com.mobile.auth.BuildConfig;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.push.Cdo;
import com.xiaomi.push.dp;
import java.io.File;

/* loaded from: classes6.dex */
public class Logger {
    private static boolean sDisablePushLog = false;
    private static LoggerInterface sUserLogger;

    public static void disablePushFileLog(Context context) {
        sDisablePushLog = true;
        setPushLog(context);
    }

    public static void enablePushFileLog(Context context) {
        sDisablePushLog = false;
        setPushLog(context);
    }

    public static File getLogFile(String str) {
        File file;
        try {
            file = new File(str);
        } catch (NullPointerException unused) {
            com.xiaomi.channel.commonutils.logger.b.d("null pointer exception while retrieve file.");
        }
        if (file.exists() && file.isDirectory()) {
            File[] fileArrListFiles = file.listFiles();
            for (int i2 = 0; i2 < fileArrListFiles.length; i2++) {
                if (fileArrListFiles[i2].isFile() && !fileArrListFiles[i2].getName().contains("lock") && fileArrListFiles[i2].getName().contains(BuildConfig.FLAVOR_type)) {
                    return fileArrListFiles[i2];
                }
            }
            return null;
        }
        return null;
    }

    public static LoggerInterface getUserLogger() {
        return sUserLogger;
    }

    private static boolean hasWritePermission(Context context) {
        try {
            String[] strArr = context.getPackageManager().getPackageInfo(context.getPackageName(), 4096).requestedPermissions;
            if (strArr != null) {
                for (String str : strArr) {
                    if (Permission.WRITE_EXTERNAL_STORAGE.equals(str)) {
                        return true;
                    }
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    public static void setLogger(Context context, LoggerInterface loggerInterface) {
        sUserLogger = loggerInterface;
        setPushLog(context);
    }

    public static void setPushLog(Context context) {
        LoggerInterface cdo;
        boolean z2 = sUserLogger != null;
        dp dpVar = new dp(context);
        if (!sDisablePushLog && hasWritePermission(context) && z2) {
            cdo = new Cdo(sUserLogger, dpVar);
        } else {
            if (!sDisablePushLog && hasWritePermission(context)) {
                com.xiaomi.channel.commonutils.logger.b.a(dpVar);
                return;
            }
            cdo = z2 ? sUserLogger : new Cdo(null, null);
        }
        com.xiaomi.channel.commonutils.logger.b.a(cdo);
    }

    public static void uploadLogFile(Context context, boolean z2) {
        com.xiaomi.push.ai.a(context).a(new y(context, z2));
    }
}
