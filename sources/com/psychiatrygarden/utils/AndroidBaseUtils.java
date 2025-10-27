package com.psychiatrygarden.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import cn.hutool.core.text.StrPool;
import com.aliyun.vod.log.core.AliyunLogCommon;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

/* loaded from: classes6.dex */
public class AndroidBaseUtils {
    private static int mAPPVersionCode = -1;
    private static String mAPPVersionName = null;
    private static String mDeviceName = null;
    private static String mIMEI = null;
    private static String mIMSI = null;
    private static String mOSVersion = null;
    private static int mSDKVersion = -1;

    public static CharSequence GetUTCTime() {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.add(14, -(calendar.get(15) + calendar.get(16)));
        return DateFormat.format("yyyy'-'MM'-'dd'T'kk':'mm':'ss'Z'", calendar);
    }

    public static int getAPPVersionCode(Context context) throws PackageManager.NameNotFoundException {
        if (mAPPVersionCode == -1) {
            mAPPVersionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        }
        return mAPPVersionCode;
    }

    public static String getAppVersion(Context context) throws PackageManager.NameNotFoundException {
        if (TextUtils.isEmpty(mAPPVersionName)) {
            mAPPVersionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        }
        return mAPPVersionName;
    }

    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    public static String getDeviceName() {
        try {
            if (TextUtils.isEmpty(mDeviceName)) {
                mDeviceName = getDeviceBrand() + StrPool.UNDERLINE + Build.MODEL;
            }
            return mDeviceName;
        } catch (Exception unused) {
            return "UNKNOWN";
        }
    }

    @SuppressLint({"MissingPermission"})
    public static String getIMEI(Context context) {
        mIMEI = null;
        if (!TextUtils.isEmpty(null)) {
            return mIMEI;
        }
        String md52 = Md5Util.getMD52(getUniquePsuedoID());
        mIMEI = md52;
        return md52;
    }

    @SuppressLint({"MissingPermission"})
    public static String getIMSI(Context context) {
        if (TextUtils.isEmpty(mIMSI)) {
            mIMSI = ((TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE)).getDeviceId();
        }
        return mIMSI;
    }

    public static String getOSVersion() {
        if (TextUtils.isEmpty(mOSVersion)) {
            mOSVersion = Build.VERSION.RELEASE;
        }
        return mOSVersion;
    }

    public static int getSDKVersion() {
        if (mSDKVersion == -1) {
            mSDKVersion = Build.VERSION.SDK_INT;
        }
        return mSDKVersion;
    }

    public static String getSHA1(Context context) {
        try {
            byte[] bArrDigest = MessageDigest.getInstance("SHA1").digest(context.getPackageManager().getPackageInfo(context.getPackageName(), 64).signatures[0].toByteArray());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b3 : bArrDigest) {
                String upperCase = Integer.toHexString(b3 & 255).toUpperCase(Locale.US);
                if (upperCase.length() == 1) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(upperCase);
            }
            return stringBuffer.toString();
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e3) {
            e3.printStackTrace();
            return null;
        } catch (Exception e4) {
            e4.printStackTrace();
            return null;
        }
    }

    public static String getUniquePsuedoID() {
        String str = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.DISPLAY.length() % 10) + (Build.HOST.length() % 10) + (Build.ID.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10) + (Build.TAGS.length() % 10) + (Build.TYPE.length() % 10) + (Build.USER.length() % 10);
        try {
            return new UUID(str.hashCode(), Build.class.getField("SERIAL").get(null).toString().hashCode()).toString();
        } catch (Exception unused) {
            return new UUID(str.hashCode(), -905839116).toString();
        }
    }

    public static void hideSoftInputFromWindow(Context context) {
        if (((Activity) context).getWindow().getCurrentFocus() != null) {
            ((InputMethodManager) context.getSystemService("input_method")).hideSoftInputFromWindow(((Activity) context).getWindow().getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static boolean isCurOriLand(Context context) {
        try {
            return context.getResources().getConfiguration().orientation == 2;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static boolean isPad(Context context) {
        boolean z2 = (context.getResources().getConfiguration().screenLayout & 15) >= 3;
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return z2 || Math.sqrt(Math.pow((double) (((float) displayMetrics.widthPixels) / displayMetrics.xdpi), 2.0d) + Math.pow((double) (((float) displayMetrics.heightPixels) / displayMetrics.ydpi), 2.0d)) >= 7.0d;
    }
}
