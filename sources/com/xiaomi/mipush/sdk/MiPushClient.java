package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.text.TextUtils;
import com.xiaomi.clientreport.data.Config;
import com.xiaomi.clientreport.manager.ClientReportClient;
import com.xiaomi.mipush.sdk.MiTinyDataClient;
import com.xiaomi.push.dt;
import com.xiaomi.push.fi;
import com.xiaomi.push.fj;
import com.xiaomi.push.fk;
import com.xiaomi.push.fq;
import com.xiaomi.push.hw;
import com.xiaomi.push.ic;
import com.xiaomi.push.in;
import com.xiaomi.push.iq;
import com.xiaomi.push.is;
import com.xiaomi.push.iz;
import com.xiaomi.push.je;
import com.xiaomi.push.jf;
import com.xiaomi.push.jj;
import com.xiaomi.push.jl;
import com.xiaomi.push.jn;
import com.xiaomi.push.service.receivers.NetworkStatusReceiver;
import com.yikaobang.yixue.R2;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/* loaded from: classes6.dex */
public abstract class MiPushClient {
    public static final String COMMAND_REGISTER = "register";
    public static final String COMMAND_SET_ACCEPT_TIME = "accept-time";
    public static final String COMMAND_SET_ACCOUNT = "set-account";
    public static final String COMMAND_SET_ALIAS = "set-alias";
    public static final String COMMAND_SUBSCRIBE_TOPIC = "subscribe-topic";
    public static final String COMMAND_UNREGISTER = "unregister";
    public static final String COMMAND_UNSET_ACCOUNT = "unset-account";
    public static final String COMMAND_UNSET_ALIAS = "unset-alias";
    public static final String COMMAND_UNSUBSCRIBE_TOPIC = "unsubscibe-topic";
    public static final String PREF_EXTRA = "mipush_extra";
    private static boolean isCrashHandlerSuggested = false;
    private static bh mSyncMIIDHelper;
    private static Context sContext;
    private static long sCurMsgId = System.currentTimeMillis();

    @Deprecated
    public static abstract class MiPushClientCallback {
        private String category;

        public String getCategory() {
            return this.category;
        }

        public void onCommandResult(String str, long j2, String str2, List<String> list) {
        }

        public void onInitializeResult(long j2, String str, String str2) {
        }

        public void onReceiveMessage(MiPushMessage miPushMessage) {
        }

        public void onReceiveMessage(String str, String str2, String str3, boolean z2) {
        }

        public void onSubscribeResult(long j2, String str, String str2) {
        }

        public void onUnsubscribeResult(long j2, String str, String str2) {
        }

        public void setCategory(String str) {
            this.category = str;
        }
    }

    private static boolean acceptTimeSet(Context context, String str, String str2) {
        return TextUtils.equals(getAcceptTime(context), str + "," + str2);
    }

    public static long accountSetTime(Context context, String str) {
        return context.getSharedPreferences("mipush_extra", 0).getLong("account_" + str, -1L);
    }

    public static synchronized void addAcceptTime(Context context, String str, String str2) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("mipush_extra", 0).edit();
        editorEdit.putString(Constants.EXTRA_KEY_ACCEPT_TIME, str + "," + str2);
        com.xiaomi.push.t.a(editorEdit);
    }

    public static synchronized void addAccount(Context context, String str) {
        context.getSharedPreferences("mipush_extra", 0).edit().putLong("account_" + str, System.currentTimeMillis()).commit();
    }

    public static synchronized void addAlias(Context context, String str) {
        context.getSharedPreferences("mipush_extra", 0).edit().putLong("alias_" + str, System.currentTimeMillis()).commit();
    }

    private static void addPullNotificationTime(Context context) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("mipush_extra", 0).edit();
        editorEdit.putLong("last_pull_notification", System.currentTimeMillis());
        com.xiaomi.push.t.a(editorEdit);
    }

    private static void addRegRequestTime(Context context) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("mipush_extra", 0).edit();
        editorEdit.putLong("last_reg_request", System.currentTimeMillis());
        com.xiaomi.push.t.a(editorEdit);
    }

    public static synchronized void addTopic(Context context, String str) {
        context.getSharedPreferences("mipush_extra", 0).edit().putLong("topic_" + str, System.currentTimeMillis()).commit();
    }

    public static long aliasSetTime(Context context, String str) {
        return context.getSharedPreferences("mipush_extra", 0).getLong("alias_" + str, -1L);
    }

    public static void awakeApps(Context context, String[] strArr) {
        com.xiaomi.push.ai.a(context).a(new aj(strArr, context));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void awakePushServiceByPackageInfo(Context context, PackageInfo packageInfo) {
        ServiceInfo[] serviceInfoArr = packageInfo.services;
        if (serviceInfoArr != null) {
            for (ServiceInfo serviceInfo : serviceInfoArr) {
                if (serviceInfo.exported && serviceInfo.enabled && "com.xiaomi.mipush.sdk.PushMessageHandler".equals(serviceInfo.name) && !context.getPackageName().equals(serviceInfo.packageName)) {
                    try {
                        Thread.sleep(((long) ((Math.random() * 2.0d) + 1.0d)) * 1000);
                        Intent intent = new Intent();
                        intent.setClassName(serviceInfo.packageName, serviceInfo.name);
                        intent.setAction("com.xiaomi.mipush.sdk.WAKEUP");
                        intent.putExtra("waker_pkgname", context.getPackageName());
                        PushMessageHandler.a(context, intent);
                        return;
                    } catch (Throwable unused) {
                        return;
                    }
                }
            }
        }
    }

    private static void checkNotNull(Object obj, String str) {
        if (obj != null) {
            return;
        }
        throw new IllegalArgumentException("param " + str + " is not nullable");
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0051  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean checkPermission(android.content.Context r5) {
        /*
            java.lang.String r0 = "android.permission.WRITE_EXTERNAL_STORAGE"
            java.lang.String r1 = "android.permission.READ_PHONE_STATE"
            if (r5 == 0) goto L51
            boolean r2 = com.xiaomi.push.n.m679a()
            r3 = 1
            if (r2 != 0) goto L52
            java.lang.String r2 = "com.xiaomi.xmsf"
            java.lang.String r4 = r5.getPackageName()
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L1a
            goto L52
        L1a:
            java.lang.String r2 = com.xiaomi.push.j.b(r5)
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L25
            goto L52
        L25:
            android.content.pm.ApplicationInfo r2 = r5.getApplicationInfo()
            int r2 = r2.targetSdkVersion
            r4 = 23
            if (r2 < r4) goto L3c
            boolean r2 = com.xiaomi.push.o.a(r5, r1)
            if (r2 != 0) goto L52
            boolean r2 = com.xiaomi.push.o.a(r5, r0)
            if (r2 == 0) goto L51
            goto L52
        L3c:
            java.lang.String r2 = com.xiaomi.push.j.f(r5)
            java.lang.String r4 = com.xiaomi.push.j.m586a()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L52
            boolean r2 = android.text.TextUtils.isEmpty(r4)
            if (r2 != 0) goto L51
            goto L52
        L51:
            r3 = 0
        L52:
            if (r3 != 0) goto La5
            java.lang.String r2 = "Because of lack of necessary information, mi push can't be initialized"
            com.xiaomi.channel.commonutils.logger.b.d(r2)
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            boolean r4 = com.xiaomi.push.o.a(r5, r1)
            if (r4 != 0) goto L67
            r2.add(r1)
        L67:
            boolean r1 = com.xiaomi.push.o.a(r5, r0)
            if (r1 != 0) goto L70
            r2.add(r0)
        L70:
            boolean r0 = r2.isEmpty()
            if (r0 != 0) goto La5
            int r0 = r2.size()
            java.lang.String[] r0 = new java.lang.String[r0]
            r2.toArray(r0)
            android.content.Intent r1 = new android.content.Intent
            r1.<init>()
            java.lang.String r2 = "com.xiaomi.mipush.ERROR"
            r1.setAction(r2)
            java.lang.String r2 = r5.getPackageName()
            r1.setPackage(r2)
            java.lang.String r2 = "message_type"
            r4 = 5
            r1.putExtra(r2, r4)
            java.lang.String r2 = "error_type"
            java.lang.String r4 = "error_lack_of_permission"
            r1.putExtra(r2, r4)
            java.lang.String r2 = "error_message"
            r1.putExtra(r2, r0)
            r5.sendBroadcast(r1)
        La5:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.mipush.sdk.MiPushClient.checkPermission(android.content.Context):boolean");
    }

    public static void clearExtras(Context context) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("mipush_extra", 0).edit();
        editorEdit.clear();
        editorEdit.commit();
    }

    public static void clearLocalNotificationType(Context context) {
        az.a(context).m153e();
    }

    public static void clearNotification(Context context) {
        az.a(context).a(-1);
    }

    public static void clearNotification(Context context, int i2) {
        az.a(context).a(i2);
    }

    public static void clearNotification(Context context, String str, String str2) {
        az.a(context).a(str, str2);
    }

    public static void disablePush(Context context) {
        az.a(context).a(true);
    }

    private static void enableGeo(Context context, boolean z2) {
        if (Math.abs(System.currentTimeMillis() - getGeoEnableTime(context, String.valueOf(z2))) > 60000) {
            com.xiaomi.push.service.j.a(context, z2);
            w.a(context, z2);
            setGeoEnableTime(context, String.valueOf(z2));
        }
    }

    public static void enablePush(Context context) {
        az.a(context).a(false);
    }

    private static void forceHandleCrash() {
        boolean zA = com.xiaomi.push.service.ao.a(sContext).a(ic.ForceHandleCrashSwitch.a(), false);
        if (isCrashHandlerSuggested || !zA) {
            return;
        }
        Thread.setDefaultUncaughtExceptionHandler(new z(sContext));
    }

    public static String getAcceptTime(Context context) {
        return context.getSharedPreferences("mipush_extra", 0).getString(Constants.EXTRA_KEY_ACCEPT_TIME, "00:00-23:59");
    }

    public static List<String> getAllAlias(Context context) {
        ArrayList arrayList = new ArrayList();
        for (String str : context.getSharedPreferences("mipush_extra", 0).getAll().keySet()) {
            if (str.startsWith("alias_")) {
                arrayList.add(str.substring(6));
            }
        }
        return arrayList;
    }

    public static List<String> getAllTopic(Context context) {
        ArrayList arrayList = new ArrayList();
        for (String str : context.getSharedPreferences("mipush_extra", 0).getAll().keySet()) {
            if (str.startsWith("topic_") && !str.contains("**ALL**")) {
                arrayList.add(str.substring(6));
            }
        }
        return arrayList;
    }

    public static List<String> getAllUserAccount(Context context) {
        ArrayList arrayList = new ArrayList();
        for (String str : context.getSharedPreferences("mipush_extra", 0).getAll().keySet()) {
            if (str.startsWith("account_")) {
                arrayList.add(str.substring(8));
            }
        }
        return arrayList;
    }

    public static String getAppRegion(Context context) {
        if (d.m156a(context).m165c()) {
            return d.m156a(context).f();
        }
        return null;
    }

    private static boolean getDefaultSwitch() {
        return com.xiaomi.push.n.m680b();
    }

    private static long getGeoEnableTime(Context context, String str) {
        return context.getSharedPreferences("mipush_extra", 0).getLong("geo_" + str, -1L);
    }

    public static boolean getOpenFCMPush() {
        return g.a(sContext).b(f.ASSEMBLE_PUSH_FCM);
    }

    public static boolean getOpenHmsPush() {
        return g.a(sContext).b(f.ASSEMBLE_PUSH_HUAWEI);
    }

    public static boolean getOpenOPPOPush() {
        return g.a(sContext).b(f.ASSEMBLE_PUSH_COS);
    }

    public static String getRegId(Context context) {
        if (d.m156a(context).m165c()) {
            return d.m156a(context).m164c();
        }
        return null;
    }

    private static void initEventPerfLogic(Context context) {
        fk.a(new ak());
        Config configA = fk.a(context);
        ClientReportClient.init(context, configA, new fi(context), new fj(context));
        a.a(context);
        r.a(context, configA);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Deprecated
    public static void initialize(Context context, String str, String str2, MiPushClientCallback miPushClientCallback) {
        if (miPushClientCallback != null) {
            try {
                PushMessageHandler.a(miPushClientCallback);
            } catch (Throwable th) {
                com.xiaomi.channel.commonutils.logger.b.a(th);
                return;
            }
        }
        if (com.xiaomi.push.v.m772a(sContext)) {
            ab.a(sContext);
        }
        if (d.m156a(sContext).a(str, str2) || checkPermission(sContext)) {
            boolean z2 = d.m156a(sContext).a() != Constants.a();
            if (!z2 && !shouldSendRegRequest(sContext)) {
                az.a(sContext).m145a();
                com.xiaomi.channel.commonutils.logger.b.m117a("Could not send  register message within 5s repeatly .");
                return;
            }
            if (z2 || !d.m156a(sContext).a(str, str2) || d.m156a(sContext).m167e()) {
                String strA = com.xiaomi.push.ay.a(6);
                d.m156a(sContext).m158a();
                d.m156a(sContext).a(Constants.a());
                d.m156a(sContext).a(str, str2, strA);
                MiTinyDataClient.a.a().b(MiTinyDataClient.PENDING_REASON_APPID);
                clearExtras(sContext);
                jf jfVar = new jf();
                jfVar.a(com.xiaomi.push.service.ar.a());
                jfVar.b(str);
                jfVar.e(str2);
                jfVar.d(sContext.getPackageName());
                jfVar.f(strA);
                Context context2 = sContext;
                jfVar.c(com.xiaomi.push.g.m439a(context2, context2.getPackageName()));
                Context context3 = sContext;
                jfVar.b(com.xiaomi.push.g.a(context3, context3.getPackageName()));
                jfVar.g("3_6_12");
                jfVar.a(R2.styleable.background_selector_bl_focused_activated);
                jfVar.h(com.xiaomi.push.j.e(sContext));
                jfVar.a(is.Init);
                if (!com.xiaomi.push.n.d()) {
                    String strG = com.xiaomi.push.j.g(sContext);
                    String strI = com.xiaomi.push.j.i(sContext);
                    if (!TextUtils.isEmpty(strG)) {
                        if (com.xiaomi.push.n.m680b()) {
                            if (!TextUtils.isEmpty(strI)) {
                                strG = strG + "," + strI;
                            }
                            jfVar.i(strG);
                        }
                        jfVar.k(com.xiaomi.push.ay.a(strG) + "," + com.xiaomi.push.j.j(sContext));
                    }
                }
                jfVar.j(com.xiaomi.push.j.m586a());
                int iA = com.xiaomi.push.j.a();
                if (iA >= 0) {
                    jfVar.c(iA);
                }
                az.a(sContext).a(jfVar, z2);
                b.a(sContext);
                sContext.getSharedPreferences("mipush_extra", 4).getBoolean("mipush_registed", true);
            } else {
                if (1 == PushMessageHelper.getPushMode(sContext)) {
                    checkNotNull(miPushClientCallback, com.alipay.sdk.authjs.a.f3170c);
                    miPushClientCallback.onInitializeResult(0L, null, d.m156a(sContext).m164c());
                } else {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(d.m156a(sContext).m164c());
                    PushMessageHelper.sendCommandMessageBroadcast(sContext, PushMessageHelper.generateCommandMessage(fq.COMMAND_REGISTER.f429a, arrayList, 0L, null, null));
                }
                az.a(sContext).m145a();
                if (d.m156a(sContext).m160a()) {
                    je jeVar = new je();
                    jeVar.b(d.m156a(sContext).m157a());
                    jeVar.c("client_info_update");
                    jeVar.a(com.xiaomi.push.service.ar.a());
                    HashMap map = new HashMap();
                    jeVar.f769a = map;
                    Context context4 = sContext;
                    map.put("app_version", com.xiaomi.push.g.m439a(context4, context4.getPackageName()));
                    Map<String, String> map2 = jeVar.f769a;
                    Context context5 = sContext;
                    map2.put(Constants.EXTRA_KEY_APP_VERSION_CODE, Integer.toString(com.xiaomi.push.g.a(context5, context5.getPackageName())));
                    jeVar.f769a.put("push_sdk_vn", "3_6_12");
                    jeVar.f769a.put("push_sdk_vc", Integer.toString(R2.styleable.background_selector_bl_focused_activated));
                    String strE = d.m156a(sContext).e();
                    if (!TextUtils.isEmpty(strE)) {
                        jeVar.f769a.put("deviceid", strE);
                    }
                    az.a(sContext).a((az) jeVar, hw.Notification, false, (iq) null);
                    b.a(sContext);
                }
                if (!com.xiaomi.push.p.m681a(sContext, "update_devId", false)) {
                    updateIMEI();
                    com.xiaomi.push.p.a(sContext, "update_devId", true);
                }
                String strD = com.xiaomi.push.j.d(sContext);
                if (!TextUtils.isEmpty(strD)) {
                    iz izVar = new iz();
                    izVar.a(com.xiaomi.push.service.ar.a());
                    izVar.b(str);
                    izVar.c(fq.COMMAND_CHK_VDEVID.f429a);
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.add(com.xiaomi.push.j.c(sContext));
                    arrayList2.add(strD);
                    String str3 = Build.MODEL;
                    if (str3 == null) {
                        str3 = "";
                    }
                    arrayList2.add(str3);
                    String str4 = Build.BOARD;
                    arrayList2.add(str4 != null ? str4 : "");
                    izVar.a(arrayList2);
                    az.a(sContext).a((az) izVar, hw.Command, false, (iq) null);
                }
                if (shouldUseMIUIPush(sContext) && shouldPullNotification(sContext)) {
                    je jeVar2 = new je();
                    jeVar2.b(d.m156a(sContext).m157a());
                    jeVar2.c(in.PullOfflineMessage.f622a);
                    jeVar2.a(com.xiaomi.push.service.ar.a());
                    jeVar2.a(false);
                    az.a(sContext).a((az) jeVar2, hw.Notification, false, (iq) null, false);
                    addPullNotificationTime(sContext);
                }
            }
            addRegRequestTime(sContext);
            scheduleOcVersionCheckJob();
            scheduleGeoFenceLocUploadJobs();
            scheduleDataCollectionJobs(sContext);
            initEventPerfLogic(sContext);
            bf.a(sContext);
            forceHandleCrash();
            if (!sContext.getPackageName().equals("com.xiaomi.xmsf")) {
                Logger.setLogger(sContext, Logger.getUserLogger());
                com.xiaomi.channel.commonutils.logger.b.a(2);
            }
            try {
                if (mSyncMIIDHelper == null) {
                    mSyncMIIDHelper = new bh(sContext);
                }
                mSyncMIIDHelper.a(sContext);
            } catch (Exception e2) {
                com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
            }
            if ("syncing".equals(ap.a(sContext).a(be.DISABLE_PUSH))) {
                disablePush(sContext);
            }
            if ("syncing".equals(ap.a(sContext).a(be.ENABLE_PUSH))) {
                enablePush(sContext);
            }
            if ("syncing".equals(ap.a(sContext).a(be.UPLOAD_HUAWEI_TOKEN))) {
                syncAssemblePushToken(sContext);
            }
            if ("syncing".equals(ap.a(sContext).a(be.UPLOAD_FCM_TOKEN))) {
                syncAssembleFCMPushToken(sContext);
            }
            if ("syncing".equals(ap.a(context).a(be.UPLOAD_COS_TOKEN))) {
                syncAssembleCOSPushToken(sContext);
            }
        }
    }

    public static void pausePush(Context context, String str) {
        setAcceptTime(context, 0, 0, 0, 0, str);
    }

    public static void reInitialize(Context context, is isVar) {
        if (d.m156a(context).m165c()) {
            String strA = com.xiaomi.push.ay.a(6);
            String strM157a = d.m156a(context).m157a();
            String strB = d.m156a(context).b();
            d.m156a(context).m158a();
            d.m156a(context).a(Constants.a());
            d.m156a(context).a(strM157a, strB, strA);
            jf jfVar = new jf();
            jfVar.a(com.xiaomi.push.service.ar.a());
            jfVar.b(strM157a);
            jfVar.e(strB);
            jfVar.f(strA);
            jfVar.d(context.getPackageName());
            jfVar.c(com.xiaomi.push.g.m439a(context, context.getPackageName()));
            jfVar.a(isVar);
            az.a(context).a(jfVar, false);
        }
    }

    public static void registerCrashHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        Thread.setDefaultUncaughtExceptionHandler(new z(sContext, uncaughtExceptionHandler));
        isCrashHandlerSuggested = true;
    }

    private static void registerNetworkReceiver(Context context) {
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            intentFilter.addCategory("android.intent.category.DEFAULT");
            context.getApplicationContext().registerReceiver(new NetworkStatusReceiver(null), intentFilter);
        } catch (Throwable th) {
            com.xiaomi.channel.commonutils.logger.b.a(th);
        }
    }

    public static void registerPush(Context context, String str, String str2) {
        registerPush(context, str, str2, new PushConfiguration());
    }

    public static void registerPush(Context context, String str, String str2, PushConfiguration pushConfiguration) {
        checkNotNull(context, com.umeng.analytics.pro.d.R);
        checkNotNull(str, com.heytap.mcssdk.constant.b.f7196u);
        checkNotNull(str2, "appToken");
        Context applicationContext = context.getApplicationContext();
        sContext = applicationContext;
        if (applicationContext == null) {
            sContext = context;
        }
        Context context2 = sContext;
        if (!NetworkStatusReceiver.a()) {
            registerNetworkReceiver(sContext);
        }
        g.a(sContext).a(pushConfiguration);
        enableGeo(sContext, pushConfiguration.getGeoEnable());
        b.a();
        com.xiaomi.push.ai.a(context2).a(new ag(str, str2));
    }

    public static synchronized void removeAcceptTime(Context context) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("mipush_extra", 0).edit();
        editorEdit.remove(Constants.EXTRA_KEY_ACCEPT_TIME);
        com.xiaomi.push.t.a(editorEdit);
    }

    public static synchronized void removeAccount(Context context, String str) {
        context.getSharedPreferences("mipush_extra", 0).edit().remove("account_" + str).commit();
    }

    public static synchronized void removeAlias(Context context, String str) {
        context.getSharedPreferences("mipush_extra", 0).edit().remove("alias_" + str).commit();
    }

    public static synchronized void removeAllAccounts(Context context) {
        Iterator<String> it = getAllUserAccount(context).iterator();
        while (it.hasNext()) {
            removeAccount(context, it.next());
        }
    }

    public static synchronized void removeAllAliases(Context context) {
        Iterator<String> it = getAllAlias(context).iterator();
        while (it.hasNext()) {
            removeAlias(context, it.next());
        }
    }

    public static synchronized void removeAllTopics(Context context) {
        Iterator<String> it = getAllTopic(context).iterator();
        while (it.hasNext()) {
            removeTopic(context, it.next());
        }
    }

    public static synchronized void removeTopic(Context context, String str) {
        context.getSharedPreferences("mipush_extra", 0).edit().remove("topic_" + str).commit();
    }

    public static void reportAppRunInBackground(Context context, boolean z2) {
        if (d.m156a(context).m163b()) {
            in inVar = z2 ? in.APP_SLEEP : in.APP_WAKEUP;
            je jeVar = new je();
            jeVar.b(d.m156a(context).m157a());
            jeVar.c(inVar.f622a);
            jeVar.d(context.getPackageName());
            jeVar.a(com.xiaomi.push.service.ar.a());
            jeVar.a(false);
            az.a(context).a((az) jeVar, hw.Notification, false, (iq) null, false);
        }
    }

    public static void reportIgnoreRegMessageClicked(Context context, String str, iq iqVar, String str2, String str3) {
        je jeVar = new je();
        if (TextUtils.isEmpty(str3)) {
            com.xiaomi.channel.commonutils.logger.b.d("do not report clicked message");
            return;
        }
        jeVar.b(str3);
        jeVar.c("bar:click");
        jeVar.a(str);
        jeVar.a(false);
        az.a(context).a(jeVar, hw.Notification, false, true, iqVar, true, str2, str3);
    }

    public static void reportMessageClicked(Context context, MiPushMessage miPushMessage) {
        iq iqVar = new iq();
        iqVar.a(miPushMessage.getMessageId());
        iqVar.b(miPushMessage.getTopic());
        iqVar.d(miPushMessage.getDescription());
        iqVar.c(miPushMessage.getTitle());
        iqVar.c(miPushMessage.getNotifyId());
        iqVar.a(miPushMessage.getNotifyType());
        iqVar.b(miPushMessage.getPassThrough());
        iqVar.a(miPushMessage.getExtra());
        reportMessageClicked(context, miPushMessage.getMessageId(), iqVar, null);
    }

    @Deprecated
    public static void reportMessageClicked(Context context, String str) {
        reportMessageClicked(context, str, null, null);
    }

    public static void reportMessageClicked(Context context, String str, iq iqVar, String str2) {
        je jeVar = new je();
        if (TextUtils.isEmpty(str2)) {
            if (!d.m156a(context).m163b()) {
                com.xiaomi.channel.commonutils.logger.b.d("do not report clicked message");
                return;
            }
            str2 = d.m156a(context).m157a();
        }
        jeVar.b(str2);
        jeVar.c("bar:click");
        jeVar.a(str);
        jeVar.a(false);
        az.a(context).a((az) jeVar, hw.Notification, false, iqVar);
    }

    public static void resumePush(Context context, String str) {
        setAcceptTime(context, 0, 0, 23, 59, str);
    }

    private static void scheduleDataCollectionJobs(Context context) {
        if (com.xiaomi.push.service.ao.a(sContext).a(ic.DataCollectionSwitch.a(), getDefaultSwitch())) {
            dt.a().a(new q(context));
            com.xiaomi.push.ai.a(sContext).a(new ah(), 10);
        }
    }

    private static void scheduleGeoFenceLocUploadJobs() {
        if (com.xiaomi.push.service.j.e(sContext) && !TextUtils.equals("com.xiaomi.xmsf", sContext.getPackageName()) && com.xiaomi.push.service.ao.a(sContext).a(ic.UploadGeoAppLocSwitch.a(), true) && !com.xiaomi.push.v.b()) {
            u.a(sContext, true);
            int iMax = Math.max(60, com.xiaomi.push.service.ao.a(sContext).a(ic.UploadWIFIGeoLocFrequency.a(), 900));
            com.xiaomi.push.ai.a(sContext).a(new u(sContext, iMax), iMax, iMax);
        }
    }

    private static void scheduleOcVersionCheckJob() {
        com.xiaomi.push.ai.a(sContext).a(new ao(sContext), com.xiaomi.push.service.ao.a(sContext).a(ic.OcVersionCheckFrequency.a(), 86400), 5);
    }

    public static void setAcceptTime(Context context, int i2, int i3, int i4, int i5, String str) {
        if (i2 < 0 || i2 >= 24 || i4 < 0 || i4 >= 24 || i3 < 0 || i3 >= 60 || i5 < 0 || i5 >= 60) {
            throw new IllegalArgumentException("the input parameter is not valid.");
        }
        long rawOffset = ((TimeZone.getTimeZone("GMT+08").getRawOffset() - TimeZone.getDefault().getRawOffset()) / 1000) / 60;
        long j2 = ((((i2 * 60) + i3) + rawOffset) + 1440) % 1440;
        long j3 = ((((i4 * 60) + i5) + rawOffset) + 1440) % 1440;
        ArrayList arrayList = new ArrayList();
        arrayList.add(String.format("%1$02d:%2$02d", Long.valueOf(j2 / 60), Long.valueOf(j2 % 60)));
        arrayList.add(String.format("%1$02d:%2$02d", Long.valueOf(j3 / 60), Long.valueOf(j3 % 60)));
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(String.format("%1$02d:%2$02d", Integer.valueOf(i2), Integer.valueOf(i3)));
        arrayList2.add(String.format("%1$02d:%2$02d", Integer.valueOf(i4), Integer.valueOf(i5)));
        if (!acceptTimeSet(context, (String) arrayList.get(0), (String) arrayList.get(1))) {
            setCommand(context, fq.COMMAND_SET_ACCEPT_TIME.f429a, (ArrayList<String>) arrayList, str);
        } else if (1 == PushMessageHelper.getPushMode(context)) {
            PushMessageHandler.a(context, str, fq.COMMAND_SET_ACCEPT_TIME.f429a, 0L, null, arrayList2);
        } else {
            PushMessageHelper.sendCommandMessageBroadcast(context, PushMessageHelper.generateCommandMessage(fq.COMMAND_SET_ACCEPT_TIME.f429a, arrayList2, 0L, null, null));
        }
    }

    public static void setAlias(Context context, String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        setCommand(context, fq.COMMAND_SET_ALIAS.f429a, str, str2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0037, code lost:
    
        if (1 == com.xiaomi.mipush.sdk.PushMessageHelper.getPushMode(r16)) goto L11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0039, code lost:
    
        com.xiaomi.mipush.sdk.PushMessageHandler.a(r16, r19, r17, 0, null, r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0047, code lost:
    
        com.xiaomi.mipush.sdk.PushMessageHelper.sendCommandMessageBroadcast(r16, com.xiaomi.mipush.sdk.PushMessageHelper.generateCommandMessage(r3.f429a, r6, 0, null, r19));
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00ae, code lost:
    
        if (1 == com.xiaomi.mipush.sdk.PushMessageHelper.getPushMode(r16)) goto L11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:?, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void setCommand(android.content.Context r16, java.lang.String r17, java.lang.String r18, java.lang.String r19) {
        /*
            r0 = r16
            r2 = r17
            r1 = r18
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            boolean r3 = android.text.TextUtils.isEmpty(r18)
            if (r3 != 0) goto L14
            r6.add(r1)
        L14:
            com.xiaomi.push.fq r3 = com.xiaomi.push.fq.COMMAND_SET_ALIAS
            java.lang.String r4 = r3.f429a
            boolean r4 = r4.equalsIgnoreCase(r2)
            r5 = 1
            r7 = 3600000(0x36ee80, double:1.7786363E-317)
            if (r4 == 0) goto L58
            long r9 = java.lang.System.currentTimeMillis()
            long r11 = aliasSetTime(r0, r1)
            long r9 = r9 - r11
            long r9 = java.lang.Math.abs(r9)
            int r4 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
            if (r4 >= 0) goto L58
            int r1 = com.xiaomi.mipush.sdk.PushMessageHelper.getPushMode(r16)
            if (r5 != r1) goto L47
        L39:
            r3 = 0
            r5 = 0
            r0 = r16
            r1 = r19
            r2 = r17
            com.xiaomi.mipush.sdk.PushMessageHandler.a(r0, r1, r2, r3, r5, r6)
            goto Ld0
        L47:
            java.lang.String r1 = r3.f429a
            r3 = 0
            r5 = 0
            r2 = r6
            r6 = r19
            com.xiaomi.mipush.sdk.MiPushCommandMessage r1 = com.xiaomi.mipush.sdk.PushMessageHelper.generateCommandMessage(r1, r2, r3, r5, r6)
            com.xiaomi.mipush.sdk.PushMessageHelper.sendCommandMessageBroadcast(r0, r1)
            goto Ld0
        L58:
            com.xiaomi.push.fq r3 = com.xiaomi.push.fq.COMMAND_UNSET_ALIAS
            java.lang.String r3 = r3.f429a
            boolean r3 = r3.equalsIgnoreCase(r2)
            java.lang.String r4 = " is unseted"
            r9 = 3
            r10 = 0
            if (r3 == 0) goto L8f
            long r12 = aliasSetTime(r0, r1)
            int r3 = (r12 > r10 ? 1 : (r12 == r10 ? 0 : -1))
            if (r3 >= 0) goto L8f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Don't cancel alias for "
        L76:
            r0.append(r1)
            java.lang.String r1 = r6.toString()
            java.lang.String r1 = com.xiaomi.push.ay.a(r1, r9)
            r0.append(r1)
            r0.append(r4)
            java.lang.String r0 = r0.toString()
            com.xiaomi.channel.commonutils.logger.b.m117a(r0)
            goto Ld0
        L8f:
            com.xiaomi.push.fq r3 = com.xiaomi.push.fq.COMMAND_SET_ACCOUNT
            java.lang.String r12 = r3.f429a
            boolean r12 = r12.equalsIgnoreCase(r2)
            if (r12 == 0) goto Lb1
            long r12 = java.lang.System.currentTimeMillis()
            long r14 = accountSetTime(r0, r1)
            long r12 = r12 - r14
            long r12 = java.lang.Math.abs(r12)
            int r7 = (r12 > r7 ? 1 : (r12 == r7 ? 0 : -1))
            if (r7 >= 0) goto Lb1
            int r1 = com.xiaomi.mipush.sdk.PushMessageHelper.getPushMode(r16)
            if (r5 != r1) goto L47
            goto L39
        Lb1:
            com.xiaomi.push.fq r3 = com.xiaomi.push.fq.COMMAND_UNSET_ACCOUNT
            java.lang.String r3 = r3.f429a
            boolean r3 = r3.equalsIgnoreCase(r2)
            if (r3 == 0) goto Lcb
            long r7 = accountSetTime(r0, r1)
            int r1 = (r7 > r10 ? 1 : (r7 == r10 ? 0 : -1))
            if (r1 >= 0) goto Lcb
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Don't cancel account for "
            goto L76
        Lcb:
            r1 = r19
            setCommand(r0, r2, r6, r1)
        Ld0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.mipush.sdk.MiPushClient.setCommand(android.content.Context, java.lang.String, java.lang.String, java.lang.String):void");
    }

    public static void setCommand(Context context, String str, ArrayList<String> arrayList, String str2) {
        if (TextUtils.isEmpty(d.m156a(context).m157a())) {
            return;
        }
        iz izVar = new iz();
        izVar.a(com.xiaomi.push.service.ar.a());
        izVar.b(d.m156a(context).m157a());
        izVar.c(str);
        Iterator<String> it = arrayList.iterator();
        while (it.hasNext()) {
            izVar.m583a(it.next());
        }
        izVar.e(str2);
        izVar.d(context.getPackageName());
        az.a(context).a((az) izVar, hw.Command, (iq) null);
    }

    public static synchronized void setGeoEnableTime(Context context, String str) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("mipush_extra", 0).edit();
        editorEdit.putLong("geo_" + str, System.currentTimeMillis());
        com.xiaomi.push.t.a(editorEdit);
    }

    public static void setLocalNotificationType(Context context, int i2) {
        az.a(context).b(i2 & (-1));
    }

    public static void setUserAccount(Context context, String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        setCommand(context, fq.COMMAND_SET_ACCOUNT.f429a, str, str2);
    }

    private static boolean shouldPullNotification(Context context) {
        return Math.abs(System.currentTimeMillis() - context.getSharedPreferences("mipush_extra", 0).getLong("last_pull_notification", -1L)) > 300000;
    }

    private static boolean shouldSendRegRequest(Context context) {
        return Math.abs(System.currentTimeMillis() - context.getSharedPreferences("mipush_extra", 0).getLong("last_reg_request", -1L)) > 5000;
    }

    public static boolean shouldUseMIUIPush(Context context) {
        return az.a(context).m147a();
    }

    public static void subscribe(Context context, String str, String str2) {
        if (TextUtils.isEmpty(d.m156a(context).m157a()) || TextUtils.isEmpty(str)) {
            return;
        }
        if (Math.abs(System.currentTimeMillis() - topicSubscribedTime(context, str)) <= 86400000) {
            if (1 == PushMessageHelper.getPushMode(context)) {
                PushMessageHandler.a(context, str2, 0L, null, str);
                return;
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(str);
            PushMessageHelper.sendCommandMessageBroadcast(context, PushMessageHelper.generateCommandMessage(fq.COMMAND_SUBSCRIBE_TOPIC.f429a, arrayList, 0L, null, null));
            return;
        }
        jj jjVar = new jj();
        jjVar.a(com.xiaomi.push.service.ar.a());
        jjVar.b(d.m156a(context).m157a());
        jjVar.c(str);
        jjVar.d(context.getPackageName());
        jjVar.e(str2);
        az.a(context).a((az) jjVar, hw.Subscription, (iq) null);
    }

    public static void syncAssembleCOSPushToken(Context context) {
        az.a(context).a((String) null, be.UPLOAD_COS_TOKEN, f.ASSEMBLE_PUSH_COS);
    }

    public static void syncAssembleFCMPushToken(Context context) {
        az.a(context).a((String) null, be.UPLOAD_FCM_TOKEN, f.ASSEMBLE_PUSH_FCM);
    }

    public static void syncAssemblePushToken(Context context) {
        az.a(context).a((String) null, be.UPLOAD_HUAWEI_TOKEN, f.ASSEMBLE_PUSH_HUAWEI);
    }

    public static long topicSubscribedTime(Context context, String str) {
        return context.getSharedPreferences("mipush_extra", 0).getLong("topic_" + str, -1L);
    }

    public static void unregisterPush(Context context) {
        i.c(context);
        if (d.m156a(context).m163b()) {
            jl jlVar = new jl();
            jlVar.a(com.xiaomi.push.service.ar.a());
            jlVar.b(d.m156a(context).m157a());
            jlVar.c(d.m156a(context).m164c());
            jlVar.e(d.m156a(context).b());
            jlVar.d(context.getPackageName());
            az.a(context).a(jlVar);
            PushMessageHandler.a();
            d.m156a(context).m162b();
            clearLocalNotificationType(context);
            clearNotification(context);
            if (mSyncMIIDHelper != null) {
                com.xiaomi.push.service.o.a(context).b(mSyncMIIDHelper);
            }
            clearExtras(context);
        }
    }

    public static void unsetAlias(Context context, String str, String str2) {
        setCommand(context, fq.COMMAND_UNSET_ALIAS.f429a, str, str2);
    }

    public static void unsetUserAccount(Context context, String str, String str2) {
        setCommand(context, fq.COMMAND_UNSET_ACCOUNT.f429a, str, str2);
    }

    public static void unsubscribe(Context context, String str, String str2) {
        if (d.m156a(context).m163b()) {
            if (topicSubscribedTime(context, str) < 0) {
                com.xiaomi.channel.commonutils.logger.b.m117a("Don't cancel subscribe for " + str + " is unsubscribed");
                return;
            }
            jn jnVar = new jn();
            jnVar.a(com.xiaomi.push.service.ar.a());
            jnVar.b(d.m156a(context).m157a());
            jnVar.c(str);
            jnVar.d(context.getPackageName());
            jnVar.e(str2);
            az.a(context).a((az) jnVar, hw.UnSubscription, (iq) null);
        }
    }

    private static void updateIMEI() {
        new Thread(new ai()).start();
    }
}
