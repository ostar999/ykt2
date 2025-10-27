package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.push.gn;
import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.XMPushService.a;
import com.xiaomi.push.service.at;
import java.io.IOException;
import java.util.Collection;
import org.json.JSONException;

/* loaded from: classes6.dex */
public class v extends XMPushService.i {

    /* renamed from: a, reason: collision with root package name */
    private XMPushService f25722a;

    /* renamed from: a, reason: collision with other field name */
    private String f1094a;

    /* renamed from: a, reason: collision with other field name */
    private byte[] f1095a;

    /* renamed from: b, reason: collision with root package name */
    private String f25723b;

    /* renamed from: c, reason: collision with root package name */
    private String f25724c;

    public v(XMPushService xMPushService, String str, String str2, String str3, byte[] bArr) {
        super(9);
        this.f25722a = xMPushService;
        this.f1094a = str;
        this.f1095a = bArr;
        this.f25723b = str2;
        this.f25724c = str3;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    public String a() {
        return "register app";
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public void mo463a() {
        at.b next;
        s sVarA = t.a((Context) this.f25722a);
        if (sVarA == null) {
            try {
                sVarA = t.a(this.f25722a, this.f1094a, this.f25723b, this.f25724c);
            } catch (IOException | JSONException e2) {
                com.xiaomi.channel.commonutils.logger.b.a(e2);
            }
        }
        if (sVarA == null) {
            com.xiaomi.channel.commonutils.logger.b.d("no account for mipush");
            w.a(this.f25722a, ErrorCode.ERROR_AUTHERICATION_ERROR, "no account.");
            return;
        }
        Collection<at.b> collectionM717a = at.a().m717a("5");
        if (collectionM717a.isEmpty()) {
            next = sVarA.a(this.f25722a);
            af.a(this.f25722a, next);
            at.a().a(next);
        } else {
            next = collectionM717a.iterator().next();
        }
        if (!this.f25722a.m702c()) {
            this.f25722a.a(true);
            return;
        }
        try {
            at.c cVar = next.f1010a;
            if (cVar == at.c.binded) {
                af.a(this.f25722a, this.f1094a, this.f1095a);
            } else if (cVar == at.c.unbind) {
                XMPushService xMPushService = this.f25722a;
                xMPushService.getClass();
                xMPushService.a(xMPushService.new a(next));
            }
        } catch (gn e3) {
            com.xiaomi.channel.commonutils.logger.b.a(e3);
            this.f25722a.a(10, e3);
        }
    }
}
