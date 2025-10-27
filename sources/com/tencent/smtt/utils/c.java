package com.tencent.smtt.utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/* loaded from: classes6.dex */
public class c implements Closeable {

    /* renamed from: a, reason: collision with root package name */
    private final RandomAccessFile f21459a;

    /* renamed from: b, reason: collision with root package name */
    private final File f21460b;

    /* renamed from: c, reason: collision with root package name */
    private final byte[] f21461c;

    /* renamed from: d, reason: collision with root package name */
    private boolean f21462d;

    public c(File file) throws FileNotFoundException {
        this.f21461c = new byte[8];
        this.f21460b = file;
        this.f21459a = new RandomAccessFile(file, "r");
    }

    public c(String str) throws FileNotFoundException {
        this(new File(str));
    }

    public final int a(byte[] bArr) throws IOException {
        return this.f21459a.read(bArr);
    }

    public final int a(char[] cArr) throws IOException {
        byte[] bArr = new byte[cArr.length];
        int i2 = this.f21459a.read(bArr);
        for (int i3 = 0; i3 < cArr.length; i3++) {
            cArr[i3] = (char) bArr[i3];
        }
        return i2;
    }

    public final short a() throws IOException {
        short s2 = this.f21459a.readShort();
        if (!this.f21462d) {
            return s2;
        }
        return (short) (((s2 & 65280) >>> 8) | ((s2 & 255) << 8));
    }

    public void a(long j2) throws IOException {
        this.f21459a.seek(j2);
    }

    public void a(boolean z2) {
        this.f21462d = z2;
    }

    public final int b() throws IOException {
        int i2 = this.f21459a.readInt();
        if (!this.f21462d) {
            return i2;
        }
        return ((i2 & (-16777216)) >>> 24) | ((i2 & 255) << 24) | ((65280 & i2) << 8) | ((16711680 & i2) >>> 8);
    }

    public final long c() throws IOException {
        if (!this.f21462d) {
            return this.f21459a.readLong();
        }
        this.f21459a.readFully(this.f21461c, 0, 8);
        byte[] bArr = this.f21461c;
        return (bArr[0] & 255) | (bArr[7] << 56) | ((bArr[6] & 255) << 48) | ((bArr[5] & 255) << 40) | ((bArr[4] & 255) << 32) | ((bArr[3] & 255) << 24) | ((bArr[2] & 255) << 16) | ((bArr[1] & 255) << 8);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        try {
            this.f21459a.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}
