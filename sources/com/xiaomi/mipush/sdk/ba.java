package com.xiaomi.mipush.sdk;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.HashMap;

/* loaded from: classes6.dex */
class ba extends Handler {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ az f24542a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ba(az azVar, Looper looper) {
        super(looper);
        this.f24542a = azVar;
    }

    @Override // android.os.Handler
    public void dispatchMessage(Message message) {
        az azVar;
        HashMap<String, String> mapA;
        az azVar2;
        if (message.what != 19) {
            return;
        }
        String str = (String) message.obj;
        int i2 = message.arg1;
        synchronized (ap.class) {
            if (ap.a(this.f24542a.f135a).m134a(str)) {
                if (ap.a(this.f24542a.f135a).a(str) < 10) {
                    be beVar = be.DISABLE_PUSH;
                    if (beVar.ordinal() == i2 && "syncing".equals(ap.a(this.f24542a.f135a).a(beVar))) {
                        azVar2 = this.f24542a;
                    } else {
                        beVar = be.ENABLE_PUSH;
                        if (beVar.ordinal() == i2 && "syncing".equals(ap.a(this.f24542a.f135a).a(beVar))) {
                            azVar2 = this.f24542a;
                        } else {
                            be beVar2 = be.UPLOAD_HUAWEI_TOKEN;
                            if (beVar2.ordinal() == i2 && "syncing".equals(ap.a(this.f24542a.f135a).a(beVar2))) {
                                azVar = this.f24542a;
                                mapA = i.a(azVar.f135a, f.ASSEMBLE_PUSH_HUAWEI);
                            } else {
                                beVar2 = be.UPLOAD_FCM_TOKEN;
                                if (beVar2.ordinal() == i2 && "syncing".equals(ap.a(this.f24542a.f135a).a(beVar2))) {
                                    azVar = this.f24542a;
                                    mapA = i.a(azVar.f135a, f.ASSEMBLE_PUSH_FCM);
                                } else {
                                    beVar2 = be.UPLOAD_COS_TOKEN;
                                    if (beVar2.ordinal() == i2 && "syncing".equals(ap.a(this.f24542a.f135a).a(beVar2))) {
                                        azVar = this.f24542a;
                                        mapA = i.a(azVar.f135a, f.ASSEMBLE_PUSH_COS);
                                    }
                                    ap.a(this.f24542a.f135a).b(str);
                                }
                            }
                            azVar.a(str, beVar2, false, (HashMap<String, String>) mapA);
                            ap.a(this.f24542a.f135a).b(str);
                        }
                    }
                    azVar2.a(str, beVar, true, (HashMap<String, String>) null);
                    ap.a(this.f24542a.f135a).b(str);
                } else {
                    ap.a(this.f24542a.f135a).c(str);
                }
            }
        }
    }
}
