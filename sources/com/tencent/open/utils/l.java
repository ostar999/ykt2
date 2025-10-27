package com.tencent.open.utils;

/* loaded from: classes6.dex */
public final class l implements Cloneable {

    /* renamed from: a, reason: collision with root package name */
    private long f20694a;

    public l(long j2) {
        this.f20694a = j2;
    }

    public byte[] a() {
        long j2 = this.f20694a;
        return new byte[]{(byte) (255 & j2), (byte) ((65280 & j2) >> 8), (byte) ((16711680 & j2) >> 16), (byte) ((j2 & 4278190080L) >> 24)};
    }

    public long b() {
        return this.f20694a;
    }

    public boolean equals(Object obj) {
        return obj != null && (obj instanceof l) && this.f20694a == ((l) obj).b();
    }

    public int hashCode() {
        return (int) this.f20694a;
    }
}
