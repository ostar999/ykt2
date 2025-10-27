package com.beizi.ad.internal.utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.ServiceState;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.beizi.ad.BeiZi;
import com.beizi.ad.c.e;
import com.hjq.permissions.Permission;
import java.lang.reflect.Method;

/* loaded from: classes2.dex */
public class UserEnvInfoUtil {
    @SuppressLint({"MissingPermission"})
    private static int adjustNetworkType(Context context, int i2) {
        ServiceState serviceState;
        ServiceState serviceState2;
        if (Build.VERSION.SDK_INT < 29 || context.checkSelfPermission(Permission.READ_PHONE_STATE) != 0) {
            return i2;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
            int subId = getSubId();
            if (subId == -1) {
                serviceState2 = telephonyManager.getServiceState();
            } else {
                try {
                    Method declaredMethod = TelephonyManager.class.getDeclaredMethod("getServiceStateForSubscriber", Integer.TYPE);
                    declaredMethod.setAccessible(true);
                    serviceState = (ServiceState) declaredMethod.invoke(telephonyManager, Integer.valueOf(subId));
                } catch (Throwable th) {
                    th.printStackTrace();
                    serviceState = null;
                }
                serviceState2 = serviceState == null ? telephonyManager.getServiceState() : serviceState;
            }
            if (serviceState2 == null) {
                return i2;
            }
            if (isServiceStateFiveGAvailable(serviceState2.toString())) {
                return 20;
            }
            return i2;
        } catch (Throwable th2) {
            th2.printStackTrace();
            return i2;
        }
    }

    public static e.d getNetWorkType(Context context) {
        NetworkInfo activeNetworkInfo;
        if (context == null) {
            return e.d.NET_OTHER;
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
            if (connectivityManager != null && (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) != null) {
                int type = activeNetworkInfo.getType();
                if (1 == type) {
                    return e.d.NET_WIFI;
                }
                if (type == 0) {
                    int subtype = activeNetworkInfo.getSubtype();
                    String subtypeName = activeNetworkInfo.getSubtypeName();
                    switch (subtype) {
                        case 1:
                        case 2:
                        case 4:
                        case 7:
                        case 11:
                        case 16:
                            return e.d.NET_2G;
                        case 3:
                        case 5:
                        case 6:
                        case 8:
                        case 9:
                        case 10:
                        case 12:
                        case 14:
                        case 15:
                        case 17:
                            return e.d.NET_3G;
                        case 13:
                        case 18:
                            return e.d.NET_4G;
                        case 19:
                        default:
                            if (!subtypeName.equalsIgnoreCase("TD-SCDMA") && !subtypeName.equalsIgnoreCase("WCDMA") && !subtypeName.equalsIgnoreCase("CDMA2000")) {
                                return e.d.NET_OTHER;
                            }
                            return e.d.NET_3G;
                        case 20:
                            return e.d.NET_5G;
                    }
                }
            }
            return e.d.NET_OTHER;
        } catch (Exception e2) {
            e2.printStackTrace();
            return e.d.NET_OTHER;
        }
    }

    private static e.d getNetworkClass(Context context) {
        int iIntValue;
        int networkType;
        if (Build.VERSION.SDK_INT < 29 || context.checkSelfPermission(Permission.READ_PHONE_STATE) != 0 || (BeiZi.getCustomController() != null && (BeiZi.getCustomController() == null || !BeiZi.getCustomController().isCanUsePhoneState()))) {
            return getNetWorkType(context);
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
        NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getNetworkInfo(1) : null;
        if (networkInfo != null && networkInfo.isConnected()) {
            return e.d.NET_WIFI;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
        try {
            int subId = getSubId();
            if (subId == -1) {
                networkType = telephonyManager.getNetworkType();
            } else {
                try {
                    Method declaredMethod = TelephonyManager.class.getDeclaredMethod("getDataNetworkType", Integer.TYPE);
                    declaredMethod.setAccessible(true);
                    iIntValue = ((Integer) declaredMethod.invoke(telephonyManager, Integer.valueOf(subId))).intValue();
                } catch (Throwable th) {
                    th.printStackTrace();
                    iIntValue = 0;
                }
                networkType = iIntValue == 0 ? telephonyManager.getNetworkType() : iIntValue;
            }
            if (networkType == 13) {
                networkType = adjustNetworkType(context, networkType);
            }
            if (networkType == 20) {
                return e.d.NET_5G;
            }
            switch (networkType) {
                case 1:
                case 2:
                case 4:
                case 7:
                case 11:
                    return e.d.NET_2G;
                case 3:
                case 5:
                case 6:
                case 8:
                case 9:
                case 10:
                case 12:
                case 14:
                case 15:
                    return e.d.NET_3G;
                case 13:
                    return e.d.NET_4G;
                default:
                    return e.d.NET_OTHER;
            }
        } catch (Throwable unused) {
            return e.d.NET_OTHER;
        }
    }

    private static int getSubId() {
        try {
            if (Build.VERSION.SDK_INT >= 24) {
                return SubscriptionManager.getDefaultDataSubscriptionId();
            }
            return -1;
        } catch (Throwable th) {
            th.printStackTrace();
            return -1;
        }
    }

    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception unused) {
            return "";
        }
    }

    private static boolean isServiceStateFiveGAvailable(String str) {
        return !TextUtils.isEmpty(str) && (str.contains("nrState=NOT_RESTRICTED") || str.contains("nrState=CONNECTED"));
    }

    public static boolean isUsingWifi(Context context) {
        return ((ConnectivityManager) context.getApplicationContext().getSystemService("connectivity")).getNetworkInfo(1).isConnected();
    }

    public static void refreshLocation(Context context) {
        UserEnvInfo.getInstance();
    }

    public static void retrieveUserEnvInfo(Context context) {
        if (context != null) {
            UserEnvInfo userEnvInfo = UserEnvInfo.getInstance();
            try {
                userEnvInfo.f4406net = getNetworkClass(context);
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
                if (telephonyManager != null && 5 == telephonyManager.getSimState()) {
                    String simOperator = telephonyManager.getSimOperator();
                    if (simOperator == null) {
                        userEnvInfo.isp = e.c.ISP_UNKNOWN;
                    } else if (simOperator.equals("46000") || simOperator.equals("46002") || simOperator.equals("46007")) {
                        userEnvInfo.isp = e.c.ISP_CN_MOBILE;
                    } else if (simOperator.equals("46001")) {
                        userEnvInfo.isp = e.c.ISP_CN_UNICOM;
                    } else if (simOperator.equals("46003")) {
                        userEnvInfo.isp = e.c.ISP_CN_TEL;
                    }
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
            userEnvInfo.ip = org.eclipse.jetty.util.StringUtil.ALL_INTERFACES;
            refreshLocation(context);
        }
    }
}
