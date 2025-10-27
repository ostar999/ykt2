package com.vivo.push.b;

import android.text.TextUtils;
import com.xiaomi.mipush.sdk.MIPushNotificationHelper4Hybrid;
import java.util.HashMap;

/* loaded from: classes6.dex */
public final class x extends com.vivo.push.o {

    /* renamed from: a, reason: collision with root package name */
    private HashMap<String, String> f24281a;

    /* renamed from: b, reason: collision with root package name */
    private long f24282b;

    public x() {
        super(2012);
    }

    public final void a(HashMap<String, String> map) {
        this.f24281a = map;
    }

    @Override // com.vivo.push.o
    public final void c(com.vivo.push.a aVar) {
        aVar.a("ReporterCommand.EXTRA_PARAMS", this.f24281a);
        aVar.a("ReporterCommand.EXTRA_REPORTER_TYPE", this.f24282b);
    }

    @Override // com.vivo.push.o
    public final void d(com.vivo.push.a aVar) {
        this.f24281a = (HashMap) aVar.d("ReporterCommand.EXTRA_PARAMS");
        this.f24282b = aVar.b("ReporterCommand.EXTRA_REPORTER_TYPE", this.f24282b);
    }

    @Override // com.vivo.push.o
    public final String toString() {
        return "ReporterCommand（" + this.f24282b + ")";
    }

    public x(long j2) {
        this();
        this.f24282b = j2;
    }

    public final void d() {
        if (this.f24281a == null) {
            com.vivo.push.util.p.d("ReporterCommand", "reportParams is empty");
            return;
        }
        StringBuilder sb = new StringBuilder("report message reportType:");
        sb.append(this.f24282b);
        sb.append(",msgId:");
        String str = this.f24281a.get(com.heytap.mcssdk.constant.b.f7178c);
        if (TextUtils.isEmpty(str)) {
            str = this.f24281a.get(MIPushNotificationHelper4Hybrid.KEY_MESSAGE_ID);
        }
        sb.append(str);
        com.vivo.push.util.p.d("ReporterCommand", sb.toString());
    }
}
