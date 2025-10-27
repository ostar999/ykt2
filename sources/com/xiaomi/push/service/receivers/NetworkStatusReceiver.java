package com.xiaomi.push.service.receivers;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.mipush.sdk.COSPushHelper;
import com.xiaomi.mipush.sdk.HWPushHelper;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.ap;
import com.xiaomi.mipush.sdk.az;
import com.xiaomi.mipush.sdk.be;
import com.xiaomi.mipush.sdk.d;
import com.xiaomi.push.as;
import com.xiaomi.push.service.bf;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes6.dex */
public class NetworkStatusReceiver extends BroadcastReceiver {

    /* renamed from: b, reason: collision with other field name */
    private boolean f1088b = true;

    /* renamed from: a, reason: collision with other field name */
    private static BlockingQueue<Runnable> f1085a = new LinkedBlockingQueue();

    /* renamed from: a, reason: collision with root package name */
    private static int f25708a = 1;

    /* renamed from: b, reason: collision with root package name */
    private static int f25709b = 1;

    /* renamed from: c, reason: collision with root package name */
    private static int f25710c = 2;

    /* renamed from: a, reason: collision with other field name */
    private static ThreadPoolExecutor f1086a = new ThreadPoolExecutor(f25708a, f25709b, f25710c, TimeUnit.SECONDS, f1085a);

    /* renamed from: a, reason: collision with other field name */
    private static boolean f1087a = false;

    public NetworkStatusReceiver() {
    }

    public NetworkStatusReceiver(Object obj) {
        f1087a = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Context context) {
        if (!az.a(context).m147a() && d.m156a(context).m165c() && !d.m156a(context).m167e()) {
            try {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(context, "com.xiaomi.push.service.XMPushService"));
                intent.setAction("com.xiaomi.push.network_status_changed");
                bf.a(context).m730a(intent);
            } catch (Exception e2) {
                b.a(e2);
            }
        }
        if (as.b(context) && az.a(context).m150b()) {
            az.a(context).m151c();
        }
        if (as.b(context)) {
            if ("syncing".equals(ap.a(context).a(be.DISABLE_PUSH))) {
                MiPushClient.disablePush(context);
            }
            if ("syncing".equals(ap.a(context).a(be.ENABLE_PUSH))) {
                MiPushClient.enablePush(context);
            }
            if ("syncing".equals(ap.a(context).a(be.UPLOAD_HUAWEI_TOKEN))) {
                MiPushClient.syncAssemblePushToken(context);
            }
            if ("syncing".equals(ap.a(context).a(be.UPLOAD_FCM_TOKEN))) {
                MiPushClient.syncAssembleFCMPushToken(context);
            }
            if ("syncing".equals(ap.a(context).a(be.UPLOAD_COS_TOKEN))) {
                MiPushClient.syncAssembleCOSPushToken(context);
            }
            if (HWPushHelper.needConnect() && HWPushHelper.shouldTryConnect(context)) {
                HWPushHelper.setConnectTime(context);
                HWPushHelper.registerHuaWeiAssemblePush(context);
            }
            COSPushHelper.doInNetworkChange(context);
        }
    }

    public static boolean a() {
        return f1087a;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (this.f1088b) {
            return;
        }
        f1086a.execute(new a(this, context));
    }
}
