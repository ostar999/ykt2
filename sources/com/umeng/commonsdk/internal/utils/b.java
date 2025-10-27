package com.umeng.commonsdk.internal.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.internal.crash.UMCrashManager;
import com.umeng.commonsdk.statistics.common.ULog;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static final String f23217a = "BatteryUtils";

    /* renamed from: b, reason: collision with root package name */
    private static boolean f23218b = false;

    /* renamed from: c, reason: collision with root package name */
    private static Context f23219c;

    /* renamed from: d, reason: collision with root package name */
    private BroadcastReceiver f23220d;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final b f23222a = new b();

        private a() {
        }
    }

    public static b a(Context context) {
        if (f23219c == null && context != null) {
            f23219c = context.getApplicationContext();
        }
        return a.f23222a;
    }

    public synchronized void b() {
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
            f23219c.registerReceiver(this.f23220d, intentFilter);
            f23218b = true;
        } catch (Throwable th) {
            UMCrashManager.reportCrash(f23219c, th);
        }
    }

    public synchronized void c() {
        try {
            f23219c.unregisterReceiver(this.f23220d);
            f23218b = false;
        } catch (Throwable th) {
            UMCrashManager.reportCrash(f23219c, th);
        }
    }

    private b() {
        this.f23220d = new BroadcastReceiver() { // from class: com.umeng.commonsdk.internal.utils.b.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                try {
                    if (intent.getAction().equals("android.intent.action.BATTERY_CHANGED")) {
                        JSONObject jSONObject = new JSONObject();
                        try {
                            jSONObject.put("le", intent.getIntExtra(DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL, 0));
                        } catch (Exception unused) {
                        }
                        try {
                            jSONObject.put("vol", intent.getIntExtra("voltage", 0));
                        } catch (Exception unused2) {
                        }
                        try {
                            jSONObject.put("temp", intent.getIntExtra("temperature", 0));
                            jSONObject.put("ts", System.currentTimeMillis());
                        } catch (Exception unused3) {
                        }
                        int intExtra = intent.getIntExtra("status", 0);
                        int i2 = -1;
                        int i3 = 2;
                        if (intExtra != 1) {
                            if (intExtra == 2) {
                                i2 = 1;
                            } else if (intExtra == 4) {
                                i2 = 0;
                            } else if (intExtra == 5) {
                                i2 = 2;
                            }
                        }
                        try {
                            jSONObject.put("st", i2);
                        } catch (Exception unused4) {
                        }
                        int intExtra2 = intent.getIntExtra("plugged", 0);
                        if (intExtra2 == 1) {
                            i3 = 1;
                        } else if (intExtra2 != 2) {
                            i3 = 0;
                        }
                        try {
                            jSONObject.put("ct", i3);
                            jSONObject.put("ts", System.currentTimeMillis());
                        } catch (Exception unused5) {
                        }
                        ULog.i(b.f23217a, jSONObject.toString());
                        UMWorkDispatch.sendEvent(context, com.umeng.commonsdk.internal.a.f23174h, com.umeng.commonsdk.internal.b.a(b.f23219c).a(), jSONObject.toString());
                        b.this.c();
                    }
                } catch (Throwable th) {
                    UMCrashManager.reportCrash(b.f23219c, th);
                }
            }
        };
    }

    public synchronized boolean a() {
        return f23218b;
    }
}
