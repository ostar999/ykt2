package com.meizu.cloud.pushsdk.handler.a.c;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.a.b.g;

/* loaded from: classes4.dex */
public class e extends com.meizu.cloud.pushsdk.handler.a.a<g> {
    public e(Context context, com.meizu.cloud.pushsdk.handler.a aVar) {
        super(context, aVar);
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public int a() {
        return 262144;
    }

    @Override // com.meizu.cloud.pushsdk.handler.a.a
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void b(g gVar) {
        com.meizu.cloud.pushsdk.util.c.c(c(), gVar.c(), gVar.a().b().d(), gVar.a().b().a(), gVar.a().b().e(), gVar.a().b().b());
    }

    @Override // com.meizu.cloud.pushsdk.handler.a.a
    public void a(g gVar, com.meizu.cloud.pushsdk.notification.c cVar) {
        NotificationManager notificationManager = (NotificationManager) c().getSystemService(RemoteMessageConst.NOTIFICATION);
        if (notificationManager != null) {
            DebugLogger.e("AbstractMessageHandler", "start cancel notification id " + gVar.b());
            notificationManager.cancel(gVar.b());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0027  */
    @Override // com.meizu.cloud.pushsdk.handler.c
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean a(android.content.Intent r4) {
        /*
            r3 = this;
            java.lang.String r0 = "AbstractMessageHandler"
            java.lang.String r1 = "start WithDrawMessageHandler match"
            com.meizu.cloud.pushinternal.DebugLogger.i(r0, r1)
            java.lang.String r0 = "mz_push_control_message"
            java.lang.String r0 = r4.getStringExtra(r0)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            r2 = 0
            if (r1 != 0) goto L27
            com.meizu.cloud.pushsdk.handler.a.b.b r0 = com.meizu.cloud.pushsdk.handler.a.b.b.a(r0)
            com.meizu.cloud.pushsdk.handler.a.b.a r1 = r0.a()
            if (r1 == 0) goto L27
            com.meizu.cloud.pushsdk.handler.a.b.a r0 = r0.a()
            int r0 = r0.a()
            goto L28
        L27:
            r0 = r2
        L28:
            java.lang.String r1 = "com.meizu.flyme.push.intent.MESSAGE"
            java.lang.String r4 = r4.getAction()
            boolean r4 = r1.equals(r4)
            if (r4 == 0) goto L41
            java.lang.String r4 = "4"
            java.lang.String r0 = java.lang.String.valueOf(r0)
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L41
            r2 = 1
        L41:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.pushsdk.handler.a.c.e.a(android.content.Intent):boolean");
    }

    @Override // com.meizu.cloud.pushsdk.handler.a.a
    /* renamed from: j, reason: merged with bridge method [inline-methods] */
    public g c(Intent intent) {
        String stringExtra = intent.getStringExtra(PushConstants.MZ_PUSH_CONTROL_MESSAGE);
        String stringExtra2 = intent.getStringExtra(PushConstants.EXTRA_APP_PUSH_SEQ_ID);
        return new g(intent.getStringExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE), g(intent), stringExtra, intent.getStringExtra(PushConstants.MZ_PUSH_MESSAGE_STATISTICS_IMEI_KEY), stringExtra2);
    }
}
