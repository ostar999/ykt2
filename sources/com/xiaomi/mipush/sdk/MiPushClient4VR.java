package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.push.hw;
import com.xiaomi.push.in;
import com.xiaomi.push.iq;
import com.xiaomi.push.je;

/* loaded from: classes6.dex */
public class MiPushClient4VR {
    public static void uploadData(Context context, String str) {
        je jeVar = new je();
        jeVar.c(in.VRUpload.f622a);
        jeVar.b(d.m156a(context).m157a());
        jeVar.d(context.getPackageName());
        jeVar.a("data", str);
        jeVar.a(com.xiaomi.push.service.ar.a());
        az.a(context).a((az) jeVar, hw.Notification, (iq) null);
    }
}
