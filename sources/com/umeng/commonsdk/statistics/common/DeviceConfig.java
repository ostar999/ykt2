package com.umeng.commonsdk.statistics.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.google.android.material.timepicker.TimeModel;
import com.hjq.permissions.Permission;
import com.hyphenate.easeui.utils.RomUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.umeng.analytics.pro.am;
import com.umeng.commonsdk.config.FieldManager;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.statistics.AnalyticsConstants;
import com.umeng.commonsdk.statistics.idtracking.h;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.yikaobang.yixue.R2;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import javax.microedition.khronos.opengles.GL10;

/* loaded from: classes6.dex */
public class DeviceConfig {
    public static final int DEFAULT_TIMEZONE = 8;
    private static final String KEY_EMUI_VERSION_CODE = "ro.build.hw_emui_api_level";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    protected static final String LOG_TAG = "com.umeng.commonsdk.statistics.common.DeviceConfig";
    public static final String MOBILE_NETWORK = "2G/3G";
    public static final String UNKNOW = "";
    public static final String WIFI = "Wi-Fi";
    private static DeviceTypeEnum deviceTypeEnum = DeviceTypeEnum.DEFAULT;
    private static volatile String sWifiMac = "";
    private static volatile String sImei = "";
    private static volatile String sMeid = "";
    private static volatile String sImsi = "";
    private static volatile String sSerialNo = "";
    private static volatile String sAndroidID = "";
    private static volatile String sIDFA = "";
    private static volatile String sOAID = "";
    private static volatile String sSecondImei = "";
    private static volatile String sSimSerialNumber = "";
    private static volatile boolean sImeiOrMeidFlag = false;
    private static volatile boolean sSerialFlag = false;
    private static volatile boolean sSimSerialNumberFlag = false;
    private static volatile boolean sImsiFlag = false;
    private static volatile boolean sSecondImeiFlag = false;
    private static volatile boolean hasReadImsi = false;

    private static String byte2HexFormatted(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (int i2 = 0; i2 < bArr.length; i2++) {
            String hexString = Integer.toHexString(bArr[i2]);
            int length = hexString.length();
            if (length == 1) {
                hexString = "0" + hexString;
            }
            if (length > 2) {
                hexString = hexString.substring(length - 2, length);
            }
            sb.append(hexString.toUpperCase(Locale.getDefault()));
            if (i2 < bArr.length - 1) {
                sb.append(':');
            }
        }
        return sb.toString();
    }

    public static boolean checkPermission(Context context, String str) {
        if (context == null) {
            return false;
        }
        try {
            return ((Integer) Class.forName("android.content.Context").getMethod("checkSelfPermission", String.class).invoke(context, str)).intValue() == 0;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static String getAndroidId(Context context) {
        if (!TextUtils.isEmpty(sAndroidID)) {
            return sAndroidID;
        }
        String string = null;
        if (!FieldManager.allow(com.umeng.commonsdk.utils.b.f23526i) || context == null) {
            return null;
        }
        try {
            string = Settings.Secure.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
            sAndroidID = string;
            return string;
        } catch (Exception unused) {
            if (!AnalyticsConstants.UM_DEBUG) {
                return string;
            }
            MLog.w("can't read android id");
            return string;
        }
    }

    public static String getAppHashKey(Context context) {
        try {
            Signature[] signatureArr = context.getPackageManager().getPackageInfo(getPackageName(context), 64).signatures;
            if (signatureArr.length <= 0) {
                return null;
            }
            Signature signature = signatureArr[0];
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
            messageDigest.update(signature.toByteArray());
            return Base64.encodeToString(messageDigest.digest(), 0).trim();
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String getAppMD5Signature(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return byte2HexFormatted(MessageDigest.getInstance("MD5").digest(((X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(new ByteArrayInputStream(context.getPackageManager().getPackageInfo(getPackageName(context), 64).signatures[0].toByteArray()))).getEncoded()));
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String getAppName(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.loadLabel(context.getPackageManager()).toString();
        } catch (Throwable th) {
            if (!AnalyticsConstants.UM_DEBUG) {
                return null;
            }
            MLog.i(LOG_TAG, th);
            return null;
        }
    }

    public static String getAppSHA1Key(Context context) {
        try {
            return byte2HexFormatted(MessageDigest.getInstance("SHA1").digest(((X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(new ByteArrayInputStream(context.getPackageManager().getPackageInfo(getPackageName(context), 64).signatures[0].toByteArray()))).getEncoded()));
        } catch (Exception unused) {
            return null;
        }
    }

    public static String getAppVersionCode(Context context) {
        return UMUtils.getAppVersionCode(context);
    }

    public static String getAppVersionName(Context context) {
        return UMUtils.getAppVersionName(context);
    }

    public static String getApplicationLable(Context context) {
        return context == null ? "" : context.getPackageManager().getApplicationLabel(context.getApplicationInfo()).toString();
    }

    private static Properties getBuildProp() {
        FileInputStream fileInputStream;
        Properties properties = new Properties();
        FileInputStream fileInputStream2 = null;
        try {
            try {
                fileInputStream = new FileInputStream(new File(Environment.getRootDirectory(), "build.prop"));
            } catch (Throwable unused) {
            }
            try {
                properties.load(fileInputStream);
                fileInputStream.close();
            } catch (Throwable unused2) {
                fileInputStream2 = fileInputStream;
                if (fileInputStream2 != null) {
                    fileInputStream2.close();
                }
                return properties;
            }
        } catch (Throwable unused3) {
        }
        return properties;
    }

    public static String getCPU() {
        String line = null;
        try {
            FileReader fileReader = new FileReader("/proc/cpuinfo");
            try {
                BufferedReader bufferedReader = new BufferedReader(fileReader, 1024);
                line = bufferedReader.readLine();
                bufferedReader.close();
                fileReader.close();
            } catch (Throwable th) {
                MLog.e(LOG_TAG, "Could not read from file /proc/cpuinfo", th);
            }
        } catch (FileNotFoundException e2) {
            MLog.e(LOG_TAG, "Could not open file /proc/cpuinfo", e2);
        }
        return line != null ? line.substring(line.indexOf(58) + 1).trim() : "";
    }

    public static String getDBencryptID(Context context) {
        return UMUtils.genId();
    }

    public static String getDeviceId(Context context) {
        return AnalyticsConstants.getDeviceType() == 2 ? getDeviceIdForBox(context) : getDeviceIdForGeneral(context);
    }

    public static String getDeviceIdForBox(Context context) {
        String androidId = "";
        if (context == null) {
            return "";
        }
        try {
            int i2 = Build.VERSION.SDK_INT;
            if (i2 == 23) {
                if (FieldManager.allow(com.umeng.commonsdk.utils.b.f23526i)) {
                    androidId = getAndroidId(context);
                    deviceTypeEnum = DeviceTypeEnum.ANDROIDID;
                    if (AnalyticsConstants.UM_DEBUG) {
                        MLog.i(LOG_TAG, "getDeviceId, ANDROID_ID: " + androidId);
                    }
                }
                if (!TextUtils.isEmpty(androidId)) {
                    return androidId;
                }
                String macByJavaAPI = getMacByJavaAPI();
                DeviceTypeEnum deviceTypeEnum2 = DeviceTypeEnum.MAC;
                deviceTypeEnum = deviceTypeEnum2;
                if (TextUtils.isEmpty(macByJavaAPI)) {
                    if (AnalyticsConstants.CHECK_DEVICE) {
                        macByJavaAPI = getMacShell();
                        deviceTypeEnum = deviceTypeEnum2;
                    } else {
                        macByJavaAPI = getMacBySystemInterface(context);
                        deviceTypeEnum = deviceTypeEnum2;
                    }
                }
                if (AnalyticsConstants.UM_DEBUG) {
                    MLog.i(LOG_TAG, "getDeviceId, MAC: " + macByJavaAPI);
                }
                if (!TextUtils.isEmpty(macByJavaAPI)) {
                    return macByJavaAPI;
                }
                String serialNo = getSerialNo();
                deviceTypeEnum = DeviceTypeEnum.SERIALNO;
                if (!TextUtils.isEmpty(serialNo)) {
                    return serialNo;
                }
                String imei = getIMEI(context);
                deviceTypeEnum = DeviceTypeEnum.IMEI;
                return imei;
            }
            if (i2 >= 29) {
                String oaid = getOaid(context);
                deviceTypeEnum = DeviceTypeEnum.OAID;
                if (!TextUtils.isEmpty(oaid)) {
                    return oaid;
                }
                String idfa = getIdfa(context);
                deviceTypeEnum = DeviceTypeEnum.IDFA;
                if (!TextUtils.isEmpty(idfa)) {
                    return idfa;
                }
                String androidId2 = getAndroidId(context);
                deviceTypeEnum = DeviceTypeEnum.ANDROIDID;
                if (!TextUtils.isEmpty(androidId2)) {
                    return androidId2;
                }
                String serialNo2 = getSerialNo();
                deviceTypeEnum = DeviceTypeEnum.SERIALNO;
                if (!TextUtils.isEmpty(serialNo2)) {
                    return serialNo2;
                }
                String macByJavaAPI2 = getMacByJavaAPI();
                DeviceTypeEnum deviceTypeEnum3 = DeviceTypeEnum.MAC;
                deviceTypeEnum = deviceTypeEnum3;
                if (!TextUtils.isEmpty(macByJavaAPI2)) {
                    return macByJavaAPI2;
                }
                String macBySystemInterface = getMacBySystemInterface(context);
                deviceTypeEnum = deviceTypeEnum3;
                return macBySystemInterface;
            }
            if (FieldManager.allow(com.umeng.commonsdk.utils.b.f23526i)) {
                androidId = getAndroidId(context);
                deviceTypeEnum = DeviceTypeEnum.ANDROIDID;
                if (AnalyticsConstants.UM_DEBUG) {
                    MLog.i(LOG_TAG, "getDeviceId: ANDROID_ID: " + androidId);
                }
            }
            if (!TextUtils.isEmpty(androidId)) {
                return androidId;
            }
            String serialNo3 = getSerialNo();
            deviceTypeEnum = DeviceTypeEnum.SERIALNO;
            if (!TextUtils.isEmpty(serialNo3)) {
                return serialNo3;
            }
            String imei2 = getIMEI(context);
            deviceTypeEnum = DeviceTypeEnum.IMEI;
            if (!TextUtils.isEmpty(imei2)) {
                return imei2;
            }
            String macByJavaAPI3 = getMacByJavaAPI();
            DeviceTypeEnum deviceTypeEnum4 = DeviceTypeEnum.MAC;
            deviceTypeEnum = deviceTypeEnum4;
            if (!TextUtils.isEmpty(macByJavaAPI3)) {
                return macByJavaAPI3;
            }
            String macBySystemInterface2 = getMacBySystemInterface(context);
            deviceTypeEnum = deviceTypeEnum4;
            if (!AnalyticsConstants.UM_DEBUG) {
                return macBySystemInterface2;
            }
            MLog.i(LOG_TAG, "getDeviceId, MAC: " + macBySystemInterface2);
            return macBySystemInterface2;
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String getDeviceIdForGeneral(Context context) {
        if (context == null) {
            return "";
        }
        try {
            int i2 = Build.VERSION.SDK_INT;
            if (i2 == 23) {
                String imei = getIMEI(context);
                deviceTypeEnum = DeviceTypeEnum.IMEI;
                if (!TextUtils.isEmpty(imei)) {
                    return imei;
                }
                String macByJavaAPI = getMacByJavaAPI();
                DeviceTypeEnum deviceTypeEnum2 = DeviceTypeEnum.MAC;
                deviceTypeEnum = deviceTypeEnum2;
                if (TextUtils.isEmpty(macByJavaAPI)) {
                    if (AnalyticsConstants.CHECK_DEVICE) {
                        macByJavaAPI = getMacShell();
                        deviceTypeEnum = deviceTypeEnum2;
                    } else {
                        macByJavaAPI = getMacBySystemInterface(context);
                        deviceTypeEnum = deviceTypeEnum2;
                    }
                }
                boolean z2 = AnalyticsConstants.UM_DEBUG;
                if (z2) {
                    MLog.i(LOG_TAG, "getDeviceId, MAC: " + macByJavaAPI);
                }
                if (!TextUtils.isEmpty(macByJavaAPI)) {
                    return macByJavaAPI;
                }
                if (FieldManager.allow(com.umeng.commonsdk.utils.b.f23526i)) {
                    macByJavaAPI = getAndroidId(context);
                    deviceTypeEnum = DeviceTypeEnum.ANDROIDID;
                    if (z2) {
                        MLog.i(LOG_TAG, "getDeviceId, ANDROID_ID: " + macByJavaAPI);
                    }
                }
                if (!TextUtils.isEmpty(macByJavaAPI)) {
                    return macByJavaAPI;
                }
                String serialNo = getSerialNo();
                deviceTypeEnum = DeviceTypeEnum.SERIALNO;
                return serialNo;
            }
            if (i2 >= 29) {
                String oaid = getOaid(context);
                deviceTypeEnum = DeviceTypeEnum.OAID;
                if (!TextUtils.isEmpty(oaid)) {
                    return oaid;
                }
                String idfa = getIdfa(context);
                deviceTypeEnum = DeviceTypeEnum.IDFA;
                if (!TextUtils.isEmpty(idfa)) {
                    return idfa;
                }
                String androidId = getAndroidId(context);
                deviceTypeEnum = DeviceTypeEnum.ANDROIDID;
                if (!TextUtils.isEmpty(androidId)) {
                    return androidId;
                }
                String serialNo2 = getSerialNo();
                deviceTypeEnum = DeviceTypeEnum.SERIALNO;
                if (!TextUtils.isEmpty(serialNo2)) {
                    return serialNo2;
                }
                String macByJavaAPI2 = getMacByJavaAPI();
                DeviceTypeEnum deviceTypeEnum3 = DeviceTypeEnum.MAC;
                deviceTypeEnum = deviceTypeEnum3;
                if (!TextUtils.isEmpty(macByJavaAPI2)) {
                    return macByJavaAPI2;
                }
                String macBySystemInterface = getMacBySystemInterface(context);
                deviceTypeEnum = deviceTypeEnum3;
                return macBySystemInterface;
            }
            String imei2 = getIMEI(context);
            deviceTypeEnum = DeviceTypeEnum.IMEI;
            if (!TextUtils.isEmpty(imei2)) {
                return imei2;
            }
            String serialNo3 = getSerialNo();
            deviceTypeEnum = DeviceTypeEnum.SERIALNO;
            if (!TextUtils.isEmpty(serialNo3)) {
                return serialNo3;
            }
            if (FieldManager.allow(com.umeng.commonsdk.utils.b.f23526i)) {
                serialNo3 = getAndroidId(context);
                deviceTypeEnum = DeviceTypeEnum.ANDROIDID;
                if (AnalyticsConstants.UM_DEBUG) {
                    MLog.i(LOG_TAG, "getDeviceId, ANDROID_ID: " + serialNo3);
                }
            }
            if (!TextUtils.isEmpty(serialNo3)) {
                return serialNo3;
            }
            String macByJavaAPI3 = getMacByJavaAPI();
            DeviceTypeEnum deviceTypeEnum4 = DeviceTypeEnum.MAC;
            deviceTypeEnum = deviceTypeEnum4;
            if (!TextUtils.isEmpty(macByJavaAPI3)) {
                return macByJavaAPI3;
            }
            String macBySystemInterface2 = getMacBySystemInterface(context);
            deviceTypeEnum = deviceTypeEnum4;
            if (!AnalyticsConstants.UM_DEBUG) {
                return macBySystemInterface2;
            }
            MLog.i(LOG_TAG, "getDeviceId, MAC: " + macBySystemInterface2);
            return macBySystemInterface2;
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String getDeviceIdType() {
        return deviceTypeEnum.getDeviceIdType();
    }

    public static String getDeviceIdUmengMD5(Context context) {
        return HelperUtils.getUmengMD5(getDeviceId(context));
    }

    public static String getDeviceType(Context context) {
        if (context == null) {
            return "Phone";
        }
        try {
            return (context.getResources().getConfiguration().screenLayout & 15) >= 3 ? "Tablet" : "Phone";
        } catch (Throwable unused) {
            return "Phone";
        }
    }

    public static String getDisplayResolution(Context context) {
        if (context == null) {
            return "";
        }
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) context.getSystemService("window");
            if (windowManager != null) {
                windowManager.getDefaultDisplay().getMetrics(displayMetrics);
                int i2 = displayMetrics.widthPixels;
                return String.valueOf(displayMetrics.heightPixels) + "*" + String.valueOf(i2);
            }
        } catch (Throwable unused) {
        }
        return "";
    }

    private static String getEmuiVersion(Properties properties) {
        try {
            return properties.getProperty(KEY_EMUI_VERSION_CODE, null);
        } catch (Exception unused) {
            return null;
        }
    }

    private static String getFlymeVersion(Properties properties) {
        try {
            String lowerCase = properties.getProperty("ro.build.display.id").toLowerCase(Locale.getDefault());
            if (lowerCase.contains("flyme os")) {
                return lowerCase.split(" ")[2];
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String[] getGPU(GL10 gl10) {
        try {
            return new String[]{gl10.glGetString(R2.dimen.material_cursor_width), gl10.glGetString(R2.dimen.material_divider_thickness)};
        } catch (Throwable th) {
            if (AnalyticsConstants.UM_DEBUG) {
                MLog.e(LOG_TAG, "Could not read gpu infor:", th);
            }
            return new String[0];
        }
    }

    public static Activity getGlobleActivity(Context context) {
        Activity activity = null;
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Object objInvoke = cls.getMethod("currentActivityThread", new Class[0]).invoke(null, new Object[0]);
            Field declaredField = cls.getDeclaredField("mActivities");
            declaredField.setAccessible(true);
            for (Object obj : ((Map) declaredField.get(objInvoke)).values()) {
                Class<?> cls2 = obj.getClass();
                Field declaredField2 = cls2.getDeclaredField("paused");
                declaredField2.setAccessible(true);
                if (!declaredField2.getBoolean(obj)) {
                    Field declaredField3 = cls2.getDeclaredField(PushConstants.INTENT_ACTIVITY_NAME);
                    declaredField3.setAccessible(true);
                    activity = (Activity) declaredField3.get(obj);
                }
            }
        } catch (Throwable unused) {
        }
        return activity;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0060 A[PHI: r1
      0x0060: PHI (r1v2 java.lang.String) = (r1v3 java.lang.String), (r1v4 java.lang.String) binds: [B:34:0x0071, B:27:0x005e] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String getIMEI(android.content.Context r7) {
        /*
            java.lang.String r0 = com.umeng.commonsdk.statistics.common.DeviceConfig.sImei
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto Lb
            java.lang.String r7 = com.umeng.commonsdk.statistics.common.DeviceConfig.sImei
            return r7
        Lb:
            int r0 = android.os.Build.VERSION.SDK_INT
            java.lang.String r1 = ""
            r2 = 29
            if (r0 < r2) goto L18
            boolean r3 = com.umeng.commonsdk.statistics.common.DeviceConfig.sImeiOrMeidFlag
            if (r3 == 0) goto L18
            return r1
        L18:
            java.lang.String r3 = "header_device_id_imei"
            boolean r3 = com.umeng.commonsdk.config.FieldManager.allow(r3)
            if (r3 == 0) goto L7c
            if (r7 != 0) goto L23
            return r1
        L23:
            java.lang.String r3 = "phone"
            java.lang.Object r3 = r7.getSystemService(r3)
            android.telephony.TelephonyManager r3 = (android.telephony.TelephonyManager) r3
            if (r3 == 0) goto L7c
            r4 = 1
            java.lang.String r5 = "android.permission.READ_PHONE_STATE"
            boolean r7 = checkPermission(r7, r5)     // Catch: java.lang.Throwable -> L63
            if (r7 == 0) goto L5e
            java.lang.String r7 = r3.getDeviceId()     // Catch: java.lang.Throwable -> L63
            boolean r1 = com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG     // Catch: java.lang.Throwable -> L5b
            if (r1 == 0) goto L59
            java.lang.String r1 = com.umeng.commonsdk.statistics.common.DeviceConfig.LOG_TAG     // Catch: java.lang.Throwable -> L5b
            java.lang.Object[] r3 = new java.lang.Object[r4]     // Catch: java.lang.Throwable -> L5b
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L5b
            r5.<init>()     // Catch: java.lang.Throwable -> L5b
            java.lang.String r6 = "getDeviceId, IMEI: "
            r5.append(r6)     // Catch: java.lang.Throwable -> L5b
            r5.append(r7)     // Catch: java.lang.Throwable -> L5b
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L5b
            r6 = 0
            r3[r6] = r5     // Catch: java.lang.Throwable -> L5b
            com.umeng.commonsdk.statistics.common.MLog.i(r1, r3)     // Catch: java.lang.Throwable -> L5b
        L59:
            r1 = r7
            goto L5e
        L5b:
            r0 = move-exception
            r1 = r7
            goto L64
        L5e:
            if (r0 < r2) goto L7c
        L60:
            com.umeng.commonsdk.statistics.common.DeviceConfig.sImeiOrMeidFlag = r4
            goto L7c
        L63:
            r0 = move-exception
        L64:
            boolean r7 = com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG     // Catch: java.lang.Throwable -> L74
            if (r7 == 0) goto L6f
            java.lang.String r7 = com.umeng.commonsdk.statistics.common.DeviceConfig.LOG_TAG     // Catch: java.lang.Throwable -> L74
            java.lang.String r3 = "No IMEI."
            com.umeng.commonsdk.statistics.common.MLog.w(r7, r3, r0)     // Catch: java.lang.Throwable -> L74
        L6f:
            int r7 = android.os.Build.VERSION.SDK_INT
            if (r7 < r2) goto L7c
            goto L60
        L74:
            r7 = move-exception
            int r0 = android.os.Build.VERSION.SDK_INT
            if (r0 < r2) goto L7b
            com.umeng.commonsdk.statistics.common.DeviceConfig.sImeiOrMeidFlag = r4
        L7b:
            throw r7
        L7c:
            com.umeng.commonsdk.statistics.common.DeviceConfig.sImei = r1
            java.lang.String r7 = com.umeng.commonsdk.statistics.common.DeviceConfig.sImei
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.statistics.common.DeviceConfig.getIMEI(android.content.Context):java.lang.String");
    }

    public static String getIdfa(Context context) {
        if (!TextUtils.isEmpty(sIDFA)) {
            return sIDFA;
        }
        String strA = "";
        try {
            if (!FieldManager.allow(com.umeng.commonsdk.utils.b.f23540w)) {
                return "";
            }
            strA = a.a(context);
            sIDFA = strA;
            return strA;
        } catch (Throwable unused) {
            return strA;
        }
    }

    public static String getImei(Context context) {
        int i2;
        TelephonyManager telephonyManager;
        if (!TextUtils.isEmpty(sImei)) {
            return sImei;
        }
        String deviceId = null;
        try {
            i2 = Build.VERSION.SDK_INT;
        } catch (Throwable th) {
            try {
                if (AnalyticsConstants.UM_DEBUG) {
                    MLog.w("No IMEI.", th);
                }
                if (Build.VERSION.SDK_INT >= 29) {
                }
            } catch (Throwable th2) {
                if (Build.VERSION.SDK_INT >= 29) {
                    sImeiOrMeidFlag = true;
                }
                throw th2;
            }
        }
        if (i2 >= 29 && sImeiOrMeidFlag) {
            if (i2 >= 29) {
                sImeiOrMeidFlag = true;
            }
            return null;
        }
        if (FieldManager.allow(com.umeng.commonsdk.utils.b.f23524g) && context != null && (telephonyManager = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE)) != null && checkPermission(context, Permission.READ_PHONE_STATE)) {
            deviceId = telephonyManager.getDeviceId();
        }
        if (i2 >= 29) {
            sImeiOrMeidFlag = true;
        }
        sImei = deviceId;
        return deviceId;
    }

    public static String getImeiNew(Context context) {
        int i2;
        TelephonyManager telephonyManager;
        if (!TextUtils.isEmpty(sImei)) {
            return sImei;
        }
        String deviceId = null;
        try {
            i2 = Build.VERSION.SDK_INT;
        } catch (Throwable th) {
            try {
                if (AnalyticsConstants.UM_DEBUG) {
                    MLog.w("No IMEI.", th);
                }
                if (Build.VERSION.SDK_INT >= 29) {
                }
            } finally {
                if (Build.VERSION.SDK_INT >= 29) {
                    sImeiOrMeidFlag = true;
                }
            }
        }
        if (i2 >= 29 && sImeiOrMeidFlag) {
            if (i2 >= 29) {
                sImeiOrMeidFlag = true;
            }
            return null;
        }
        if (FieldManager.allow(com.umeng.commonsdk.utils.b.f23524g) && context != null && (telephonyManager = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE)) != null && checkPermission(context, Permission.READ_PHONE_STATE)) {
            if (i2 >= 26) {
                try {
                    Method method = telephonyManager.getClass().getMethod("getImei", new Class[0]);
                    method.setAccessible(true);
                    deviceId = (String) method.invoke(telephonyManager, new Object[0]);
                } catch (Throwable unused) {
                }
                if (TextUtils.isEmpty(deviceId)) {
                    deviceId = telephonyManager.getDeviceId();
                }
            } else {
                deviceId = telephonyManager.getDeviceId();
            }
        }
        sImei = deviceId;
        return deviceId;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0045 A[PHI: r0
      0x0045: PHI (r0v4 java.lang.String) = (r0v5 java.lang.String), (r0v6 java.lang.String) binds: [B:29:0x004a, B:26:0x0043] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getImsi(android.content.Context r6) {
        /*
            java.lang.String r0 = com.umeng.commonsdk.statistics.common.DeviceConfig.sImsi
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto Lb
            java.lang.String r6 = com.umeng.commonsdk.statistics.common.DeviceConfig.sImsi
            return r6
        Lb:
            r0 = 0
            if (r6 != 0) goto Lf
            return r0
        Lf:
            int r1 = android.os.Build.VERSION.SDK_INT
            r2 = 29
            if (r1 < r2) goto L1a
            boolean r3 = com.umeng.commonsdk.statistics.common.DeviceConfig.sImsiFlag
            if (r3 == 0) goto L1a
            return r0
        L1a:
            java.lang.String r3 = "phone"
            java.lang.Object r3 = r6.getSystemService(r3)
            android.telephony.TelephonyManager r3 = (android.telephony.TelephonyManager) r3
            java.lang.String r4 = "internal_imsi"
            boolean r4 = com.umeng.commonsdk.config.FieldManager.allow(r4)
            if (r4 == 0) goto L4d
            r4 = 1
            java.lang.String r5 = "android.permission.READ_PHONE_STATE"
            boolean r6 = checkPermission(r6, r5)     // Catch: java.lang.Throwable -> L48
            if (r6 == 0) goto L43
            if (r3 == 0) goto L43
            boolean r6 = com.umeng.commonsdk.statistics.common.DeviceConfig.hasReadImsi     // Catch: java.lang.Throwable -> L48
            if (r6 != 0) goto L43
            java.lang.String r6 = r3.getSubscriberId()     // Catch: java.lang.Throwable -> L48
            com.umeng.commonsdk.statistics.common.DeviceConfig.hasReadImsi = r4     // Catch: java.lang.Throwable -> L41
            r0 = r6
            goto L43
        L41:
            r0 = r6
            goto L48
        L43:
            if (r1 < r2) goto L4d
        L45:
            com.umeng.commonsdk.statistics.common.DeviceConfig.sImsiFlag = r4
            goto L4d
        L48:
            int r6 = android.os.Build.VERSION.SDK_INT
            if (r6 < r2) goto L4d
            goto L45
        L4d:
            com.umeng.commonsdk.statistics.common.DeviceConfig.sImsi = r0
            java.lang.String r6 = com.umeng.commonsdk.statistics.common.DeviceConfig.sImsi
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.statistics.common.DeviceConfig.getImsi(android.content.Context):java.lang.String");
    }

    private static Locale getLocale(Context context) {
        Locale locale;
        if (context == null) {
            return Locale.getDefault();
        }
        try {
            Configuration configuration = new Configuration();
            configuration.setToDefaults();
            Settings.System.getConfiguration(context.getContentResolver(), configuration);
            locale = configuration.locale;
        } catch (Throwable unused) {
            MLog.e(LOG_TAG, "fail to read user config locale");
            locale = null;
        }
        return locale == null ? Locale.getDefault() : locale;
    }

    public static String[] getLocaleInfo(Context context) {
        String[] strArr = {"Unknown", "Unknown"};
        if (context == null) {
            return strArr;
        }
        try {
            Locale locale = getLocale(context);
            if (locale != null) {
                strArr[0] = locale.getCountry();
                strArr[1] = locale.getLanguage();
            }
            if (TextUtils.isEmpty(strArr[0])) {
                strArr[0] = "Unknown";
            }
            if (TextUtils.isEmpty(strArr[1])) {
                strArr[1] = "Unknown";
            }
            return strArr;
        } catch (Throwable th) {
            MLog.e(LOG_TAG, "error in getLocaleInfo", th);
            return strArr;
        }
    }

    public static String getMCCMNC(Context context) {
        if (context == null) {
            return null;
        }
        if (getImsi(context) == null) {
            return null;
        }
        int i2 = context.getResources().getConfiguration().mcc;
        int i3 = context.getResources().getConfiguration().mnc;
        if (i2 != 0) {
            String strValueOf = String.valueOf(i3);
            if (i3 < 10) {
                strValueOf = String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i3));
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(String.valueOf(i2));
            stringBuffer.append(strValueOf);
            return stringBuffer.toString();
        }
        return null;
    }

    public static String getMac(Context context) {
        if (!TextUtils.isEmpty(sWifiMac)) {
            return sWifiMac;
        }
        String macByJavaAPI = "";
        if (FieldManager.allow(com.umeng.commonsdk.utils.b.f23525h)) {
            if (context == null) {
                return "";
            }
            if (Build.VERSION.SDK_INT == 23) {
                macByJavaAPI = getMacByJavaAPI();
                if (TextUtils.isEmpty(macByJavaAPI)) {
                    macByJavaAPI = AnalyticsConstants.CHECK_DEVICE ? getMacShell() : getMacBySystemInterface(context);
                }
            } else {
                macByJavaAPI = getMacByJavaAPI();
                if (TextUtils.isEmpty(macByJavaAPI)) {
                    macByJavaAPI = getMacBySystemInterface(context);
                }
            }
        }
        sWifiMac = macByJavaAPI;
        return sWifiMac;
    }

    private static String getMacByJavaAPI() {
        try {
            if (!FieldManager.allow(com.umeng.commonsdk.utils.b.f23525h)) {
                return null;
            }
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                if ("wlan0".equals(networkInterfaceNextElement.getName()) || "eth0".equals(networkInterfaceNextElement.getName())) {
                    byte[] hardwareAddress = networkInterfaceNextElement.getHardwareAddress();
                    if (hardwareAddress != null && hardwareAddress.length != 0) {
                        StringBuilder sb = new StringBuilder();
                        for (byte b3 : hardwareAddress) {
                            sb.append(String.format("%02X:", Byte.valueOf(b3)));
                        }
                        if (sb.length() > 0) {
                            sb.deleteCharAt(sb.length() - 1);
                        }
                        return sb.toString().toLowerCase(Locale.getDefault());
                    }
                    return null;
                }
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    private static String getMacBySystemInterface(Context context) {
        if (context == null) {
            return "";
        }
        try {
            if (FieldManager.allow(com.umeng.commonsdk.utils.b.f23525h)) {
                WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
                if (checkPermission(context, "android.permission.ACCESS_WIFI_STATE")) {
                    return wifiManager != null ? wifiManager.getConnectionInfo().getMacAddress() : "";
                }
                if (AnalyticsConstants.UM_DEBUG) {
                    MLog.w(LOG_TAG, "Could not get mac address.[no permission android.permission.ACCESS_WIFI_STATE");
                }
            }
            return "";
        } catch (Throwable th) {
            if (AnalyticsConstants.UM_DEBUG) {
                MLog.w(LOG_TAG, "Could not get mac address." + th.toString());
            }
            return "";
        }
    }

    private static String getMacShell() {
        String strReaMac;
        try {
            if (!FieldManager.allow(com.umeng.commonsdk.utils.b.f23525h)) {
                return null;
            }
            String[] strArr = {"/sys/class/net/wlan0/address", "/sys/class/net/eth0/address", "/sys/devices/virtual/net/wlan0/address"};
            for (int i2 = 0; i2 < 3; i2++) {
                try {
                    strReaMac = reaMac(strArr[i2]);
                } catch (Throwable th) {
                    if (AnalyticsConstants.UM_DEBUG) {
                        MLog.e(LOG_TAG, "open file  Failed", th);
                    }
                }
                if (strReaMac != null) {
                    return strReaMac;
                }
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String getMeid(Context context) {
        String strMeid = null;
        if (context == null || ((TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE)) == null) {
            return null;
        }
        if (FieldManager.allow(com.umeng.commonsdk.utils.b.ak)) {
            try {
                if (checkPermission(context, Permission.READ_PHONE_STATE)) {
                    if (Build.VERSION.SDK_INT < 26) {
                        strMeid = getIMEI(context);
                    } else {
                        strMeid = meid(context);
                        if (TextUtils.isEmpty(strMeid)) {
                            strMeid = getIMEI(context);
                        }
                    }
                }
            } catch (Throwable unused) {
            }
        }
        return strMeid;
    }

    public static String[] getNetworkAccessMode(Context context) {
        String[] strArr = {"", ""};
        if (context == null) {
            return strArr;
        }
        if (!checkPermission(context, "android.permission.ACCESS_NETWORK_STATE")) {
            strArr[0] = "";
            return strArr;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            strArr[0] = "";
            return strArr;
        }
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
        if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
            strArr[0] = "Wi-Fi";
            return strArr;
        }
        NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(0);
        if (networkInfo2 != null && networkInfo2.getState() == NetworkInfo.State.CONNECTED) {
            strArr[0] = "2G/3G";
            strArr[1] = networkInfo2.getSubtypeName();
        }
        return strArr;
    }

    public static String getNetworkOperatorName(Context context) {
        if (context == null) {
            return "";
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
            if (checkPermission(context, Permission.READ_PHONE_STATE) && telephonyManager != null) {
                return telephonyManager.getNetworkOperatorName();
            }
        } catch (Throwable unused) {
        }
        return "";
    }

    public static int getNetworkType(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
            if (checkPermission(context, Permission.READ_PHONE_STATE)) {
                return telephonyManager.getNetworkType();
            }
            return 0;
        } catch (Exception unused) {
            return -100;
        }
    }

    public static String getOaid(Context context) {
        if (!TextUtils.isEmpty(sOAID)) {
            return sOAID;
        }
        if (!FieldManager.allow(com.umeng.commonsdk.utils.b.G)) {
            return "";
        }
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(h.f23379a, 0);
            if (sharedPreferences == null) {
                return "";
            }
            String string = sharedPreferences.getString(h.f23380b, "");
            try {
                sOAID = string;
            } catch (Throwable unused) {
            }
            return string;
        } catch (Throwable unused2) {
            return "";
        }
    }

    public static String getPackageName(Context context) {
        if (context == null) {
            return null;
        }
        return context.getPackageName();
    }

    public static String getRegisteredOperator(Context context) {
        if (context == null) {
            return null;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
            if (!checkPermission(context, Permission.READ_PHONE_STATE) || telephonyManager == null) {
                return null;
            }
            return telephonyManager.getNetworkOperator();
        } catch (Throwable unused) {
            return null;
        }
    }

    public static int[] getResolutionArray(Context context) {
        if (context == null) {
            return null;
        }
        int[] iArr = new int[2];
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (windowManager != null) {
            Display defaultDisplay = windowManager.getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            try {
                Method method = Class.forName("android.view.Display").getMethod("getRealMetrics", DisplayMetrics.class);
                if (method != null) {
                    method.invoke(defaultDisplay, displayMetrics);
                    int i2 = displayMetrics.widthPixels;
                    int i3 = displayMetrics.heightPixels;
                    if (i2 > i3) {
                        iArr[0] = i3;
                        iArr[1] = i2;
                    } else {
                        iArr[0] = i2;
                        iArr[1] = i3;
                    }
                    iArr[0] = i2;
                    iArr[1] = i3;
                    return iArr;
                }
            } catch (Throwable unused) {
            }
        }
        return null;
    }

    public static String getSecondSimIMEi(Context context) {
        TelephonyManager telephonyManager;
        if (!TextUtils.isEmpty(sSecondImei)) {
            return sSecondImei;
        }
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 29 && sSecondImeiFlag) {
            return null;
        }
        if (context != null && FieldManager.allow(com.umeng.commonsdk.utils.b.am) && UMUtils.checkPermission(context, Permission.READ_PHONE_STATE)) {
            try {
                telephonyManager = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
            } catch (Throwable unused) {
                if (Build.VERSION.SDK_INT >= 29) {
                }
            }
            if (telephonyManager == null) {
                if (i2 >= 29) {
                    sSecondImeiFlag = true;
                }
                return null;
            }
            Class<?> cls = telephonyManager.getClass();
            if (((Integer) cls.getMethod("getPhoneCount", new Class[0]).invoke(telephonyManager, new Object[0])).intValue() == 2) {
                sSecondImei = (String) cls.getMethod("getDeviceId", Integer.TYPE).invoke(telephonyManager, 2);
            }
            if (i2 >= 29) {
                sSecondImeiFlag = true;
            }
        }
        return sSecondImei;
    }

    public static String getSerial() {
        return getSerialNo();
    }

    private static String getSerialNo() {
        String str;
        if (!TextUtils.isEmpty(sSerialNo)) {
            return sSerialNo;
        }
        int i2 = Build.VERSION.SDK_INT;
        String str2 = "";
        if (i2 >= 29 && sSerialFlag) {
            return "";
        }
        if (FieldManager.allow(com.umeng.commonsdk.utils.b.f23527j)) {
            if (i2 >= 26) {
                try {
                    Class<?> cls = Class.forName("android.os.Build");
                    str = (String) cls.getMethod("getSerial", new Class[0]).invoke(cls, new Object[0]);
                } catch (Throwable unused) {
                }
            } else {
                try {
                    str = Build.SERIAL;
                } catch (Throwable unused2) {
                    if (Build.VERSION.SDK_INT >= 29) {
                    }
                }
            }
            str2 = str;
            sSerialNo = str2;
            if (Build.VERSION.SDK_INT >= 29) {
                sSerialFlag = true;
            }
        }
        if (AnalyticsConstants.UM_DEBUG) {
            MLog.i(LOG_TAG, "getDeviceId, serial no: " + str2);
        }
        return sSerialNo;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0047  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getSimICCID(android.content.Context r5) {
        /*
            java.lang.String r0 = com.umeng.commonsdk.statistics.common.DeviceConfig.sSimSerialNumber
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto Lb
            java.lang.String r5 = com.umeng.commonsdk.statistics.common.DeviceConfig.sSimSerialNumber
            return r5
        Lb:
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 0
            r2 = 29
            if (r0 < r2) goto L17
            boolean r3 = com.umeng.commonsdk.statistics.common.DeviceConfig.sSimSerialNumberFlag
            if (r3 == 0) goto L17
            return r1
        L17:
            java.lang.String r3 = "inner_iccid"
            boolean r3 = com.umeng.commonsdk.config.FieldManager.allow(r3)
            if (r3 == 0) goto L49
            r3 = 1
            if (r5 == 0) goto L45
            java.lang.String r4 = "android.permission.READ_PHONE_STATE"
            boolean r4 = com.umeng.commonsdk.utils.UMUtils.checkPermission(r5, r4)     // Catch: java.lang.Throwable -> L40
            if (r4 == 0) goto L45
            java.lang.String r4 = "phone"
            java.lang.Object r5 = r5.getSystemService(r4)     // Catch: java.lang.Throwable -> L40
            android.telephony.TelephonyManager r5 = (android.telephony.TelephonyManager) r5     // Catch: java.lang.Throwable -> L40
            if (r5 != 0) goto L39
            if (r0 < r2) goto L38
            com.umeng.commonsdk.statistics.common.DeviceConfig.sSimSerialNumberFlag = r3
        L38:
            return r1
        L39:
            java.lang.String r5 = r5.getSimSerialNumber()     // Catch: java.lang.Throwable -> L40
            com.umeng.commonsdk.statistics.common.DeviceConfig.sSimSerialNumber = r5     // Catch: java.lang.Throwable -> L40
            goto L45
        L40:
            int r5 = android.os.Build.VERSION.SDK_INT
            if (r5 < r2) goto L49
            goto L47
        L45:
            if (r0 < r2) goto L49
        L47:
            com.umeng.commonsdk.statistics.common.DeviceConfig.sSimSerialNumberFlag = r3
        L49:
            java.lang.String r5 = com.umeng.commonsdk.statistics.common.DeviceConfig.sSimSerialNumber
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.statistics.common.DeviceConfig.getSimICCID(android.content.Context):java.lang.String");
    }

    public static String getSubOSName(Context context) {
        Properties buildProp = getBuildProp();
        try {
            String property = buildProp.getProperty(KEY_MIUI_VERSION_NAME);
            return TextUtils.isEmpty(property) ? isFlyMe() ? "Flyme" : isEmui(buildProp) ? "Emui" : !TextUtils.isEmpty(getYunOSVersion(buildProp)) ? "YunOS" : property : RomUtils.ROM_MIUI;
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String getSubOSVersion(Context context) {
        Properties buildProp = getBuildProp();
        try {
            String property = buildProp.getProperty(KEY_MIUI_VERSION_NAME);
            if (!TextUtils.isEmpty(property)) {
                return property;
            }
            try {
                property = isFlyMe() ? getFlymeVersion(buildProp) : isEmui(buildProp) ? getEmuiVersion(buildProp) : getYunOSVersion(buildProp);
                return property;
            } catch (Throwable unused) {
                return property;
            }
        } catch (Throwable unused2) {
            return null;
        }
    }

    public static int getTimeZone(Context context) {
        if (context == null) {
            return 8;
        }
        try {
            Calendar calendar = Calendar.getInstance(getLocale(context));
            if (calendar != null) {
                return calendar.getTimeZone().getRawOffset() / 3600000;
            }
        } catch (Throwable th) {
            MLog.i(LOG_TAG, "error in getTimeZone", th);
        }
        return 8;
    }

    private static String getYunOSVersion(Properties properties) {
        try {
            String property = properties.getProperty("ro.yunos.version");
            if (TextUtils.isEmpty(property)) {
                return null;
            }
            return property;
        } catch (Throwable unused) {
            return null;
        }
    }

    public static boolean isChineseAera(Context context) {
        String strImprintProperty;
        if (context == null) {
            return false;
        }
        try {
            strImprintProperty = UMEnvelopeBuild.imprintProperty(context, am.O, "");
        } catch (Throwable unused) {
        }
        if (!TextUtils.isEmpty(strImprintProperty)) {
            return strImprintProperty.equals(AdvanceSetting.CLEAR_NOTIFICATION);
        }
        if (getImsi(context) != null) {
            int i2 = context.getResources().getConfiguration().mcc;
            if (i2 != 460 && i2 != 461) {
                if (i2 == 0) {
                    String str = getLocaleInfo(context)[0];
                    if (TextUtils.isEmpty(str) || !str.equalsIgnoreCase(AdvanceSetting.CLEAR_NOTIFICATION)) {
                    }
                }
            }
            return true;
        }
        String str2 = getLocaleInfo(context)[0];
        if (!TextUtils.isEmpty(str2) && str2.equalsIgnoreCase(AdvanceSetting.CLEAR_NOTIFICATION)) {
            return true;
        }
        return false;
    }

    private static boolean isEmui(Properties properties) {
        return properties.getProperty(KEY_EMUI_VERSION_CODE, null) != null;
    }

    private static boolean isFlyMe() {
        try {
            Build.class.getMethod("hasSmartBar", new Class[0]);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static boolean isHarmony(Context context) {
        try {
            return context.getString(Resources.getSystem().getIdentifier("config_os_brand", TypedValues.Custom.S_STRING, "android")).equals("harmony");
        } catch (Throwable unused) {
            return false;
        }
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager;
        NetworkInfo activeNetworkInfo;
        if (context == null) {
            return false;
        }
        try {
            if (checkPermission(context, "android.permission.ACCESS_NETWORK_STATE") && (connectivityManager = (ConnectivityManager) context.getSystemService("connectivity")) != null && (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) != null) {
                return activeNetworkInfo.isConnectedOrConnecting();
            }
        } catch (Throwable unused) {
        }
        return false;
    }

    public static boolean isWiFiAvailable(Context context) {
        if (context == null) {
            return false;
        }
        return "Wi-Fi".equals(getNetworkAccessMode(context)[0]);
    }

    private static String meid(Context context) {
        if (TextUtils.isEmpty(sMeid)) {
            return sMeid;
        }
        int i2 = Build.VERSION.SDK_INT;
        String str = null;
        if ((i2 >= 29 && sImeiOrMeidFlag) || context == null) {
            return null;
        }
        try {
            Object objInvoke = Class.forName("android.telephony.TelephonyManager").getMethod("getMeid", new Class[0]).invoke(null, new Object[0]);
            if (objInvoke != null && (objInvoke instanceof String)) {
                str = (String) objInvoke;
            }
        } catch (Throwable th) {
            try {
                ULog.e("meid:" + th.getMessage());
                if (Build.VERSION.SDK_INT >= 29) {
                }
            } catch (Throwable th2) {
                if (Build.VERSION.SDK_INT >= 29) {
                    sImeiOrMeidFlag = true;
                }
                throw th2;
            }
        }
        if (i2 >= 29) {
            sImeiOrMeidFlag = true;
        }
        sMeid = str;
        return sMeid;
    }

    private static String reaMac(String str) {
        BufferedReader bufferedReader;
        String line = null;
        try {
            FileReader fileReader = new FileReader(str);
            try {
                bufferedReader = new BufferedReader(fileReader, 1024);
            } catch (Throwable th) {
                th = th;
                bufferedReader = null;
            }
            try {
                line = bufferedReader.readLine();
                try {
                    fileReader.close();
                } catch (Throwable unused) {
                }
                bufferedReader.close();
            } catch (Throwable th2) {
                th = th2;
                try {
                    fileReader.close();
                } catch (Throwable unused2) {
                }
                if (bufferedReader == null) {
                    throw th;
                }
                try {
                    bufferedReader.close();
                    throw th;
                } catch (Throwable unused3) {
                    throw th;
                }
            }
        } catch (Throwable unused4) {
        }
        return line;
    }

    private static int reflectMetrics(Object obj, String str) {
        try {
            Field declaredField = DisplayMetrics.class.getDeclaredField(str);
            declaredField.setAccessible(true);
            return declaredField.getInt(obj);
        } catch (Throwable unused) {
            return -1;
        }
    }
}
