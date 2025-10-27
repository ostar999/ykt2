package com.meizu.cloud.pushsdk.c;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.c.b.c;
import com.meizu.cloud.pushsdk.c.b.f;
import com.meizu.cloud.pushsdk.c.e.b;
import com.meizu.cloud.pushsdk.c.e.c;
import com.meizu.cloud.pushsdk.c.f.e;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static c f9269a;

    /* renamed from: b, reason: collision with root package name */
    private static BroadcastReceiver f9270b;

    /* renamed from: c, reason: collision with root package name */
    private static AtomicBoolean f9271c = new AtomicBoolean(false);

    private static b a(Context context) {
        return new b.a().a(context).a();
    }

    public static c a(Context context, com.meizu.cloud.pushsdk.b.c.a aVar, f fVar) {
        if (f9269a == null) {
            synchronized (a.class) {
                if (f9269a == null) {
                    f9269a = a(b(context, aVar, fVar), (b) null, context);
                }
                if (f9271c.compareAndSet(false, true)) {
                    a(context, f9269a);
                }
            }
        }
        return f9269a;
    }

    public static c a(Context context, f fVar) {
        return a(context, (com.meizu.cloud.pushsdk.b.c.a) null, fVar);
    }

    public static c a(Context context, boolean z2) {
        if (f9269a == null) {
            synchronized (a.class) {
                if (f9269a == null) {
                    f9269a = a(b(context, null, null), (b) null, context);
                }
            }
        }
        DebugLogger.i("PushAndroidTracker", "can upload subject " + z2);
        if (z2) {
            f9269a.a(a(context));
        }
        return f9269a;
    }

    private static c a(com.meizu.cloud.pushsdk.c.b.c cVar, b bVar, Context context) {
        return new com.meizu.cloud.pushsdk.c.e.a.a(new c.a(cVar, "PushAndroidTracker", context.getPackageCodePath(), context, com.meizu.cloud.pushsdk.c.e.a.a.class).a(com.meizu.cloud.pushsdk.c.f.b.VERBOSE).a(Boolean.FALSE).a(bVar).a(4));
    }

    private static String a() {
        if (MzSystemUtils.isInternational() || MzSystemUtils.isIndiaLocal()) {
            return "push-statics.in.meizu.com";
        }
        DebugLogger.e("QuickTracker", "current statics domain is push-statics.meizu.com");
        return "push-statics.meizu.com";
    }

    private static void a(Context context, final c cVar) {
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.meizu.cloud.pushsdk.c.a.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (e.a(context2)) {
                    com.meizu.cloud.pushsdk.c.f.c.a("QuickTracker", "restart track event: %s", "online true");
                    cVar.a();
                }
            }
        };
        f9270b = broadcastReceiver;
        context.registerReceiver(broadcastReceiver, intentFilter);
    }

    private static com.meizu.cloud.pushsdk.c.b.c b(Context context, com.meizu.cloud.pushsdk.b.c.a aVar, f fVar) {
        c.a aVarA = new c.a(a(), context, com.meizu.cloud.pushsdk.c.b.a.a.class).a(fVar).a(aVar).a(1);
        com.meizu.cloud.pushsdk.c.b.a aVar2 = com.meizu.cloud.pushsdk.c.b.a.DefaultGroup;
        return new com.meizu.cloud.pushsdk.c.b.a.a(aVarA.a(aVar2).b(aVar2.a()).c(2));
    }
}
