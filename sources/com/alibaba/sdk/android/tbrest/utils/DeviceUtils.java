package com.alibaba.sdk.android.tbrest.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.ta.utdid2.device.UTDevice;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Random;

/* loaded from: classes2.dex */
public class DeviceUtils {
    private static final String NETWORK_CLASS_2_G = "2G";
    private static final String NETWORK_CLASS_3_G = "3G";
    private static final String NETWORK_CLASS_4_G = "4G";
    private static final String NETWORK_CLASS_5_G = "5G";
    private static final String NETWORK_CLASS_UNKNOWN = "Unknown";
    public static final String NETWORK_CLASS_WIFI = "Wi-Fi";
    private static String carrier;
    private static String cpuName;
    private static String[] arrayOfString = {"Unknown", "Unknown"};
    private static String imsi = null;
    private static String imei = null;

    public static byte[] IntGetBytes(int i2) {
        byte[] bArr = {(byte) ((i >> 8) % 256), (byte) (i % 256), (byte) (i % 256), (byte) (i2 % 256)};
        int i3 = i2 >> 8;
        int i4 = i3 >> 8;
        return bArr;
    }

    public static String getCarrier(Context context) {
        try {
            String str = carrier;
            if (str != null) {
                return str;
            }
            String networkOperatorName = ((TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE)).getNetworkOperatorName();
            carrier = networkOperatorName;
            return networkOperatorName;
        } catch (Exception unused) {
            return null;
        }
    }

    public static String getCountry() {
        try {
            return Locale.getDefault().getCountry();
        } catch (Exception e2) {
            LogUtil.e("get country error ", e2);
            return null;
        }
    }

    public static String getCpuName() throws Throwable {
        FileReader fileReader;
        Throwable th;
        BufferedReader bufferedReader;
        String line;
        String str = cpuName;
        if (str != null) {
            return str;
        }
        try {
            try {
                fileReader = new FileReader("/proc/cpuinfo");
                try {
                    bufferedReader = new BufferedReader(fileReader);
                } catch (IOException unused) {
                    bufferedReader = null;
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader = null;
                }
            } catch (IOException unused2) {
                bufferedReader = null;
                fileReader = null;
            } catch (Throwable th3) {
                fileReader = null;
                th = th3;
                bufferedReader = null;
            }
        } catch (Exception unused3) {
        }
        do {
            try {
                line = bufferedReader.readLine();
            } catch (IOException unused4) {
                if (fileReader != null) {
                    fileReader.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                return null;
            } catch (Throwable th4) {
                th = th4;
                if (fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (Exception unused5) {
                        throw th;
                    }
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                throw th;
            }
            if (line == null) {
                fileReader.close();
                bufferedReader.close();
                return null;
            }
        } while (!line.contains("Hardware"));
        String str2 = line.split(":")[1];
        cpuName = str2;
        try {
            fileReader.close();
            bufferedReader.close();
        } catch (Exception unused6) {
        }
        return str2;
    }

    public static String getImei(Context context) {
        String str = imei;
        if (str != null) {
            return str;
        }
        String uniqueID = getUniqueID();
        imei = uniqueID;
        return uniqueID;
    }

    public static String getImsi(Context context) {
        String str = imsi;
        if (str != null) {
            return str;
        }
        if (StringUtils.isEmpty(str)) {
            imsi = getUniqueID();
        }
        return imsi;
    }

    public static String getLanguage() {
        try {
            return Locale.getDefault().getLanguage();
        } catch (Exception e2) {
            LogUtil.e("get country error ", e2);
            return null;
        }
    }

    private static String getNetworkClass(int i2) {
        if (i2 == 20) {
            return NETWORK_CLASS_5_G;
        }
        switch (i2) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return NETWORK_CLASS_2_G;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return NETWORK_CLASS_3_G;
            case 13:
                return NETWORK_CLASS_4_G;
            default:
                return "Unknown";
        }
    }

    @SuppressLint({"WrongConstant"})
    public static String[] getNetworkType(Context context) {
        if (context == null) {
            return arrayOfString;
        }
        if (context.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", context.getPackageName()) != 0) {
            return arrayOfString;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return arrayOfString;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return arrayOfString;
        }
        if (activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == 1) {
                String[] strArr = arrayOfString;
                strArr[0] = "Wi-Fi";
                return strArr;
            }
            if (activeNetworkInfo.getType() == 0) {
                if (isNRConnected((TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE))) {
                    arrayOfString[0] = NETWORK_CLASS_5_G;
                } else {
                    arrayOfString[0] = getNetworkClass(activeNetworkInfo.getSubtype());
                }
                arrayOfString[1] = activeNetworkInfo.getSubtypeName();
                return arrayOfString;
            }
        }
        return arrayOfString;
    }

    public static String getResolution(Context context) {
        try {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int i2 = displayMetrics.widthPixels;
            int i3 = displayMetrics.heightPixels;
            if (i2 > i3) {
                int i4 = i2 ^ i3;
                i3 ^= i4;
                i2 = i4 ^ i3;
            }
            return i3 + "*" + i2;
        } catch (Exception e2) {
            LogUtil.e("DeviceUtils getResolution: error", e2);
            return "Unknown";
        }
    }

    public static final String getUniqueID() {
        try {
            int iCurrentTimeMillis = (int) (System.currentTimeMillis() / 1000);
            int iNanoTime = (int) System.nanoTime();
            int iNextInt = new Random().nextInt();
            int iNextInt2 = new Random().nextInt();
            byte[] bArrIntGetBytes = IntGetBytes(iCurrentTimeMillis);
            byte[] bArrIntGetBytes2 = IntGetBytes(iNanoTime);
            byte[] bArrIntGetBytes3 = IntGetBytes(iNextInt);
            byte[] bArrIntGetBytes4 = IntGetBytes(iNextInt2);
            byte[] bArr = new byte[16];
            System.arraycopy(bArrIntGetBytes, 0, bArr, 0, 4);
            System.arraycopy(bArrIntGetBytes2, 0, bArr, 4, 4);
            System.arraycopy(bArrIntGetBytes3, 0, bArr, 8, 4);
            System.arraycopy(bArrIntGetBytes4, 0, bArr, 12, 4);
            return Base64.encodeBase64String(bArr);
        } catch (Exception unused) {
            return null;
        }
    }

    public static String getUtdid(Context context) {
        try {
            return UTDevice.getUtdid(context);
        } catch (Exception e2) {
            LogUtil.e("get utdid error ", e2);
            return null;
        }
    }

    private static boolean isNRConnected(TelephonyManager telephonyManager) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        int i2;
        try {
            Object objInvoke = Class.forName(telephonyManager.getClass().getName()).getDeclaredMethod("getServiceState", new Class[0]).invoke(telephonyManager, new Object[0]);
            for (Method method : Class.forName(objInvoke.getClass().getName()).getDeclaredMethods()) {
                i2 = (method.getName().equals("getNrStatus") || method.getName().equals("getNrState")) ? 0 : i2 + 1;
                method.setAccessible(true);
                return ((Integer) method.invoke(objInvoke, new Object[0])).intValue() == 3;
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }
}
