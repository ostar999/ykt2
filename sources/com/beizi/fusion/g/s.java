package com.beizi.fusion.g;

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
import com.beizi.fusion.BeiZis;
import com.hjq.permissions.Permission;
import java.lang.reflect.Method;

/* loaded from: classes2.dex */
public class s {
    public static int a(Context context) {
        String simOperator;
        int i2;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
            if (5 != telephonyManager.getSimState() || (simOperator = telephonyManager.getSimOperator()) == null) {
                return 0;
            }
            if (simOperator.equals("46000") || simOperator.equals("46002") || simOperator.equals("46007")) {
                i2 = 1;
            } else if (simOperator.equals("46001")) {
                i2 = 2;
            } else {
                if (!simOperator.equals("46003")) {
                    return 0;
                }
                i2 = 3;
            }
            return i2;
        } catch (SecurityException unused) {
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public static int b(Context context) {
        int iIntValue;
        int networkType;
        if (Build.VERSION.SDK_INT < 29 || context.checkSelfPermission(Permission.READ_PHONE_STATE) != 0 || (BeiZis.getCustomController() != null && (BeiZis.getCustomController() == null || !BeiZis.getCustomController().isCanUsePhoneState()))) {
            return c(context);
        }
        NetworkInfo networkInfo = ((ConnectivityManager) context.getApplicationContext().getSystemService("connectivity")).getNetworkInfo(1);
        if (networkInfo != null && networkInfo.isConnected()) {
            return 4;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
        try {
            int iD = d();
            if (iD == -1) {
                networkType = telephonyManager.getNetworkType();
            } else {
                try {
                    Method declaredMethod = TelephonyManager.class.getDeclaredMethod("getDataNetworkType", Integer.TYPE);
                    declaredMethod.setAccessible(true);
                    iIntValue = ((Integer) declaredMethod.invoke(telephonyManager, Integer.valueOf(iD))).intValue();
                } catch (Throwable th) {
                    th.printStackTrace();
                    iIntValue = 0;
                }
                networkType = iIntValue == 0 ? telephonyManager.getNetworkType() : iIntValue;
            }
            if (networkType == 13) {
                networkType = a(context, networkType);
            }
            if (networkType == 20) {
                return 3;
            }
            switch (networkType) {
                case 1:
                case 2:
                case 4:
                case 7:
                case 11:
                    return 6;
                case 3:
                case 5:
                case 6:
                case 8:
                case 9:
                case 10:
                case 12:
                case 14:
                case 15:
                    return 1;
                case 13:
                    return 2;
                default:
                    return 5;
            }
        } catch (Throwable unused) {
            return 5;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int c(Context context) {
        NetworkInfo activeNetworkInfo;
        int i2;
        if (context == null) {
            return 5;
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
            if (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null) {
                return 5;
            }
            int type = activeNetworkInfo.getType();
            if (1 != type) {
                if (type != 0) {
                    return 5;
                }
                int subtype = activeNetworkInfo.getSubtype();
                String subtypeName = activeNetworkInfo.getSubtypeName();
                switch (subtype) {
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                    case 11:
                    case 16:
                        i2 = 6;
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
                    case 17:
                        break;
                    case 13:
                    case 18:
                        i2 = 2;
                        break;
                    case 19:
                    default:
                        if (!subtypeName.equalsIgnoreCase("TD-SCDMA") && !subtypeName.equalsIgnoreCase("WCDMA")) {
                            if (!subtypeName.equalsIgnoreCase("CDMA2000")) {
                            }
                        }
                        break;
                    case 20:
                        i2 = 3;
                        break;
                }
                return 5;
            }
            i2 = 4;
            return i2;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 5;
        }
    }

    public static String d(Context context) {
        try {
            if (((TelephonyManager) context.getApplicationContext().getSystemService(AliyunLogCommon.TERMINAL_TYPE)).getPhoneType() != 0) {
                return "1";
            }
        } catch (SecurityException unused) {
            int i2 = context.getResources().getConfiguration().screenLayout & 15;
            if (i2 != 4 && i2 != 3) {
                return "1";
            }
        }
        return "2";
    }

    private static int d() {
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

    public static String a() {
        try {
            return Build.BRAND;
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static String c() {
        try {
            return Build.VERSION.SDK_INT + "(" + Build.VERSION.RELEASE + ")";
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    @SuppressLint({"MissingPermission"})
    private static int a(Context context, int i2) {
        ServiceState serviceState;
        ServiceState serviceState2;
        if (Build.VERSION.SDK_INT < 29 || context.checkSelfPermission(Permission.READ_PHONE_STATE) != 0) {
            return i2;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
            int iD = d();
            if (iD == -1) {
                serviceState2 = telephonyManager.getServiceState();
            } else {
                try {
                    Method declaredMethod = TelephonyManager.class.getDeclaredMethod("getServiceStateForSubscriber", Integer.TYPE);
                    declaredMethod.setAccessible(true);
                    serviceState = (ServiceState) declaredMethod.invoke(telephonyManager, Integer.valueOf(iD));
                } catch (Throwable th) {
                    th.printStackTrace();
                    serviceState = null;
                }
                serviceState2 = serviceState == null ? telephonyManager.getServiceState() : serviceState;
            }
            if (serviceState2 == null) {
                return i2;
            }
            if (a(serviceState2.toString())) {
                return 20;
            }
            return i2;
        } catch (Throwable th2) {
            th2.printStackTrace();
            return i2;
        }
    }

    public static String b() {
        try {
            return Build.MODEL;
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    private static boolean a(String str) {
        return !TextUtils.isEmpty(str) && (str.contains("nrState=NOT_RESTRICTED") || str.contains("nrState=CONNECTED"));
    }
}
