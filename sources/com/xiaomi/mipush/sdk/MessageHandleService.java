package com.xiaomi.mipush.sdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.PushMessageHandler;
import com.xiaomi.push.fl;
import com.xiaomi.push.fq;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes6.dex */
public class MessageHandleService extends BaseService {

    /* renamed from: a, reason: collision with root package name */
    private static ConcurrentLinkedQueue<a> f24505a = new ConcurrentLinkedQueue<>();

    /* renamed from: a, reason: collision with other field name */
    private static ExecutorService f105a = new ThreadPoolExecutor(1, 1, 15, TimeUnit.SECONDS, new LinkedBlockingQueue());

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private Intent f24506a;

        /* renamed from: a, reason: collision with other field name */
        private PushMessageReceiver f106a;

        public a(Intent intent, PushMessageReceiver pushMessageReceiver) {
            this.f106a = pushMessageReceiver;
            this.f24506a = intent;
        }

        public Intent a() {
            return this.f24506a;
        }

        /* renamed from: a, reason: collision with other method in class */
        public PushMessageReceiver m126a() {
            return this.f106a;
        }
    }

    public static void a(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        b(context);
    }

    public static void addJob(Context context, a aVar) {
        if (aVar != null) {
            f24505a.add(aVar);
            b(context);
            startService(context);
        }
    }

    private static void b(Context context) {
        if (f105a.isShutdown()) {
            return;
        }
        f105a.execute(new af(context));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(Context context) {
        String[] stringArrayExtra;
        try {
            a aVarPoll = f24505a.poll();
            if (aVarPoll == null) {
                return;
            }
            PushMessageReceiver pushMessageReceiverM126a = aVarPoll.m126a();
            Intent intentA = aVarPoll.a();
            int intExtra = intentA.getIntExtra("message_type", 1);
            if (intExtra == 1) {
                PushMessageHandler.a aVarA = aw.a(context).a(intentA);
                int intExtra2 = intentA.getIntExtra("eventMessageType", -1);
                if (aVarA == null) {
                    return;
                }
                if (aVarA instanceof MiPushMessage) {
                    MiPushMessage miPushMessage = (MiPushMessage) aVarA;
                    if (!miPushMessage.isArrivedMessage()) {
                        pushMessageReceiverM126a.onReceiveMessage(context, miPushMessage);
                    }
                    if (miPushMessage.getPassThrough() == 1) {
                        fl.a(context.getApplicationContext()).a(context.getPackageName(), intentA, 2004, "call passThrough callBack");
                        pushMessageReceiverM126a.onReceivePassThroughMessage(context, miPushMessage);
                        return;
                    }
                    if (!miPushMessage.isNotified()) {
                        pushMessageReceiverM126a.onNotificationMessageArrived(context, miPushMessage);
                        return;
                    }
                    if (intExtra2 == 1000) {
                        fl.a(context.getApplicationContext()).a(context.getPackageName(), intentA, 1007, "call notification callBack");
                    } else {
                        fl.a(context.getApplicationContext()).a(context.getPackageName(), intentA, 3007, "call business callBack");
                    }
                    com.xiaomi.channel.commonutils.logger.b.m117a("begin execute onNotificationMessageClicked from\u3000" + miPushMessage.getMessageId());
                    pushMessageReceiverM126a.onNotificationMessageClicked(context, miPushMessage);
                    return;
                }
                if (!(aVarA instanceof MiPushCommandMessage)) {
                    return;
                }
                MiPushCommandMessage miPushCommandMessage = (MiPushCommandMessage) aVarA;
                pushMessageReceiverM126a.onCommandResult(context, miPushCommandMessage);
                if (!TextUtils.equals(miPushCommandMessage.getCommand(), fq.COMMAND_REGISTER.f429a)) {
                    return;
                }
                pushMessageReceiverM126a.onReceiveRegisterResult(context, miPushCommandMessage);
                if (miPushCommandMessage.getResultCode() != 0) {
                    return;
                }
            } else {
                if (intExtra != 3) {
                    if (intExtra == 5 && PushMessageHelper.ERROR_TYPE_NEED_PERMISSION.equals(intentA.getStringExtra(PushMessageHelper.ERROR_TYPE)) && (stringArrayExtra = intentA.getStringArrayExtra(PushMessageHelper.ERROR_MESSAGE)) != null) {
                        pushMessageReceiverM126a.onRequirePermissions(context, stringArrayExtra);
                        return;
                    }
                    return;
                }
                MiPushCommandMessage miPushCommandMessage2 = (MiPushCommandMessage) intentA.getSerializableExtra(PushMessageHelper.KEY_COMMAND);
                pushMessageReceiverM126a.onCommandResult(context, miPushCommandMessage2);
                if (!TextUtils.equals(miPushCommandMessage2.getCommand(), fq.COMMAND_REGISTER.f429a)) {
                    return;
                }
                pushMessageReceiverM126a.onReceiveRegisterResult(context, miPushCommandMessage2);
                if (miPushCommandMessage2.getResultCode() != 0) {
                    return;
                }
            }
            i.b(context);
        } catch (RuntimeException e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
    }

    public static void startService(Context context) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(context, (Class<?>) MessageHandleService.class));
        com.xiaomi.push.ai.a(context).a(new ae(context, intent));
    }

    @Override // com.xiaomi.mipush.sdk.BaseService
    /* renamed from: a */
    public boolean mo132a() {
        ConcurrentLinkedQueue<a> concurrentLinkedQueue = f24505a;
        return concurrentLinkedQueue != null && concurrentLinkedQueue.size() > 0;
    }

    @Override // com.xiaomi.mipush.sdk.BaseService, android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // com.xiaomi.mipush.sdk.BaseService, android.app.Service
    public void onStart(Intent intent, int i2) {
        super.onStart(intent, i2);
    }
}
