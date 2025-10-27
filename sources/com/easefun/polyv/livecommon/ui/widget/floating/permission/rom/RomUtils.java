package com.easefun.polyv.livecommon.ui.widget.floating.permission.rom;

import android.os.Build;
import android.text.TextUtils;
import cn.hutool.core.text.StrPool;

/* loaded from: classes3.dex */
public class RomUtils {
    private static String TAG = "RomUtils";

    public static boolean checkIs360Rom() {
        String str = Build.MANUFACTURER;
        return str.contains("QiKU") || str.contains("360");
    }

    public static boolean checkIsHuaweiRom() {
        return Build.MANUFACTURER.contains("HUAWEI");
    }

    public static boolean checkIsMeizuRom() throws Throwable {
        String systemProperty = getSystemProperty("ro.build.display.id");
        if (TextUtils.isEmpty(systemProperty)) {
            return systemProperty.contains("flyme") || systemProperty.toLowerCase().contains("flyme");
        }
        return false;
    }

    public static boolean checkIsMiuiRom() {
        return !TextUtils.isEmpty(getSystemProperty("ro.miui.ui.version.name"));
    }

    public static boolean checkIsOppoRom() {
        String str = Build.MANUFACTURER;
        return str.contains(com.hyphenate.easeui.utils.RomUtils.ROM_OPPO) || str.contains("oppo");
    }

    public static boolean checkIsVivoRom() {
        String str = Build.MANUFACTURER;
        return str.contains(com.hyphenate.easeui.utils.RomUtils.ROM_VIVO) || str.contains("vivo");
    }

    public static double getEmuiVersion() throws Throwable {
        try {
            String systemProperty = getSystemProperty("ro.build.version.emui");
            if (systemProperty != null) {
                return Double.parseDouble(systemProperty.substring(systemProperty.indexOf(StrPool.UNDERLINE) + 1));
            }
            return 4.0d;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 4.0d;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x005c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getSystemProperty(java.lang.String r5) throws java.lang.Throwable {
        /*
            java.lang.String r0 = "Exception while closing InputStream"
            r1 = 0
            java.lang.Runtime r2 = java.lang.Runtime.getRuntime()     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            r3.<init>()     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            java.lang.String r4 = "getprop "
            r3.append(r4)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            r3.append(r5)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            java.lang.String r5 = r3.toString()     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            java.lang.Process r5 = r2.exec(r5)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            java.io.InputStream r5 = r5.getInputStream()     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            r3.<init>(r5)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            r5 = 1024(0x400, float:1.435E-42)
            r2.<init>(r3, r5)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            java.lang.String r5 = r2.readLine()     // Catch: java.lang.Exception -> L3e java.lang.Throwable -> L58
            r2.close()     // Catch: java.lang.Exception -> L3e java.lang.Throwable -> L58
            r2.close()     // Catch: java.io.IOException -> L37
            goto L3d
        L37:
            r1 = move-exception
            java.lang.String r2 = com.easefun.polyv.livecommon.ui.widget.floating.permission.rom.RomUtils.TAG
            android.util.Log.e(r2, r0, r1)
        L3d:
            return r5
        L3e:
            r5 = move-exception
            goto L44
        L40:
            r5 = move-exception
            goto L5a
        L42:
            r5 = move-exception
            r2 = r1
        L44:
            java.lang.String r3 = com.easefun.polyv.livecommon.ui.widget.floating.permission.rom.RomUtils.TAG     // Catch: java.lang.Throwable -> L58
            java.lang.String r4 = "Unable to read sysprop $propName"
            android.util.Log.e(r3, r4, r5)     // Catch: java.lang.Throwable -> L58
            if (r2 == 0) goto L57
            r2.close()     // Catch: java.io.IOException -> L51
            goto L57
        L51:
            r5 = move-exception
            java.lang.String r2 = com.easefun.polyv.livecommon.ui.widget.floating.permission.rom.RomUtils.TAG
            android.util.Log.e(r2, r0, r5)
        L57:
            return r1
        L58:
            r5 = move-exception
            r1 = r2
        L5a:
            if (r1 == 0) goto L66
            r1.close()     // Catch: java.io.IOException -> L60
            goto L66
        L60:
            r1 = move-exception
            java.lang.String r2 = com.easefun.polyv.livecommon.ui.widget.floating.permission.rom.RomUtils.TAG
            android.util.Log.e(r2, r0, r1)
        L66:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.easefun.polyv.livecommon.ui.widget.floating.permission.rom.RomUtils.getSystemProperty(java.lang.String):java.lang.String");
    }
}
