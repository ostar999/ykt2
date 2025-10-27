package com.tencent.tbs.one.impl.c.a;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/* loaded from: classes6.dex */
public final class a implements Closeable {

    /* renamed from: a, reason: collision with root package name */
    public boolean f21810a;

    /* renamed from: b, reason: collision with root package name */
    public final RandomAccessFile f21811b;

    /* renamed from: c, reason: collision with root package name */
    public final File f21812c;

    /* renamed from: d, reason: collision with root package name */
    public final byte[] f21813d = new byte[8];

    public a(File file) {
        this.f21812c = file;
        this.f21811b = new RandomAccessFile(file, "r");
    }

    public final int a(byte[] bArr) {
        return this.f21811b.read(bArr);
    }

    public final int a(char[] cArr) throws IOException {
        byte[] bArr = new byte[cArr.length];
        int i2 = this.f21811b.read(bArr);
        for (int i3 = 0; i3 < cArr.length; i3++) {
            cArr[i3] = (char) bArr[i3];
        }
        return i2;
    }

    public final short a() throws IOException {
        short s2 = this.f21811b.readShort();
        if (!this.f21810a) {
            return s2;
        }
        return (short) (((s2 & 65280) >>> 8) | ((s2 & 255) << 8));
    }

    public final void a(long j2) throws IOException {
        this.f21811b.seek(j2);
    }

    public final int b() throws IOException {
        int i2 = this.f21811b.readInt();
        if (!this.f21810a) {
            return i2;
        }
        return ((i2 & (-16777216)) >>> 24) | ((i2 & 255) << 24) | ((65280 & i2) << 8) | ((16711680 & i2) >>> 8);
    }

    public final long c() throws IOException {
        if (!this.f21810a) {
            return this.f21811b.readLong();
        }
        this.f21811b.readFully(this.f21813d, 0, 8);
        byte[] bArr = this.f21813d;
        return (bArr[0] & 255) | (bArr[7] << 56) | ((bArr[6] & 255) << 48) | ((bArr[5] & 255) << 40) | ((bArr[4] & 255) << 32) | ((bArr[3] & 255) << 24) | ((bArr[2] & 255) << 16) | ((bArr[1] & 255) << 8);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws IOException {
        com.tencent.tbs.one.impl.a.d.a(this.f21811b);
    }
}
