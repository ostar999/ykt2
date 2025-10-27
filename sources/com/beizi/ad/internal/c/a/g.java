package com.beizi.ad.internal.c.a;

import java.io.File;
import java.io.IOException;

/* loaded from: classes2.dex */
public class g extends e {

    /* renamed from: a, reason: collision with root package name */
    private final long f4058a;

    public g(long j2) {
        if (j2 <= 0) {
            throw new IllegalArgumentException("Max size must be positive number!");
        }
        this.f4058a = j2;
    }

    @Override // com.beizi.ad.internal.c.a.e, com.beizi.ad.internal.c.a.a
    public /* bridge */ /* synthetic */ void a(File file) throws IOException {
        super.a(file);
    }

    @Override // com.beizi.ad.internal.c.a.e
    public boolean a(File file, long j2, int i2) {
        return j2 <= this.f4058a;
    }
}
