package com.alibaba.sdk.android.sender;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import cn.hutool.core.date.DatePattern;
import com.alibaba.sdk.android.logger.ILog;
import com.alibaba.sdk.android.tbrest.SendService;
import com.heytap.mcssdk.constant.b;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.yikaobang.yixue.R2;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class AlicloudSender {

    /* renamed from: a, reason: collision with root package name */
    private static final String f2850a = null;

    /* renamed from: b, reason: collision with root package name */
    private static Map<String, SdkInfo> f2851b;

    /* renamed from: c, reason: collision with root package name */
    private static Map<String, a> f2852c;

    /* renamed from: f, reason: collision with root package name */
    private static SendService f2855f;

    /* renamed from: g, reason: collision with root package name */
    private static ExecutorService f2856g;

    /* renamed from: d, reason: collision with root package name */
    private static final AtomicBoolean f2853d = new AtomicBoolean(false);

    /* renamed from: e, reason: collision with root package name */
    private static final AtomicBoolean f2854e = new AtomicBoolean(false);

    /* renamed from: h, reason: collision with root package name */
    private static final ILog f2857h = SenderLog.getLogger(AlicloudSender.class);

    /* renamed from: i, reason: collision with root package name */
    private static boolean f2858i = false;

    /* renamed from: j, reason: collision with root package name */
    @SuppressLint({"SimpleDateFormat"})
    private static final SimpleDateFormat f2859j = new SimpleDateFormat(DatePattern.PURE_DATE_PATTERN);

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private int f2863a;

        /* renamed from: b, reason: collision with root package name */
        private String f2864b;

        /* renamed from: c, reason: collision with root package name */
        private String f2865c;

        private a() {
            this.f2863a = -1;
            this.f2864b = "";
            this.f2865c = "";
        }
    }

    private static void a(Application application) {
        if (f2854e.compareAndSet(false, true)) {
            application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() { // from class: com.alibaba.sdk.android.sender.AlicloudSender.1
                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityCreated(Activity activity, Bundle bundle) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityDestroyed(Activity activity) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityPaused(Activity activity) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityResumed(Activity activity) {
                    if (AlicloudSender.f2851b == null || AlicloudSender.f2851b.isEmpty()) {
                        return;
                    }
                    Iterator it = AlicloudSender.f2851b.values().iterator();
                    while (it.hasNext()) {
                        AlicloudSender.b(activity.getApplicationContext(), (SdkInfo) it.next());
                    }
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityStarted(Activity activity) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityStopped(Activity activity) {
                }
            });
        }
    }

    private static void a(Context context) {
        if (f2853d.compareAndSet(false, true)) {
            f2851b = new ConcurrentHashMap();
            f2852c = c(context);
            SendService sendService = new SendService();
            f2855f = sendService;
            sendService.openHttp = Boolean.valueOf(f2858i);
            f2855f.init(context, "24527540@android", "24527540", b(context), null, null);
            f2855f.appSecret = "56fc10fbe8c6ae7d0d895f49c4fb6838";
            f2856g = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue());
        }
    }

    private static void a(Context context, Map<String, a> map) throws JSONException {
        SharedPreferences.Editor editorRemove;
        if (map == null || map.isEmpty()) {
            editorRemove = context.getSharedPreferences("sp_emas_info", 0).edit().remove("emas_sdk_info");
        } else {
            JSONArray jSONArray = new JSONArray();
            for (String str : map.keySet()) {
                a aVar = map.get(str);
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("id", str);
                    jSONObject.put("version", aVar.f2865c);
                    jSONObject.put(CrashHianalyticsData.TIME, aVar.f2864b);
                    jSONObject.put("statu", aVar.f2863a);
                    jSONArray.put(jSONObject);
                } catch (Exception unused) {
                }
            }
            editorRemove = context.getSharedPreferences("sp_emas_info", 0).edit().putString("emas_sdk_info", jSONArray.toString());
        }
        editorRemove.apply();
    }

    public static void asyncSend(Application application, SdkInfo sdkInfo) {
        if (application == null) {
            f2857h.d("asyncSend failed. application is null. ");
            return;
        }
        if (sdkInfo == null) {
            f2857h.d("asyncSend failed. sdk info is null. ");
            return;
        }
        String strA = sdkInfo.a();
        if (TextUtils.isEmpty(strA)) {
            f2857h.d("asyncSend failed. sdk id is empty. ");
            return;
        }
        if (TextUtils.isEmpty(sdkInfo.b())) {
            f2857h.d("asyncSend failed. sdk version is empty. ");
            return;
        }
        a(application.getApplicationContext());
        a(application);
        f2851b.put(strA, sdkInfo);
        b(application.getApplicationContext(), sdkInfo);
    }

    @Deprecated
    public static void asyncSend(Context context, SdkInfo sdkInfo) {
        if (context == null) {
            f2857h.d("asyncSend failed. context is null. ");
            return;
        }
        if (sdkInfo == null) {
            f2857h.d("asyncSend failed. sdk info is null. ");
            return;
        }
        String strA = sdkInfo.a();
        if (TextUtils.isEmpty(strA)) {
            f2857h.d("asyncSend failed. sdk id is empty. ");
        } else {
            if (TextUtils.isEmpty(sdkInfo.b())) {
                f2857h.d("asyncSend failed. sdk version is empty. ");
                return;
            }
            a(context.getApplicationContext());
            f2851b.put(strA, sdkInfo);
            b(context.getApplicationContext(), sdkInfo);
        }
    }

    private static String b(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(final Context context, final SdkInfo sdkInfo) {
        final String str = f2859j.format(new Date(System.currentTimeMillis()));
        try {
            a aVar = f2852c.get(sdkInfo.a());
            if (aVar != null && TextUtils.equals(str, aVar.f2864b) && TextUtils.equals(sdkInfo.b(), aVar.f2865c) && aVar.f2863a == 0) {
                f2857h.d(sdkInfo.a() + " " + sdkInfo.b() + " send abort send. ");
                return;
            }
        } catch (Exception unused) {
        }
        f2856g.execute(new Runnable() { // from class: com.alibaba.sdk.android.sender.AlicloudSender.2
            @Override // java.lang.Runnable
            public void run() throws JSONException {
                AlicloudSender.b(context, sdkInfo, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Context context, SdkInfo sdkInfo, String str) throws JSONException {
        a aVar = f2852c.get(sdkInfo.a());
        if (aVar == null) {
            aVar = new a();
            f2852c.put(sdkInfo.a(), aVar);
        }
        HashMap map = new HashMap();
        map.put("sdkId", sdkInfo.a());
        map.put("packageName", context.getPackageName());
        map.put(b.C, sdkInfo.b());
        map.put("kVersion", "1.1.2");
        if (!TextUtils.isEmpty(sdkInfo.c())) {
            map.put(b.f7201z, sdkInfo.c());
        }
        Map<String, String> map2 = sdkInfo.f2867a;
        if (map2 != null) {
            map.putAll(map2);
        }
        map.put("_aliyun_biz_id", "emas-active");
        ILog iLog = f2857h;
        iLog.d(sdkInfo.a() + " " + sdkInfo.b() + " start send. ");
        boolean zBooleanValue = f2855f.sendRequest("adash-emas.cn-hangzhou.aliyuncs.com", System.currentTimeMillis(), f2850a, R2.id.tv_region, sdkInfo.a() + "_biz_active", null, null, map).booleanValue();
        StringBuilder sb = new StringBuilder();
        sb.append(sdkInfo.a());
        sb.append(" ");
        sb.append(sdkInfo.b());
        sb.append(" send ");
        sb.append(zBooleanValue ? "success. " : "failed. ");
        iLog.d(sb.toString());
        aVar.f2864b = str;
        aVar.f2865c = sdkInfo.b();
        aVar.f2863a = zBooleanValue ? 0 : -1;
        a(context, f2852c);
    }

    private static Map<String, a> c(Context context) throws JSONException {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        String string = context.getSharedPreferences("sp_emas_info", 0).getString("emas_sdk_info", "");
        if (!TextUtils.isEmpty(string)) {
            try {
                JSONArray jSONArray = new JSONArray(string);
                if (jSONArray.length() > 0) {
                    for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i2);
                        String string2 = jSONObject.getString("id");
                        a aVar = new a();
                        aVar.f2864b = jSONObject.getString(CrashHianalyticsData.TIME);
                        aVar.f2863a = jSONObject.getInt("statu");
                        aVar.f2865c = jSONObject.getString("version");
                        concurrentHashMap.put(string2, aVar);
                    }
                }
            } catch (Exception unused) {
            }
        }
        return concurrentHashMap;
    }

    public static void openHttp() {
        f2858i = true;
    }
}
