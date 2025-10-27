package com.vivo.push.b;

import android.text.TextUtils;
import com.vivo.push.model.InsideNotificationItem;

/* loaded from: classes6.dex */
public final class p extends com.vivo.push.o {

    /* renamed from: a, reason: collision with root package name */
    private String f24265a;

    /* renamed from: b, reason: collision with root package name */
    private String f24266b;

    /* renamed from: c, reason: collision with root package name */
    private byte[] f24267c;

    /* renamed from: d, reason: collision with root package name */
    private long f24268d;

    /* renamed from: e, reason: collision with root package name */
    private InsideNotificationItem f24269e;

    public p(String str, long j2, InsideNotificationItem insideNotificationItem) {
        super(5);
        this.f24265a = str;
        this.f24268d = j2;
        this.f24269e = insideNotificationItem;
    }

    @Override // com.vivo.push.o
    public final void c(com.vivo.push.a aVar) {
        aVar.a("package_name", this.f24265a);
        aVar.a("notify_id", this.f24268d);
        aVar.a("notification_v1", com.vivo.push.util.q.b(this.f24269e));
        aVar.a("open_pkg_name", this.f24266b);
        aVar.a("open_pkg_name_encode", this.f24267c);
    }

    public final String d() {
        return this.f24265a;
    }

    public final long e() {
        return this.f24268d;
    }

    public final InsideNotificationItem f() {
        return this.f24269e;
    }

    @Override // com.vivo.push.o
    public final String toString() {
        return "OnNotificationClickCommand";
    }

    @Override // com.vivo.push.o
    public final void d(com.vivo.push.a aVar) {
        this.f24265a = aVar.a("package_name");
        this.f24268d = aVar.b("notify_id", -1L);
        this.f24266b = aVar.a("open_pkg_name");
        this.f24267c = aVar.b("open_pkg_name_encode");
        String strA = aVar.a("notification_v1");
        if (!TextUtils.isEmpty(strA)) {
            this.f24269e = com.vivo.push.util.q.a(strA);
        }
        InsideNotificationItem insideNotificationItem = this.f24269e;
        if (insideNotificationItem != null) {
            insideNotificationItem.setMsgId(this.f24268d);
        }
    }

    public p() {
        super(5);
    }
}
