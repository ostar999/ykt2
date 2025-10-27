package com.meizu.cloud.pushsdk;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Process;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.base.a.d;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.a.b.b;
import com.meizu.cloud.pushsdk.util.c;
import java.util.List;

/* loaded from: classes4.dex */
public class NotificationService extends IntentService {

    /* renamed from: a, reason: collision with root package name */
    private Object f8909a;

    public NotificationService() {
        super("NotificationService");
    }

    public NotificationService(String str) {
        super(str);
    }

    public String a(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        Intent intent = new Intent(str2);
        intent.setPackage(str);
        List<ResolveInfo> listQueryBroadcastReceivers = getPackageManager().queryBroadcastReceivers(intent, 0);
        if (listQueryBroadcastReceivers == null || listQueryBroadcastReceivers.size() <= 0) {
            return null;
        }
        return listQueryBroadcastReceivers.get(0).activityInfo.name;
    }

    public void a(Intent intent) {
        String strA = a(getPackageName(), intent.getAction());
        if (TextUtils.isEmpty(strA)) {
            c.a(this, intent, "reflectReceiver sendbroadcast", 2005);
            DebugLogger.i("NotificationService", " reflectReceiver error: receiver for: " + intent.getAction() + " not found, package: " + getPackageName());
            intent.setPackage(getPackageName());
            sendBroadcast(intent);
            return;
        }
        try {
            c.a(this, intent, "reflectReceiver startservice", 2003);
            intent.setClassName(getPackageName(), strA);
            d dVarA = com.meizu.cloud.pushsdk.base.a.a.a(strA).a((Class<?>[]) null).a(null);
            if (!dVarA.f9230a || dVarA.f9231b == 0) {
                return;
            }
            DebugLogger.i("NotificationService", "Reflect MzPushReceiver " + dVarA.f9230a);
            com.meizu.cloud.pushsdk.base.a.a.a(dVarA.f9231b).a("onReceive", Context.class, Intent.class).a(dVarA.f9231b, getApplicationContext(), intent);
        } catch (Exception e2) {
            DebugLogger.i("NotificationService", "reflect e: " + e2);
            c.a(this, intent, e2.getMessage(), 2004);
        }
    }

    @Override // android.app.IntentService, android.app.Service
    public void onDestroy() {
        DebugLogger.i("NotificationService", "NotificationService destroy");
        this.f8909a = null;
        super.onDestroy();
    }

    @Override // android.app.IntentService
    public void onHandleIntent(Intent intent) throws SecurityException, IllegalArgumentException {
        Process.setThreadPriority(10);
        if (intent != null) {
            try {
                DebugLogger.i("NotificationService", "onHandleIntentaction " + intent.getAction());
                String stringExtra = intent.getStringExtra("command_type");
                boolean z2 = PushConstants.MZ_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction()) || PushConstants.MZ_PUSH_ON_REGISTER_ACTION.equals(intent.getAction()) || PushConstants.MZ_PUSH_ON_UNREGISTER_ACTION.equals(intent.getAction());
                DebugLogger.d("NotificationService", "-- command_type -- " + stringExtra + " legalAction " + z2);
                if (!TextUtils.isEmpty(stringExtra) && stringExtra.equals("reflect_receiver") && z2) {
                    String stringExtra2 = intent.getStringExtra(PushConstants.MZ_PUSH_CONTROL_MESSAGE);
                    DebugLogger.i("NotificationService", "control message is " + stringExtra2);
                    if (!TextUtils.isEmpty(stringExtra2)) {
                        com.meizu.cloud.pushsdk.c.a.a(this, new b(stringExtra2, null, null).b().c());
                    }
                    a(intent);
                }
            } catch (Exception e2) {
                DebugLogger.e("NotificationService", "onHandleIntent error " + e2.getMessage());
            }
        }
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
