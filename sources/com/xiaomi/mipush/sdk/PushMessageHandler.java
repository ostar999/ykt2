package com.xiaomi.mipush.sdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.MessageHandleService;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.push.fl;
import com.xiaomi.push.fq;
import com.xiaomi.push.ib;
import com.xiaomi.push.jp;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes6.dex */
public class PushMessageHandler extends BaseService {

    /* renamed from: a, reason: collision with root package name */
    private static List<MiPushClient.MiPushClientCallback> f24511a = new ArrayList();

    /* renamed from: a, reason: collision with other field name */
    private static ThreadPoolExecutor f118a = new ThreadPoolExecutor(1, 1, 15, TimeUnit.SECONDS, new LinkedBlockingQueue());

    public interface a extends Serializable {
    }

    public static void a() {
        synchronized (f24511a) {
            f24511a.clear();
        }
    }

    public static void a(long j2, String str, String str2) {
        synchronized (f24511a) {
            Iterator<MiPushClient.MiPushClientCallback> it = f24511a.iterator();
            while (it.hasNext()) {
                it.next().onInitializeResult(j2, str, str2);
            }
        }
    }

    public static void a(Context context) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(context, (Class<?>) PushMessageHandler.class));
        try {
            context.startService(intent);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m117a(e2.getMessage());
        }
    }

    public static void a(Context context, Intent intent) {
        com.xiaomi.channel.commonutils.logger.b.c("addjob PushMessageHandler " + intent);
        if (intent != null) {
            c(context, intent);
            a(context);
        }
    }

    private static void a(Context context, Intent intent, ResolveInfo resolveInfo) {
        try {
            MessageHandleService.addJob(context.getApplicationContext(), new MessageHandleService.a(intent, (PushMessageReceiver) Class.forName(resolveInfo.activityInfo.name).newInstance()));
            MessageHandleService.a(context, new Intent(context.getApplicationContext(), (Class<?>) MessageHandleService.class));
        } catch (Throwable th) {
            com.xiaomi.channel.commonutils.logger.b.a(th);
        }
    }

    public static void a(Context context, MiPushMessage miPushMessage) {
        synchronized (f24511a) {
            for (MiPushClient.MiPushClientCallback miPushClientCallback : f24511a) {
                if (a(miPushMessage.getCategory(), miPushClientCallback.getCategory())) {
                    miPushClientCallback.onReceiveMessage(miPushMessage.getContent(), miPushMessage.getAlias(), miPushMessage.getTopic(), miPushMessage.isNotified());
                    miPushClientCallback.onReceiveMessage(miPushMessage);
                }
            }
        }
    }

    public static void a(Context context, a aVar) {
        if (aVar instanceof MiPushMessage) {
            a(context, (MiPushMessage) aVar);
            return;
        }
        if (aVar instanceof MiPushCommandMessage) {
            MiPushCommandMessage miPushCommandMessage = (MiPushCommandMessage) aVar;
            String command = miPushCommandMessage.getCommand();
            String str = null;
            if (fq.COMMAND_REGISTER.f429a.equals(command)) {
                List<String> commandArguments = miPushCommandMessage.getCommandArguments();
                if (commandArguments != null && !commandArguments.isEmpty()) {
                    str = commandArguments.get(0);
                }
                a(miPushCommandMessage.getResultCode(), miPushCommandMessage.getReason(), str);
                return;
            }
            if (fq.COMMAND_SET_ALIAS.f429a.equals(command) || fq.COMMAND_UNSET_ALIAS.f429a.equals(command) || fq.COMMAND_SET_ACCEPT_TIME.f429a.equals(command)) {
                a(context, miPushCommandMessage.getCategory(), command, miPushCommandMessage.getResultCode(), miPushCommandMessage.getReason(), miPushCommandMessage.getCommandArguments());
                return;
            }
            if (fq.COMMAND_SUBSCRIBE_TOPIC.f429a.equals(command)) {
                List<String> commandArguments2 = miPushCommandMessage.getCommandArguments();
                if (commandArguments2 != null && !commandArguments2.isEmpty()) {
                    str = commandArguments2.get(0);
                }
                a(context, miPushCommandMessage.getCategory(), miPushCommandMessage.getResultCode(), miPushCommandMessage.getReason(), str);
                return;
            }
            if (fq.COMMAND_UNSUBSCRIBE_TOPIC.f429a.equals(command)) {
                List<String> commandArguments3 = miPushCommandMessage.getCommandArguments();
                if (commandArguments3 != null && !commandArguments3.isEmpty()) {
                    str = commandArguments3.get(0);
                }
                b(context, miPushCommandMessage.getCategory(), miPushCommandMessage.getResultCode(), miPushCommandMessage.getReason(), str);
            }
        }
    }

    public static void a(Context context, String str, long j2, String str2, String str3) {
        synchronized (f24511a) {
            for (MiPushClient.MiPushClientCallback miPushClientCallback : f24511a) {
                if (a(str, miPushClientCallback.getCategory())) {
                    miPushClientCallback.onSubscribeResult(j2, str2, str3);
                }
            }
        }
    }

    public static void a(Context context, String str, String str2, long j2, String str3, List<String> list) {
        synchronized (f24511a) {
            for (MiPushClient.MiPushClientCallback miPushClientCallback : f24511a) {
                if (a(str, miPushClientCallback.getCategory())) {
                    miPushClientCallback.onCommandResult(str2, j2, str3, list);
                }
            }
        }
    }

    public static void a(MiPushClient.MiPushClientCallback miPushClientCallback) {
        synchronized (f24511a) {
            if (!f24511a.contains(miPushClientCallback)) {
                f24511a.add(miPushClientCallback);
            }
        }
    }

    public static boolean a(String str, String str2) {
        return (TextUtils.isEmpty(str) && TextUtils.isEmpty(str2)) || TextUtils.equals(str, str2);
    }

    public static void b(Context context, Intent intent) {
        try {
            ResolveInfo resolveInfo = null;
            if ("com.xiaomi.mipush.sdk.WAKEUP".equals(intent.getAction())) {
                o.a(context, intent, null);
                return;
            }
            if ("com.xiaomi.mipush.SEND_TINYDATA".equals(intent.getAction())) {
                ib ibVar = new ib();
                jp.a(ibVar, intent.getByteArrayExtra("mipush_payload"));
                com.xiaomi.channel.commonutils.logger.b.c("PushMessageHandler.onHandleIntent " + ibVar.d());
                MiTinyDataClient.upload(context, ibVar);
                return;
            }
            if ("com.xiaomi.mipush.sdk.HYBRID_NOTIFICATION_CLICK".equals(intent.getAction())) {
                MiPushMessage miPushMessage = (MiPushMessage) intent.getSerializableExtra("mipush_payload");
                String stringExtra = intent.getStringExtra("mipush_hybrid_app_pkg");
                MiPushClient.reportMessageClicked(context, miPushMessage);
                MiPushClient4Hybrid.onNotificationMessageClicked(context, stringExtra, miPushMessage);
                return;
            }
            if (1 == PushMessageHelper.getPushMode(context)) {
                if (b()) {
                    com.xiaomi.channel.commonutils.logger.b.d("receive a message before application calling initialize");
                    return;
                }
                a aVarA = aw.a(context).a(intent);
                if (aVarA != null) {
                    a(context, aVarA);
                    return;
                }
                return;
            }
            if ("com.xiaomi.mipush.sdk.SYNC_LOG".equals(intent.getAction())) {
                Logger.uploadLogFile(context, false);
                return;
            }
            Intent intent2 = new Intent("com.xiaomi.mipush.RECEIVE_MESSAGE");
            intent2.setPackage(context.getPackageName());
            intent2.putExtras(intent);
            try {
                List<ResolveInfo> listQueryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent2, 32);
                if (listQueryBroadcastReceivers != null) {
                    Iterator<ResolveInfo> it = listQueryBroadcastReceivers.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        ResolveInfo next = it.next();
                        ActivityInfo activityInfo = next.activityInfo;
                        if (activityInfo != null && activityInfo.packageName.equals(context.getPackageName()) && PushMessageReceiver.class.isAssignableFrom(Class.forName(next.activityInfo.name))) {
                            resolveInfo = next;
                            break;
                        }
                    }
                }
                if (resolveInfo != null) {
                    a(context, intent2, resolveInfo);
                } else {
                    com.xiaomi.channel.commonutils.logger.b.d("cannot find the receiver to handler this message, check your manifest");
                    fl.a(context).a(context.getPackageName(), intent, "cannot find the receiver to handler this message, check your manifest");
                }
            } catch (Exception e2) {
                com.xiaomi.channel.commonutils.logger.b.a(e2);
                fl.a(context).a(context.getPackageName(), intent, e2);
            }
        } catch (Throwable th) {
            com.xiaomi.channel.commonutils.logger.b.a(th);
            fl.a(context).a(context.getPackageName(), intent, th);
        }
    }

    public static void b(Context context, String str, long j2, String str2, String str3) {
        synchronized (f24511a) {
            for (MiPushClient.MiPushClientCallback miPushClientCallback : f24511a) {
                if (a(str, miPushClientCallback.getCategory())) {
                    miPushClientCallback.onUnsubscribeResult(j2, str2, str3);
                }
            }
        }
    }

    public static boolean b() {
        return f24511a.isEmpty();
    }

    private static void c(Context context, Intent intent) {
        if (intent == null || f118a.isShutdown()) {
            return;
        }
        f118a.execute(new av(context, intent));
    }

    @Override // com.xiaomi.mipush.sdk.BaseService
    /* renamed from: a, reason: collision with other method in class */
    public boolean mo132a() {
        ThreadPoolExecutor threadPoolExecutor = f118a;
        return (threadPoolExecutor == null || threadPoolExecutor.getQueue() == null || f118a.getQueue().size() <= 0) ? false : true;
    }

    @Override // com.xiaomi.mipush.sdk.BaseService, android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // com.xiaomi.mipush.sdk.BaseService, android.app.Service
    public void onStart(Intent intent, int i2) {
        super.onStart(intent, i2);
        c(getApplicationContext(), intent);
    }
}
