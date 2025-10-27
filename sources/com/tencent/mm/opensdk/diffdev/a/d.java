package com.tencent.mm.opensdk.diffdev.a;

/* loaded from: classes6.dex */
public enum d {
    UUID_EXPIRED(402),
    UUID_CANCELED(403),
    UUID_SCANED(404),
    UUID_CONFIRM(405),
    UUID_KEEP_CONNECT(408),
    UUID_ERROR(500);


    /* renamed from: a, reason: collision with root package name */
    private int f20454a;

    d(int i2) {
        this.f20454a = i2;
    }

    public int a() {
        return this.f20454a;
    }

    @Override // java.lang.Enum
    public String toString() {
        return "UUIDStatusCode:" + this.f20454a;
    }
}
