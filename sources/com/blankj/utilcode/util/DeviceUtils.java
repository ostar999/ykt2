package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import com.blankj.utilcode.util.ShellUtils;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.UUID;

/* loaded from: classes2.dex */
public final class DeviceUtils {
    private static final String KEY_UDID = "KEY_UDID";
    private static volatile String udid;

    private DeviceUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static String[] getABIs() {
        return Build.SUPPORTED_ABIS;
    }

    @SuppressLint({"HardwareIds"})
    public static String getAndroidID() {
        String string = Settings.Secure.getString(Utils.getApp().getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
        return ("9774d56d682e549c".equals(string) || string == null) ? "" : string;
    }

    private static InetAddress getInetAddress() throws SocketException {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                if (networkInterfaceNextElement.isUp()) {
                    Enumeration<InetAddress> inetAddresses = networkInterfaceNextElement.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddressNextElement = inetAddresses.nextElement();
                        if (!inetAddressNextElement.isLoopbackAddress() && inetAddressNextElement.getHostAddress().indexOf(58) < 0) {
                            return inetAddressNextElement;
                        }
                    }
                }
            }
            return null;
        } catch (SocketException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    @RequiresPermission(allOf = {"android.permission.ACCESS_WIFI_STATE", "android.permission.CHANGE_WIFI_STATE"})
    public static String getMacAddress() throws SocketException {
        String macAddress = getMacAddress(null);
        if (!TextUtils.isEmpty(macAddress) || getWifiEnabled()) {
            return macAddress;
        }
        setWifiEnabled(true);
        setWifiEnabled(false);
        return getMacAddress(null);
    }

    private static String getMacAddressByFile() {
        String str;
        String str2;
        ShellUtils.CommandResult commandResultExecCmd = UtilsBridge.execCmd("getprop wifi.interface", false);
        if (commandResultExecCmd.result != 0 || (str = commandResultExecCmd.successMsg) == null) {
            return "02:00:00:00:00:00";
        }
        ShellUtils.CommandResult commandResultExecCmd2 = UtilsBridge.execCmd("cat /sys/class/net/" + str + "/address", false);
        return (commandResultExecCmd2.result != 0 || (str2 = commandResultExecCmd2.successMsg) == null || str2.length() <= 0) ? "02:00:00:00:00:00" : str2;
    }

    private static String getMacAddressByInetAddress() {
        NetworkInterface byInetAddress;
        byte[] hardwareAddress;
        try {
            InetAddress inetAddress = getInetAddress();
            if (inetAddress == null || (byInetAddress = NetworkInterface.getByInetAddress(inetAddress)) == null || (hardwareAddress = byInetAddress.getHardwareAddress()) == null || hardwareAddress.length <= 0) {
                return "02:00:00:00:00:00";
            }
            StringBuilder sb = new StringBuilder();
            for (byte b3 : hardwareAddress) {
                sb.append(String.format("%02x:", Byte.valueOf(b3)));
            }
            return sb.substring(0, sb.length() - 1);
        } catch (Exception e2) {
            e2.printStackTrace();
            return "02:00:00:00:00:00";
        }
    }

    private static String getMacAddressByNetworkInterface() throws SocketException {
        byte[] hardwareAddress;
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                if (networkInterfaceNextElement != null && networkInterfaceNextElement.getName().equalsIgnoreCase("wlan0") && (hardwareAddress = networkInterfaceNextElement.getHardwareAddress()) != null && hardwareAddress.length > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (byte b3 : hardwareAddress) {
                        sb.append(String.format("%02x:", Byte.valueOf(b3)));
                    }
                    return sb.substring(0, sb.length() - 1);
                }
            }
            return "02:00:00:00:00:00";
        } catch (Exception e2) {
            e2.printStackTrace();
            return "02:00:00:00:00:00";
        }
    }

    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    private static String getMacAddressByWifiInfo() {
        WifiInfo connectionInfo;
        try {
            WifiManager wifiManager = (WifiManager) Utils.getApp().getApplicationContext().getSystemService("wifi");
            if (wifiManager == null || (connectionInfo = wifiManager.getConnectionInfo()) == null) {
                return "02:00:00:00:00:00";
            }
            String macAddress = connectionInfo.getMacAddress();
            return !TextUtils.isEmpty(macAddress) ? macAddress : "02:00:00:00:00:00";
        } catch (Exception e2) {
            e2.printStackTrace();
            return "02:00:00:00:00:00";
        }
    }

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    public static String getModel() {
        String str = Build.MODEL;
        return str != null ? str.trim().replaceAll("\\s*", "") : "";
    }

    public static int getSDKVersionCode() {
        return Build.VERSION.SDK_INT;
    }

    public static String getSDKVersionName() {
        return Build.VERSION.RELEASE;
    }

    private static String getUdid(String str, String str2) {
        if (str2.equals("")) {
            return str + UUID.randomUUID().toString().replace("-", "");
        }
        return str + UUID.nameUUIDFromBytes(str2.getBytes()).toString().replace("-", "");
    }

    public static String getUniqueDeviceId() {
        return getUniqueDeviceId("", true);
    }

    private static String getUniqueDeviceIdReal(String str) {
        try {
            String androidID = getAndroidID();
            if (!TextUtils.isEmpty(androidID)) {
                return saveUdid(str + 2, androidID);
            }
        } catch (Exception unused) {
        }
        return saveUdid(str + 9, "");
    }

    private static boolean getWifiEnabled() {
        WifiManager wifiManager = (WifiManager) Utils.getApp().getSystemService("wifi");
        if (wifiManager == null) {
            return false;
        }
        return wifiManager.isWifiEnabled();
    }

    @RequiresApi(api = 17)
    public static boolean isAdbEnabled() {
        return Settings.Secure.getInt(Utils.getApp().getContentResolver(), "adb_enabled", 0) > 0;
    }

    private static boolean isAddressNotInExcepts(String str, String... strArr) {
        if (TextUtils.isEmpty(str) || "02:00:00:00:00:00".equals(str)) {
            return false;
        }
        if (strArr != null && strArr.length != 0) {
            for (String str2 : strArr) {
                if (str2 != null && str2.equals(str)) {
                    return false;
                }
            }
        }
        return true;
    }

    @RequiresApi(api = 17)
    public static boolean isDevelopmentSettingsEnabled() {
        return Settings.Global.getInt(Utils.getApp().getContentResolver(), "development_settings_enabled", 0) > 0;
    }

    public static boolean isDeviceRooted() {
        String[] strArr = {"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/", "/system/sbin/", "/usr/bin/", "/vendor/bin/"};
        for (int i2 = 0; i2 < 11; i2++) {
            if (new File(strArr[i2] + "su").exists()) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0063  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isEmulator() {
        /*
            java.lang.String r0 = android.os.Build.FINGERPRINT
            java.lang.String r1 = "generic"
            boolean r2 = r0.startsWith(r1)
            r3 = 0
            r4 = 1
            if (r2 != 0) goto L63
            java.lang.String r2 = r0.toLowerCase()
            java.lang.String r5 = "vbox"
            boolean r2 = r2.contains(r5)
            if (r2 != 0) goto L63
            java.lang.String r0 = r0.toLowerCase()
            java.lang.String r2 = "test-keys"
            boolean r0 = r0.contains(r2)
            if (r0 != 0) goto L63
            java.lang.String r0 = android.os.Build.MODEL
            java.lang.String r2 = "google_sdk"
            boolean r5 = r0.contains(r2)
            if (r5 != 0) goto L63
            java.lang.String r5 = "Emulator"
            boolean r5 = r0.contains(r5)
            if (r5 != 0) goto L63
            java.lang.String r5 = "Android SDK built for x86"
            boolean r0 = r0.contains(r5)
            if (r0 != 0) goto L63
            java.lang.String r0 = android.os.Build.MANUFACTURER
            java.lang.String r5 = "Genymotion"
            boolean r0 = r0.contains(r5)
            if (r0 != 0) goto L63
            java.lang.String r0 = android.os.Build.BRAND
            boolean r0 = r0.startsWith(r1)
            if (r0 == 0) goto L58
            java.lang.String r0 = android.os.Build.DEVICE
            boolean r0 = r0.startsWith(r1)
            if (r0 != 0) goto L63
        L58:
            java.lang.String r0 = android.os.Build.PRODUCT
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L61
            goto L63
        L61:
            r0 = r3
            goto L64
        L63:
            r0 = r4
        L64:
            if (r0 == 0) goto L67
            return r4
        L67:
            android.app.Application r0 = com.blankj.utilcode.util.Utils.getApp()
            java.lang.String r1 = "phone"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.telephony.TelephonyManager r0 = (android.telephony.TelephonyManager) r0
            if (r0 == 0) goto L7c
            java.lang.String r0 = r0.getNetworkOperatorName()
            if (r0 == 0) goto L7c
            goto L7e
        L7c:
            java.lang.String r0 = ""
        L7e:
            java.lang.String r0 = r0.toLowerCase()
            java.lang.String r1 = "android"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L8b
            return r4
        L8b:
            android.content.Intent r0 = new android.content.Intent
            r0.<init>()
            java.lang.String r1 = "tel:123456"
            android.net.Uri r1 = android.net.Uri.parse(r1)
            r0.setData(r1)
            java.lang.String r1 = "android.intent.action.DIAL"
            r0.setAction(r1)
            android.app.Application r1 = com.blankj.utilcode.util.Utils.getApp()
            android.content.pm.PackageManager r1 = r1.getPackageManager()
            android.content.ComponentName r0 = r0.resolveActivity(r1)
            if (r0 != 0) goto Lae
            r0 = r4
            goto Laf
        Lae:
            r0 = r3
        Laf:
            if (r0 == 0) goto Lb2
            return r4
        Lb2:
            boolean r0 = isEmulatorByCpu()
            if (r0 == 0) goto Lb9
            return r4
        Lb9:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.DeviceUtils.isEmulator():boolean");
    }

    private static boolean isEmulatorByCpu() throws IOException {
        String cpuInfo = readCpuInfo();
        return cpuInfo.contains("intel") || cpuInfo.contains("amd");
    }

    @RequiresPermission(allOf = {"android.permission.ACCESS_WIFI_STATE", "android.permission.INTERNET", "android.permission.CHANGE_WIFI_STATE"})
    public static boolean isSameDevice(String str) throws SocketException {
        if (TextUtils.isEmpty(str) && str.length() < 33) {
            return false;
        }
        if (str.equals(udid) || str.equals(UtilsBridge.getSpUtils4Utils().getString(KEY_UDID, null))) {
            return true;
        }
        int length = str.length() - 33;
        int i2 = length + 1;
        String strSubstring = str.substring(length, i2);
        if (strSubstring.startsWith("1")) {
            String macAddress = getMacAddress();
            if (macAddress.equals("")) {
                return false;
            }
            return str.substring(i2).equals(getUdid("", macAddress));
        }
        if (!strSubstring.startsWith("2")) {
            return false;
        }
        String androidID = getAndroidID();
        if (TextUtils.isEmpty(androidID)) {
            return false;
        }
        return str.substring(i2).equals(getUdid("", androidID));
    }

    public static boolean isTablet() {
        return (Resources.getSystem().getConfiguration().screenLayout & 15) >= 3;
    }

    private static String readCpuInfo() throws IOException {
        try {
            Process processStart = new ProcessBuilder("/system/bin/cat", "/proc/cpuinfo").start();
            StringBuilder sb = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(processStart.getInputStream(), "utf-8"));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    bufferedReader.close();
                    return sb.toString().toLowerCase();
                }
                sb.append(line);
            }
        } catch (IOException unused) {
            return "";
        }
    }

    private static String saveUdid(String str, String str2) {
        udid = getUdid(str, str2);
        UtilsBridge.getSpUtils4Utils().put(KEY_UDID, udid);
        return udid;
    }

    @RequiresPermission("android.permission.CHANGE_WIFI_STATE")
    private static void setWifiEnabled(boolean z2) {
        WifiManager wifiManager = (WifiManager) Utils.getApp().getSystemService("wifi");
        if (wifiManager == null || z2 == wifiManager.isWifiEnabled()) {
            return;
        }
        wifiManager.setWifiEnabled(z2);
    }

    public static String getUniqueDeviceId(String str) {
        return getUniqueDeviceId(str, true);
    }

    public static String getUniqueDeviceId(boolean z2) {
        return getUniqueDeviceId("", z2);
    }

    public static String getUniqueDeviceId(String str, boolean z2) {
        if (!z2) {
            return getUniqueDeviceIdReal(str);
        }
        if (udid == null) {
            synchronized (DeviceUtils.class) {
                if (udid == null) {
                    String string = UtilsBridge.getSpUtils4Utils().getString(KEY_UDID, null);
                    if (string != null) {
                        udid = string;
                        return udid;
                    }
                    return getUniqueDeviceIdReal(str);
                }
            }
        }
        return udid;
    }

    @RequiresPermission(allOf = {"android.permission.ACCESS_WIFI_STATE"})
    public static String getMacAddress(String... strArr) throws SocketException {
        String macAddressByNetworkInterface = getMacAddressByNetworkInterface();
        if (isAddressNotInExcepts(macAddressByNetworkInterface, strArr)) {
            return macAddressByNetworkInterface;
        }
        String macAddressByInetAddress = getMacAddressByInetAddress();
        if (isAddressNotInExcepts(macAddressByInetAddress, strArr)) {
            return macAddressByInetAddress;
        }
        String macAddressByWifiInfo = getMacAddressByWifiInfo();
        if (isAddressNotInExcepts(macAddressByWifiInfo, strArr)) {
            return macAddressByWifiInfo;
        }
        String macAddressByFile = getMacAddressByFile();
        return isAddressNotInExcepts(macAddressByFile, strArr) ? macAddressByFile : "";
    }
}
