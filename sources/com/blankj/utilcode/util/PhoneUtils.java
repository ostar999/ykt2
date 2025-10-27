package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.hjq.permissions.Permission;

/* loaded from: classes2.dex */
public final class PhoneUtils {
    private PhoneUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    @RequiresPermission(Permission.CALL_PHONE)
    public static void call(@NonNull String str) {
        Utils.getApp().startActivity(UtilsBridge.getCallIntent(str));
    }

    public static void dial(@NonNull String str) {
        Utils.getApp().startActivity(UtilsBridge.getDialIntent(str));
    }

    @RequiresPermission(Permission.READ_PHONE_STATE)
    @SuppressLint({"HardwareIds"})
    public static String getDeviceId() {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 29) {
            return "";
        }
        TelephonyManager telephonyManager = getTelephonyManager();
        String deviceId = telephonyManager.getDeviceId();
        if (!TextUtils.isEmpty(deviceId)) {
            return deviceId;
        }
        if (i2 < 26) {
            return "";
        }
        String imei = telephonyManager.getImei();
        if (!TextUtils.isEmpty(imei)) {
            return imei;
        }
        String meid = telephonyManager.getMeid();
        return TextUtils.isEmpty(meid) ? "" : meid;
    }

    @RequiresPermission(Permission.READ_PHONE_STATE)
    public static String getIMEI() {
        return getImeiOrMeid(true);
    }

    @RequiresPermission(Permission.READ_PHONE_STATE)
    @SuppressLint({"HardwareIds"})
    public static String getIMSI() {
        if (Build.VERSION.SDK_INT >= 29) {
            try {
                getTelephonyManager().getSubscriberId();
            } catch (SecurityException e2) {
                e2.printStackTrace();
                return "";
            }
        }
        return getTelephonyManager().getSubscriberId();
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00b6 A[PHI: r0
      0x00b6: PHI (r0v7 java.lang.String) = (r0v5 java.lang.String), (r0v5 java.lang.String), (r0v9 java.lang.String), (r0v9 java.lang.String) binds: [B:52:0x00ad, B:54:0x00b3, B:43:0x0099, B:45:0x009f] A[DONT_GENERATE, DONT_INLINE]] */
    @androidx.annotation.RequiresPermission(com.hjq.permissions.Permission.READ_PHONE_STATE)
    @android.annotation.SuppressLint({"HardwareIds"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getImeiOrMeid(boolean r10) throws java.lang.NoSuchMethodException, java.lang.ClassNotFoundException, java.lang.SecurityException {
        /*
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 29
            java.lang.String r2 = ""
            if (r0 < r1) goto L9
            return r2
        L9:
            android.telephony.TelephonyManager r1 = getTelephonyManager()
            r3 = 26
            r4 = 1
            r5 = 0
            if (r0 < r3) goto L2f
            if (r10 == 0) goto L22
            java.lang.String r10 = com.blankj.utilcode.util.m.a(r1, r5)
            java.lang.String r0 = com.blankj.utilcode.util.m.a(r1, r4)
            java.lang.String r10 = getMinOne(r10, r0)
            return r10
        L22:
            java.lang.String r10 = com.blankj.utilcode.util.n.a(r1, r5)
            java.lang.String r0 = com.blankj.utilcode.util.n.a(r1, r4)
            java.lang.String r10 = getMinOne(r10, r0)
            return r10
        L2f:
            if (r10 == 0) goto L34
            java.lang.String r0 = "ril.gsm.imei"
            goto L36
        L34:
            java.lang.String r0 = "ril.cdma.meid"
        L36:
            java.lang.String r0 = getSystemPropertyByReflect(r0)
            boolean r3 = android.text.TextUtils.isEmpty(r0)
            r6 = 2
            if (r3 != 0) goto L56
            java.lang.String r10 = ","
            java.lang.String[] r10 = r0.split(r10)
            int r0 = r10.length
            if (r0 != r6) goto L53
            r0 = r10[r5]
            r10 = r10[r4]
            java.lang.String r10 = getMinOne(r0, r10)
            return r10
        L53:
            r10 = r10[r5]
            return r10
        L56:
            java.lang.String r0 = r1.getDeviceId()
            java.lang.Class r3 = r1.getClass()     // Catch: java.lang.reflect.InvocationTargetException -> L7d java.lang.IllegalAccessException -> L82 java.lang.NoSuchMethodException -> L87
            java.lang.String r7 = "getDeviceId"
            java.lang.Class[] r8 = new java.lang.Class[r4]     // Catch: java.lang.reflect.InvocationTargetException -> L7d java.lang.IllegalAccessException -> L82 java.lang.NoSuchMethodException -> L87
            java.lang.Class r9 = java.lang.Integer.TYPE     // Catch: java.lang.reflect.InvocationTargetException -> L7d java.lang.IllegalAccessException -> L82 java.lang.NoSuchMethodException -> L87
            r8[r5] = r9     // Catch: java.lang.reflect.InvocationTargetException -> L7d java.lang.IllegalAccessException -> L82 java.lang.NoSuchMethodException -> L87
            java.lang.reflect.Method r3 = r3.getMethod(r7, r8)     // Catch: java.lang.reflect.InvocationTargetException -> L7d java.lang.IllegalAccessException -> L82 java.lang.NoSuchMethodException -> L87
            java.lang.Object[] r7 = new java.lang.Object[r4]     // Catch: java.lang.reflect.InvocationTargetException -> L7d java.lang.IllegalAccessException -> L82 java.lang.NoSuchMethodException -> L87
            if (r10 == 0) goto L6f
            goto L70
        L6f:
            r4 = r6
        L70:
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch: java.lang.reflect.InvocationTargetException -> L7d java.lang.IllegalAccessException -> L82 java.lang.NoSuchMethodException -> L87
            r7[r5] = r4     // Catch: java.lang.reflect.InvocationTargetException -> L7d java.lang.IllegalAccessException -> L82 java.lang.NoSuchMethodException -> L87
            java.lang.Object r1 = r3.invoke(r1, r7)     // Catch: java.lang.reflect.InvocationTargetException -> L7d java.lang.IllegalAccessException -> L82 java.lang.NoSuchMethodException -> L87
            java.lang.String r1 = (java.lang.String) r1     // Catch: java.lang.reflect.InvocationTargetException -> L7d java.lang.IllegalAccessException -> L82 java.lang.NoSuchMethodException -> L87
            goto L8c
        L7d:
            r1 = move-exception
            r1.printStackTrace()
            goto L8b
        L82:
            r1 = move-exception
            r1.printStackTrace()
            goto L8b
        L87:
            r1 = move-exception
            r1.printStackTrace()
        L8b:
            r1 = r2
        L8c:
            if (r10 == 0) goto La2
            r10 = 15
            if (r0 == 0) goto L99
            int r3 = r0.length()
            if (r3 >= r10) goto L99
            r0 = r2
        L99:
            if (r1 == 0) goto Lb6
            int r3 = r1.length()
            if (r3 >= r10) goto Lb6
            goto Lb7
        La2:
            r10 = 14
            if (r0 == 0) goto Lad
            int r3 = r0.length()
            if (r3 != r10) goto Lad
            r0 = r2
        Lad:
            if (r1 == 0) goto Lb6
            int r3 = r1.length()
            if (r3 != r10) goto Lb6
            goto Lb7
        Lb6:
            r2 = r1
        Lb7:
            java.lang.String r10 = getMinOne(r0, r2)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.PhoneUtils.getImeiOrMeid(boolean):java.lang.String");
    }

    @RequiresPermission(Permission.READ_PHONE_STATE)
    public static String getMEID() {
        return getImeiOrMeid(false);
    }

    private static String getMinOne(String str, String str2) {
        boolean zIsEmpty = TextUtils.isEmpty(str);
        boolean zIsEmpty2 = TextUtils.isEmpty(str2);
        return (zIsEmpty && zIsEmpty2) ? "" : (zIsEmpty || zIsEmpty2) ? !zIsEmpty ? str : str2 : str.compareTo(str2) <= 0 ? str : str2;
    }

    public static int getPhoneType() {
        return getTelephonyManager().getPhoneType();
    }

    @RequiresPermission(Permission.READ_PHONE_STATE)
    @SuppressLint({"HardwareIds"})
    public static String getSerial() {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 < 29) {
            return i2 >= 26 ? Build.getSerial() : Build.SERIAL;
        }
        try {
            return Build.getSerial();
        } catch (SecurityException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static String getSimOperatorByMnc() {
        String simOperator = getTelephonyManager().getSimOperator();
        if (simOperator == null) {
            return "";
        }
        switch (simOperator) {
            case "46000":
            case "46002":
            case "46007":
            case "46020":
                return "中国移动";
            case "46001":
            case "46006":
            case "46009":
                return "中国联通";
            case "46003":
            case "46005":
            case "46011":
                return "中国电信";
            default:
                return simOperator;
        }
    }

    public static String getSimOperatorName() {
        return getTelephonyManager().getSimOperatorName();
    }

    private static String getSystemPropertyByReflect(String str) throws ClassNotFoundException {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", String.class, String.class).invoke(cls, str, "");
        } catch (Exception unused) {
            return "";
        }
    }

    private static TelephonyManager getTelephonyManager() {
        return (TelephonyManager) Utils.getApp().getSystemService(AliyunLogCommon.TERMINAL_TYPE);
    }

    public static boolean isPhone() {
        return getTelephonyManager().getPhoneType() != 0;
    }

    public static boolean isSimCardReady() {
        return getTelephonyManager().getSimState() == 5;
    }

    public static void sendSms(@NonNull String str, String str2) {
        Utils.getApp().startActivity(UtilsBridge.getSendSmsIntent(str, str2));
    }
}
