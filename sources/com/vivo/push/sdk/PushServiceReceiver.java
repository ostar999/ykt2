package com.vivo.push.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.HandlerThread;
import com.google.android.exoplayer2.ExoPlayer;
import com.vivo.push.PushClient;
import com.vivo.push.cache.ClientConfigManagerImpl;
import com.vivo.push.e;
import com.vivo.push.util.ContextDelegate;
import com.vivo.push.util.VivoPushException;
import com.vivo.push.util.p;
import com.vivo.push.util.r;

/* loaded from: classes6.dex */
public class PushServiceReceiver extends BroadcastReceiver {

    /* renamed from: a, reason: collision with root package name */
    private static HandlerThread f24412a;

    /* renamed from: b, reason: collision with root package name */
    private static Handler f24413b;

    /* renamed from: c, reason: collision with root package name */
    private static a f24414c = new a();

    public static class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        private Context f24415a;

        /* renamed from: b, reason: collision with root package name */
        private String f24416b;

        public static /* synthetic */ void a(a aVar, Context context, String str) {
            aVar.f24415a = ContextDelegate.getContext(context);
            aVar.f24416b = str;
        }

        @Override // java.lang.Runnable
        public final void run() {
            NetworkInfo networkInfoA = r.a(this.f24415a);
            if (!(networkInfoA != null ? networkInfoA.isConnectedOrConnecting() : false)) {
                p.d("PushServiceReceiver", this.f24415a.getPackageName() + ": 无网络  by " + this.f24416b);
                p.a(this.f24415a, "触发静态广播:无网络(" + this.f24416b + "," + this.f24415a.getPackageName() + ")");
                return;
            }
            p.d("PushServiceReceiver", this.f24415a.getPackageName() + ": 执行开始出发动作: " + this.f24416b);
            p.a(this.f24415a, "触发静态广播(" + this.f24416b + "," + this.f24415a.getPackageName() + ")");
            e.a().a(this.f24415a);
            if (ClientConfigManagerImpl.getInstance(this.f24415a).isCancleBroadcastReceiver()) {
                return;
            }
            try {
                PushClient.getInstance(this.f24415a).initialize();
            } catch (VivoPushException e2) {
                e2.printStackTrace();
                p.a(this.f24415a, " 初始化异常 error= " + e2.getMessage());
            }
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Context context2 = ContextDelegate.getContext(context);
        String action = intent.getAction();
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action) || "android.intent.action.ACTION_POWER_CONNECTED".equals(action) || "android.intent.action.ACTION_POWER_DISCONNECTED".equals(action)) {
            if (f24412a == null) {
                HandlerThread handlerThread = new HandlerThread("PushServiceReceiver");
                f24412a = handlerThread;
                handlerThread.start();
                f24413b = new Handler(f24412a.getLooper());
            }
            p.d("PushServiceReceiver", context2.getPackageName() + ": start PushSerevice for by " + action + "  ; handler : " + f24413b);
            a.a(f24414c, context2, action);
            f24413b.removeCallbacks(f24414c);
            f24413b.postDelayed(f24414c, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
        }
    }
}
