package io.agora.rtc.internal;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.WindowManager;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.hjq.permissions.Permission;
import com.yikaobang.yixue.R2;
import io.agora.rtc.internal.RtcEngineMessage;
import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/* loaded from: classes8.dex */
class CommonUtility {
    private static final String TAG = "CommonUtility";
    private static WeakReference<Application> mApplication;
    private volatile boolean mAccessible;
    private long mBridgeHandle;
    private WeakReference<Context> mContext;
    private AgoraPhoneStateListener mPhoneStateListener;
    private ConnectionChangeBroadcastReceiver mConnectionBroadcastReceiver = null;
    private BroadcastReceiver mOrientationObserver = null;
    private PowerConnectionReceiver mPowerConnectionReceiver = null;
    private int mMobileType = -1;
    private int batteryPercentage = 255;

    public class AgoraPhoneStateListener extends PhoneStateListener {
        private SignalStrength mSignalStrenth;
        private boolean phoneStatusNeedResume = false;

        public AgoraPhoneStateListener() {
        }

        private int invokeMethod(String methodName) {
            Method declaredMethod;
            try {
                SignalStrength signalStrength = this.mSignalStrenth;
                if (signalStrength != null && (declaredMethod = signalStrength.getClass().getDeclaredMethod(methodName, new Class[0])) != null) {
                    return ((Integer) declaredMethod.invoke(this.mSignalStrenth, new Object[0])).intValue();
                }
            } catch (Exception unused) {
            }
            return 0;
        }

        public int getAsuLevel() {
            if (Build.VERSION.SDK_INT <= 28) {
                return invokeMethod("getAsuLevel");
            }
            return 0;
        }

        public int getLevel() {
            return invokeMethod("getLevel");
        }

        public int getRssi() {
            if (Build.VERSION.SDK_INT <= 28) {
                return invokeMethod("getDbm");
            }
            return 0;
        }

        @Override // android.telephony.PhoneStateListener
        public void onCallStateChanged(int state, String incomingNumber) {
            if (((Context) CommonUtility.this.mContext.get()) == null || !CommonUtility.this.mAccessible) {
                return;
            }
            super.onCallStateChanged(state, incomingNumber);
            if (state == 0) {
                if (this.phoneStatusNeedResume) {
                    this.phoneStatusNeedResume = false;
                    Logging.i(CommonUtility.TAG, "system phone call end delay 1000ms");
                    new Handler().postDelayed(new Runnable() { // from class: io.agora.rtc.internal.CommonUtility.AgoraPhoneStateListener.1
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                CommonUtility.this.onPhoneStateChanged(true, 22, 0);
                            } catch (Exception e2) {
                                Logging.e(CommonUtility.TAG, "fail to resume ", e2);
                            }
                        }
                    }, 1000L);
                    return;
                }
                return;
            }
            if (state == 1) {
                Logging.i(CommonUtility.TAG, "system phone call ring");
                this.phoneStatusNeedResume = true;
                CommonUtility.this.onPhoneStateChanged(false, 22, 1);
            } else {
                if (state != 2) {
                    return;
                }
                Logging.i(CommonUtility.TAG, "system phone call start");
                this.phoneStatusNeedResume = true;
                CommonUtility.this.onPhoneStateChanged(false, 22, 2);
            }
        }

        @Override // android.telephony.PhoneStateListener
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            if (((Context) CommonUtility.this.mContext.get()) == null || !CommonUtility.this.mAccessible) {
                return;
            }
            super.onSignalStrengthsChanged(signalStrength);
            this.mSignalStrenth = signalStrength;
        }
    }

    public static class MobileType {
        public static final int Cdma = 1;
        public static final int Gsm = 0;
        public static final int Lte = 3;
        public static final int Unknown = -1;
        public static final int Wcdma = 2;
    }

    public CommonUtility(Context context, long bridge) {
        this.mAccessible = false;
        this.mPhoneStateListener = null;
        this.mBridgeHandle = 0L;
        this.mContext = new WeakReference<>(context);
        this.mBridgeHandle = bridge;
        try {
            this.mPhoneStateListener = new AgoraPhoneStateListener();
            ((TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE)).listen(this.mPhoneStateListener, R2.attr.alignContent);
        } catch (Exception e2) {
            Logging.e(TAG, "Unable to create PhoneStateListener, ", e2);
        }
        monitorConnectionEvent(true);
        monitorPowerChange(true);
        this.mAccessible = true;
        Logging.i(TAG, "[init] done!");
    }

    public static boolean canGetDefaultContext() {
        Looper.myLooper();
        Looper.getMainLooper();
        return true;
    }

    private static boolean checkAccessNetworkState(Context context) {
        return context != null && context.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") == 0;
    }

    private static boolean checkAccessWifiState(Context context) {
        return context != null && context.checkCallingOrSelfPermission("android.permission.ACCESS_WIFI_STATE") == 0;
    }

    private RtcEngineMessage.MediaNetworkInfo doGetNetworkInfo(Context context) {
        InetAddress inetAddressIntToInetAddress;
        if (context == null || !this.mAccessible) {
            return null;
        }
        RtcEngineMessage.MediaNetworkInfo mediaNetworkInfo = new RtcEngineMessage.MediaNetworkInfo();
        if (!checkAccessNetworkState(context)) {
            mediaNetworkInfo.ssid = "";
            mediaNetworkInfo.bssid = "";
            mediaNetworkInfo.rssi = 0;
            mediaNetworkInfo.signalLevel = 0;
            mediaNetworkInfo.frequency = 0;
            mediaNetworkInfo.linkspeed = 0;
            return mediaNetworkInfo;
        }
        String localHost = getLocalHost();
        if (localHost != null) {
            mediaNetworkInfo.localIp4 = localHost;
        }
        NetworkInfo networkInfo = Connectivity.getNetworkInfo(context);
        mediaNetworkInfo.networkType = Connectivity.getNetworkType(networkInfo);
        if (networkInfo != null) {
            mediaNetworkInfo.networkSubtype = networkInfo.getSubtype();
        }
        mediaNetworkInfo.dnsList = Connectivity.getDnsList();
        if (mediaNetworkInfo.networkType != 2) {
            AgoraPhoneStateListener agoraPhoneStateListener = this.mPhoneStateListener;
            if (agoraPhoneStateListener != null) {
                mediaNetworkInfo.rssi = agoraPhoneStateListener.getRssi();
                mediaNetworkInfo.signalLevel = this.mPhoneStateListener.getLevel();
                mediaNetworkInfo.asu = this.mPhoneStateListener.getAsuLevel();
            } else if (context.checkCallingOrSelfPermission(Permission.ACCESS_COARSE_LOCATION) == 0) {
                getSignalStrength(context, mediaNetworkInfo);
            }
        } else {
            if (!checkAccessWifiState(context)) {
                mediaNetworkInfo.ssid = "";
                mediaNetworkInfo.bssid = "";
                mediaNetworkInfo.rssi = 0;
                mediaNetworkInfo.signalLevel = 0;
                mediaNetworkInfo.frequency = 0;
                mediaNetworkInfo.linkspeed = 0;
                return mediaNetworkInfo;
            }
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
            if (dhcpInfo != null && (inetAddressIntToInetAddress = intToInetAddress(dhcpInfo.gateway)) != null) {
                mediaNetworkInfo.gatewayIp4 = inetAddressIntToInetAddress.getHostAddress();
            }
            WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null) {
                int rssi = connectionInfo.getRssi();
                mediaNetworkInfo.rssi = rssi;
                mediaNetworkInfo.signalLevel = WifiManager.calculateSignalLevel(rssi, 5);
                mediaNetworkInfo.linkspeed = connectionInfo.getLinkSpeed();
                int frequency = connectionInfo.getFrequency();
                mediaNetworkInfo.frequency = frequency;
                if (frequency >= 5000) {
                    mediaNetworkInfo.networkSubtype = 101;
                } else if (frequency >= 2400) {
                    mediaNetworkInfo.networkSubtype = 100;
                }
            }
        }
        return mediaNetworkInfo;
    }

    private static String getAndroidID(Context context) {
        return "";
    }

    private static String getAppPrivateStorageDir(Context context) {
        File externalFilesDir;
        return (!"mounted".equals(Environment.getExternalStorageState()) || (externalFilesDir = context.getExternalFilesDir(null)) == null) ? context.getFilesDir().getAbsolutePath() : externalFilesDir.getAbsolutePath();
    }

    private static String getAppStorageDir(Context context) {
        if (context == null) {
            return null;
        }
        if (context.checkCallingOrSelfPermission(Permission.READ_EXTERNAL_STORAGE) != 0) {
            Logging.w(TAG, "read external storage is not granted");
            return null;
        }
        return "/sdcard/" + context.getApplicationInfo().packageName;
    }

    private AudioManager getAudioManager(Context context) {
        if (context == null) {
            return null;
        }
        return (AudioManager) context.getSystemService("audio");
    }

    public static byte[] getContextInfo(Context context) {
        if (context == null) {
            return null;
        }
        RtcEngineMessage.PAndroidContextInfo pAndroidContextInfo = new RtcEngineMessage.PAndroidContextInfo();
        pAndroidContextInfo.device = DeviceUtils.getDeviceId();
        pAndroidContextInfo.deviceInfo = DeviceUtils.getDeviceInfo();
        pAndroidContextInfo.systemInfo = DeviceUtils.getSystemInfo();
        pAndroidContextInfo.configDir = getAppPrivateStorageDir(context);
        pAndroidContextInfo.dataDir = context.getCacheDir().getAbsolutePath();
        pAndroidContextInfo.pluginDir = context.getApplicationInfo().nativeLibraryDir;
        pAndroidContextInfo.imei = "";
        pAndroidContextInfo.macAddress = "";
        pAndroidContextInfo.androidID = "";
        if (TextUtils.isEmpty(pAndroidContextInfo.device)) {
            pAndroidContextInfo.device = "";
        }
        if (TextUtils.isEmpty(pAndroidContextInfo.deviceInfo)) {
            pAndroidContextInfo.deviceInfo = "";
        }
        if (TextUtils.isEmpty(pAndroidContextInfo.systemInfo)) {
            pAndroidContextInfo.systemInfo = "";
        }
        if (TextUtils.isEmpty(pAndroidContextInfo.configDir)) {
            pAndroidContextInfo.configDir = "";
        }
        if (TextUtils.isEmpty(pAndroidContextInfo.dataDir)) {
            pAndroidContextInfo.dataDir = "";
        }
        if (TextUtils.isEmpty(pAndroidContextInfo.pluginDir)) {
            pAndroidContextInfo.pluginDir = "";
        }
        if (TextUtils.isEmpty(pAndroidContextInfo.imei)) {
            pAndroidContextInfo.imei = "";
        }
        if (TextUtils.isEmpty(pAndroidContextInfo.macAddress)) {
            pAndroidContextInfo.macAddress = "";
        }
        if (TextUtils.isEmpty(pAndroidContextInfo.androidID)) {
            pAndroidContextInfo.androidID = "";
        }
        return pAndroidContextInfo.marshall();
    }

    public static String getLocalHost() {
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (!networkInterface.getName().startsWith("usb")) {
                    Iterator it = Collections.list(networkInterface.getInetAddresses()).iterator();
                    while (it.hasNext()) {
                        String strInetAddressToIpAddress = inetAddressToIpAddress((InetAddress) it.next());
                        if (strInetAddressToIpAddress != null && !strInetAddressToIpAddress.isEmpty()) {
                            return strInetAddressToIpAddress;
                        }
                    }
                }
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    public static String[] getLocalHostList() {
        try {
            ArrayList<NetworkInterface> list = Collections.list(NetworkInterface.getNetworkInterfaces());
            ArrayList arrayList = new ArrayList();
            for (NetworkInterface networkInterface : list) {
                if (!networkInterface.getName().startsWith("usb")) {
                    Iterator it = Collections.list(networkInterface.getInetAddresses()).iterator();
                    while (it.hasNext()) {
                        String strInetAddressToIpAddress = inetAddressToIpAddress((InetAddress) it.next());
                        if (strInetAddressToIpAddress != null) {
                            arrayList.add(strInetAddressToIpAddress);
                        }
                    }
                }
            }
            if (arrayList.isEmpty()) {
                return null;
            }
            String[] strArr = new String[arrayList.size()];
            Iterator it2 = arrayList.iterator();
            int i2 = 0;
            while (it2.hasNext()) {
                strArr[i2] = (String) it2.next();
                i2++;
            }
            return strArr;
        } catch (Exception unused) {
            return null;
        }
    }

    public static String getRandomUUID() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    @TargetApi(17)
    private boolean getSignalStrength(Context context, RtcEngineMessage.MediaNetworkInfo ni) {
        CellInfo cellInfo;
        CellSignalStrengthLte cellSignalStrength;
        CellSignalStrengthWcdma cellSignalStrength2;
        CellSignalStrengthCdma cellSignalStrength3;
        CellSignalStrengthGsm cellSignalStrength4;
        if (context == null) {
            this.mMobileType = -1;
            return false;
        }
        List<CellInfo> allCellInfo = ((TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE)).getAllCellInfo();
        if (allCellInfo == null || allCellInfo.isEmpty() || (cellInfo = allCellInfo.get(0)) == null) {
            return false;
        }
        try {
            int i2 = this.mMobileType;
            if ((i2 == -1 || i2 == 0) && (cellSignalStrength4 = ((CellInfoGsm) cellInfo).getCellSignalStrength()) != null) {
                this.mMobileType = 0;
                ni.rssi = cellSignalStrength4.getDbm();
                ni.signalLevel = cellSignalStrength4.getLevel();
                ni.asu = cellSignalStrength4.getAsuLevel();
                return true;
            }
        } catch (Exception unused) {
            this.mMobileType = -1;
        }
        try {
            int i3 = this.mMobileType;
            if ((i3 == -1 || i3 == 1) && (cellSignalStrength3 = ((CellInfoCdma) cellInfo).getCellSignalStrength()) != null) {
                this.mMobileType = 1;
                ni.rssi = cellSignalStrength3.getDbm();
                ni.signalLevel = cellSignalStrength3.getLevel();
                ni.asu = cellSignalStrength3.getAsuLevel();
                return true;
            }
        } catch (Exception unused2) {
            this.mMobileType = -1;
        }
        try {
            int i4 = this.mMobileType;
            if ((i4 == -1 || i4 == 2) && (cellSignalStrength2 = ((CellInfoWcdma) cellInfo).getCellSignalStrength()) != null) {
                this.mMobileType = 2;
                ni.rssi = cellSignalStrength2.getDbm();
                ni.signalLevel = cellSignalStrength2.getLevel();
                ni.asu = cellSignalStrength2.getAsuLevel();
                return true;
            }
        } catch (Exception unused3) {
            this.mMobileType = -1;
        }
        try {
            int i5 = this.mMobileType;
            if ((i5 == -1 || i5 == 3) && (cellSignalStrength = ((CellInfoLte) cellInfo).getCellSignalStrength()) != null) {
                this.mMobileType = 3;
                ni.rssi = cellSignalStrength.getDbm();
                ni.signalLevel = cellSignalStrength.getLevel();
                ni.asu = cellSignalStrength.getAsuLevel();
                return true;
            }
        } catch (Exception unused4) {
            this.mMobileType = -1;
        }
        return false;
    }

    private static String getSystemProperty(String name) throws Exception {
        Class<?> cls = Class.forName("android.os.SystemProperties");
        return (String) cls.getMethod("get", String.class).invoke(cls, name);
    }

    private static String inetAddressToIpAddress(InetAddress address) {
        if (address.isLoopbackAddress()) {
            return null;
        }
        if (address instanceof Inet4Address) {
            return ((Inet4Address) address).getHostAddress();
        }
        boolean z2 = address instanceof Inet6Address;
        return null;
    }

    private static InetAddress intToInetAddress(int hostAddress) {
        try {
            return InetAddress.getByAddress(new byte[]{(byte) (hostAddress & 255), (byte) ((hostAddress >> 8) & 255), (byte) ((hostAddress >> 16) & 255), (byte) ((hostAddress >> 24) & 255)});
        } catch (UnknownHostException unused) {
            return null;
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(12:0|2|(2:57|3)|(9:5|(0)(1:9)|10|63|11|(1:17)|20|(11:22|(1:24)|61|28|(1:34)|59|37|(1:41)|55|44|(1:48))|(1:65)(2:53|54))|7|10|63|11|(3:13|15|17)(0)|20|(0)|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0081, code lost:
    
        io.agora.rtc.internal.Logging.e(io.agora.rtc.internal.CommonUtility.TAG, "get property arch fail.");
     */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0064 A[Catch: Exception -> 0x0081, TRY_LEAVE, TryCatch #4 {Exception -> 0x0081, blocks: (B:11:0x0044, B:13:0x004c, B:15:0x0058, B:17:0x0064), top: B:63:0x0044 }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x015a  */
    /* JADX WARN: Removed duplicated region for block: B:65:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean isSimulatorProperty() {
        /*
            Method dump skipped, instructions count: 348
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.agora.rtc.internal.CommonUtility.isSimulatorProperty():boolean");
    }

    private native void nativeAudioRoutingPhoneChanged(long bridgeHandle, boolean enableAudio, int event, int arg);

    private native int nativeNotifyNetworkChange(long bridgeHandle, byte[] networkInfo);

    private void regiseterBroadcaster(Context context) {
        if (context == null) {
            return;
        }
        this.mOrientationObserver = new BroadcastReceiver() { // from class: io.agora.rtc.internal.CommonUtility.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (intent.getAction().equals("android.intent.action.CONFIGURATION_CHANGED") && CommonUtility.this.mAccessible) {
                    ((WindowManager) ((Context) CommonUtility.this.mContext.get()).getSystemService("window")).getDefaultDisplay().getRotation();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
        context.registerReceiver(this.mOrientationObserver, intentFilter);
    }

    private void unregisterBroadcaster(Context context) {
        BroadcastReceiver broadcastReceiver;
        if (context == null || (broadcastReceiver = this.mOrientationObserver) == null) {
            return;
        }
        context.unregisterReceiver(broadcastReceiver);
    }

    public void destroy() {
        this.mAccessible = false;
        Context context = this.mContext.get();
        if (this.mPhoneStateListener != null && context != null) {
            ((TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE)).listen(this.mPhoneStateListener, 0);
            this.mPhoneStateListener = null;
        }
        monitorConnectionEvent(false);
        monitorPowerChange(false);
    }

    public int getAndroidVersion() {
        return Build.VERSION.SDK_INT;
    }

    public int getBatteryLifePercent() {
        if (this.mContext.get() == null || !this.mAccessible) {
            return 255;
        }
        return this.batteryPercentage;
    }

    public int getFrontCameraIndex() {
        return DeviceUtils.selectFrontCamera();
    }

    public byte[] getNetworkInfo() {
        RtcEngineMessage.MediaNetworkInfo mediaNetworkInfoDoGetNetworkInfo;
        Context context = this.mContext.get();
        if (context == null || !this.mAccessible || (mediaNetworkInfoDoGetNetworkInfo = doGetNetworkInfo(context)) == null) {
            return null;
        }
        return mediaNetworkInfoDoGetNetworkInfo.marshall();
    }

    public int getNetworkType() {
        Context context = this.mContext.get();
        if (context != null && this.mAccessible && checkAccessNetworkState(context)) {
            return Connectivity.getNetworkType(context);
        }
        return -1;
    }

    public int getNumberOfCameras() {
        return DeviceUtils.getNumberOfCameras();
    }

    /* JADX WARN: Can't wrap try/catch for region: R(16:0|2|54|3|(1:5)(1:(1:7)(1:9))|(3:50|10|(1:16)(3:55|13|14))|52|20|(1:22)|23|26|(1:28)|29|(2:31|(1:41))(2:42|(1:47))|48|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0079, code lost:
    
        io.agora.rtc.internal.Logging.e(io.agora.rtc.internal.CommonUtility.TAG, "get manufacturer info fail.");
     */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0060 A[Catch: Exception -> 0x0079, TryCatch #1 {Exception -> 0x0079, blocks: (B:20:0x0052, B:22:0x0060, B:23:0x0062), top: B:52:0x0052 }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00b8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int isSimulator() {
        /*
            r10 = this;
            java.lang.String r0 = "unknown"
            java.lang.String r1 = ""
            r2 = 28
            r3 = 1
            r4 = 0
            int r5 = android.os.Build.VERSION.SDK_INT     // Catch: java.lang.Exception -> L49
            r6 = 26
            if (r5 >= r6) goto L11
            java.lang.String r7 = android.os.Build.SERIAL     // Catch: java.lang.Exception -> L49
            goto L19
        L11:
            if (r5 > r2) goto L18
            java.lang.String r7 = com.blankj.utilcode.util.l.a()     // Catch: java.lang.Exception -> L49
            goto L19
        L18:
            r7 = r1
        L19:
            java.lang.String r8 = r7.toLowerCase()     // Catch: java.lang.Exception -> L4a
            boolean r8 = r8.equals(r0)     // Catch: java.lang.Exception -> L4a
            if (r8 == 0) goto L47
            if (r5 >= r6) goto L47
            java.lang.String r5 = io.agora.rtc.internal.CommonUtility.TAG     // Catch: java.lang.Exception -> L45
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L45
            r6.<init>()     // Catch: java.lang.Exception -> L45
            java.lang.String r8 = "serial = "
            r6.append(r8)     // Catch: java.lang.Exception -> L45
            r6.append(r7)     // Catch: java.lang.Exception -> L45
            java.lang.String r8 = ", suspectCount = "
            r6.append(r8)     // Catch: java.lang.Exception -> L45
            r6.append(r3)     // Catch: java.lang.Exception -> L45
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Exception -> L45
            io.agora.rtc.internal.Logging.i(r5, r6)     // Catch: java.lang.Exception -> L45
            r5 = r3
            goto L52
        L45:
            r5 = r3
            goto L4b
        L47:
            r5 = r4
            goto L52
        L49:
            r7 = r1
        L4a:
            r5 = r4
        L4b:
            java.lang.String r6 = io.agora.rtc.internal.CommonUtility.TAG
            java.lang.String r8 = "get serial info fail."
            io.agora.rtc.internal.Logging.e(r6, r8)
        L52:
            java.lang.String r1 = android.os.Build.MANUFACTURER     // Catch: java.lang.Exception -> L79
            java.lang.String r6 = r1.toLowerCase()     // Catch: java.lang.Exception -> L79
            java.lang.String r8 = "netease"
            boolean r6 = r6.contains(r8)     // Catch: java.lang.Exception -> L79
            if (r6 == 0) goto L62
            int r5 = r5 + 1
        L62:
            java.lang.String r6 = io.agora.rtc.internal.CommonUtility.TAG     // Catch: java.lang.Exception -> L79
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L79
            r8.<init>()     // Catch: java.lang.Exception -> L79
            java.lang.String r9 = "manufacturer = "
            r8.append(r9)     // Catch: java.lang.Exception -> L79
            r8.append(r1)     // Catch: java.lang.Exception -> L79
            java.lang.String r8 = r8.toString()     // Catch: java.lang.Exception -> L79
            io.agora.rtc.internal.Logging.i(r6, r8)     // Catch: java.lang.Exception -> L79
            goto L80
        L79:
            java.lang.String r6 = io.agora.rtc.internal.CommonUtility.TAG
            java.lang.String r8 = "get manufacturer info fail."
            io.agora.rtc.internal.Logging.e(r6, r8)
        L80:
            boolean r6 = r10.isSimulatorProperty()
            if (r6 == 0) goto L88
            int r5 = r5 + 1
        L88:
            int r6 = android.os.Build.VERSION.SDK_INT
            java.lang.String r8 = "welldo"
            if (r6 <= r2) goto Lb8
            java.lang.String r0 = "nokia"
            boolean r0 = r0.equalsIgnoreCase(r1)
            if (r0 == 0) goto Lab
            java.lang.String r0 = "Nokia_N1"
            java.lang.String r2 = android.os.Build.DEVICE
            boolean r0 = r0.equalsIgnoreCase(r2)
            if (r0 != 0) goto Laa
            java.lang.String r0 = "N1"
            java.lang.String r2 = android.os.Build.MODEL
            boolean r0 = r0.equalsIgnoreCase(r2)
            if (r0 == 0) goto Lab
        Laa:
            return r4
        Lab:
            if (r5 <= 0) goto Lcf
            java.lang.String r0 = r1.toLowerCase()
            boolean r0 = r0.contains(r8)
            if (r0 != 0) goto Lcf
            return r3
        Lb8:
            java.lang.String r2 = r7.toLowerCase()
            boolean r0 = r2.equals(r0)
            if (r0 != 0) goto Lc4
            if (r5 <= 0) goto Lcf
        Lc4:
            java.lang.String r0 = r1.toLowerCase()
            boolean r0 = r0.contains(r8)
            if (r0 != 0) goto Lcf
            return r3
        Lcf:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: io.agora.rtc.internal.CommonUtility.isSimulator():int");
    }

    public int isSpeakerphoneEnabled(Context context) {
        if (context == null) {
            return 0;
        }
        return getAudioManager(context).isSpeakerphoneOn() ? 1 : 0;
    }

    public void monitorConnectionEvent(boolean monitor) {
        ConnectionChangeBroadcastReceiver connectionChangeBroadcastReceiver;
        ConnectionChangeBroadcastReceiver connectionChangeBroadcastReceiver2;
        if (!monitor) {
            try {
                Context context = this.mContext.get();
                if (context != null && (connectionChangeBroadcastReceiver = this.mConnectionBroadcastReceiver) != null) {
                    context.unregisterReceiver(connectionChangeBroadcastReceiver);
                }
            } catch (IllegalArgumentException unused) {
            }
            this.mConnectionBroadcastReceiver = null;
            return;
        }
        if (this.mConnectionBroadcastReceiver == null) {
            try {
                this.mConnectionBroadcastReceiver = new ConnectionChangeBroadcastReceiver(this);
                Context context2 = this.mContext.get();
                if (context2 == null || (connectionChangeBroadcastReceiver2 = this.mConnectionBroadcastReceiver) == null) {
                    return;
                }
                context2.registerReceiver(connectionChangeBroadcastReceiver2, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
            } catch (Exception e2) {
                Logging.e(TAG, "Unable to create ConnectionChangeBroadcastReceiver, ", e2);
            }
        }
    }

    public void monitorPowerChange(boolean monitor) {
        PowerConnectionReceiver powerConnectionReceiver;
        if (!monitor) {
            try {
                Context context = this.mContext.get();
                if (context != null && (powerConnectionReceiver = this.mPowerConnectionReceiver) != null) {
                    context.unregisterReceiver(powerConnectionReceiver);
                }
            } catch (IllegalArgumentException unused) {
            }
            this.mPowerConnectionReceiver = null;
            return;
        }
        if (this.mPowerConnectionReceiver == null) {
            try {
                this.mPowerConnectionReceiver = new PowerConnectionReceiver(this);
                Context context2 = this.mContext.get();
                if (context2 == null || this.mPowerConnectionReceiver == null) {
                    return;
                }
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
                context2.registerReceiver(this.mPowerConnectionReceiver, intentFilter);
            } catch (Exception e2) {
                Logging.e(TAG, "Unable to create PowerConnectionReceiver, ", e2);
            }
        }
    }

    public void notifyNetworkChange() {
        byte[] networkInfo;
        if (this.mContext.get() == null || !this.mAccessible || (networkInfo = getNetworkInfo()) == null) {
            return;
        }
        nativeNotifyNetworkChange(this.mBridgeHandle, networkInfo);
    }

    public void onPhoneStateChanged(boolean enableAudio, int event, int arg) {
        if (this.mBridgeHandle == 0 || !this.mAccessible) {
            return;
        }
        nativeAudioRoutingPhoneChanged(this.mBridgeHandle, enableAudio, event, arg);
    }

    public void onPowerChange(int batteryPct) {
        if (this.mContext.get() == null || !this.mAccessible) {
            return;
        }
        this.batteryPercentage = batteryPct;
    }
}
