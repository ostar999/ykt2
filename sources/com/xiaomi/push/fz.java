package com.xiaomi.push;

import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public final class fz extends fv {
    public fz() {
        a("PING", (String) null);
        a("0");
        a(0);
    }

    @Override // com.xiaomi.push.fv
    /* renamed from: a */
    public ByteBuffer mo429a(ByteBuffer byteBuffer) {
        return m432a().length == 0 ? byteBuffer : super.mo429a(byteBuffer);
    }

    @Override // com.xiaomi.push.fv
    public int c() {
        if (m432a().length == 0) {
            return 0;
        }
        return super.c();
    }
}
