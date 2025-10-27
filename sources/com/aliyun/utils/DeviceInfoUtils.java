package com.aliyun.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.ConfigurationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.cicada.player.utils.NativeUsed;
import com.google.android.exoplayer2.C;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

@NativeUsed
/* loaded from: classes2.dex */
public class DeviceInfoUtils {
    public static File ALIVC_DATA_FILE = null;
    public static final String DATA_DIRECTORY = "AlivcData";
    private static final int MAX_WRITE_COUNT = 10;
    private static final String UUID_FILE = "alivc_data.txt";
    private static final String UUID_PROP = "UUID";
    private static c mCpuTracker;
    private static Context sAppContext;
    private static String sCpuProcessorInfo;
    private static String sDeviceUUID;
    private static String sSessionId;
    private static int sWriteUUIDCount;

    public static class a extends TimerTask {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ File f3578a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ String f3579b;

        public a(File file, String str) {
            this.f3578a = file;
            this.f3579b = str;
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            boolean z2 = false;
            try {
                boolean z3 = this.f3578a.exists() || this.f3578a.createNewFile();
                Properties properties = new Properties();
                properties.setProperty(DeviceInfoUtils.UUID_PROP, this.f3579b);
                if (z3) {
                    FileWriter fileWriter = new FileWriter(this.f3578a);
                    properties.store(fileWriter, (String) null);
                    fileWriter.close();
                    z2 = true;
                }
            } catch (Throwable unused) {
            }
            DeviceInfoUtils.access$008();
            if (z2 || DeviceInfoUtils.sWriteUUIDCount >= 10) {
                return;
            }
            DeviceInfoUtils.writeUUIDToFile(this.f3578a, this.f3579b);
        }
    }

    static {
        f.b();
        ALIVC_DATA_FILE = null;
    }

    public static /* synthetic */ int access$008() {
        int i2 = sWriteUUIDCount;
        sWriteUUIDCount = i2 + 1;
        return i2;
    }

    private static String canGetContext() {
        return getSDKContext() != null ? k.a.f27523u : k.a.f27524v;
    }

    public static String generateNewSessionId() {
        String strReplace = UUID.randomUUID().toString().replace("-", "");
        sSessionId = strReplace;
        return strReplace;
    }

    public static String getApplicationName() throws PackageManager.NameNotFoundException {
        PackageManager packageManager;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = getSDKContext().getPackageManager();
            try {
                applicationInfo = packageManager.getApplicationInfo(getSDKContext().getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException unused) {
            }
        } catch (PackageManager.NameNotFoundException unused2) {
            packageManager = null;
        }
        return (String) packageManager.getApplicationLabel(applicationInfo);
    }

    public static String getApplicationVersion() {
        try {
            if (getSDKContext() != null) {
                return getSDKContext().getPackageManager().getPackageInfo(getSDKContext().getPackageName(), 0).versionName;
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return "";
    }

    public static String getCPUInfo() {
        String str;
        try {
            str = (String) Class.forName("android.os.SystemProperties").getDeclaredMethod("get", String.class, String.class).invoke(null, "ro.board.platform", "");
        } catch (Exception e2) {
            e2.printStackTrace();
            str = "";
        }
        return str.equals("") ? Build.HARDWARE : str;
    }

    public static String getCPUProcessorInfo() throws Throwable {
        if (!TextUtils.isEmpty(sCpuProcessorInfo)) {
            return sCpuProcessorInfo;
        }
        requestCPUInfo();
        return sCpuProcessorInfo;
    }

    public static String getCPUUsageRatio() {
        if (mCpuTracker == null) {
            mCpuTracker = new c();
        }
        return String.valueOf(mCpuTracker.e());
    }

    private static String getCacheDir() {
        File externalCacheDir;
        return (getSDKContext() == null || (externalCacheDir = getSDKContext().getExternalCacheDir()) == null || !externalCacheDir.exists()) ? "" : externalCacheDir.getAbsolutePath();
    }

    @SuppressLint({"SimpleDateFormat"})
    public static String getCurrentTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
    }

    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    private static String getDeviceFeature() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (sAppContext != null) {
            try {
                jSONObject.put("UIModeType", getUIModeType());
            } catch (Exception unused) {
            }
            putFeature(jSONObject, "android.hardware.audio.low_latency");
            putFeature(jSONObject, "android.hardware.bluetooth");
            putFeature(jSONObject, "android.hardware.bluetooth_le");
            putFeature(jSONObject, "android.hardware.screen.landscape");
            putFeature(jSONObject, "android.hardware.screen.portrait");
            int i2 = Build.VERSION.SDK_INT;
            putFeature(jSONObject, "android.hardware.type.watch");
            putFeature(jSONObject, "android.hardware.audio.output");
            putFeature(jSONObject, "android.software.live_tv");
            putFeature(jSONObject, "android.hardware.opengles.aep");
            putFeature(jSONObject, "android.hardware.audio.pro");
            putFeature(jSONObject, "android.hardware.type.automotive");
            putFeature(jSONObject, "android.hardware.sensor.hifi_sensors");
            putFeature(jSONObject, "android.software.midi");
            if (i2 >= 24) {
                putFeature(jSONObject, "android.software.picture_in_picture");
                putFeature(jSONObject, "android.hardware.vr.high_performance");
                putFeature(jSONObject, "android.hardware.vulkan.level");
                putFeature(jSONObject, "android.hardware.vulkan.version");
            }
            if (i2 >= 27) {
                putFeature(jSONObject, "android.hardware.ram.low");
                putFeature(jSONObject, "android.hardware.ram.normal");
            }
            if (i2 >= 26) {
                putFeature(jSONObject, "android.software.activities_on_secondary_displays");
                putFeature(jSONObject, "android.hardware.type.embedded");
                putFeature(jSONObject, "android.hardware.vr.headtracking");
                putFeature(jSONObject, "android.hardware.vulkan.compute");
            }
            putFeature(jSONObject, "android.hardware.touchscreen");
            putFeature(jSONObject, "android.hardware.faketouch");
            putFeature(jSONObject, "android.hardware.telephony");
            putFeature(jSONObject, "android.hardware.camera");
            putFeature(jSONObject, "android.hardware.nfc");
            putFeature(jSONObject, "android.hardware.location.gps");
            putFeature(jSONObject, "android.hardware.microphone");
            putFeature(jSONObject, "android.hardware.sensor.compass");
        }
        return jSONObject.toString();
    }

    public static String getDeviceManufacturer() {
        return Build.MANUFACTURER;
    }

    public static String getDeviceModel() {
        return Build.MODEL;
    }

    public static synchronized String getDeviceUUID() {
        if (!TextUtils.isEmpty(sDeviceUUID)) {
            return sDeviceUUID;
        }
        if (ALIVC_DATA_FILE == null) {
            ALIVC_DATA_FILE = new File(getSDKContext().getCacheDir(), "AlivcData");
        }
        File file = new File(ALIVC_DATA_FILE, UUID_FILE);
        try {
            if (ALIVC_DATA_FILE.exists() || ALIVC_DATA_FILE.mkdir()) {
                Properties properties = new Properties();
                FileReader fileReader = new FileReader(file);
                properties.load(fileReader);
                fileReader.close();
                sDeviceUUID = properties.getProperty(UUID_PROP);
            }
        } catch (Throwable unused) {
        }
        if (TextUtils.isEmpty(sDeviceUUID)) {
            sWriteUUIDCount = 0;
            String strReplace = UUID.randomUUID().toString().replace("-", "");
            sDeviceUUID = strReplace;
            writeUUIDToFile(file, strReplace);
        }
        return sDeviceUUID;
    }

    public static String getElectricUsageRatio() {
        if (sAppContext == null) {
            return "0";
        }
        try {
            Intent intentRegisterReceiver = sAppContext.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            return String.valueOf((int) (((intentRegisterReceiver != null ? intentRegisterReceiver.getIntExtra(DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL, -1) : -1) / (intentRegisterReceiver != null ? intentRegisterReceiver.getIntExtra("scale", -1) : -1)) * 100.0f));
        } catch (Throwable unused) {
            return "0";
        }
    }

    public static String getGPUInfo() {
        return "";
    }

    public static String getMemoryTotal() {
        try {
            Context context = sAppContext;
            if (context == null) {
                return "0";
            }
            ActivityManager activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            activityManager.getMemoryInfo(memoryInfo);
            return ((int) (memoryInfo.totalMem / 1048576.0f)) + "";
        } catch (Exception unused) {
            return "0";
        }
    }

    public static String getMemoryUsage() {
        if (((ActivityManager) sAppContext.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getProcessMemoryInfo(new int[]{Process.myPid()}).length == 0) {
            return "0";
        }
        return (r0[0].getTotalPss() / 1024.0f) + "";
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static String getNetworkType() {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) sAppContext.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return "NoActive";
            }
            String subtypeName = activeNetworkInfo.getSubtypeName();
            if (activeNetworkInfo.getType() == 1) {
                return "WIFI";
            }
            if (activeNetworkInfo.getType() != 0) {
                return subtypeName;
            }
            int subtype = activeNetworkInfo.getSubtype();
            if (subtype == 20) {
                return "5G";
            }
            switch (subtype) {
                case 1:
                case 2:
                case 4:
                case 7:
                case 11:
                    return "2G";
                case 3:
                case 5:
                case 6:
                case 8:
                case 9:
                case 10:
                case 12:
                case 14:
                case 15:
                    return "3G";
                case 13:
                    return "4G";
                default:
                    if (!subtypeName.equalsIgnoreCase("TD-SCDMA") && !subtypeName.equalsIgnoreCase("WCDMA") && !subtypeName.equalsIgnoreCase("CDMA2000")) {
                        return "Mobile:" + subtypeName;
                    }
                    return "3G";
            }
        } catch (Exception unused) {
            return "Unknow";
        }
    }

    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getOpenGLVersion() {
        ConfigurationInfo deviceConfigurationInfo;
        Context context = sAppContext;
        if (context != null) {
            try {
                ActivityManager activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
                if (activityManager != null && (deviceConfigurationInfo = activityManager.getDeviceConfigurationInfo()) != null) {
                    return Integer.toHexString(Integer.parseInt(deviceConfigurationInfo.reqGlEsVersion + ""));
                }
            } catch (Throwable unused) {
            }
        }
        return null;
    }

    public static Context getSDKContext() {
        return sAppContext;
    }

    public static String getSDKVersion() {
        return String.valueOf(Build.VERSION.SDK_INT);
    }

    public static String getTerminalType() {
        try {
            Context context = sAppContext;
            return (context == null || context.getResources() == null || sAppContext.getResources().getConfiguration() == null) ? AliyunLogCommon.TERMINAL_TYPE : (sAppContext.getResources().getConfiguration().screenLayout & 15) >= 3 ? "pad" : AliyunLogCommon.TERMINAL_TYPE;
        } catch (Throwable unused) {
            return AliyunLogCommon.TERMINAL_TYPE;
        }
    }

    private static String getUIModeType() throws Exception {
        int currentModeType = ((UiModeManager) sAppContext.getSystemService("uimode")).getCurrentModeType();
        return currentModeType != 6 ? currentModeType != 7 ? currentModeType != 15 ? currentModeType != 1 ? currentModeType != 2 ? currentModeType != 3 ? currentModeType != 4 ? "UNDEFINED" : "TELEVISION" : "CAR" : "DESK" : "NORMAL" : "MASK" : "VR_HEADSET" : "WATCH";
    }

    public static void loadClass() {
    }

    @NativeUsed
    private static String native_getDeviceInfo(String str) {
        str.hashCode();
        switch (str) {
            case "device_manufacturer":
                return getDeviceManufacturer();
            case "cpu_usage":
                return getCPUUsageRatio();
            case "terminal_type":
                return getTerminalType();
            case "application_id":
                return getSDKContext().getPackageName();
            case "os_name":
                return "android";
            case "gpu_info":
                return getGPUInfo();
            case "opengl_version":
                return getOpenGLVersion();
            case "application_version":
                return getApplicationVersion();
            case "electric_usage":
                return getElectricUsageRatio();
            case "device_brand":
                return getDeviceBrand();
            case "device_model":
                return getDeviceModel();
            case "cache_dir":
                return getCacheDir();
            case "can_get_context":
                return canGetContext();
            case "network_type":
                return getNetworkType();
            case "application_name":
                return getApplicationName();
            case "uuid":
                return getDeviceUUID();
            case "cpu_processor":
                return getCPUProcessorInfo();
            case "cpu_info":
                return getCPUInfo();
            case "os_version":
                return getOSVersion();
            case "device_feature":
                return getDeviceFeature();
            case "mem_total":
                return getMemoryTotal();
            case "mem_usage":
                return getMemoryUsage();
            default:
                return "";
        }
    }

    private static void putFeature(JSONObject jSONObject, String str) throws JSONException {
        try {
            jSONObject.put(str, sAppContext.getPackageManager().hasSystemFeature(str) ? "1" : "0");
        } catch (Exception unused) {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x003a, code lost:
    
        r1.close();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void requestCPUInfo() throws java.lang.Throwable {
        /*
            r0 = 0
            java.io.FileReader r1 = new java.io.FileReader     // Catch: java.lang.Throwable -> L57 java.lang.Exception -> L64
            java.lang.String r2 = "/proc/cpuinfo"
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L57 java.lang.Exception -> L64
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L54
            r2.<init>(r1)     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L54
            java.lang.String r0 = r2.readLine()     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L55
            r3 = 0
            r4 = r3
        L13:
            r5 = 1
            int r4 = r4 + r5
            r6 = 30
            if (r4 < r6) goto L1a
            goto L3a
        L1a:
            java.lang.String r6 = ":\\s+"
            r7 = 2
            java.lang.String[] r0 = r0.split(r6, r7)     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L55
            int r6 = r0.length     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L55
            if (r6 <= r5) goto L32
            r6 = r0[r3]     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L55
            java.lang.String r7 = "Processor"
            boolean r6 = r6.contains(r7)     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L55
            if (r6 == 0) goto L32
            r0 = r0[r5]     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L55
            com.aliyun.utils.DeviceInfoUtils.sCpuProcessorInfo = r0     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L55
        L32:
            java.lang.String r0 = com.aliyun.utils.DeviceInfoUtils.sCpuProcessorInfo     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L55
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L55
            if (r0 != 0) goto L44
        L3a:
            r1.close()     // Catch: java.io.IOException -> L3d java.lang.Throwable -> L49 java.lang.Exception -> L55
        L3d:
            r2.close()     // Catch: java.io.IOException -> L40 java.lang.Throwable -> L49 java.lang.Exception -> L55
        L40:
            r1.close()     // Catch: java.io.IOException -> L6c
            goto L6c
        L44:
            java.lang.String r0 = r2.readLine()     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L55
            goto L13
        L49:
            r0 = move-exception
            r8 = r1
            r1 = r0
            r0 = r8
            goto L59
        L4e:
            r2 = move-exception
            r8 = r2
            r2 = r0
            r0 = r1
            r1 = r8
            goto L59
        L54:
            r2 = r0
        L55:
            r0 = r1
            goto L65
        L57:
            r1 = move-exception
            r2 = r0
        L59:
            if (r0 == 0) goto L5e
            r0.close()     // Catch: java.io.IOException -> L5e
        L5e:
            if (r2 == 0) goto L63
            r2.close()     // Catch: java.io.IOException -> L63
        L63:
            throw r1
        L64:
            r2 = r0
        L65:
            if (r0 == 0) goto L6a
            r0.close()     // Catch: java.io.IOException -> L6a
        L6a:
            if (r2 == 0) goto L6f
        L6c:
            r2.close()     // Catch: java.io.IOException -> L6f
        L6f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.utils.DeviceInfoUtils.requestCPUInfo():void");
    }

    public static void setSDKContext(Context context) {
        if (context != null) {
            context = context.getApplicationContext();
        }
        sAppContext = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void writeUUIDToFile(File file, String str) {
        if (file == null || TextUtils.isEmpty(str)) {
            return;
        }
        new Timer().schedule(new a(file, str), C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
    }
}
