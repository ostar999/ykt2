package com.meizu.cloud.pushsdk.handler.a.a;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import cn.hutool.core.text.StrPool;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.a.b.f;
import com.meizu.cloud.pushsdk.util.c;
import java.io.File;

/* loaded from: classes4.dex */
public class a extends com.meizu.cloud.pushsdk.handler.a.a<f> {
    public a(Context context, com.meizu.cloud.pushsdk.handler.a aVar) {
        super(context, aVar);
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public int a() {
        return 65536;
    }

    @Override // com.meizu.cloud.pushsdk.handler.a.a
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void b(f fVar) {
        c.c(c(), c().getPackageName(), fVar.d().b().d(), fVar.d().b().a(), fVar.d().b().e(), fVar.d().b().b());
    }

    @Override // com.meizu.cloud.pushsdk.handler.a.a
    public void a(f fVar, com.meizu.cloud.pushsdk.notification.c cVar) {
        String message;
        DebugLogger.flush();
        String str = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/pushSdktmp/" + fVar.d().b().a() + StrPool.UNDERLINE + fVar.d().b().d() + ".zip";
        File file = null;
        try {
            new b(str).a(fVar.c());
            File file2 = new File(str);
            message = null;
            file = file2;
        } catch (Exception e2) {
            message = e2.getMessage();
            DebugLogger.e("AbstractMessageHandler", "zip error message " + message);
        }
        if (file != null && file.length() / 1024 > fVar.a()) {
            message = "the upload file exceeds the max size";
        } else if (fVar.b() && !com.meizu.cloud.pushsdk.util.a.b(c())) {
            message = "current network not allowed upload log file";
        }
        com.meizu.cloud.pushsdk.b.a.c<String> cVarA = com.meizu.cloud.pushsdk.platform.a.b.a(c()).a(fVar.d().b().a(), fVar.d().b().d(), message, file);
        if (cVarA == null || !cVarA.b()) {
            DebugLogger.i("AbstractMessageHandler", "upload error code " + cVarA.c() + cVarA.a());
            return;
        }
        if (file != null) {
            file.delete();
        }
        DebugLogger.e("AbstractMessageHandler", "upload success " + cVarA.a());
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
            java.lang.String r1 = "start LogUploadMessageHandler match"
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
            java.lang.String r4 = "2"
            java.lang.String r0 = java.lang.String.valueOf(r0)
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L41
            r2 = 1
        L41:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.pushsdk.handler.a.a.a.a(android.content.Intent):boolean");
    }

    @Override // com.meizu.cloud.pushsdk.handler.a.a
    /* renamed from: j, reason: merged with bridge method [inline-methods] */
    public f c(Intent intent) {
        String stringExtra = intent.getStringExtra(PushConstants.MZ_PUSH_CONTROL_MESSAGE);
        String stringExtra2 = intent.getStringExtra(PushConstants.EXTRA_APP_PUSH_SEQ_ID);
        return new f(intent.getStringExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE), stringExtra, intent.getStringExtra(PushConstants.MZ_PUSH_MESSAGE_STATISTICS_IMEI_KEY), stringExtra2);
    }
}
