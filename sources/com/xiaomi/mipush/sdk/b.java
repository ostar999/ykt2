package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import com.xiaomi.push.ic;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    public static StackTraceElement[] f24541a;

    public static void a() {
        try {
            f24541a = Thread.getAllStackTraces().get(Thread.currentThread());
        } catch (Throwable unused) {
        }
    }

    public static void a(Context context) {
        com.xiaomi.push.ai.a(context).a(new c(context), 20);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String b(int i2) {
        StackTraceElement[] stackTraceElementArr = f24541a;
        if (stackTraceElementArr != null && stackTraceElementArr.length > 4) {
            ArrayList arrayList = new ArrayList();
            int i3 = 4;
            while (true) {
                try {
                    StackTraceElement[] stackTraceElementArr2 = f24541a;
                    if (i3 >= stackTraceElementArr2.length || i3 >= i2 + 4) {
                        break;
                    }
                    arrayList.add(stackTraceElementArr2[i3].toString());
                    i3++;
                } catch (Exception unused) {
                }
            }
            return arrayList.toString();
        }
        return "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b, reason: collision with other method in class */
    public static boolean m155b(Context context) {
        com.xiaomi.push.service.ao aoVarA = com.xiaomi.push.service.ao.a(context);
        if (!aoVarA.a(ic.AggregationSdkMonitorSwitch.a(), false)) {
            return false;
        }
        return Math.abs(System.currentTimeMillis() - context.getSharedPreferences("mipush_extra", 0).getLong("last_upload_call_stack_timestamp", 0L)) >= ((long) Math.max(60, aoVarA.a(ic.AggregationSdkMonitorFrequency.a(), 86400)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(Context context) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("mipush_extra", 0).edit();
        editorEdit.putLong("last_upload_call_stack_timestamp", System.currentTimeMillis());
        editorEdit.commit();
    }
}
