package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.text.TextUtils;
import com.hjq.permissions.Permission;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class ab {

    public static class a extends RuntimeException {
        public a(String str) {
            super(str);
        }
    }

    public static class b {

        /* renamed from: a, reason: collision with root package name */
        public String f24514a;

        /* renamed from: a, reason: collision with other field name */
        public boolean f119a;

        /* renamed from: b, reason: collision with root package name */
        public String f24515b;

        /* renamed from: b, reason: collision with other field name */
        public boolean f120b;

        public b(String str, boolean z2, boolean z3, String str2) {
            this.f24514a = str;
            this.f119a = z2;
            this.f120b = z3;
            this.f24515b = str2;
        }
    }

    private static ActivityInfo a(PackageManager packageManager, Intent intent, Class<?> cls) {
        Iterator<ResolveInfo> it = packageManager.queryBroadcastReceivers(intent, 16384).iterator();
        while (it.hasNext()) {
            ActivityInfo activityInfo = it.next().activityInfo;
            if (activityInfo != null && cls.getCanonicalName().equals(activityInfo.name)) {
                return activityInfo;
            }
        }
        return null;
    }

    public static void a(Context context) {
        new Thread(new ac(context)).start();
    }

    private static void a(Context context, String str, String str2) {
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        Intent intent = new Intent(str);
        intent.setPackage(packageName);
        Iterator<ResolveInfo> it = packageManager.queryBroadcastReceivers(intent, 16384).iterator();
        boolean z2 = false;
        while (it.hasNext()) {
            ActivityInfo activityInfo = it.next().activityInfo;
            z2 = (activityInfo == null || TextUtils.isEmpty(activityInfo.name) || !activityInfo.name.equals(str2)) ? false : true;
            if (z2) {
                break;
            }
        }
        if (!z2) {
            throw new a(String.format("<receiver android:name=\"%1$s\" .../> is missing or disabled in AndroidManifest.", str2));
        }
    }

    private static void a(ActivityInfo activityInfo, Boolean[] boolArr) {
        if (boolArr[0].booleanValue() != activityInfo.enabled) {
            throw new a(String.format("<receiver android:name=\"%1$s\" .../> in AndroidManifest had the wrong enabled attribute, which should be android:enabled=%2$b.", activityInfo.name, boolArr[0]));
        }
        if (boolArr[1].booleanValue() != activityInfo.exported) {
            throw new a(String.format("<receiver android:name=\"%1$s\" .../> in AndroidManifest had the wrong exported attribute, which should be android:exported=%2$b.", activityInfo.name, boolArr[1]));
        }
    }

    private static boolean a(PackageInfo packageInfo, String[] strArr) {
        for (ServiceInfo serviceInfo : packageInfo.services) {
            if (a(strArr, serviceInfo.name)) {
                return true;
            }
        }
        return false;
    }

    private static boolean a(String[] strArr, String str) {
        if (strArr != null && str != null) {
            for (String str2 : strArr) {
                if (TextUtils.equals(str2, str)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00a4 A[EDGE_INSN: B:41:0x00a4->B:30:0x00a4 BREAK  A[LOOP:0: B:15:0x0070->B:44:0x0070], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0070 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void c(android.content.Context r8) {
        /*
            android.content.pm.PackageManager r0 = r8.getPackageManager()
            java.lang.String r1 = r8.getPackageName()
            android.content.Intent r2 = new android.content.Intent
            java.lang.String r3 = com.xiaomi.push.service.ax.f25625o
            r2.<init>(r3)
            r2.setPackage(r1)
            r3 = 1
            r4 = 0
            java.lang.String r5 = "com.xiaomi.push.service.receivers.PingReceiver"
            java.lang.Class r5 = java.lang.Class.forName(r5)     // Catch: java.lang.ClassNotFoundException -> L57
            android.content.pm.ActivityInfo r2 = a(r0, r2, r5)     // Catch: java.lang.ClassNotFoundException -> L57
            boolean r5 = com.xiaomi.mipush.sdk.MiPushClient.shouldUseMIUIPush(r8)     // Catch: java.lang.ClassNotFoundException -> L57
            r6 = 2
            if (r5 != 0) goto L47
            if (r2 == 0) goto L35
            java.lang.Boolean[] r5 = new java.lang.Boolean[r6]     // Catch: java.lang.ClassNotFoundException -> L57
            java.lang.Boolean r6 = java.lang.Boolean.TRUE     // Catch: java.lang.ClassNotFoundException -> L57
            r5[r4] = r6     // Catch: java.lang.ClassNotFoundException -> L57
            java.lang.Boolean r6 = java.lang.Boolean.FALSE     // Catch: java.lang.ClassNotFoundException -> L57
            r5[r3] = r6     // Catch: java.lang.ClassNotFoundException -> L57
            a(r2, r5)     // Catch: java.lang.ClassNotFoundException -> L57
            goto L5b
        L35:
            com.xiaomi.mipush.sdk.ab$a r2 = new com.xiaomi.mipush.sdk.ab$a     // Catch: java.lang.ClassNotFoundException -> L57
            java.lang.String r5 = "<receiver android:name=\"%1$s\" .../> is missing or disabled in AndroidManifest."
            java.lang.Object[] r6 = new java.lang.Object[r3]     // Catch: java.lang.ClassNotFoundException -> L57
            java.lang.String r7 = "com.xiaomi.push.service.receivers.PingReceiver"
            r6[r4] = r7     // Catch: java.lang.ClassNotFoundException -> L57
            java.lang.String r5 = java.lang.String.format(r5, r6)     // Catch: java.lang.ClassNotFoundException -> L57
            r2.<init>(r5)     // Catch: java.lang.ClassNotFoundException -> L57
            throw r2     // Catch: java.lang.ClassNotFoundException -> L57
        L47:
            if (r2 == 0) goto L5b
            java.lang.Boolean[] r5 = new java.lang.Boolean[r6]     // Catch: java.lang.ClassNotFoundException -> L57
            java.lang.Boolean r6 = java.lang.Boolean.TRUE     // Catch: java.lang.ClassNotFoundException -> L57
            r5[r4] = r6     // Catch: java.lang.ClassNotFoundException -> L57
            java.lang.Boolean r6 = java.lang.Boolean.FALSE     // Catch: java.lang.ClassNotFoundException -> L57
            r5[r3] = r6     // Catch: java.lang.ClassNotFoundException -> L57
            a(r2, r5)     // Catch: java.lang.ClassNotFoundException -> L57
            goto L5b
        L57:
            r2 = move-exception
            com.xiaomi.channel.commonutils.logger.b.a(r2)
        L5b:
            android.content.Intent r2 = new android.content.Intent
            java.lang.String r5 = "com.xiaomi.mipush.RECEIVE_MESSAGE"
            r2.<init>(r5)
            r2.setPackage(r1)
            r1 = 16384(0x4000, float:2.2959E-41)
            java.util.List r0 = r0.queryBroadcastReceivers(r2, r1)
            java.util.Iterator r0 = r0.iterator()
            r1 = r4
        L70:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto La4
            java.lang.Object r2 = r0.next()
            android.content.pm.ResolveInfo r2 = (android.content.pm.ResolveInfo) r2
            android.content.pm.ActivityInfo r2 = r2.activityInfo
            if (r2 == 0) goto La1
            java.lang.String r5 = r2.name     // Catch: java.lang.ClassNotFoundException -> L9c
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch: java.lang.ClassNotFoundException -> L9c
            if (r5 != 0) goto La1
            java.lang.Class<com.xiaomi.mipush.sdk.PushMessageReceiver> r5 = com.xiaomi.mipush.sdk.PushMessageReceiver.class
            java.lang.String r6 = r2.name     // Catch: java.lang.ClassNotFoundException -> L9c
            java.lang.Class r6 = java.lang.Class.forName(r6)     // Catch: java.lang.ClassNotFoundException -> L9c
            boolean r5 = r5.isAssignableFrom(r6)     // Catch: java.lang.ClassNotFoundException -> L9c
            if (r5 == 0) goto La1
            boolean r1 = r2.enabled     // Catch: java.lang.ClassNotFoundException -> L9c
            if (r1 == 0) goto La1
            r1 = r3
            goto La2
        L9c:
            r2 = move-exception
            com.xiaomi.channel.commonutils.logger.b.a(r2)
            goto L70
        La1:
            r1 = r4
        La2:
            if (r1 == 0) goto L70
        La4:
            if (r1 == 0) goto Lbb
            boolean r0 = com.xiaomi.mipush.sdk.MiPushClient.getOpenHmsPush()
            if (r0 == 0) goto Lba
            java.lang.String r0 = "com.huawei.android.push.intent.RECEIVE"
            java.lang.String r1 = "com.xiaomi.assemble.control.HmsPushReceiver"
            a(r8, r0, r1)
            java.lang.String r0 = "com.huawei.intent.action.PUSH"
            java.lang.String r1 = "com.huawei.hms.support.api.push.PushEventReceiver"
            a(r8, r0, r1)
        Lba:
            return
        Lbb:
            com.xiaomi.mipush.sdk.ab$a r8 = new com.xiaomi.mipush.sdk.ab$a
            java.lang.String r0 = "Receiver: none of the subclasses of PushMessageReceiver is enabled or defined."
            r8.<init>(r0)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.mipush.sdk.ab.c(android.content.Context):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(Context context, PackageInfo packageInfo) {
        boolean z2;
        HashSet hashSet = new HashSet();
        String str = context.getPackageName() + ".permission.MIPUSH_RECEIVE";
        hashSet.addAll(Arrays.asList("android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE", str, "android.permission.ACCESS_WIFI_STATE", Permission.READ_PHONE_STATE, "android.permission.GET_TASKS", "android.permission.VIBRATE"));
        PermissionInfo[] permissionInfoArr = packageInfo.permissions;
        if (permissionInfoArr != null) {
            for (PermissionInfo permissionInfo : permissionInfoArr) {
                if (str.equals(permissionInfo.name)) {
                    z2 = true;
                    break;
                }
            }
            z2 = false;
        } else {
            z2 = false;
        }
        if (!z2) {
            throw new a(String.format("<permission android:name=\"%1$s\" .../> is undefined in AndroidManifest.", str));
        }
        String[] strArr = packageInfo.requestedPermissions;
        if (strArr != null) {
            for (String str2 : strArr) {
                if (!TextUtils.isEmpty(str2) && hashSet.contains(str2)) {
                    hashSet.remove(str2);
                    if (hashSet.isEmpty()) {
                        break;
                    }
                }
            }
        }
        if (!hashSet.isEmpty()) {
            throw new a(String.format("<uses-permission android:name=\"%1$s\"/> is missing in AndroidManifest.", hashSet.iterator().next()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void d(Context context, PackageInfo packageInfo) {
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        map2.put(PushMessageHandler.class.getCanonicalName(), new b(PushMessageHandler.class.getCanonicalName(), true, true, ""));
        map2.put(MessageHandleService.class.getCanonicalName(), new b(MessageHandleService.class.getCanonicalName(), true, false, ""));
        if (!MiPushClient.shouldUseMIUIPush(context) || a(packageInfo, new String[]{"com.xiaomi.push.service.XMJobService", "com.xiaomi.push.service.XMPushService"})) {
            map2.put("com.xiaomi.push.service.XMJobService", new b("com.xiaomi.push.service.XMJobService", true, false, "android.permission.BIND_JOB_SERVICE"));
            map2.put("com.xiaomi.push.service.XMPushService", new b("com.xiaomi.push.service.XMPushService", true, false, ""));
        }
        if (MiPushClient.getOpenFCMPush()) {
            map2.put("com.xiaomi.assemble.control.MiFireBaseInstanceIdService", new b("com.xiaomi.assemble.control.MiFireBaseInstanceIdService", true, false, ""));
            map2.put("com.xiaomi.assemble.control.MiFirebaseMessagingService", new b("com.xiaomi.assemble.control.MiFirebaseMessagingService", true, false, ""));
        }
        if (MiPushClient.getOpenOPPOPush()) {
            map2.put("com.xiaomi.assemble.control.COSPushMessageService", new b("com.xiaomi.assemble.control.COSPushMessageService", true, true, "com.coloros.mcs.permission.SEND_MCS_MESSAGE"));
        }
        ServiceInfo[] serviceInfoArr = packageInfo.services;
        if (serviceInfoArr != null) {
            for (ServiceInfo serviceInfo : serviceInfoArr) {
                if (!TextUtils.isEmpty(serviceInfo.name) && map2.containsKey(serviceInfo.name)) {
                    b bVar = (b) map2.remove(serviceInfo.name);
                    boolean z2 = bVar.f119a;
                    boolean z3 = bVar.f120b;
                    String str = bVar.f24515b;
                    if (z2 != serviceInfo.enabled) {
                        throw new a(String.format("<service android:name=\"%1$s\" .../> in AndroidManifest had the wrong enabled attribute, which should be android:enabled=%2$b.", serviceInfo.name, Boolean.valueOf(z2)));
                    }
                    if (z3 != serviceInfo.exported) {
                        throw new a(String.format("<service android:name=\"%1$s\" .../> in AndroidManifest had the wrong exported attribute, which should be android:exported=%2$b.", serviceInfo.name, Boolean.valueOf(z3)));
                    }
                    if (!TextUtils.isEmpty(str) && !TextUtils.equals(str, serviceInfo.permission)) {
                        throw new a(String.format("<service android:name=\"%1$s\" .../> in AndroidManifest had the wrong permission attribute, which should be android:permission=\"%2$s\".", serviceInfo.name, str));
                    }
                    map.put(serviceInfo.name, serviceInfo.processName);
                    if (map2.isEmpty()) {
                        break;
                    }
                }
            }
        }
        if (!map2.isEmpty()) {
            throw new a(String.format("<service android:name=\"%1$s\" .../> is missing or disabled in AndroidManifest.", map2.keySet().iterator().next()));
        }
        if (!TextUtils.equals((CharSequence) map.get(PushMessageHandler.class.getCanonicalName()), (CharSequence) map.get(MessageHandleService.class.getCanonicalName()))) {
            throw new a(String.format("\"%1$s\" and \"%2$s\" must be running in the same process.", PushMessageHandler.class.getCanonicalName(), MessageHandleService.class.getCanonicalName()));
        }
        if (map.containsKey("com.xiaomi.push.service.XMJobService") && map.containsKey("com.xiaomi.push.service.XMPushService") && !TextUtils.equals((CharSequence) map.get("com.xiaomi.push.service.XMJobService"), (CharSequence) map.get("com.xiaomi.push.service.XMPushService"))) {
            throw new a(String.format("\"%1$s\" and \"%2$s\" must be running in the same process.", "com.xiaomi.push.service.XMJobService", "com.xiaomi.push.service.XMPushService"));
        }
    }
}
