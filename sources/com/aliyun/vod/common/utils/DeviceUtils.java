package com.aliyun.vod.common.utils;

import android.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.psychiatrygarden.utils.Constants;
import com.umeng.analytics.pro.aq;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class DeviceUtils {
    public static final int NETWORK_CLASS_2_G = 2;
    public static final int NETWORK_CLASS_3_G = 3;
    public static final int NETWORK_CLASS_4_G = 4;
    public static final int NETWORK_CLASS_UNKNOWN = 0;
    public static final int NETWORK_WIFI = 1;

    @TargetApi(11)
    public static void coptyToClipBoard(Context context, String str) {
        ((ClipboardManager) context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText(Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_LABEL, str));
    }

    public static int dip2px(Context context, float f2) {
        return (int) ((f2 * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static boolean existSDCard() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static long getAllSize() {
        if (!existSDCard()) {
            return 0L;
        }
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return statFs.getBlockCount() * statFs.getBlockSize();
    }

    public static long getAvailaleSize() {
        if (!existSDCard()) {
            return 0L;
        }
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return statFs.getAvailableBlocks() * statFs.getBlockSize();
    }

    public static String getExternalStorageDirectory() {
        Map<String, String> map = System.getenv();
        int size = map.values().size();
        String[] strArr = new String[size];
        map.values().toArray(strArr);
        String str = strArr[size - 1];
        if (!str.startsWith("/mnt/") || Environment.getExternalStorageDirectory().getAbsolutePath().equals(str)) {
            return null;
        }
        return str;
    }

    public static String getLatestCameraPicture(Context context) {
        if (!existSDCard()) {
            return null;
        }
        Cursor cursorQuery = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{aq.f22519d, "_data", "bucket_display_name", "datetaken", "mime_type"}, null, null, "datetaken DESC");
        if (cursorQuery.moveToFirst()) {
            return cursorQuery.getString(1);
        }
        return null;
    }

    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (identifier > 0) {
            return resources.getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static String getNetWorkType(Context context) {
        NetworkInfo activeNetworkInfo;
        if (context == null || (activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo()) == null) {
            return "";
        }
        int type = activeNetworkInfo.getType();
        return type == 0 ? "cellnetwork" : type == 1 ? "WiFi" : "";
    }

    public static DisplayMetrics getScreenPix(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static int getStatusBarHeight(Context context) {
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static int getTopBarHeight(Activity activity) {
        return activity.getWindow().findViewById(R.id.content).getTop();
    }

    public static String getUDID(Context context) {
        String string = Settings.Secure.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
        if (StringUtils.isEmpty(string) || string.equals("9774d56d682e549c") || string.length() < 15) {
            string = new BigInteger(64, new SecureRandom()).toString(16);
        }
        return StringUtils.isEmpty(string) ? "" : string;
    }

    public static void goHome(Context context) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.addFlags(270532608);
        context.startActivity(intent);
    }

    public static void hideInputSoftFromWindowMethod(Context context, View view) {
        try {
            ((InputMethodManager) context.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static boolean isActiveSoftInput(Context context) {
        return ((InputMethodManager) context.getSystemService("input_method")).isActive();
    }

    public static boolean isPhone(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE)).getPhoneType() != 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static boolean isProessRunning(Context context, String str) {
        Iterator<ActivityManager.RunningAppProcessInfo> it = ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses().iterator();
        while (it.hasNext()) {
            if (it.next().processName.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isServiceRunning(Context context, String str) throws SecurityException {
        List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningServices(Integer.MAX_VALUE);
        if (runningServices.size() == 0) {
            return false;
        }
        for (int i2 = 0; i2 < runningServices.size(); i2++) {
            if (runningServices.get(i2).service.getClassName().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTabletDevice(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 15) >= 3;
    }

    public static int px2dip(Context context, float f2) {
        return (int) ((f2 / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static void showInputSoftFromWindowMethod(Context context, View view) {
        try {
            ((InputMethodManager) context.getSystemService("input_method")).showSoftInput(view, 2);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @SuppressLint({"NewApi"})
    public static boolean startActivityForPackage(Context context, String str) throws PackageManager.NameNotFoundException {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(str, 0);
            Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.setFlags(131072);
            intent.setPackage(packageInfo.packageName);
            ResolveInfo next = context.getPackageManager().queryIntentActivities(intent, 0).iterator().next();
            if (next == null) {
                return false;
            }
            ActivityInfo activityInfo = next.activityInfo;
            String str2 = activityInfo.packageName;
            String str3 = activityInfo.name;
            Intent intent2 = new Intent("android.intent.action.MAIN");
            intent2.addCategory("android.intent.category.LAUNCHER");
            intent2.setFlags(270532608);
            intent2.setComponent(new ComponentName(str2, str3));
            context.startActivity(intent2);
            return true;
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static void vibrate(Context context, long j2) {
        ((Vibrator) context.getSystemService("vibrator")).vibrate(new long[]{0, j2}, -1);
    }

    public boolean isSoftKeyAvail(Activity activity) {
        final boolean[] zArr = {false};
        final View viewFindViewById = activity.getWindow().getDecorView().findViewById(R.id.content);
        viewFindViewById.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.aliyun.vod.common.utils.DeviceUtils.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                if (viewFindViewById.getRootView().getHeight() - viewFindViewById.getHeight() > 100) {
                    zArr[0] = true;
                }
            }
        });
        return zArr[0];
    }
}
