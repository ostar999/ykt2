package com.weibo.ssosdk;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import cn.hutool.core.text.StrPool;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import com.umeng.analytics.pro.am;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Collections;
import javax.crypto.Cipher;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class MfpBuilder {
    public static final String AID_TAG = "weibo_aid_value";
    private static final String SINA_WEIBO_FAKE = "";
    private static final String TAG = "MfpBuilder";

    public static final class BatteryInfo {
        private Intent batteryInfoIntent;

        private int getHealth() {
            return this.batteryInfoIntent.getIntExtra("health", 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getLevel() {
            return this.batteryInfoIntent.getIntExtra(DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL, 0);
        }

        private int getPlugged() {
            return this.batteryInfoIntent.getIntExtra("plugged", 0);
        }

        private boolean getPresent() {
            return this.batteryInfoIntent.getBooleanExtra("present", false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getScale() {
            return this.batteryInfoIntent.getIntExtra("scale", 0);
        }

        private int getStatus() {
            return this.batteryInfoIntent.getIntExtra("status", 0);
        }

        private String getTechnology() {
            return this.batteryInfoIntent.getStringExtra("technology");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getTemperature() {
            return this.batteryInfoIntent.getIntExtra("temperature", 0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getVoltage() {
            return this.batteryInfoIntent.getIntExtra("voltage", 0);
        }

        private BatteryInfo(Context context) {
            this.batteryInfoIntent = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        }
    }

    private static void LogD(String str) {
    }

    private static void LogE(String str) {
    }

    private static String bytesToString(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (byte b3 : bArr) {
            sb.append(String.format("%02X:", Byte.valueOf(b3)));
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    private static String encryptRsa(String str, String str2) throws Throwable {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(1, getPublicKey(str2));
        byte[] bytes = str.getBytes("UTF-8");
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            int i2 = 0;
            while (true) {
                try {
                    int iSplite = splite(bytes, i2, 117);
                    if (iSplite == -1) {
                        break;
                    }
                    byteArrayOutputStream2.write(cipher.doFinal(bytes, i2, iSplite));
                    i2 += iSplite;
                } catch (Throwable th) {
                    th = th;
                    byteArrayOutputStream = byteArrayOutputStream2;
                    if (byteArrayOutputStream != null) {
                        try {
                            byteArrayOutputStream.close();
                        } catch (IOException unused) {
                        }
                    }
                    throw th;
                }
            }
            byteArrayOutputStream2.flush();
            String strConcat = HiAnalyticsConstant.KeyAndValue.NUMBER_01.concat(new String(Base64.encode(byteArrayOutputStream2.toByteArray(), 2), "UTF-8"));
            try {
                byteArrayOutputStream2.close();
            } catch (IOException unused2) {
            }
            return strConcat;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static String genMfpString(Context context) throws JSONException, IllegalAccessException, InstantiationException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        String strGenerateUAAid;
        JSONObject jSONObject = new JSONObject();
        try {
            String os = getOS();
            if (!TextUtils.isEmpty(os)) {
                jSONObject.put("os", os);
            }
            String imei = getImei(context);
            if (!TextUtils.isEmpty(imei)) {
                jSONObject.put("imei", imei);
            }
            String meid = getMeid(context);
            if (!TextUtils.isEmpty(meid)) {
                jSONObject.put("meid", meid);
            }
            String imsi = getImsi(context);
            if (!TextUtils.isEmpty(imsi)) {
                jSONObject.put("imsi", imsi);
            }
            String mac = getMac(context);
            if (!TextUtils.isEmpty(mac)) {
                jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_MAC, mac);
            }
            String iccid = getIccid(context);
            if (!TextUtils.isEmpty(iccid)) {
                jSONObject.put(am.aa, iccid);
            }
            String serialNo = getSerialNo();
            if (!TextUtils.isEmpty(serialNo)) {
                jSONObject.put("serial", serialNo);
            }
            String androidId = getAndroidId(context);
            if (!TextUtils.isEmpty(androidId)) {
                jSONObject.put("androidid", androidId);
            }
            String cpu = getCpu();
            if (!TextUtils.isEmpty(cpu)) {
                jSONObject.put(am.f22460w, cpu);
            }
            String model = getModel();
            if (!TextUtils.isEmpty(model)) {
                jSONObject.put("model", model);
            }
            String sdSize = getSdSize();
            if (!TextUtils.isEmpty(sdSize)) {
                jSONObject.put("sdcard", sdSize);
            }
            String resolution = getResolution(context);
            if (!TextUtils.isEmpty(resolution)) {
                jSONObject.put("resolution", resolution);
            }
            String ssid = getSsid(context);
            if (!TextUtils.isEmpty(ssid)) {
                jSONObject.put("ssid", ssid);
            }
            String wifiBssid = getWifiBssid(context);
            if (!TextUtils.isEmpty(wifiBssid)) {
                jSONObject.put("bssid", wifiBssid);
            }
            String deviceName = getDeviceName();
            if (!TextUtils.isEmpty(deviceName)) {
                jSONObject.put("deviceName", deviceName);
            }
            String connectType = getConnectType(context);
            if (!TextUtils.isEmpty(connectType)) {
                jSONObject.put("connecttype", connectType);
            }
            try {
                strGenerateUAAid = generateUAAid(context);
            } catch (Exception e2) {
                e2.printStackTrace();
                strGenerateUAAid = "";
            }
            if (!TextUtils.isEmpty(strGenerateUAAid)) {
                jSONObject.put("ua", strGenerateUAAid);
            }
            double batteryCapacity = getBatteryCapacity(context);
            jSONObject.put("batterymaxcapacity", String.valueOf(batteryCapacity));
            jSONObject.put("batterycurrentcapacity", String.valueOf(batteryCapacity));
            BatteryInfo batteryInfo = new BatteryInfo(context);
            jSONObject.put("batterycurrentvoltage", batteryInfo.getVoltage());
            jSONObject.put("batterycurrenttemperature", batteryInfo.getTemperature());
            jSONObject.put("batterycurrentcapacity", (batteryCapacity * batteryInfo.getLevel()) / batteryInfo.getScale());
            return jSONObject.toString();
        } catch (JSONException unused) {
            return "";
        }
    }

    private static String generateUAAid(Context context) {
        StringBuilder sb = new StringBuilder();
        String packageName = context.getPackageName();
        String str = (TextUtils.isEmpty(packageName) || !packageName.contains(com.sina.weibo.BuildConfig.APPLICATION_ID)) ? "ssosdk" : "weibo";
        sb.append(Build.MANUFACTURER);
        sb.append("-");
        sb.append(Build.MODEL);
        sb.append("__");
        sb.append(str);
        sb.append("__");
        try {
            sb.append(WeiboSsoSdk.SDK_VERSION_CODE.replaceAll("\\s+", StrPool.UNDERLINE));
        } catch (Exception unused) {
            sb.append("unknown");
        }
        sb.append("__android__android");
        sb.append(Build.VERSION.RELEASE);
        return sb.toString();
    }

    private static String getAndroidId(Context context) {
        try {
            return Settings.Secure.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
        } catch (Exception unused) {
            return "";
        }
    }

    private static double getBatteryCapacity(Context context) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
        Object objNewInstance;
        try {
            objNewInstance = Class.forName("com.android.internal.os.PowerProfile").getConstructor(Context.class).newInstance(context);
        } catch (Exception unused) {
            objNewInstance = null;
        }
        try {
            return ((Double) Class.forName("com.android.internal.os.PowerProfile").getMethod("getAveragePower", String.class).invoke(objNewInstance, "battery.capacity")).doubleValue();
        } catch (Exception unused2) {
            return 0.0d;
        }
    }

    private static String getConnectType(Context context) {
        String str = "none";
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                if (activeNetworkInfo.getType() == 0) {
                    switch (activeNetworkInfo.getSubtype()) {
                        case 1:
                        case 2:
                        case 4:
                        case 7:
                        case 11:
                            str = "2G";
                            break;
                        case 3:
                        case 5:
                        case 6:
                        case 8:
                        case 9:
                        case 10:
                        case 12:
                        case 14:
                        case 15:
                            str = "3G";
                            break;
                        case 13:
                            str = "4G";
                            break;
                    }
                } else if (activeNetworkInfo.getType() == 1) {
                    str = "wifi";
                }
            }
        } catch (Exception unused) {
        }
        return str;
    }

    private static String getCpu() {
        try {
            return Build.CPU_ABI;
        } catch (Exception unused) {
            return "";
        }
    }

    private static String getDeviceName() {
        try {
            return Build.BRAND;
        } catch (Exception unused) {
            return "";
        }
    }

    private static String getIccid(Context context) {
        return "";
    }

    private static String getImei(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE)).getDeviceId();
        } catch (Exception unused) {
            return "";
        }
    }

    private static String getImsi(Context context) {
        return "";
    }

    private static String getMac(Context context) {
        return getMacAddr();
    }

    private static String getMacAddr() throws SocketException {
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (networkInterface.getName().equalsIgnoreCase("wlan0")) {
                    byte[] hardwareAddress = networkInterface.getHardwareAddress();
                    if (hardwareAddress == null) {
                        return "";
                    }
                    StringBuilder sb = new StringBuilder();
                    for (byte b3 : hardwareAddress) {
                        sb.append(String.format("%02X:", Byte.valueOf(b3)));
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    return sb.toString();
                }
            }
            return "";
        } catch (Exception unused) {
            return "";
        }
    }

    private static String getMeid(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE)).getDeviceId();
        } catch (Exception unused) {
            return "";
        }
    }

    public static String getMfp(Context context) {
        try {
            return new String(genMfpString(context).getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return "";
        }
    }

    private static String getModel() {
        try {
            return Build.MODEL;
        } catch (Exception unused) {
            return "";
        }
    }

    private static String getOS() {
        try {
            return "Android " + Build.VERSION.RELEASE;
        } catch (Exception unused) {
            return "";
        }
    }

    private static PublicKey getPublicKey(String str) {
        return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(str.getBytes(), 2)));
    }

    private static String getResolution(Context context) {
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
            return String.valueOf(displayMetrics.widthPixels) + "*" + String.valueOf(displayMetrics.heightPixels);
        } catch (Exception unused) {
            return "";
        }
    }

    private static String getSdSize() {
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return Long.toString(statFs.getBlockCount() * statFs.getBlockSize());
        } catch (Exception unused) {
            return "";
        }
    }

    private static String getSerialNo() throws ClassNotFoundException {
        if (Build.VERSION.SDK_INT >= 26) {
            return getSerialNoForO();
        }
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", String.class, String.class).invoke(cls, "ro.serialno", "unknown");
        } catch (Exception unused) {
            return "";
        }
    }

    @TargetApi(26)
    private static String getSerialNoForO() {
        try {
            return Build.getSerial();
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    private static String getSsid(Context context) {
        try {
            WifiInfo connectionInfo = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo();
            return connectionInfo != null ? connectionInfo.getSSID() : "";
        } catch (Exception unused) {
            return "";
        }
    }

    public static String getWifiBssid(Context context) {
        try {
            WifiInfo connectionInfo = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo();
            return connectionInfo != null ? connectionInfo.getBSSID() : "";
        } catch (SecurityException unused) {
            return "";
        }
    }

    public static void init(Context context) {
    }

    private static int splite(byte[] bArr, int i2, int i3) {
        if (i2 >= bArr.length) {
            return -1;
        }
        return Math.min(bArr.length - i2, i3);
    }
}
