package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.push.ds;
import com.xiaomi.push.hw;
import com.xiaomi.push.iq;
import com.xiaomi.push.je;

/* loaded from: classes6.dex */
public class q implements ds {

    /* renamed from: a, reason: collision with root package name */
    private Context f24577a;

    public q(Context context) {
        this.f24577a = context;
    }

    @Override // com.xiaomi.push.ds
    public String a() {
        return d.m156a(this.f24577a).d();
    }

    @Override // com.xiaomi.push.ds
    public void a(je jeVar, hw hwVar, iq iqVar) {
        az.a(this.f24577a).a((az) jeVar, hwVar, iqVar);
    }
}
