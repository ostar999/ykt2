package com.unity3d.splash.services.core.device;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import cn.hutool.crypto.KeyUtil;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import com.umeng.analytics.pro.am;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.misc.Utilities;
import com.unity3d.splash.services.core.properties.ClientProperties;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes6.dex */
public class Device {

    /* renamed from: com.unity3d.splash.services.core.device.Device$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$unity3d$splash$services$core$device$Device$MemoryInfoType;

        static {
            int[] iArr = new int[MemoryInfoType.values().length];
            $SwitchMap$com$unity3d$splash$services$core$device$Device$MemoryInfoType = iArr;
            try {
                iArr[MemoryInfoType.TOTAL_MEMORY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$unity3d$splash$services$core$device$Device$MemoryInfoType[MemoryInfoType.FREE_MEMORY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public enum MemoryInfoType {
        TOTAL_MEMORY,
        FREE_MEMORY
    }

    public static int getApiLevel() {
        return Build.VERSION.SDK_INT;
    }

    public static String getApkDigest() throws Throwable {
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2 = null;
        try {
            fileInputStream = new FileInputStream(new File(ClientProperties.getApplicationContext().getPackageCodePath()));
        } catch (Throwable th) {
            th = th;
        }
        try {
            String strSha256 = Utilities.Sha256(fileInputStream);
            try {
                fileInputStream.close();
            } catch (IOException unused) {
            }
            return strSha256;
        } catch (Throwable th2) {
            th = th2;
            fileInputStream2 = fileInputStream;
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (IOException unused2) {
                }
            }
            throw th;
        }
    }

    public static float getBatteryLevel() {
        Intent intentRegisterReceiver;
        if (ClientProperties.getApplicationContext() == null || (intentRegisterReceiver = ClientProperties.getApplicationContext().registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"))) == null) {
            return -1.0f;
        }
        return intentRegisterReceiver.getIntExtra(DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL, -1) / intentRegisterReceiver.getIntExtra("scale", -1);
    }

    public static int getBatteryStatus() {
        Intent intentRegisterReceiver;
        if (ClientProperties.getApplicationContext() == null || (intentRegisterReceiver = ClientProperties.getApplicationContext().registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"))) == null) {
            return -1;
        }
        return intentRegisterReceiver.getIntExtra("status", -1);
    }

    public static String getBoard() {
        return Build.BOARD;
    }

    public static String getBootloader() {
        return Build.BOOTLOADER;
    }

    public static String getBrand() {
        return Build.BRAND;
    }

    public static String getBuildId() {
        return Build.ID;
    }

    public static String getBuildVersionIncremental() {
        return Build.VERSION.INCREMENTAL;
    }

    public static long getCPUCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    public static String getCertificateFingerprint() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            Signature[] signatureArr = ClientProperties.getApplicationContext().getPackageManager().getPackageInfo(ClientProperties.getApplicationContext().getPackageName(), 64).signatures;
            if (signatureArr == null || signatureArr.length <= 0) {
                return null;
            }
            return Utilities.toHexString(MessageDigest.getInstance("SHA-1").digest(((X509Certificate) CertificateFactory.getInstance(KeyUtil.CERT_TYPE_X509).generateCertificate(new ByteArrayInputStream(signatureArr[0].toByteArray()))).getEncoded()));
        } catch (Exception e2) {
            DeviceLog.exception("Exception when signing certificate fingerprint", e2);
            return null;
        }
    }

    public static String getDevice() {
        return Build.DEVICE;
    }

    public static long getElapsedRealtime() {
        return SystemClock.elapsedRealtime();
    }

    public static String getFingerprint() {
        return Build.FINGERPRINT;
    }

    public static long getFreeMemory() {
        return getMemoryInfo(MemoryInfoType.FREE_MEMORY);
    }

    public static long getFreeSpace(File file) {
        if (file == null || !file.exists()) {
            return -1L;
        }
        return Math.round(file.getFreeSpace() / 1024);
    }

    public static String getHardware() {
        return Build.HARDWARE;
    }

    public static String getHost() {
        return Build.HOST;
    }

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /* JADX WARN: Not initialized variable reg: 3, insn: 0x0057: MOVE (r1 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:32:0x0057 */
    private static long getMemoryInfo(MemoryInfoType memoryInfoType) throws Throwable {
        RandomAccessFile randomAccessFile;
        IOException e2;
        RandomAccessFile randomAccessFile2;
        int i2 = AnonymousClass1.$SwitchMap$com$unity3d$splash$services$core$device$Device$MemoryInfoType[memoryInfoType.ordinal()];
        int i3 = 1;
        if (i2 != 1) {
            i3 = 2;
            if (i2 != 2) {
                i3 = -1;
            }
        }
        RandomAccessFile randomAccessFile3 = null;
        String line = null;
        try {
            try {
                randomAccessFile = new RandomAccessFile("/proc/meminfo", "r");
                for (int i4 = 0; i4 < i3; i4++) {
                    try {
                        line = randomAccessFile.readLine();
                    } catch (IOException e3) {
                        e2 = e3;
                        DeviceLog.exception("Error while reading memory info: " + memoryInfoType, e2);
                        try {
                            randomAccessFile.close();
                            return -1L;
                        } catch (IOException e4) {
                            DeviceLog.exception("Error closing RandomAccessFile", e4);
                            return -1L;
                        }
                    }
                }
                long memoryValueFromString = getMemoryValueFromString(line);
                try {
                    randomAccessFile.close();
                } catch (IOException e5) {
                    DeviceLog.exception("Error closing RandomAccessFile", e5);
                }
                return memoryValueFromString;
            } catch (IOException e6) {
                randomAccessFile = null;
                e2 = e6;
            } catch (Throwable th) {
                th = th;
                try {
                    randomAccessFile3.close();
                } catch (IOException e7) {
                    DeviceLog.exception("Error closing RandomAccessFile", e7);
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            randomAccessFile3 = randomAccessFile2;
            randomAccessFile3.close();
            throw th;
        }
    }

    private static long getMemoryValueFromString(String str) {
        if (str == null) {
            return -1L;
        }
        Matcher matcher = Pattern.compile("(\\d+)").matcher(str);
        String strGroup = "";
        while (matcher.find()) {
            strGroup = matcher.group(1);
        }
        return Long.parseLong(strGroup);
    }

    public static String getModel() {
        return Build.MODEL;
    }

    public static String getNetworkCountryISO() {
        return ClientProperties.getApplicationContext() != null ? ((TelephonyManager) ClientProperties.getApplicationContext().getSystemService(AliyunLogCommon.TERMINAL_TYPE)).getNetworkCountryIso() : "";
    }

    public static boolean getNetworkMetered() {
        ConnectivityManager connectivityManager;
        if (ClientProperties.getApplicationContext() == null || (connectivityManager = (ConnectivityManager) ClientProperties.getApplicationContext().getSystemService("connectivity")) == null) {
            return false;
        }
        return connectivityManager.isActiveNetworkMetered();
    }

    public static String getNetworkOperator() {
        return ClientProperties.getApplicationContext() != null ? ((TelephonyManager) ClientProperties.getApplicationContext().getSystemService(AliyunLogCommon.TERMINAL_TYPE)).getNetworkOperator() : "";
    }

    public static String getNetworkOperatorName() {
        return ClientProperties.getApplicationContext() != null ? ((TelephonyManager) ClientProperties.getApplicationContext().getSystemService(AliyunLogCommon.TERMINAL_TYPE)).getNetworkOperatorName() : "";
    }

    public static int getNetworkType() {
        if (ClientProperties.getApplicationContext() != null) {
            return ((TelephonyManager) ClientProperties.getApplicationContext().getSystemService(AliyunLogCommon.TERMINAL_TYPE)).getNetworkType();
        }
        return -1;
    }

    @TargetApi(21)
    private static ArrayList getNewAbiList() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(Arrays.asList(Build.SUPPORTED_ABIS));
        return arrayList;
    }

    private static ArrayList getOldAbiList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Build.CPU_ABI);
        arrayList.add(Build.CPU_ABI2);
        return arrayList;
    }

    public static String getOsVersion() {
        return Build.VERSION.RELEASE;
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x0032 -> B:26:0x0035). Please report as a decompilation issue!!! */
    public static Map getProcessInfo() throws Throwable {
        Throwable th;
        RandomAccessFile randomAccessFile;
        IOException e2;
        HashMap map = new HashMap();
        RandomAccessFile randomAccessFile2 = null;
        try {
        } catch (IOException e3) {
            DeviceLog.exception("Error closing RandomAccessFile", e3);
        }
        try {
            try {
                randomAccessFile = new RandomAccessFile("/proc/self/stat", "r");
            } catch (IOException e4) {
                randomAccessFile = null;
                e2 = e4;
            } catch (Throwable th2) {
                th = th2;
                try {
                    randomAccessFile2.close();
                } catch (IOException e5) {
                    DeviceLog.exception("Error closing RandomAccessFile", e5);
                }
                throw th;
            }
            try {
                map.put("stat", randomAccessFile.readLine());
                randomAccessFile.close();
            } catch (IOException e6) {
                e2 = e6;
                DeviceLog.exception("Error while reading processor info: ", e2);
                randomAccessFile.close();
                return map;
            }
            return map;
        } catch (Throwable th3) {
            th = th3;
            randomAccessFile2 = randomAccessFile;
            randomAccessFile2.close();
            throw th;
        }
    }

    public static String getProduct() {
        return Build.PRODUCT;
    }

    public static int getRingerMode() {
        if (ClientProperties.getApplicationContext() == null) {
            return -1;
        }
        AudioManager audioManager = (AudioManager) ClientProperties.getApplicationContext().getSystemService("audio");
        if (audioManager != null) {
            return audioManager.getRingerMode();
        }
        return -2;
    }

    public static String getSIMMCC(Context context) {
        String simOperator = ((TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE)).getSimOperator();
        return (simOperator.length() >= 3) & (simOperator != null) ? simOperator.substring(0, 3) : "";
    }

    public static int getScreenBrightness() {
        if (ClientProperties.getApplicationContext() != null) {
            return Settings.System.getInt(ClientProperties.getApplicationContext().getContentResolver(), "screen_brightness", -1);
        }
        return -1;
    }

    public static int getScreenDensity() {
        if (ClientProperties.getApplicationContext() != null) {
            return ClientProperties.getApplicationContext().getResources().getDisplayMetrics().densityDpi;
        }
        return -1;
    }

    public static int getScreenHeight() {
        if (ClientProperties.getApplicationContext() != null) {
            return ClientProperties.getApplicationContext().getResources().getDisplayMetrics().heightPixels;
        }
        return -1;
    }

    public static int getScreenLayout() {
        if (ClientProperties.getApplicationContext() != null) {
            return ClientProperties.getApplicationContext().getResources().getConfiguration().screenLayout;
        }
        return -1;
    }

    public static int getScreenWidth() {
        if (ClientProperties.getApplicationContext() != null) {
            return ClientProperties.getApplicationContext().getResources().getDisplayMetrics().widthPixels;
        }
        return -1;
    }

    public static List getSensorList() {
        if (ClientProperties.getApplicationContext() != null) {
            return ((SensorManager) ClientProperties.getApplicationContext().getSystemService(am.ac)).getSensorList(-1);
        }
        return null;
    }

    public static int getStreamMaxVolume(int i2) {
        if (ClientProperties.getApplicationContext() == null) {
            return -1;
        }
        AudioManager audioManager = (AudioManager) ClientProperties.getApplicationContext().getSystemService("audio");
        if (audioManager != null) {
            return audioManager.getStreamMaxVolume(i2);
        }
        return -2;
    }

    public static int getStreamVolume(int i2) {
        if (ClientProperties.getApplicationContext() == null) {
            return -1;
        }
        AudioManager audioManager = (AudioManager) ClientProperties.getApplicationContext().getSystemService("audio");
        if (audioManager != null) {
            return audioManager.getStreamVolume(i2);
        }
        return -2;
    }

    public static ArrayList getSupportedAbis() {
        return getApiLevel() < 21 ? getOldAbiList() : getNewAbiList();
    }

    public static String getSystemProperty(String str, String str2) {
        return str2 != null ? System.getProperty(str, str2) : System.getProperty(str);
    }

    public static long getTotalMemory() {
        return getMemoryInfo(MemoryInfoType.TOTAL_MEMORY);
    }

    public static long getTotalSpace(File file) {
        if (file == null || !file.exists()) {
            return -1L;
        }
        return Math.round(file.getTotalSpace() / 1024);
    }

    public static String getUniqueEventId() {
        return UUID.randomUUID().toString();
    }

    public static long getUptime() {
        return SystemClock.uptimeMillis();
    }

    public static boolean isActiveNetworkConnected() {
        ConnectivityManager connectivityManager;
        NetworkInfo activeNetworkInfo;
        return (ClientProperties.getApplicationContext() == null || (connectivityManager = (ConnectivityManager) ClientProperties.getApplicationContext().getSystemService("connectivity")) == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isConnected()) ? false : true;
    }

    public static Boolean isAdbEnabled() {
        return getApiLevel() < 17 ? oldAdbStatus() : newAdbStatus();
    }

    public static boolean isAppInstalled(String str) throws PackageManager.NameNotFoundException {
        String str2;
        if (ClientProperties.getApplicationContext() != null) {
            try {
                PackageInfo packageInfo = ClientProperties.getApplicationContext().getPackageManager().getPackageInfo(str, 0);
                if (packageInfo != null && (str2 = packageInfo.packageName) != null) {
                    if (str.equals(str2)) {
                        return true;
                    }
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        return false;
    }

    public static boolean isRooted() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            return searchPathForBinary("su");
        } catch (Exception e2) {
            DeviceLog.exception("Rooted check failed", e2);
            return false;
        }
    }

    public static boolean isUSBConnected() {
        Intent intentRegisterReceiver;
        if (ClientProperties.getApplicationContext() == null || (intentRegisterReceiver = ClientProperties.getApplicationContext().registerReceiver(null, new IntentFilter("android.hardware.usb.action.USB_STATE"))) == null) {
            return false;
        }
        return intentRegisterReceiver.getBooleanExtra("connected", false);
    }

    public static boolean isUsingWifi() {
        ConnectivityManager connectivityManager;
        if (ClientProperties.getApplicationContext() == null || (connectivityManager = (ConnectivityManager) ClientProperties.getApplicationContext().getSystemService("connectivity")) == null) {
            return false;
        }
        TelephonyManager telephonyManager = (TelephonyManager) ClientProperties.getApplicationContext().getSystemService(AliyunLogCommon.TERMINAL_TYPE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && connectivityManager.getBackgroundDataSetting() && connectivityManager.getActiveNetworkInfo().isConnected() && telephonyManager != null && activeNetworkInfo.getType() == 1 && activeNetworkInfo.isConnected();
    }

    public static boolean isWiredHeadsetOn() {
        if (ClientProperties.getApplicationContext() != null) {
            return ((AudioManager) ClientProperties.getApplicationContext().getSystemService("audio")).isWiredHeadsetOn();
        }
        return false;
    }

    @TargetApi(17)
    private static Boolean newAdbStatus() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            return Boolean.valueOf(1 == Settings.Global.getInt(ClientProperties.getApplicationContext().getContentResolver(), "adb_enabled", 0));
        } catch (Exception e2) {
            DeviceLog.exception("Problems fetching adb enabled status", e2);
            return null;
        }
    }

    private static Boolean oldAdbStatus() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            return Boolean.valueOf(1 == Settings.Secure.getInt(ClientProperties.getApplicationContext().getContentResolver(), "adb_enabled", 0));
        } catch (Exception e2) {
            DeviceLog.exception("Problems fetching adb enabled status", e2);
            return null;
        }
    }

    private static boolean searchPathForBinary(String str) {
        File[] fileArrListFiles;
        for (String str2 : System.getenv("PATH").split(":")) {
            File file = new File(str2);
            if (file.exists() && file.isDirectory() && (fileArrListFiles = file.listFiles()) != null) {
                for (File file2 : fileArrListFiles) {
                    if (file2.getName().equals(str)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
