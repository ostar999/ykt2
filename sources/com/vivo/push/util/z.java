package com.vivo.push.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import com.hjq.permissions.Permission;
import com.yikaobang.yixue.R2;
import java.security.PublicKey;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes6.dex */
public final class z {

    /* renamed from: a, reason: collision with root package name */
    private static String[] f24484a = {"com.vivo.push.sdk.RegistrationReceiver", "com.vivo.push.sdk.service.PushService", "com.vivo.push.sdk.service.CommonJobService"};

    /* renamed from: b, reason: collision with root package name */
    private static String[] f24485b = {"android.permission.INTERNET", Permission.READ_PHONE_STATE, "android.permission.ACCESS_NETWORK_STATE", Permission.WRITE_SETTINGS, "android.permission.VIBRATE", Permission.WRITE_EXTERNAL_STORAGE, "android.permission.ACCESS_WIFI_STATE", "android.permission.WAKE_LOCK", Permission.GET_ACCOUNTS, "com.bbk.account.permission.READ_ACCOUNTINFO", "android.permission.AUTHENTICATE_ACCOUNTS", "android.permission.MOUNT_UNMOUNT_FILESYSTEMS", "android.permission.GET_TASKS"};

    /* renamed from: c, reason: collision with root package name */
    private static String[] f24486c = {"com.vivo.push.sdk.service.CommandService", "com.vivo.push.sdk.service.CommonJobService"};

    /* renamed from: d, reason: collision with root package name */
    private static String[] f24487d = {"com.vivo.push.sdk.RegistrationReceiver"};

    /* renamed from: e, reason: collision with root package name */
    private static String[] f24488e = new String[0];

    /* renamed from: f, reason: collision with root package name */
    private static Map<String, Bundle> f24489f = new ConcurrentHashMap();

    public static long a(Context context) {
        String strB = t.b(context);
        if (!TextUtils.isEmpty(strB)) {
            return a(context, strB);
        }
        p.a("Utility", "systemPushPkgName is null");
        return -1L;
    }

    public static String b(Context context, String str) throws PackageManager.NameNotFoundException {
        Object objA = a(context, str, "com.vivo.push.app_id");
        if (objA != null) {
            return objA.toString();
        }
        Object objA2 = a(context, str, "app_id");
        return objA2 != null ? objA2.toString() : "";
    }

    public static String c(Context context, String str) throws PackageManager.NameNotFoundException {
        Object objA = a(context, str, "verification_status");
        return objA != null ? objA.toString() : "";
    }

    private static void d(Context context, String str) throws VivoPushException {
        try {
            if (context.getPackageManager() == null) {
                throw new VivoPushException("localPackageManager is null");
            }
            ServiceInfo[] serviceInfoArr = context.getPackageManager().getPackageInfo(context.getPackageName(), 4).services;
            if (serviceInfoArr == null) {
                throw new VivoPushException("serviceInfos is null");
            }
            for (String str2 : f24486c) {
                a(str2, serviceInfoArr, str);
            }
        } catch (Exception e2) {
            throw new VivoPushException("error " + e2.getMessage());
        }
    }

    private static void e(Context context, String str) throws VivoPushException {
        if (f24488e.length <= 0) {
            return;
        }
        try {
            if (context.getPackageManager() == null) {
                throw new VivoPushException("localPackageManager is null");
            }
            ActivityInfo[] activityInfoArr = context.getPackageManager().getPackageInfo(context.getPackageName(), 1).activities;
            if (activityInfoArr == null) {
                throw new VivoPushException("activityInfos is null");
            }
            for (String str2 : f24488e) {
                a(str2, activityInfoArr, str);
            }
        } catch (Exception e2) {
            throw new VivoPushException("error " + e2.getMessage());
        }
    }

    private static void f(Context context, String str) throws VivoPushException {
        try {
            if (context.getPackageManager() == null) {
                throw new VivoPushException("localPackageManager is null");
            }
            ActivityInfo[] activityInfoArr = context.getPackageManager().getPackageInfo(context.getPackageName(), 2).receivers;
            if (activityInfoArr == null) {
                throw new VivoPushException("receivers is null");
            }
            for (String str2 : f24487d) {
                a(str2, activityInfoArr, str);
            }
        } catch (Exception e2) {
            throw new VivoPushException(e2.getMessage());
        }
    }

    public static PublicKey c(Context context) {
        Cursor cursorQuery = context.getContentResolver().query(com.vivo.push.p.f24404a, null, null, null, null);
        if (cursorQuery == null) {
            return null;
        }
        while (cursorQuery.moveToNext()) {
            try {
                try {
                    if ("pushkey".equals(cursorQuery.getString(cursorQuery.getColumnIndex("name")))) {
                        String string = cursorQuery.getString(cursorQuery.getColumnIndex("value"));
                        p.d("Utility", "result key : ".concat(String.valueOf(string)));
                        return u.a(string);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } finally {
                try {
                    cursorQuery.close();
                } catch (Exception unused) {
                }
            }
        }
        try {
            cursorQuery.close();
        } catch (Exception unused2) {
        }
        return null;
    }

    public static long a(Context context, String str) throws PackageManager.NameNotFoundException {
        Object objA = a(context, str, "com.vivo.push.sdk_version");
        if (objA == null) {
            objA = a(context, str, "sdk_version");
        }
        if (objA != null) {
            try {
                return Long.parseLong(objA.toString());
            } catch (Exception e2) {
                e2.printStackTrace();
                p.a("Utility", "getSdkVersionCode error ", e2);
                return -1L;
            }
        }
        p.a("Utility", "getSdkVersionCode sdk version is null");
        return -1L;
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x00c7, code lost:
    
        r10 = r10 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void b(android.content.Context r23) throws android.content.pm.PackageManager.NameNotFoundException, com.vivo.push.util.VivoPushException {
        /*
            Method dump skipped, instructions count: 428
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vivo.push.util.z.b(android.content.Context):void");
    }

    public static boolean d(Context context) {
        Cursor cursor = null;
        try {
            try {
                try {
                } catch (Throwable th) {
                    if (0 != 0) {
                        try {
                            cursor.close();
                        } catch (Exception e2) {
                            p.a("Utility", "close", e2);
                        }
                    }
                    throw th;
                }
            } catch (Exception e3) {
                p.a("Utility", "isSupport", e3);
                if (0 != 0) {
                    cursor.close();
                }
            }
        } catch (Exception e4) {
            p.a("Utility", "close", e4);
        }
        if (context == null) {
            p.a("Utility", "context is null");
            return false;
        }
        String packageName = context.getPackageName();
        Cursor cursorQuery = context.getContentResolver().query(com.vivo.push.p.f24405b, null, "pushVersion = ? and appPkgName = ? and appCode = ? ", new String[]{"323", packageName, String.valueOf(context.getPackageManager().getPackageInfo(packageName, 0).versionCode)}, null);
        if (cursorQuery == null) {
            p.a("Utility", "cursor is null");
            if (cursorQuery != null) {
                try {
                    cursorQuery.close();
                } catch (Exception e5) {
                    p.a("Utility", "close", e5);
                }
            }
            return false;
        }
        if (cursorQuery.moveToFirst() && (cursorQuery.getInt(cursorQuery.getColumnIndex("permission")) & 1) != 0) {
            try {
                cursorQuery.close();
            } catch (Exception e6) {
                p.a("Utility", "close", e6);
            }
            return true;
        }
        cursorQuery.close();
        return false;
    }

    public static Object a(Context context, String str, String str2) throws PackageManager.NameNotFoundException {
        Object obj;
        Bundle bundle;
        if (context == null || str2 == null || TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            Map<String, Bundle> map = f24489f;
            Object obj2 = (map == null || map.size() <= 0 || (bundle = f24489f.get(str)) == null) ? null : bundle.get(str2);
            if (obj2 != null) {
                return obj2;
            }
            try {
                ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(str, 128);
                bundle = applicationInfo != null ? applicationInfo.metaData : null;
                obj = bundle != null ? bundle.get(str2) : obj2;
            } catch (Exception e2) {
                e = e2;
                bundle = obj2;
            }
            try {
                if (f24489f.size() > 300) {
                    return obj;
                }
                f24489f.put(str, bundle);
                return obj;
            } catch (Exception e3) {
                bundle = obj;
                e = e3;
                p.a("Utility", "getMetaValue::".concat(String.valueOf(e)));
                return bundle;
            }
        } catch (Exception e4) {
            e = e4;
        }
    }

    public static Object a(String str, String str2) throws Exception {
        Class<?> cls = Class.forName(str);
        return cls.getField(str2).get(cls);
    }

    private static void a(String str, ComponentInfo[] componentInfoArr, String str2) throws VivoPushException {
        for (ComponentInfo componentInfo : componentInfoArr) {
            if (str.equals(componentInfo.name)) {
                if (componentInfo.enabled) {
                    a(componentInfo, str2);
                    return;
                }
                throw new VivoPushException(componentInfo.name + " module Push-SDK need is illegitmacy !");
            }
        }
        throw new VivoPushException(str + " module Push-SDK need is not exist");
    }

    private static void a(ComponentInfo componentInfo, String str) throws VivoPushException {
        if (componentInfo.applicationInfo.packageName.equals(str)) {
            return;
        }
        for (String str2 : f24484a) {
            if (str2.equals(componentInfo.name) && !componentInfo.processName.contains(":pushservice")) {
                throw new VivoPushException("module : " + componentInfo.name + " process :" + componentInfo.processName + "  check process fail");
            }
        }
    }

    private static void a(Context context, String str, String str2, boolean z2) throws VivoPushException {
        Intent intent = new Intent(str);
        intent.setPackage(context.getPackageName());
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null) {
                throw new VivoPushException("localPackageManager is null");
            }
            if (z2) {
                List<ResolveInfo> listQueryBroadcastReceivers = packageManager.queryBroadcastReceivers(intent, R2.attr.bl_enabled_gradient_centerColor);
                if (listQueryBroadcastReceivers != null && listQueryBroadcastReceivers.size() > 0) {
                    Iterator<ResolveInfo> it = listQueryBroadcastReceivers.iterator();
                    while (it.hasNext()) {
                        if (str2.equals(it.next().activityInfo.name)) {
                            return;
                        }
                    }
                    throw new VivoPushException(str2 + " is missing");
                }
                throw new VivoPushException("checkModule " + intent + " has no receivers");
            }
            List<ResolveInfo> listQueryIntentServices = packageManager.queryIntentServices(intent, R2.attr.bl_enabled_gradient_centerColor);
            if (listQueryIntentServices != null && listQueryIntentServices.size() > 0) {
                for (ResolveInfo resolveInfo : listQueryIntentServices) {
                    if (str2.equals(resolveInfo.serviceInfo.name)) {
                        if (resolveInfo.serviceInfo.exported) {
                            return;
                        }
                        throw new VivoPushException(resolveInfo.serviceInfo.name + " exported is false");
                    }
                }
                throw new VivoPushException(str2 + " is missing");
            }
            throw new VivoPushException("checkModule " + intent + " has no services");
        } catch (Exception e2) {
            p.a("Utility", "error  " + e2.getMessage());
            throw new VivoPushException("checkModule error" + e2.getMessage());
        }
    }

    public static String b(String str, String str2) {
        String str3;
        try {
            str3 = (String) Class.forName("android.os.SystemProperties").getMethod("get", String.class).invoke(null, str);
        } catch (Exception e2) {
            e2.printStackTrace();
            str3 = str2;
        }
        return (str3 == null || str3.length() == 0) ? str2 : str3;
    }

    public static boolean b(Context context, String str, String str2) {
        Cursor cursor = null;
        try {
            try {
                try {
                } catch (Throwable th) {
                    if (0 != 0) {
                        try {
                            cursor.close();
                        } catch (Exception e2) {
                            p.a("Utility", "close", e2);
                        }
                    }
                    throw th;
                }
            } catch (Exception e3) {
                p.a("Utility", "isOverdue", e3);
                if (0 != 0) {
                    cursor.close();
                }
            }
        } catch (Exception e4) {
            p.a("Utility", "close", e4);
        }
        if (context == null) {
            p.a("Utility", "context is null");
            return false;
        }
        Cursor cursorQuery = context.getContentResolver().query(com.vivo.push.p.f24406c, null, "appPkgName = ? and regId = ? sdkVersion = ? ", new String[]{str, str2, "323"}, null);
        if (cursorQuery == null) {
            p.a("Utility", "cursor is null");
            if (cursorQuery != null) {
                try {
                    cursorQuery.close();
                } catch (Exception e5) {
                    p.a("Utility", "close", e5);
                }
            }
            return false;
        }
        if (cursorQuery.moveToFirst()) {
            boolean z2 = Boolean.parseBoolean(cursorQuery.getString(cursorQuery.getColumnIndex("clientState")));
            try {
                cursorQuery.close();
            } catch (Exception e6) {
                p.a("Utility", "close", e6);
            }
            return z2;
        }
        cursorQuery.close();
        return false;
    }

    public static void a(Context context, Intent intent) {
        String strB = t.b(context);
        String stringExtra = intent.getStringExtra("client_pkgname");
        if (TextUtils.isEmpty(strB)) {
            p.a("Utility", "illegality abe adapter : push pkg is null");
            return;
        }
        if (TextUtils.isEmpty(stringExtra)) {
            p.a("Utility", "illegality abe adapter : src pkg is null");
            return;
        }
        if (strB.equals(context.getPackageName())) {
            p.a("Utility", "illegality abe adapter : abe is not pushservice");
            return;
        }
        if (!strB.equals(stringExtra)) {
            p.d("Utility", "proxy to core : intent pkg : " + intent.getPackage() + " ; src pkg : " + stringExtra + " ; push pkg : " + strB);
            intent.setPackage(strB);
            intent.setClassName(strB, "com.vivo.push.sdk.service.PushService");
            context.startService(intent);
            return;
        }
        p.a("Utility", "illegality abe adapter : pushPkg = " + strB + " ; srcPkg = " + stringExtra);
    }
}
