package com.meizu.cloud.pushsdk.b.g;

/* loaded from: classes4.dex */
final class j {

    /* renamed from: a, reason: collision with root package name */
    static i f9206a;

    /* renamed from: b, reason: collision with root package name */
    static long f9207b;

    private j() {
    }

    public static i a() {
        synchronized (j.class) {
            i iVar = f9206a;
            if (iVar == null) {
                return new i();
            }
            f9206a = iVar.f9204f;
            iVar.f9204f = null;
            f9207b -= 2048;
            return iVar;
        }
    }

    public static void a(i iVar) {
        if (iVar.f9204f != null || iVar.f9205g != null) {
            throw new IllegalArgumentException();
        }
        if (iVar.f9202d) {
            return;
        }
        synchronized (j.class) {
            long j2 = f9207b;
            if (j2 + 2048 > 65536) {
                return;
            }
            f9207b = j2 + 2048;
            iVar.f9204f = f9206a;
            iVar.f9201c = 0;
            iVar.f9200b = 0;
            f9206a = iVar;
        }
    }
}
