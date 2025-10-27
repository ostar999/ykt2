package com.meizu.cloud.pushsdk.b.c;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes4.dex */
public abstract class l implements Closeable {
    public abstract com.meizu.cloud.pushsdk.b.g.c a();

    public final InputStream b() {
        return a().d();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        m.a(a());
    }
}
