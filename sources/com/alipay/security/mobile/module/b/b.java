package com.alipay.security.mobile.module.b;

import android.app.KeyguardManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import androidx.core.app.NotificationCompat;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.hjq.permissions.Permission;
import com.mobile.auth.gatewayauth.Constant;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import com.umeng.analytics.pro.am;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class b {

    /* renamed from: a, reason: collision with root package name */
    private static b f3418a = new b();

    private b() {
    }

    private static String A() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddressNextElement = inetAddresses.nextElement();
                    if (!inetAddressNextElement.isLoopbackAddress() && (inetAddressNextElement instanceof Inet4Address)) {
                        return inetAddressNextElement.getHostAddress().toString();
                    }
                }
            }
            return "";
        } catch (Throwable unused) {
            return "";
        }
    }

    public static b a() {
        return f3418a;
    }

    private static String a(BluetoothAdapter bluetoothAdapter) {
        Object obj;
        try {
            Field declaredField = BluetoothAdapter.class.getDeclaredField("mService");
            declaredField.setAccessible(true);
            obj = declaredField.get(bluetoothAdapter);
        } catch (Throwable unused) {
        }
        if (obj == null) {
            return null;
        }
        Method declaredMethod = obj.getClass().getDeclaredMethod("getAddress", new Class[0]);
        declaredMethod.setAccessible(true);
        Object objInvoke = declaredMethod.invoke(obj, new Object[0]);
        if (objInvoke != null && (objInvoke instanceof String)) {
            return (String) objInvoke;
        }
        return null;
    }

    public static String a(Context context) {
        if (a(context, Permission.READ_PHONE_STATE)) {
            return "";
        }
        String deviceId = null;
        if (context != null) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
                if (telephonyManager != null) {
                    deviceId = telephonyManager.getDeviceId();
                }
            } catch (Throwable unused) {
            }
        }
        return deviceId == null ? "" : deviceId;
    }

    private static boolean a(Context context, String str) {
        return !(context.getPackageManager().checkPermission(str, context.getPackageName()) == 0);
    }

    public static String b() {
        long availableBlocks;
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            availableBlocks = statFs.getAvailableBlocks() * statFs.getBlockSize();
        } catch (Throwable unused) {
            availableBlocks = 0;
        }
        return String.valueOf(availableBlocks);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x001c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String b(android.content.Context r2) {
        /*
            java.lang.String r0 = "android.permission.READ_PHONE_STATE"
            boolean r0 = a(r2, r0)
            java.lang.String r1 = ""
            if (r0 == 0) goto Lb
            return r1
        Lb:
            if (r2 == 0) goto L1c
            java.lang.String r0 = "phone"
            java.lang.Object r2 = r2.getSystemService(r0)     // Catch: java.lang.Throwable -> L1c
            android.telephony.TelephonyManager r2 = (android.telephony.TelephonyManager) r2     // Catch: java.lang.Throwable -> L1c
            if (r2 == 0) goto L1c
            java.lang.String r2 = r2.getSubscriberId()     // Catch: java.lang.Throwable -> L1c
            goto L1d
        L1c:
            r2 = r1
        L1d:
            if (r2 != 0) goto L20
            goto L21
        L20:
            r1 = r2
        L21:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.b.b.b(android.content.Context):java.lang.String");
    }

    public static String c() {
        long blockSize = 0;
        try {
            if ("mounted".equals(Environment.getExternalStorageState())) {
                StatFs statFs = new StatFs(com.alipay.security.mobile.module.a.a.a().getPath());
                blockSize = statFs.getBlockSize() * statFs.getAvailableBlocks();
            }
        } catch (Throwable unused) {
        }
        return String.valueOf(blockSize);
    }

    public static String c(Context context) {
        int i2 = 0;
        try {
            i2 = Settings.System.getInt(context.getContentResolver(), "airplane_mode_on", 0);
        } catch (Throwable unused) {
        }
        return i2 == 1 ? "1" : "0";
    }

    public static String d() {
        return "";
    }

    public static String d(Context context) {
        JSONObject jSONObject = new JSONObject();
        try {
            AudioManager audioManager = (AudioManager) context.getSystemService("audio");
            int i2 = audioManager.getRingerMode() == 0 ? 1 : 0;
            int streamVolume = audioManager.getStreamVolume(0);
            int streamVolume2 = audioManager.getStreamVolume(1);
            int streamVolume3 = audioManager.getStreamVolume(2);
            int streamVolume4 = audioManager.getStreamVolume(3);
            int streamVolume5 = audioManager.getStreamVolume(4);
            jSONObject.put("ringermode", String.valueOf(i2));
            jSONObject.put("call", String.valueOf(streamVolume));
            jSONObject.put("system", String.valueOf(streamVolume2));
            jSONObject.put("ring", String.valueOf(streamVolume3));
            jSONObject.put("music", String.valueOf(streamVolume4));
            jSONObject.put(NotificationCompat.CATEGORY_ALARM, String.valueOf(streamVolume5));
        } catch (Throwable unused) {
        }
        return jSONObject.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x002d, code lost:
    
        r0 = r6.substring(r6.indexOf(":") + 1, r6.length()).trim();
     */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0063 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:57:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String e() {
        /*
            java.lang.String r0 = "0000000000000000"
            r1 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L52
            java.io.File r3 = new java.io.File     // Catch: java.lang.Throwable -> L52
            java.lang.String r4 = "/proc/cpuinfo"
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L52
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L52
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L50
            r3.<init>(r2)     // Catch: java.lang.Throwable -> L50
            java.io.LineNumberReader r4 = new java.io.LineNumberReader     // Catch: java.lang.Throwable -> L54
            r4.<init>(r3)     // Catch: java.lang.Throwable -> L54
            r1 = 1
            r5 = r1
        L1b:
            r6 = 100
            if (r5 >= r6) goto L46
            java.lang.String r6 = r4.readLine()     // Catch: java.lang.Throwable -> L44
            if (r6 == 0) goto L46
            java.lang.String r7 = "Serial"
            int r7 = r6.indexOf(r7)     // Catch: java.lang.Throwable -> L44
            if (r7 < 0) goto L41
            java.lang.String r5 = ":"
            int r5 = r6.indexOf(r5)     // Catch: java.lang.Throwable -> L44
            int r5 = r5 + r1
            int r1 = r6.length()     // Catch: java.lang.Throwable -> L44
            java.lang.String r1 = r6.substring(r5, r1)     // Catch: java.lang.Throwable -> L44
            java.lang.String r0 = r1.trim()     // Catch: java.lang.Throwable -> L44
            goto L46
        L41:
            int r5 = r5 + 1
            goto L1b
        L44:
            r1 = r4
            goto L54
        L46:
            r4.close()     // Catch: java.lang.Throwable -> L49
        L49:
            r3.close()     // Catch: java.lang.Throwable -> L4c
        L4c:
            r2.close()     // Catch: java.lang.Throwable -> L61
            goto L61
        L50:
            r3 = r1
            goto L54
        L52:
            r2 = r1
            r3 = r2
        L54:
            if (r1 == 0) goto L59
            r1.close()     // Catch: java.lang.Throwable -> L59
        L59:
            if (r3 == 0) goto L5e
            r3.close()     // Catch: java.lang.Throwable -> L5e
        L5e:
            if (r2 == 0) goto L61
            goto L4c
        L61:
            if (r0 != 0) goto L65
            java.lang.String r0 = ""
        L65:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.b.b.e():java.lang.String");
    }

    public static String e(Context context) {
        String networkOperatorName = null;
        if (context != null) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
                if (telephonyManager != null) {
                    networkOperatorName = telephonyManager.getNetworkOperatorName();
                }
            } catch (Throwable unused) {
            }
        }
        return (networkOperatorName == null || "null".equals(networkOperatorName)) ? "" : networkOperatorName;
    }

    public static String f(Context context) {
        List<Sensor> sensorList;
        String strE = null;
        if (context != null) {
            try {
                SensorManager sensorManager = (SensorManager) context.getSystemService(am.ac);
                if (sensorManager != null && (sensorList = sensorManager.getSensorList(-1)) != null && sensorList.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (Sensor sensor : sensorList) {
                        sb.append(sensor.getName());
                        sb.append(sensor.getVersion());
                        sb.append(sensor.getVendor());
                    }
                    strE = com.alipay.security.mobile.module.a.a.e(sb.toString());
                }
            } catch (Throwable unused) {
            }
        }
        return strE == null ? "" : strE;
    }

    public static String g() {
        String strX = x();
        return !com.alipay.security.mobile.module.a.a.a(strX) ? strX : y();
    }

    public static String g(Context context) {
        List<Sensor> sensorList;
        JSONArray jSONArray = new JSONArray();
        if (context != null) {
            try {
                SensorManager sensorManager = (SensorManager) context.getSystemService(am.ac);
                if (sensorManager != null && (sensorList = sensorManager.getSensorList(-1)) != null && sensorList.size() > 0) {
                    for (Sensor sensor : sensorList) {
                        if (sensor != null) {
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("name", sensor.getName());
                            jSONObject.put("version", sensor.getVersion());
                            jSONObject.put(Constant.LOGIN_ACTIVITY_VENDOR_KEY, sensor.getVendor());
                            jSONArray.put(jSONObject);
                        }
                    }
                }
            } catch (Throwable unused) {
            }
        }
        return jSONArray.toString();
    }

    public static String h() {
        BufferedReader bufferedReader;
        FileReader fileReader;
        String[] strArrSplit;
        FileReader fileReader2 = null;
        try {
            fileReader = new FileReader("/proc/cpuinfo");
            try {
                bufferedReader = new BufferedReader(fileReader);
                try {
                    strArrSplit = bufferedReader.readLine().split(":\\s+", 2);
                } catch (Throwable unused) {
                    fileReader2 = fileReader;
                    if (fileReader2 != null) {
                        try {
                            fileReader2.close();
                        } catch (Throwable unused2) {
                        }
                    }
                    if (bufferedReader == null) {
                        return "";
                    }
                    try {
                        bufferedReader.close();
                        return "";
                    } catch (Throwable unused3) {
                        return "";
                    }
                }
            } catch (Throwable unused4) {
                bufferedReader = null;
            }
        } catch (Throwable unused5) {
            bufferedReader = null;
        }
        if (strArrSplit == null || strArrSplit.length <= 1) {
            try {
                fileReader.close();
            } catch (Throwable unused6) {
            }
            bufferedReader.close();
            return "";
        }
        String str = strArrSplit[1];
        try {
            fileReader.close();
        } catch (Throwable unused7) {
        }
        try {
            bufferedReader.close();
        } catch (Throwable unused8) {
        }
        return str;
    }

    public static String h(Context context) {
        try {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            return Integer.toString(displayMetrics.widthPixels) + "*" + Integer.toString(displayMetrics.heightPixels);
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String i() {
        BufferedReader bufferedReader;
        FileReader fileReader = null;
        try {
            FileReader fileReader2 = new FileReader("/proc/meminfo");
            try {
                bufferedReader = new BufferedReader(fileReader2, 8192);
                try {
                    j = bufferedReader.readLine() != null ? Integer.parseInt(r3.split("\\s+")[1]) : 0L;
                    try {
                        fileReader2.close();
                    } catch (Throwable unused) {
                    }
                } catch (Throwable unused2) {
                    fileReader = fileReader2;
                    if (fileReader != null) {
                        try {
                            fileReader.close();
                        } catch (Throwable unused3) {
                        }
                    }
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    return String.valueOf(j);
                }
            } catch (Throwable unused4) {
                bufferedReader = null;
            }
        } catch (Throwable unused5) {
            bufferedReader = null;
        }
        try {
            bufferedReader.close();
        } catch (Throwable unused6) {
        }
        return String.valueOf(j);
    }

    public static String i(Context context) {
        try {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            StringBuilder sb = new StringBuilder();
            sb.append(displayMetrics.widthPixels);
            return sb.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String j() {
        long blockCount;
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            blockCount = statFs.getBlockCount() * statFs.getBlockSize();
        } catch (Throwable unused) {
            blockCount = 0;
        }
        return String.valueOf(blockCount);
    }

    public static String j(Context context) {
        try {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            StringBuilder sb = new StringBuilder();
            sb.append(displayMetrics.heightPixels);
            return sb.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String k() {
        long blockSize = 0;
        try {
            if ("mounted".equals(Environment.getExternalStorageState())) {
                StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
                blockSize = statFs.getBlockSize() * statFs.getBlockCount();
            }
        } catch (Throwable unused) {
        }
        return String.valueOf(blockSize);
    }

    public static String k(Context context) {
        String macAddress = "";
        if (a(context, "android.permission.ACCESS_WIFI_STATE")) {
            return "";
        }
        try {
            macAddress = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
            if (macAddress == null || macAddress.length() == 0 || "02:00:00:00:00:00".equals(macAddress)) {
                return w();
            }
        } catch (Throwable unused) {
        }
        return macAddress;
    }

    public static String l() {
        String str;
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            str = (String) cls.getMethod("get", String.class, String.class).invoke(cls.newInstance(), "gsm.version.baseband", "no message");
        } catch (Throwable unused) {
            str = "";
        }
        return str == null ? "" : str;
    }

    public static String l(Context context) {
        if (a(context, Permission.READ_PHONE_STATE)) {
            return "";
        }
        try {
            String simSerialNumber = ((TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE)).getSimSerialNumber();
            if (simSerialNumber == null) {
                return "";
            }
            try {
                if (simSerialNumber.length() == 0) {
                    return "";
                }
            } catch (Throwable unused) {
            }
            return simSerialNumber;
        } catch (Throwable unused2) {
            return "";
        }
    }

    public static String m() {
        String str;
        try {
            str = Build.SERIAL;
        } catch (Throwable unused) {
            str = "";
        }
        return str == null ? "" : str;
    }

    public static String m(Context context) {
        String string;
        try {
            string = Settings.Secure.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
        } catch (Throwable unused) {
            string = "";
        }
        return string == null ? "" : string;
    }

    public static String n() {
        String string;
        try {
            string = Locale.getDefault().toString();
        } catch (Throwable unused) {
            string = "";
        }
        return string == null ? "" : string;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x001f A[Catch: all -> 0x002e, TRY_LEAVE, TryCatch #0 {all -> 0x002e, blocks: (B:7:0x0011, B:9:0x0017, B:11:0x001f), top: B:18:0x0011 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String n(android.content.Context r3) {
        /*
            java.lang.String r0 = "android.permission.BLUETOOTH"
            boolean r0 = a(r3, r0)
            java.lang.String r1 = ""
            if (r0 == 0) goto Lb
            return r1
        Lb:
            java.lang.String r0 = z()
            if (r0 == 0) goto L1f
            int r2 = r0.length()     // Catch: java.lang.Throwable -> L2e
            if (r2 == 0) goto L1f
            java.lang.String r2 = "02:00:00:00:00:00"
            boolean r2 = r2.equals(r0)     // Catch: java.lang.Throwable -> L2e
            if (r2 == 0) goto L29
        L1f:
            android.content.ContentResolver r3 = r3.getContentResolver()     // Catch: java.lang.Throwable -> L2e
            java.lang.String r2 = "bluetooth_address"
            java.lang.String r0 = android.provider.Settings.Secure.getString(r3, r2)     // Catch: java.lang.Throwable -> L2e
        L29:
            if (r0 != 0) goto L2c
            goto L2d
        L2c:
            r1 = r0
        L2d:
            r0 = r1
        L2e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.b.b.n(android.content.Context):java.lang.String");
    }

    public static String o() {
        String displayName;
        try {
            displayName = TimeZone.getDefault().getDisplayName(false, 0);
        } catch (Throwable unused) {
            displayName = "";
        }
        return displayName == null ? "" : displayName;
    }

    public static String o(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
            return telephonyManager != null ? String.valueOf(telephonyManager.getNetworkType()) : "";
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String p() {
        try {
            long jCurrentTimeMillis = System.currentTimeMillis() - SystemClock.elapsedRealtime();
            StringBuilder sb = new StringBuilder();
            sb.append(jCurrentTimeMillis - (jCurrentTimeMillis % 1000));
            return sb.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String p(Context context) {
        WifiManager wifiManager;
        if (a(context, "android.permission.ACCESS_WIFI_STATE")) {
            return "";
        }
        try {
            wifiManager = (WifiManager) context.getSystemService("wifi");
        } catch (Throwable unused) {
        }
        String bssid = wifiManager.isWifiEnabled() ? wifiManager.getConnectionInfo().getBSSID() : "";
        return bssid == null ? "" : bssid;
    }

    public static String q() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(SystemClock.elapsedRealtime());
            return sb.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String q(Context context) {
        try {
            String strT = t(context);
            String strA = A();
            if (!com.alipay.security.mobile.module.a.a.b(strT) || !com.alipay.security.mobile.module.a.a.b(strA)) {
                return "";
            }
            return strT + ":" + A();
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String r() {
        try {
            StringBuilder sb = new StringBuilder();
            String[] strArr = {"/dev/qemu_pipe", "/dev/socket/qemud", "/system/lib/libc_malloc_debug_qemu.so", "/sys/qemu_trace", "/system/bin/qemu-props", "/dev/socket/genyd", "/dev/socket/baseband_genyd"};
            sb.append(TarConstants.VERSION_POSIX + ":");
            for (int i2 = 0; i2 < 7; i2++) {
                sb.append(new File(strArr[i2]).exists() ? "1" : "0");
            }
            return sb.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String r(Context context) {
        long jLastModified;
        try {
            if (!((KeyguardManager) context.getSystemService("keyguard")).isKeyguardSecure()) {
                return "0:0";
            }
            String[] strArr = {"/data/system/password.key", "/data/system/gesture.key", "/data/system/gatekeeper.password.key", "/data/system/gatekeeper.gesture.key", "/data/system/gatekeeper.pattern.key"};
            long jMax = 0;
            for (int i2 = 0; i2 < 5; i2++) {
                try {
                    jLastModified = new File(strArr[i2]).lastModified();
                } catch (Throwable unused) {
                    jLastModified = -1;
                }
                jMax = Math.max(jLastModified, jMax);
            }
            return "1:" + jMax;
        } catch (Throwable unused2) {
            return "";
        }
    }

    public static String s() {
        String[] strArr = {"dalvik.system.Taint"};
        StringBuilder sb = new StringBuilder();
        sb.append(TarConstants.VERSION_POSIX);
        sb.append(":");
        for (int i2 = 0; i2 <= 0; i2++) {
            try {
                Class.forName(strArr[0]);
                sb.append("1");
            } catch (Throwable unused) {
                sb.append("0");
            }
        }
        return sb.toString();
    }

    public static String s(Context context) {
        try {
            Intent intentRegisterReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            int intExtra = intentRegisterReceiver.getIntExtra(DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL, -1);
            int intExtra2 = intentRegisterReceiver.getIntExtra("status", -1);
            boolean z2 = intExtra2 == 2 || intExtra2 == 5;
            StringBuilder sb = new StringBuilder();
            sb.append(z2 ? "1" : "0");
            sb.append(":");
            sb.append(intExtra);
            return sb.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String t() {
        LineNumberReader lineNumberReader;
        StringBuilder sb = new StringBuilder();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("/system/build.prop", "ro.product.name=sdk");
        linkedHashMap.put("/proc/tty/drivers", "goldfish");
        linkedHashMap.put("/proc/cpuinfo", "goldfish");
        sb.append(TarConstants.VERSION_POSIX + ":");
        for (String str : linkedHashMap.keySet()) {
            char c3 = '0';
            try {
                lineNumberReader = new LineNumberReader(new InputStreamReader(new FileInputStream(str)));
                while (true) {
                    try {
                        String line = lineNumberReader.readLine();
                        if (line == null) {
                            break;
                        }
                        if (line.toLowerCase().contains((CharSequence) linkedHashMap.get(str))) {
                            c3 = '1';
                            break;
                        }
                    } catch (Throwable unused) {
                        sb.append('0');
                        if (lineNumberReader != null) {
                            lineNumberReader.close();
                        }
                    }
                }
                sb.append(c3);
            } catch (Throwable unused2) {
                lineNumberReader = null;
            }
            try {
                lineNumberReader.close();
            } catch (Throwable unused3) {
            }
        }
        return sb.toString();
    }

    private static String t(Context context) {
        if (a(context, "android.permission.ACCESS_NETWORK_STATE")) {
            return "";
        }
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return null;
            }
            if (activeNetworkInfo.getType() == 1) {
                return "WIFI";
            }
            if (activeNetworkInfo.getType() != 0) {
                return null;
            }
            int subtype = activeNetworkInfo.getSubtype();
            if (subtype != 4 && subtype != 1 && subtype != 2 && subtype != 7 && subtype != 11) {
                if (subtype != 3 && subtype != 5 && subtype != 6 && subtype != 8 && subtype != 9 && subtype != 10 && subtype != 12 && subtype != 14 && subtype != 15) {
                    return subtype == 13 ? "4G" : "UNKNOW";
                }
                return "3G";
            }
            return "2G";
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String u() {
        StringBuilder sb = new StringBuilder();
        sb.append(TarConstants.VERSION_POSIX + ":");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("BRAND", "generic");
        linkedHashMap.put("BOARD", "unknown");
        linkedHashMap.put("DEVICE", "generic");
        linkedHashMap.put("HARDWARE", "goldfish");
        linkedHashMap.put("PRODUCT", "sdk");
        linkedHashMap.put("MODEL", "sdk");
        for (String str : linkedHashMap.keySet()) {
            char c3 = '0';
            try {
                String str2 = (String) Build.class.getField(str).get(null);
                String str3 = (String) linkedHashMap.get(str);
                String lowerCase = str2 != null ? str2.toLowerCase() : null;
                if (lowerCase != null && lowerCase.contains(str3)) {
                    c3 = '1';
                }
            } catch (Throwable unused) {
            }
            sb.append(c3);
        }
        return sb.toString();
    }

    public static String v() {
        StringBuilder sb = new StringBuilder();
        sb.append(TarConstants.VERSION_POSIX + ":");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("ro.hardware", "goldfish");
        linkedHashMap.put("ro.kernel.qemu", "1");
        linkedHashMap.put("ro.product.device", "generic");
        linkedHashMap.put("ro.product.model", "sdk");
        linkedHashMap.put("ro.product.brand", "generic");
        linkedHashMap.put("ro.product.name", "sdk");
        linkedHashMap.put("ro.build.fingerprint", "test-keys");
        linkedHashMap.put("ro.product.manufacturer", "unknow");
        for (String str : linkedHashMap.keySet()) {
            String str2 = (String) linkedHashMap.get(str);
            String strB = com.alipay.security.mobile.module.a.a.b(str, "");
            sb.append((strB == null || !strB.contains(str2)) ? '0' : '1');
        }
        return sb.toString();
    }

    private static String w() {
        try {
            ArrayList<NetworkInterface> list = Collections.list(NetworkInterface.getNetworkInterfaces());
            if (list == null) {
                return "02:00:00:00:00:00";
            }
            for (NetworkInterface networkInterface : list) {
                if (networkInterface != null && networkInterface.getName() != null && networkInterface.getName().equalsIgnoreCase("wlan0")) {
                    byte[] hardwareAddress = networkInterface.getHardwareAddress();
                    if (hardwareAddress == null) {
                        return "02:00:00:00:00:00";
                    }
                    StringBuilder sb = new StringBuilder();
                    for (byte b3 : hardwareAddress) {
                        sb.append(String.format("%02X:", Integer.valueOf(b3 & 255)));
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    return sb.toString();
                }
            }
            return "02:00:00:00:00:00";
        } catch (Throwable unused) {
            return "02:00:00:00:00:00";
        }
    }

    private static String x() {
        FileReader fileReader;
        BufferedReader bufferedReader;
        String line;
        BufferedReader bufferedReader2 = null;
        try {
            fileReader = new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq");
            try {
                bufferedReader = new BufferedReader(fileReader, 8192);
            } catch (Throwable unused) {
            }
        } catch (Throwable unused2) {
            fileReader = null;
        }
        try {
            line = bufferedReader.readLine();
        } catch (Throwable unused3) {
            bufferedReader2 = bufferedReader;
            if (bufferedReader2 != null) {
                try {
                    bufferedReader2.close();
                } catch (Throwable unused4) {
                }
            }
            if (fileReader == null) {
                return "";
            }
            try {
                fileReader.close();
                return "";
            } catch (Throwable unused5) {
                return "";
            }
        }
        if (com.alipay.security.mobile.module.a.a.a(line)) {
            try {
                bufferedReader.close();
            } catch (Throwable unused6) {
            }
            fileReader.close();
            return "";
        }
        String strTrim = line.trim();
        try {
            bufferedReader.close();
        } catch (Throwable unused7) {
        }
        try {
            fileReader.close();
        } catch (Throwable unused8) {
        }
        return strTrim;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0034, code lost:
    
        r1 = r2[1].trim();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String y() {
        /*
            java.lang.String r0 = "/proc/cpuinfo"
            java.lang.String r1 = ""
            r2 = 0
            java.io.FileReader r3 = new java.io.FileReader     // Catch: java.lang.Throwable -> L44
            r3.<init>(r0)     // Catch: java.lang.Throwable -> L44
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L41
            r4 = 8192(0x2000, float:1.148E-41)
            r0.<init>(r3, r4)     // Catch: java.lang.Throwable -> L41
        L11:
            java.lang.String r2 = r0.readLine()     // Catch: java.lang.Throwable -> L42
            if (r2 == 0) goto L3a
            boolean r4 = com.alipay.security.mobile.module.a.a.a(r2)     // Catch: java.lang.Throwable -> L42
            if (r4 != 0) goto L11
            java.lang.String r4 = ":"
            java.lang.String[] r2 = r2.split(r4)     // Catch: java.lang.Throwable -> L42
            if (r2 == 0) goto L11
            int r4 = r2.length     // Catch: java.lang.Throwable -> L42
            r5 = 1
            if (r4 <= r5) goto L11
            r4 = 0
            r4 = r2[r4]     // Catch: java.lang.Throwable -> L42
            java.lang.String r6 = "BogoMIPS"
            boolean r4 = r4.contains(r6)     // Catch: java.lang.Throwable -> L42
            if (r4 == 0) goto L11
            r2 = r2[r5]     // Catch: java.lang.Throwable -> L42
            java.lang.String r1 = r2.trim()     // Catch: java.lang.Throwable -> L42
        L3a:
            r3.close()     // Catch: java.lang.Throwable -> L3d
        L3d:
            r0.close()     // Catch: java.lang.Throwable -> L4d
            goto L4d
        L41:
            r0 = r2
        L42:
            r2 = r3
            goto L45
        L44:
            r0 = r2
        L45:
            if (r2 == 0) goto L4a
            r2.close()     // Catch: java.lang.Throwable -> L4a
        L4a:
            if (r0 == 0) goto L4d
            goto L3d
        L4d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.b.b.y():java.lang.String");
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0027  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0020 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String z() {
        /*
            java.lang.String r0 = ""
            android.bluetooth.BluetoothAdapter r1 = android.bluetooth.BluetoothAdapter.getDefaultAdapter()     // Catch: java.lang.Throwable -> L14
            if (r1 == 0) goto Lf
            boolean r2 = r1.isEnabled()     // Catch: java.lang.Throwable -> L15
            if (r2 != 0) goto Lf
            return r0
        Lf:
            java.lang.String r2 = r1.getAddress()     // Catch: java.lang.Throwable -> L15
            goto L16
        L14:
            r1 = 0
        L15:
            r2 = r0
        L16:
            if (r2 == 0) goto L20
            java.lang.String r3 = "00:00:00:00:00"
            boolean r3 = r2.endsWith(r3)
            if (r3 == 0) goto L24
        L20:
            java.lang.String r2 = a(r1)     // Catch: java.lang.Throwable -> L24
        L24:
            if (r2 != 0) goto L27
            goto L28
        L27:
            r0 = r2
        L28:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.b.b.z():java.lang.String");
    }

    public final String f() {
        try {
            return String.valueOf(new File("/sys/devices/system/cpu/").listFiles(new c(this)).length);
        } catch (Throwable unused) {
            return "1";
        }
    }
}
